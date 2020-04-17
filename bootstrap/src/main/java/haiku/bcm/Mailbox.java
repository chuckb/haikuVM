package haiku.bcm;

import haiku.bcm.MailboxConstants.*;
import haiku.vm.NativeCHeader;
import haiku.vm.NativeCVariable32;
import haiku.vm.NativeCFunction;

@NativeCHeader(cImpl = "#include <platforms/pi/rpi-mailbox-interface.h>")

public class Mailbox {

  @NativeCFunction(cImpl = "RPI_Mailbox0Write((mailbox0_channel_t)arg1, arg2);")
  private static native void _Mailbox0Write( int mailbox0Channel, int value);

  /**
   * Write to mailbox 0.
   * @param mailbox0Channel  The mailbox channel to write to.
   * @param value            The value to write.
   */
  public static void Mailbox0Write(MAILBOX0_CHANNEL mailbox0Channel, int value) {
    _Mailbox0Write(mailbox0Channel.ordinal(), value);
  }

  @NativeCFunction(cImpl = "return RPI_Mailbox0Read((mailbox0_channel_t)arg1);")
  private static native int _Mailbox0Read(int mailbox0Channel);

  /**
   * Read from mailbox 0.
   * @param mailbox0Channel  The mailbox channel to read from.
   */
  public static int Mailbox0Read(MAILBOX0_CHANNEL mailbox0Channel) {
    return _Mailbox0Read(mailbox0Channel.ordinal());
  }

  @NativeCFunction(cImpl = "RPI_PropertyInit();")
  public static native void PropertyInit();

  @NativeCFunction(cImpl = "return RPI_PropertyProcess();")
  public static native int PropertyProcess();

  /**
   * Add a property tag to the current tag list.
   *
   * @param tag The tag to add.
   */
  @NativeCFunction(cImpl = "RPI_PropertyAddTag((rpi_mailbox_tag_t)arg1);")
  public static native void PropertyAddTag(MAILBOX_TAG tag);

  /**
   * Add a property tag to the current tag list. Send one data value for the tag.
   *
   * @param tag The tag to add.
   * @param va1 Data value to send to the tag.
   */
  @NativeCFunction(cImpl = "RPI_PropertyAddTag((rpi_mailbox_tag_t)arg1, arg2);")
  public static native void PropertyAddTag(MAILBOX_TAG tag, int va1);

  /**
   * Add a property tag to the current tag list. Send two data values for the tag.
   *
   * @param tag The tag to add.
   * @param va1 Data value to send to the tag.
   * @param va2 Data value to send to the tag.
   */
  @NativeCFunction(cImpl = "RPI_PropertyAddTag((rpi_mailbox_tag_t)arg1, arg2, arg3);")
  public static native void PropertyAddTag(MAILBOX_TAG tag, int va1, int va2);

  /**
   * Add a property tag to the current tag list. Send three data values for the tag.
   *
   * @param tag The tag to add.
   * @param va1 Data value to send to the tag.
   * @param va2 Data value to send to the tag.
   * @param va3 Data value to send to the tag.
   */
  @NativeCFunction(cImpl = "RPI_PropertyAddTag((rpi_mailbox_tag_t)arg1, arg2, arg3, arg4);")
  public static native void PropertyAddTag(MAILBOX_TAG tag, int va1, int va2, int va3);

  /**
   * Add a property tag to the current tag list. Send four data values for the tag.
   *
   * @param tag The tag to add.
   * @param va1 Data value to send to the tag.
   * @param va2 Data value to send to the tag.
   * @param va3 Data value to send to the tag.
   * @param va4 Data value to send to the tag.
   */
  @NativeCFunction(cImpl = "RPI_PropertyAddTag((rpi_mailbox_tag_t)arg1, arg2, arg3, arg4, arg5);")
  public static native void PropertyAddTag(MAILBOX_TAG tag, int va1, int va2, int va3, int va4);

  @NativeCFunction(cImpl = "return RPI_PropertyGet((rpi_mailbox_tag_t)arg1);")
  private static native int _PropertyGet(MAILBOX_TAG tag);

  /**
   * Given a tag, fetch the property information retrieved from the mailbox API call.
   *
   * @param tag The tag of the property to fetch.
   * @return    The MailboxProperty instance for the property tag fetched.
   */
  public static MailboxProperty PropertyGet(MAILBOX_TAG tag) {
    return new MailboxProperty(_PropertyGet(tag));
  }

  /**
   * Offset in bytes from Mailbox0 base of Read register.
   */
  @NativeCVariable32
  public static int o_Read;

  /**
   * Offset in bytes from Mailbox0 base of Poll register.
   */
  @NativeCVariable32
  public static int o_Poll;

  /**
   * Offset in bytes from Mailbox0 base of Sender register.
   */
  @NativeCVariable32
  public static int o_Sender;

  /**
   * Offset in bytes from Mailbox0 base of Status register.
   */
  @NativeCVariable32
  public static int o_Status;

  /**
   * Offset in bytes from Mailbox0 base of Configuration register.
   */
  @NativeCVariable32
  public static int o_Configuration;

  /**
   * Offset in bytes from Mailbox0 base of Write register.
   */
  @NativeCVariable32
  public static int o_Write;
}