package haikuvm.bench;
/*
  DoubleTest2.java
 */

public class DoubleTest2 {

	static double parallel(double a, double b) {
		return (a * b) / (a + b);
	}

	public static void main(String[] args) {
		double a = 0, b = 0;
		for (a = 0.0; a <= 20.0; a += 1.0) {
			for (b = 0.0; b <= 20.0; b += 1.0) {
				double d = parallel(a, b);
				System.out.println(a + ", " + a + " || " + b + " = " + d);
			}
		}
		System.out.println(a + " || " + b);
	}
}
