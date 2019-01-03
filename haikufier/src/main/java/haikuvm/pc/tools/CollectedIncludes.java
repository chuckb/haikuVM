package haikuvm.pc.tools;
import haikuvm.pc.tools.haikuc.PrintOnChange;

import java.io.PrintStream;
import java.util.Vector;


public class CollectedIncludes {
	private static Vector<String> includes=new  Vector<String> ();


	public static void put(String inc) {
		if (includes.contains(inc)) return;
		includes.add(inc);		
	}
	
	public static void printC(PrintOnChange haikuConfigc) {
		haikuConfigc.printf("\n");
		haikuConfigc.printf("\n");
		for (String key : includes) {
			haikuConfigc.println(key);
		}
	}

    public static boolean contains(String inc) {
        return includes.contains(inc);
    }

}
