package haikuvm.bench;
/**
 * Test all 
 *   putfield getfield
 *   putstatic getstatic
 *   
 *   See also ArrayFieldsTest
 * 
 * @author genom2
 *
 */
public class PrimitiveFieldsTest {
    static final byte     s_byte1= 2;      
    static final boolean  s_boolean1= true;      
    static final char     s_char1= 66;      
    static final short    s_short1= 4;      
    static final int      s_int1 = 1;
    static final long     s_long1= 123456789;      
    static final float    s_float1= 1234.0f;      
    static final double   s_double1= 123456.0;      
    static final String   s_string1= "sstring";      

    byte     v_byte1= 2;      
    boolean  v_boolean1= false;      
    char     v_char1= 67;      
    short    v_short1= 4;      
    int      v_int1 = 1;
    long     v_long1= 123456789;      
    float    v_float1= 1234.0f;      
    double   v_double1= 123456.0;      
    String   v_string1= "vstring";      

	public static void main(String[] args) {
        print(s_byte1);
        print(s_boolean1);
        print(s_char1);
        print(s_short1);
        print(s_int1);
        print(s_long1);
        print(s_float1);
        print(s_double1);
        print(s_string1);
		PrimitiveFieldsTest aft = new PrimitiveFieldsTest();
		aft.print();
		System.out.println("Done");
	}

    private void print() {
        print(v_byte1);
        print(v_boolean1);
        print(v_char1);
        print(v_short1);
        print(v_int1);
        print(v_long1);
        print(v_float1);
        print(v_double1);
        print(v_string1);
    }

    private static void print(String a) {
        System.out.println(a);
    }

    private static void print(double a) {
        System.out.println(a);
    }

    private static void print(float a) {
        System.out.println(a);
    }

    private static void print(long a) {
        System.out.println(a);
    }

    private static void print(int a) {
        System.out.println(a);
    }

    private static void print(short a) {
        System.out.println(a);
    }

    private static void print(char a) {
        System.out.println(a);
    }

    private static void print(boolean a) {
        System.out.println(a);
    }

    private static void print(byte a) {
        System.out.println(a);
    }
}
