#ifndef _HAIKUJ2C_H_
#define _HAIKUJ2C_H_

#include <stddef.h>
#include <stdio.h>
#include "platforms/interface.h"

// AVRs lib supports only float 32
#define DBL64_CNSTR_MIN(x) (x==0?x: (-DBL_MIN<x && x<DBL_MIN?(x>0?DBL_MIN:-DBL_MIN):x))
#define DBL64_CNSTR(x) (x>DBL_MAX?DBL_MAX: (x<-DBL_MAX?-DBL_MAX:DBL64_CNSTR_MIN(x)))

// Random.nextDouble uses factor 1.1102230246251565E-16 which as to be mapped
#define DBL32_Const(x) (\
		x == 1.1102230246251565E-16?1.0/2.013254016E9 :\
		x\
		)
#define DBL32_CNSTR_MIN(x) (x==0?DBL32_Const(x): (-FLT_MIN<x && x<FLT_MIN?(x>0?FLT_MIN:-FLT_MIN):DBL32_Const(x)))
#define DBL32_CNSTR(x) (x>FLT_MAX?FLT_MAX: (x<-FLT_MAX?-FLT_MAX:DBL32_CNSTR_MIN(x)))
#define DBL16_CNSTR_MIN(x) (x==0?x: (-FLT_MIN<x && x<FLT_MIN?(x>0?FLT_MIN:-FLT_MIN):x))
#define DBL16_CNSTR(x) (x>FLT_MAX?FLT_MAX: (x<-FLT_MAX?-FLT_MAX:DBL16_CNSTR_MIN(x)))

#define FLT32_CNSTR(x) (x)
#define FLT16_CNSTR(x) (x)

#define LONG64_CNSTR(x) (x)
// Math.sqrt uses magic number 0x5fe6ec85e7de30daL which as to be mapped
#define LONG32_CNSTR(x) (\
		x == 0x5fe6ec85e7de30daL?0x5f3759dfL:\
		x == 0x7fffffffffffffffL?0x7fffffffL:\
		x\
		)
#define LONG16_CNSTR(x) (\
		x == 0x5fe6ec85e7de30daL?0x5f37L:\
		x == 0x7fffffffffffffffL?0x7fffL:\
		x\
		)

#define INT32_CNSTR(x) (x)
#define INT16_CNSTR(x) (x == 0x7fffffff?0x7fff:x)

#define HAIKU_32_64 4
#define HAIKU_16_64 3
#define HAIKU_32_32 2
#define HAIKU_16_32 1
#define HAIKU_16_16 0

// Experimental beware ldc_jstring_t.length in ldc()
#define HAIKU_8_16  5
#define HAIKU_8_8   6

 // usefull for mc with small mem sizes
#define HAIKU_CHAR_8   1

// default JAVA size
#define HAIKU_CHAR_16   2

//HAIKU_GC
#define HAIKU_NoGC 1
#define HAIKU_StopTheWorldGC 2
#define HAIKU_ConservativGC  2
#define HAIKU_IncrementalGC 3
#define HAIKU_CompactingGC 4

//HAIKU_AOTVariant
#define HAIKU_AOTBytecodeAsJump 1
#define HAIKU_AOTBytecodeAsSwitch 2
#define HAIKU_AOTThreadedAsCall 3

// Exception Flags
// enabled
#define NullPointerException		2

// disable|enabled
#define ArrayStoreException			4

// disable|enabled
#define IndexOutOfBoundsException	8

// enabled
#define NoSuchMethodError			16

// disable|enable
#define StackOverflowError			32

// enabled
#define OutOfMemoryError			64

// enabled
#define ClassCastException			128

// disable|enable
#define ArrayIndexOutOfBoundsException	256

// enabled
#define InternalError			512

#define ArithmeticException			1024


#include "utility/haikuDefs.h"

//#define HAIKU_Mode HAIKU_32_64
//#define HAIKU_Char HAIKU_CHAR_16

//#define HAIKU_Mode HAIKU_16_32
//#define HAIKU_Char HAIKU_CHAR_8
//#define HAIKU_InitialMainThreadStackSize 142 //95 96 (25 for Threads) (142/[1300-1350] for fib(30)) (180/[?-1500] for fib(35))

#ifndef DEBUG
  #define DEBUG 0
#endif
#if DEBUG
#define Debug(x) x
#else
#define Debug(x)
#endif

#ifndef TRACEING
  #define TRACEING 0
#endif
#define TRACEING 0
#if TRACEING
#define Traceout(...) jprintf(__VA_ARGS__)
#else
#define Traceout(...)
#endif

/////////////////////////////////
// macros for express bytecode parameters
////
// int32 parameter
#define INT32(x) (x)
// int16 parameter
#define INT16(x) (x)
#define HB(x) (((x)>>8)&0xff)
#define LB(x) ((x)&0xff)
// byte parameter
#define B(x) (x)
// access of object fields
#define FIDX(x, f) INT16(offsetof(x, f))
// access of static fields
#define SADR(x) (OPadr)(&heapmem.allStatics.statics.x)
#define ADR(x) (OPadr)(&x)
// access of class
#define CADR(x) (&x)
// access of native C variables (annotation: @NativeCVariable)
#define NADR(x) (OPadr)(&x)
// access of native C struct variables (annotation: @NativeCVariable)
#define NstructADR(x) (OPadr)(((char*)(&x)))
// access of native C array variables (annotation: @NativeCVariable)
#define NarrayADR(x) (OPadr)(((char*)(&x))-sizeof(jint))
// jump targets
#define TARGET(target) &JMETHOD.op##target
/////////////////////////////////


#if NO_MICRO

	// doesn't make sense like this
	//#if HAIKU_Target==WIN32
	#if 1
		// keep value of HAIKU_MemorySize
	#else
		// must be a test on alien ;-) hardware
		#if _DEBUG
			#undef HAIKU_MemorySize
			#define HAIKU_MemorySize 1850 // 1192.1193/593 possible for fib(20) only if HAIKU_InitialMainThreadStackSize=96
		#else
			//#define HAIKU_MemorySize 0x4000
		#endif
	#endif
	#define __attribute__(x)

#else
	//#define HAIKU_MemorySize 1530 // 365 -> fib(20) alive / 360 -> dead

	//ASURO
	//#define HAIKU_MemorySize 700
#endif

#include "ROMspace.h"

#define DEBUG_DISPATCHER 0

#define	OP_GETSTATIC_NativeCVariable8 BYTECODE(GETSTATIC_B)
#define	OP_PUTSTATIC_NativeCVariable8 BYTECODE(PUTSTATIC_B)

#define	OP_GETSTATIC_NativeCVariable16 BYTECODE(GETSTATIC_S)
#define	OP_PUTSTATIC_NativeCVariable16 BYTECODE(PUTSTATIC_S)

#if HAIKU_Mode==HAIKU_32_64
#define FLOAT2TF(x) x
#define TF2FLOAT(x) x
#define DOUBLE2TD(x) x
#define TD2DOUBLE(x) x

#if NO_MICRO
#define DBL_CNSTR(x) DBL64_CNSTR(x)
#define FLT_CNSTR(x) FLT32_CNSTR(x)
#define LONG_CNSTR(x) LONG64_CNSTR(x)
#define INT_CNSTR(x) INT32_CNSTR(x)
#else
#define DBL_CNSTR(x) DBL64_CNSTR(x)
#define FLT_CNSTR(x) FLT32_CNSTR(x)
#define LONG_CNSTR(x) LONG32_CNSTR(x)
#define INT_CNSTR(x) INT32_CNSTR(x)
#endif

#define	OP_GETSTATIC_NativeCVariable32 BYTECODE(GETSTATIC_I)
#define	OP_PUTSTATIC_NativeCVariable32 BYTECODE(PUTSTATIC_I)

#define	OP_GETSTATIC_NativeCVariable64 BYTECODE(GETSTATIC_J)
#define	OP_PUTSTATIC_NativeCVariable64 BYTECODE(PUTSTATIC_J)

	typedef int32_t jint;
	typedef uint32_t juint; // see ushr

	typedef float jfloat;
	typedef double jdouble;

	typedef float jfloatldc;
	typedef double jdoubleldc;
	typedef uint32_t jfloatraw;
	typedef uint64_t jdoubleraw;

	typedef int64_t jlong;
	typedef uint64_t julong; // see ushr
#endif

#if HAIKU_Mode==HAIKU_32_32
#define FLOAT2TF(x) x
#define TF2FLOAT(x) x
#define DOUBLE2TD(x) x
#define TD2DOUBLE(x) x

#define DBL_CNSTR(x) DBL32_CNSTR(x)
#define FLT_CNSTR(x) FLT32_CNSTR(x)
#define LONG_CNSTR(x) LONG32_CNSTR(x)
#define INT_CNSTR(x) INT32_CNSTR(x)

#define	OP_GETSTATIC_NativeCVariable32 BYTECODE(GETSTATIC_I)
#define	OP_PUTSTATIC_NativeCVariable32 BYTECODE(PUTSTATIC_I)

#define	OP_GETSTATIC_NativeCVariable64
#define	OP_PUTSTATIC_NativeCVariable64

	typedef int32_t jint;
	typedef uint32_t juint; // see ushr

	typedef float jfloat;
	typedef float jdouble;

	typedef float jfloatldc;
	typedef float jdoubleldc;
	typedef uint32_t jfloatraw;
	typedef uint32_t jdoubleraw;

	typedef int32_t jlong;
	typedef uint32_t julong; // see ushr
#endif

#if HAIKU_Mode==HAIKU_16_32
#define FLOAT2TF(x) floatToInt16(x)
#define TF2FLOAT(x) int16ToFloat(x)
#define DOUBLE2TD(x) x
#define TD2DOUBLE(x) x

#define DBL_CNSTR(x) DBL32_CNSTR(x)
#define FLT_CNSTR(x) FLT16_CNSTR(x)
#define LONG_CNSTR(x) LONG32_CNSTR(x)
#define INT_CNSTR(x) INT16_CNSTR(x)

#define	OP_GETSTATIC_NativeCVariable32 BYTECODE(GETSTATIC_J)
#define	OP_PUTSTATIC_NativeCVariable32 BYTECODE(PUTSTATIC_J)

#define	OP_GETSTATIC_NativeCVariable64
#define	OP_PUTSTATIC_NativeCVariable64

	typedef int16_t jint;
	typedef uint16_t juint; // see ushr

	typedef int16_t jfloat;
	typedef float jdouble;

	typedef float jfloatldc;
	typedef float jdoubleldc;
	typedef uint32_t jfloatraw;
	typedef uint32_t jdoubleraw;

	typedef int32_t jlong;
	typedef uint32_t julong; // see ushr
#endif

#if HAIKU_Mode==HAIKU_16_16
#define FLOAT2TF(x) floatToInt16(x)
#define TF2FLOAT(x) int16ToFloat(x)
#define DOUBLE2TD(x) floatToInt16(x)
#define TD2DOUBLE(x) int16ToFloat(x)

#define DBL_CNSTR(x) DBL16_CNSTR(x)
#define FLT_CNSTR(x) FLT16_CNSTR(x)
#define LONG_CNSTR(x) LONG16_CNSTR(x)
#define INT_CNSTR(x) INT16_CNSTR(x)

#define	OP_GETSTATIC_NativeCVariable32
#define	OP_PUTSTATIC_NativeCVariable32

#define	OP_GETSTATIC_NativeCVariable64
#define	OP_PUTSTATIC_NativeCVariable64

	typedef int16_t jint;
	typedef uint16_t juint; // see ushr

	typedef int16_t jfloat;
	typedef int16_t jdouble;

	typedef float jfloatldc;
	typedef float jdoubleldc;
	typedef uint32_t jfloatraw;
	typedef uint32_t jdoubleraw;

	typedef int16_t jlong;
	typedef uint16_t julong; // see ushr
#endif

#if  HAIKU_MemorySize < 0x080
	typedef uint8_t jheapsize;
#elif HAIKU_MemorySize < 0x08000
	typedef uint16_t jheapsize;
#else
	typedef uint32_t jheapsize;
#endif

typedef uint8_t jboolean;
typedef int8_t jbyte;
typedef int16_t jshort;
typedef uint16_t jchar;

//only used for jchar8or16Array
//to be able to compress char Arrays elements to 1 byte
#if HAIKU_Char==HAIKU_CHAR_16
typedef uint16_t jchar8or16;
#else
typedef uint8_t jchar8or16;
#endif



typedef struct {
	uint8_t max_stack; uint8_t purLocals; uint8_t purParams;
	char code[]; // With code[0x7fff] error: type '<anonymous struct>' is too large. Why?
} ByteCode;

typedef	void (* BytecodeFoo)() ;

typedef struct {
	int8_t tag;
	BytecodeFoo nativeFoo;
} NativCode;

typedef union {
	struct {uint8_t max_stack; uint8_t locals; uint8_t params;};
	struct {uint8_t tag; BytecodeFoo nativeFoo;};
} TaggedCode;

typedef struct {
	const uint8_t msgIdx;
	const ByteCode * meth;
} msg2meth_t;

typedef struct class_t {
	const struct class_t * superClass;  // pointer to superclass (extends superClass)
	const uint8_t mem;                  // size of object in bytes
	const uint8_t size;                 // length of msg2meth[]
	const msg2meth_t msg2meth[];        // msgIdx -> meth for every virtual method
} class_t;

typedef struct aclass_t {
	const struct class_t * superClass;  // pointer to superclass (extends superClass)
	const uint8_t mem;                  // size of object in bytes
} aclass_t;

#define COMPACTINGWITHTAGS(x) (x)

/* But problems with BEGCODE(LDC_C)
 * Better idea:
typedef struct class_t {
	typedef struct heap_t {
		jheapsize xallocsize; // size includes this header (store 0 with care because it means end of jobject queue)
		jclass xclazz; // NULL means FREE space
	} heap_t;
	const struct class_t * superClass;  // pointer to superclass (extends superClass)
	const jint mem;                     // size of object in bytes
	const uint8_t size;                 // length of msg2meth[]
	const msg2meth_t msg2meth[];        // msgIdx -> meth for every virtual method
} class_t;
 *
 */
typedef class_t * jclass;

#define FREE ((jclass)0)
//#define FREE ((jclass)-1) //!= 0 not needed because now arrays are of class Object (before that NULL which leaded to ((jclass)-1)).

typedef struct heap_t {
	jheapsize xallocsize; // size includes this header (store 0 with care because it means end of jobject queue)
	jclass xclazz; // NULL means FREE space
} heap_t;

typedef heap_t* jheap;
typedef void* jobject;

#define OBJ2HEAP(obj)      ((jheap)(((char*)obj)-sizeof(heap_t)))
#define HEAP2OBJ(heap)     ((jobject)(((char*)heap)+sizeof(heap_t)))

#define getClass(obj)      (OBJ2HEAP(obj)->xclazz)
#define getAllocsize(obj)  (OBJ2HEAP(obj)->xallocsize&(((jheapsize)(-1)>>1)))
#define getMarkFlag(obj) (((OBJ2HEAP(obj)->xallocsize) & (~((jheapsize)(-1)>>1)))!=0)

#define setClass(obj, clazz)     OBJ2HEAP(obj)->xclazz = clazz
#define setAllocsize(obj, size)  OBJ2HEAP(obj)->xallocsize = size
#define setMarkFlag(obj)         OBJ2HEAP(obj)->xallocsize |= (~((jheapsize)(-1)>>1))

//WIN32: I tried this union. I expected sizeof(array_t)=8 but sadly it is 12 (independent of alignment settings.)
//typedef struct {
//	jint length;
//	union {
//		 jbyte jbyteArray[];
//		 jint jintArray[];
//		 jobject jobjectArray[];
//	};
//} array_t;

// So I decided to stay simple like this
typedef struct {
	jint length;
	jbyte array[0];
} array_t;

#define ARRAYTYPE(type, size) struct {	jint length;	type array[size]; }

#define ARRAYOFMEM(type) ARRAYTYPE(type, 10)  type##ArrayMem

typedef ARRAYOFMEM(jchar8or16);
typedef ARRAYOFMEM(jboolean);
typedef ARRAYOFMEM(jbyte);
typedef ARRAYOFMEM(jint);
typedef ARRAYOFMEM(jobject);
typedef ARRAYOFMEM(jlong);
typedef ARRAYOFMEM(jshort);
typedef ARRAYOFMEM(jfloat);
typedef ARRAYOFMEM(jdouble);

// use the ARRAYOF types only for type casts
#define ARRAYOF(type) type##ArrayMem* type##Array

typedef ARRAYOF(jchar8or16);
typedef ARRAYOF(jboolean);
typedef ARRAYOF(jbyte);
typedef ARRAYOF(jint);
typedef ARRAYOF(jobject);
typedef ARRAYOF(jlong);
typedef ARRAYOF(jshort);
typedef ARRAYOF(jfloat);
typedef ARRAYOF(jdouble);

typedef struct {
	jchar8or16Array chars;
} HVM_String;

typedef struct {
	jchar8or16Array chars; //[C
	heap_t heapControl;    // start of heap part of char array
	jint length;           // start of char array
	jchar8or16 array[];	   // data of char array
} ldc_String;

typedef void * jvoid;
typedef HVM_String* jstring;

typedef ARRAYOFMEM(jstring);
typedef ARRAYOF(jstring);

//ZCSIJDFLjava/lang/String;
typedef union {
	jint i;
	jfloat f;
	jobject a;

	//for smooth debugging:
	void * v;
	char * ch;
	array_t * ay;
	HVM_String * str;
	// float: needed for conversion in LDC because of PROGMEM
	jint fl;
} stack_t;

typedef stack_t* jstack;

typedef struct {
	jint length;
	stack_t stackbase[];
} stackBlock_t;

typedef struct {
	jint length;
	stack_t stackbase[50];
} stackBlock4Debug_t;

#define STACKIDX(frame, p) ((int)((unsigned int)(p)-(unsigned int)&frame->stackbase[0])/sizeof(stack_t))

typedef union {
	struct {stack_t s0; stack_t s1;};
	jdouble d;
	julong dl; // double: used with LDC because of PROGMEM
	jlong j; // long
} top_t, *JNIEnv;


typedef union  { jfloatldc f; jfloatraw fl;} ldc_jfloat_t;
typedef struct { jint i; } ldc_jint_t;
typedef struct { jlong j; } ldc_jlong_t;
typedef union  { jdoubleldc d; jdoubleraw dl;} ldc_jdouble_t;
typedef struct { jint length; jchar8or16 array[]; } ldc_jstring_t;


#define currentThread ((native_java_lang_Thread*)heapmem.allStatics.statics.java_lang_Thread_currentThread)

/**
according to this https://en.wikipedia.org/wiki/Threaded_code

Separating the data and return stacks in a machine eliminates a great deal of stack
management code, substantially reducing the size of the threaded code. The dual-stack
principle was originated three times independently: for Burroughs large systems,
Forth and PostScript, and is used in some Java virtual machines.

Three registers are often present in a threaded virtual machine. Another one exists
for passing data between subroutines ('words'). These are:

    ip or i (instruction pointer) of the virtual machine (not to be confused with the
				program counter of the underlying hardware implementing the VM)
    w (work pointer)
    rp or r (return stack pointer)
    sp or s (parameter stack pointer for passing parameters between words)

Often, threaded virtual machines such as implementations of Forth have a simple
virtual machine at heart, consisting of three primitives. Those are:

    nest, also called docol
    unnest, or semi_s (;s)
    next

In an indirect-threaded virtual machine, the one given here, the operations are:

next:   *ip++ ->   w    ;  jmp **w++
nest:    ip   -> *rp++  ;  w -> ip  ;  next
unnest: *--rp ->   ip   ;  next

This is perhaps the simplest and fastest interpreter or virtual machine.

slower and bigger:
#define pc currentThread->programcounter
#define dataSp currentThread->stackpointer
#define lsp currentThread->stackframe

*/

#define pc tvm.ip
#define dataSp tvm.sp
#define lsp tvm.rp

typedef struct {
	unsigned char * ip;
	jstack sp;
	jstack rp;
} TVM;

extern TVM tvm;

extern uint8_t bc;

extern top_t top;

#ifdef __cplusplus
extern "C"
{
#endif
extern stackBlock4Debug_t* currentStackblock;
extern stackBlock_t* newStack(int size);

extern const class_t java_lang_Object__class PROGMEM;
extern const class_t YLstack__class PROGMEM;

extern top_t* getLocal(int i);
extern top_t   * popp2();
extern jstack push();

extern void pushTop0();
extern void popTop0();
extern void pushTop();
extern void popTop();
extern jstack get(int idx);

extern void  setLocal(int i, jstack val);
extern void isetLocal(int i, int    val);
extern void vsetLocal(int i, void * val);
extern void fsetLocal(int i, float  val);

#if _DEBUG || NO_MICRO
extern void showstack(char *  msg);
#else
#define showstack(msg)
#endif

extern int8_t t;

extern void invoke(const ByteCode * bytecode);

extern jstack pop();
extern jbyte bpop();
extern jobject apop();
extern jint ipop();
extern jfloat fpop();
#define cppop() (char *) apop()

extern void ipush(int i);
extern void fpush(float f);
extern void jpush(long l);
extern void vpush(void * v);
extern void ldc();
extern void compare(jint v);

extern array_t * newarray(jint length, jclass baseclass);
extern void multiArray(jobjectArray* GCroot, int d, jclass baseclass);
extern void * getMethod(jobject obj, int msgIdx);
extern jobject newInstance(jclass clazz);
extern void* java_lang_Object_nomsg__V(void);
extern void * java_lang_Thread_yield__V();
extern void unimplemented();
extern void unused();
extern void interpretNextByteCodeChunk();
extern void setupHaikuVM();
extern void interpret();
extern void ldc_string();

// #undef  panic -> needed for ESP8266
#undef panic
extern void panic(jint exception, jint arg);
extern char * getField();

#include "gcVariants/interface.h"

extern void areturn();
extern void athrow(jobject throwable);
extern jint instanceOf(jobject obj, jclass target);
extern void monitorenter();
extern void monitorexit();
extern HVM_String * newString(char * string);
#ifdef __cplusplus
}
#endif


extern int16_t    GETCODEWORD2();
extern int32_t    GETCODEDWORD2();
extern int8_t    GETCODEBYTE2();
extern void *    GETCODEADR2();

#if NO_MICRO
extern uint8_t mem[];

#define _SFR_8(idx) (mem[idx])
#define _SFR_16(idx) (mem[idx])

#define UDR0_IDX	0x00
#define ADC_IDX	0x01
#define ADCSRA_IDX	0x01

#define UBRR0H_IDX	0x07
#define UBRR0L_IDX	0x03

#define TCCR0A_IDX	0x04
#define TCCR0B_IDX	0x02

#define UCSR0A_IDX	0x06
#define UCSR0B_IDX	0x05
#define TIMSK0_IDX	0x08

#define PORTB_IDX 0x09 // haiku.avr.AVRConstants.PORTB gets=2 puts=2 native=4
#define DDRB_IDX 0x0a // haiku.avr.AVRConstants.DDRB gets=0 puts=1 native=1



#define UDR0	_SFR_8(UDR0_IDX)
#define ADC		_SFR_8(ADC_IDX)
#define ADCSRA	_SFR_8(ADCSRA_IDX)

#define UBRR0H	_SFR_8(UBRR0H_IDX)
#define UBRR0L	_SFR_8(UBRR0L_IDX)

#define TCCR0A	_SFR_8(TCCR0A_IDX)
#define TCCR0B	_SFR_8(TCCR0B_IDX)

#define UCSR0A	_SFR_8(UCSR0A_IDX)
#define UCSR0B	_SFR_8(UCSR0B_IDX)
#define TIMSK0	_SFR_8(TIMSK0_IDX)

#define PORTB	_SFR_8(PORTB_IDX) // haiku.avr.AVRConstants.PORTB gets=2 puts=2 native=4
#define DDRB	_SFR_8(DDRB_IDX) // haiku.avr.AVRConstants.DDRB gets=0 puts=1 native=1


#endif


extern void haikuHeapWalk(char * msg, int mode);

extern const class_t java_lang_String__class;
extern const class_t java_lang_StringBuilder__class;

extern const BytecodeFoo bytecodeFoo[];
extern const char * bytecodeDesc[];

extern float int16ToFloat(uint16_t i);
extern jfloat floatToInt16(float f);
#if HAIKU_GC == HAIKU_IncrementalGC
extern void incrementalGC(jheapsize incremental);
#define setMarkBit(obj)
#else
#define incrementalGC(x)
#define setMarkBit(obj)
#endif





#define JNULL NULL

/**
Mit der Idee:
methodBegin( storage, clazz, foo, sig, params, type, ms, ml, cl, pl)
{
	methodInit( storage, clazz, foo, sig, params, type, ms, ml, cl, pl);
	...
}
*/
#define methodBegin( storage, clazz, foo, sig, params, type, ms, ml, cl, pl) type clazz##_##foo##_##sig ()
#define methodInit(  storage, clazz, foo, sig, params, type, ms, ml, cl, pl) \
	int lsp=sp-(pl);\
	sp=lsp+ml

#define methodIdx(                   foo, sig) MSG_##foo##_##sig
#define methodMap(   storage, clazz, foo, sig, params, type, ms, ml, cl, pl) methodIdx(foo, sig), methodName(storage, clazz, foo, sig, type, ms, ml, cl, pl)
#define methodName(  storage, clazz, foo, sig, params, type, ms, ml, cl, pl) clazz##_##foo##_##sig
#define methodExtern(storage, clazz, foo, sig, params, type, ms, ml, cl, pl) extern type clazz##_##foo##_##sig();

#define access___
#define access__static_
#define access__public_static_

#if	HAIKU_PanicSupport
#define PanicAware(mem, x) if (mem) x
#else
#define PanicAware(mem, x) x
#endif

#if	HAIKU_PanicExceptions & ArrayIndexOutOfBoundsException
	extern int checkIndex(array_t * adr, jint index);
#else
	#define checkIndex(adr, index) 1
#endif

typedef uint8_t  OP__8;
typedef uint16_t OP_16;
typedef uint32_t OP_32;
typedef const void*    OPadr;
typedef const void*    OPcon;
typedef const void*    OPtrg;

#if HAIKU_AOTVariant == HAIKU_AOTBytecodeAsJump
typedef uint8_t  OP_bc;

#define BYTECODE(x) OP_##x
#define INVOKESTATIC(meth)         OP_INVOKESTATIC,  ADR(meth)
#define INVOKESPECIAL(meth)        OP_INVOKESPECIAL, ADR(meth)
#define INVOKESHORT(meth)          OP_INVOKESHORT_##meth
#define INVOKEVIRTUAL(jcode, meth) OP_INVOKEVIRTUAL, B(jcode), LB(meth)

#define INVOKESTATICt(op, a)       OP_bc op; OPadr a
#define INVOKESPECIALt(op, a)      OP_bc op; OPadr a
#define INVOKESHORTt(op, a)        OP_bc op
#define INVOKEVIRTUALt(op, a, b)   OP_bc op; OP__8 a; OP__8 b

#elif HAIKU_AOTVariant == HAIKU_AOTBytecodeAsSwitch
typedef uint8_t  OP_bc;

#define BYTECODE(x) OP_##x
#define INVOKESTATIC(meth)         OP_INVOKESTATIC,  ADR(meth)
#define INVOKESPECIAL(meth)        OP_INVOKESPECIAL, ADR(meth)
#define INVOKESHORT(meth)          OP_INVOKESHORT_##meth
#define INVOKEVIRTUAL(jcode, meth) OP_INVOKEVIRTUAL, B(jcode), LB(meth)

#define INVOKESTATICt(op, a)       OP_bc op; OPadr a
#define INVOKESPECIALt(op, a)      OP_bc op; OPadr a
#define INVOKESHORTt(op, a)        OP_bc op
#define INVOKEVIRTUALt(op, a, b)   OP_bc op; OP__8 a; OP__8 b

#elif HAIKU_AOTVariant == HAIKU_AOTThreadedAsJump
typedef const void*  OP_bc;

#define BYTECODE(x) &&OPF_##x
#define INVOKESTATIC(meth)         &&OPF_INVOKESTATIC,  ADR(meth)
#define INVOKESPECIAL(meth)        &&OPF_INVOKESPECIAL, ADR(meth)
#define INVOKESHORT(meth)          &&OPF_INVOKESTATIC,  ADR(meth)
#define INVOKEVIRTUAL(jcode, meth) &&OPF_INVOKEVIRTUAL, B(jcode), LB(meth)

#define INVOKESTATICt(op, a)       OP_bc op; OPadr a
#define INVOKESPECIALt(op, a)      OP_bc op; OPadr a
#define INVOKESHORTt(op, a)        OP_bc op; OPadr a
#define INVOKEVIRTUALt(op, a, b)   OP_bc op; OP__8 a; OP__8 b

#elif HAIKU_AOTVariant == HAIKU_AOTThreadedAsCall
typedef const void*  OP_bc;

#define BYTECODE(x) &OPF_##x
#define INVOKESTATIC(meth)         &OPF_INVOKESTATIC,  ADR(meth)
#define INVOKESPECIAL(meth)        &OPF_INVOKESPECIAL, ADR(meth)
#define INVOKESHORT(meth)          &OPF_INVOKESTATIC,  ADR(meth)
#define INVOKEVIRTUAL(jcode, meth) &OPF_INVOKEVIRTUAL, B(jcode), LB(meth)

#define INVOKESTATICt(op, a)       OP_bc op; OPadr a
#define INVOKESPECIALt(op, a)      OP_bc op; OPadr a
#define INVOKESHORTt(op, a)        OP_bc op; OPadr a
#define INVOKEVIRTUALt(op, a, b)   OP_bc op; OP__8 a; OP__8 b

#endif


typedef struct { OP_bc * from;  OP_bc * to;  OP_bc * handler;  jclass eclazz; } Exception_t;

extern Exception_t const	exceptionTable[];
extern ByteCode *  const 	functionTable[];



#include "haikuJavaNatives.h"


#endif //_HAIKUJ2C_H_
