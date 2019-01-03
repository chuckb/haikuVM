package haiku.avr.lib.arduino;

import static haiku.avr.ATmega328p.*;
import static haiku.avr.AVRDefines.*;
import static haiku.avr.lib.arduino.WProgram.*;
import static haiku.vm.MemoryAccess.*;

/**
 * Moved to JAVA by genom2. Source was
 * Servo.h - Hardware Servo Timer Library Author: Jim Studt, jim@federated.com
 * Copyright (c) 2007 David A. Mellis. All right reserved.
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 */
public class Servo {
	public static final byte NO_ANGLE = (byte)(0xff);

	byte pin;
	byte angle; // in degrees
	int min; // minimum pulse, 1uS units (default is 544)
	int max; // maximum pulse, 1uS units, 0-4ms range (default is 2400)

	static boolean attached9 = false;
	static boolean attached10 = false;

	static private void seizeTimer1() {
		//byte oldSREG = SREG;

		//cli();
		TCCR1A = _BV(WGM11); /* Fast PWM, ICR1 is top */
		TCCR1B = _BV(WGM13)
			| _BV(WGM12) /* Fast PWM, ICR1 is top */
			| _BV(CS11) /* div 8 clock prescaler */;
		OCR1A = 3000;
		OCR1B = 3000;
		// 20000 uS is a bit fast for the refresh, 20ms, but
		// it keeps us from overflowing ICR1 at 20MHz clocks
		// That "/8" at the end is the prescaler.
		ICR1 = (int)(clockCyclesPerMicrosecond() * (20000L / 8)); 
		if (__AVR_ATmega8__) {
			TIMSK &= ~(_BV(TICIE1) | _BV(OCIE1A) | _BV(OCIE1B) | _BV(TOIE1));
		} else {
			TIMSK1 &= ~(_BV(OCIE1A) | _BV(OCIE1B) | _BV(TOIE1));
		}

		//setMemory8(SREG, oldSREG); // undo cli()
	}

	static private void releaseTimer1() {
	};

	public Servo() {
		pin = 0;
		angle = NO_ANGLE;
	}

	public byte attach(int pinArg) {
		return attach(pinArg, 544, 2400);
	}

	public byte attach(int pinArg, int min, int max) {
		if (pinArg != 9 && pinArg != 10)
			return 0;

		this.min = min;
		this.max = max;

		pin = (byte) pinArg;
		angle = NO_ANGLE;
		digitalWrite(pin, LOW);
		pinMode(pin, OUTPUT);

		if (!attached9 && !attached10)
			seizeTimer1();

		if (pin == 9) {
			attached9 = true;
			TCCR1A = (TCCR1A & ~_BV(COM1A0)) | _BV(COM1A1);
		}

		if (pin == 10) {
			attached10 = true;
			TCCR1A = (TCCR1A & ~_BV(COM1B0)) | _BV(COM1B1);
		}
		return 1;
	}

	public void detach() {
		// muck with timer flags
		if (pin == 9) {
			attached9 = false;
			TCCR1A &= ~_BV(COM1A0) & ~_BV(COM1A1);
			pinMode(pin, INPUT);
		}

		if (pin == 10) {
			attached10 = false;
			TCCR1A &= ~_BV(COM1B0) & ~_BV(COM1B1);
			pinMode(pin, INPUT);
		}

		if (!attached9 && !attached10)
			releaseTimer1();
	}

	public void write(int angleArg) {
		int p;

		if (angleArg < 0)
			angleArg = 0;
		if (angleArg > 180)
			angleArg = 180;
		angle = (byte) angleArg;

		// bleh, have to use longs to prevent overflow, could be tricky if
		// always a 16MHz clock, but not true
		// That 8L on the end is the TCNT1 prescaler, it will need to change if
		// the clock's prescaler changes,
		// but then there will likely be an overflow problem, so it will have to
		// be handled by a human.
		p = (int) (map(angleArg, 0, 180, min, max)*16/8);
		if (pin == 9)
			OCR1A = p;
		if (pin == 10)
			OCR1B = p;
	}

	public int read() {
		return angle&0xff;
	}

	public boolean attached() {
		if (pin == 9 && attached9)
			return true;
		if (pin == 10 && attached10)
			return true;
		return false;
	}

    public static void on() {
        seizeTimer1();        
        if (attached9) {
            digitalWrite((byte)9, LOW);
            pinMode((byte)9, OUTPUT);
            TCCR1A = (TCCR1A & ~_BV(COM1A0)) | _BV(COM1A1);
        }

        if (attached10) {
            digitalWrite((byte)10, LOW);
            pinMode((byte)10, OUTPUT);
            TCCR1A = (TCCR1A & ~_BV(COM1B0)) | _BV(COM1B1);
        }
    }

    public static void off() {
        if (attached9) {
            TCCR1A &= ~_BV(COM1A0) & ~_BV(COM1A1);
            pinMode((byte)9, INPUT);
        }

        if (attached10) {
            TCCR1A &= ~_BV(COM1B0) & ~_BV(COM1B1);
            pinMode((byte)10, INPUT);
        }

        if (!attached9 && !attached10)
            releaseTimer1();
    }

}
