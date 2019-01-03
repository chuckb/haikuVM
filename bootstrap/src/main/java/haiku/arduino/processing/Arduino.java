package haiku.arduino.processing;

/**
 * Main Arduino functions for Digital I/O, Analog I/O, Advanced I/O.
 * You can emulate the Arduino using {@link ArduinoConsoleEmulator}.
 * 
 * @author rproell
 * 
 */
public class Arduino {
	static ArduinoImpl impl = new ArduinoImpl();

	private Arduino() {
	}

	/**
	 * The meaning of {@link Arduino#LOW} also has a different meaning depending
	 * on whether a pin is set to {@link Arduino#INPUT} or
	 * {@link Arduino#OUTPUT}. When a pin
	 * is configured as an {@link Arduino#INPUT} with {@link Arduino#pinMode},
	 * and read with {@link Arduino#digitalRead}, the Arduino (Atmega) will
	 * report {@link Arduino#LOW} if:
	 * 
	 * a voltage less than 3 volts is present at the pin (5V boards);
	 * a voltage less than 2 volts is present at the pin (3.3V boards);
	 * 
	 * When a pin is configured to {@link Arduino#OUTPUT} with
	 * {@link Arduino#pinMode}, and set to {@link Arduino#LOW} with
	 * {@link Arduino#digitalWrite} , the pin is at 0 volts (both 5V and 3.3V
	 * boards). In this
	 * state it can sink current, e.g. light an LED that is connected through a
	 * series resistor to +5 volts (or +3.3 volts).
	 */
	public static final byte LOW = 0x0;
	/**
	 * The meaning of {@link Arduino#HIGH} (in reference to a pin) is somewhat
	 * different depending on whether a pin is set to an {@link Arduino#INPUT}
	 * or {@link Arduino#OUTPUT}. When a pin is configured as an
	 * {@link Arduino#INPUT} with {@link Arduino#pinMode}, and
	 * read with {@link Arduino#digitalRead}, the Arduino (Atmega) will report
	 * {@link Arduino#HIGH} if:
	 * 
	 * a voltage greater than 3 volts is present at the pin (5V boards);
	 * a voltage greater than 2 volts is present at the pin (3.3V boards);
	 * 
	 * A pin may also be configured as an {@link Arduino#INPUT} with
	 * {@link Arduino#pinMode}, and subsequently
	 * made {@link Arduino#HIGH} with {@link Arduino#digitalWrite}. This will
	 * enable the internal 20K pullup
	 * resistors, which will pull up the input pin to a {@link Arduino#HIGH}
	 * reading unless it is pulled {@link Arduino#LOW} by external circuitry.
	 * This is how {@link Arduino#INPUT_PULLUP} works and is described below in
	 * more detail.
	 * 
	 * When a pin is configured to {@link Arduino#OUTPUT} with
	 * {@link Arduino#pinMode}, and set to {@link Arduino#HIGH} with
	 * {@link Arduino#digitalWrite}, the pin is at:
	 * 
	 * 5 volts (5V boards);
	 * 3.3 volts (3.3V boards);
	 * 
	 * In this state it can source current, e.g. light an LED that is connected
	 * through a series resistor to ground.
	 */
	public static final byte HIGH = 0x1;

	/**
	 * Arduino (Atmega) pins configured as {@link Arduino#INPUT} with
	 * {@link Arduino#pinMode} are said to be
	 * in a high-impedance state. Pins configured as {@link Arduino#INPUT} make
	 * extremely small
	 * demands on the circuit that they are sampling, equivalent to a series
	 * resistor of 100 Megohms in front of the pin. This makes them useful for
	 * reading a sensor.
	 * 
	 * If you have your pin configured as an {@link Arduino#INPUT}, and are
	 * reading a switch,
	 * when the switch is in the open state the input pin will be "floating",
	 * resulting in unpredictable results. In order to assure a proper reading
	 * when the switch is open, a pull-up or pull-down resistor must be used.
	 * The purpose of this resistor is to pull the pin to a known state when the
	 * switch is open. A 10 K ohm resistor is usually chosen, as it is a low
	 * enough value to reliably prevent a floating input, and at the same time a
	 * high enough value to not not draw too much current when the switch is
	 * closed. See the Digital Read Serial tutorial for more information.
	 * 
	 * If a pull-down resistor is used, the input pin will be
	 * {@link Arduino#LOW} when the
	 * switch is open and {@link Arduino#HIGH} when the switch is closed.
	 * 
	 * If a pull-up resistor is used, the input pin will be {@link Arduino#HIGH}
	 * when the switch
	 * is open and {@link Arduino#LOW} when the switch is closed.
	 */
	public static final byte INPUT = 0x0;
	/**
	 * Pins configured as {@link Arduino#OUTPUT} with {@link Arduino#pinMode}
	 * are said to be in a
	 * low-impedance state. This means that they can provide a substantial
	 * amount of current to other circuits. Atmega pins can source (provide
	 * current) or sink (absorb current) up to 40 mA (milliamps) of current to
	 * other devices/circuits. This makes them useful for powering LEDs because
	 * LEDs typically use less than 40 mA. Loads greater than 40 mA (e.g.
	 * motors) will require a transistor or other interface circuitry.
	 * 
	 * Pins configured as outputs can be damaged or destroyed if they are
	 * connected to either the ground or positive power rails.
	 */
	public static final byte OUTPUT = 0x1;
	/**
	 * The Atmega microcontroller on the Arduino has internal pull-up resistors
	 * (resistors that connect to power internally) that you can access. If you
	 * prefer to use these instead of external pull-up resistors, you can use
	 * the INPUT_PULLUP argument in {@link Arduino#pinMode}.
	 * 
	 * See the Input Pullup Serial tutorial for an example of this in use.
	 * 
	 * Pins configured as inputs with either {@link Arduino#INPUT} or
	 * INPUT_PULLUP can be
	 * damaged or destroyed if they are connected to voltages below ground
	 * (negative voltages) or above the positive power rail (5V or 3V).
	 */
	public static final byte INPUT_PULLUP = 0x2;

	/**
	 * Most Arduino boards have a pin connected to an on-board LED in series
	 * with a resistor. The constant LED_BUILTIN is the number of the pin to
	 * which the on-board LED is connected. Most boards have this LED connected
	 * to digital pin 13.
	 */
	public static final byte LED_BUILTIN = 13;

	/**
	 * Configures the specified pin to behave either as an input or an output.
	 * See the description of digital pins for details on the functionality of
	 * the pins.
	 * As of Arduino 1.0.1, it is possible to enable the internal pullup
	 * resistors with the mode {@link Arduino#INPUT_PULLUP}. Additionally, the
	 * {@link Arduino#INPUT} mode
	 * explicitly disables the internal pullups.
	 * 
	 * @param pin
	 *            the number of the pin whose mode you wish to set
	 * @param mode
	 *            {@link Arduino#INPUT}, {@link Arduino#OUTPUT}, or
	 *            {@link Arduino#INPUT_PULLUP}
	 */
	public static void pinMode(byte pin, byte mode) {
		impl.pinMode(pin, mode);
	}

	/**
	 * Write a {@link Arduino#HIGH} or a {@link Arduino#LOW} value to a digital
	 * pin.
	 * 
	 * If the pin has been configured as an {@link Arduino#OUTPUT} with
	 * {@link Arduino#pinMode}, its voltage
	 * will be set to the corresponding value: 5V (or 3.3V on 3.3V boards) for
	 * {@link Arduino#HIGH}, 0V (ground) for {@link Arduino#LOW}.
	 * 
	 * If the pin is configured as an {@link Arduino#INPUT},
	 * {@link Arduino#digitalWrite} will
	 * enable ({@link Arduino#HIGH})
	 * or disable ({@link Arduino#LOW}) the internal pullup on the input pin. It
	 * is recommended
	 * to set the {@link Arduino#pinMode} to {@link Arduino#INPUT_PULLUP} to
	 * enable the
	 * internal pull-up
	 * resistor. See the digital pins tutorial for more information.
	 * 
	 * NOTE: If you do not set the {@link Arduino#pinMode} to
	 * {@link Arduino#OUTPUT}, and
	 * connect an LED to a
	 * pin, when calling digitalWrite({@link Arduino#HIGH}), the LED may appear
	 * dim. Without
	 * explicitly setting {@link Arduino#pinMode}, {@link Arduino#digitalWrite}
	 * will have enabled the
	 * internal pull-up resistor, which acts like a large current-limiting
	 * resistor.
	 * 
	 * @param pin
	 *            the number of the digital pin you want to write
	 * @param value
	 *            {@link Arduino#LOW} or {@link Arduino#HIGH}
	 */
	public static void digitalWrite(byte pin, byte value) {
		impl.digitalWrite(pin, value);
	}

	/**
	 * Reads the value from a specified digital pin, either {@link Arduino#HIGH}
	 * or {@link Arduino#LOW}.
	 * 
	 * @param pin
	 *            the number of the digital pin you want to read
	 * @return {@link Arduino#LOW} or {@link Arduino#HIGH}
	 */
	public static byte digitalRead(byte pin) {
		return impl.digitalRead(pin);
	}

	// TODO Analog I/O
	// TODO Advanced I/O

	/**
	 * Returns the number of milliseconds since the Arduino board began running
	 * the current program. This number will overflow (go back to zero)
	 * 
	 * @return Number of milliseconds since the program started
	 */
	public static int millis() {
		return impl.millis();
	}

	/**
	 * Pauses the program for the amount of time (in miliseconds) specified as
	 * parameter. (There are 1000 milliseconds in a second.)
	 * 
	 * @param ms
	 *            the number of milliseconds to pause
	 */
	public static void delay(int ms) {
		impl.delay(ms);
	}
}
