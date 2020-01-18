package haiku.bcm.lib.rpi;

import static haiku.bcm.GPIO.*;
import static haiku.bcm.GPIOConstants.*;

public class Rpi0Lib extends Rpi {
  public static final int PERIPHERIAL_BASE = 0x20000000;
  public Rpi0Lib() {
    // Wire up constants specific to the Rpi0
    this.ACTLED_GPFSEL_REG = GPFSEL4;
    this.ACTLED_GPFSEL_BIT = FSEL47;
    this.ACTLED_GPSET_REG = GPSET1;
    this.ACTLED_GPCLR_REG = GPCLR1;
    this.ACTLED_GPSET_BIT = SET47;
    this.ACTLED_GPCLR_BIT = CLR47;

    // set gpio base address
    setGPIOBase((PERIPHERIAL_BASE + 0x200000));

    // Write 1 to the GPIO init nibble in the Function Select GPIO peripheral register to set
    // the GPIO pin for the ACT LED as an output
    setGPIOBit(ACTLED_GPFSEL_REG, ACTLED_GPFSEL_BIT, GPOUTPUT);    
  }
}