package haikuvm.pc.tools;
import haikuvm.pc.tools.haikuc.HaikuDefs;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;


public class FunctionTable {
	private static class Info {
		private Integer count;
		private String name;

		public Info(String methodName, int i) {
			name=methodName;
			count=i;
		}
	}

	private Map<String, Integer> map=new HashMap<String, Integer>();
	private Info[] declared;
	private Info[] sort;
    private static int defaultTecMax=999;


    /**
     * Set default amount of usable InvokeShort bytecodes.
     * Attention some space (35 is a good estimate) is needed
     * for artificial bytecodes (PUT*I|J.., INC1, ..).
     */
    public static void setInvokeShortMax(int invokeShortMax) {
        defaultTecMax=invokeShortMax;
    }

    public void callOf(String methodName) {
    	if (!Haikufy.HAIKU_GenerateFullVM) {
            int count=1;
            if (map.containsKey(methodName)) count=map.get(methodName)+1;
            map.put(methodName, count);
    	}
    }

	private void prepareFoos() {
		if (declared==null) {
			int tecMax;
			try {
				tecMax=Integer.parseInt(HaikuDefs.getProperty("InvokeShort"));
			} catch (NumberFormatException e) {
				tecMax=defaultTecMax;
			}
			sort= new Info[map.size()];
			int i=0;
			for (Entry<String, Integer> info : map.entrySet()) {
				sort[i++]=new Info(info.getKey(), info.getValue());
			}
			Arrays.sort(sort, new Comparator<Info>() {
				@Override
				public int compare(Info o1, Info o2) {
					return - new Integer(o1.count).compareTo(o2.count);
				}
			});
			int max;
			for (max = 0; max < sort.length && max < tecMax && sort[max].count>0; max++) {
				// calc max;
			}
			for (i=max; i < sort.length; i++) {
				map.remove(sort[i].name);
			}
			declared=new Info[max];
			System.arraycopy(sort, 0, declared, 0, max);
		}
	}

	public String toString() {
		String str;
		prepareFoos();

		str= "#if _DEBUG || NO_MICRO\n";
		str+="const char *	functionDesc[] PROGMEM = {\n";
		for (int i=0; i<sort.length; i++) {
			str+=new Formatter().format("\t/* %3d %4d */ \"%s\",\n", i, sort[i].count, sort[i].name);
		}
		if (sort.length==0) {
			// avoid syntax error
			str+="\tNULL";
		}
		str+="};\n";
		str+="#endif\n";
		str+="\n";
		str+="//Needed for Exception unwinding\n";
		str+="ByteCode * const        functionTable[] PROGMEM = {\n";
		for (int i=0; i<sort.length; i++) {
			str+=new Formatter().format("\t/* %3d %4d */ (ByteCode *)&%s,\n", i, sort[i].count, sort[i].name);
		}
		str+="\tNULL";
		str+="};";
		return str;
	}

	public boolean isShortcut(String methodName) {
		prepareFoos();
		int count=0;
		if (map.containsKey(methodName)) count=map.get(methodName);
		return count>0;
	}

	public int getInvokeShortMax() {
		prepareFoos();
		return declared.length;
	}

	public String get(int i) {
		prepareFoos();
		return declared[i].name;
	}
}
