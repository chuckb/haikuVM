package haiku.rcx;

import java.io.IOException;
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
 * In addition I found that IR communication is not reliable. Roughly 1 bit error on every 500 bits. So I investigate on ECC.
 * The idea is to send 2 Bytes to deliver 1 reliable data Byte (12, 8) or even simpler (16, 8) coding.
 * http://www.researchgate.net/publication/228819322_A_%2816_8%29_error_correcting_code_%28t_2%29_for_critical_memory_applications
 *
 * Here are some useful functions I found for ecc (or better fec)
 * ecc for at least 1 bit error (often more up to some 3 bit errors)
 *  (16, 8)
 *  	y=233*(x + 6) <==> 233*b + 2281
 *
 *  (16, 10)
 *  	y=61*x + 1361 <==> 61*(x+22) + 19
 *  or another simple (small bytecode footprint) alternative
 *  	y=61*(x + 25)
 *
 * ecc for at least 2 bit error (often more up to some 3 bit errors)
 *  (16, 8)
 *  	 y= (x ^ ((x ^ ((x ^ ((x ^ (x<<3))<<1))<<1))<<3))
 *  <==> y= x ^ 8*(x ^ 2*(x ^ 2*(x ^ 8*x)))
 *  <==> y= x ^ 8*x ^ 16*x ^ 32*x ^ 256*x
 *
 * see write(..)
 *
 * @author Genom
 *
 */
public class HaikuMicroKernel4JUnits extends haiku.vm.MicroKernel {
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

	private static volatile long bit_delay;

	/**
	 * <pre>
	 * http://www.datasheetarchive.com/files/renesas/html/h8_300h_an.htm#
	 * Example of Using User Boot Mode of Renesas 0.18μm SH&H8 Flash Devices with Xmodem Data Transfer
	 * see serial.c it looks like:
	 *   while ( SCI_SSR.BIT.TDRE == 0 );
	 *   SCI_TDR = b;
	 *   SCI_SSR.BIT.TDRE = 0;
	 * </pre>
	 */
	private static void init() {
        System.out = new PrintStream(new OutputStream() {

            @Override
        	/**
        	 *
        	 * @param b
        	 */
        	public void write(int b) {
            	int fec=61*(b + 25);
            	write0(fec>>8);
            	write0(fec&0xff);
        	}

        	public void write0(int b) {
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
		//S_CR = SCR_TRANSMIT | SCR_RECEIVE; // enable Tx and Rx


		// serial port initialisation
		//
		// disable Tx and Rx
		//SCI_SCR.BIT.TE = 0;
		//SCI_SCR.BIT.RE = 0;
		// internal baud rate generator with SCK pin used as general IO
		//SCI_SCR.BIT.CKE = 0;
		S_CR=0;

		// async mode
		//		SCI_SMR.BIT.CA = 0;
		// 8-N-1
		//		SCI_SMR.BIT.CHR = 0;
		//		SCI_SMR.BIT.PE = 0;
		//		SCI_SMR.BIT.STOP = 0;
		// even parity (not required for 8-N-1)
		//		SCI_SMR.BIT.OE = 0;
		// multiprocessor mode disabled
		//		SCI_SMR.BIT.MP = 0;
		// n=0 (uses system clock)
		//		SCI_SMR.BIT.CKS = 0;
	    // individual bit definitions shown above replaced by single statement below to reduce code size
		//SCI_SMR.BYTE = 0;
		//S_MR=0;
        S_MR = 	SMR_ASYNC | SMR_8BIT | SMR_P_ODD | SMR_1STOP;

		// baud rate
		//SCI_BRR = BAUD_57600;
        S_BRR = B2400;

		// wait at least 1 bit time
		for (bit_delay=0; bit_delay<5000; bit_delay++);

		// disable transmit interrupts
		//		SCI_SCR.BIT.TIE = 0;
		// disable receive and receive error interrupts
		//		SCI_SCR.BIT.RIE = 0;
		// disable transmit end interrupts
		//		SCI_SCR.BIT.TEIE = 0;
		// disable multiprocessor interrupts
		//		SCI_SCR.BIT.MPIE = 0;
	    // individual bit definitions shown above replaced by single statement below to reduce code size
		//SCI_SCR.BYTE = 0;
		S_CR=0;

		// enable Tx and Rx
		//SCI_SCR.BIT.TE = 1;
		//SCI_SCR.BIT.RE = 1;
		S_CR = SCR_TRANSMIT | SCR_RECEIVE;
	}

	@NativeCFunction
	private static native void systime_init();

    private static void delay(int count) {
  	  for (int i = 0; i  < count; i++) { }
	}

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

		// Wait for the JUNIT framework to be ready to read
		delay (30000);

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
