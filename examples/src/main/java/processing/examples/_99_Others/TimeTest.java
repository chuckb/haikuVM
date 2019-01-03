package processing.examples._99_Others;


import static processing.hardware.arduino.cores.arduino.Arduino.*;
/**
 */
public class TimeTest {
	public static void setup() {
	    Serial.begin(57600);
	}

	public static void loop() {
    	Serial.println(System.currentTimeMillis());
    	Thread.nap(1000);
	}
}
