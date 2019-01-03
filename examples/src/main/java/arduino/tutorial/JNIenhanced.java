package arduino.tutorial;

/**
 * 
 *@author genom2
 *
 *@see http://haiku-vm.sourceforge.net/#[[Tutorial%20JNI]]
 */
public class JNIenhanced {

    private static native void add(int a, float b, double c);
    private static native double result();
    
    public static void main(String[] args) {
        int a=3;
        float b=4;
        double c=5;
        
        add(a, b, c);
        System.out.println(a+" + "+b+" + "+c+" = "+result());
    }
}
