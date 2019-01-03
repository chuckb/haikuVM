package haikuvm.bench;

public class MemoryOverflow1 {
	public static void main(String[] args) throws Exception {
	    overflow(1);
	}

	private static void overflow(int level) {
		System.out.println("overflow "+level);
        overflow(level+1);
	}
}
