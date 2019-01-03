package haikuvm.bench;

import static haiku.avr.lib.arduino.WProgram.*;

/**
 * The use of synchroniced will not hurt.
 * 
 * @author genom2
 *
 */
public class SyncDyn2 {
	static private final int N = 10;
	private static SyncDyn2 dyn;
	
    private static void delay(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
        }
    }

    private synchronized int methA(int i) {
        Serial.println(">>A"+i);
        delay(1000);
        if (i<32) {
            Serial.println("<<A"+i);
            return 50;
        }
        Serial.println("<<A"+i);
        return 100;
    }
    
    private synchronized int methB(int i) {
        Serial.println(">>B"+i);
        delay(500);
        if (i<32) {
            Serial.println("<<B"+i);
            return 50;
        }
        Serial.println("<<B"+i);
        return 100;
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
	    dyn=new SyncDyn2();
        new Thread(new A()).start();
        new B().run();
	}
}
