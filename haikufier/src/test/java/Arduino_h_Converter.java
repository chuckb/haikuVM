import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.Arrays;
import java.util.Formatter;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * void pinMode(uint8_t, uint8_t);
 * A==>
 * public static void pinMode(int p1, int p2) {
 *   Impl.pinMode(p1, p2);
 * }
 * B==>
 * public static native void pinMode(int p1, int p2);
 *
 * @author genom2
 *
 */
public class Arduino_h_Converter {

    private static class PushBackLines extends PushbackReader {

        public PushBackLines(Reader in) {
            super(in, 1000);
        }

        public String readLine() {
            String line="";
            int c;
            try {
                while ((c=read())>=0) {
                    if (c=='\r') {
                        c=read();
                        if (c!='\n') unread(c);
                        return line;
                    }
                    if (c=='\n') {
                        return line;
                    }
                    line+=(char)c;
                }
            } catch (IOException e) {
            }
            return null;
        }

        public void unreadLine(String line) {
            if (line==null) return;
            try {
                unread('\n');
                for (int i = line.length()-1; i >=0; i--) {
                    unread(line.charAt(i));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

	private static Set<String> lhs=new TreeSet<String>();
	private static Set<String> cand=new TreeSet<String>();

	private static Set<String> natives=new TreeSet<String>();


	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
//        test(0);
//        test(10);
//        test(100);
//        test(1000);
//        test(10000);
//        test(100000);
//        test(0x1000000L);
//        test(0xffffffffL);
//        for (long i = 0x0L; i < 0x100000000L; i+=1001) {
//            test(i);
//        }
		//getAVRconst();
        convertArduino2Java("../lib/includes/arduino/cores/arduino/Arduino.h");
        convertArduino2ImplNative("../lib/includes/arduino/cores/arduino/Arduino.h");
	}

	private static void test(long i) {
        search(i, i/10, i%10);
        //test(i, i/10, i%10);
    }

    private static void test1(long i, long d, long m) {
        long l;
        l=51*i+51;
        l=l + (l>>>8);
        l=l + (l>>>16);
        l=l + (l>>>32);
        long n=(10*(0xff&(l>>>1))) >>> 8;
        l=l>>>9;

        if (l!=d) {
            System.err.println(i+" d expected "+d+" but was "+l);
        }
        if (n!=m) {
            System.err.printf("%d 0x%08x m expected %d but was %d\n", i, i, n, m);
        }
    }

    private static void test(long i, long d, long m) {
        long l;
        l=6528*i+6528;
        l=l + (l>>>8);
        l=l + (l>>>16);
        l=l + (l>>>32);
        long n=(10*(0xff&(l>>>8))) >>> 8;
        l=l>>>16;

        if (l!=d) {
            System.err.println(i+" d expected "+d+" but was "+l);
        }
        if (n!=m) {
            System.err.printf("%d 0x%08x m expected %d but was %d\n", i, i, n, m);
        }
    }

    static Set<Integer> v= new TreeSet<Integer>();
    private static void search0(long i, long d, long m) {
        long l=0;
        int s=0;
        for (int j = 0; j < 41; j++) {
            for (int j2 = 0; j2 < 41; j2++) {
                for (s = 1; s<256; s++) {
                    l=s*i+s;
                    l=l + (l>>>j);
                    l=l + (l>>>j2);
                    l=l>>>8;
                    if (l==d) {
                        if (v.add(s)) {
                            System.err.println(i+" s= "+s+" s1="+j+" s2="+j2);
                        }
                        break;
                    }
                }
                if (l==d) break;
            }
            if (l==d) break;
        }
        if (l==d) {
        } else {
            System.err.printf("%d 0x%08x d expected %d but was %d\n", i, i, d, l);
        }
    }

    private static void search(long i, long d, long m) {
        long l=0;
        int s=0;
        for (int a = 1; a < 256; a++) {
            for (int b = 1; b < 256; b++) {
                l=(256*i/a+256*i/b);
                l=l>>>8;
                if (l==d) {
                    if (v.add(a)) {
                        System.err.println(i+" s= "+a+" s1="+b);
                    }
                    break;
                }
                if (l==d) break;
            }
            if (l==d) break;
        }
        if (l==d) {
        } else {
            System.err.printf("%d 0x%08x d expected %d but was %d\n", i, i, d, l);
        }
    }

    private static void convertArduino2Java(String filename) throws FileNotFoundException, IOException {
        System.out.println("    /* Generated with Arduino_h_Converter for HaikuVM");
        System.out.println("     * from file: '"+new File(filename).getName()+"'");
        System.out.println("     */");
        System.out.println();
        PushBackLines bi= new PushBackLines(new FileReader(new File(filename).getCanonicalPath()));
        int lastempty=-1;
        String line;
        Vector<String> comment=new Vector<String>();
        Vector<String> code=new Vector<String>();
        for (int i = 0; (line=bi.readLine())!=null; i++) {
            line= line.replace("unsigned ", "");
            line= line.replace("volatile ", "");
            line= line.replace("UL", "L");
            line= line.replace("(void)", "()");
            String[] token = line.split("[ \t]+");
            if (false) {
                ;
            } else if (line.startsWith("#define") && token[1].contains("(")) {
                System.out.printf("public static int %s {\n", signature2(line.replaceFirst("#define\\s+([^)]*\\)).*", "$1")));
                System.out.printf("    return %s;\n", line.replaceFirst("#define\\s+([^)]*\\))(.*)", "$2"));
                System.out.printf("}\n");
                System.out.printf("\n");
                lastempty = i;
            } else if (line.startsWith("#define") && token.length==3 && !token[1].startsWith("_")) {
                if (token[2].contains(".")) {
                    System.out.printf("    public static final double %-10s = %s;\n", token[1], token[2]);
                } else {
                    System.out.printf("    public static final int %-10s = %s;\n", token[1], token[2]);
                }
            } else if (line.startsWith("#define") && token.length>3 && token[3].startsWith("/*") ) {
                System.out.printf("    public static final int %-10s = %s;", token[1], token[2]);
                for (int j = 3; j < token.length; j++) {
                    System.out.printf(" %s", token[j]);
                }
                System.out.printf("\n");
            } else if (token.length>0 && token[0].startsWith("/*") && token[token.length-1].endsWith("*/") ) {
                System.out.println("    "+line);
            } else if (line.startsWith("#define") && token.length>=3) {
                //#define RAMEND       0x8FF     /* Last On-Chip SRAM Location */
                //-->
                //      * RAMEND       0x8FF     /* Last On-Chip SRAM Location */<br>
                //     public static final int RAMEND = 0x8FF
                String name = token[1];
                String value = token[2];
                if (value.equals("_VECTOR")) continue;
                if (value.equals("unsigned")) continue;
                System.out.printf("    public static final int %-10s = %s;\n", name, value);
            } else if (line.length()==0) {
                if (lastempty != i-1) {
                    System.out.println();
                }
                lastempty = i;
            } else if (line.contains("(") && line.endsWith(";")) {
                System.out.printf("public static %s {\n", signature1(line.replace(";" , "")));
                System.out.printf("    %s;\n", body1(line));
                System.out.printf("}\n");
                System.out.printf("\n");
                lastempty = i;
            } else {
                if (lastempty != i-1) {
                    System.out.println();
                }
                lastempty = i;
            }
        }
    }

    private static void convertArduino2ImplNative(String filename) throws FileNotFoundException, IOException {
        System.out.println("    /* Generated with Arduino_h_Converter for HaikuVM");
        System.out.println("     * from file: '"+new File(filename).getName()+"'");
        System.out.println("     */");
        System.out.println();
        PushBackLines bi= new PushBackLines(new FileReader(new File(filename).getCanonicalPath()));
        int lastempty=-1;
        String line;
        Vector<String> comment=new Vector<String>();
        Vector<String> code=new Vector<String>();
        for (int i = 0; (line=bi.readLine())!=null; i++) {
            line= line.replace("unsigned ", "");
            line= line.replace("volatile ", "");
            line= line.replace("UL", "L");
            line= line.replace("(void)", "()");
            String[] token = line.split("[ \t]+");
            if (false) {
                ;
            } else if (line.startsWith("#define") && token[1].contains("(")) {
                lastempty = i;
            } else if (line.startsWith("#define") && token.length==3 && !token[1].startsWith("_")) {
                lastempty = i;
            } else if (line.startsWith("#define") && token.length>3 && token[3].startsWith("/*") ) {
                lastempty = i;
            } else if (token.length>0 && token[0].startsWith("/*") && token[token.length-1].endsWith("*/") ) {
                System.out.println("    "+line);
            } else if (line.startsWith("#define") && token.length>=3) {
                lastempty = i;
            } else if (line.length()==0) {
                if (lastempty != i-1) {
                    System.out.println();
                }
                lastempty = i;
            } else if (line.contains("(") && line.endsWith(";")) {
                System.out.printf("@NativeCFunction\n");
                System.out.printf("public static native %s;\n", signature1(line.replace(";" , "")));
                System.out.printf("\n");
                lastempty = i;
            } else {
                if (lastempty != i-1) {
                    System.out.println();
                }
                lastempty = i;
            }
        }
    }

    private static String signature2(String replace) {
        replace=replace.replace("uint8_t", "byte");
        replace=replace.replace("uint16_t", "int");
        Matcher m = java.util.regex.Pattern.compile("(.*\\(\\s*)(.*)\\)").matcher(replace);
        //Pattern p = java.util.regex.Pattern.compile("(.)*");
        m.find();
        String res = m.group(1);
        String[] list = m.group(2).split(",\\s*");
        for (int i = 0; i < list.length; i++) {
            if (i > 0) res+=", ";
            if(list[i].length()>0) res += "int "+list[i];
        }
        return res+")";
    }

    private static String signature1(String replace) {
        replace=replace.replace("uint8_t", "byte");
        replace=replace.replace("uint16_t", "int");
        Matcher m = java.util.regex.Pattern.compile("(.*\\(\\s*)(.*)\\)").matcher(replace);
        //Pattern p = java.util.regex.Pattern.compile("(.)*");
        m.find();
        String res = m.group(1);
        String[] list = m.group(2).split(",\\s*");
        for (int i = 0; i < list.length; i++) {
            if (i > 0) res+=", ";
            String[] list2 = list[i].split("\\s+");
            if (list2.length==1 && list2[0].length()>0) {
                res += list[i] + " p"+i;
            } else if (list2.length>1) {
                res += list[i];
            }
        }
        return res+")";
    }

    private static String body1(String replace) {
        replace=replace.replace("uint8_t", "byte");
        replace=replace.replace("uint16_t", "int");
        Matcher m = java.util.regex.Pattern.compile("(.*)\\s+(.*)\\(\\s*(.*)\\)").matcher(replace);
        //Pattern p = java.util.regex.Pattern.compile("(.)*");
        m.find();
        String res = "";
        if (!m.group(1).equals("void")) {
            res+="return ";
        }
        res += "Impl."+m.group(2)+"(";
        String[] list = m.group(3).split(",\\s*");
        for (int i = 0; i < list.length; i++) {
            if (i > 0) res+=", ";
            String[] list2 = list[i].split("\\s+");
            if (list2.length==1 && list2[0].length()>0) {
                res += "p"+i;
            } if (list2.length==2) {
                res += list2[1];
            } else {
            }
        }
        return res+")";
    }

    private static void getMore(PushBackLines bi, Vector<String> comment,
            Vector<String> code) throws IOException {
        comment.clear();
        code.clear();
        String line;
        for (int i = 0; (line=bi.readLine())!=null; i++) {
            if (line.contains("_SFR_")) {
                bi.unreadLine(line);
                return;
            }
            if (line.startsWith("#define")) {
            //#define RAMEND       0x8FF     /* Last On-Chip SRAM Location */
            //-->
            //      * RAMEND       0x8FF     /* Last On-Chip SRAM Location */<br>
            //     public static final int RAMEND = 0x8FF
                String[] token = line.split("[() \t]+");
                String name = token[1];
                String value = token[2];
                comment.add(new Formatter().format("     *  %-10s %s <br>\n", name, value).toString());
                code.add(new Formatter().format("    public static final int %-10s = %s;\n", name, value).toString());
            } else {
                break;
            }
        }

    }

    private static void getAVRconst() throws FileNotFoundException, IOException {
		int token=0, state=0;
		//StreamTokenizer st = new StreamTokenizer(new FileReader("../bootstrap/src/main/java/haiku/avr/lib/arduino/Servo.java"));
		StreamTokenizer st = new StreamTokenizer(new FileReader("D:\\users\\Genom\\arduino_eclipse\\workspace\\arduino_core\\Servo\\Servo.cpp"));
		st.slashSlashComments(true);
		st.slashStarComments(true);
		st.wordChars('#', '#');
		st.wordChars('_', '_');
		while ((token=st.nextToken())!=st.TT_EOF) {
			if (token==';' || token=='{' || "#endif".equals(st.sval)) {
				state=0;
			} else if (token==st.TT_WORD && st.sval.equals(st.sval.toUpperCase()) && st.sval.length()>=3 && !st.sval.startsWith("_")) {
				cand.add(st.sval);
				if (state==0) {
					lhs.add(st.sval);
				} else {
				}
				state=1;
			} else {
				state=1;
			}
		}
		for (String var : cand) {
			String rhs;
			String comment="";
			if (lhs.contains(var)) {
				rhs="&"+var;
				comment=" // is adress";
			} else {
				rhs=var;
			}
			System.out.printf("#ifdef %s\n", var);
			System.out.printf("    give(\"%s\", %s, \"%s\");\n", var, rhs, comment);
			System.out.printf("#else\n");
			System.out.printf("    undefined(\"%s\", \"%s\");\n", var, comment);
			System.out.printf("#endif\n");
			System.out.printf("\n");
		}


	}
}
