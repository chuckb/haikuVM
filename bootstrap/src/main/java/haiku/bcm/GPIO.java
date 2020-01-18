package haiku.bcm;

import haiku.vm.NativeCBody;
import haiku.vm.NativeCFunction;

@NativeCBody(cImpl = "volatile unsigned int* gpio;")

public class GPIO {
  @NativeCFunction(cImpl = "gpio[arg1] = arg2;")
  public static native void setGPIO(int offset, int value);  
        
  @NativeCFunction(cImpl = "return gpio[arg1];")
  public static native int getGPIO(int offset);

  @NativeCFunction(cImpl = "gpio = (unsigned int*)arg1;")
  public static native void setGPIOBase(int gpio_base);

  @NativeCFunction(cImpl = "gpio[arg1] |= (arg3 << arg2);")
  public static native void setGPIOBit(int offset, int bit, int value);
}