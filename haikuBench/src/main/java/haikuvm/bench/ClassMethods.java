package haikuvm.bench;

public class ClassMethods implements Runnable {

	private static synchronized void lock(int ms) throws InterruptedException {
        System.out.println("ClassMethods begin at "+System.currentTimeMillis()+" for "+ ms);
	    ClassMethods.class.wait(ms);
        System.out.println("done at "+System.currentTimeMillis()+" for "+ ms);
	}

	public static void main(String[] args) throws Exception {
		new Thread(new ClassMethods()).start();
        lock(2000);
	}

    @Override
    public void run() {
        try {
            lock(1000);
        } catch (InterruptedException e) {
        }
    }
}
