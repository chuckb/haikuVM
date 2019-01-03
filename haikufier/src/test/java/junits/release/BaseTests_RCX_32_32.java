package junits.release;

import java.io.IOException;

import junits.BaseTests;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import base.JUnitSetUp;
import static base.JUnitSetUp.*;

public class BaseTests_RCX_32_32 extends BaseTests {

    @BeforeClass
    public static void beforeTests() throws IOException, InterruptedException,
            Exception {
        JUnitSetUp.setTarget("rcx");
        JUnitSetUp.setMode("32/32");
        JUnitSetUp.setPort("rcx");
    }

    @After
    public void tierDown() throws IOException, InterruptedException, Exception {
        base.JUnitSetUp.tierDownSerial();
    }



//
//    @Test
//    public void test014_SyncStatic() throws Exception {
//        testee("haikuvm.bench.SyncStatic");
//    }
//
//    @Test
//    public void test016_SyncDyn() throws Exception {
//        testee("haikuvm.bench.SyncDyn");
//    }


    //@Test
    public void test090_HeapFragmentation() throws Exception {
        testee("haikuvm.bench.HeapFragmentation");
    }

    //@Test
    public void test_Double2String() throws Exception {
        testee("haikuvm.bench.Double2String");
    }

    //@Test
    public void test090_MathTest() throws Exception {
        testee("nanovm.bench.MathTest");
    }

}
