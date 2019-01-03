package asuro.tutorial;
import static haiku.avr.lib.asuro.lib2_8_0_rc1.AsuroLib2_8_0_rc1.*;

/**
 * This simple program demonstrates Thread usage.
 * The status LED and the back LEDs of ASURO 
 * should blink with different frequencies.
 * 
 * mkdir myProject 
 * cd myProject
 * C:\haikuVM\bin\haikuc                                             C:\haikuVM\examples\src\main\java\asuro\tutorial\BlinkWithThread.java
 * C:\haikuVM\bin\haikulink -v --Config asuro -o BlinkWithThread.hex C:\haikuVM\examples\src\main\java\asuro\tutorial\BlinkWithThread
 * C:\haikuVM\bin\haikuupload                    BlinkWithThread.hex
 * 
 * @author genom2
 */
public class BlinkWithThread {
	private static class blink_Task extends Thread {
		public void run() {
			while (true) {
				BackLED(ON, ON);
				Msleep(1000);
				BackLED(OFF, OFF);
				Msleep(1000);
			}
		}
	}

	public static void main(String[] args) {
		Init(); // initialize the ASURO
		
		new blink_Task().start(); // starts the parallel blink task.

		while (true) {
			StatusLED(GREEN);
			Msleep(500);
			StatusLED(RED);
			Msleep(500);
		}
	}
}
