/*
 * AlignmentTest.java
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
import haikuvm.bench.from.darjeeling.testvm.classes.ByteTwiddle;

public class AlignmentTest
{
        //make byteArrays, randomly assign them and trigger garbage collection
        private static void byteTest(int testNr)
        {
                byte oddByte=0;
                byte i2=2;
                byte i3=3;
                short i4=4;
                short i5=5;
                int i6=6;
                int i7=7;
                //Use a class with bytes and shorts interleaved.
                ByteTwiddle bt = new ByteTwiddle();
                //static setters
                bt.setStaticByte(i2);
                bt.setStaticShort(i4);
                bt.setStaticInt(i6);
                //field setters
                bt.setFieldByte(i3);
                bt.setFieldShort(i5);
                bt.setFieldInt(i7);
                Darjeeling.assertTrue(testNr++, 2*3*4*5*6*7==bt.getProduct());
                //bytes as return arguments, and static+field getters
                int product = bt.getStaticByte();
                product *= bt.getStaticShort();
                product *= bt.getStaticInt();
                product *= bt.getFieldByte();
                product *= bt.getFieldShort();
                product *= bt.getFieldInt();
                Darjeeling.assertTrue(testNr++, 2*3*4*5*6*7==product);
                //function arguments with odd number of bytes
                int ret = bt.setArray((short)0x55aa,(byte)1,(byte)(2),(byte)3,(short)0x55aa);
                Darjeeling.assertTrue(testNr++, ret == 0x55aa*0x55aa);
        }

        public static void test(int testBase)
        {
                byteTest(testBase + 00);
        }
}
