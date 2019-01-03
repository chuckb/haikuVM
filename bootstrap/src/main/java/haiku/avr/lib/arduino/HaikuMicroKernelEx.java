package haiku.avr.lib.arduino;

import haiku.vm.HaikuMagic;

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
public class HaikuMicroKernelEx extends haiku.vm.MicroKernel {

    public static void init() {
        System.out = new PrintStream(new OutputStream() {

            @Override
            /**
             * Wrong or not thread save:
             * whitout synchronize can not be garantied the loop
             * is executed in thread A but 
             * setMemory8(..) is executed in thread b.
             * Which leads to some data b lost. 
             */
            public void write(int b) throws IOException {
                while ((UCSR0A & (1 << UDRE0)) == 0)
                    ;

                UDR0 = b;
            }
        });
        System.err = System.out;

        System.in = new InputStream() {
            public int available() {
                if ((UCSR0A & (1 << RXC0)) != 0)
                    return 1;
                return 0;
            }

            public int read() {
                while (available() == 0)
                    ;

                return UDR0;
            }
        };
    }

    public static void main(String[] args) {
        try {
            ArduinoLib.init();

            clinitHaikuMagic();

            init();

            /**
             * The following call of panic() and consequently the
             * method body panic() will be extincted from code if
             * property Target.PanicSupport is set to 0 ..
             * --Config:PanicSupport=0 ..
             */
            panic(0, 0);
            HaikuMagic.main(args); // Will call your main method (by
                                            // some HaikuVM magic)

        } catch (Throwable e) {
            System.out.println(e.toString());
        }
        Thread.currentThread().stop();
    }
}
