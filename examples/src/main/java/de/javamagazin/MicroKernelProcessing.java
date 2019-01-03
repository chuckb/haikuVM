package de.javamagazin;
public class MicroKernelProcessing extends haiku.vm.MicroKernel {
	
	public static void main(String[] args) {
	    haiku.arduino.api.Arduino.init();
        
        haiku.vm.HaikuMagic.setup(); // Will call your setup method (by some HaikuVM magic)
        for (;;)
            haiku.vm.HaikuMagic.loop(); // Will call your loop method (by some HaikuVM magic)
        
	}
}
