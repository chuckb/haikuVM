/*
 * BitManipulationTest.java
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


import haikuvm.bench.from.darjeeling.testvm.classes.Child;


public class BitManipulationTest {
	public static void test(int testBase)
	{
		Child child = new Child(new byte[4]);
		for (int i = 0; i < 10; i ++){
			for (int j = 0; j < child.getBytes().length; j ++)
			child.setCongested(true);
			child.getBytes()[0] = 0;
			Darjeeling.assertTrue(testBase + i*4, child.getCongested());
			child.setCongested(false);
			Darjeeling.assertTrue(testBase + i*4 + 1, !child.getCongested());
			child.setRoutingPull(true);
			Darjeeling.assertTrue(testBase + i*4 + 2, child.getRoutingPull());
			child.setRoutingPull(false);
			Darjeeling.assertTrue(testBase + i*4 + 3, !child.getRoutingPull());
		}
	}
}
