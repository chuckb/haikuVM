package arduino.tutorial;

import static haiku.avr.lib.arduino.WProgram.*;

/**
 */
public class HelloWorld {

    /**
     * @param args
     */
    public static void main(String[] args) {
        init();
        Serial.begin(57600);
        Serial.println("Hello World");
    }
}
