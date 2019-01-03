import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;


public class ConvertWiki {
	

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		//bytecodeDef();
		
		//bytecodeExtern();
		
		//bytecodeFinals();
		//bytecodeSwitch();
		//bytecodeFunctions();
		//bytecodeFunctionsC();
		//getAVRconst();
        bytecode4Haiku();
	    //extractCMDExamples();
        //augmentBytecodes_h();
        //ifDefinedBytecodes_h();
	}

	private static void getAVRconst() throws FileNotFoundException, IOException {
		String line=null;
		Vector<String> sig1=new Vector<String>();
//		BufferedReader bi= new BufferedReader(new FileReader("../haikuBench/src/main/java/robotlib/asuro/AsuroLib2_8_0.java"));
		BufferedReader bi= new BufferedReader(new FileReader("../haikuBench/src/main/java/robotlib/arduino/ArduinoLib.java"));
		for(int i=1; (line=bi.readLine())!=null; i++) {
			String list[]=line.split("\\s+");
			if(line.startsWith("//")) continue;
			if(list.length<7) continue;
			
			//private static final int REFS1 = 0;
			if(list[3].equals("final")) {
				System.out.printf("public static final %-7s STR(%s)\t= %s;\n", list[4], list[5], list[5]);
			}
		}
	}

	private static void augmentBytecodes_h() throws FileNotFoundException, IOException {
		Map<String, String> bc2bc = mapBCs(false);
		Map<String, String> bc2constrain = bytecode4Haiku(bc2bc, false);
		int applied=0;
		String line;
		BufferedReader bcc= new BufferedReader(new FileReader("../haikuVM/Bytecodes.h"));
		for(int i=1; (line=bcc.readLine())!=null; i++) {
			if(line.startsWith("BEGCODE")) {
				System.out.println(line);

				String[] list=line.split("[ ()\t]+");
				String foo=list[1].toLowerCase();
				String constraint = bc2constrain.get(foo);
				for(; (line=bcc.readLine())!=null; i++) {
					if (constraint!=null) {
						list=line.split("[\\s*=;]+");
						if (list.length>=3 && list[0].length()==0 && list[1].matches("\\w+") && list[2].matches("\\w+")) {
							//char* obj=cppop();
						} else {
							System.out.println("\tCONSTRAIN0("+constraint+");");
							applied++;
							constraint=null;
						}
					}
					System.out.println(line);
					if(line.startsWith("ENDCODE")) break;
				}
			} else {
				System.out.println(line);
			}
		}
		//System.out.println(applied+" "+bc2constrain.size());
	}
	
	private static void ifDefinedBytecodes_h() throws FileNotFoundException, IOException {
		String line;
		BufferedReader bcc= new BufferedReader(new FileReader("../haikuVM/Bytecodes.h"));
		for(int i=1; (line=bcc.readLine())!=null; i++) {
			if(line.startsWith("BEGCODE")) {
				String[] list=line.split("[ ()\t]+");
				String foo=list[1].toUpperCase();
				System.out.println("#if HAIKU_AOTVariant != HAIKU_AOTBytecodeAsSwitch || defined(HBC_DEF_"+foo+")");
				System.out.println(line);
			} else if(line.startsWith("ENDCODE")) {
				System.out.println(line);
				System.out.println("#endif");
			} else {
				System.out.println(line);
			}
		}
		//System.out.println(applied+" "+bc2constrain.size());
	}
	
	/**
	 * Generate static{..} code snippet for class BC2IDX
	 * 
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void bytecode4Haiku() throws FileNotFoundException, IOException {
		bytecode4Haiku(mapBCs(true), true);
	}
	
	private static Map<String, String> bytecode4Haiku(Map<String, String> bc2bc, boolean print) throws FileNotFoundException, IOException {
		Map<String, String> bc2constrain=new Hashtable<String, String>();
		String line=null;
		//if (line==null) throw new IOException();
		int bc=0;
		int mode=0;
		Vector<String> sig1=new Vector<String>();
		BufferedReader bi= new BufferedReader(new FileReader("src/test/java/haiku_bytecode.txt"));
		for(int i=1; (line=bi.readLine())!=null; i++) {
			String list[]=line.split("\t");
			if(line.startsWith("#")) continue;
			if(line.startsWith("CONSTRAINS:")) { mode=1; continue; }
			if(line.startsWith("BYTECODES:")) { mode=2; continue; }
			if(list.length<2) continue;
			
			if (mode==1) {
				list=line.split("[\t ,]+");
				bc2constrain.put(list[0], list[1]);
				
			} else if (mode==2) {
				if(list[1].length()!=2) continue;
				//if(list[0].equals("xxxunusedxxx")) continue;

				String param="0";
				String info=list[2];
				String len="0";
				if(info.length()>0) {
					len=info.split(":")[0];
					if (len.length()==1) {
						String paraml[]=info.substring(3).split("[ ,]+");
						for (int j = 0; j < paraml.length; j++) {
							if (paraml[j].matches(".*\\d")) {
								paraml[j]=paraml[j].replaceFirst(".*(\\d)", "$1");
							} else {
								paraml[j]="9";
							}
						}
						param=Arrays.toString(paraml);
					} else {
						len="0";
					}
				}
				if(!sig1.contains(param)) sig1.add(param);
				String constraint=bc2constrain.get(list[0]);
				if (print) System.out.printf("\thaikucode(%-20s, %-20s, 0x%s, %s, %d, %s, %d); // %s\n", "\""+list[0]+"\"", "\""+bc2bc.get(list[0].toUpperCase())+"\"", list[1], len, sig1.indexOf(param), constraint, bc,param+"# "+info);
				bc++;
			}
			
		}
		return bc2constrain;
	}

	/**
	 * 
	 * Looks for equal Bytecode implementations in "../haikuVM/Bytecodes.h".
	 * E.g. ALOAD_0 == ALOAD_1 == ALOAD_2 == ALOAD_3
	 * Therefore they are all mapped to ALOAD_0
	 * 
	 * Mostly this means C variable "bc" is used to make an online distinction.
	 * 
	 * If Bytecode implementation contains only a call of "unimplemented();"
	 * E.g. JSR
	 * Then: JSR -> // which is empty String ""
	 * 
	 * 
	 * @return maps e.g. ALOAD_2 -> ALOAD_0 or JSR -> 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static Map<String, String> mapBCs(boolean print) throws FileNotFoundException, IOException {
		Map<String, String> bc2bc=new Hashtable<String, String>();
		Map<String, String> code2bc=new Hashtable<String, String>();
		String line;
		BufferedReader bcc= new BufferedReader(new FileReader("../haikuVM/Bytecodes.h"));
		for(int i=1; (line=bcc.readLine())!=null; i++) {
			if(!line.startsWith("BEGCODE")) continue;
			String list[]=line.split("[ ()\t]+");
			String foo=list[1];
			String body="";
			for(; (line=bcc.readLine())!=null; i++) {
				if(line.startsWith("\tCONSTRAIN")) continue;
				if(line.startsWith("ENDCODE")) break;
				body+=line;
			}
			String map=foo;
			if (body.indexOf("unimplemented")>=0 && body.indexOf("unimplemented")<50) {
				body="	unimplemented();";
				map="";
			} else {
				String strip=body.replaceAll("[ \t();]+", "");
				if (strip.startsWith("OPF_")) strip=strip.substring(4);
				if(code2bc.containsValue(strip)) {
					map=strip;
				} else {
					if(code2bc.containsKey(body)) {
						map=code2bc.get(body);
					} else {
						code2bc.put(body, foo);
					}
				}
			}
			bc2bc.put(foo, map);
			if (print) System.out.printf("%-20s -> %s\n", foo, map);
			//System.out.printf("%s\n", body);
		}
		String key="";
		String map=null;
		while(key!=null) {
			for (String k : bc2bc.keySet()) {
				if (!k.equals(bc2bc.get(k))) {
					key=bc2bc.get(k);
					if (!key.equals(bc2bc.get(key)) && bc2bc.get(key)!=null) {
						map=bc2bc.get(key);
						key=k;
						break;
					}
				}
				// key == map(key) && map(key) == map(map(key))
				key=null;
			}
			if (key!=null) bc2bc.put(key, map);
		}
		return bc2bc;
	}

	private static void bytecodeDefines() throws FileNotFoundException, IOException {
		BufferedReader bi= new BufferedReader(new FileReader("src/test/java/bytecode.txt"));
		String line;
		for(int i=1; (line=bi.readLine())!=null; i++) {
			String list[]=line.split("\t");
			if(list[1].length()==2) {
				String len=list[2];
				if(len.length()>0) len=" // "+len;
				System.out.printf("#define %-20s 0x%s%s\n", "OP_"+list[0].toUpperCase(), list[1], len);
			}
		}
	}

	private static void bytecodeFinals() throws FileNotFoundException, IOException {
		BufferedReader bi= new BufferedReader(new FileReader("src/test/java/bytecode.txt"));
		String line;
		for(int i=1; (line=bi.readLine())!=null; i++) {
			String list[]=line.split("\t");
			System.out.printf("/**\n * %s (%s) %s\n *	%s\n *	%s\n */\n", list[0], list[1], list[2], list[3], list[4]);
			String signature=getSignature(list);
			if (signature!=null) System.out.printf("final byte OP_%s= (byte)0x%s;\n", list[0].toUpperCase(), list[1]);
			System.out.printf("\n");
		}
	}
	
	private static void bytecodeFunctionsJava() throws FileNotFoundException, IOException {
		BufferedReader bi= new BufferedReader(new FileReader("src/test/java/bytecode.txt"));
		String line;
		for(int i=1; (line=bi.readLine())!=null; i++) {
			String list[]=line.split("\t");
			String signature=getSignature(list);
			if (signature!=null) {
				char c=list[0].charAt(0);
				if ("bsilfd".indexOf(c)<0) c='i';
				System.out.printf("\t/**\n\t * %s (%s) %s<br>\n\t *	%s<br>\n\t *	%s<br>\n\t */\n", list[0], list[1], list[2], list[3], list[4]);
				System.out.printf("	public static void OPF_%s() {\n", list[0].toUpperCase());
				if (list[3].indexOf("==>")>=0) {
					if (list[3].endsWith("==>")) {
						// just pops
						System.out.printf("		//%c0;\n", c);
					} else {
						if (list[3].indexOf(",")>=0) {
							System.out.printf("		%c0=%c1-%c0;\n", c,c,c);
						} else {
							System.out.printf("		%c0=%c1;\n", c,c);
						}
					}
				}
				System.out.printf("		unimplemented();\n");
				System.out.printf("	};\n");
				System.out.printf("\n");
			}
		}
	}
	
	private static void bytecodeFunctionsC() throws FileNotFoundException, IOException {
		BufferedReader bi= new BufferedReader(new FileReader("src/test/java/bytecode.txt"));
		String line;
		for(int i=1; (line=bi.readLine())!=null; i++) {
			String list[]=line.split("\t");
			String signature=getSignature(list);
			if (signature!=null) {
				char c=list[0].charAt(0);
				if ("bsilfd".indexOf(c)<0) c='i';
				System.out.printf("/**\n * %s (%s) %s<br>\n *	%s<br>\n *	%s<br>\n */\n", list[0], list[1], list[2], list[3], list[4]);
				System.out.printf("void OPF_%s() {\n", list[0].toUpperCase());
				if (list[3].indexOf("==>")>=0) {
					if (list[3].endsWith("==>")) {
						// just pops
						System.out.printf("	//%c0;\n", c);
					} else {
						if (list[3].indexOf(",")>=0) {
							System.out.printf("	%c0=%c1-%c0;\n", c,c,c);
						} else {
							System.out.printf("	%c0=%c1;\n", c,c);
						}
					}
				}
				System.out.printf("	unimplemented();\n");
				System.out.printf("};\n");
				System.out.printf("\n");
			}
		}
	}
	
	private static void bytecodeMicroCode() throws FileNotFoundException, IOException {
		BufferedReader bi= new BufferedReader(new FileReader("src/test/java/bytecode.txt"));
		String line;
		for(int i=1; (line=bi.readLine())!=null; i++) {
			String list[]=line.split("\t");
			String signature=getSignature(list);
			if (signature!=null) {
				char c=list[0].charAt(0);
				if ("bsilfd".indexOf(c)<0) c='i';
				System.out.printf("\t/**\n\t * %s (%s) %s<br>\n\t *	%s<br>\n\t *	%s<br>\n\t */\n", list[0], list[1], list[2], list[3], list[4]);
				System.out.printf("	static final MicroCode OPF_%s = new MicroCode() {\n", list[0].toUpperCase());
				System.out.printf("		public void microcode() {\n");
				if (list[3].indexOf("==>")>=0) {
					if (list[3].endsWith("==>")) {
						// just pops
						System.out.printf("		//%c0;\n", c);
					} else {
						if (list[3].indexOf(",")>=0) {
							System.out.printf("		%c0=%c1-%c0;\n", c,c,c);
						} else {
							System.out.printf("		%c0=%c1;\n", c,c);
						}
					}
				}
				System.out.printf("			unimplemented();\n");
				System.out.printf("		};\n");
				System.out.printf("	};\n");
				System.out.printf("\n");
			}
		}
	}
	
	private static void bytecodeSwitch() throws FileNotFoundException, IOException {
		BufferedReader bi= new BufferedReader(new FileReader("src/test/java/bytecode.txt"));
		String line;
		for(int i=1; (line=bi.readLine())!=null; i++) {
			String list[]=line.split("\t");
			String signature=getSignature(list);
			if (signature!=null) {
				System.out.printf("case %-20s OPF_%s(); break;\n", "OP_"+list[0].toUpperCase()+":", list[0].toUpperCase());
			}
		}
		System.out.printf("default: unimplemented();\n");
	}
	
    private static void bytecodeDef() throws FileNotFoundException, IOException {
        BufferedReader bi= new BufferedReader(new FileReader("src/test/java/bytecode.txt"));
        String line;
        for(int i=1; (line=bi.readLine())!=null; i++) {
            String list[]=line.split("\t");
            System.out.printf("/**\n * %s (%s) %s\n *   %s\n *  %s\n */\n", list[0], list[1], list[2], list[3], list[4]);
            String signature=getSignature(list);
            if (signature!=null) System.out.printf("#define %s\n", signature);
            System.out.printf("\n");
        }
    }

    private static void extractCMDExamples() throws FileNotFoundException, IOException {
        BufferedReader bi= new BufferedReader(new FileReader("//Pc111/backup/homeweb/HaikuVM.html"));
        String line;
        int e=0;
        for(int i=1; (line=bi.readLine())!=null; i++) {
            if (line.contains("&lt;code bash")) {
                String example="\n";
                for(int j=1; (line=bi.readLine())!=null; j++) {
                    if (line.contains("&lt;/code")) break;
                    example+=line+"\n";
                }
                if (example.contains("haiku") && (example.contains("c:") || example.contains("C:"))) {
                    e++;

                    example=example.replace("&gt;", ">");
                    example=example.replace("~", "");
                    example=example.replace("&amp;", "&");
                    example=example.replace("\nc:", "\ncmd /c c:");
                    example=example.replace("\nC:", "\ncmd /c C:");
                    
                    System.out.println();
                    System.out.println("echo ##################### "+e);
                    System.out.println("(");
                    System.out.print("echo ##################### "+e);
                    System.out.print(example);
                    System.out.println(") 1>>C:/temp/stdout.txt");
                }
            }
        }
    }

	private static void bytecodeExtern() throws FileNotFoundException, IOException {
		BufferedReader bi= new BufferedReader(new FileReader("src/test/java/bytecode.txt"));
		String line;
		for(int i=1; (line=bi.readLine())!=null; i++) {
			String list[]=line.split("\t");
			String cSignature=getCSignature(getSignature(list));
			if (cSignature!=null) {
				System.out.printf("extern %s;\n", cSignature);
			}
		}
	}

	private static String getCSignature(String signature) {
		if (signature==null || signature.indexOf(") ")<0 || signature.indexOf("branch")>0 ) return null;
		String cSignature="OPF_"+signature.substring(4, signature.indexOf(')')+1);
		if (cSignature.indexOf("()")>0 ) return cSignature;
		String list[]=cSignature.split("[)(, ]+");
		cSignature=list[0]+"("+list[1]+"_t "+list[1];
		for (int i = 2; i < list.length; i++) {
			cSignature+=", "+list[i]+"_t "+list[i];
		}
		return cSignature+")";
	}

	private static String getSignature(String[] list) {
		if (list[1].length()!=2) return null;
		String params="";
		if (list[2].length()>0) {
			String plist[]=list[2].split("[-:, \\d+<>\\]\\[/\\.]+");
			for (int j = 0; j < plist.length; j++) {
				plist[j]=plist[j].replaceAll("byte", "");
				plist[j]=plist[j].replaceAll("index", "value");
				plist[j]=plist[j].replaceAll("const", "cnst");
				plist[j]=plist[j].replaceAll("default", "dflt");
				if(plist[j].length()==0) plist[j]="value";
			}
			params=plist[1];
			for (int j = 2; j < plist.length; j++) {
				if (!plist[j].equals(plist[j-1])) params+=", "+plist[j];
			}
		}
		String except=getException("OPM_"+list[0]);
		if (except!=null) {
			return except;
		} else {
			return "OPM_"+list[0].toUpperCase()+"("+params+") OPF_"+list[0].toUpperCase()+"("+params+")";
		}
	}
	
	private static String getException(String string) {
		for (String ele : exceptions) {
			if (ele.toUpperCase().startsWith(string.toUpperCase())) return ele;
		}
		return null;
	}

	static String exceptions[]={
			"OPM_INVOKESPECIAL(obj, foo, sig, params, type) (*obj##_##foo##_##sig)()",
			"OPM_INVOKESTATIC(obj, foo, sig, params, type) (*obj##_##foo##_##sig)()",
			"OPM_INVOKEVIRTUAL(clazz, foo, sig, params, type) (*getMethod(params, methodIdx(foo, sig), clazz##_##foo##_##sig))()",
			"OPM_GETSTATIC(clazz, var, jtype) OPF_GETSTATIC(clazz##_##var)",
			"OPM_WIDE(opcode, index, iinc, count)\tOPF_WIDE(opcode, index, iinc, count)",
			"OPM_TABLESWITCH(s, padding, default, low, high, jump, offsets)\tOPF_TABLESWITCH(s, padding, default, low, high, jump, offsets)",
			"OPM_LOOKUPSWITCH(s, padding, default, npairs, match, offset, pairs)\tOPF_LOOKUPSWITCH(s, padding, default, npairs, match, offset, pairs)",
	};

}
