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

public class HaikuByteCodeTypes2H extends HaikuJava2File {
    static private StringBuilder single=new StringBuilder();
	static private boolean hasWritten;

    public HaikuByteCodeTypes2H(File file) {
        super(file);
        if(!hasWritten) {
            println("#ifdef __cplusplus");
            println("extern \"C\" {");
            println("#endif");
            println();
            hasWritten=true;
        }
        println("//////////////////////////////////////////////////");
        println("// "+file.getAbsolutePath());
        println("//");
        println();
    }

    public void close() throws IOException {
    	single.append(buffer);
    }

    public static void closeAll() throws IOException {
        if (isSingleFile()) {
        	single.append("\n");
        	single.append("#ifdef __cplusplus\n");
        	single.append("}\n");
        	single.append("#endif\n");
            close(new File(getGenTargetDir()+"/haikuByteCodeTypes.h"), single);
        }
    }
}
