package junits.release;

import java.io.IOException;

import junits.BaseTests;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import base.JUnitSetUp;

import static base.JUnitSetUp.*;

public class ArduinoIDEUpload_16_32 {

    @BeforeClass
    public static void beforeTests() throws IOException, InterruptedException,
            Exception {
        JUnitSetUp.setTarget("arduinoIDEUpload");
        JUnitSetUp.setMode("16/32");
    }
    
    @After
    public void tierDown() throws IOException, InterruptedException, Exception {
        base.JUnitSetUp.tierDownSerial();
    }

    @Test
    public void test010_Hello() throws Exception {
        testee("processing.examples._99_Others.Hello");
    }

    @Test
    public void test020_HelloWorld() throws Exception {
        testee("processing.examples._99_Others.HelloWorld");
    }

}
