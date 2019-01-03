package haikuvm.bench;

public class Threads0 {
	static class Blink implements Runnable {
		private String name;

		public Blink(String name) {
			this.name = name;
		}

		public void run() {
			for (int i = 0; i < 10; i++) {
				System.out.println(i + ": " + name);
				Threads0.print("b");
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Thread b = new Thread(new Blink("-B"));
		b.start();
		for (int i = 0; i < 10; i++) {
			System.out.println(i + ": M-");
			print("m");
		}
	}

	public static void print(String string) {
		// for (int i = 0; i < 1000; i++) {
		// System.out.print(string);
		// }
	}

}

