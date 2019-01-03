package arduino.tutorial;

import static haiku.avr.lib.arduino.WProgram.*;

/**
 * The status LED of the ARDUINO will blink. 
 * 
 * mkdir myProject 
 * cd myProject
 * C:\haikuVM\bin\haikuc                                     C:\haikuVM\examples\src\main\java\arduino\tutorial\Blink.java
 * C:\haikuVM\bin\haikulink -v --Config arduino -o Blink.hex C:\haikuVM\examples\src\main\java\arduino\tutorial\Blink
 * C:\haikuVM\bin\haikuupload                      Blink.hex
 * 
 * @author genom2
 */
public class Blink {
    static byte ledPin = 13;            // LED connected to digital pin 13

    public static void loop()           // run over and over again
    {
        digitalWrite(ledPin, HIGH);     // sets the LED on
        delay(1000);                    // waits for a second
        digitalWrite(ledPin, LOW);      // sets the LED off
        delay(1000);                    // waits for a second
    }

	public static void main(String[] args) {
        pinMode(ledPin, OUTPUT);        // sets the digital pin as output
        while(true) {
        	loop();
        }
    }
}
