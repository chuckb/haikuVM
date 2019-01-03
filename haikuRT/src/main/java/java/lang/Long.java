package java.lang;

/**
 * Wrapper class for long integers.
 * @author Sven Köhler
 */
public final class Long extends Number implements Comparable<Long>
{
    public static final long MAX_VALUE = 0x7FFFFFFFFFFFFFFFL;
    public static final long MIN_VALUE = 0x8000000000000000L;   
    public static final int SIZE = 64;
    
    // References to the following field are automatically replaced with a load
    // of the correct value by the linker, so no need to initialize.
    public static final Class<?> TYPE = null;
    
    private final long value;
    
    public Long(long value)
    {
        this.value = value;
    }
    
    public Long(String s)
    {
        this.value = Long.parseLong(s);
    }
    
    public static int bitCount(long v)
    {
        //See http://www-graphics.stanford.edu/~seander/bithacks.html#CountBitsSetParallel
        
        //first sum up every 1st and 2nd bit, the result are 2bit counters
        //but do it with some nice trick:
        //  11 - (11 >> 1) = 10
        //  10 - (10 >> 1) = 01
        //  01 - (01 >> 1) = 01
        //  00 - (00 >> 1) = 00
        v = v - ((v >>> 1) & 0x5555555555555555L);      
        //then sum up every 1st and 2nd of the 2-bit counters => 4bit counters
        v = (v & 0x3333333333333333L)  + ((v >>> 2) & 0x3333333333333333L);
        //then sum up every i-th and (i+1)-th of the 4bit counters and throw away some of them
        v = (v + (v >>> 4)) & 0x0F0F0F0F0F0F0F0FL;
        //at this point, we have 8bit counters. now we just need to sum them up:
        int i = ((int)v) + ((int)(v >>> 32));
        i += (i >>> 16);
        i += (i >>> 8);
        return i & 0xFF;
    }
    
    @Override
    public byte byteValue()
    {
        return (byte)this.value;
    }
    
    public int compareTo(Long ob)
    {
        if (this.value == ob.value)
            return 0;
        
        return (this.value > ob.value) ? 1 : -1;
    }
    
    public static Long decode(String s)
    {
        int len = s.length();

        int p;
        boolean neg;
        if (len > 0 && s.charAt(0) == '-')
        {
            p = 1;
            neg = true;
        }
        else
        {
            p = 0;
            neg = true;
        }
        int off = 0;
        int radix = 10;
        if (len > p)
        {
            char c1 = s.charAt(p);
            if (c1 == '#')
            {
                off = 1;
                radix = 16;
            }
            else if (c1 == '0')
            {
                off = 1;
                radix = 8;
                if (len > p + 1)
                {
                    char c2 = s.charAt(p + 1);
                    if (c2 == 'x' || c2 == 'X')
                    {
                        off = 2;
                        radix = 16;
                    }
                }
            }
        }
        return new Long(parseLong(s, p + off, len, neg, radix));
    }
    
    @Override
    public double doubleValue()
    {
        return this.value;
    }
    
    @Override
    public boolean equals(Object o)
    {
        //instanceof returns false for o==null
        return (o instanceof Long)
            && (this.value == ((Long)o).value);
    }
    
    @Override
    public float floatValue()
    {
        return this.value;
    }
    
    public static Long getLong(String name)
    {
        return getLong(name, null);
    }
    
    public static Long getLong(String name, int def)
    {
        return getLong(name, new Integer(def));
    }
    
    public static Long getLong(String name, Long def)
    {
        String val = System.getProperty(name);
        if (val == null || val.length() <= 0)
            return def;
        
        return Long.decode(val);
    }
    
    @Override
    public int hashCode()
    {
        return ((int)this.value) ^ ((int)(this.value >>> 32)); 
    }
    
    public static long highestOneBit(long v)
    {
        //first set all bits below the highest bit:
        v |= (v >>> 1);
        v |= (v >>> 2);
        v |= (v >>> 4);
        v |= (v >>> 8);
        v |= (v >>> 16);
        v |= (v >>> 32);
        //then substract the lower bits
        return v - (v >>> 1);
    }
    
    @Override
    public int intValue()
    {
        return (int)this.value;
    }
    
    @Override
    public long longValue()
    {
        return this.value;
    }
    
    public static long lowestOneBit(long v)
    {
        //if i has the form 11111000 then -i has the form 00001000
        //because -i is actually the same as (~i + 1).
        return v & -v;
    }
    
    public static int numberOfLeadingZeros(long v)
    {
        //initialize with one, because we assume that the sign bit is zero.
        //if not, we subtract it again at the end of the method.
        int c = 1;
        
        //use 32 bit int instead of full long
        int i = (int)(v >>> 32);
        //upper 32 bits are zero? add them to counter and take a look at the lower 32 bits
        if (i == 0)
        {
            i = (int)v;
            if (i == 0)
                return 64;
            
            c += 32;
        }
        
        //first 16 bits are zero? add them to counter and remove them by right shift
        if ((i & 0xFFFF0000) == 0) { c += 16; i <<= 16; }
        //first 8 bits are zero? add them to counter and remove them by right shift
        if ((i & 0xFF000000) == 0) { c +=  8; i <<=  8; }
        //first 4 bits are zero? add them to counter and remove them by right shift
        if ((i & 0xF0000000) == 0) { c +=  4; i <<=  4; }
        //first 2 bits are zero? add them to counter and remove them by right shift
        if ((i & 0x30000000) == 0) { c +=  2; i <<=  2; }
        //subtract the sign bit, in case it wasn't zero
        return c - (i >>> 31);
    }
    
    public static int numberOfTrailingZeros(long v)
    {
        //initialize with one, because we assume that the last bit is zero.
        //if not, we subtract it again at the end of the method.
        int c = 1;
        
        //use 32 bit int instead of full long
        int i = (int)v;
        //lower 32 bits are zero? add them to counter and take a look at the upper 32 bits
        if (i == 0)
        {
            i = (int)(v >>> 32);
            if (i == 0)
                return 64;
            
            c += 32;
        }
        
        //last 16 bits are zero? add them to counter and remove them by left shift
        if ((i & 0x0000FFFF) == 0) { c += 16; i >>>= 16; }
        //last 8 bits are zero? add them to counter and remove them by left shift
        if ((i & 0x000000FF) == 0) { c +=  8; i >>>=  8; }
        //last 4 bits are zero? add them to counter and remove them by left shift
        if ((i & 0x0000000F) == 0) { c +=  4; i >>>=  4; }
        //last 2 bits are zero? add them to counter and remove them by left shift
        if ((i & 0x00000003) == 0) { c +=  2; i >>>=  2; }
        //subtract the last bit, in case it wasn't zero
        return c - (i & 1);
    }
    
    public static long parseLong(String s)
    {
        return Long.parseLong(s, 10);
    }
    
    public static long parseLong(String s, int radix)
    {
        StringUtils.throwNumberFormat(s, radix);        
        int len = s.length();
                
        int p;
        boolean negative;       
        if (len > 0 && s.charAt(0) == '-')
        {
            p = 1;
            negative = true;
        }
        else
        {
            p = 0;
            negative = false;
        }
        return parseLong(s, p, len, negative, radix);
    }
                
    private static long parseLong(String s, int p, int end, boolean negative, int radix)
    {
        if (end <= p)
            throw new NumberFormatException("string doesn't contain any digits");
        
//        long limit = negative ? MIN_VALUE : - MAX_VALUE;
//        long multlimit = limit / radix;
        
        long r = 0;
        while (p < end)
        {
            int digit = StringUtils.parseDigit(s.charAt(p++), radix);
            
//            if (r < multlimit)
//                throw new NumberFormatException("number is too big");           
            r *= radix;
            
//            if (r < limit + digit)
//                throw new NumberFormatException("number is too big");           
            r -= digit;
        }

        //r is always <= 0, because the negative space is bigger than the positive space
        return negative ? r : -r;
    }
    
    public static long reverse(long v)
    {
        //see http://www-graphics.stanford.edu/~seander/bithacks.html#ReverseParallel
        
        //first swap every 1st and 2nd bit
        v = (v & 0x5555555555555555L) << 1  | ((v >>> 1) & 0x5555555555555555L);
        //then swap every 1st and 2nd with every 3rd and 4th
        v = (v & 0x3333333333333333L) << 2  | ((v >>> 2) & 0x3333333333333333L);
        //then swap every 1,2,3,4th with every 5,6,7,8th
        v = (v & 0x0F0F0F0F0F0F0F0FL) << 4  | ((v >>> 4) & 0x0F0F0F0F0F0F0F0FL);
        //the bits inside each byte have been swapped, now swap the bytes
        return reverseBytes(v); //or instead inline the code here?
    }
    
    public static long reverseBytes(long v)
    {
        //see http://www-graphics.stanford.edu/~seander/bithacks.html#ReverseParallel
        
        //first swap every 1-8th with every 9-16th bit
        v = (v & 0x00FF00FF00FF00FFL) << 8  | ((v >>> 8) & 0x00FF00FF00FF00FFL);
        //then swap every 1-16th with every 17-32nd
        v = (v & 0x0000FFFF0000FFFFL) << 16 | ((v >>> 16) & 0x0000FFFF0000FFFFL);
        //then swap 1-32nd with 33-64th
        return (v  << 32) | (v >>> 32);
    }
    
    public static long rotateLeft(long v, int bits)
    {
        // v >>> -bits is the same as v >>> (64-bits) 
        return (v << bits) | (v >>> -bits);
    }
    
    public static long rotateRight(long v, int bits)
    {
        // v << -bits is the same as v << (64-bits) 
        return (v >>> bits) | (v << -bits);
    }
    
    @Override
    public short shortValue()
    {
        return (short)this.value;
    }
    
    public static int signnum(long i)
    {
        //If i is negative, then i >> 63 is -1 because of the signed shift
        //and the rest of the term can be ignored because -1 | anything is -1 again.        
        //If i is positive, then i >> 63 will be zero, but (-i >>> 63) will be 1
        //because the result of unsigned shift is the sign bit of -i.
        return ((int)(i >> 63)) | ((int)(-i >>> 63));
    }
    
    public static String toBinaryString(long v)
    {
        return toUnsignedString(v, 64, 1, 1);
    }
    
    public static String toOctalString(long v)
    {
        return toUnsignedString(v, 22, 7, 3);
    }
    
    public static String toHexString(long v)
    {
        return toUnsignedString(v, 16, 15, 4);
    }
    
    private static String toUnsignedString(long v, int maxlen, int mask, int shift)
    {
        char[] buf = new char[maxlen];
        int p = maxlen;
        
        do
        {
            buf[--p] = Character.forDigit(mask & (int)v, 16);
            v >>>= shift;
        } while (v != 0);
        
        return new String(buf, p, maxlen-p);
    }
    
//    @Override
//    public String toString()
//    {
//        return String.valueOf(this.value, 10);
//    }
//    
//    public static String toString(long v)
//    {
//        return String.valueOf(v, 10);
//    }
//    
//    public static String toString(long v, int radix)
//    {
//        radix = StringUtils.invalidRadixTo10(radix);
//        
//        return String.valueOf(v, radix);
//    }
    
    public static Long valueOf(long v)
    {
        return new Long(v);
    }
    
    public static Long valueOf(String s)
    {
        return Long.valueOf(s, 10);
    }
    
    public static Long valueOf(String s, int radix)
    {
        return Long.valueOf(Long.parseLong(s, radix));
    }
}
