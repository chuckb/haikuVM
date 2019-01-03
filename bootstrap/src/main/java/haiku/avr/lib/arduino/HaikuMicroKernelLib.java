package haiku.avr.lib.arduino;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.String;
import java.lang.Thread;
import java.lang.Throwable;
import static haiku.avr.ATmega328p.*;

/**
 * Skeleton for Haiku VM organizing the start of the target program.
 * 
 */
public class HaikuMicroKernelLib extends haiku.vm.MicroKernel {

    private static native void init();
    private static native int read();
    private static native int available();
    private static native void write(int b);
    
    public static native void debug(int marker);
 
    public static void initJava() {
        System.out = new PrintStream(new OutputStream() {

            @Override
            public void write(int b) throws IOException {
                HaikuMicroKernelLib.write(b);
            }
        });
        System.err = System.out;

        System.in = new InputStream() {
            public int available() {
                return HaikuMicroKernelLib.available();
            }

            public int read() {
                while (available() == 0)
                    ;

                return HaikuMicroKernelLib.read();
            }
        };
    }

    public static void main(String[] args) {
        try {
            init();
            initJava();

            clinitHaikuMagic();

            /**
             * The following call of panic() and consequently the
             * method body panic() will be extincted from code if
             * property Target.PanicSupport is set to 0 ..
             * --Config:PanicSupport=0 ..
             */
            panic(0, 0);
            haiku.vm.HaikuMagic.main(args); // Will call your main method (by
                                            // some HaikuVM magic)

        } catch (Throwable e) {
            System.out.println("Throwable: "+e.toString());
        }
        Thread.currentThread().stop();
    }
    
}
