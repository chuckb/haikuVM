package arduino.tutorial;

import static processing.hardware.arduino.cores.arduino.Arduino.*;


/**
 */
public class HelloWorldProcessing {

    public static void setup() {
        // Open serial communications and wait for port to open:
        Serial.begin(57600);
        while (!Serial.isOpen()) {
            ; // wait for serial port to connect. Needed for Leonardo only
        }
    }
    
    public static void loop() {
        for (int i = 0; i < 10; i++) {
            Serial.println("Hello World "+i+", "+sq(i)+", "+random(1000));
        }
    }
}
