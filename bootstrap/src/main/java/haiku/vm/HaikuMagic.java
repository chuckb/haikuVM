package haiku.vm;

/**
 * This is just a template class for MicroKernel(s).
 * 
 * This is the simple "magic":
 * Every call of any method X of HaikuMagic will be substituted by haikufier 
 * with a call to method X of your JAVA program.
 */
public abstract class HaikuMagic {	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Will never execute. Just a template for HaikuMicroKernel(s).
	}

	// for Arduino processing
	public static void setup() {
		// Will never execute. Just a template for HaikuMicroKernel(s).
	}

	// for Arduino processing
	public static void loop() {
		// Will never execute. Just a template for HaikuMicroKernel(s).
	}

}
