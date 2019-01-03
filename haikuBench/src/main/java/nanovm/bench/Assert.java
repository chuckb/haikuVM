package nanovm.bench;

public class Assert {
	static void equal(String msg, int a, int b) {
		if (a==b) {
			System.out.println(msg+a+" ("+b+")");
		} else {
			System.out.println(msg+a+" but should be "+b);
		}
	}

	static void equal(String msg, boolean a, boolean b) {
		if (a==b) {
			System.out.println(msg+" " +a+" ("+b+")");
		} else {
			System.out.println(msg+" " +a+" but should be "+b);
		}
	}
	static void equal(String msg, boolean a, char b, int i, long l, double d, float f, short s, String m) {
	}
}
