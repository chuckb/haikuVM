package processing.examples._01_Basics;

import static processing.hardware.arduino.cores.arduino.Arduino.*;

/**
 * 
 * cd C:\arduino-1.0.5\libraries
 * C:\haikuVM\bin\haiku -v --Config arduinoIDE C:\haikuVM\examples\src\main\java\processing\examples\_01_Basics\DigitalReadSerial.java
 * 
 */
public class DigitalReadSerial {
    /*
     * DigitalReadSerial Reads a digital input on pin 2, prints the result to
     * the serial monitor
     * 
     * This example code is in the public domain.
     */

    // digital pin 2 has a pushbutton attached to it. Give it a name:
    static int pushButton = 2;

    // the setup routine runs once when you press reset:
    public static void setup() {
        // initialize serial communication at 9600 bits per second:
        Serial.begin(9600);
        // make the pushbutton's pin an input:
        pinMode(pushButton, INPUT);
    }

    // the loop routine runs over and over again forever:
    public static void loop() {
        // read the input pin:
        int buttonState = digitalRead(pushButton);
        // print out the state of the button:
        Serial.println(buttonState);
        delay(1); // delay in between reads for stability
    }
}
