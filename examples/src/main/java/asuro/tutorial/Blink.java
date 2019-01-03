package asuro.tutorial;

import static haiku.avr.lib.asuro.lib2_8_0_rc1.AsuroLib2_8_0_rc1.*;

/**
 * The status LED of the ASURO will blink.
 * 
 * mkdir myProject 
 * cd myProject 
 * C:\haikuVM\bin\haikuc                                   C:\haikuVM\examples\src\main\java\asuro\tutorial\Blink.java
 * C:\haikuVM\bin\haikulink -v --Config asuro -o Blink.hex C:\haikuVM\examples\src\main\java\asuro\tutorial\Blink
 * C:\haikuVM\bin\haikuupload                    Blink.hex
 * 
 * @author genom2
 */
public class Blink {
    public static void main(String[] args) {
        Init(); // initialize the ASURO

        while (true) {
            StatusLED(RED);
            Msleep(1000);
            StatusLED(GREEN);
            Msleep(1000);
        }
    }
}
