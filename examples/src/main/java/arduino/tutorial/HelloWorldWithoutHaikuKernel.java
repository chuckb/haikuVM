package arduino.tutorial;

import static haiku.avr.AVRConstants.*;

public class HelloWorldWithoutHaikuKernel {
    private static final int BAUD_RATE = 57600;
    private static final long F_CPU = 16000000;

    private static void init() {
        // set baud rate
        final short t = (short) (((F_CPU >> 4) + (BAUD_RATE >>> 1)) / BAUD_RATE - 1);
        UBRR0H = (t >>> 8);
        UBRR0L = t;
        UCSR0B = (1 << RXEN0) | (1 << TXEN0);
    }

    private static void write(int b) {
        while ((UCSR0A & (1 << UDRE0)) == 0)
            ;

        UDR0 = b;
    }

    public static void println(String line) {
        for (int i = 0; i < line.length(); i++) {
            write(line.charAt(i));
        }
        write('\r');
        write('\n');
    }

    public static void main(String[] args) {
        init();
        println("Hello World");
        while(true) ;
    }
}
