/*
 * Sink.java
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

public class Sink
{
	
	public short nodeId;
	public short route;
	public int lastHeard;
	
	private short seenSequenceNrIndex;
	private short[] seenSequenceNrEntries;
	
	public Sink(short nodeId)
	{
		this.nodeId = nodeId;
		seenSequenceNrEntries = new short[12];
		for (short i=0; i<seenSequenceNrEntries.length; i++)
			seenSequenceNrEntries[i] = -1;
	}
	
	public boolean seenSequenceNumber(short sequenceNr)
	{
		// check if we've seen this sequence number before
		for (short i=0; i<seenSequenceNrEntries.length; i++)
			if (seenSequenceNrEntries[i]==sequenceNr) return true;
		
		// add the sequence number to the buffer
		seenSequenceNrEntries[seenSequenceNrIndex] = sequenceNr;
		seenSequenceNrIndex = (short)((seenSequenceNrIndex+1) % seenSequenceNrEntries.length);
		
		return false;
	}

}
