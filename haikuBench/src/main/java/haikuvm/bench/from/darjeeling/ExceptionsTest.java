/*
 * ExceptionsTest.java
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

import haikuvm.bench.from.darjeeling.testvm.classes.A;

public class ExceptionsTest
{

    public static void main(String[] args) {
        test(0);
        System.out.println("Done");
    }
    
	
	private static void testMethodThrows() throws Exception
	{
		boolean dummy = true;
		if (dummy) throw new Exception();
	}
	
	public static void test(int testBase)
	{
		
		boolean dummy = true;
		
		// basic test
		try {
			if (dummy) throw new Throwable();	// the 'if (true)' is just there to fool the compiler's flow analysis :D
			Darjeeling.assertTrue(testBase+00, false);
		} catch(Throwable t) {
			Darjeeling.assertTrue(testBase+00, true);
		}
		
		// inheritance test
		try {
			if (dummy) throw new Exception();
			Darjeeling.assertTrue(testBase+10, false);
		} catch(Throwable t) {
			Darjeeling.assertTrue(testBase+10, true);
		}
		
		// inheritance test 2 (should not be handled by the second catch block)
		try {
			if (dummy) throw new Exception();
			Darjeeling.assertTrue(testBase+20, false);
		} catch(Exception t) {
			Darjeeling.assertTrue(testBase+20, true);
		} catch(Throwable t) {
			Darjeeling.assertTrue(testBase+20, false);
		}
		
		// inheritance test 3 (should be handled by the second catch block)
		try {
			if (dummy) throw new Exception();
			Darjeeling.assertTrue(testBase+30, false);
		} catch(Error t) {
			Darjeeling.assertTrue(testBase+30, false);
		} catch(Throwable t) {
			Darjeeling.assertTrue(testBase+30, true);
		}

		// interprocedural exception catching
		try {
			testMethodThrows();
			Darjeeling.assertTrue(testBase+40, false);
		} catch(Throwable t) {
			Darjeeling.assertTrue(testBase+40, true);
		}
		
		// nullpointer test
		try {
			A a = null;
			a.x = 10;
			Darjeeling.assertTrue(testBase+50, false);
		} catch (NullPointerException ex)
		{
			Darjeeling.assertTrue(testBase+50, true);
		}

        try {
			A a = null;
			int x = a.x;
			Darjeeling.assertTrue(testBase+51, false);
		} catch (NullPointerException ex)
		{
			Darjeeling.assertTrue(testBase+51, true);
		}

        
		// out of bounds tests

        // test with negative index
        try {
			byte[] arr = new byte[0];
			arr[-1] = 10;
			Darjeeling.assertTrue(testBase+60, false);
		} catch (IndexOutOfBoundsException ex)
		{
			Darjeeling.assertTrue(testBase+60, true);
		}

        // test with index 0 on empty array
		try {
			short[] arr = new short[0];
			arr[0] = 10;
			Darjeeling.assertTrue(testBase+61, false);
		} catch (IndexOutOfBoundsException ex)
		{
			Darjeeling.assertTrue(testBase+61, true);
		}

        // test with index 0 on one-slot array
        try {
			int[] arr = new int[1];
			arr[0] = 10;
			Darjeeling.assertTrue(testBase+62, true);
		} catch (IndexOutOfBoundsException ex)
		{
			Darjeeling.assertTrue(testBase+62, false);
		}

        // test with index beyond array size
		try {
			int[] arr = new int[10];
			arr[10] = 10;
			Darjeeling.assertTrue(testBase+63, false);
		} catch (IndexOutOfBoundsException ex)
		{
			Darjeeling.assertTrue(testBase+63, true);
		}

		// hacky test
		/*
		try {
			SinkList sinks = new SinkList();
			sinks.add(new Sink((short)1));
			int size = sinks.size();
			Darjeeling.assertTrue(testBase+70, true);
		} catch (Error err)
		{
			Darjeeling.assertTrue(testBase+70, false);
		}
		*/
		
		// hacky test
		try {
			A a = new A();
			a.x = 10;
			Darjeeling.assertTrue(testBase+80, a.createInner().getX()==10);
		} catch (Error err)
		{
			Darjeeling.assertTrue(testBase+80, false);
		}
		
		// test finally
		boolean fin = false;
		try {
			try
            {
				throw new NullPointerException();
			}
            catch (NullPointerException ex)
			{
				throw new RuntimeException();
			}
            finally
            {
				fin = true;
			}
		} catch (RuntimeException ex)
		{
			Darjeeling.assertTrue(testBase+90, fin);
		}
		
		// ClassCastException test (submitted by François Revol)
		Object o = new Object();
		try {
			String s = (String)o;
			Darjeeling.assertTrue(testBase+91, false);
		} catch (Exception e) {
			Darjeeling.assertTrue(testBase+91, true);
		}

        int counterIntuitive = returnsMinusOne();
        Darjeeling.assertTrue(testBase+92,counterIntuitive == -1);
        
	}

    //see http://www.cs.arizona.edu/projects/sumatra/hallofshame/
    public static int returnsMinusOne()
    {
        while(true) {       	   
           try {
               return 1;
           } finally {
               break;	   
           }		   
       }
       return -1;
    }
    

}
