package arduino.tutorial;

import haiku.vm.NativeCVariable16;

public class NativeCVariableSimple {

    @NativeCVariable16
    private static volatile int result3=10;

    private static native void inc(int a);
    
    public static void main(String[] args) {
        System.out.println(result3);
        inc(30);
        System.out.println(result3);
    }
}
