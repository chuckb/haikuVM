package haiku.avr.lib.arduino;

import haiku.vm.HaikuMagic;

import java.lang.String;
import java.lang.Thread;
import java.lang.Throwable;

/**
 * Skeleton for Haiku VM organizing the start of the target program.
 * 
 * @author genom2
 *
 */
public class HaikuMicroKernelProcessing extends haiku.vm.MicroKernel {
    private static native void init();
	
	public static void main(String[] args) {
		try {
		    clinitHaikuMagic();		
			
			/**
			 * The following call of panic()
			 * and consequently the method body panic() 
			 * will be extingted from code if property <Target>.PanicSupport is set to 0
			 * .. -Config:PanicSupport=0 ..
			 */
			panic(0, 0);
			
		    init();

			HaikuMagic.setup(); // Will call your setup method (by some HaikuVM magic)
		    
			for (;;)
				HaikuMagic.loop(); // Will call your loop method (by some HaikuVM magic)
			
		} catch (Throwable e) {
		}
		Thread.currentThread().stop();
	}
}
