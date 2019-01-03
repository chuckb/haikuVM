package haikuvm.bench;
public class Threads1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Thread b= new Thread(new Blink("-B", 600));
		b.start();
		for (int i = 0; i < 10; i++) {
			try {
				Thread.sleep(900);
			} catch (InterruptedException e) {}
			System.out.println(i+": M-");
			print("m");
		}
	}

	public static void print(String string) {
//		for (int i = 0; i < 1000; i++) {
//			System.out.print(string);
//		}
	}

}

class Blink implements Runnable {
	private int intervall;
	private String name;
	
	public Blink(String name, int intervall) {
		this.name=name;
		this.intervall=intervall;
	}
	
	
	public void run() {
		for (int i = 0; i < 10; i++) {
			try {
				Thread.sleep(intervall);
			} catch (InterruptedException e) {}
			System.out.println(i+": "+name);
			Threads1.print("b");
		}
	}
}
