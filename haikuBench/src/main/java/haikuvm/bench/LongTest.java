package haikuvm.bench;

/**
 *
 */
public class LongTest {

	public static void main(String[] args) {
		long l = 1;
		for (int i = 0; i < 64; i++) {
			System.out.println(i + ": " + l + " \t"+ System.currentTimeMillis());
			l += l;
		}
	}
}
