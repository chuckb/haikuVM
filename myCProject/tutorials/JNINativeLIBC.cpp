#if AVR
// Its not the focus of this example but
// Why not try this example in cpp?

#include <jni.h>
#include <math.h>

/////////////////////////////////////////////////

/*
 * Class:     arduino.tutorial.JNINativeLIBC
 * Method:    sin
 * Signature: (D)D
 */
JNIEXPORT jdouble Java_arduino_tutorial_JNINativeLIBC_sin
  (JNIEnv *env, jclass obj, jdouble arg1)
{
    return DOUBLE2TD(sin(TD2DOUBLE(arg1)));
}

/*
 * Proprietary HaikuVM stack to JNI interface function.
 * DO NOT EDIT THIS FUNCTION – it is machine generated.
 *
 * Class:     arduino.tutorial.JNINativeLIBC
 * Method:    sin
 * Signature: (D)D
 */
JNIEXPORT void native_arduino_tutorial_JNINativeLIBC_sin_DD(void) {
    pushTop();    // Save variable top onto stack.
    {
        jdouble    arg1 = popp2()->d;
        jclass     obj = NULL;
        JNIEnv     *env = NULL; // not used in HaikuVM
        top.d = Java_arduino_tutorial_JNINativeLIBC_sin(env, obj, arg1);
    }
    // Variable top holds the return value. But we have to push the lower half.
    pushTop0();
}


/////////////////////////////////////////////////

#endif
