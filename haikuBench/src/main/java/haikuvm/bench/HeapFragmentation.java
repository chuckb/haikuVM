package haikuvm.bench;

public class HeapFragmentation {
	private static class Rand {
		int next = 1;

		public int rand() // RAND_MAX assumed to be 32767
		{
		    next = next * 1103515245 + 12345;
		    short r=(short)((next/65536) % 32768);
		    if(r<0) return -r;
		    return r;
		}

		public void srand(int seed)
		{
		    next = seed;
		}

		public boolean equals(Rand b)
		{
		    return next == b.next;
		}
	}

	static Rand slow=new Rand();
	static Rand fast=new Rand();

	public static void xmain(String[] args) throws Exception {
		long i=0;
		for (i = 0; ; i++) {
			fast.rand();
			if (slow.equals(fast)) break;
			fast.rand();
			if (slow.equals(fast)) break;

			slow.rand();
		}
		System.out.println("loop!: "+i);

		for (i = 0; ; i++) {
			fast.rand();
			if (slow.equals(fast)) break;
		}
		System.out.println("loop size: "+i);
	}

	public static void main(String[] args) throws Exception {
		// the sum of bytes in array fill is never greater then MAX
		//final int MAX=200;	// duemilanove: round>2400
		final int MAX=500;	// duemilanove: round=158	sum=2593	filled=500	index=37	size=13 (then crash)
		//final int MAX=2500;	// RCX with 5000 heap: round=175	sum=4368	filled=1325	index=33	size=33 (But may be still running)
	    byte[][] fill=new byte[50][];
        // for ever or until memory crash
	    int sum=0,f=0;
	    for (int t=0; ; t++) {
	        int r,d,i;
	        do {
		        i=(int)(Micro.rand()%fill.length);
		        r=(int)(Micro.rand()%50);
		        d=(fill[i]==null)?0:fill[i].length;
	        } while(f+r-d>MAX);

	        f+=r-d;
	        sum+=r;
	        System.out.println("round="+t+"\tsum="+sum+"\tfilled="+f+"\tindex="+i+"\tsize="+r);
	        //System.out.println(sum);
	        fill[i]=null;
	        fill[i]=new byte[r];
        }
	}
}
