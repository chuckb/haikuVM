package processing.examples._99_Others;


import static processing.hardware.arduino.cores.arduino.Arduino.*;

//#include <Servo.h> 
import haiku.avr.lib.arduino.Servo;

/**
 * Sweep by BARRAGAN <http://barraganstudio.com> This example code is in the
 * public domain.
 * 
 */
public class Sweep {

	/**
	 * create servo object to control a servo a maximum of eight servo objects
	 * can be created
	 */
	static Servo myservo = new Servo();

	/**
	 * variable to store the servo position
	 */
	static int pos = 0;

	static void setup() {
		myservo.attach(9); // attaches the servo on pin 9 to the servo object
	}

	public static void loop() {
		// goes from 0 degrees to 180 degrees
		// in steps of 1 degree
		for (pos = 0; pos < 180; pos += 1) 
		{ 
			// tell servo to go to position in variable 'pos'
			myservo.write(pos); 
			delay(15); // waits 15ms for the servo to reach the position
		}
		// goes from 180 degrees to 0 degrees
		for (pos = 180; pos >= 1; pos -= 1)
		{
			// tell servo to go to position in variable 'pos'
			myservo.write(pos); 
			delay(15); // waits 15ms for the servo to reach the position
		}
	}
}
