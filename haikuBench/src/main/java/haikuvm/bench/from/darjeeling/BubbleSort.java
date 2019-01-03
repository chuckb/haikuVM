package haikuvm.bench.from.darjeeling;
/*
 * BubbleSort.java
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

public class BubbleSort
{
    // performance see http://www.st.ewi.tudelft.nl/~niels/p369-brouwers.pdf
	public static void main(String args[])
	{
		int i,j,l;

		short NUMNUMBERS = 500;
		short numbers[] = new short[NUMNUMBERS];
		long t0=System.currentTimeMillis();

		System.out.println("START");

		for (l=0; l<2; l++)
		{
	        System.out.println("a "+l);

			for (i=0; i<NUMNUMBERS; i++)
				numbers[i] = (short)(NUMNUMBERS - 1 - i);

            System.out.println("b "+l);
			for (i=0; i<NUMNUMBERS; i++)
			{
				for (j=0; j<NUMNUMBERS-i-1; j++)
					if (numbers[j]>numbers[j+1])
					{
						short temp = numbers[j];
						numbers[j] = numbers[j+1];
						numbers[j+1] = temp;
					}
			}
            System.out.println("c "+l);
		}
		
		System.out.println("END "+ (System.currentTimeMillis()-t0));
	}
}
