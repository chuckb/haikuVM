/******************************************************************************
 * from: http://www.mathworks.com/matlabcentral/fileexchange/23173
 *
 * MATLAB (R) is a trademark of The Mathworks (R) Corporation
 *
 * Function:    halfprecision
 * Filename:    halfprecision.c
 * Programmer:  James Tursa
 * Version:     1.0
 * Date:        March 3, 2009
 * Copyright:   (c) 2009 by James Tursa, All Rights Reserved
 *
 *  This code uses the BSD License:
 *
 *  Redistribution and use in source and binary forms, with or without 
 *  modification, are permitted provided that the following conditions are 
 *  met:
 *
 *     * Redistributions of source code must retain the above copyright 
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright 
 *       notice, this list of conditions and the following disclaimer in 
 *       the documentation and/or other materials provided with the distribution
 *      
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 *  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 *  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE 
 *  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 *  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 *  INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 *  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 *  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 *  POSSIBILITY OF SUCH DAMAGE.
 *
 * halfprecision converts the input argument to/from a half precision floating
 * point bit pattern corresponding to IEEE 754r. The bit pattern is stored in a
 * uint16 class variable. Please note that halfprecision is *not* a class. That
 * is, you cannot do any arithmetic with the half precision bit patterns.
 * halfprecision is simply a function that converts the IEEE 754r half precision
 * bit pattern to/from other numeric MATLAB variables. You can, however, take
 * the half precision bit patterns, convert them to single or double, do the
 * operation, and then convert the result back manually.
 *
 * 1 bit sign bit
 * 5 bits exponent, biased by 15
 * 10 bits mantissa, hidden leading bit, normalized to 1.0
 *
 * Special floating point bit patterns recognized and supported:
 *
 * All exponent bits zero:
 * - If all mantissa bits are zero, then number is zero (possibly signed)
 * - Otherwise, number is a denormalized bit pattern
 *
 * All exponent bits set to 1:
 * - If all mantissa bits are zero, then number is +Infinity or -Infinity
 * - Otherwise, number is NaN (Not a Number)
 *
 * Building:
 *
 *  halfprecision requires that a mex routine be built (one time only). This
 *  process is typically self-building the first time you call the function
 *  as long as you have the files halfprecision.m and halfprecision.c in the
 *  same directory somewhere on the MATLAB path. If you need to manually build
 *  the mex function, here are the commands:
 *
 * >> mex -setup
 *   (then follow instructions to select a C / C++ compiler of your choice)
 * >> mex halfprecision.c
 *
 * If you have an older version of MATLAB, you may need to use this command:
 *
 * >> mex -DDEFINEMWSIZE halfprecision.c
 *
 * Syntax
 *
 * B = halfprecision(A)
 * C = halfprecision(B,S)
 *     halfprecision(B,'disp')
 *
 * Description
 *
 * A = a MATLAB numeric array, char array, or logical array.
 *
 * B = the variable A converted into half precision floating point bit pattern.
 *     The bit pattern will be returned as a uint16 class variable. The values
 *     displayed are simply the bit pattern interpreted as if it were an unsigned
 *     16-bit integer. To see the halfprecision values, use the 'disp' option, which
 *     simply converts the bit patterns into a single class and then displays them.
 *
 * C = the half precision floating point bit pattern in B converted into class S.
 *     B must be a uint16 or int16 class variable.
 *
 * S = char string naming the desired class (e.g., 'single', 'int32', etc.)
 *     If S = 'disp', then the floating point bit values are simply displayed.
 *
 * Examples
 * 
 * >> a = [-inf -1e30 -1.2 NaN 1.2 1e30 inf]
 * a =
 * 1.0e+030 *
 *     -Inf   -1.0000   -0.0000       NaN    0.0000    1.0000       Inf
 *
 * >> b = halfprecision(a)
 * b =
 * 64512  64512  48333  65024  15565  31744  31744
 *
 * >> halfprecision(b,'disp')
 *     -Inf      -Inf   -1.2002       NaN    1.2002       Inf       Inf
 *
 * >> halfprecision(b,'double')
 * ans =
 *     -Inf      -Inf   -1.2002       NaN    1.2002       Inf       Inf
 *
 * >> 2^(-24)
 * ans =
 * 5.9605e-008
 *
 * >> halfprecision(ans)
 * ans =
 *     1
 *
 * >> halfprecision(ans,'disp')
 * 5.9605e-008
 *
 * >> 2^(-25)
 * ans =
 * 2.9802e-008
 *
 * >> halfprecision(ans)
 * ans =
 *     1
 *
 * >> halfprecision(ans,'disp')
 * 5.9605e-008
 *
 * >> 2^(-26)
 * ans =
 *  1.4901e-008
 *
 * >> halfprecision(ans)
 * ans =
 *     0
 *
 * >> halfprecision(ans,'disp')
 *    0
 *
 * Note that the special cases of -Inf, +Inf, and NaN are handled correctly.
 * Also, note that the -1e30 and 1e30 values overflow the half precision format
 * and are converted into half precision -Inf and +Inf values, and stay that
 * way when they are converted back into doubles.
 *
 * For the denormalized cases, note that 2^(-24) is the smallest number that can
 * be represented in half precision exactly. 2^(-25) will convert to 2^(-24)
 * because of the rounding algorithm used, and 2^(-26) is too small and underflows
 * to zero.
 *
 ********************************************************************************/

#include "haikuJ2C.h"

#if (HAIKU_Mode==HAIKU_16_32 || HAIKU_Mode==HAIKU_16_16)
 /*****************************
http://en.wikipedia.org/wiki/Half-precision_floating-point_format
The IEEE 754 standard specifies a binary16 as having:
 Sign bit: 1 bit
 Exponent width: 5 bits
 Significant precision: 11 (10 explicitly stored)

 The minimum strictly positive (subnormal) value is 2^-24 	~ 5.96 * 10^-8.
 The minimum positive normal value is 2^-14 				~ 6.10 * 10^-5.
 The maximum representable value is (2-2^-10) * 2^15 		= 65504.

typedef union  { jfloatldc f; jfloatraw fl;} ldc_jfloat_t;
*/


/**
 * Conversion of Float to Half-Float.
Intuitively, conversion from float to half-float is a slightly more complex process, due to the need to
handle overflows and underflows. As before, there is a compact and simple version which is pretty
straight-forward:
h = ((f>>16)&0x8000)|((((f&0x7f800000)-0x38000000)>>13)&0x7c00)|((f>>13)&0x03ff)
In a nutshell, first the sign bit is shifted and masked, then the exponent is masked off, and the biascorrection
subtracted; the result is shifted, and finally the mantissa is shifted and masked off. All the
pieces are then assembled together.
The above expression suffers from a few fatal flaws, however. It doesn't handle zero, Infinity, NaN, or
small float numbers which are only representable as subnormal half-floats.
Proper conversion must handle the following cases:
1. Really small numbers and zero should map to a half-float value of zero.
2. Small numbers should convert to subnormal half-float values.
3. Regular magnitude numbers should just lose some precision.
4. Large numbers should map to half-float Infinity.
5. Infinity and NaNs should remain Infinity and NaNs in their half-float representation.
Ideally, it would be nice if the conversions were round-trip safe, i.e. half-float -> float -> half-float
would yield the same identical number one started out with.
	 */
jfloat floatToInt16(float ff) {		
		int32_t xs,xe,xm;
		int16_t hs, hes,hm,he;
		ldc_jfloat_t c={ff};
		int32_t bits=c.fl;

	    if( (bits & 0x7FFFFFFF) == 0 ) {  // Signed zero
	        return  (bits >> 16);  // Return the signed zero
	    } else { // Not zero
	        xs = bits & 0x80000000;  // Pick off sign bit
	        xe = bits & 0x7F800000;  // Pick off exponent bits
	        xm = bits & 0x007FFFFF;  // Pick off mantissa bits
	        if( xe == 0 ) {  // Denormal will underflow, return a signed zero
	        	return  (xs >> 16);
	        } else if( xe == 0x7F800000 ) {  // Inf or NaN (all the exponent bits are set)
	            if( xm == 0 ) { // If mantissa is zero ...
	            	return  ((xs >> 16) | 0x7C00); // Signed Inf
	            } else {
	            	return   0xFE00; // NaN, only 1st mantissa bit set
	            }
	        } else { // Normalized number
	            hs = (xs >> 16); // Sign bit
	            hes = ((xe >> 23) - 127 + 15); // Exponent unbias the single, then bias the halfp
	            if( hes >= 0x1F ) {  // Overflow
	            	return  ((xs >> 16) | 0x7C00); // Signed Inf
	            } else if( hes <= 0 ) {  // Underflow
	                if( (14 - hes) > 24 ) {  // Mantissa shifted all the way off & no rounding possibility
	                    hm = 0;  // Set mantissa to zero
	                } else {
	                    xm |= 0x00800000;  // Add the hidden leading bit
	                    hm = (xm >> (14 - hes)); // Mantissa
	                    if( ((xm >> (13 - hes)) & 0x00000001)!=0 ) // Check for rounding
	                        hm += 1; // Round, might overflow into exp bit, but this is OK
	                }
	                return  (hs | hm); // Combine sign bit and mantissa bits, biased exponent is zero
	            } else {
	                he = (hes << 10); // Exponent
	                hm = (xm >> 13); // Mantissa
	                if( (xm & 0x00001000)!=0 ) // Check for rounding
	                	return  ((hs | he | hm) +  1); // Round, might overflow to inf, this is OK
	                else
	                	return  (hs | he | hm);  // No rounding
	            }
	        }
	    }
	}
/**
 * Conversion of Half Float to Float.
Conversion of half float to float is, in principle, simple: copy the sign bit, subtract the half-float bias
(15) from the exponent and add the single-precision float bias (127), and append 13 zero-bits to the
mantissa. In C code:
f = ((h&0x8000)<<16) | (((h&0x7c00)+0x1C000)<<13) | ((h&0x03FF)<<13)
The above expression however only works for "normal" half-float numbers; it does not work for
special cases such as zero or subnormal (denormal) numbers.
A correct conversion must treat all the special cases:
1. Zero (and negative zero).
2. Subnormal half-float values should map to their corresponding float values; since floats have a
larger range that half-floats, subnormal half-float values will map to proper float values.
3. The half float value Infinity should map to a float value of Infinity.
4. Half float NaN (not a number) values should map to float NaN values.
	 * @param bits
	 * @return
	 */
float int16ToFloat(uint16_t bits) {
	ldc_jfloat_t c;
	int32_t s=bits & 0x8000;
	int32_t e=((bits>>10) & 0x1f)-15;
	int32_t m=bits & 0x03ff;
	if( (bits & 0x7FFF) == 0 ) {  // Signed zero
		c.fl=(s<<16); // Return the signed zero
	} else { // Not zero
		if( e == -15 ) {  // Denormal will convert to normalized
			e++; // The following loop figures out how much extra to adjust the exponent
			do {
				e--;
				m <<= 1;
			} while( (m & 0x0400) == 0 ); // Shift until leading bit overflows into exponent bit
			m &= 0x03ff;
		} else if( (bits & 0x7C00)==0x7C00 ) {  // Inf or NaN (all the exponent bits are set)
			e=128; // 0x7F800000 Signed Inf
			if( m != 0 ) { // If mantissa is not zero ...
				//0xFFC00000u; NaN, only 1st mantissa bit set
				s=0x8000;
				m=0x200;
			}
		}
		c.fl=((s<<16) | ((e+127)<<23) | (m<<13));
	}
	return c.f;
}
#endif
