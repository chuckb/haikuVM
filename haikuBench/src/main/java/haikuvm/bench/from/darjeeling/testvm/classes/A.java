/*
 * A.java
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
 
package haikuvm.bench.from.darjeeling.testvm.classes;

public class A implements AInterface
{
	public static int integer1, integer2, integer3; 
	public static long long1, long2, long3; 
	public static byte byte1, byte2, byte3; 
	public static short short1, short2, short3; 
	public static boolean boolean1, boolean2, boolean3; 
	public static int[] inta1, inta2, inta3; 
	
	public int x,y;
	
	public class Inner
	{
		
		public int getX()
		{
			return x;
		}
		
	}
	
	int a;
	
	public A(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public A()
	{
		this.x = 2; 
		this.y = 3;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getSquaredLength()
	{
		return x*x + y*y;
	}
	
	public int virtualMethod()
	{
		return 0;
	}

	public int AInterfaceMethod()
	{
		return 0;
	}
	
	public Inner createInner()
	{
		return new Inner();
	}


}
