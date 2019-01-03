package haikuvm.bench;

import static haiku.avr.lib.arduino.WProgram.*;
/**
 */
public class SerialPrint {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		init();
	    Serial.begin(57600);
        Serial.println("Test Serial.print*");
        Serial.println(12);
        Serial.println(123456L);
        Serial.println('!');
        Serial.println(123.45);
	}

}
