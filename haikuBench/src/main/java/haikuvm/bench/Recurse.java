/**
 * 
 */
package haikuvm.bench;

/**
 * $Version 1.1.1 $ $Date: 2013-11-14 19:18:55 +0100 (Do, 14 Nov 2013) $
 * 
 * Effective configuration for 'duemilanove':
  APP_BASE = D:\Entwicklung\haikuVM\myCProject\.
  APP_NAME = Recurse
  AsuroTiming = null
  CC = avr-gcc
  CC_OPT = $(HAIKU_CFLAGS) -Wall -Os -fpack-struct -fshort-enums -std=gnu99 -funsigned-char -funsigned-bitfields -ffunction-sections -fdata-sections -mmcu=$(HAIKU_TARGET) -DF_CPU=$(HAIKU_CLOCK)UL -I"$(HAIKU_APP_BASE)" -I"$(HAIKU_VM_BASE)" -c -o"$@" "$<"
  CFLAGS = 
  CLIBS = 
  CXX = avr-g++
  CXX_OPT = $(HAIKU_CFLAGS) -Wall -Os -fpack-struct -fshort-enums            -funsigned-char -funsigned-bitfields -ffunction-sections -fdata-sections -mmcu=$(HAIKU_TARGET) -DF_CPU=$(HAIKU_CLOCK)UL -I"$(HAIKU_APP_BASE)" -I"$(HAIKU_VM_BASE)" -c -o"$@" "$<"
  Char = HAIKU_CHAR_8
  Clock = 16000000
  Config = duemilanove
  Extends = avr
  Extension = .hex
  GC = HAIKU_StopTheWorldGC
  HAIKUVM4C = ./haikuVM
  HOME = D:\Entwicklung\haikuVM
  IncrementalGCSlice = 10
  InitialMainThreadStackSize = 142
  InitialOtherThreadStackSize = 45
  PanicExceptions = NullPointerException | NoSuchMethodError | OutOfMemoryError | ClassCastException | VirtualMachineError | ArithmeticException
  PanicSupport = 0
  LDFLAGS = -Wl,-Map,$(HAIKU_APP_NAME).map -Wl,--gc-sections -mmcu=$(HAIKU_TARGET) -o"$(HAIKU_APP_NAME).elf" -lc -lm $(OBJS) $(USER_OBJS) $(HAIKU_CLIBS) $(LIBS)
  LINKER = avr-g++
  MemorySize = (RAMEND-0x100) - 300
  MicroKernel = haiku.avr.lib.arduino.HaikuMicroKernelEx
  MillisDividend = 128
  MillisDivisor = 125
  MillisPrescale = 64
  Mode = HAIKU_16_32
  Output = $(HAIKU_APP_NAME)$(HAIKU_EXTENSION)
  Port = \\\.\com17
  Target = atmega328p
  Threads = 0
  TimerInterrupt = TIMER0_OVF_vect
  Upload = avrdude -p $(HAIKU_TARGET) -c stk500v1 -P $(HAIKU_PORT) -b 57600 -U flash:w:$(HAIKU_OUTPUT)
  UserMain = null
  VM_BASE = ../../HaikuVM

Device: atmega328p
Program:    7338 bytes
(.text + .data + .bootloader)
Data:       1773 bytes (86.6% Full)
(.data + .bss + .noinit)

 *
 * result:
50      286
51      576
52      939
53      1287
54      1677
55      2085
56      2532
57      3014
58      3499
59      4045
60      4605
61      5175
62      5762
63      6397
64      7036
65      7703
66      8423
67      9267
68      10035
69      11034
70      11819
71      12871
72      13810
73      14663
74      15601
75      16541
76      17737
 * And then silent crash. 
 * Because we have an OutOfMemoryException inside an OutOfMemoryException.
 * When setting up the stack for the exception handler for the first OutOfMemoryException.
 * 
 * I expected a defined unwinding into the catch clause of haiku.avr.lib.arduino.HaikuMicroKernelEx.main(..)
 *         } catch (Throwable e) {
 *             System.out.println(e.toString());
 *         }
 *   
 *
 *
 * @author genom2
 *
 */
public class Recurse {

    private static final int N=200;
    
	public static void main(String[] args) {
		int result=0;
		for (int j = 50; j < N; j++) {
	        for (int i=0; i<=j; i++) {
	            result = recurse(i, 0);
	            if (j==N+1) System.out.println("Recurse "+result); //never print
	        }
	        System.out.println(j+"\t"+System.currentTimeMillis());
        }
	}

	private static int recurse(int n, int m) {
	    int a;
	    if (n<=0) {
	        a=m;
	    } else {
	        a=recurse(n - 1, m + 1);
	    }
		return a;
	}
}
