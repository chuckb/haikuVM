package haiku.avr.lib.arduino;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import haiku.arduino.processing.Serial;
import haiku.vm.HaikuMagic;

/**
 * Skeleton for Haiku VM organizing the start of the target program.
 */
public class HaikuMicroKernel4ArduinoIDEWithMainUsingSystem extends haiku.vm.MicroKernel {

	public static void init() {
	    System.out = new PrintStream(new OutputStream() {

	        @Override
	        /**
	         * Wrong or not thread save:
	         */
	        public void write(int b) throws IOException {
	            Serial.write((byte)b);
	        }
	    });
	    System.err = System.out;

	    System.in = new InputStream() {
	        public int available() {
	            return Serial.available();
	        }

	        public int read() {
	            while (available() == 0)
	                ;

	            return Serial.read();
	        }
	    };

	    Serial.begin(57600);
	    while(!Serial.isOpen()) ; // wait
	}

	public static void main(String[] args) {
		try {
			clinitHaikuMagic();

            init();

			/**
			 * The following call of panic()
			 * and consequently the method body panic()
			 * will be extingted from code if property Target.PanicSupport is
			 * set to 0
			 * .. --Config:PanicSupport=0 ..
			 */
			panic(0, 0);
			HaikuMagic.main(args); // Will call your main method (by some HaikuVM magic)
		} catch (Throwable e) {
		}
		Thread.currentThread().stop();
	}
}
