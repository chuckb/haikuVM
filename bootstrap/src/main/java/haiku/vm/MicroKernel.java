package haiku.vm;

public class MicroKernel {
    /**
     * This is the panic room. 
     * In case panic was triggered by low space conditions
     * HaikuVM will release the memory for this array to have 
     * space to handle the panic. 
     * Initialization is done with panic(0, *)
     */
    private static volatile Object[] panicRoom;

    /**
     * Bytecode placeholder method for all static initializers.
     * 
     * Will be filled by haikufier with the (ordered) list of
     * all static initializers from your JAVA program.
     */
    public static native void clinitHaikuMagic();

    /**
     * @throws the indended Throwable
     */
    public static void panic(int exceptionCode, int exceptionArg) throws Throwable {
        switch (exceptionCode) {
        case   0:   panicRoom=new Object[140]; return; // for initialization
        case   2:   throw new NullPointerException("NullPointerException"); 
        case   4:   throw new ArrayStoreException(); 
        case   8:   throw new IndexOutOfBoundsException("IndexOutOfBoundsException"); 
        case  16:   throw new NoSuchMethodError("NoSuchMethodError: msgCode="+exceptionArg); 
        case  32:   throw new StackOverflowError("StackOverflow: " + exceptionArg); 
//  case  64:   throw new OutOfMemoryError("Heap overflow: size needed="+haiku.vm.HaikuMagic.exceptionArg); 
        case  64:   throw new OutOfMemoryError("OutOfMemoryError"); 
        case 128:   throw new ClassCastException("ClassCastException classCode="+exceptionArg); 
        case 256:   throw new ArrayIndexOutOfBoundsException(exceptionArg);
        case 512:   throw new InternalError("InternalError OP code is "+(exceptionArg==0?"unimplemented":"unused")); 
        case 1024:  throw new ArithmeticException("ArithmeticException: Code="+exceptionArg); 
        }
        throw new InternalError("Unregistered Haiku Exception: exceptionCode="+exceptionCode+" arg="+exceptionArg);
    }
}
