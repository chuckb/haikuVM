package haiku.bcm;

import haiku.bcm.MailboxConstants.*;
import haiku.vm.NativeCHeader;
import haiku.vm.NativeCVariable32;
import haiku.vm.NativeCFunction;

@NativeCHeader(cImpl = "#include <platforms/pi/rpi-mailbox-interface.h>")

/**
 * The class represents the data retrieved from the Mailbox API interface
 * for a given {@link MAILBOX_TAG}.
 */
public class MailboxProperty {
  private final int addr;

  /**
   * Constructor receiving the address of the internal C rpi_mailbox_property_t struct.
   * This is obtained by a call to the native function RPI_PropertyGet. A factory
   * method can be called {@link Mailbox.PropertyGet()} to fetch an instance of this
   * class.
   */
  public MailboxProperty(int addr) {
    this.addr = addr;
  }

  @NativeCFunction(cImpl = "return ((rpi_mailbox_property_t *)arg1)->tag;")
  private native int _getTag(int addr);

  /**
   * Get the {@link MAILBOX_TAG} for the property retrieved.
   * @return The MAILBOX_TAG
   */
  public MAILBOX_TAG getTag() {
    return MAILBOX_TAG.values()[_getTag(this.addr)];
  }

  @NativeCFunction(cImpl = "return ((rpi_mailbox_property_t *)arg1)->byte_length;")
  private native int _getByteLength(int addr);

  /**
   * Get the byte length of data buffer returned for properties that return data.
   * @return The byte length
   */
  public int getByteLength() {
    return _getByteLength(this.addr);
  }

  @NativeCFunction(cImpl = "return ((rpi_mailbox_property_t *)arg1)->data.value_32;")
  private native int _getValue32(int addr);

  /**
   * Get the data value for properties that simply fetch an int.
   *
   * @return The value fetched for the property
   */
  public int getValue32() {
    return _getValue32(this.addr);
  }

  @NativeCFunction(cImpl = "return ((rpi_mailbox_property_t *)arg1)->data.buffer_8[arg2];")
  private native byte _getBuffer8(int addr, int offset);

  /**
   * Get the data values for properties that fetch a byte array.
   *
   * @param offset The offset into the byte array of the byte to fetch.
   * @return The value fetched at the offset.
   */
  public byte getBuffer8(int offset) {
    return _getBuffer8(this.addr, offset);
  }

  @NativeCFunction(cImpl = "return ((rpi_mailbox_property_t *)arg1)->data.buffer_32[arg2];")
  private native int _getBuffer32(int addr, int offset);
  
  /**
   * Get the data values for properties that fetch a int array.
   *
   * @param offset The offset into the int array of the int to fetch.
   * @return The value fetched at the offset.
   */
  public int getBuffer32(int offset) {
    return _getBuffer32(this.addr, offset);
  }
}
