#include "haikuJ2C.h"

#if AVR

#include <avr/pgmspace.h>

uint8_t pgm_read_byteROM(uint8_t* x) {
	return pgm_read_byte(x) & 0xff;
}

uint16_t pgm_read_wordROM(uint16_t* x) {
	return pgm_read_word(x);
}

uint32_t pgm_read_dwordROM(uint32_t* x) {
	return pgm_read_dword(x);
}

#endif

