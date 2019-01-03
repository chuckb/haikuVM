package problems;

import static haiku.avr.AVRConstants.*;

/**
 * Let some LED blink. Example to demonstrate thread definition, semaphores, and
 * thread sleep.
 * 
 * Assumes that LED is connected at Port B and Bit 5.
 * 
 * mkdir myProject 
 * cd myProject 
 * C:\haikuVM\bin\haiku -v --Config avr -o BlinkAsync.hex C:\haikuVM\examples\src\main\java\avr\tutorial\BlinkAsync.java
 */
public class BlinkAsync {
    private static class Semaphore {
        private int value;

        public Semaphore(int init) {
            if (init < 0)
                init = 0;
            value = init;
        }

        /**
         * down
         */
        public synchronized void semWait() {
            value--;
            while (value < 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
        }

        /**
         * up
         */
        public synchronized void semSignal() {
            value++;
            notifyAll();
        }
    }

    // The LED is attached to pin 13 on Arduino.
    private static final int LED = 1 << 5;
    
    // Declare a semaphore with an inital counter value of zero.
    private static Semaphore sem = new Semaphore(0);

    /**
     * Thread 1, turn the LED off when signaled by thread 2.
     */
    private static class Thread1 extends Thread {
        // Declare the thread function for Thread 1.
        public void run() {
            while (true) {
                // Wait for signal from Thread 2.
                sem.semWait();

                // Turn LED off.
                PORTB &= ~LED;
            }
        }
    }

    /**
     * Thread 2, turn the LED on and signal thread 1 to turn the LED off.
     */
    private static class Thread2 extends Thread {
        // Declare the thread function for Thread 2.
        public void run() {
            try {
                while (true) {
                    // Turn LED on.
                    PORTB |= LED;

                    // Sleep for 200 milliseconds.
                    sleep(200);

                    // Signal Thread 1 to turn LED off.
                    sem.semSignal();

                    // Sleep for 200 milliseconds.
                    sleep(200);
                }
            } catch (InterruptedException e) {
            }
        }
    }

    static void clinitHaikuMagic() {
        
    }
    /**
     * These threads start with a null argument. A thread's name may also be
     * null to save RAM since the name is currently not used.
     */
    public static void main(String[] args) {
        clinitHaikuMagic();
        // Data direction of I/O-Port.
        DDRB = LED;
        new Thread1().start();
        new Thread2().start();
    }
}
