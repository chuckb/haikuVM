package junits.release;

import java.io.IOException;

import junits.JNITests;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import base.JUnitSetUp;
import static base.JUnitSetUp.*;

public class JNITests_16_32 extends JNITests {

    @BeforeClass
    public static void beforeTests() throws IOException, InterruptedException,
            Exception {
        JUnitSetUp.setTarget("duemilanove");
        JUnitSetUp.setMode("16/32");
        JUnitSetUp.setPermOptions(new String[] {"--Config:Port=COM9"});
    	JUnitSetUp.setPort("COM9,57600");
    }
}
