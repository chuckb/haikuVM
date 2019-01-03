package processing.examples._04_Communication;

import static processing.hardware.arduino.cores.arduino.Arduino.*;

/**
 * Haikufy like this:
 *   cd C:\arduino\libraries
 *   C:\haikuVM\bin\haiku -v --Config arduinoIDE C:\haikuVM\examples\src\main\java\processing\examples\_04_Communication\Echo.java
 * Then use your Arduino IDE to upload the generated sketch.
 * 
 * or, more convenient, haikufy by using the Arduino IDE infrastructure, but let it compile, verify and upload from HaikuVM:
 *   cd C:\arduino\libraries
 *   C:\haikuVM\bin\haiku -v --Config arduinoIDEUpload C:\haikuVM\examples\src\main\java\processing\examples\_04_Communication\Echo.java
 * No need to start your Arduino IDE anymore. It's already uploaded.
 *   
 * 
 * With UNIX haikufy like this:
 *   cd /home/bob/arduino/libraries
 *   /home/bob/haikuVM/bin/haiku -v --Config arduinoIDE /home/bob/haikuVM/examples/src/main/java/processing/examples/_04_Communication/Echo.java
 * Then use your Arduino IDE to upload the generated sketch.
 * 
ABCDEFGHIJKLMNOPQRSTUVWXYZ,ABCDEFGHIJKLMNOPQRSTUVWXYZ,ABCDEFGHIJKLMNOPQRSTUVWXYZ,ABCDEFGHIJKLMNOPQRSTUVWXYZ,ABCDEFGHIJKLMNOPQRSTUVWXYZ!
 */
public class Echo {

    public static void setup() {
        // Open serial communications and wait for port to open:
        Serial.begin(57600);
        while (!Serial.isOpen()) {
            ; // wait for serial port to connect. Needed for Leonardo only
        }
        Serial.println("Please start typing:");
    }
    
    public static void loop() {
	    for(int i=0; ;i++) {
	       	int c;
            while((c=Serial.read())<0) ;
            Serial.println(i+": "+(char)c);
	    }
    }
}
