package haikuvm.pc.tools.aot;

import haikuvm.pc.tools.BC2IDX;
import haikuvm.pc.tools.CIDX;
import haikuvm.pc.tools.ClassTable;
import haikuvm.pc.tools.Clinit;
import haikuvm.pc.tools.Closure;
import haikuvm.pc.tools.CollectedIncludes;
import haikuvm.pc.tools.Condition;
import haikuvm.pc.tools.HaikuVM;
import haikuvm.pc.tools.Haikufy;
import haikuvm.pc.tools.MethInfo;
import haikuvm.pc.tools.Msg2Meth;
import haikuvm.pc.tools.ObjSpace;
import haikuvm.pc.tools.Preprocess;
import haikuvm.pc.tools.Verbose;
import haikuvm.pc.tools.haikuc.HaikuClasses2H;
import haikuvm.pc.tools.haikuc.HaikuByteCode2H;
import haikuvm.pc.tools.haikuc.HaikuByteCodeTypes2H;
import haikuvm.pc.tools.haikuc.HaikuDefs;
import haikuvm.pc.tools.haikuc.HaikuJava2File;
import haikuvm.pc.tools.haikuc.PrintOnChange;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Formatter;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import org.apache.bcel.Constants;
import org.apache.bcel.classfile.Attribute;
import org.apache.bcel.classfile.ClassFormatException;
import org.apache.bcel.classfile.Code;
import org.apache.bcel.classfile.CodeException;
import org.apache.bcel.classfile.Constant;
import org.apache.bcel.classfile.ConstantClass;
import org.apache.bcel.classfile.ConstantDouble;
import org.apache.bcel.classfile.ConstantFloat;
import org.apache.bcel.classfile.ConstantInteger;
import org.apache.bcel.classfile.ConstantLong;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.ConstantString;
import org.apache.bcel.classfile.ConstantUtf8;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.LocalVariable;
import org.apache.bcel.classfile.LocalVariableTable;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.classfile.Unknown;
import org.apache.bcel.generic.Type;

/**
 * Bob Genom 16.11.2014
 *
 * Code produced in version 1
 *

/**
public static void main(String[] arg0)
Code(max_stack = 2, max_locals = 2, code_length = 36)
* /
#undef  JMETHOD
#define JMETHOD main_YLjava_lang_String
const           main_YLjava_lang_String_t JMETHOD PROGMEM ={
2+(2)+2,    1,    1,    // MaxLocals+(lsp+pc)+MaxStack, purLocals, purParams

OP_INVOKESHORT_haiku_vm_MicroKernel_clinitHaikuMagic_V,                // 0:    invokestatic    haiku.pc.lib.HaikuMicroKernel.clinitHaikuMagic ()V (11)
OP_INVOKESHORT_haiku_pc_lib_HaikuMicroKernel_init_V,                   // 3:    invokestatic    haiku.pc.lib.HaikuMicroKernel.init ()V (12)
OP_ICONST_0,                                                           // 6:    iconst_0
OP_ICONST_0,                                                           // 7:    iconst_0
OP_INVOKESHORT_haiku_vm_MicroKernel_panic_IIV,                         // 8:    invokestatic    haiku.pc.lib.HaikuMicroKernel.panic (II)V (13)
OP_ALOAD_0,                                                            // 11:   aload_0
OP_INVOKESHORT_arduino_tutorial_JNI_FieldAccess_main_YLjava_lang_String_V,
                                                                       // 12:   invokestatic    haiku.vm.HaikuMagic.main ([Ljava/lang/String;)V (14)
OP_GOTO,             TARGET(29),                                       // 15:   goto        #29
OP_ASTORE_1,                                                           // 18:   astore_1
OP_GETSTATIC_L,      SADR(java_lang_System_out),                       // 19:   getstatic       java.lang.System.out Ljava/io/PrintStream; (6)
OP_ALOAD_1,                                                            // 22:   aload_1
OP_INVOKEVIRTUAL,    B(0), LB(MSG_toString___Ljava_lang_String),       // 23:   invokevirtual   java.lang.Throwable.toString ()Ljava/lang/String; (16)
OP_INVOKEVIRTUAL,    B(1), LB(MSG_println__Ljava_lang_String__V),      // 26:   invokevirtual   java.io.PrintStream.println (Ljava/lang/String;)V (17)
OP_INVOKESHORT_java_lang_Thread_currentThread_Ljava_lang_Thread,       // 29:   invokestatic    java.lang.Thread.currentThread ()Ljava/lang/Thread; (18)
OP_INVOKEVIRTUAL,    B(0), LB(MSG_stop___V),                           // 32:   invokevirtual   java.lang.Thread.stop ()V (19)
OP_RETURN,                                                             // 35:   return
};

 *
 * Code produced in version 2
 *

/**
public static void main(String[] arg0)
Code(max_stack = 2, max_locals = 2, code_length = 36)
* /
#undef  JMETHOD
#define JMETHOD main_YLjava_lang_String
void main_YLjava_lang_String() {
  initstack(2+(2)+2,    1,    1);    // MaxLocals+(lsp+pc)+MaxStack, purLocals, purParams
  goto(0);
}
void main_YLjava_lang_String$$0() {
  invoke(haiku_vm_MicroKernel_clinitHaikuMagic_V);                     // 0:    invokestatic    haiku.pc.lib.HaikuMicroKernel.clinitHaikuMagic ()V (11)
  invoke(haiku_pc_lib_HaikuMicroKernel_init_V);                        // 3:    invokestatic    haiku.pc.lib.HaikuMicroKernel.init ()V (12)
  iconst(0);                                                           // 6:    iconst_0
  iconst(0);                                                           // 7:    iconst_0
  invoke(haiku_vm_MicroKernel_panic_IIV);                              // 8:    invokestatic    haiku.pc.lib.HaikuMicroKernel.panic (II)V (13)
  aload(0);                                                            // 11:   aload_0
  invoke(arduino_tutorial_JNI_FieldAccess_main_YLjava_lang_String_V);  // 12:   invokestatic    haiku.vm.HaikuMagic.main ([Ljava/lang/String;)V (14)
  goto(29);                                                            // 15:   goto        #29
}
void main_YLjava_lang_String$$18() {
  astore(1);                                                           // 18:   astore_1
  getstatic_l(java_lang_System_out);                                   // 19:   getstatic       java.lang.System.out Ljava/io/PrintStream; (6)
  aload(1);                                                            // 22:   aload_1
  invokevirtual(MSG_toString___Ljava_lang_String);                     // 23:   invokevirtual   java.lang.Throwable.toString ()Ljava/lang/String; (16)
  invokevirtual(MSG_println__Ljava_lang_String__V);                    // 26:   invokevirtual   java.io.PrintStream.println (Ljava/lang/String;)V (17)
  goto(29);
}
void main_YLjava_lang_String$$29() {
  invoke(java_lang_Thread_currentThread_Ljava_lang_Thread);            // 29:   invokestatic    java.lang.Thread.currentThread ()Ljava/lang/Thread; (18)
  invokevirtual(MSG_stop___V);                                         // 32:   invokevirtual   java.lang.Thread.stop ()V (19)
  return();                                                            // 35:   return
};

 *
 * Single base for version 1 and version 2
 *

/**
public static void main(String[] arg0)
Code(max_stack = 2, max_locals = 2, code_length = 36)
* /
#undef  JMETHOD
#define JMETHOD main_YLjava_lang_String
method(main_YLjava_lang_String) {
  initstack(2+(2)+2,    1,    1)    // MaxLocals+(lsp+pc)+MaxStack, purLocals, purParams
  yield(0)
  invokestatic(haiku_vm_MicroKernel_clinitHaikuMagic_V)               // 0:    invokestatic    haiku.pc.lib.HaikuMicroKernel.clinitHaikuMagic ()V (11)
  invokestatic(haiku_pc_lib_HaikuMicroKernel_init_V)                  // 3:    invokestatic    haiku.pc.lib.HaikuMicroKernel.init ()V (12)
  iconst_0()                                                          // 6:    iconst_0
  iconst_0()                                                          // 7:    iconst_0
  invokestatic(haiku_vm_MicroKernel_panic_IIV)                        // 8:    invokestatic    haiku.pc.lib.HaikuMicroKernel.panic (II)V (13)
  aload(0)                                                            // 11:   aload_0
  invokestatic(arduino_tutorial_JNI_FieldAccess_main_YLjava_lang_String_V)  // 12:   invokestatic    haiku.vm.HaikuMagic.main ([Ljava/lang/String;)V (14)
  goto(29)                                                            // 15:   goto        #29
  yield(18)
  astore_1()                                                          // 18:   astore_1
  getstatic_l(java_lang_System_out)                                   // 19:   getstatic       java.lang.System.out Ljava/io/PrintStream; (6)
  aload_1()                                                           // 22:   aload_1
  invokevirtual(MSG_toString___Ljava_lang_String)                     // 23:   invokevirtual   java.lang.Throwable.toString ()Ljava/lang/String; (16)
  invokevirtual(MSG_println__Ljava_lang_String__V)                    // 26:   invokevirtual   java.io.PrintStream.println (Ljava/lang/String;)V (17)
  yield(29)
  invokestatic(java_lang_Thread_currentThread_Ljava_lang_Thread)      // 29:   invokestatic    java.lang.Thread.currentThread ()Ljava/lang/Thread; (18)
  invokevirtual(MSG_stop___V)                                         // 32:   invokevirtual   java.lang.Thread.stop ()V (19)
  return()                                                            // 35:   return
};


 *
 * With
 * #define yield(n) goto(n);} void main_YLjava_lang_String$$##n() {
 *
 * #define aload_1 OPF_ALOAD_1
 *
 * and defined in Bytercodes.h:
 *
 * void OPF_ALOAD_1() {
 *   pushTop();
 *   top.s1=getLocal(bc&7)->s0; // TODO: bc is what??
 *   setMarkBit(top.s1.a);
 * }
 *
 * void OPF_ALOAD() { // TODO: void OPF_ALOAD(uint_8 arg1) {
 *   pushTop();
 *   top.s1=getLocal(GETCODEBYTE2())->s0;
 *   setMarkBit(top.s1.a);
 * }
 *

 * @author genom2
 *
 */
public class Compile2Data extends HaikuVM implements Aot {
	private static HashSet<String> closure=new HashSet<String>();

	private String methodName;
	private boolean remapIt;

	static private HashSet<String> arraysAll= new HashSet<String>();
	static private HashSet<String> arrays= new HashSet<String>();

	private static int pc=0;

	public Compile2Data(String classname) {
		super(classname);
	}

	public void compile() throws IOException {
		if (classname==null || classname.startsWith("[") || closure.contains(classname) || !Closure.contains(classname.replace('\\', '.').replace('/', '.'))) return;
		if (classname.endsWith("HaikuMagic")) {
			System.out.printf("link ! %s from %s will be not generated because it ends with 'HaikuMagic' which means it's template only!\n", classname, source);
			return;
		}

		jc =getClassFile(classname);

		Preprocess p=new Preprocess(jc);
		jc=p.preprocess();

		closure.add(classname);
		System.out.printf("link > %s.class from %s\n", classname.replace('/', '.'), new File(source).getCanonicalPath());

		File cf=new File(HaikuJava2File.getGenTargetDir()+"/"+classname+".c");
		if (!singleFile && cf.getParentFile()!=null) cf.getParentFile().mkdirs();
		printC2Image();
		System.out.printf("into   %s\n", singleFile?new File(HaikuJava2File.getGenTargetDir()+"/haikuJava.c"):cf.getCanonicalPath());
		Verbose.println();


		ConstantPool cp=jc.getConstantPool();
		Constant[] c=cp.getConstantPool();
		for (int i=0; i< c.length; i++) {
			if (c[i]==null) continue;
			if (c[i].getTag()==Constants.CONSTANT_Class) {
				ConstantClass cc= (ConstantClass)c[i];
				String classname=cc.getBytes(cp);
				new Compile2Data(classname).compile();
			}
		}
	}

	/*
	private static String sformat(int sign, long scaled, int leading, int magnitude, int post)
	Code(max_stack = 4, max_locals = 11, code_length = 170)
	const ByteCode java_lang_String_sformat_IJIII PROGMEM ={
	0,    4,   11,    5,    // tag, max_stack, locals, params (TODO: Wrong if double/long)
	{
		OP_ILOAD,            B(4),
		OP_ISTORE,           B(5),
		OP_ILOAD_3,
		OP_ISTORE,           B(4),
		OP_ILOAD_2,
		OP_ISTORE_3,
	OP_LDC,              CIDX_java_lang_String_82,                         // 0:    ldc		"" (82)
	OP_ASTORE,           B(6),                                             // 2:    astore		%6
	OP_LDC2_W,           INT16(CIDX_java_lang_String_8),                   // 4:    ldc2_w		-1000000000 (8)
	OP_LSTORE,           B(7),                                             // 7:    lstore		%7
	OP_ILOAD_0,                                                            // 9:    iload_0
	OP_IFGE,             INT16(7),                                         // 10:   ifge		#17
	OP_LDC,              CIDX_java_lang_String_84,                         // 13:   ldc		"-" (84)
	OP_ASTORE,           B(6),                                             // 15:   astore		%6
	OP_ICONST_0,                                                           // 17:   iconst_0
	OP_ISTORE,           B(9),                                             // 18:   istore		%9
	OP_GOTO,             INT16(135),                                       // 20:   goto		#155
	OP_ICONST_0,                                                           // 23:   iconst_0
	OP_ISTORE,           B(10),                                            // 24:   istore		%10
	OP_LLOAD,            B(7),                                             // 26:   lload		%7
	OP_LCONST_0,                                                           // 28:   lconst_0
	OP_LCMP,                                                               // 29:   lcmp
	OP_IFGE,             INT16(24),                                        // 30:   ifge		#54
	OP_LLOAD_1,                                                            // 33:   lload_1
	OP_LLOAD,            B(7),                                             // 34:   lload		%7
	OP_LDIV,                                                               // 36:   ldiv
	OP_LDC2_W,           INT16(CIDX_java_lang_String_86),                  // 37:   ldc2_w		10 (86)
	OP_LREM,                                                               // 40:   lrem
	OP_L2I,                                                                // 41:   l2i
	OP_ISTORE,           B(10),                                            // 42:   istore		%10
	OP_ILOAD,            B(10),                                            // 44:   iload		%10
	OP_IFGE,             INT16(8),                                         // 46:   ifge		#54
	OP_ILOAD,            B(10),                                            // 49:   iload		%10
	OP_INEG,                                                               // 51:   ineg
	OP_ISTORE,           B(10),                                            // 52:   istore		%10
	OP_ILOAD_3,                                                            // 54:   iload_3
	OP_IFNE,             INT16(15),                                        // 55:   ifne		#70
	OP_ILOAD,            B(10),                                            // 58:   iload		%10
	OP_IFNE,             INT16(10),                                        // 60:   ifne		#70
	OP_ILOAD,            B(9),                                             // 63:   iload		%9
	OP_ILOAD,            B(4),                                             // 65:   iload		%4
	OP_IF_ICMPNE,        INT16(31),                                        // 67:   if_icmpne		#98
	OP_ICONST_M1,                                                          // 70:   iconst_m1
	OP_ISTORE_3,                                                           // 71:   istore_3
	OP_NEW,              HADR(java_lang_StringBuilder__class), LADR(java_lang_StringBuilder__class),
	                                                                       // 72:   new		<java.lang.StringBuilder> (35)
	OP_DUP,                                                                // 75:   dup
	OP_ALOAD,            B(6),                                             // 76:   aload		%6
	OP_INVOKESTATIC,     HADR(java_lang_String_valueOf_Ljava_lang_Object), LADR(java_lang_String_valueOf_Ljava_lang_Object),
	                                                                       // 78:   invokestatic	java.lang.String.valueOf (Ljava/lang/Object;)Ljava/lang/String; (88)
	OP_INVOKESPECIAL,    HADR(java_lang_StringBuilder__init__Ljava_lang_String), LADR(java_lang_StringBuilder__init__Ljava_lang_String),
	                                                                       // 81:   invokespecial	java.lang.StringBuilder.<init> (Ljava/lang/String;)V (90)
	OP_BIPUSH,           B(48),                                            // 84:   bipush		48
	OP_ILOAD,            B(10),                                            // 86:   iload		%10
	OP_IADD,                                                               // 88:   iadd
	OP_I2C,                                                                // 89:   i2c
	OP_INVOKEVIRTUAL,    B(1), LB(MSG_append__C_Ljava_lang_StringBuilder),
	                                                                       // 90:   invokevirtual	java.lang.StringBuilder.append (C)Ljava/lang/StringBuilder; (38)
	OP_INVOKEVIRTUAL,    B(0), LB(MSG_toString___Ljava_lang_String),       // 93:   invokevirtual	java.lang.StringBuilder.toString ()Ljava/lang/String; (42)
	OP_ASTORE,           B(6),                                             // 96:   astore		%6
	OP_ILOAD,            B(9),                                             // 98:   iload		%9
	OP_ILOAD,            B(4),                                             // 100:  iload		%4
	OP_IF_ICMPNE,        INT16(32),                                        // 102:  if_icmpne		#134
	OP_ILOAD,            B(5),                                             // 105:  iload		%5
	OP_IFLE,             INT16(27),                                        // 107:  ifle		#134
	OP_ICONST_M1,                                                          // 110:  iconst_m1
	OP_ISTORE_3,                                                           // 111:  istore_3
	OP_NEW,              HADR(java_lang_StringBuilder__class), LADR(java_lang_StringBuilder__class),
	                                                                       // 112:  new		<java.lang.StringBuilder> (35)
	OP_DUP,                                                                // 115:  dup
	OP_ALOAD,            B(6),                                             // 116:  aload		%6
	OP_INVOKESTATIC,     HADR(java_lang_String_valueOf_Ljava_lang_Object), LADR(java_lang_String_valueOf_Ljava_lang_Object),
	                                                                       // 118:  invokestatic	java.lang.String.valueOf (Ljava/lang/Object;)Ljava/lang/String; (88)
	OP_INVOKESPECIAL,    HADR(java_lang_StringBuilder__init__Ljava_lang_String), LADR(java_lang_StringBuilder__init__Ljava_lang_String),
	                                                                       // 121:  invokespecial	java.lang.StringBuilder.<init> (Ljava/lang/String;)V (90)
	OP_BIPUSH,           B(46),                                            // 124:  bipush		46
	OP_INVOKEVIRTUAL,    B(1), LB(MSG_append__C_Ljava_lang_StringBuilder),
	                                                                       // 126:  invokevirtual	java.lang.StringBuilder.append (C)Ljava/lang/StringBuilder; (38)
	OP_INVOKEVIRTUAL,    B(0), LB(MSG_toString___Ljava_lang_String),       // 129:  invokevirtual	java.lang.StringBuilder.toString ()Ljava/lang/String; (42)
	OP_ASTORE,           B(6),                                             // 132:  astore		%6
	OP_ILOAD,            B(9),                                             // 134:  iload		%9
	OP_ILOAD,            B(4),                                             // 136:  iload		%4
	OP_IF_ICMPLE,        INT16(6),                                         // 138:  if_icmple		#144
	OP_IINC,             B(5), B(255),                                     // 141:  iinc		%5	-1
	OP_LLOAD,            B(7),                                             // 144:  lload		%7
	OP_LDC2_W,           INT16(CIDX_java_lang_String_86),                  // 146:  ldc2_w		10 (86)
	OP_LDIV,                                                               // 149:  ldiv
	OP_LSTORE,           B(7),                                             // 150:  lstore		%7
	OP_IINC,             B(9), B(1),                                       // 152:  iinc		%9	1
	OP_ILOAD,            B(9),                                             // 155:  iload		%9
	OP_ILOAD,            B(4),                                             // 157:  iload		%4
	OP_IF_ICMPLT,        INT16(-136),                                      // 159:  if_icmplt		#23
	OP_ILOAD,            B(5),                                             // 162:  iload		%5
	OP_IFGT,             INT16(-141),                                      // 164:  ifgt		#23
	OP_ALOAD,            B(6),                                             // 167:  aload		%6
	OP_ARETURN,                                                            // 169:  areturn
	}
	};

	 */
	private void printC2Image() throws IOException {
		boolean natives=false;
		msg2meth =new Msg2Meth();

		Method[] methods=jc.getMethods();
		outd=new HaikuClasses2H(new File(HaikuJava2File.getGenTargetDir()+"/"+filename(classname)+".c"));
		outc=new HaikuByteCode2H(new File(HaikuJava2File.getGenTargetDir()+"/"+filename(classname)+".c"));
		outh=new HaikuByteCodeTypes2H(new File(HaikuJava2File.getGenTargetDir()+"/"+filename(classname)+".h"));

		outc.println("/*");
        outc.printf("reason #%d: %s\n", Closure.get(classname).getId(), Closure.get(classname).getReason());
        outc.println();
		outc.println(jc);
		outc.println("*/");
		outc.println();
		outc.println();

		ConstantPool cp=jc.getConstantPool();
		Constant[] cons=cp.getConstantPool();
		Set<String> incSet=new HashSet<String>();
		for (int i=0; i< cons.length; i++) {
			if (cons[i]==null) continue;
			if (cons[i].getTag()==Constants.CONSTANT_Class) {
				ConstantClass cc= (ConstantClass)cons[i];
				String include=cc.getBytes(cp);
				if (!singleFile) {
	                outc.println("//                  "+include);
				}
                includeThis(incSet, include);
			}
		}
        String include="java/lang/Object";
        if (!singleFile) {
            outc.println("//                  "+include+" in case of checkcast and array relaxation");
        }
        includeThis(incSet, include);
		if (classname.replace("/", ".").equals(mainOsThread.replace("/", "."))) {
		    conditions.add(Condition.MAIN.desc("MicroKernel is "+classname));
			outc.printf("\n");
            if (!singleFile) {
                outc.println("// main             "+mainclass);
            }
			includeThis(incSet, mainclass);
			//outc.printf("extern const ByteCode %s;\n", mangle(mainclass)+"_main_YLjava_lang_String");
		}


		String typedef=calcTypedef();

		for (int i = 0; i < methods.length; i++) {
			Method method=methods[i];
			if (Closure.isMarked(jc.getClassName()+"."+method.getName()+method.getSignature())) {
				outc.println();
				outc.println("/**");
				outc.println(method);

				bytecode(incSet, method);
				natives=natives || method.isNative();
				if (isVirtual(method)) {
					String methodName= getMethodSignature("1 bc "+jc.getClassName()+"."+method.getName()+" "+method.getSignature());
					msg2meth.put(getMessageSignature("1 bc "+jc.getClassName()+"."+method.getName()+" "+method.getSignature()), methodName);
				}
			}
		}


		outh.println();
		outh.println();
		if (false && natives) {
			outh.printf("#include \"native/%s.h\"\n", filename(classname));
			outh.printf("\n");
			outh.printf("#ifndef NATIVE_TYPEDEF_%s\n", mangle(jc.getClassName()));
			outh.printf(typedef);
			outh.printf("#endif\n");
		} else {
            outh.printf("#ifndef %s\n", mangle(jc.getClassName()));
            ClassTable.classDeclaration(jc.getClassName());
            outh.printf(typedef);
            outh.printf("#endif\n");
		}

		outh.printf("extern const class_t %s__class;\n", mangle(jc.getClassName()));
		printArrayDecls();

		totalClassesLength+=2+2;

		outc.printf("\n");
		outd.haikuData(jc, msg2meth);

		if (HaikuDefs.getProperty("Opt-v")!=null) {
			Verbose.printf("  const class_t %s__class PROGMEM = {\n", mangle(jc.getClassName()));
			if (jc.getClassName().equals(jc.getSuperclassName())) {
				Verbose.printf("	NULL,\n");
			} else {
				Verbose.printf("	& %s__class,\n", mangle(jc.getSuperclassName()));
			}
			Verbose.printf("	SIZEOF_%s,\n", mangle(jc.getClassName()));
			//msg2meth.printC(System.out);
			Verbose.printf("  };\n");
		}

        outc.close();
        outd.close();
        outh.close();
	}

	/**
	 * Writes the #include line to outc.<br>
	 * But only if not already written (in incSet)
	 * And collect it in CollectedIncludes
	 *
	 * @param incSet
	 * @param include
	 * @throws IOException
	 */
	private void includeThis(Set<String> incSet, String clazz)
			throws IOException {
	    clazz=filename(clazz);
        Set<String> value = MethInfo.getClassMap(clazz);
        if (value==null) {
            if (incSet.add(clazz)) {
                includeThis(clazz);
            }
        } else {
            for (String include : value) {
                if (incSet.add(include)) {
                    includeThis(include);
                }
            }
        }
	}

    private void includeThis(String include) throws IOException {
        String array=null;
        if (include.startsWith("Y")) {
//            // former [
//            // YI -> YI
//            // YLdsdsa -> YLdsdsa_
//            array=include.replace('/', '_');
//            if (array.length()>2) array+="_";
            // keep array types flat
            include="Yarrays/"+array;
        }
        String inc=  "#include \""+include+".h\"";
        File cf=new File(HaikuJava2File.getGenTargetDir()+"/"+include+".h");
        if (!singleFile) {
            if (cf.getParentFile()!=null) cf.getParentFile().mkdirs();
            outc.println(inc);
        }
        if (array==null) {
            if (!singleFile) {
                if (!cf.exists()) {
                    PrintOnChange f = new HaikuByteCodeTypes2H(cf);
                    f.close();
                }
            }
        } else if (!CollectedIncludes.contains(inc)) {
            toArrayClass(array);
        }
        CollectedIncludes.put(inc);
    }

	/**
		private static void getSemaphore(int count, int intervall)
		Code(max_stack = 4, max_locals = 4, code_length = 119)
		#undef JMETHOD
		#define JMETHOD bench_haikuvm_SynchronizedThreads1_getSemaphore_II
		const bench_haikuvm_SynchronizedThreads1_getSemaphore_II_t bench_haikuvm_SynchronizedThreads1_getSemaphore_II PROGMEM ={
		10,    2,    2,    // max_stack, purLocals, purParams

		OP_LDC_C,            CADR(bench_haikuvm_SynchronizedThreads1__class),  // 0:    ldc		bench.haikuvm.SynchronizedThreads1 (1)
		OP_DUP,                                                                // 2:    dup
		OP_ASTORE_2,                                                           // 3:    astore_2
		OP_MONITORENTER,                                                       // 4:    monitorenter
		...
		OP_ALOAD_2,                                                            // 110:  aload_2
		OP_MONITOREXIT,                                                        // 111:  monitorexit
		OP_GOTO,             TARGET(118),                                      // 112:  goto		#118
		OP_ALOAD_2,                                                            // 115:  aload_2
		OP_MONITOREXIT,                                                        // 116:  monitorexit
		OP_ATHROW,                                                             // 117:  athrow
		OP_RETURN,                                                             // 118:  return
		};

	Exception_t	exceptionTable[] PROGMEM = {
		...
		{&bench_haikuvm_SynchronizedThreads1_getSemaphore_II.op5, &bench_haikuvm_SynchronizedThreads1_getSemaphore_II.op112, &bench_haikuvm_SynchronizedThreads1_getSemaphore_II.op115, &java_lang_Object__class},
		{&bench_haikuvm_SynchronizedThreads1_getSemaphore_II.op115, &bench_haikuvm_SynchronizedThreads1_getSemaphore_II.op117, &bench_haikuvm_SynchronizedThreads1_getSemaphore_II.op115, &java_lang_Object__class},
		...
		{NULL, NULL, NULL, NULL}
	};
	 * @throws IOException

	*/
	private void bytecode(Set<String> incSet, Method method) throws IOException {
		Code code= method.getCode();
		methodName= getMethodSignature("1 bc "+jc.getClassName()+"."+method.getName()+" "+method.getSignature());
		if (code == null) {
			// is abstract or native method
			outc.println("*/");
			if (method.isNative()) {
                totalMethods++;
                totalBClength+=3;

                if (methodName.endsWith("_clinitHaikuMagic_V")) {
                    conditions.add(Condition.CLINIT.desc("found clinitHaikuMagic() in: "+classname));
                    outc.printf("\n");
                    for (Clinit desc : Clinit.set()) {
                        String include=desc.getClassName().replace('.', '/');
                        outc.println("// clinit           "+include);
                        includeThis(incSet, include);
                        //outc.printf("extern const ByteCode %s__clinit__V;\n", mangle(desc.getClassName()));
                    }
                    /**
                        typedef struct {
                            uint8_t max_stack; int8_t purLocals; uint8_t purParams;

                            OP_bc oph9; OPadr fh9;  // <clinit>
                            OP_bc oph12; OPadr fh12;  // <clinit>
                            OP_bc oph15; OPadr fh15;  // <clinit>
                            OP_bc oph18; OPadr fh18;  // <clinit>
                            OP_bc op0;                                                             // 0:    return
                        }            haiku_avr_lib_arduino_HaikuMicroKernelEx_clinitHaikuMagic_V_t;
                        extern const haiku_avr_lib_arduino_HaikuMicroKernelEx_clinitHaikuMagic_V_t haiku_avr_lib_arduino_HaikuMicroKernelEx_clinitHaikuMagic_V;

                    public static synchronized void clinitHaikuMagic()
                    Code(max_stack = 0, max_locals = 0, code_length = 1)
                    * /
                    #undef  JMETHOD
                    #define JMETHOD haiku_avr_lib_arduino_HaikuMicroKernelEx_clinitHaikuMagic_V
                    const           haiku_avr_lib_arduino_HaikuMicroKernelEx_clinitHaikuMagic_V_t JMETHOD PROGMEM ={
                    0+0 +2,    0,    0,    // max_stack, purLocals, purParams

                    OP_INVOKESTATIC,     ADR(haiku_avr_AVRDefines__clinit__V),  // haiku.avr.AVRDefines.<clinit>
                    OP_INVOKESTATIC,     ADR(java_lang_Math__clinit__V),    // java.lang.Math.<clinit>
                    OP_INVOKESTATIC,     ADR(haikuvm_bench_JUnit1__clinit__V),  // haikuvm.bench.JUnit1.<clinit>
                    OP_INVOKESTATIC,     ADR(java_lang_Double__clinit__V),  // java.lang.Double.<clinit>
                    OP_RETURN,                                                             // 0:    return
                    };
                    */

                    outh.printf("typedef struct {\n");
                    outh.printf("    uint8_t max_stack; int8_t purLocals; uint8_t purParams;\n");
                    for (Clinit desc : Clinit.set()) {
                        totalBClength+=3;
                        outh.printf("\tINVOKESTATICt(oph%d, fh%d);  // <clinit>\n", totalBClength, totalBClength);
                    }
                    outh.printf("    OP_bc op0;                                                             //       return\n");
                    outh.printf("}            %s_t;\n", methodName, methodName);
                    outh.printf("extern const %s_t %s;\n", methodName, methodName);
                    outh.printf("\n");

                    outc.printf("const           %s_t %s PROGMEM ={\n", methodName, methodName);
                    outc.printf("2, 0, 0,    // max_stack, purLocals, purParams\n");
                    outc.printf("\n");
                    for (Clinit desc : Clinit.set()) {
                        outc.printf("INVOKESTATIC(%s__clinit__V),   // %s.<clinit>\n", mangle(desc.getClassName()), desc.getClassName());
                        BC2IDX.put("INVOKESTATIC");
                    }
                    outc.printf("BYTECODE(RETURN),                                                             //       return\n");
                    outc.printf("};\n");
                } else {
                    outh.printf("#ifndef %s\n", "native_"+methodName);
                    outh.printf("extern const NativCode %s;\n", methodName);
                    outh.printf("extern void            %s(void);\n",    "native_"+methodName);
                    outh.printf("#endif\n");
                    outh.printf("\n");

                    outc.printf("#ifndef %s\n", "native_"+methodName);
                    outc.printf("const NativCode %s PROGMEM ={0xff, &%s};\n", methodName, "native_"+methodName);
                    outc.printf("#endif\n");

                    totalBClength+=2;
                    haikuJNIc.nativeInfo(jc, classname, method, methodName);
                }
			}
			return;
		}

		int remap[]=makeArgumentReMap(method);
		int start=0, end;
		String str =code.toString(true);
		byte bc[]=code.getCode();
		totalBClength+=bc.length+3 + 2 /*for pointer in functionTable*/;
		list=str.split("\n");
		for (int k = 0; k < list.length; k++) {
			String line=list[k];
			if (line.matches("^\\d+:.*")) {
				start=Integer.parseInt(line.replaceAll(":.*", ""));
                end=bc.length;
                if (k+1<list.length) {
                    try {
                        end = Integer.parseInt(list[k+1].replaceAll(":.*", ""));
                    } catch (NumberFormatException e) {
                    }
                }
				if (start==0) {
					outc.println("*/");

					outh.printf("typedef struct {\n");
					outh.printf("\tuint8_t max_stack; int8_t purLocals; uint8_t purParams;\n");
					outh.printf("\n");



					totalMethods++;
					outc.printf("#undef  JMETHOD\n");
					outc.printf("#define JMETHOD %s\n", methodName);

					/*
					 * max_stack: max inner method working stack usage (without purLocals and purParams)
					 * purParams: only stack usage of parameters (without purLocals)
					 * purLocals: all local variables of the method (without purParams)
					 *
					 * Comment:
					 * getlocal(idx) addresses the stack beginning with the first (leftmost) Parameter position getLocal(0)
					 * 	It covers both parameters and local variables.
					 */
					outc.printf("const           %s_t JMETHOD PROGMEM ={\n", methodName);
					outc.printf("%d+(2)+%d, %4d, %4d,    // MaxLocals+(lsp+pc)+MaxStack, purLocals, purParams\n", code.getMaxLocals(), code.getMaxStack(), code.getMaxLocals()-getParams(method), getParams(method));
					outc.printf("\n");
					if (methodName.endsWith("_clinitHaikuMagic_V")) {
                        conditions.add(Condition.CLINIT.desc("found clinitHaikuMagic() in: "+classname));
						for (Clinit desc : Clinit.set()) {
							totalBClength+=3;
							outh.printf("\tINVOKESTATICt(oph%d, fh%d);  // <clinit>\n", totalBClength, totalBClength);
							outc.printf("INVOKESTATIC(%s__clinit__V),	// %s.<clinit>\n", mangle(desc.getClassName()), desc.getClassName());
							BC2IDX.put("INVOKESTATIC");
						}
					}
					pc+=4;
				}
				String[] token= list[k].split("[ \t,]+");
				if (token.length<=2 || !supressPanicSupport(token[2])) {
					String bcode=token[1];
					if (bcode.equals("wide")) {
						k++;
						end = Integer.parseInt(list[k+1].replaceAll(":.*", ""));
					}
					outh.println("\t"+compileJ2Typedef(list[k], bc, start, end));
					outc.println(compileJ2Image(list[k], bc, start, end, remap));
				}
				start=999;
			} else {
				if (start==0) outc.println(line);
			}
		}
		outh.printf("}            %s_t;\n", methodName);
		outh.printf("extern const %s_t %s;\n", methodName, methodName);
		outh.printf("\n");
		outc.printf("};\n");


		CodeException[] et = code.getExceptionTable();
		ConstantPool cp=jc.getConstantPool();
		Constant[] cons=cp.getConstantPool();

		for (int i = 0; i < et.length; i++) {
			int ct=et[i].getCatchType();
			String ex;
			if (ct==0) {
				ex="&java_lang_Object__class";
			} else {
				ex="&"+mangle(cp.constantToString(ct, Constants.CONSTANT_Class))+"__class";
			}
			//Haikout.println(ex);
			exceptionTable+="\t{&"+methodName+".op"+et[i].getStartPC()+", &"+methodName+".op"+et[i].getEndPC()+", &"+methodName+".op"+et[i].getHandlerPC()+", "+ex+"},\n";
		}
	}

	/**
	 * Versuch die stackparameter zu berechnen die vom caller-stack in den neuen Stackframe kopiert werden m�ssen.
	 *
	 * TODO:
	 * long und double z�hlen eigentlich doppelt. Hier aber erst mal nur einfach.
	 *
	 * @param method
	 * @return
	 */
	private int getParams(Method method) {
		int purParams = method.isStatic()?0:1;

		for (int i = 0; i < method.getArgumentTypes().length; i++) {
			if (method.getArgumentTypes()[i]==Type.DOUBLE || method.getArgumentTypes()[i]==Type.LONG) {
				if (remapIt) {
					purParams+=1;
				} else {
					purParams+=2;
				}
			} else {
				purParams+=1;
			}
		}
		return purParams;
	}

	private boolean isVirtual(Method method) {
		return ! (method.isStatic() || method.isPrivate() || method.getName().equals("<init>")) ;
		//return Utility.accessToString(method.getAccessFlags()).indexOf("static")<0;
	}

	/**
	 * remap locals weil in der Argumentliste long und double nur einen Stackplatz verbrauchen.
	 * dies ist anderes als in Standard JAVA.
	 * @param method
	 * @return
	 */
	private int[] makeArgumentReMap(Method method) {
		int locals=method.getCode().getMaxLocals();
		Type type[]=method.getArgumentTypes();
		int remap[]= new int[locals];
		for (int i = 0; i < locals; i++) {
			remap[i]=-9999;
		}
		int from=0;
		int to=0;
		if (!method.isStatic()) {
			remap[0]=0;
			from++;
			to++;
		}
		// Dies sind die Argumente
		int i;
		for (i = 0; i<type.length; i++, from++, to++) {
			remap[from]=to;
			if (remapIt) {
				if (type[i].toString().equals("long") || type[i].toString().equals("double")) {
					from++;
				}
			}
		}
		// Dies sind die (wahren) Locals
		for (; from<locals; from++) {
			remap[from]=from;
		}

		return remap;
	}

	/**
	 * TODO:
	 * New byte code for long/double
	 * 	getstatic_w
	 * 	getfield_w
	 * 	putstatic_w
	 * 	putfield_w
	 *
	 * @param jcode
	 * @param bc
	 * @param start
	 * @param end
	 * @return
	 * @throws IOException
	 */
	private String compileJ2Image(String jcode, byte bc[], int start, int end, int[] remap) throws IOException {
		String haiku="";
		String[] token= jcode.split("[ \t,]+");
		String bcode=token[1].toUpperCase();
		String ccode=bcode;

		ccode="BYTECODE("+ccode+"), ";
		int len=end-start;
		String index=getConstIndex(jcode);
		switch(len) {
		case 1:
			if (bcode.matches(".+LOAD_\\d") || bcode.matches(".+STORE_\\d")) {
				int local=Integer.parseInt(bcode.substring(bcode.length()-1));
				int rm=remap[local];
				if (local!=rm) {
					haiku=" !!haiku remap";
				}
				bcode=bcode.substring(0, bcode.length()-1)+rm;
				ccode="BYTECODE("+bcode+"), ";
			}
			break;
		case 2:
			ccode=new Formatter().format("%-20s ",  ccode).toString();
			if (index!=null) {
				//31:   ldc		0.3010299 (53)
				int i=Integer.parseInt(token[token.length-1].replaceAll("[()]", ""));
				String ext=getConstExt(i);
				bcode=bcode+"_"+ext;
				ccode="BYTECODE("+bcode+"), ";
				ccode=new Formatter().format("%-20s ",  ccode).toString();
				if (ext.endsWith("X")) {
					ccode+="B("+getConstSpec(i, 8)+"), ";
				} else if (ext.endsWith("C")) {
					// 18:   ldc		bench.haikuvm.Sync (1)
					ccode+="CADR("+mangle("<"+token[2]+">")+"), ";
				} else {
					ccode+="CADR("+getConstSpec(i, 16)+"), ";
				}
			} else if (token[1].equals("newarray")) {
				//7:    newarray		<byte>
				// with
				// <byte> --> YB__class
				ccode+=new Formatter().format("ADR(%s), ", toArrayClass(token[2]));
			} else {
				if (bcode.endsWith("LOAD") || bcode.endsWith("STORE") || token[1].equals("ret")) {
					int local=bc[start+1]&0xff;
					int rm=remap[local];
					if (local!=rm) {
						haiku=" !!haiku remap";
					}
					ccode+=new Formatter().format("B(%d), ", rm);
				} else {
					ccode+=new Formatter().format("B(%d), ", bc[start+1]);
				}
			}
			break;
		case 3:
			ccode=new Formatter().format("%-20s ",  ccode).toString();
			//16:   invokespecial	java.lang.StringBuilder.<init> (Ljava/lang/String;)V
			//20:   invokevirtual	java.lang.StringBuilder.append (I)Ljava/lang/StringBuilder;
			if (token[1].equals("invokevirtual")) {
				String meth=getMessageSignature(jcode);
				msg2meth.put(meth, null);
				ccode=new Formatter().format("%s(%d, %s), ", bcode, getMessageParamNo(jcode), meth).toString();
				break;
			}
			if (token[1].equals("invokespecial") || token[1].equals("invokestatic")) {
				String meth=getMethodSignature(jcode);
				if (token[1].equals("invokestatic")) {
					meth=MethInfo.findRealMethod(token[2], token[3]).getLongName();
				}
				if (functionTable.isShortcut(meth)) {
					bcode=null;
					ccode="INVOKESHORT("+meth+"), ";
					totalBClength-=2;
				} else {
					ccode=new Formatter().format("%s(%s), ", bcode, meth).toString();
				}
				break;
			}
			// 3:    getstatic		Fibonacci.lng J (71)
			if (token[1].equals("putstatic") || token[1].equals("getstatic")) {
				String btype=token[3].substring(0, 1);
				if (btype.equals("[")) btype="L";
				bcode+="_"+btype;
				ccode=new Formatter().format("%-20s ",  "BYTECODE("+bcode+"), ").toString();
				String var=staticMap.get(token[2]);
				if (var==null) {
					// Global static e.g. java.lang.System.out
					var=token[2];
				}

				String anno=getAnnotations(token[2].replaceFirst("(.*)[.].+", "$1"), token[2].replaceFirst(".*[.](.+)", "$1"));
				if (anno.contains("/NativeCVariable;")) {
                    ObjSpace.set(btype, var, token[1]+"&native");
                    if (btype.equals("L")) {
//                        ccode+=new Formatter().format("NStructADR(%s), ", token[2].replaceFirst("(.*)[.](.+)", "$2"));
                        bcode=token[1].toUpperCase()+"_N";
                        ccode=new Formatter().format("%-20s ",  "BYTECODE("+bcode+"), ").toString();
                        if (token[3].startsWith("[")) {
                            ccode+=new Formatter().format("NarrayADR(%s), ", token[2].replaceFirst("(.*)[.](.+)", "$2"));
                        } else {
                            ccode+=new Formatter().format("NstructADR(%s), ", token[2].replaceFirst("(.*)[.](.+)", "$2"));
                        }
                    } else {
                        ccode+=new Formatter().format("NADR(%s), ", token[2].replaceFirst("(.*)[.](.+)", "$2"));
                    }
                } else if (anno.contains("/NativeCVariable")) { //8, 16, 32, 64
                    anno=anno.replaceFirst(".*(NativeCVariable.+);.*", "$1");
                    if (anno.endsWith("8")) {
                        ObjSpace.set("8", var, token[1]+"&native");
                        bcode=token[1].toUpperCase()+"_B";
                    } else if (anno.endsWith("16")) {
                        ObjSpace.set("16", var, token[1]+"&native");
                        bcode=token[1].toUpperCase()+"_S";
                    } else {
                        // we are not able to decide because it depends on memory model
                        // so register both posibilities
                        ObjSpace.set(btype, var, token[1]+"&native");
                        BC2IDX.put(token[1].toUpperCase()+"_I");
                        bcode=token[1].toUpperCase()+"_J"; // to be puted later (see near end)
                    }
                    ccode=new Formatter().format("%-20s ",  "OP_"+token[1].toUpperCase()+"_"+anno+", ").toString();
                    ccode+=new Formatter().format("NADR(%s), ", token[2].replaceFirst("(.*)[.](.+)", "$2"));
                } else {
	                ObjSpace.set(btype, var, token[1]);
	                ccode+=new Formatter().format("SADR(%s), ", mangleField(var));
				}
				break;
			}
			// 6:    putfield		java.lang.Double.value D (13)
			// -> FIDX(java_lang_Double, value)
			// -> offsetof(java_lang_Double, user.value)
			if (token[1].equals("putfield") || token[1].equals("getfield")) {
                String anno=getAnnotations(token[2].replaceFirst("(.*)[.].+", "$1"), token[2].replaceFirst(".*[.](.+)", "$1"));
				String btype=token[3].substring(0, 1);
				if (btype.equals("[")) btype="L";
				bcode+="_"+btype;
				ccode=new Formatter().format("%-20s ",  "BYTECODE("+bcode+"), ").toString();
				int p=token[2].lastIndexOf('.');
				String type=mangle(token[2].substring(0, p));
				String member=mangleField(token[2].substring(p+1));
				ccode+=new Formatter().format("FIDX(%s, %s), ", type, member);
                if (anno.contains("/NativeCVariable")) {
                    anno=anno.replaceFirst(".*(NativeCVariable.+);.*", "$1");
                    Verbose.error("Annotation @%s is not allowed with non static fields. Here %s.!", anno, token[2]);
                }
				break;
			}
			if (token[1].equals("iinc")) {
				int local=bc[start+1]&0xff;
				int rm=remap[local];
				if (local!=rm) {
					haiku=" !!haiku remap";
				}
				int inc=bc[start+2]&0xff;
				if (inc==1) {
					bcode="IINC1";
					ccode="BYTECODE(IINC1), "+new Formatter().format("B(%d), ", rm);
				} else {
					ccode+=new Formatter().format("B(%d), B(%d), ", rm, bc[start+2]&0xff);
				}
				break;
			}
			if (jcode.matches(".+#\\d+$")) {
				String target=jcode.replaceAll(".+#(\\d+)$", "$1");
				ccode+=new Formatter().format("TARGET(%d), ", Integer.parseInt(target));
				break;
			}
			if (index!=null) {
				if (token[1].startsWith("ldc")) {
					// 6:    ldc2_w		-2147483648 (49) ??
					int i=Integer.parseInt(token[token.length-1].replaceAll("[()]", ""));
					String ext=getConstExt(i);
					bcode=bcode+"_"+ext;
					if (bcode.equals("LDC_W_C")) bcode="LDC_C"; // tweak
					ccode="BYTECODE("+bcode+"), ";
					ccode=new Formatter().format("%-20s ",  ccode).toString();
					if (ext.endsWith("X")) {
						ccode+="B("+getConstSpec(i, 8)+"), ";
					} else {
						ccode+="CADR("+getConstSpec(i, 16)+"), ";
					}
                } else if (token[1].equals("instanceof")) {
					// 29:   instanceof	<[Lhaikuvm.bench.from.darjeeling.testvm.classes.A;> (94)
                    String adr=token[2];
                    if (token.length>4) adr+="_"+token[3];
                    if (adr.contains("[")) {
                        adr=toArrayClass(adr);
                    } else {
                        adr=mangle(adr);
                        //adr=mangle("<java.lang.Object>");
                    }
                    ccode+=new Formatter().format("ADR(%s), ", adr);
                } else if (token[1].equals("checkcast")) {
                    // HaikuVM simplifys/ignores/relaxes array cast
                    String adr=token[2];
                    if (token.length>4) adr+="_"+token[3];
                    if (adr.contains("[")) {
                        adr=toArrayClass(adr);
                    } else {
                        adr=mangle(adr);
                        //adr=mangle("<java.lang.Object>");
                    }
                    ccode+=new Formatter().format("ADR(%s), ", adr);
                } else if (token[1].equals("anewarray")) {
					// 3:    anewarray     <java.lang.Object> (3)
				    // 2:    anewarray     <[I> (19)
//					if (! size(token[2]).equals("0")) {
//					    Verbose.warning("anewarray expected sizeidx 0 but is "+ size(token[2]));
//					}
//                    ccode+=new Formatter().format("B(%s), ", size(token[2]));

				    // HaikuVM simplifys/ignores type of array
				    // Which is: forces it to (anonymous) <java.lang.Object>
                    ccode+=new Formatter().format("ADR(%s), ", toArrayClass("<[Ljava.lang.Object>")); // sorry toArrayClass(token[2]));
				} else {
					// 3:    new		<Fibonacci> (17)
					String adr=token[2];
					if (token.length>4) adr+="_"+token[3];
					adr=mangle(adr);
					ccode+=new Formatter().format("ADR(%s), ", adr);
				}
				break;
			}
			ccode+=new Formatter().format("INT16(%d) /*0x%04x*/, ", (short)((bc[start+1]&0xff)<<8) + (bc[start+2]&0xff), (short)((bc[start+1]&0xff)<<8) + (bc[start+2]&0xff));
			break;
		case 6:
			if (bcode.equals("IINC")) {
				/*
				 * OP_WIDE,                                                               // 70:   wide	(wide)
				 * OP_IINC, B(0), B(3), B(1), B(244),                                     // 71:   iinc		%3	500
				 * ==>
				 * OP_WIINC, INT16(3), INT16(500),                                   	  // 70:   iinc		%3	500
				 *
				 */
				int local=(short)((bc[start+2]&0xff)<<8) + (bc[start+3]&0xff);
				int rm=remap[local];
				if (local!=rm) {
					haiku=" !!haiku remap";
				}
				bcode="WIINC";
				ccode="BYTECODE(WIINC), "+ new Formatter().format("INT16(%d), INT16(%d), ", rm, (short)((bc[start+4]&0xff)<<8) + (bc[start+5]&0xff));
				break;
			}
			break;
		default:
			if (token[1].equals("invokeinterface")) {
				ccode=new Formatter().format("%-20s ",  ccode).toString();
				String meth=getMessageSignature(jcode);
				msg2meth.put(meth, null);
				ccode+=new Formatter().format("B(%d), LB(%s), B(%d), B(%d), ", getMessageParamNo(jcode), meth, bc[start+3]&0xff, bc[start+4]&0xff);
				break;
			} else if (token[1].equals("lookupswitch")) {
			    /* TODO
			     * According to Squawk ideas:
			     * The lookupswitch bytecode should be replaced by the lookup bytecode that precedes a tableswitch or
			     * stableswitch bytecode. This allows more compact lookup switch tables to be
			     * implemented and also simplifies the VM by only having one kind of multi-branch
			     * opcode.
			     *
			     * My idea: not pairs but two lists. One for values and the second for jumps:
			     *   Xlookupswitch npairs, Xvalue0, .. XvalueN, default, address0, .. addressN
			     * where npairs is a uint16 and X in:
			     *   i: int
			     *   s: short
			     *   b: byte
			     *
			     */
				// lookupswitch (ab) 4+: <0-3 bytes padding>, defaultbyte1, defaultbyte2, defaultbyte3, defaultbyte4, npairs1, npairs2, npairs3, npairs4, match-offset pairs...<br>

				//	0		1			2		3	4	5	6	7	8
				// 84:   lookupswitch	default = 172, npairs = 4 ((31, 128), (73, 150), (94, 161), (10000, 139))
				token= jcode.split("[ \t,)(:]+");
				int pc=Integer.parseInt(token[0]);
				int dflt=Integer.parseInt(token[4]);
				int npairs=Integer.parseInt(token[7]);
				ccode+="TARGET("+dflt+"), "+ccode(npairs)+"\n\t\t\t\t";
				for (int i = 0; i < npairs; i++) {
					ccode+=ccode(Integer.parseInt(token[8+2*i]))+"TARGET("+Integer.parseInt(token[8+2*i+1])+"), "+"\n\t\t\t\t";
				}

			} else if (token[1].equals("tableswitch")) {
				// tableswitch (aa) 4+: [0-3 bytes padding], defaultbyte1, defaultbyte2, defaultbyte3, defaultbyte4, lowbyte1, lowbyte2, lowbyte3, lowbyte4, highbyte1, highbyte2, highbyte3, highbyte4, jump offsets...<br>

				//	0		1			2		3	4	5  6 7	8    9 10
				// 69:   tableswitch	default = 144, low = 1, high = 4(100, 111, 122, 133)
				token= jcode.split("[ \t,)(:]+");
				int pc=Integer.parseInt(token[0]);
				int dflt=Integer.parseInt(token[4]);
				int low=Integer.parseInt(token[7]);
				int high=Integer.parseInt(token[10]);
				ccode+="TARGET("+dflt+"), "+ccode(low)+ccode(high)+"\n\t\t\t\t";
				for (int i = 0; i <= high-low; i++) {
					ccode+="TARGET("+Integer.parseInt(token[11+i])+"), "+"\n\t\t\t\t";
				}
			} else if (token[1].equals("multianewarray")) {
				// 11:   multianewarray	<[[[I>	3 (114)

			    // HaikuVM simplifys/ignores array-array type
				// And fall back to final element type:
				// <[[Ljava.lang.String;> -> <java.lang.String>
				String aa=token[2];
				String et=aa.replaceFirst("\\[+L", "YL").replaceFirst("\\[+", "Y").replace(";", "");
				ccode+=new Formatter().format("ADR(%s), B(%d), ", toArrayClass(et), bc[start+3]&0xff);
			} else {
				for (int i = 1; i < len; i++) {
					ccode+="B("+(bc[start+i]&0xff)+"), ";
				}
			}

		}

		if (bcode!=null) BC2IDX.put(bcode);

		if(ccode.length()>70) {
			return new Formatter().format("%-70s\n%-70s // %s", ccode, "", jcode+haiku).toString();
		}
		return new Formatter().format("%-70s // %s", ccode, jcode+haiku).toString();
	}

	private String getAnnotations(String classname, String fieldname) throws IOException {
        String res="";
        JavaClass ljc = getClassFile(classname);
        Field[] fields = ljc.getFields();
        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            if (fieldname.equals(f.getName())) {
                Attribute[] a = f.getAttributes();
                for (int j = 0; j < a.length; j++) {
                    if (a[j].toString().contains(" RuntimeVisibleAnnotations:")) {
                        byte[] x = ((Unknown)a[j]).getBytes();
                        int idx=((x[2]&0xff)<<8)+(x[3]&0xff);
                        res+=a[j].getConstantPool().getConstant(idx);
                    }
                }
                return res;
            }
        }
        classname=ljc.getSuperclassName();
        return getAnnotations(classname, fieldname);
    }

    private String size(String atype) {
		String def = "B 1, Z 1, C 2, S 3, I 4, J 5, F 6, D 7, byte 1, boolean 1, char 2, short 3, int 4, long 5, float 6, double 7";
		String[] list=def.split("[ ,]+");
		atype=atype.replaceAll("[<>\\[]", "");
		for (int i = 0; i < list.length; i+=2) {
			if (atype.equals(list[i]))
				return list[i+1];
		}

		return "0";
	}

    private String compileJ2Typedef(String jcode, byte bc[], int start, int end) throws IOException {
		String[] token= jcode.split("[ \t,]+");
		String ccode=null;

		int len=end-start;
		String index=getConstIndex(jcode);
		switch(len) {
		case 1:
			ccode="OP_bc op"+start;
			break;
		case 2:
			if (token[1].startsWith("ldc")) {
				// 6:    ldc		-2147483648 (49) ??
				int i=Integer.parseInt(token[token.length-1].replaceAll("[()]", ""));
				String ext=getConstExt(i);
				if (ext.endsWith("X")) {
					ccode="OP_bc op"+start+"; OP__8 b"+start;
				} else {
					ccode="OP_bc op"+start+"; OPcon c"+start;
				}
			} else if (token[1].equals("newarray")) {
				//7:    newarray		<byte>
				// with
				// <byte> --> YB__class
				ccode="OP_bc op"+start+";  OPadr f"+start;
			} else {
				ccode="OP_bc op"+start+"; OP__8 b"+start;
			}
			break;
		case 3:
			ccode="OP_bc op"+start+"; OP__8 b"+start;
			//16:   invokespecial	java.lang.StringBuilder.<init> (Ljava/lang/String;)V
			//20:   invokevirtual	java.lang.StringBuilder.append (I)Ljava/lang/StringBuilder;
			if (token[1].equals("invokevirtual")) {
				ccode="INVOKEVIRTUALt(op"+start+", a"+start+", b"+start+")";
				break;
			}
			if (token[1].equals("invokespecial") || token[1].equals("invokestatic")) {
				String meth=getMethodSignature(jcode);
				if (token[1].equals("invokestatic")) {
					meth=MethInfo.findRealMethod(token[2], token[3]).getLongName();
				}
				if (functionTable.isShortcut(meth)) {
					ccode="INVOKESHORTt(op"+start+", f"+start+")";
				} else {
					ccode=token[1].toUpperCase()+"t(op"+start+", f"+start+")";
				}
				break;
			}
			// 3:    getstatic		Fibonacci.lng J (71)
			if (token[1].equals("putstatic") || token[1].equals("getstatic")) {
				ccode="OP_bc op"+start+"; OPadr a"+start;
				break;
			}
			// 6:    putfield		java.lang.Double.value D (13)
			// offsetof(java_lang_Double, user.value)
			if (token[1].equals("putfield") || token[1].equals("getfield")) {
				ccode="OP_bc op"+start+"; OP_16 a"+start;
				break;
			}
			if (token[1].equals("iinc")) {
				int inc=bc[start+2]&0xff;
				if (inc==1) {
					ccode="OP_bc op"+start+"; OP__8 a"+start;
				} else {
					ccode="OP_bc op"+start+"; OP__8 a"+start+"; OP__8 b"+start;
				}
				break;
			}
			if (jcode.matches(".+#\\d+$")) {
				ccode="OP_bc op"+start+"; OPtrg a"+start;
				break;
			}
			if (index!=null) {
				if (token[1].startsWith("ldc")) {
					// 6:    ldc2_w		-2147483648 (49) ??
					int i=Integer.parseInt(token[token.length-1].replaceAll("[()]", ""));
					String ext=getConstExt(i);
					if (ext.endsWith("X")) {
						ccode="OP_bc op"+start+"; OP__8 b"+start;
					} else {
						ccode="OP_bc op"+start+"; OPcon c"+start;
					}
				} else if (token[1].equals("anewarray")) {
					// 3:    anewarray		<java.lang.Object> (3)
					ccode="OP_bc op"+start+";  OPadr f"+start;
				} else {
					// 3:    new		<Fibonacci> (17)
					ccode="OP_bc op"+start+"; OPadr f"+start;
				}
				break;
			}
			ccode="OP_bc op"+start+"; OP_16 a"+start;
			break;
		case 6:
			if (token[1].equals("iinc")) {
				ccode="OP_bc op"+start+"; OP_16 a"+start+"; OP_16 b"+start;
			}
			break;
		default:
			if (token[1].equals("invokeinterface")) {
				ccode="OP_bc op"+start+"; OP__8 a"+start+"; OP__8 b"+start+"; OP__8 c"+start+"; OP__8 d"+start;
				break;
			} else if (token[1].equals("lookupswitch")) {
				// lookupswitch (ab) 4+: <0-3 bytes padding>, defaultbyte1, defaultbyte2, defaultbyte3, defaultbyte4, npairs1, npairs2, npairs3, npairs4, match-offset pairs...<br>

				//	0		1			2		3	4	5	6	7	8
				// 84:   lookupswitch	default = 172, npairs = 4 ((31, 128), (73, 150), (94, 161), (10000, 139))
				token= jcode.split("[ \t,)(:]+");
				ccode="OP_bc op"+start+"; OPtrg d"+start+"; OP_32 b"+start+"\n\t\t\t\t";
				int npairs=Integer.parseInt(token[7]);
				for (int i = 0; i < npairs; i++) {
					ccode+="; OP_32 m"+(start+i)+"; OPtrg t"+(start+i)+"\n\t\t\t\t";
				}
			} else if (token[1].equals("tableswitch")) {
				// tableswitch (aa) 4+: [0-3 bytes padding], defaultbyte1, defaultbyte2, defaultbyte3, defaultbyte4, lowbyte1, lowbyte2, lowbyte3, lowbyte4, highbyte1, highbyte2, highbyte3, highbyte4, jump offsets...<br>

				//	0		1			2		3	4	5  6 7	8    9 10
				// 69:   tableswitch	default = 144, low = 1, high = 4(100, 111, 122, 133)
				token= jcode.split("[ \t,)(:]+");
				int low=Integer.parseInt(token[7]);
				int high=Integer.parseInt(token[10]);
				ccode="OP_bc op"+start+"; OPtrg d"+start+"; OP_32 b"+start+"; OP_32 c"+start+"\n\t\t\t\t";
				for (int i = 0; i <= high-low; i++) {
					ccode+="; OPtrg t"+(start+i)+"\n\t\t\t\t";
				}
			} else if (token[1].equals("multianewarray")) {
				// 11:   multianewarray	<[[[I>	3 (114)
				ccode="OP_bc op"+start+"; OPadr f"+start+"; OP__8 d"+start;
			} else {
				ccode="OP_bc op"+start;
				for (int i = 1; i < len; i++) {
					ccode+="; OP__8 b"+(start+i);
				}
			}
		}
		return new Formatter().format("%-70s // %s", ccode+";", jcode).toString();
	}

	private String getConstExt(int i) {
		ConstantPool cp=jc.getConstantPool();
		Constant[] cons=cp.getConstantPool();
		int tag=cons[i].getTag();
		switch (tag) {
		case Constants.CONSTANT_String:
			return "S";
		case Constants.CONSTANT_Float:
			String value=Float.toString(((ConstantFloat)cons[i]).getBytes());
			if (value.equals("-Infinity") ||value.equals("NaN") || value.equals("Infinity")) {
	        	return "FX";
			} else {
	        	return "F";
			}
		case Constants.CONSTANT_Double:
			value=Double.toString(((ConstantDouble)cons[i]).getBytes());
			if (value.equals("-Infinity") ||value.equals("NaN") || value.equals("Infinity")) {
	        	return "DX";
			} else {
	        	return "D";
			}
		case Constants.CONSTANT_Integer:
			return "I";
		case Constants.CONSTANT_Long:
			return "L";
		case Constants.CONSTANT_Class:
			return "C";
		default:
			return ""; // Error
	  }
	}

	private String getConstSpec(int i, int constrain) {
		ConstantPool cp=jc.getConstantPool();
		Constant[] cons=cp.getConstantPool();
		int tag=cons[i].getTag();
		String value, key;
        switch (tag) {
        case Constants.CONSTANT_String:
    		value=escape(((ConstantUtf8)cons[((ConstantString)cons[i]).getStringIndex()]).getBytes());
			key=CIDX.getConstKey("S", value);
			if (! CIDX.containsKey(key)) {
	    		totalConstLength+=escape(((ConstantUtf8)cons[((ConstantString)cons[i]).getStringIndex()]).getBytes()).length()+1;
				//CIDX.set(key, new Formatter().format("const ldc_jstring_t\t%s PROGMEM =  {%d,\t\"%s\"};\n", key, value.length(), value).toString(), constrain);
				String def="";
				int len=0;
				if (value.length()>0) {
					def+="{";
					for (int j = 0; j < value.length(); j++) {
						if (j>0) def+=",";
						len++;
						if (value.charAt(j)=='\\') {
							j++;
							def+="'\\"+value.charAt(j)+"'";
                        } else if (value.charAt(j)=='\'') {
                                def+="'\\"+value.charAt(j)+"'";
                        } else {
							def+="'"+value.charAt(j)+"'";
						}
					}
					def+="}";
				}
				def+="};\n";
				def=new Formatter().format("const ldc_jstring_t\t%s PROGMEM =  {%d,\t", key, len).toString()+def;
				CIDX.set(key, def, constrain);
			}
			return key;
        case Constants.CONSTANT_Float:
    		value=Float.toString(((ConstantFloat)cons[i]).getBytes());
    		if (value.equals("-Infinity")) {
				return "0";
    		} else if (value.equals("NaN")) {
				return "1";
    		} else if (value.equals("Infinity")) {
				return "2";
    		}
			key=CIDX.getConstKey("F", value);
			if (! CIDX.containsKey(key)) {
	    		totalConstLength+=4;
                String org=value;
                if (value.equals("3.4028234663852886E38")) value="FLT_MAX";
                if (value.equals("-3.4028234663852886E38")) value="-FLT_MAX";
                if (value.equals("1.401298464324817E-45")) value="FLT_MIN";
                if (value.equals("-1.401298464324817E-45")) value="-FLT_MIN";
                if (!value.equals(org)) {
                    org=" // "+org;
                } else {
                    org="";
                }
				CIDX.set(key, new Formatter().format("const ldc_jfloat_t\t%s PROGMEM =  {FLT_CNSTR(%sf)};%s\n", key, value, org).toString(), constrain);
			}
			return key;
        case Constants.CONSTANT_Double:
    		value=Double.toString(((ConstantDouble)cons[i]).getBytes());
    		if (value.equals("-Infinity")) {
				return "0";
    		} else if (value.equals("NaN")) {
				return "1";
    		} else if (value.equals("Infinity")) {
				return "2";
    		}
			key=CIDX.getConstKey("D", value);
			if (! CIDX.containsKey(key)) {
	    		totalConstLength+=4; // TODO: ist es wirklich immer 4 (evtl. 8)
	    		String org=value;
                if (value.equals("1.7976931348623157E308")) value="DBL_MAX";
                if (value.equals("-1.7976931348623157E308")) value="-DBL_MAX";
                if (value.equals("4.9E-324")) value="DBL_MIN";
                if (value.equals("-4.9E-324")) value="-DBL_MIN";
                if (value.equals("3.4028234663852886E38")) value="FLT_MAX";
                if (value.equals("-3.4028234663852886E38")) value="-FLT_MAX";
                if (value.equals("1.401298464324817E-45")) value="FLT_MIN";
                if (value.equals("-1.401298464324817E-45")) value="-FLT_MIN";
                if (!value.equals(org)) {
                    org=" // "+org;
                } else {
                    org="";
                }
				CIDX.set(key, new Formatter().format("const ldc_jdouble_t\t%s PROGMEM =  {DBL_CNSTR(%s)};%s\n", key, value, org).toString(), constrain);
			}
			return key;
        case Constants.CONSTANT_Integer:
    		value=Integer.toString(((ConstantInteger)cons[i]).getBytes());
    		if (HaikuDefs.getProperty("Mode").startsWith("HAIKU_16_") || HaikuDefs.getProperty("Mode").startsWith("HAIKU_8_")) {
                if (((ConstantInteger)cons[i]).getBytes()>0xffff || -((ConstantInteger)cons[i]).getBytes()>0xffff ) {
//                  throw new IndexOutOfBoundsException("int16_t size exceeded by "+((ConstantInteger)cons[i]).getBytes());
                    Verbose.warning("int16_t size exceeded: %d (0x%x)", ((ConstantInteger)cons[i]).getBytes(), ((ConstantInteger)cons[i]).getBytes());
                }
    		}
			key=CIDX.getConstKey("I", value);
			if (! CIDX.containsKey(key)) {
	    		totalConstLength+=2;
				CIDX.set(key, new Formatter().format("const ldc_jint_t\t%s PROGMEM =  {INT_CNSTR(%s)}; //0x%08x\n", key, value, ((ConstantInteger)cons[i]).getBytes()).toString(), constrain);
			}
			return key;
        case Constants.CONSTANT_Long:
    		value=Long.toString(((ConstantLong)cons[i]).getBytes());
			key=CIDX.getConstKey("L", value);
			if (! CIDX.containsKey(key)) {
	    		totalConstLength+=4;
				CIDX.set(key, new Formatter().format("const ldc_jlong_t\t%s PROGMEM =  {LONG_CNSTR(%sL)}; //0x%016xL\n", key, value, ((ConstantLong)cons[i]).getBytes()).toString(), constrain);
			}
			return key;
        case Constants.CONSTANT_Class:
            key="<"+((ConstantUtf8)cons[((ConstantClass)cons[i]).getNameIndex()]).getBytes()+">";
            Verbose.warning("CONSTANT_Class %d used: %s\n", i, ((ConstantUtf8)cons[((ConstantClass)cons[i]).getNameIndex()]).getBytes());
            //throw new ClassFormatException("should not reach this code point: " + cons[i].getTag());
            // <haikuvm/bench/SynchronizedThreads3> -> haikuvm_bench_SynchronizedThreads3__class
            return mangle(key);
        default:
			outc.printf("// %d: %s\n", i, ((ConstantUtf8)cons[((ConstantString)cons[i]).getStringIndex()]).getBytes());
            throw new ClassFormatException("unsupported byte tag in constant pool: " + cons[i].getTag());
		}
	}

	/**
	 *
	 * @param jcode -> 20:   invokevirtual	java.lang.StringBuilder.append (ID)Ljava/lang/StringBuilder;
	 * @return number of Parameters -> 3
	 */
	private int getMessageParamNo(String jcode) {
		String[] token= jcode.split("[ \t,]+", 9);
		String param=token[3];
		int params=0;
		int p=param.indexOf(')')-1;
		if (p!=0) {
			for (int i = 1; i <= p; i++) {
				if (param.charAt(i)=='L') {
					params++;
					for(;param.charAt(i)!=';'; i++) {
					}
				} else {
					if (param.charAt(i)=='/') ;
					else if (param.charAt(i)=='[') {
					} else if (param.charAt(i)=='D' || param.charAt(i)=='J')  {
						if (remapIt) {
							params+=1;
						} else {
							params+=2;
						}
					} else {
						params++;
					}
				}
			}
		}
		return params;
	}

	private String getConstIndex(String jcode) {
		if (!jcode.matches(".+[(]\\d+[)]$")) return null;
		return jcode.replaceAll(".+[(](\\d+)[)]$", "$1");
	}

	private String ccode(int high) {
		return "INT32("+high+"), ";
	}

	/**
	 *
	 * @param jcode = 20:   invokevirtual	java.lang.StringBuilder.append (I)Ljava/lang/StringBuilder;
	 * @return MSG_append_I
	 */
	private String getMessageSignature(String jcode) {
		String[] token= jcode.split("[ \t,]+", 9);
		String meth=token[2];
		int p=meth.lastIndexOf('.');
		if (p>=0) meth=meth.substring(p+1);
		return "MSG_"+mangle(meth+"_"+token[3]);
	}

	private String escape(String str) {
		String res="";
		for (int i = 0; i < str.length(); i++) {
			char c=str.charAt(i);
			switch(c) {
			case '\n': res+="\\n"; break;
			case '\r': res+="\\r"; break;
			case '\t': res+="\\t"; break;
			default: res+=c;
			}
		}
		return res;
	}

	/**
	 * HaikuVM simplifys/ignores array of array type
	 * Which is: forces it to (anonymous) <java.lang.Object>
	 *
	 * @param atype
	 * @return
	 * @throws IOException
	 */
	private String toArrayClass(String name) throws IOException {
		String def = "YB 1, YZ 1, YC 2, YS 3, YI 4, YJ 5, YF 6, YD 7, byte 1, boolean 1, char 2, short 3, int 4, long 5, float 6, double 7";
		String atype=name;
		String[] list=def.split("[ ,]+");
		atype=mangle(atype);
		// YYB -> YB
		// atype=atype.replaceAll("^[\\[Y]+", "Y");
//		atype=atype.replaceAll("_$", "");
		for (int i = 0; i < list.length; i+=2) {
			if (atype.equals(list[i])||atype.replaceAll("__class$", "").equals(list[i])) {
				atype=atype.replaceAll("__class$", "");
				String aname=i>=16?list[i-16]:atype;
				String jname=i>=16?"j"+list[i]:"j"+list[i+16];
				if (jname.equals("jchar")) jname="jchar8or16";
				outd.haikuArrayData(aname+"__class", jname);
				if (arraysAll.add(aname)) {
					arrays.add(aname+"__class");
				}
				ClassTable.classDeclaration(aname);
				return aname+"__class";
			}
		}
		outd.haikuArrayData(atype, "jobject");
		if (arraysAll.add(atype)) {
			arrays.add(atype);
		}
		ClassTable.classDeclaration(atype.replaceFirst("__class$", ""));
		return atype;
	}

	private void printArrayDecls() throws IOException {
		for (String atype : arrays) {
			outh.printf("extern const class_t %s PROGMEM;\n", atype);
		}
		arrays.clear();
	}

}
