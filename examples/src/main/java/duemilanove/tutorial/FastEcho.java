package duemilanove.tutorial;

import static haiku.avr.lib.arduino.WProgram.*;
import static haiku.avr.ATmega328p.*;

/**
 * 
 * call with:
 * C:\haikuVM\myCProject
 * C:\haikuVM\bin\haiku -v --Config duemilanove C:\haikuVM\examples\src\main\java\duemilanove\tutorial\Echo.java
 * 
ABCDEFGHIJKLMNOPQRSTUVWXYZ,ABCDEFGHIJKLMNOPQRSTUVWXYZ,ABCDEFGHIJKLMNOPQRSTUVWXYZ,ABCDEFGHIJKLMNOPQRSTUVWXYZ,ABCDEFGHIJKLMNOPQRSTUVWXYZ!
 */
public class FastEcho {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		init();
	    Serial.begin(57600);
        Serial.println("Please start typing");

	    for(int i=0; ;i++) {
	    	int c;
	    	// read
	       	while ((UCSR0A & (1 << RXC0)) == 0);
	       	c=UDR0;
	    	// write
	        while ((UCSR0A & (1 << UDRE0)) == 0);
	        UDR0=c;
	        while ((UCSR0A & (1 << UDRE0)) == 0);
	    }
	}

}
