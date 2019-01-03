package asuro.robot;
import static haiku.avr.lib.asuro.lib2_8_0_rc1.AsuroLib2_8_0_rc1.*;

public class Threading {
	private final static int slow = 60, fast = 80;
	private final static int SWITCH_NOISE = 5;
	private static int is_collision;


	private static class blink_Task extends Thread {
		public void run() {
			int led = 0;
			while (true) {
				led = 1 - led;
				StatusLED(led);
				Msleep(1000);
			}
		}
	}

	private static class avoid_Task extends Thread {
		public void run() {
			int count, old=0, sensor;
			while (true) {
				is_collision = 0;
				for (count = 0;; count++) {
					sensor = PollSwitch();
					if (old != sensor)
						count = 0;
					old = sensor;
					if (count >= SWITCH_NOISE && sensor != 0)
						break;
				}
				is_collision = 1;
				// What to do when robot hit a wall?
				SetMotorPower(-slow, -slow);
				Msleep(2000);
				if (sensor < 16)
					SetMotorPower(-slow, 0);
				else
					SetMotorPower(0, -slow);
				Msleep(1000);
			}
		}
	}

	private static class cruise_Task extends Thread {
		public void run() {
			while (true) {
				if (is_collision == 0)
					SetMotorPower(fast, fast);
			}
		}
	}

	private static long fib(int n) {
		if (n < 2)
			return 1;
		return fib(n - 1) + fib(n - 2);
	};

	public static void main(String[] args) {
		Init();
		new blink_Task().start();
		new avoid_Task().start();
		new cruise_Task().start();

		for (int i = 0; ; i++) {
			PrintInt(i);
			SerPrint(" ");
			PrintLong(fib(i));
			SerPrint("\n");
		}
	}
}
