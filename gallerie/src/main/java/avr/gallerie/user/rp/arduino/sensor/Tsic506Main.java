package avr.gallerie.user.rp.arduino.sensor;

import static haiku.arduino.processing.Arduino.HIGH;
import static haiku.arduino.processing.Arduino.LED_BUILTIN;
import static haiku.arduino.processing.Arduino.LOW;
import static haiku.arduino.processing.Arduino.OUTPUT;
import static haiku.arduino.processing.Arduino.digitalWrite;
import static haiku.arduino.processing.Arduino.pinMode;
import haiku.arduino.processing.Serial;

/**
 * cd C:\arduino-1.6.4\libraries
 * C:\haikuVM\bin\haiku -v --Config arduinoIDEUpload --Config:MicroKernel=haiku.avr.lib.arduino.HaikuMicroKernel4ArduinoIDEWithMain C:\haikuVM\gallerie\src\main\java\avr\gallerie/user\rp\arduino\sensor\Tsic506Main.java
 * 
 * WARNING: use console with 9600 baud
 * 
 * 
 * @author rp
 *
 */
public class Tsic506Main {
	private static final byte DATA_PIN = (byte) 10;
	private static final byte VCC_PIN = (byte) 11;

	public static void main(String[] args) throws Exception {
		pinMode(LED_BUILTIN, OUTPUT);

		Thread.sleep(2000);
		digitalWrite(LED_BUILTIN, HIGH);
		Serial.begin(9600);
		Thread.sleep(2000);
		digitalWrite(LED_BUILTIN, LOW);
		Serial.out.println("start");

		Tsic506 sensor = new Tsic506(DATA_PIN, VCC_PIN);
		int data;
		while (true) {
			data = sensor.getTemperatur();
			Serial.out.print("data: ");
			Serial.out.println(data);
			Thread.sleep(500);
		}
	}

}
