#include <jni.h>

static double result;

/*
 * Class:     arduino.tutorial.JNIenhanced
 * Method:    add
 * Signature: (IFD)V
 */
JNIEXPORT void Java_arduino_tutorial_JNIenhanced_add
  (JNIEnv *env, jclass obj, jint arg1, jfloat arg2, jdouble arg3)
{
    result = arg1 + TF2FLOAT(arg2) + TD2DOUBLE(arg3);
}

/*
 * Proprietary HaikuVM stack to JNI interface function.
 * DO NOT EDIT THIS FUNCTION – it is machine generated.
 *
 * Class:     arduino.tutorial.JNIenhanced
 * Method:    add
 * Signature: (IFD)V
 */
JNIEXPORT void native_arduino_tutorial_JNIenhanced_add_IFDV(void) {
    pushTop();	// Save variable top onto stack.
    {
        jdouble	arg3 = popp2()->d;
        jfloat	arg2 = pop()->f;
        jint	arg1 = pop()->i;
        jobject	 obj = NULL;
        JNIEnv  *env = NULL; // not used in HaikuVM
        Java_arduino_tutorial_JNIenhanced_add(env, obj, arg1, arg2, arg3);
    }
    popTop();
}

/////////////////////////////////////////////////


/*
 * Class:     arduino.tutorial.JNIenhanced
 * Method:    result
 * Signature: ()D
 */
JNIEXPORT jdouble Java_arduino_tutorial_JNIenhanced_result
  (JNIEnv *env, jclass obj)
{
    return DOUBLE2TD(result);
}

/*
 * Proprietary HaikuVM stack to JNI interface function.
 * DO NOT EDIT THIS FUNCTION – it is machine generated.
 *
 * Class:     arduino.tutorial.JNIenhanced
 * Method:    result
 * Signature: ()D
 */
JNIEXPORT void native_arduino_tutorial_JNIenhanced_result_D(void) {
    pushTop();	// Save variable top onto stack.
    {
        jobject	 obj = NULL;
        JNIEnv  *env = NULL; // not used in HaikuVM
        top.d = Java_arduino_tutorial_JNIenhanced_result(env, obj);
    }
    // Variable top holds the return value. But we have to push the lower half.
    pushTop0();
}

/////////////////////////////////////////////////
