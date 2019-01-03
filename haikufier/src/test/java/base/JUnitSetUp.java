package base;

import haikuvm.pc.tools.HaikuVM;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import communication.Communication;


public class JUnitSetUp
{

    private static String target = "arduino";
    private static String mode  = "16/32";
    private static String[] tmpOptions;
    private static String[] permOptions;
	private static String port="COM7";

	private static int[] ecc=new int[0x10000];

	static {
		for (int x = 0; x < 256; x++) {
			int y=eccInv(x);
			if (ecc[y]==0) ecc[y]=x+1;
		}
		for (int x = 0; x < 256; x++) {
			int y=eccInv(x);
			for (int i = 0; i < 16; i++) {
				int ye=y ^ (1<<i);
				if (ecc[ye]==0) ecc[ye]=x+1;
			}
		}
		for (int x = 0; x < 256; x++) {
			int y=eccInv(x);
			for (int i = 0; i < 16; i++) {
				for (int j = i+1; j < 16; j++) {
					int ye=y ^ (1<<i) ^ (1<<j);
					if (ecc[ye]==0) ecc[ye]=x+1;
				}
			}
		}
		for (int x = 0; x < 256; x++) {
			int y=eccInv(x);
			for (int i = 0; i < 16; i++) {
				for (int j = i+1; j < 16; j++) {
					for (int k = j+1; k < 16; k++) {
						int ye=y ^ (1<<i) ^ (1<<j) ^ (1<<k);
						if (ecc[ye]==0) ecc[ye]=x+1;
					}
				}
			}
		}
		for (int y = 0; y<ecc.length; y++) {
			ecc[y]--;
			if (ecc[y]<0) ecc[y]='?';
		}
	}

    /**
     * and reset permOptions
     * @param target
     */
    public static void setTarget(String target) {
        JUnitSetUp.target=target;
        JUnitSetUp.permOptions=null;
    }

    /**
	 * The idea is to send 2 Bytes to deliver 1 reliable data Byte (12, 8) or even simpler (16, 8) coding.
	 * http://www.researchgate.net/publication/228819322_A_%2816_8%29_error_correcting_code_%28t_2%29_for_critical_memory_applications
	 *
	 * Here are some useful functions I found for ecc (or better fec)
	 * ecc for at least 1 bit error (often more up to some 3 bit errors)
	 *  (16, 8)
	 *  	y=233*(x + 6) <==> 233*b + 2281
	 *
	 *  (16, 10)
	 *  	y=61*x + 1361 <==> 61*(x+22) + 19
	 *  or another simple (small bytecode footprint) alternative
	 *  	y=61*(x + 25)
     *
     * @param x
     * @return
     */
    private static int eccInv(int x) {
		return 61*(x + 25);
	}

    private static int ecc(int y) {
		return ecc[y];
	}

	/**
     * and reset permOptions
     * @param mode
     */
    public static void setMode(String mode) {
        JUnitSetUp.mode=mode;
        JUnitSetUp.permOptions=null;
    }

    /**
     * 
     * @param string e.g. COM9 or COM9,19200 or COm8,2400,rcx
     */
	public static void setPort(String string) {
		port=string;
	}

    /**
     * just for this one test call
     * @param options
     */
    public static void setTempOptions(String[] options) {
        JUnitSetUp.tmpOptions=options;
    }

    /**
     * for ever until set again or setTarget or setMode
     * @param options e.g. new String[] {"--Config:AOTVariant=HAIKU_AOTBytecodeAsSwitch"}
     */
    public static void setPermOptions(String[] options) {
        JUnitSetUp.permOptions=options;
    }


    /** */
    public static class SerialReader implements SerialPortEventListener
    {
        private Vector<String> lines=new Vector<String>();
        private InputStreamReader in;
		private String line;
        private static long t0;
        private static long t1;
        private long lastcharTime, firstcharTime;
        private int c0;
        private int ecc_count;
        private int lasttime=30000; // 30 sec
        private int count=0;

        public SerialReader ( InputStream in )
        {
			line = "";
            this.in = new InputStreamReader(in);
        }

		@Override
		public void serialEvent(SerialPortEvent evt) {
			if (evt.isRXCHAR()) {
				try {
					int c;
	                for ( int i=0;  con.getPort().getInputBufferBytesCount()>0; i++ ) {
	                	c=con.getPort().readBytes(1)[0] & 0xff;
						if (count==0 && port.contains(",rcx")) {
							// on rcx first char is garbage
						} else {
							if (port.contains(",rcx")) {
								ecc_count++;
								if (ecc_count%2==0) {
									//System.out.printf("0 ecc_count=%4d;  c0=%3d;   c=%3d;  c0#c=%d\n", ecc_count,c0, c, (c0<<8)+c);
									c=ecc((c0<<8)+c);
								} else {
									//System.out.printf("1 ecc_count=%4d;  c0=%3d;   c=%3d;  c0#c=%d\n", ecc_count,c0, c, (c0<<8)+c);
									c0=c;
									continue;
								}
							}
							lastcharTime=System.currentTimeMillis();
							if (firstcharTime==0) firstcharTime=lastcharTime;
							if (c == '\n') {
								if (line.endsWith("\r"))
									line = line.substring(0, line.length() - 1);
								lines.add(line);
								line = "";
							} else {
								line += (char) c;
							}
						}
						count++;
	                }
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("event: "+evt.getEventType()+", "+evt.getEventValue());
			}
		}

        public String readLine() throws IOException {
            if (t0==0) t0=System.currentTimeMillis();
            int inc=100; //millis
            for(int i=0; i<Math.max(10000 /*min is 10sec*/, 2*lasttime); i+=inc) {
                if (lines.size()>0) {
                    t1=System.currentTimeMillis();
                    lasttime=(int)(t1-t0);
                    t0=t1;
                    //System.out.println(lasttime);
                    return lines.remove(0);
                }
                try {
                    Thread.sleep(inc);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
            //throw new IOException("readLine() timeout");
        }

		public long getElapsedTime() {
			return lastcharTime-firstcharTime;
		}
    }

    /** */
    public static class SerialWriter implements Runnable
    {
        OutputStream out;
        private String cmd;

        public SerialWriter ( OutputStream out )
        {
            this.out = out;
        }

        public void run ()
        {
            try
            {
                while ( true )
                {
                    byte[] buf = getCmd();
                    Thread.sleep(10);
                    for (int i = 0; i < buf.length; i++) {
                        out.write(buf[i]);
                        Thread.sleep(10);
                    }
                }
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private synchronized byte[] getCmd() throws InterruptedException {
            while(true) {
                if (cmd!=null) break;
                wait();
            }
            byte[] res = cmd.getBytes();
            cmd=null;
            notifyAll();
            return res;
        }

        public synchronized void send(String cmd) throws InterruptedException {
            while(true) {
                if (this.cmd==null) break;
                wait();
            }
            this.cmd=cmd;
            notifyAll();
        }
    }

    private static SerialWriter sw;
    private static SerialReader sr;
    private static Communication con;

    public static void write(String cmd) throws InterruptedException {
        sw.send(cmd);
    }

    public static String readLine() throws IOException {
        return sr.readLine();
    }



    public static void main ( String[] args ) throws Exception
    {
        setUpSerialSpezial();
        sw.send("!;");
        System.out.print(sr.readLine());
        System.out.print(sr.readLine());
        System.out.print(sr.readLine());
        System.out.print(sr.readLine());
        System.out.print(sr.readLine());

        System.out.println(getDouble("123, 10 / ? ;"));
        System.out.println(getDouble("123, 10 + ? ;"));
        System.out.println(getDouble("123, 10 - ? ;"));
        System.out.println(getDouble("dmax() ? ;"));
        System.out.println(getDouble("dmin() ? ;"));
        System.out.println(getDouble("fmax() ? ;"));
        System.out.println(getDouble("fmin() ? ;"));
        for (int i = 0; i < 10; i++) {
            System.out.println(getDouble(i+", 10 + ? ;"));
        }
        for (int i = 0; i < 10; i++) {
            System.out.println(getDouble(i+" sqrt() ? ;"));
        }
    }

	public static void setUpSerial() throws Exception, IOException, InterruptedException {
        if (con!=null) con.close();
        con = new Communication();
        String[] list = port.split(",");
        if (list.length>1) {
            if (port.contains(",rcx")) {
            	System.out.println("connectRCX: "+port);
            	con.connectRCX(list[0], Integer.parseInt(list[1]));
            } else {
            	System.out.println("connect: "+port);
            	con.connect(list[0], Integer.parseInt(list[1]));
            }
        } else {
        	System.out.println("connect 57600: "+port);
            con.connect(port, 57600);
        }
        sw=new SerialWriter(con.getOutputStream());
        sr=new SerialReader(con.getInputStream());
        con.addEventListener(sr);
        Thread t0=new Thread(sw);
        t0.setDaemon(true);
        t0.start();
    }

    public static void setUpSerialSpezial() throws Exception, IOException, InterruptedException {
        setUpSerial();
        sr.readLine();
        sw.send("#;");
    }

    public static void tierDownSerial() throws Exception, IOException, InterruptedException {
        if (con!=null) con.close();
   }

    public static double getDouble(String cmd) throws InterruptedException, IOException {
        sw.send(cmd);
        String res=sr.readLine();
        res=res.replaceFirst(".+[;]", "");
        double d=Double.parseDouble(res);
        return d;
    }

    private static String[] concat(String[] A, String[] B) {
        if (A==null) return B;
        if (B==null) return A;
        int aLen = A.length;
        int bLen = B.length;
        String[] C= new String[aLen+bLen];
        System.arraycopy(A, 0, C, 0, aLen);
        System.arraycopy(B, 0, C, aLen, bLen);
        return C;
    }

    public static void testee(String clazz, String type) throws InterruptedException, Exception {
        System.setProperty("COMMAND_NAME", "haiku");
        String[] tmp = tmpOptions;
        tmpOptions=null;
        Vector<String> output = HaikuVM.executeBlocking(concat(new String[] {
                "src/test/resources/junit",
                type, target, mode, "-v",
                // "--bootclasspath",
                // "../bootstrap/bin;../haikuRT\bin;../lib/nxt/classes.jar",
                // "--classpath",
                // "../haikuBench/bin;../examples/bin;../gattaca/bin",
                clazz}, concat(permOptions, tmp)));

        String program="";
		String data="";
		for (String line : output) {
            if (line.startsWith("Program: ")) {
                program=line.replace("\r", "");;
            } else if (line.startsWith("Total image size=")) {
            	//h8300-hms-size
            	program=line.replace("\r", "").replace("Total image size=", "Program: ");
            } else if (line.startsWith("Data: ")) {
                data=line.replace("\r", "");;
            }
		}



		PrintWriter junitOut;
        Date dt = new Date();
        SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.S" );

        if (target.contains("cygwin") ||target.startsWith("unix") || target.startsWith("linux") || target.startsWith("pc")) {
//            output = HaikuVM.executeBlocking(concat(new String[] {
//                    "src/test/resources/junit",
//                    "execute", target, mode, "-v",
//                    // "--bootclasspath",
//                    // "../bootstrap/bin;../haikuRT\bin;../lib/nxt/classes.jar",
//                    // "--classpath",
//                    // "../haikuBench/bin;../examples/bin;../gattaca/bin",
//                    clazz}, options));
//            output.remove(0); // execute linux32 32/64 1 haikuvm.bench.Fibonacci
//            output.remove(0); // #
//            output.remove(0); // #
//            output.remove(0); // #
//            output.remove(0); // #############################################################
//            output.remove(0); // # upload file
//            output.remove(0); // #############################################################
//            output.remove(0); // ./Fibonacci
//            output.remove(output.size()-1); // #############################################################
//            output.remove(output.size()-1); // # upload done
//            output.remove(output.size()-1); // #############################################################
            junitOut = new PrintWriter(new FileWriter("src/test/resources/junit_" + target + "_" + mode.replace('/', '_') + ".log", true));
            junitOut.println("##########  Date: " + df.format( dt ) );        // '2001-01-26 19:03:56.731'
            junitOut.println("##########  "+clazz);
            Vector<String> newoutput = new Vector<String>();
            boolean inSync = false;
            for (String line : output) {
                if (inSync) {
                    newoutput.add(line);
                    junitOut.println(line);
                }
                if (line.startsWith("Program: ")) {
                    inSync=true;
                }
			}
            junitOut.println("##########  elapsed time:");
            junitOut.println("##########  " + program);
            junitOut.println("##########  " + data);
            output=newoutput;
        } else {
            setUpSerial();

            junitOut = new PrintWriter(new FileWriter("src/test/resources/junit_" + target + "_"
                    + mode.replace('/', '_') + ".log", true)) {
                public void println(String line) {
                    super.println(line);
                    System.out.println(line);
                }
            };

            output = new Vector<String>();

            junitOut.println("##########  Date: " + df.format( dt ) );        // '2001-01-26 19:03:56.731'
            junitOut.println("##########  "+clazz);
            String line;
            for (int i = 0; (line = readLine()) != null; i++) {
                output.add(line);
                junitOut.println(line);
            }
            junitOut.println("##########  elapsed time: " + sr.getElapsedTime() + " ms");
            junitOut.println("##########  " + program);
            junitOut.println("##########  " + data);

            tierDownSerial();
        }
        junitOut.println("##########  end");
        junitOut.println();
        junitOut.println();
        junitOut.close();

        assertWithTemplate(clazz, output);
    }

    public static void testee(String clazz) throws InterruptedException, Exception {
        testee(clazz, "develop_link_upload");
    }

    /**
##########  HelloWorld
Hello World!
old ##########  11 msec
##########  elapsed time: 11 msec
##########  Program:    5950 bytes (18.2% Full)
##########  Data:       1566 bytes (76.5% Full)

     *
     * @param clazz
     * @param output
     * @throws IOException
     */
    public static void assertWithTemplate(String clazz, Vector<String> output)
            throws IOException {
        String expected = "src/test/resources/valid_" + target + "_"
                + mode.replace('/', '_') + ".lst";
        MyJUnit ju = new MyJUnit(expected, clazz, output);
        ju.start();
    }
}
