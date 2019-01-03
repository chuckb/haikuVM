//////////////////////////////////////////////////
// D:\arduino-1.0.5\libraries\.\HaikuVM\utility\haiku\avr\lib\arduino\HaikuMicroKernel4ArduinoIDE.h
//
typedef struct {
	uint8_t max_stack; int8_t purLocals; uint8_t purParams;

	OP_bc op0;                                                             // 0:    invokestatic	haiku.avr.lib.arduino.HaikuMicroKernel4ArduinoIDE.clinitHaikuMagic ()V (2)
	OP_bc op6;                                                             // 6:    invokestatic	haiku.vm.HaikuMagic.setup ()V (4)
	OP_bc op9;                                                             // 9:    invokestatic	haiku.vm.HaikuMagic.loop ()V (5)
	OP_bc op12; OPtrg a12;                                                 // 12:   goto		#9
	OP_bc op15;                                                            // 15:   astore_1
	OP_bc op16;                                                            // 16:   invokestatic	java.lang.Thread.currentThread ()Ljava/lang/Thread; (7)
	OP_bc op19; OP__8 a19; OP__8 b19;                                      // 19:   invokevirtual	java.lang.Thread.stop ()V (8)
	OP_bc op22;                                                            // 22:   return
}            main_YLjava_lang_String_t;
extern const main_YLjava_lang_String_t main_YLjava_lang_String;



#ifndef haiku_avr_lib_arduino_HaikuMicroKernel4ArduinoIDE

#define SIZEOF_haiku_avr_lib_arduino_HaikuMicroKernel4ArduinoIDE 0


#endif
extern const class_t haiku_avr_lib_arduino_HaikuMicroKernel4ArduinoIDE__class;


//////////////////////////////////////////////////
// D:\arduino-1.0.5\libraries\.\HaikuVM\utility\java\lang\Throwable.h
//
typedef struct {
	uint8_t max_stack; int8_t purLocals; uint8_t purParams;

	OP_bc op0;                                                             // 0:    aload_0
	OP_bc op1;                                                             // 1:    invokespecial	java.lang.Object.<init> ()V (1)
	OP_bc op4;                                                             // 4:    aload_0
	OP_bc op5; OP__8 a5; OP__8 b5;                                         // 5:    invokevirtual	java.lang.Throwable.fillInStackTrace ()Ljava/lang/Throwable; (2)
	OP_bc op8;                                                             // 8:    pop
	OP_bc op9;                                                             // 9:    aload_0
	OP_bc op10;                                                            // 10:   aload_1
	OP_bc op11; OP_16 a11;                                                 // 11:   putfield		java.lang.Throwable._message Ljava/lang/String; (3)
	OP_bc op14;                                                            // 14:   return
}            java_lang_Throwable__init__Ljava_lang_String_V_t;
extern const java_lang_Throwable__init__Ljava_lang_String_V_t java_lang_Throwable__init__Ljava_lang_String_V;

typedef struct {
	uint8_t max_stack; int8_t purLocals; uint8_t purParams;

	OP_bc op0;                                                             // 0:    aload_0
	OP_bc op1;                                                             // 1:    areturn
}            java_lang_Throwable_fillInStackTrace_Ljava_lang_Throwable_t;
extern const java_lang_Throwable_fillInStackTrace_Ljava_lang_Throwable_t java_lang_Throwable_fillInStackTrace_Ljava_lang_Throwable;



#ifndef java_lang_Throwable

typedef struct java_lang_Throwable {
  jintArray _stackTrace;	//[I
  jstring _message;	//Ljava/lang/String;
  jobject _cause;	//Ljava/lang/Throwable;
} java_lang_Throwable;
#define SIZEOF_java_lang_Throwable sizeof(java_lang_Throwable)


#endif
extern const class_t java_lang_Throwable__class;


//////////////////////////////////////////////////
// D:\arduino-1.0.5\libraries\.\HaikuVM\utility\java\lang\Object.h
//
typedef struct {
	uint8_t max_stack; int8_t purLocals; uint8_t purParams;

	OP_bc op0;                                                             // 0:    return
}            java_lang_Object__init__V_t;
extern const java_lang_Object__init__V_t java_lang_Object__init__V;



#ifndef java_lang_Object

#define SIZEOF_java_lang_Object 0


#endif
extern const class_t java_lang_Object__class;


//////////////////////////////////////////////////
// D:\arduino-1.0.5\libraries\.\HaikuVM\utility\java\lang\System.h
//
#ifndef native_java_lang_System_currentTimeMillis_J
extern const NativCode java_lang_System_currentTimeMillis_J;
extern void            native_java_lang_System_currentTimeMillis_J(void);
#endif



#ifndef java_lang_System

#define SIZEOF_java_lang_System 0


#endif
extern const class_t java_lang_System__class;


//////////////////////////////////////////////////
// D:\arduino-1.0.5\libraries\.\HaikuVM\utility\java\lang\Thread.h
//
typedef struct {
	uint8_t max_stack; int8_t purLocals; uint8_t purParams;

	OP_bc op0; OPadr a0;                                                   // 0:    getstatic		java.lang.Thread.currentThread Ljava/lang/Thread; (3)
	OP_bc op3;                                                             // 3:    invokestatic	java.lang.System.currentTimeMillis ()J (6)
	OP_bc op6;                                                             // 6:    lload_0
	OP_bc op7;                                                             // 7:    ladd
	OP_bc op8; OP_16 a8;                                                   // 8:    putfield		java.lang.Thread.waitUntil J (7)
	OP_bc op11; OPadr a11;                                                 // 11:   getstatic		java.lang.Thread.currentThread Ljava/lang/Thread; (3)
	OP_bc op14; OP__8 b14;                                                 // 14:   bipush		6
	OP_bc op16;                                                            // 16:   invokespecial	java.lang.Thread.setStateAndSwitch (I)I (8)
	OP_bc op19;                                                            // 19:   pop
	OP_bc op20;                                                            // 20:   return
}            java_lang_Thread_nap_JV_t;
extern const java_lang_Thread_nap_JV_t java_lang_Thread_nap_JV;

typedef struct {
	uint8_t max_stack; int8_t purLocals; uint8_t purParams;

	OP_bc op0;                                                             // 0:    aload_0
	OP_bc op1; OP_16 a1;                                                   // 1:    getfield		java.lang.Thread.state I (19)
	OP_bc op4; OP__8 b4;                                                   // 4:    bipush		8
	OP_bc op6; OPtrg a6;                                                   // 6:    if_icmpeq		#45
	OP_bc op9;                                                             // 9:    aload_0
	OP_bc op10;                                                            // 10:   astore_1
	OP_bc op11;                                                            // 11:   aload_1
	OP_bc op12; OP_16 a12;                                                 // 12:   getfield		java.lang.Thread.next Ljava/lang/Thread; (12)
	OP_bc op15;                                                            // 15:   aload_0
	OP_bc op16; OPtrg a16;                                                 // 16:   if_acmpeq		#27
	OP_bc op19;                                                            // 19:   aload_1
	OP_bc op20; OP_16 a20;                                                 // 20:   getfield		java.lang.Thread.next Ljava/lang/Thread; (12)
	OP_bc op23;                                                            // 23:   astore_1
	OP_bc op24; OPtrg a24;                                                 // 24:   goto		#11
	OP_bc op27;                                                            // 27:   aload_1
	OP_bc op28;                                                            // 28:   aload_1
	OP_bc op29; OP_16 a29;                                                 // 29:   getfield		java.lang.Thread.next Ljava/lang/Thread; (12)
	OP_bc op32; OP_16 a32;                                                 // 32:   getfield		java.lang.Thread.next Ljava/lang/Thread; (12)
	OP_bc op35; OP_16 a35;                                                 // 35:   putfield		java.lang.Thread.next Ljava/lang/Thread; (12)
	OP_bc op38;                                                            // 38:   aload_0
	OP_bc op39; OP__8 b39;                                                 // 39:   bipush		8
	OP_bc op41;                                                            // 41:   invokespecial	java.lang.Thread.setStateAndSwitch (I)I (8)
	OP_bc op44;                                                            // 44:   pop
	OP_bc op45;                                                            // 45:   return
}            java_lang_Thread_stop_V_t;
extern const java_lang_Thread_stop_V_t java_lang_Thread_stop_V;

typedef struct {
	uint8_t max_stack; int8_t purLocals; uint8_t purParams;

	OP_bc op0; OPadr a0;                                                   // 0:    getstatic		java.lang.Thread.currentThread Ljava/lang/Thread; (3)
	OP_bc op3;                                                             // 3:    areturn
}            java_lang_Thread_currentThread_Ljava_lang_Thread_t;
extern const java_lang_Thread_currentThread_Ljava_lang_Thread_t java_lang_Thread_currentThread_Ljava_lang_Thread;

#ifndef native_java_lang_Thread_setStateAndSwitch_II
extern const NativCode java_lang_Thread_setStateAndSwitch_II;
extern void            native_java_lang_Thread_setStateAndSwitch_II(void);
#endif



#ifndef java_lang_Thread

typedef struct java_lang_Thread {
  jobject target;	//Ljava/lang/Runnable;
  jobject next;	//Ljava/lang/Thread;
  jint state;	//I
  jobject programcounter;	//Ljava/lang/Object;
  jobject stackpointer;	//Ljava/lang/Object;
  jobject stackframe;	//Ljava/lang/Object;
  jobject stack;	//Ljava/lang/Object;
  jlong waitUntil;	//J
  jobject locks;	//Ljava/lang/Object;
  jboolean interrupt;	//Z
  jobject waitingOn;	//Ljava/lang/Object;
} java_lang_Thread;
#define SIZEOF_java_lang_Thread sizeof(java_lang_Thread)


#endif
extern const class_t java_lang_Thread__class;


//////////////////////////////////////////////////
// D:\arduino-1.0.5\libraries\.\HaikuVM\utility\Yarrays\YC.h
//
extern const class_t YC__class;


//////////////////////////////////////////////////
// D:\arduino-1.0.5\libraries\.\HaikuVM\utility\java\lang\String.h
//


#ifndef java_lang_String

typedef struct java_lang_String {
  jchar8or16Array characters;	//[C
} java_lang_String;
#define SIZEOF_java_lang_String sizeof(java_lang_String)


#endif
extern const class_t java_lang_String__class;


//////////////////////////////////////////////////
// D:\arduino-1.0.5\libraries\.\HaikuVM\utility\haiku\vm\MicroKernel.h
//
typedef struct {
    uint8_t max_stack; int8_t purLocals; uint8_t purParams;
	OP_bc oph147; OPadr fh147;  // <clinit>
	OP_bc oph150; OPadr fh150;  // <clinit>
	OP_bc oph153; OPadr fh153;  // <clinit>
	OP_bc oph156; OPadr fh156;  // <clinit>
    OP_bc op0;                                                             //       return
}            haiku_vm_MicroKernel_clinitHaikuMagic_V_t;
extern const haiku_vm_MicroKernel_clinitHaikuMagic_V_t haiku_vm_MicroKernel_clinitHaikuMagic_V;

typedef struct {
	uint8_t max_stack; int8_t purLocals; uint8_t purParams;

	OP_bc op0; OPadr f0;                                                   // 0:    new		<java.lang.OutOfMemoryError> (42)
	OP_bc op3;                                                             // 3:    dup
	OP_bc op4; OPcon c4;                                                   // 4:    ldc		"OutOfMemoryError" (43)
	OP_bc op6;                                                             // 6:    invokespecial	java.lang.OutOfMemoryError.<init> (Ljava/lang/String;)V (44)
	OP_bc op9; OPadr a9;                                                   // 9:    putstatic		haiku.vm.MicroKernel.outOfMemoryError Ljava/lang/Throwable; (26)
	OP_bc op12;                                                            // 12:   return
}            haiku_vm_MicroKernel__clinit__V_t;
extern const haiku_vm_MicroKernel__clinit__V_t haiku_vm_MicroKernel__clinit__V;



#ifndef haiku_vm_MicroKernel

#define SIZEOF_haiku_vm_MicroKernel 0


#endif
extern const class_t haiku_vm_MicroKernel__class;


//////////////////////////////////////////////////
// D:\arduino-1.0.5\libraries\.\HaikuVM\utility\java\lang\OutOfMemoryError.h
//
typedef struct {
	uint8_t max_stack; int8_t purLocals; uint8_t purParams;

	OP_bc op0;                                                             // 0:    aload_0
	OP_bc op1;                                                             // 1:    aload_1
	OP_bc op2;                                                             // 2:    invokespecial	java.lang.Error.<init> (Ljava/lang/String;)V (2)
	OP_bc op5;                                                             // 5:    return
}            java_lang_OutOfMemoryError__init__Ljava_lang_String_V_t;
extern const java_lang_OutOfMemoryError__init__Ljava_lang_String_V_t java_lang_OutOfMemoryError__init__Ljava_lang_String_V;



#ifndef java_lang_OutOfMemoryError

typedef struct java_lang_OutOfMemoryError {
  jintArray _stackTrace;	//[I
  jstring _message;	//Ljava/lang/String;
  jobject _cause;	//Ljava/lang/Throwable;
} java_lang_OutOfMemoryError;
#define SIZEOF_java_lang_OutOfMemoryError sizeof(java_lang_OutOfMemoryError)


#endif
extern const class_t java_lang_OutOfMemoryError__class;


//////////////////////////////////////////////////
// D:\arduino-1.0.5\libraries\.\HaikuVM\utility\java\lang\Error.h
//
typedef struct {
	uint8_t max_stack; int8_t purLocals; uint8_t purParams;

	OP_bc op0;                                                             // 0:    aload_0
	OP_bc op1;                                                             // 1:    aload_1
	OP_bc op2;                                                             // 2:    invokespecial	java.lang.Throwable.<init> (Ljava/lang/String;)V (2)
	OP_bc op5;                                                             // 5:    return
}            java_lang_Error__init__Ljava_lang_String_V_t;
extern const java_lang_Error__init__Ljava_lang_String_V_t java_lang_Error__init__Ljava_lang_String_V;



#ifndef java_lang_Error

typedef struct java_lang_Error {
  jintArray _stackTrace;	//[I
  jstring _message;	//Ljava/lang/String;
  jobject _cause;	//Ljava/lang/Throwable;
} java_lang_Error;
#define SIZEOF_java_lang_Error sizeof(java_lang_Error)


#endif
extern const class_t java_lang_Error__class;


//////////////////////////////////////////////////
// D:\arduino-1.0.5\libraries\.\HaikuVM\utility\processing\examples\_01_Basics\Blink.h
//
typedef struct {
	uint8_t max_stack; int8_t purLocals; uint8_t purParams;

	OP_bc op0; OPadr a0;                                                   // 0:    getstatic		processing.examples._01_Basics.Blink.ledPin B (2)
	OP_bc op3;                                                             // 3:    iconst_1
	OP_bc op4;                                                             // 4:    invokestatic	processing.hardware.arduino.cores.arduino.Arduino.pinMode (II)V (3)
	OP_bc op7;                                                             // 7:    return
}            processing_examples__01_Basics_Blink_setup_V_t;
extern const processing_examples__01_Basics_Blink_setup_V_t processing_examples__01_Basics_Blink_setup_V;

typedef struct {
	uint8_t max_stack; int8_t purLocals; uint8_t purParams;

	OP_bc op0; OPadr a0;                                                   // 0:    getstatic		processing.examples._01_Basics.Blink.ledPin B (2)
	OP_bc op3;                                                             // 3:    iconst_1
	OP_bc op4;                                                             // 4:    invokestatic	processing.hardware.arduino.cores.arduino.Arduino.digitalWrite (II)V (4)
	OP_bc op7; OPcon c7;                                                   // 7:    ldc2_w		1000 (5)
	OP_bc op10;                                                            // 10:   invokestatic	processing.hardware.arduino.cores.arduino.Arduino.delay (J)V (7)
	OP_bc op13; OPadr a13;                                                 // 13:   getstatic		processing.examples._01_Basics.Blink.ledPin B (2)
	OP_bc op16;                                                            // 16:   iconst_0
	OP_bc op17;                                                            // 17:   invokestatic	processing.hardware.arduino.cores.arduino.Arduino.digitalWrite (II)V (4)
	OP_bc op20; OPcon c20;                                                 // 20:   ldc2_w		1000 (5)
	OP_bc op23;                                                            // 23:   invokestatic	processing.hardware.arduino.cores.arduino.Arduino.delay (J)V (7)
	OP_bc op26;                                                            // 26:   return
}            processing_examples__01_Basics_Blink_loop_V_t;
extern const processing_examples__01_Basics_Blink_loop_V_t processing_examples__01_Basics_Blink_loop_V;

typedef struct {
	uint8_t max_stack; int8_t purLocals; uint8_t purParams;

	OP_bc op0; OP__8 b0;                                                   // 0:    bipush		13
	OP_bc op2; OPadr a2;                                                   // 2:    putstatic		processing.examples._01_Basics.Blink.ledPin B (2)
	OP_bc op5;                                                             // 5:    return
}            processing_examples__01_Basics_Blink__clinit__V_t;
extern const processing_examples__01_Basics_Blink__clinit__V_t processing_examples__01_Basics_Blink__clinit__V;



#ifndef processing_examples__01_Basics_Blink

#define SIZEOF_processing_examples__01_Basics_Blink 0


#endif
extern const class_t processing_examples__01_Basics_Blink__class;


//////////////////////////////////////////////////
// D:\arduino-1.0.5\libraries\.\HaikuVM\utility\processing\hardware\arduino\cores\arduino\Arduino.h
//
typedef struct {
	uint8_t max_stack; int8_t purLocals; uint8_t purParams;

	OP_bc op0;                                                             // 0:    iload_0
	OP_bc op1;                                                             // 1:    i2b
	OP_bc op2;                                                             // 2:    iload_1
	OP_bc op3;                                                             // 3:    i2b
	OP_bc op4;                                                             // 4:    invokestatic	processing.hardware.arduino.cores.arduino.ArduinoImpl.pinMode (BB)V (16)
	OP_bc op7;                                                             // 7:    return
}            processing_hardware_arduino_cores_arduino_Arduino_pinMode_IIV_t;
extern const processing_hardware_arduino_cores_arduino_Arduino_pinMode_IIV_t processing_hardware_arduino_cores_arduino_Arduino_pinMode_IIV;

typedef struct {
	uint8_t max_stack; int8_t purLocals; uint8_t purParams;

	OP_bc op0;                                                             // 0:    iload_0
	OP_bc op1;                                                             // 1:    i2b
	OP_bc op2;                                                             // 2:    iload_1
	OP_bc op3;                                                             // 3:    i2b
	OP_bc op4;                                                             // 4:    invokestatic	processing.hardware.arduino.cores.arduino.ArduinoImpl.digitalWrite (BB)V (17)
	OP_bc op7;                                                             // 7:    return
}            processing_hardware_arduino_cores_arduino_Arduino_digitalWrite_IIV_t;
extern const processing_hardware_arduino_cores_arduino_Arduino_digitalWrite_IIV_t processing_hardware_arduino_cores_arduino_Arduino_digitalWrite_IIV;

typedef struct {
	uint8_t max_stack; int8_t purLocals; uint8_t purParams;

	OP_bc op0;                                                             // 0:    lload_0
	OP_bc op1;                                                             // 1:    invokestatic	processing.hardware.arduino.cores.arduino.ArduinoImpl.delay (J)V (24)
	OP_bc op4;                                                             // 4:    return
}            processing_hardware_arduino_cores_arduino_Arduino_delay_JV_t;
extern const processing_hardware_arduino_cores_arduino_Arduino_delay_JV_t processing_hardware_arduino_cores_arduino_Arduino_delay_JV;

typedef struct {
	uint8_t max_stack; int8_t purLocals; uint8_t purParams;

	OP_bc op0; OPadr f0;                                                   // 0:    new		<processing.hardware.arduino.cores.arduino.HardwareSerial> (41)
	OP_bc op3;                                                             // 3:    dup
	OP_bc op4;                                                             // 4:    invokespecial	processing.hardware.arduino.cores.arduino.HardwareSerial.<init> ()V (42)
	OP_bc op7; OPadr a7;                                                   // 7:    putstatic		processing.hardware.arduino.cores.arduino.Arduino.Serial Lprocessing/hardware/arduino/cores/arduino/HardwareSerial; (43)
	OP_bc op10;                                                            // 10:   return
}            processing_hardware_arduino_cores_arduino_Arduino__clinit__V_t;
extern const processing_hardware_arduino_cores_arduino_Arduino__clinit__V_t processing_hardware_arduino_cores_arduino_Arduino__clinit__V;



#ifndef processing_hardware_arduino_cores_arduino_Arduino

#define SIZEOF_processing_hardware_arduino_cores_arduino_Arduino 0


#endif
extern const class_t processing_hardware_arduino_cores_arduino_Arduino__class;


//////////////////////////////////////////////////
// D:\arduino-1.0.5\libraries\.\HaikuVM\utility\processing\hardware\arduino\cores\arduino\HardwareSerial.h
//
typedef struct {
	uint8_t max_stack; int8_t purLocals; uint8_t purParams;

	OP_bc op0;                                                             // 0:    aload_0
	OP_bc op1;                                                             // 1:    invokespecial	processing.hardware.arduino.cores.arduino.Stream.<init> ()V (1)
	OP_bc op4;                                                             // 4:    aload_0
	OP_bc op5;                                                             // 5:    iconst_m1
	OP_bc op6; OP_16 a6;                                                   // 6:    putfield		processing.hardware.arduino.cores.arduino.HardwareSerial.lastChar I (2)
	OP_bc op9;                                                             // 9:    return
}            processing_hardware_arduino_cores_arduino_HardwareSerial__init__V_t;
extern const processing_hardware_arduino_cores_arduino_HardwareSerial__init__V_t processing_hardware_arduino_cores_arduino_HardwareSerial__init__V;



#ifndef processing_hardware_arduino_cores_arduino_HardwareSerial

typedef struct processing_hardware_arduino_cores_arduino_HardwareSerial {
  jlong _timeout;	//J
  jlong _startMillis;	//J
  jint PARSE_TIMEOUT_jfPARSE_TIMEOUT;	//I
  jchar NO_SKIP_CHAR_jfNO_SKIP_CHAR;	//C
  jint lastChar;	//I
} processing_hardware_arduino_cores_arduino_HardwareSerial;
#define SIZEOF_processing_hardware_arduino_cores_arduino_HardwareSerial sizeof(processing_hardware_arduino_cores_arduino_HardwareSerial)


#endif
extern const class_t processing_hardware_arduino_cores_arduino_HardwareSerial__class;


//////////////////////////////////////////////////
// D:\arduino-1.0.5\libraries\.\HaikuVM\utility\processing\hardware\arduino\cores\arduino\Stream.h
//
typedef struct {
	uint8_t max_stack; int8_t purLocals; uint8_t purParams;

	OP_bc op0;                                                             // 0:    aload_0
	OP_bc op1;                                                             // 1:    invokespecial	processing.hardware.arduino.cores.arduino.Print.<init> ()V (1)
	OP_bc op4;                                                             // 4:    aload_0
	OP_bc op5; OP_16 a5;                                                   // 5:    sipush		1000
	OP_bc op8; OP_16 a8;                                                   // 8:    putfield		processing.hardware.arduino.cores.arduino.Stream.PARSE_TIMEOUT I (2)
	OP_bc op11;                                                            // 11:   aload_0
	OP_bc op12;                                                            // 12:   iconst_1
	OP_bc op13; OP_16 a13;                                                 // 13:   putfield		processing.hardware.arduino.cores.arduino.Stream.NO_SKIP_CHAR C (3)
	OP_bc op16;                                                            // 16:   return
}            processing_hardware_arduino_cores_arduino_Stream__init__V_t;
extern const processing_hardware_arduino_cores_arduino_Stream__init__V_t processing_hardware_arduino_cores_arduino_Stream__init__V;



#ifndef processing_hardware_arduino_cores_arduino_Stream

typedef struct processing_hardware_arduino_cores_arduino_Stream {
  jlong _timeout;	//J
  jlong _startMillis;	//J
  jint PARSE_TIMEOUT_jfPARSE_TIMEOUT;	//I
  jchar NO_SKIP_CHAR_jfNO_SKIP_CHAR;	//C
} processing_hardware_arduino_cores_arduino_Stream;
#define SIZEOF_processing_hardware_arduino_cores_arduino_Stream sizeof(processing_hardware_arduino_cores_arduino_Stream)


#endif
extern const class_t processing_hardware_arduino_cores_arduino_Stream__class;


//////////////////////////////////////////////////
// D:\arduino-1.0.5\libraries\.\HaikuVM\utility\Yarrays\YB.h
//
extern const class_t YB__class;


//////////////////////////////////////////////////
// D:\arduino-1.0.5\libraries\.\HaikuVM\utility\processing\hardware\arduino\cores\arduino\Print.h
//
typedef struct {
	uint8_t max_stack; int8_t purLocals; uint8_t purParams;

	OP_bc op0;                                                             // 0:    aload_0
	OP_bc op1;                                                             // 1:    invokespecial	java.lang.Object.<init> ()V (1)
	OP_bc op4;                                                             // 4:    return
}            processing_hardware_arduino_cores_arduino_Print__init__V_t;
extern const processing_hardware_arduino_cores_arduino_Print__init__V_t processing_hardware_arduino_cores_arduino_Print__init__V;

typedef struct {
	uint8_t max_stack; int8_t purLocals; uint8_t purParams;

	OP_bc op0;                                                             // 0:    iconst_0
	OP_bc op1; OPadr a1;                                                   // 1:    putstatic		processing.hardware.arduino.cores.arduino.Print.write_error I (2)
	OP_bc op4;                                                             // 4:    return
}            processing_hardware_arduino_cores_arduino_Print__clinit__V_t;
extern const processing_hardware_arduino_cores_arduino_Print__clinit__V_t processing_hardware_arduino_cores_arduino_Print__clinit__V;



#ifndef processing_hardware_arduino_cores_arduino_Print

#define SIZEOF_processing_hardware_arduino_cores_arduino_Print 0


#endif
extern const class_t processing_hardware_arduino_cores_arduino_Print__class;


//////////////////////////////////////////////////
// D:\arduino-1.0.5\libraries\.\HaikuVM\utility\processing\hardware\arduino\cores\arduino\ArduinoImpl.h
//
#ifndef native_processing_hardware_arduino_cores_arduino_ArduinoImpl_pinMode_BBV
extern const NativCode processing_hardware_arduino_cores_arduino_ArduinoImpl_pinMode_BBV;
extern void            native_processing_hardware_arduino_cores_arduino_ArduinoImpl_pinMode_BBV(void);
#endif

#ifndef native_processing_hardware_arduino_cores_arduino_ArduinoImpl_digitalWrite_BBV
extern const NativCode processing_hardware_arduino_cores_arduino_ArduinoImpl_digitalWrite_BBV;
extern void            native_processing_hardware_arduino_cores_arduino_ArduinoImpl_digitalWrite_BBV(void);
#endif

typedef struct {
	uint8_t max_stack; int8_t purLocals; uint8_t purParams;

	OP_bc op0;                                                             // 0:    lload_0
	OP_bc op1;                                                             // 1:    invokestatic	java.lang.Thread.nap (J)V (2)
	OP_bc op4;                                                             // 4:    return
}            processing_hardware_arduino_cores_arduino_ArduinoImpl_delay_JV_t;
extern const processing_hardware_arduino_cores_arduino_ArduinoImpl_delay_JV_t processing_hardware_arduino_cores_arduino_ArduinoImpl_delay_JV;



#ifndef processing_hardware_arduino_cores_arduino_ArduinoImpl

#define SIZEOF_processing_hardware_arduino_cores_arduino_ArduinoImpl 0


#endif
extern const class_t processing_hardware_arduino_cores_arduino_ArduinoImpl__class;


