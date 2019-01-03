/**
 * interface:
 * #####################################
 *
 * // used in bytecodeDispatcher for declaring bytecodeLabels[]
 * #define LABEL(x)
 * typedef X label_t;
 *
 * // make or fake a C switch statement in this file
 * #define SWITCH(x)
 * #define ENDSWITCH
 *
 *
 * // augment code in Bytecodes.h
 * #define BEGCODE(x)
 * #define PUSHTOP0()
 * #define LOOP_THREAD()
 * #define ENDCODE
 *
 *
 *
 * #ifdef ARDUINO
 *
 * // used in loop()
 * void interpretNextByteCodeChunk() {..}
 *
 * #else
 *
 * // has its own fast loop
 * void startJVM() {..}
 *
 * #endif
 *
 */

#if NO_MICRO

#define	LABEL(x)	HBC_##x

#define SWITCH(x)	switch(*(bytecodeLabels+x)) {
#define BEGCODE(x)    case HBC_##x: {
#define ENDCODE       }; break;
#define ENDSWITCH	default: unused(); }

//#define PUSHTOP0()	goto loopTop0
#define PUSHTOP0()	pushTop0();
#define LOOP_THREAD() goto yield
typedef int label_t;

#else

#define	LABEL(x)	&&OPL_##x
#define SWITCH(x)	goto *(label_t)pgm_read_wordRef(*(bytecodeLabels+x));

#define BEGCODE(x)  OPL_##x: {
#define ENDCODE     }; goto loop;

#define ENDSWITCH
//#define PUSHTOP0()	goto loopTop0
#define PUSHTOP0()	pushTop0();
#define LOOP_THREAD() goto yield

typedef void * label_t;

#endif


#ifdef ARDUINO
//slow (but needed for Arduino IDE) to put into loop()
void interpretNextByteCodeChunk(){
	int step;
#include "utility/bytecodeDispatcher.h"

	for(step=0; step<100; step++) {
		bc=pgm_read_byteRef((*pc));
		pc++;

		SWITCH(bc)
#include "Bytecodes.h"
		ENDSWITCH;

yield:
#if HAIKU_Threads!=0
		if ((t++&0x3)==0) {
			switchThread();
		}
#endif

loop:
		;
	}
}
#else
/**
 * fast
 * But for some unknown reason it is included into the hex file
 * if compiled with ArduinoIDE, but shoudn't be there. (interpretNextByteCodeChunk() should be there instead.)
 * This is why we need #ifndef ARDUINO. This saves space.
 *
 * setup JVM and interpret main_YLjava_lang_String for ever (which is not over and over again)
 */
void interpret() {
#include "utility/bytecodeDispatcher.h"

yield:
#if HAIKU_Threads!=0
	if ((t++&0x3)==0) switchThread();
#endif

loop:
	bc=pgm_read_byteRef((*pc));
	pc+=sizeof(OP_bc);

	goto *(label_t)pgm_read_wordRef(*(bytecodeLabels+bc));
#include "Bytecodes.h"
}
#endif
