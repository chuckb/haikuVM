#include <stdlib.h>
#include <stdio.h>
#include <stdarg.h>
#include <string.h>


#include "haikuJ2C.h"
#include "utility/haikuConfig.h"
#include "simpleOS.h"

#if HAIKU_GC==HAIKU_ConservativGC || HAIKU_GC==HAIKU_StopTheWorldGC
extern int setMark(jobject pObj);
extern int isMarked(jobject obj);
extern int isFree(jobject obj);
extern void MarkBit(jobject obj);

extern jheapsize getObjLength(jobject obj);
extern jobject Reuse(jheapsize size);


/**
 * Conservative
 */
static void Mark() {
	char* ptr;
	//char* p;
	jheapsize len;
	jheapsize i;
	char* h=HEAP2OBJ(&heapmem.heapControl0);

	// Mark (short but slow -> O(N*N) maybe O(N*N*N) because of indirect use of isPointer(..) in setMark(..))
rescan:
	for(ptr=h; (len=getObjLength(ptr)); ptr+=len) {
		if (isMarked(ptr)) {
			// scan this specific heap alloc block (which starts at ptr)
			/*
			*/
			for(i=0; i+sizeof(void*)+sizeof(heap_t)-1<len; i+=HAIKU_Align) {
				h=*(jobject*)(ptr+i);
				if (setMark(h) && h < ptr) goto rescan; // only if we marked something before ptr. setMark(h) garanties that h points to a jobject.
			}
		}
	}
}

static void Sweep() {
	char* ptr;
	char* p;
	jheapsize len;
	jheapsize i;

	// Sweep Version 1
	for(ptr=HEAP2OBJ(&heapmem.heapControl0); (len=getObjLength(ptr)); ptr+=len) {
		if(!isMarked(ptr)) {
			for(p=ptr; (i=getObjLength(p)) && !isMarked(p); p+=i) {
				// noop: just feed forward to next marked element
			}
			len=p-ptr;
			setClass(ptr, FREE);
		}
		setAllocsize(ptr, len); // (re-)set len and unMark
	}
	/* Version 3 is 2 Bytes longer and 2 ms slower than version 1
	ptr=&heapmem.heapControl0;
	while(len=getObjLength(ptr)) {
		p=ptr+len;
		if(!isMarked(ptr)) {
			((jheap)ptr)->clazz=FREE;
			i=getObjLength(p);
			if (!isMarked(p) && i!=0) {
				len+=i;
				p=ptr;
			}
		}
		((jheap)ptr)->allocsize=len; // (re-)set len and unMark
		ptr=p;
	}*/
	/*
	*/
	/* Version 2 is 20 Bytes longer and 1 ms slower than version 1
	for(ptr=&heapmem.heapControl0; len=getObjLength(ptr); ptr+=len) {
		if(isMarked(ptr)) {
			if (free) {
				((jheap)free)->allocsize=(ptr-free); // (re-)set len and unMark
				free=NULL;
			}
			((jheap)ptr)->allocsize=len; // (re-)set len and unMark
		} else {
			if (free==NULL) {
				free=ptr;
				((jheap)free)->clazz=FREE;
			}
		}
	}
	if (free) {
		((jheap)free)->allocsize=(ptr-free); // (re-)set len and unMark
	}
	*/
}

/**
 * experimental to save (about 120 byte) code size
 *
static void MarkSweep() {
	int8_t m;
	char* p;
	char* ptr;
	jheapsize len;
	jheapsize i;

	// Mark (short but slow -> O(N*N) maybe O(N*N*N) because of indirect use of isPointer(..) in setMark(..))
	for(m=0; m<2; m++) {
		for(ptr=HEAP2OBJ(&heapmem.heapControl0); (len=getObjLength(ptr)); ptr+=len) {
				if (isMarked(ptr)) {
						// scan this specific heap alloc block (which starts at ptr)
						for(i=0; m<=0 && i+sizeof(void*)+sizeof(heap_t)<=len; i++) {
							if (setMark(*(jobject*)(ptr+i))) m=-1; // only if we marked something before ptr. setMark(h) garanties that h points to a jobject.
						}
				} else {
					if(m>0) {
						for(p=ptr; (i=getObjLength(p)) && !isMarked(p); p+=i) {
							// noop: just feed forward to next marked element
						}
						len=p-ptr;
						setClass(ptr, FREE);
					};
				}
				if(m>0)	setAllocsize(ptr, len); // (re-)set len and unMark
		}
	}
}
*/

void native_java_lang_System_gc_V() {
	int i;
	Debug(GCcount++;)
	Debug(jprintf("\nGC ####################\n");)
	Debug(showstack("");)

	Debug(haikuHeapWalk("heap", 1);)

	memset(dataSp, 0, ((int)&currentStackblock->stackbase[currentStackblock->length] - (int)dataSp)); // good for GC
	for(i=0; i<STATIC_ROOTS; i++) {
		setMark(heapmem.allStatics.gcRoots[i]);
	}
	setMark(top.s1.a);
    Mark();
    Sweep();
	Debug(haikuHeapWalk("heap", 1);)
}

jobject GCalloc(jclass clazz, jheapsize size) {
	jobject ptr;
	int8_t rec=0;

#if HAIKU_Align>1
	size = ((size + HAIKU_Align - 1)&~(HAIKU_Align - 1));
#endif

	for (;;) {
		ptr = Reuse(size + sizeof(heap_t));
		if (ptr) {
			memset(ptr, 0, size);
			setClass(ptr, clazz);
			setAllocsize(ptr, size + sizeof(heap_t));
			break;
		}
		else {
			if (rec) {
#if NO_MICRO && _DEBUG
				jprintf("needed %d (+%d if reuse) more bytes\n", size + sizeof(heap_t), sizeof(heap_t));
#endif
				panic(OutOfMemoryError, size);
				//What happens here? Please explain.
				break;
			}
			else {
				native_java_lang_System_gc_V();
			}
		}
		rec = 1;
	}
	return ptr;
}
#endif