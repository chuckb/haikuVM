package avr.tutorial;

import static haiku.avr.AVRConstants.*;

/**
 * The status LED of the Duemilanove will blink. 
 * 
 * mkdir myProject 
 * cd myProject
 * C:\haikuVM\bin\haiku -v --Config duemilanove C:\haikuVM\examples\src\main\java\avr\tutorial\Blinky.java
 * 
 * @author genom2
 */
public class Blinky {
    // LED is connected with PORTB and Bit 5.
    // give it a name:
    private static final int LED = 1 << 5;

    private static void initAtmega328p_16MHz() {
        // set timer 0 prescale factor to 64
        // for correct millisecond timing of:
        // Atmega328p 16MHz
        TCCR0B |= (1<<CS01) | (1<<CS00);

        // enable timer 0 overflow interrupt
        // important for:
        // Thread.sleep(ms)
        // System.currentTimeMillis()
        TIMSK0 |= (1<<TOIE0);
    }

    public static void main(String[] args) throws InterruptedException {
        initAtmega328p_16MHz();

        // initialize the digital pin as an output.
        DDRB |= LED;
        // the loop runs forever:
        while (true) {
            PORTB |= LED;       // turn the LED on (HIGH is the voltage level)
            Thread.sleep(1000); // wait for a second
            PORTB &= ~LED;      // turn the LED off by making the voltage LOW
            Thread.sleep(1000); // wait for a second
        }
    }
}
