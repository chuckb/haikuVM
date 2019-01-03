package haikuvm.pc.tools;
import haikuvm.pc.tools.haikuc.PrintOnChange;

import java.io.PrintStream;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;


public class ObjSpace {
    private static class VarInfo {
        String type, name;
        int puts;
        int gets;
        int natives;
        public VarInfo(String type, String name) {
            this.type=type;
            this.name=name;
        }

        public void incPut() {
            puts++;
        }

        public void incGet() {
            gets++;
        }

        public void incNative() {
            natives++;
        }

        public String toString() {
            return name;
        }
    }
	private static Map<String, VarInfo> map=new TreeMap<String, VarInfo>();
    private static String list[] = {
        "A", "jobject",
        "B", "jbyte",
        "C", "jchar",
        "D", "jdouble",
        "F", "jfloat",
        "I", "jint",
        "J", "jlong",
        "S", "jshort",
        "Z", "jboolean" };

	static {
		set("L", "java.lang.Thread.currentThread", "put&get");
	}

	public static void set(String type, String var, String putget) {
		if (type.equals("L")) type="A";
		if (!map.containsKey(var)) {
			map.put(var, new VarInfo(type, var));
		}
		VarInfo v = map.get(var);
        if (putget.contains("put")) {
            v.incPut();
        }
        if (putget.contains("get")) {
            v.incGet();
        }
        if (putget.contains("native")) {
            v.incNative();
        }
	}

	/**
#define STATIC_ROOTS 2
#define STATIC_SIZE (STATIC_ROOTS*sizeof(jobject)+0*sizeof(jbyte)+0*sizeof(jchar)+0*sizeof(jdouble)+0*sizeof(jfloat)+2*sizeof(jint)+0*sizeof(jlong)+0*sizeof(jshort)+2*sizeof(jboolean))

#if defined(HAIKU_MemorySize)
#define HEAPSIZE (HAIKU_MemorySize - STATIC_SIZE)
#else
#if defined(ARDUINO_SAM_DUE)
#define HEAPSIZE (10000 - STATIC_SIZE)
#else
#define HEAPSIZE (((RAMEND-0x100) - 500) - STATIC_SIZE)
#endif
#endif

typedef struct {
	heap_t heapControl;
	char data[HEAPSIZE];
	jheapsize nullallocsize;
} heapmem_t;

extern heapmem_t heapmem;

typedef struct {
	jobject	java_lang_Thread_currentThread;
	jobject with_lib_processing_Sweep_myservo;
	jint	haiku_avr_AVRDefines__next;
	jint	with_lib_processing_Sweep_pos;
	jboolean	haiku_avr_lib_arduino_Servo_attached9;
	jboolean	haiku_avr_lib_arduino_Servo_attached10;
} statics_u_t;

typedef union {
	jobject gcRoots[STATIC_ROOTS];
	statics_u_t user;
} statics_t;

	 * @param haikuConfigh
	 */
	public static void printH(PrintOnChange haikuConfigh) {
		haikuConfigh.printf("\n");
		haikuConfigh.printf("\n");
		haikuConfigh.printf("typedef struct {\n");
		for (int i = 0; i < list.length; i+=2) {
			for (VarInfo varInfo : map.values()) {
                if (varInfo.type.equals(list[i])) {
                    if (varInfo.natives==0) {
                        haikuConfigh.printf("\tvolatile %-10s %s;\n", list[i+1], Haikufy.mangleField(varInfo.name));
                    }
                }
            }
		}
		haikuConfigh.printf("} statics_u_t;\n");
		haikuConfigh.printf("\n");
		haikuConfigh.printf("#define STATIC_ROOTS %d\n", count(list[0]));
		haikuConfigh.printf("\n");

		haikuConfigh.printf("typedef union {\n");
		haikuConfigh.printf("	jobject     gcRoots[STATIC_ROOTS];\n");
		haikuConfigh.printf("	statics_u_t statics;\n");
		haikuConfigh.printf("} statics_t;\n");
		haikuConfigh.printf("\n");

		haikuConfigh.printf("#if defined(HAIKU_MemorySize)\n");
		haikuConfigh.printf("#define HEAPSIZE (HAIKU_MemorySize - sizeof(statics_t))\n");
		haikuConfigh.printf("#else\n");
		haikuConfigh.printf("#if defined(ARDUINO_SAM_DUE)\n");
		haikuConfigh.printf("#define HEAPSIZE (10000 - sizeof(statics_t))\n");
		haikuConfigh.printf("#else\n");
        //haikuConfigh.printf("#define HEAPSIZE (((RAMEND-0x100) - ((HAIKU_Mode==HAIKU_32_64||HAIKU_Mode==HAIKU_16_64)?2:1)* 500) - STATIC_SIZE)\n");
        haikuConfigh.printf("#define HEAPSIZE (((RAMEND-0x100) - 500) - sizeof(statics_t))\n");
		haikuConfigh.printf("#endif\n");
		haikuConfigh.printf("#endif\n");
		haikuConfigh.printf("\n");

		haikuConfigh.printf("typedef struct {\n");
		//haikuConfigh.printf("	heap_t heapControl0;\n");
		haikuConfigh.printf("	statics_t allStatics;\n");
		// Detailed information
        for (int i = 0; i < list.length; i+=2) {
            haikuConfigh.printf("//  %s:\n", list[i+1]);
            int j=0;
            for (VarInfo varInfo : map.values()) {
                if (varInfo.type.equals(list[i]) && varInfo.natives==0) {
                    haikuConfigh.printf("//\t  %d\t%s; gets=%d puts=%d native=%d\n", j++, Haikufy.mangleField(varInfo.name), varInfo.gets, varInfo.puts, varInfo.natives);
                }
            }
        }
        haikuConfigh.printf("\n");
		haikuConfigh.printf("	heap_t heapControl0;\n");
		haikuConfigh.printf("	char data[HEAPSIZE];\n");
		haikuConfigh.printf("	jheapsize nullallocsize;\n");
		haikuConfigh.printf("} heapmem_t;\n");
		haikuConfigh.printf("\n");
		haikuConfigh.printf("extern heapmem_t heapmem;\n");

		haikuConfigh.printf("\n");
//        outc.printf("\n");
//        outc.printf("#if NO_MICRO\n");;
//        for (VarInfo varInfo : map.values()) {
//            if (varInfo.natives!=0) {
//                // #define EEAR    _SFR_IO16(0x1E)
//                outc.printf("#define %s\t_SFR_%s(%s_IDX)\t// gets=%d puts=%d native=%d\n", nativeC(varInfo.name), varInfo.type, nativeC(varInfo.name), varInfo.gets, varInfo.puts, varInfo.natives);
//                //outc.printf("volatile %-10s %s;\n", list[i+1], nativeC(varInfo.name));
//            }
//        }
//        outc.printf("#endif\n");;
        haikuConfigh.printf("\n");
        for (VarInfo varInfo : map.values()) {
            if (varInfo.natives!=0) {
                // #define EEAR    _SFR_IO16(0x1E)
                haikuConfigh.printf("#ifndef %s\n", nativeC(varInfo.name));
                haikuConfigh.printf("  extern %s %s;\t// gets=%d puts=%d native=%d\n", nativeType(varInfo.type), nativeC(varInfo.name), varInfo.gets, varInfo.puts, varInfo.natives);
                haikuConfigh.printf("#endif\n\n");
                //outc.printf("volatile %-10s %s;\n", list[i+1], nativeC(varInfo.name));
            }
        }
	}

    private static Object nativeC(String name) {
        int pos=name.lastIndexOf('.');
        if (pos<0) return name;
        return name.substring(pos+1);
    }

    private static String nativeType(String type) {
        for (int i = 0; i < list.length; i+=2) {
            if (type.equals(list[i])) {
                return list[i+1];
            }
        }
        return "int"+type+"_t"; // int8_t | int16_t | ...
    }

    private static int count(String type) {
	    int count=0;
        for (VarInfo varInfo : map.values()) {
            if (varInfo.type.equals(type) && varInfo.natives==0) {
                count++;
            }
        }
        return count;
    }

}
