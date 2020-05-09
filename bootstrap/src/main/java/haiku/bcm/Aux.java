package haiku.bcm;

import haiku.vm.NativeCHeader;
import haiku.vm.NativeCFunction;

@NativeCHeader(cImpl = "#include <platforms/pi/rpi-aux.h>")

public class Aux {
  /**
   * Set up Mini UART 1 on the BCM2835 for writing.
   * This UART is located on the Aux peripherial as documented
   * within https://www.raspberrypi.org/app/uploads/2012/02/BCM2835-ARM-Peripherals.pdf.
   * @param baud  The baud rate.
   * @param bits  7 or 8 data bits.
   */
  @NativeCFunction(cImpl = "RPI_AuxMiniUartInit(arg1, arg2);")
  public static native void AuxMiniUartInit(int baud, int bits);

  /**
   * Write out a character to the FIFO buffer of the UART
   * by waiting for a spot to become available. Note that
   * this routine can wait forever.
   * @param c   The character to write.
   */
  @NativeCFunction(cImpl = "RPI_AuxMiniUartWrite(arg1);")
  public native static void AuxMiniUartWrite( char c );
}