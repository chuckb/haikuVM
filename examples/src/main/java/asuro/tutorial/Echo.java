package asuro.tutorial;

import static haiku.avr.lib.asuro.lib2_8_0_rc1.AsuroLib2_8_0_rc1.*;
/**
 */
public class Echo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        Init();
        SerPrint("Please start typing\r\n");

	    for(int i=0; ;i++) {
	       	char c=(char)UartGetc();
            PrintInt(i);
            SerPrint(": ");
            UartPutc(c);
            SerPrint("\r\n");
	    }
	}

}
