package processing.libraries.LiquidCrystal;

import haiku.vm.NativeCppMethod;
import processing.hardware.arduino.cores.arduino.Print;

/**
 * Before using, please study this discussion thread:
 * https://sourceforge.net/p/haiku-vm/discussion/general/thread/25b657d8/?limit=25#bc1b
 * From this it turns out, that a lot of C code is missing to make this run.
 * 
 * @author genom2
 *
 */
public class LiquidCrystal extends Print {

    private Object realSubject;

    public LiquidCrystal(int rs, int enable, int d0, int d1, int d2, int d3) {
    	realSubject=cppConstructor(rs, enable, d0, d1, d2, d3);
    }

    
    @NativeCppMethod
    private native static Object cppConstructor(int rs, int enable, int d0, int d1, int d2, int d3);

    @Override
    @NativeCppMethod
    public native byte write(byte c);

    @NativeCppMethod
    public native void begin(int i, int j);

    @NativeCppMethod
    public native void setCursor(int i, int j);

}
