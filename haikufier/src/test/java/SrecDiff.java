import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PushbackReader;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Formatter;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

/**
 * https://en.wikipedia.org/wiki/SREC_%28file_format%29
 *
 * @author Genom
 *
 */
public class SrecDiff {
	Vector<String> file = new Vector<String>();
	private int lineidx;
	private int[] filedirty;
	private static int startLine0;
	static class Srec {
		private String line;
		String getValue(int addr) {

			return line.substring(3, 7);
		}
		String getContent() {
			return line.substring(7);
		}
	}

	/**
	 *
	 * @param adr
	 * @param srec2
	 * @return null if equal else return hex word of this at addr
	 */
	String diff(int addr, SrecDiff srec2) {
		String v1=getValue(addr);
		String v2=srec2.getValue(addr);
		if (v1.equals(v2)) return null;
		return v1+", "+v2;
	}

	/**
S012000048656C6C6F576F726C642E7372656316
S113880000081B060D6646045A0088886A0BB8BC2B
S1138810F30019225E00B0940B810B817900B8BA81
...
S113BF00A864A876A888A8A6A8C0A8DAA8F8A90A48
S113BF10A91CA92EA93EA978A9B6A9D2A9EEAA64FA
S109BF20AA9040C00000DD
S113E7DE0000000100000002000000030000000120
S107E7EE40000000E3
S90380007C

	 * @param addr
	 * @return
	 */
	private String getValue(int addr) {
		for (int i = 0; i < file.size(); i++) {
			String line=file.get(i);
			lineidx=i;
			if (isDataType(line)) {
				int addr0 = Integer.parseInt(line.substring(4,  8), 16);
				int addr1 = addr0+(line.length()-9)/2;
				if (addr0>addr) {
					return "0000";
				}
				if (addr0<=addr && addr<addr1) {
					int idx=2*(addr-addr0);
					return line.substring(idx+8, idx+12);
				}
			}

		}
		return "0000";
	}

	boolean isDataType(String line) {
		if("S1".equals(line.substring(0, 2))) return true;
		return false;
	}

	/**
	 * [
	 * 00..00Do you byte, when I knock?
	 * ...
	 * 0000
	 * <word0><addr0>
	 * <word1><addr1>
	 * ...
	 * <wordN><addrN>
	 * ]
	 *
	 * @param srec2
	 * @return
	 */
	byte[] diff(SrecDiff srec2) {
		String eyecatcher = "Do you byte, when I knock?";
		Vector<Byte> collect = new Vector<Byte>();
		collect.add((byte)(0));
		collect.add((byte)(0));
		int i=0,c=0;
		for (int addr = 0x8000; addr < 0xffff; addr+=2) {
			i++;
			String d = diff(addr, srec2);
			if (d!=null) {
				if (c>50 && addr%16==0) {
					startLine0=lineidx;
					break;
				}
				c++;
				int v = Integer.parseInt(d.substring(0, 4), 16);
				collect.add((byte)(v>>8));
				collect.add((byte)(v&0xff));
				collect.add((byte)(addr>>8));
				collect.add((byte)(addr&0xff));
				System.out.printf("%4d:\t%04x: %s\t%f\n", c, addr, d, 100.0*c/i);
			}
		}

		while(collect.size()%16!=0) {
			collect.add(0, (byte)0);
		}
		for (int j = 0; j < eyecatcher.length(); j++) {
			collect.add(0, (byte)eyecatcher.charAt(eyecatcher.length()-j-1));
		}
		while(collect.size()%16!=0) {
			collect.add(0, (byte)0);
		}
		byte[] res = new byte[collect.size()];
		for (int j = 0; j < res.length; j++) {
			res[j]=collect.get(j);
		}
		return res;
	}

	void buildDiff(byte[] patch, PrintStream out) {
		boolean section2=false;
		int p=0;
		int addr0=0;
		for (int i = 0; i < file.size(); i++) {
			String line=file.get(i);
			if (!isDataType(line)) {
				p++;
				out.println(line);
			} else {
				int addr1 = Integer.parseInt(line.substring(4,  8), 16);
				if (addr0!=0 && addr0+16<addr1) {
					//out.println("new section");
					p+=printPatch(out, addr1, patch);
					section2=true;
				}
				addr0=addr1;
				if (section2 || (startLine0!=0 && i>=startLine0 )) {
					p++;
					out.println(line);
				}
			}
		}
		System.err.printf("%6.2f%% diff\n", 100.0*p/file.size());
	}

	void buildGapDiff(PrintStream out, SrecDiff srec2) {
		int maxEqual=0;
		int tmpEqual=0;
		int bestStop=0;
		int bestRestart=0;
		int tmpStop=0;
		int section=0;
		int p=0;
		int addr0=0;
		for (int addr = 0x8000; addr < 0xffff; addr+=2) {
			String d = diff(addr, srec2);
			if (d!=null) {
				filedirty[lineidx]=1;
			}
		}

		for(int lineno=0; lineno < filedirty.length;) {
			while(lineno < filedirty.length && filedirty[lineno]==1) {
				lineno++;
			}
			tmpStop=lineno;
			tmpEqual=0;
			while(lineno < filedirty.length && filedirty[lineno]==0) {
				lineno++;
				tmpEqual++;
			}
			if (maxEqual<=tmpEqual) {
				maxEqual=tmpEqual;
				bestStop=tmpStop;
				bestRestart=lineno;
			}
		}

		for (int i = 0; i < file.size(); i++) {
			String line=file.get(i);
			if (!isDataType(line) || maxEqual==0) {
				p++;
				out.println(line);
			} else {
				if (i<bestStop || i>=bestRestart) {
					int addr1 = Integer.parseInt(line.substring(4,  8), 16);
					if (addr0!=0 && addr0+16<addr1) {
						section++;
					}
					addr0=addr1;
					if (section<2) {
						p++;
						out.println(line);
					}
				}
			}
		}
		System.err.printf("%6.2f%% diff\n", 100.0*p/file.size());
	}

	/*
	 *  the checksum value is calculated as follows:
	 *      Add: add each byte 13 + 7A+F0 + 0A+0A+0D+00+00+00+00+00+00+00+00+00+00+00+00+00 = 19E (hex) total.
	 *      Mask: keep the least significant byte of the total = 9E (hex).
	 *      Complement: compute ones' complement of least significant byte = 61 (hex).
	 *
	 */
	private int printPatch(PrintStream out, int addr, byte[] patch) {
		int saddr=addr-patch.length;
		int checksum=0x13;
		for (int i = 0; i < patch.length; i+=16) {
			out.printf("S113%04x", saddr+i);
			checksum+=(saddr+i)>>8;
			checksum+=(saddr+i)&0xff;
			for (int j = 0; j < 16; j++) {
				out.printf("%02x", patch[i+j]);
				checksum+=patch[i+j];
			}
			out.printf("%02x\n", (~checksum) & 0xff);
		}
		return patch.length/16;
	}

	void read(String image) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(image));
		String line;
		while ((line=br.readLine()) !=null) {
			file.add(line);
		}
		br.close();
		filedirty=new int[file.size()];
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String oldimage="", infile=null, outfile=null;
//		try {
			for (int i = 0; i < args.length; i++) {
				switch (args[i]) {
				case "-i": oldimage=args[++i]; break;
				case "-f": infile=args[++i]; break;
				case "-o": outfile=args[++i]; break;
				}
			}

			PrintStream out = System.out;
			try {
				out=new PrintStream(outfile);
			} catch (Exception e1) {
			}

			SrecDiff srec1 = new SrecDiff();
			SrecDiff srec2 = new SrecDiff();
			srec1.read(infile);
			try {
				srec2.read(oldimage);
//				byte[] patch = srec1.diff(srec2);
//				srec1.buildDiff(patch, out);
				srec1.buildGapDiff(out, srec2);
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("old image '"+oldimage+"' does not exist!");
				for (int i = 0; i< srec1.file.size(); i++) {
					out.printf("%s\n", srec1.file.get(i));
				}
			}

			//diff(infile, image, outfile);
			showsec(infile);

//		} catch (Exception e) {
//			e.printStackTrace(System.err);
//			System.err.printf(e+"\nusage: SrecDiff -f <newimage.srec> [-i <oldimage.srec>] [-o <diff.srec>]\n");
//		}
	}

	private static void showsec(String infile) throws IOException {
		BufferedReader asm= new BufferedReader(new FileReader(infile+"/../dis.asm"));
		String line;
		ArrayList<String> arr = new ArrayList<String>();
		for (int i = 0; (line=asm.readLine()) !=null; i++) {
			if (line.contains("(sec  4)") && line.contains("(nx 0)") || line.contains("(sec  3)") && !line.contains(" .") ) {
				String[] list = line.split("\\s+");
				arr.add(line.replaceFirst("(.*)(0x0000.+)", "$2"));
				//System.out.println(line);
			}
		}
		asm.close();
		arr.sort(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		for (int i = 0; i < arr.size(); i++) {
			System.out.println(arr.get(i));
		}
	}

	/**
	 * @throws IOException
	 */
    private static void diff(String newImage, String oldImage, String outfile) throws IOException {
    	boolean dirty=false;
		String newLine, oldLine;
		int al=0, bl=0;
		BufferedReader ar= new BufferedReader(new FileReader(newImage));
		PrintStream out = System.out;
		try {
			out=new PrintStream(outfile);
		} catch (Exception e1) {
		}
		BufferedReader br=null;
		try {
			br = new BufferedReader(new FileReader(oldImage));
			for (bl = 0; (newLine=ar.readLine()) !=null; bl++) {
				boolean write=false;
				oldLine=br.readLine();
				if (oldLine!=null) {
					if (dirty) write=true;
					if (newLine.startsWith("S9")  || newLine.startsWith("S012")) write=true;
					if (!write && !newLine.equals(oldLine)) {
						write=true;
						dirty=true;
					}
					if (!write) continue;
				}
				al++;
				out.println(newLine);
//				out.printf("%s\t%s\n", aline, srec2ascii(aline));
			}
			ar.close();
			br.close();
			out.close();
			System.err.printf("%6.2f%% change compared to oldImage '%s'\n", 100.0*al/bl, oldImage);
		} catch (IOException e) {
			System.err.println("old image '"+oldImage+"' does not exist!");
			e.printStackTrace();
			for (bl = 0; (newLine=ar.readLine()) !=null; bl++) {
				out.printf("%s\n", newLine);
			}
		}
    }

	/**
	 * @throws IOException
	 */
    private static void diff_40_500_10(String newImage, String oldImage, String outfile) throws IOException {
		String aline, bline;
		int al=0, bl=0;
		BufferedReader ar= new BufferedReader(new FileReader(newImage));
		PrintStream out = System.out;
		try {
			out=new PrintStream(outfile);
		} catch (Exception e1) {
		}
		BufferedReader br=null;
		try {
			br = new BufferedReader(new FileReader(oldImage));
			for (bl = 0; (aline=ar.readLine()) !=null; bl++) {
				bline=br.readLine();
				if (bline!=null) {
					if (bl%10!=0 && bl>40 && bl<500 && aline.equals(bline) && !aline.startsWith("S9")) continue;
				}
				al++;
				out.println(aline);
//				out.printf("%s\t%s\n", aline, srec2ascii(aline));
			}
			ar.close();
			br.close();
			out.close();
			System.err.printf("%6.2f%% change compared to oldImage '%s'\n", 100.0*al/bl, oldImage);
		} catch (IOException e) {
			System.err.println("old image '"+oldImage+"' does not exist!");
			e.printStackTrace();
			for (bl = 0; (aline=ar.readLine()) !=null; bl++) {
				out.printf("%s\n", aline);
			}
		}
    }

	private static String srec2ascii(String aline) {
		String res=aline.substring(0, 8);
		for (int i = 8; i < aline.length()-2; i++) {
			char c=(char)(16*"0123456789ABCDEF".indexOf(aline.charAt(i)) + "0123456789ABCDEF".indexOf(aline.charAt(++i)));
			if (Character.isLetterOrDigit(c) || "#+-.,;:_'*´ß<>|@²³[]}{µ!\"§$%&/()=?`\\ \t".indexOf(c)>=0) res+=c;
		}
		return res+aline.substring(aline.length()-2);
	}
}
