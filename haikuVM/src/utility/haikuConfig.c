#include "haikuConfig.h"

#include "Bytecodes.h"
#include <float.h>



#if DEBUG_DISPATCHER
// bytecodes: 63

const BytecodeFoo bytecodeFoo[] PROGMEM = {
	OPF_INVOKESHORT,               //   0 java_lang_Thread_setStateAndSwitch_II
	OPF_INVOKESHORT,               //   1 java_lang_Object__init__V
	OPF_INVOKESHORT,               //   2 processing_hardware_arduino_cores_arduino_Arduino_delay_JV
	OPF_INVOKESHORT,               //   3 processing_hardware_arduino_cores_arduino_Arduino_digitalWrite_IIV
	OPF_INVOKESHORT,               //   4 processing_hardware_arduino_cores_arduino_Stream__init__V
	OPF_INVOKESHORT,               //   5 java_lang_System_currentTimeMillis_J
	OPF_INVOKESHORT,               //   6 processing_hardware_arduino_cores_arduino_ArduinoImpl_pinMode_BBV
	OPF_INVOKESHORT,               //   7 processing_hardware_arduino_cores_arduino_Print__init__V
	OPF_INVOKESHORT,               //   8 processing_examples__01_Basics_Blink_loop_V
	OPF_INVOKESHORT,               //   9 java_lang_Throwable__init__Ljava_lang_String_V
	OPF_INVOKESHORT,               //  10 processing_hardware_arduino_cores_arduino_HardwareSerial__init__V
	OPF_INVOKESHORT,               //  11 java_lang_OutOfMemoryError__init__Ljava_lang_String_V
	OPF_INVOKESHORT,               //  12 java_lang_Error__init__Ljava_lang_String_V
	OPF_INVOKESHORT,               //  13 processing_hardware_arduino_cores_arduino_Arduino_pinMode_IIV
	OPF_INVOKESHORT,               //  14 java_lang_Thread_currentThread_Ljava_lang_Thread
	OPF_INVOKESHORT,               //  15 processing_examples__01_Basics_Blink_setup_V
	OPF_INVOKESHORT,               //  16 haiku_vm_MicroKernel_clinitHaikuMagic_V
	OPF_INVOKESHORT,               //  17 processing_hardware_arduino_cores_arduino_ArduinoImpl_delay_JV
	OPF_INVOKESHORT,               //  18 processing_hardware_arduino_cores_arduino_ArduinoImpl_digitalWrite_BBV
	OPF_INVOKESHORT,               //  19 java_lang_Thread_nap_JV
	OPF_INVOKESHORT,               //  20 processing_examples__01_Basics_Blink__clinit__V
	OPF_INVOKESHORT,               //  21 haiku_vm_MicroKernel__clinit__V
	OPF_INVOKESHORT,               //  22 processing_hardware_arduino_cores_arduino_Print__clinit__V
	OPF_INVOKESHORT,               //  23 processing_hardware_arduino_cores_arduino_Arduino__clinit__V
	OPF_INVOKESHORT,               //  24 java_lang_Throwable_fillInStackTrace_Ljava_lang_Throwable
	OPF_INVOKESHORT,               //  25 java_lang_Thread_stop_V
	OPF_INVOKESHORT,               //  26 main_YLjava_lang_String
	OPF_ARETURN,                   //  27 ARETURN
	OPF_BIPUSH,                    //  28 BIPUSH
	OPF_LDC_S,                     //  29 LDC_S
	OPF_DUP,                       //  30 DUP
	OPF_LDC2_W_L,                  //  31 LDC2_W_L
	OPF_ALOAD_0,                   //  32 ALOAD_0
	OPF_ALOAD_0,                   //  33 ALOAD_1
	OPF_IF_ACMPEQ,                 //  34 IF_ACMPEQ
	OPF_GETFIELD_I,                //  35 GETFIELD_I
	OPF_GETFIELD_L,                //  36 GETFIELD_L
	OPF_GETSTATIC_B,               //  37 GETSTATIC_B
	OPF_GETSTATIC_L,               //  38 GETSTATIC_L
	OPF_GOTO,                      //  39 GOTO
	OPF_LLOAD_0,                   //  40 LLOAD_0
	OPF_ASTORE_0,                  //  41 ASTORE_1
	OPF_IF_ICMPEQ,                 //  42 IF_ICMPEQ
	OPF_I2B,                       //  43 I2B
	OPF_ICONST_0,                  //  44 ICONST_0
	OPF_ICONST_1,                  //  45 ICONST_1
	OPF_ICONST_M1,                 //  46 ICONST_M1
	OPF_ILOAD_0,                   //  47 ILOAD_0
	OPF_ILOAD_1,                   //  48 ILOAD_1
	OPF_INVOKESPECIAL,             //  49 INVOKESTATIC
	OPF_INVOKEVIRTUAL,             //  50 INVOKEVIRTUAL
	OPF_LADD,                      //  51 LADD
	OPF_NEW,                       //  52 NEW
	OPF_POP,                       //  53 POP
	OPF_PUTFIELD_C,                //  54 PUTFIELD_C
	OPF_PUTFIELD_I,                //  55 PUTFIELD_I
	OPF_PUTFIELD_J,                //  56 PUTFIELD_J
	OPF_PUTFIELD_L,                //  57 PUTFIELD_L
	OPF_PUTSTATIC_B,               //  58 PUTSTATIC_B
	OPF_PUTSTATIC_I,               //  59 PUTSTATIC_I
	OPF_PUTSTATIC_L,               //  60 PUTSTATIC_L
	OPF_RETURN,                    //  61 RETURN
	OPF_SIPUSH,                    //  62 SIPUSH
};
#endif

#if _DEBUG
char bytecodeDesc000[] PROGMEM ="java_lang_Thread_setStateAndSwitch_II";
char bytecodeDesc001[] PROGMEM ="java_lang_Object__init__V";
char bytecodeDesc002[] PROGMEM ="processing_hardware_arduino_cores_arduino_Arduino_delay_JV";
char bytecodeDesc003[] PROGMEM ="processing_hardware_arduino_cores_arduino_Arduino_digitalWrite_IIV";
char bytecodeDesc004[] PROGMEM ="processing_hardware_arduino_cores_arduino_Stream__init__V";
char bytecodeDesc005[] PROGMEM ="java_lang_System_currentTimeMillis_J";
char bytecodeDesc006[] PROGMEM ="processing_hardware_arduino_cores_arduino_ArduinoImpl_pinMode_BBV";
char bytecodeDesc007[] PROGMEM ="processing_hardware_arduino_cores_arduino_Print__init__V";
char bytecodeDesc008[] PROGMEM ="processing_examples__01_Basics_Blink_loop_V";
char bytecodeDesc009[] PROGMEM ="java_lang_Throwable__init__Ljava_lang_String_V";
char bytecodeDesc010[] PROGMEM ="processing_hardware_arduino_cores_arduino_HardwareSerial__init__V";
char bytecodeDesc011[] PROGMEM ="java_lang_OutOfMemoryError__init__Ljava_lang_String_V";
char bytecodeDesc012[] PROGMEM ="java_lang_Error__init__Ljava_lang_String_V";
char bytecodeDesc013[] PROGMEM ="processing_hardware_arduino_cores_arduino_Arduino_pinMode_IIV";
char bytecodeDesc014[] PROGMEM ="java_lang_Thread_currentThread_Ljava_lang_Thread";
char bytecodeDesc015[] PROGMEM ="processing_examples__01_Basics_Blink_setup_V";
char bytecodeDesc016[] PROGMEM ="haiku_vm_MicroKernel_clinitHaikuMagic_V";
char bytecodeDesc017[] PROGMEM ="processing_hardware_arduino_cores_arduino_ArduinoImpl_delay_JV";
char bytecodeDesc018[] PROGMEM ="processing_hardware_arduino_cores_arduino_ArduinoImpl_digitalWrite_BBV";
char bytecodeDesc019[] PROGMEM ="java_lang_Thread_nap_JV";
char bytecodeDesc020[] PROGMEM ="processing_examples__01_Basics_Blink__clinit__V";
char bytecodeDesc021[] PROGMEM ="haiku_vm_MicroKernel__clinit__V";
char bytecodeDesc022[] PROGMEM ="processing_hardware_arduino_cores_arduino_Print__clinit__V";
char bytecodeDesc023[] PROGMEM ="processing_hardware_arduino_cores_arduino_Arduino__clinit__V";
char bytecodeDesc024[] PROGMEM ="java_lang_Throwable_fillInStackTrace_Ljava_lang_Throwable";
char bytecodeDesc025[] PROGMEM ="java_lang_Thread_stop_V";
char bytecodeDesc026[] PROGMEM ="main_YLjava_lang_String";
char bytecodeDesc027[] PROGMEM ="ARETURN";
char bytecodeDesc028[] PROGMEM ="BIPUSH";
char bytecodeDesc029[] PROGMEM ="LDC_S";
char bytecodeDesc030[] PROGMEM ="DUP";
char bytecodeDesc031[] PROGMEM ="LDC2_W_L";
char bytecodeDesc032[] PROGMEM ="ALOAD_0";
char bytecodeDesc033[] PROGMEM ="ALOAD_1";
char bytecodeDesc034[] PROGMEM ="IF_ACMPEQ";
char bytecodeDesc035[] PROGMEM ="GETFIELD_I";
char bytecodeDesc036[] PROGMEM ="GETFIELD_L";
char bytecodeDesc037[] PROGMEM ="GETSTATIC_B";
char bytecodeDesc038[] PROGMEM ="GETSTATIC_L";
char bytecodeDesc039[] PROGMEM ="GOTO";
char bytecodeDesc040[] PROGMEM ="LLOAD_0";
char bytecodeDesc041[] PROGMEM ="ASTORE_1";
char bytecodeDesc042[] PROGMEM ="IF_ICMPEQ";
char bytecodeDesc043[] PROGMEM ="I2B";
char bytecodeDesc044[] PROGMEM ="ICONST_0";
char bytecodeDesc045[] PROGMEM ="ICONST_1";
char bytecodeDesc046[] PROGMEM ="ICONST_M1";
char bytecodeDesc047[] PROGMEM ="ILOAD_0";
char bytecodeDesc048[] PROGMEM ="ILOAD_1";
char bytecodeDesc049[] PROGMEM ="INVOKESTATIC";
char bytecodeDesc050[] PROGMEM ="INVOKEVIRTUAL";
char bytecodeDesc051[] PROGMEM ="LADD";
char bytecodeDesc052[] PROGMEM ="NEW";
char bytecodeDesc053[] PROGMEM ="POP";
char bytecodeDesc054[] PROGMEM ="PUTFIELD_C";
char bytecodeDesc055[] PROGMEM ="PUTFIELD_I";
char bytecodeDesc056[] PROGMEM ="PUTFIELD_J";
char bytecodeDesc057[] PROGMEM ="PUTFIELD_L";
char bytecodeDesc058[] PROGMEM ="PUTSTATIC_B";
char bytecodeDesc059[] PROGMEM ="PUTSTATIC_I";
char bytecodeDesc060[] PROGMEM ="PUTSTATIC_L";
char bytecodeDesc061[] PROGMEM ="RETURN";
char bytecodeDesc062[] PROGMEM ="SIPUSH";
char bytecodeDesc999[] PROGMEM ="unused";

const char* bytecodeDesc[] PROGMEM = {
	bytecodeDesc000,
	bytecodeDesc001,
	bytecodeDesc002,
	bytecodeDesc003,
	bytecodeDesc004,
	bytecodeDesc005,
	bytecodeDesc006,
	bytecodeDesc007,
	bytecodeDesc008,
	bytecodeDesc009,
	bytecodeDesc010,
	bytecodeDesc011,
	bytecodeDesc012,
	bytecodeDesc013,
	bytecodeDesc014,
	bytecodeDesc015,
	bytecodeDesc016,
	bytecodeDesc017,
	bytecodeDesc018,
	bytecodeDesc019,
	bytecodeDesc020,
	bytecodeDesc021,
	bytecodeDesc022,
	bytecodeDesc023,
	bytecodeDesc024,
	bytecodeDesc025,
	bytecodeDesc026,
	bytecodeDesc027,
	bytecodeDesc028,
	bytecodeDesc029,
	bytecodeDesc030,
	bytecodeDesc031,
	bytecodeDesc032,
	bytecodeDesc033,
	bytecodeDesc034,
	bytecodeDesc035,
	bytecodeDesc036,
	bytecodeDesc037,
	bytecodeDesc038,
	bytecodeDesc039,
	bytecodeDesc040,
	bytecodeDesc041,
	bytecodeDesc042,
	bytecodeDesc043,
	bytecodeDesc044,
	bytecodeDesc045,
	bytecodeDesc046,
	bytecodeDesc047,
	bytecodeDesc048,
	bytecodeDesc049,
	bytecodeDesc050,
	bytecodeDesc051,
	bytecodeDesc052,
	bytecodeDesc053,
	bytecodeDesc054,
	bytecodeDesc055,
	bytecodeDesc056,
	bytecodeDesc057,
	bytecodeDesc058,
	bytecodeDesc059,
	bytecodeDesc060,
	bytecodeDesc061,
	bytecodeDesc062,
	bytecodeDesc999,//063
	bytecodeDesc999,//064
	bytecodeDesc999,//065
	bytecodeDesc999,//066
	bytecodeDesc999,//067
	bytecodeDesc999,//068
	bytecodeDesc999,//069
	bytecodeDesc999,//070
	bytecodeDesc999,//071
	bytecodeDesc999,//072
	bytecodeDesc999,//073
	bytecodeDesc999,//074
	bytecodeDesc999,//075
	bytecodeDesc999,//076
	bytecodeDesc999,//077
	bytecodeDesc999,//078
	bytecodeDesc999,//079
	bytecodeDesc999,//080
	bytecodeDesc999,//081
	bytecodeDesc999,//082
	bytecodeDesc999,//083
	bytecodeDesc999,//084
	bytecodeDesc999,//085
	bytecodeDesc999,//086
	bytecodeDesc999,//087
	bytecodeDesc999,//088
	bytecodeDesc999,//089
	bytecodeDesc999,//090
	bytecodeDesc999,//091
	bytecodeDesc999,//092
	bytecodeDesc999,//093
	bytecodeDesc999,//094
	bytecodeDesc999,//095
	bytecodeDesc999,//096
	bytecodeDesc999,//097
	bytecodeDesc999,//098
	bytecodeDesc999,//099
	bytecodeDesc999,//100
	bytecodeDesc999,//101
	bytecodeDesc999,//102
	bytecodeDesc999,//103
	bytecodeDesc999,//104
	bytecodeDesc999,//105
	bytecodeDesc999,//106
	bytecodeDesc999,//107
	bytecodeDesc999,//108
	bytecodeDesc999,//109
	bytecodeDesc999,//110
	bytecodeDesc999,//111
	bytecodeDesc999,//112
	bytecodeDesc999,//113
	bytecodeDesc999,//114
	bytecodeDesc999,//115
	bytecodeDesc999,//116
	bytecodeDesc999,//117
	bytecodeDesc999,//118
	bytecodeDesc999,//119
	bytecodeDesc999,//120
	bytecodeDesc999,//121
	bytecodeDesc999,//122
	bytecodeDesc999,//123
	bytecodeDesc999,//124
	bytecodeDesc999,//125
	bytecodeDesc999,//126
	bytecodeDesc999,//127
	bytecodeDesc999,//128
	bytecodeDesc999,//129
	bytecodeDesc999,//130
	bytecodeDesc999,//131
	bytecodeDesc999,//132
	bytecodeDesc999,//133
	bytecodeDesc999,//134
	bytecodeDesc999,//135
	bytecodeDesc999,//136
	bytecodeDesc999,//137
	bytecodeDesc999,//138
	bytecodeDesc999,//139
	bytecodeDesc999,//140
	bytecodeDesc999,//141
	bytecodeDesc999,//142
	bytecodeDesc999,//143
	bytecodeDesc999,//144
	bytecodeDesc999,//145
	bytecodeDesc999,//146
	bytecodeDesc999,//147
	bytecodeDesc999,//148
	bytecodeDesc999,//149
	bytecodeDesc999,//150
	bytecodeDesc999,//151
	bytecodeDesc999,//152
	bytecodeDesc999,//153
	bytecodeDesc999,//154
	bytecodeDesc999,//155
	bytecodeDesc999,//156
	bytecodeDesc999,//157
	bytecodeDesc999,//158
	bytecodeDesc999,//159
	bytecodeDesc999,//160
	bytecodeDesc999,//161
	bytecodeDesc999,//162
	bytecodeDesc999,//163
	bytecodeDesc999,//164
	bytecodeDesc999,//165
	bytecodeDesc999,//166
	bytecodeDesc999,//167
	bytecodeDesc999,//168
	bytecodeDesc999,//169
	bytecodeDesc999,//170
	bytecodeDesc999,//171
	bytecodeDesc999,//172
	bytecodeDesc999,//173
	bytecodeDesc999,//174
	bytecodeDesc999,//175
	bytecodeDesc999,//176
	bytecodeDesc999,//177
	bytecodeDesc999,//178
	bytecodeDesc999,//179
	bytecodeDesc999,//180
	bytecodeDesc999,//181
	bytecodeDesc999,//182
	bytecodeDesc999,//183
	bytecodeDesc999,//184
	bytecodeDesc999,//185
	bytecodeDesc999,//186
	bytecodeDesc999,//187
	bytecodeDesc999,//188
	bytecodeDesc999,//189
	bytecodeDesc999,//190
	bytecodeDesc999,//191
	bytecodeDesc999,//192
	bytecodeDesc999,//193
	bytecodeDesc999,//194
	bytecodeDesc999,//195
	bytecodeDesc999,//196
	bytecodeDesc999,//197
	bytecodeDesc999,//198
	bytecodeDesc999,//199
	bytecodeDesc999,//200
	bytecodeDesc999,//201
	bytecodeDesc999,//202
	bytecodeDesc999,//203
	bytecodeDesc999,//204
	bytecodeDesc999,//205
	bytecodeDesc999,//206
	bytecodeDesc999,//207
	bytecodeDesc999,//208
	bytecodeDesc999,//209
	bytecodeDesc999,//210
	bytecodeDesc999,//211
	bytecodeDesc999,//212
	bytecodeDesc999,//213
	bytecodeDesc999,//214
	bytecodeDesc999,//215
	bytecodeDesc999,//216
	bytecodeDesc999,//217
	bytecodeDesc999,//218
	bytecodeDesc999,//219
	bytecodeDesc999,//220
	bytecodeDesc999,//221
	bytecodeDesc999,//222
	bytecodeDesc999,//223
	bytecodeDesc999,//224
	bytecodeDesc999,//225
	bytecodeDesc999,//226
	bytecodeDesc999,//227
	bytecodeDesc999,//228
	bytecodeDesc999,//229
	bytecodeDesc999,//230
	bytecodeDesc999,//231
	bytecodeDesc999,//232
	bytecodeDesc999,//233
	bytecodeDesc999,//234
	bytecodeDesc999,//235
	bytecodeDesc999,//236
	bytecodeDesc999,//237
	bytecodeDesc999,//238
	bytecodeDesc999,//239
	bytecodeDesc999,//240
	bytecodeDesc999,//241
	bytecodeDesc999,//242
	bytecodeDesc999,//243
	bytecodeDesc999,//244
	bytecodeDesc999,//245
	bytecodeDesc999,//246
	bytecodeDesc999,//247
	bytecodeDesc999,//248
	bytecodeDesc999,//249
	bytecodeDesc999,//250
	bytecodeDesc999,//251
	bytecodeDesc999,//252
	bytecodeDesc999,//253
	bytecodeDesc999,//254
	bytecodeDesc999,//255
};
#endif


#include "haikuJava.h"


const ldc_jstring_t	Const0000 PROGMEM =  {16,	{'O','u','t','O','f','M','e','m','o','r','y','E','r','r','o','r'}};
const ldc_jlong_t	Const0001 PROGMEM =  {LONG_CNSTR(1000L)}; //0x00000000000003e8L


statics_t allStatics;
//jobject:
//	0	haiku_vm_MicroKernel_outOfMemoryError; gets=0 puts=1 native=0
//	1	java_lang_Thread_currentThread; gets=4 puts=1 native=0
//	2	processing_hardware_arduino_cores_arduino_Arduino_Serial; gets=0 puts=1 native=0
//jbyte:
//	0	processing_examples__01_Basics_Blink_ledPin; gets=3 puts=1 native=0
//jchar:
//jdouble:
//jfloat:
//jint:
//	0	processing_hardware_arduino_cores_arduino_Print_write_error; gets=0 puts=1 native=0
//jlong:
//jshort:
//jboolean:



Exception_t	exceptionTable[] PROGMEM = {
	{&main_YLjava_lang_String.op0, &main_YLjava_lang_String.op15, &main_YLjava_lang_String.op15, &java_lang_Throwable__class},
	{NULL, NULL, NULL, NULL}
};

#if _DEBUG || NO_MICRO
const char *	functionDesc[] PROGMEM = {
	/*   0   11 */ "java_lang_Thread_setStateAndSwitch_II",
	/*   1   11 */ "java_lang_Object__init__V",
	/*   2    9 */ "processing_hardware_arduino_cores_arduino_Arduino_delay_JV",
	/*   3    9 */ "processing_hardware_arduino_cores_arduino_Arduino_digitalWrite_IIV",
	/*   4    6 */ "processing_hardware_arduino_cores_arduino_Stream__init__V",
	/*   5    6 */ "java_lang_System_currentTimeMillis_J",
	/*   6    6 */ "processing_hardware_arduino_cores_arduino_ArduinoImpl_pinMode_BBV",
	/*   7    6 */ "processing_hardware_arduino_cores_arduino_Print__init__V",
	/*   8    6 */ "processing_examples__01_Basics_Blink_loop_V",
	/*   9    6 */ "java_lang_Throwable__init__Ljava_lang_String_V",
	/*  10    6 */ "processing_hardware_arduino_cores_arduino_HardwareSerial__init__V",
	/*  11    6 */ "java_lang_OutOfMemoryError__init__Ljava_lang_String_V",
	/*  12    6 */ "java_lang_Error__init__Ljava_lang_String_V",
	/*  13    6 */ "processing_hardware_arduino_cores_arduino_Arduino_pinMode_IIV",
	/*  14    6 */ "java_lang_Thread_currentThread_Ljava_lang_Thread",
	/*  15    6 */ "processing_examples__01_Basics_Blink_setup_V",
	/*  16    6 */ "haiku_vm_MicroKernel_clinitHaikuMagic_V",
	/*  17    5 */ "processing_hardware_arduino_cores_arduino_ArduinoImpl_delay_JV",
	/*  18    5 */ "processing_hardware_arduino_cores_arduino_ArduinoImpl_digitalWrite_BBV",
	/*  19    5 */ "java_lang_Thread_nap_JV",
	/*  20    1 */ "processing_examples__01_Basics_Blink__clinit__V",
	/*  21    1 */ "haiku_vm_MicroKernel__clinit__V",
	/*  22    1 */ "processing_hardware_arduino_cores_arduino_Print__clinit__V",
	/*  23    1 */ "processing_hardware_arduino_cores_arduino_Arduino__clinit__V",
	/*  24    1 */ "java_lang_Throwable_fillInStackTrace_Ljava_lang_Throwable",
	/*  25    1 */ "java_lang_Thread_stop_V",
	/*  26    1 */ "main_YLjava_lang_String",
};
#endif

//Needed for Exception unwinding
ByteCode *	functionTable[] PROGMEM = {
	/*   0   11 */ (ByteCode *)&java_lang_Thread_setStateAndSwitch_II,
	/*   1   11 */ (ByteCode *)&java_lang_Object__init__V,
	/*   2    9 */ (ByteCode *)&processing_hardware_arduino_cores_arduino_Arduino_delay_JV,
	/*   3    9 */ (ByteCode *)&processing_hardware_arduino_cores_arduino_Arduino_digitalWrite_IIV,
	/*   4    6 */ (ByteCode *)&processing_hardware_arduino_cores_arduino_Stream__init__V,
	/*   5    6 */ (ByteCode *)&java_lang_System_currentTimeMillis_J,
	/*   6    6 */ (ByteCode *)&processing_hardware_arduino_cores_arduino_ArduinoImpl_pinMode_BBV,
	/*   7    6 */ (ByteCode *)&processing_hardware_arduino_cores_arduino_Print__init__V,
	/*   8    6 */ (ByteCode *)&processing_examples__01_Basics_Blink_loop_V,
	/*   9    6 */ (ByteCode *)&java_lang_Throwable__init__Ljava_lang_String_V,
	/*  10    6 */ (ByteCode *)&processing_hardware_arduino_cores_arduino_HardwareSerial__init__V,
	/*  11    6 */ (ByteCode *)&java_lang_OutOfMemoryError__init__Ljava_lang_String_V,
	/*  12    6 */ (ByteCode *)&java_lang_Error__init__Ljava_lang_String_V,
	/*  13    6 */ (ByteCode *)&processing_hardware_arduino_cores_arduino_Arduino_pinMode_IIV,
	/*  14    6 */ (ByteCode *)&java_lang_Thread_currentThread_Ljava_lang_Thread,
	/*  15    6 */ (ByteCode *)&processing_examples__01_Basics_Blink_setup_V,
	/*  16    6 */ (ByteCode *)&haiku_vm_MicroKernel_clinitHaikuMagic_V,
	/*  17    5 */ (ByteCode *)&processing_hardware_arduino_cores_arduino_ArduinoImpl_delay_JV,
	/*  18    5 */ (ByteCode *)&processing_hardware_arduino_cores_arduino_ArduinoImpl_digitalWrite_BBV,
	/*  19    5 */ (ByteCode *)&java_lang_Thread_nap_JV,
	/*  20    1 */ (ByteCode *)&processing_examples__01_Basics_Blink__clinit__V,
	/*  21    1 */ (ByteCode *)&haiku_vm_MicroKernel__clinit__V,
	/*  22    1 */ (ByteCode *)&processing_hardware_arduino_cores_arduino_Print__clinit__V,
	/*  23    1 */ (ByteCode *)&processing_hardware_arduino_cores_arduino_Arduino__clinit__V,
	/*  24    1 */ (ByteCode *)&java_lang_Throwable_fillInStackTrace_Ljava_lang_Throwable,
	/*  25    1 */ (ByteCode *)&java_lang_Thread_stop_V,
	/*  26    1 */ (ByteCode *)&main_YLjava_lang_String,
	NULL};

#if _DEBUG || NO_MICRO

char classDesc000[] PROGMEM ="free block";
char classDesc001[] PROGMEM ="haiku.avr.lib.arduino.HaikuMicroKernel4ArduinoIDE";
char classDesc002[] PROGMEM ="haiku.vm.MicroKernel";
char classDesc003[] PROGMEM ="java.lang.Error";
char classDesc004[] PROGMEM ="java.lang.Object";
char classDesc005[] PROGMEM ="java.lang.OutOfMemoryError";
char classDesc006[] PROGMEM ="java.lang.String";
char classDesc007[] PROGMEM ="java.lang.System";
char classDesc008[] PROGMEM ="java.lang.Thread";
char classDesc009[] PROGMEM ="java.lang.Throwable";
char classDesc010[] PROGMEM ="processing.examples._01_Basics.Blink";
char classDesc011[] PROGMEM ="processing.hardware.arduino.cores.arduino.Arduino";
char classDesc012[] PROGMEM ="processing.hardware.arduino.cores.arduino.ArduinoImpl";
char classDesc013[] PROGMEM ="processing.hardware.arduino.cores.arduino.HardwareSerial";
char classDesc014[] PROGMEM ="processing.hardware.arduino.cores.arduino.Print";
char classDesc015[] PROGMEM ="processing.hardware.arduino.cores.arduino.Stream";

const char *	classDesc[] PROGMEM = {
	classDesc001,
	classDesc002,
	classDesc003,
	classDesc004,
	classDesc005,
	classDesc006,
	classDesc007,
	classDesc008,
	classDesc009,
	classDesc010,
	classDesc011,
	classDesc012,
	classDesc013,
	classDesc014,
	classDesc015,
	classDesc000,
};

//Needed for Heap and Stack debugging
const jclass    classTable[] PROGMEM = {
	(jclass)&haiku_avr_lib_arduino_HaikuMicroKernel4ArduinoIDE__class,
	(jclass)&haiku_vm_MicroKernel__class,
	(jclass)&java_lang_Error__class,
	(jclass)&java_lang_Object__class,
	(jclass)&java_lang_OutOfMemoryError__class,
	(jclass)&java_lang_String__class,
	(jclass)&java_lang_System__class,
	(jclass)&java_lang_Thread__class,
	(jclass)&java_lang_Throwable__class,
	(jclass)&processing_examples__01_Basics_Blink__class,
	(jclass)&processing_hardware_arduino_cores_arduino_Arduino__class,
	(jclass)&processing_hardware_arduino_cores_arduino_ArduinoImpl__class,
	(jclass)&processing_hardware_arduino_cores_arduino_HardwareSerial__class,
	(jclass)&processing_hardware_arduino_cores_arduino_Print__class,
	(jclass)&processing_hardware_arduino_cores_arduino_Stream__class,
	NULL
};
#endif
