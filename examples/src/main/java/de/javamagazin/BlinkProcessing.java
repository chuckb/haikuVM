package de.javamagazin;

import static haiku.arduino.api.Arduino.*;

/**
 * The status LED of the Duemilanove will blink. 
 * 
 * For this example to run, you first need to copy file 'ProcessingCLIB.cpp' into your 'yourProject' directory.
 * File 'ProcessingCLIB.cpp' can be found in 'C:\haikuVM\myCProject\tutorials'. 
 * 
 * cd C:\yourProject
 * C:\haikuVM\bin\haiku -v --Config duemilanove.ProcessingCLIB C:\haikuVM\examples\src\main\java\de\javamagazin\BlinkProcessing.java
 * 
 * @author genom2
 */
public class BlinkProcessing {
    static byte ledPin = 13;            // LED connected to digital pin 13

    public static void loop()           // run over and over again
    {
        digitalWrite(ledPin, HIGH);     // sets the LED on
        delay(1000);                    // waits for a second
        digitalWrite(ledPin, LOW);      // sets the LED off
        delay(1000);                    // waits for a second
    }

    public static void setup() {
        pinMode(ledPin, OUTPUT);        // sets the digital pin as output
    }
}
