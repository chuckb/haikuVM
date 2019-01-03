package junits.release;

import java.io.IOException;

import junits.BaseTests;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import base.JUnitSetUp;

import static base.JUnitSetUp.*;

public class BaseTests_Duemilanove_Call_16_32 extends BaseTests {

    @BeforeClass
    public static void beforeTests() throws IOException, InterruptedException,
            Exception {
        JUnitSetUp.setTarget("duemilanove");
        JUnitSetUp.setMode("16/32");
        //JUnitSetUp.setPermOptions(new String[] {"--Config:AOTVariant=HAIKU_AOTThreadedAsCall"});
        JUnitSetUp.setPermOptions(new String[] {"--Config:AOTVariant=HAIKU_AOTBytecodeAsSwitch"});

   }
   
    @Test
    public void test012_SerialPrint() throws Exception {
        testee("haikuvm.bench.SerialPrint");
    }

}
