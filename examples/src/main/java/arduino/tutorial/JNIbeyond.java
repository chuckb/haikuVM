package arduino.tutorial;

import haiku.vm.NativeCBody;
import haiku.vm.NativeCppFunction;

/**
 * 
 *@author genom2
 *
 *@see http://haiku-vm.sourceforge.net/#[[Tutorial%20JNI]]
 */
@NativeCBody(cImpl = "static double result;")
public class JNIbeyond {

	@NativeCppFunction(cImpl = "result = arg1 + TF2FLOAT(arg2) + TD2DOUBLE(arg3);")
    private static native void add(int a, float b, double c);
		
	@NativeCppFunction(cImpl = "return DOUBLE2TD(result);")
    private static native double result();
    
    public static void main(String[] args) {
        int a=4;
        float b=5;
        double c=6;
        
        add(a, b, c);
        System.out.println(a+" + "+b+" + "+c+" = "+result());
    }
}
