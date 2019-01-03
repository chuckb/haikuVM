/*
 * TestSuite.java
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
 
package haikuvm.bench.from.darjeeling;

public class TestSuite
{
	
	public static void test()
	{
        ArithmeticTest.test(1000);
        ArrayTest.test(2000);
        CompareTest.test(3000);
        //ClassHierarchyTest.test(4000);
        StaticFieldsTest.test(5000);
        FieldTest.test(6000);
        InitialiserTest.test(7000);
        InvokeVirtualTest.test(8000);
        GarbageCollectionTest.test(9000);
        SwitchTest.test(10000);
        ThreadTest.test(11000);
        InheritanceTest.test(12000);
        ExceptionsTest.test(13000);
        MD5Test.test(14000);
        MethodOverloadingTest.test(15000);
        RuntimeExceptionsTest.test(16000);
        BitManipulationTest.test(17000);
        AlignmentTest.test(18000);
//      TryCatchTest.test(1900);
//      InfusionTest.test(2000);
		System.out.println("Testsuites passed "+Darjeeling.passed+" / failed "+Darjeeling.failed+"\n");
	}

	public static void main(String[] args)
	{
		try {
			test();
		} catch (Throwable t)
		{
            System.out.println("Uncaught throwable: " + t.getMessage());
		}
	}
}
