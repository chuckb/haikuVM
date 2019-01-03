package haikuvm.bench;

/**
 *
 */
public class GCTestSimple {

	public static void main(String[] args) throws InterruptedException {
		for (int t = 0; t < 100; t++) {
			long l = 1;
			for (int i = 0; i < 8; i++) {
				System.out.println("["+t+"]  2^"+i + " = " + l);
				l += l;
			}
			Thread.sleep(100);
		}
	}
}
