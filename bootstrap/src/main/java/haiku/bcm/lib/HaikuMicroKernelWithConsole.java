package haiku.bcm.lib;

import haiku.bcm.lib.rpi.*;
import static haiku.bcm.Aux.*;

import haiku.vm.HaikuMagic;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.String;
import java.lang.Thread;
import java.lang.Throwable;


/**
 * Skeleton for Haiku VM organizing the start of the target program.
 * 
 * @author genom2
 *
 */
public class HaikuMicroKernelWithConsole extends haiku.vm.MicroKernel {
	
	public static void main(String[] args) {
		try {
		  clinitHaikuMagic();		
			
			/**
			 * The following call of panic()
			 * and therefor the method body panic() 
			 * will be extingted from code if property Target.PanicSupport is set to 0
			 * .. --Config:PanicSupport=0 ..
			 */
			panic(0, 0);

      // Set the auxilliary base address.
      setAuxBase(Rpi.GetPERIPHERAL_BASE() + 0x215000);
      // Initialize mini UART for console output display
      initMiniUART(115200, 8);

      // Wire up System streams to UART
	    System.out = new PrintStream(new OutputStream() {

        @Override
        /**
         * Wrong or not thread save:
         */
        public void write(int b) throws IOException {
            miniUARTWrite((char)b);
        }
      });
      System.err = System.out;

			HaikuMagic.main(args); // Will call your main method (by some HaikuVM magic)
			
		} catch (Throwable e) {
			// All Exceptions will end up at least here
		}
		Thread.currentThread().stop();
	}
}
