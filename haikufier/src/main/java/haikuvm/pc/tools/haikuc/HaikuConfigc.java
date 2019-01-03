package haikuvm.pc.tools.haikuc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class HaikuConfigc {

    private PrintStream out;

    private HaikuConfigc(File file) throws FileNotFoundException {
        out=new PrintStream(file);
    }

    public HaikuConfigc() throws FileNotFoundException {
        this(new File(HaikuJava2File.getGenTargetDir()+"/haikuConfig.c"));
    }

    public void close() {
    }
}
