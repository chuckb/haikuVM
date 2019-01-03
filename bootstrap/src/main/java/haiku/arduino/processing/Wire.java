package haiku.arduino.processing;

/**
 * This library allows you to communicate with I2C / TWI devices.
 */
public class Wire {
	static WireImpl impl = new WireImpl();

	/**
	 * Initiate the Wire library and join the I2C bus as a master. This should
	 * normally be called only once.
	 */
	public static void begin() {
		impl.begin();
	}

	/**
	 * Begin a transmission to the I2C slave device with the given address.
	 * Subsequently, queue bytes for transmission with the
	 * {@link Wire#write(byte)} function and transmit them by calling
	 * {@link Wire#endTransmission()}.
	 * 
	 * @param address
	 *            the 7-bit address of the device to transmit to
	 */
	public static void beginTransmission(byte address) {
		impl.beginTransmission(address);
	}

	/**
	 * Ends a transmission to a slave device that was begun by
	 * {@link Wire#endTransmission()} and transmits the bytes that were queued
	 * by {@link Wire#write(byte)}.
	 * 
	 * @return indicates the status of the transmission:
	 *         0:success
	 *         1:data too long to fit in transmit buffer
	 *         2:received NACK on transmit of address
	 *         3:received NACK on transmit of data
	 *         4:other error
	 */
	public static byte endTransmission() {
		return impl.endTransmission();
	}

	/**
	 * Writes data from a slave device in response to a request from a master,
	 * or queues bytes for transmission from a master to slave device
	 * (in-between calls to {@link Wire#beginTransmission(byte)} and
	 * {@link Wire#endTransmission()}).
	 * 
	 * @return the number of bytes written, though reading that number is
	 *         optional
	 */
	public static byte write(byte value) {
		return impl.write(value);
	}

	/**
	 * Used by the master to request bytes from a slave device. The bytes may
	 * then be retrieved with the {@link Wire#read()} functions.
	 * 
	 * @param address
	 *            the 7-bit address of the device to request bytes from
	 * @param quantity
	 *            the number of bytes to request
	 * @return the number of bytes returned from the slave device
	 */
	public static byte requestFrom(byte address, byte quantity) {
		return impl.requestFrom(address, quantity);
	}

	/**
	 * Reads a byte that was transmitted from a slave device to a master after a
	 * call to {@link Wire#requestFrom(byte, byte)} or was transmitted from a
	 * master to a slave.
	 * 
	 * @return The next byte received
	 */
	public static byte read() {
		return (byte) impl.read();
	}
}
