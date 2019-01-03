// To access arduino clibs, it's important to have an extension of cpp.

#include <jni.h>

#if !defined(HAIKU_TimerInterrupt) && AVR
#include <Arduino.h>

void debug(int marker) {
	for(int i=0; i<2; i++) {
		for(int j=0; j<9; j++) {
			Serial.print(marker); Serial.print(' ');
		}
		Serial.println(marker);
	}
}

/////////////////////////////////////////////////


/*
 * Class:     haiku.avr.lib.arduino.HaikuMicroKernelLib
 * Method:    init
 * Signature: ()V
 */
JNIEXPORT void Java_haiku_avr_lib_arduino_HaikuMicroKernelLib_init
  (JNIEnv *env, jclass obj)
{
    init();
#if defined(USBCON)
    USBDevice.attach();
#endif
    Serial.begin(57600);
}

/*
 * Proprietary HaikuVM stack to JNI interface function.
 * DO NOT EDIT THIS FUNCTION – it is machine generated.
 *
 * Class:     haiku.avr.lib.arduino.HaikuMicroKernelLib
 * Method:    init
 * Signature: ()V
 */
JNIEXPORT void native_haiku_avr_lib_arduino_HaikuMicroKernelLib_init_V(void) {
    pushTop();    // Save variable top onto stack.
    {
        jclass     obj = NULL;
        JNIEnv     *env = NULL; // not used in HaikuVM
        Java_haiku_avr_lib_arduino_HaikuMicroKernelLib_init(env, obj);
    }
    popTop();
}

/////////////////////////////////////////////////


/*
 * Class:     haiku.avr.lib.arduino.HaikuMicroKernelLib
 * Method:    read
 * Signature: ()I
 */
JNIEXPORT jint Java_haiku_avr_lib_arduino_HaikuMicroKernelLib_read
  (JNIEnv *env, jclass obj)
{
    return Serial.read();
}

/*
 * Proprietary HaikuVM stack to JNI interface function.
 * DO NOT EDIT THIS FUNCTION – it is machine generated.
 *
 * Class:     haiku.avr.lib.arduino.HaikuMicroKernelLib
 * Method:    read
 * Signature: ()I
 */
JNIEXPORT void native_haiku_avr_lib_arduino_HaikuMicroKernelLib_read_I(void) {
    pushTop();    // Save variable top onto stack.
    {
        jclass     obj = NULL;
        JNIEnv     *env = NULL; // not used in HaikuVM
        top.s1.i = Java_haiku_avr_lib_arduino_HaikuMicroKernelLib_read(env, obj);
    }
    // Variable top holds the return value.
}

/////////////////////////////////////////////////


/*
 * Class:     haiku.avr.lib.arduino.HaikuMicroKernelLib
 * Method:    available
 * Signature: ()I
 */
JNIEXPORT jint Java_haiku_avr_lib_arduino_HaikuMicroKernelLib_available
  (JNIEnv *env, jclass obj)
{
    return Serial.available();
}

/*
 * Proprietary HaikuVM stack to JNI interface function.
 * DO NOT EDIT THIS FUNCTION – it is machine generated.
 *
 * Class:     haiku.avr.lib.arduino.HaikuMicroKernelLib
 * Method:    available
 * Signature: ()I
 */
JNIEXPORT void native_haiku_avr_lib_arduino_HaikuMicroKernelLib_available_I(void) {
    pushTop();    // Save variable top onto stack.
    {
        jclass     obj = NULL;
        JNIEnv     *env = NULL; // not used in HaikuVM
        top.s1.i = Java_haiku_avr_lib_arduino_HaikuMicroKernelLib_available(env, obj);
    }
    // Variable top holds the return value.
}

/////////////////////////////////////////////////


/*
 * Class:     haiku.avr.lib.arduino.HaikuMicroKernelLib
 * Method:    write
 * Signature: (I)V
 */
JNIEXPORT void Java_haiku_avr_lib_arduino_HaikuMicroKernelLib_write
  (JNIEnv *env, jclass obj, jint arg1)
{
    Serial.write((uint8_t) arg1);
}

/*
 * Proprietary HaikuVM stack to JNI interface function.
 * DO NOT EDIT THIS FUNCTION – it is machine generated.
 *
 * Class:     haiku.avr.lib.arduino.HaikuMicroKernelLib
 * Method:    write
 * Signature: (I)V
 */
JNIEXPORT void native_haiku_avr_lib_arduino_HaikuMicroKernelLib_write_IV(void) {
    pushTop();    // Save variable top onto stack.
    {
        jint    arg1 = pop()->i;
        jclass     obj = NULL;
        JNIEnv     *env = NULL; // not used in HaikuVM
        Java_haiku_avr_lib_arduino_HaikuMicroKernelLib_write(env, obj, arg1);
    }
    popTop();
}

/////////////////////////////////////////////////

/*
 * Class:     haiku.avr.lib.arduino.HaikuMicroKernelLib
 * Method:    debug
 * Signature: (I)V
 */
JNIEXPORT void Java_haiku_avr_lib_arduino_HaikuMicroKernelLib_debug
  (JNIEnv *env, jclass obj, jint arg1)
{
    debug(arg1);
}

/*
 * Proprietary HaikuVM stack to JNI interface function.
 * DO NOT EDIT THIS FUNCTION – it is machine generated.
 *
 * Class:     haiku.avr.lib.arduino.HaikuMicroKernelLib
 * Method:    debug
 * Signature: (I)V
 */
JNIEXPORT void native_haiku_avr_lib_arduino_HaikuMicroKernelLib_debug_IV(void) {
    pushTop();    // Save variable top onto stack.
    {
        jint    arg1 = pop()->i;
        jclass     obj = NULL;
        JNIEnv     *env = NULL; // not used in HaikuVM
        Java_haiku_avr_lib_arduino_HaikuMicroKernelLib_debug(env, obj, arg1);
    }
    popTop();
}

/////////////////////////////////////////////////


/*
 * Class:     haiku.avr.lib.arduino.HaikuMicroKernel
 * Method:    init
 * Signature: ()V
 */
JNIEXPORT void Java_haiku_avr_lib_arduino_HaikuMicroKernel_init
  (JNIEnv *env, jclass obj)
{
    init();
#if defined(USBCON)
    USBDevice.attach();
#endif
}

/*
 * Proprietary HaikuVM stack to JNI interface function.
 * DO NOT EDIT THIS FUNCTION – it is machine generated.
 *
 * Class:     haiku.avr.lib.arduino.HaikuMicroKernel
 * Method:    init
 * Signature: ()V
 */
JNIEXPORT void native_haiku_avr_lib_arduino_HaikuMicroKernel_init_V(void) {
    pushTop();    // Save variable top onto stack.
    {
        jclass     obj = NULL;
        JNIEnv     *env = NULL; // not used in HaikuVM
        Java_haiku_avr_lib_arduino_HaikuMicroKernel_init(env, obj);
    }
    popTop();
}

/////////////////////////////////////////////////
#endif
