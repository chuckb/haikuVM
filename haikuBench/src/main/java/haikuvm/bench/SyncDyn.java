package haikuvm.bench;

import static haiku.avr.lib.arduino.WProgram.*;

/**
 * The use of synchroniced will not hurt.
 * 
 * @author genom2
 *
 */
public class SyncDyn {
	static private final int N = 10;
	private static SyncDyn dyn;
	
    private static void delay(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
        }
    }

    private synchronized void methA(int i) {
        Serial.println(">>A"+i);
        delay(1000);
        Serial.println("<<A"+i);
    }
    
    private synchronized void methB(int i) {
        Serial.println(">>B"+i);
        delay(500);
        Serial.println("<<B"+i);
    }
    
    static private class A implements Runnable {
        @Override
        public void run() {
            for(int i=0; i<N+1; i++) {
                dyn.methA(i);
            }
        }
    }

    static private class B implements Runnable {
        @Override
        public void run() {
            for(int i=0; i<N; i++) {
                dyn.methB(i);
            }
        }
    }

	public static void main(String[] args) {
	    dyn=new SyncDyn();
        new Thread(new A()).start();
        new B().run();
	}
}
