#include <jni.h>

/////////////////////////////////////////////////


/*
 * Class:     java.lang.System
 * Method:    getDataAddress
 * Signature: (Ljava/lang/Object;)I
 */
JNIEXPORT jint Java_arduino_tutorial_JNIsimple_add
  (JNIEnv *env, jclass obj, jint arg1, jint arg2)
{
    return arg1 + arg2;
}

/*
 * Proprietary HaikuVM stack to JNI interface function.
 * DO NOT EDIT THIS FUNCTION – it is machine generated.
 *
 * Class:     java.lang.System
 * Method:    getDataAddress
 * Signature: (Ljava/lang/Object;)I
 */
JNIEXPORT void native_arduino_tutorial_JNIsimple_add_III(void) {
    pushTop();	// Save variable top onto stack.
    {
        jint	arg2 = pop()->i;
        jint	arg1 = pop()->i;
        jobject	obj = NULL;
        JNIEnv *env = NULL;
        top.s1.i = Java_arduino_tutorial_JNIsimple_add(env, obj, arg1, arg2);
    }
    // Variable top holds the return value.
}

/////////////////////////////////////////////////
