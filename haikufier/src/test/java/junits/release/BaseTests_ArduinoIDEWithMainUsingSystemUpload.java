package junits.release;

import java.io.IOException;

import junits.BaseTests;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import base.JUnitSetUp;
import static base.JUnitSetUp.*;

public class BaseTests_ArduinoIDEWithMainUsingSystemUpload extends BaseTests {

    @BeforeClass
    public static void beforeTests() throws IOException, InterruptedException,
            Exception {
        JUnitSetUp.setTarget("arduinoIDEWithMainUsingSystemUpload");
        JUnitSetUp.setMode("16/32");
        JUnitSetUp.setPort("COM9,19200");
    }

    @After
    public void tierDown() throws IOException, InterruptedException, Exception {
        base.JUnitSetUp.tierDownSerial();
    }

    @Test
    public void test0090_EnumTest1() throws Exception {
    	//throw new Exception("ArduinoIDE gcc stalls with 1.6.4");
        testee("haikuvm.bench.EnumTest1");
    }


    @Test
    public void test090_ExceptionTest() throws Exception {
    	//throw new Exception("ArduinoIDE gcc stalls with 1.6.4");
        JUnitSetUp.setTempOptions(new String[] {"--Config:PanicSupport=1"});
        testee("haikuvm.bench.ExceptionTest");
    }

}
