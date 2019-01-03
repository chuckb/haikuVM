package raw_rcx;

import static haiku.rcx.lib.ROM.*;

/**
 * Shows the state of all RCX keys until the power key is pressed
 *
 * @author Genom
 *
 */
public class ButtonTest {
	public static void main(String[] args) {
		short_t status = new short_t();

		get_power_status(POWER_KEY, status);
		while (status.s != 0) {

			// Check on buttons
			read_buttons(0x3000, status);

			// show buttons on LCD display
			showNumber(status.s);

			get_power_status(POWER_KEY, status);
		}
	}

}
