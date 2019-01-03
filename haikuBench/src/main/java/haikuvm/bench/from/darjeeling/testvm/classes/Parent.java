/*
 * Parent.java
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



public abstract class Parent
{
	
	protected byte[] bytes;
	public final static short SIZE = 3;
	public final static short BROADCAST_ID = -1;
	private short receiverId;
	
	protected Parent(short size, byte id, short senderId, short receiverId)
	{
		bytes = new byte[size];
		bytes[0] = id;
		setShort((short)1, senderId);
		this.receiverId = receiverId;
	}
	
	protected Parent(short size, byte id, short senderId)
	{
		bytes = new byte[size];
		bytes[0] = id;
		setShort((short)1, senderId);
		receiverId = BROADCAST_ID;
	}
	
	protected Parent(byte[] bytes)
	{
		this.bytes = bytes;
	}
	
	public short getReceiverId()
	{
		return receiverId;
	}
	
	public void setReceiverId(short receiverId)
	{
		this.receiverId = receiverId;
	}
	
	public boolean isBroadcast()
	{
		return receiverId == BROADCAST_ID;
	}
	
	public byte getPacketId()
	{
		return bytes[0];
	}
	
	public short getSenderId()
	{
		return getShort((short)1);
	}
	
	public void setSenderId(short id)
	{
		setShort((short)1, id);
	}
	
	public byte[] getBytes()
	{
		return bytes;
	}
	
	protected byte getByte(short offset)
	{
		return bytes[offset];
	}

	protected short getUnsignedByte(short offset)
	{
		return (short)(bytes[offset] & 255);
	}
	
	protected short getShort(short offset)
	{
		return (short)((bytes[offset+1]&255) + ((bytes[offset]&255)<<8)); 
	}

	protected int getUnsignedShort(short offset)
	{
		return (int)((bytes[offset+1]&255) + ((bytes[offset]&255)<<8)); 
	}

	protected void setByte(short offset, byte value)
	{
		bytes[offset] = value;
	}

	protected void setShort(short offset, short value)
	{
		bytes[offset] = (byte)(value >> 8); 
		bytes[offset+1] = (byte)(value); 
	}
	
}
