package haiku.avr.lib.arduino;

import static haiku.avr.ATmega328p.*;

public class WProgram extends ArduinoLib {
	public static final byte LOW = 0;
	public static final byte HIGH = 1;
	public static final byte INPUT = 0;
	public static final byte OUTPUT = 1;
	public static final double HALF_PI = 1.57079;
	public static final double TWO_PI = 6.283185;
	public static final double DEG_TO_RAD = 0.01745329;
	public static final double RAD_TO_DEG = 57.2957786;
	public static final byte SERIAL = 0x0;
	public static final byte DISPLAY = 0x1;

	public static final byte LSBFIRST = 0;
	public static final byte MSBFIRST = 1;

	public static final byte CHANGE = 1;
	public static final byte FALLING = 2;
	public static final byte RISING = 3;

	public static final byte INTERNAL = 3;
	public static final byte DEFAULT = 1;
	public static final byte EXTERNAL = 0;

	/*
	 * wiring.h - Partial implementation of the Wiring API for the ATmega8. 
	 * Part of Arduino - http://www.arduino.cc/
	 * 
	 * Copyright (c) 2005-2006 David A. Mellis
	 * 
	 * 
	 * #define clockCyclesToMicroseconds(a) ( (a) / clockCyclesPerMicrosecond()) 
	 * 
	 * #define microsecondsToClockCycles(a) ( (a) * clockCyclesPerMicrosecond())
	 * 
	 * #define lowByte(w) ((w) & 0xff) 
	 * 
	 * #define highByte(w) ((w) >> 8)
	 * 
	 * #define bitRead(value, bit) (((value) >> (bit)) & 0x01) 
	 * 
	 * #define bitSet(value, bit) ((value) |= (1UL << (bit))) 
	 * 
	 * #define bitClear(value, bit) ((value) &= ~(1UL << (bit))) 
	 * 
	 * #define bitWrite(value, bit, bitvalue) (bitvalue ? bitSet(value, bit) : bitClear(value, bit))
	 * 
	 * typedef unsigned int word;
	 * 
	 * #define bit(b) (1 << (b))
	 * 
	 * typedef uint8_t boolean; 
	 * 
	 * typedef uint8_t byte;
	 * 
	 * void init(void);
	 * 
	 * int digitalRead(uint8_t); 
	 * 
	 * void analogReference(uint8_t mode);
	 *  
	 * void analogWrite(uint8_t, int);
	 * 
	 * unsigned long micros(void); 
	 * 
	 * void delayMicroseconds(unsigned int us);
	 * 
	 * unsigned long pulseIn(uint8_t pin, uint8_t state, unsigned long timeout);
	 * 
	 * void shiftOut(uint8_t dataPin, uint8_t clockPin, uint8_t bitOrder, byte val);
	 * 
	 * void attachInterrupt(uint8_t, void (*)(void), int mode); 
	 * 
	 * void detachInterrupt(uint8_t);
	 */

	public static haiku.avr.lib.arduino.HardwareSerial Serial = new haiku.avr.lib.arduino.HardwareSerial();
	

	/**
	 * #define interrupts() sei() 
	 */ 
	public static void interrupts() {
		 SREG|=0x80;
	}

	/**
	 * #define noInterrupts() cli()
	 */ 
	public static void noInterrupts() {
		 SREG&=~0x80;
	}

	public static int constrain(int amt, int low, int high) {
		return ((amt) < (low) ? (low) : ((amt) > (high) ? (high) : (amt)));
	};

	public static double round(double x) {
		return ((x) >= 0 ? (long) ((x) + 0.5) : (long) ((x) - 0.5));
	};

	public static double radians(double deg) {
		return ((deg) * DEG_TO_RAD);
	};

	public static double degrees(double rad) {
		return ((rad) * RAD_TO_DEG);
	};

	public static double sq(double x) {
		return ((x) * (x));
	};

	public static long sq(long x) {
		return ((x) * (x));
	};

	public static long millis() {
		return System.currentTimeMillis();
	}

	public static void delayMicroseconds(long us) {
		Thread.nap(us / 1000);
	}

	public static void delay(long ms) {
		Thread.nap(ms);
	}

	public static int clockCyclesPerMicrosecond() {
		return (int) (F_CPU / 1000000L);
	}

    /**
     *  0   PD,    PD,    PD,    PD,    PD,    PD,    PD,    PD,<br>
     *  8   PB,    PB,    PB,    PB,    PB,    PB,    PC,<br>
     * 14   PC,    PC,    PC,    PC,    PC,<br>
     * 19<br>
     * 
     * @param pin
     * @param mode
     */
	public static void pinMode(byte pin, byte mode) {
        if (pin<8) {
            int bit = 1 << (pin - 0);
            if (mode == INPUT)
                DDRD &= ~(bit);
            else
                DDRD |= bit;
        } else if (pin<14) {
            int bit = 1 << (pin - 8);
            if (mode == INPUT)
                DDRB &= ~(bit);
            else
                DDRB |= bit;
        } else if (pin<19) {
            int bit = 1 << (pin - 14);
            if (mode == INPUT)
                DDRC &= ~(bit);
            else
                DDRC |= bit;
        }
	}

	/**
	 *  0   PD,    PD,    PD,    PD,    PD,    PD,    PD,    PD,<br>
	 *  8   PB,    PB,    PB,    PB,    PB,    PB,    PC,<br>
	 * 14   PC,    PC,    PC,    PC,    PC,<br>
	 * 19<br>
	 * 
	 * @param pin
	 * @param val
	 */
    public static void digitalWrite(byte pin, byte val) {
        if (pin<8) {
            int bit = 1 << (pin - 0);
            if (val == LOW)
                PORTD &= ~(bit);
            else
                PORTD |= bit;
        } else if (pin<14) {
            int bit = 1 << (pin - 8);
            if (val == LOW)
                PORTB &= ~(bit);
            else
                PORTB |= bit;
        } else if (pin<19) {
            int bit = 1 << (pin - 14);
            if (val == LOW)
                PORTC &= ~(bit);
            else
                PORTC |= bit;
        }
    }
    /**
     *  0   PD,    PD,    PD,    PD,    PD,    PD,    PD,    PD,<br>
     *  8   PB,    PB,    PB,    PB,    PB,    PB,    PC,<br>
     * 14   PC,    PC,    PC,    PC,    PC,<br>
     * 19<br>
     * 
     * @param pin
     * @param val
     */
    public static int digitalRead(byte pin)
    {
        if (pin<8) {
            int bit = 1 << (pin - 0);
            if ((PORTD & bit)!=0) {
                return HIGH;
            }
        } else if (pin<14) {
            int bit = 1 << (pin - 8);
            if ((PORTB & bit)!=0) {
                return HIGH;
            }
        } else if (pin<19) {
            int bit = 1 << (pin - 14);
            if ((PORTC & bit)!=0) {
                return HIGH;
            }
        }
        return LOW;
    }

}
