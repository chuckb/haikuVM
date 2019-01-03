package arduino.tutorial;

import static haiku.avr.lib.arduino.WProgram.*;

public class BlinkWithThread extends Thread {
    static byte ledPin = 13;            // LED connected to digital pin 13

    public void run()                   // run over and over again
    {
        while (true) {
            digitalWrite(ledPin, HIGH); // sets the LED on
            delay(1000);                // waits for a second
            digitalWrite(ledPin, LOW);  // sets the LED off
            delay(1000);                // waits for a second
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        pinMode(ledPin, OUTPUT);        // sets the digital pin as output
        new BlinkWithThread().start();
    }
}

