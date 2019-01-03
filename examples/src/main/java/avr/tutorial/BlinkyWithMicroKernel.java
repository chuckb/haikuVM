package avr.tutorial;

import static haiku.avr.AVRConstants.*;

/**
 * The status LED of the Duemilanove will blink. 
 * 
 * mkdir myProject 
 * cd myProject
 * C:\haikuVM\bin\haiku -v --Config duemilanove C:\haikuVM\examples\src\main\java\avr\tutorial\BlinkyWithMicroKernel.java
 * 
 * @author genom2
 */
public class BlinkyWithMicroKernel {
    // LED is connected with PORTB and Bit 5.
    // give it a name:
    private static final int LED = 1 << 5;

    public static void main(String[] args) throws InterruptedException {
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
