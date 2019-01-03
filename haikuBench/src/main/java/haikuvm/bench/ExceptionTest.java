package haikuvm.bench;
import java.io.FileNotFoundException;

/*
  arithmetic.java
 */

public class ExceptionTest {
	public static void main(String[] args) throws Exception {
		System.out.println("ExceptionTest a");
		new Exception("Exception toString1");
		System.out.println("ExceptionTest b");
		System.out.println(""+new Exception("Exception toString2"));
		ExceptionTest et = new ExceptionTest();
		System.out.println(et.test1());
		try {
			System.out.println(et.test2());
		} catch (Exception e) {
			System.out.println("exception: "+e);
		}
		System.out.println(et.test3());
		et.test4();
		et.test5();
	}

	private String test1() {
		System.out.println("test1");
		String res="test1: ";
		try {
			res+="try, ";
			throw new Exception("in try");
		} catch (Exception e) {
			res+="catch, ";
		} finally {
			res+="finally, ";
		}
		res+="return";
		return res;
	}

	private String test2() throws Exception {
		System.out.println("test2");
		String res="test2: ";
		try {
			res+="try, ";
			if (res.length()>0) throw new Exception(res+"throw in try, ");
		} catch (Exception e) {
			throw new Exception(e+"catch, ");
		} finally {
			res+="finally, ";
		}
		res+="return";
		return res;
	}

	private String test3() {
		System.out.println("test3");
		String res="test3: ";
		try {
			res+="try, ";
			if (res.length()>0) throw new Exception(res+"throw in try, ");
		} catch (Exception e) {
			res+=e+"catch, ";
			return res;
		} finally {
			res+="finally, ";
			return res;
		}
	}

	private void test4() {
		System.out.println("test4");
		try {
			throw new FileNotFoundException("test4");
		} catch (IndexOutOfBoundsException e) {
			System.out.println("IndexOutOfBoundsException");
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException");
		}
	}

	private String test5() throws Exception {
		System.out.println("test5");
		throw new Exception("Exception");
	}
}
