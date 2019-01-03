/*
 * Child.java
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

public class Child extends Parent
{
	
	protected Child(short size, byte type, short id)
	{
		super(size, type, id);		
	}
	
	protected Child(short size, byte type, short id, short receiverId)
	{
		super(size, type, id, receiverId);		
	}
	
	public Child(byte[] bytes)
	{
		super(bytes);
	}
	
	
	public boolean getRoutingPull()
	{
		return (bytes[Parent.SIZE] & 1)>0;
	}

	public boolean getCongested()
	{
		return (bytes[Parent.SIZE] & 2)>0;
	}
	
		public void setRoutingPull(boolean pull)
		{
			if (pull){
				bytes[Parent.SIZE] |= 1;
			}
			else{
				bytes[Parent.SIZE] &= ~1;
			}
		}

	public void setCongested(boolean congested)
	{
		if (congested){
			bytes[Parent.SIZE] |= 2;
		}
		else{
			bytes[Parent.SIZE] &= ~2;
		}
	}

}
