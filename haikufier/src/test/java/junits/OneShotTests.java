package junits;

import java.io.IOException;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import base.JUnitSetUp;
import static base.JUnitSetUp.*;

public class OneShotTests {
   
    @BeforeClass
    public static void beforeTests() throws IOException, InterruptedException, Exception {
//      JUnitSetUp.setTarget("rcx");
//      JUnitSetUp.setTarget("junit.cygwin");
//        JUnitSetUp.setMode("16/32");
//        JUnitSetUp.setMode("32/64");
//        JUnitSetUp.setTarget("pc");
//        JUnitSetUp.setMode("16/32");
        
//        JUnitSetUp.setTarget("arduinoIDEUpload");
//        JUnitSetUp.setMode("16/32");
    }

    @After
    public void tierDown() throws IOException, InterruptedException, Exception {
        base.JUnitSetUp.tierDownSerial();        
    }
    
    //@Test
    public void test010_JNIbeyond() throws Exception {
        testee("arduino.tutorial.JNIbeyond");
    }

    //@Test
    public void test090_JNINativeLIBC() throws Exception {
        testee("arduino.tutorial.JNINativeLIBC");
    }

    //@Test
    public void test090_Threads1() throws Exception {
        testee("haikuvm.bench.Clone");
    }

    //@Test
    public void test090_SynchronizedThreads3() throws Exception {
        testee("haikuvm.bench.SynchronizedThreads3");
    }

    @Test
    public void oneShot() throws Exception {
        //JUnitSetUp.setOptions(new String[] {"--Config:PanicSupport=1", "--Config:MemorySize=66000"});
        //JUnitSetUp.setOptions(new String[] {"--Config:MemorySize=66000"});
        //JUnitSetUp.setOptions(new String[] {"--Config:AOTVariant=HAIKU_AOTThreadedAsJump"});
        //JUnitSetUp.setOptions(new String[] {"--Config:AOTVariant=HAIKU_AOTThreadedAsCall"});
        //JUnitSetUp.setOptions(new String[] {"--Config:AOTVariant=HAIKU_AOTBytecodeAsSwitch"});
        //testee("haikuvm.bench.Fibonacci");
        //testee("processing.examples._99_Others.Hello");
        //testee("haikuvm.bench.MBBenchmark");
        testee("nanovm.bench.BenchmarkPicker");

        //testee("haikuvm.bench.PrimitivFieldsTest");
        //testee("arduino.tutorial.NativeCVariableEnhanced");
        //testee("haikuvm.bench.ArrayFieldsTest");
        //testee("haikuvm.bench.ClassMethods");
    }
    
    //@Test
    public void test090_ExceptionTest() throws Exception {
        testee("haikuvm.bench.ExceptionTest");
    }

    //@Test
    public void test090() throws Exception {
        testee("haikuvm.bench.MemoryOverflow2", "link_upload_PanicSupport");
    }
}
