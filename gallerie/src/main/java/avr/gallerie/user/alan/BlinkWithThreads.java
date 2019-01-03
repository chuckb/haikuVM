package avr.gallerie.user.alan;

import static haiku.avr.AVRConstants.*;

/**
 * Runs on Atmega328p 12MHz 
 */
public class BlinkWithThreads implements Runnable {

    /*
     * This program demonstrates how threads can be used to do multiple things
     * at a time and reusing code. We use the same Runnable instance to blink 2
     * different LEDs at different frequencies.
     */
    private static final int LED5 = (1 << 5);
    private static final int LED6 = (1 << 6);
    private static final int LEDMASK = LED5 | LED6;

    static {
        /*
         * This sets up one of the timers - it seems like it has an influence on
         * Thread.sleep();
         */
        TCCR0B |= _BV(CS01) | _BV(CS00);
        TIMSK0 |= _BV(TOIE0);
        DDRD = LEDMASK;
    }

    public static int _BV(int bit) {
        return 1 << bit;
    }

    // Each instance has its own state
    private int led;
    private int delayms;

    public BlinkWithThreads(int led, int delay) {
        this.led = led;
        this.delayms = delay;
    }

    public void run() {
        while (true) {
            PORTD |= led;
            delay(delayms);
            PORTD &= ~led;
            delay(delayms);
        }
    }

    private void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
        }
    }

    public static void main(String[] args) {
        // Here we fire off 2 similar tasks but with different
        // state
        new Thread(new BlinkWithThreads(LED5, 1000)).start();
        new Thread(new BlinkWithThreads(LED6, 500)).start();
    }
}
