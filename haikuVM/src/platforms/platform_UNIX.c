#if UNIX
/*
 *  Created on: 22.08.2011
 *      Author: genom2
 */

#include <stdio.h>
#include <stdarg.h>
#include <string.h>
#include <sys/time.h>
#include <unistd.h>
#include "haikuJ2C.h"
#include "simpleOS.h"


#if _DEBUG
static char buf[80];

#undef jprintf
extern int jprintf(const char * format, ...);

#endif

#if NO_MICRO
FILE * tracefile=NULL;

#if _DEBUG
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
#endif

unsigned long millis() {
	struct timeval t;
	gettimeofday(&t, 0);
	return t.tv_sec * 1000 + t.tv_usec / 1000;
}
#endif

#endif
