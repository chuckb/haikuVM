/*
 * StackUsageTest.java
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


import haikuvm.bench.from.darjeeling.testvm.classes.TreeNode;

public class StackUsageTest implements Runnable
{
	
    public static void main(String[] args) {
        test(0);
        System.out.println("Done");
    }

    private Object lock;
    private int testBase;

    private static int threads;
	
	public StackUsageTest(Object lock,int testBase)
	{
		this.lock = lock;
        this.testBase = testBase;
	}

	public static int recurseTree(TreeNode node, short i, byte[] targetArray)
	{
		int ret = 0;
		if (node==null) return 0;
		
		ret += recurseTree(node.getLeft(), i, targetArray);
		
		targetArray[i+ret] = node.getValue();
		ret++;
		
		ret += recurseTree(node.getRight(), (short)(i + ret), targetArray);
		
		return ret;
	}

	public void run()
	{
		synchronized(lock)
		{
            Darjeeling.assertTrue(testBase+0, true);
            byte numbers[] = new byte[] { 21,6,36,76,7,97,94,30,90,86,13,80,84,79,28,55,36,95,23 };
			
			TreeNode rootNode = new TreeNode(numbers[0]);
			
			for (int i=1; i<numbers.length; i++)
			{
				rootNode.insert(new TreeNode(numbers[i]));
			}
			
			// should leave tree intact since we have a ref to the rootnode
			// in the current frame
			recurseTree(rootNode, (short)0, numbers);
			
			boolean isAscending = true;
			for (short i=0; i<numbers.length-1; i++)
				isAscending &= numbers[i]<=numbers[i+1];
				
			threads--;
            Darjeeling.assertTrue(testBase+1, true);
		}
        Darjeeling.assertTrue(testBase+2, true);
		while (threads>0);
        Darjeeling.assertTrue(testBase+3, true);

	}

	public static void test(int testBase)
	{
		Object lock = new Object();
		for (int i=0; i<3; i++)
		{
			threads++;
			Thread t = new Thread(new StackUsageTest(lock,testBase+i*10));
			t.start();
		}

        boolean done=false;
        while(!done)
        {
            synchronized(lock)
            {
                done = (threads == 0);
            }
        }

        Darjeeling.assertTrue(testBase+99,true);
        
	}

}
