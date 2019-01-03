#ifndef NATIVE_THREAD_H
#define NATIVE_THREAD_H

#define	IDLE  0
#define	YIELD  2
//#define	INIT  4
#define	WAITING  6
#define	STOPPED  8


typedef struct ListLA {
	struct ListLA* next;
	jobject lock;
} ListLA;

typedef struct native_java_lang_Thread {
	jobject target;	//Ljava/lang/Runnable;
	struct native_java_lang_Thread* next;	//Ljava/lang/Thread;
	jint state;	//I
	unsigned char * programcounter;	//Ljava/lang/Object;
	jstack stackpointer;	//I
	jstack stackframe;	//I
	stackBlock4Debug_t* stackblock;	//Ljava/lang/Object;
	julong waitUntil;	// unsigned! J
	ListLA* locks;
	jboolean interrupt;
	jobject waitingOn;
} native_java_lang_Thread;
#define SIZEOF_java_lang_Thread sizeof(java_lang_Thread)

#define java_lang_Thread native_java_lang_Thread

typedef struct {
	uint8_t max_stack; int8_t purLocals; uint8_t purParams;

	OP_bc op0;                                                             // 0:    aload_0
	OP_bc op1;                                                             // 1:    monitorexit
	OP_bc op2; OPadr a2;                                                   // 2:    getstatic		java.lang.Thread.currentThread Ljava/lang/Thread; (9)
	OP_bc op5; OP__8 b5;                                                   // 5:    bipush		6
	INVOKESPECIALt(op7, a3);                                                 // 7:    invokespecial	java.lang.Thread.setStateAndSwitch (I)I (24)
	OP_bc op10;                                                            // 10:   pop
	OP_bc op11;                                                            // 11:   aload_0
	OP_bc op12;                                                            // 12:   monitorenter
	OP_bc op13;                                                            // 13:   return
}            java_lang_Thread_haikuReleaseLock_Ljava_lang_Object_V_t;
extern const java_lang_Thread_haikuReleaseLock_Ljava_lang_Object_V_t java_lang_Thread_haikuReleaseLock_Ljava_lang_Object_V;
#define native_java_lang_Thread_haikuReleaseLock_Ljava_lang_Object_V

extern void native_java_lang_Thread_fork_I();

extern const NativCode java_lang_Thread_setStateAndSwitch_II;
extern void            native_java_lang_Thread_setStateAndSwitch_II(void);

#endif
