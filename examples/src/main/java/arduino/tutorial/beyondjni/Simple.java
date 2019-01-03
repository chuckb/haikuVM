package arduino.tutorial.beyondjni;

import haiku.vm.NativeCBody;
import haiku.vm.NativeCppFunction;

/**
 * cd D:\Arduino-1.6.4\libraries
 * D:\Entwicklung\haikuVM\bin\haiku.bat -v --Config arduinoIDEUpload duemilanove --Mode 16/32 --Config:MicroKernel haiku.avr.lib.arduino.HaikuMicroKernelEx D:\Entwicklung\haikuVM\examples\src\main\java\arduino\tutorial\beyondjni\Simple.java
 * 
 *@author genom2
 *
 *@see http://haiku-vm.sourceforge.net/#[[Tutorial%20JNI]]
 */
@NativeCBody(cImpl = "static double result;")
public class Simple {

	@NativeCppFunction(cImpl = "result = arg1 + TF2FLOAT(arg2) + TD2DOUBLE(arg3);")
    private static native void add(int a, float b, double c);
		
	@NativeCppFunction(cImpl = "return DOUBLE2TD(result);")
    private static native double result();
    
    public static void main(String[] args) {
        int a=5;
        float b=6;
        double c=7;
        
        add(a, b, c);
        System.out.println(a+" + "+b+" + "+c+" = "+result());
    }
}
