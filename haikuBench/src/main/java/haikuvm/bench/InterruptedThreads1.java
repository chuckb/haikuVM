package haikuvm.bench;

public class InterruptedThreads1 {
	static private Semaphore semaphore =new Semaphore();
	static private long timeout;

	static class Semaphore {
		synchronized void lock(int timeout) {
			String msg;
			long t0 = System.currentTimeMillis();
			try {
				wait(timeout);
				msg="Normal end after";
			} catch (InterruptedException e) {
				msg="Someone interrupted me after";
			}
			long t1 = System.currentTimeMillis();
			System.out.println(msg + " "+(t1-t0)+" [msec]");
		}		
	}

	static class Interruptable extends Thread {
		private int intervall;
		
		public Interruptable(int intervall) {
			this.intervall=intervall;
		}
		
		public void run() {
			while (System.currentTimeMillis()<timeout) {
				semaphore.lock(intervall);
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new InterruptedThreads1().start();
	}

	private void start() {
		timeout=System.currentTimeMillis()+30000;
		
		Thread t0=new Interruptable(733);
		t0.start();
		Thread t1=new Interruptable(4000);
		t1.start();
		
		for (int t=1000; System.currentTimeMillis()<timeout; t+=500) {
			try {
				Thread.sleep(t);
			} catch (InterruptedException e) {
				System.out.println("sleep1 was interrupted unexpected!");
			}
			t1.interrupt();
		}
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			System.out.println("sleep2 was interrupted unexpected!");
		}
		System.out.println("done");
	}
}

