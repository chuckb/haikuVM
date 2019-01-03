package java.lang;

public class String implements CharSequence, Comparable<String> {
	final char characters[]; // not private because it's used in StringBuilder

	public String()
    {
        characters = new char[0];
    }

    private String(int len)
    {
        characters = new char[len];
    }

	public String(char[] bytes) {
		characters = new char[bytes.length];
		for (int i = 0; i < bytes.length; i++) {
			characters[i] = bytes[i];
		}
		// System.arraycopy(bytes, 0, chars, 0, bytes.length);
	}

	public String(char c) {
		characters = new char[]{c};
	}

	public String(String string) {
		this.characters = string.characters;
	}

	public String(char[] buf, int offset, int len) {
        this(len);
        for (int i = 0; i < len; i++) {
            characters[i] = buf[offset+i];
        }
    }

    public int length() {
		return characters.length;
	}

	public char charAt(int index) {
		return characters[index];
	}

	/**
	 * Returns the string representation of the <code>Object</code> argument.
	 *
	 * @param obj
	 *            an <code>Object</code>.
	 * @return if the argument is <code>null</code>, then a string equal to
	 *         <code>"null"</code>; otherwise, the value of
	 *         <code>obj.toString()</code> is returned.
	 * @see java.lang.Object#toString()
	 */
	public static String valueOf(Object obj) {
		return (obj == null) ? "null" : obj.toString();
	}

	public static String valueOf(boolean b) {
		return (b) ? "true" : "false";
	}

	public static String valueOf(char c) {
		return new String(c);
	}

	public static String valueOf(float val) {
		return valueOf((double)val);
	}

	/**
	 * On RCX (32/32) we had errors:
	 * (14 * 19) / (14 + 19) -> 7.1060606 instead of 8.060606
	 * ( 9 * 11) / ( 9 + 11) -> 4.9410    instead of 4.9499998
	 *
	 * @param d
	 * @return
	 */
	public static String valueOf(double d) {
		if (d != d)	return "NaN";
		if (d == Double.POSITIVE_INFINITY) return "Infinity";
		if (d == Double.NEGATIVE_INFINITY) return "-Infinity";
		String res = "";
		double scale = 1;
		if (d < 0) {
			res = "-";
			d = -d;
		}
		int m = 1;
		while (d/scale >= 10) {
			scale *= 10;
			m++;
		}
		while (m>-6 && (m > -1 || d > 0)) {
			if (m == 0)
				res += ".";
			int z= (int)(d/scale);
			if (z==10) {
				z--;
			} else if (z<9 && (int)((d-z*scale)/(scale/10))==10) {
				z++;
			}
			d-=z*scale;
			scale /= 10;
			res += z;
			m--;
		}
		return res;
	}

	public String toString() {
		return this;
	}

	public static String valueOf(long v) {
		String res = "";
		long s=v;
		if (v > 0) v=-v;
		while (true) {
			res = (char) ('0' - (int)(v % 10)) + res;
			v /= 10;
            if (v==0) {
                if (s < 0)
                    return "-" + res;
                return res;
            }
        }
	}

	public static String valueOf(int v) {
		String res = "";
		int s=v;
		if (v > 0) v=-v;
		while (true) {
			res = (char) ('0' - v % 10) + res;
			v /= 10;
			if (v==0) {
		        if (s < 0)
		            return "-" + res;
		        return res;
			}
		}
	}

    /**
     * For use by {@link Integer}
     */
    static String valueOf(int i, int radix)
    {
        int len = StringUtils.exactStringLength(i, radix);
        String r = new String(len);
        StringUtils.getIntChars(r.characters, len, i, radix);
        return r;
    }


	/**
	 * Compares the String with an Object
	 *
	 * @return true if the String is equal to the object, false otherwise
	 */
	@Override
	public boolean equals(Object other)
	{
		if (other == this)
			return true;
		// also catches other == null
		if (!(other instanceof String))
			return false;

		String os = (String)other;
		if (os.characters.length != characters.length)
			return false;

		for (int i = 0; i < characters.length; i++)
		{
			if (characters[i] != os.characters[i])
				return false;
		}

		return true;
	}

	/**
	 * Special version of hash that returns the same value the same String
	 * values
	 */
	@Override
	public int hashCode()
	{
		// computation is compatible with JDK, otherwise Java 7 String-switch-case,
		// which is based on hashCode, will fail.
		int h = 0;
		for (int i = 0; i < this.characters.length; i++)
		{
			h = 31 * h + this.characters[i];
		}
		return h;
	}

	/**
	 * Find the index of a character.
	 *
	 * @param ch The character to find.
	 * @return The index of the character. -1 means it wasn't found.
	 */
	public int indexOf(int ch)
	{
		return indexOf(ch, 0);
	}

	/**
	 * Returns the index within this string of the first occurrence of the
	 * specified character, starting the search at the specified index.
	 *
	 * @param ch a character (Unicode code point).
	 * @param fromIndex the index to start the search from.
	 * @return the index of the first occurrence of the character in the
	 *         character sequence represented by this object that is greater
	 *         than or equal to <code>fromIndex</code>, or <code>-1</code> if
	 *         the character does not occur.
	 */
	public int indexOf(int ch, int fromIndex)
	{
		int max = characters.length;
		char v[] = characters;

		if (fromIndex < 0)
			fromIndex = 0;

		for (int i=fromIndex; i<max; i++)
			if (v[i] == ch)
				return i;

		return -1;
	}

	/**
	 * Finds the location of a string within this string
	 *
	 * @param str the String
	 * @return Index of string location, -1 if string does not exist.
	 */
	public int indexOf(String str)
	{
		return indexOf(str, 0);
	}

	/**
	 * Find location of String starting at a given index
	 *
	 * @param str the String
	 * @param fromIndex the starting position
	 * @return Index of string location, -1 if string does not exist.
	 */
	public int indexOf(String str, int fromIndex)
	{
		return String.indexOf(characters, 0, characters.length, str.characters, 0,
			str.characters.length, fromIndex);
	}


	public String substring(int beginIndex, int endIndex) {
		if (beginIndex < 0) {
			throw new StringIndexOutOfBoundsException(beginIndex);
		}
		if (endIndex > length()) {
			throw new StringIndexOutOfBoundsException(endIndex);
		}
		if (beginIndex > endIndex) {
			throw new StringIndexOutOfBoundsException(endIndex - beginIndex);
		}
		if ((beginIndex == 0) && (endIndex == length())) return this;

		char[] chars = new char[endIndex - beginIndex];
		for (int i = 0; i < chars.length; i++) {
			chars[i] = charAt(beginIndex+i);
		}
		return new String(chars);
	}

	public String substring(int beginIndex) {
		return substring(beginIndex, this.length());
	}
	/**
	 * Code shared by String and StringBuffer to do searches. The source is the
	 * character array being searched, and the target is the string being
	 * searched for.
	 *
	 * @param source the characters being searched.
	 * @param sourceOffset offset of the source string.
	 * @param sourceCount count of the source string.
	 * @param target the characters being searched for.
	 * @param targetOffset offset of the target string.
	 * @param targetCount count of the target string.
	 * @param fromIndex the index to begin searching from.
	 */
	static int lastIndexOf(char[] source, int sourceOffset, int sourceCount, char[] target,
		int targetOffset, int targetCount, int fromIndex)
	{
		/*
		 * Check arguments; return immediately where possible. For consistency,
		 * don't check for null str.
		 */
		int rightIndex = sourceCount - targetCount;
		if (fromIndex < 0)
		{
			return -1;
		}
		if (fromIndex > rightIndex)
		{
			fromIndex = rightIndex;
		}
		/* Empty string always matches. */
		if (targetCount == 0)
		{
			return fromIndex;
		}

		int strLastIndex = targetOffset + targetCount - 1;
		char strLastChar = target[strLastIndex];
		int min = sourceOffset + targetCount - 1;
		int i = min + fromIndex;

		startSearchForLastChar : while (true)
		{
			while (i >= min && source[i] != strLastChar)
			{
				i--;
			}
			if (i < min)
			{
				return -1;
			}
			int j = i - 1;
			int start = j - (targetCount - 1);
			int k = strLastIndex - 1;

			while (j > start)
			{
				if (source[j--] != target[k--])
				{
					i--;
					continue startSearchForLastChar;
				}
			}
			return start - sourceOffset + 1;
		}
	}

	/**
	 * Code shared by String and StringBuffer to do searches. The source is the
	 * character array being searched, and the target is the string being
	 * searched for.
	 *
	 * @param source the characters being searched.
	 * @param sourceOffset offset of the source string.
	 * @param sourceCount count of the source string.
	 * @param target the characters being searched for.
	 * @param targetOffset offset of the target string.
	 * @param targetCount count of the target string.
	 * @param fromIndex the index to begin searching from.
	 */
	static int indexOf(char[] source, int sourceOffset, int sourceCount, char[] target,
		int targetOffset, int targetCount, int fromIndex)
	{
		if (fromIndex >= sourceCount)
		{
			return (targetCount == 0 ? sourceCount : -1);
		}
		if (fromIndex < 0)
		{
			fromIndex = 0;
		}
		if (targetCount == 0)
		{
			return fromIndex;
		}

		char first = target[targetOffset];
		int max = sourceOffset + (sourceCount - targetCount);

		for (int i = sourceOffset + fromIndex; i <= max; i++)
		{
			/* Look for first character. */
			if (source[i] != first)
			{
				while (++i <= max && source[i] != first);
			}

			/* Found first character, now look at the rest of v2 */
			if (i <= max)
			{
				int j = i + 1;
				int end = j + targetCount - 1;
				for (int k = targetOffset + 1; j < end && source[j] == target[k]; j++, k++);

				if (j == end)
				{
					/* Found whole string. */
					return i - sourceOffset;
				}
			}
		}
		return -1;
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return this.substring(start, end);
	}

	@Override
	public int compareTo(String other) {
		int len1 = this.characters.length;
		int len2 = other.characters.length;

		int len = (len1 < len2) ? len1 : len2;

		for (int i=0; i<len; i++)
		{
			char c1 = this.characters[i];
			char c2 = other.characters[i];

			if (c1 != c2)
				return (c1 < c2) ? -1 : 1;
		}

		if (len1 != len2)
			return (len1 < len2) ? -1 : 1;

		return 0;
	}

    public String trim() {
        int beginIndex, endIndex;
        for (beginIndex = 0; beginIndex < characters.length && characters[beginIndex]==' '; beginIndex++) {
            ;
        }
        for (endIndex = characters.length-1; endIndex >= beginIndex && characters[endIndex]==' '; endIndex--) {
            ;
        }
        return this.substring(beginIndex, endIndex+1);
    }

}
