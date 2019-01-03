//////////////////////////////////////////////////
// D:\arduino-1.0.5\libraries\.\HaikuVM\utility\haiku\avr\lib\arduino\HaikuMicroKernel4ArduinoIDE.c
//
/*
public class haiku.avr.lib.arduino.HaikuMicroKernel4ArduinoIDE extends haiku.vm.MicroKernel
filename		C:\DOKUME~1\genom\LOKALE~1\Temp\haikuvm/haiku/avr/lib/arduino/HaikuMicroKernel4ArduinoIDE.class
compiled from		HaikuMicroKernel4ArduinoIDE.java
compiler version	50.0
access flags		33
constant pool		42 entries
ACC_SUPER flag		true

Attribute(s):
	SourceFile(HaikuMicroKernel4ArduinoIDE.java)

2 methods:
	public void <init>()
	public static void main(String[] arg0)

*/


#include "haikuConfig.h"
#include "haikuJava.h"



/**
public static void main(String[] arg0)
Code(max_stack = 1, max_locals = 2, code_length = 23)
*/
#undef  JMETHOD
#define JMETHOD main_YLjava_lang_String
const           main_YLjava_lang_String_t JMETHOD PROGMEM ={
1+2 +2,    1,    1,    // max_stack, purLocals, purParams

OP_INVOKESHORT_haiku_vm_MicroKernel_clinitHaikuMagic_V,                // 0:    invokestatic	haiku.avr.lib.arduino.HaikuMicroKernel4ArduinoIDE.clinitHaikuMagic ()V (2)
OP_INVOKESHORT_processing_examples__01_Basics_Blink_setup_V,           // 6:    invokestatic	haiku.vm.HaikuMagic.setup ()V (4)
OP_INVOKESHORT_processing_examples__01_Basics_Blink_loop_V,            // 9:    invokestatic	haiku.vm.HaikuMagic.loop ()V (5)
OP_GOTO,             TARGET(9),                                        // 12:   goto		#9
OP_ASTORE_1,                                                           // 15:   astore_1
OP_INVOKESHORT_java_lang_Thread_currentThread_Ljava_lang_Thread,       // 16:   invokestatic	java.lang.Thread.currentThread ()Ljava/lang/Thread; (7)
OP_INVOKEVIRTUAL,    B(0), LB(MSG_stop___V),                           // 19:   invokevirtual	java.lang.Thread.stop ()V (8)
OP_RETURN,                                                             // 22:   return
};

const class_t haiku_avr_lib_arduino_HaikuMicroKernel4ArduinoIDE__class PROGMEM = {
	& haiku_vm_MicroKernel__class,
	SIZEOF_haiku_avr_lib_arduino_HaikuMicroKernel4ArduinoIDE,
	0,
    // {{}} VC 5
};


//////////////////////////////////////////////////
// D:\arduino-1.0.5\libraries\.\HaikuVM\utility\java\lang\Throwable.c
//
/*
public class java.lang.Throwable extends java.lang.Object
filename		C:\DOKUME~1\genom\LOKALE~1\Temp\haikuvm/java/lang/Throwable.class
compiled from		Throwable.java
compiler version	50.0
access flags		33
constant pool		54 entries
ACC_SUPER flag		true

Attribute(s):
	SourceFile(Throwable.java)

3 fields:
	private int[] _stackTrace
	private String _message
	Throwable _cause

10 methods:
	public void <init>()
	public void <init>(String arg1)
	public void <init>(String arg1, Throwable arg2)
	public void <init>(Throwable arg1)
	public Throwable initCause(Throwable arg1)
	public Throwable getCause()
	public String getLocalizedMessage()
	public String getMessage()
	public String toString()
	public Throwable fillInStackTrace()

*/



/**
public void <init>(String arg1)
Code(max_stack = 2, max_locals = 2, code_length = 15)
*/
#undef  JMETHOD
#define JMETHOD java_lang_Throwable__init__Ljava_lang_String_V
const           java_lang_Throwable__init__Ljava_lang_String_V_t JMETHOD PROGMEM ={
2+2 +2,    0,    2,    // max_stack, purLocals, purParams

OP_ALOAD_0,                                                            // 0:    aload_0
OP_INVOKESHORT_java_lang_Object__init__V,                              // 1:    invokespecial	java.lang.Object.<init> ()V (1)
OP_ALOAD_0,                                                            // 4:    aload_0
OP_INVOKEVIRTUAL,    B(0), LB(MSG_fillInStackTrace___Ljava_lang_Throwable), 
                                                                       // 5:    invokevirtual	java.lang.Throwable.fillInStackTrace ()Ljava/lang/Throwable; (2)
OP_POP,                                                                // 8:    pop
OP_ALOAD_0,                                                            // 9:    aload_0
OP_ALOAD_1,                                                            // 10:   aload_1
OP_PUTFIELD_L,       FIDX(java_lang_Throwable, _message),              // 11:   putfield		java.lang.Throwable._message Ljava/lang/String; (3)
OP_RETURN,                                                             // 14:   return
};

/**
public Throwable fillInStackTrace()
Code(max_stack = 1, max_locals = 1, code_length = 2)
*/
#undef  JMETHOD
#define JMETHOD java_lang_Throwable_fillInStackTrace_Ljava_lang_Throwable
const           java_lang_Throwable_fillInStackTrace_Ljava_lang_Throwable_t JMETHOD PROGMEM ={
1+1 +2,    0,    1,    // max_stack, purLocals, purParams

OP_ALOAD_0,                                                            // 0:    aload_0
OP_ARETURN,                                                            // 1:    areturn
};

const class_t java_lang_Throwable__class PROGMEM = {
	& java_lang_Object__class,
	SIZEOF_java_lang_Throwable,
	1,
    {
		{MSG_fillInStackTrace___Ljava_lang_Throwable, (ByteCode *)(&java_lang_Throwable_fillInStackTrace_Ljava_lang_Throwable)},
	}
};


//////////////////////////////////////////////////
// D:\arduino-1.0.5\libraries\.\HaikuVM\utility\java\lang\Object.c
//
/*
public class java.lang.Object extends java.lang.Object
filename		C:\DOKUME~1\genom\LOKALE~1\Temp\haikuvm/java/lang/Object.class
compiled from		Object.java
compiler version	50.0
access flags		33
constant pool		78 entries
ACC_SUPER flag		true

Attribute(s):
	SourceFile(Object.java)

12 methods:
	public void <init>()
	public String toString()
	public boolean equals(Object arg1)
	public final void wait(long arg1)
		throws java.lang.InterruptedException
	public final void wait(long arg1, int arg3)
		throws java.lang.InterruptedException
	public final void wait()
		throws java.lang.InterruptedException
	protected native Object clone()
		throws java.lang.CloneNotSupportedException
	protected void finalize()
	public int hashCode()
	public Class getClass() [Signature(()Ljava/lang/Class<*>;)]
	public void notifyAll()
	public void notify()

*/



/**
public void <init>()
Code(max_stack = 0, max_locals = 1, code_length = 1)
*/
#undef  JMETHOD
#define JMETHOD java_lang_Object__init__V
const           java_lang_Object__init__V_t JMETHOD PROGMEM ={
0+1 +2,    0,    1,    // max_stack, purLocals, purParams

OP_RETURN,                                                             // 0:    return
};

const class_t java_lang_Object__class PROGMEM = {
	NULL,
	SIZEOF_java_lang_Object,
	0,
    // {{}} VC 5
};


//////////////////////////////////////////////////
// D:\arduino-1.0.5\libraries\.\HaikuVM\utility\java\lang\System.c
//
/*
public class java.lang.System extends java.lang.Object
filename		C:\DOKUME~1\genom\LOKALE~1\Temp\haikuvm/java/lang/System.class
compiled from		System.java
compiler version	50.0
access flags		33
constant pool		41 entries
ACC_SUPER flag		true

Attribute(s):
	SourceFile(System.java)

3 fields:
	public static java.io.PrintStream out
	public static java.io.PrintStream err
	public static java.io.InputStream in

10 methods:
	public void <init>()
	public static native long currentTimeMillis()
	public static String getProperty(String arg0)
	public static String getProperty(String arg0, String arg1)
	public static int identityHashCode(Object arg0)
	private static native int getDataAddress(Object arg0)
	public static native void gc()
	private static java.io.PrintStream nullPrintStream()
	public static native void arraycopy(Object arg0, int arg1, Object arg2, int arg3, int arg4)
	public static void loadLibrary(String arg0)

*/



/**
public static native long currentTimeMillis()
*/
#ifndef native_java_lang_System_currentTimeMillis_J
const NativCode java_lang_System_currentTimeMillis_J PROGMEM ={0xff, &native_java_lang_System_currentTimeMillis_J};
#endif

const class_t java_lang_System__class PROGMEM = {
	& java_lang_Object__class,
	SIZEOF_java_lang_System,
	0,
    // {{}} VC 5
};


//////////////////////////////////////////////////
// D:\arduino-1.0.5\libraries\.\HaikuVM\utility\java\lang\Thread.c
//
/*
public class java.lang.Thread extends java.lang.Object
implements		java.lang.Runnable
filename		Thread.java
compiled from		Thread.java
compiler version	50.0
access flags		33
constant pool		135 entries
ACC_SUPER flag		true

Attribute(s):
	SourceFile(Thread.java)

19 fields:
	private static final int IDLE = 0
	private static final int YIELD = 2
	private static final int WAITING = 6
	private static final int STOPPED = 8
	public static final int MAX_PRIORITY = 0
	public static final int NORM_PRIORITY = 0
	public static final int MIN_PRIORITY = 0
	private static volatile Thread currentThread
	private volatile Runnable target
	private volatile Thread next
	private volatile int state
	private volatile Object programcounter
	private volatile Object stackpointer
	private volatile Object stackframe
	private volatile Object stack
	private volatile long waitUntil
	private volatile Object locks
	private volatile boolean interrupt
	private volatile Object waitingOn

30 methods:
	public static void foolingProGuard()
	public void <init>()
	public void <init>(Runnable arg1)
	public static void sleep(long arg0)
		throws java.lang.InterruptedException
	public static void nap(long arg0)
	public void start()
	private native int fork()
	public void run()
	public void run$()
	public final void stop()
	public static Thread currentThread()
	public static void yield()
	private native int setStateAndSwitch(int arg1)
	public void setDaemon(boolean arg1)
	public void setPriority(int arg1)
	public void <init>(String arg1)
	public void <init>(Runnable arg1, String arg2)
	public final int getPriority()
	public String getName()
	public void setName(String arg1)
	public void interrupt()
	public static boolean interrupted()
	public final boolean isInterrupted()
	public final boolean isDaemon()
	public final void join()
		throws java.lang.InterruptedException
	public final void join(long arg1)
		throws java.lang.InterruptedException
	static void notifyAll(Object arg0)
	static void notify(Object arg0)
	private static native void haikuReleaseLock(Object arg0)
	static void haikuWait(Object arg0, long arg1)
		throws java.lang.InterruptedException

*/



/**
public static void nap(long arg0)
Code(max_stack = 5, max_locals = 2, code_length = 21)
*/
#undef  JMETHOD
#define JMETHOD java_lang_Thread_nap_JV
const           java_lang_Thread_nap_JV_t JMETHOD PROGMEM ={
5+2 +2,    0,    2,    // max_stack, purLocals, purParams

OP_GETSTATIC_L,      SADR(java_lang_Thread_currentThread),             // 0:    getstatic		java.lang.Thread.currentThread Ljava/lang/Thread; (3)
OP_INVOKESHORT_java_lang_System_currentTimeMillis_J,                   // 3:    invokestatic	java.lang.System.currentTimeMillis ()J (6)
OP_LLOAD_0,                                                            // 6:    lload_0
OP_LADD,                                                               // 7:    ladd
OP_PUTFIELD_J,       FIDX(java_lang_Thread, waitUntil),                // 8:    putfield		java.lang.Thread.waitUntil J (7)
OP_GETSTATIC_L,      SADR(java_lang_Thread_currentThread),             // 11:   getstatic		java.lang.Thread.currentThread Ljava/lang/Thread; (3)
OP_BIPUSH,           B(6),                                             // 14:   bipush		6
OP_INVOKESHORT_java_lang_Thread_setStateAndSwitch_II,                  // 16:   invokespecial	java.lang.Thread.setStateAndSwitch (I)I (8)
OP_POP,                                                                // 19:   pop
OP_RETURN,                                                             // 20:   return
};

/**
public final void stop()
Code(max_stack = 2, max_locals = 2, code_length = 46)
*/
#undef  JMETHOD
#define JMETHOD java_lang_Thread_stop_V
const           java_lang_Thread_stop_V_t JMETHOD PROGMEM ={
2+2 +2,    1,    1,    // max_stack, purLocals, purParams

OP_ALOAD_0,                                                            // 0:    aload_0
OP_GETFIELD_I,       FIDX(java_lang_Thread, state),                    // 1:    getfield		java.lang.Thread.state I (19)
OP_BIPUSH,           B(8),                                             // 4:    bipush		8
OP_IF_ICMPEQ,        TARGET(45),                                       // 6:    if_icmpeq		#45
OP_ALOAD_0,                                                            // 9:    aload_0
OP_ASTORE_1,                                                           // 10:   astore_1
OP_ALOAD_1,                                                            // 11:   aload_1
OP_GETFIELD_L,       FIDX(java_lang_Thread, next),                     // 12:   getfield		java.lang.Thread.next Ljava/lang/Thread; (12)
OP_ALOAD_0,                                                            // 15:   aload_0
OP_IF_ACMPEQ,        TARGET(27),                                       // 16:   if_acmpeq		#27
OP_ALOAD_1,                                                            // 19:   aload_1
OP_GETFIELD_L,       FIDX(java_lang_Thread, next),                     // 20:   getfield		java.lang.Thread.next Ljava/lang/Thread; (12)
OP_ASTORE_1,                                                           // 23:   astore_1
OP_GOTO,             TARGET(11),                                       // 24:   goto		#11
OP_ALOAD_1,                                                            // 27:   aload_1
OP_ALOAD_1,                                                            // 28:   aload_1
OP_GETFIELD_L,       FIDX(java_lang_Thread, next),                     // 29:   getfield		java.lang.Thread.next Ljava/lang/Thread; (12)
OP_GETFIELD_L,       FIDX(java_lang_Thread, next),                     // 32:   getfield		java.lang.Thread.next Ljava/lang/Thread; (12)
OP_PUTFIELD_L,       FIDX(java_lang_Thread, next),                     // 35:   putfield		java.lang.Thread.next Ljava/lang/Thread; (12)
OP_ALOAD_0,                                                            // 38:   aload_0
OP_BIPUSH,           B(8),                                             // 39:   bipush		8
OP_INVOKESHORT_java_lang_Thread_setStateAndSwitch_II,                  // 41:   invokespecial	java.lang.Thread.setStateAndSwitch (I)I (8)
OP_POP,                                                                // 44:   pop
OP_RETURN,                                                             // 45:   return
};

/**
public static Thread currentThread()
Code(max_stack = 1, max_locals = 0, code_length = 4)
*/
#undef  JMETHOD
#define JMETHOD java_lang_Thread_currentThread_Ljava_lang_Thread
const           java_lang_Thread_currentThread_Ljava_lang_Thread_t JMETHOD PROGMEM ={
1+0 +2,    0,    0,    // max_stack, purLocals, purParams

OP_GETSTATIC_L,      SADR(java_lang_Thread_currentThread),             // 0:    getstatic		java.lang.Thread.currentThread Ljava/lang/Thread; (3)
OP_ARETURN,                                                            // 3:    areturn
};

/**
private native int setStateAndSwitch(int arg1)
*/
#ifndef native_java_lang_Thread_setStateAndSwitch_II
const NativCode java_lang_Thread_setStateAndSwitch_II PROGMEM ={0xff, &native_java_lang_Thread_setStateAndSwitch_II};
#endif

const class_t java_lang_Thread__class PROGMEM = {
	& java_lang_Object__class,
	SIZEOF_java_lang_Thread,
	1,
    {
		{MSG_stop___V, (ByteCode *)(&java_lang_Thread_stop_V)},
	}
};


//////////////////////////////////////////////////
// D:\arduino-1.0.5\libraries\.\HaikuVM\utility\Yarrays\YC.c
//

const class_t YC__class PROGMEM = {
    & java_lang_Object__class,
    0,
    0,
    // {{}} VC 5
};


//////////////////////////////////////////////////
// D:\arduino-1.0.5\libraries\.\HaikuVM\utility\java\lang\String.c
//
/*
public class java.lang.String extends java.lang.Object
implements		java.lang.CharSequence, java.lang.Comparable
filename		C:\DOKUME~1\genom\LOKALE~1\Temp\haikuvm/java/lang/String.class
compiled from		String.java
compiler version	50.0
access flags		33
constant pool		137 entries
ACC_SUPER flag		true

Attribute(s):
	Signature(Ljava/lang/Object;Ljava/lang/CharSequence;Ljava/lang/Comparable<Ljava/lang/String;>;)
	SourceFile(String.java)

1 fields:
	final char[] characters

29 methods:
	private void <init>(int arg1)
	public void <init>(char[] arg1)
	public void <init>(char arg1)
	public void <init>(String arg1)
	public int length()
	public char charAt(int arg1)
	public static String valueOf(Object arg0)
	public static String valueOf(boolean arg0)
	public static String valueOf(char arg0)
	public static String valueOf(float arg0)
	public static String valueOf(double arg0)
	public String toString()
	public static String valueOf(long arg0)
	public static String valueOf(int arg0)
	static String valueOf(int arg0, int arg1)
	public boolean equals(Object arg1)
	public int hashCode()
	public int indexOf(int arg1)
	public int indexOf(int arg1, int arg2)
	public int indexOf(String arg1)
	public int indexOf(String arg1, int arg2)
	public String substring(int arg1, int arg2)
	public String substring(int arg1)
	static int lastIndexOf(char[] arg0, int arg1, int arg2, char[] arg3, int arg4, int arg5, int arg6)
	static int indexOf(char[] arg0, int arg1, int arg2, char[] arg3, int arg4, int arg5, int arg6)
	public CharSequence subSequence(int arg1, int arg2)
	public int compareTo(String arg1)
	public String trim()
	public volatile synthetic int compareTo(Object arg1)

*/



const class_t java_lang_String__class PROGMEM = {
	& java_lang_Object__class,
	SIZEOF_java_lang_String,
	0,
    // {{}} VC 5
};


//////////////////////////////////////////////////
// D:\arduino-1.0.5\libraries\.\HaikuVM\utility\haiku\vm\MicroKernel.c
//
/*
public class haiku.vm.MicroKernel extends java.lang.Object
filename		C:\DOKUME~1\genom\LOKALE~1\Temp\haikuvm/haiku/vm/MicroKernel.class
compiled from		MicroKernel.java
compiler version	50.0
access flags		33
constant pool		114 entries
ACC_SUPER flag		true

Attribute(s):
	SourceFile(MicroKernel.java)

1 fields:
	private static Throwable outOfMemoryError

4 methods:
	public void <init>()
	public static native void clinitHaikuMagic()
	public static void throwException()
		throws java.lang.Throwable
	static void <clinit>()

*/



/**
public static native void clinitHaikuMagic()
*/

// clinit           processing/hardware/arduino/cores/arduino/Print
// clinit           processing/hardware/arduino/cores/arduino/Arduino
// clinit           haiku/vm/MicroKernel
// clinit           processing/examples/_01_Basics/Blink
const           haiku_vm_MicroKernel_clinitHaikuMagic_V_t haiku_vm_MicroKernel_clinitHaikuMagic_V PROGMEM ={
2, 0, 0,    // max_stack, purLocals, purParams

OP_INVOKESTATIC,     ADR(processing_hardware_arduino_cores_arduino_Print__clinit__V),   // processing.hardware.arduino.cores.arduino.Print.<clinit>
OP_INVOKESTATIC,     ADR(processing_hardware_arduino_cores_arduino_Arduino__clinit__V),   // processing.hardware.arduino.cores.arduino.Arduino.<clinit>
OP_INVOKESTATIC,     ADR(haiku_vm_MicroKernel__clinit__V),   // haiku.vm.MicroKernel.<clinit>
OP_INVOKESTATIC,     ADR(processing_examples__01_Basics_Blink__clinit__V),   // processing.examples._01_Basics.Blink.<clinit>
OP_RETURN,                                                             //       return
};

/**
static void <clinit>()
Code(max_stack = 3, max_locals = 0, code_length = 13)
*/
#undef  JMETHOD
#define JMETHOD haiku_vm_MicroKernel__clinit__V
const           haiku_vm_MicroKernel__clinit__V_t JMETHOD PROGMEM ={
3+0 +2,    0,    0,    // max_stack, purLocals, purParams

OP_NEW,              ADR(java_lang_OutOfMemoryError__class),           // 0:    new		<java.lang.OutOfMemoryError> (42)
OP_DUP,                                                                // 3:    dup
OP_LDC_S,            CADR(Const0000),                                  // 4:    ldc		"OutOfMemoryError" (43)
OP_INVOKESHORT_java_lang_OutOfMemoryError__init__Ljava_lang_String_V,  // 6:    invokespecial	java.lang.OutOfMemoryError.<init> (Ljava/lang/String;)V (44)
OP_PUTSTATIC_L,      SADR(haiku_vm_MicroKernel_outOfMemoryError),      // 9:    putstatic		haiku.vm.MicroKernel.outOfMemoryError Ljava/lang/Throwable; (26)
OP_RETURN,                                                             // 12:   return
};

const class_t haiku_vm_MicroKernel__class PROGMEM = {
	& java_lang_Object__class,
	SIZEOF_haiku_vm_MicroKernel,
	0,
    // {{}} VC 5
};


//////////////////////////////////////////////////
// D:\arduino-1.0.5\libraries\.\HaikuVM\utility\java\lang\OutOfMemoryError.c
//
/*
public class java.lang.OutOfMemoryError extends java.lang.Error
filename		java/lang/OutOfMemoryError.class
compiled from		OutOfMemoryError.java
compiler version	49.0
access flags		33
constant pool		21 entries
ACC_SUPER flag		true

Attribute(s):
	SourceFile(OutOfMemoryError.java)

2 methods:
	public void <init>()
	public void <init>(String message)

*/



/**
public void <init>(String message)
Code(max_stack = 2, max_locals = 2, code_length = 6)
*/
#undef  JMETHOD
#define JMETHOD java_lang_OutOfMemoryError__init__Ljava_lang_String_V
const           java_lang_OutOfMemoryError__init__Ljava_lang_String_V_t JMETHOD PROGMEM ={
2+2 +2,    0,    2,    // max_stack, purLocals, purParams

OP_ALOAD_0,                                                            // 0:    aload_0
OP_ALOAD_1,                                                            // 1:    aload_1
OP_INVOKESHORT_java_lang_Error__init__Ljava_lang_String_V,             // 2:    invokespecial	java.lang.Error.<init> (Ljava/lang/String;)V (2)
OP_RETURN,                                                             // 5:    return
};

const class_t java_lang_OutOfMemoryError__class PROGMEM = {
	& java_lang_Error__class,
	SIZEOF_java_lang_OutOfMemoryError,
	0,
    // {{}} VC 5
};


//////////////////////////////////////////////////
// D:\arduino-1.0.5\libraries\.\HaikuVM\utility\java\lang\Error.c
//
/*
public class java.lang.Error extends java.lang.Throwable
filename		java/lang/Error.class
compiled from		Error.java
compiler version	49.0
access flags		33
constant pool		21 entries
ACC_SUPER flag		true

Attribute(s):
	SourceFile(Error.java)

2 methods:
	public void <init>()
	public void <init>(String message)

*/



/**
public void <init>(String message)
Code(max_stack = 2, max_locals = 2, code_length = 6)
*/
#undef  JMETHOD
#define JMETHOD java_lang_Error__init__Ljava_lang_String_V
const           java_lang_Error__init__Ljava_lang_String_V_t JMETHOD PROGMEM ={
2+2 +2,    0,    2,    // max_stack, purLocals, purParams

OP_ALOAD_0,                                                            // 0:    aload_0
OP_ALOAD_1,                                                            // 1:    aload_1
OP_INVOKESHORT_java_lang_Throwable__init__Ljava_lang_String_V,         // 2:    invokespecial	java.lang.Throwable.<init> (Ljava/lang/String;)V (2)
OP_RETURN,                                                             // 5:    return
};

const class_t java_lang_Error__class PROGMEM = {
	& java_lang_Throwable__class,
	SIZEOF_java_lang_Error,
	0,
    // {{}} VC 5
};


//////////////////////////////////////////////////
// D:\arduino-1.0.5\libraries\.\HaikuVM\utility\processing\examples\_01_Basics\Blink.c
//
/*
public class processing.examples._01_Basics.Blink extends java.lang.Object
filename		C:\DOKUME~1\genom\LOKALE~1\Temp\haikuvm/processing/examples/_01_Basics/Blink.class
compiled from		Blink.java
compiler version	50.0
access flags		33
constant pool		35 entries
ACC_SUPER flag		true

Attribute(s):
	SourceFile(Blink.java)

1 fields:
	static byte ledPin

4 methods:
	public void <init>()
	public static void setup()
	public static void loop()
	static void <clinit>()

*/



/**
public static void setup()
Code(max_stack = 2, max_locals = 0, code_length = 8)
*/
#undef  JMETHOD
#define JMETHOD processing_examples__01_Basics_Blink_setup_V
const           processing_examples__01_Basics_Blink_setup_V_t JMETHOD PROGMEM ={
2+0 +2,    0,    0,    // max_stack, purLocals, purParams

OP_GETSTATIC_B,      SADR(processing_examples__01_Basics_Blink_ledPin), 
                                                                       // 0:    getstatic		processing.examples._01_Basics.Blink.ledPin B (2)
OP_ICONST_1,                                                           // 3:    iconst_1
OP_INVOKESHORT_processing_hardware_arduino_cores_arduino_Arduino_pinMode_IIV, 
                                                                       // 4:    invokestatic	processing.hardware.arduino.cores.arduino.Arduino.pinMode (II)V (3)
OP_RETURN,                                                             // 7:    return
};

/**
public static void loop()
Code(max_stack = 2, max_locals = 0, code_length = 27)
*/
#undef  JMETHOD
#define JMETHOD processing_examples__01_Basics_Blink_loop_V
const           processing_examples__01_Basics_Blink_loop_V_t JMETHOD PROGMEM ={
2+0 +2,    0,    0,    // max_stack, purLocals, purParams

OP_GETSTATIC_B,      SADR(processing_examples__01_Basics_Blink_ledPin), 
                                                                       // 0:    getstatic		processing.examples._01_Basics.Blink.ledPin B (2)
OP_ICONST_1,                                                           // 3:    iconst_1
OP_INVOKESHORT_processing_hardware_arduino_cores_arduino_Arduino_digitalWrite_IIV, 
                                                                       // 4:    invokestatic	processing.hardware.arduino.cores.arduino.Arduino.digitalWrite (II)V (4)
OP_LDC2_W_L,         CADR(Const0001),                                  // 7:    ldc2_w		1000 (5)
OP_INVOKESHORT_processing_hardware_arduino_cores_arduino_Arduino_delay_JV, 
                                                                       // 10:   invokestatic	processing.hardware.arduino.cores.arduino.Arduino.delay (J)V (7)
OP_GETSTATIC_B,      SADR(processing_examples__01_Basics_Blink_ledPin), 
                                                                       // 13:   getstatic		processing.examples._01_Basics.Blink.ledPin B (2)
OP_ICONST_0,                                                           // 16:   iconst_0
OP_INVOKESHORT_processing_hardware_arduino_cores_arduino_Arduino_digitalWrite_IIV, 
                                                                       // 17:   invokestatic	processing.hardware.arduino.cores.arduino.Arduino.digitalWrite (II)V (4)
OP_LDC2_W_L,         CADR(Const0001),                                  // 20:   ldc2_w		1000 (5)
OP_INVOKESHORT_processing_hardware_arduino_cores_arduino_Arduino_delay_JV, 
                                                                       // 23:   invokestatic	processing.hardware.arduino.cores.arduino.Arduino.delay (J)V (7)
OP_RETURN,                                                             // 26:   return
};

/**
static void <clinit>()
Code(max_stack = 1, max_locals = 0, code_length = 6)
*/
#undef  JMETHOD
#define JMETHOD processing_examples__01_Basics_Blink__clinit__V
const           processing_examples__01_Basics_Blink__clinit__V_t JMETHOD PROGMEM ={
1+0 +2,    0,    0,    // max_stack, purLocals, purParams

OP_BIPUSH,           B(13),                                            // 0:    bipush		13
OP_PUTSTATIC_B,      SADR(processing_examples__01_Basics_Blink_ledPin), 
                                                                       // 2:    putstatic		processing.examples._01_Basics.Blink.ledPin B (2)
OP_RETURN,                                                             // 5:    return
};

const class_t processing_examples__01_Basics_Blink__class PROGMEM = {
	& java_lang_Object__class,
	SIZEOF_processing_examples__01_Basics_Blink,
	0,
    // {{}} VC 5
};


//////////////////////////////////////////////////
// D:\arduino-1.0.5\libraries\.\HaikuVM\utility\processing\hardware\arduino\cores\arduino\Arduino.c
//
/*
public class processing.hardware.arduino.cores.arduino.Arduino extends java.lang.Object
filename		C:\DOKUME~1\genom\LOKALE~1\Temp\haikuvm/processing/hardware/arduino/cores/arduino/Arduino.class
compiled from		Arduino.java
compiler version	50.0
access flags		33
constant pool		252 entries
ACC_SUPER flag		true

Attribute(s):
	SourceFile(Arduino.java)

64 fields:
	public static final int HIGH = 1
	public static final int LOW = 0
	public static final int INPUT = 0
	public static final int OUTPUT = 1
	public static final int INPUT_PLLUP = 2
	public static final double PI = 3.141592653589793
	public static final double HALF_PI = 1.5707963267948966
	public static final double TWO_PI = 6.283185307179586
	public static final double DEG_TO_RAD = 0.017453292519943295
	public static final double RAD_TO_DEG = 57.29577951308232
	public static final int SERIAL = 0
	public static final int DISPLAY = 1
	public static final int LSBFIRST = 0
	public static final int MSBFIRST = 1
	public static final int CHANGE = 1
	public static final int FALLING = 2
	public static final int RISING = 3
	public static final int DEFALT = 0
	public static final int EXTERNAL = 1
	public static final int INTERNAL = 2
	public static final int INTERNAL1V1 = 2
	public static final int INTERNAL2V56 = 3
	public static final int DEC = 10
	public static final int HEX = 16
	public static final int OCT = 8
	public static final int BIN = 2
	public static processing.hardware.arduino.cores.arduino.HardwareSerial Serial
	public static final int NOT_A_PIN = 0
	public static final int NOT_A_PORT = 0
	public static final int A0 = 0
	public static final int A1 = 1
	public static final int A2 = 2
	public static final int A3 = 3
	public static final int A4 = 4
	public static final int A5 = 5
	public static final int PA = 1
	public static final int PB = 2
	public static final int PC = 3
	public static final int PD = 4
	public static final int PE = 5
	public static final int PF = 6
	public static final int PG = 7
	public static final int PH = 8
	public static final int PJ = 10
	public static final int PK = 11
	public static final int PL = 12
	public static final int NOT_ON_TIMER = 0
	public static final int TIMER0A = 1
	public static final int TIMER0B = 2
	public static final int TIMER1A = 3
	public static final int TIMER1B = 4
	public static final int TIMER2 = 5
	public static final int TIMER2A = 6
	public static final int TIMER2B = 7
	public static final int TIMER3A = 8
	public static final int TIMER3B = 9
	public static final int TIMER3C = 10
	public static final int TIMER4A = 11
	public static final int TIMER4B = 12
	public static final int TIMER4C = 13
	public static final int TIMER4D = 14
	public static final int TIMER5A = 15
	public static final int TIMER5B = 16
	public static final int TIMER5C = 17

59 methods:
	public void <init>()
	public static int min(int arg0, int arg1)
	public static int max(int arg0, int arg1)
	public static int abs(int arg0)
	public static int constrain(int arg0, int arg1, int arg2)
	public static double min(double arg0, double arg2)
	public static double max(double arg0, double arg2)
	public static double abs(double arg0)
	public static double constrain(double arg0, double arg2, double arg4)
	public static long round(double arg0)
	public static double radians(double arg0)
	public static double degrees(double arg0)
	public static int sq(int arg0)
	public static long sq(long arg0)
	public static double sq(double arg0)
	public static void interrupts()
	public static void noInterrupts()
	public static int clockCyclesPerMicrosecond()
	public static long clockCyclesToMicroseconds(int arg0)
	public static long microsecondsToClockCycles(int arg0)
	public static int lowByte(int arg0)
	public static int highByte(int arg0)
	public static int bitRead(int arg0, int arg1)
	public static int bitSet(int arg0, int arg1)
	public static int bitClear(int arg0, int arg1)
	public static int bitWrite(int arg0, int arg1, int arg2)
	public static int bit(int arg0)
	public static long bitRead(long arg0, int arg2)
	public static long bitSet(long arg0, int arg2)
	public static long bitClear(long arg0, int arg2)
	public static long bitWrite(long arg0, int arg2, int arg3)
	public static long bit(long arg0)
	public static void init()
	public static void pinMode(int arg0, int arg1)
	public static void digitalWrite(int arg0, int arg1)
	public static int digitalRead(int arg0)
	public static int analogRead(int arg0)
	public static void analogReference(int arg0)
	public static void analogWrite(int arg0, int arg1)
	public static long millis()
	public static long micros()
	public static void delay(long arg0)
	public static void delayMicroseconds(int arg0)
	public static long pulseIn(byte arg0, byte arg1, long arg2)
	public static void shiftOut(byte arg0, byte arg1, byte arg2, byte arg3)
	public static byte shiftIn(byte arg0, byte arg1, byte arg2)
	public static void detachInterrupt(byte arg0)
	public static int analogInPinToBit(int arg0)
	public static int makeWord(int arg0)
	public static int makeWord(byte arg0, byte arg1)
	public static long pulseIn(byte arg0, byte arg1)
	public static void tone(int arg0, int arg1)
	public static void tone(int arg0, int arg1, long arg2)
	public static void noTone(int arg0)
	public static long random(long arg0)
	public static long random(long arg0, long arg2)
	public static void randomSeed(int arg0)
	public static long map(long arg0, long arg2, long arg4, long arg6, long arg8)
	static void <clinit>()

*/



/**
public static void pinMode(int arg0, int arg1)
Code(max_stack = 2, max_locals = 2, code_length = 8)
*/
#undef  JMETHOD
#define JMETHOD processing_hardware_arduino_cores_arduino_Arduino_pinMode_IIV
const           processing_hardware_arduino_cores_arduino_Arduino_pinMode_IIV_t JMETHOD PROGMEM ={
2+2 +2,    0,    2,    // max_stack, purLocals, purParams

OP_ILOAD_0,                                                            // 0:    iload_0
OP_I2B,                                                                // 1:    i2b
OP_ILOAD_1,                                                            // 2:    iload_1
OP_I2B,                                                                // 3:    i2b
OP_INVOKESHORT_processing_hardware_arduino_cores_arduino_ArduinoImpl_pinMode_BBV, 
                                                                       // 4:    invokestatic	processing.hardware.arduino.cores.arduino.ArduinoImpl.pinMode (BB)V (16)
OP_RETURN,                                                             // 7:    return
};

/**
public static void digitalWrite(int arg0, int arg1)
Code(max_stack = 2, max_locals = 2, code_length = 8)
*/
#undef  JMETHOD
#define JMETHOD processing_hardware_arduino_cores_arduino_Arduino_digitalWrite_IIV
const           processing_hardware_arduino_cores_arduino_Arduino_digitalWrite_IIV_t JMETHOD PROGMEM ={
2+2 +2,    0,    2,    // max_stack, purLocals, purParams

OP_ILOAD_0,                                                            // 0:    iload_0
OP_I2B,                                                                // 1:    i2b
OP_ILOAD_1,                                                            // 2:    iload_1
OP_I2B,                                                                // 3:    i2b
OP_INVOKESHORT_processing_hardware_arduino_cores_arduino_ArduinoImpl_digitalWrite_BBV, 
                                                                       // 4:    invokestatic	processing.hardware.arduino.cores.arduino.ArduinoImpl.digitalWrite (BB)V (17)
OP_RETURN,                                                             // 7:    return
};

/**
public static void delay(long arg0)
Code(max_stack = 2, max_locals = 2, code_length = 5)
*/
#undef  JMETHOD
#define JMETHOD processing_hardware_arduino_cores_arduino_Arduino_delay_JV
const           processing_hardware_arduino_cores_arduino_Arduino_delay_JV_t JMETHOD PROGMEM ={
2+2 +2,    0,    2,    // max_stack, purLocals, purParams

OP_LLOAD_0,                                                            // 0:    lload_0
OP_INVOKESHORT_processing_hardware_arduino_cores_arduino_ArduinoImpl_delay_JV, 
                                                                       // 1:    invokestatic	processing.hardware.arduino.cores.arduino.ArduinoImpl.delay (J)V (24)
OP_RETURN,                                                             // 4:    return
};

/**
static void <clinit>()
Code(max_stack = 2, max_locals = 0, code_length = 11)
*/
#undef  JMETHOD
#define JMETHOD processing_hardware_arduino_cores_arduino_Arduino__clinit__V
const           processing_hardware_arduino_cores_arduino_Arduino__clinit__V_t JMETHOD PROGMEM ={
2+0 +2,    0,    0,    // max_stack, purLocals, purParams

OP_NEW,              ADR(processing_hardware_arduino_cores_arduino_HardwareSerial__class), 
                                                                       // 0:    new		<processing.hardware.arduino.cores.arduino.HardwareSerial> (41)
OP_DUP,                                                                // 3:    dup
OP_INVOKESHORT_processing_hardware_arduino_cores_arduino_HardwareSerial__init__V, 
                                                                       // 4:    invokespecial	processing.hardware.arduino.cores.arduino.HardwareSerial.<init> ()V (42)
OP_PUTSTATIC_L,      SADR(processing_hardware_arduino_cores_arduino_Arduino_Serial), 
                                                                       // 7:    putstatic		processing.hardware.arduino.cores.arduino.Arduino.Serial Lprocessing/hardware/arduino/cores/arduino/HardwareSerial; (43)
OP_RETURN,                                                             // 10:   return
};

const class_t processing_hardware_arduino_cores_arduino_Arduino__class PROGMEM = {
	& java_lang_Object__class,
	SIZEOF_processing_hardware_arduino_cores_arduino_Arduino,
	0,
    // {{}} VC 5
};


//////////////////////////////////////////////////
// D:\arduino-1.0.5\libraries\.\HaikuVM\utility\processing\hardware\arduino\cores\arduino\HardwareSerial.c
//
/*
public class processing.hardware.arduino.cores.arduino.HardwareSerial extends processing.hardware.arduino.cores.arduino.Stream
filename		C:\DOKUME~1\genom\LOKALE~1\Temp\haikuvm/processing/hardware/arduino/cores/arduino/HardwareSerial.class
compiled from		HardwareSerial.java
compiler version	50.0
access flags		33
constant pool		41 entries
ACC_SUPER flag		true

Attribute(s):
	SourceFile(HardwareSerial.java)

1 fields:
	private int lastChar

8 methods:
	public void <init>()
	public void begin(long arg1)
	public int available()
	public int read()
	public byte write(byte arg1)
	public boolean isOpen()
	public void flush()
	public int peek()

*/



/**
public void <init>()
Code(max_stack = 2, max_locals = 1, code_length = 10)
*/
#undef  JMETHOD
#define JMETHOD processing_hardware_arduino_cores_arduino_HardwareSerial__init__V
const           processing_hardware_arduino_cores_arduino_HardwareSerial__init__V_t JMETHOD PROGMEM ={
2+1 +2,    0,    1,    // max_stack, purLocals, purParams

OP_ALOAD_0,                                                            // 0:    aload_0
OP_INVOKESHORT_processing_hardware_arduino_cores_arduino_Stream__init__V, 
                                                                       // 1:    invokespecial	processing.hardware.arduino.cores.arduino.Stream.<init> ()V (1)
OP_ALOAD_0,                                                            // 4:    aload_0
OP_ICONST_M1,                                                          // 5:    iconst_m1
OP_PUTFIELD_I,       FIDX(processing_hardware_arduino_cores_arduino_HardwareSerial, lastChar), 
                                                                       // 6:    putfield		processing.hardware.arduino.cores.arduino.HardwareSerial.lastChar I (2)
OP_RETURN,                                                             // 9:    return
};

const class_t processing_hardware_arduino_cores_arduino_HardwareSerial__class PROGMEM = {
	& processing_hardware_arduino_cores_arduino_Stream__class,
	SIZEOF_processing_hardware_arduino_cores_arduino_HardwareSerial,
	0,
    // {{}} VC 5
};


//////////////////////////////////////////////////
// D:\arduino-1.0.5\libraries\.\HaikuVM\utility\processing\hardware\arduino\cores\arduino\Stream.c
//
/*
public abstract class processing.hardware.arduino.cores.arduino.Stream extends processing.hardware.arduino.cores.arduino.Print
filename		C:\DOKUME~1\genom\LOKALE~1\Temp\haikuvm/processing/hardware/arduino/cores/arduino/Stream.class
compiled from		Stream.java
compiler version	50.0
access flags		1057
constant pool		64 entries
ACC_SUPER flag		true

Attribute(s):
	SourceFile(Stream.java)

4 fields:
	private long _timeout
	private long _startMillis
	private final int PARSE_TIMEOUT = 1000
	private final char NO_SKIP_CHAR = 1

11 methods:
	public void <init>()
	public abstract byte write(byte arg1)
	public abstract int read()
	public abstract int available()
	public abstract int peek()
	public abstract void flush()
	private int timedRead()
	private int timedPeek()
	public long parseInt()
	public long parseInt(char arg1)
	private int peekNextDigit()

*/



/**
public void <init>()
Code(max_stack = 2, max_locals = 1, code_length = 17)
*/
#undef  JMETHOD
#define JMETHOD processing_hardware_arduino_cores_arduino_Stream__init__V
const           processing_hardware_arduino_cores_arduino_Stream__init__V_t JMETHOD PROGMEM ={
2+1 +2,    0,    1,    // max_stack, purLocals, purParams

OP_ALOAD_0,                                                            // 0:    aload_0
OP_INVOKESHORT_processing_hardware_arduino_cores_arduino_Print__init__V, 
                                                                       // 1:    invokespecial	processing.hardware.arduino.cores.arduino.Print.<init> ()V (1)
OP_ALOAD_0,                                                            // 4:    aload_0
OP_SIPUSH,           INT16(1000),                                      // 5:    sipush		1000
OP_PUTFIELD_I,       FIDX(processing_hardware_arduino_cores_arduino_Stream, PARSE_TIMEOUT_jfPARSE_TIMEOUT), 
                                                                       // 8:    putfield		processing.hardware.arduino.cores.arduino.Stream.PARSE_TIMEOUT I (2)
OP_ALOAD_0,                                                            // 11:   aload_0
OP_ICONST_1,                                                           // 12:   iconst_1
OP_PUTFIELD_C,       FIDX(processing_hardware_arduino_cores_arduino_Stream, NO_SKIP_CHAR_jfNO_SKIP_CHAR), 
                                                                       // 13:   putfield		processing.hardware.arduino.cores.arduino.Stream.NO_SKIP_CHAR C (3)
OP_RETURN,                                                             // 16:   return
};

const class_t processing_hardware_arduino_cores_arduino_Stream__class PROGMEM = {
	& processing_hardware_arduino_cores_arduino_Print__class,
	SIZEOF_processing_hardware_arduino_cores_arduino_Stream,
	0,
    // {{}} VC 5
};


//////////////////////////////////////////////////
// D:\arduino-1.0.5\libraries\.\HaikuVM\utility\Yarrays\YB.c
//

const class_t YB__class PROGMEM = {
    & java_lang_Object__class,
    0,
    0,
    // {{}} VC 5
};


//////////////////////////////////////////////////
// D:\arduino-1.0.5\libraries\.\HaikuVM\utility\processing\hardware\arduino\cores\arduino\Print.c
//
/*
public abstract class processing.hardware.arduino.cores.arduino.Print extends java.lang.Object
filename		C:\DOKUME~1\genom\LOKALE~1\Temp\haikuvm/processing/hardware/arduino/cores/arduino/Print.class
compiled from		Print.java
compiler version	50.0
access flags		1057
constant pool		80 entries
ACC_SUPER flag		true

Attribute(s):
	SourceFile(Print.java)

2 fields:
	public static final int BYTE = 0
	private static int write_error

22 methods:
	public void <init>()
	protected void setWriteError(int arg1)
	protected void setWriteError()
	public int getWriteError()
	public void clearWriteError()
	public void print(char arg1)
	public abstract byte write(byte arg1)
	public void println(String arg1)
	public void print(String arg1)
	public void print(int arg1)
	public void print(long arg1)
	public void print(long arg1, int arg3)
	public void print(double arg1)
	public void println()
	public void println(char arg1)
	public void println(int arg1)
	public void println(long arg1)
	public void println(long arg1, int arg3)
	public void println(double arg1)
	private void printNumber(long arg1, int arg3)
	public void printFloat(double arg1, int arg3)
	static void <clinit>()

*/



/**
public void <init>()
Code(max_stack = 1, max_locals = 1, code_length = 5)
*/
#undef  JMETHOD
#define JMETHOD processing_hardware_arduino_cores_arduino_Print__init__V
const           processing_hardware_arduino_cores_arduino_Print__init__V_t JMETHOD PROGMEM ={
1+1 +2,    0,    1,    // max_stack, purLocals, purParams

OP_ALOAD_0,                                                            // 0:    aload_0
OP_INVOKESHORT_java_lang_Object__init__V,                              // 1:    invokespecial	java.lang.Object.<init> ()V (1)
OP_RETURN,                                                             // 4:    return
};

/**
static void <clinit>()
Code(max_stack = 1, max_locals = 0, code_length = 5)
*/
#undef  JMETHOD
#define JMETHOD processing_hardware_arduino_cores_arduino_Print__clinit__V
const           processing_hardware_arduino_cores_arduino_Print__clinit__V_t JMETHOD PROGMEM ={
1+0 +2,    0,    0,    // max_stack, purLocals, purParams

OP_ICONST_0,                                                           // 0:    iconst_0
OP_PUTSTATIC_I,      SADR(processing_hardware_arduino_cores_arduino_Print_write_error), 
                                                                       // 1:    putstatic		processing.hardware.arduino.cores.arduino.Print.write_error I (2)
OP_RETURN,                                                             // 4:    return
};

const class_t processing_hardware_arduino_cores_arduino_Print__class PROGMEM = {
	& java_lang_Object__class,
	SIZEOF_processing_hardware_arduino_cores_arduino_Print,
	0,
    // {{}} VC 5
};


//////////////////////////////////////////////////
// D:\arduino-1.0.5\libraries\.\HaikuVM\utility\processing\hardware\arduino\cores\arduino\ArduinoImpl.c
//
/*
public class processing.hardware.arduino.cores.arduino.ArduinoImpl extends java.lang.Object
filename		C:\DOKUME~1\genom\LOKALE~1\Temp\haikuvm/processing/hardware/arduino/cores/arduino/ArduinoImpl.class
compiled from		ArduinoImpl.java
compiler version	50.0
access flags		33
constant pool		65 entries
ACC_SUPER flag		true

Attribute(s):
	SourceFile(ArduinoImpl.java)

1 fields:
	public static final long F_CPU = 16000000

26 methods:
	public void <init>()
	public static native void init() [(Unknown attribute RuntimeVisibleAnnotations: 00 01 00 10 00 00)]
	public static native void pinMode(byte arg0, byte arg1) [(Unknown attribute RuntimeVisibleAnnotations: 00 01 00 10 00 00)]
	public static native void digitalWrite(byte arg0, byte arg1) [(Unknown attribute RuntimeVisibleAnnotations: 00 01 00 10 00 00)]
	public static native int digitalRead(byte arg0) [(Unknown attribute RuntimeVisibleAnnotations: 00 01 00 10 00 00)]
	public static native int analogRead(byte arg0) [(Unknown attribute RuntimeVisibleAnnotations: 00 01 00 10 00 00)]
	public static native void analogReference(byte arg0) [(Unknown attribute RuntimeVisibleAnnotations: 00 01 00 10 00 00)]
	public static native void analogWrite(byte arg0, int arg1) [(Unknown attribute RuntimeVisibleAnnotations: 00 01 00 10 00 00)]
	public static native long millis() [(Unknown attribute RuntimeVisibleAnnotations: 00 01 00 10 00 00)]
	public static native long micros() [(Unknown attribute RuntimeVisibleAnnotations: 00 01 00 10 00 00)]
	public static void delay(long arg0)
	public static native void delayMicroseconds(int arg0) [(Unknown attribute RuntimeVisibleAnnotations: 00 01 00 10 00 00)]
	public static native long pulseIn(byte arg0, byte arg1, long arg2) [(Unknown attribute RuntimeVisibleAnnotations: 00 01 00 10 00 00)]
	public static native void shiftOut(byte arg0, byte arg1, byte arg2, byte arg3) [(Unknown attribute RuntimeVisibleAnnotations: 00 01 00 10 00 00)]
	public static native byte shiftIn(byte arg0, byte arg1, byte arg2) [(Unknown attribute RuntimeVisibleAnnotations: 00 01 00 10 00 00)]
	public static native void detachInterrupt(byte arg0) [(Unknown attribute RuntimeVisibleAnnotations: 00 01 00 10 00 00)]
	public static native void setup() [(Unknown attribute RuntimeVisibleAnnotations: 00 01 00 10 00 00)]
	public static native void loop() [(Unknown attribute RuntimeVisibleAnnotations: 00 01 00 10 00 00)]
	public static native int makeWord(int arg0) [(Unknown attribute RuntimeVisibleAnnotations: 00 01 00 10 00 00)]
	public static native int makeWord(byte arg0, byte arg1) [(Unknown attribute RuntimeVisibleAnnotations: 00 01 00 10 00 00)]
	public static native void tone(byte arg0, int arg1, long arg2) [(Unknown attribute RuntimeVisibleAnnotations: 00 01 00 30 00 00)]
	public static native void noTone(byte arg0) [(Unknown attribute RuntimeVisibleAnnotations: 00 01 00 30 00 00)]
	public static native long random(long arg0) [(Unknown attribute RuntimeVisibleAnnotations: 00 01 00 30 00 00)]
	public static native long random(long arg0, long arg2) [(Unknown attribute RuntimeVisibleAnnotations: 00 01 00 30 00 00)]
	public static native void randomSeed(int arg0) [(Unknown attribute RuntimeVisibleAnnotations: 00 01 00 30 00 00)]
	public static native long map(long arg0, long arg2, long arg4, long arg6, long arg8) [(Unknown attribute RuntimeVisibleAnnotations: 00 01 00 30 00 00)]

*/



/**
public static native void pinMode(byte arg0, byte arg1) [(Unknown attribute RuntimeVisibleAnnotations: 00 01 00 10 00 00)]
*/
#ifndef native_processing_hardware_arduino_cores_arduino_ArduinoImpl_pinMode_BBV
const NativCode processing_hardware_arduino_cores_arduino_ArduinoImpl_pinMode_BBV PROGMEM ={0xff, &native_processing_hardware_arduino_cores_arduino_ArduinoImpl_pinMode_BBV};
#endif

/**
public static native void digitalWrite(byte arg0, byte arg1) [(Unknown attribute RuntimeVisibleAnnotations: 00 01 00 10 00 00)]
*/
#ifndef native_processing_hardware_arduino_cores_arduino_ArduinoImpl_digitalWrite_BBV
const NativCode processing_hardware_arduino_cores_arduino_ArduinoImpl_digitalWrite_BBV PROGMEM ={0xff, &native_processing_hardware_arduino_cores_arduino_ArduinoImpl_digitalWrite_BBV};
#endif

/**
public static void delay(long arg0)
Code(max_stack = 2, max_locals = 2, code_length = 5)
*/
#undef  JMETHOD
#define JMETHOD processing_hardware_arduino_cores_arduino_ArduinoImpl_delay_JV
const           processing_hardware_arduino_cores_arduino_ArduinoImpl_delay_JV_t JMETHOD PROGMEM ={
2+2 +2,    0,    2,    // max_stack, purLocals, purParams

OP_LLOAD_0,                                                            // 0:    lload_0
OP_INVOKESHORT_java_lang_Thread_nap_JV,                                // 1:    invokestatic	java.lang.Thread.nap (J)V (2)
OP_RETURN,                                                             // 4:    return
};

const class_t processing_hardware_arduino_cores_arduino_ArduinoImpl__class PROGMEM = {
	& java_lang_Object__class,
	SIZEOF_processing_hardware_arduino_cores_arduino_ArduinoImpl,
	0,
    // {{}} VC 5
};


