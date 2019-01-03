package haiku.arduino.processing;

import haiku.vm.NativeCHeader;
import haiku.vm.NativeCppFunction;

@NativeCHeader(cImpl="#include <Arduino.h>")
public class SerialImpl {
	/**
	 * should be created only by {@link Serial}
	 */
	SerialImpl() {
	}

	@NativeCppFunction(cImpl = "return Serial;")
	public native boolean isOpen();

	@NativeCppFunction(cImpl = "return Serial.available();")
	public native short available();

	@NativeCppFunction(cImpl = "Serial.begin(arg1);")
	public native void begin(int speed);

	@NativeCppFunction(cImpl = "Serial.end();")
	public native void end();

	@NativeCppFunction(cImpl = "return Serial.read();")
	public native byte read();

	@NativeCppFunction(cImpl = "Serial.write(arg1);")
	public native void write(byte value);
}
