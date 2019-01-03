package haiku.avr.lib.asuro.lib2_8_0_rc1;

import static haiku.avr.AVRConstants.*;
import haiku.vm.HaikuMagic;

import java.io.IOException;
import java.io.InputStream;
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
public class HaikuMicroKernelEx extends haiku.vm.MicroKernel {
	
	public static void init() {
		System.out=new PrintStream(new OutputStream() {

			@Override
			public void write(int b) throws IOException {
				AsuroLib2_8_0_rc1.UartPutc((char) b);
			}});
		
		System.in=new InputStream() {
			public int available() {
				if ((UCSRA & (1 << RXC)) != 0)  return 1;
				return 0;
			}

			public int read() {
				while(available()==0) 
					;
				
				return UDR;
			}
		};	
	}
	
	public static void main(String[] args) {
		try {
		    clinitHaikuMagic();		

			AsuroLib2_8_0_rc1.Init();
			init();
			
			/**
			 * The following call of panic()
			 * and consequently the method body panic() 
			 * will be extingted from code if property Target.PanicSupport is set to 0
			 * .. --Config:PanicSupport=0 ..
			 */
			panic(0, 0);
			
			HaikuMagic.main(args); // Will call your main method (by some HaikuVM magic)
			
		} catch (Throwable e) {
			// All Exceptions will end up at least here
		}
		Thread.currentThread().stop();
	}
}
