package arduino.tutorial;

import haiku.vm.NativeCFunction;


/**
 * 
 *@author genom2
 *
 *@see http://haiku-vm.sourceforge.net/#[[Tutorial%20JNI]]
 */
public class JNINativeLIBC {

    private static native double sin(double a);
    
    @NativeCFunction
    private static native double cos(double a);
    
    public static void main(String[] args) {
        long t0, t1;
        double x=2.1, step=0.03;
        System.out.println("Calculate sin("+x+")");
        System.out.println("This is the JAVA result: "+Math.sin(x));
        System.out.println("This is the libc result: "+sin(x));

        t0=System.currentTimeMillis();
        for (x = 0; x < Math.PI; x+=step) {
            Math.sin(x);
        }
        t1=System.currentTimeMillis();
        System.out.println("JAVA performance: "+(t1-t0));

        t0=System.currentTimeMillis();
        for (x = 0; x < Math.PI; x+=step) {
            sin(x);
        }
        t1=System.currentTimeMillis();
        System.out.println("libc performance: "+(t1-t0));
        
        System.out.println();
        System.out.println("Calculate cos("+x+")");
        System.out.println("This is the JAVA result: "+Math.cos(x));
        System.out.println("This is the libc result: "+cos(x));
        
    }
}
