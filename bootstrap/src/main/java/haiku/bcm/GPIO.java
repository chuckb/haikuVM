package haiku.bcm;

import haiku.vm.NativeCHeader;
import haiku.vm.NativeCVariable32;
import haiku.vm.NativeCFunction;

@NativeCHeader(cImpl = "#include <platforms/pi/rpi-gpio.h>")

public class GPIO {
  public enum VALUE {
    IO_LO,
    IO_HI,
    IO_ON,
    IO_OFF,
    IO_UNKNOWN
  }
  public enum PIN {
    GPIO0,
    GPIO1,
    GPIO2,
    GPIO3,
    GPIO4,
    GPIO5,
    GPIO6,
    GPIO7,
    GPIO8,
    GPIO9,
    GPIO10,
    GPIO11,
    GPIO12,
    GPIO13,
    GPIO14,
    GPIO15,
    GPIO16,
    GPIO17,
    GPIO18,
    GPIO19,
    GPIO20,
    GPIO21,
    GPIO22,
    GPIO23,
    GPIO24,
    GPIO25,
    GPIO26,
    GPIO27,
    GPIO28,
    GPIO29,
    GPIO30,
    GPIO31,
    GPIO32,
    GPIO33,
    GPIO34,
    GPIO35,
    GPIO36,
    GPIO37,
    GPIO38,
    GPIO39,
    GPIO40,
    GPIO41,
    GPIO42,
    GPIO43,
    GPIO44,
    GPIO45,
    GPIO46,
    GPIO47,
    GPIO48,
    GPIO49,
    GPIO50,
    GPIO51,
    GPIO52,
    GPIO53
  }
  public enum ALT_FUNCTION {
    FS_INPUT,
    FS_OUTPUT,
    FS_ALT5,
    FS_ALT4,
    FS_ALT0,
    FS_ALT1,
    FS_ALT2,
    FS_ALT3
  }


  /**
   * Set the GPIO register with a value from an offset given
   * from the GPIO base address, which is set automatically
   * based on the type of Raspberry Pi defined at build time.
   * @param offset  Offset in bytes from GPIO base address
   * @param value   Value to set
   */
  @NativeCFunction(cImpl = "*(volatile unsigned int *)((uint8_t*)(void*)&RPI_GetGpio()[arg1]) = arg2;")
  public static native void SetGPIORegister(int offset, int value);  

  @NativeCFunction(cImpl = "RPI_GetGpio()->GPPUD = arg1;")
  public static native void SetGPPUD(int value);

  @NativeCFunction(cImpl = "RPI_GetGpio()->GPPUDCLK0 = arg1;")
  public static native void SetGPPUDCLK0(int value);

  /**
   * Get the GPIO register value given an offset from the GPIO base address.
   * @param offset  Offset in bytes from GPIO base address
   * @return        The value contained within the register
   */
  @NativeCFunction(cImpl = "return *(volatile unsigned int *)((uint8_t*)(void*)&RPI_GetGpio()[arg1])")
  public static native int GetGPIORegister(int offset);

  /**
   * Set the bit value within a register given an offset from the
   * GPIO base address. Base offset address is set automatically
   * based on the type of Raspberry Pi defined at build time.
   * @param offset  Offset in bytes from GPIO base address
   * @param bit     Bit to set
   * @param value   Value to shift into register
   */
  @NativeCFunction(cImpl = "*(volatile unsigned int *)((uint8_t*)(void*)&RPI_GetGpio()[arg1]) |= (arg3 << arg2);")
  public static native void SetGPIORegisterBit(int offset, int bit, int value);

  /**
   * GPIO function select bit of the ACT LED of the 
   * defined Raspberry Pi.
   * @return  The function select bit
   */
  @NativeCFunction(cImpl = "return LED_GPFBIT;")
  public static native int GetLED_GPFBIT();

  @NativeCFunction(cImpl = "RPI_SetGpioPinFunction((rpi_gpio_pin_t)arg1, (rpi_gpio_alt_function_t)arg2);")
  private static native void _SetGpioPinFunction( int gpio, int func );

  /**
   * Set the GPIO pin funtion of of a given pin.
   * @param gpio  The pin to set.
   * @param func  The pin function.
   */
  public static void SetGpioPinFunction( PIN gpio, ALT_FUNCTION func ) {
    _SetGpioPinFunction(gpio.ordinal(), func.ordinal());
  }

  @NativeCFunction(cImpl = "RPI_SetGpioOutput((rpi_gpio_pin_t)arg1);")
  private static native void _SetGpioOutput( int gpio );

  /**
   * Set the GPIO pin function to output.
   * @param gpio  The pin to set.
   */
  public static void SetGpioOutput( PIN gpio ) {
    _SetGpioOutput(gpio.ordinal());
  }

  @NativeCFunction(cImpl = "RPI_SetGpioInput((rpi_gpio_pin_t)arg1);")
  private static native void _SetGpioInput( int gpio );

  /**
   * Set the GPIO pin function to input.
   * @param gpio  The pin to set.
   */
  public static void SetGpioInput( PIN gpio ) {
    _SetGpioInput(gpio.ordinal());
  }

  @NativeCFunction(cImpl = "return (jint)RPI_GetGpioValue((rpi_gpio_pin_t)arg1);")
  private static native int _GetGpioValue( int gpio );

  /**
   * Get the value of the GPIO pin.
   */
  public static VALUE GetGpioValue( PIN gpio ) {
    return VALUE.values()[_GetGpioValue(gpio.ordinal())];
  }

  @NativeCFunction(cImpl = "RPI_SetGpioHi((rpi_gpio_pin_t)arg1);")
  private static native void _SetGpioHi( int gpio );

  /**
   * Set the GPIO pin value to Hi.
   * @param gpio  The pin to set.
   */
  public static void SetGpioHi( PIN gpio ) {
    _SetGpioHi(gpio.ordinal());
  }

  @NativeCFunction(cImpl = "RPI_SetGpioLo((rpi_gpio_pin_t)arg1);")
  private static native void _SetGpioLo( int gpio );

  /**
   * Set the GPIO pin value to Lo.
   * @param gpio  The pin to set.
   */
  public static void SetGpioLo( PIN gpio ) {
    _SetGpioLo(gpio.ordinal());
  }

  @NativeCFunction(cImpl = "RPI_SetGpioValue((rpi_gpio_pin_t)arg1, (rpi_gpio_value_t)arg2);")
  private static native void _SetGpioValue( int gpio, int value );

  /**
   * Set the GPIO pin to the given value.
   * @param gpio    The pin to set.
   * @param value   The value to set.
   */
  public static void SetGpioValue( PIN gpio, VALUE value ) {
    _SetGpioValue(gpio.ordinal(), value.ordinal());
  }

  @NativeCFunction(cImpl = "RPI_ToggleGpio((rpi_gpio_pin_t)arg1);")
  private static native void _ToggleGpio( int gpio );

  /**
   * Toggle the value of a given pin.
   * @param gpio  The pin to toggle.
   */
  public static void ToggleGpio( PIN gpio ) {
    _ToggleGpio(gpio.ordinal());
  }

  /**
   * Offset in bytes from GPIO base of function select
   * register of the active LED for the defined Pi.
   */
  @NativeCVariable32
  public static int o_LED_GPFSEL;

  /**
   * Offset in bytes from GPIO base of GPIO function select
   * register 0.
   */
  @NativeCVariable32
  public static int o_GPFSEL0;

  /**
   * Offset in bytes from GPIO base of GPIO function select
   * register 1.
   */
  @NativeCVariable32
  public static int o_GPFSEL1;

  /**
   * Offset in bytes from GPIO base of GPIO function select
   * register 2.
   */
  @NativeCVariable32
  public static int o_GPFSEL2;

  /**
   * Offset in bytes from GPIO base of GPIO function select
   * register 3.
   */
  @NativeCVariable32
  public static int o_GPFSEL3;

  /**
   * Offset in bytes from GPIO base of GPIO function select
   * register 4.
   */
  @NativeCVariable32
  public static int o_GPFSEL4;

  /**
   * Offset in bytes from GPIO base of GPIO function select
   * register 5.
   */
  @NativeCVariable32
  public static int o_GPFSEL5;

  /**
   * Offset in bytes from GPIO base of GPIO pin set register
   * bank 0.
   */
  @NativeCVariable32
  public static int o_GPSET0;

  /**
   * Offset in bytes from GPIO base of GPIO pin set register
   * bank 1.
   */
  @NativeCVariable32
  public static int o_GPSET1;

  /**
   * Offset in bytes from GPIO base of GPIO pin clear register
   * bank 0.
   */
  @NativeCVariable32
  public static int o_GPCLR0;

  /**
   * Offset in bytes from GPIO base of GPIO pin clear register
   * bank 1.
   */
  @NativeCVariable32
  public static int o_GPCLR1;

  /**
   * Offset in bytes from GPIO base of GPIO pin level register
   * bank 0.
   */
  @NativeCVariable32
  public static int o_GPLEV0;

  /**
   * Offset in bytes from GPIO base of GPIO pin level register
   * bank 1.
   */
  @NativeCVariable32
  public static int o_GPLEV1;

  /**
   * Offset in bytes from GPIO base of GPIO pin event detect status register
   * bank 0.
   */
  @NativeCVariable32
  public static int o_GPEDS0;

  /**
   * Offset in bytes from GPIO base of GPIO pin event detect status register
   * bank 1.
   */
  @NativeCVariable32
  public static int o_GPEDS1;

  /**
   * Offset in bytes from GPIO base of GPIO rising edge detect enable register
   * bank 0.
   */
  @NativeCVariable32
  public static int o_GPREN0;

  /**
   * Offset in bytes from GPIO base of GPIO rising edge detect enable register
   * bank 1.
   */
  @NativeCVariable32
  public static int o_GPREN1;

  /**
   * Offset in bytes from GPIO base of GPIO falling edge detect enable register
   * bank 0.
   */
  @NativeCVariable32
  public static int o_GPFEN0;

  /**
   * Offset in bytes from GPIO base of GPIO falling edge detect enable register
   * bank 1.
   */
  @NativeCVariable32
  public static int o_GPFEN1;

  /**
   * Offset in bytes from GPIO base of GPIO high detect enable register
   * bank 0.
   */
  @NativeCVariable32
  public static int o_GPHEN0;

  /**
   * Offset in bytes from GPIO base of GPIO high detect enable register
   * bank 1.
   */
  @NativeCVariable32
  public static int o_GPHEN1;

  /**
   * Offset in bytes from GPIO base of GPIO low detect enable register
   * bank 0.
   */
  @NativeCVariable32
  public static int o_GPLEN0;

  /**
   * Offset in bytes from GPIO base of GPIO low detect enable register
   * bank 1.
   */
  @NativeCVariable32
  public static int o_GPLEN1;

  /**
   * Offset in bytes from GPIO base of GPIO async rising edge detect enable register
   * bank 0.
   */
  @NativeCVariable32
  public static int o_GPAREN0;

  /**
   * Offset in bytes from GPIO base of GPIO async rising edge detect enable register
   * bank 1.
   */
  @NativeCVariable32
  public static int o_GPAREN1;

  /**
   * Offset in bytes from GPIO base of GPIO async falling edge detect enable register
   * bank 0.
   */
  @NativeCVariable32
  public static int o_GPAFEN0;

  /**
   * Offset in bytes from GPIO base of GPIO async falling edge detect enable register
   * bank 1.
   */
  @NativeCVariable32
  public static int o_GPAFEN1;

  /**
   * Offset in bytes from GPIO base of GPIO pull-up/pull-down enable register.
   * Note that all GPIO are impacted with one setting. Used in conjunction
   * with GPPUDCLK0 and GPPUDCLK1.
   */
  @NativeCVariable32
  public static int o_GPPUD;

  /**
   * Offset in bytes from GPIO base of GPIO pull-up/pull-down clock register
   * bank 0.
   */
  @NativeCVariable32
  public static int o_GPPUDCLK0;

  /**
   * Offset in bytes from GPIO base of GPIO pull-up/pull-down clock register
   * bank 1.
   */
  @NativeCVariable32
  public static int o_GPPUDCLK1;
}