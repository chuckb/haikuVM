package haikuvm.pc.tools.haikuc;

import haikuvm.pc.tools.Haikufy;
import haikuvm.pc.tools.Msg2Meth;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Formatter;
import java.util.HashSet;
import java.util.Set;

import org.apache.bcel.classfile.JavaClass;

public class HaikuClasses2H extends HaikuJava2File {
    static private StringBuilder single=new StringBuilder();
	static private boolean hasWritten;
	static private Set<String> written= new HashSet<String>();

    public HaikuClasses2H(File file) {
        super(file);
//        if (!hasWritten) {
//            printf("\n");
//            printf("#include \"haikuConfig.h\"\n");
//            printf("#include \"haikuJava.h\"\n");
//            printf("\n");
//            hasWritten=true;
//        }
        printf("//////////////////////////////////////////////////\n");
        printf("// "+file.getAbsolutePath()+"\n");
        printf("//\n");
    }

    public void close() throws IOException {
    	single.append(buffer);
    	single.append("\n\n");
    }

    public void haikuArrayData(String array, String elementtype) throws IOException {
    	if (written.add(array)) {
    		printf("\n");
    		printf("const class_t %s PROGMEM = {\n", array);
    		printf("    & java_lang_Object__class,\n");
    		printf("    sizeof("+elementtype+"),\n");
    		printf("    // 0,\n");
    		printf("    // {{}} VC 5\n");
    		printf("};\n\n");
    	}
	}

    public void haikuData(JavaClass jc, Msg2Meth msg2meth) {
		printf("const class_t %s__class PROGMEM = {\n", Haikufy.mangle(jc.getClassName()));
		if (jc.getClassName().equals(jc.getSuperclassName())) {
			printf("	NULL,\n");
		} else {
			printf("	& %s__class,\n", Haikufy.mangle(jc.getSuperclassName()));
		}
		printf("	SIZEOF_%s,\n", Haikufy.mangle(jc.getClassName()));
		msg2meth.printC(this);
		printf("};\n\n");
	}

	public static void closeAll() throws IOException {
        close(new File(getGenTargetDir()+"/haikuClasses.h"), single);
    }

}
