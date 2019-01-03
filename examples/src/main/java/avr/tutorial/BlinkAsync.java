package avr.tutorial;

import static haiku.avr.AVRConstants.*;

/**
 * Let some LED blink. Example to demonstrate thread definition, semaphores, and
 * thread sleep.
 * 
 * Assumes that LED is connected at Port B and Bit 5.
 * 
 * mkdir myProject 
 * cd myProject C:\haikuVM\bin\haiku -v --Config avr -o BlinkAsync.hex C:\haikuVM\examples\src\main\java\avr\tutorial\BlinkAsync.java
 */
public class BlinkAsync {
    
    private static BlinkAsync blinkAsync;

    private synchronized void off() throws InterruptedException {
        while (true) {
            // Wait for signal from Thread 2.
            wait();

            // Turn LED off.
            PORTB &= ~LED;
        }
    }

    private synchronized void on() throws InterruptedException {
        while (true) {
            // Turn LED on.
            PORTB |= LED;

            // Sleep for 200 milliseconds.
            Thread.sleep(1000);

            // Signal Thread 1 to turn LED off.
            notifyAll();

            // Sleep for 200 milliseconds.
            wait(200);
        }
    }
    // The LED is attached to pin 13 on Arduino.
    private static final int LED = 1 << 5;

    /**
     * Thread 1, turn the LED off when signaled by thread 2.
     */
    private static class Thread1 extends Thread {
        // Declare the thread function for Thread 1.
        public void run() {
            try {
                blinkAsync.off();
            } catch (InterruptedException e) {
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
                blinkAsync.on();
            } catch (InterruptedException e) {
            }
        }
    }

    /**
     * These threads start with a null argument. A thread's name may also be
     * null to save RAM since the name is currently not used.
     */
    public static void main(String[] args) {
        blinkAsync = new BlinkAsync();
        // Data direction of I/O-Port.
        DDRB = LED;
        new Thread1().start();
        new Thread2().start();
        while(true) ;
    }
}
