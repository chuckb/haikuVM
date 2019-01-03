/**
 * since HaikuVM 1.4.0
 *
 * HaikuAppMain.c
 *
 *  Created on: 16.09.2011
 *      Author: genom2
 *
 *
 */
#include "haikuJ2C.h"
#include "utility/haikuDefs.h"

#ifndef HAIKU_UserMain

int main() {

#if defined(ARDUINO_SAM_DUE)
#else
#if defined(USBCON)
    USBCON &= 127; // CLEAR BIT 7 -> disable USB interrupt
#endif
#endif

    setupHaikuVM();

	sei();


	interpret();
}
#endif
