package avr.gallerie.user.TimReset;

/**
 * 
 * I tested like this:
 * cd C:\haikuVM\myCProject
 * C:\haikuVM\bin\haiku -v --Config duemilanove --Mode 32/64 C:\haikuVM\gallerie\src\main\java\avr\gallerie/user\TimReset\LongTest.java
 *
 */
public class LongTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//hex and binary representation field is similarly - 
		long cInt =0x80000000;
		long cLong=0x80000000L;
		long b=0b10000000000000000000000000000000;
		System.out.println("cInt ="+cInt);
		System.out.println("cLong="+cLong);
		System.out.println("b    ="+b);
	}
}
