package java.lang;

public class ThreadMicro {
	
    /**
     * @deprecated use {@link Micro#nap(long)} instead.
     */
	@Deprecated
    public static void nap(long millis) {
		Thread.currentThread.waitUntil=System.currentTimeMillis()+millis;
		Thread.currentThread.setStateAndSwitch(Thread.WAITING);  
    }
}
