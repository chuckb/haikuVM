package haiku.arduino.api;

import haiku.vm.NativeCFunction;
import haiku.vm.NativeCppFunction;

public class ArduinoImpl {
    public static final long F_CPU = 16000000L;

    /*
     * Generated with Arduino_h_Converter for HaikuVM from file: 'Arduino.h'
     */

    @NativeCFunction
    public static native void init();

    @NativeCFunction
    public static native void pinMode(byte p0, byte p1);

    @NativeCFunction
    public static native void digitalWrite(byte p0, byte p1);

    @NativeCFunction
    public static native int digitalRead(byte p0);

    @NativeCFunction
    public static native int analogRead(byte p0);

    @NativeCFunction
    public static native void analogReference(byte mode);

    @NativeCFunction
    public static native void analogWrite(byte p0, int p1);

    @NativeCFunction
    public static native long millis();

    @NativeCFunction
    public static native long micros();

    public static void delay(long ms) {
        Thread.nap(ms);
    }

    @NativeCFunction
    public static native void delayMicroseconds(int us);

    @NativeCFunction
    public static native long pulseIn(byte pin, byte state, long timeout);

    @NativeCFunction
    public static native void shiftOut(byte dataPin, byte clockPin, byte bitOrder, byte val);

    @NativeCFunction
    public static native byte shiftIn(byte dataPin, byte clockPin, byte bitOrder);

//    @NativeCFunction
//    public static native void attachInterrupt(byte, void (*)() p0, int mode);

    @NativeCFunction
    public static native void detachInterrupt(byte p0);

    @NativeCFunction
    public static native void setup();

    @NativeCFunction
    public static native void loop();

    @NativeCFunction
    public static native int makeWord(int w);

    @NativeCFunction
    public static native int makeWord(byte h, byte l);

//    @NativeCFunction
//    public static native long pulseIn(byte pin, byte state, long timeout = 1000000L);

    @NativeCFunction
    public static native void tone(byte _pin, int frequency, long duration);

    @NativeCFunction
    public static native void noTone(byte _pin);

    @NativeCppFunction
    public static native long random(long p0);

    @NativeCppFunction
    public static native long random(long p0, long p1);

    @NativeCppFunction
    public static native void randomSeed(int p0);

    @NativeCppFunction
    public static native long map(long p0, long p1, long p2, long p3, long p4);

}
