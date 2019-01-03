package processing.hardware.arduino.cores.arduino;

import haiku.vm.NativeCVariable;

public class Arduino {
    /*
     * Generated with Arduino_h_Converter for HaikuVM from file: 'Arduino.h'
     */

    public static final int HIGH = 0x1;
    public static final int LOW = 0x0;

    public static final int INPUT = 0x0;
    public static final int OUTPUT = 0x1;
    public static final int INPUT_PLLUP = 0x2;

    public static final double PI = 3.1415926535897932384626433832795;
    public static final double HALF_PI = 1.5707963267948966192313216916398;
    public static final double TWO_PI = 6.283185307179586476925286766559;
    public static final double DEG_TO_RAD = 0.017453292519943295769236907684886;
    public static final double RAD_TO_DEG = 57.295779513082320876798154814105;

    public static final int SERIAL = 0x0;
    public static final int DISPLAY = 0x1;

    public static final int LSBFIRST = 0;
    public static final int MSBFIRST = 1;

    public static final int CHANGE = 1;
    public static final int FALLING = 2;
    public static final int RISING = 3;

    public static final int DEFALT = 0;
    public static final int EXTERNAL = 1;
    public static final int INTERNAL = 2;

    public static final int INTERNAL1V1 = 2;
    public static final int INTERNAL2V56 = 3;
    
    public static final int DEC = 10;
    public static final int HEX = 16;
    public static final int OCT = 8;
    public static final int BIN = 2;

    //@NativeCVariable
    public static HardwareSerial Serial = new HardwareSerial();

    public static int min(int a, int b) {
        return ((a) < (b) ? (a) : (b));
    }

    public static int max(int a, int b) {
        return ((a) > (b) ? (a) : (b));
    }

    public static int abs(int x) {
        return ((x) > 0 ? (x) : -(x));
    }

    public static int constrain(int amt, int low, int high) {
        return ((amt) < (low) ? (low) : ((amt) > (high) ? (high) : (amt)));
    }

   public static double min(double a, double b) {
        return ((a) < (b) ? (a) : (b));
    }

    public static double max(double a, double b) {
        return ((a) > (b) ? (a) : (b));
    }

    public static double abs(double x) {
        return ((x) > 0 ? (x) : -(x));
    }

    public static double constrain(double amt, double low, double high) {
        return ((amt) < (low) ? (low) : ((amt) > (high) ? (high) : (amt)));
    }

    public static long round(double x) {
        return ((x) >= 0 ? (long) ((x) + 0.5) : (long) ((x) - 0.5));
    }

    public static double radians(double deg) {
        return ((deg) * DEG_TO_RAD);
    }

    public static double degrees(double rad) {
        return ((rad) * RAD_TO_DEG);
    }

    public static int sq(int x) {
        return ((x) * (x));
    }

    public static long sq(long x) {
        return ((x) * (x));
    }

    public static double sq(double x) {
        return ((x) * (x));
    }

    public static void interrupts() {
     // no meaning in JAVA sei();
    }

    public static void noInterrupts() {
     // no meaning in JAVA cli();
    }

    public static int clockCyclesPerMicrosecond() {
        return (int) (ArduinoImpl.F_CPU / 1000000L);
    }

    public static long clockCyclesToMicroseconds(int a) {
        return ((a) / clockCyclesPerMicrosecond());
    }

    public static long microsecondsToClockCycles(int a) {
        return ((a) * clockCyclesPerMicrosecond());
    }

    public static int lowByte(int w) {
        return ((byte) ((w) & 0xff));
    }

    public static int highByte(int w) {
        return ((byte) ((w) >> 8));
    }

    public static int bitRead(int value, int bit) {
        return (((value) >> (bit)) & 0x01);
    }

    public static int bitSet(int value, int bit) {
        return ((value) |= (1L << (bit)));
    }

    public static int bitClear(int value, int bit) {
        return ((value) &= ~(1L << (bit)));
    }

    public static int bitWrite(int value, int bit, int bitvalue) {
        return (bitvalue > 0 ? bitSet(value, bit) : bitClear(value, bit));
    }

    public static int bit(int b) {
        return (1 << (b));
    }

    public static long bitRead(long value, int bit) {
        return (((value) >> (bit)) & 0x01);
    }

    public static long bitSet(long value, int bit) {
        return ((value) |= (1L << (bit)));
    }

    public static long bitClear(long value, int bit) {
        return ((value) &= ~(1L << (bit)));
    }

    public static long bitWrite(long value, int bit, int bitvalue) {
        return (bitvalue > 0 ? bitSet(value, bit) : bitClear(value, bit));
    }

    public static long bit(long b) {
        return (1L << (b));
    }

    public static void init() {
        ArduinoImpl.init();
    }

    public static void pinMode(int p0, int p1) {
        ArduinoImpl.pinMode((byte)p0, (byte)p1);
    }

    public static void digitalWrite(int p0, int p1) {
        ArduinoImpl.digitalWrite((byte)p0, (byte)p1);
    }

    public static int digitalRead(int p0) {
        return ArduinoImpl.digitalRead((byte)p0);
    }

    public static int analogRead(int p0) {
        return ArduinoImpl.analogRead((byte)p0);
    }

    public static void analogReference(int mode) {
        ArduinoImpl.analogReference((byte)mode);
    }

    public static void analogWrite(int p0, int p1) {
        ArduinoImpl.analogWrite((byte)p0, (byte)p1);
    }

    public static long millis() {
        return ArduinoImpl.millis();
    }

    public static long micros() {
        return ArduinoImpl.micros();
    }

    public static void delay(long ms) {
        ArduinoImpl.delay(ms);
    }

    public static void delayMicroseconds(int us) {
        ArduinoImpl.delayMicroseconds(us);
    }

    public static long pulseIn(byte pin, byte state, long timeout) {
        return ArduinoImpl.pulseIn(pin, state, timeout);
    }

    public static void shiftOut(byte dataPin, byte clockPin, byte bitOrder,
            byte val) {
        ArduinoImpl.shiftOut(dataPin, clockPin, bitOrder, val);
    }

    public static byte shiftIn(byte dataPin, byte clockPin, byte bitOrder) {
        return ArduinoImpl.shiftIn(dataPin, clockPin, bitOrder);
    }

    // public static void attachInterrupt(byte, void (*)() p0, int mode) {
    // return Impl.(*)(p0, mode);
    // }

    public static void detachInterrupt(byte p0) {
        ArduinoImpl.detachInterrupt(p0);
    }

//    public static void setup() {
//        Impl.setup();
//    }
//
//    public static void loop() {
//        Impl.loop();
//    }

    // public static int digitalPinToPort(int P) {
    // return ( pgm_read_byte( digital_pin_to_port_PGM + (P) ) );
    // }
    //
    // public static int digitalPinToBitMask(int P) {
    // return ( pgm_read_byte( digital_pin_to_bit_mask_PGM + (P) ) );
    // }
    //
    // public static int digitalPinToTimer(int P) {
    // return ( pgm_read_byte( digital_pin_to_timer_PGM + (P) ) );
    // }
    //
     public static int analogInPinToBit(int P) {
         return (P);
     }
    //
    // public static int portOutputRegister(int P) {
    // return ( (uint8_t *)( pgm_read_word( port_to_output_PGM + (P))) );
    // }
    //
    // public static int portInputRegister(int P) {
    // return ( (uint8_t *)( pgm_read_word( port_to_input_PGM + (P))) );
    // }
    //
    // public static int portModeRegister(int P) {
    // return ( (uint8_t *)( pgm_read_word( port_to_mode_PGM + (P))) );
    // }

    public static final int NOT_A_PIN = 0;
    public static final int NOT_A_PORT = 0;

    public static final int A0 = 0;
    public static final int A1 = 1;
    public static final int A2 = 2;
    public static final int A3 = 3;
    public static final int A4 = 4;
    public static final int A5 = 5;
    public static final int PA = 1;
    public static final int PB = 2;
    public static final int PC = 3;
    public static final int PD = 4;
    public static final int PE = 5;
    public static final int PF = 6;
    public static final int PG = 7;
    public static final int PH = 8;
    public static final int PJ = 10;
    public static final int PK = 11;
    public static final int PL = 12;

    public static final int NOT_ON_TIMER = 0;
    public static final int TIMER0A = 1;
    public static final int TIMER0B = 2;
    public static final int TIMER1A = 3;
    public static final int TIMER1B = 4;
    public static final int TIMER2 = 5;
    public static final int TIMER2A = 6;
    public static final int TIMER2B = 7;

    public static final int TIMER3A = 8;
    public static final int TIMER3B = 9;
    public static final int TIMER3C = 10;
    public static final int TIMER4A = 11;
    public static final int TIMER4B = 12;
    public static final int TIMER4C = 13;
    public static final int TIMER4D = 14;
    public static final int TIMER5A = 15;
    public static final int TIMER5B = 16;
    public static final int TIMER5C = 17;

    public static int makeWord(int w) {
        return ArduinoImpl.makeWord(w);
    }

    public static int makeWord(byte h, byte l) {
        return ArduinoImpl.makeWord(h, l);
    }

    // public static int word(int ...) {
    // return makeWord(__VA_ARGS__);
    // }

    public static long pulseIn(byte pin, byte state) {
        return pulseIn(pin, state, 1000000L);
    }

    public static void tone(int _pin, int frequency) {
        ArduinoImpl.tone((byte)_pin, frequency, 0);
    }

    public static void tone(int _pin, int frequency, long duration) {
        ArduinoImpl.tone((byte)_pin, frequency, duration);
    }

    public static void noTone(int _pin) {
        ArduinoImpl.noTone((byte)_pin);
    }

    public static long random(long p0) {
        return ArduinoImpl.random(p0);
    }

    public static long random(long p0, long p1) {
        return ArduinoImpl.random(p0, p1);
    }

    public static void randomSeed(int p0) {
        ArduinoImpl.randomSeed(p0);
    }

    public static long map(long x, long in_min, long in_max, long out_min, long out_max) {
        return ArduinoImpl.map(x, in_min, in_max, out_min, out_max);
    }

}
