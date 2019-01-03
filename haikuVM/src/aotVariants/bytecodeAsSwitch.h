/**
 * interface:
 * #####################################
 *
 * // used in bytecodeDispatcher for declaring bytecodeLabels[]
 * #define LABEL(x)
 * typedef T label_t;
 *
 * Example:
 * definitions in haikuConfig.h
 *   #define OP_ALOAD_0 32
 *   #define OP_ALOAD_1 33
 * and map both to implementation HBC_ALOAD_0 via bytecodeLabels[bc]
 *   bytecodeLabels[OP_ALOAD_0] -> HBC_ALOAD_0
 *   bytecodeLabels[OP_ALOAD_1] -> HBC_ALOAD_0
 * to share the same implementation. This is possible because bc&7
 * is an implicit parameter of implementation HBC_ALOAD_0.
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

#define SWITCH(x)	switch(x) {
#define ENDSWITCH	default: unused(); }

#define BEGCODE(x)    HBC_DEF_##x {
#define ENDCODE       }; break;
#define PUSHTOP0()	pushTop0();
#define LOOP_THREAD() goto yield
#define CONSTRAIN0(x)
#define ALL_BYTECODES 0

#include "utility/haikuByteCodeTypes.h"
#include "utility/haikuByteCode.h"
#include "utility/haikuClasses.h"

#ifdef ARDUINO
//slow (but needed for Arduino IDE) to put into loop()
void interpretNextByteCodeChunk(){
	int step;
	for(step=0; step<100; step++) {
		bc=pgm_read_byteRef((*pc));
		pc+=sizeof(OP_bc);
		SWITCH(bc)
#include "Bytecodes.h"
		ENDSWITCH;

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
yield:
#if HAIKU_Threads!=0
	if ((t++&0x3)==0) switchThread();
#endif

loop:
	bc=pgm_read_byteRef((*pc));
	pc+=sizeof(OP_bc);
	SWITCH(bc)
#include "Bytecodes.h"
	ENDSWITCH;

	goto loop;
}
#endif
