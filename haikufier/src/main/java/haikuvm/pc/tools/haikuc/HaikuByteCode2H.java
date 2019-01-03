package haikuvm.pc.tools.haikuc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Formatter;

public class HaikuByteCode2H extends HaikuJava2File {
    static private StringBuilder single=new StringBuilder();

    public HaikuByteCode2H(File file) {
        super(file);
        println("//////////////////////////////////////////////////");
        println("// "+file.getAbsolutePath());
        println("//");
    }

    public void close() throws IOException {
        if (isSingleFile()) {
            single.append(buffer);
            single.append("\n\n");
        } else {
            super.close();
        }
    }

    public static void closeAll() throws IOException {
        if (isSingleFile()) {
            close(new File(getGenTargetDir()+"/haikuByteCode.h"), single);
        }
    }

}
