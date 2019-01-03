package haikuvm.bench;

public class ThreadsWithSync1 {
	
	static class Blink implements Runnable {
		private int intervall;
		private String name;
		private ThreadsWithSync1 ths;
		
		public Blink(String name, int intervall, ThreadsWithSync1 ths) {
			this.name=name;
			this.intervall=intervall;
			this.ths=ths;
		}
		
		public void run() {
			ths.run(name, intervall);
			//ThreadsWithSync1.runs(name, intervall);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ThreadsWithSync1 ths=new ThreadsWithSync1();
		Thread b= new Thread(new Blink("-B", 60, ths));
		b.start();
		Thread m= new Thread(new Blink("M-", 100, ths));
		m.start();
	}

	public void run(String name, int intervall) {
		synchronized (this) {
			run_sync(name, intervall);
		}
	}

	public void run_sync(String name, int intervall) {
		for (int i = 0; i < 10; i++) {
			try {
				wait(intervall);
			} catch (InterruptedException e) {
			}
			System.out.println(i + ": " + name);
		}
	}

	public static synchronized void runs(String name, int intervall) {
		for (int i = 0; i < 10; i++) {
			try {
				ThreadsWithSync1.class.wait(intervall);
			} catch (InterruptedException e) {}
			System.out.println(i+": "+name);
		}
	}
}

