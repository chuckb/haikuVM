package haikuvm.bench;
/**
 * Experiments to store (not only final) arrays in (read only memory)
 *   flash
 *   eeprom
 *   
 *
 * ALLOC
 *  iconst_S | bipush  S | sipush  S
 *  newarray <int|byte|...> | anewarray  <java.lang.String|...>
 *   
 * ELEMENT*
 *  dup
 *  iconst_I | bipush  I | sipush  I
 *  iconst_V | fconst_V | bipush  V | sipush  V | ldc  ADR(Const_V) | ldc2_w  ADR(Const_V)
 *  iastore | bastore | castore | sastore | lastore | fastore | dastore | aastore
 * 
 * ASSIGN
 *  putstatic  ADR(var)
 * 
 * @author genom2
 *
 */
public class ArrayInROM {
    private static class FlashArray {
        public static <E> E of(E ... es ) {
            return (E)es;
        }    
    }

    static final int MAX = 4;
    
    static final int[] int1 = { 0, 0, 0, 0};
    //struct {jint length; jint array[4];} int1 = {4, { 0, 0, 0, 0}; };
    
    static final int[] int2 = { 0, 123456789, 2};
    //struct {jint length; jint array[3];} int2 = {3, { 0, 123456789, 2}; };
    
    static final int[] int3 = { 2, 2};
    //struct {jint length; jint array[3];} int3 = {2, { 2, 2}; };

    static final int[] int5 = new int[] { 21,6,36,76,7,97,94,30,90,86,13,80,84,79,28,55,36,95,23,82,57,73,28,46,48,94,18,23,86,100,47,42,39,33,52,98,77,81,86,64,27,70,91,42,6,95,8,6,36,71,79,32,27,34,87,100,85,90,69,12,73,70,76,65,51,21,4,5,82,77,63,87,12,11,69,79,12,35,43,35,67,16,38,78,60,6,79,92,43,69,57,74,58,21,45,69,45,55,73,21,6,36,76,7,97,94,30,90,86,13,80,84,79,28,55,36,95,23,82,57,73,28,46,48,94,18,23,86,100,47,42,39,33,52,98,77,81,86,64,27,70,91,42,6,95,8,6,36,71,79,32,27,34,87,100,85,90,69,12,73,70,76,65,51,21,4,5,82,77,63,87,12,11,69,79,12,35,43,35,67,16,38,78,60,6,79,92,43,69,57,74,58,21,45,69,45,55,73,24 };
    static final int[] int6 = new int[] { 21,6,36,76,7,97,94,30,90,86,13,80,84,79,28,55,36,95,23 };
    
    static final byte[] byte1= new byte[] {        21,  6, 36, 76,  7, 97, 94, 30, 90, 86,        13, 80, 84, 79, 28, 55, 36, 95, 23, 82        };      
    //struct {jint length; jbyte array[33];} byte1 = {33, {        21,  6, 36, 76,  7, 97, 94, 30, 90, 86,        13, 80, 84, 79, 28, 55, 36, 95, 23, 82        }; };
    
    static final char[] char1= new char[] { 21,  6, 36, 76,  7, 97, 94, 30, 90, 86, 13, 80, 84, 79, 28, 55, 36, 95, 23, 82 };      
    static final short[] short1= new short[] { 21,  6, 36, 76,  7, 97, 94, 30, 90, 86, 13, 80, 84, 79, 28, 55, 36, 95, 23, 82 };      
    static final boolean[] boolean1= new boolean[] { true, false, false };      
    static final long[] long1= new long[] { 0, 123456789, 2, 3};      
    static final float[] float1= new float[] { 0, 123456789.0f, 2f, 3};      
    static final double[] double1= new double[] { 0, 123456789.0, 2, 3};      
    static final String[] string1= new String[] { "", "one", "two", "three"};  
//    const ldc_jstring_t Const0001 PROGMEM =  {0,    };
//    const ldc_jstring_t Const000f PROGMEM =  {3,    {'o','n','e'}};
//    const ldc_jstring_t Const0010 PROGMEM =  {3,    {'t','w','o'}};
//    const ldc_jstring_t Const0011 PROGMEM =  {5,    {'t','h','r','e','e'}};

//    struct {jint length; jstring array[4];} byte1 = {4, { Const0001,  Const000f, Const0010, Const0011 }; };

    static final int[][] int2D1= new int[][] { 
        {100},
        {100, 200},
        {100, 200, 300},
        {},
    };      

    
	public static void main(String[] args) {
        IntegerArray(int1);
        IntegerArray(int2);
        IntegerArray(int3);
		System.out.println("Done");
	}

	private void ByteArray2D() {
		System.out.println("ByteArray2D");
		byte[][] a = new byte[MAX][MAX-1];
		byte i=0;
		for (int x = 0; x < MAX; x++) {
			for (int y = 0; y < MAX-1; y++) {
				a[x][y]=i++;
				System.out.print(a[x][y]+", ");
			}
			System.out.println();
		}
	}

	private void DoubleArray2D() {
		System.out.println("DoubleArray2D");
		double[][] a = new double[MAX][MAX-1];
		int i=0;
		for (int x = 0; x < MAX; x++) {
			for (int y = 0; y < MAX-1; y++) {
				a[x][y]=i++;
				System.out.print(a[x][y]+", ");
			}
			System.out.println();
		}
	}

	private void FloatArray2D() {
		System.out.println("FloatArray2D");
		float[][] a = new float[MAX][MAX-1];
		int i=0;
		for (int x = 0; x < MAX; x++) {
			for (int y = 0; y < MAX-1; y++) {
				a[x][y]=i++;
				System.out.print(a[x][y]+", ");
			}
			System.out.println();
		}
	}

	private void LongArray2D() {
		System.out.println("LongArray2D");
		long[][] a = new long[MAX][MAX-1];
		int i=0;
		for (int x = 0; x < MAX; x++) {
			for (int y = 0; y < MAX-1; y++) {
				a[x][y]=i++;
				System.out.print(a[x][y]+", ");
			}
			System.out.println();
		}
	}

	private void ShortArray2D() {
		System.out.println("ShortArray2D");
		short[][] a = new short[MAX][MAX-1];
		short i=0;
		for (int x = 0; x < MAX; x++) {
			for (int y = 0; y < MAX-1; y++) {
				a[x][y]=i++;
				System.out.print(a[x][y]+", ");
			}
			System.out.println();
		}
	}

	private void CharArray2D() {
		System.out.println("CharArray2D");
		char[][] a = new char[MAX][MAX-1];
		char i='A';
		for (int x = 0; x < MAX; x++) {
			for (int y = 0; y < MAX-1; y++) {
				a[x][y]=i++;
				System.out.print(a[x][y]+", ");
			}
			System.out.println();
		}
	}

	private void BooleanArray2D() {
		System.out.println("BooleanArray2D");
		boolean[][] a = new boolean[MAX][MAX-1];
		int i=0;
		for (int x = 0; x < MAX; x++) {
			for (int y = 0; y < MAX-1; y++) {
				a[x][y]=(i++&1)==1;
				System.out.print(a[x][y]+", ");
			}
			System.out.println();
		}
	}

	private void StringArray2D() {
		System.out.println("StringArray2D");
		String[][] a = new String[MAX][MAX-1];
		int i=0;
		for (int x = 0; x < MAX; x++) {
			for (int y = 0; y < MAX-1; y++) {
				a[x][y]="string"+(i++);
				System.out.print(a[x][y]+", ");
			}
			System.out.println();
		}
	}

	private void ByteArray() {
		System.out.println("ByteArray");
		byte[] a = new byte[MAX];
		for (int i = 0; i < MAX; i++) {
			a[i]=1;
			System.out.println(a[i]);
		}
	}

	private static void IntegerArray(int[] a) {
		System.out.println("IntegerArray");
		for (int i = 0; i < a.length; i++) {
			System.out.println(a[i]);
		}
	}

	private void IntegerArray2D() {
		System.out.println("IntegerArray2D");
		int[][] a = new int[MAX][MAX-1];
		int i=0;
		for (int x = 0; x < MAX; x++) {
			for (int y = 0; y < MAX-1; y++) {
				a[x][y]=i++;
				System.out.print(a[x][y]+", ");
			}
			System.out.println();
		}
	}

	private void IntegerArray3D() {
		System.out.println("IntegerArray3D");
		int[][][] a = new int[MAX][MAX-1][MAX-2];
		int i=0;
		for (int x = 0; x < MAX; x++) {
			for (int y = 0; y < MAX-1; y++) {
				for (int z = 0; z < MAX-2; z++) {
					a[x][y][z]=i++;
					System.out.print(a[x][y][z]+", ");
				}
			}
			System.out.println();
		}
	}

	private void StringArray() {
		System.out.println("StringArray");
		String[] a = new String[MAX];
		for (int i = 0; i < MAX; i++) {
			a[i]="fünf";
			System.out.println(a[i]);
		}
	}

	private void DoubleArray() {
		System.out.println("DoubleArray");
		double[] a = new double[MAX];
		for (int i = 0; i < MAX; i++) {
			a[i]=1234.5678;
			System.out.println(a[i]);
		}
	}

	private void FloatArray() {
		System.out.println("FloatArray");
		float[] a = new float[MAX];
		for (int i = 0; i < MAX; i++) {
			a[i]=1234;
			System.out.println(a[i]);
		}
	}

	private void LongArray() {
		System.out.println("LongArray");
		long[] a = new long[MAX];
		for (int i = 0; i < MAX; i++) {
			a[i]=12345678;
			System.out.println(a[i]);
		}
	}

	private void ShortArray() {
		System.out.println("ShortArray");
		short[] a = new short[MAX];
		for (int i = 0; i < MAX; i++) {
			a[i]=123;
			System.out.println(a[i]);
		}
	}

	private void CharArray() {
		System.out.println("CharArray");
		char[] a = new char[MAX];
		for (int i = 0; i < MAX; i++) {
			a[i]='X';
			System.out.println(a[i]);
		}
	}

	private void BooleanArray() {
		System.out.println("BooleanArray");
		boolean[] a = new boolean[MAX];
		for (int i = 0; i < MAX; i++) {
			a[i]=true;
			System.out.println(a[i]);
		}
	}
}
