package haikuvm.bench;

public class MemoryOverflow0 {
	public static void main(String[] args) throws Exception {
	    overflow();
	}

	/**
	 * test with virtual no stack usage
	 */
	private static void overflow() {
        overflow();
	}
}
