/**
 * Compaction in draft state!
 */
static void Mark() {
	char* ptr;
	//char* p;
	jheapsize len;
	jheapsize i;
	char* h=HEAP2OBJ(&heapmem);

	// Mark (short but slow -> O(N*N) maybe O(N*N*N) because of indirect use of isPointer(..) in setMark(..))
rescan:
	for(ptr=h; ptr<heapend; ptr+=len) {
		len=getObjLength(ptr);
		if (isMarked(ptr)) {
			// scan this specific heap alloc block (which starts at ptr)
			for(i=0; i+sizeof(void*)+sizeof(heap_t)-1<len; i++) {
				h=*(jobject*)(ptr+i);
				if (setMark(h) && h < ptr) goto rescan; // only if we marked something before ptr. setMark(h) garanties that h points to a jobject.
			}
		}
	}
}

/**
 * legend:
 * [ &heapmem
 * ] heapend
 * @ ptr
 * - unmarked block
 * . unused mem
 * r root block
 * a-q reachable block
 * s-z unreachable block
 * A-Q S-Z corresponding pointers
 *
 *
 * sketch:
 *  [@aabzyccwdvv]..{WYB.AD..CXVZ.
 *  [@aab--cc-d--]..{WYB.AD..CXVZ.
 *  [aabccd-----@]....{B.AD..C....
 *  [aabccd].....@....{B.AD..C....
 *
 */
static void Compaction() {
	char* ptr; // corresponds to @ marker
	char* free;
	jheapsize len;

	for(ptr=HEAP2OBJ(&heapmem); ptr<heapend; ptr+=len) {
		len=getObjLength(ptr);
		if(!isMarked(ptr)) {
			//  [aab@zyccwdvv]..{WYB.AD..CXVZ.
			// compaction is possible now
			for(free=ptr; ptr<heapend; ptr+=len) {
				len=getObjLength(ptr);
				if (isMarked(ptr)) {
					//  [aab--@ccwdvv]..{WYB.AD..CXVZ.
					// ->
					//  [aabcc--@wdvv]..{WYB.AD..CXVZ.
					// move an used element
					memcpy(OBJ2HEAP(ptr), OBJ2HEAP(free), len);
					UpdateSlot(ptr, free);
					free+=len;
				} else {
					//  [aab@zyccwdvv]..{WYB.AD..CXVZ.
					// ->
					//  [aab-@yccwdvv]..{WYB.AD..CXV..
					// step over an unmarked (free) element
					UpdateSlot(ptr, NULL);
				}
			}
			memset(heapend, 0, slotbegin-heapend);
			heapend=free;
		}
	}
}

void native_java_lang_System_gc_V() {
	int i;
	Debug(GCcount++;)
	Debug(jprintf("\nGC ####################\n");)
	Debug(showstack("");)

	Debug(haikuHeapWalk("heap", 1);)
	if (stackblock!=NULL) {
		memset(dataSp, 0, ((int)&stackblock->stackbase[stackblock->length]-(int)dataSp)); // good for GC
	}
	for(i=0; i<=STATIC_ROOTS; i++) {
		setMark(allStatics.gcRoots[i]);
	}
	setMark(top.s1.a);
    Mark();
    Compaction();
	Debug(haikuHeapWalk("heap", 1);)
}

/**
 * get new Block nn with SlotPointer N
 *
 * legend:
 * { slot begin
 *
 *  [aabccd]............{BADC
 *  [aabccdnn].........{NBADC
 * or
 *  [aabccd].........{B.AD..C
 *  [aabccdnn].......{B.AD.NC
 */
static jobject getBlock(jclass clazz, jint size) {
	void** slot;
	// find a slot space
	for(slot=&heapmem+HEAPSIZE-sizeof(void*); slot>=heapend; slot--) {
		if (*slot==NULL && size <= slotbegin-heapsize && size <= slot-heapsize) {
			if (slotbegin > slot) slotbegin=slot;
			*slot=heapend;
			memset(heapend, 0, size); // not needed see Compaction
			setClass(slot, clazz);
			setAllocsize(slot, size);
			heapend+=size;
			return slot;
		}
	}
	return NULL;
}

jobject GCalloc(jclass clazz, jint size) {
	int i=0;
	jobject ptr;
	for (;i<2; i++) {
		ptr=getBlock(size);
		if (ptr) return ptr;
		native_java_lang_System_gc_V();
	};
#if NO_MICRO
	jprintf("needed %d (+%d if reuse) more bytes for %s\n", size+sizeof(heap_t), sizeof(heap_t), findClass(clazz));
#endif
	panic(OutOfMemoryError, size);
	return ptr;
}
