package arduino.tutorial.beyondjni;

import haiku.vm.NativeCBody;
import haiku.vm.NativeCppMethod;

/**
 * 
 *@author genom2
 *
 *@see http://haiku-vm.sourceforge.net/#[[Tutorial%20JNI]]
 */
@NativeCBody(cImpl = 
	"class AccessCppMethods {\n" +
	"public:\n" +
	"	static long s;\n" +
	"	static long getS();\n" +
	"	static void setS(long s);\n" +
	"\n" +
	"	int a;\n" +
	"	double b;\n" +
	"\n" +
	"	AccessCppMethods(int a, double b);\n" +
	"\n" +
	"	int getA();\n" +
	"	void setA(int);\n" +
	"	double getB();\n" +
	"	void setB(double);\n" +
	"};\n" +
	"long AccessCppMethods::s;\n" +
	"\n" +
	"AccessCppMethods::AccessCppMethods(int a, double b) {\n" +
	"	this->a=a;\n" +
	"	this->b=b;\n" +
	"}\n" +
	"long AccessCppMethods::getS() {\n" +
	"	return AccessCppMethods::s;\n" +
	"}\n" +
	"void AccessCppMethods::setS(long s) {\n" +
	"	AccessCppMethods::s=s;\n" +
	"}\n" +
	"\n" +
	"int AccessCppMethods::getA() {\n" +
	"	return a;\n" +
	"}\n" +
	"void AccessCppMethods::setA(int a) {\n" +
	"	this->a=a;\n" +
	"}\n" +
	"\n" +
	"double AccessCppMethods::getB() {\n" +
	"	return b;\n" +
	"}\n" +
	"void AccessCppMethods::setB(double b) {\n" +
	"	this->b=b;\n" +
	"}"
	)
public class AccessCppMethods {
    private AccessCppMethods realSubject;

    public AccessCppMethods(int a, double b) {
    	realSubject=AccessCppMethods.AccessCppMethods(a, b);
    }

	@NativeCppMethod
    private native static AccessCppMethods AccessCppMethods(int a, double b);

	@NativeCppMethod
	public static native void setS(long s);
		
	@NativeCppMethod
	public static native long getS();

	@NativeCppMethod
	public native void setA(int a);
		
	@NativeCppMethod
	public native int getA();
		
	@NativeCppMethod
	public native void setB(double b);
		
	@NativeCppMethod
	public native double getB();
    
	static private AccessCppMethods obj1=new AccessCppMethods(101, 102);
	static private AccessCppMethods obj2=new AccessCppMethods(201, 202);
	
    public static void main(String[] args) {
    	info();
    	AccessCppMethods.setS(110); obj1.setA(111); obj1.setB(112);
    	info();
    	AccessCppMethods.setS(210); obj2.setA(211); obj2.setB(212);
    	info();
    }

	private static void info() {
        System.out.println("class: "+AccessCppMethods.getS());
        System.out.println("obj1:  "+obj1.getA()+" + "+obj1.getB());
        System.out.println("obj2:  "+obj2.getA()+" + "+obj2.getB());
	}
}
