package haikuvm.pc.tools;
import haikuvm.pc.tools.haikuc.PrintOnChange;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;


public class Msg2Meth {
	static private class Info {
		int token;
		int declared;
	}
	private static SortedMap<String, Info> msg2token=new TreeMap<String, Info>();
	private SortedMap<String, String> msg2meth=new TreeMap<String, String>();
	private static int gtoken=2;
	private int size=0;
	
	public void put(String msg, String map) {
		Info info=msg2token.get(msg);
		if (info==null) {
			info=new Info();
			info.token=gtoken;
			msg2token.put(msg, info);
			gtoken++;
		}
		String tmp=msg2meth.get(msg);
		if (tmp==null && map==null) {
			msg2meth.put(msg, null);
		} else {
			if (map!=null) {
				size++;
				msg2meth.put(msg, map);
				info.declared++;
			}
		}
	}

	public void printC(PrintOnChange outc) {
		HaikuVM.totalClassesLength+=1;
		outc.printf("	%d,\n", size);
		if (size==0) {
            outc.printf("    // {{}} VC 5\n");
		} else {
			outc.printf("    {\n");
			for (Entry<String, String> map : msg2meth.entrySet()) {
				if (map.getValue()!=null) {
					HaikuVM.totalClassesLength+=1+2;
					outc.printf("		{%s, (ByteCode *)(&%s)},\n", map.getKey(), map.getValue());
				}
			}
			outc.printf("	}\n");
		}
	}

	public static void printAllMsgsH(PrintOnChange haikuConfigh) {
		haikuConfigh.printf("\n");
		for (String msg : msg2token.keySet()) {
			haikuConfigh.printf("#define %-30s	%d\n", msg, msg2token.get(msg).token);
			if (msg2token.get(msg).declared==0) {
			    Verbose.severe_warning("%s [%d] was never declared!", msg, msg2token.get(msg).token);
			}
		}
	}
}
