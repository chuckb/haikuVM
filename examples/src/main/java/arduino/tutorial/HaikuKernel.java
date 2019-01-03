package arduino.tutorial;

import static haiku.avr.AVRConstants.*;
import java.io.*;

public class HaikuKernel {
    private static final int BAUD_RATE = 57600;
    private static final long F_CPU = 16000000;

    private static void init() {
        // set baud rate
        final short t = (short) (((F_CPU >> 4) + (BAUD_RATE >>> 1)) / BAUD_RATE - 1);
        UBRR0H = (t >>> 8);
        UBRR0L = t;
        
        // Enable RX and TX
        UCSR0B = (1 << RXEN0) | (1 << TXEN0);
        
        // set timer 0 prescale factor to 64
        TCCR0B = (1<<CS01)  | (1<<CS00);
        
        // enable timer 0 overflow interrupt
        TIMSK0 = (1<<TOIE0);

        System.out = new PrintStream(new OutputStream() {
            public void write(int b) throws IOException {
                while ((UCSR0A & (1 << UDRE0)) == 0)
                    ;

                UDR0 = b;
            }
        });
    }

    public static void main(String[] args) {
        init();
        haiku.vm.HaikuMagic.main(args);
        Thread.currentThread().stop();
    }
}
