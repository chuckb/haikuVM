package haiku.arduino.processing;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Used for communication between the Arduino board and a computer or other
 * devices. All Arduino boards have at least one serial port (also known as a
 * UART or USART): Serial. It communicates on digital pins 0 (RX) and 1 (TX) as
 * well as with the computer via USB. Thus, if you use these functions, you
 * cannot also use pins 0 and 1 for digital input or output.
 * 
 * You can use the Arduino environment's built-in serial monitor to communicate
 * with an Arduino board. Click the serial monitor button in the toolbar and
 * select the same baud rate used in the call to {@link Serial#begin}.
 */
public class Serial {
	static SerialImpl impl = new SerialImpl();

	/**
	 * Print to serial output.
	 */
	public static final PrintStream out = new PrintStream(new SerialOutputStream());

	//TODO InputStream in = new ...

	/**
	 * Indicates if the specified Serial port is ready.
	 * 
	 * On the Leonardo, if (Serial) indicates wether or not the USB CDC serial
	 * connection is open. For all other instances, including if (Serial1) on
	 * the Leonardo, this will always returns true.
	 * 
	 * This was introduced in Arduino 1.0.1.
	 */
	public static boolean isOpen() {
		return impl.isOpen();
	}

	/**
	 * Get the number of bytes (characters) available for reading from the
	 * serial port. This is data that's already arrived and stored in the serial
	 * receive buffer (which holds 64 bytes).
	 * 
	 * @return the number of bytes available to read
	 */
	public static short available() {
		return impl.available();
	}

	/**
	 * Sets the data rate in bits per second (baud) for serial data
	 * transmission. For communicating with the computer, use one of these
	 * rates: 300, 600, 1200, 2400, 4800, 9600, 14400, 19200, 28800, 38400,
	 * 57600, or 115200. You can, however, specify other rates - for example, to
	 * communicate over pins 0 and 1 with a component that requires a particular
	 * baud rate.
	 * 
	 * @param speed
	 *            in bits per second (baud)
	 */
	public static void begin(int speed) {
		impl.begin(speed);
	}

	/**
	 * Disables serial communication, allowing the RX and TX pins to be used for
	 * general input and output. To re-enable serial communication, call
	 * {@link Serial#begin}.
	 */
	public static void end() {
		impl.end();
	}

	/**
	 * Reads incoming serial data.
	 * 
	 * @return the first byte of incoming serial data available (or -1 if no
	 *         data is available)
	 */
	public static byte read() {
		return impl.read();
	}

	/**
	 * Writes binary data to the serial port.
	 * 
	 * @param value
	 *            a value to send as a single byte
	 */
	public static void write(byte value) {
		impl.write(value);
	}

	private static class SerialOutputStream extends OutputStream {

		@Override
		public void write(int value) throws IOException {
			Serial.write((byte) value);
		}
	}
}
