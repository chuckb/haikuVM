package de.javamagazin;

import static haiku.avr.AVRConstants.*;

/**
 * The status LED of the Duemilanove will blink. 
 * 
 * mkdir myProject 
 * cd myProject
 * C:\haikuVM\bin\haiku -v --Config duemilanove C:\haikuVM\examples\src\main\java\de\javamagazin\BlinkWithThread.java
 * 
 * @author genom2
 */
public class BlinkWithThread {
    // LED1 is connected with PORTB and Bit 5.
    // LED2 is connected with PORTB and Bit 4.
    // give them a name:
    private static final int LED1 = 1 << 5;
    private static final int LED2 = 1 << 4;

    private static void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
        }
    };
    
    public static void main(String[] args) throws InterruptedException {
        // initialize the digital pins as an output.
        DDRB |= (LED1|LED2);
        
        new Thread("for LED2") {
            public void run() {
                // the loop runs forever:
                while (true) {
                    PORTB |= LED2;  // turn the LED2 on (HIGH is the voltage level)
                    delay(500);     // wait for 500 milliseconds
                    PORTB &= ~LED2; // turn the LED2 off by making the voltage LOW
                    delay(500);     // wait for a second
                }
            }
        }.start();
        
        // the loop runs forever:
        while (true) {
            PORTB |= LED1;  // turn the LED1 on (HIGH is the voltage level)
            delay(1000);    // wait for a second
            PORTB &= ~LED1; // turn the LED1 off by making the voltage LOW
            delay(1000);    // wait for a second
        }
    }
}
