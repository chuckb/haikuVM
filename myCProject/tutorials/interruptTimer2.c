#include <jni.h>
#if AVR

#include <avr/interrupt.h>

volatile static jlong timer2_interrupts = 0;

#if defined(HAIKU_TimerInterrupt) && ! (HAIKU_TimerInterrupt==TIMER2_OVF_vect)
ISR(TIMER2_OVF_vect) {
    timer2_interrupts ++;
}
#endif

/**
 * Automatic generated JNI template
 * getTimer2()J
 */
JNIEXPORT jlong Java_arduino_tutorial_InterruptTimer2_getTimer2(JNIEnv *env, jobject obj) {
    jlong value;
    uint8_t oldSREG = SREG;
    cli(); // we don't want to be desturbed by an interrupt
    value = timer2_interrupts;
    SREG = oldSREG; // restore interruptable state again.
    return value;
}

/**
 * Automatic generated JNI wrapper
 * getTimer2()J
 */
JNIEXPORT void native_arduino_tutorial_InterruptTimer2_getTimer2_J(void) {
	pushTop();	// Save variable top onto stack.
	{
		jobject	obj = NULL;
		JNIEnv *env = NULL;
		top.j = Java_arduino_tutorial_InterruptTimer2_getTimer2(env, obj);
	}
	// Variable top holds the return value. But we have to push the lower half.
	pushTop0();
}
#endif
