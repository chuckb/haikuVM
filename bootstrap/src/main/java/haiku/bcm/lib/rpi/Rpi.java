package haiku.bcm.lib.rpi;

import static haiku.bcm.GPIO.*;

/**
 * Base class to implement Raspberry Pi functionality based
 * on Broadcom chipset
 */
public class Rpi {
  protected int ACTLED_GPFSEL_REG = 0;
  protected int ACTLED_GPFSEL_BIT = 0;
  protected int ACTLED_GPSET_REG = 0;
  protected int ACTLED_GPCLR_REG = 0;
  protected int ACTLED_GPSET_BIT = 0;
  protected int ACTLED_GPCLR_BIT = 0;

  /**
   * Set the state of the ACT LED on the pi board
   * @param status  ON or OFF
   */
	public void ACTLEDOn() {
    // Pull down the gpio to light LED - active low
    setGPIOBit(ACTLED_GPCLR_REG, ACTLED_GPCLR_BIT, 1);
  }
  public void ACTLEDOff() {
    setGPIOBit(ACTLED_GPSET_REG, ACTLED_GPSET_BIT, 1);
  }
}