package haikuvm.bench;
public class ArrayTest {
	static final int MAX = 4;

	public static void main(String[] args) {
		ArrayTest at = new ArrayTest();

		at.StringArray();
		at.BooleanArray();
		at.ByteArray();
		at.CharArray();
		at.ShortArray();
		at.IntegerArray();
		at.LongArray();
		at.FloatArray();
		at.DoubleArray();
		
		at.StringArray2D();
		at.BooleanArray2D();
		at.ByteArray2D();
		at.CharArray2D();
		at.ShortArray2D();
		at.IntegerArray2D();
		at.LongArray2D();
		at.FloatArray2D();
		at.DoubleArray2D();

		at.IntegerArray3D();
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

	private void IntegerArray() {
		System.out.println("IntegerArray");
		int[] a = new int[MAX];
		for (int i = 0; i < MAX; i++) {
			a[i]=1234;
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
