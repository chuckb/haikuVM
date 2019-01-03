package haiku.vm;
public class MemoryAccess {
    public static native void   setMemory8(int adr, int int8);
    public static native void   setMemory16(int adr, int int16);
    public static native int    getMemory8(int adr);
    public static native int    getMemory16(int adr);
    
    public static native int    getMemory32(int adr);
    public static native long   getMemory64(int adr);
    public static native short  getMemoryShort(int adr);
    public static native int    getMemoryInt(int adr);
    public static native long   getMemoryLong(int adr);
}
