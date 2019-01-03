package avr.gallerie.user.TimReset;

import static haiku.avr.lib.arduino.WProgram.*;
/**
 * call with:
 * C:\haikuVM\bin\haiku -v --Config arduino C:\haikuVM\examples\src\main\java\arduino\tutorial\Echo.java
 * 
 * 
 * Incorrect read long string from Serial 
 * see: https://sourceforge.net/p/haiku-vm/discussion/general/thread/bacfa829/
 * 
 * 

Good day!

I upload arduino.tutorial.Echo to Arduino Nano, connect with it and write string longer 3 symbols and get result not all symbols.

Example:
Write: Hello!

Result:
Please start typing
0: H
1: e
2: l
3: !

Missing symbol "l".

I tried read from serial with System.in - result same.

I write same code on C++:

void loop() {
if (Serial.available() > 0) {
Serial.println(Serial.readString());
}
}

It's work correct.

I read source code of function Serial.readString() - it's contains "timedRead()":

int Stream::timedRead()
{
int c;
_startMillis = millis();
do {
c = read();
if (c >= 0) return c;
} while(millis() - _startMillis < _timeout);
return -1; // -1 indicates timeout
}

I wrote similar function on Java, but it's not has effect.

Do have any idea why symbols disappear?
 ******************
 *
Back to your last problem:
"For greater string the program hanging :( And I don't know why."

I guess the reason is heap fragmentation. After a while of allocating and collecting this

char[] chars = new char[500];
together with
new String(chars, 0, pos)

There is no contiguous 500 char space on the heap.

Try to make it static:

static char[] chars = new char[500];

 *
 * now call with:
 * C:\haikuVM\bin\haiku -v --Config arduino --Config:PanicSupport 1 --Config:PanicExceptions 0xffff C:\haikuVM\gallerie\src\main\java/avr/gallerie/user/TimReset/Echo.java
 * 
 * to see StackOverflowError | OutOfMemoryError
 * 
 */
public class Echo {
    static final int maxLen = 400;
    static char[] chars = new char[maxLen];
	
    static String readString() {
        int pos = 0;
       	int c;
        int startMillis = 0;
        while (pos < maxLen) {
            if ((c=Serial.read())>=0 ) {
                chars[pos] = (char) c;
                pos++;
                startMillis = 0;
            } else if (startMillis >= 1000) {  
            	break;
            };
            startMillis++;
        }
        return new String(chars, 0, pos);
    }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		init();
	    Serial.begin(57600);
        Serial.println("Please start typing");

	    for(int i=0; ;i++) {
	       	String in=readString();
            for(int k=0; k<in.length(); k++) {
                Serial.println(k+": "+in.charAt(k));
            }
	    }
	}

}
