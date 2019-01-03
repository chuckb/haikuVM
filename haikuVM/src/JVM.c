#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <stdarg.h>

#include "haikuJ2C.h"
#include "utility/haikuConfig.h"
#include "simpleOS.h"

uint8_t bc;
int8_t t;


void unimplemented() {
	panic(InternalError, bc); //unimplemented byte code
}

void unused() {
	panic(InternalError, bc); //(normaly) unused byte code
}

#if HAIKU_Align>1
#define min(a,b) (a<b?a:b)
#define align(ptr, type) (char *)((unsigned int)(ptr + min(sizeof(type), HAIKU_Align) - 1) & ~(min(sizeof(type), HAIKU_Align) - 1))
#else
#define align(ptr, type) ptr
#endif

__attribute__((noinline)) int32_t GETCODEDWORD2() {
	int32_t codei=pgm_read_dwordRef(*((int32_t*)(pc = align(pc, int32_t))));
	pc += sizeof(int32_t);
	return codei;
}

__attribute__((noinline)) int16_t GETCODEWORD2() {
	int16_t codei=pgm_read_wordRef(*((int16_t*)(pc = align(pc, int16_t))));
	pc += sizeof(int16_t);
	return codei;
}

__attribute__((noinline)) int8_t GETCODEBYTE2() {
	int8_t codei=pgm_read_byteRef(*pc);
	pc+=1;
	return codei;
}

__attribute__((noinline)) void* GETCODEADR2() {
	void* codei = pgm_read_dwordRef(*((void**)(pc = align(pc, void*))));
	pc += sizeof(void*);
	return codei;
}

void switchThread() {
	// Task switch
	native_java_lang_Thread *oldThread=currentThread;

	while(1) {
		incrementalGC(HAIKU_IncrementalGCSlice);
		heapmem.allStatics.statics.java_lang_Thread_currentThread = (jobject) currentThread->next;

			// restart if state is IDLE or YIELD
		if (currentThread->state<=YIELD ||
			// restart if state is WAITING and waiting time is over (see sleep(..) and wait(..))
		    (currentThread->state==WAITING && (unsigned long)(currentThread->waitUntil)<=millis())) break;
#if NO_MICRO
		if (currentThread->state==STOPPED && currentThread->next == currentThread ) {
			// Only one thread is in the queue AND is in STOPPED state.
#if	_DEBUG
			getc(stdin);
#endif
			exit(0);
		}
#else
		// a micro will stay in this while(1) loop
#endif
	}
	currentThread->state=IDLE;

#if HAIKU_Threads
	if (oldThread != currentThread) {
		*dataSp = top.s1;
		oldThread->programcounter = pc;
		oldThread->stackpointer = dataSp;
		oldThread->stackframe = lsp;
		oldThread->stackblock = currentStackblock;

		pc = (unsigned char*)currentThread->programcounter;
		dataSp = currentThread->stackpointer;
		lsp = currentThread->stackframe;
		currentStackblock = currentThread->stackblock;
		top.s1 = *dataSp;
#if TRACEING
		jprintf("################################# next Thread %p\n", currentThread);
#endif
	}
#endif
}

#pragma GCC diagnostic ignored "-Wunused-label"

heapmem_t heapmem; // ={HEAPSIZE, FREE};

#pragma GCC diagnostic ignored "-Wunused-label"

#if HAIKU_AOTVariant == HAIKU_AOTBytecodeAsJump
#include "aotVariants/bytecodeAsJump.h"

#elif HAIKU_AOTVariant == HAIKU_AOTBytecodeAsSwitch
#include "aotVariants/bytecodeAsSwitch.h"

#elif HAIKU_AOTVariant == HAIKU_AOTThreadedAsJump
#error "unsupported HAIKU_AOTVariant: Use e.g.: HAIKU_AOTThreadedAsCall"
#include "aotVariants/threadedAsJump.h"

#elif HAIKU_AOTVariant == HAIKU_AOTThreadedAsCall
#include "aotVariants/threadedAsCall.h"

//#else
//
//#if GCC
//#include "aotVariants/bytecodeAsJump.h"
//#else
//#include "aotVariants/bytecodeAsSwitch.h"
//#endif
//
#else
#error "unknown HAIKU_AOTVariant: Use e.g.: HAIKU_AOTBytecodeAsJump"
#endif


void setupHaikuVM() {
	// init Java heap
	heapmem.heapControl0.xallocsize = HEAPSIZE;

	// must be the following order to save Mark(stack) in java_lang_System_gc_V()
	// setup main thread
	heapmem.allStatics.statics.java_lang_Thread_currentThread = newInstance(
		(class_t*)&java_lang_Thread__class);

	// now setup the first stack frame
	currentStackblock = (stackBlock4Debug_t*)newStack(HAIKU_InitialMainThreadStackSize);
	dataSp = &currentStackblock->stackbase[0];

	// init main thread
	currentThread->next = currentThread;
	currentThread->stackblock = currentStackblock;

	// prepare the first method to run
	invoke(&main_YLjava_lang_String);
}

