package haikuvm.pc.tools;

import haikuvm.pc.tools.aot.AotFactory;
import haikuvm.pc.tools.aot.Compile2C;
import haikuvm.pc.tools.aot.Compile2Data;
import haikuvm.pc.tools.haikuc.HaikuClasses2H;
import haikuvm.pc.tools.haikuc.HaikuDefs;
import haikuvm.pc.tools.haikuc.HaikuJNI;
import haikuvm.pc.tools.haikuc.HaikuByteCode2H;
import haikuvm.pc.tools.haikuc.HaikuJava2File;
import haikuvm.pc.tools.haikuc.HaikuByteCodeTypes2H;
import haikuvm.pc.tools.haikuc.PrintOnChange;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Formatter;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.Vector;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.util.ClassPath;
import org.apache.bcel.util.SyntheticRepository;


public class HaikuVM extends Haikufy {

	private static boolean verbose;
    private static String aotOption="data";

	public HaikuVM(String classname) {
        super(classname);
    }

    /**
	 * javah [ options ] fully-qualified-classname. . .
	 *
	 * OPTIONS
  -d directory
Sets the directory where javah saves the header files or the stub files. Only one of -d or -o may be used.
  -stubs
Causes javah to generate C declarations from the Java object file.
  -version
Print out javah version information.
  -jni
Causes javah to create an output file containing JNI-style native method function prototypes. This is the default output, so use of -jni is optional.
  -classpath path
Specifies the path javah uses to look up classes. Overrides the default or the CLASSPATH environment variable if it is set. Directories are separated by semi-colons. Thus the general format for path is:
   .;<your_path>
For example:
   .;C:/users/dac/classes;C:/tools/java/classes

  -bootclasspath path
Specifies path from which to load bootstrap classes. By default, the bootstrap classes are the classes implementing the core Java platform located in jre\lib\rt.jar and several other jar files.
  -old
Specifies that old JDK1.0-style header files should be generated.
  -force
Specifies that output files should always be written.
  -Joption
Pass option to the Java virtual machine, where option is one of the options described on the reference page for the java application launcher. For example, -J-Xms48m sets the startup memory to 48 megabytes.


The -v or --verbose flag causes a list of class names and method signatures included in the binary to be sent to the standard output. This output is extremely useful for debugging.

The -g or --debug flag is not jet supported from HaikuVM.
The -gr or --remotedebug flag is not jet supported from HaikuVM.
The -od or --outputdebug flag is not jet supported from HaikuVM.
The -dm or --disablememcompactions flag is always set for HaikuVM. So memory compaction is always disabled and can not be enabled for HaikuVM. Normally a marture garbage collector will attempt to move large objects in memory to maximise the amount of contiguous free space, this is not implemented in the simple garbage collector of HaikuVM.

The -ec or --enablechecks flag is used to enable additional run time checks. These checks are relatively expensive (and rarely generate errors) and so are off by default. Currently the only check that this setting enables is the testing for *StackOverflowError* and *ArrayIndexOutOfBoundsException*.
The -ea or --enableassertions flag is used to enable the checking of assert statements with the program.

The linker removes methods that are not used. Specify -a or --all to include all methods whether they are used or not. This should never be necessary.

The -o <binary> option is used to define the name of the output file.
The -t or --target <target> flag is an addition of HaikuVM to specify (overwrite) the default target (see configuration properties).
Use the -h or --help flag to print out the options.

	 *
	 * An output like this would be nice too:
	 * http://lejos.sourceforge.net/forum/viewtopic.php?t=1215&highlight=exception
	 *
	 * @param args
	 * @throws Exception
	 */
    public static void main(String[] args) throws Exception {

        try {
            HaikuDefs.init();
            HaikuDefs.setProperty("APP_BASE", new File(".").getAbsolutePath());
            System.out.println();
            System.out.println(version());
            System.out.println();
            if ("haiku".equalsIgnoreCase(System.getProperty("COMMAND_NAME"))) {
                haiku0(args);
            } else if ("haikuc".equalsIgnoreCase(System.getProperty("COMMAND_NAME"))) {
                haikuc0(args);
            } else if ("haikulink".equalsIgnoreCase(System.getProperty("COMMAND_NAME"))) {
                haikulink(args);
            } else if ("haikuupload".equalsIgnoreCase(System.getProperty("COMMAND_NAME"))) {
                haikuupload0(args);
            } else {
                haikulink(args);
            }
            Verbose.summary();
            if (Verbose.isError()) System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.flush();
            Verbose.summary();
            System.out.flush();
            System.err.flush();

            System.exit(1);
        }
    }

	private static String version() {
		return "$Version 1.4.3 $ $Id: HaikuVM.java 765 2017-04-30 10:32:24Z genom2 $";
	}

    /**
     * cd C:\haikuVM\myCProject
     * C:\haikuVM\bin\haiku -v --Config asuro [-o Blink.hex] C:\haikuVM\examples\src\main\java\arduino\tutorial\Blink[.java]
     *
     *
     * cd C:\haikuVM\myCProject
     * C:\haikuVM\bin\haikuc                                   C:\haikuVM\examples\src\main\java\arduino\tutorial\Blink.java
     * C:\haikuVM\bin\haikulink -v --Config asuro -o Blink.hex C:\haikuVM\examples\src\main\java\arduino\tutorial\Blink
     * C:\haikuVM\bin\haikuupload                    Blink.hex
     *
     * @param args
     * @throws Exception
     */
    public static void haiku0(String[] args) throws Exception {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-v")) {
                verbose=true;
            }
        }
        haikuc0(args);
        haikulink0(args);
        haikuupload0(args);
    }

    public static void haikuc0(String[] args) throws Exception {
        String file=null;
        String filter[]={
                "-bootclasspath",
                "-classpath",
                "-extdirs",
        };
        Vector<String> vargs= new Vector<String>();
        for (int i = 0; i < args.length; i++) {
            if (!args[i].startsWith("-")) {
                file=args[i];
            }
            for (int j = 0; j < filter.length; j++) {
                if (args[i].endsWith(filter[j])) {
                    vargs.add(filter[j]);
                    vargs.add(args[++i]);
                    break;
                }
            }
        }
        if (file==null) usage("Missing option <java-file>", "");

        if (!file.endsWith(".java"))  {
            file+=".java";
        }

        vargs.add(file);
        call("haikuc", vargs);
    }

    public static void haikuupload0(String[] args) throws Exception {
        String file=null;
        Vector<String> vargs= new Vector<String>();
        for (int i = 0; i < args.length; i++) {
            if (!args[i].startsWith("-")) {
                file=new File(args[i]).getName();
                if (file.endsWith(".java"))  {
                    file=file.replace(".java", "");
                }

                if (!file.endsWith(HaikuDefs.getProperty("Extension")) && !"".equals(HaikuDefs.getProperty("Extension"))) {
                    file+=HaikuDefs.getProperty("Extension");
                }
            }
            if (args[i].endsWith("-o")) {
                file=args[++i];
                break;
            }
        }
        if (file==null) usage("Missing option <upload-file>", "");

        vargs.add(file);
        call("haikuupload", vargs);
    }

    public static void haikulink0(String[] args) throws Exception {
        Vector<String> vargs= new Vector<String>();
        for (int i = 0; i < args.length; i++) {
            vargs.add(args[i]);
        }
        call("haikulink", vargs);
    }

    private static void call(String cmd, Vector<String> vargs) throws Exception {
        vargs.add(0, new File(System.getProperty("haikuvm.home")).getAbsolutePath()+"/bin/"+cmd);
        String[] args = new String[vargs.size()];

        for (int i = 0; i < vargs.size(); i++) {
            if (verbose) System.out.print(vargs.get(i)+" ");
            args[i]=vargs.get(i);
        }
        if (verbose) System.out.println();
        executeBlocking(args);
    }

    private static void usage(String msg, String usage) {
        throw new RuntimeException(msg+"\n"+usage);
    }

    /**
     */
    public static void haikulink(String[] args) throws Exception {

		mainclass="";

		for (int i = 0; i < args.length; i++) {
			if (false) {
			    // just to align
			} else if (args[i].equals("--debug")
					|| args[i].equals("--remotedebug")
					|| args[i].equals("--outputdebug")
					|| args[i].equals("--disablememcompactions")
					|| args[i].equals("-g") || args[i].equals("-gr")
					|| args[i].equals("-od") || args[i].equals("-od")) {
				// not jet supported
			} else if (args[i].equals("--enableassertions")
					|| args[i].equals("-ea")) {
			} else if (args[i].equals("--enablechecks")
					|| args[i].equals("-ec")) {
				HaikuDefs.setProperty("PanicExceptions", "0xffff");
			} else if (args[i].equals("-o")) {
				HaikuDefs.setProperty("Output", args[++i]);
			} else if (args[i].equals("-d")) {
				HaikuDefs.setProperty("APP_BASE", args[++i]);
			} else if (args[i].equals("-stubs")) {
				HaikuDefs.setProperty("Opt-stubs", "true");
			} else if (args[i].equals("-compile")) {
				aotOption = "c";
			} else if (args[i].equals("-linker")) {
				aotOption = "data";
			} else if (args[i].equals("--verbose") || args[i].equals("-v")) {
				HaikuDefs.setProperty("Opt-v", "true");
			} else if (args[i].equals("--Config")) {
				HaikuDefs.setProperty("Config", args[++i]);
			} else if (args[i].startsWith("--Config:")) {
			    if (args[i].endsWith("=")) {
			        //--Config:Char= 1
	                HaikuDefs.setProperty(args[i].substring(9).replace("=", "").trim(), args[++i]);
			    } else if (args[i].contains("=")) {
                    //--Config:Char=1
                    String key=args[i].substring(9, args[i].indexOf('=')).trim();
                    String value=args[i].substring(args[i].indexOf('=')+1).trim();
                    HaikuDefs.setProperty(key, value);
			    } else {
			        String key=args[i].substring(9);
                    String value=args[++i];
                    if (value.equals("=")) {
                        //--Config:Char = 1
                        HaikuDefs.setProperty(key, args[++i]);
                    } else if (value.startsWith("=")) {
                        //--Config:Char =1
                        HaikuDefs.setProperty(key, value.substring(1));
                    } else {
                        //--Config:Char 1
                        HaikuDefs.setProperty(key, value);
                    }
			    }
			} else if (args[i].equals("--Mode")) {
				HaikuDefs.setProperty("Mode", args[++i]);
			} else if (args[i].equals("--help") || args[i].equals("-h")) {
			} else if (args[i].equals("-version")) {
			} else if (args[i].endsWith("-classpath")) {
				// .;<your_path>
				// .;C:/users/dac/classes;C:/tools/java/classes
				classpath += args[++i]+";";
			} else if (args[i].endsWith("-bootclasspath")) {
				bootclasspath += args[++i]+";";
			} else if (args[i].equals("-clean")) {
				clean = true;
			} else if (args[i].startsWith("-")) {
			    Verbose.warning("unknown option " + args[i]);
			} else {
				mainclass = args[i];
				if (mainclass.endsWith(".java")) {
				    mainclass=mainclass.substring(0, mainclass.length()-5);
				} else if (mainclass.endsWith(".class")) {
				    mainclass=mainclass.substring(0, mainclass.length()-6);
				}
				String name = new File(mainclass).getName();
				HaikuDefs.setProperty("APP_NAME", name);
			}
		}

		HaikuJava2File.setAppBase(HaikuDefs.getProperty("APP_BASE"));

		BuildMakes.deleteUtility();
        HaikuJava2File.getGenTargetDir().mkdirs();

		copyUserFiles(new File(mainclass).getParentFile());
		
		BuildMakes makes;
		if (System.getProperty("haikuvm.home")==null) {
			makes=new BuildMakes(HaikuDefs.getProperty("HAIKUVM_HOME")+"/"+HaikuDefs.getProperty("HAIKUVM4C"));
		} else {
			makes=new BuildMakes(System.getProperty("haikuvm.home")+"/"+HaikuDefs.getProperty("HAIKUVM4C"));
		}
		makes.copyVM();
		

		adjustClasspathAndMainClass();

        Haikufy.HAIKU_GenerateFullVM="1".equals(HaikuDefs.getProperty("GenerateFullVM"));

		if (System.getProperty("haikuvm.home")!=null && new File(System.getProperty("haikuvm.home")).getAbsoluteFile().getCanonicalPath().equals(new File(HaikuDefs.getProperty("APP_BASE")).getAbsoluteFile().getCanonicalPath())) {
		    Verbose.error("Error: Don't use HAIKUVM_HOME='"+System.getProperty("haikuvm.home")+"' as your project home. Please select an other directory.");
			return;
		}

		if (HaikuDefs.getProperty("APP_NAME")==null) {
		    Verbose.error("Error: No application to link");
			return;
		}
		if (HaikuDefs.getProperty("Config")==null) {
			HaikuDefs.setProperty("Config", "arduino");
			Verbose.warning("Warning: Using default --Config arduino");
		}

		try {
			getClassFile(HaikuDefs.getProperty("MicroKernel"));
			mainOsThread=HaikuDefs.getProperty("MicroKernel");
		} catch (Exception e) {
		    Verbose.warning("Warning: no MicroKernel named '"+HaikuDefs.getProperty("MicroKernel")+"' was found");
		}

		if (mainOsThread==null) {
		    if (HaikuDefs.getProperty("MicroKernel")==null || "undef".equals(HaikuDefs.getProperty("MicroKernel")) ) {
	            Verbose.warning("Warning: no MicroKernel used.");
		        mainOsThread=mainclass;
		    } else {
			     throw new Exception("Missing target system or missing MicroKernel for '"+HaikuDefs.getProperty("Config")+"'. See Option --Config <config>");
		    }
		}
        Closure.root(mainOsThread);

		if (Closure.size()>0) {
            if (clean) deleteAllFiles(new File(HaikuDefs.getProperty("APP_BASE")+"/target"), clean);
            deleteAllFiles(HaikuJava2File.getGenTargetDir(), clean);
            HaikuJava2File.getGenTargetDir().mkdirs();

            haikuJNIc=new HaikuJNI(HaikuDefs.getProperty("APP_BASE"));

            // Must be after Closure.root(..) because of HAIKU_Threads may become 1
            new HaikuDefs().print();

			File cf=new File(HaikuJava2File.getGenTargetDir()+"/haikuConfig.h");
			haikuConfigh=new PrintOnChange(cf);
			haikuConfigh.println("#include \"haikuJ2C.h\"");
			haikuConfigh.print("\n");
			haikuConfigh.print("#ifdef __cplusplus\n");
			haikuConfigh.print("extern \"C\" {\n");
			haikuConfigh.print("#endif\n");
			haikuConfigh.print("\n");


			File ni=new File(HaikuJava2File.getGenTargetDir()+"/haikuConfig.c");
			haikuConfigc=new PrintOnChange(ni);
			haikuConfigc.println("#include \"haikuConfig.h\"");
			haikuConfigc.println();
			//haikuConfigc.printf("#include \"Bytecodes.h\"\n");
			haikuConfigc.printf("#include <float.h>\n");
			haikuConfigc.printf("\n");


            Closure.root("java.lang.Thread");
            Closure.root("java.lang.String");

            int distinctBCs = Closure.getDistinctBCs();
            FunctionTable.setInvokeShortMax(Math.max(256-distinctBCs-35, 0));

			/*
			 * Order is important to keep exceptionCode at position 0 in case of exception usage.
			 *
			 * statics_t allStatics;
			 * //jobject:
			 * ...
			 * //jint:
			 * //				0	haiku_vm_arduino_HaikuMicroKernel_exceptionCode
			 * //				1	haiku_vm_arduino_HaikuMicroKernel_exceptionArg
			 *
			 * FIRST
			 */
            AotFactory aot = new AotFactory(aotOption);
			if(mainOsThread!=null) {
			    aot.get(mainOsThread).compile();
			}

            /*
             * SECOND
             */
            if (!mainOsThread.equals(mainclass)) {
                aot.get(mainclass).compile();
            }

            /*
             * THIRD
             *
             */
            aot.get("java/lang/Thread").compile();

            /*
             * FOURTH
             *
             */
            aot.get("java/lang/String").compile();

			Msg2Meth.printAllMsgsH(haikuConfigh);

			BC2IDX.printAllBCdefinesH(haikuConfigh);
			BC2IDX.printBCtableC(haikuConfigc);
			PrintOnChange bytecodeDispatcher=new PrintOnChange(new File(HaikuJava2File.getGenTargetDir()+"/bytecodeDispatcher.h"));
			BC2IDX.printBytecodeLabels(bytecodeDispatcher);
			bytecodeDispatcher.close();

			if (singleFile) {
		        haikuConfigc.printf("\n");
                haikuConfigc.printf("\n");
                haikuConfigc.println("#include \"haikuByteCodeTypes.h\"");
			} else {
			    CollectedIncludes.printC(haikuConfigc);
			}

			CIDX.printCIDXdefinesH(haikuConfigh);
			CIDX.printCIDXtableC(haikuConfigc);

			ObjSpace.printH(haikuConfigh);

			haikuConfigc.println();
			haikuConfigc.println();
			haikuConfigc.println("Exception_t const  	exceptionTable[] PROGMEM = {");
			haikuConfigc.print(exceptionTable);
			haikuConfigc.println("\t{NULL, NULL, NULL, NULL}");
			haikuConfigc.println("};");
			haikuConfigc.println();
            haikuConfigc.print(functionTable);
            haikuConfigc.print(new ClassTable());



			haikuConfigh.print("\n");
			haikuConfigh.print("#ifdef __cplusplus\n");
			haikuConfigh.print("}\n");
			haikuConfigh.print("#endif\n");
			haikuConfigh.close();
			haikuConfigc.close();
			haikuJNIc.close();
            HaikuByteCodeTypes2H.closeAll();
            HaikuByteCode2H.closeAll();
            HaikuClasses2H.closeAll();
    		
    		makes.build();
    		
			Verbose.printf("totalMethods=      %d\n", totalMethods);
			Verbose.printf("totalBClength=     %d\n", totalBClength);
			Verbose.printf("totalConstLength=  %d\n", totalConstLength);
			Verbose.printf("totalClassesLength=%d\n", totalClassesLength);

            if (!Clinit.set().isEmpty() && !conditions.contains(Condition.CLINIT)) {
                Verbose.error("Found static initializers but no method clinitHaikuMagic() to place them.");
                for (Clinit desc : Clinit.set()) {
                    System.err.println("    "+desc.getClassName());
                }
                System.err.println("  Consider to declare an (empty) method 'static void clinitHaikuMagic()' and call it in main.");
            }
            if (!conditions.contains(Condition.MAIN)) {
                Verbose.error("Did not found a 'static void main(String[] args)' method to start.");
            }
		}
	}

	/**
	 * Collect and copy user C files into HaikuVM/src/utility flat.
	 * This is, if a user has c/cpp/h/ino files in his java Main source directory. For example see:
	 *
	 *   /gallerie/src/main/java/avr/gallerie/user/rp/arduino/sensor/Tsic506Main.java
	 *   /gallerie/src/main/java/avr/gallerie/user/rp/arduino/sensor/Tsic506.h/.cpp
	 *
	 * Then these files are copied flat into HaikuVM/src/utility.
	 *
	 * See FAQ: If I use JNI, where need the .h/.cpp files to be placed to get picked up?
	 *
	 *
	 * @throws IOException
	 */
	private static void copyUserFiles(File srcdir) throws IOException {
    	Verbose.println("user .h/.c/.cpp files from "+srcdir.getAbsolutePath()+" into "+HaikuJava2File.getGenTargetDir());
		if (srcdir!=null && srcdir.exists()) {
			File files[]=srcdir.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
				} else if (files[i].getName().endsWith(".c") || files[i].getName().endsWith(".cpp") || files[i].getName().endsWith(".h") || files[i].getName().endsWith(".ino")){
					// TODO would be good to have a own directory but i don't know how this is possible with #include
					File target = new File(HaikuJava2File.getGenTargetDir()	+ "/" + files[i].getName());
					Verbose.println("Copy user file '"+files[i].getAbsolutePath()+"'\n\tinto '"+target.getAbsolutePath()+"'.");

					InputStream is=new FileInputStream(files[i]);
					OutputStream os = new FileOutputStream(target);
					int b;
					while ((b=is.read())!=-1) {
						os.write(b);
					}
					is.close();
					os.close();
				}
			}
		}
	}

	private static synchronized void println(PrintStream out, String line) {
    	out.println(line);
    }

    /**
     * executes the command and waits until it exits.
     *
     * @param args
     *            the command to execute
     * @return list of stdout lines of command
     * @throws Exception
     */
    public static Vector<String> executeBlocking(String[] args) throws Exception {
        final Vector<String> output = new Vector<String>();
        class ByteFlushThread extends Thread {
            protected InputStream is;
            private PrintStream out;
            public ByteFlushThread(InputStream pIs, PrintStream out,
                    String name) {
                super(name);
                this.is = pIs;
                this.out = out;
                setDaemon(true);
                start();
            }

            @Override
            public void run() {
                try {
                    String line="";
                    int c;
                    while ((c=is.read())!= -1) {
                        if (c=='\n') {
                            if (out!=null) {
                                if (line.endsWith("\r")) line=line.substring(0, line.length()-1);
                                output.add(line);
                                println(out, line);
                            }
                            line="";
                        } else {
                            line+=(char)c;
                        }
                    }
                } catch (final IOException e) {
                }
            }
        };

        if (System.getProperty("os.name").startsWith("Windows")) {
            args[0]+=".bat";
        }
        Process p = Runtime.getRuntime().exec(args);
        Thread tout = new ByteFlushThread(p.getInputStream(), System.out, "-out");
        Thread terr = new ByteFlushThread(p.getErrorStream(), System.err, "-err");
//        Thread terr = new ByteFlushThread(p.getErrorStream(), verbose?System.err:null, "-err");

        final int exitCode = p.waitFor();

        tout.join();
        terr.join();

        if (exitCode!=0) throw new Exception("Script '"+args[0]+"' exited with errorcode="+exitCode);
        return output;
    }

}
