package raw_rcx.robots;

import static haiku.rcx.lib.ROM.*;

/**
 * <pre>
 * Motor0 'A': on the left.
 * Motor1 'B':
 * Motor2 'C': on the right
 *
 * Sensor0 '1':
 * Sensor1 '2': connected with Light Sensor
 * Sensor2 '3':
 *
 * Display: 8888 | 9
 *   Where 8888 is placeholder of lightsensor
 *   and 9 is placeholder of speed
 *
 *begin:
 * Put the Robot on a black circle track, in anti-clockwise direction and let him calibrate on black
 * Press Run to start: The Robot should follow the black line
 * Press Run again to halt (and goto begin:)
 *
 * Press Prgm to increase speed
 * Press View to decrease speed
 *
 * Press On-Off to terminate program
 * </pre>
 * @author Genom
 *
 */
public class FollowLine {
	private static final int FORWARD_STATE_LEFT = 1;
	private static final int TURN_LEFT_STATE = 2;
	private static final int FORWARD_STATE_RIGHT = 3;
	private static final int TURN_RIGHT_STATE = 4;

	private static final int delta = 500;

	private static short_t  status = new short_t();
	private static sensor_t sensor = new sensor_t();

	private static int speed=2;
	private static int black; // I measured about 860 for black

	/**
	 * <i>Low-level API</i> for controlling a motor. This method is not meant to
	 * be called directly.
	 *
	 * @param aMotor
	 *            The motor id: 'A', 'B' or 'C'.
	 * @param aMode
	 *            1=forward, 2=backward, 3=stop, 4=float
	 * @param aPower
	 *            A value in the range [0-7].
	 */
	public static void controlMotor(char aMotor, int aMode, int aPower) {
		__rcall3((short) 0x1a4e, (short) (0x2000 + aMotor - 'A'),
				(short) aMode, (short) aPower);
	}

	public static int getLineSensor() {
		read_sensor(SENSOR_1, sensor);
		int value=sensor.value;
		set_lcd_number (LCD_PROGRAM, speed, 0);
		showNumber(value);
		return value;
	}

	public static void main(String[] args) throws InterruptedException {
		init_sensors();
		set_sensor_active(SENSOR_1);

		while (true) {
			clear_lcd_segment(LCD_WALKING);
			set_lcd_segment (LCD_STANDING);

			while (!isRunPressed()) {
				black=getLineSensor();
			};

			clear_lcd_segment(LCD_STANDING);
			set_lcd_segment (LCD_WALKING);
			debounce();

			long giveup=0;
			for(int state=FORWARD_STATE_LEFT; !isRunPressed();) {
				long t1=System.currentTimeMillis();
				switch(state) {
				case FORWARD_STATE_LEFT:
					controlMotor('A', MOTOR_FWD, speed);
					controlMotor('C', MOTOR_FLOAT, 0);
					if (!isOnBlackLine()) {
						state=TURN_LEFT_STATE;
						giveup=t1+delta;
					}
					break;
				case TURN_LEFT_STATE:
					controlMotor('A', MOTOR_STOP, 0);
					controlMotor('C', MOTOR_FWD, speed);
					if (isOnBlackLine()) {
						state=FORWARD_STATE_LEFT;
					} else if (giveup!=0 && t1>giveup) {
						giveup=0;
						state=TURN_RIGHT_STATE;
					}
					break;
				case FORWARD_STATE_RIGHT:
					controlMotor('A', MOTOR_FLOAT, 0);
					controlMotor('C', MOTOR_FWD, speed);
					if (!isOnBlackLine()) {
						state=TURN_RIGHT_STATE;
						giveup=t1+delta;
					}
					break;
				case TURN_RIGHT_STATE:
					controlMotor('A', MOTOR_FWD, speed);
					controlMotor('C', MOTOR_STOP, 0);
					if (isOnBlackLine()) {
						state=FORWARD_STATE_RIGHT;
					} else if (giveup!=0 && t1>giveup) {
						giveup=0;
						state=TURN_LEFT_STATE;
					}
					break;
				}
			}
			controlMotor('A', MOTOR_FLOAT, 0);
			controlMotor('C', MOTOR_FLOAT, 0);
			debounce();
		}
	}

	private static boolean isRunPressed() {
		return (getKeys()&BUTTON_RUN)!=0;
	}

	private static boolean isOnBlackLine() {
		return getLineSensor()+50 > black;
	}

	private static int getKeys() {
		get_power_status(POWER_KEY, status);
		if (status.s == 0) throw new RuntimeException(); //Power Key Pressed -> terminate!

		read_buttons(0x3000, status);
		if ((status.s&BUTTON_VIEW)!=0) {
			speed--;
			debounce();
		}
		if ((status.s&BUTTON_PRGM)!=0) {
			speed++;
			debounce();
		}
		return status.s;
	}

	private static void debounce() {
		try {
			play_system_sound(SOUND_QUEUED, 0);
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
	}

}
