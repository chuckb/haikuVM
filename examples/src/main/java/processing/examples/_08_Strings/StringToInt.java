package processing.examples._08_Strings;

import static processing.hardware.arduino.cores.arduino.Arduino.*;

/**
 * String to Integer conversion
 * 
 * Reads a serial input string until it sees a newline, then converts the
 * string to a number if the characters are digits.
 * 
 * The circuit: No external components needed.
 * 
 * created 29 Nov 2010 by Tom Igoe
 * 
 * This example code is in the public domain.
 */
public class StringToInt {

    static String inString = ""; // string to hold input

    public static void setup() {
        // Open serial communications and wait for port to open:
        Serial.begin(9600);
        while (!Serial.isOpen()) {
            ; // wait for serial port to connect. Needed for Leonardo only
        }

        // send an intro:
        Serial.println("\n\nString parseLong():");
        Serial.println();
    }

    public static void loop() {
        // Read serial input:
        while (Serial.available() > 0) {
            int inChar = Serial.read();
            if (Character.isDigit((char) inChar)) {
                // convert the incoming byte to a char
                // and add it to the string:
                inString += (char) inChar;
            }
            // if you get a newline, print the string,
            // then the string's value:
            if (inChar == '\n') {
                Serial.print("Value:");
                Serial.println(Long.parseLong(inString));
                Serial.print("String: ");
                Serial.println(inString);
                // clear the string for new input:
                inString = "";
            }
        }
    }
}
