package arduino.tutorial;

import static haiku.avr.lib.arduino.WProgram.*;
/**
 */
public class IRSearch implements Runnable {
	static int dist=0;
	static String  line="##########"+"##########"+"##########"+"##########"+"##########"+"##########"+"##########"+"##########"+"##########"+"##########";
	
	public void run() {
		while(true) {
			Serial.println(line.substring(0, 80-(int)map(dist, 0, 800, 0,80)));
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		init();
	    Serial.begin(57600);
		
		new Thread(new IRSearch()).start();

	    while(true) {
	       	dist=analogRead(0);
	    }
	}

}
