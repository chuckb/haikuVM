package arduino.tutorial.beyondjni;

import haiku.arduino.processing.Arduino;
import haiku.vm.NativeCppFunction;

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
 */

/**
 * I found this and adapted it for HaikuVM.
 *  
 * Similar to {@link arduino.tutorial.JNI_FieldAccess} but this time using annotations
 * for writing C code inline in Java source.
 *
 *@author genom2
 *  
 *@see http://journals.ecs.soton.ac.uk/java/tutorial/native1.1/implementing/field.html
 *@see http://haiku-vm.sourceforge.net/#[[Tutorial%20JNI]]
 */
class FieldAccess {
    static int si;
    String s;

    @NativeCppFunction(cImpl=
    	"//  jclass cls = (*env)->GetObjectClass(env, obj);\r\n" + 
    	"  #define cls arduino_tutorial_beyondjni_FieldAccess\r\n" + 
    	"  jfieldID fid;\r\n" + 
    	"  jstring jstr;\r\n" + 
    	"  char *str;\r\n" + 
    	"  jint si;\r\n" + 
    	"\r\n" + 
    	"  printf(\"In C:\\n\");\r\n" + 
    	"\r\n" + 
    	"  fid = (*env)->GetStaticFieldID(env, cls, si, \"I\");\r\n" + 
    	"  if (fid == 0)\r\n" + 
    	"    return;\r\n" + 
    	"  si = (*env)->GetStaticIntField(env, cls, fid);\r\n" + 
    	"  printf(\"  FieldAccess.si = %d\\n\", si);\r\n" + 
    	"  (*env)->SetStaticIntField(env, cls, fid, 200);\r\n" + 
    	"\r\n" + 
    	"  fid = (*env)->GetFieldID(env, cls, s, \"Ljava/lang/String;\");\r\n" + 
    	"  if (fid == 0)\r\n" + 
    	"    return;\r\n" + 
    	"  jstr = (jstring)((*env)->GetObjectField(env, obj, fid));\r\n" + 
    	"  str = (*env)->GetStringUTFChars(env, jstr, 0);\r\n" + 
    	"  printf(\"  c.s = \\\"%s\\\"\\n\", str);\r\n" + 
    	"  (*env)->ReleaseStringUTFChars(env, jstr, str);\r\n" + 
    	"\r\n" + 
    	"  jstr = (*env)->NewStringUTF(env, \"123\");\r\n" + 
    	"  (*env)->SetObjectField(env, obj, fid, jstr);\r\n" + 
    	""
    	)
    private native void accessFields();

    public static void main(String args[]) {
        FieldAccess c = new FieldAccess();
        FieldAccess.si = 100;
        c.s = "abc";
        System.out.println("Initial values:");
        System.out.println("  FieldAccess.si = " + FieldAccess.si);
        System.out.println("  c.s = \"" + c.s + "\"");
        c.accessFields();
        System.out.println("After the C function changed the values:");
        System.out.println("  FieldAccess.si = " + FieldAccess.si);
        System.out.println("  c.s = \"" + c.s + "\"");
    }

    static {
//        System.loadLibrary("MyImpOfFieldAccess");
    }
}
