
#if defined(WIN32) || defined(UNIX)

#define NO_MICRO 1
#define isMICRO 0
#define sei()

#else
#define isMICRO 1
#endif


#if defined(WIN32)
extern void LCD_showNumber(int aValue);
	typedef signed int int32_t;
	typedef signed long long int64_t;
	typedef signed short int16_t;
	typedef signed char int8_t;
	typedef unsigned int uint32_t;
	typedef unsigned long long uint64_t;
	typedef unsigned short uint16_t;
	typedef unsigned char uint8_t;
#elif defined(RCX)

extern void LCD_showNumber(int aValue);

	typedef signed long int32_t;
	typedef signed long long int64_t;
	typedef signed short int16_t;
	typedef signed char int8_t;
	typedef unsigned long uint32_t;
	typedef unsigned long long uint64_t;
	typedef unsigned short uint16_t;
	typedef unsigned char uint8_t;
#else
#include <stdint.h>
#define LCD_showNumber(aValue)
#endif


#ifndef RCX
#include <stdio.h>
#endif

#ifdef RCX
#define sei()
#endif
