#include <stdlib.h>
#include <stdio.h>
#include <stdarg.h>
#include <string.h>


#include "haikuJ2C.h"
#include "utility/haikuConfig.h"
#include "simpleOS.h"

#if HAIKU_GC==HAIKU_IncrementalGC

extern int setMark(jobject pObj);
extern int isMarked(jobject obj);
extern int isFree(jobject obj);
extern void MarkBit(jobject obj);

extern jheapsize getObjLength(jobject obj);
extern jobject Reuse(jheapsize size);

Debug(static int GCinc);

static int GCtransaction = 0;

/**
 * Incremental Conservative
 */
void incrementalGC(jheapsize incremental) {
#define ROOT 0
#define MARK 1
#define SWEEP 2
	static int lastGCtransaction = 0;
	static int gcPhase = ROOT;
	static char* ptr;
	char* p;
	jheapsize len;
	jheapsize i;
	jobject h;

	Debug(GCinc++);
	if (gcPhase==ROOT && lastGCtransaction != GCtransaction && currentStackblock!=NULL) {
		lastGCtransaction = GCtransaction;

		memset(dataSp, 0, ((int)&currentStackblock->stackbase[currentStackblock->length]-(int)dataSp)); // just good for GC
		for(i=0; i<STATIC_ROOTS; i++) {
			if(heapmem.allStatics.gcRoots[i]) MarkBit(heapmem.allStatics.gcRoots[i]);
		}
		setMark(top.s1.a);
		gcPhase=MARK;
		ptr =HEAP2OBJ(&heapmem.heapControl0);
	}
	if (gcPhase==MARK) {
		// Mark (short but slow -> O(N*N) maybe O(N*N*N) because of indirect use of isPointer(..) in setMark(..))
rescan:
		for (; (len = getObjLength(ptr)); ptr += len) {
			if (--incremental==0) return;
			if (isMarked(ptr)) {
				// scan this specific heap alloc block (which starts at ptr)
				for(i=0; i+sizeof(void*)+sizeof(heap_t)-1<len; i += HAIKU_Align) {
					h=*(jobject*)(ptr +i);
					if (setMark(h) && h < ptr) {
							ptr = h;
							goto rescan; // only if we marked something befor ptr. setMark(h) garanties that h points to a heap alloc block.
					}
				}

			}
		}
		ptr=HEAP2OBJ(&heapmem.heapControl0);
		gcPhase=SWEEP;
	}
	if (gcPhase==SWEEP) {
		// Sweep Version 1
		while((len=getObjLength(ptr))) {
			if(!isMarked(ptr)) {
				for(p=ptr; (i=getObjLength(p)) && !isMarked(p); p+=i) {
					// noop: just feed forward to next marked element
				}
				len=p-ptr;
				setClass(ptr, FREE);
			}
			setAllocsize(ptr, len); // (re-)set len and unMark

			ptr+=len;
			if (--incremental==0) return;
		}
		gcPhase=ROOT;

		Debug(GCcount++);
		Debug(jprintf("\nGC #################### %d/%d\n", GCinc, GCcount));

		Debug(haikuHeapWalk("heap", 1);)
		Debug(haikuHeapWalk("free", 0);)
	}
}

void native_java_lang_System_gc_V() {
	incrementalGC(HAIKU_MemorySize);
	incrementalGC(HAIKU_MemorySize);
}

jobject GCalloc(jclass clazz, jheapsize size) {
	jobject ptr;
	int8_t rec;

#if HAIKU_Align>1
	size = ((size + HAIKU_Align - 1)&~(HAIKU_Align - 1));
#endif

	for (rec=0;;rec++) {
		ptr = Reuse(size + sizeof(heap_t));
		if (ptr) {
			memset(ptr, 0, size);
			setClass(ptr, clazz);
			setAllocsize(ptr, size + sizeof(heap_t));
			MarkBit(ptr); // no write_barrier shift needed
			GCtransaction++;
			break;
		}
		else {
			if (rec==2) {
#if NO_MICRO && _DEBUG
				jprintf("needed %d (+%d if reuse) more bytes\n", size + sizeof(heap_t), sizeof(heap_t));
#endif
				panic(OutOfMemoryError, size);
				//What happens here? Please explain.
				break;
			}
			else {
				incrementalGC(HAIKU_MemorySize);
			}
		}
	}
	return ptr;
}
#endif