package processing.examples._99_Others;

import static processing.hardware.arduino.cores.arduino.Arduino.*;
/**
 */
public class Hello {
	public static void setup() {
	    Serial.begin(57600);
        while (!Serial.isOpen()) {
            ; // wait for serial port to connect. Needed for Leonardo only
        }
	}

	public static void loop() {
        Serial.println("Hello!");
        
        // do nothing while true:
        while (true)
            ;
 	}
}
