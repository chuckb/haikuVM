package junits;

import java.io.IOException;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import base.JUnitSetUp;

import static base.JUnitSetUp.*;

public class BaseTests {

    @BeforeClass
    public static void beforeTests() throws IOException, InterruptedException,
            Exception {
    }

    @After
    public void tierDown() throws IOException, InterruptedException, Exception {
        base.JUnitSetUp.tierDownSerial();
    }

    @Test
    public void test010_JNIbeyond() throws Exception {
        testee("arduino.tutorial.JNIbeyond");
    }
    
    @Test
    public void test00880_PrimitiveFieldsTest() throws Exception {
        testee("haikuvm.bench.PrimitiveFieldsTest");
    }

    @Test
    public void test00890_ArrayFieldsTest() throws Exception {
        testee("haikuvm.bench.ArrayFieldsTest");
    }

    @Test
    public void test0090_Clone() throws Exception {
        testee("haikuvm.bench.Clone");
    }

    @Test
    public void test0090_EnumTest1() throws Exception {
        testee("haikuvm.bench.EnumTest1");
    }
    
    @Test
    public void test010_SimpleTest1() throws Exception {
        testee("haikuvm.bench.SimpleTest1");
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

    @Test
    public void test020_Fibonacci() throws Exception {
        testee("haikuvm.bench.Fibonacci");
    }

    @Test
    public void test030_arithmetic() throws Exception {
        testee("nanovm.bench.arithmetic");
    }

    @Test
    public void test040_Erathostenes() throws Exception {
        testee("nanovm.bench.Erathostenes");
    }

    @Test
    public void test050_FloatTest() throws Exception {
        testee("nanovm.bench.FloatTest");
    }

    @Test
    public void test060_FloatTest2() throws Exception {
        testee("nanovm.bench.FloatTest2");
    }

    //@Test
    public void test062_MathTestDouble() throws Exception {
        testee("haikuvm.bench.MathTestDouble");
    }

    @Test
    public void test070_Inheritance() throws Exception {
        testee("nanovm.bench.Inheritance");
    }

    @Test
    public void test080_JustFields0() throws Exception {
        testee("nanovm.bench.JustFields0");
    }

    @Test
    public void test090_QuickSort() throws Exception {
        testee("nanovm.bench.QuickSort");
    }

    @Test
    public void test090_HelloWorld() throws Exception {
        testee("nanovm.bench.HelloWorld");
    }

    @Test
    public void test090_count() throws Exception {
        testee("nanovm.bench.count");
    }

    @Test
    public void test090_MathTest() throws Exception {
        testee("nanovm.bench.MathTest");
    }

    @Test
    public void test090_DivByZero() throws Exception {
        JUnitSetUp.setTempOptions(new String[] {"--Config:PanicSupport=1"});
        testee("nanovm.bench.DivByZero");
    }

    @Test
    public void test090_OneClass() throws Exception {
        testee("nanovm.bench.OneClass");
    }

    @Test
    public void test090_MethodCall() throws Exception {
        testee("nanovm.bench.MethodCall");
    }

    @Test
    public void test090_Poly() throws Exception {
        testee("nanovm.bench.Poly");
    }

    @Test
    public void test090_SelfInstance() throws Exception {
        testee("nanovm.bench.SelfInstance");
    }

    @Test
    public void test090_Inheritance2() throws Exception {
        testee("nanovm.bench.Inheritance2");
    }

    @Test
    public void test090_icmp() throws Exception {
        testee("nanovm.bench.icmp");
    }

    @Test
    public void test090_DoubleTest2() throws Exception {
        testee("haikuvm.bench.DoubleTest2");
    }

    @Test
    public void test090_DoubleTest() throws Exception {
        testee("haikuvm.bench.DoubleTest");
    }

    @Test
    public void test090_FloatTest2() throws Exception {
        testee("nanovm.bench.FloatTest2");
    }

    @Test
    public void test090_StringAndHeapTest() throws Exception {
        testee("nanovm.bench.StringAndHeapTest");
    }

    @Test
    public void test090_Switch2() throws Exception {
        testee("nanovm.bench.Switch2");
    }

    @Test
    public void test090_Switch() throws Exception {
        testee("nanovm.bench.Switch");
    }

    @Test
    public void test090_ArrayTest() throws Exception {
        testee("haikuvm.bench.ArrayTest");
    }

    @Test
    public void test090_FibonacciFloat() throws Exception {
        testee("haikuvm.bench.FibonacciFloat");
    }

    @Test
    public void test090_Threads1() throws Exception {
        testee("haikuvm.bench.Threads1");
    }

    @Test
    public void test090_StaticTest() throws Exception {
        testee("nanovm.bench.StaticTest");
    }

    @Test
    public void test090_ExceptionTest() throws Exception {
        JUnitSetUp.setTempOptions(new String[] {"--Config:PanicSupport=1"});
        testee("haikuvm.bench.ExceptionTest");
    }

}
