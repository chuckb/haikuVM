#include <jni.h>

jint result4bCpp;

class ResultClass {
public:
	jint c;
	jdouble d;
	long getD();
    static long getC();
	void set(int);
};

long ResultClass::getD() {
	return c;
}

long ResultClass::getC() {
	return 2345678;
}

void ResultClass::set(int a) {
	c=a;
}

ResultClass resultClass;

void NativeCVariableEnhanced_set() {
	resultClass.c=5;
	resultClass.d=6;
	//printf("resultClass.GetC()=%ld\n", resultClass.getD());
}

long getB() {
	return 1234567;
}

/*
 * @NativeCFunction
 * native long get();
 */
JNIEXPORT jlong Java_processing_hardware_arduino_cores_arduino_ResultClass_getB
  (JNIEnv *env, jclass obj)
{
    return getB();
}

/*
 * @NativeCppMethod
 * static native long get();
 */
JNIEXPORT jlong Java_processing_hardware_arduino_cores_arduino_ResultClass_getC
  (JNIEnv *env, jclass obj)
{
    return ResultClass::getC();
}

/*
 * @NativeCppMethod
 * native long get();
 */
JNIEXPORT jlong Java_processing_hardware_arduino_cores_arduino_ResultClass_getD
  (JNIEnv *env, jobject obj)
{
    return 0; //((ResultClass*)getCObject(obj))->getD();
}
