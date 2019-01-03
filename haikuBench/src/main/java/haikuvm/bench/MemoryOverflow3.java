package haikuvm.bench;

/**
 * Test of outOfMemory in a Thread
 */
public class MemoryOverflow3 extends Thread {
    public static void main(String[] args) throws Exception {
	    MemoryOverflow3 mo = new MemoryOverflow3();
	    mo.start();
	}

    public void run() {
        byte[][] fill=new byte[100][];
        // until memory crash
	    for (int i = 0; i < fill.length; i++) {
	        System.out.println("fill "+i);
	        fill[i]=new byte[10];            
        }
        System.out.println("ready");
    }
}
