#include <stdlib.h>
#include <stdio.h>
#include <stdarg.h>
#include <string.h>


#include "haikuJ2C.h"
#include "utility/haikuConfig.h"
#include "simpleOS.h"
#include "gcVariants/interface.h"



#define Debug(x)
const class_t YLjava_lang_ObjectClass PROGMEM = {
	&java_lang_Object__class,
	sizeof(jobject),
};

// WARNING: attribute const is important here!!
// without this the h8300 compiler (I used) will silently skip initialisation
const class_t YLstack__class PROGMEM = {
	&java_lang_Object__class,
	sizeof(stack_t),
};


Debug(static int GCcalls=0;)
/*
Heap layout:
typedef struct {
	jheapsize allocsize;
	jclass clazz;
	struct {
		field1;
		...
		fieldN;
	} user;
} Object1;
...
typedef struct {
	jheapsize allocsize;
	jclass clazz;
	struct {
		field1;
		...
		fieldN;
	} user;
} ObjectN;
...
typedef struct { // not realy a struct but used here to explain a FREE block
	jheapsize allocsize;
	jclass clazz; // is set with value FREE
	char free[allocsize/2]
} FreeBlock;
...
0;	//allocsize of 0 signals end of heap.

*/
int isMarked(jobject obj) {
	return getMarkFlag(obj);
}

int isFree(jobject obj) {
	return getClass(obj)==FREE;
}

void MarkBit(jobject obj) {
	setMarkFlag(obj);
}

jheapsize getObjLength(jobject obj) {
	return getAllocsize(obj);
}

#if _DEBUG || NO_MICRO
#define min(a,b) ((a)<(b)?a:b)
extern const char * findClass(jclass clazz);

static int	GCcount=0;
static jheapsize usedHeap=0;
/**
* mode=0 -> FREE
* mode=1 -> Used
*/
void haikuHeapWalk(char * msg, int mode) {
	char* ptr;
	jheapsize len;
	int i, sum=0, free=0;
	jprintf("  walk: %s [%p, %p] len=%d GCcount=%d usedHeap=%d\n", msg, (char*)&heapmem.heapControl0, ((char*)&heapmem.heapControl0)+HEAPSIZE, HEAPSIZE, GCcount, usedHeap);
	for(ptr=HEAP2OBJ(&heapmem.heapControl0), i=0; (len=getObjLength(ptr)); ptr+=len, i++) {
		if (ptr+len<((char*)&heapmem.heapControl0) || ((char*)HEAP2OBJ(&heapmem.heapControl0))+HEAPSIZE<ptr+len) {
			//heap is corrupt
			jprintf("  walk: heap is corrupt at %p (= %p + %d) with len %d and HEAPSIZE=%d\n", ptr, (char*)&heapmem.heapControl0, ptr-(char*)&heapmem.heapControl0, len, HEAPSIZE);
			return;
		}
	}
	for(ptr=HEAP2OBJ(&heapmem.heapControl0), i=0; (len=getObjLength(ptr)); ptr+=len, i++) {
		sum+=len;
		if (isFree(ptr)) {
			free+=len;
			jprintf("%6d\t%p\t  %d\t%d\t%d\t%s\n", i, ptr, len, sum, free, findClass(getClass(ptr)));
		} else {
			char * cname=findClass(getClass(ptr));
			jprintf("%6d\t%p\t%d %d\t%d\t  \t", i, ptr, isMarked(ptr), len, sum);
			if (strstr(cname, "YLstack") == cname) {
				jprintf("<stack>[%d]", ((array_t*)ptr)->length);
			} else if (strchr(cname, '[')) {
				jprintf("%s%d]", cname, ((array_t*)ptr)->length);
				if (strcmp("char[", cname) == 0) {
					int j;
					jchar8or16ArrayMem* a = (jchar8or16ArrayMem*)ptr;
					jprintf(" = \"");
					for (j = 0; j<min(10, a->length); j++) {
						if (a->array[j]) {
							jprintf("%c", a->array[j]);
						}
						else {
							jprintf("\\0");
						}
					}
					if (10<a->length)	jprintf(" ... ");
					jprintf("\"");
				}
				else if (strcmp("byte[", cname) == 0) {
					int j;
					jbyteArrayMem* a = (jbyteArrayMem*)ptr;
					jprintf(" = {");
					for (j = 0; j<min(10, a->length); j++) {
						jprintf("%d, ", a->array[j]);
					}
					if (10<a->length)	jprintf(" ... ");
					jprintf("};");
				}
				else if (strcmp("int[", cname) == 0) {
					int j;
					jintArrayMem* a = (jintArrayMem*)ptr;
					jprintf(" = {");
					for (j = 0; j<min(10, a->length); j++) {
						jprintf("%d, ", a->array[j]);
					}
					if (10<a->length)	jprintf(" ... ");
					jprintf("};");
				}
				else if (strcmp("long[", cname) == 0) {
					int j;
					jlongArrayMem* a = (jlongArrayMem*)ptr;
					jprintf(" = {");
					for (j = 0; j<min(10, a->length); j++) {
						jprintf("%ld, ", a->array[j]);
					}
					if (10<a->length)	jprintf(" ... ");
					jprintf("};");
				}
			} else if (strcmp("java.lang.String", cname)==0) {
				int j;
				jstring str= (jstring)ptr;
				jprintf("%s", cname);
				if (str->chars) {
					jheapsize clen = getObjLength(str->chars);
					if (len >= sizeof(ldc_String)) {
						jprintf(" ==> ");
					} else {
						jprintf(" --> ");
					}
					jprintf("%p [%2d] = \"", str->chars, str->chars->length);
					for (j = 0; j<min(str->chars->length, clen); j++) {
						if (str->chars->array[j]) {
							jprintf("%c", str->chars->array[j]);
						}
						else {
							jprintf("\\0");
						}
					}
					jprintf("\"");
					if (str->chars->length >= clen) {
						jprintf(" corrupt! ");
					}
				} else {
					jprintf("   = null");
				}
			}
			else {
				jprintf("%s", cname);
			}
			jprintf("\n");
		}
	}
	jprintf("end\n");
}
#endif

static int isPointer(jobject test) {
	char* ptr=HEAP2OBJ(&heapmem.heapControl0);
	jheapsize len;

	// true only if test is the begin of a heap alloc
	for(; (len=getObjLength(ptr)) && (char*)test!=ptr; ptr+=len) {
		// noop
	}
	return len;
}

/**
 * if pObj is an object on the heap AND is not marked
 * then mark it and return 1
 * else return 0
 */
int setMark(jobject pObj) {
	char* ptr=HEAP2OBJ(&heapmem.heapControl0);
	if ((char*)pObj<ptr || ptr+sizeof(heapmem_t)<=(char*)pObj) return 0; // test is no heap pointer
	if ( !isMarked(pObj) && isPointer(pObj)) { // Marked returns the marked flag from object header
        MarkBit(pObj); // Marks the flag in obj header
		return 1;
	}
	return 0;
}

jobject Reuse(jheapsize size) {
	char* ptr;
	jheapsize len;
	for(ptr=HEAP2OBJ(&heapmem.heapControl0); (len=getObjLength(ptr)); ptr+=len) {
		if(isFree(ptr)) {
			if (size==len) break; // Perfect hit so don't allocate extra FREE block
			if (size+sizeof(heap_t)<=len) {
				jobject free=ptr;
#if _DEBUG
				usedHeap+=size;
#endif
				size=len-size;
				if (2*(ptr-(char*)(&heapmem.heapControl0))>HEAPSIZE-len) {
					ptr+=size;
				} else {
					free=(ptr+(len-size));
				}
				setAllocsize(free, size);
				setClass(free, FREE);
				break;
			}
		}
	}
	if (len==0) ptr=NULL;
	return (jobject)ptr;
}


jobject newInstance(jclass clazz) {
	return GCalloc(clazz, COMPACTINGWITHTAGS(pgm_read_byteRef(clazz->mem)));
}

array_t * newarray(jint length, jclass baseclass) {
	//Every array is an Object
	array_t * a= (array_t *)GCalloc(baseclass, sizeof(array_t) + length*pgm_read_byteRef(baseclass->mem));
#if	HAIKU_PanicExceptions & OutOfMemoryError
	if (a!=NULL) a->length=length;
#else
	a->length=length;
#endif
	return a;
	/*
	static const int size PROGMEM= (sizeof(jdouble)<<12)+(sizeof(jfloat)<<10)+(sizeof(jlong)<<8)+(sizeof(jint)<<6)+(sizeof(jchar)<<4)+(sizeof(jbyte)<<2)+sizeof(jobject);
	int s=pgm_read_wordRef(size);
	s>>=2*GETCODEBYTE2();
	newarray(s&0x3);
	*/
}

void multiArray(jobjectArray* GCroot, int d, jclass baseclass) {
	jint length=(dataSp-d)->i;
	d--;
	if (d) {
		int i;
		jobjectArray a=(jobjectArray)newarray(length, (jclass)&YLjava_lang_ObjectClass);
#if	HAIKU_PanicExceptions & OutOfMemoryError
	    if (a==NULL) return;
#endif
		*GCroot=a;
		for (i=0; i<length; i++) {
			multiArray((jobjectArray*)&(a->array[i]), d, baseclass);
		}
	} else {
		*GCroot=(jobjectArray)newarray(length, baseclass);
	}
}

stackBlock_t* newStack(int length) {
	return (stackBlock_t *)newarray(length, (jclass)&YLstack__class);
}

HVM_String * newString(char * string) {
	int length = strlen(string);
	ldc_String* str=(ldc_String*)GCalloc((jclass)&java_lang_String__class, COMPACTINGWITHTAGS(sizeof(ldc_String)+length));
#if	HAIKU_PanicExceptions & OutOfMemoryError
	if (str==NULL) return NULL;
#endif
	str->chars=(jchar8or16Array)&(str->length);
	setAllocsize(str->chars, sizeof(heap_t)+sizeof(array_t)+length);
    str->chars->length=length;
    memcpy(str->chars->array, string, length);
 	return (HVM_String*) str;
}

