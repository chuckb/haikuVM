package haiku.avr;

public class ATmega328p extends AVRConstants {
    // ARDUINO __AVR_ATmega328P__
    public static final boolean __AVR_ATmega328P__ = true;

    public static final int BAUD_RATE = 57600;
    public static final long F_CPU = 16000000;

    public static final int TXB80 = 0;
    public static final int RXB80 = 1;
    public static final int UCSZ02 = 2;
    public static final int TXEN0 = 3;
    public static final int RXEN0 = 4;
    public static final int UDRIE0 = 5;
    public static final int TXCIE0 = 6;
    public static final int RXCIE0 = 7;

    public static final int WGM01 = 1;
    public static final int WGM00 = 0;
    public static final int CS01 = 1;
    public static final int CS00 = 0;
    public static final int TOIE0 = 0;
    public static final int CS10 = 0;
    public static final int CS22 = 2;
    public static final int ADPS0 = 0;

    public static final int REFS0 = 6;
    public static final int ADSC = 6;
    public static final int ADIF = 4;
    public static final int UDRE0 = 5;

    public static final int RXC0 = 7;

    public static final int COM1A0 = 0x06;
    public static final int COM1A1 = 0x07;
    public static final int COM1B0 = 0x04;
    public static final int COM1B1 = 0x05;
    public static final int CS11 = 0x01;
    public static final int OCIE1A = 0x01;
    public static final int OCIE1B = 0x02;
    public static final int TOIE1 = 0x00;
    public static final int WGM11 = 0x01;
    public static final int WGM12 = 0x03;
    public static final int WGM13 = 0x04;
    public static final int TWINT = 0x07;
    public static final int TWEN = 0x02;
    public static final int TWSTA = 0x05;
    public static final int TWEA = 0x06;
    public static final int TWSTO = 0x04;
    public static final int PORTC4 = 0x04;
    public static final int PORTC5 = 0x05;
}
