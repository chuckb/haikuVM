package rpi.tutorial;

import haiku.bcm.ArmTimer;
import haiku.bcm.Interrupts;
import haiku.bcm.Mailbox;
import static haiku.bcm.MailboxConstants.*;
import haiku.bcm.MailboxProperty;
import haiku.bcm.lib.rpi.*;
import haiku.vm.NativeCBody;
import haiku.vm.NativeCFunction;

import java.lang.StringBuilder;

/**
 * Bare metal Java exercise program for Raspberry Pi.<p>
 * Run this with:<p>
 * <pre>
 * mkdir myProject
 * cd myProject
 * /Users/chuck_benedict/haikuVM/bin/haiku -v --Config (rpi0 | rpi1 | rpi2 | rpi3 | rpi3apbp | rpi4) /Users/chuck_benedict/haikuVM/examples/src/main/java/rpi/tutorial/PiExerciser.java
 * </pre>
 * <a href="https://github.com/chuckb/raspbootin/tree/master/raspbootin2">Bootloader</a> will need to be installed on Raspberry Pi SD card.
 */

//
// Interrupt service routine using Java decorator support included with HaikuMV.
// This decorator includes the following C source whichs overload the interrupt service routine
// defined in the platform support file <a href="file:../../../../../../haikuVM/src/platforms/platform_RPI.c">platform_RPI.c</a>
// The ARM timer, based on the setup code in the main Java app, will call this
// service handler when the scaled tick count elapses.
//
@NativeCBody(cImpl = "#include <platforms/pi/rpi-armtimer.h>\n" +
"volatile int lit = 0;\n" +
"extern \"C\" void __attribute__((interrupt(\"IRQ\"))) interrupt_vector(void) {\n" +
"    RPI_GetArmTimer()->IRQClear = 1;\n" +
"    if( lit )\n" +
"    {\n" +
"        lit = 0;\n" +
"    }\n" +
"    else\n" + 
"    {\n" +
"        lit = 1;\n" +
"    }\n" +
"}")

public class PiExerciser {
  /**
   * Get the external lit flag updated by the interrupt service routine
   * 
   * @return  0 or 1 toggeled every time the ISR fires
   */
  @NativeCFunction(cImpl = "return lit;")
  public static native int getLit();

  /**
   * Helper function for hexToString to pad left with zeros.
   * 
   * @param inputString
   * @param length
   * @return
   */
  public static String padLeftZeros(String inputString, int length) {
    if (inputString.length() >= length) {
        return inputString;
    }
    StringBuilder sb = new StringBuilder();
    while (sb.length() < length - inputString.length()) {
        sb.append('0');
    }
    sb.append(inputString);
 
    return sb.toString();
  }

  /**
   * Bare metal Java exercise program for Raspberry Pi.
   * <ul>
   * <li>Test mini UART hookup to System.out stream.
   * <li>Test System.currentTimeMillis hookup to ARM system timer.
   * <li>Test ARM timer interrupt service routine.
   * <li>Test GPIO interaction by flashing ACT LED.
   * <li>Test ARM->GPU mailbox interaction by printing board info.
   * </ul> 
   */
  public static void main(String[] args) {
    // How often should we print the system timer ms tick value?
    long printEveryMs = 100;

    // Prime the last time we ticked
    long lastMs = System.currentTimeMillis();

    // System.out will spit out to the console if the HaikuMicroKernelWithConsole
    // is used in build settings (which is the default).
    System.out.println("Bare Metal Java on Raspberry Pi Exercise Program.");

    // Get some info from the GPU through the Mailbox about the board
    Mailbox.PropertyInit();
    Mailbox.PropertyAddTag(MAILBOX_TAG.TAG_GET_BOARD_MODEL);
    Mailbox.PropertyAddTag(MAILBOX_TAG.TAG_GET_BOARD_REVISION);
    Mailbox.PropertyAddTag(MAILBOX_TAG.TAG_GET_FIRMWARE_VERSION);
    Mailbox.PropertyAddTag(MAILBOX_TAG.TAG_GET_BOARD_MAC_ADDRESS);
    Mailbox.PropertyAddTag(MAILBOX_TAG.TAG_GET_BOARD_SERIAL);
    Mailbox.PropertyAddTag(MAILBOX_TAG.TAG_GET_BOARD_MAC_ADDRESS);
    Mailbox.PropertyAddTag(MAILBOX_TAG.TAG_GET_MAX_CLOCK_RATE, CLOCK_ID.TAG_CLOCK_ARM.getId());
    Mailbox.PropertyProcess();

    // Print out all the info we received for the board
    System.out.print("Board Model: ");
    try {
      MailboxProperty mp = Mailbox.PropertyGet(MAILBOX_TAG.TAG_GET_BOARD_MODEL);
      System.out.println(mp.getValue32());
    } catch (IllegalArgumentException e) {
      System.out.println("NULL");
    }

    System.out.print("Board Revision: ");
    try {
      MailboxProperty mp = Mailbox.PropertyGet(MAILBOX_TAG.TAG_GET_BOARD_REVISION);
      System.out.println(mp.getValue32());
    } catch (IllegalArgumentException e) {
      System.out.println("NULL");
    }

    System.out.print("Firmware Version: ");
    try {
      MailboxProperty mp = Mailbox.PropertyGet(MAILBOX_TAG.TAG_GET_FIRMWARE_VERSION);
      System.out.println(mp.getValue32());
    } catch (IllegalArgumentException e) {
      System.out.println("NULL");
    }

    System.out.print("Serial Number: ");
    try {
      MailboxProperty mp = Mailbox.PropertyGet(MAILBOX_TAG.TAG_GET_BOARD_SERIAL);
      System.out.print(Long.toHexString(mp.getBuffer32(0)));
      System.out.println(padLeftZeros(Long.toHexString(mp.getBuffer32(1)), 8));
    } catch (IllegalArgumentException e) {
      System.out.println("NULL");
    }

    System.out.print("Mac Address: ");
    try {
      MailboxProperty mp = Mailbox.PropertyGet(MAILBOX_TAG.TAG_GET_BOARD_MAC_ADDRESS);
      System.out.print(Integer.toHexString(mp.getBuffer8(0)));
      System.out.print(":");
      System.out.print(Integer.toHexString(mp.getBuffer8(1)));
      System.out.print(":");
      System.out.print(Integer.toHexString(mp.getBuffer8(2)));
      System.out.print(":");
      System.out.print(Integer.toHexString(mp.getBuffer8(3)));
      System.out.print(":");
      System.out.print(Integer.toHexString(mp.getBuffer8(4)));
      System.out.print(":");
      System.out.println(Integer.toHexString(mp.getBuffer8(5)));
    } catch (IllegalArgumentException e) {
      System.out.println("NULL");
    }

    System.out.print("Max ARM Clock Rate: ");
    try {
      MailboxProperty mp = Mailbox.PropertyGet(MAILBOX_TAG.TAG_GET_MAX_CLOCK_RATE);
      System.out.print(mp.getBuffer32(1));
      System.out.println("Hz");
    } catch (IllegalArgumentException e) {
      System.out.println("NULL");
    }

    // Enable the Arm Timer interrupt
    Interrupts.setEnable_Basic_IRQs(Interrupts.RPI_BASIC_ARM_TIMER_IRQ);

    // Init the Arm Timer with a reasonable scale value
    // frequency = Clk/256 * 0x400 
    ArmTimer.setLoad(0x400);

    // Set up the ARM Timer
    ArmTimer.setControl(
      ArmTimer.RPI_ARMTIMER_CTRL_23BIT |
      ArmTimer.RPI_ARMTIMER_CTRL_ENABLE |
      ArmTimer.RPI_ARMTIMER_CTRL_INT_ENABLE |
      ArmTimer.RPI_ARMTIMER_CTRL_PRESCALE_256
    );

    // ArmTimer...start your interrupting engines.
    Rpi._enable_interrupts();

    // Start main loop
    while(true) {
      // Check timer to see if ticker should print
      if ((System.currentTimeMillis() - lastMs) > printEveryMs) {
        lastMs = System.currentTimeMillis();
        // Show the user that the system timer is ticking
        System.out.print("Tick: ");
        System.out.println(String.valueOf(lastMs));
      }
      // Do what the lit flag tells us from the interrupt service routine
      if (getLit() == 1) {
        Rpi.ACTLEDOn();
      } else {
        Rpi.ACTLEDOff();
      }
    }
  }  
}