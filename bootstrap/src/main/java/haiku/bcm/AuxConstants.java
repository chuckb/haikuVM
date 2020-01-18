package haiku.bcm;

/**
 * Defines the constants for interracting with the BCM2835 Aux peripherial.
 * These are documented in: https://www.raspberrypi.org/app/uploads/2012/02/BCM2835-ARM-Peripherals.pdf
 * NOTE: serious erata located in: https://elinux.org/BCM2835_datasheet_errata
 */
public class AuxConstants {
  // Register offsets
  public static final int AUX_IRQ         = 0;    // Auxiliary Interrupt          0x00
  public static final int AUX_ENABLES     = 1;    // Auxiliary enables            0x04
  public static final int AUX_MU_IO_REG   = 16;   // Mini Uart I/O Data           0x40
  public static final int AUX_MU_IER_REG  = 17;   // Mini Uart Interrupt Enable   0x44
  public static final int AUX_MU_IIR_REG  = 18;   // Mini Uart Interrupt Identify 0x48
  public static final int AUX_MU_LCR_REG  = 19;   // Mini Uart Line Control       0x4C
  public static final int AUX_MU_MCR_REG  = 20;   // Mini Uart Modem Control      0x50
  public static final int AUX_MU_LSR_REG  = 21;   // Mini Uart Line Status        0x54
  public static final int AUX_MU_MSR_REG  = 22;   // Mini Uart Modem Status       0x58
  public static final int AUX_MU_SCRATCH  = 23;   // Mini Uart Scratch            0x5C
  public static final int AUX_MU_CNTL_REG = 24;   // Mini Uart Extra Control      0x60
  public static final int AUX_MU_STAT_REG = 25;   // Mini Uart Extra Status       0x64
  public static final int AUX_MU_BAUD_REG = 26;   // Mini Uart Baudrate           0x68
  // Do not be confused...docs name registers SPI0 and SPI1, but they are actually
  // SPI1 and SPI2. I continue the (poor, uncorrected) practice here.
  // Note that register address eratta has been corrected.
/*  
  public static final int AUX_SPI0_CNTL0_REG  = 0x80; // SPI 1 Control register 0 32
  public static final int AUX_SPI0_CNTL1_REG  = 0x84; // SPI 1 Control register 1 8
  public static final int AUX_SPI0_STAT_REG   = 0x88; // SPI 1 Status 32
  public static final int AUX_SPI0_IO_REG     = 0xAC; // SPI 1 Data 32
  public static final int AUX_SPI0_PEEK_REG   = 0x8C; // SPI 1 Peek 16
  public static final int AUX_SPI0_TXHOLD_REG = 0xBC; // SPI 1 Transmit Hold
  public static final int AUX_SPI1_CNTL0_REG  = 0xC0; // SPI 2 Control register 0 32
  public static final int AUX_SPI1_CNTL1_REG  = 0xC4; // SPI 2 Control register 1 8 
  public static final int AUX_SPI1_STAT_REG   = 0xC8; // SPI 2 Status 32
  public static final int AUX_SPI1_IO_REG     = 0xEC; // SPI 2 Data 32
  public static final int AUX_SPI1_PEEK_REG   = 0xCC; // SPI 2 Peek 16
  public static final int AUX_SPI1_TXHOLD_REG = 0xFC; // SPI 2 Transmit Hold   
*/

  // Bit flags
  public static final int AUX_ENA_MINIUART      = ( 1 << 0 );
  public static final int AUX_MULCR_8BIT_MODE   = ( 3 << 0 );  /* See errata for this value */
  public static final int AUX_MUCNTL_TX_ENABLE  = ( 1 << 1 );
  public static final int AUX_MULSR_TX_EMPTY    = ( 1 << 5 );

  // Misc
  /* Define the system clock frequency in MHz for the baud rate calculation.
    NOTE: Broadcom docs are not clear about the core clock frequency of the chip.
    This is clearly defined on the BCM2835 datasheet errata page:
    http://elinux.org/BCM2835_datasheet_errata */
  public static final int SYS_FREQ = 250000000;
}