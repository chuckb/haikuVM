package haikuvm.bench;

public class SynchronizedThreads3 {
	private static final int TEST_TIME = 50000;
	static private int semaphore;
	private static long end;

	static class Sync implements Runnable {
		private int intervall;
		
		public Sync(int intervall) {
			this.intervall=intervall;
		}
		
		public void run() {
			for (int i = 0; System.currentTimeMillis()<end; i++) {
				getSemaphore(i, intervall);
			}
			System.out.println("done "+intervall);
		}
	}

	private static void getSemaphore(int count, int intervall) {
		synchronized (SynchronizedThreads3.class) {
			semaphore++;
			System.out.println(count+" locked  "+intervall+" -> "+semaphore);
			try {
				Thread.sleep(intervall);
			} catch (InterruptedException e) {}
			semaphore--;
			System.out.println(count+" release "+intervall+" -> "+semaphore);
			
			try {
				SynchronizedThreads3.class.wait(1000);
			} catch (InterruptedException e) {}

			semaphore++;
			System.out.println(count+" locked  "+intervall+" -> "+semaphore);
			try {
				Thread.sleep(intervall);
			} catch (InterruptedException e) {}
			semaphore--;
			System.out.println(count+" release "+intervall+" -> "+semaphore);
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		end=System.currentTimeMillis()+TEST_TIME;
		new Thread(new Sync(1000)).start();
		new Thread(new Sync(2000)).start();
		new Thread(new Sync(3000)).start();
		new Thread(new Sync(4000)).start();
		
		long done=end;
		for (int i = 0; System.currentTimeMillis()<done; i++) {
			int s=semaphore;
			if (s>0) done=System.currentTimeMillis()+500;
			if (s<0 || s>1) {
				System.out.println("Assertion violated "+s);
				break;
			}
		}
		System.out.println("assertion ready");
	}
}

