#if AVR

#include <avr/pgmspace.h>
#include <avr/interrupt.h>

#define pgm_read_byteRef(x) pgm_read_byte(&(x))
#define pgm_read_wordRef(x) pgm_read_word(&(x))
#define pgm_read_dwordRef(x) pgm_read_dword(&(x))

#elif RCX

#ifndef __ATTR_PROGMEM__
#define __ATTR_PROGMEM__ __attribute__((section (".haiku")))
#endif

#define strcpy_P strcpy
#define strlen_P strlen
#define memcpy_P memcpy
//#define pgm_read_byte(x) x
#define pgm_read_byteRef(x) (x)
//#define pgm_read_word(x) x
#define pgm_read_wordRef(x) (x)
#define pgm_read_dwordRef(x) (x)
#define pgm_read_word(x) (*(x))

#define PROGMEM __ATTR_PROGMEM__

#else

#ifndef PROGMEM

#define PROGMEM
#define strcpy_P strcpy
#define strlen_P strlen
#define memcpy_P memcpy
//#define pgm_read_byte(x) x
#define pgm_read_byteRef(x) (x)
//#define pgm_read_word(x) x
#define pgm_read_wordRef(x) (x)
#define pgm_read_dwordRef(x) (x)
#define pgm_read_word(x) (*(x))

#endif

#endif



