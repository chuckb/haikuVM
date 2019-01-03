/*
 * Copyright (c) 1995-1997 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Permission to use, copy, modify, and distribute this software
 * and its documentation for NON-COMMERCIAL purposes and without
 * fee is hereby granted provided that this copyright notice
 * appears in all copies. Please refer to the file "copyright.html"
 * for further important copyright and licensing information.
 *
 * SUN MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. SUN SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 *
 * Adapted to HaikuVM from genom2
#include <stdio.h>
#include <jni.h>
#include "FieldAccess.h"

JNIEXPORT void JNICALL
Java_arduino_tutorial_JNI_FieldAccess_accessFields(JNIEnv *env, jobject obj)
{
  jclass cls = (*env)->GetObjectClass(env, obj);
  jfieldID fid;
  jstring jstr;
  const char *str;
  jint si;

  printf("In C:\n");

  fid = (*env)->GetStaticFieldID(env, cls, "si", "I");
  if (fid == 0)
    return;
  si = (*env)->GetStaticIntField(env, cls, fid);
  printf("  FieldAccess.si = %d\n", si);
  (*env)->SetStaticIntField(env, cls, fid, 200);

  fid = (*env)->GetFieldID(env, cls, "s", "Ljava/lang/String;");
  if (fid == 0)
    return;
  jstr = (*env)->GetObjectField(env, obj, fid);
  str = (*env)->GetStringUTFChars(env, jstr, 0);
  printf("  c.s = \"%s\"\n", str);
  (*env)->ReleaseStringUTFChars(env, jstr, str);

  jstr = (*env)->NewStringUTF(env, "123");
  (*env)->SetObjectField(env, obj, fid, jstr);
}
 */

/*
 * Adapted for HaikuVM from genom2
 * assumes char is 1 byte long: Char = 8
 */
#include <stdio.h>
#include <jni.h>

#include "haikuConfig.h"
#include "haikuByteCodeTypes.h"

#ifdef SIZEOF_arduino_tutorial_JNI_FieldAccess

JNIEXPORT void JNICALL
Java_arduino_tutorial_JNI_FieldAccess_accessFields(JNIEnv *env, jobject obj)
{
//  jclass cls = (*env)->GetObjectClass(env, obj);
  #define cls arduino_tutorial_JNI_FieldAccess
  jfieldID fid;
  jstring jstr;
  char *str;
  jint si;

  printf("In C:\n");

  fid = (*env)->GetStaticFieldID(env, cls, si, "I");
  if (fid == 0)
    return;
  si = (*env)->GetStaticIntField(env, cls, fid);
  printf("  FieldAccess.si = %d\n", si);
  (*env)->SetStaticIntField(env, cls, fid, 200);

  fid = (*env)->GetFieldID(env, cls, s, "Ljava/lang/String;");
  if (fid == 0)
    return;
  jstr = (*env)->GetObjectField(env, obj, fid);
  str = (*env)->GetStringUTFChars(env, jstr, 0);
  printf("  c.s = \"%s\"\n", str);
  (*env)->ReleaseStringUTFChars(env, jstr, str);

  jstr = (*env)->NewStringUTF(env, "123");
  (*env)->SetObjectField(env, obj, fid, jstr);
}

/*
 * Proprietary HaikuVM stack to JNI interface function.
 * DO NOT EDIT THIS FUNCTION – it is machine generated.
 *
 * Class:     arduino.tutorial.JNIfields
 * Method:    add
 * Signature: ()V
 */
JNIEXPORT void native_arduino_tutorial_JNI_FieldAccess_accessFields_V(void) {
    pushTop();    // Save variable top onto stack.
    {
        jobject    obj = pop()->a;
        JNIEnv     *env = (JNIEnv*)&top;
        Java_arduino_tutorial_JNI_FieldAccess_accessFields((JNIEnv*)&env, obj);
    }
    popTop();
}
/////////////////////////////////////////////////
#endif
