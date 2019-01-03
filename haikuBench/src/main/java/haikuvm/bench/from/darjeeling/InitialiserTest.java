/*
 * InitialiserTest.java
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


public class InitialiserTest
{
	
	public static int a = 100;
	public static int b;
	
	static
	{
		b = 20;
	}
	
	private static class InitTest
	{
		public int a = 123;
		public static int poepjes = 1;
	}
	
	public static void test(int testBase)
	{
		Darjeeling.assertTrue(testBase + 0, a==100);
		Darjeeling.assertTrue(testBase + 1, b==20);
		Darjeeling.assertTrue(testBase + 2, new InitTest().a==123);
		Darjeeling.assertTrue(testBase + 3, new InitTest().poepjes==1);
		Darjeeling.assertTrue(testBase + 4, InitTest.poepjes==1);
	}
	
    public static void main(String[] args) {
        test(0);
        System.out.println("Done");
   }

}
