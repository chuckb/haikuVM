package haiku.rcx.retro;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import haiku.vm.HaikuMagic;
import haiku.vm.NativeCFunction;
import haiku.vm.NativeCVariable16;
import haiku.vm.NativeCVariable8;
import static haiku.rcx.lib.ROM.*;

/**
 * manually transpiled from rcx_impl/main.c into Java
 *
 * Prgm:
 * Select a program
 *
 * Run:
 * If Prgm is 0 reboot and ready for new firmware
 * else start your program
 *
 * OnOff: pressed longer than 2 seconds
 * Goes into hardware hibernate state, waiting for OnOff pressed again.
 * Your program and any other thread will be terminated first.
 *
+-------------------------+
|View    1   2   3    Prgm|
|[__]  +-----------+  [__]|
|      | 00.PA   [ |	  |
|OnOff +-----------+  Run |
|[__]    A   B   C    [__]|
+-------------------------+
 *
 * @author Genom
 *
 */
public class HaikuMicroKernel extends haiku.vm.MicroKernel {
	static short_t status=new short_t();
	static byte[] timerdata1 = new byte[6];
	static async_t timerdata0 = new async_t();

//	public static final int BAUD_115200=			5;
//	public static final int BAUD_57600=			11;
//	public static final int BAUD_56000=			11;
//	public static final int BAUD_38400=			17;
//	public static final int BAUD_9600=			71;

	//
	// values for the bit rate register BRR
	// assuming CMR_CLOCK selected on 16 MHz processor
	// error <= 0.16%
	//

	public static final int B2400=		207;
	public static final int B4800=		103;
	public static final int B9600=		51;
	public static final int B19200=		25;
	public static final int B38400=		12;

	// Serial baud rate register
	@NativeCVariable8
	private static int S_BRR;

	// Serial receive data register
    @NativeCVariable8
	private static int S_RDR;

	// Serial transmit data register
    @NativeCVariable8
	private static int S_TDR;

	// Serial mode register
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

	// Serial control register
    @NativeCVariable8
	private static int S_CR;

	public static final int SCR_TX_IRQ=	0x80;	   // Transmit irq enable
	public static final int SCR_RX_IRQ=	0x40;	   // Receive / recv err irq enable
	public static final int SCR_TRANSMIT=	0x20;	   // Enable transmission
	public static final int SCR_RECEIVE=	0x10;	   // Enable receiving
	public static final int SCR_TE_IRQ=	0x04;	   // Transmit end irq enable

	// Serial status register
    @NativeCVariable8
	private static byte S_SR;
	private static int gProgramNumber;

	public static final int SSR_TRANS_EMPTY=	0x80;	   // Transmit buffer empty
	public static final int SSR_RECV_FULL=	0x40;	   // Receive buffer full
	public static final int SSR_OVERRUN_ERR=	0x20;	   // Overrun error
	public static final int SSR_FRAMING_ERR=	0x10;	   // Framing error
	public static final int SSR_PARITY_ERR=	0x08;	   // Parity error
	public static final int SSR_ERRORS=      0x38;       // All errors
	public static final int SSR_TRANS_END=	0x04;	   // Transmission end because buffer empty

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

		S_CR = SCR_TRANSMIT | SCR_RECEIVE; // enable transmit
	}

	@NativeCFunction
	private static native void systime_init();

	static void wait_for_power_release(int count) {
		int debouncer = 0;

		do {
			delay(20);
			get_power_status(POWER_KEY, status);
			if (status.s == 0)
				debouncer = 0;
			else
				debouncer++;
		} while (debouncer <= count);
	}

	static void wait_for_release(short code) {
		short debouncer = 0;

		do {
			read_buttons(0x3000, status);
			if ((status.s&code) != 0)
				debouncer = 0;
			else
				debouncer++;
		} while (debouncer < 100);
	}

    private static void delay(int count) {
    	  for (int i = 0; i  < count; i+=20) { }
	}


	public static void main(String[] args) {
	    clinitHaikuMagic();

		/**
		 * The following call of panic()
		 * and consequently the method body panic()
		 * will be extingted from code if property PanicSupport is set to 0
		 * .. -DPanicSupport=0 ..
		 */
		try {
			panic(0, 0);
		} catch (Throwable e1) {
		}

		for (;;) {
			gProgramNumber=1;
			// Power up initializations
			// The following call always needs to be the first one.
			init_timer(timerdata0, timerdata1);

			// Set sleep mode to 'standby' plus other stuff needed for init_serial()
			init_power();

			// Initialize timer handler.
			systime_init();

			// Not sure why this is done.
			init_sensors();

			// If power key pressed, wait until it's released.
			wait_for_power_release(600);

			// Initialize serial I/O
			//init_serial (&timerdata1[4], &timerdata0, 1, 1);
			init_serial (null, null, 1, 1);

			init();


			play_system_sound (SOUND_QUEUED, 1);
			clear_display();

			// Start "user interface"
			while(!isPowerKeyPressed()) {
				get_power_status (POWER_BATTERY, status);
				status.s = (short)((status.s * 100L) / 355L);
				set_lcd_number (LCD_SIGNED, status.s, LCD_DECIMAL_1);

				// Show program number
				set_lcd_number (LCD_PROGRAM, gProgramNumber, 0);

				// Show standing man.
				set_lcd_segment (LCD_STANDING);
				refresh_display();

				// Check on a few more buttons
				read_buttons(0x3000, status);

				// Run the program
				if ((status.s & BUTTON_RUN) != 0) {
				    play_system_sound (SOUND_QUEUED, 0);
					wait_for_release(BUTTON_RUN);
					if (gProgramNumber==0)	{
						haiku.vm.MemoryAccess.setMemory8(0xffcc, 1);
						exit(1);
					} else {

						// Show walking man.
						clear_lcd_segment (LCD_STANDING);
						set_lcd_segment (LCD_WALKING);
						refresh_display();

						new Thread() {
							public void run() {
								try {
									HaikuMagic.main(new String[] {""+gProgramNumber}); // Will call your main method (by some HaikuVM bytecode manipulation magic)
								} catch (Throwable e) {
								}
							}
						}.start();

						// wait until program is ready or power key is pressed
						try {
							while(true) {
								Thread.sleep(2000);
								if (isPowerKeyPressed()) {
									break;
								} else if (Thread.nextThread()==Thread.currentThread()) {
									break;
								}
							}
						} catch (InterruptedException e) {
						}
						break;
					}
				}
				// Select a different program
				else if ((status.s & BUTTON_PRGM) != 0) {
					play_system_sound(SOUND_QUEUED, 0);
					wait_for_release(BUTTON_PRGM);
					gProgramNumber=(gProgramNumber+1) % 5;
				}
			}

		    play_system_sound (SOUND_QUEUED, 0);

			// terminate all other Threads
			for(Thread nthread=null; (nthread=Thread.nextThread())!=Thread.currentThread(); ) {
				nthread.stop();
			}

			// Program terminated.
			clear_display();
			refresh_display();

			// Power off has been pressed. Pause
			// for some time to allow motors to spin down.
			delay (5000);

			shutdown_sensors();
			shutdown_buttons();
			shutdown_timer();

			shutdown_power();     // Presumably doesn't return again until power is pressed
		}
	}

	private static boolean isPowerKeyPressed() {
		get_power_status(POWER_KEY, status);
		return status.s == 0;
	}
}
