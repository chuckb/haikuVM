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
 * #define CONSTRAIN0(x)
 * #define PUSHTOP0()
 * #define LOOP_THREAD()
 * #define ENDCODE
 *
 *
 * void interpret() {..}
 *
 */

typedef void * label_t;

#define BEGCODE(x)  OPF_##x: {
#define PUSHTOP0()	pushTop0();
#define LOOP_THREAD() goto yield
#define ENDCODE     }; goto loop;
#define CONSTRAIN0(x) bc=x
#define ALL_BYTECODES 1

#include "utility/haikuByteCodeTypes.h"
#include "utility/haikuByteCode.h"
#include "utility/haikuClasses.h"

void interpret() {
	label_t target;

yield:
#if HAIKU_Threads!=0
	if ((t++&0x3)==0) switchThread();
#endif

loop:
	target= GETCODEADR2();
	goto *(void*)(target);

#include "Bytecodes.h"
}
