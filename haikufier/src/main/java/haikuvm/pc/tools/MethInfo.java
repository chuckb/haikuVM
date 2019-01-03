package haikuvm.pc.tools;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

public class MethInfo {

    private static Map<String, Set<String>> classMap = new HashMap<String, Set<String>>(); 
	private String body;
	private String args;
	public MethInfo(String body, String args) {
		if (body.startsWith("haiku.vm.HaikuMagic")) {
			body=body.replace("haiku.vm.HaikuMagic", HaikuVM.mainclass.replace("/", "."));
		}
		this.body=body;
		this.args=args;
	}
	
    public static Set<String> getClassMap(String clazz) {
        return classMap.get(clazz);
    }

	/**
	 * @return haiku.vm.arduino.ArduinoLib
	 */
	public String getClassname() {
		return body.substring(0, body.lastIndexOf('.'));
	}

	/**
	 * @return sbi(II)V
	 */
	public String getShortName() {
		return body.substring(body.lastIndexOf('.')+1)+args;
	}

	/**
	 * @return haiku_vm_arduino_ArduinoLib_sbi_II
	 */
	public String getLongName() {
		return HaikuVM.getMethodSignature("1 bc "+body+" "+args);
	}

	/**
	 * @return haiku.vm.arduino.ArduinoLib.sbi(II)V
	 */
	public String getInclude() {
		return body+args;
	}
	
	/**
	 * Idea:
	 * haiku.vm.arduino.ArduinoLib.sbi(II)V -> haiku.avr.AVRDefines.sbi(II)V 
	 * 
	 * @param body haiku.vm.arduino.ArduinoLib.sbi
	 * @param signature (II)V
	 * 
	 * @return MethInfo(..)
	 * @throws IOException 
	 */
	public static MethInfo findRealMethod(String body, String signature) throws IOException {
		MethInfo mi = new MethInfo(body, signature);
        String clazz0=mi.getClassname();
        String clazz=clazz0;
		
		for(int j=0; ; j++) {
			JavaClass jc=HaikuVM.getClassFile(clazz);
			Method[] methods = jc.getMethods();
			for (int i = 0; i < methods.length; i++) {
				if (methods[i].isStatic()) {
					String name = methods[i].getName()+methods[i].getSignature();
					if (name.equals(mi.getShortName())) {
                        map(clazz0, clazz);
						if (j>0) {
							return new MethInfo(body.replace(clazz0, clazz), signature);
						} else {
							return mi;
						}
					}
				}
			}
			try {
				clazz=jc.getSuperClass().getClassName();
			} catch (Exception e) {
				throw new IOException("problem finding method '"+mi.getInclude()+"'.\nCheck:\n" +
						"1) if your class file is up to date.\n" +
						"2) if your method is static (and public).\n"+(e.getMessage()==null?"":e.getMessage()));
			}
		}
	}

    private static void map(String clazz0, String clazz) {
        Set<String> value = classMap.get(Haikufy.filename(clazz0.replace('.', '/')));
        if (value==null) {
            value=new TreeSet<String>();
            value.add(Haikufy.filename(clazz0.replace('.', '/')));
        }
        value.add(Haikufy.filename(clazz.replace('.', '/')));
        classMap.put(Haikufy.filename(clazz0.replace('.', '/')), value);
    }

}
