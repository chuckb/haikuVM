package haiku.bcm;

import haiku.vm.NativeCFunction;
import haiku.vm.NativeCHeader;

@NativeCHeader(cImpl = "#include <platforms/pi/rpi-armtimer.h>")

public class ArmTimer {
  @NativeCFunction(cImpl = "RPI_GetArmTimer()->Load = arg1;")
  public static native void setLoad(int value);

  @NativeCFunction(cImpl = "RPI_GetArmTimer()->Control = arg1;")
  public static native void setControl(int value);

  /** @brief 0 : 16-bit counters - 1 : 23-bit counter */
  public static int RPI_ARMTIMER_CTRL_23BIT =         ( 1 << 1 );

  public static int RPI_ARMTIMER_CTRL_PRESCALE_1 =    ( 0 << 2 );
  public static int RPI_ARMTIMER_CTRL_PRESCALE_16 =   ( 1 << 2 );
  public static int RPI_ARMTIMER_CTRL_PRESCALE_256 =  ( 2 << 2 );

  /** @brief 0 : Timer interrupt disabled - 1 : Timer interrupt enabled */
  public static int RPI_ARMTIMER_CTRL_INT_ENABLE =    ( 1 << 5 );
  public static int RPI_ARMTIMER_CTRL_INT_DISABLE =   ( 0 << 5 );

  /** @brief 0 : Timer disabled - 1 : Timer enabled */
  public static int RPI_ARMTIMER_CTRL_ENABLE =        ( 1 << 7 );
  public static int RPI_ARMTIMER_CTRL_DISABLE =       ( 0 << 7 );
}