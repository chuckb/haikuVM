package arduino.tutorial;

import static haiku.avr.lib.arduino.WProgram.*;
/**
avrdude: 10904 bytes of flash verified

For N=1: 710 chars read in 753 msec -> 0.942895 chars/msec
For N=2: 710 chars read in 864 msec -> 0.821759 chars/msec
For N=3: 540 chars read in 852 msec -> 0.6338028 chars/msec
For N=5: 350 chars read in 736 msec -> 0.475543 chars/msec
 */
public class UartInWithThreads implements Runnable {
	static boolean ready=false;
	private String label;
	
	public UartInWithThreads(String label) {
		this.label=label;
	}

	public void run() {
		while(!ready) {
			Serial.println(label);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final int N=2;
		
		init();
	    Serial.begin(57600);
		
		for (int i = 0; i < N; i++) {
			new Thread(new UartInWithThreads(""+(i+1))).start();
		}

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
	    	ready=true;
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
