package haikuvm.bench;

public class TypeCasts {
    private static final int SIZE = 10;
    static int ivalue = 4;
    static byte bvalue = 4;
    private static byte[] filoChannel= new byte[SIZE];
	
    public static void test2(int channel) {
        for (int i = SIZE-1; i>=0; i--) {
            filoChannel[i]=(byte)i;
        }
        
        System.out.println("just"+" pollute"+" the"+" stack");
        if (filoChannel[channel]==channel) {
            System.out.println("passed2");
        } else {
            System.out.println("failed2");
        }
    }

	public static void main(String[] args) {
		if (ivalue==bvalue) {
		    System.out.println("passed1");
		} else {
            System.out.println("failed1");
		}
		test2(7);
	}
}
