package haiku.rcx;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import haiku.vm.HaikuMagic;
import haiku.vm.NativeCFunction;
import haiku.vm.NativeCVariable8;
import static haiku.rcx.lib.ROM.*;

/**
 * manually transpiled from rcx_impl/main.c into Java
 *
 * When your program is uploaded this class
 * 1) initializes the RCX
 * 2) initializes System.out and System.in for 2400 baud
 * 3) plays a beep and clears the display
 * 4) starts your program
 *
 * If your program comes back it reboots in a way to be ready for upload again.
 *
 * @author Genom
 *
 */
public class HaikuMicroKernel extends haiku.vm.MicroKernel {
	static byte[] timerdata1 = new byte[6];
	static async_t timerdata0 = new async_t();

//	public static final int BAUD_115200=		5;
//	public static final int BAUD_57600=			11;
//	public static final int BAUD_56000=			11;
//	public static final int BAUD_38400=			17;
//	public static final int BAUD_9600=			71;

	/**
	 *  values for the bit rate register BRR
	 *  assuming CMR_CLOCK selected on 16 MHz processor
	 *  error <= 0.16%
	 *
	 */
	public static final int B2400=		207;
	public static final int B4800=		103;
	public static final int B9600=		51;
	public static final int B19200=		25;
	public static final int B38400=		12;

	/** Serial baud rate register	 */
	@NativeCVariable8
	private static int S_BRR;

	/** Serial receive data register */
    @NativeCVariable8
	private static int S_RDR;

    /** Serial transmit data register */
    @NativeCVariable8
	private static int S_TDR;

	/** Serial mode register */
    @NativeCVariable8
	private static int S_MR;

	public static final int SMR_SYNC=	0x80;	   // in sync mode, the other settings
	public static final int SMR_ASYNC=	0x00;	   // have no effect.

	public static final int SMR_7BIT=	0x40;
	public static final int SMR_8BIT=	0x00;

	public static final int SMR_P_NONE=	0x00;
	public static final int SMR_P_EVEN=	0x20;
	public static final int SMR_P_ODD=	0x30;

	public static final int SMR_1STOP=	0x00;
	public static final int SMR_2STOP=	0x08;

	/** Serial control register */
    @NativeCVariable8
	private static int S_CR;

	public static final int SCR_TX_IRQ=		0x80;	   // Transmit irq enable
	public static final int SCR_RX_IRQ=		0x40;	   // Receive / recv err irq enable
	public static final int SCR_TRANSMIT=	0x20;	   // Enable transmission
	public static final int SCR_RECEIVE=	0x10;	   // Enable receiving
	public static final int SCR_TE_IRQ=		0x04;	   // Transmit end irq enable

	/** Serial status register */
    @NativeCVariable8
	private static byte S_SR;

	public static final int SSR_TRANS_EMPTY=	0x80;	   // Transmit buffer empty
	public static final int SSR_RECV_FULL=		0x40;	   // Receive buffer full
	public static final int SSR_OVERRUN_ERR=	0x20;	   // Overrun error
	public static final int SSR_FRAMING_ERR=	0x10;	   // Framing error
	public static final int SSR_PARITY_ERR=		0x08;	   // Parity error
	public static final int SSR_ERRORS=      	0x38;       // All errors
	public static final int SSR_TRANS_END=		0x04;	   // Transmission end because buffer empty

	private static int iostate=0;

	private static void init() {
        System.out = new PrintStream(new OutputStream() {

            @Override
        	/**
        	 * http://www.datasheetarchive.com/files/renesas/html/h8_300h_an.htm#
        	 * Example of Using User Boot Mode of Renesas 0.18μm SH&H8 Flash Devices with Xmodem Data Transfer
        	 * see serial.c it looks like:
        	 *   while ( SCI_SSR.BIT.TDRE == 0 );
        	 *   SCI_TDR = b;
        	 *   SCI_SSR.BIT.TDRE = 0;
        	 *
        	 * @param b
        	 */
        	public void write(int b) {
            	while ((S_SR & SSR_TRANS_END)==0); // wait while NOT SSR_TRANS_END
        		S_TDR = b;      // transmit byte
        		S_SR &= ~SSR_TRANS_EMPTY; // without this outputs only 'A' and hangs for 'B'
        		iostate=1;
        	}

        });

        System.err = System.out;

        System.in = new InputStream() {
        	private int available = 0;

            public int available() {
            	if (available==0) {
                    available = S_SR & SSR_RECV_FULL;
                    if (available==0) {
                        if ((S_SR & (SSR_OVERRUN_ERR | SSR_FRAMING_ERR | SSR_PARITY_ERR)) != 0) {
                			// clear error flags
                            S_SR &= ~(SSR_OVERRUN_ERR | SSR_FRAMING_ERR | SSR_PARITY_ERR);
                        }
                    } else if (iostate==1) {
                    	// is there a snappier way to clear the IR echo-garbage input buffer?
                    	read();
                    	available = S_SR & SSR_RECV_FULL;
                    	if (available != 0) read();
                    }
            	}
        		return available;
            }

            public int read() {
	            while (available() == 0);
	            available = 0; // consume
	            int b=S_RDR & 0xff;
    			// clear receive flag
                S_SR &= ~(SSR_RECV_FULL);
        		iostate=2;
                return b;
            }
        };

        //S_BRR = B2400;
        //S_MR = 	SMR_ASYNC | SMR_8BIT | SMR_P_ODD | SMR_1STOP;
		S_CR = SCR_TRANSMIT | SCR_RECEIVE; // enable Tx and Rx
	}

	@NativeCFunction
	private static native void systime_init();

	public static void main(String[] args) {
	    clinitHaikuMagic();

		// Power up initializations
		// The following call always needs to be the first one.
		init_timer(timerdata0, timerdata1);

		// Set sleep mode to 'standby' plus other stuff needed for init_serial()
		init_power();

		// Initialize timer handler.
		systime_init();

		// Initialize serial I/O
		// reset_rcx_serial();
		//init_serial (&timerdata1[4], &timerdata0, 1, 1);
		init_serial (null, null, 1, 1);

		init();

		play_system_sound (SOUND_QUEUED, 1);
		clear_display();

		try {
			/*
			 * The following call of panic()
			 * and consequently the method body panic()
			 * will be extingted from code if property PanicSupport is set to 0
			 * .. -DPanicSupport=0 ..
			 * (by some HaikuVM bytecode manipulation magic)
			 */
			panic(0, 0);
			//System.out.println("stacktop="+stacktop());

			HaikuMagic.main(args); // Will call your main method (by some HaikuVM bytecode manipulation magic)
			Thread.sleep(1000); // Let the hardware write potential pending output bytes
		} catch (Throwable e) {
            System.out.println(e.toString());
		}

		// Program terminated.

		haiku.vm.MemoryAccess.setMemory8(0xffcc, 1);
		exit(1);
	}

}
