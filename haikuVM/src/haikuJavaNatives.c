#include <string.h>
#include "haikuJ2C.h"
#include "simpleOS.h"

#include "haikuJavaNatives.h"
#include "utility/haikuConfig.h"

void native_java_lang_Double_doubleToRawLongBits_DJ() {
	/* NOTHING TO DO
	 * top.s1.j will present top.s1.d as long
	 */
}

void native_java_lang_Double_longBitsToDouble_JD() {
	/* NOTHING TO DO
	 * top.s1.j will present top.s1.d as long
	 */
}

void native_java_lang_Float_floatToRawIntBits_FI() {
	/* NOTHING TO DO
	 * top.s1.i will present top.s1.f as int
	 */
}

#if NO_MICRO
#define MAXMEMORY 1000
uint8_t mem[MAXMEMORY];
#if defined(WIN32)
#include <io.h>
#include <fcntl.h>

int os_putchar(int b) {
	_setmode(1, _O_BINARY);
	return putchar(b);
}

int os_getchar() {
	return getch();
}
int os_available() {
	return kbhit();
}
#else
int os_putchar(int b) {
	return putchar(b);
}

int os_getchar() {
	return getchar();
}
int os_available() {
//from http://stackoverflow.com/questions/448944/c-non-blocking-keyboard-input
//#include <stdlib.h>
//#include <string.h>
//#include <unistd.h>
//#include <sys/select.h>
//#include <termios.h>
//
//    struct timeval tv = { 0L, 0L };
//    fd_set fds;
//    FD_ZERO(&fds);
//    FD_SET(0, &fds);
//    return select(1, &fds, NULL, NULL, &tv);
	return 1;
}
#endif

void setMemory8(uint8_t * mem_addr, int value) {
	*mem_addr=value;
	switch (mem_addr-mem) {
		case UDR0_IDX:	os_putchar(value); break;
#if _DEBUG
		case PORTB_IDX:
			printf("PORTB=0x%02x;\n", value); break;
		default:
			printf("memory8 [0x%04x]=0x%02x;\n", mem_addr-mem, value);
#endif
	}
}

void setMemory16(uint8_t * mem_addr, int value) {
	mem_addr[0]=value;
	mem_addr[1]=value>>8;
}

int getMemory8(uint8_t * mem_addr) {
	static int kbflag=0;
	switch (mem_addr-mem) {
		case UCSR0A_IDX:
#if defined(WIN32)
			if (!kbflag) kbflag=kbhit();
#endif
			return kbflag?0xa0:0x20;
		case UDR0_IDX:
			kbflag=0;
			return os_getchar(); // not getchar()!!
		case ADCSRA_IDX:
		//while ((getMemory8(ADCSRA_328P) & (1 << ADIF)) == 0)		// end of AD-conversion: wait until ?? (ADF==4)
			return mem_addr[0]|(1<<4);
		default:
#if _DEBUG
			if (mem_addr-mem<0||mem_addr-mem>=MAXMEMORY) {
				showstack("out of range");
				printf("memory8 [0x%04x]->%d;\n", mem_addr-mem, mem_addr[0]);
			} else {
//				printf("memory8 [0x%04x]->%d;\n", top.s1.i, mem[top.s1.i]);
			}
#endif
			return mem_addr[0];
	}
}

int getMemory16(uint8_t * mem_addr) {
	int v=*(uint16_t*)(mem_addr);
	switch (mem_addr-mem) {
		case ADC_IDX:
		//AD-conversion getMemory16(ADC); // result is 10-Bit only
			v=(v+1)%1024;
			return v;
		default:
#if _DEBUG
			printf("memory16[0x%04x]->%d;\n", mem_addr-mem, v);
#endif
			return v;
	}
}

void native_haiku_vm_MemoryAccess_setMemory8_IIV() {
	uint8_t      value=  top.s1.i;
	uint8_t *    mem_addr=  (uint8_t *)&mem[ipop()];
	setMemory8(mem_addr, value);
	popTop();
}

void native_haiku_vm_MemoryAccess_setMemory16_IIV() {
	uint16_t      value=  top.s1.i;
	uint8_t *    mem_addr= &mem[ipop()];
	setMemory16(mem_addr, value);
	popTop();
}

void native_haiku_vm_MemoryAccess_getMemory8_II() {
	top.s1.i= getMemory8(&mem[top.s1.i]);
}

void native_haiku_vm_MemoryAccess_getMemory16_II() {
	top.s1.i= getMemory16(&mem[top.s1.i]);
}
#else

void native_haiku_vm_MemoryAccess_setMemory8_IIV() {
	uint8_t      value=  top.s1.i;
	uint8_t *    mem_addr=  (uint8_t *)ipop();
	*mem_addr=value;
	popTop();
}

void native_haiku_vm_MemoryAccess_setMemory16_IIV() {
	uint16_t      value=  top.s1.i;
	uint16_t *    mem_addr=  (uint16_t *)ipop();
	*mem_addr=value;
	popTop();
}

void native_haiku_vm_MemoryAccess_getMemory8_II() {
	top.s1.i= *(uint8_t*)top.s1.i;
}

void native_haiku_vm_MemoryAccess_getMemory16_II() {
	top.s1.i= *(uint16_t*)top.s1.v;
}

#endif



extern jheapsize getObjLength(jobject obj);

/*
 * Class:     java.lang.Object
 * Method:    clone
 * Signature: ()Ljava/lang/Object;
 */
void native_java_lang_Object_clone_Ljava_lang_Object(void) {
	jobject clone=GCalloc(FREE, getObjLength(top.s1.a)-sizeof(heap_t));
	memcpy(OBJ2HEAP(clone), OBJ2HEAP(top.s1.a), getObjLength(top.s1.a));
    top.s1.a = clone;
}


void native_java_lang_System_getDataAddress_Ljava_lang_Object_I() {
	/* NOTHING TO DO
	 * top.s1.i will present top.s1.a as int
	 *  -> Wrong!! Look at a 16 bit little endian machine in 32/x Mode.
	 * TODO: Still a problem with 64Bit system
	 */
	top.s1.i = top.s1.a;
}

void native_java_lang_System_currentTimeMillis_J() {
	pushTop();
	top.j=millis();
	pushTop0();
}

/**
 * return the size in byte of one element in the array
 */
__attribute__((noinline)) static int getSize(array_t* arr) {
	return (getObjLength((jheap)arr)-sizeof(heap_t)-sizeof(array_t))/arr->length;
}

    /**
Copies an array from the specified source array, beginning at the specified position,
to the specified position of the destination array. A subsequence of array components
are copied from the source array referenced by src to the destination array referenced
by dest. The number of components copied is equal to the length argument. T
he components at positions srcPos through srcPos+length-1 in the source array are
copied into positions destPos through destPos+length-1, respectively, of the
destination array.

If the src and dest arguments refer to the same array object, then the copying is performed as if the components at positions srcPos through srcPos+length-1 were first copied to a temporary array with length components and then the contents of the temporary array were copied into positions destPos through destPos+length-1 of the destination array.

If dest is null, then a NullPointerException is thrown.

If src is null, then a NullPointerException is thrown and the destination array is not modified.

Otherwise, if any of the following is true, an ArrayStoreException is thrown and the destination is not modified:

The src argument refers to an object that is not an array.
The dest argument refers to an object that is not an array.
The src argument and dest argument refer to arrays whose component types are different primitive types.
The src argument refers to an array with a primitive component type and the dest argument refers to an array with a reference component type.
The src argument refers to an array with a reference component type and the dest argument refers to an array with a primitive component type.
Otherwise, if any of the following is true, an IndexOutOfBoundsException is thrown and the destination is not modified:

The srcPos argument is negative.
The destPos argument is negative.
The length argument is negative.
srcPos+length is greater than src.length, the length of the source array.
destPos+length is greater than dest.length, the length of the destination array.
Otherwise, if any actual component of the source array from position srcPos through srcPos+length-1 cannot be converted to the component type of the destination array by assignment conversion, an ArrayStoreException is thrown. In this case, let k be the smallest nonnegative integer less than length such that src[srcPos+k] cannot be converted to the component type of the destination array; when the exception is thrown, source array components from positions srcPos through srcPos+k-1 will already have been copied to destination array positions destPos through destPos+k-1 and no other positions of the destination array will have been modified. (Because of the restrictions already itemized, this paragraph effectively applies only to the situation where both arrays have component types that are reference types.)

Parameters:
src the source array.
srcPos starting position in the source array.
dest the destination array.
destPos starting position in the destination data.
length the number of array elements to be copied.
Throws:
HaikuWRONG: IndexOutOfBoundsException - if copying would cause access of data outside array bounds.
Experiment (see ArrayIndexTest) shows that Sun'S JVM throws: ArrayIndexOutOfBoundsException
ArrayStoreException - if an element in the src array could not be stored into the dest array because of a type mismatch.
NullPointerException - if either src or dest is null.
     */
void native_java_lang_System_arraycopy_Ljava_lang_Object_ILjava_lang_Object_IIV() {
	int      length=  top.s1.i;
	int      destPos= ipop();
    array_t* dest=    (array_t*) apop();
	int      srcPos=  ipop();
	array_t* src=     (array_t*) apop();
	int size;
	popTop();

#if	HAIKU_PanicExceptions & NullPointerException
	if (src==NULL || dest==NULL) {
		panic(NullPointerException, 0);
		return;
	}
#endif
#if	HAIKU_PanicExceptions & ArrayIndexOutOfBoundsException
	if ( srcPos<0 ||destPos<0 ||length <0 || srcPos+length > src->length || destPos+length >dest->length) {
		panic(ArrayIndexOutOfBoundsException, srcPos);
		return;
	}
#endif

	if (length==0) return;

	size=getSize(dest); // Has to be here to be save because otherwise integer div by 0 could happen (if dest->length==0)

#if	HAIKU_PanicExceptions & ArrayStoreException
	if (size!=getSize(src)) {
		panic(ArrayStoreException, size);
		return;
	}
#endif
	memcpy(dest->array+destPos*size, src->array+srcPos*size, length*size);
}


/**
 * Inspired from UNIX fork().
 * This is what happens :
 *  thisThread->programcounter=pc; // current pc
 *  thisThread->stack=new StackFrame(36); // so on thisThread stack, top stack element is an integer 0
 *  push the integer 1 // in contrast: on current stack, top stack element is integer 1
 *  In other words: return 0 if it's the new child Thread else 1
 */
void native_java_lang_Thread_fork_I() {
	native_java_lang_Thread* thisThread=(native_java_lang_Thread*) top.s1.a;
	thisThread->stackblock =         (stackBlock4Debug_t*) newStack(HAIKU_InitialOtherThreadStackSize);
#if	HAIKU_PanicExceptions & OutOfMemoryError
	if (thisThread->stackblock ==NULL) return;
#endif
	thisThread->programcounter=pc;
	thisThread->stackframe=	(jstack) &thisThread->stackblock->stackbase[0];
	thisThread->stackpointer=	(jstack) &thisThread->stackblock->stackbase[1]; // stack[0] is not allowed because areturn() will trigger a (this time wrong) dequeue
	top.s1.i=1;
}

extern void	switchThread();

void native_java_lang_Thread_setStateAndSwitch_II() {
	native_java_lang_Thread* thisThread=(native_java_lang_Thread*) apop();
//	extern	int ccount[];
//		int i;
//		for(i=0; i<6; i++) {
//			printf("%d -> %d\n", i, ccount[i]);
//		};
	thisThread->state=top.s1.i;
	switchThread();
}

#if HAIKU_Threads
/**
 * releases a lock (object.MONITOREXIT) and<br>
 * tries to get it again after waitUntil is reached (object.MONITORENTER).
 *
 * legacy (because now the bytecode is here, see below) WARNING:
 * haiku hack for this method:
 * haiku will modify the code to have the desired byte code result of roughly :
    	object.MONITOREXIT -> automatic inserted
    	... see your code below
    	object.MONITORENTER -> automatic appended
 *
private static void haikuReleaseLock(Object object)
Code(max_stack = 2, max_locals = 1, code_length = 14)
 */
const           java_lang_Thread_haikuReleaseLock_Ljava_lang_Object_V_t java_lang_Thread_haikuReleaseLock_Ljava_lang_Object_V PROGMEM ={
2+1 +2,    0,    1,    // max_stack, purLocals, purParams

// The idea is to release the lock (if we have it). Monitorexit does this job.
BYTECODE(ALOAD_0),                                                            // 0:    aload_0
BYTECODE(MONITOREXIT),                                                        // 1:    monitorexit

// Then (to be fair) force a thread switch
BYTECODE(GETSTATIC_L),      SADR(java_lang_Thread_currentThread),             // 2:    getstatic		java.lang.Thread.currentThread Ljava/lang/Thread; (9)
BYTECODE(BIPUSH),           B(WAITING),                                       // 5:    bipush		6 (==WAITING)
INVOKESPECIAL(java_lang_Thread_setStateAndSwitch_II),                           // 7:    invokespecial	java.lang.Thread.setStateAndSwitch (I)I (24)

// When we are back try to get a lock on this object
BYTECODE(POP),                                                                // 10:   pop
BYTECODE(ALOAD_0),                                                            // 11:   aload_0
BYTECODE(MONITORENTER),                                                       // 12:   monitorenter
BYTECODE(RETURN),                                                             // 13:   return
};
#endif


/*
public class java.lang.VM extends java.lang.Object
filename		...\bootstrap\bin/java/lang/VM.class
compiled from		VM.java
compiler version	50.0
access flags		33
constant pool		18 entries
ACC_SUPER flag		true

Attribute(s):
	SourceFile(VM.java)

2 methods:
	public void <init>()
	public static native String getFirmwareRevision()

*/

/**
 * just because some leJOS benchmark needs it.
*/
void native_java_lang_VM_getFirmwareRevision_Ljava_lang_String() {
	pushTop();
}
