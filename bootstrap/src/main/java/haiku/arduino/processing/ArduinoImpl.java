package haiku.arduino.processing;

import haiku.vm.NativeCHeader;
import haiku.vm.NativeCppFunction;

@NativeCHeader(cImpl="#include <Arduino.h>")
public class ArduinoImpl {
	/**
	 * should be created only by {@link Arduino}
	 */
	ArduinoImpl() {
	}

	@NativeCppFunction
	public native void pinMode(byte pin, byte mode);

	@NativeCppFunction
	public native void digitalWrite(byte pin, byte value);

	@NativeCppFunction
	public native byte digitalRead(byte pin);

	public int millis() {
		return (int) System.currentTimeMillis();
	}

	public void delay(int ms) {
		Thread.nap(ms);
	}
}
