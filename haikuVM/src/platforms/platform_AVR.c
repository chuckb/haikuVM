/*
*  Created on: 08.10.2015
*      Author: genom2
*/

#ifdef AVR

#include <stdio.h>
#include <stdarg.h>
#include <string.h>
#include "haikuJ2C.h"
#include "simpleOS.h"
#include <avr/interrupt.h>

#if defined(HAIKU_TimerInterrupt)

#define clockCyclesPerMicrosecond() ( F_CPU / 1000000L )

/****************************************************************************
  \brief
  Counter fuer 36kHz.

  \see
  Interruptfunktion SIGNAL (SIG_OVERFLOW2) in asuro.c\n
  Gettime(), Sleep() in time.c
*****************************************************************************/
volatile unsigned char count36kHz;

//NOT static!
//because it my be a nice experiment to play with
//direct memory access via:
// @NativeCVariable
// volatile static long timer0_interrupts;
volatile unsigned long timer0_interrupts = 0;

#if defined(HAIKU_AsuroTiming)
/****************************************************************************
  \brief
  Interrupt-Funktion fuer Timer-2-Ueberlauf.

  \see
  count36kHz, timebase

  \par
  Der zum Timer gehoerende Zaehler TCNT2 wird so justiert, dass damit die\n
  gewuenschten 36 kHz erreicht werden.\n
  Fuer die Zeitfunktionen werden die globalen Variablen count36kHz und\n
  timebase hochgezaehlt.

*****************************************************************************/
ISR(HAIKU_TimerInterrupt)
{
  TCNT2 += 0x25;
  count36kHz ++;
  if (!count36kHz)
	  timer0_interrupts ++;
}

#else
ISR(HAIKU_TimerInterrupt)
{
	timer0_interrupts++;
}
#endif

unsigned long millis()
{
	unsigned long m;
	uint8_t oldSREG = SREG;

	// timer 0 prescale factor is 64 and the timer overflows at 256

	// disable interrupts while we read timer0_millis or we might get an
	// inconsistent value (e.g. in the middle of the timer0_millis++)
	cli();
	m = timer0_interrupts;
	SREG = oldSREG;

#if defined(HAIKU_AsuroTiming)
    return ((m * 256) + count36kHz) / 36;
	//return m * (clockCyclesPerMicrosecond() * 1000UL)/(64UL * 256UL);
#else
	return m * HAIKU_MillisDividend / HAIKU_MillisDivisor;
#endif
}

#endif

#endif
