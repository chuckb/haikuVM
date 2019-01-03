package haiku.pc.lib;

import haiku.vm.HaikuMagic;
import haiku.vm.NativeCFunction;

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
public class HaikuMicroKernel extends haiku.vm.MicroKernel {

	public static void init() {
		System.out=new PrintStream(new OutputStream() {
			@Override
			public void write(int b) throws IOException {
			    os_putchar(b);
			}});
        System.err = System.out;

        System.in = new InputStream() {
            public int available() {
                return os_available();
            }

            public int read() {
                return os_getchar();
            }
        };
	}
	
    @NativeCFunction
    protected static native int os_putchar(int b);

    @NativeCFunction
    protected static native int os_getchar();

    @NativeCFunction
    protected static native int os_available();

    public static void main(String[] args) {
		try {
		    clinitHaikuMagic();		
			
			init();
			
			/**
			 * The following call of panic()
			 * and consequently the method body panic() 
			 * will be extingted from code if property PanicSupport is set to 0
			 * .. -DPanicSupport=0 ..
			 */
			panic(0, 0);
			
			HaikuMagic.main(args); // Will call your main method (by some HaikuVM magic)
			
		} catch (Throwable e) {
			System.out.println(e.toString());
		}
		Thread.currentThread().stop();
	}
}
