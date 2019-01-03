package arduino.tutorial;

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
 * Found here
 *  http://journals.ecs.soton.ac.uk/java/tutorial/native1.1/implementing/field.html
 * and adapted for HaikuVM from
 *  genom2
 */

/**
 * I found this and adapted it for HaikuVM.
 *  
 * THe corresponding JNI C code is in myCProject:tutorials/JNI_FieldAccess.c
 *  
 *@see http://journals.ecs.soton.ac.uk/java/tutorial/native1.1/implementing/field.html
 *@see http://haiku-vm.sourceforge.net/#[[JNI%20field%20access]]
 */
class JNI_FieldAccess {
    static int si;
    String s;

    private native void accessFields();

    public static void main(String args[]) {
        JNI_FieldAccess c = new JNI_FieldAccess();
        JNI_FieldAccess.si = 100;
        c.s = "abc";
        System.out.println("Initial values:");
        System.out.println("  FieldAccess.si = " + JNI_FieldAccess.si);
        System.out.println("  c.s = \"" + c.s + "\"");
        c.accessFields();
        System.out.println("After the C function changed the values:");
        System.out.println("  FieldAccess.si = " + JNI_FieldAccess.si);
        System.out.println("  c.s = \"" + c.s + "\"");
    }

    static {
//        System.loadLibrary("MyImpOfFieldAccess");
    }
}
