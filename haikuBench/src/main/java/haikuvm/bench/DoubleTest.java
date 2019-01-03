package haikuvm.bench;
/*
  DoubleTest.java
 */

public class DoubleTest {

	public static void main(String[] args) {
		int i0 = 1;
		int i1 = 20;
		for (double a = i0; a <= i1; a += 1.0) {
			for (double b = i0; b <= i1; b += 1.0) {
				double d = (a * b) / (a + b);
				System.out.println((int) a + " " + (int) b + " " + d);
			}
		}
	}
}
