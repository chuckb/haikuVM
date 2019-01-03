package haikuvm.pc.tools;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class Clinit {
	private String classname;
	private int level;
	private static Vector<Clinit> clinits= new Vector<Clinit>();


	public Clinit(String classname, int level) {
		this.classname=classname.replace('/', '.');
		this.level=level;
		for (int i = 0; i < clinits.size(); i++) {
			if (clinits.get(i).classname.equals(this.classname)) {
				return;
			}
		}
		clinits.add(this);
	}


	public static Vector<Clinit> set() {
		Collections.sort(clinits, new Comparator<Clinit>() {

			@Override
			public int compare(Clinit o1, Clinit o2) {
				return - new Integer(o1.level).compareTo(o2.level);
			}
			
		});
//		for (Clinit clinit : clinits) {
//			System.out.println(">>	"+clinit.level+"	"+clinit.classname);
//		}
		return clinits;
	}

	public String getClassName() {
		return classname;
	}
}
