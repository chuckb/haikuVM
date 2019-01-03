package avr.gallerie.user.glockenspiel;

//import static haiku.avr.lib.arduino.WProgram.*;
import static haiku.avr.lib.arduino.WProgram.*;
//import static processing.hardware.arduino.cores.arduino.Arduino.*;
//import static processing.examples._02_Digital.pitches.*;
import static haiku.avr.ATmega328p.*;

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
 

	C:\haikuVM\myCProject> 
	
	C:\haikuVM\bin\haiku -v --Config arduino -o Blink.hex C:\
	haikuVM\examples\src\main\java\tar\glockenspiel\Blink.java
 
 
 */
public class Blink {
    static byte ledPin = 13;            // LED connected to digital pin 13
	static byte buttonPin = 5;			// Input
	static int buttonState = 0;
	
    public static void loop()           // run over and over again
    {
        // read the state of the pushbutton value:
        buttonState = digitalRead(buttonPin);

		Serial.println(buttonState);
        // check if the pushbutton is pressed.
        // if it is, the buttonState is HIGH:
        if (buttonState == HIGH) {
            // turn LED on:
            digitalWrite(ledPin, HIGH);
        } else {
            // turn LED off:
            digitalWrite(ledPin, LOW);
        }
		
//		digitalWrite(ledPin, HIGH);     // sets the LED on
//        delay(1000);                    // waits for a second
//       digitalWrite(ledPin, LOW);      // sets the LED off
//        delay(1000);                    // waits for a second
    }

	public static void main(String[] args) {
        pinMode(ledPin, OUTPUT);        // sets the digital pin as output
		pinMode(buttonPin, INPUT);			// sets the digital pin as input
        Serial.begin(9600);
		
		while(true) {
        	loop();
        }
    }
}
