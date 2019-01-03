package java.lang;

public class Micro {
	static private long next = 1;

	public static int rand() // RAND_MAX assumed to be 32767
	{
	    next = next * 1103515245L + 12345;
	    int r=(int)((next/65536L) % 32768L);
	    if(r<0) return -r;
	    return r;
	}

	public static void srand(int seed)
	{
	    next = seed;
	}
	
    public static void nap(long millis) {
		Thread.currentThread.waitUntil=System.currentTimeMillis()+millis;
		Thread.currentThread.setStateAndSwitch(Thread.WAITING);  
    }
}
