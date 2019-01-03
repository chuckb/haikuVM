package junits;

import java.io.IOException;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import static base.JUnitSetUp.*;

public class JNITests {
    @After
    public void tierDown() throws IOException, InterruptedException, Exception {
        base.JUnitSetUp.tierDownSerial();
    }

    @Test
    public void test005_JNI_FieldAccess() throws Exception {
        testee("arduino.tutorial.JNI_FieldAccess");
    }

    @Test
    public void test010_JNIsimple() throws Exception {
        testee("arduino.tutorial.JNIsimple");
    }

    @Test
    public void test020_JNIenhanced() throws Exception {
        testee("arduino.tutorial.JNIenhanced");
    }

    @Test
    public void test030_NativeCVariableSimple() throws Exception {
        testee("arduino.tutorial.NativeCVariableSimple");
    }

    @Test
    public void test040_NativeCVariableEnhanced() throws Exception {
        testee("arduino.tutorial.NativeCVariableEnhanced");
    }

    @Test
    public void test100_beyond_Simple() throws Exception {
        testee("arduino.tutorial.beyondjni.Simple");
    }

    @Test
    public void test110_beyond_AccessCppFields() throws Exception {
        testee("arduino.tutorial.beyondjni.AccessCppFields");
    }

    @Test
    public void test120_beyond_AccessCppMethods() throws Exception {
        testee("arduino.tutorial.beyondjni.AccessCppMethods");
    }

    @Test
    public void test130_beyond_FieldAccess() throws Exception {
        testee("arduino.tutorial.beyondjni.FieldAccess");
    }
}
