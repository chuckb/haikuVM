package haikuvm.bench.from.darjeeling;

public class Darjeeling {
	static public int passed;
	static public int failed;

    public static void assertTrue(int i, boolean b) {
        if (b) {
        	passed++;
            System.out.println("Darjeeling.assertTrue("+i/1000+", "+i%1000+") passed");
        } else {
        	failed++;
            System.out.println("Darjeeling.assertTrue("+i/1000+", "+i%1000+") failed !! "+ failed);
            //throw new RuntimeException("failed");
        }
    }

}
