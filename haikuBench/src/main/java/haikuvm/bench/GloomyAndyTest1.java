package haikuvm.bench;

/**
 * ARDUINO
 * 
 * GCC -Os: 
 * Program:    2770 bytes (8.5% Full)
 * Data:         36 bytes (1.8% Full)
 * result 4 in 8469 ms
 * 
 * GCC -O3: 
 * Program:    2820 bytes (8.6% Full)
 * Data:         36 bytes (1.8% Full)
 * result 4 in 8456 ms
 * 
 * 
 * haikuVM 32_64:
 * Program:   16708 bytes (51.0% Full)
 * Data:       1838 bytes (89.7% Full)
 * result 4 in 31996 ms
 * 
 * http://lejos.sourceforge.net/forum/viewtopic.php?f=7&t=2989
 * 
 * The above program takes 
 * 4512ms with leJOS 
 * 13221ms with RobotC and 
 * with gcc C it takes 288ms.
 *  
 * So in this test leJOS is 3 times faster than RobotC, and gcc C is 16 
 * times faster than leJOS...
 * 
 * The NXT is the brain of a MINDSTORMS® robot. 
 * It’s an intelligent, computer-controlled LEGO® brick that 
 * lets a MINDSTORMS robot come alive and perform different operations.

Technical specifications

    * 32-bit ARM7 microcontroller
    *   256 Kbytes FLASH, 64 Kbytes RAM
    *   (The main clock is configured to run at close to 48 MHz, which is a value that allows USB communication)
    * 8-bit AVR microcontroller
    *   4 Kbytes FLASH, 512 Byte RAM
    * Bluetooth wireless communication (Bluetooth Class II V2.0 compliant)
    * USB full speed port (12 Mbit/s)
    * 4 input ports, 6-wire cable digital platform (One port includes a IEC 61158 Type 4/EN 50 170 compliant expansion port for future use)
    * 3 output ports, 6-wire cable digital platform
    * 100 x 64 pixel LCD graphical display
    * Loudspeaker - 8 kHz sound quality. Sound channel with 8-bit resolution and 2-16 KHz sample rate.
    * Power source: 6 AA batteries
 * 
 * @author genom2
 * gcc C is 3.7  times faster than Haiku
 * 
 */
public class GloomyAndyTest1 {
	static int test(int n1, int n2, int n3, int n4) {
		if (n1 < 50000)
			return n1 * n2 + n3 * n4;
		else
			return n1 / n2 + n3 / n4;
	}

	public static void main(String[] args) {
		long t0 = System.currentTimeMillis();
		int res = 0;
		for (int i = 0; i <= 100000; i++)
			res = test(i, i / 2, res, res / 2);
		System.out.println("result " + res + " in "
				+ (System.currentTimeMillis() - t0) + " ms");
	}

}
