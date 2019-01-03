/*
*  Created on: 01.02.2019
*      Author: chuckb
*/

#ifdef TEENSYDUINO

#include <stdio.h>
#include <stdarg.h>
#include <string.h>
#include "haikuJ2C.h"
#include "simpleOS.h"
#include "EventResponder.h"

extern volatile uint32_t systick_millis_count;
unsigned long millis()
{
  return systick_millis_count;
}

#endif
