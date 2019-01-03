package leonardo.tutorial;

import static haiku.avr.AVRConstants.*;

public class BlinkSimple {
    public static void main(String[] args) {
    	//int LED = 1 << 5; DDRB|=LED; PORTB|=LED;  //Duemilanove
    	int LED = 1 << 7; DDRC|=LED; PORTC|=LED;  //Leonardo
    }
}
