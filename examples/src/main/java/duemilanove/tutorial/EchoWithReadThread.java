package duemilanove.tutorial;

import static haiku.avr.lib.arduino.WProgram.*;
import static haiku.avr.ATmega328p.*;

/**
 * call with:
 * C:\haikuVM\myCProject
 * C:\haikuVM\bin\haiku -v --Config duemilanove C:\haikuVM\examples\src\main\java\duemilanove\tutorial\EchoWithReadThread.java
 *
 * Enter:
ABCDEFGHIJKLMNOPQRSTUVWXYZ,ABCDEFGHIJKLMNOPQRSTUVWXYZ,ABCDEFGHIJKLMNOPQRSTUVWXYZ,ABCDEFGHIJKLMNOPQRSTUVWXYZ,ABCDEFGHIJKLMNOPQRSTUVWXYZ!
 */
public class EchoWithReadThread {
	static final int MAX=100;
	static final char buffer[]= new char[MAX];
	static int head, tail, len;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		init();
	    Serial.begin(19200);
        Serial.println("Please start typing");
        
        new Thread("reader") {
        	public void run() {
        		while (true) {
        	       	while ((UCSR0A & (1 << RXC0)) == 0) Thread.yield();
        	       	len++;
        	       	buffer[tail++]=(char)UDR0;
        			if (tail>MAX) tail=0;
        		}
        	}
        }.start();
        
	    for(int i=0; ;i++) {
	       	int c;
            while((c=read())<0) Thread.yield();
            Serial.println(i+":"+(char)c);
	    }
	}

	private static int read() {
		if (len==0) return -1;
		len--;
		int c=buffer[head++];
		if (head>MAX) head=0;
		return c;
	}

}
