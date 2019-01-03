#include <jni.h>

int result3;

/////////////////////////////////////////////////


/**
 * Automatic generated JNI template
 * inc(I)V
 */
void Java_arduino_tutorial_NativeCVariableSimple_inc(JNIEnv *env, jobject obj, jint arg1) {
    result3+=arg1;
}

/**
 * Automatic generated JNI wrapper
 * inc(I)V
 */
void native_arduino_tutorial_NativeCVariableSimple_inc_IV(void) {
    pushTop();	// Save variable top onto stack.
    {
        jint	arg1 = pop()->i;
        jobject	 obj = NULL;
        JNIEnv  *env = NULL; // not used in HaikuVM
        Java_arduino_tutorial_NativeCVariableSimple_inc(env, obj, arg1);
    }
    popTop();
}

/////////////////////////////////////////////////
