package avr.gallerie.user.rp.arduino.sensor;

import static haiku.arduino.processing.Arduino.HIGH;
import static haiku.arduino.processing.Arduino.INPUT;
import static haiku.arduino.processing.Arduino.LOW;
import static haiku.arduino.processing.Arduino.OUTPUT;
import static haiku.arduino.processing.Arduino.digitalWrite;
import static haiku.arduino.processing.Arduino.pinMode;
import haiku.vm.NativeCHeader;
import haiku.vm.NativeCppFunction;

@NativeCHeader(cImpl = "#include <Tsic506.h>")
public class Tsic506 {
	private static final int RESOLUTION = 34;
	private static final int MIN_TEMPERATURE = -9782; // for exact calculation of 25°C

	private byte dataPin;
	private byte vccPin;

	public Tsic506(byte dataPin, byte vccPin) {
		this.dataPin = dataPin;
		this.vccPin = vccPin;
		initPins();
	}

	private void initPins() {
		pinMode(dataPin, INPUT);
		pinMode(vccPin, OUTPUT);
		digitalWrite(vccPin, LOW);
	}

	/**
	 * @return temperature in milli °C
	 */
	public int getTemperatur() {
		digitalWrite(vccPin, HIGH);
		int sensorData = readSensor(dataPin);
		digitalWrite(vccPin, LOW);
		return sensorData * RESOLUTION + MIN_TEMPERATURE;
	}

	@NativeCppFunction
	native short readSensor(byte dataPin);
}
