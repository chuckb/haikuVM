// To access arduino clibs, it's important to have an extension of cpp.

#include <jni.h>

#if !defined(HAIKU_TimerInterrupt) && AVR
#include <Arduino.h>

/////////////////////////////////////////////////

/*
 * Class:     haiku.avr.lib.arduino.HaikuMicroKernelProcessing
 * Method:    init
 * Signature: ()V
 */
JNIEXPORT void Java_haiku_avr_lib_arduino_HaikuMicroKernelProcessing_init
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
 * Class:     haiku.avr.lib.arduino.HaikuMicroKernelProcessing
 * Method:    init
 * Signature: ()V
 */
JNIEXPORT void native_haiku_avr_lib_arduino_HaikuMicroKernelProcessing_init_V(void) {
    pushTop();    // Save variable top onto stack.
    {
        jclass     obj = NULL;
        JNIEnv     *env = NULL; // not used in HaikuVM
        Java_haiku_avr_lib_arduino_HaikuMicroKernelProcessing_init(env, obj);
    }
    popTop();
}

#endif
