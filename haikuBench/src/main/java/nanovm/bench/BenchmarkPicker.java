package nanovm.bench;
import haikuvm.bench.*;
import nanovm.bench.*;

public class BenchmarkPicker {

	// remember 2 is for SVN
	// 1101 <storageModule moduleId="org.eclipse.cdt.make.core.buildtargets"/>

	public static final int TEST =5;
	private static long t0;

	public static void xmain(String[] args) throws Exception {
		try {
			//HaikuMagic.clinit();
			
			//ArduinoLib.Init();
			//Microkernel.init();
			
			asuro.robot.FollowLine.main(args);
		} catch (Throwable e) {
		}
		Thread.currentThread().stop();
	}
		
	public static void main(String[] args) throws Exception {
		long t0 = System.currentTimeMillis();
		
		if (TEST==6) { 
			haikuvm.bench.InterruptedThreads1.main(args);
		}
		
		if (TEST==0) { 
			haikuvm.bench.FollowLine.main(args);
		}
		
		if (TEST==1) { 
			//MathTestDouble.main(args);
            JustFields0.main(args);
//			Fibonacci.main(args);
			//FloatTest2.main(args);
		}
		/*
		 * haiku_32_64
		 * haiku_32_32
		 * haiku_16_32
		 */
		if (TEST==2) { 
			Erathostenes.main(args);
			count.main(args);
			JustFields0.main(args);
			Inheritance.main(args);
			StaticTest.main(args);
			QuickSort.main(args);
			icmp.main(args);
			DoubleTest2.main(args);
			DoubleTest.main(args);
			StringAndHeapTest.main(args);
			Switch2.main(args);
			Switch.main(args);
			HelloWorld.main(args);
			Fibonacci.main(args);
		}
		if (TEST==3) { 
			Threads1.main(args);
		}

		if (TEST==4) { 
			Erathostenes.main(args);
			
			count.main(args);
			//DivByZero.main(args);
			FibonacciFloat.main(args);
			FloatTest.main(args);
			FloatTest2.main(args);
			JustFields0.main(args);
			Inheritance.main(args);
			Inheritance2.main(args);
			StaticTest.main(args);
			QuickSort.main(args);
			icmp.main(args);
			FloatTest2.main(args);
			FloatTest.main(args);
			arithmetic.main(args);
			StringAndHeapTest.main(args);
			Switch2.main(args);
			Switch.main(args);
			HelloWorld.main(args);
			Fibonacci.main(args);
		}
		/* More complete JUNIT Test
		 * haiku_32_64
		 * haiku_32_32
		 */
		if (TEST==5) { 
			beginJUnit("HelloWorld");
			HelloWorld.main(args);
			endJUnit();
			
			beginJUnit("Erathostenes");
			Erathostenes.main(args);		
			endJUnit();
			
			beginJUnit("count");
			count.main(args);
			endJUnit();
			
			beginJUnit("MathTest");
			MathTest.main(args);
			endJUnit();
			
			beginJUnit("DivByZero");
			try {
				DivByZero.main(args);
			} catch (ArithmeticException e1) {
				System.out.println("/ by zero");
			}
			endJUnit();
			
			beginJUnit("OneClass");
			OneClass.main(args);
			endJUnit();
			
			beginJUnit("MethodCall");
			MethodCall.main(args);
			endJUnit();
			
			beginJUnit("Poly");
			Poly.main(args);
			endJUnit();
			
			beginJUnit("SelfInstance");
			SelfInstance.main(args);
			endJUnit();
			
			beginJUnit("Inheritance");
			Inheritance.main(args);
			endJUnit();
			
			beginJUnit("Inheritance2");
			Inheritance2.main(args);
			endJUnit();
			
			beginJUnit("QuickSort");
			QuickSort.main(args);
			endJUnit();
			
			beginJUnit("icmp");
			icmp.main(args);
			endJUnit();
			
			beginJUnit("DoubleTest2");
			DoubleTest2.main(args);
			endJUnit();
			
			beginJUnit("DoubleTest");
			DoubleTest.main(args);
			endJUnit();
			
			beginJUnit("FloatTest2");
			FloatTest2.main(args);
			endJUnit();
			
			beginJUnit("FloatTest");
			FloatTest.main(args);
			endJUnit();
			
			beginJUnit("arithmetic");
			arithmetic.main(args);
			endJUnit();
			
			beginJUnit("StringAndHeapTest");
			StringAndHeapTest.main(args);
			endJUnit();
			
			beginJUnit("Switch2");
			Switch2.main(args);
			endJUnit();
			
			beginJUnit("Switch");
			Switch.main(args);
			endJUnit();
			
			beginJUnit("ArrayTest");
			ArrayTest.main(args);
			endJUnit();
			
			beginJUnit("FibonacciFloat");
			FibonacciFloat.main(args);
			endJUnit();
			
			beginJUnit("Fibonacci");
			Fibonacci.main(args);
			endJUnit();
			
			beginJUnit("Threads1");
			Threads1.main(args);
			endJUnit();
			
			beginJUnit("StaticTest");
			StaticTest.main(args);
			endJUnit();
			
			beginJUnit("JustFields0");
			JustFields0.main(args);
			endJUnit();
			
			// Muss letzter sein.
			
			beginJUnit("ExceptionTest");
			try {
				ExceptionTest.main(args);
			} catch (Exception e) {
				System.out.println("Exception");
			}
			endJUnit();
		}
		
		long t1 = System.currentTimeMillis();
		if (TEST==1) {
			System.out.println("Elapsed time "+(int)(t1-t0)+" msec");
		} else {
			System.out.println("Elapsed time "+(t1-t0)+" msec");
		}
	}

	private static void endJUnit() {
		long d=(System.currentTimeMillis()-t0);
		System.out.println("##########  "+d+ " msec");
		System.out.println();
	}

	private static void beginJUnit(String name) {
		System.out.println("##########  "+name);
		t0=System.currentTimeMillis();
		System.gc();
	}
}
