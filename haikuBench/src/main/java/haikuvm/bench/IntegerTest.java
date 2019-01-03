package haikuvm.bench;

/**
 * I had a problem with unsigned char on RCX. so -2 became 254 in BIPUSH
 * @author Genom
 *
 */
public class IntegerTest {

	public static String valueOf(int v) {
		String res = "";
		int s=v;
		if (v > 0) {
			v=-v;
			System.out.println("v>0");
		} else {
			System.out.println("v<=0");
		}
		while (true) {
			res = (char) ('0' - v % 10) + res;
			System.out.println(res);
			v /= 10;
			if (v==0) {
		        if (s < 0)
		            return "-" + res;
				System.out.println(res);
		        return res;
			}
		}
	}

	public static void main(String[] args) {
		System.out.println(2);
		System.out.println(-2);
		valueOf(2);
		valueOf(-2);
		valueOf(1234);
	}
}
