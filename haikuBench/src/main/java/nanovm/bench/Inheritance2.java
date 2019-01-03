package nanovm.bench;
interface Interface1 {
	String y="Interface";	
}

class Inheritance4 implements Interface1 {
	String x="Inheritance4";
	
	void go() {
		System.out.println("Inheritance4.x="+x);		
	}

	void call() {
		System.out.println("Inheritance4.call()");	
	}
}

class Inheritance3 extends Inheritance4 {
	String x="Inheritance3";
	
	void go() {
		System.out.println("Inheritance3.x="+x);		
		super.go();
	}

	void call() {
		System.out.println("Inheritance3.call()");	
		super.call();		
	}
}

public class Inheritance2 extends Inheritance3 {
	String x="Inheritance2";

	void go() {
		System.out.println("Inheritance2.x="+x);	
		super.go();
		
		System.out.println();
		Inheritance4 i4= new Inheritance2();
		Inheritance3 i3= new Inheritance2();
		Interface1 int1= new Inheritance2();
		System.out.println("i4.x="+i4.x);	
		System.out.println("i3.x="+i3.x);	
		System.out.println("int1.y="+int1.y);	
	}

	void call() {
		System.out.println("Inheritance2.call()");	
		super.call();		

		System.out.println();
		Inheritance4 i4= new Inheritance3();
		Inheritance3 i3= new Inheritance3();
		System.out.print("i4.call() -> ");	i4.call();
		System.out.print("i3.call() -> ");	i3.call();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Inheritance2().go();
		System.out.println();
		System.out.println();
		new Inheritance2().call();	
	}
}
