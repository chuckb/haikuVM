package haiku.arduino.processing;

import haiku.vm.NativeCHeader;
import haiku.vm.NativeCppFunction;

@NativeCHeader(cImpl = "#include <Wire.h>")
public class WireImpl {
	/**
	 * should be created only by {@link Wire}
	 */
	WireImpl() {
	}

	@NativeCppFunction(cImpl = "Wire.begin();")
	public native void begin();

	@NativeCppFunction(cImpl = "Wire.beginTransmission(arg1);")
	public native void beginTransmission(byte address);

	@NativeCppFunction(cImpl = "return Wire.endTransmission();")
	public native byte endTransmission();

	@NativeCppFunction(cImpl = "return Wire.write(arg1);")
	public native byte write(byte value);

	@NativeCppFunction(cImpl = "return Wire.requestFrom(arg1, arg2);")
	public native byte requestFrom(byte address, byte quantity);

	@NativeCppFunction(cImpl = "return Wire.read();")
	public native short read();
}
