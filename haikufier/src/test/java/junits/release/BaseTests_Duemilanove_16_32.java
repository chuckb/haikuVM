package junits.release;

import java.io.IOException;

import junits.BaseTests;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import base.JUnitSetUp;

import static base.JUnitSetUp.*;

public class BaseTests_Duemilanove_16_32 extends BaseTests {

    @BeforeClass
    public static void beforeTests() throws IOException, InterruptedException,
            Exception {
        JUnitSetUp.setTarget("duemilanove");
        JUnitSetUp.setMode("16/32");
        JUnitSetUp.setPermOptions(new String[] {"--Config:Port=COM9", "--Config:GC=HAIKU_IncrementalGC"});
    	JUnitSetUp.setPort("COM9,57600");
    }
   
    @Test
    public void test012_SerialPrint() throws Exception {
        testee("haikuvm.bench.SerialPrint");
    }

}
