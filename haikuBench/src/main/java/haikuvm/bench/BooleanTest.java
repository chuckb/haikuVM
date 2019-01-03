package haikuvm.bench;
/*
  DoubleTest.java
 */

public class BooleanTest {
	
	private boolean dvalue1, dvalue2;
	private static boolean svalue;

	public static void main(String[] args) {
		BooleanTest bt = new BooleanTest();
		
		System.out.println("static  boolean is "+bt.svalue);
		bt.svalue=true;
		System.out.println("static  boolean is "+bt.svalue);
		bt.svalue=false;
		System.out.println("static  boolean is "+bt.svalue);
		
		// Do we overwriting something in memory (big vs. little endians)??
		bt.dvalue1=false; bt.dvalue2=false;
		System.out.println("dynamic booleans are ("+bt.dvalue1+", "+bt.dvalue2+")");
		bt.dvalue1=false; bt.dvalue2=true;
		System.out.println("dynamic booleans are ("+bt.dvalue1+", "+bt.dvalue2+")");
		bt.dvalue1=true; bt.dvalue2=false;
		System.out.println("dynamic booleans are ("+bt.dvalue1+", "+bt.dvalue2+")");
		bt.dvalue1=true; bt.dvalue2=true;
		System.out.println("dynamic booleans are ("+bt.dvalue1+", "+bt.dvalue2+")");
		
		bt.dvalue2=false; bt.dvalue1=false;
		System.out.println("dynamic booleans are ("+bt.dvalue1+", "+bt.dvalue2+")");
		bt.dvalue2=true; bt.dvalue1=false;
		System.out.println("dynamic booleans are ("+bt.dvalue1+", "+bt.dvalue2+")");
		bt.dvalue2=false; bt.dvalue1=true;
		System.out.println("dynamic booleans are ("+bt.dvalue1+", "+bt.dvalue2+")");
		bt.dvalue2=true; bt.dvalue1=true;
		System.out.println("dynamic booleans are ("+bt.dvalue1+", "+bt.dvalue2+")");
	}
}
