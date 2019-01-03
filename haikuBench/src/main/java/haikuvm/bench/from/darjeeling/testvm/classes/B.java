/*
 * B.java
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

public class B extends A
{
	
	public int integer1, integer2, integer3; 
	public long long1, long2, long3; 
	public byte byte1, byte2, byte3; 
	public short short1, short2, short3; 
	public boolean boolean1, boolean2, boolean3; 
	public int[] inta1, inta2, inta3; 

	A a;
	
 	public int virtualMethod()
	{
		return 1;
	}
	

}
