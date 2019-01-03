package org.lejos.nxt.benchmark;

import java.io.PrintStream;

public final class MathBench
{
	private static final int[] PADVEC = { 8, 40, 6, 6 };
	private static final String VERSION = "1.2";

	
	private static int benchSqrtCurrent(int count, String comment, double x)
	{
		long nullTime = BenchUtils.getIterationTime(count);
	
		// Function calls
		long start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			Math.sqrt(x);
		long end = System.currentTimeMillis();
	
		report(count, "sqrt (current, "+comment+")", count, "ops", end - start - nullTime);
		return count;
	}
	
	private static int benchExp(int count, String comment, double x)
	{
		long nullTime = BenchUtils.getIterationTime(count);
	
		// Function calls
		long start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			Math.exp(x);
		long end = System.currentTimeMillis();
	
		report(count, "exp (current, "+comment+")", count, "ops", end - start - nullTime);
		return count;
	}
	
	private static int benchLog(int count, String comment, double x)
	{
		long nullTime = BenchUtils.getIterationTime(count);
	
		// Function calls
		long start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			Math.log(x);
		long end = System.currentTimeMillis();
	
		report(count, "log (current, "+comment+")", count, "ops", end - start - nullTime);
		return count;
	}
	
	private static int benchDoubleToStr(int count, String comment, double x)
	{
		long nullTime = BenchUtils.getIterationTime(count);
		
		// Function calls
		long start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			String.valueOf(x);
		long end = System.currentTimeMillis();
	
		report(count, "D2STR (current, "+comment+")", count, "ops", end - start - nullTime);
		return count;
	}

	private static int benchSin(int count, String comment, double x)
	{
		long nullTime = BenchUtils.getIterationTime(count);
		
		// Function calls
		long start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			Math.sin(x);
		long end = System.currentTimeMillis();
	
		report(count, "sin (current, "+comment+")", count, "ops", end - start - nullTime);
		return count;
	}

	private static int benchCos(int count, String comment, double x)
	{
		long nullTime = BenchUtils.getIterationTime(count);
		
		// Function calls
		long start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			Math.cos(x);
		long end = System.currentTimeMillis();
	
		report(count, "cos (current, "+comment+")", count, "ops", end - start - nullTime);
		return count;
	}

	private static int benchTan(int count, String comment, double x)
	{
		long nullTime = BenchUtils.getIterationTime(count);
		
		// Function calls
		long start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			Math.tan(x);
		long end = System.currentTimeMillis();
	
		report(count, "tan (current, "+comment+")", count, "ops", end - start - nullTime);
		return count;
	}

	public static void main(String[] args)
	{
		System.out.println("MathBench " + VERSION);
	
		BenchUtils.cleanUp("At start");
	
		int iterate = 4000;
	
		int countAll = 0;
		long startAll = System.currentTimeMillis();
	
		countAll += benchSqrtCurrent(iterate, "subnormal", Math.PI * 0x1p-1060);
		BenchUtils.cleanUp(null);
		countAll += benchSqrtCurrent(iterate, "normal", Math.PI);
		BenchUtils.cleanUp(null);
	
		countAll += benchSin(iterate, "1", 1);
		BenchUtils.cleanUp(null);	
		countAll += benchSin(iterate, "2", 2);
		BenchUtils.cleanUp(null);	
		countAll += benchSin(iterate, "3", 3);
		BenchUtils.cleanUp(null);	
		countAll += benchSin(iterate, "4", 4);
		BenchUtils.cleanUp(null);	
		countAll += benchSin(iterate, "5", 5);
		BenchUtils.cleanUp(null);	
		countAll += benchSin(iterate, "6", 6);
		BenchUtils.cleanUp(null);	
		countAll += benchSin(iterate, "7", 7);
		BenchUtils.cleanUp(null);	

		countAll += benchCos(iterate, "1", 1);
		BenchUtils.cleanUp(null);	
		countAll += benchCos(iterate, "2", 2);
		BenchUtils.cleanUp(null);	
		countAll += benchCos(iterate, "3", 3);
		BenchUtils.cleanUp(null);	
		countAll += benchCos(iterate, "4", 4);
		BenchUtils.cleanUp(null);	
		countAll += benchCos(iterate, "5", 5);
		BenchUtils.cleanUp(null);	
		countAll += benchCos(iterate, "6", 6);
		BenchUtils.cleanUp(null);	
		countAll += benchCos(iterate, "7", 7);
		BenchUtils.cleanUp(null);	


		countAll += benchTan(iterate, "1", 1);
		BenchUtils.cleanUp(null);	
		countAll += benchTan(iterate, "2", 2);
		BenchUtils.cleanUp(null);	
		countAll += benchTan(iterate, "3", 3);
		BenchUtils.cleanUp(null);	
		countAll += benchTan(iterate, "4", 4);
		BenchUtils.cleanUp(null);	
		countAll += benchTan(iterate, "5", 5);
		BenchUtils.cleanUp(null);	
		countAll += benchTan(iterate, "6", 6);
		BenchUtils.cleanUp(null);	
		countAll += benchTan(iterate, "7", 7);
		BenchUtils.cleanUp(null);	

		//infinite loop for subnormal values
		countAll += benchExp(iterate, "-700", -700);
		BenchUtils.cleanUp(null);	
		countAll += benchExp(iterate, "-0.5", -0.5);
		BenchUtils.cleanUp(null);
		countAll += benchExp(iterate, "-0.1", -0.1);
		BenchUtils.cleanUp(null);
		countAll += benchExp(iterate, "0.0", 0.0);
		BenchUtils.cleanUp(null);
		countAll += benchExp(iterate, "+0.062", +0.17);
		BenchUtils.cleanUp(null);
		countAll += benchExp(iterate, "+0.1", +0.1);
		BenchUtils.cleanUp(null);
		countAll += benchExp(iterate, "+0.5", +0.5);
		BenchUtils.cleanUp(null);
		countAll += benchExp(iterate, "+700", +700);
		BenchUtils.cleanUp(null);
	
		//infinite loop for subnormal values		
		countAll += benchLog(iterate / 2, "subnormal", Math.PI * 0x1p-1060);
		BenchUtils.cleanUp(null);	
		countAll += benchLog(iterate / 2, "normal", Math.PI);
		BenchUtils.cleanUp(null);
		countAll += benchLog(iterate * 2, "1.0", 1.0);
		BenchUtils.cleanUp(null);
		countAll += benchLog(iterate, "1.0001", 1.01);
		BenchUtils.cleanUp(null);
		countAll += benchLog(iterate, "1.001", 1.01);
		BenchUtils.cleanUp(null);
		countAll += benchLog(iterate, "1.01", 1.01);
		BenchUtils.cleanUp(null);
		countAll += benchLog(iterate, "1.3", 1.1);
		BenchUtils.cleanUp(null);
		countAll += benchLog(iterate / 2, "1.9", 1.9);
		BenchUtils.cleanUp(null);
	
		countAll += benchDoubleToStr(iterate / 200, "PI*1E+300", Math.PI*1E+300);
		BenchUtils.cleanUp(null);
		countAll += benchDoubleToStr(iterate / 100, "PI*1E+100", Math.PI*1E+100);
		BenchUtils.cleanUp(null);
		countAll += benchDoubleToStr(iterate / 10, "PI*1E0", Math.PI*1E0);
		BenchUtils.cleanUp(null);
		countAll += benchDoubleToStr(iterate / 100, "PI*1E-100", Math.PI*1E-100);
		BenchUtils.cleanUp(null);
		countAll += benchDoubleToStr(iterate / 200, "PI*1E-300", Math.PI*1E-300);
		BenchUtils.cleanUp(null);
	
		long endAll = System.currentTimeMillis();
		report(countAll, "Total Loop Executions", countAll, "loops", endAll - startAll);
		System.out.println("Note: each Loop Execution includes multiple Java operations");
		System.out.flush();
	}
	
	/**
	 * Print out the time it took to complete a task Also calculate the rate per
	 * second = (count * 1000) / time <br>
	 * where time is in msec, hence the factor of 1000
	 * 
	 * @param count
	 *            How many iterations of the task
	 * @param task
	 *            String description of the task
	 * @param count2
	 *            How many items of unit
	 * @param unit
	 *            String description of the unit
	 * @param time
	 *            How many msec it took
	 */
	private static void report(long count, String task, long count2, String unit, long time)
	{
		BenchUtils.report(count, task, count2, unit, time, PADVEC);
	}

}
