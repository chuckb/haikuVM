/*
 * TreeNode.java
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

public class TreeNode
{
	
	private TreeNode left,right;
	private byte value;
	
	public TreeNode(byte value)
	{
		right = left = null;
		this.value = value;
	}
	
	public byte getValue()
	{
		return value;
	}
	
	public void insert(TreeNode node)
	{
		if (node.getValue()<value)
			insertLeft(node);
		else
			insertRight(node);
	}
	
	private void insertLeft(TreeNode node)
	{
		if (left==null)
			left=node;
		else
			left.insert(node);
	}
	
	private void insertRight(TreeNode node)
	{
		if (right==null)
			right=node;
		else
			right.insert(node);
	}
	
	public TreeNode getLeft()
	{
		return left;
	}

	public TreeNode getRight()
	{
		return right;
	}

}
