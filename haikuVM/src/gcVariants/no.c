#include <stdlib.h>
#include <stdio.h>
#include <stdarg.h>
#include <string.h>


#include "haikuJ2C.h"
#include "utility/haikuConfig.h"
#include "simpleOS.h"

#if HAIKU_GC==HAIKU_NoGC

extern int setMark(jobject pObj);
extern int isMarked(jobject obj);
extern int isFree(jobject obj);
extern void MarkBit(jobject obj);

extern jheapsize getObjLength(jobject obj);
extern jobject Reuse(jheapsize size);

void native_java_lang_System_gc_V() {
}

jobject GCalloc(jclass clazz, jheapsize size) {
	jobject ptr;

	ptr=Reuse(size+sizeof(heap_t));
	if (ptr) {
		memset(ptr, 0, size);
		setClass(ptr, clazz);
		setAllocsize(ptr, size+sizeof(heap_t));
	} else {
#if NO_MICRO
		jprintf("needed %d (+%d if reuse) more bytes\n", size, sizeof(heap_t));
#endif
		//fatal("heap overflow");
		panic(OutOfMemoryError, size);
	}
	return ptr;
}
#endif
