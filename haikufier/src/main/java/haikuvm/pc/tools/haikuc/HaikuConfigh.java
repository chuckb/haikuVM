package haikuvm.pc.tools.haikuc;

import haikuvm.pc.tools.Haikufy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class HaikuConfigh {

    private PrintStream out;

    private HaikuConfigh(File file) throws FileNotFoundException {
        out=new PrintStream(file);
        out.println("#ifdef __cplusplus\n");
        out.println("extern \"C\" {\n");
        out.println("#endif\n");
        out.println("\n");

    }

    public HaikuConfigh() throws FileNotFoundException {
        this(new File(HaikuJava2File.getGenTargetDir()+"/haikuConfig.h"));
    }

    public void close() {
    	out.println("#ifdef __cplusplus");
    	out.println("}");
    	out.println("#endif");
    }
}
