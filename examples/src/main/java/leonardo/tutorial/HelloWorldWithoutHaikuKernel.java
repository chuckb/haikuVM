package leonardo.tutorial;

//import static haiku.avr.AVRConstants.*;
import haiku.vm.NativeCVariable8;

/**
 * This is for the physical serial port only (not for USB).
 * 
 * See
 * http://academy.cba.mit.edu/classes/embedded_programming/doc7766.pdf
 * Chapter 31. Register Summary
 */
public class HelloWorldWithoutHaikuKernel {
    /** UBRR1L     MEM Addr=0xCC Bits=8 <br>
     */
    @NativeCVariable8
    private static volatile int UBRR1L;

    /** UBRR1H     MEM Addr=0xCD Bits=8 <br>
     */
    @NativeCVariable8
    private static volatile int UBRR1H;
    
    /** UDR1       MEM Addr=0xCE Bits=8 <br>
     */
    @NativeCVariable8
    private static volatile int UDR1;

    /** UCSR1B     MEM Addr=0xC9 Bits=8 <br>
     *  TXB81      0 <br>
     *  RXB81      1 <br>
     *  UCSZ12     2 <br>
     *  TXEN1      3 <br>
     *  RXEN1      4 <br>
     *  UDRIE1     5 <br>
     *  TXCIE1     6 <br>
     *  RXCIE1     7 <br>
     */
    @NativeCVariable8
    private static volatile int UCSR1B;
    private static final int TXB81      = 0;
    private static final int RXB81      = 1;
    private static final int UCSZ12     = 2;
    private static final int TXEN1      = 3;
    private static final int RXEN1      = 4;
    private static final int UDRIE1     = 5;
    private static final int TXCIE1     = 6;
    private static final int RXCIE1     = 7;

    /** UCSR1A     MEM Addr=0xC8 Bits=8 <br>
     *  MPCM1      0 <br>
     *  U2X1       1 <br>
     *  UPE1       2 <br>
     *  DOR1       3 <br>
     *  FE1        4 <br>
     *  UDRE1      5 <br>
     *  TXC1       6 <br>
     *  RXC1       7 <br>
     */
    @NativeCVariable8
    private static volatile int UCSR1A;
    private static final int MPCM1      = 0;
    private static final int U2X1       = 1;
    private static final int UPE1       = 2;
    private static final int DOR1       = 3;
    private static final int FE1        = 4;
    private static final int UDRE1      = 5;
    private static final int TXC1       = 6;
    private static final int RXC1       = 7;

    private static final int BAUD_RATE = 57600;
    private static final long F_CPU = 16000000;
    
    private static void init() {
        // set baud rate
        final short t = (short) (((F_CPU >> 4) + (BAUD_RATE >>> 1)) / BAUD_RATE - 1);
        UBRR1H = (t >>> 8);
        UBRR1L = t;
        UCSR1B = (1 << RXEN1) | (1 << TXEN1);
    }

    private static void write(int b) {
        while ((UCSR1A & (1 << UDRE1)) == 0)
            ;

        UDR1 = b;
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
        while(true) {
            println("Hello World");
        }
    }
}
