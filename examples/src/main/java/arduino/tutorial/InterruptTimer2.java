package arduino.tutorial;

import static haiku.avr.AVRConstants.*;

public class InterruptTimer2 {

    private static native long getTimer2();

    public static void main(String[] args) {

        // prescaler 1024
        TCCR2B = (1<<CS22) | (1<<CS21) | (1<<CS20);

        // enable timer interrupt overflow
        TIMSK2 = (1<<TOIE2);

        for (int i = 0; i < 10; i++) {
            System.out.println("Value of timer2_interrupts is: " + getTimer2());
        }
    }
}
