package haikuvm.bench;
public class ArrayFieldsTest {
    static final int[]      s_int1 = {1};
    static final byte[]     s_byte1= new byte[] {2};      
    static final char[]     s_char1= new char[] {66};      
    static final short[]    s_short1= new short[] {4};      
    static final boolean[]  s_boolean1= new boolean[] { true, false };      
    static final long[]     s_long1= new long[] { 123456789};      
    static final float[]    s_float1= new float[] { 1234.0f};      
    static final double[]   s_double1= new double[] { 123456.0};      
    static final String[]   s_string1= new String[] { ""};      
    static final byte[][]   s_byte2D1= new byte[][] { {100}, {}, };      
    static final int[][]    s_int2D1= new int[][] { {100}, {}, };      
    static final String[][] s_string2D1= new String[][] { {"sone"}, {"s"}, };      

    int[]      v_int1 = {1};
    byte[]     v_byte1= new byte[] {2};      
    char[]     v_char1= new char[] {67};      
    short[]    v_short1= new short[] {4};      
    boolean[]  v_boolean1= new boolean[] { true, false };      
    long[]     v_long1= new long[] { 123456789};      
    float[]    v_float1= new float[] { 1234.0f};      
    double[]   v_double1= new double[] { 123456.0};      
    String[]   v_string1= new String[] { ""};      
    byte[][]   v_byte2D1= new byte[][] { {100}, {}, };      
    int[][]    v_int2D1= new int[][] { {100}, {}, };      
    String[][] v_string2D1= new String[][] { {"vone"}, {"v"}, };      

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
        print(s_byte2D1);
        print(s_int2D1);
        print(s_string2D1);
		ArrayFieldsTest aft = new ArrayFieldsTest();
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
        print(v_byte2D1);
        print(v_int2D1);
        print(v_string2D1);
    }

    private static void print(String[][] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                System.out.print(a[i][j]+", ");
            }
            System.out.println();
        }
        System.out.println("################");
    }

    private static void print(int[][] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                System.out.print(a[i][j]+", ");
            }
            System.out.println();
        }
        System.out.println("################");
    }

    private static void print(byte[][] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                System.out.print(a[i][j]+", ");
            }
            System.out.println();
        }
        System.out.println("################");
    }

    private static void print(String[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i]+", ");
        }
        System.out.println("################");
    }

    private static void print(double[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i]+", ");
        }
        System.out.println("################");
    }

    private static void print(float[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i]+", ");
        }
        System.out.println("################");
    }

    private static void print(long[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i]+", ");
        }
        System.out.println("################");
    }

    private static void print(int[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i]+", ");
        }
        System.out.println("################");
    }

    private static void print(short[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i]+", ");
        }
        System.out.println("################");
    }

    private static void print(char[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i]+", ");
        }
        System.out.println("################");
    }

    private static void print(boolean[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i]+", ");
        }
        System.out.println("################");
    }

    private static void print(byte[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i]+", ");
        }
        System.out.println("################");
    }
}
