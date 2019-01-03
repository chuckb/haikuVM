package haikuvm.pc.tools;
import haikuvm.pc.tools.haikuc.HaikuDefs;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import org.apache.bcel.Constants;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.Code;
import org.apache.bcel.classfile.CodeException;
import org.apache.bcel.classfile.Constant;
import org.apache.bcel.classfile.ConstantClass;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;


public class Closure {

	private String classname;
    private static Set<String> distinctBCs = new HashSet<String>();
	private static SortedSet<String> rescan=new TreeSet<String>();
	static private Set<String> mark=new HashSet<String>();
	static private Set<String> virtual=new HashSet<String>();
	static private Vector<Reason> closure=new Vector<Reason>(); // all referenced classes of the source


	public static int getDistinctBCs() {
	    return distinctBCs.size();
	}

    public static boolean contains(String classname) {
        for (Reason r : closure) {
            if (r.getClassname().equals(classname)) 
                return true;
        }
        return false;
    }

	public Closure(Reason reason) {
		this.classname=reason.getClassname();
		if (contains(classname)) return;
		closure.add(reason.useit());
	}

	public static void root(String classname) throws IOException {
        Verbose.println();
        Verbose.println("#################### closure start on "+classname);
		new Closure(new Reason(classname, "root call with "+classname)).collectClass("");
		
		// Don't forget this class because is needed in function ldc()
		new Closure(new Reason("java.lang.String", "root call")).collectClass("");
		
		for (int i=0; i==0 || !rescan.isEmpty(); i++) {
		    if(!rescan.isEmpty()) {
	            Verbose.println("#### rescan because:");
	            for (String reason : rescan) {
	                Verbose.println("###### "+reason);
	            }
	            rescan.clear();
		    }
			
			Vector<Reason> closure0=(Vector<Reason>)(closure.clone());
			for (Reason r : closure0) {
				//Haikout.println(cn);
				new Closure(new Reason(r.getClassname(), "root rescan")).collectClass("  ");
			}
		}
		Verbose.println("#################### closure complete!");
		Verbose.println();
		Verbose.println();
	}

	private void collectClass(String level) throws IOException {
		collectFoos(level);
	}

	private void collectFoos(String level) throws IOException {
		if (classname.startsWith("[")) return;
		
		JavaClass jc = HaikuVM.getClassFile(classname);

		Method[] methods=jc.getMethods();
		for (int i = 0; i < methods.length; i++) {
			Method method=methods[i];
			MethInfo mi=new MethInfo(jc.getClassName()+"."+method.getName(), method.getSignature());
            if (method.isAbstract()) continue;

			if (mark.contains(mi.getInclude()) || method.getName().equals("<clinit>") || virtual.contains(method.getName()+method.getSignature())) {
				if (mark.add(mi.getInclude())) {
					Verbose.println("included# "+level+mi.getInclude());
					String methodName= HaikuVM.getMethodSignature("1 bc "+mi.getInclude());
					HaikuVM.functionTable.callOf(methodName);
				}
				if (method.getName().equals("<clinit>")) {
					new Clinit(classname, level.length());
				}
	            if (method.isNative() ) {
	                continue;
	            }
				Code code= method.getCode();
				String str =code.toString(true);
				String[] list=str.split("\n");
				for (int k = 0; k < list.length; k++) {
					String jcode=list[k];
					String[] token= jcode.split("[ \t,]+", 9);
					if (token[0].endsWith(":")) { //[39:, iconst_0]
					    distinctBCs .add(token[1]);
					}
					if (token.length>=5 && token[1].startsWith("invoke")) {
						mi=new MethInfo(token[2], token[3]);

						if (token[1].equals("invokestatic") || token[1].equals("invokespecial")) {
							String classnameSaved = mi.getClassname();
							if (token[1].equals("invokestatic")) mi=MethInfo.findRealMethod(token[2], token[3]);
							
                            if (token[2].equals("java.lang.Thread.fork") || token[2].equals("java.lang.Thread.haikuReleaseLock")) {
                                HaikuDefs.setProperty("Threads", "1");
                            }
                            
							if (!(HaikuVM.supressPanicSupport(token[2]))) {
								HaikuVM.functionTable.callOf(mi.getLongName());
							}
							if (!HaikuVM.supressPanicSupport(token[2]) && mark.add(mi.getInclude())) {
								Verbose.println("included: "+level+mi.getInclude());
								HaikuVM.functionTable.callOf(mi.getLongName());
								rescan.add("usage of message --> "+mi.getInclude());
							}
							if (!contains(classnameSaved)) {
                                rescan.add("usage of new Class (1 bc) --> "+classnameSaved);
								new Closure(new Reason(classnameSaved, classname+": "+method+"; 1 bytecode "+token[1])).collectClass(level+"  ");
							}
							if (!contains(mi.getClassname())) {
                                rescan.add("usage of new Class (2 bc) --> "+mi.getClassname());
								new Closure(new Reason(mi.getClassname(), classname+": "+method+"; 2 bytecode "+token[1])).collectClass(level+"  ");
							}
						} else if (token[1].equals("invokevirtual") || token[1].equals("invokeinterface")) {
							if (virtual.add(mi.getShortName())) {
								Verbose.println("virtual : "+level+mi.getShortName());
                                rescan.add("usage of (invokevirtual || invokeinterface) for message --> "+mi.getShortName());
							}
							if (!contains(mi.getClassname())) {
                                rescan.add("usage of (invokevirtual || invokeinterface) with new Class --> "+mi.getClassname());
								new Closure(new Reason(mi.getClassname(), classname+": "+method+"; bytecode "+token[1])).collectClass(level+"  ");
							}
						}
					} else if (token.length>=5 && (token[1].equals("getstatic") || token[1].equals("setstatic"))) {
					    // value
					    // haiku.avr.AVRConstants.TCCR0A -> haiku.avr.AVRConstants
					    String classname = token[2].substring(0, token[2].lastIndexOf('.'));
                        if (!contains(classname)) {
                            rescan.add("usage of (getstatic || setstatic) with new Class --> "+classname);
                            new Closure(new Reason(classname, this.classname+": "+method+"; bytecode "+token[1])).collectClass(level+"  ");
                        }
                        // type
                        // Lhaiku/avr/AVRConstants; -> haiku.avr.AVRConstants
                        if (token[3].startsWith("L")) {
                            String type = token[3].substring(1, token[3].lastIndexOf(';')).replace('/', '.');
                            if (!contains(type)) {
                                rescan.add("usage of (getstatic || setstatic) with new type --> "+type);
                                new Closure(new Reason(type, this.classname+": "+method+"; type bytecode "+token[1])).collectClass(level+"  ");
                            }
                        }
                    } else if (token.length>=4 && (token[1].equals("checkcast") || token[1].equals("instanceof"))) {
                        // <haikuvm.bench.from.darjeeling.testvm.classes.B> -> haikuvm.bench.from.darjeeling.testvm.classes.B
                        String classname = token[2].substring(1, token[2].length()-1);
                        if (!contains(classname)) {
                            rescan.add("usage of (checkcast || instanceof) with new Class --> "+classname);
                            new Closure(new Reason(classname, this.classname+": "+method+"; bytecode "+token[1])).collectClass(level+"  ");
                        }
					}
				}

				collectExceptions(jc, code, level);
			} else	if (level.length()==0 && (method.getName()+method.getSignature()).equals("main([Ljava/lang/String;)V")) {
				if (mark.add(mi.getInclude())) {
					String methodName= HaikuVM.getMethodSignature("1 bc "+mi.getInclude());
					HaikuVM.functionTable.callOf(methodName);
					Verbose.println("included; "+mi.getInclude());
					new Closure(new Reason(jc.getClassName(), classname+"."+"main([Ljava/lang/String;)V forced!")).collectClass(level+"  ");
					return;
				}
			}
		}
		try {
			if (jc.getSuperClass()!=null) {
				String superclass=jc.getSuperClass().getClassName();
				if (!contains(superclass)) {
					new Closure(new Reason(superclass, classname+": superclass closure")).collectClass(level+"  ");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    private void collectExceptions(JavaClass jc, Code code, String level) {
		CodeException[] et = code.getExceptionTable();
		ConstantPool cp=jc.getConstantPool();

		for (int i = 0; i < et.length; i++) {
			int ct=et[i].getCatchType();
			if (ct==0) {
			} else {
				String ex=cp.constantToString(ct, Constants.CONSTANT_Class);
				if (!contains(ex))
					try {
						new Closure(new Reason(ex, classname+": exception closure")).collectClass(level+"  ");
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}
	}

	/**
	 * 
	 * @param key
	 * @return true if key is found<br>
	 * key in method name eg. 
	 * <li>
	 * java.lang.StringBuilder.&lt;init&gt;(Ljava/lang/String;)V
	 * java.lang.System.identityHashCode(Ljava/lang/Object;)I
	 * </li> 
	 */
	public static boolean isMarked(String key) {
		return mark.contains(key);
	}

    public static int size() {
        return closure.size();
    }

    public static Reason get(String classname) {
        classname=classname.replace('/', '.');
        for (Reason r : closure) {
            if (r.getClassname().equals(classname)) 
                return r;
        }
        return null;
    }

}
