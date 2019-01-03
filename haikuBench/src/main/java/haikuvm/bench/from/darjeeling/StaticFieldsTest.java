/*
 * StaticFieldsTest.java
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

public class StaticFieldsTest
{

    public static void main(String[] args) {
        test(0);
        System.out.println("Done");
    }
	private static int _integer;
	private static long _long;
	private static byte _byte;
	private static short _short;
	private static boolean _boolean;
	private static int[] _inta;
	
	private static void testInteger(int testBase)
	{
		_integer = 10;
		Darjeeling.assertTrue(testBase +  0, _integer == 10);
		A.integer1 = _integer;
		Darjeeling.assertTrue(testBase +  1, _integer == A.integer1);
		Darjeeling.assertTrue(testBase +  2, A.integer1 == 10);

		_integer = 20;
		Darjeeling.assertTrue(testBase +  3, _integer == 20);
		A.integer2 = _integer;
		Darjeeling.assertTrue(testBase +  4, _integer == A.integer2);
		Darjeeling.assertTrue(testBase +  5, A.integer2 == 20);

		_integer = 30;
		Darjeeling.assertTrue(testBase +  6, _integer == 30);
		A.integer3 = _integer;
		Darjeeling.assertTrue(testBase +  7, _integer == A.integer3);
		Darjeeling.assertTrue(testBase +  8, A.integer3 == 30);

	}
	
	private static void testLong(int testBase)
	{
		_long = 10;
		Darjeeling.assertTrue(testBase +  0, _long == 10);
		A.long1 = _long;
		Darjeeling.assertTrue(testBase +  1, _long == A.long1);
		Darjeeling.assertTrue(testBase +  2, A.long1 == 10);

		_long = 20;
		Darjeeling.assertTrue(testBase +  3, _long == 20);
		A.long2 = _long;
		Darjeeling.assertTrue(testBase +  4, _long == A.long2);
		Darjeeling.assertTrue(testBase +  5, A.long2 == 20);

		_long = 30;
		Darjeeling.assertTrue(testBase +  6, _long == 30);
		A.long3 = _long;
		Darjeeling.assertTrue(testBase +  7, _long == A.long3);
		Darjeeling.assertTrue(testBase +  8, A.long3 == 30);

	}
	
	private static void testShort(int testBase)
	{
		_short = 10;
		Darjeeling.assertTrue(testBase +  0, _short == 10);
		A.short1 = _short;
		Darjeeling.assertTrue(testBase +  1, _short == A.short1);
		Darjeeling.assertTrue(testBase +  2, A.short1 == 10);

		_short = 20;
		Darjeeling.assertTrue(testBase +  3, _short == 20);
		A.short2 = _short;
		Darjeeling.assertTrue(testBase +  4, _short == A.short2);
		Darjeeling.assertTrue(testBase +  5, A.short2 == 20);

		_short = 30;
		Darjeeling.assertTrue(testBase +  6, _short == 30);
		A.short3 = _short;
		Darjeeling.assertTrue(testBase +  7, _short == A.short3);
		Darjeeling.assertTrue(testBase +  8, A.short3 == 30);

	}
	
	private static void testByte(int testBase)
	{
		_byte = 10;
		Darjeeling.assertTrue(testBase +  0, _byte == 10);
		A.byte1 = _byte;
		Darjeeling.assertTrue(testBase +  1, _byte == A.byte1);
		Darjeeling.assertTrue(testBase +  2, A.byte1 == 10);

		_byte = 20;
		Darjeeling.assertTrue(testBase +  3, _byte == 20);
		A.byte2 = _byte;
		Darjeeling.assertTrue(testBase +  4, _byte == A.byte2);
		Darjeeling.assertTrue(testBase +  5, A.byte2 == 20);

		_byte = 30;
		Darjeeling.assertTrue(testBase +  6, _byte == 30);
		A.byte3 = _byte;
		Darjeeling.assertTrue(testBase +  7, _byte == A.byte3);
		Darjeeling.assertTrue(testBase +  8, A.byte3 == 30);

	}	
	
	private static void testBoolean(int testBase)
	{
		_boolean = true;
		Darjeeling.assertTrue(testBase +  0, _boolean == true);
		A.boolean1 = _boolean;
		Darjeeling.assertTrue(testBase +  1, _boolean == A.boolean1);
		Darjeeling.assertTrue(testBase +  2, A.boolean1 == true);

		_boolean = false;
		Darjeeling.assertTrue(testBase +  3, _boolean == false);
		A.boolean2 = _boolean;
		Darjeeling.assertTrue(testBase +  4, _boolean == A.boolean2);
		Darjeeling.assertTrue(testBase +  5, A.boolean2 == false);

		_boolean = true;
		Darjeeling.assertTrue(testBase +  6, _boolean == true);
		A.boolean3 = _boolean;
		Darjeeling.assertTrue(testBase +  7, _boolean == A.boolean3);
		Darjeeling.assertTrue(testBase +  8, A.boolean3 == true);

	}	
	
	private static void testIntArray(int testBase)
	{
		int[] inta = new int[10];
		_inta = inta;
		Darjeeling.assertTrue(testBase +  0, _inta == inta);
		A.inta1 = _inta;
		Darjeeling.assertTrue(testBase +  1, _inta == A.inta1);
		Darjeeling.assertTrue(testBase +  2, A.inta1 == inta);

		inta = new int[10];
		_inta = inta;
		Darjeeling.assertTrue(testBase +  3, _inta == inta);
		A.inta2 = _inta;
		Darjeeling.assertTrue(testBase +  4, _inta == A.inta2);
		Darjeeling.assertTrue(testBase +  5, A.inta2 == inta);

		inta = new int[10];
		_inta = inta;
		Darjeeling.assertTrue(testBase +  6, _inta == inta);
		A.inta3 = _inta;
		Darjeeling.assertTrue(testBase +  7, _inta == A.inta3);
		Darjeeling.assertTrue(testBase +  8, A.inta3 == inta);

	}	
	
	public static void test(int testBase)
	{
		testByte(testBase + 00);		
		testBoolean(testBase + 10);
		testShort(testBase + 20);		
		testInteger(testBase + 30);		
		testLong(testBase + 40);		
		testIntArray(testBase + 50);		
	}
	

}
