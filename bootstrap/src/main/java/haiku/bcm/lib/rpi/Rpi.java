package haiku.bcm.lib.rpi;

import haiku.vm.NativeCBody;
import haiku.vm.NativeCFunction;

import static haiku.bcm.GPIO.*;

@NativeCBody(cImpl = "#include <platforms/pi/rpi-gpio.h>\n" +
"extern \"C\" void _enable_interrupts(void);\n" +
"extern \"C\" void _disable_interrupts(void);\n"
)

/**
 * Base class to implement Raspberry Pi functionality based
 * on Broadcom chipset
 */
public class Rpi {

  /**
   * Set the state of the ACT LED on the pi board to ON
   */
  @NativeCFunction(cImpl = "LED_ON();")
  public native static void ACTLEDOn();
  
  /**
   * Set the state of the ACT LED on the pi board to OFF
   */
  @NativeCFunction(cImpl = "LED_OFF();")
  public native static void ACTLEDOff();

  @NativeCFunction(cImpl = "return PERIPHERAL_BASE;")
  public native static int GetPERIPHERAL_BASE();

  @NativeCFunction
  public static native void _enable_interrupts();

  @NativeCFunction
  public static native void _disable_interrupts();

  public static void init() {
    // Initialize function select for the LED GPIO to output
    SetGPIORegisterBit(o_LED_GPFSEL, GetLED_GPFBIT(), 1);
  }
}