package avr.gallerie.user.TimReset;
import haiku.arduino.api.ArduinoImpl;
import haiku.vm.NativeCFunction;

import static haiku.avr.AVRConstants.*;
import static haiku.avr.lib.arduino.WProgram.*;
import static processing.hardware.arduino.cores.arduino.Arduino.Serial;

/**
 * 
 * I tested like this:
 * cd C:\Arduino-1.6.4\libraries
 * C:\haikuVM\bin\haiku -v --Config arduinoIDEUpload --Mode 16/32 C:\haikuVM\gallerie\src\main\java\avr\gallerie/user\TimReset\IRremote.java
 *
 */
public class IRremote {
    private static final long SYSCLOCK = ArduinoImpl.F_CPU;
    // Pulse parms are *50-100 for the Mark and *50+100 for the space
    // First MARK is the one after the long gap
    // pulse parameters in usec
    
    
    static final int NEC_OFFSET = 204; // offset found by experiments
    static final int NEC_HDR_MARK = 9000-NEC_OFFSET;
    static final int NEC_HDR_SPACE = 4500-NEC_OFFSET;
    static final int NEC_BIT_MARK = 560-NEC_OFFSET-140; // offset found by experiments
    static final int NEC_ONE_SPACE = 1600-NEC_OFFSET;
    static final int NEC_ZERO_SPACE = 560-NEC_OFFSET;
    static final int NEC_RPT_SPACE = 2250-NEC_OFFSET;

    static final long TOPBIT = 0x80000000L; //0b10000000000000000000000000000000;//max_int

    static final long SPEAKER_IR_POWER =   0x807f40bfL; //2155823295L; //  0b10000000011111110100000010111111;

    static private byte ledPin = 3;
    
//    static private long t0,t1;
//    static private int cnt;
//    static private int info[]= new int[70];
//
//    @NativeCFunction
//    private static native long micros();

    @NativeCFunction
    private static native void delayMicroseconds(int time);

    private static void mark(int time) {
        TIMER_ENABLE_PWM();
        //      Arduino.delayMicroseconds(time);
        delayMicroseconds(time);
        //t1=micros(); info[cnt++]=(int)(t1-t0); t0=t1;
    }

    /* Leave pin off for time (given in microseconds) */
    private static void space(int time) {
        //      digitalWrite(ledPin, LOW);
        TIMER_DISABLE_PWM();
        //      Arduino.delayMicroseconds(time);
        delayMicroseconds(time);
        //t1=micros(); info[cnt++]=(int)(t1-t0); t0=t1;
    }

	private static void sendNEC(long data, int nbits) {
        Serial.println("Send " + data + "; TOPBIT " + TOPBIT + "; nbits " + nbits);
        enableIROut(38);

        //t0=micros();
        mark(NEC_HDR_MARK);
        space(NEC_HDR_SPACE);
        for (int i = 0; i < nbits; i++) {
            if ((data & TOPBIT) != 0) {
                mark(NEC_BIT_MARK);
                space(NEC_ONE_SPACE);
            } else {
                mark(NEC_BIT_MARK);
                space(NEC_ZERO_SPACE);
            }
            data <<= 1;
        }
        mark(NEC_BIT_MARK);
        space(0);
        //t1=micros();
        //Serial.println("Sum: "+(t1-t0));
    }

    private static void enableIROut(int khz) {
        TIMER_DISABLE_INTR(); //Timer2 Overflow Interrupt

        pinMode(ledPin, OUTPUT);
        digitalWrite(ledPin, LOW);

        TIMER_CONFIG_KHZ(khz);
    }

    private static void TIMER_ENABLE_PWM() {
        TCCR2A |= _BV(COM2B1);
    }

    private static void TIMER_DISABLE_PWM() {
        TCCR2A &= ~(_BV(COM2B1));
    }

    private static void TIMER_CONFIG_KHZ(int khz) {
        int pwmval = (int) SYSCLOCK / 2000 / khz;
        TCCR2A = _BV(WGM20);
        TCCR2B = _BV(WGM22) | _BV(CS20);
        OCR2A = pwmval;
        OCR2B = pwmval / 3;
    }

    private static void TIMER_DISABLE_INTR() {
        TIMSK2 = 0;
    }

//    public static void main(String[] args) {
//
//        init();
//        Serial.begin(57600);
//        while (true) {
//            sendNEC(SPEAKER_IR_POWER, 32);
//            delay(2000);
//        }
//    }
    
	public static void setup() {
	    Serial.begin(57600);
        while (!Serial.isOpen()) {
            ; // wait for serial port to connect. Needed for Leonardo only
        }
	}
	
	// The loop function is called in an endless loop
	public static void loop() {
        delay(2000);
        sendNEC(SPEAKER_IR_POWER, 32);
//        for(int i=0; i<cnt; i+=2) {
//        	Serial.println((1000+i)+":\t"+info[i]);
//        	Serial.println("    :\t"+info[i+1]);
//        }
//        cnt=0;
    }
}
