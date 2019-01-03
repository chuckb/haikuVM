package arduino.tutorial;

/**
 * 
 *@author genom2
 *
 *@see http://haiku-vm.sourceforge.net/#[[Tutorial%20JNI]]
 */
public class JNIsimple {

    private static native int add(int a, int b);
    
    public static void main(String[] args) {
        int a=3;
        int b=4;
        System.out.println(a+" + "+b+" = "+add(a,b));
    }
}
