package asuro.tutorial;

import static haiku.avr.lib.asuro.lib2_8_0_rc1.AsuroLib2_8_0_rc1.*;

public class HelloWorld {

    public static void main(String[] args) {
        Init();
        for (int i = 0;; i++) {
            PrintInt(i);
            SerPrint(" Hello World\r\n");
        }
    }
}
