package processing.examples._01_Basics;

import static processing.hardware.arduino.cores.arduino.Arduino.*;

/**
 * 
 * cd C:\arduino-1.0.5\libraries
 * C:\haikuVM\bin\haiku -v --Config arduinoIDE C:\haikuVM\examples\src\main\java\processing\examples\_01_Basics\AnalogReadSerial.java
 * 
 */
public class AnalogReadSerial {
    /*
     * AnalogReadSerial Reads an analog input on pin 0, prints the result to the
     * serial monitor. Attach the center pin of a potentiometer to pin A0, and
     * the outside pins to +5V and ground.
     * 
     * This example code is in the public domain.
     */

    // the setup routine runs once when you press reset:
    public static void setup() {
        // initialize serial communication at 9600 bits per second:
        Serial.begin(9600);
    }

    // the loop routine runs over and over again forever:
    public static void loop() {
        // read the input on analog pin 0:
        int sensorValue = analogRead(A0);
        // print out the value you read:
        Serial.println(sensorValue);
        delay(1); // delay in between reads for stability
    }
}
