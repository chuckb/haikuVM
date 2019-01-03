package haikuvm.pc.tools;

import haikuvm.pc.tools.haikuc.HaikuClasses2H;
import haikuvm.pc.tools.haikuc.HaikuByteCode2H;
import haikuvm.pc.tools.haikuc.HaikuByteCodeTypes2H;
import haikuvm.pc.tools.haikuc.HaikuDefs;
import haikuvm.pc.tools.haikuc.HaikuJNI;
import haikuvm.pc.tools.haikuc.PrintOnChange;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Formatter;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.Vector;

import org.apache.bcel.classfile.ClassFormatException;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.util.ClassPath;
import org.apache.bcel.util.SyntheticRepository;


public class Haikufy {

    public static Set<Condition> conditions= new HashSet<Condition>();
	protected static String classpath="";

	static String bootclasspath="";

	protected static PrintOnChange haikuConfigh;

	protected static PrintOnChange haikuConfigc;

	protected JavaClass jc;

	protected PrintOnChange outc;

	protected HaikuClasses2H outd;

	protected PrintOnChange outh;

	protected String classname;

	protected Msg2Meth msg2meth;

	protected String[] list;

	protected static int totalMethods=0;
	protected static int totalBClength=0;

	public static int totalConstLength;

	public static int totalClassesLength;

    static boolean clean=false;
    protected static boolean singleFile=true;

	/**
	 * Needed for to follow static inheritence (see JUnit JustFields0)
	 */
	protected Hashtable<String, String> staticMap= new Hashtable<String, String>();

	protected static String exceptionTable="";
	protected static FunctionTable functionTable= new FunctionTable();

	protected static Vector<Reason> classnames;

	protected static String mainclass;

	protected static String mainOsThread;

	public static String source;

    protected static HaikuJNI haikuJNIc;
    private static boolean hasWrittenHaikuConfig;

    public static boolean HAIKU_GenerateFullVM = false;

	public Haikufy(String classname) {
		this.classname=classname.replace('.', '/');
	}

	static private class TypedefInfo {
		int level;
		String sig, obj, var;
		String info;
	}

	protected String calcTypedef() throws IOException {
		String res="";
		Vector<TypedefInfo> list=new Vector<TypedefInfo>();

		Set<String> set=new HashSet<String>();

		calcFields(jc, 0, list);
		res+="\n";
		if (list.size()>0) {
	        res+="typedef struct "+mangle(jc.getClassName())+" {\n";
			for (int i = list.size()-1; i >=0; i--) {
				TypedefInfo t=list.get(i);
				if (set.add(t.var)) {
					t.info=t.var;
				} else {
					t.info=t.obj+"_"+t.var;
				}
			}
			for (int i = 0; i <list.size(); i++) {
				TypedefInfo t=list.get(i);
				res+=new Formatter().format("  %-10s %s; //%s\n", signature2JNI(t.sig), mangleField(t.info), t.sig).toString();
			}
	        res+="} "+mangle(jc.getClassName())+";\n";
            res+="#define SIZEOF_"+mangle(jc.getClassName())+" sizeof("+mangle(jc.getClassName())+")\n";
		} else {
            res+="#define SIZEOF_"+mangle(jc.getClassName())+" 0\n";
		}
		res+="\n";
		res+="\n";
		return res;
	}

	private void calcFields(JavaClass localjc, int level, Vector<TypedefInfo> list) throws IOException {

		JavaClass parent = getClassFile(localjc.getSuperclassName());

		if (!parent.getClassName().equals(localjc.getClassName())) {
			calcFields(parent, level+1, list);
		}

		Field[] fields=localjc.getFields();
		for (int i = 0; i < fields.length; i++) {
			TypedefInfo t= new TypedefInfo();
			t.level=level;
			t.sig=fields[i].getSignature().toString();
			t.var=fields[i].getName();
			t.obj=localjc.getClassName();
			if (fields[i].isStatic()) {
				String target=new Formatter().format("%s.%s", t.obj, t.var).toString();
				String key;
				if (level==0) {
					key=target;
				} else {
					key=new Formatter().format("%s.%s", jc.getClassName(), t.var).toString();
				}
				staticMap.put(key, target);
			} else {
				list.add(t);
			}
		}
	}

	/**
	 * I -> jint
	 * int -> jint
	 * Ljava/lang/String; -> jstring
	 *
	 * @param sig
	 * @return
	 */
	public static String signature2JNI(String sig) {
		String[] map={"L", "jobject", "I", "jint", "J", "jlong", "Z", "jboolean", "S", "jshort", "V", "jvoid", "D", "jdouble", "F", "jfloat", "C", "jchar", "B", "jbyte" };
        if (sig.equals("Ljava/lang/String;")) {
            return "jstring";
        }
        if (sig.equals("[Ljava/lang/String;")) {
            return "jstringArray";
        }
        if (sig.startsWith("[[")) {
            return "jobjectArray";
        }
		String c=sig.substring(0, 1);
		if (sig.charAt(0)=='[')
			c=sig.substring(1, 2);
		for (int i = 0; i < map.length; i+=2) {
			if (map[i].equals(c) || map[i+1].equals("j"+sig)) {
				if (sig.charAt(0)=='[') {
	                if (map[i+1].equals("jchar")) {
	                    return "jchar8or16Array";
	                } else {
	                    return map[i+1]+"Array";
	                }
				}
				return map[i+1];
			}
		}
		return sig;
	}

	public static String mangle(String java) {
		String mangled= java;
		mangled= mangled.replaceAll("(^<)|([;>]$)", "");
		mangled= mangled.replaceAll("[)(\\./$<>;]", "_");
		mangled= mangled.replaceAll("\\[", "Y");

		//<java.lang.Double>
		if (java.startsWith("<") || java.endsWith(">")) return mangled+"__class";

		if(mangled.endsWith(mainOsThread.replace('.', '_').replace('/', '_')+"_main_YLjava_lang_String_V"))
			mangled="main_YLjava_lang_String";
		if(mangled.endsWith(mainOsThread.replace('.', '_').replace('/', '_')+"_panic_IIV"))
			mangled="panic_IIV";

		if (!mangled.endsWith("_exceptionCode") && !mangled.endsWith("_exceptionArg") )
			if (mangled.startsWith("haiku_vm_HaikuMagic")) mangled=mangled.replace("haiku_vm_HaikuMagic", mangle(mainclass));

		return mangled;
	}

	/**
	 * a.b.xyz -> a.b.xyz<br>
	 * a.b.XYZ -> a.b.jfXYZ<br>
	 * because we don't want to collide with C-#defines
	 * which are usualy upper case.
	 *
	 * @param field
	 * @return
	 */
	protected static String mangleField(String field) {
        String prefix=field.replaceFirst("(.*)\\.(.*)", "$1");
        String suffix=field.replaceFirst("(.*)\\.(.*)", "$2");
	    if (suffix.equals(suffix.toUpperCase())) {
	        return mangle(prefix+".jf"+suffix);
	    }
		return mangle(field);
	}

	/**
	 *  '<[Lhaikuvm.bench.EnumTest1$Day;>' -> 'YLhaikuvm_bench_EnumTest1_Day_'
	 *
	 * @return the file/path of this class name
	 */
	protected static String filename(String java) {
		String mangled= java;
		mangled= mangled.replaceAll("(^<)|([;>]$)", "");
		mangled= mangled.replaceAll("[)(.$<>;]", "_");
		mangled= mangled.replaceAll("\\[", "Y");
		return mangled;
	}

	/**
	 *
	 * @param jcode = 16:   invokespecial	java.lang.StringBuilder.<init> (Ljava/lang/String;)V
	 * @return java_lang_StringBuilder__init___Ljava_lang_String
	 */
	protected static String getMethodSignature(String jcode) {
		String[] token= jcode.split("[)( \t,]+", 9);
		//16:   invokespecial	java.lang.StringBuilder.<init> Ljava/lang/String; V
		//1:    invokespecial	java.lang.Object.<init> ()V (14)
		//1 bc java.lang.StringBuilder.<init> (Ljava/lang/String;)V
		//1 bc haiku.avr.lib.arduino.ArduinoLib.init ()V
		String meth=token[2]+"_"+token[3]+((token.length==7 || (token[0].equals("1") && token.length>4))?token[4]:"");
		return mangle(meth);
	}

	static void adjustClasspathAndMainClass() throws IOException {
		JavaClass jc =getClassFile(mainclass);

		String pack = jc.getClassName().replace(".", "/");
		pack=new File(pack).getPath().replace("\\", "/");
		if (!mainclass.equals(pack)) {
			try {
				jc =getClassFile(pack);
			} catch (FileNotFoundException e) {
                try {
                    classpath+=";"+mainclass.substring(0, mainclass.length()-pack.length());
                } catch (Exception e1) {
                    throw new FileNotFoundException(e+"\nClass files are searched with respect to their package prefix. Please, consider to change the current directory or classpath.");
                }
			}
			mainclass=pack;
		}
	}


    protected static void deleteAllFiles(File dir, boolean clean) throws IOException {
		if (! dir.isDirectory() || dir.getName().startsWith(".")) return;
		for(File f: dir.listFiles()) {
			deleteAllFiles(f , clean);
			if(f.getName().endsWith(".h") || f.getName().endsWith(".c") || f.getName().endsWith(".cpp") || f.getName().endsWith(".o") || f.getName().endsWith(".d")) {
				if (clean) {
					f.delete();
				} else {
//				    keep thsi file but zero it (convenient for VC)
//					if (f.length()!=0) {
//						f.delete();
//						f.createNewFile();
//					}
				}
			}
		}
		if(clean) dir.delete();
	}

	static JavaClass xgetClassFile(String classname) throws IOException {
		String path[]=(bootclasspath+System.getProperty("path.separator")+classpath).split("[;"+System.getProperty("path.separator")+"]+");
		String classpath="";
		for (int i = 0; i < path.length; i++) {
			classpath+=path[i]+System.getProperty("path.separator");
		}

		try {
			ClassPath classPath = new ClassPath(classpath);

			String source;
			if (new File(classname).isAbsolute()) {
				source=classname;
			} else {
				source=classname.replace('.', '/');
			}
			JavaClass jc=SyntheticRepository.getInstance(classPath).loadClass(source);
			return jc;
		} catch (ClassNotFoundException e) {
			throw new FileNotFoundException(new Formatter().format("File: '%s' not found in bootclasspath='%s' and classpath='%s'", classname, bootclasspath, classpath).toString());
		}
	}

	static public JavaClass getClassFile(String classname) throws IOException {
		String path[]=(bootclasspath+System.getProperty("path.separator")+classpath).split("[;"+System.getProperty("path.separator")+"]+");
		String classp="";
		for (int i = 0; i < path.length; i++) {
			classp+=path[i]+System.getProperty("path.separator");
		}
		ClassPath classPath = new ClassPath(classp);

		try {
            if (new File(classname).isAbsolute()) {
            	source=classname+".class";
            	ClassParser cp = new ClassParser(source);
            	JavaClass jc=cp.parse();
            	jc.setRepository(SyntheticRepository.getInstance(classPath));
            	return jc;
            }
        } catch (NullPointerException e1) {
        } catch (ClassFormatException e1) {
        }
        classname=classname.replace('.', '/').replace('\\', '/')+".class";
        String[] struct = classname.split("/");
        for (int c = 0; c < struct.length; c++) {
            String classname0=struct[c];
            for (int b = c+1; b < struct.length; b++) {
                classname0+="/"+struct[b];
            }
            for (int i = 0; i < path.length; i++) {
                if (path[i].endsWith(".jar") || path[i].endsWith(".zip")) {
                    source=path[i];
                    if (new File(source).exists()) {
                        try {
                            ClassParser cp = new ClassParser(source, classname0);
                            JavaClass jc=cp.parse();
                            jc.setRepository(SyntheticRepository.getInstance(classPath));
                            return jc;
                        } catch (Exception e) {
                        }
                    }
                } else {
                    source=path[i]+"/"+classname0;
                    if (new File(source).exists()) {
                        try {
                            ClassParser cp = new ClassParser(source);
                            JavaClass jc=cp.parse();
                            jc.setRepository(SyntheticRepository.getInstance(classPath));
                            return jc;
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }

		throw new FileNotFoundException(new Formatter().format("Current directory is: "+new File(".").getAbsolutePath()+"\nFile: '%s' not found in bootclasspath='%s' and classpath='%s'", classname, bootclasspath, classpath).toString());
	}

	/**
	 * Target.PanicSupport!=0 ==> let live<br>
	 *
	 * Target.PanicSupport==0 && pattern.indexOf(".panic")>=0 ==> wipe out
	 *
	 * @param pattern
	 * @return
	 */
	public static boolean supressPanicSupport(String pattern) {
		int es=0;
		if (HaikuDefs.getProperty("PanicSupport").startsWith("0x")) {
			es=Integer.parseInt(HaikuDefs.getProperty("PanicSupport").substring(2), 16);
		} else {
			es=Integer.parseInt(HaikuDefs.getProperty("PanicSupport"));
		}
		if (es!=0) return false;
		return pattern.indexOf(".panic")>=0;
	}

}
