package duemilanove.tutorial;

//import static haiku.avr.AVRConstants.*;
import haiku.vm.NativeCVariable8;

public class HelloWorldWithoutHaikuKernel {
    /**
     * UBRR0H MEM Addr=0xC5 Bits=8 <br>
     * UBRR0_8 0 <br>
     * UBRR0_9 1 <br>
     * UBRR0_10 2 <br>
     * UBRR0_11 3 <br>
     */
    @NativeCVariable8
    private static volatile int UBRR0H;
    private static final int UBRR0_8 = 0;
    private static final int UBRR0_9 = 1;
    private static final int UBRR0_10 = 2;
    private static final int UBRR0_11 = 3;

    /**
     * UBRR0L MEM Addr=0xC4 Bits=8 <br>
     * UBRR0_0 0 <br>
     * UBRR0_1 1 <br>
     * UBRR0_2 2 <br>
     * UBRR0_3 3 <br>
     * UBRR0_4 4 <br>
     * UBRR0_5 5 <br>
     * UBRR0_6 6 <br>
     * UBRR0_7 7 <br>
     */
    @NativeCVariable8
    private static volatile int UBRR0L;
    private static final int UBRR0_0 = 0;
    private static final int UBRR0_1 = 1;
    private static final int UBRR0_2 = 2;
    private static final int UBRR0_3 = 3;
    private static final int UBRR0_4 = 4;
    private static final int UBRR0_5 = 5;
    private static final int UBRR0_6 = 6;
    private static final int UBRR0_7 = 7;

    /**
     * UCSR0A MEM Addr=0xC0 Bits=8 <br>
     * MPCM0 0 <br>
     * U2X0 1 <br>
     * UPE0 2 <br>
     * DOR0 3 <br>
     * FE0 4 <br>
     * UDRE0 5 <br>
     * TXC0 6 <br>
     * RXC0 7 <br>
     */
    @NativeCVariable8
    private static volatile int UCSR0A;
    private static final int MPCM0 = 0;
    private static final int U2X0 = 1;
    private static final int UPE0 = 2;
    private static final int DOR0 = 3;
    private static final int FE0 = 4;
    private static final int UDRE0 = 5;
    private static final int TXC0 = 6;
    private static final int RXC0 = 7;

    /**
     * UDR0 MEM Addr=0xC6 Bits=8 <br>
     * UDR0_0 0 <br>
     * UDR0_1 1 <br>
     * UDR0_2 2 <br>
     * UDR0_3 3 <br>
     * UDR0_4 4 <br>
     * UDR0_5 5 <br>
     * UDR0_6 6 <br>
     * UDR0_7 7 <br>
     */
    @NativeCVariable8
    private static volatile int UDR0;
    private static final int UDR0_0 = 0;
    private static final int UDR0_1 = 1;
    private static final int UDR0_2 = 2;
    private static final int UDR0_3 = 3;
    private static final int UDR0_4 = 4;
    private static final int UDR0_5 = 5;
    private static final int UDR0_6 = 6;
    private static final int UDR0_7 = 7;

    /**
     * UCSR0B MEM Addr=0xC1 Bits=8 <br>
     * TXB80 0 <br>
     * RXB80 1 <br>
     * UCSZ02 2 <br>
     * TXEN0 3 <br>
     * RXEN0 4 <br>
     * UDRIE0 5 <br>
     * TXCIE0 6 <br>
     * RXCIE0 7 <br>
     */
    @NativeCVariable8
    private static volatile int UCSR0B;
    private static final int TXB80 = 0;
    private static final int RXB80 = 1;
    private static final int UCSZ02 = 2;
    private static final int TXEN0 = 3;
    private static final int RXEN0 = 4;
    private static final int UDRIE0 = 5;
    private static final int TXCIE0 = 6;
    private static final int RXCIE0 = 7;

    
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
