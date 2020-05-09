#include <stdlib.h>
#include <stdio.h>
#include <stdarg.h>
#include <string.h>


#include "haikuJ2C.h"
#include "utility/haikuConfig.h"
#include "simpleOS.h"

#if TRACEING
int indent=0;
#endif

TVM tvm;

stackBlock4Debug_t* currentStackblock;

top_t top;

extern const class_t java_lang_Object__class PROGMEM;
extern const jclass	classTable[] PROGMEM;

#if _DEBUG || NO_MICRO
//stange: The arduino IDE tells me this:
//error: variable 'functionDesc' must be const in order to be put into read-only section by means of '__attribute__((progmem))'

extern const char *	functionDesc[] PROGMEM;
extern const char *	classDesc[] PROGMEM;
#endif

#if _DEBUG || NO_MICRO
extern jheapsize getObjLength(jobject obj);

// adapted from heap.c
static int isPointer(jobject test) {
	char* ptr=HEAP2OBJ(&heapmem.heapControl0);
	jheapsize len;

	// true only if test is the begin of a heap alloc
	for(; (len=getObjLength(ptr)) && (char*)test!=ptr; ptr+=len) {
		if (ptr+len<((char*)&heapmem.heapControl0) || ((char*)HEAP2OBJ(&heapmem.heapControl0))+HEAPSIZE<ptr+len) {
			//heap is corrupt
			return 0;
		}
	}
	return len;
}

const char * findFoo(unsigned char * lpc) {
	ByteCode * foo, *tfoo;
	int k, fooIdx;
	foo=NULL;
	for(k=0; (tfoo=(ByteCode *)pgm_read_wordRef(functionTable[k])); k++) {
		if ((unsigned int)lpc>(unsigned int)tfoo && (unsigned int)lpc-(unsigned int)tfoo < (unsigned int)lpc-(unsigned int)foo) {
			foo=tfoo;
			fooIdx=k;
		}
	};
	if (foo==NULL || (unsigned int)lpc-(unsigned int)foo>3000 ) return "<unknown function>";
	return functionDesc[fooIdx];
}

const char * findClass(jclass clazz) {
	int k;
	jclass tclazz;
	for(k=0; ; k++) {
		tclazz=(jclass)pgm_read_wordRef(classTable[k]);
		if (tclazz==clazz) {
			return classDesc[k];
		}
		if (tclazz==NULL) break;
	};
	return "<unknown class>";
}

static jstack info(stackBlock4Debug_t*F, int pos, top_t *top) {
	if (isPointer(top->s0.a)) {
		jprintf("%p %2d\t%8p  Object(class==%8p)[%d] %s\n ", &F->stackbase[pos], pos, top->s0.a, getClass(top->s0.a), getAllocsize(top->s0.a), findClass(getClass(top->s0.a)));
	} else if (STACKIDX(F, top->s0.a)>=0 && STACKIDX(F, top->s0.a) < F->length) {
		jprintf("%p %2d\t%8p Stack frame pointer %d\n ", &F->stackbase[pos], pos, top->s0.a, STACKIDX(F, top->s0.a));
		return (jstack) top->s0.a;
#if	_DEBUG
	} else if ((unsigned int)top->s0.a==0xCDCDCDCD) {
		jprintf("%p %2d\t%8p  undefined\n ", &F->stackbase[pos], pos, top->s0.a);
#endif
	} else if (findFoo((unsigned char *)top->s0.a)[0]!='<') {
		jprintf("%p %2d\t%8p Function %s\n ", &F->stackbase[pos], pos, top->s0.a, findFoo((unsigned char *)top->s0.a));
	} else {
		// basic data type
//		jprintf("%p %2d\t%8p %4d %12d %12lld %15g\n ", &F->stackbase[pos], pos, top->s0.a, (jbyte)top->s0.i, top->s0.i, top->j, top->d);
    // TOOD: Having trouble pass floats for ARM...for now, don't.
		jprintf("%p %2d\t%8p %4d %12d %12lld\n ", &F->stackbase[pos], pos, top->s0.a, (jbyte)top->s0.i, top->s0.i, top->j);
	}
	return NULL;
}
/*
   L    s
 [...o123
or:
     l      s
 [...o123::Lp
or:
 F L S          l      s
 [...o123  ] [SFo123::Lp
*/
void showstack(char * msg) {
	stackBlock4Debug_t* F;
	native_java_lang_Thread* ct;
	top_t ssTop;
	jstack shift_lsp0=lsp, shift_lsp1=NULL;
	jstack s=dataSp, c;
	int i;

	jprintf("\nWe are in function %s at %p", findFoo(pc), pc);
	if (currentStackblock ==NULL) {
		jprintf("\ncurrentStackblock==NULL\n");
	}
	else if (currentStackblock->length == 0) {
		jprintf("\ncurrentStackblock->length==0\n");
	}
	else if (currentThread->stackblock != currentStackblock) {
		jprintf("\ncurrentThread->stack!=stack (%p != %p)\n", currentThread->stackblock, currentStackblock);
	} else {
		ct=currentThread;
		F= currentStackblock;
		ssTop.s0=top.s1; ssTop.s1.a=0;
		jprintf("\n#############(thread=%p) t=%d %s\n", ct, t, msg);
		if (F==NULL) {
			jprintf("------------------------(stack=%p | [%d])\n ", F, 0);
		} else {
			jprintf("------------------------(stack=%p | [%d] -> %d)\n", F, F->length, getAllocsize(F));
			i=((int)s-(int)&F->stackbase[0])/sizeof(stack_t)-1;
			if (i+1>=F->length) {
				jprintf("!");
			} else {
				jprintf(" ");
			}
			info(F, i+1, &ssTop);
			if(i<0) F=NULL;
		}

		while(1) { // for each thread
			while (F!=NULL) { // for each stack (of this thread)
				for(; i>=2; i--) {
					ssTop.s1=ssTop.s0; ssTop.s0=F->stackbase[i];
					c=info(F, i, &ssTop);
					if (c!=NULL) shift_lsp1=c;
					if (&F->stackbase[i]==shift_lsp0) {
						jprintf("\n ");
						shift_lsp0=shift_lsp1;
					}
				}
		        jprintf("%p previous stack=    %8p\n ", &F->stackbase[1], F->stackbase[1].a);
		        jprintf("%p last stack pointer=%8p\n ", &F->stackbase[0], F->stackbase[0].a);
				jprintf("%p length=            %d\n", &F->length, F->length);

				s=(jstack)(F->stackbase[0].a);
				F=(stackBlock4Debug_t*)(F->stackbase[1].a);
				jprintf("\n");
				// is F the next stack Frame?
				// some heuristiks:
				if (s==NULL || !isPointer((jheap)F) || F->length==0 || getAllocsize(F)!=sizeof(heap_t)+sizeof(array_t)+F->length*sizeof(stack_t)) break;
				// Yes F seems to be valid
				i=((int)s-(int)&F->stackbase[0]-1)/sizeof(stack_t);
				jprintf("------------------------(stack=%p | %d)\n ", F, F->length);
				ssTop.s0.a=0;
			}
			ct=(native_java_lang_Thread*)ct->next;
			if (ct==currentThread) break;
			F=ct->stackblock;
			s=ct->stackpointer;
			shift_lsp0=ct->stackframe;
			i=((int)s-(int)&F->stackbase[0])/sizeof(stack_t);
			jprintf("=============(thread=%p)\n", ct);
			jprintf("------------------------(stack=%p | %d)\n ", F, F->length);
		}
	}
}
#endif


#if	HAIKU_PanicSupport
extern ByteCode haiku_vm_MicroKernel_panic_IIV;

void invokePanic(const ByteCode * bytecode, jint exceptionCode, jint exceptionArg) {
	int max_stack=pgm_read_byteRef(bytecode->max_stack);
	int purParams=pgm_read_byteRef(bytecode->purParams);
	jstack nlsp;
	// L s
	//...?
	// Because it's evil if we are here caused by StackOverflow -> may be no space  is left in frame
	// We have to check this
	if (&currentStackblock->stackbase[currentStackblock->length]<=dataSp) {
		// we have panic recursive to invoke, so pushTop() was done there.
		// So we allready have:
		// L  s
		//...?
#if	NO_MICRO && _DEBUG
		jprintf("");
#endif
	} else {
		pushTop();
		// L  s
		//...?
	}
	nlsp=dataSp;
	if (&currentStackblock->stackbase[currentStackblock->length]<=nlsp+max_stack) {
		stackBlock_t * newstack=newStack(2/*old_lsp, old_stack*/+max_stack);
		if (newstack==NULL) {
#if	HAIKU_PanicExceptions & StackOverflowError
			if (heapmem.allStatics.statics.haiku_vm_MicroKernel_panicRoom!=NULL) {
				// let the GC use the panicRoom
				heapmem.allStatics.statics.haiku_vm_MicroKernel_panicRoom=NULL;
				// and try again ...
				newstack=newStack(2/*old_lsp, old_stack*/+max_stack);
				if (newstack==NULL) {
					athrow(NULL);
					return;
				}
				// ... but this time as StackOverflowError
				exceptionCode=StackOverflowError;
			} else {
				athrow(NULL);
				return;
			}
#else
			athrow(NULL);
			return;
#endif
		}
		memset(dataSp, 0, ((int)&currentStackblock->stackbase[currentStackblock->length]-(int)dataSp)); // good for GC
		// link old stack with new stack and set it in currentStack
		newstack->stackbase[0].v=nlsp;
		newstack->stackbase[1].v= currentStackblock;
		currentStackblock = (stackBlock4Debug_t *)newstack;
		currentThread->stackblock = currentStackblock;

		nlsp=&currentStackblock->stackbase[2];
		dataSp=nlsp;
	}
	// set stack pointer
	dataSp+=purParams+pgm_read_byteRef(bytecode->purLocals)+1;

	// save (lsp, pc)
	(dataSp-1)->v=lsp;
	top.s1.v=pc; // the next push will write it on the stack
	//   l    s
	//...12::Lp

	// set (lsp, pc)
	lsp=nlsp;
	isetLocal(0, exceptionCode);
	isetLocal(1, exceptionArg);

	pc=(unsigned char *)bytecode->code;
}

/**
 * invoke(&haiku_vm_MicroKernel_panic_IIV);
 * sets top.s1.a ! So don't overwrite it.
 */
void panic(jint exceptionCode, jint exceptionArg) {
	static int rec=0;
	// First: prevent recursive exceptions during panic setup
	rec++;
	if (rec>1) return;
#if	NO_MICRO && _DEBUG
	showstack("panic");
	haikuHeapWalk("heap", 1);
#endif
	invokePanic(&haiku_vm_MicroKernel_panic_IIV, exceptionCode, exceptionArg); // may throw recursive OutOfMemoryError
	rec=0;
}

#else

static void fatal(char * msg, jint exception, jint arg) {
#if	_DEBUG
	jprintf("\n");
	jprintf("[%d]\n", bc);
	jprintf("'%s' exception=%d arg=%d\n", msg, exception, arg);
	showstack(msg);
	haikuHeapWalk("heap", 1);
#if NO_MICRO
	getc(stdin);
#endif
#endif

#if NO_MICRO
	exit(exception);
#else
#if	HAIKU_PanicSupport!=0
	athrow(NULL);
#endif
#endif
}

void panic(jint exceptionCode, jint exceptionArg) {
	fatal("panic", exceptionCode, exceptionArg);
}
#endif

extern const class_t java_lang_Object__class;

void * getMethod(jobject obj, int msgIdx) {
#if NO_MICRO
	if (obj==NULL) {
		panic(NullPointerException, 0);
#if	HAIKU_PanicSupport
		return &haiku_vm_MicroKernel_panic_IIV;
#endif
	}
#endif
	{
		int i;
		jclass clazz=getClass(obj);
secondTryAsObject:
		if((char*)obj<(char*)&heapmem.heapControl0 || ((char*)&heapmem.heapControl0)+HEAPSIZE<=(char*)obj) {
			// Assumption here that obj was a class like
			// in Monitor.class.wait(10) looking for a method
			// AND classes are NOT stored in heap.
			// (Danger on Harvard architectures)
			clazz=(jclass)&java_lang_Object__class;
		}
		while (clazz!=NULL) {
			for( i=0; i<pgm_read_byteRef(clazz->size); i++) {
				if (pgm_read_byteRef(clazz->msg2meth[i].msgIdx)==msgIdx) {
					return (void *) (pgm_read_wordRef(clazz->msg2meth[i].meth));
				}
			};
			clazz=(jclass)(pgm_read_wordRef(clazz->superClass)); // follow parent
		}
		// second try with Object
		// Assumption here that obj was a class like
		// in Monitor.class.wait(10) looking for a method.
		// Heuristik (not perfect) for Harvard architectures.
		if (obj!=NULL) {
			obj=NULL; //prevent endless loop
			goto secondTryAsObject;
		}
	#if	_DEBUG
		jprintf("\n");
		jprintf("msgIdx={%d} ", msgIdx);
	#endif
		//fatal("no Method found!");
		panic(obj==NULL?NullPointerException:NoSuchMethodError, msgIdx);
#if	HAIKU_PanicSupport
		return &haiku_vm_MicroKernel_panic_IIV;
#endif
		return NULL;
	}
}

#if	_DEBUG && (HAIKU_PanicExceptions & StackOverflowError)
static int check(jstack p) {
	int sp=STACKIDX(currentStackblock, p);
	if (sp<0 || sp>= currentStackblock->length) {
		panic(StackOverflowError, sp);
		return 1;
	}
	return 0;
}
#endif

#if	HAIKU_PanicExceptions & ArrayIndexOutOfBoundsException
int checkIndex(array_t * adr, jint index) {
	if (adr==NULL) {
		panic(NullPointerException, 0);
		return 0;
	}
	if (index<0 || index>=adr->length) {
		panic(ArrayIndexOutOfBoundsException, index);
		return 0;
	}
	return 1;
}
#else
	// see haikuJ2C.h: #define checkIndex(adr, index) 1
#endif


jstack push() {
#if	_DEBUG && (HAIKU_PanicExceptions & StackOverflowError)
	if (check(dataSp /* not +1*/)) return dataSp;
#endif
	return dataSp++;
};

top_t* getLocal(int i) {
#if	_DEBUG && (HAIKU_PanicExceptions & StackOverflowError)
	if (check(lsp+i)) return lsp;
#endif
	return (top_t*)(lsp+i);
}

void isetLocal(int i, int val) {
	getLocal(i)->s0.i=val;
}

void setLocal(int i, jstack val) {
	getLocal(i)->s0=*val;
}

void vsetLocal(int i, void* val) {
	getLocal(i)->s0.v=val;
}

void ipush(int val) {
	push()->i=val;
};

void pushTop0() {
	*push()=top.s0;
};

void pushTop() {
	*push()=top.s1;
};

void vpush(void * val) {
	push()->v=val;
};

jstack pop() {
#if	_DEBUG && (HAIKU_PanicExceptions & StackOverflowError)
	if (check(dataSp-1)) return dataSp;
#endif
	return --dataSp;
}

void popTop0() {
	top.s0=*pop();
}

void popTop() {
	top.s1=*pop();
}

jobject apop() {
	return pop()->a;
}

jint ipop() {
	return pop()->i;
}

jfloat fpop() {
	return pop()->f;
}

top_t* popp2() {
#if	_DEBUG && (HAIKU_PanicExceptions & StackOverflowError)
	if (check(dataSp-2)) return dataSp;
#endif
	dataSp-=2;
	return (top_t*)dataSp;
}

void ldc_string() {
	void* src=(void*)GETCODEADR2();
	pushTop();

#if HAIKU_Mode==HAIKU_32_64 || HAIKU_Mode==HAIKU_32_32
	int length = HAIKU_Char*pgm_read_dwordRef(((ldc_jstring_t*)src)->length);
#elif HAIKU_Mode==HAIKU_8_16 || HAIKU_Mode==HAIKU_8_8
	int length = HAIKU_Char*pgm_read_byteRef(((ldc_jstring_t*)src)->length);
#else
	int length = HAIKU_Char*pgm_read_wordRef(((ldc_jstring_t*)src)->length);
#endif
	ldc_String* str = (ldc_String*)GCalloc((jclass)&java_lang_String__class, COMPACTINGWITHTAGS(sizeof(ldc_String) + length));
#if	HAIKU_PanicExceptions & OutOfMemoryError
	if (str == NULL) return;
#endif
	str->chars = (jchar8or16Array)&(str->length);
	// 1) We have to set xallocsize because getSize(array_t* arr) needs this to calc the length of *one* element. See System.arraycopy(..)
	str->heapControl.xallocsize = (sizeof(array_t) + length + sizeof(heap_t));
	memcpy_P(&(str->length), src, length + sizeof(jint));
	top.s1.str = (HVM_String *)str;
}

/**
 * test condition if
 * true: read next sizeof(void*) bytes and jump to this address
 * false: proceed to next bytecode (no jump)
 */
void compare(jint v) {
	// sadly "fast dispatch" is more expensive +20 Byte and +100ms
	unsigned char* offset = (unsigned char*)GETCODEADR2();
	int8_t t;

	if		(v> 0)  {t=1; }
	else if (v)		{t=4; }
	else /* (v==0)*/{t=2; }

	if (t&bc) {
		pc=offset;
	}
	popTop();
};

char * getField() {
	char* obj=(char*)top.s1.v;
#if	HAIKU_PanicExceptions & NullPointerException
	if (obj==JNULL) {
		panic(NullPointerException, 0);
		// and keep &pc (from above panic call) in case there is a push of getfield (but its risky see eg. GETFIELD_B)
		return NULL;
	}
#endif
	return obj + GETCODEWORD2();
}
/**
 * Enters a method. The method may be either Java or native. If the method is
 * native, the param bytecode is used to get the function pointer to be called.
 *
 * If the method is not native, check if method frame fits into current
 * stack segment. If not, a new stack segment is allocated. In either case
 * the context switch to the new method frame is performed.
 *
 * @param bytecode pointing to the method to be executed
 *
from:
   L    s
 [...o123

to:
     l      s
 [...o123::Lp

or, if a new Stack is needed:
 S   L   s      l      s
 [...o123  ] [sSo123::Lp

with:
 [o=object]
 [1=param1]
 [2=param2]
 [..]
 [N=paramN]
 [:=placeholder for a local]

 p=pc //is ONLY written into variable top NOT on stack itself
 l=lsp
 s=dataSp
 S=stack
 L=(prev) lsp
*/
void invoke(const ByteCode * bytecode){
	//jprintf("tag=%d\n", pgm_read_byteRef(bytecode->tag));
	//jprintf("code=%p %p %p\n", &java_lang_System__clinit__V, bytecode, &(bytecode->tag));
	//
	//tried but no benefit (time(+3%) & space(+0.5%)):
	//  TaggedCode tc;
	//  memcpy_P(tc, bytecode, sizeof(TaggedCode));

	//max_stack already includes the extra size needed for storing l and p
	int max_stack=pgm_read_byteRef(bytecode->max_stack);

	// check if the method is a native methods
	if((uint8_t)max_stack==0xff) {
		//call native function
		BytecodeFoo nativeFoo=(BytecodeFoo)pgm_read_wordRef(((NativCode*)bytecode)->nativeFoo);
		(*nativeFoo)();
	} else {
		// calculate the size of the frame to create
		// remark: using int is 10 bytes shorter than uint8_t
		int purParams=pgm_read_byteRef(bytecode->purParams);
		jstack nlsp;
		// L    s
		//...o123
		pushTop();
		// L     s
		//...o123
		nlsp=dataSp-purParams;
/*
#if	_DEBUG
		if(1||t>0) {
			Debug(jprintf("\nlength %d\t", stack->length);)
			Debug(jprintf("sp %d\t", ((int)dataSp-(int)&stack->stack[0])/sizeof(stack_t));)
			Debug(jprintf("max_stack=%d locals=%d params=%d\n", max_stack, pgm_read_byteRef(bytecode->purLocals), purParams);)
		}
#endif
*/
		// check if method frame fits into current stack segment.
//		if (stack->length-nlsp<=max_stack) {
		if (&currentStackblock->stackbase[currentStackblock->length]<=nlsp+max_stack) {
			// we need a new stack block for this frame
			int i;
			stackBlock_t * newstack=newStack(2/*old_lsp, old_stack*/+max_stack);
#if	HAIKU_PanicExceptions & StackOverflowError
			if (newstack==NULL) {
				return;
			}
#endif
			memset(dataSp, 0, ((int)&currentStackblock->stackbase[currentStackblock->length]-(int)dataSp)); // good for GC
			newstack->stackbase[0].v=nlsp;
			newstack->stackbase[1].v= currentStackblock;

			Debug(jprintf("we need a new stackFrame of 4 + %d\n", pgm_read_byteRef(bytecode->purLocals)+purParams+max_stack);)

			// now copy function purParams into new Stackframe
//			memcpy(&newstack->stack[2], &stack->stack[nlsp], purParams*sizeof(stack_t)); // needs more bytes
			for(i=0; i<purParams; i++) {
				newstack->stackbase[2+i]=*(nlsp++);
			}
			currentStackblock = (stackBlock4Debug_t *)newstack;
			currentThread->stackblock = currentStackblock;

			nlsp=&currentStackblock->stackbase[2];
			dataSp=nlsp+i;
			//Debug(showstack("new frame", lsp);)
		}
#if	_DEBUG
		{
			int i;
			for(i=0; i<pgm_read_byteRef(bytecode->purLocals); i++)
				(dataSp+i)->a=(jobject)0xCDCDCDCD;
		}
#endif
		// set stack pointer
		dataSp+=pgm_read_byteRef(bytecode->purLocals)+1;

		// save (lsp, pc)
		(dataSp-1)->v=lsp;
		top.s1.v=pc; // the next push will write it on the stack

		// set (lsp, pc)
		lsp=nlsp;
		//   l      s
		//...o123::Lp
		pc=(unsigned char *)bytecode->code;
#if	TRACEING
		jprintf("%-.*s{ %p [%p, %3d]%3d %s(", indent, "|||||||||||||||||||||||||||||||||||||||||||||||||+", bytecode, currentStackblock, ((int)dataSp-(int)&currentStackblock->stackbase[0])/sizeof(stack_t), currentStackblock->length, findFoo(pc));
		{
			int i;
			for(i=0; i<pgm_read_byteRef(bytecode->purParams); i++) {
				jprintf("%p/%d", (lsp+i)->v, (lsp+i)->i);
				if (i+1<pgm_read_byteRef(bytecode->purParams)) jprintf(", ");
			}
		}
		jprintf(")\n");
		indent++;
#endif
	}
}

void areturn() {
	jstack olddataSp=dataSp;
	//   l       s
	//...o123::Lpr
	//L  s
	//...r
	// jedoch r=top.s1.{i,a,f}
	if (lsp==&currentStackblock->stackbase[2]) {
		lsp=(jstack)currentStackblock->stackbase[0].v;
		currentStackblock =(stackBlock4Debug_t *)currentStackblock->stackbase[1].v;
		currentThread->stackblock = currentStackblock;
	};
	dataSp=lsp;
	pc=(unsigned char *)(olddataSp-1)->v;
	lsp=(jstack)(olddataSp-2)->v;
	Traceout("%-*s}\n%-*s= %p [%p, %3d] %s\n", --indent, "", indent-1, "", pc, currentStackblock, ((int)dataSp-(int)&currentStackblock->stackbase[0])/sizeof(stack_t), findFoo(pc));
	// r=top.s1.{i,a,f}
}

jint instanceOf(jobject obj, jclass target) {
	if (obj==NULL) return 0;
	{
		jclass clazz=getClass(obj);
		while (clazz!=NULL) {
			if (clazz==target) {
				return 1;
			}
			clazz=(jclass)pgm_read_wordRef(clazz->superClass); // follow parent
		}
		return 0;
	}
}

void athrow(jobject throwable) {
	ByteCode * foo, *tfoo;
	int i,k;
	Traceout("<<<<<<<<<<<<<<<<<<<<<<<<< throw\n");
	while(1) {
	//for(j=0;;j++) {
		pc--;
		foo=NULL;
		for(k=0; (tfoo=(ByteCode *)pgm_read_wordRef(functionTable[k])); k++) {
			if ((unsigned int)pc>(unsigned int)tfoo && (unsigned int)pc-(unsigned int)tfoo < (unsigned int)pc-(unsigned int)foo) {
				foo=tfoo;
				dataSp=lsp+ pgm_read_byteRef(foo->purParams) + pgm_read_byteRef(foo->purLocals) +2;
				//Traceout("%d	%04x %04x (%d, %d)\n", k, pc, foo, pgm_read_byteRef(foo->purParams), pgm_read_byteRef(foo->purLocals));
			}
		};

		// is pc (of foo) in exception table?
		for(i=0; pgm_read_wordRef(exceptionTable[i].from); i++) {
			//Traceout("%d %p <= %p < %p ? (%d)\n", i, pgm_read_wordRef(exceptionTable[i].from), pc, pgm_read_wordRef(exceptionTable[i].to), instanceOf(obj, pgm_read_wordRef(exceptionTable[i].eclazz)));
			if ((unsigned int)pgm_read_wordRef(exceptionTable[i].from)<=(unsigned int)pc && (unsigned int)pc<(unsigned int)pgm_read_wordRef(exceptionTable[i].to) && instanceOf(throwable, (jclass)pgm_read_wordRef(exceptionTable[i].eclazz))) {
				//Traceout("Exception at %d\n", i);
				pc=(unsigned char *)pgm_read_wordRef(exceptionTable[i].handler);
				Traceout(">>>>>>>>>>>>>>>>>>>>>>>>>\n");
				return;
			}
		}
		//Traceout("unroll %d sp=%d lsp=%d\n", j, ((int)dataSp-(int)&stack->stack[0])/sizeof(stack_t), ((int)lsp-(int)&stack->stack[0])/sizeof(stack_t));
		//showstack("unwind");
		if (lsp==&currentStackblock->stackbase[0]) {
			// exit: when in panic() mode it uses throwable==null just to unwind the complete stack
			pc++; // undo pc-- (see begin of this loop)
			return;
		}
		areturn();
	}
}

static int hasLock(native_java_lang_Thread* t, void * lr, int mode) {
	if (t->locks==NULL /*|| t->state==WAITING*/) return 0;
	if (t->locks->lock==lr) {
		if (mode) t->locks=t->locks->next; // dequeue
		return 1;
	} else {
		struct ListLA* la;
		for(la=t->locks; la->next!=NULL; la=la->next) {
			if (la->next->lock==lr) {
				if (mode) la->next=la->next->next; // dequeue
				return 1;
			}
		}
	}
	return 0;
}

extern const class_t java_lang_Object__class PROGMEM;
extern void switchThread();

void monitorenter() {
	native_java_lang_Thread* t= currentThread;
	native_java_lang_Thread* n;
	void * lr=top.s1.v;
	//showstack("monitorenter", NULL);
	for(n=t->next; t!=n; n=n->next) {
		if (hasLock(n, lr, 0)) {
			pc--; // restart monitorenter over and over until nobody else owns lr
			//jprintf("Thread blocked %p pc=%p stack=%p\n", t, pc, dataSp);
			switchThread();
			return;
		}
	}
	if (!hasLock(t, lr, 0)) {
		//enqueue the locked object lr
		ListLA* newla=(ListLA*)GCalloc((jclass)&java_lang_Object__class, COMPACTINGWITHTAGS(sizeof(ListLA)));
		newla->next=t->locks;
		newla->lock=(jobject)lr;
		t->locks=newla;
	}
	// now we are thru and holding the lock on lr
	popTop(); // just pop the lock
}

void monitorexit() {
	native_java_lang_Thread* t= currentThread;
	void * lr=top.s1.v;
	popTop(); // and pop the lock
	if (!hasLock(t, lr, 1)) {
		// throw new IllegalStateException()
	}
}
