package base;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;


public class MyJUnit {
	private static final String PREFIX = "##########";
	private String expectedFile;
	private String actualFile;
	private String m0;
	private BufferedReader f1;
	private int passed;
	private int failed;
	private int t0, i0;
	private int t1, i1;
	private double precision;
	private int precisionCount;
	private boolean first=true;
    private String clazz;
    private Vector<String> expected;
    private Vector<String> actual;
	private BufferedReader f0;

	private static class Assertion extends RuntimeException {

		public Assertion(String msg) {
			super(msg);
		}

	}

	public MyJUnit(String expectedFile, String actualFile) {
		this.expectedFile=expectedFile;
		this.actualFile=actualFile;
	}

	/**
	 *
	 * and delete garbage tail

Soft WDT reset

ctx: cont
sp: 3fff0470 end: 3fff06d0 offset: 01b0

>>>stack>>>
3fff0620:  feefeffe 0000003d 0000000a 40202f24
3fff0630:  000000ff 0000000c 3fff20cc 40201955
3fff0640:  3ffe8718 00000000 3fff20cc 402015f1
3fff0650:  00000001 3ffe917e 3fff20cc 3ffe930c
3fff0660:  00000004 00000037 3fff20cc 0000000e
3fff0670:  00000004 0000000c 3fff20cc 40201337
3fff0680:  00000000 3fff182c 0000000b 3ffef6a8
3fff0690:  3fffdad0 00000000 3fff0d2c 3ffef6a8
3fff06a0:  3fffdad0 00000000 3ffef6a0 40202804
3fff06b0:  3fffdad0 00000000 3ffef6a0 40202d4c
3fff06c0:  feefeffe feefeffe 3ffef6b0 40100114
<<<stack<<<
B¶
r‡Ä¤„•ïHBß¨!{÷…Çc1ÂÂ@·…gC))j%÷sR91ñ‡ë;«n&À…¤)!Â

	 * @param expectedFile
	 * @param clazz
	 * @param actual
	 */
	public MyJUnit(String expectedFile, String clazz, Vector<String> actual) {
        this.expectedFile=expectedFile;
        this.clazz=clazz;
        this.actual=actual;
        for (int i = 0; i < actual.size(); i++) {
			if (i < actual.size()-1 && actual.get(i).contains("Soft WDT reset")) {
				actual.removeElementAt(i+1);
				i--;
			}
		}
		if (actual.size()>0 && actual.get(actual.size()-1).contains("Soft WDT reset")) {
			actual.removeElementAt(actual.size()-1);
			actual.removeElementAt(actual.size()-1);
		}
        actual.add(PREFIX);
    }

    /**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		MyJUnit ju;
		ju = new MyJUnit("D:/Entwicklung/sourceforge.haikuvm/trunk/haikuBench/src/main/resources/valid_JVM.lst", "D:/Entwicklung/sourceforge.haikuvm/trunk/haikuBench/src/main/resources/valid_arduino_32_32.lst");

		ju = new MyJUnit("../haikuBench/src/main/resources/valid_JVM.lst", "../haikuBench/src/main/resources/valid_pc_32_64.lst");
		ju.start();
		ju = new MyJUnit("../haikuBench/src/main/resources/valid_JVM.lst", "../haikuBench/src/main/resources/valid_pc_32_32.lst");
		ju.start();
		ju = new MyJUnit("../haikuBench/src/main/resources/valid_JVM.lst", "../haikuBench/src/main/resources/valid_linux64_32_64.lst");
		ju.start();
		ju = new MyJUnit("../haikuBench/src/main/resources/valid_JVM.lst", "../haikuBench/src/main/resources/valid_linux64_16_32.lst");
		ju.start();
		ju = new MyJUnit("../haikuBench/src/main/resources/valid_JVM.lst", "../haikuBench/src/main/resources/valid_arduino_32_32.lst");
		ju.start();
		ju = new MyJUnit("../haikuBench/src/main/resources/valid_JVM.lst", "../haikuBench/src/main/resources/valid_arduino_32_32_02092012.lst");
		ju.start();
		ju = new MyJUnit("../haikuBench/src/main/resources/valid_JVM.lst", "../haikuBench/src/main/resources/valid_arduino_16_32.lst");
		ju.start();

		ju = new MyJUnit("../haikuBench/src/main/resources/valid_pc_32_64.lst", "../haikuBench/src/main/resources/valid_linux64_32_64.lst");
		ju.start();
		ju = new MyJUnit("../haikuBench/src/main/resources/valid_linux64_32_64.lst", "../haikuBench/src/main/resources/valid_linux64_16_32.lst");
		ju.start();

		ju = new MyJUnit("../haikuBench/src/main/resources/valid_arduino_32_32.lst", "../haikuBench/src/main/resources/valid_arduino_32_32_02092012.lst");
		ju.start();
		ju = new MyJUnit("../haikuBench/src/main/resources/valid_arduino_32_32.lst", "../haikuBench/src/main/resources/valid_arduino_32_32_09092012.lst");
		ju.start();
		ju = new MyJUnit("../haikuBench/src/main/resources/valid_arduino_32_32.lst", "../haikuBench/src/main/resources/valid_arduino_16_32.lst");
		ju.start();
		ju = new MyJUnit("../haikuBench/src/main/resources/valid_arduino_32_32.lst", "../haikuBench/src/main/resources/valid_arduino_16_16.lst");
		ju.start();
	}

    void start() throws IOException {
        if (actual!=null) {
            startVector();
        } else {
            startFiles();
        }
    }

    void startVector() throws IOException {
        f0= new BufferedReader(new FileReader(expectedFile));
        String line0;
        for(int i=1; (line0=f0.readLine())!=null; i++) {
            String[] token = line0.split(" +");
            if (line0.startsWith(PREFIX) && token.length==2 && (clazz.equals(token[1]) || clazz.endsWith("."+token[1]))) {
                //##########  MathTest
                m0 = token[1];
                Assertion e=compare();
                if (e==null) {
                    break;
                } else {
                    throw e;
                }
            }
        }
        if (m0==null) throw new Assertion("JUnit not found in "+expectedFile+".");
    }

    private void compareWith(Vector<String> output) throws IOException {
        try{
            for(int i=1; i<output.size()+1; i++) {
                String line0=output.get(i-1);
                String[] token = line0.split(" +");
                Assertion e=compare();
                throw e;
            }
            throw new Assertion("JUnit not found.");
        } catch (Assertion e) {
            System.out.println(m0+": !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n"+e.getMessage());
            failed++;
        }
    }

    void startFiles() throws IOException {
        f0= new BufferedReader(new FileReader(expectedFile));
        String line0;
        for(int i=1; (line0=f0.readLine())!=null; i++) {
            String[] token = line0.split(" +");
            if (line0.startsWith("/*") || line0.startsWith(" *")) {
                System.out.println(line0);
            }
            if (line0.startsWith(PREFIX) && token.length==2) {
                //##########  MathTest
                m0 = token[1];
                compareWith();
                first=false;
            }
        }
        System.out.println();
        System.out.println("Test summary:");
        System.out.printf("sum: %d; passed: %d; failed: %d\n", passed+failed, passed, failed);
        System.out.printf("time0: %d msec; time1: %d msec;\n%39.2f %s\n", t0, t1, timef(t0, t1), time(t0, t1));
        System.out.printf("time0: %d msec; time1: %d msec;\n%39.2f %s\n", t0-9000, t1-9000, timef(t0-9000, t1-9000), time(t0-9000, t1-9000));
        System.out.printf("precision errors: %d    avg: %g\n", precisionCount, precision/precisionCount);
        System.out.println();
        System.out.println();
    }

    private void compareWith() throws IOException {
        f1= new BufferedReader(new FileReader(actualFile));
        String line0;
        try{
            for(int i=1; (line0=f1.readLine())!=null; i++) {
                String[] token = line0.split(" +");
                if (first) {
                    if (line0.startsWith("/*") || line0.startsWith(" *")) {
                        System.out.println(line0);
                    }
                }
                if (line0.startsWith(PREFIX) && token.length==2 && token[1].equals(m0)) {
                    //##########  MathTest
                    Assertion e=compare();
                    System.out.printf("%-20s: %s   %8.2f %s\n", m0, e==null?"passed":"failed", timef(i0, i1), time(i0, i1));
                    if (e==null) {
                        passed++;
                        return;
                    }
                    throw e;
                }
            }
            throw new Assertion("JUnit not found.");
        } catch (Assertion e) {
            System.out.println(m0+": !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n"+e.getMessage());
            failed++;
        } finally {
            f1.close();
        }
    }

	private double timef(int i0, int i1) {
		double f=1;
		if (i0==0) {
			if (i1>0) f=100;
		} else {
			f=1.0*i1/i0;
		}
		return f;
	}
	private String time(int i0, int i1) {
		double s=timef(i0, i1);
		if (s<0) return "??????";
		if (s>2) s=2;
		s=2-s;
		int j=(int)(s*10+0.5); // 0,0 .. 2,0 -> 0 - 20
		// 0 - 9; 10; 11 - 20
		String a="          ----------";
		String b="++++++++++         ";
		if (j<=10) {
			a=a.substring(10-j, (10-j)+10);
			b=b.substring(10);
		} else {
			a=a.substring(0, 10);
			b=b.substring(20-j, (20-j)+9);
		}
		return a+"|"+b;
	}

	private Assertion compare() throws IOException {
		Assertion azzert=null;
		String line0;
		String line1;
		int i=1;
		boolean synced=false;
		line0 = f0.readLine();
		for(i=1; ; i++) {
			if (actual==null) {
	            line1 = f1.readLine();
			} else {
	            line1 = actual.get(i-1);
			}
			if (line0==null && line1!=null) throw new Assertion("line0==null && line1!=null in line "+i);
			if (line0!=null && line1==null) throw new Assertion("line0!=null && line1==null in line "+i);
			if (line0.startsWith(PREFIX)) break;
			if (line1.startsWith(PREFIX)) break;
			if (line0.equals(line1)) {
				synced=true;
				line0 = f0.readLine();
				continue;
			}

			String relax0 = relax(line0);
			String relax1 = relax(line1);
			if (relax0.equals(relax1)) {
				synced=true;
				line0 = f0.readLine();
				continue;
			}

			if (precision(relax0, relax1, 0.001)) {
				//System.out.println("precision expected: '"+relax0+"'\nbut was           : '"+relax1+"'");
				synced=true;
				line0 = f0.readLine();
				continue;
			}
			if (! synced ) {
				if (line1.endsWith(line0)) {
					synced=true;
					line0 = f0.readLine();
				}
				continue;
			}
			//System.out.println("expected: '"+relax0+"'\nbut was : '"+relax1+"'");
			if (azzert==null) azzert=new Assertion("expected: '"+relax0+"'\nbut was : '"+relax1+"' in line "+i);
			throw new Assertion("expected: '"+line0+"'\nbut was : '"+line1+"'");
		}
		if (line0.startsWith(PREFIX) && !line1.startsWith(PREFIX)) throw new Assertion("line0.startsWith(PREFIX) && line1.startsWith("+line1+") in line "+i);
		if (!line0.startsWith(PREFIX) && line1.startsWith(PREFIX)) throw new Assertion("line0.startsWith("+line0+") && line1.startsWith(PREFIX) in line "+i);
		String[] token0 = line0.split(" +");
		try {
            i0=Integer.parseInt(token0[1]);
            String[] token1 = line1.split(" +");
            if (token1.length>1) {
                i1=Integer.parseInt(token1[1]);
                if (i0>=0 && i1>=0) {
                    t0+=i0;
                    t1+=i1;
                }
            }
        } catch (Exception e) {
        }
		return azzert;
	}

	private boolean precision(String relax0, String relax1, double epsilon) {
		String[] token0 = relax0.split("[ \\)\\(=]");
		String[] token1 = relax1.split("[ \\)\\(=]");

		if (token0.length!=token1.length) return false;
		int c=0;
		for (int i = 0; i < token0.length; i++) {
			if (!token0[i].equals(token1[i])) {
				try {
					double d0 = Double.parseDouble(token0[i]);
					double d1 = Double.parseDouble(token1[i]);
					double d = d0-d1;
					double err;
					if ( Double.isInfinite(d0) && !Double.isInfinite(d1)) return false;
					if (!Double.isInfinite(d0) &&  Double.isInfinite(d1)) return false;
					if ( Double.isNaN(d0) && !Double.isNaN(d1)) return false;
					if (!Double.isNaN(d0) &&  Double.isNaN(d1)) return false;
					if (d0!=0 && d1!=0)  {
						err=Math.abs(d/d0);
					} else {
						err=Math.abs(d);
					}
					if (err>=epsilon) return false;
					precision+=err;
					precisionCount++;
					c++;
				} catch (NumberFormatException e) {
					return false;
				}
			}
		}
		return c>0;
	}

	private String relax(String string) {
		String result=string.replace("ï¿½", "ï¿½");
		result=result.replaceAll("java\\.lang\\.Exception: ", "");
		result=result.replaceAll(" +$", ""); //trailing blanks
		return result;
	}
}
