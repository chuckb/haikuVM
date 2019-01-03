/*
 * ClassHierarchyTest.java
 * 
 * Copyright (c) 2008-2010 CSIRO, Delft University of Technology.
 * 
 * This file is part of Darjeeling.
 * 
 * Darjeeling is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Darjeeling is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Darjeeling.  If not, see <http://www.gnu.org/licenses/>.
 */
 
package haikuvm.bench.from.darjeeling.failed;

import haikuvm.bench.from.darjeeling.Darjeeling;
import haikuvm.bench.from.darjeeling.testvm.classes.A;
import haikuvm.bench.from.darjeeling.testvm.classes.AInterface;
import haikuvm.bench.from.darjeeling.testvm.classes.B;
import haikuvm.bench.from.darjeeling.testvm.classes.C;
import haikuvm.bench.from.darjeeling.testvm.classes.CInterface;
import haikuvm.bench.from.darjeeling.testvm.classes.D;
import haikuvm.bench.from.darjeeling.testvm.classes.E;
import haikuvm.bench.from.darjeeling.testvm.classes.EInterface;
import haikuvm.bench.from.darjeeling.testvm.classes.FInterface;

/**
 * Test suite for the NEW, INSTANCEOF and CHECKCAST instructions.
 * <p>
 * @author Niels Brouwers
 *
 */
public class ClassHierarchyTest
{
	
    public static void main(String[] args) {
        test(0);
        System.out.println("Done");
    }
    
	private static Object stato;
	
	public static class Super
	{
        
        
	}

	public static class Sub extends Super
	{
        
        
	}

	public static class Inner extends Sub
	{
        
        
	}
    
    public static void test(int testBase)
	{
		instanceOfTest(testBase + 00);
		arrayInstanceOfTest(testBase + 20);
		interfaceImplementTest(testBase + 60);
		interfaceArrayImplementTest(testBase + 80);
		checkcastTest(testBase + 90);
	}
    
	private static void instanceOfTest(int testBase)
	{
		
		// test NEW and INSTANCEOF
		A a = new A();// A implements AInterface
		B b = new B();// B extends A
		C c = new C();// C extends B implents CInterface
		D d = new D();// D extends C
		String str = new String();
		Inner inner = new Inner();
		
		Darjeeling.assertTrue(testBase +  0, a instanceof A);  
		Darjeeling.assertTrue(testBase +  1, !(a instanceof B));
		Darjeeling.assertTrue(testBase +  2, b instanceof A);
		Darjeeling.assertTrue(testBase +  3, b instanceof B);

		Darjeeling.assertTrue(testBase +  4, c instanceof A);
		Darjeeling.assertTrue(testBase +  5, c instanceof B);
		Darjeeling.assertTrue(testBase +  6, c instanceof C);
		Darjeeling.assertTrue(testBase +  7, !(c instanceof D));

		Darjeeling.assertTrue(testBase +  8, d instanceof A);
		Darjeeling.assertTrue(testBase +  9, d instanceof B);
		Darjeeling.assertTrue(testBase + 10, d instanceof C);
		Darjeeling.assertTrue(testBase + 11, d instanceof D);
		
		// test if INSTANCEOF also works across infusions 
		Darjeeling.assertTrue(testBase + 12, str instanceof String);

		// test if INSTANCEOF also works for inner classes (should be trivial)
		// and across infusions (inner's parent is from the system infusion)
		Darjeeling.assertTrue(testBase + 13, inner instanceof Sub);
				
		Darjeeling.assertTrue(testBase + 14, !(null instanceof A));
		Darjeeling.assertTrue(testBase + 15, !(null instanceof Object));

		// any object should be an instance of java.lang.Object 
		Darjeeling.assertTrue(testBase + 16, a instanceof Object);
		Darjeeling.assertTrue(testBase + 17, b instanceof Object);
	}

	private static void arrayInstanceOfTest(int testBase)
	{
		// array instance tests
		A[] aa = new A[10];
		B[] bb = new B[10];
		C[] cc = new C[10];
		D[] dd = new D[10];
		Darjeeling.assertTrue(testBase +  0, aa instanceof A[]);
		Darjeeling.assertTrue(testBase +  1, !(aa instanceof B[]));
		
		Darjeeling.assertTrue(testBase +  2, bb instanceof A[]);
		Darjeeling.assertTrue(testBase +  3, bb instanceof B[]);
		Darjeeling.assertTrue(testBase +  4, !(bb instanceof C[]));
		
		Darjeeling.assertTrue(testBase +  5, cc instanceof A[]);
		Darjeeling.assertTrue(testBase +  6, cc instanceof B[]);
		Darjeeling.assertTrue(testBase +  7, cc instanceof C[]);
		Darjeeling.assertTrue(testBase +  8, !(cc instanceof D[]));

		Darjeeling.assertTrue(testBase +  9, dd instanceof A[]);
		Darjeeling.assertTrue(testBase + 10, dd instanceof B[]);
		Darjeeling.assertTrue(testBase + 11, dd instanceof C[]);
		Darjeeling.assertTrue(testBase + 12, dd instanceof D[]);
		
		// check int array forms  
		Object inta = new int[10];
		Object intb = new byte[10];
		Object intbl = new boolean[10];
		Object ints = new short[10];

		Darjeeling.assertTrue(testBase + 13, inta instanceof int[]);
		Darjeeling.assertTrue(testBase + 14, !(inta instanceof byte[]));
		Darjeeling.assertTrue(testBase + 15, !(inta instanceof boolean[]));
		Darjeeling.assertTrue(testBase + 16, !(inta instanceof short[]));
		Darjeeling.assertTrue(testBase + 17, !(inta instanceof A));
		Darjeeling.assertTrue(testBase + 18, inta instanceof Object);

		Darjeeling.assertTrue(testBase + 19, !(intb instanceof int[]));
		Darjeeling.assertTrue(testBase + 20, intb instanceof byte[]);
		Darjeeling.assertTrue(testBase + 21, !(intb instanceof boolean[]));
		Darjeeling.assertTrue(testBase + 22, !(intb instanceof short[]));

		Darjeeling.assertTrue(testBase + 23, !(intbl instanceof int[]));
		Darjeeling.assertTrue(testBase + 24, !(intbl instanceof byte[]));
		Darjeeling.assertTrue(testBase + 25, intbl instanceof boolean[]);
		Darjeeling.assertTrue(testBase + 26, !(intbl instanceof short[]));

		Darjeeling.assertTrue(testBase + 27, !(ints instanceof int[]));
		Darjeeling.assertTrue(testBase + 28, !(ints instanceof byte[]));
		Darjeeling.assertTrue(testBase + 29, !(ints instanceof boolean[]));
		Darjeeling.assertTrue(testBase + 30, ints instanceof short[]);
		
	}
	
	public static void interfaceImplementTest(int testBase)
	{
		A a = new A();
		B b = new B();
		C c = new C();
		D d = new D();
		E e = new E();
        Inner inner = new Inner();

		Darjeeling.assertTrue(testBase +  0, a instanceof AInterface);
		Darjeeling.assertTrue(testBase +  1, b instanceof AInterface);
		Darjeeling.assertTrue(testBase +  2, c instanceof AInterface);
		Darjeeling.assertTrue(testBase +  3, d instanceof AInterface);

		Darjeeling.assertTrue(testBase +  4, !(a instanceof CInterface));
		Darjeeling.assertTrue(testBase +  5, !(b instanceof CInterface));
		Darjeeling.assertTrue(testBase +  6, c instanceof CInterface);
		Darjeeling.assertTrue(testBase +  7, d instanceof CInterface);

		Darjeeling.assertTrue(testBase +  8, e instanceof EInterface);
		Darjeeling.assertTrue(testBase +  9, e instanceof CInterface);
		Darjeeling.assertTrue(testBase + 10, e instanceof AInterface);
		Darjeeling.assertTrue(testBase + 11, !(e instanceof FInterface));

        Darjeeling.assertTrue(testBase + 12, !(a instanceof EInterface));
        Darjeeling.assertTrue(testBase + 13, !(d instanceof EInterface));

        // test if INSTANCEOF also works for interfaces across packages 
        Darjeeling.assertTrue(testBase + 14, inner instanceof Super);
        // Darjeeling.assertTrue(testBase + 15, inner instanceof Collection);
	}

	public static void interfaceArrayImplementTest(int testBase)
	{
		Object aa = new A[10];
		Object bb = new B[10];
		Object cc = new C[10];
		Object dd = new D[10];

		Darjeeling.assertTrue(testBase +  0, aa instanceof AInterface[]);
		Darjeeling.assertTrue(testBase +  1, bb instanceof AInterface[]);
		Darjeeling.assertTrue(testBase +  2, cc instanceof AInterface[]);
		Darjeeling.assertTrue(testBase +  3, dd instanceof AInterface[]);

		Darjeeling.assertTrue(testBase +  4, !(aa instanceof CInterface[]));
		Darjeeling.assertTrue(testBase +  5, !(bb instanceof CInterface[]));
		Darjeeling.assertTrue(testBase +  6, cc instanceof CInterface[]);
		Darjeeling.assertTrue(testBase +  7, dd instanceof CInterface[]);

		/*
		 * TODO these tests fail on Darjeeling, but pass on hotspot - a ticket has been created on TRAC
		Darjeeling.assertTrue(testBase +  8, !(aa instanceof AInterface));
		Darjeeling.assertTrue(testBase +  9, !(bb instanceof AInterface));
		*/
	}

	private static void checkcastTest(int testBase)
	{
		// check cast
		Object a = new A();
		Object b = new B();
		Object c = new C();
		Object d = new D();

		Object o = b;
		a = (A)o;
		Darjeeling.assertTrue(testBase +  0, a instanceof A);
		Darjeeling.assertTrue(testBase +  1, a instanceof B);

		o = c;
		a = (B)o;
		Darjeeling.assertTrue(testBase +  2, a instanceof A);
		Darjeeling.assertTrue(testBase +  3, a instanceof B);
		Darjeeling.assertTrue(testBase +  4, a instanceof C);
		
		C[] cc = new C[10];
		D[] dd = new D[10];
		Object oo = cc;
		A[] aa = (A[])cc;
		Darjeeling.assertTrue(testBase +  5, aa instanceof A[]);
		Darjeeling.assertTrue(testBase +  6, aa instanceof B[]);
		Darjeeling.assertTrue(testBase +  7, aa instanceof C[]);
		
		o = d;
		AInterface ai = (AInterface)o;
		Darjeeling.assertTrue(testBase +  8, ai instanceof AInterface);

		/*
		stato = new D();
		Vector v = new Vector((short)4, (short)4);
		v.add(stato);
		TouchScreenListener listener = (TouchScreenListener)v.get((short)0);
		Darjeeling.assertTrue(testBase +  9, listener instanceof TouchScreenListener);
		*/
		
	}
	
}
