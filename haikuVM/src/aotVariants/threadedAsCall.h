/**
 * interface:
 * #####################################
 *
 * // used in bytecodeDispatcher for declaring bytecodeLabels[]
 * #define LABEL(x)
 * typedef T label_t;
 *
 *
 * // augment code in Bytecodes.h
 * #define BEGCODE(x)
 * #define PUSHTOP0()
 * #define LOOP_THREAD()
 * #define ENDCODE
 *
 *
 * void interpret() {..}
 *
 */

typedef void (*foo)();

#define BEGCODE(x)  void OPF_##x() {
#define PUSHTOP0()	pushTop0();

#define OPTIMIZE_SPEED (!(defined(__OPTIMIZE_SIZE__) || _DEBUG))

#if	OPTIMIZE_SPEED
	#ifndef WIN32
	#warning "threadedAsCall in SPEED mode. (For AVR you need AVR GCC 4.9.2 at least)"
	#endif
	// tail recursion elimination is needed and is only supported in NON debug or SPEED mode
	#define LOOP_THREAD() return;
	#define ENDCODE     ((void(*)())(GETCODEADR2()))(); };
#else
	#if HAIKU_Threads!=0
		#define LOOP_THREAD() if ((t++ & 0x3) == 0) switchThread();
	#else
		#define LOOP_THREAD()
	#endif
	#define ENDCODE     };
#endif

#define CONSTRAIN0(x) bc=x
#define ALL_BYTECODES 1

#include "utility/haikuByteCodeTypes.h"
#include "utility/haikuByteCode.h"
#include "utility/haikuClasses.h"

#include "Bytecodes.h"

#ifdef ARDUINO
//slow (but needed for Arduino IDE) to put into loop()
void interpretNextByteCodeChunk(){
	foo target;
	int step;
	for(step=0; step<100; step++) {
#if HAIKU_Threads!=0 &&	OPTIMIZE_SPEED
		if ((t++ & 0x3) == 0) switchThread();
#endif
		target= pgm_read_wordRef(*((foo *)pc));
		pc+=sizeof(foo);
		(target)();
	}
}
#else

void interpret() {
loop:
#if HAIKU_Threads!=0 &&	OPTIMIZE_SPEED
	if ((t++&0x3)==0) switchThread();
#endif

	((void(*)())(GETCODEADR2()))();
	goto loop;
}
#endif
