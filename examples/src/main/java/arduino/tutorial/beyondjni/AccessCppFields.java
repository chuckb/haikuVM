package arduino.tutorial.beyondjni;

import haiku.vm.NativeCBody;
import haiku.vm.NativeCppFunction;

/**
 * C:\HaikuVM\bin\haiku --Config arduino C:\HaikuVM\examples\src\main\java\arduino\tutorial\beyondjni\AccessCppFields.java
 * Program:    8884 bytes (27.1% Full)
 * (.text + .data + .bootloader)
 * Data:       1749 bytes (85.4% Full)
 * (.data + .bss + .noinit)
 * 
 * 
 * You may call it for the Arduino IDE (1.6.4), too:
 * C:\HaikuVM\bin\haiku --Config arduinoIDEUpload --Config:MicroKernel haiku.avr.lib.arduino.HaikuMicroKernelEx C:\HaikuVM\examples\src\main\java\arduino\tutorial\beyondjni\AccessCppFields.java
 * Program: 10.906 Bytes (35%)
 * Data: 1.934 Bytes (94%)
 * 
 * I wonder about the difference in program and data?
 * So I decided to set
 * arduinoIDE.MemorySize = (RAMEND-0x100) - (700+HAIKU_Mode*50)
 * to get:
 * Program: 10.906 Bytes (35%)
 * Data: 1.734 Bytes
 * 
 * Again I wonder, because I expected to set it like so to get the wanted result:
 * arduinoIDE.MemorySize = (RAMEND-0x100) - (500+HAIKU_Mode*50)
 * 
 * 
 *@author genom2
 *
 *@see http://haiku-vm.sourceforge.net/#[[Tutorial%20JNI]]
 */
@NativeCBody(cImpl = 
	"class AccessCppFields {\n" +
	"public:\n" +
	"	static long s;\n" +
	"	static long getS();\n" +
	"	static void setS(long s);\n" +
	"\n" +
	"	int a;\n" +
	"	double b;\n" +
	"\n" +
	"	AccessCppFields(int a, double b);\n" +
	"\n" +
	"	int getA();\n" +
	"	void setA(int);\n" +
	"	double getB();\n" +
	"	void setB(double);\n" +
	"};\n" +
	"long AccessCppFields::s;\n" +
	"\n" +
	"AccessCppFields::AccessCppFields(int a, double b) {\n" +
	"	this->a=a;\n" +
	"	this->b=b;\n" +
	"}"
	)
public class AccessCppFields {
    private AccessCppFields realSubject;

    public AccessCppFields(int a, double b) {
    	realSubject=AccessCppFields(a, b);
    }

    @NativeCppFunction(cImpl="return (jobject) new AccessCppFields(arg1, TD2DOUBLE(arg2));")
    private native static AccessCppFields AccessCppFields(int a, double b);

	@NativeCppFunction(cImpl="AccessCppFields::s=arg1;")
	public static native void setS(long s);
		
	@NativeCppFunction(cImpl="return AccessCppFields::s;")
	public static native long getS();

	@NativeCppFunction(cImpl="getRealCppSubject(AccessCppFields, obj)->a=arg1;")
	public native void setA(int a);
		
	@NativeCppFunction(cImpl="return getRealCppSubject(AccessCppFields, obj)->a;")
	public native int getA();
		
	@NativeCppFunction(cImpl="getRealCppSubject(AccessCppFields, obj)->b=arg1;")
	public native void setB(double b);
		
	@NativeCppFunction(cImpl="return getRealCppSubject(AccessCppFields, obj)->b;")
	public native double getB();
    
	static private AccessCppFields obj1=new AccessCppFields(101, 102);
	static private AccessCppFields obj2=new AccessCppFields(201, 202);
	
    public static void main(String[] args) {
        System.out.println("begin");
    	info();
    	AccessCppFields.setS(110); obj1.setA(111); obj1.setB(112);
    	info();
    	AccessCppFields.setS(210); obj2.setA(211); obj2.setB(212);
    	info();
        System.out.println("end");
    }

	private static void info() {
        System.out.println("class: "+AccessCppFields.getS());
        System.out.println("obj1:  "+obj1.getA()+" + "+obj1.getB());
        System.out.println("obj2:  "+obj2.getA()+" + "+obj2.getB());
	}
}
