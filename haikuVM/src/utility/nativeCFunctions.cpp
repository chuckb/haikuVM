#include <jni.h>
#include <math.h>
#include <string.h>

/////////////////////////////////////////////////


#ifndef pinMode
extern "C" void pinMode(char, char);
#endif

/*
 * Class:     processing.hardware.arduino.cores.arduino.ArduinoImpl
 * Method:    pinMode
 * Signature: (BB)V
 */
JNIEXPORT void Java_processing_hardware_arduino_cores_arduino_ArduinoImpl_pinMode
  (JNIEnv *env, jclass obj, jbyte arg1, jbyte arg2)
{
    pinMode(arg1, arg2);
}

/*
 * Proprietary HaikuVM stack to JNI interface function.
 * DO NOT EDIT THIS FUNCTION – it is machine generated.
 * 
 * Class:     processing.hardware.arduino.cores.arduino.ArduinoImpl
 * Method:    pinMode
 * Signature: (BB)V
 */
JNIEXPORT void native_processing_hardware_arduino_cores_arduino_ArduinoImpl_pinMode_BBV(void) {
    pushTop();    // Save variable top onto stack.
    {
        jbyte    arg2 = pop()->b;
        jbyte    arg1 = pop()->b;
        jclass     obj = NULL;
        JNIEnv     *env = NULL; // not used in HaikuVM
        Java_processing_hardware_arduino_cores_arduino_ArduinoImpl_pinMode(env, obj, arg1, arg2);
    }
    popTop();
}

/////////////////////////////////////////////////


#ifndef digitalWrite
extern "C" void digitalWrite(char, char);
#endif

/*
 * Class:     processing.hardware.arduino.cores.arduino.ArduinoImpl
 * Method:    digitalWrite
 * Signature: (BB)V
 */
JNIEXPORT void Java_processing_hardware_arduino_cores_arduino_ArduinoImpl_digitalWrite
  (JNIEnv *env, jclass obj, jbyte arg1, jbyte arg2)
{
    digitalWrite(arg1, arg2);
}

/*
 * Proprietary HaikuVM stack to JNI interface function.
 * DO NOT EDIT THIS FUNCTION – it is machine generated.
 * 
 * Class:     processing.hardware.arduino.cores.arduino.ArduinoImpl
 * Method:    digitalWrite
 * Signature: (BB)V
 */
JNIEXPORT void native_processing_hardware_arduino_cores_arduino_ArduinoImpl_digitalWrite_BBV(void) {
    pushTop();    // Save variable top onto stack.
    {
        jbyte    arg2 = pop()->b;
        jbyte    arg1 = pop()->b;
        jclass     obj = NULL;
        JNIEnv     *env = NULL; // not used in HaikuVM
        Java_processing_hardware_arduino_cores_arduino_ArduinoImpl_digitalWrite(env, obj, arg1, arg2);
    }
    popTop();
}

/////////////////////////////////////////////////


