package junits;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import static base.JUnitSetUp.*;
import static junit.framework.TestCase.*;


public class JUnitMath1Test {
    static private double delta=0.00001;

    static private int charModel;
    static private int intModel;
    static private int longModel;
    static private int floatModel;
    static private int doubleModel;
   
    @BeforeClass
    public static void beforeTests() throws IOException, InterruptedException, Exception {
        setUpSerialSpezial();
    }
    
    /**
    #;char8: 0
    int16: -32768
    long32: -2147483648
    float16: Infinity
    double32: Infinity
     * @throws IOException 
    */
    @Test
    public void testMemoryModel() throws InterruptedException, IOException {
        String line;
        write("!;");
        line=readLine();
        System.out.println(line);
        if (line.contains("char8:")) {
            charModel=8;
        } else if (line.contains("char16:")) {
            charModel=16;
        } else {
            assertTrue("unsupported charModel: "+line, false);
        }
        
        line=readLine();
        System.out.println(line);
        if (line.contains("int16:")) {
            intModel=16;
        } else if (line.contains("int32:")) {
            intModel=32;
        } else {
            assertTrue("unsupported intModel: "+line, false);
        }
        
        line=readLine();
        System.out.println(line);
        if (line.contains("long16:")) {
            longModel=16;
        } else if (line.contains("long32:")) {
            longModel=32;
        } else if (line.contains("long64:")) {
            longModel=64;
        } else {
            assertTrue("unsupported longModel: "+line, false);
        }
        
        line=readLine();
        System.out.println(line);
        if (line.contains("float16:")) {
            floatModel=16;
        } else if (line.contains("float32:")) {
            floatModel=32;
        } else {
            assertTrue("unsupported floatModel: "+line, false);
        }
        
        line=readLine();
        System.out.println(line);
        if (line.contains("double16:")) {
            doubleModel=16;
        } else if (line.contains("double32:")) {
            doubleModel=32;
        } else if (line.contains("double64:")) {
            doubleModel=64;
        } else {
            assertTrue("unsupported doubleModel: "+line, false);
        }
        
    }

    @Test
    public void test1() throws InterruptedException, IOException {
        assertEquals(12.3, getDouble("123, 10 / ?;"), delta); 
    }

    @Test
    public void sin() throws InterruptedException, IOException {
        for (int i = 0; i < 100; i+=10) {
            assertEquals(Math.sin(1.0*i/10), getDouble(i+", 10 / sin() ?;"), Math.abs(delta*Math.sin(1.0*i/10))); 
        }
    }

    @Test
    public void asin() throws InterruptedException, IOException {
        for (int i = 0; i < 100; i+=10) {
            assertEquals(Math.asin(1.0*i/10), getDouble(i+", 10 / asin() ?;"), Math.abs(delta*Math.asin(1.0*i/10))); 
        }
    }

    @Test
    public void cos() throws InterruptedException, IOException {
        for (int i = 0; i < 100; i+=10) {
            assertEquals(Math.cos(1.0*i/10), getDouble(i+", 10 / cos() ?;"), Math.abs(delta*Math.cos(1.0*i/10))); 
        }
    }

    @Test
    public void sqrt() throws InterruptedException, IOException {
        for (int i = 0; i < 100; i+=10) {
            assertEquals(Math.sqrt(1.0*i/10), getDouble(i+", 10 / sqrt() ?;"), delta*Math.sqrt(1.0*i/10)); 
        }
        for (int i = 10; i < 30000; i*=2) {
            assertEquals(Math.sqrt(i), getDouble(i+" sqrt() ?;"), delta*Math.sqrt(i)); 
        }
    }

    @Test
    public void random() throws InterruptedException, IOException {
        double max=0;
        for (int i = 0; i < 20; i++) {
            double o=getDouble("random() ?;");
            double r=Math.abs(o);
            if (max<r) max=r;
            System.out.println(i+": "+o+"\t"+max);
        }
        System.out.println("max random="+max);
    }

}
