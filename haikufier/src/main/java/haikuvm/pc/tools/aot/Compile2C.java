package haikuvm.pc.tools.aot;

import haikuvm.pc.tools.CollectedIncludes;
import haikuvm.pc.tools.HaikuVM;
import haikuvm.pc.tools.Msg2Meth;
import haikuvm.pc.tools.Verbose;
import haikuvm.pc.tools.haikuc.HaikuDefs;
import haikuvm.pc.tools.haikuc.PrintOnChange;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Formatter;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.bcel.Constants;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.Code;
import org.apache.bcel.classfile.Constant;
import org.apache.bcel.classfile.ConstantClass;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.classfile.Utility;

public class Compile2C extends HaikuVM implements Aot {
	private static HashSet<String> closure=new HashSet<String>();

	private String[] switchvalues;

	public Compile2C(String classname) {
		super(classname);
	}

	public void compile() throws IOException {
		if (classname.startsWith("[") || !closure.add(classname)) return;		
		
		String source=classpath+"/"+classname+".class";
		if (source==null) {
		    Verbose.warning("no .class file found for %s.java!\n", classname);
			return;
		}
		Verbose.printf("compile> %s", source);
		
		ClassParser a = new ClassParser(source);
		jc = a.parse();
		File cf=new File(HaikuDefs.getProperty("APP_BASE")+"/"+classname+".c");
		Verbose.printf("	into %s\n", cf.getPath());
		if (cf.getParentFile()!=null) cf.getParentFile().mkdirs();
		printJ2C();
		
		
		ConstantPool cp=jc.getConstantPool();
		Constant[] c=cp.getConstantPool();
		for (int i=0; i< c.length; i++) {
			if (c[i]==null) continue;
			if (c[i].getTag()==Constants.CONSTANT_Class) {
				ConstantClass cc= (ConstantClass)c[i];
				Compile2C jp=new Compile2C(cc.getBytes(cp));
				jp.compile();
			}
		}
	
	}

	public void printJ2C() throws IOException {
		msg2meth =new Msg2Meth();
		
		Method[] methods=jc.getMethods();
		outc=new PrintOnChange(new File(HaikuDefs.getProperty("APP_BASE")+"/"+filename(classname)+".c"));
		outh=new PrintOnChange(new File(HaikuDefs.getProperty("APP_BASE")+"/"+filename(classname)+".h"));
	
		outc.println("/*");
		outc.println(jc);
		outc.println("*/");
		outc.println();
		outc.println();
	
		ConstantPool cp=jc.getConstantPool();
		Constant[] c=cp.getConstantPool();
		for (int i=0; i< c.length; i++) {
			if (c[i]==null) continue;
			if (c[i].getTag()==Constants.CONSTANT_Class) {
				ConstantClass cc= (ConstantClass)c[i];
				String include=cc.getBytes(cp);
				if (!include.startsWith("[")) {
					String inc="#include \""+filename(include)+".h\"";
					outc.println(inc);
					CollectedIncludes.put(inc);
					File cf=new File(HaikuDefs.getProperty("APP_BASE")+"/"+filename(include)+".h");
					if (cf.getParentFile()!=null) cf.getParentFile().mkdirs();
					cf.createNewFile();
				}
			}
		}
	
		outc.println();
		outh.println();
		calcTypedef();
		
		outh.printf("extern class_t %s__class;\n", mangle(jc.getClassName()));
		outh.printf("\n");
		
		outc.println();
		outc.println();
		
		outc.println("/**");
		outc.println("void <new>()");
		outc.println("*/");
	
		String access = "access__"+"public"+"_";
		String methodName= process(jc.getClassName()+"."+"<new>");
		outh.printf("methodExtern(%s, %s, _V, 0, jobject, %d, %d, %d, %d)\n", access, methodName, 0, 0, 0, 0);
		outc.printf("methodBegin(%s, %s, _V, 0, jobject, %d, %d, %d, %d)\n", access, methodName, 0, 0, 0, 0);
		outc.println("{");				
		outc.printf("	return newInstance(sizeof(%s), & %s__class);\n", jc.getClassName(), jc.getClassName());
		outc.println("}");				
		for (int i = 0; i < methods.length; i++) {
			Method method=methods[i];
			outc.println();
			outc.println("/**");
			outc.println(method);
	
			Code code = method.getCode();
			if (code != null) { // Non-abstract method
				String str =code.toString(false);
				list=str.split("\n");
				for (int j = 0; j < list.length; j++) {
					String line=list[j];
					if (line.matches("^\\d+:.*")) {
						outc.println("*/");				
	
						access = "access__"+Utility.accessToString(method.getAccessFlags()).replace(' ', '_')+"_";
						String thisParam="+1";
						if (access.indexOf("static")>=0) {
							thisParam="";
						}
						methodName= process(jc.getClassName()+"."+method.getName())+", "+process(method.getSignature());
						String methodDesc=new Formatter().format("%s, %s, %d, %d, %d, %d%s", access, methodName, code.getMaxStack(), code.getMaxLocals(), code.getCode().length, method.getArgumentTypes().length, thisParam).toString();
						String meth[]=methodName.split("[, ]+");
						totalBClength+=code.getCode().length;
						if (methodName.indexOf(" main,")>=0) {
							haikuConfigh.printf("methodExtern(%s)\n", methodDesc);
							//haikuInit.printf("	{2, \"%s\", methodName(%s)}, // main\n", classname, methodDesc);
						}
						if (thisParam.length()>0) {
							msg2meth.put("MSG_"+meth[1]+"_"+meth[2], "methodName("+methodDesc+")");
						}
						if (methodName.indexOf(" _clinit_,")>=0) {
							haikuConfigh.printf("methodExtern(%s)\n", methodDesc);
							//haikuInit.printf("	{1, \"%s\", methodName(%s)}, // <clinit>\n", classname, methodDesc);
							haikuConfigc.printf("	 methodName(%s), // %s.<clinit>\n", methodDesc, classname);
						}
						outh.printf("methodExtern(%s)\n", methodDesc);
	
						outc.printf("methodBegin(%s)\n", methodDesc);
						outc.print("{\n");				
						outc.printf("		methodInit(%s);\n", methodDesc);
						outc.printf("		yield();\n");
						
						bytecode(j);
						break;
					} else {
						outc.println(line);
					}
				}
			}
			outc.print("}\n");				
	
		}
		
		outc.printf("\n");
		outc.printf("class_t %s__class = {\n", mangle(jc.getClassName()));
		outc.printf("	& %s__class,\n", mangle(jc.getSuperclassName()));
		msg2meth.printC(outc);
		outc.printf("};\n");
	
		outh.close();
		outc.close();
	}

	private void bytecode(int start) {
		String line;
		for (int k = start; k < list.length; k++) {
			line=list[k];
			if (line.matches("^\\d+:.*")) {
				outc.println(compileJ2C(list[k]));
				if (switchvalues!=null) {
					//[69:, tableswitch, default, 144, low, 1, high, 4, 100, 111, 122, 133]
					//[84:, lookupswitch, default, 172, npairs, 4, 31, 128, 73, 150, 94, 161, 10000, 139]
					if (switchvalues[1].equals("tableswitch")) {
						k=tableswitch(k+1, switchvalues.clone(), 0);
					} else {
						k=lookupswitch(k+1, switchvalues.clone(), 0);
					}
					switchvalues=null;
				}
			} else {
				break;
			}
		}
	}

	private String compileJ2C(String jcode) {
		String ccode=null;
		String yield="";
		String[] token= jcode.split("[ \t,]+", 9);
		if (token[1].equals("lookupswitch")) {
			//	0		1			2		3	4	5	6	7	8
			// 84:   lookupswitch	default = 172, npairs = 4 ((31, 128), (73, 150), (94, 161), (10000, 139))
			switchvalues= jcode.split("[ \t,()=]+");
			ccode=token[1]+"_()";
		} else if (token[1].equals("tableswitch")) {
			token= jcode.split("[ \t,(]+", 12);
			int p=jcode.indexOf('(');
			//	0		1			2		3	4	5  6 7	8    9 10
			// 69:   tableswitch	default = 144, low = 1, high = 4(100, 111, 122, 133)
			switchvalues= jcode.split("[ \t,()=]+");
			ccode=token[1]+"_()";
		} else if (token.length==2) {
			if (token[1].matches(".+_\\d")) {
				int p=token[1].lastIndexOf('_');
				ccode=token[1].substring(0, p)+"_("+token[1].substring(p+1)+")";				
			} else {
				ccode=token[1]+"_()";
			}
		} else if (token[2].startsWith("\"")) {
			ccode=token[1]+"_("+jcode.substring(jcode.indexOf('"'))+")";
		} else {
			ccode=token[1]+"_(";
			if (token.length>2) {
				ccode+=process(token[2]);
				if (token.length>3) {
					ccode+=", "+process(token[3]);
				} else {
					if (token[2].startsWith("#")) {
						//2:    if_icmpne		#46
						String[] jtoken= jcode.split("[ \t,:#]+", 3);
						//2    if_icmpne		46
						if (Integer.parseInt(jtoken[0])>=Integer.parseInt(jtoken[2])) 
							yield="yield(); ";					
					}
				}
			}
			ccode+=")";
		}
		//L16: 	invokespecial$(java_lang_StringBuilder, $init$, $Ljava_lang_String, jvoid); // 16:   invokespecial	java.lang.StringBuilder.<init> (Ljava/lang/String;)V
		//L20: 	invokevirtual$(java_lang_StringBuilder, append, $I, jobject);          // 20:   invokevirtual	java.lang.StringBuilder.append (I)Ljava/lang/StringBuilder;
		if (ccode.startsWith("invokevirtual")) {
			String[] p=ccode.split("[ ,]+");
			msg2meth.put("MSG_"+p[1]+"_"+p[2], null);
		}
		/*
		 * Korrigiere
		 * 63:   ldc		20	-> ildc$(20.0)
		 * 63:   ldc		20.0	-> fldc$(20.0)
		 * 52:   ldc		"Hello"	-> ldc$("Hello")
		 */
		if (ccode.startsWith("ldc_") && !ccode.startsWith("ldc_(\"")) {
			if (ccode.indexOf('.')<0) {
				ccode="i"+ccode;
			} else {
				ccode="f"+ccode;
			}
		}
		return new Formatter().format("L%s 	%-70s // %s", token[0], yield+ccode+";", jcode).toString();
	}

	/**
	 * [84:, lookupswitch, default, 172, npairs, 4, 31, 128, 73, 150, 94, 161, 10000, 139]
	 * @param clone
	 */
	private int lookupswitch(int idx, String[] params, int level) {
		HashMap<String, String> map = new HashMap<String, String>();
		String def=params[3];
		for (int i = 6; i < params.length; i+=2) {
			map.put("L"+params[i+1]+":", params[i]);
		}
		
		String ccode;
		switchvalues=null;
		outc.printf("		switch(itmp0) {;\n");
		for (; idx < list.length; idx++) {
			ccode=compileJ2C(list[idx]);
			if (ccode.startsWith("L"+def+":")) {
				outc.printf("		}\n");
				return idx-1;
			}
			String token[]=ccode.split("[ \t]");
			if (map.containsKey(token[0])) {
				outc.printf("			case %s:\n", map.get(token[0]));
			}
			outc.printf("%s\n", ccode);
			if (switchvalues!=null) {
				//[69:, tableswitch, default, 144, low, 1, high, 4, 100, 111, 122, 133]
				//[84:, lookupswitch, default, 172, npairs, 4, 31, 128, 73, 150, 94, 161, 10000, 139]
				if (switchvalues[1].equals("tableswitch")) {
					idx=tableswitch(idx+1, switchvalues.clone(), level+1);
				} else {
					idx=lookupswitch(idx+1, switchvalues.clone(), level+1);
				}
			}
		}
		return idx;
	}

	/**
	 * [69:, tableswitch, default, 144, low, 1, high, 4, 111, 100, 144, 133]
	 * @param clone
	 */
	private int tableswitch(int idx, String[] params, int level) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		String def=params[3];
		for (int i = 8, s=Integer.parseInt(params[5]); i < params.length; i++, s++) {
			if(!params[i].equals(def)) {
				map.put("L"+params[i]+":", s);
			}
		}
		
		String ccode;
		switchvalues=null;
		outc.printf("		switch(itmp0) {;\n");
		for (; idx < list.length; idx++) {
			ccode=compileJ2C(list[idx]);
			if (ccode.startsWith("L"+def+":")) {
				outc.printf("		}\n");
				return idx-1;
			}
			String token[]=ccode.split("[ \t]");
			if (map.containsKey(token[0])) {
				outc.printf("			case %d:\n", map.get(token[0]));
			}
			outc.printf("%s\n", ccode);
			if (switchvalues!=null) {
				//[69:, tableswitch, default, 144, low, 1, high, 4, 100, 111, 122, 133]
				//[84:, lookupswitch, default, 172, npairs, 4, 31, 128, 73, 150, 94, 161, 10000, 139]
				if (switchvalues[1].equals("tableswitch")) {
					idx=tableswitch(idx+1, switchvalues.clone(), level+1);
				} else {
					idx=lookupswitch(idx+1, switchvalues.clone(), level+1);
				}
			}
		}
		return idx;
	}

	/**
	 * (Ljava/lang/String;)Ljava/lang/StringBuilder; -> $Ljava_lang_StringBuilder, 1, jobject 
	 * (Ljava/lang/String;ID)V -> $Ljava_lang_StringBuilder$ID, 3, jobject
	 * ()Ljava/lang/StringBuilder; -> V, 0, jobject 
	 * (ID)Ljava/lang/StringBuilder; ->I, 2, jobject
	 * 
	 * <java.lang.StringBuilder> -> java_lang_StringBuilder
	 * java.lang.StringBuilder.<init> -> java_lang_StringBuilder, $init$
	 * 
	 * java.lang.StringBuilder.append -> java_lang_StringBuilder, append
	 *  
	 * @param param
	 * @return
	 */
	private String process(String param) {
		if (param.startsWith("#")) return "L"+param.substring(1);
		if (param.startsWith("%")) return param.substring(1);
		if (param.startsWith("(")) {
			int params=0;
			int p=param.indexOf(')')-1;
			String ccode="_";
			if (p==0) {
				ccode+="V";
			} else {
				for (int i = 1; i <= p; i++) {
					if (param.charAt(i)=='L') {
						params++;
						for(;param.charAt(i)!=';'; i++) {
							if (param.charAt(i)=='/') ccode+="_";
							else if (param.charAt(i)=='[') ccode+="Y";
							else ccode+=param.charAt(i);
						}
						if (i<p) ccode+="_";
					} else {
						if (param.charAt(i)=='/') ccode+="_";
						else if (param.charAt(i)=='[') ccode+="Y";
						else {
							params++;
							ccode+=param.charAt(i);
						}
					}
				}
			}
			return ccode+", "+params+", "+signature2JNI(param.substring(p+2));
		}
		if (param.startsWith("<")) return param.replace("<", "").replace(">", "").replace(".", "_");
		
		try {
			Double.parseDouble(param);
		} catch (NumberFormatException e) {
			int p=param.lastIndexOf('.');
			if (p>=0) {
				String ccode=param.substring(p+1);
				return param.substring(0, p).replace('.', '_')+", "+ccode.replace("<", "_").replace(">", "_");
			}
		}
		return param;
	}

}
