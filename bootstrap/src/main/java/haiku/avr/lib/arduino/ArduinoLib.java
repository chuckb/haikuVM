/*****************************************************************************
 *                                                                            *
 *   This program is free software; you can redistribute it and/or modify     *
 *   it under the terms of the GNU General Public License as published by     *
 *   the Free Software Foundation; either version 2 of the License, or        *
 *   any later version.                                                       *
 *                                                                            *
 *****************************************************************************/
/**
 \file     leds.c

 \brief    Funktionen zur Steuerung der LED's.

 Die Port-Pin's zu den LED's werden so manipuliert, dass sie die daran\n
 angeschlossenen LED's mit Strom versorgen oder eben nicht. Dazu muessen die\n
 entsprechenden Ports als Ausgang konfiguriert sein. Dies erfolgt entweder\n
 schon in der Init()-Funktion, oder wird im Falle der BackLED()-Funktion\n
 dort vorgenommen.

 \see      Defines fuer die Auswahl der ADC-Kanaele in asuro.h\n
 ON, OFF, GREEN, YELLOW, RED

 \version  V--- - 10.11.2003 - Jan Grewe - DLR\n
 Original Version von der ASURO CD\n
 \version  V--- - bis zum 07.01.2007 - \n
 Bitte in Datei CHANGELOG nachsehen.\n
 \version  V001 - 13.01.2007 - m.a.r.v.i.n\n
 +++ Alle Funktionen\n
 Zerlegte Sourcen in einzelne Dateien fuer eine echte Library.
 \version  V002 - 05.02.2007 - Sternthaler\n
 +++ Alle Funktionen\n
 Kommentierte Version (KEINE Funktionsaenderung)


 \file     motor_low.c

 \brief    Low Level Funktionen zur Steuerung der Motoren.

 Die Motorsteuerung erfolgt grundsaetzlich ueber die auf der Asuro-Platine\n
 aufgebauten H-Bruecken. Dies ist eine Schaltung, ueber die ein Strom in\n
 verschiedene Richtungen durch die Motoren geleitet werden kann.\n
 Zur Geschwindigkeitssteuerung werden die beiden im Prozessor vorhandenen\n
 PWM-Kanaele genutzt, deren Ausgangssignale die Staerke des Stromflusses in\n
 den H-Bruecken beinflusst.\n
 Die Initialisierung der PWM-Funktionalitaet erfolgt in der Funktion Init().

 \see      Defines fuer die Auswahl der ADC-Kanaele in asuro.h\n
 FWD, RWD, BREAK, FREE

 \version  V--- - 10.11.2003 - Jan Grewe - DLR\n
 Original Version von der ASURO CD\n
 \version  V--- - bis zum 07.01.2007 - \n
 Bitte in Datei CHANGELOG nachsehen.\n
 \version  V001 - 13.01.2007 - m.a.r.v.i.n\n
 +++ Alle Funktionen\n
 Zerlegte Sourcen in einzelne Dateien fuer eine echte Library.
 \version  V002 - 05.02.2007 - Sternthaler\n
 +++ Alle Funktionen\n
 Kommentierte Version (KEINE Funktionsaenderung)
 \version  V003 - 18.02.2007 - m.a.r.v.i.n\n
 Datei gesplitted in motor_low.c und motor.c 
 \file     switches.c

 \brief    Funktionen zum lesen der Taster und um den 'Interrupt-Betrieb' ein-\n
 und auszuschalten. Im 'Interrupt-Betrieb' wird die globale Variable\n
 switched gesetzt beim betaetigen irgendeines Tasters.

 \see      Define fuer die Auswahl des ADC-Kanals in asuro.h\n
 SWITCH

 \version  V--- - 10.11.2003 - Jan Grewe - DLR\n
 Original Version von der ASURO CD\n
 \version  V--- - bis zum 07.01.2007 - \n
 Bitte in Datei CHANGELOG nachsehen.\n
 \version  V001 - 13.01.2007 - m.a.r.v.i.n\n
 +++ Alle Funktionen\n
 Zerlegte Sourcen in einzelne Dateien fuer eine echte Library.
 \version  V002 - 22.01.2007 - Sternthaler\n
 +++ Alle Funktionen\n
 Kommentierte Version (KEINE Funktionsaenderung)
 \version  V003 - 18.02.2007 - Sternthaler\n
 +++ StartSwitch()
 Korrektur im Code-Beispiel
 \version  V004 - 20.02.2007 - m.a.r.v.i.n\n
 Korrekturfaktur aus myasuro.h verwenden
 \version  V005 - 20.01.2008 - m.a.r.v.i.n\n
 ReadADC Funktion zum Auslesen des A/D Wandlers verwenden\n
 Anpassungen an ATmega168
 \version  V006 - 21.04.2008 - m.a.r.v.i.n\n
 Bug Report von RN-User thowil\n
 - Bugfix in PollSwitch Funktion fuehrt zu staendig neuen Interrupts\n
 bei Verwendung mit StartSwitch\n
 - PollSwitch kehrt jetzt sofort ohne A/D Wandlung zurueck\n
 wenn keine Taste gedrueckt wurde

 \file     adc_low.c

 \brief    Low Level Funktion zum Auslesen der ADC-Wandler.

 \version  V001 - 20.01.2008 - m.a.r.v.i.n\n
 *****************************************************************************/

package haiku.avr.lib.arduino;

import static haiku.avr.ATmega328p.*;

public class ArduinoLib extends haiku.avr.AVRDefines {

    public static void setBaudRate(long speed) {
        final short t = (short) (((F_CPU >> 4) + (speed >>> 1)) / speed - 1);
        UBRR0H = (t >>> 8);
        UBRR0L = t;
        UCSR0B |= _BV(RXEN0);
        UCSR0B |= _BV(TXEN0);
        // sbi(UCSR0B, RXCIE0);
    }

    public static void init() {
        // this needs to be called before setup() or some functions won't
        // work there
        sei();

        // on the ATmega168, timer 0 is also used for fast hardware pwm
        // (using phase-correct PWM would mean that timer 0 overflowed half as
        // often
        // resulting in different millis() behavior on the ATmega8 and
        // ATmega168)
        if (!__AVR_ATmega8__) {
            TCCR0A |= _BV(WGM01);
            TCCR0A |= _BV(WGM00);
        }
        // set timer 0 prescale factor to 64
        if (__AVR_ATmega8__) {
            TCCR0 |= _BV(CS01);
            TCCR0 |= _BV(CS00);
        } else {
            TCCR0B |= _BV(CS01);
            TCCR0B |= _BV(CS00);
        }
        // enable timer 0 overflow interrupt
        if (__AVR_ATmega8__) {
            TIMSK |= _BV(TOIE0);
        } else {
            TIMSK0 |= _BV(TOIE0);
        }

        if (false) {
            // timers 1 and 2 are used for phase-correct hardware pwm
            // this is better for motors as it ensures an even waveform
            // note, however, that fast pwm mode can achieve a frequency of up
            // 8 MHz (with a 16 MHz clock) at 50% duty cycle

            // set timer 1 prescale factor to 64
            TCCR1B |= _BV(CS11);
            TCCR1B |= _BV(CS10);
            // put timer 1 in 8-bit phase correct pwm mode
            TCCR1A |= _BV(WGM10);

            // set timer 2 prescale factor to 64
            if (__AVR_ATmega8__) {
                TCCR2 |= _BV(CS22);
            } else {
                TCCR2B |= _BV(CS22);
            }
            // configure timer 2 for phase correct pwm (8-bit)
            if (__AVR_ATmega8__) {
                TCCR2 |= _BV(WGM20);
            } else {
                TCCR2A |= _BV(WGM20);
            }

            if (__AVR_ATmega1280__) {
                // set timer 3, 4, 5 prescale factor to 64
                TCCR3B |= _BV(CS31);
                TCCR3B |= _BV(CS30);
                TCCR4B |= _BV(CS41);
                TCCR4B |= _BV(CS40);
                TCCR5B |= _BV(CS51);
                TCCR5B |= _BV(CS50);
                // put timer 3, 4, 5 in 8-bit phase correct pwm mode
                TCCR3A |= _BV(WGM30);
                TCCR4A |= _BV(WGM40);
                TCCR5A |= _BV(WGM50);
            }
        }
        // set a2d prescale factor to 128
        // 16 MHz / 128 = 125 KHz, inside the desired 50-200 KHz range.
        // XXX: this will not work properly for other clock speeds, and
        // this code should use F_CPU to determine the prescale factor.
        ADCSRA |= _BV(ADPS2);
        ADCSRA |= _BV(ADPS1);
        ADCSRA |= _BV(ADPS0);

        // enable a2d conversions
        ADCSRA |= _BV(ADEN);

        // the bootloader connects pins 0 and 1 to the USART; disconnect them
        // here so they can be used as normal digital i/o; they will be
        // reconnected in Serial.begin()
        if (__AVR_ATmega8__) {
            UCSRB = 0;
        } else {
            UCSR0B = 0;
        }

        // setBaudRate(57600);
        final short t = (short) (((F_CPU >> 4) + (BAUD_RATE >>> 1)) / BAUD_RATE - 1);
        UBRR0H = (t >>> 8);
        UBRR0L = t;
        UCSR0B |= _BV(RXEN0);
        UCSR0B |= _BV(TXEN0);
        // sbi(UCSR0B, RXCIE0); // Interrupt bei Empfang

    }

    /**
     * @param mux
     * @return 10 Bit A/D Value (in 0..1023)
     */
    public static int getADC(int mux) {
        return analogRead(mux);
    }

    /**
     * @param pin
     * @return 10 Bit A/D Value (in 0..1023)
     */
    public static int analogRead(int pin) {
        // if ((mux) == (BATTERIE))
        // setMemory8(ADMUX, (1 << REFS0) | (1 << REFS1) | mux); // interne
        // 2.56V Referenz
        // else
        ADMUX = (1 << REFS0) | pin; // Referenz mit externer Kapazitaet

        ADCSRA |= (1 << ADSC); // Starte AD-Wandlung
        while ((ADCSRA & (1 << ADSC)) != 0)
            // Ende der AD-Wandlung abwarten
            ;
        // ADCSRA_328P |= (1 << ADIF); // AD-Interupt-Flag zuruecksetzen
        return ADC; // Ergebnis als 16-Bit-Wert

    }

    public static long map(long x, long in_min, long in_max, long out_min, long out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }
}
