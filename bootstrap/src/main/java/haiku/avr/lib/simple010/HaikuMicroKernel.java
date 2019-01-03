package haiku.avr.lib.simple010;

import haiku.vm.HaikuMagic;
import haiku.vm.MicroKernel;

/**
 * PLEASE DON'T EDIT
 * 
 * Skeleton for Haiku VM organizing the start of the target program.
 */
public class HaikuMicroKernel extends haiku.vm.MicroKernel {
    
	public static void main(String[] args) {
		try {
		    clinitHaikuMagic();

			HaikuMagic.main(args); // Will call your main method (by some HaikuVM magic)  
			
		} catch (Throwable e) {
			// All Exceptions will end up at least here
		}
		Thread.currentThread().stop();
	}
}
