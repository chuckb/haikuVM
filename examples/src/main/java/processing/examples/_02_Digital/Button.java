package processing.examples._02_Digital;

import static processing.hardware.arduino.cores.arduino.Arduino.*;
import static processing.examples._02_Digital.pitches.*;

/**
 * 
 * cd C:\arduino-1.0.5\libraries
 * C:\haikuVM\bin\haiku -v --Config arduinoIDE C:\haikuVM\examples\src\main\java\processing\examples\_02_Digital\Button.java
 * 
 */
public class Button {
    /*
     * Button
     * 
     * Turns on and off a light emitting diode(LED) connected to digital pin 13,
     * when pressing a pushbutton attached to pin 2.
     * 
     * 
     * The circuit: LED attached from pin 13 to ground pushbutton attached to
     * pin 2 from +5V 10K resistor attached to pin 2 from ground
     * 
     * Note: on most Arduinos there is already an LED on the board attached to
     * pin 13.
     * 
     * 
     * created 2005 by DojoDave <http://www.0j0.org> modified 30 Aug 2011 by Tom
     * Igoe
     * 
     * This example code is in the public domain.
     * 
     * http://www.arduino.cc/en/Tutorial/Button
     */

    // constants won't change. They're used here to
    // set pin numbers:
    static final int buttonPin = 2; // the number of the pushbutton pin
    static final int ledPin = 13; // the number of the LED pin

    // variables will change:
    static int buttonState = 0; // variable for reading the pushbutton status

    public static void setup() {
        // initialize the LED pin as an output:
        pinMode(ledPin, OUTPUT);
        // initialize the pushbutton pin as an input:
        pinMode(buttonPin, INPUT);
    }

    public static void loop() {
        // read the state of the pushbutton value:
        buttonState = digitalRead(buttonPin);

        // check if the pushbutton is pressed.
        // if it is, the buttonState is HIGH:
        if (buttonState == HIGH) {
            // turn LED on:
            digitalWrite(ledPin, HIGH);
        } else {
            // turn LED off:
            digitalWrite(ledPin, LOW);
        }
    }
}
