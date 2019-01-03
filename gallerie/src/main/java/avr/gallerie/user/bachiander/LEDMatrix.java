package avr.gallerie.user.bachiander;

import haiku.avr.lib.arduino.WProgram;
import haiku.vm.NativeCFunction;

import static haiku.avr.lib.arduino.WProgram.*;

public class LEDMatrix {

	static int A1 = 14;
	static int A2 = 15;
	static int A3 = 16;

	static boolean reverseJ = false;
	static boolean reverseI = false;

	static LedControl lc = new LedControl(A3, A1, A2, 1);

	public static void main(String[] args) {

	}

	static void setup() {

		lc.shutdown(0, false);
		lc.setIntensity(0, 8);
		lc.clearDisplay(0);
	}

	static void loop() {

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				lc.clearDisplay(0);
				lc.setLed(0, reverseI ? 7 - i : i, reverseJ ? 7 - j : j, true);
				delay(100);
			}
			reverseJ = !reverseJ;
		}
		reverseI = !reverseI;
	}
}