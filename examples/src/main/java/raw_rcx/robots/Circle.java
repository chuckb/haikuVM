package raw_rcx.robots;

import static haiku.rcx.lib.ROM.*;

/**
 * <pre>
 * Motor0 'A': on the left.
 * Motor1 'B':
 * Motor2 'C': on the right
 *
 * Drive a circle for 60 secs.
 *
 * </pre>
 * @author Genom
 *
 */
public class Circle {

	public static void main(String[] args) throws InterruptedException {
		control_motor(MOTOR_0, MOTOR_FWD, 7);
		control_motor(MOTOR_2, MOTOR_FWD, 1);
		for (int i = 0; i < 60; i++) {

			// display counter i
			showNumber(i);

			Thread.sleep(1000);
		}
	}

}
