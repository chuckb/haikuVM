package processing.examples._01_Basics;

import static processing.hardware.arduino.cores.arduino.Arduino.*;

/**
 * Haikufy like this:
 *   cd C:\arduino\libraries
 *   C:\haikuVM\bin\haiku -v --Config arduinoIDE C:\haikuVM\examples\src\main\java\processing\examples\_01_Basics\Blink.java
 * Then use your Arduino IDE to upload the generated sketch.
 * 
 * or, more convenient, haikufy by using the Arduino IDE infrastructure, but let it compile, verify and upload from HaikuVM:
 *   cd C:\arduino\libraries
 *   C:\haikuVM\bin\haiku -v --Config arduinoIDEUpload C:\haikuVM\examples\src\main\java\processing\examples\_01_Basics\Blink.java
 * No need to start your Arduino IDE anymore. It's already uploaded.
 * 
 * 
 * With UNIX haikufy like this:
 *   cd /home/bob/arduino-1.0.5/libraries
 *   /home/bob/haikuVM/bin/haiku -v --Config arduinoIDE /home/bob/haikuVM/examples/src/main/java/processing/examples/_01_Basics/Blink.java
 * Then use your Arduino IDE to upload the generated sketch.
 */
public class Blink {
    // The status LED of the ARDUINO will blink.
    
    static byte ledPin = 13;            // LED connected to digital pin 13

    public static void setup() {
        pinMode(ledPin, OUTPUT);        // sets the digital pin as output
    }

    public static void loop()           // run over and over again
    {
        digitalWrite(ledPin, HIGH);     // sets the LED on
        delay(1000);                    // waits for a second
        digitalWrite(ledPin, LOW);      // sets the LED off
        delay(1000);                    // waits for a second
    }
}
