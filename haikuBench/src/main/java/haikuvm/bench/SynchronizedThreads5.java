package haikuvm.bench;

public class SynchronizedThreads5 {
	private static final int TEST_TIME = 50000;
	static private int semaphore;
	private static long end;

	static class Sync implements Runnable {
		private int intervall;
		private SynchronizedThreads5 synchronizedThreads;
		
		public Sync(SynchronizedThreads5 synchronizedThreads, int intervall) {
			this.synchronizedThreads=synchronizedThreads;
			this.intervall=intervall;
		}
		
		public void run() {
			for (int i = 0; System.currentTimeMillis()<end; i++) {
				synchronizedThreads.getSemaphore(i, intervall);
			}
			System.out.println("done "+intervall);
		}
	}

	private synchronized void getSemaphore(int count, int intervall) {
		semaphore++;
		System.out.println(count+" locked  "+intervall+" -> "+semaphore);
		try {
			Thread.sleep(intervall);
		} catch (InterruptedException e) {}
		semaphore--;
		System.out.println(count+" release "+intervall+" -> "+semaphore);
	}
	
//	private void getSemaphore2(int count, int intervall) {
//		synchronized (this) {
//		}
//	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new SynchronizedThreads5().start();
	}

	private void start() {
		end=System.currentTimeMillis()+TEST_TIME;
		new Thread(new Sync(this, 1000)).start();
		new Thread(new Sync(this, 2000)).start();
		new Thread(new Sync(this, 3000)).start();
		//new Thread(new Sync(this, 4000)).start();
		
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

