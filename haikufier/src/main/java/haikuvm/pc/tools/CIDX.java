package haikuvm.pc.tools;
import haikuvm.pc.tools.haikuc.PrintOnChange;

import java.io.PrintStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;


public class CIDX {
	private static class Const {
		private String key;
		private String map;
		private int constrain;

		public Const(String key, String map, int constrain) {
			this.key=key;
			this.map=map;
			this.constrain=constrain;
		}
		
	}
	private static SortedMap<String, Const> CIDX=new TreeMap<String, Const>();
	private static Vector<String> constants=new Vector<String>();
	private static Vector<Const> sortconst;

	public static boolean containsKey(String key) {
		return CIDX.containsKey(key);
	}

	public static void set(String key, String map, int constrain) {
		CIDX.put(key, new Const(key, map, constrain));
	}

	/**
	 * merge/link über alle Applikations Dateien
	 * @param haikuConfigh
	 */
	public static void printCIDXdefinesH(PrintOnChange haikuConfigh) {
		prepareCIDX();
		haikuConfigh.printf("\n");
		haikuConfigh.printf("\n");
		haikuConfigh.printf("// ConstantPool: [0, %d]\n", sortconst.size()-1);
		haikuConfigh.printf("\n");
		for (Const c : sortconst) {
			String extern=c.map;
			extern=extern.substring(0, extern.indexOf('='));
			haikuConfigh.printf("extern %s;\n", extern);
		}
	}

	private static void prepareCIDX() {
		if (sortconst!=null) return;
		sortconst=new Vector<Const>();
		
		for (String key : CIDX.keySet()) {
			sortconst.add(CIDX.get(key));
			if (CIDX.get(key).constrain==8)	{
				HaikuVM.totalConstLength+=2;
			}
		}
		
		Collections.sort(sortconst, new Comparator<Const>() {
			   public int compare(Const s1, Const s2){
			      return new Integer(s1.constrain).compareTo(s2.constrain);
			   }
			});
	}

	public static void printCIDXtableC(PrintOnChange haikuConfigc) {
		prepareCIDX();
		haikuConfigc.printf("\n");
		haikuConfigc.printf("\n");
		for (Const c : sortconst) {
			haikuConfigc.printf("%s", c.map);
		}
		haikuConfigc.printf("\n");
	}

	public static String getConstKey(String type, String value) {
		value=type+value;
		int h=constants.indexOf(value);
		if (h<0) {
			constants.add(value);
			h=constants.indexOf(value);
		}
		String key="Const"+Integer.toHexString(h+0x10000).substring(1);
		return key;
	}

}
