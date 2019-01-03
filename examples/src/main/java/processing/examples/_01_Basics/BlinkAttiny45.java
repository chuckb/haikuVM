package processing.examples._01_Basics;

import static processing.hardware.arduino.cores.arduino.Arduino.*;

/**
 * For programming the Attiny with the Arduino IDE I followed this tutorial:
 * http://highlowtech.org/?p=1695
 * 
 * Haikufy like this:
 *   cd C:\arduino-1.0.5\libraries
 *   C:\haikuVM\bin\haiku -v --Config arduinoIDE4Attiny C:\haikuVM\examples\src\main\java\processing\examples\_01_Basics\BlinkAttiny45.java
 * Then use your Arduino IDE to upload the generated sketch.
 * 
 * With UNIX haikufy like this:
 *   cd /home/bob/arduino-1.0.5/libraries
 *   /home/bob/haikuVM/bin/haiku -v --Config arduinoIDE4Attiny /home/bob/haikuVM/examples/src/main/java/processing/examples/_01_Basics/BlinkAttiny45.java
 * Then use your Arduino IDE to upload the generated sketch.
 */
public class BlinkAttiny45 {
    // The status LED of the Attiny45 will blink.
    
    static final int ledPin = 0;            // LED connected to digital pin 0

    public static void setup() {
        pinMode(ledPin, OUTPUT);        // sets the digital pin as output
    }

    static int toggle=0;
    
    public static void loop()           // run over and over again
    {
        //delay(1000);                    // waits for a second but it's to expensive for Haikuvm on attiny45. So I used the following loop for timing:
        for (int i = 0; i < 200; i++) {
            digitalWrite(ledPin, toggle);      // sets the LED on or off
        }
        toggle^=1;
    }
}
