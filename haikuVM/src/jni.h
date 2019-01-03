#ifdef __cplusplus
extern "C" {
#endif

#include "haikuJ2C.h"

#ifdef __cplusplus
#define JNIEXPORT extern "C"
#else
#define JNIEXPORT extern
#endif

#define JNICALL

typedef jint jfieldID;

//e.g.: (*(AccessCppFields**) obj)
#define getRealCppSubject(cppClazz, jobj) (*(cppClazz**) jobj)

//  fid = (*env)->GetStaticFieldID(env, cls, "si", "I");
#define PASTE3(a, b, c) a ## b ## c
#define GetStaticFieldID(env, cls, fname, ftype)	s0.i=((char*)(&heapmem.allStatics.statics.PASTE3(cls, _, fname)) - (char*)(&heapmem.allStatics))+0x7fff
#define GetFieldID(env, cls, fname, ftype)			s0.i=offsetof(cls, fname)+0x7fff

  //arduino_tutorial_JNI_FieldAccess_#fid
#define GetStaticIntField(env, cls, fid)			s0.i=*(jint*)(((char*)(&heapmem.allStatics)+fid)-0x7fff)
#define SetStaticIntField(env, cls, fid, value)		s0.i=1; *((jint*)(((char*)(&heapmem.allStatics)+fid)-0x7fff)) = value

#define GetObjectField(env, obj, fid)				s0.v=*(jobject*)(((char*)(obj)+fid)-0x7fff)
#define SetObjectField(env, obj, fid, value)		s0.i=1; *((jobject*)(((char*)(obj)+fid)-0x7fff)) = value


#define GetStringUTFChars(env, jstr, isCopy)		s0.ch=(char*)malloc(jstr->chars->length+1);\
	memcpy((char*)((*env)->s0.str), (char*)(jstr->chars->array), jstr->chars->length);\
    ((char*)((*env)->s0.str))[jstr->chars->length]=0
#define ReleaseStringUTFChars(env, jstr, str)		s0.i=1; free(str)
#define NewStringUTF(env, string)					s0.str=newString(string)


#ifdef __cplusplus
}
#endif
