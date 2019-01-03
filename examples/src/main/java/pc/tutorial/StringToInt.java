package pc.tutorial;

import java.io.IOException;

public class StringToInt {
    /*
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

    static String inString = ""; // string to hold input

    public static void main(String[] args)  throws IOException {
        // send an intro:
        System.out.println("\n\nString toInt():");
        System.out.println();

        while (true) {
            // Read serial input:
            while (System.in.available() > 0) {
                int inChar = System.in.read();
                if (Character.isDigit((char) inChar)) {
                    // convert the incoming byte to a char
                    // and add it to the string:
                    inString += (char) inChar;
                }
                // if you get a newline, print the string,
                // then the string's value:
                if (inChar == '\n' || inChar == '\r') {
                    System.out.print("String: ");
                    System.out.println(inString);
                    System.out.print("Value:");
                    System.out.println(Long.parseLong(inString));
                    // clear the string for new input:
                    inString = "";
                }
            }
        }
    }
}
