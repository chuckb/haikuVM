package processing.examples._08_Strings;

import static processing.hardware.arduino.cores.arduino.Arduino.*;

public class StringLengthTrim {
    /*
     * String length() and trim()
     * 
     * Examples of how to use length() and trim() in a String
     * 
     * created 27 July 2010 modified 2 Apr 2012 by Tom Igoe
     * 
     * http://arduino.cc/en/Tutorial/StringLengthTrim
     * 
     * This example code is in the public domain.
     */

    public static void setup() {
        // Open serial communications and wait for port to open:
        Serial.begin(9600);
        while (!Serial.isOpen()) {
            ; // wait for serial port to connect. Needed for Leonardo only
        }

        // send an intro:
        Serial.println("\n\nString  length() and trim():");
        Serial.println();
    }

    public static void loop() {
        // here's a String with empty spaces at the end (called white space):
        String stringOne = "Hello!       ";
        Serial.print(stringOne);
        Serial.print("<--- end of string. Length: ");
        Serial.println(stringOne.length());

        // trim the white space off the string:
        stringOne=stringOne.trim();
        Serial.print(stringOne);
        Serial.print("<--- end of trimmed string. Length: ");
        Serial.println(stringOne.length());

        // do nothing while true:
        while (true)
            ;
    }

}
