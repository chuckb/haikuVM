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


public class AVRDeviceExplorer {

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
		//getAVRconst();
        convertAVRDevice2Java("C:/WinAVR-20100110/avr/include/avr/iom328p.h");
        convertAVRDevice2Java("C:/WinAVR-20100110/avr/include/avr/iom8.h");
        convertAVRDevice2Java("C:/WinAVR-20100110/avr/include/avr/iom32u4.h");
        for (String name : natives) {
            System.out.printf("        %-10s=32;\n", name);
        }
	}

	private static void convertAVRDevice2Java(String filename) throws FileNotFoundException, IOException {
        System.out.println("    /* Generated with AVR Device Explorer for HaikuVM");
        System.out.println("     * from file: '"+new File(filename).getName()+"'");
        System.out.println("     */");
        System.out.println();
        PushBackLines bi= new PushBackLines(new FileReader(filename));
	    String line;
        Vector<String> comment=new Vector<String>();
        Vector<String> code=new Vector<String>();
        for (int i = 0; (line=bi.readLine())!=null; i++) {
            String[] token = line.split("[() \t]+");
            if (line.contains("_SFR_MEM") || line.contains("_SFR_IO")) {
                //#define UBRR0 _SFR_MEM16(0xC4) /* Changed in 2486H-AVR-09/02 */
                // -->
                //    /** UBRR0 MEM 0xC4 16 <br>
                //     *  Changed in 2486H-AVR-09/02
                //     */
                //    @NativeCVariable16
                //    public static int UBRR0;
                getMore(bi, comment, code);
                String name = token[1];
                String addr = token[3];
                String bits = token[2].replaceAll(".*MEM(\\d+)", "$1");
                natives.add(name);
                if (!bits.equals(token[2])) {
                    System.out.printf("    /** %-10s MEM Addr=%s Bits=%s <br>\n", name, addr, bits);
                } else {
                    bits = token[2].replaceAll(".*IO(.*)", "$1");
                    System.out.printf("    /** %-10s IO  %s %s <br>\n", name, addr, bits);
                }
                if (token.length>4 && token[4].startsWith("/*") && token[token.length-1].endsWith("*/")) {
                    System.out.printf("     * ");
                    for (int j = 5; j < token.length-1; j++) {
                        System.out.printf(" %s", token[j]);
                    }
                    System.out.printf(" <br>\n");
                }
                for (String l : comment) {
                    System.out.printf(l);
                }
                System.out.printf("     */\n");
                System.out.printf("    @NativeCVariable%s\n", bits);
                System.out.printf("    public static volatile int %s;\n", name);
                for (String l : code) {
                    System.out.printf(l);
                }
                System.out.printf("\n");
            } else if (line.startsWith("#define") && token.length==3 && !token[1].startsWith("_")) {
                System.out.printf("    public static final int %-10s = %s;\n", token[1], token[2]);
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
                System.out.println();
            }
        }
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
