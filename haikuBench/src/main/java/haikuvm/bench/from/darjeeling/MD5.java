/*
 * MD5.java
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


public class MD5
{
	
	private static byte padding[] = new byte[] { 
		(byte) 0x80, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		0, 0, 0, 0, 0, 0, 0
		};
	
	private static class MD5State
	{
		int state[];
		int count[];
		byte buffer[];

		public MD5State()
		{
			buffer = new byte[64];
			count = new int[2];
			state = new int[4];

			state[0] = 0x67452301;
			state[1] = 0xefcdab89;
			state[2] = 0x98badcfe;
			state[3] = 0x10325476;

			count[0] = count[1] = 0;
		}

		/** Create this State as a copy of another state */
		public MD5State(MD5State from)
		{
			this();

			int i;

			for (i = 0; i < buffer.length; i++)
				this.buffer[i] = from.buffer[i];

			for (i = 0; i < state.length; i++)
				this.state[i] = from.state[i];

			for (i = 0; i < count.length; i++)
				this.count[i] = from.count[i];
		}
		
	};

	static MD5State state = new MD5State();
	static MD5State finals;
	
	public static boolean testItem(byte[] input, byte[] desiredOutput)
	{
		MD5 md5 = new MD5();
		md5.Init();
		md5.Update(input, 0, input.length);
		byte[] hash = md5.Final();
		
		for (int i=0; i<hash.length; i++)
			if (desiredOutput[i]!=hash[i]) return false;

		return true;
	}
	
	public static void testMany(int testBase)
	{

		Darjeeling.assertTrue(testBase+0, testItem(
				new byte[] {},
				new byte[] { (byte)0xd4, (byte)0x1d, (byte)0x8c, (byte)0xd9, (byte)0x8f, (byte)0x00, (byte)0xb2, (byte)0x04, (byte)0xe9, (byte)0x80, (byte)0x09, (byte)0x98, (byte)0xec, (byte)0xf8, (byte)0x42, (byte)0x7e }
				));
		
		Darjeeling.assertTrue(testBase+1, testItem(
				new byte[] { (byte)'a' },
				new byte[] { (byte)0x0c, (byte)0xc1, (byte)0x75, (byte)0xb9, (byte)0xc0, (byte)0xf1, (byte)0xb6, (byte)0xa8, (byte)0x31, (byte)0xc3, (byte)0x99, (byte)0xe2, (byte)0x69, (byte)0x77, (byte)0x26, (byte)0x61 }
				));

		Darjeeling.assertTrue(testBase+2, testItem(
				new byte[] { (byte)'a', (byte)'b', (byte)'c'  },
				new byte[] { (byte)0x90, (byte)0x01, (byte)0x50, (byte)0x98, (byte)0x3c, (byte)0xd2, (byte)0x4f, (byte)0xb0, (byte)0xd6, (byte)0x96, (byte)0x3f, (byte)0x7d, (byte)0x28, (byte)0xe1, (byte)0x7f, (byte)0x72 }
				));

		Darjeeling.assertTrue(testBase+3, testItem(
				new byte[] { (byte)'m', (byte)'e', (byte)'s', (byte)'s', (byte)'a', (byte)'g', (byte)'e', (byte)' ', (byte)'d', (byte)'i', (byte)'g', (byte)'e', (byte)'s', (byte)'t' },
				new byte[] { (byte)0xf9, (byte)0x6b, (byte)0x69, (byte)0x7d, (byte)0x7c, (byte)0xb7, (byte)0x93, (byte)0x8d, (byte)0x52, (byte)0x5a, (byte)0x2f, (byte)0x31, (byte)0xaa, (byte)0xf1, (byte)0x61, (byte)0xd0 }
				));
		
		Darjeeling.assertTrue(testBase+4, testItem(
				new byte[] { (byte)'d', (byte)'a', (byte)'r', (byte)'j', (byte)'e', (byte)'e', (byte)'l', (byte)'i', (byte)'n', (byte)'g' },
				new byte[] { (byte)0xb9, (byte)0xcc, (byte)0xe4, (byte)0x72, (byte)0xb9, (byte)0x7d, (byte)0x91, (byte)0x58, (byte)0xd0, (byte)0x78, (byte)0x6a, (byte)0xcf, (byte)0x37, (byte)0xce, (byte)0x6a, (byte)0xe3 }
				));

	}

	/**
	 * Initialize MD5 internal state (object can be reused just by
	 * calling Init() after every Final()
	 */
	public synchronized void Init()
	{
		state = new MD5State();
		finals = null;
	}

	/**
	 * Class constructor
	 */
	public MD5()
	{
		this.Init();
	}

	private int rotate_left(int x, int n)
	{
		return (x << n) | (x >>> (32 - n));
	}

	private int[] Decode(byte buffer[], int len, int shift)
	{
		int out[];
		int i, j;

		out = new int[16];

		for (i = j = 0; j < len; i++, j += 4)
		{
			out[i] = ((int) (buffer[j + shift] & 0xff))
					| (((int) (buffer[j + 1 + shift] & 0xff)) << 8)
					| (((int) (buffer[j + 2 + shift] & 0xff)) << 16)
					| (((int) (buffer[j + 3 + shift] & 0xff)) << 24);

		}

		return out;
	}

	private void Transform(MD5State state, byte buffer[], int shift)
	{
		int a = state.state[0], b = state.state[1], c = state.state[2], d = state.state[3], x[];

		x = Decode(buffer, 64, shift);

		/* Round 1 */
		
		a = rotate_left(a + ((b & c) | (~b & d)) + x[0] + 0xd76aa478, 7) + b; /* 1 */
		d = rotate_left(d + ((a & b) | (~a & c)) + x[1] + 0xe8c7b756, 12) + a; /* 2 */
		c = rotate_left(c + ((d & a) | (~d & b)) + x[2] + 0x242070db, 17) + d; /* 3 */
		b = rotate_left(b + ((c & d) | (~c & a)) + x[3] + 0xc1bdceee, 22) + c; /* 4 */
		a = rotate_left(a + ((b & c) | (~b & d)) + x[4] + 0xf57c0faf, 7) + b; /* 5 */
		d = rotate_left(d + ((a & b) | (~a & c)) + x[5] + 0x4787c62a, 12) + a; /* 6 */
		c = rotate_left(c + ((d & a) | (~d & b)) + x[6] + 0xa8304613, 17) + d; /* 7 */
		b = rotate_left(b + ((c & d) | (~c & a)) + x[7] + 0xfd469501, 22) + c; /* 8 */
		a = rotate_left(a + ((b & c) | (~b & d)) + x[8] + 0x698098d8, 7) + b; /* 9 */
		d = rotate_left(d + ((a & b) | (~a & c)) + x[9] + 0x8b44f7af, 12) + a; /* 10 */
		c = rotate_left(c + ((d & a) | (~d & b)) + x[10] + 0xffff5bb1, 17) + d; /* 11 */
		b = rotate_left(b + ((c & d) | (~c & a)) + x[11] + 0x895cd7be, 22) + c; /* 12 */
		a = rotate_left(a + ((b & c) | (~b & d)) + x[12] + 0x6b901122, 7) + b; /* 13 */
		d = rotate_left(d + ((a & b) | (~a & c)) + x[13] + 0xfd987193, 12) + a; /* 14 */
		c = rotate_left(c + ((d & a) | (~d & b)) + x[14] + 0xa679438e, 17) + d; /* 15 */
		b = rotate_left(b + ((c & d) | (~c & a)) + x[15] + 0x49b40821, 22) + c; /* 16 */

		/* Round 2 */
		a = rotate_left(a +  ((b & d) | (c & ~d)) +  x[1] +  0xf61e2562, 5) +  b; /* 17 */
		d = rotate_left(d +  ((a & c) | (b & ~c)) +  x[6] +  0xc040b340, 9) +  a; /* 18 */
		c = rotate_left(c +  ((d & b) | (a & ~b)) +  x[11] +  0x265e5a51, 14) +  d; /* 19 */
		b = rotate_left(b +  ((c & a) | (d & ~a)) +  x[0] +  0xe9b6c7aa, 20) +  c; /* 20 */
		a = rotate_left(a +  ((b & d) | (c & ~d)) +  x[5] +  0xd62f105d, 5) +  b; /* 21 */
		d = rotate_left(d +  ((a & c) | (b & ~c)) +  x[10] +  0x2441453, 9) +  a; /* 22 */
		c = rotate_left(c +  ((d & b) | (a & ~b)) +  x[15] +  0xd8a1e681, 14) +  d; /* 23 */
		b = rotate_left(b +  ((c & a) | (d & ~a)) +  x[4] +  0xe7d3fbc8, 20) +  c; /* 24 */
		a = rotate_left(a +  ((b & d) | (c & ~d)) +  x[9] +  0x21e1cde6, 5) +  b; /* 25 */
		d = rotate_left(d +  ((a & c) | (b & ~c)) +  x[14] +  0xc33707d6, 9) +  a; /* 26 */
		c = rotate_left(c +  ((d & b) | (a & ~b)) +  x[3] +  0xf4d50d87, 14) +  d; /* 27 */
		b = rotate_left(b +  ((c & a) | (d & ~a)) +  x[8] +  0x455a14ed, 20) +  c; /* 28 */
		a = rotate_left(a +  ((b & d) | (c & ~d)) +  x[13] +  0xa9e3e905, 5) +  b; /* 29 */
		d = rotate_left(d +  ((a & c) | (b & ~c)) +  x[2] +  0xfcefa3f8, 9) +  a; /* 30 */
		c = rotate_left(c +  ((d & b) | (a & ~b)) +  x[7] +  0x676f02d9, 14) +  d; /* 31 */
		b = rotate_left(b +  ((c & a) | (d & ~a)) +  x[12] +  0x8d2a4c8a, 20) +  c; /* 32 */

		/* Round 3 */
		a = rotate_left(a +  (b ^ c ^ d) +  x[5] +  0xfffa3942, 4) + b; /* 33 */
		d = rotate_left(d +  (a ^ b ^ c) +  x[8] +  0x8771f681, 11) + a; /* 34 */
		c = rotate_left(c +  (d ^ a ^ b) +  x[11] +  0x6d9d6122, 16) + d; /* 35 */
		b = rotate_left(b +  (c ^ d ^ a) +  x[14] +  0xfde5380c, 23) + c; /* 36 */
		a = rotate_left(a +  (b ^ c ^ d) +  x[1] +  0xa4beea44, 4) + b; /* 37 */
		d = rotate_left(d +  (a ^ b ^ c) +  x[4] +  0x4bdecfa9, 11) + a; /* 38 */
		c = rotate_left(c +  (d ^ a ^ b) +  x[7] +  0xf6bb4b60, 16) + d; /* 39 */
		b = rotate_left(b +  (c ^ d ^ a) +  x[10] +  0xbebfbc70, 23) + c; /* 40 */
		a = rotate_left(a +  (b ^ c ^ d) +  x[13] +  0x289b7ec6, 4) + b; /* 41 */
		d = rotate_left(d +  (a ^ b ^ c) +  x[0] +  0xeaa127fa, 11) + a; /* 42 */
		c = rotate_left(c +  (d ^ a ^ b) +  x[3] +  0xd4ef3085, 16) + d; /* 43 */
		b = rotate_left(b +  (c ^ d ^ a) +  x[6] +  0x4881d05, 23) + c; /* 44 */
		a = rotate_left(a +  (b ^ c ^ d) +  x[9] +  0xd9d4d039, 4) + b; /* 45 */
		d = rotate_left(d +  (a ^ b ^ c) +  x[12] +  0xe6db99e5, 11) + a; /* 46 */
		c = rotate_left(c +  (d ^ a ^ b) +  x[15] +  0x1fa27cf8, 16) + d; /* 47 */
		b = rotate_left(b +  (c ^ d ^ a) +  x[2] +  0xc4ac5665, 23) + c; /* 48 */

		/* Round 4 */
		a = rotate_left(a +  (c ^ (b | ~d)) +  x[0] + 0xf4292244, 6) + b; /* 49 */
		d = rotate_left(d +  (b ^ (a | ~c)) +  x[7] + 0x432aff97, 10) + a; /* 50 */
		c = rotate_left(c +  (a ^ (d | ~b)) +  x[14] + 0xab9423a7, 15) + d; /* 51 */
		b = rotate_left(b +  (d ^ (c | ~a)) +  x[5] + 0xfc93a039, 21) + c; /* 52 */
		a = rotate_left(a +  (c ^ (b | ~d)) +  x[12] + 0x655b59c3, 6) + b; /* 53 */
		d = rotate_left(d +  (b ^ (a | ~c)) +  x[3] + 0x8f0ccc92, 10) + a; /* 54 */
		c = rotate_left(c +  (a ^ (d | ~b)) +  x[10] + 0xffeff47d, 15) + d; /* 55 */
		b = rotate_left(b +  (d ^ (c | ~a)) +  x[1] + 0x85845dd1, 21) + c; /* 56 */
		a = rotate_left(a +  (c ^ (b | ~d)) +  x[8] + 0x6fa87e4f, 6) + b; /* 57 */
		d = rotate_left(d +  (b ^ (a | ~c)) +  x[15] + 0xfe2ce6e0, 10) + a; /* 58 */
		c = rotate_left(c +  (a ^ (d | ~b)) +  x[6] + 0xa3014314, 15) + d; /* 59 */
		b = rotate_left(b +  (d ^ (c | ~a)) +  x[13] + 0x4e0811a1, 21) + c; /* 60 */
		a = rotate_left(a +  (c ^ (b | ~d)) +  x[4] + 0xf7537e82, 6) + b; /* 61 */
		d = rotate_left(d +  (b ^ (a | ~c)) +  x[11] + 0xbd3af235, 10) + a; /* 62 */
		c = rotate_left(c +  (a ^ (d | ~b)) +  x[2] + 0x2ad7d2bb, 15) + d; /* 63 */
		b = rotate_left(b +  (d ^ (c | ~a)) +  x[9] + 0xeb86d391, 21) + c; /* 64 */

		state.state[0] += a;
		state.state[1] += b;
		state.state[2] += c;
		state.state[3] += d;
	}

	/**
	 * Updates hash with the bytebuffer given (using at maximum length bytes from
	 * that buffer)
	 *
	 * @param state	Which state is updated
	 * @param buffer	Array of bytes to be hashed
	 * @param offset	Offset to buffer array
	 * @param length	Use at maximum `length' bytes (absolute
	 *			maximum is buffer.length)
	 */
	public void Update(MD5State stat, byte buffer[], int offset, int length)
	{
		int index, partlen, i, start;

		/*    System.out.print("Offset = " + offset + "\tLength = " + length + "\t");
		 System.out.print("Buffer = ");
		 for (i = 0; i < buffer.length; i++)
		 System.out.print((int) (buffer[i] & 0xff) + " ");
		 System.out.print("\n");*/

		finals = null;

		/* Length can be told to be shorter, but not inter */
		if ((length - offset) > buffer.length)
			length = buffer.length - offset;

		/* compute number of bytes mod 64 */
		index = (int) (stat.count[0] >>> 3) & 0x3f;

		if ((stat.count[0] += (length << 3)) < (length << 3))
			stat.count[1]++;

		stat.count[1] += length >>> 29;

		partlen = 64 - index;

		if (length >= partlen)
		{
			for (i = 0; i < partlen; i++)
				stat.buffer[i + index] = buffer[i + offset];

			Transform(stat, stat.buffer, 0);

			for (i = partlen; (i + 63) < length; i += 64)
				Transform(stat, buffer, i);

			index = 0;
		} else
			i = 0;

		/* buffer remaining input */
		if (i < length)
		{
			start = i;
			for (; i < length; i++)
				stat.buffer[index + i - start] = buffer[i + offset];
		}
	}

	/*
	 * Update()s for other datatypes than byte[] also. Update(byte[], int)
	 * is only the main driver.
	 */

	/**
	 * Plain update, updates this object
	 */

	public void Update(byte buffer[], int offset, int length)
	{
		Update(this.state, buffer, offset, length);
	}

	public void Update(byte buffer[], int length)
	{
		Update(this.state, buffer, 0, length);
	}

	/**
	 * Updates hash with given array of bytes
	 *
	 * @param buffer	Array of bytes to use for updating the hash
	 */
	public void Update(byte buffer[])
	{
		Update(buffer, 0, buffer.length);
	}

	/**
	 * Updates hash with a single byte
	 *
	 * @param b		Single byte to update the hash
	 */
	public void Update(byte b)
	{
		byte buffer[] = new byte[1];
		buffer[0] = b;

		Update(buffer, 1);
	}

	/**
	 * Update buffer with given string.
	 *
	 * @param s		String to be update to hash (is used as
	 *		       	s.getBytes())
	 */
	public void Update(String s)
	{
		byte chars[];

		/*
		chars = new byte[s.length()];
		chars = s.getBytes();
		*/

		/* Update(chars, chars.length); */
	}

	/**
	 * Update buffer with a single integer (only & 0xff part is used,
	 * as a byte)
	 *
	 * @param i		Integer value, which is then converted to
	 *			byte as i & 0xff
	 */
	public void Update(int i)
	{
		Update((byte) (i & 0xff));
	}

	private byte[] Encode(int input[], int len)
	{
		int i, j;
		byte out[];

		out = new byte[len];

		for (i = j = 0; j < len; i++, j += 4)
		{
			out[j] = (byte) (input[i] & 0xff);
			out[j + 1] = (byte) ((input[i] >>> 8) & 0xff);
			out[j + 2] = (byte) ((input[i] >>> 16) & 0xff);
			out[j + 3] = (byte) ((input[i] >>> 24) & 0xff);
		}

		return out;
	}

	/**
	 * Returns array of bytes (16 bytes) representing hash as of the
	 * current state of this object. Note: getting a hash does not
	 * invalidate the hash object, it only creates a copy of the real
	 * state which is finalized.
	 *
	 * @return	Array of 16 bytes, the hash of all updated bytes
	 */
	public synchronized byte[] Final()
	{
		byte bits[];
		int index, padlen;
		
		MD5State fin;

		if (finals == null)
		{
			fin = new MD5State(state);

			bits = Encode(fin.count, 8);

			index = (int) ((fin.count[0] >>> 3) & 0x3f);
			padlen = (index < 56) ? (56 - index) : (120 - index);

			Update(fin, padding, 0, padlen);
			Update(fin, bits, 0, 8);

			finals = fin;
		}

		return Encode(finals.state, 16);
	}
	
}
