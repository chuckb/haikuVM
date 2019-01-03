package duemilanove.tutorial;

import static haiku.avr.lib.arduino.WProgram.*;
/**
avrdude: 9734 bytes of flash verified

ABCDEFGHIJKLMNOPQRSTUVWXY
ABCDEFGHIJKLMNOPQRSTUVWXY
ABCDEFGHIJKLMNOPQRSTUVWXY
ABCDEFGHIJKLMNOPQRSTUVWXY
ABCDEFGHIJKLMNOPQRSTUVWXY
ABCDEFGHIJKLMNOPQRSTUVWXY
ABCDEFGHIJKLMNOPQRSTUVWXY
ABCDEFGHIJKLMNOPQRSTUVWXY
ABCDEFGHIJKLMNOPQRSTUVWXY
ABCDEFGHIJKLMNOPQRSTUVWXY
5.9090909 chars/msec
 */
public class UartOut {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		init();
	    Serial.begin(57600);

	    while(true) {
	    	long t0=millis();
	    	for(int i=0; i<10; i++) {
	            for(char c='A'; c<'Z'; c++) {
	            	Serial.print(c);
	            }
	        	Serial.println();
	    	}
	    	Serial.print(10.0*('Z'-'A'+1)/(millis()-t0));
	    	Serial.println(" chars/msec");
	    }
	}

}
