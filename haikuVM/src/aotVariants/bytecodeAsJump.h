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

#define	LABEL(x)	&&OPL_##x
typedef void * label_t;

#define BEGCODE(x)  OPL_##x: {
#define PUSHTOP0()	pushTop0();
#define LOOP_THREAD() goto yield;
#define ENDCODE     }; goto loop;
#define CONSTRAIN0(x)
#define ALL_BYTECODES 1

#include "utility/haikuByteCodeTypes.h"
#include "utility/haikuByteCode.h"
#include "utility/haikuClasses.h"


#ifdef ARDUINO
//slow (but needed for Arduino IDE) to put into loop()
void interpretNextByteCodeChunk(){
	int step;
#include "utility/bytecodeDispatcher.h"

	for(step=0; step<100; step++) {
		bc=pgm_read_byteRef((*pc));
		pc+=sizeof(OP_bc);
		goto *(label_t)pgm_read_wordRef(*(bytecodeLabels+bc));
#include "Bytecodes.h"

yield:
#if HAIKU_Threads!=0
		if ((t++&0x3)==0) switchThread();
#endif

loop:
		;
	}
}

#else

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
