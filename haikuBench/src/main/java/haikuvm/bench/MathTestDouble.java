package haikuvm.bench;

/*
 MathTest.java
 */
public class MathTestDouble {

	public static void printfn(double val) {
		System.out.println("sin(" + val + ")=" + Math.sin(val));
		System.out.println("cos(" + val + ")=" + Math.cos(val));
		System.out.println("tan(" + val + ")=" + Math.tan(val));
	}

	public static void main(String[] args) {
	    printfn(-Math.PI);
	    printfn(0.0);
	    printfn(Math.PI);
	    printfn(1.5*Math.PI);
	    printfn(2.0*Math.PI);

		double a = 10.1;
		double b = -20.2;
		double c = -30.3;

		System.out.println("a=" + a);
		System.out.println("b=" + b);
		System.out.println("c=" + c);
		System.out.println("a+b=" + (a + b));
		System.out.println("b*a=" + (b * a));
		System.out.println("c/a=" + (c / a));
		System.out.println("0.0=" + 0);
		System.out.println("0.000001=" + 0.000001);
		System.out.println("100000.1=" + 100000.1);

	}

}
