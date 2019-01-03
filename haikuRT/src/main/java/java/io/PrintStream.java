package java.io; 

import java.lang.String;

public class PrintStream {
	private OutputStream out;
	
	public PrintStream(String str) {
		
	}
	
	public PrintStream(OutputStream out) {
		this.out=out;
	}
	
	private void jprintf(String format, Object... args) {
		int p=0;
		for (int i = 0; i < format.length(); i++) {
			char c=format.charAt(i);
			if (c=='%') {
				for (i = i+1; i < format.length(); i++) {
					c=format.charAt(i);
					if ( !(('0'<=c && c<='9' ) || c=='.' || c=='-' )) {
						break;
					}
				}
				switch(c) {
				case '%': print('%'); break;
				case 's': case 'd': case 'f': case 'g': 
					print(args[p++].toString()); 
					break;
				default: print("%?"); print(c); break;
				}
			} else {
				print(c);
			}
		}
	}
	
//	private native static char write(char c);

	public PrintStream printf(String format, Object... args) {
		jprintf(format, args);
		return this;
	}
	
	public void print(String string) {
		if (string==null) {
			print("null");
		} else {
			for (int i = 0; i < string.length(); i++) {
				print(string.charAt(i));
			}
		}
	}
	
	public void println(String string) {
		print(string);
		print('\n');
	}
	
	public void print(Object obj) {
		if (obj==null) {
			print((String)null);
		} else {
			print(obj.toString());
		}
	}
	
	public void println(Object obj) {
		print(obj);
		print('\n');
	}
	
	public void println() {
		print('\n');
	}

	public void flush() {
	}
	
	public void print(char c) {
		try {
			if (c=='\n') out.write('\r');
			out.write(c);
		} catch (IOException e) {
			// trouble
		}
	}
	
	public void println(char c) {
		print(c);
		print('\n');
	}
	
	public void print(int i) {
		print(String.valueOf(i));
	}
	
	public void println(int i) {
		print(i);
		print('\n');
	}
	
	public void print(long l) {
		print(String.valueOf(l));
	}
	
	public void println(long l) {
		print(l);
		print('\n');
	}
	
	public void print(float f) {
		print(String.valueOf(f));
	}
	
	public void println(float f) {
		print(f);
		print('\n');
	}
	
	public void print(double d) {
		print(String.valueOf(d));
	}
	
	public void println(double d) {
		print(d);
		print('\n');
	}

	public void print(boolean b) {
		print(String.valueOf(b));
	}
	
	public void println(boolean b) {
		print(b);
		print('\n');
	}
}
