package processing.examples._01_Basics;

import static processing.hardware.arduino.cores.arduino.Arduino.*;

/**
 * The status LED of the ARDUINO will blink. 
 * 
 * cd C:\arduino-1.0.5\libraries
 * C:\haikuVM\bin\haiku -v --Config arduinoIDE C:\haikuVM\examples\src\main\java\processing\examples\_01_Basics\Fade.java
 */
public class Fade {
    /*
     * Fade
     * 
     * This example shows how to fade an LED on pin 9 using the analogWrite()
     * function.
     * 
     * This example code is in the public domain.
     */

    static int led = 9; // the pin that the LED is attached to
    static int brightness = 0; // how bright the LED is
    static int fadeAmount = 5; // how many points to fade the LED by

    // the setup routine runs once when you press reset:
    public static void setup() {
        // declare pin 9 to be an output:
        pinMode(led, OUTPUT);
    }

    // the loop routine runs over and over again forever:
    public static void loop() {
        // set the brightness of pin 9:
        analogWrite(led, brightness);

        // change the brightness for next time through the loop:
        brightness = brightness + fadeAmount;

        // reverse the direction of the fading at the ends of the fade:
        if (brightness == 0 || brightness == 255) {
            fadeAmount = -fadeAmount;
        }
        // wait for 30 milliseconds to see the dimming effect
        delay(30);
    }
}
