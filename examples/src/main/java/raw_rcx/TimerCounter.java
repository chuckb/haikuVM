package raw_rcx;

import static haiku.rcx.lib.ROM.*;

/**
 * Counts down to 0 and shows it on the LCD display
 *
 * @author Genom
 *
 */
public class TimerCounter {
	public static void main(String[] aArg) throws Exception {
		for (int i = 0; i < 100; i++) {
			showNumber(i);
			// Sound.beep();
			Thread.sleep(1000);
		}
	}
}
