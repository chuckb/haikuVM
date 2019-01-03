/*
 * MethodOverloadingTest.java
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



public class MethodOverloadingTest
{
	
	public int test()
	{
		return 0;
	}
	 
	public int test(String a)
	{
		return 1;
	}
	 
	public int test(int a, int b)
	{
		return 2;
	}
	 
	public int test(int a, int b, int c)
	{
		return 3;
	}
	 
	
	public static void test(int testBase)
	{
		MethodOverloadingTest test = new MethodOverloadingTest();
		Darjeeling.assertTrue(testBase + 0, test.test()==0);
		Darjeeling.assertTrue(testBase + 1, test.test(null)==1);
		Darjeeling.assertTrue(testBase + 2, test.test(0,0)==2);
		Darjeeling.assertTrue(testBase + 3, test.test(0,0,0)==3);
		
	}

}
