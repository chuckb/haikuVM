package haiku.bcm;

import haiku.vm.NativeCBody;
import haiku.vm.NativeCFunction;
import static haiku.bcm.AuxConstants.*;
import static haiku.bcm.GPIO.*;

@NativeCBody(cImpl = "volatile unsigned int* aux;")

public class Aux {
  @NativeCFunction(cImpl = "aux[arg1] = arg2;")
  public static native void setAux(int offset, int value);  
        
  @NativeCFunction(cImpl = "return aux[arg1];")
  public static native int getAux(int offset);

  @NativeCFunction(cImpl = "aux = (unsigned int*)arg1;")
  public static native void setAuxBase(int gpio_base);

  @NativeCFunction(cImpl = "aux[arg1] |= (arg3 << arg2);")
  public static native void setAuxBit(int offset, int bit, int value);

  /**
   * Set up Mini UART 1 on the BCM2835 for writing.
   * This UART is located on the Aux peripherial as documented
   * within https://www.raspberrypi.org/app/uploads/2012/02/BCM2835-ARM-Peripherals.pdf.
   * @param baud  The baud rate.
   * @param bits  7 or 8 data bits.
   */
  public static void initMiniUART(int baud, int bits) {
    // Setup GPIO 14 and 15 as alternative function 5 which is
    // UART 1 TXD/RXD. These need to be set before enabling the UART
    SetGpioPinFunction(PIN.GPIO14, ALT_FUNCTION.FS_ALT5);
    SetGpioPinFunction(PIN.GPIO15, ALT_FUNCTION.FS_ALT5);

    // Set control signal to disable internal pull ups/pull downs
    SetGPIORegister(o_GPPUD, 0);
    // Setup time for control signal per docs...note that these are not
    // techincally CPU cycles, so this is longer than necessary.
    for ( int i=0; i<150; i++ ) { }
    // Signal clock to strobe appropriate GPIO control pads
    SetGPIORegister(o_GPPUDCLK0, (1 << 14));
    // Delay for strobing to complete
    for ( int i=0; i<150; i++ ) { }
    // Turn off signal clock control
    SetGPIORegister(o_GPPUDCLK0, 0);

    // Set the enable bit to enabled...this is required
    // before accessing any UART registers.
    setAux(AUX_ENABLES, AUX_ENA_MINIUART);

    // Disable interrupts
    setAux(AUX_MU_IER_REG, 0);

    /* Disable flow control,enable transmitter and receiver! */
    setAux(AUX_MU_CNTL_REG, 0);

    /* Decide between seven or eight-bit mode */
    if ( bits == 8 ) {
      setAux(AUX_MU_LCR_REG, AUX_MULCR_8BIT_MODE);
    } else {
      setAux(AUX_MU_LCR_REG, 0);
    }

    // Disable modem control
    setAux(AUX_MU_MCR_REG, 0);

    /* Mind the eratta https://raspberrypi.org/app/uploads/2012/02/BCM2835-ARM-Peripherals.pdf */
    /* Disable all interrupts from MU */
    setAux(AUX_MU_IER_REG, 0);
    /* Clear the fifos */
    setAux(AUX_MU_IIR_REG, 0xC6);

    // Set baud rate
    /* Transposed calculation from Section 2.2.1 of the ARM peripherals
      manual */
    setAux(AUX_MU_BAUD_REG, (( SYS_FREQ / ( 8 * baud ) ) - 1));

    // Finally, transmit enable...
    setAux(AUX_MU_CNTL_REG, AUX_MUCNTL_TX_ENABLE);
  }

  /**
   * Write out a character to the FIFO buffer of the UART
   * by waiting for a spot to become available. Note that
   * this routine can wait forever.
   * @param c   The character to write.
   */
  public synchronized static void miniUARTWrite( char c )
  {
    /* Wait until the UART has an empty space in the FIFO */
    while (true) {
      if ((getAux(AUX_MU_LSR_REG) & AUX_MULSR_TX_EMPTY) != 0) {
        break;
      }
    }

    // Cast to get ascii value of the unicode char.
    int ascii = (int) c;
    /* Write the character to the FIFO for transmission */
    setAux(AUX_MU_IO_REG, ascii);
  }

  /**
   * Write out a byte to the FIFO buffer of the UART
   * by waiting for a spot to become available. Note that
   * this routine can wait forever.
   * @param c   The byte to write.
   */
  public synchronized static void miniUARTWrite( byte b )
  {
    /* Wait until the UART has an empty space in the FIFO */
    while (true) {
      if ((getAux(AUX_MU_LSR_REG) & AUX_MULSR_TX_EMPTY) != 0) {
        break;
      }
    }

    /* Write the byte to the FIFO for transmission */
    setAux(AUX_MU_IO_REG, (int)b);
  }
}