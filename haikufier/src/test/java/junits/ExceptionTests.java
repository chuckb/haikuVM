package junits;

import haikuvm.pc.tools.HaikuVM;

import java.io.IOException;
import java.util.Vector;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import base.JUnitSetUp;

import static base.JUnitSetUp.*;

public class ExceptionTests {

    @BeforeClass
    public static void beforeTests() throws IOException, InterruptedException,
            Exception {
    }

    @After
    public void tierDown() throws IOException, InterruptedException, Exception {
        base.JUnitSetUp.tierDownSerial();
    }

    @Test
    public void test010_MemoryOverflow1() throws Exception {
        testee("haikuvm.bench.MemoryOverflow1", "link_upload_PanicSupport");
    }

    @Test
    public void test020_MemoryOverflow2() throws Exception {
        testee("haikuvm.bench.MemoryOverflow2", "link_upload_PanicSupport");
    }

    @Test
    public void test090_ExceptionTest() throws Exception {
        testee("haikuvm.bench.ExceptionTest");
    }

    @Test
    public void test090_ArrayIndexTest() throws Exception {
        testee("haikuvm.bench.ArrayIndexTest", "link_upload_PanicSupport");
    }
}
