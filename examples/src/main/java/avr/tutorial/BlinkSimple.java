package avr.tutorial;

import haiku.vm.NativeCVariable8;

/**
 * Let some LED blink.
 * 
 * Assumes that LED is connected with Port B and Bit 5.
 * 
 * Start like this:
 * mkdir myProject 
 * cd myProject 
 * C:\haikuVM\bin\haiku -v --Config avr -o BlinkSimple.hex C:\haikuVM\examples\src\main\java\avr\tutorial\BlinkSimple.java
 */
public class BlinkSimple {
    @NativeCVariable8
    private static volatile int PORTB;

    @NativeCVariable8
    private static volatile int DDRB;
    
    private static final int LED = 1 << 5;

    private static void delay(int nounit) {
        while(nounit>0) {
            nounit--;
        }
    }

    public static void main(String[] args) {
        // Data direction of I/O-Port.
        DDRB = LED;
        while (true) {
            PORTB |= LED; // on
            delay(3000);
            PORTB &= ~LED; // off
            delay(30000);
        }
    }
}
