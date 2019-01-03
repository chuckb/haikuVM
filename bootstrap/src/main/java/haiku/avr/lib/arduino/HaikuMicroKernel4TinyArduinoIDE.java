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
public class HaikuMicroKernel4TinyArduinoIDE extends haiku.vm.MicroKernel {
	public static void main(String[] args) {
        clinitHaikuMagic();     
        
        HaikuMagic.setup(); // Will call your setup method (by some HaikuVM magic)
        
        for (;;)
            HaikuMagic.loop(); // Will call your loop method (by some HaikuVM magic)
	}
}
