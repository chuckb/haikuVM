package arduino.tutorial;

import haiku.vm.NativeCFunction;
import haiku.vm.NativeCppFunction;
import haiku.vm.NativeCVariable;

public class NativeCVariableEnhanced {
    private static class JavaClass {
        int x=2;
        double y=3.0;
    }

    private static class ResultStruct {
        int x;
        double y;
    }

    private static class ResultClass {
        int x;
        double y;

        @NativeCppFunction
        public native int getX();
    }

    @NativeCVariable
    private static volatile int result4b;
    
    @NativeCVariable
    private static volatile int result4bCpp;

    @NativeCVariable
    // Don't initialize any NativeCVariable Object or Array
    // in HaikuVM because its memory will be freed 
    // when GC is running next time.
    // So this is a bad idea, because resultArray is 
    // (static but) not in the root set of the HaikuVM GC:
    //private static volatile int resultArray[]= new int[3];
    
    // This will work if you do the initializing in C:
    private static volatile int resultArray[];
    
    @NativeCVariable
    private static volatile ResultStruct resultStruct;

    @NativeCVariable
    private static volatile ResultClass resultClass;

    @NativeCVariable
    private static volatile double result4a=100.3;

    @NativeCFunction
    private static native void NativeCVariableEnhanced_inc(double a);

    public static void main(String[] args) {
        JavaClass jc=new JavaClass();
        System.out.println(result4a);
        NativeCVariableEnhanced_inc(3.3);
        System.out.println(result4a);
        System.out.println(result4b);
        
        System.out.println();
        // Don't use it with resultArray.length because C arrays have no field length.
        // Like in C, you have to know the length of the array.
        for (int i = 0; i < 3; i++) {
            System.out.println("C resultArray["+i+"]="+resultArray[i]);
        }

        System.out.println();
        System.out.println("C resultStruct.a="+resultStruct.x);
        System.out.println("C resultStruct.b="+resultStruct.y);

        System.out.println("done");
    }
}
