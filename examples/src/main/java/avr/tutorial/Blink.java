package avr.tutorial;

import haiku.vm.NativeCVariable8;

/**
 * Let some LED blink.
 * 
 * Assumes that LED is connected at Port B and Bit 5.
 * 
 * mkdir myProject 
 * cd myProject 
 * C:\haikuVM\bin\haiku -v --Config avr -o Blink.hex C:\haikuVM\examples\src\main\java\avr\tutorial\Blink.java
 */
public class Blink {
    /**
     * TCCR0B IO 0x25 8 <br>
     * CS00 0 <br>
     * CS01 1 <br>
     * CS02 2 <br>
     * WGM02 3 <br>
     * FOC0B 6 <br>
     * FOC0A 7 <br>
     */
    @NativeCVariable8
    private static volatile int TCCR0B;
    private static final int CS00 = 0;
    private static final int CS01 = 1;
    private static final int CS02 = 2;
    private static final int WGM02 = 3;
    private static final int FOC0B = 6;
    private static final int FOC0A = 7;

    /**
     * TIMSK0 MEM Addr=0x6E Bits=8 <br>
     * TOIE0 0 <br>
     * OCIE0A 1 <br>
     * OCIE0B 2 <br>
     */
    @NativeCVariable8
    private static volatile int TIMSK0;
    private static final int TOIE0 = 0;
    private static final int OCIE0A = 1;
    private static final int OCIE0B = 2;

    @NativeCVariable8
    private static volatile int PORTB;

    @NativeCVariable8
    private static volatile int DDRB;
    
    private static final int LED = _BV(5);

    private static void initAtmega328p_16MHz() {
        // set timer 0 prescale factor to 64
        // for correct millisecond timing of:
        // Atmega328p 16MHz
        TCCR0B |= _BV(CS01) | _BV(CS00);

        // enable timer 0 overflow interrupt
        // important for:
        // Thread.sleep(ms)
        // System.currentTimeMillis()
        TIMSK0 |= _BV(TOIE0);
    }

    private static void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
        }
    }

    public static int _BV(int bit) {
        return 1 << bit;
    }

    public static void main(String[] args) {
        initAtmega328p_16MHz();

        // Data direction of I/O-Port.
        DDRB = LED;
        while (true) {
            PORTB |= LED; // on
            delay(200);
            PORTB &= ~LED; // off
            delay(1000);
        }
    }
}
