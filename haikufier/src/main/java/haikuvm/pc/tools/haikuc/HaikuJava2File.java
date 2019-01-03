package haikuvm.pc.tools.haikuc;

import haikuvm.pc.tools.Haikufy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Formatter;

public class HaikuJava2File extends PrintOnChange {
    private static File dir;
	private static final String HAIKU_VM_SRC_UTILITY = "HaikuVM/src/utility";

    public HaikuJava2File(File file) {
        super(file);
    }

    public static boolean isSingleFile() {
        return dir!=null;
    }

    public static void setAppBase(String appBase) {
        dir=new File(appBase+"/" + HAIKU_VM_SRC_UTILITY);
    }

    public static File getGenTargetDir() {
        return dir;
    }
}
