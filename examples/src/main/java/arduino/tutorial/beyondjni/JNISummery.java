package arduino.tutorial.beyondjni;

import haiku.vm.NativeCBody;
import haiku.vm.NativeCFunction;
import haiku.vm.NativeCppFunction;
import haiku.vm.NativeCppMethod;

/**
 *
 *@author genom2
 *
 *@see http://haiku-vm.sourceforge.net/#[[Tutorial%20JNI]]
 */
@NativeCBody(cImpl = "// some impl is needed (even a comment only) to direct HaikuVM in the right direction")
public class JNISummery {
    ////////////////    just native
    public native double min(double x, double y);
    public static native double max(double x, double y);


    ////////////////    @NativeCFunction
    @NativeCFunction
    public native double sin(double x);
    @NativeCFunction
    public static native double cos(double x);


    ////////////////    @NativeCppFunction
    @NativeCppFunction
    public native double tan(double x);
    @NativeCppFunction
    public static native double sqrt(double x);


    ////////////////    @NativeCppMethod
    @NativeCppMethod
    public static native void setS(long s);
    @NativeCppMethod
    public static native long getS();
    @NativeCppMethod
    public native void setA(int a);
    @NativeCppMethod
    public native int getA();


    ////////////////    @NativeCppMethod and C++ Constructors
    @NativeCppMethod
    private native static JNISummery JNISummery(int a);
    @NativeCppMethod
    private native static JNISummery cppConstructor(int a);


    private JNISummery realSubject;
    static private JNISummery obj1=new JNISummery(101);

    public JNISummery(int a) {
        realSubject=JNISummery(a);
        realSubject=cppConstructor(a);
    }

    public static void main(String[] args) {
        setS(110); obj1.setA(111);

        System.out.println("class: "+getS());
        System.out.println("obj1:  "+obj1.getA());

        System.out.println("min : "+obj1.min(1, 3));
        System.out.println("max : "+max(1,5));
        System.out.println("sin : "+obj1.sin(1));
        System.out.println("cos : "+cos(1));
        System.out.println("tan : "+obj1.tan(1));
        System.out.println("sqrt: "+sqrt(1));
    }
}
