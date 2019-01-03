package haikuvm.bench;

public class Double2String {

	public static void main(String[] args) {
		double d;
		double a=9;
		double b=11;
		d= parallel(a, b);
		valueOf(d);
		System.out.println(a + ", " + a + " || " + b + " = " + d);
		System.out.println();

		a=14;
		b=19;
		d= parallel(a, b);
		valueOf(d);
		System.out.println(a + ", " + a + " || " + b + " = " + d);
		System.out.println();
    }

	static double parallel(double a, double b) {
		return (a * b) / (a + b);
	}

	/**
	 * On RCX (32/32) we had errors:
	 * (14 * 19) / (14 + 19) -> 7.1060606 instead of 8.060606
	 * ( 9 * 11) / ( 9 + 11) -> 4.9410    instead of 4.9499998
	 *
	 * @param d
	 * @return
	 */
	public static String valueOf(double d) {
		if (d != d)	return "NaN";
		if (d == Double.POSITIVE_INFINITY) return "Infinity";
		if (d == Double.NEGATIVE_INFINITY) return "-Infinity";
		double scale = 1;
		if (d < 0) {
			System.out.println("-");
			d = -d;
		}
		int m = 1;
		while (d/scale >= 10) {
			scale *= 10;
			m++;
		}
		while (m>-6 && (m > -1 || d > 0)) {
			if (m == 0)
				System.out.println(".");
			int z= (int)(d/scale);
			if (z==10) {
				z--;
			} else if (z<9 && (int)((d-z*scale)/(scale/10))==10) {
				z++;
			}
			d-=z*scale;
			scale /= 10;
			System.out.println(z);
			m--;
		}
		return "";
	}


}
