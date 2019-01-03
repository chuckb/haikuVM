import haikuvm.pc.tools.BC2IDX;
import haikuvm.pc.tools.haikuc.PrintOnChange;

import java.io.File;
import org.junit.Test;


public class ForceAllByteCodes {   
    
    @Test
    public void printBytecodeLabels() throws Exception {
        PrintOnChange bytecodeDispatcher=new PrintOnChange(new File("AllBytecodeDispatcher.h"));
        BC2IDX.printBytecodeLabels(bytecodeDispatcher);
        bytecodeDispatcher.close();
    }
}
