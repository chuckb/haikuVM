/*
 * simpleOS.h
 *
 *  Created on: 22.08.2011
 *      Author: genom2
 */

#ifndef SIMPLEOS_H_
#define SIMPLEOS_H_

extern void platform_init();

/* Observed with Arduino 1.8.1 (AVR Compiler) for UNO when compiled for Java 64 Bit long:
 * Mostly millis() has platform dependent type long.
 * Don't force it to type julong
 * and let C do the casting.
 * extern julong millis(); is wrong now
 */
extern unsigned long millis();


#if _DEBUG || defined(WIN32)
#include <stdio.h>
extern int jprintf(const char * format, ...);
#else
#define jprintf printf
#endif

#endif /* SIMPLEOS_H_ */
