/**
 * Compacting with pointers taged.
 *
 *
 * The problem with this solution is top.s1.a. I don't know if it's a pointer or not.
 * In turn I don't know something about stack values. 
 * Are they valid object pointers or just binaries which look like a valid pointer by chance?
 *
 */

static jobject moveException;

static int isTagedAsPointer(char * obj, int len, int offset) {
	char byte = obj[len - offset / 8 - 1];
	return byte & (1 << (offset % 8)) | 1;
}

#define JOBJECT 1
#define JSTACK 2
#define JARRAYofJOBJECTs 3
#define JARRAYofPrimitives 0
extern const aclass_t arrayDefs[] PROGMEM;

static int typeOfObject(jobject obj) {
	jclass clazz=getClass(obj);
	if (clazz == &arrayDefs[8]) 
		return JSTACK;
	if (clazz == &arrayDefs[0]) 
		return JARRAYofJOBJECTs;
	if (&arrayDefs[0] < clazz && clazz < &arrayDefs[8]) 
		return JARRAYofPrimitives;
	return JOBJECT;
}

static void Mark() {
	int markFlag=1;

	// Mark (short but slow -> O(N*N) maybe O(N*N*N) because of indirect use of isPointer(..) in setMark(..))
	while(markFlag) {
		char* obj;
		//char* p;
		jheapsize len;
		jheapsize end;
		jheapsize i;
		markFlag=0;
		for(obj=HEAP2OBJ(&heapmem); (len=getObjLength(obj)); obj+=len) {
			if (isMarked(obj)) {
				/*
				 * We have a heap block of length len which is marked (as reachable).
				 * Now we go thru this jobject (starting at obj).
				 * If we hit a pointer to another jobject we mark the corresponding heap block as reachable, too.
				 */
				switch(typeOfObject(obj)) {
				case JOBJECT: 
					// ordenary jobject with possible pointers to other objects
					end=len-sizeof(heap_t)-(sizeof(void*)-1);
					for(i=0; i<end; i++) {
						if (isTagedAsPointer(obj, len, i) && setMark(*(jobject*)(obj+i))) markFlag=1;
					}
					break;
				case JSTACK:
					// stack
					end=len-sizeof(heap_t);
					for(i=0; i<end; i+=sizeof(stack_t)) {
						if (isTagedAsPointer(obj, len, i) && setMark(*(jobject*)(obj+i))) markFlag=1;
					}
					break;
				case JARRAYofJOBJECTs:
					// jarray of pointers to other objects like e.g.:
					//   Object array[]=new Object[..]
					//   String array[]=new String[..]
					//   int  array[][]=new int[..][..]
					end=len-sizeof(heap_t);
					for(i=0; i<end; i+=sizeof(void*)) {
						if (setMark(*(jobject*)(obj+i))) markFlag=1;
					}
					break;
				default:;
					// jarray of 1 dimension of other primitive type e.g.:
					//   int  array[]=new int[..]
					//   char array[]=new char[..]
					// nothing to do because there are no pointers.
				}
			}
		}
	}
}

/**
 * reset all known pointers pointing to 'from' to 'to'
 * if we found at least one return 1
 * else return 0
 */
static int renumber(char* from, char * to) {
	char* obj;
	jheapsize len;
	jheapsize end;
	jheapsize i;
	int isRenumbered;

	for(i=0; i<=STATIC_ROOTS; i++) {
		if(allStatics.gcRoots[i]==from) { allStatics.gcRoots[i]=to; isRenumbered=1;	}
	}
//	setMark(top.s1.a); // ToDo: BIG problem we don't know if top.s1.a is a pointer
//	if(top.s1.a==from) { top.s1.a=to; isRenumbered=1; }

	for(obj=HEAP2OBJ(&heapmem); (len=getObjLength(obj)); obj+=len) {
		if (obj<to || isMarked(obj)) {
			/*
			 * We have a heap block of length len which is reachable.
			 * Now we go thru this jobject (starting at obj).
			 * If we hit a pointer == from we set it to to.
			 */
			switch (typeOfObject(obj)) {
			case JOBJECT:
				// ordenary jobject with possible pointers to other objects
				end = len - sizeof(heap_t) - (sizeof(void*) - 1);
				for (i = 0; i < end; i++) {
					if (isTagedAsPointer(obj, len, i) && *(jobject*)(obj + i) == from) { *(jobject*)(obj + i) = to; isRenumbered = 1; }
				}
				break;
			case JSTACK:
				// stack
				end = len - sizeof(heap_t);
				for (i = 0; i < end; i += sizeof(stack_t)) {
					if (isTagedAsPointer(obj, len, i) && *(jobject*)(obj + i) == from) { *(jobject*)(obj + i) = to; isRenumbered = 1; }
				}
				break;
			case JARRAYofJOBJECTs:
				// jarray of pointers to other objects like e.g.:
				//   Object array[]=new Object[..]
				//   String array[]=new String[..]
				//   int  array[][]=new int[..][..]
				end = len - sizeof(heap_t);
				for (i = 0; i < end; i += sizeof(void*)) {
					if (*(jobject*)(obj + i) == from) { *(jobject*)(obj + i) = to; isRenumbered = 1; }
				}
				break;
			default:;
				// jarray of 1 dimension of other primitive type e.g.:
				//   int  array[]=new int[..]
				//   char array[]=new char[..]
				// nothing to do because there are no pointers.
			}
		}
	}
	return isRenumbered;
}

static void Sweep() {
	char* ptr;
	char* p;
	jheapsize len;
	jheapsize i;

	// Sweep Version 1
	for(ptr=HEAP2OBJ(&heapmem); (len=getObjLength(ptr)); ptr+=len) {
		if(!isMarked(ptr)) {
			// ptr is Free OR  not marked/reachable
			char* lastFreeBlock=ptr;
			jheapsize lenFree=len;
			setClass(lastFreeBlock, FREE); //now ptr is Free

			for(ptr+=len; (len=getObjLength(ptr)); ptr+=len) {
				//ptr is after @ (ptr == a)
				//F@abc
				if(isMarked(ptr)) {
					if (ptr == moveException) goto resync;
					if (renumber(ptr, lastFreeBlock)) {
						// contradiction: how could it be marked?
						jprintf("");
					}
					//compacting by swapping F and a
					memcpy(lastFreeBlock-sizeof(heap_t), ptr-sizeof(heap_t), len);
					setAllocsize(lastFreeBlock, len); // (re-)set len AND unMark

					//setup moved Free block
					lastFreeBlock+=len;
					setClass(lastFreeBlock, FREE);
					setAllocsize(lastFreeBlock, lenFree); // (re-)set len AND unMark
					//after ptr+=len: aF@bc
				} else {
					//compact F@Uabc
					lenFree += len;
					setAllocsize(lastFreeBlock, lenFree); // (re-)set len
					//after ptr+=len: F-@abc
				}
			}
			return;
		}
resync:;
	}
}

void native_java_lang_System_gc_V() {
	int i;
	jprintf("\nGC ####################\n");
	showstack("");

	haikuHeapWalk("heap", 1);

	moveException=NULL;
	if (setMark(top.s1.a)) {
		// BIG problem we don't know if top.s1.a is a pointer
		// solution:
		// we mark it but we don't move it
		moveException = top.s1.a;
	};

	memset(dataSp, 0, ((int)&currentStackblock->stackbase[currentStackblock->length] - (int)dataSp)); // good for GC
	for(i=0; i<=STATIC_ROOTS; i++) {
		setMark(allStatics.gcRoots[i]);
	}
    Mark();
    Sweep();
	haikuHeapWalk("heap", 1);
}

jobject GCalloc(jclass clazz, jint size) {
	int rec=0;
	jobject ptr;

	if (GCcalls<20) {
		GCcalls++;
gc:
		native_java_lang_System_gc_V();
	}
	ptr=Reuse(size+sizeof(heap_t));
	if (ptr) {
		memset(ptr, 0, size);
		setClass(ptr, clazz);
		setAllocsize(ptr, size+sizeof(heap_t));
	} else {
		if (rec) {
#if NO_MICRO && _DEBUG
			jprintf("needed %d (+%d if reuse) more bytes\n", size+sizeof(heap_t), sizeof(heap_t));
#endif
			//fatal("heap overflow");
			panic(OutOfMemoryError, size);
		} else {
			rec=1;
			goto gc;
		}
	}
	return ptr;
}
