#include <jni.h>

jdouble result4a;
jint result4b;

jint resultArray[3]= { 1, 2, 3 };

typedef struct {
	jint a;
	jdouble b;
} resultStruct_t;

resultStruct_t resultStruct= { 1, 2 };


void NativeCVariableEnhanced_inc(double arg1) {
    // result4a += arg1;
    result4a=DOUBLE2TD(TD2DOUBLE(result4a)+arg1);
    result4b=TD2DOUBLE(result4a);
    resultArray[1]=result4b;
}
