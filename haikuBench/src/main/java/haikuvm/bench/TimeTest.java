package haikuvm.bench;

/**
 */
public class TimeTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {

	    while(true) {
	    	System.out.println(System.currentTimeMillis());
	    	Thread.nap(1000);
	    }
	}
}
