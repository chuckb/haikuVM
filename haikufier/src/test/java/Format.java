import java.util.Formatter;


public class Format {
	private static final long SCALE = -1000000000;
	private static final int SSCALE = -1000000000;
	private static final int SSSCALE = -10000;
	
	
	private static String nformat(float f) {
		String res="";
		if (f<0) {
			res="-";
			f=-f;
		}
		short m=1;
		float scale=10;
		while (scale<=f) {scale*=10; m++; }
		f/=scale/10;
		scale=0.0000005f;
		for (short i = 0; m>-1 || f>scale; i++, m--) {
			//System.out.println("f="+f);
			if (m==0) res+=".";
			res+=(char)('0'+(short)(f+0.000005f)%10);
			f=10*(f-(short)(f+0.000005f)%10);
			scale*=10;
		}
		return res;
	}

	
	private static float pow10(int i) {
		float p=1;
		for(;i>0; i--) p*=10;
		return p;
	}

	private static String sformat(float f) {
		long l=Float.floatToRawIntBits(f);
		short sign=(l & 0x80000000)==0?(short)1:(short)-1;
		short e=(short)((l & 0x7f800000) >>23);
//		int m=l & 0x007fffff;
		final float log10_2=0.3010299f; //Math.log10(2);
		short magnitude=(short)((e-127)*log10_2);
		short leading=0;
		int scaled; 
		if (l==0 || magnitude<0) {
			magnitude=-1;
			leading=1;
			scaled = (int)(f*SSCALE); 
		} else {
			float scaling_factor=SSCALE/pow10(magnitude+1);
			scaled = (int)(f*scaling_factor); 
		}

		return sformat(sign, sign*scaled, leading, (short)(magnitude+1), (short)6); // -scaled (<0) because abs(-2147483648) > abs(2147483647)   
	}
	
	/**
	 * 
	 * @param sign
	 * @param scaled must be <0 because abs(-2147483648) > abs(2147483647)
	 * @param magnitude
	 * @param post
	 * @return
	 */
	private static String sformat(short sign, int scaled, short leading, short magnitude, short post) {
		String res="";
		int scale=SSCALE;
		if (sign<0) res="-";
		for (short i = 0; i < magnitude || post>0; i++) {
			short d=0;
			if (scale<0) {
				d=(short)((scaled/scale)%10);
				if (d<0) {
					d=(short)-d;
				}
			}
			if (!(leading==0 && d==0) || i==magnitude) {
				leading=-1;
				res+=(char)('0'+d);
			}
			if (i==magnitude && post>0) {
				leading=-1;
				res+='.';
			}
			if (i>magnitude) post--;
			scale/=10;
		}
		
		return res;
	}

	private static String ssformat(float f) {
		long l=Float.floatToRawIntBits(f);
		short sign=(l & 0x80000000)==0?(short)1:(short)-1;
		short e=(short)((l & 0x7f800000) >>23);
//		int m=l & 0x007fffff;
		final float log10_2=0.3010299f; //Math.log10(2);
		short magnitude=(short)((e-127)*log10_2);
		short leading=0;
		short scaled; 
		if (l==0 || magnitude<0) {
			magnitude=-1;
			leading=1;
			scaled = (short)(f*SSSCALE); 
		} else {
			float scaling_factor=SSSCALE/pow10(magnitude+1);
			scaled = (short)(f*scaling_factor); 
		}

		return ssformat(sign, (short)(sign*scaled), leading, (short)(magnitude+1), (short)6); // -scaled (<0) because abs(-2147483648) > abs(2147483647)   
	}
	
	/**
	 * 
	 * @param sign
	 * @param scaled must be <0 because abs(-2147483648) > abs(2147483647)
	 * @param magnitude
	 * @param post
	 * @return
	 */
	private static String ssformat(short sign, short scaled, short leading, short magnitude, short post) {
		String res="";
		int scale=SSSCALE;
		if (sign<0) res="-";
		for (short i = 0; i < magnitude || post>0; i++) {
			short d=0;
			if (scale<0) {
				d=(short)((scaled/scale)%10);
				if (d<0) {
					d=(short)-d;
				}
			}
			if (!(leading==0 && d==0) || i==magnitude) {
				leading=-1;
				res+=(char)('0'+d);
			}
			if (i==magnitude && post>0) {
				leading=-1;
				res+='.';
			}
			if (i>magnitude) post--;
			scale/=10;
		}
		
		return res;
	}

	
	private static String format(float f) {
		long l=Float.floatToRawIntBits(f);
		int sign=(l & 0x80000000)==0?1:-1;
		long e=(int)((l & 0x7f800000) >>23);
//		int m=l & 0x007fffff;
		final float log10_2=0.3010299f; //Math.log10(2);
		int magnitude=(int)((e-127)*log10_2);
		int leading=0;
		int scaled; 
		if (l==0 || magnitude<0) {
			magnitude=-1;
			leading=1;
			scaled = (int)(f*SCALE); 
		} else {
			float scaling_factor=SCALE/pow10(magnitude+1);
			scaled = (int)(f*scaling_factor); 
		}

		return format(sign, sign*scaled, leading, magnitude+1, 6); // -scaled (<0) because abs(-2147483648) > abs(2147483647)   
	}
	
	/**
	 * 
	 * @param sign
	 * @param scaled must be <0 because abs(-2147483648) > abs(2147483647)
	 * @param magnitude
	 * @param post
	 * @return
	 */
	private static String format(int sign, long scaled, int leading, int magnitude, int post) {
		String digits[]={"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"}; //, "10", "11", "12", "13", "14", "15", "16", "17", "18", "19"};
		String res="";
		if (sign<0) res="-";
		for (int i = 0; i < magnitude || post>0; i++) {
			int d=(int)(scaled/SCALE);
			if (!(leading==0 && d==0) || i==magnitude) {
				leading=-1;
				res+=digits[(int)d];
			}
			if (i==magnitude && post>0) {
				leading=-1;
				res+=".";
			}
			if (i>magnitude) post--;
			scaled=10*(scaled -d*SCALE);
		}
		
		return res;
	}

	private static String randomFloat() {
		int len=(int)(Math.random()*36)+1;
		int p=(int)(Math.random()*len);
		String fstr="";
		if (Math.random()<0.5) fstr="-";
		for (int i = 0; i < len; i++) {
			if (p==i) fstr+=".";
			int d=(int)(Math.random()*10);
			fstr+="0123456789".charAt(d);
		}
		return fstr;
	}
	
	


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (Float.POSITIVE_INFINITY==Float.POSITIVE_INFINITY) {
			System.out.println("equal");
		}
		if (Float.POSITIVE_INFINITY!=Float.NEGATIVE_INFINITY) {
			System.out.println("not equal");
		}
		testHalfFloats();
		//haikuTest();
	}

	public static void javaTest() {
		ftest("0");
		ftest("0.64753524");
		ftest("0.012345");
		ftest("12.345");
		ftest("123.45");
		ftest("9.87654321");
		ftest("16554473551681.279623420");
		ftest("6554473551681.279623420");
		itest(""+Integer.MAX_VALUE);
		itest(""+Integer.MIN_VALUE);
		itest("123");
		while(true) {
			String fstr=randomFloat();
			ftest(fstr);
		}

	}

	public static void haikusTest() {
		fsstest("0.64753524");
		fsstest("0.012345");
		fsstest("12.345");
		fsstest("123.45");
		fsstest("9.87654321");
		fsstest("16554473551681.279623420");
		fsstest("6554473551681.279623420");
		fsstest("0");
		while(true) {
			String fstr=randomFloat();
			fsstest(fstr);
		}

	}

	public static void haikuTest() {
		fstest("0");
		fstest("1");
		fstest("3");
		fstest("12");
		fstest("21");
		fstest("1024.0");
		fstest("0.64753524");
		fstest("0.012345");
		fstest("12.345");
		fstest("123.45");
		fstest("9.87654321");
		fstest("16554473551681.279623420");
		fstest("6554473551681.279623420");
		istest(""+Integer.MIN_VALUE);
		istest(""+(Integer.MIN_VALUE+1));
		istest(""+Integer.MAX_VALUE);
		istest(""+Integer.MIN_VALUE);
		istest("123");
		while(true) {
			String fstr=randomFloat();
			fstest(fstr);
		}

	}

	private static void fsstest(String string) {
		float f=Float.parseFloat(string);
		System.out.printf("%s\n%f\n%s\n########\n", string, f, ssformat(f));
		if (f!=Float.parseFloat(ssformat(f))) {
			System.out.println();
		}
	}

	private static void fstest(String string) {
		float f=Float.parseFloat(string);
		System.out.printf("%s\n%f\n%s\n########\n", string, f, nformat(f));
		if (f!=Float.parseFloat(nformat(f))) {
			System.out.println();
		}
	}

	private static void ftest(String string) {
		float f=Float.parseFloat(string);
		System.out.printf("%s\n%f\n%s\n########\n", string, f, format(f));
		if (f!=Float.parseFloat(format(f))) {
			System.out.println();
		}
	}

	private static void itest(String string) {
		int i=Integer.parseInt(string);
		int s=i>=0?1:-1;
		System.out.printf("%s\t%d\t%s\n", ""+i, i, format(s, -s*i, 0, 10, 0));
	}

	private static void istest(String string) {
		int i=Integer.parseInt(string);
		int s=i>=0?1:-1;
		int v=i;
		if (v>0) v=-i;
		System.out.printf("%s\t%d\t%s\n", ""+i, i, sformat((short)s, v, (short)0, (short)10, (short)0));
	}
	
	/*****************************
	http://en.wikipedia.org/wiki/Half-precision_floating-point_format
	The IEEE 754 standard specifies a binary16 as having:
	 Sign bit: 1 bit
	 Exponent width: 5 bits
	 Significant precision: 11 (10 explicitly stored)

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
	static short xfloatToInt16(float ff) {
		int bits=Float.floatToRawIntBits(ff);
		int s=(bits>>16) & 0x8000;
		int e=((bits>>23) & 0xff)-127;
		int m=((bits>>13)&0x03ff);
		if (e==-127) {
			e=-15;
		} else {
			if (e+15>31) {
				e=0x1f-15; // +-infinit
			} else if (e<-15) { // underflow
				if (e<-15-10) {
					m=0;
				} else {
	                m |= 0x0400;  // Add the hidden leading bit
					m>>=(-15-e);
				}
				e=-15;
			}
		}
		return (short) ((s) | ((e+15)<<10) | (m));
	}
	
	static short floatToInt16(float ff) {		
		int xs,xe,xm;
		short hs, hes,hm,he;
		int bits=Float.floatToRawIntBits(ff);
	    if( (bits & 0x7FFFFFFF) == 0 ) {  // Signed zero
	        return  (short) (bits >> 16);  // Return the signed zero
	    } else { // Not zero
	        xs = bits & 0x80000000;  // Pick off sign bit
	        xe = bits & 0x7F800000;  // Pick off exponent bits
	        xm = bits & 0x007FFFFF;  // Pick off mantissa bits
	        if( xe == 0 ) {  // Denormal will underflow, return a signed zero
	        	return  (short)  (xs >> 16);
	        } else if( xe == 0x7F800000 ) {  // Inf or NaN (all the exponent bits are set)
	            if( xm == 0 ) { // If mantissa is zero ...
	            	return  (short)  ((xs >> 16) | 0x7C00); // Signed Inf
	            } else {
	            	return  (short)  0xFE00; // NaN, only 1st mantissa bit set
	            }
	        } else { // Normalized number
	            hs = (short) (xs >> 16); // Sign bit
	            hes = (short)(((int)(xe >> 23)) - 127 + 15); // Exponent unbias the single, then bias the halfp
	            if( hes >= 0x1F ) {  // Overflow
	            	return  (short) ((xs >> 16) | 0x7C00); // Signed Inf
	            } else if( hes <= 0 ) {  // Underflow
	                if( (14 - hes) > 24 ) {  // Mantissa shifted all the way off & no rounding possibility
	                    hm = 0;  // Set mantissa to zero
	                } else {
	                    xm |= 0x00800000;  // Add the hidden leading bit
	                    hm = (short) (xm >> (14 - hes)); // Mantissa
	                    if( ((xm >> (13 - hes)) & 0x00000001)!=0 ) // Check for rounding
	                        hm += 1; // Round, might overflow into exp bit, but this is OK
	                }
	                return  (short) (hs | hm); // Combine sign bit and mantissa bits, biased exponent is zero
	            } else {
	                he = (short) (hes << 10); // Exponent
	                hm = (short) (xm >> 13); // Mantissa
	                if( (xm & 0x00001000)!=0 ) // Check for rounding
	                	return  (short) ((hs | he | hm) +  1); // Round, might overflow to inf, this is OK
	                else
	                	return  (short) (hs | he | hm);  // No rounding
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
	static float int16ToFloat(short bits) {
		int s=bits & 0x8000;
		int e=((bits>>10) & 0x1f)-15;
		int m=bits & 0x3ff;
		if (e==-15) {
			if (m==0) {
				e=-127;
			} else {
				while((m&0x0400)==0) {
					m<<=1;
					e--;
				}
				e++;
				m&=0x3ff;
			}
		} else {
			if (e+15==0x1f) e=0xff-127; // +-infinit
		}
		return Float.intBitsToFloat((s<<16) | ((e+127)<<23) | (m<<13));
	}


	static float float2Float(float in) {
		short hf=floatToInt16(in);
		float out=int16ToFloat(hf);
		double err=Math.abs(Math.abs(in-out)/in);
		System.out.printf("%g -> %g [%s, %s, %s] (%g)\n", in, out, showFloat(in), showFloat(hf), showFloat(out), err);
		if (out!=0 && !Float.isInfinite(out)&& err>0.0019) {
			System.err.println("problem");
		}
		return out;
	}
	
	private static String showFloat(float ff) {
		int bits=Float.floatToRawIntBits(ff);
		int e=((bits>>23) & 0xff)-127;
		int m=(bits&0x007fffff);

		String str="00000000000000000000000000000000"+Integer.toBinaryString(bits);
		str=str.substring(str.length()-32, str.length());
		
		return str.substring(0,1)+"."+str.substring(1,9)+"."+str.substring(9,32)+" ("+e+", "+m+")";
	}

	private static String showFloat(short v) {
		int bits=v;
		int e=((bits>>10) & 0x1f)-15;
		int m=bits & 0x3ff;
		
		String str="0000000000000000"+Integer.toBinaryString(bits);
		str=str.substring(str.length()-16, str.length());
		
		return str.substring(0,1)+"."+str.substring(1,6)+"."+str.substring(6,16)+" ("+e+", "+m+")";
	}


	static void testHalfFloats() {
		float[] list = new float[] {100000, 201648, 0.00001f, 0.000000001f, -1.0f/0.0f, 1.0f/0.0f, 0.0f/0.0f, 0.1f, 0.01f, 0.001f, 0, -1, 1};
		for (int i = 0; i < list.length; i++) {
			float2Float(list[i]);
		}
		for (int i = 0; i < 100000; i++) {
			double a = Math.random();
			double b = Math.random();
			float f=(float)(a/b);
			float2Float(f);
		}
	}

}
