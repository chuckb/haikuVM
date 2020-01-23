package haiku.bcm;

import haiku.vm.NativeCFunction;
import haiku.vm.NativeCHeader;

@NativeCHeader(cImpl = "#include <platforms/pi/rpi-interrupts.h>")

public class Interrupts {
  @NativeCFunction(cImpl = "RPI_GetIrqController()->Enable_Basic_IRQs = arg1;")
  public static native void setEnable_Basic_IRQs(int value);

  /** @brief Bits in the Enable_Basic_IRQs register to enable various interrupts.
    See the BCM2835 ARM Peripherals manual, section 7.5 */
  public static int RPI_BASIC_ARM_TIMER_IRQ =         (1 << 0);
  public static int RPI_BASIC_ARM_MAILBOX_IRQ =       (1 << 1);
  public static int RPI_BASIC_ARM_DOORBELL_0_IRQ =    (1 << 2);
  public static int RPI_BASIC_ARM_DOORBELL_1_IRQ =    (1 << 3);
  public static int RPI_BASIC_GPU_0_HALTED_IRQ =      (1 << 4);
  public static int RPI_BASIC_GPU_1_HALTED_IRQ =      (1 << 5);
  public static int RPI_BASIC_ACCESS_ERROR_1_IRQ =    (1 << 6);
  public static int RPI_BASIC_ACCESS_ERROR_0_IRQ =    (1 << 7);
}