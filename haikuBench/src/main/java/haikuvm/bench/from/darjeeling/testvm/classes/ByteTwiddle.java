/*
 * ByteTwiddle.java
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

public class ByteTwiddle
{
        //Bytes and shorts interleaved
        private static byte staticByte_;
        private static short staticShort_;
        private static int staticInt_;

        private byte fieldByte_;
        private short fieldShort_;
        private int fieldInt_;

        //class fields occupies odd number of bytes. So this reference could be placed unaligned.
        private Byte[] byteArray=new Byte[3];

        //static setters/getters
        public void setStaticByte(byte value) {staticByte_ = value;}
        public byte getStaticByte() {return staticByte_;}
        public void setStaticShort(short value) {staticShort_ = value;}
        public short getStaticShort() {return staticShort_;}
        public void setStaticInt(int value) {staticInt_ = value;}
        public int getStaticInt() {return staticInt_;}

        //field setters/getters
        public void setFieldByte(byte value) {fieldByte_ = value;}
        public byte getFieldByte() {return fieldByte_;}
        public void setFieldShort(short value) {fieldShort_ = value;}
        public short getFieldShort() {return fieldShort_;}
        public void setFieldInt(int value) {fieldInt_ = value;}
        public int getFieldInt() {return fieldInt_;}

        //function arguments could be unaligned
        public int setArray(short foo, byte staticByte, byte fieldByte, byte b3, short bar)
        {
                byteArray[0] = staticByte;
                byteArray[1] = fieldByte;
                byteArray[2] = b3;
                return foo*bar;
        }
        public int getProduct()
        {
                return staticByte_*staticShort_*staticInt_*fieldByte_*fieldShort_*fieldInt_;
        }
}
