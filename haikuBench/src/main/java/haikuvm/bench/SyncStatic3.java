package haikuvm.bench;

import static haiku.avr.lib.arduino.WProgram.*;

/**
 * The use of synchroniced will not hurt.
 * 
 * @author genom2
 *
 */
public class SyncStatic3 implements Runnable {
	static private final int N = 10;
	
    private static void delay(int i) {
        for (int t = 0; t < i; t++) {
            for (int j = 0; j < 30; j++) {
                
            }
        }
    }

    private static synchronized void methA(int i) {
        Serial.println(">>A"+i);
        delay(1000);
        Serial.println("<<A"+i);
    }
    
    private static synchronized void methB(int i) {
        Serial.println(">>B"+i);
        delay(500);
        Serial.println("<<B"+i);
    }
    
    public void run() {
        for(int i=0; i<N+1; i++) {
            methA(i);
        }
    }

    static private class B implements Runnable {
        @Override
        public void run() {
            for(int i=0; i<N; i++) {
                methB(i);
            }
        }
    }

	public static void main(String[] args) {
        new Thread(new SyncStatic3()).start();
        new B().run();
	}
}
