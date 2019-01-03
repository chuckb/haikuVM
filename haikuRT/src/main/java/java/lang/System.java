package java.lang;

import java.io.InputStream;
import java.io.PrintStream;

public class System {
	public static PrintStream out;
	public static PrintStream err;
	public static InputStream in;

	/**
	 * Returns the current time in milliseconds. Note that while the unit of
	 * time of the return value is a millisecond, the granularity of the value
	 * depends on the underlying operating system and may be larger. For
	 * example, many operating systems measure time in units of tens of
	 * milliseconds.
	 *
	 * <p>
	 * See the description of the class <code>Date</code> for a discussion of
	 * slight discrepancies that may arise between "computer time" and
	 * coordinated universal time (UTC).
	 *
	 * @return the difference, measured in milliseconds, between the current
	 *         time and midnight, January 1, 1970 UTC.
	 * @see java.util.Date
	 *
	 * In the AVR, this is the number of milliseconds since the AVR has been on.
	 */
	public static native long currentTimeMillis();

	/**
	 * Returns <code>null</code>. In leJOS, there are no system properties. So
	 * this function returns <code>null</code>.
	 *
	 * @param name
	 *            name of the system property
	 * @return <code>null</code>
	 */
	public static String getProperty(String name) {
		return null;
	}

	/**
	 * Returns <code>def</code>. In leJOS, there are no system properties. So
	 * this function returns <code>def</code>.
	 *
	 * @param name
	 *            name of the system property
	 * @param def
	 *            default value that is returns if system property doesn't exist
	 * @return <code>def</code>
	 */
	public static String getProperty(String name, String def) {
		return def;
	}

	/**
	 * HaikuVM delivers the physical memory address as hash.
	 *
	 * TODO: Will not work if we move objects e.g. with a compacting GC.
	 *
	 * @return
	 */
	public static int identityHashCode(Object obj) {
		return System.getDataAddress(obj);
	}

	private native static int getDataAddress(Object obj);

	/**
	 * Collect garbage
	 */
	public static native void gc();

	private static PrintStream nullPrintStream() {
		return new PrintStream("");
	}

	/**
	 * Copies an array from the specified source array, beginning at the
	 * specified position, to the specified position of the destination array. A
	 * subsequence of array components are copied from the source array
	 * referenced by <code>src</code> to the destination array referenced by
	 * <code>dest</code>. The number of components copied is equal to the
	 * <code>length</code> argument. The components at positions
	 * <code>srcPos</code> through <code>srcPos+length-1</code> in the source
	 * array are copied into positions <code>destPos</code> through
	 * <code>destPos+length-1</code>, respectively, of the destination array.
	 * <p>
	 * If the <code>src</code> and <code>dest</code> arguments refer to the same
	 * array object, then the copying is performed as if the components at
	 * positions <code>srcPos</code> through <code>srcPos+length-1</code> were
	 * first copied to a temporary array with <code>length</code> components and
	 * then the contents of the temporary array were copied into positions
	 * <code>destPos</code> through <code>destPos+length-1</code> of the
	 * destination array.
	 * <p>
	 * If <code>dest</code> is <code>null</code>, then a
	 * <code>NullPointerException</code> is thrown.
	 * <p>
	 * If <code>src</code> is <code>null</code>, then a
	 * <code>NullPointerException</code> is thrown and the destination array is
	 * not modified.
	 * <p>
	 * Otherwise, if any of the following is true, an
	 * <code>ArrayStoreException</code> is thrown and the destination is not
	 * modified:
	 * <ul>
	 * <li>The <code>src</code> argument refers to an object that is not an
	 * array.
	 * <li>The <code>dest</code> argument refers to an object that is not an
	 * array.
	 * <li>The <code>src</code> argument and <code>dest</code> argument refer to
	 * arrays whose component types are different primitive types.
	 * <li>The <code>src</code> argument refers to an array with a primitive
	 * component type and the <code>dest</code> argument refers to an array with
	 * a reference component type.
	 * <li>The <code>src</code> argument refers to an array with a reference
	 * component type and the <code>dest</code> argument refers to an array with
	 * a primitive component type.
	 * </ul>
	 * <p>
	 * Otherwise, if any of the following is true, an
	 * <code>IndexOutOfBoundsException</code> is thrown and the destination is
	 * not modified:
	 * <ul>
	 * <li>The <code>srcPos</code> argument is negative.
	 * <li>The <code>destPos</code> argument is negative.
	 * <li>The <code>length</code> argument is negative.
	 * <li><code>srcPos+length</code> is greater than <code>src.length</code>,
	 * the length of the source array.
	 * <li><code>destPos+length</code> is greater than <code>dest.length</code>,
	 * the length of the destination array.
	 * </ul>
	 * <p>
	 * Otherwise, if any actual component of the source array from position
	 * <code>srcPos</code> through <code>srcPos+length-1</code> cannot be
	 * converted to the component type of the destination array by assignment
	 * conversion, an <code>ArrayStoreException</code> is thrown. In this case,
	 * let <b><i>k</i></b> be the smallest nonnegative integer less than length
	 * such that <code>src[srcPos+</code><i>k</i><code>]</code> cannot be
	 * converted to the component type of the destination array; when the
	 * exception is thrown, source array components from positions
	 * <code>srcPos</code> through <code>srcPos+</code><i>k</i><code>-1</code>
	 * will already have been copied to destination array positions
	 * <code>destPos</code> through <code>destPos+</code><i>k</I><code>-1</code>
	 * and no other positions of the destination array will have been modified.
	 * (Because of the restrictions already itemized, this paragraph effectively
	 * applies only to the situation where both arrays have component types that
	 * are reference types.)
	 *
	 * @param src
	 *            the source array.
	 * @param srcPos
	 *            starting position in the source array.
	 * @param dest
	 *            the destination array.
	 * @param destPos
	 *            starting position in the destination data.
	 * @param length
	 *            the number of array elements to be copied.
	 * @exception HaikuWRONG: IndexOutOfBoundsException
	 * 		Experiment (see ArrayIndexTest) shows that Sun'S JVM 1.6 throws:
	 * @exception ArrayIndexOutOfBoundsException
	 *                if copying would cause access of data outside array
	 *                bounds.
	 * @exception ArrayStoreException
	 *                if an element in the <code>src</code> array could not be
	 *                stored into the <code>dest</code> array because of a type
	 *                mismatch.
	 * @exception NullPointerException
	 *                if either <code>src</code> or <code>dest</code> is
	 *                <code>null</code>.
	 */
	public static native void arraycopy(Object src, int srcPos, Object dest,
			int destPos, int length);

	/**
	 * Exists just for compatibility and it's a Noop in HaikuVM.
	 *
	 * @param file
	 */
    public static void loadLibrary(String file) {
    }

	public static void exit(int i) {
		// TODO Auto-generated method stub
	}

	public static Runtime getRuntime() {
		// TODO Auto-generated method stub
		return null;
	}
}
