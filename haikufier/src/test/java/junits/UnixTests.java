package junits;

import java.io.IOException;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import base.JUnitSetUp;

import static base.JUnitSetUp.*;

public class UnixTests extends BaseTests {

    @BeforeClass
    public static void beforeTests() throws IOException, InterruptedException,
            Exception {
        JUnitSetUp.setTarget("linux32");
        JUnitSetUp.setMode("32/64");
    }
    
    @Test
    public void test390_darjeeling_TestSuite() throws Exception {
        testee("haikuvm.bench.from.darjeeling.TestSuite");
    }

    
}
