/*
*  Created on: 08.10.2015
*      Author: genom2
*/

#ifdef WIN32

#include <stdio.h>
#include <stdarg.h>
#include <string.h>

		// Use this order for "haikuJ2C.h". Otherwise we run into problems with:
		// ..\Microsoft Visual Studio 8\VC\PlatformSDK\Include\WinGDI.h
#include <windows.h>
#include <time.h>
#include <stddef.h>

#include "haikuJ2C.h"
#include "simpleOS.h"


FILE * tracefile=NULL;

int jprintf(const char * format, ...) {
	char buf[280]; // nicht globaler buf !!
	int err;
	va_list list;
	va_start(list, format);
	vsprintf(buf, format, list);
	err=printf(buf);
	if (tracefile) vfprintf(tracefile, format, list);
	va_end(list);
	return err;
}

unsigned long millis() {
	return clock();
}

#endif

