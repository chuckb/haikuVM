package haikuvm.pc.tools;
import haikuvm.pc.tools.haikuc.HaikuDefs;
import haikuvm.pc.tools.haikuc.HaikuJava2File;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
C_SRC
OBJ
C_DEP
SUBDIR
REL_TARGET
REL_SOURCE
HAIKU_APP_BASE D:\Entwicklung\sourceforge.haikuvm\trunk\asuroApp
HAIKU_VM_BASE D:\\Entwicklung\\sourceforge.haikuvm\\trunk\\haikuVM
HAIKU_CC_OPT -Wall -Os -fpack-struct -fshort-enums -std=gnu99 -funsigned-char -funsigned-bitfields -ffunction-sections -fdata-sections
HAIKU_TARGET atmega8
HAIKU_CLOCK 8000000
HAIKU_APP_NAME asuroApp
 */
public class BuildMakes {

	private File dirTargetC;
	private Vector<File> allDirs= new Vector<File>();
	private File dirTargetObj;
	private File dirHaikuVMSource;

	public BuildMakes(String haikuVM) {
		dirHaikuVMSource=new File(haikuVM);
		dirTargetC=new File(HaikuDefs.getProperty("APP_BASE"));
//        dirCTarget = new File(dirCSource.getAbsolutePath()+"/target/"+HaikuDefs.getProperty("Config"));
		// makes *.bat files simpler
        if (true || !Haikufy.singleFile) {
            dirTargetObj = new File(dirTargetC.getAbsolutePath()+"/target/cross");
        }
	}


	public void copyVM()  throws IOException {
		if (dirHaikuVMSource.exists()) {
			if (!new File(dirTargetC.getAbsolutePath()+"/HaikuVM").exists() || lastModified(dirHaikuVMSource.getAbsolutePath()+"/src/HaikuAppMain.c")>lastModified(dirTargetC.getAbsolutePath()+"/HaikuVM/src/HaikuAppMain.c")) {
				// conditional copy of HaikuVM-VM
				Verbose.println("Copy HaikuVM-VM into '"+dirTargetC.getAbsolutePath()+"'.\n\tBecause "
						+(!(new File(dirTargetC.getAbsolutePath()+"/HaikuVM")).exists()?
								("'"+dirTargetC.getAbsolutePath()+"/HaikuVM' does not exists!"):
									("'"+dirHaikuVMSource.getAbsolutePath()+"/src/HaikuAppMain.c' is newer than '"+dirTargetC.getAbsolutePath()+"/HaikuVM/src/HaikuAppMain.c'"))+".\n");
				copyHaikuVM(dirHaikuVMSource);
			}
			// unconditional copy of HaikuVM/utility only
			// because we called deleteUtility() (see HaikuVM#haikulink()) to get rid of user files
			// and we need this directory for collectDirs(..) see here below.
			//copyHaikuVM(new File(dirHaikuVMSource.getAbsolutePath()+"/utility"));
			HaikuJava2File.getGenTargetDir().mkdirs();
		} else {
			Verbose.warning("'"+dirHaikuVMSource.getAbsolutePath()+"' does not exist.");
		}
	}

	public void build() throws IOException {
		if (true || !Haikufy.singleFile) {
			System.out.printf("Building makefile for all subdirectories in your given directory '"+dirTargetC+"' ...\n");
	        collectDirs(dirTargetC);
	        build_subdir_mk();
	        copy_makefile();
	        printMakeDefs();
		}
	}

	private long lastModified(String filename) {
		return new File(filename).lastModified();
	}

	private void copyHaikuVM(File srcdir) throws IOException {
		if (srcdir.exists()) {
			File files[]=srcdir.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					copyHaikuVM(files[i]);
				} else if (files[i].getName().endsWith(".c") || files[i].getName().endsWith(".cpp") || files[i].getName().endsWith(".h") || files[i].getName().endsWith(".ino") || files[i].getName().endsWith(".x") || files[i].getName().endsWith(".properties")){
					File target;
					String vdir=files[i].getAbsolutePath().substring(dirHaikuVMSource.getAbsolutePath().length()).replace('\\', '/');

					File subdir=new File(dirTargetC.getAbsolutePath()+"/HaikuVM/"+vdir).getParentFile();
					target = new File(subdir.getAbsolutePath()+"/"+files[i].getName());
					subdir.mkdirs();

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

	private void copy_makefile() throws IOException {
		InputStream is=new FileInputStream(dirHaikuVMSource.getAbsolutePath()+"/haiku.makefile");
		OutputStream os = new FileOutputStream(dirTargetObj.getAbsolutePath()+"/makefile");
		int b;
		while ((b=is.read())!=-1) {
			os.write(b);
		}
		is.close();
		os.close();
	}

	/**
	################################################################################
	# Automatically-generated file. Do not edit!
	################################################################################

	# Add inputs and outputs from these tool invocations to the build variables
	C_SRCS += \
	../haikuJava/robot/asuro/AsuroMultiTasking.c \
	../haikuJava/robot/asuro/AsuroMultiTasking_avoid_Task.c \
	../haikuJava/robot/asuro/AsuroMultiTasking_blink_Task.c \
	../haikuJava/robot/asuro/AsuroMultiTasking_cruise_Task.c

	OBJS += \
	./haikuJava/robot/asuro/AsuroMultiTasking.o \
	./haikuJava/robot/asuro/AsuroMultiTasking_avoid_Task.o \
	./haikuJava/robot/asuro/AsuroMultiTasking_blink_Task.o \
	./haikuJava/robot/asuro/AsuroMultiTasking_cruise_Task.o

	C_DEPS += \
	./haikuJava/robot/asuro/AsuroMultiTasking.d \
	./haikuJava/robot/asuro/AsuroMultiTasking_avoid_Task.d \
	./haikuJava/robot/asuro/AsuroMultiTasking_blink_Task.d \
	./haikuJava/robot/asuro/AsuroMultiTasking_cruise_Task.d


	# Each subdirectory must supply rules for building sources it contributes
	haikuJava/robot/asuro/%.o: ../haikuJava/robot/asuro/%.c
	 * @throws FileNotFoundException
		@echo 'Building file: $<'
		@echo 'Invoking: AVR Compiler'
		avr-gcc -I"D:\Entwicklung\sourceforge.haikuvm\trunk\asuroApp" -I"D:\Entwicklung\sourceforge.haikuvm\trunk\haikuVM" -Wall -Os -fpack-struct -fshort-enums -std=gnu99 -funsigned-char -funsigned-bitfields -ffunction-sections -fdata-sections -mmcu=atmega8 -DF_CPU=8000000UL -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -c -o"$@" "$<"
		@echo 'Finished building: $<'
		@echo ' '
	*/
	private void build_subdir_mk() throws FileNotFoundException {
		String base = dirTargetC.getAbsolutePath();
		dirTargetObj.mkdirs();
		PrintStream out = new PrintStream(dirTargetObj.getAbsolutePath()+"/subdir.mk");
		out.println("################################################################################");
		out.println("# Automatically-generated file from 'BuildMakes.java'. Do not edit!");
		out.println("################################################################################");
		out.println();

		for (File d : allDirs) {
			Verbose.println(d.getAbsolutePath());
			String vdir=d.getAbsolutePath().substring(base.length(), d.getAbsolutePath().length()).replace('\\', '/');
			File t=new File(dirTargetObj.getAbsolutePath()+vdir);
			t.mkdirs();

			Vector<String> v= new Vector<String>();
			for (File f : d.listFiles()) {
				if (f.getName().endsWith(".c") || f.getName().endsWith(".cpp")) {
					//Haikout.println("   "+f.getAbsolutePath());
					v.add(f.getAbsolutePath().substring(base.length(), f.getAbsolutePath().length()).replace('\\', '/'));
				}
			}

			out.println();
			out.println();
			out.println();
			out.println();
			out.println("# Add inputs and outputs from these tool invocations to the build variables");
			out.println("C_SRCS += \\");
			for (int i = 0; i < v.size(); i++) {
				out.println("../.."+v.get(i)+" \\");
			}

			out.println();
			out.println("OBJS += \\");
			for (int i = 0; i < v.size(); i++) {
				out.println("."+withoutExt(v.get(i))+".o \\");
			}

			out.println();
			out.println("C_DEPS += \\");
			for (int i = 0; i < v.size(); i++) {
				out.println("."+withoutExt(v.get(i))+".d \\");
			}

			out.println();
			out.println("SUBDIRS += \\");
			out.println("."+vdir);
			out.println();
            out.println();
            out.println("# Each subdirectory must supply rules for building sources it contributes");
            out.println("."+vdir+"/%.o:\t../.."+vdir+"/%.c");
            out.println("\t@echo 'Building file: $<'");
            out.println("\t@echo 'Invoking: C Compiler'");
            out.println("\t$(HAIKU_CC)  $(HAIKU_CC_OPT)");
            out.println("\t@echo 'Finished building: $<'");
            out.println("\t@echo ' '");
            out.println();
            out.println("."+vdir+"/%.o: ../.."+vdir+"/%.cpp");
            out.println("\t@echo 'Building file: $<'");
            out.println("\t@echo 'Invoking: C++ Compiler'");
            out.println("\t$(HAIKU_CXX)  $(HAIKU_CXX_OPT)");
            out.println("\t@echo 'Finished building: $<'");
            out.println("\t@echo ' '");
		}
		out.close();
	}

	private String withoutExt(String name) {
        if (name.endsWith(".c")) return name.substring(0, name.length()-2);
        if (name.endsWith(".cpp")) return name.substring(0, name.length()-4);
        return null;
    }

    private boolean collectDirs(File d) throws IOException {
		File[] fl = d.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.isDirectory();
			}
		});
		if (fl==null) throw new IOException("This is not a valid directory for the HaikuVM C file search '"+d.getAbsolutePath()+"'");
		for (File dir : fl) {
			collectDirs(dir);
		}
		for (File f : d.listFiles()) {
			if (f.getName().endsWith(".c") || f.getName().endsWith(".cpp")) {
				allDirs.add(d);
				return true;
			}
		}
		return false;
	}

	/**
	 * HAIKU_APP_NAME = asuroTest
	 * HAIKU_TARGET = atmega8
	 * HAIKU_CLOCK = 8000000
	 * HAIKU_VM_BASE =  D:\Entwicklung\asuroTest\C\haikuVM
	 * HAIKU_APP_BASE = D:\Entwicklung\asuroTest\C
	 * HAIKU_CC_OPT = -Wall -Os -fpack-struct -fshort-enums -std=gnu99 -funsigned-char -funsigned-bitfields -ffunction-sections -fdata-sections -mmcu=$(HAIKU_TARGET) -DF_CPU=$(HAIKU_CLOCK)UL -MMD -MP
	 *
	 * @param dir
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void printMakeDefs() throws FileNotFoundException {
		File file = new File(dirTargetObj.getAbsolutePath()+"/makefile.defs");
		PrintStream defs=new PrintStream(file);
		for (String property : HaikuDefs.defs()) {
			String commit=HaikuDefs.getProperty(property);
			String raw=HaikuDefs.getRaw(property);
			if (!HaikuDefs.isSupported(property)) {
				defs.printf("# Warning: unsupported value '%s' for property '%s'!\n", raw, property);
			} else if (!raw.equals(commit)) {
				defs.printf("# Mapped '%s' to '%s'\n", raw, commit);
			}
			defs.printf("HAIKU_%s = %s\n", property.toUpperCase(), commit);
		}
		defs.close();
	}

	public static void deleteUtility() {
		File utility = HaikuJava2File.getGenTargetDir();
		if (utility.isDirectory()) {
			File files[]=utility.listFiles();
			for (int i = 0; i < files.length; i++) {
				files[i].delete();
			}
			utility.delete();
		}
	}
}
