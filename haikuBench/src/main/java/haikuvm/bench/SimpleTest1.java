package haikuvm.bench;
import java.io.FileNotFoundException;

/*
  arithmetic.java
 */

class SimpleTest0 {
	String msg;
}

public class SimpleTest1 extends SimpleTest0 {
	
	public SimpleTest1(String string) {
		msg=string;
	}

	public String toString() {
		return msg;
	}

	public static void main(String[] args) throws Exception {
		System.out.println("SimpleTest1");
		System.out.println("SimpleTest1"+" something");
		new SimpleTest1("not used");
		System.out.println("b");
		System.out.println(new SimpleTest1("SimpleTest1 2"));
		System.out.println("2 "+new SimpleTest1("SimpleTest1 3"));
		System.out.println("done");
	}
}
