package nanovm.bench;
interface JustFieldsI {
	String a="aJustFieldsI";	
}

class JustFields2 implements JustFieldsI {
	static String z="zJustFields2";
	static String z2="zJustFieldsz2";
}	

class JustFields1 extends JustFields2 {
	String x="xJustFields1";
	String y="yJustFields1";
	static String z="zJustFields1";
}	

public class JustFields0 extends JustFields1 {
	String x="xJustFields0";

	void call() {
		System.out.println("JustFields0.call()");	
		System.out.println();
		
		JustFields1 f1= new JustFields0();
		JustFields0 f0= new JustFields0();
		System.out.println("f1.x -> " + f1.x);
		System.out.println("f0.x -> " + f0.x);
		System.out.println("this.x -> " + this.x);
		System.out.println("super.x -> " + super.x);
		System.out.println("this.y -> " + this.y);
		System.out.println("super.y -> " + super.y);
		System.out.println("static z -> " + z);
		System.out.println("JustFieldsI.a -> " + a);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new JustFields0().call();	
	}
}
