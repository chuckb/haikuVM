package org.lejos.nxt.benchmark;

public class BenchUtils
{
	private static final boolean DEBUG = false;	
	private static final Runtime rt = Runtime.getRuntime();
	
	public static void report(long count, String task, long count2, String unit, long time, int[] padvec)
	{
		if (time<=0)
			time = 1; // on the PC time is often zero or 1
		
		StringBuilder sb = new StringBuilder();
		appendPadR(sb, count, padvec[0]);
		sb.append(' ');
		appendPadL(sb, task + ": ", padvec[1]);
		appendPadR(sb, time, padvec[2]);
		sb.append(" ms ");
		appendPadR(sb, count2 * 1000 / time, padvec[3]);
		sb.append(' ');
		sb.append(unit);
		sb.append("/sec");
	
		System.out.println(sb.toString());
	}

	private static void appendPadR(StringBuilder sb, long val, int pad)
	{
		appendPadR(sb, String.valueOf(val), pad);
	}

	private static void appendPadR(StringBuilder sb, String val, int pad)
	{
		for (int i=val.length(); i < pad; i++)
			sb.append(' ');
		sb.append(val);
	}

	private static void appendPadL(StringBuilder sb, String val, int pad)
	{
		sb.append(val);
		for (int i=val.length(); i < pad; i++)
			sb.append(' ');
	}

	public static void cleanUp(String debugmsg)
	{
		System.gc();
		
//		if (DEBUG)
//		{
//			if (debugmsg != null && debugmsg.length() > 0)
//				System.out.println(debugmsg + ": ");
//			System.out.println("Memory total=0x" + Long.toString(rt.totalMemory(), 16) + "/" + rt.totalMemory());
//			System.out.println(" free=0x" + Long.toString(rt.freeMemory(), 16) + "/" + rt.freeMemory());
//		}
	}

	/**
	 * Count the time it takes to iterate through a loop so that we can deduct
	 * this time from the total loop + operation time to get just the operation
	 * time.
	 * 
	 * @param count
	 *            - the number of iterations
	 */
	public static long getIterationTime(int count)
	{
		final int N=1;
		long sum=0;
		for (int s = 0; s < N; s++) {
			long start = System.currentTimeMillis();
			for (int i = 0; i < count; i++)
			{
				// do nothing
			}
			long end = System.currentTimeMillis();
			sum+=end-start;
		}
		sum=(sum+N/2)/N;
		if (true)
			System.out.println("IterationTime= " + (sum) + " msec");		
		return (sum);
	}

}
