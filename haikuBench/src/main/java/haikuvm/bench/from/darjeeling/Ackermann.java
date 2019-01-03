/**
 * 
 */
package haikuvm.bench.from.darjeeling;

/**
 * @author Michael Maaser
 *
 */
public class Ackermann implements BenchmarkImplementation {

	public static void main(String[] args) {
		BenchmarkImplementation test = new Ackermann();
		test.runTest(30);
	}
	
	/* (non-Javadoc)
	 * @see com.ihp.TestImplementation#runTest(int)
	 */
	public void runTest(int times) {
		int result=0;
		for (int i=0; i<=times;i++) {
			result = ack(i, i);
			System.out.println(getName()+" "+i+" = "+result);
		}
	}

	/* (non-Javadoc)
	 * @see com.ihp.TestImplementation#getName()
	 */
	public String getName() {
		return "Ackermann-Peter Function ";
	}

	private int ack(int n, int m) {
		if (n == 0) {
			return m + 1;
		} else if (m == 0) {
			return ack(n - 1, 1);
		} else {
			return ack(n - 1, ack(n, m - 1));
		}
	}
}
