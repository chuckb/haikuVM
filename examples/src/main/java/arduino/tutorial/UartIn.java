package arduino.tutorial;

import static haiku.avr.lib.arduino.WProgram.*;
/**
avrdude: 10364 bytes of flash verified
"
ABCDEFGHIJKLMNOPQRSTUVWXY
ABCDEFGHIJKLMNOPQRSTUVWXY
ABCDEFGHIJKLMNOPQRSTUVWXY
ABCDEFGHIJKLMNOPQRSTUVWXY
ABCDEFGHIJKLMNOPQRSTUVWXY
ABCDEFGHIJKLMNOPQRSTUVWXY
ABCDEFGHIJKLMNOPQRSTUVWXY
ABCDEFGHIJKLMNOPQRSTUVWXY
ABCDEFGHIJKLMNOPQRSTUVWXY
ABCDEFGHIJKLMNOPQRSTUVWXY!
"
start reading
710 chars read in 686 msec -> 1.034985 chars/msec
 */
public class UartIn {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		init();
	    Serial.begin(57600);

	    while(true) {
	    	int i;
	       	int c;

	    	Serial.println("start reading");
	       	while(Serial.read()<0) ;
	    	long t0=millis();
	    	for(i=1; ; i++) {
	    		while((c=Serial.read())<0) ;
	    		if(c=='!') break;
	    	}
	    	long d=millis()-t0;
	    	Serial.print(i);
	    	Serial.print(" chars read in ");
	    	Serial.print(d);
	    	Serial.print(" msec -> ");
	    	Serial.print(1.0*i/d);
	    	Serial.println(" chars/msec");
	    }
	}

}
