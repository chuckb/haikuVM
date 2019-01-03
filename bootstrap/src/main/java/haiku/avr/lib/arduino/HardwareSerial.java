package haiku.avr.lib.arduino;

import static haiku.avr.ATmega328p.*;
import static haiku.avr.AVRConstants.UCSR0A;
import static haiku.avr.AVRConstants.UDR0;

public class HardwareSerial extends Print {
    // HardwareSerial(ring_buffer *rx_buffer,
    // volatile uint8_t *ubrrh, volatile uint8_t *ubrrl,
    // volatile uint8_t *ucsra, volatile uint8_t *ucsrb,
    // volatile uint8_t *udr,
    // uint8_t rxen, uint8_t txen, uint8_t rxcie, uint8_t udre);

    // public void flush();

    public int available() {
        if (__AVR_ATmega8__) {
            if ((UCSRA & (1 << RXC)) != 0)
                return 1;
        } else {
            if ((UCSR0A & (1 << RXC0)) != 0)
                return 1;
        }
        return 0;
    }

    public int read() {
        if (available() == 0)
            return -1;

        if (__AVR_ATmega8__) {
            return UDR;
        } else {
            return UDR0;
        }
    }
    
    public byte write(byte c) {
        if (__AVR_ATmega8__) {
    		while ((UCSRA & 0x20) == 0);			// wait for empty transmit buffer
    		UDR =c;
        } else {
    		while ( (UCSR0A & (1 << UDRE0)) == 0 );
            UDR0=c;
        }        
        return 1;
    }


}
