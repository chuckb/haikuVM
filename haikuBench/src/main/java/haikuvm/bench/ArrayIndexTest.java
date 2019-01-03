package haikuvm.bench;
public class ArrayIndexTest {
	static final int MAX = 4;

	public static void main(String[] args) {
		ArrayIndexTest at = new ArrayIndexTest();

		at.ByteArray();
		at.IntegerArray();

		System.out.println("Done");
	}

	private void ByteArray() {
		System.out.println("ByteArray");
		byte[] a = new byte[MAX];
		long[] b = new long[MAX];
		int v;
		for (int i = 0; i < MAX; i++) {
			a[i] = (byte) i;
		}
		try {
			v = a[-1];
			System.err.println("expected 1 ArrayIndexOutOfBoundsException");
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			v = a[MAX];
			System.err.println("expected 2 ArrayIndexOutOfBoundsException");
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			a[-1] = 0;
			System.err.println("expected 3 ArrayIndexOutOfBoundsException");
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			a[MAX] = 0;
			System.err.println("expected 4 ArrayIndexOutOfBoundsException");
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		
		System.arraycopy(a, 0, a, MAX / 2, MAX / 2);
		for (int i = 0; i < MAX / 2; i++) {
			if (a[i] != a[MAX / 2 + i])	System.err.println(a[i]);
		}

		try {
			System.arraycopy(a, -1, a, MAX / 2, MAX / 2);
			System.err.println("expected 5 ArrayIndexOutOfBoundsException");
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			System.arraycopy(a, MAX, a, MAX / 2, MAX / 2);
			System.err.println("expected 6 ArrayIndexOutOfBoundsException");
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			System.arraycopy(a, 0, a, -1, MAX / 2);
			System.err.println("expected 7 ArrayIndexOutOfBoundsException");
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			System.arraycopy(a, 0, a, MAX, MAX / 2);
			System.err.println("expected 8 ArrayIndexOutOfBoundsException");
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			System.arraycopy(a, 0, b, MAX / 2, MAX / 2);		
			System.err.println("expected 9 ArrayStoreException");
		} catch (ArrayStoreException e) {
		}
		try {
			System.arraycopy(null, 0, b, MAX / 2, MAX / 2);		
			System.err.println("expected 10 NullPointerException");
		} catch (NullPointerException e) {
		}
		try {
			System.arraycopy(a, 0, null, MAX / 2, MAX / 2);		
			System.err.println("expected 11 NullPointerException");
		} catch (NullPointerException e) {
		}
	}

	private void IntegerArray() {
		System.out.println("IntegerArray");
		int[] a = new int[MAX];
		long[] b = new long[MAX];
		int v;
		for (int i = 0; i < MAX; i++) {
			a[i] = i;
		}
		try {
			v = a[-1];
			System.err.println("expected ArrayIndexOutOfBoundsException");
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			v = a[MAX];
			System.err.println("expected ArrayIndexOutOfBoundsException");
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			a[-1] = 0;
			System.err.println("expected ArrayIndexOutOfBoundsException");
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			a[MAX] = 0;
			System.err.println("expected ArrayIndexOutOfBoundsException");
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		
		System.arraycopy(a, 0, a, MAX / 2, MAX / 2);
		for (int i = 0; i < MAX / 2; i++) {
			if (a[i] != a[MAX / 2 + i])	System.err.println(a[i]);
		}

		try {
			System.arraycopy(a, -1, a, MAX / 2, MAX / 2);
			System.err.println("expected ArrayIndexOutOfBoundsException");
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			System.arraycopy(a, MAX, a, MAX / 2, MAX / 2);
			System.err.println("expected ArrayIndexOutOfBoundsException");
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			System.arraycopy(a, 0, a, -1, MAX / 2);
			System.err.println("expected ArrayIndexOutOfBoundsException");
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			System.arraycopy(a, 0, a, MAX, MAX / 2);
			System.err.println("expected ArrayIndexOutOfBoundsException");
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			System.arraycopy(a, 0, b, MAX / 2, MAX / 2);		
			System.err.println("expected ArrayStoreException");
		} catch (ArrayStoreException e) {
		}
		try {
			System.arraycopy(null, 0, b, MAX / 2, MAX / 2);		
			System.err.println("expected NullPointerException");
		} catch (NullPointerException e) {
		}
		try {
			System.arraycopy(a, 0, null, MAX / 2, MAX / 2);		
			System.err.println("expected NullPointerException");
		} catch (NullPointerException e) {
		}
	}
}
