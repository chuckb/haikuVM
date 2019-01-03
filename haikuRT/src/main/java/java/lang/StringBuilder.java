package java.lang;

public class StringBuilder {
	private char[] chars;

	public StringBuilder(String str) {
		this();
		append(str);
	}
	
	public StringBuilder() {
		chars=new char[0];
	}
	
	public String toString() {
		return new String(chars);
	}

	public StringBuilder append(String aString) {
		char[] newchars=new char[chars.length+aString.characters.length];
		for (int i = 0, j=0; i < newchars.length; i++) {
			newchars[i]=(i<chars.length) ?	chars[i]: aString.characters[j++];
		}
//		System.arraycopy(bytes, 0, newbytes, 0, bytes.length);
//		System.arraycopy(aString.chars, 0, newbytes, bytes.length, aString.length());
		chars=newchars;
		return this;
	}
	
	public StringBuilder append(Object anObject) {
		return append(anObject.toString());
	}
	
	public StringBuilder append(char c) {
		return append(new String(c));
	}

	public StringBuilder append(int i) {
		return append(String.valueOf(i));
	}

	public StringBuilder append(long l) {
		return append(String.valueOf(l));
	}
	
	public StringBuilder append(float f) {
		return append(String.valueOf(f));
	}

	public StringBuilder append(double d) {
		return append(String.valueOf(d));
	}

	public StringBuilder append(boolean v) {
		if (v) return append("true");
		return append("false");
	}

}
