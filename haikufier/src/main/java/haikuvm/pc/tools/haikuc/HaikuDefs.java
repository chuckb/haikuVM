package haikuvm.pc.tools.haikuc;
import haikuvm.pc.tools.Haikufy;
import haikuvm.pc.tools.Verbose;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Vector;


public class HaikuDefs {

	private static Properties properties;
	private static List <String> defs = new Vector<String>(Arrays
			.asList(new String[] {
					"Target",
					"MemorySize",
					"InitialMainThreadStackSize",
					"InitialOtherThreadStackSize",
					"Mode",
					"Char",
					"PanicExceptions",
					"PanicSupport",
					"Threads",
					"Extension",
					"GenerateFullVM",
					"Clock",
					"APP_NAME",
					"HOME",
					"VM_BASE",
					"APP_BASE",
					"CC_OPT",
					"Upload",
					}));
    private static PrintOnChange out;

    public HaikuDefs() throws FileNotFoundException {
        File file =new File(HaikuJava2File.getGenTargetDir()+"/haikuDefs.h");
        if (out==null) out=new PrintOnChange(file);
    }

    public static void init() throws FileNotFoundException, IOException {
		// Read properties file.
		properties = new Properties();
        properties.setProperty("<base>.Threads", "0");
        properties.setProperty("<base>.HOME", new File(System.getProperty("haikuvm.home")).getCanonicalPath());
        System.out.println("Looking for 'HaikuVM.properties' in path: '"+new File(".").getCanonicalPath()+"';'"+new File(System.getProperty("haikuvm.home")+"/config/").getCanonicalPath()+"'");
	    try {
			properties.load(new FileInputStream(new File("HaikuVM.properties").getCanonicalPath()));
            System.out.println("Loaded from '"+new File("HaikuVM.properties").getCanonicalPath()+"'.");
		} catch (FileNotFoundException e) {
		    try {
				properties.load(new FileInputStream(new File(System.getProperty("haikuvm.home")+"/config/HaikuVM.properties").getCanonicalPath()));
                System.out.println("Loaded from '"+new File(System.getProperty("haikuvm.home")+"/config/HaikuVM.properties").getCanonicalPath()+"'");
		    } catch (IllegalArgumentException e1) {
			    throw Verbose.error(e.getMessage()+"\n"+e1.getMessage()+"\n"+"ERROR: something wrong in file 'HaikuVM.properties'!");
			} catch (FileNotFoundException e1) {
			    throw Verbose.error(e.getMessage()+"\n"+e1.getMessage()+"\n"+"WARNING: no 'HaikuVM.properties' file found!");
			}
		}

		// include new user properties into defs
		for (Object obj : properties.keySet()) {
			if (obj instanceof String) {
				String key=obj.toString();
				int p=key.lastIndexOf('.');
				if (p>=0) {
					key=key.substring(p+1);
				}
				boolean include=true;
				for (int i = 0; i < defs.size(); i++) {
					if (key.equalsIgnoreCase(defs.get(i))) {
						include=false;
						break;
					}
				}
				if (include) {
					defs.add(key);
				}
			}
		}
	}

	public static void setProperty(String key, String value) {
		properties.setProperty(key, value);
	}

	public static String getProperty(String key) {
		String raw = getRaw(key);
		if (raw==null) {
		    //Verbose.swarning("getProperty('"+key+"') returned null");
		    return null;
		}
		String aliases = properties.getProperty("aliases.for."+key);
		if (aliases==null) return raw;
		String value=raw;
		String[] token=aliases.split("[)\\s]+");
		for (int i = 0; i < token.length; i++) {
			if (token[i]!=null) {
				if (token[i].startsWith("(")) {
					if (token[i].length()>1) {
						value=token[i].substring(1);
					} else if (i+1<token.length) {
						value=token[i+1];
					}
					token[i]=value;
				}
				if (token[i].equalsIgnoreCase(raw)) {
					return value;
				}
			}
		}
		return raw;
	}

	public static Iterable<String> defs() {
	    Collections.sort(defs);
		return defs;
	}

	public static String getRaw(String key) {
		String raw = properties.getProperty(key);
		if (raw!=null) return raw.trim();
		String config = properties.getProperty("Config");
        if (config==null) {
            throw Verbose.error("No Configuration given! Missing option -Config <configuration> <java-file>");
        }
		while (config!=null && !"root".equals(config)) {
		    config=config.trim();
			raw = properties.getProperty(config+"."+key);
			if (raw!=null) return raw.trim();
	        if (properties.getProperty(config+".Extends")==null) {
	            throw Verbose.error("Configuration '%s' is used but not defined!", config);
	        }
            config = properties.getProperty(config+".Extends");
		}
		String res=properties.getProperty("<base>."+key);
		if (res==null) return null;
		return res.trim();
	}

	public static boolean isSupported(String key) {
		String raw = getRaw(key);
		if (raw==null) return false;
		String aliases = properties.getProperty("aliases.for."+key);
		if (aliases==null) return true;
		String value=raw;
		String[] token=aliases.split("[)\\s]+");
		for (int i = 0; i < token.length; i++) {
			if (token[i]!=null) {
				if (token[i].startsWith("(")) {
					if (token[i].length()>1) {
						value=token[i].substring(1);
					} else if (i+1<token.length) {
						value=token[i+1];
					}
					token[i]=value;
				}
				if (token[i].equalsIgnoreCase(raw)) {
					return true;
				}
			}
		}
		return false;
	}

    public static long getPropertyInt(String key, long deflt) {
        try {
            String prop = getProperty(key);
            long value = Long.parseLong(prop);
            return value;
        } catch (Exception e) {
            return deflt;
        }
    }

    private static int findScale(long prescale, long f_cpu, String select) {
        double ticksPerMillis=f_cpu/(prescale*256.0*1000.0);
        double best=Double.MAX_VALUE;

        int dividend=1;
        int divisor=1;
        for (int i = 1; i< 4000; i++) {
            for (int j = 1; j< 4000; j++) {
                double t=ticksPerMillis*i/j;
                double err=t-1;
                if (Math.abs(best)>Math.abs(err)) {
                    best=err;
                    dividend=i;
                    divisor=j;
                }
            }
        }
        if (select.equals("MillisDividend")) {
            return dividend;
        }
        return divisor;
    }


    private static boolean isAutomatic(String value) {
        return (value==null || value.equals("") || value.equals("null"));
    }

    /**
     * Target: in {Atmega8, Atmega328p, WIN32}
     * MemorySize: size in Bytes
     * InitialMainThreadStackSize: size in Bytes or auto
     * InitialOtherThreadStackSize: size in Bytes or auto
     * Mode: in {32_64,  16_64, 32_32, 16_32, 16_16}
     * Char: in {1, 2} JAVA type *char* is 2 or 1 Byte
     * PanicExceptions: list of Exceptions or none
     * PanicSupport: list of Exceptions or none
     * @throws IOException
     */
    public void print() throws IOException {
//      defs.printf("#define HAIKU_Target %s\n", HaikuDefs.getProperty("Target"));
//      //defs.printf("#define %s 1\n", HaikuDefs.getProperty("Target"));
//      defs.println();
        Verbose.printf("Effective configuration for '%s':\n", HaikuDefs.getProperty("Config"));
        for (String property : HaikuDefs.defs()) {
            String commit=HaikuDefs.getProperty(property);
            String raw=HaikuDefs.getRaw(property);
            if (!HaikuDefs.isSupported(property)) {
                if (commit==null) {
                    // no problem as long as not used
                } else {
                    Verbose.warning("Warning: unsupported value '%s' for property '%s'!", raw, property);
                    if (out!=null) out.printf("// Warning: unsupported value '%s' for property '%s'!\n", raw, property);
                }
            } else if (!raw.equals(commit)) {
                if (out!=null) out.printf("// Mapped '%s' to '%s'\n", raw, commit);
            }
            if (isAutomatic(commit)) {
                if (property.equals("MillisDividend") ||property.equals("MillisDivisor")) {
                    commit=""+findScale(HaikuDefs.getPropertyInt("MillisPrescale", 64), HaikuDefs.getPropertyInt("Clock", 16000000L), property);
                }
            }
            if (commit!=null && !commit.equals("undef")) if (out!=null) out.printf("#define HAIKU_%s (%s)\n", property, commit);
            Verbose.println("  "+property+" = "+commit);
            if (commit==null && raw==null && property.equals("Target")) {
                Verbose.error("Error: Property 'Target' must be set but is null! Consider to change option '--Config' or check file 'HaikuVM.properties' for errors.");
                throw new RuntimeException("Error: Property 'Target' must be set but is null! Consider to change option '--Config' or check file 'HaikuVM.properties' for errors.");
            }
        }
        Verbose.println();
        if (out!=null) out.close();
    }
}
