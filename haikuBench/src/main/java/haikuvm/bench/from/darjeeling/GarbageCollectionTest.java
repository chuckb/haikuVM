/*
 * GarbageCollectionTest.java
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
import haikuvm.bench.from.darjeeling.testvm.classes.TreeNode;

public class GarbageCollectionTest //implements Runnable
{
	
	private static A staticA;
	
	public static void simpleObjectTest(short testBase)
	{
		staticA = new A(-3,2);
		A localA = new A(12,8);
		
		for (short i=0; i<1000; i++)
		{
			A a = new A(i,i*2);
		}

		System.gc();
        
		Darjeeling.assertTrue(testBase +  0, true);
		Darjeeling.assertTrue(testBase +  1, localA.getX()==12);
		Darjeeling.assertTrue(testBase +  2, localA.getY()==8);
		Darjeeling.assertTrue(testBase +  3, staticA.getX()==-3);
		Darjeeling.assertTrue(testBase +  4, staticA.getY()==2);
		
	}
	
	public static int recurseTree(TreeNode node, int i, byte[] targetArray)
	{
		int ret = 0;
		if (node==null) return 0;
		
		ret += recurseTree(node.getLeft(), i, targetArray);
		
		targetArray[i+ret] = node.getValue();
		ret++;
		
		ret += recurseTree(node.getRight(), i + ret, targetArray);
		
		return ret;
	}

	public static void treeSortTest(int testBase)
	{

		// int numbers[] = new int[] { 21,6,36,76,7,97,94,30,90,86,13,80,84,79,28,55,36,95,23,82,57,73,28,46,48,94,18,23,86,100,47,42,39,33,52,98,77,81,86,64,27,70,91,42,6,95,8,6,36,71,79,32,27,34,87,100,85,90,69,12,73,70,76,65,51,21,4,5,82,77,63,87,12,11,69,79,12,35,43,35,67,16,38,78,60,6,79,92,43,69,57,74,58,21,45,69,45,55,73,24 };
		// int numbers[] = new int[] { 21,6,36,76,7,97,94,30,90,86,13,80,84,79,28,55,36,95,23 };
		byte numbers[] = new byte[] { 
				21,  6, 36, 76,  7, 97, 94, 30, 90, 86,
				13, 80, 84, 79, 28, 55, 36, 95, 23, 82
				};		

		TreeNode rootNode = new TreeNode(numbers[0]);
		for (int i=1; i<numbers.length; i++)
		{
			rootNode.insert(new TreeNode(numbers[i]));
		}
		
		// should leave tree intact since we have a ref to the rootnode
		// in the current frame
		System.gc();
		recurseTree(rootNode, 0, numbers);
		
		boolean isAscending = true;
		for (int i=0; i<numbers.length-1; i++)
			isAscending &= numbers[i]<=numbers[i+1];
		
		Darjeeling.assertTrue(testBase + 0, isAscending);
		
	}
	
	public static void arrayTest(int testBase)
	{
		int i;
		int bufSize = 10;
		Object[] buf = new Object[bufSize];
		for (i=0; i<1000; i++)
		{
			buf[i%bufSize] = new byte[i%32];
		}
		Darjeeling.assertTrue(testBase, true);
	}

	public static void test(int testBase)
	{
		// simpleObjectTest((short)(testBase + 00));
		for (int i=0; i<10; i++)
			treeSortTest(testBase + 10 + i);
	        arrayTest(testBase + 20);
	}
	
}
