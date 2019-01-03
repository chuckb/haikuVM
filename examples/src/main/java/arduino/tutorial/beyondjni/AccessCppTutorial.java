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
	"class AccessCppTutorial {\n" +
	"public:\n" +
	"	static long s;\n" +
	"	static long getS();\n" +
	"	static void setS(long s);\n" +
	"\n" +
	"	int a;\n" +
	"\n" +
	"	AccessCppTutorial(int a);\n" +
	"\n" +
	"	int getA();\n" +
	"	void setA(int);\n" +
	"};\n" +
	"long AccessCppTutorial::s;\n" +
	"\n" +
	"AccessCppTutorial::AccessCppTutorial(int a) {\n" +
	"	this->a=a;\n" +
	"}\n" +
	"long AccessCppTutorial::getS() {\n" +
	"	return AccessCppTutorial::s;\n" +
	"}\n" +
	"void AccessCppTutorial::setS(long s) {\n" +
	"	AccessCppTutorial::s=s;\n" +
	"}\n" +
	"\n" +
	"int AccessCppTutorial::getA() {\n" +
	"	return a;\n" +
	"}\n" +
	"void AccessCppTutorial::setA(int a) {\n" +
	"	this->a=a;\n" +
	"}\n"
	)
public class AccessCppTutorial {
    private AccessCppTutorial realSubject;

    public AccessCppTutorial(int a) {
    	realSubject=AccessCppTutorial.AccessCppTutorial(a);
    }

	@NativeCppMethod
    private native static AccessCppTutorial AccessCppTutorial(int a);

	@NativeCppMethod
	public static native void setS(long s);
		
	@NativeCppMethod
	public static native long getS();

	@NativeCppMethod
	public native void setA(int a);
		
	@NativeCppMethod
	public native int getA();
    
	static private AccessCppTutorial obj1=new AccessCppTutorial(101);
	static private AccessCppTutorial obj2=new AccessCppTutorial(201);
	
    public static void main(String[] args) {
    	info();
    	AccessCppTutorial.setS(110); obj1.setA(111);
    	info();
    	AccessCppTutorial.setS(210); obj2.setA(211);
    	info();
    }

	private static void info() {
        System.out.println("class: "+AccessCppTutorial.getS());
        System.out.println("obj1:  "+obj1.getA());
        System.out.println("obj2:  "+obj2.getA());
	}
}
