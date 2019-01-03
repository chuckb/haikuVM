package junits;

import java.io.IOException;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import static base.JUnitSetUp.*;

public class SyncTests {

    @BeforeClass
    public static void beforeTests() throws IOException, InterruptedException,
            Exception {
    }

    @After
    public void tierDown() throws IOException, InterruptedException, Exception {
        base.JUnitSetUp.tierDownSerial();
    }

    @Test
    public void test010_SyncStatic3() throws Exception {
        testee("haikuvm.bench.SyncStatic3");
    }

    @Test
    public void test012_SyncStatic2() throws Exception {
        testee("haikuvm.bench.SyncStatic2");
    }

    @Test
    public void test015_SyncStatic() throws Exception {
        testee("haikuvm.bench.SyncStatic");
    }

    @Test
    public void test020_SyncDyn() throws Exception {
        testee("haikuvm.bench.SyncDyn");
    }

    @Test
    public void test025_SyncDyn2() throws Exception {
        testee("haikuvm.bench.SyncDyn2");
    }
}
