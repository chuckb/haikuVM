package haikuvm.pc.tools;
import haikuvm.pc.tools.haikuc.PrintOnChange;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Formatter;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;


public class BC2IDX {
	private static class Bytecode {

		private String op;			// ALOAD_1
		private String mapto; 		// ALOAD_0
		private Integer constraint;	// 1
		private int obc;			// 0x2b
		private int bc;				// 5
		private boolean used=false;
		private int index=-1;

		public Bytecode(String op, String mapto, int obc, int bc,
				Integer constraint) {
			this.op= op;
			this.mapto=mapto;
			this.obc=obc;
			this.bc=bc;
			this.constraint=constraint;
		}

		void setUsed() {
			used=true;
		}

		public void setIndex(int i) {
			index=i;
		}
	};
	private static SortedMap<String, Bytecode> bytecode=new TreeMap<String, Bytecode>();
	private static Bytecode[] position;
	private static int maxIndex;

	static {
		haikucode("aaload"            , "AALOAD"            , 0x32, 0, 0, null, 0); // 0#
		haikucode("aastore"           , "AASTORE"           , 0x53, 0, 0, null, 1); // 0#
		haikucode("aconst_null"       , "ACONST_NULL"       , 0x01, 0, 0, null, 2); // 0#
		haikucode("aload"             , "ALOAD"             , 0x19, 1, 1, null, 3); // [9]# 1: index
		haikucode("aload_0"           , "ALOAD_0"           , 0x2a, 0, 0, 0, 4); // 0#
		haikucode("aload_1"           , "ALOAD_0"           , 0x2b, 0, 0, 1, 5); // 0#
		haikucode("aload_2"           , "ALOAD_0"           , 0x2c, 0, 0, 2, 6); // 0#
		haikucode("aload_3"           , "ALOAD_0"           , 0x2d, 0, 0, 3, 7); // 0#
		haikucode("anewarray"         , "ANEWARRAY"         , 0xbd, 2, 2, null, 8); // [1, 2]# 2: indexbyte1, indexbyte2
		haikucode("areturn"           , "ARETURN"           , 0xb0, 0, 0, null, 9); // 0#
		haikucode("arraylength"       , "ARRAYLENGTH"       , 0xbe, 0, 0, null, 10); // 0#
		haikucode("astore"            , "ASTORE"            , 0x3a, 1, 1, null, 11); // [9]# 1: index
		haikucode("astore_0"          , "ASTORE_0"          , 0x4b, 0, 0, 0, 12); // 0#
		haikucode("astore_1"          , "ASTORE_0"          , 0x4c, 0, 0, 1, 13); // 0#
		haikucode("astore_2"          , "ASTORE_0"          , 0x4d, 0, 0, 2, 14); // 0#
		haikucode("astore_3"          , "ASTORE_0"          , 0x4e, 0, 0, 3, 15); // 0#
		haikucode("athrow"            , "ATHROW"            , 0xbf, 0, 0, null, 16); // 0#
		haikucode("baload"            , "BALOAD"            , 0x33, 0, 0, null, 17); // 0#
		haikucode("bastore"           , "BASTORE"           , 0x54, 0, 0, null, 18); // 0#
		haikucode("bipush"            , "BIPUSH"            , 0x10, 1, 1, null, 19); // [9]# 1: byte
		haikucode("caload"            , "CALOAD"            , 0x34, 0, 0, null, 20); // 0#
		haikucode("castore"           , "CASTORE"           , 0x55, 0, 0, null, 21); // 0#
		haikucode("checkcast"         , "CHECKCAST"         , 0xc0, 2, 2, null, 22); // [1, 2]# 2: indexbyte1, indexbyte2
		haikucode("d2f"               , "D2F"               , 0x90, 0, 0, null, 23); // 0#
		haikucode("d2i"               , "D2I"               , 0x8e, 0, 0, null, 24); // 0#
		haikucode("d2l"               , "D2L"               , 0x8f, 0, 0, null, 25); // 0#
		haikucode("dadd"              , "DADD"              , 0x63, 0, 0, null, 26); // 0#
		haikucode("daload"            , "DALOAD"            , 0x31, 0, 0, null, 27); // 0#
		haikucode("dastore"           , "DASTORE"           , 0x52, 0, 0, null, 28); // 0#
		haikucode("dcmpg"             , "DCMPG"             , 0x98, 0, 0, null, 29); // 0#
		haikucode("dcmpl"             , "DCMPL"             , 0x97, 0, 0, null, 30); // 0#
		haikucode("dconst_0"          , "DCONST_0"          , 0x0e, 0, 0, 0, 31); // 0#
		haikucode("dconst_1"          , "DCONST_0"          , 0x0f, 0, 0, 1, 32); // 0#
		haikucode("ddiv"              , "DDIV"              , 0x6f, 0, 0, null, 33); // 0#
		haikucode("dload"             , "DLOAD"             , 0x18, 1, 1, null, 34); // [9]# 1: index
		haikucode("dload_0"           , "DLOAD_0"           , 0x26, 0, 0, 0, 35); // 0#
		haikucode("dload_1"           , "DLOAD_0"           , 0x27, 0, 0, 1, 36); // 0#
		haikucode("dload_2"           , "DLOAD_0"           , 0x28, 0, 0, 2, 37); // 0#
		haikucode("dload_3"           , "DLOAD_0"           , 0x29, 0, 0, 3, 38); // 0#
		haikucode("dmul"              , "DMUL"              , 0x6b, 0, 0, null, 39); // 0#
		haikucode("dneg"              , "DNEG"              , 0x77, 0, 0, null, 40); // 0#
		haikucode("drem"              , "DREM"              , 0x73, 0, 0, null, 41); // 0#
		haikucode("dreturn"           , "DRETURN"           , 0xaf, 0, 0, null, 42); // 0#
		haikucode("dstore"            , "DSTORE"            , 0x39, 1, 1, null, 43); // [9]# 1: index
		haikucode("dstore_0"          , "DSTORE_0"          , 0x47, 0, 0, 0, 44); // 0#
		haikucode("dstore_1"          , "DSTORE_0"          , 0x48, 0, 0, 1, 45); // 0#
		haikucode("dstore_2"          , "DSTORE_0"          , 0x49, 0, 0, 2, 46); // 0#
		haikucode("dstore_3"          , "DSTORE_0"          , 0x4a, 0, 0, 3, 47); // 0#
		haikucode("dsub"              , "DSUB"              , 0x67, 0, 0, null, 48); // 0#
		haikucode("dup"               , "DUP"               , 0x59, 0, 0, null, 49); // 0#
		haikucode("dup_x1"            , "DUP_X1"            , 0x5a, 0, 0, null, 50); // 0#
		haikucode("dup_x2"            , "DUP_X2"            , 0x5b, 0, 0, null, 51); // 0#
		haikucode("dup2"              , "DUP2"              , 0x5c, 0, 0, null, 52); // 0#
		haikucode("dup2_x1"           , "DUP2_X1"           , 0x5d, 0, 0, null, 53); // 0#
		haikucode("dup2_x2"           , "DUP2_X2"           , 0x5e, 0, 0, null, 54); // 0#
		haikucode("f2d"               , "F2D"               , 0x8d, 0, 0, null, 55); // 0#
		haikucode("f2i"               , "F2I"               , 0x8b, 0, 0, null, 56); // 0#
		haikucode("f2l"               , "F2L"               , 0x8c, 0, 0, null, 57); // 0#
		haikucode("fadd"              , "FADD"              , 0x62, 0, 0, null, 58); // 0#
		haikucode("faload"            , "FALOAD"            , 0x30, 0, 0, null, 59); // 0#
		haikucode("fastore"           , "FASTORE"           , 0x51, 0, 0, null, 60); // 0#
		haikucode("fcmpg"             , "FCMPG"             , 0x96, 0, 0, null, 61); // 0#
		haikucode("fcmpl"             , "FCMPL"             , 0x95, 0, 0, null, 62); // 0#
		haikucode("fconst_0"          , "FCONST_0"          , 0x0b, 0, 0, 0, 63); // 0#
		haikucode("fconst_1"          , "FCONST_0"          , 0x0c, 0, 0, 1, 64); // 0#
		haikucode("fconst_2"          , "FCONST_0"          , 0x0d, 0, 0, 2, 65); // 0#
		haikucode("fdiv"              , "FDIV"              , 0x6e, 0, 0, null, 66); // 0#
		haikucode("fload"             , "FLOAD"             , 0x17, 1, 1, null, 67); // [9]# 1: index
		haikucode("fload_0"           , "FLOAD_0"           , 0x22, 0, 0, 0, 68); // 0#
		haikucode("fload_1"           , "FLOAD_0"           , 0x23, 0, 0, 1, 69); // 0#
		haikucode("fload_2"           , "FLOAD_0"           , 0x24, 0, 0, 2, 70); // 0#
		haikucode("fload_3"           , "FLOAD_0"           , 0x25, 0, 0, 3, 71); // 0#
		haikucode("fmul"              , "FMUL"              , 0x6a, 0, 0, null, 72); // 0#
		haikucode("fneg"              , "FNEG"              , 0x76, 0, 0, null, 73); // 0#
		haikucode("frem"              , "FREM"              , 0x72, 0, 0, null, 74); // 0#
		haikucode("freturn"           , "ARETURN"           , 0xae, 0, 0, null, 75); // 0#
		haikucode("fstore"            , "FSTORE"            , 0x38, 1, 1, null, 76); // [9]# 1: index
		haikucode("fstore_0"          , "FSTORE_0"          , 0x43, 0, 0, 0, 77); // 0#
		haikucode("fstore_1"          , "FSTORE_0"          , 0x44, 0, 0, 1, 78); // 0#
		haikucode("fstore_2"          , "FSTORE_0"          , 0x45, 0, 0, 2, 79); // 0#
		haikucode("fstore_3"          , "FSTORE_0"          , 0x46, 0, 0, 3, 80); // 0#
		haikucode("fsub"              , "FSUB"              , 0x66, 0, 0, null, 81); // 0#
		haikucode("getfield_l"        , "GETFIELD_L"        , 0xb4, 2, 2, null, 82); // [1, 2]# 2: index1, index2
		haikucode("getfield_b"        , "GETFIELD_B"        , 0xb4, 2, 2, null, 83); // [1, 2]# 2: index1, index2
		haikucode("getfield_c"        , "GETFIELD_C"        , 0xb4, 2, 2, null, 84); // [1, 2]# 2: index1, index2
		haikucode("getfield_d"        , "GETFIELD_D"        , 0xb4, 2, 2, null, 85); // [1, 2]# 2: index1, index2
		haikucode("getfield_f"        , "GETFIELD_F"        , 0xb4, 2, 2, null, 86); // [1, 2]# 2: index1, index2
		haikucode("getfield_i"        , "GETFIELD_I"        , 0xb4, 2, 2, null, 87); // [1, 2]# 2: index1, index2
		haikucode("getfield_j"        , "GETFIELD_J"        , 0xb4, 2, 2, null, 88); // [1, 2]# 2: index1, index2
		haikucode("getfield_s"        , "GETFIELD_S"        , 0xb4, 2, 2, null, 89); // [1, 2]# 2: index1, index2
		haikucode("getfield_z"        , "GETFIELD_Z"        , 0xb4, 2, 2, null, 90); // [1, 2]# 2: index1, index2
		haikucode("getstatic_l"       , "GETSTATIC_L"       , 0xb2, 2, 2, null, 91); // [1, 2]# 2: index1, index2
		haikucode("getstatic_b"       , "GETSTATIC_B"       , 0xb2, 2, 2, null, 92); // [1, 2]# 2: index1, index2
		haikucode("getstatic_c"       , "GETSTATIC_C"       , 0xb2, 2, 2, null, 93); // [1, 2]# 2: index1, index2
		haikucode("getstatic_d"       , "GETSTATIC_D"       , 0xb2, 2, 2, null, 94); // [1, 2]# 2: index1, index2
		haikucode("getstatic_f"       , "GETSTATIC_F"       , 0xb2, 2, 2, null, 95); // [1, 2]# 2: index1, index2
		haikucode("getstatic_i"       , "GETSTATIC_I"       , 0xb2, 2, 2, null, 96); // [1, 2]# 2: index1, index2
		haikucode("getstatic_j"       , "GETSTATIC_J"       , 0xb2, 2, 2, null, 97); // [1, 2]# 2: index1, index2
		haikucode("getstatic_s"       , "GETSTATIC_S"       , 0xb2, 2, 2, null, 98); // [1, 2]# 2: index1, index2
		haikucode("getstatic_z"       , "GETSTATIC_Z"       , 0xb2, 2, 2, null, 99); // [1, 2]# 2: index1, index2
		haikucode("getstatic_n"       , "GETSTATIC_N"       , 0xb2, 2, 2, null, 100); // [1, 2]# 2: index1, index2
		haikucode("goto"              , "GOTO"              , 0xa7, 2, 2, null, 101); // [1, 2]# 2: branchbyte1, branchbyte2
		haikucode("goto_w"            , ""                  , 0xc8, 4, 3, null, 102); // [1, 2, 3, 4]# 4: branchbyte1, branchbyte2, branchbyte3, branchbyte4
		haikucode("i2b"               , "I2B"               , 0x91, 0, 0, null, 103); // 0#
		haikucode("i2c"               , "I2C"               , 0x92, 0, 0, null, 104); // 0#
		haikucode("i2d"               , "I2D"               , 0x87, 0, 0, null, 105); // 0#
		haikucode("i2f"               , "I2F"               , 0x86, 0, 0, null, 106); // 0#
		haikucode("i2l"               , "I2L"               , 0x85, 0, 0, null, 107); // 0#
		haikucode("i2s"               , "I2S"               , 0x93, 0, 0, null, 108); // 0#
		haikucode("iadd"              , "IADD"              , 0x60, 0, 0, null, 109); // 0#
		haikucode("iaload"            , "IALOAD"            , 0x2e, 0, 0, null, 110); // 0#
		haikucode("iand"              , "IAND"              , 0x7e, 0, 0, null, 111); // 0#
		haikucode("iastore"           , "IASTORE"           , 0x4f, 0, 0, null, 112); // 0#
		haikucode("iconst_m1"         , "ICONST_M1"         , 0x02, 0, 0, null, 113); // 0#
		haikucode("iconst_0"          , "ICONST_0"          , 0x03, 0, 0, null, 114); // 0#
		haikucode("iconst_1"          , "ICONST_1"          , 0x04, 0, 0, null, 115); // 0#
		haikucode("iconst_2"          , "ICONST_2"          , 0x05, 0, 0, null, 116); // 0#
		haikucode("iconst_3"          , "ICONST_3"          , 0x06, 0, 0, null, 117); // 0#
		haikucode("iconst_4"          , "ICONST_4"          , 0x07, 0, 0, null, 118); // 0#
		haikucode("iconst_5"          , "ICONST_5"          , 0x08, 0, 0, null, 119); // 0#
		haikucode("idiv"              , "IDIV"              , 0x6c, 0, 0, null, 120); // 0#
		haikucode("if_acmpeq"         , "IF_ACMPEQ"         , 0xa5, 2, 2, 2, 121); // [1, 2]# 2: branchbyte1, branchbyte2
		haikucode("if_acmpne"         , "IF_ACMPEQ"         , 0xa6, 2, 2, 5, 122); // [1, 2]# 2: branchbyte1, branchbyte2
		haikucode("if_icmpeq"         , "IF_ICMPEQ"         , 0x9f, 2, 2, 2, 123); // [1, 2]# 2: branchbyte1, branchbyte2
		haikucode("if_icmpne"         , "IF_ICMPEQ"         , 0xa0, 2, 2, 5, 124); // [1, 2]# 2: branchbyte1, branchbyte2
		haikucode("if_icmplt"         , "IF_ICMPEQ"         , 0xa1, 2, 2, 4, 125); // [1, 2]# 2: branchbyte1, branchbyte2
		haikucode("if_icmpge"         , "IF_ICMPEQ"         , 0xa2, 2, 2, 3, 126); // [1, 2]# 2: branchbyte1, branchbyte2
		haikucode("if_icmpgt"         , "IF_ICMPEQ"         , 0xa3, 2, 2, 1, 127); // [1, 2]# 2: branchbyte1, branchbyte2
		haikucode("if_icmple"         , "IF_ICMPEQ"         , 0xa4, 2, 2, 6, 128); // [1, 2]# 2: branchbyte1, branchbyte2
		haikucode("ifeq"              , "IFEQ"              , 0x99, 2, 2, 2, 129); // [1, 2]# 2: branchbyte1, branchbyte2
		haikucode("ifne"              , "IFEQ"              , 0x9a, 2, 2, 5, 130); // [1, 2]# 2: branchbyte1, branchbyte2
		haikucode("iflt"              , "IFEQ"              , 0x9b, 2, 2, 4, 131); // [1, 2]# 2: branchbyte1, branchbyte2
		haikucode("ifge"              , "IFEQ"              , 0x9c, 2, 2, 3, 132); // [1, 2]# 2: branchbyte1, branchbyte2
		haikucode("ifgt"              , "IFEQ"              , 0x9d, 2, 2, 1, 133); // [1, 2]# 2: branchbyte1, branchbyte2
		haikucode("ifle"              , "IFEQ"              , 0x9e, 2, 2, 6, 134); // [1, 2]# 2: branchbyte1, branchbyte2
		haikucode("ifnonnull"         , "IFNONNULL"         , 0xc7, 2, 2, 5, 135); // [1, 2]# 2: branchbyte1, branchbyte2
		haikucode("ifnull"            , "IFNONNULL"         , 0xc6, 2, 2, 2, 136); // [1, 2]# 2: branchbyte1, branchbyte2
		haikucode("iinc"              , "IINC"              , 0x84, 2, 4, null, 137); // [9, 9]# 2: index, const
		haikucode("iinc1"             , "IINC1"             , 0x84, 1, 1, null, 138); // [9]# 1: index
		haikucode("iload"             , "ILOAD"             , 0x15, 1, 1, null, 139); // [9]# 1: index
		haikucode("iload_0"           , "ILOAD_0"           , 0x1a, 0, 0, null, 140); // 0#
		haikucode("iload_1"           , "ILOAD_1"           , 0x1b, 0, 0, null, 141); // 0#
		haikucode("iload_2"           , "ILOAD_2"           , 0x1c, 0, 0, null, 142); // 0#
		haikucode("iload_3"           , "ILOAD_3"           , 0x1d, 0, 0, null, 143); // 0#
		haikucode("imul"              , "IMUL"              , 0x68, 0, 0, null, 144); // 0#
		haikucode("ineg"              , "INEG"              , 0x74, 0, 0, null, 145); // 0#
		haikucode("instanceof"        , "INSTANCEOF"        , 0xc1, 2, 2, null, 146); // [1, 2]# 2: indexbyte1, indexbyte2
		haikucode("invokeinterface"   , "INVOKEINTERFACE"   , 0xb9, 4, 5, null, 147); // [1, 2, 9, 0]# 4: indexbyte1, indexbyte2, count, 0
		haikucode("invokespecial"     , "INVOKESPECIAL"     , 0xb7, 2, 2, null, 148); // [1, 2]# 2: indexbyte1, indexbyte2
		haikucode("invokestatic"      , "INVOKESPECIAL"     , 0xb8, 2, 2, null, 149); // [1, 2]# 2: indexbyte1, indexbyte2
		haikucode("invokevirtual"     , "INVOKEVIRTUAL"     , 0xb6, 2, 2, null, 150); // [1, 2]# 2: indexbyte1, indexbyte2
		haikucode("invokenative"      , "null"              , 0xb6, 2, 2, null, 151); // [1, 2]# 2: indexbyte1, indexbyte2
		haikucode("invokeshort"       , "INVOKESHORT"       , 0xb6, 2, 2, null, 152); // [1, 2]# 2: indexbyte1, indexbyte2
		haikucode("ior"               , "IOR"               , 0x80, 0, 0, null, 153); // 0#
		haikucode("irem"              , "IREM"              , 0x70, 0, 0, null, 154); // 0#
		haikucode("ireturn"           , "ARETURN"           , 0xac, 0, 0, null, 155); // 0#
		haikucode("ishl"              , "ISHL"              , 0x78, 0, 0, null, 156); // 0#
		haikucode("ishr"              , "ISHR"              , 0x7a, 0, 0, null, 157); // 0#
		haikucode("istore"            , "FSTORE"            , 0x36, 1, 1, null, 158); // [9]# 1: index
		haikucode("istore_0"          , "FSTORE_0"          , 0x3b, 0, 0, 0, 159); // 0#
		haikucode("istore_1"          , "FSTORE_0"          , 0x3c, 0, 0, 1, 160); // 0#
		haikucode("istore_2"          , "FSTORE_0"          , 0x3d, 0, 0, 2, 161); // 0#
		haikucode("istore_3"          , "FSTORE_0"          , 0x3e, 0, 0, 3, 162); // 0#
		haikucode("isub"              , "ISUB"              , 0x64, 0, 0, null, 163); // 0#
		haikucode("iushr"             , "IUSHR"             , 0x7c, 0, 0, null, 164); // 0#
		haikucode("ixor"              , "IXOR"              , 0x82, 0, 0, null, 165); // 0#
		haikucode("jsr"               , ""                  , 0xa8, 2, 2, null, 166); // [1, 2]# 2: branchbyte1, branchbyte2
		haikucode("jsr_w"             , ""                  , 0xc9, 4, 3, null, 167); // [1, 2, 3, 4]# 4: branchbyte1, branchbyte2, branchbyte3, branchbyte4
		haikucode("l2d"               , "L2D"               , 0x8a, 0, 0, null, 168); // 0#
		haikucode("l2f"               , "L2F"               , 0x89, 0, 0, null, 169); // 0#
		haikucode("l2i"               , "L2I"               , 0x88, 0, 0, null, 170); // 0#
		haikucode("ladd"              , "LADD"              , 0x61, 0, 0, null, 171); // 0#
		haikucode("laload"            , "LALOAD"            , 0x2f, 0, 0, null, 172); // 0#
		haikucode("land"              , "LAND"              , 0x7f, 0, 0, null, 173); // 0#
		haikucode("lastore"           , "LASTORE"           , 0x50, 0, 0, null, 174); // 0#
		haikucode("lcmp"              , "LCMP"              , 0x94, 0, 0, null, 175); // 0#
		haikucode("ldc_c"             , "LDC_C"             , 0x12, 1, 1, null, 176); // [9]# 1: index
		haikucode("ldc_s"             , "LDC_S"             , 0x12, 1, 1, 5, 177); // [9]# 1: index
		haikucode("ldc_i"             , "LDC_I"             , 0x12, 1, 1, 6, 178); // [9]# 1: index
		haikucode("ldc2_w_l"          , "LDC2_W_L"          , 0x14, 2, 2, 7, 179); // [1, 2]# 2: indexbyte1, indexbyte2
		haikucode("ldc_f"             , "LDC_F"             , 0x12, 1, 1, null, 180); // [9]# 1: index
		haikucode("ldc_w_s"           , "LDC_S"             , 0x13, 2, 2, 5, 181); // [1, 2]# 2: indexbyte1, indexbyte2
		haikucode("ldc_w_i"           , "LDC_I"             , 0x13, 2, 2, 6, 182); // [1, 2]# 2: indexbyte1, indexbyte2
		haikucode("ldc_fx"            , "LDC_FX"            , 0x12, 1, 1, null, 183); // [9]# 1: index
		haikucode("ldc_w_f"           , "LDC_F"             , 0x13, 2, 2, null, 184); // [1, 2]# 2: indexbyte1, indexbyte2
		haikucode("ldc_w_fx"          , "LDC_FX"            , 0x13, 2, 2, null, 185); // [1, 2]# 2: indexbyte1, indexbyte2
		haikucode("ldc2_w_d"          , "LDC2_W_D"          , 0x14, 2, 2, null, 186); // [1, 2]# 2: indexbyte1, indexbyte2
		haikucode("ldc2_w_dx"         , "LDC2_W_DX"         , 0x14, 2, 2, null, 187); // [1, 2]# 2: indexbyte1, indexbyte2
		haikucode("lconst_0"          , "LCONST_0"          , 0x09, 0, 0, 0, 188); // 0#
		haikucode("lconst_1"          , "LCONST_1"          , 0x0a, 0, 0, 1, 189); // 0#
		haikucode("ldiv"              , "LDIV"              , 0x6d, 0, 0, null, 190); // 0#
		haikucode("lload"             , "LLOAD"             , 0x16, 1, 1, null, 191); // [9]# 1: index
		haikucode("lload_0"           , "LLOAD_0"           , 0x1e, 0, 0, 0, 192); // 0#
		haikucode("lload_1"           , "LLOAD_0"           , 0x1f, 0, 0, 1, 193); // 0#
		haikucode("lload_2"           , "LLOAD_0"           , 0x20, 0, 0, 2, 194); // 0#
		haikucode("lload_3"           , "LLOAD_0"           , 0x21, 0, 0, 3, 195); // 0#
		haikucode("lmul"              , "LMUL"              , 0x69, 0, 0, null, 196); // 0#
		haikucode("lneg"              , "LNEG"              , 0x75, 0, 0, null, 197); // 0#
		haikucode("lookupswitch"      , "LOOKUPSWITCH"      , 0xab, 0, 0, null, 198); // 0# 4+: <0-3 bytes padding>, defaultbyte1, defaultbyte2, defaultbyte3, defaultbyte4, npairs1, npairs2, npairs3, npairs4, match-offset pairs...
		haikucode("lor"               , "LOR"               , 0x81, 0, 0, null, 199); // 0#
		haikucode("lrem"              , "LREM"              , 0x71, 0, 0, null, 200); // 0#
		haikucode("lreturn"           , "DRETURN"           , 0xad, 0, 0, null, 201); // 0#
		haikucode("lshl"              , "LSHL"              , 0x79, 0, 0, null, 202); // 0#
		haikucode("lshr"              , "LSHR"              , 0x7b, 0, 0, null, 203); // 0#
		haikucode("lstore"            , "LSTORE"            , 0x37, 1, 1, null, 204); // [9]# 1: index
		haikucode("lstore_0"          , "LSTORE_0"          , 0x3f, 0, 0, 0, 205); // 0#
		haikucode("lstore_1"          , "LSTORE_0"          , 0x40, 0, 0, 1, 206); // 0#
		haikucode("lstore_2"          , "LSTORE_0"          , 0x41, 0, 0, 2, 207); // 0#
		haikucode("lstore_3"          , "LSTORE_0"          , 0x42, 0, 0, 3, 208); // 0#
		haikucode("lsub"              , "LSUB"              , 0x65, 0, 0, null, 209); // 0#
		haikucode("lushr"             , "LUSHR"             , 0x7d, 0, 0, null, 210); // 0#
		haikucode("lxor"              , "LXOR"              , 0x83, 0, 0, null, 211); // 0#
		haikucode("monitorenter"      , "MONITORENTER"      , 0xc2, 0, 0, null, 212); // 0#
		haikucode("monitorexit"       , "MONITOREXIT"       , 0xc3, 0, 0, null, 213); // 0#
		haikucode("multianewarray"    , "MULTIANEWARRAY"    , 0xc5, 3, 6, null, 214); // [1, 2, 9]# 3: indexbyte1, indexbyte2, dimensions
		haikucode("new"               , "NEW"               , 0xbb, 2, 2, null, 215); // [1, 2]# 2: indexbyte1, indexbyte2
		haikucode("newarray"          , "ANEWARRAY"         , 0xbc, 1, 1, null, 216); // [9]# 1: atype
		haikucode("nop"               , "NOP"               , 0x00, 0, 0, null, 217); // 0#
		haikucode("pop"               , "POP"               , 0x57, 0, 0, null, 218); // 0#
		haikucode("pop2"              , "POP2"              , 0x58, 0, 0, null, 219); // 0#
		haikucode("putfield_l"        , "PUTFIELD_L"        , 0xb5, 2, 2, null, 220); // [1, 2]# 2: indexbyte1, indexbyte2
		haikucode("putfield_b"        , "PUTFIELD_B"        , 0xb5, 2, 2, null, 221); // [1, 2]# 2: indexbyte1, indexbyte2
		haikucode("putfield_c"        , "PUTFIELD_C"        , 0xb5, 2, 2, null, 222); // [1, 2]# 2: indexbyte1, indexbyte2
		haikucode("putfield_d"        , "PUTFIELD_D"        , 0xb5, 2, 2, null, 223); // [1, 2]# 2: indexbyte1, indexbyte2
		haikucode("putfield_f"        , "PUTFIELD_F"        , 0xb5, 2, 2, null, 224); // [1, 2]# 2: indexbyte1, indexbyte2
		haikucode("putfield_i"        , "PUTFIELD_I"        , 0xb5, 2, 2, null, 225); // [1, 2]# 2: indexbyte1, indexbyte2
		haikucode("putfield_j"        , "PUTFIELD_J"        , 0xb5, 2, 2, null, 226); // [1, 2]# 2: indexbyte1, indexbyte2
		haikucode("putfield_s"        , "PUTFIELD_S"        , 0xb5, 2, 2, null, 227); // [1, 2]# 2: indexbyte1, indexbyte2
		haikucode("putfield_z"        , "PUTFIELD_Z"        , 0xb5, 2, 2, null, 228); // [1, 2]# 2: indexbyte1, indexbyte2
		haikucode("putstatic_l"       , "PUTSTATIC_L"       , 0xb3, 2, 2, null, 229); // [1, 2]# 2: indexbyte1, indexbyte2
		haikucode("putstatic_b"       , "PUTSTATIC_B"       , 0xb3, 2, 2, null, 230); // [1, 2]# 2: indexbyte1, indexbyte2
		haikucode("putstatic_c"       , "PUTSTATIC_C"       , 0xb3, 2, 2, null, 231); // [1, 2]# 2: indexbyte1, indexbyte2
		haikucode("putstatic_d"       , "PUTSTATIC_D"       , 0xb3, 2, 2, null, 232); // [1, 2]# 2: indexbyte1, indexbyte2
		haikucode("putstatic_f"       , "PUTSTATIC_F"       , 0xb3, 2, 2, null, 233); // [1, 2]# 2: indexbyte1, indexbyte2
		haikucode("putstatic_i"       , "PUTSTATIC_I"       , 0xb3, 2, 2, null, 234); // [1, 2]# 2: indexbyte1, indexbyte2
		haikucode("putstatic_j"       , "PUTSTATIC_J"       , 0xb3, 2, 2, null, 235); // [1, 2]# 2: indexbyte1, indexbyte2
		haikucode("putstatic_s"       , "PUTSTATIC_S"       , 0xb3, 2, 2, null, 236); // [1, 2]# 2: indexbyte1, indexbyte2
		haikucode("putstatic_z"       , "PUTSTATIC_Z"       , 0xb3, 2, 2, null, 237); // [1, 2]# 2: indexbyte1, indexbyte2
		haikucode("ret"               , ""                  , 0xa9, 1, 1, null, 238); // [9]# 1: index
		haikucode("return"            , "RETURN"            , 0xb1, 0, 0, null, 239); // 0#
		haikucode("saload"            , "SALOAD"            , 0x35, 0, 0, null, 240); // 0#
		haikucode("sastore"           , "SASTORE"           , 0x56, 0, 0, null, 241); // 0#
		haikucode("sipush"            , "SIPUSH"            , 0x11, 2, 2, null, 242); // [1, 2]# 2: byte1, byte2
		haikucode("swap"              , "SWAP"              , 0x5f, 0, 0, null, 243); // 0#
		haikucode("tableswitch"       , "TABLESWITCH"       , 0xaa, 0, 0, null, 244); // 0# 4+: [0-3 bytes padding], defaultbyte1, defaultbyte2, defaultbyte3, defaultbyte4, lowbyte1, lowbyte2, lowbyte3, lowbyte4, highbyte1, highbyte2, highbyte3, highbyte4, jump offsets...
		haikucode("wide"              , ""                  , 0xc4, 0, 0, null, 245); // 0# 3/5: opcode, indexbyte1, indexbyte2 or iinc, indexbyte1, indexbyte2, countbyte1, countbyte2
		haikucode("wiinc"             , "WIINC"             , 0xc4, 6, 7, null, 246); // [9, 1, 2, 1, 2]# 6: iinc, indexbyte1, indexbyte2, countbyte1, countbyte2
		haikucode("breakpoint"        , ""                  , 0xca, 0, 0, null, 247); // 0#
		haikucode("impdep1"           , ""                  , 0xfe, 0, 0, null, 248); // 0#
		haikucode("impdep2"           , ""                  , 0xff, 0, 0, null, 249); // 0#
		haikucode("xxxunusedxxx"      , "XXXUNUSEDXXX"      , 0xba, 0, 0, null, 250); // 0#
	}


	public static void put(String msg) {
		msg=msg.toUpperCase();
		if (!bytecode.containsKey(msg)) {
			throw new IndexOutOfBoundsException(msg);
		}
		bytecode.get(msg).setUsed();
		if (bytecode.get(msg).mapto.length()>0) {
		} else {
			Verbose.error("ERROR: Bytecode '%s' is unimplemented!", msg);
		}

	}

	private static void haikucode(String op, String mapto, int obc, int len, int sig1, Integer constraint, int bc) {
		String msg=op.toUpperCase();
		bytecode.put(msg, new Bytecode(op.toUpperCase(), mapto, obc, bc, constraint));
		if (Haikufy.HAIKU_GenerateFullVM) {
			if (bytecode.get(msg).mapto.length()>0) {
				bytecode.get(msg).setUsed();
			}
		}
	}

	public static void printAllBCdefinesH(PrintOnChange haikuConfigh) {
		prepareIDX();
		haikuConfigh.printf("\n");
		haikuConfigh.printf("\n");
		haikuConfigh.printf("// bytecodes indexes\n");
		haikuConfigh.printf("\n");
		int invokeShortMax=HaikuVM.functionTable.getInvokeShortMax();

		for (int i = 0; i < invokeShortMax; i++) {
			haikuConfigh.printf("#define %-30s	%d\n", "OP_INVOKESHORT_"+HaikuVM.functionTable.get(i), i);
		}

        for (Bytecode bc : bytecode.values()) {
            if (!bc.used) continue;
            haikuConfigh.printf("#define %-30s   %d\n", "OP_"+bc.op, bc.index);
        }

        haikuConfigh.printf("\n");
        haikuConfigh.printf("// bytecodes not used by JAVA files (but may be by C)\n");
        haikuConfigh.printf("\n");
        for (Bytecode bc : bytecode.values()) {
            if (bc.used) continue;
            haikuConfigh.printf("#define %-30s   %d\n", "OP_"+bc.op, 255);
        }

		//haikuConfig.printf("#define %-30s	%d\n", "OP_unimplemented", 255);
		haikuConfigh.printf("\n");
		haikuConfigh.printf("\n");
		haikuConfigh.printf("// possible HaikuVM bytecode functions\n");
		haikuConfigh.printf("\n");
		for (Bytecode bc : bytecode.values()) {
			haikuConfigh.printf("extern void OPF_%s();\n", bc.op);
		}
		haikuConfigh.printf(    "extern void OPF_unimplemented();\n");

		haikuConfigh.printf("\n");
		haikuConfigh.printf("\n");
		haikuConfigh.printf("// HaikuVM bytecodes\n");
		haikuConfigh.printf("\n");
		for (Bytecode bc : bytecode.values()) {
			haikuConfigh.printf("//         %-30s  0x%2x -> %d\n", bc.op, bc.obc, bc.bc);
			haikuConfigh.printf("#define %-30s	           %d\n\n", "HBC_"+bc.op, bc.bc);
		}
		haikuConfigh.printf(    "#define %-30s             %d\n", "HBC_unimplemented", 255);

		Set<String> set=new HashSet<String>();
		haikuConfigh.printf("\n");
		haikuConfigh.printf("\n");
		haikuConfigh.printf("// HaikuVM define only used bytecodes for #if in Bytecodes.h to be used with HAIKU_AOTBytecodeAsSwitch\n");
		haikuConfigh.printf("\n");
		haikuConfigh.printf("\n");
		for (int i = 0; i<maxIndex; i++) {
            String msg;
			if (position[i]!=null) {
				String link=position[i].mapto;
				if (link.length()>0) {
					msg=link;
				} else {
					msg="unimplemented";
				}
			} else {
				msg="XXXUNUSEDXXX";
			}
			if (set.add(msg)) defineHBC(haikuConfigh, msg);
		}
	}

	private static void defineHBC(PrintOnChange haikuConfigh, String msg0) {
		haikuConfigh.printf("#define %-30s ", "HBC_DEF_"+msg0);
		for (int i = 0; i<maxIndex; i++) {
            String msg;
			if (position[i]!=null) {
				String link=position[i].mapto;
				if (link.length()>0) {
					msg=link;
				} else {
					msg="unimplemented";
				}
			} else {
				msg="XXXUNUSEDXXX";
			}
			if (msg0.equals(msg)) {
				haikuConfigh.printf("case %d: ", i);
			}
		}
		haikuConfigh.printf("\n");
	}

	private static int getUsed() {
		int count=0;
		for (Bytecode bc : bytecode.values()) {
			if (bc.used) count++;
		}
		return count;
	}

	private static void prepareIDX() {
		if (position!=null) return;
		position=new Bytecode[512];
		int invokeShortMax=HaikuVM.functionTable.getInvokeShortMax();

		// first: place all INVOKESHORT into position [0, invokeShortMax[
		for (int i = 0; i < invokeShortMax; i++) {
			position[i]=new Bytecode(HaikuVM.functionTable.get(i), "INVOKESHORT", i, i, null);
		}

        // second: place all constraint Bytecodes at their i%8 position
		for (Bytecode bc : bytecode.values()) {
			if (!bc.used) continue;
			for (int i = 0; i < position.length; i++) {
				if (bc.constraint!=null && position[i]==null && (i%8)==bc.constraint) {
					position[i]=bc;
					bc.setIndex(i);
					break;
				}
			}
		}
        // third: place the rest (unconstraint) Bytecodes
		for (Bytecode bc : bytecode.values()) {
			if (!bc.used) continue;
			for (int i = 0; i < position.length; i++) {
				if (bc.constraint==null && position[i]==null) {
					position[i]=bc;
					bc.setIndex(i);
					break;
				}
			}
		}
		maxIndex=0;
		for (int i = 0; i<position.length; i++) {
			if (position[i]!=null) {
				maxIndex=i+1;
			}
		}
		if (maxIndex>=256) {
		    // Huston we have a problem!
		    // thrown in printBCtableC(..)
		}
	}

	public static void printBCtableC(PrintOnChange haikuConfigc) throws Exception {
		prepareIDX();
		haikuConfigc.printf("\n");
		haikuConfigc.printf("\n");
		haikuConfigc.printf("#if DEBUG_DISPATCHER\n");
		haikuConfigc.printf("// bytecodes: %d\n", maxIndex);
		haikuConfigc.printf("\n");
		haikuConfigc.printf("const BytecodeFoo bytecodeFoo[] PROGMEM = {\n");
		for (int i = 0; i<maxIndex; i++) {
			String bc="", msg;
			if (position[i]!=null) {
				bc=position[i].op;
				if (position[i].mapto.length()>0) {
					msg="OPF_"+position[i].mapto+",";
				} else {
					msg="    unimplemented,";
				}
			} else {
				msg="    unused,";
			}
			HaikuVM.totalConstLength+=2;
			haikuConfigc.printf("\t%-30s // %3d %s\n", msg, i, bc);
		}
		haikuConfigc.printf("};\n");
		haikuConfigc.printf("#endif\n");

		haikuConfigc.printf("\n");
		haikuConfigc.printf("#if _DEBUG\n");
		for (int i = 0; i<256; i++) {
			if (position[i]!=null) {
				haikuConfigc.printf("char bytecodeDesc%03d[] PROGMEM =\"%s\";\n", i, position[i].op);
			}
		}
		haikuConfigc.printf("char bytecodeDesc999[] PROGMEM =\"unused\";\n");

		haikuConfigc.printf("\n");
		haikuConfigc.printf("const char* bytecodeDesc[] PROGMEM = {\n");
		for (int i = 0; i<256; i++) {
			if (position[i]!=null) {
				haikuConfigc.printf("\tbytecodeDesc%03d,\n", i);
			} else {
				haikuConfigc.printf("\tbytecodeDesc999,//%03d\n", i);
			}
		}
		haikuConfigc.printf("};\n");
		haikuConfigc.printf("#endif\n");

		int foos=HaikuVM.functionTable.getInvokeShortMax();
		int used=getUsed();
		int problem=used+foos;
		if (problem>256) {
			throw new Exception("ERROR: More than 256 bytecodes: "+problem+". Set 'InvokeShort = "+(256-used-1)+"' or less in HaikuVM.properties or use commandline option '--Config:InvokeShort "+(256-used-1)+"'");
		}
	}

	public static void printBytecodeLabels(PrintOnChange bytecodeDispatcher) throws IOException {
		prepareIDX();
		bytecodeDispatcher.printf("// bytecodes: %d\n", maxIndex);
		bytecodeDispatcher.printf("\n");

		bytecodeDispatcher.printf("static const label_t bytecodeLabels[] PROGMEM = {\n");
		for (int i = 0; i<maxIndex; i++) {
            String bc="", msg;
            String constraint=" ";
			if (position[i]!=null) {
				bc=position[i].op;
				String link=position[i].mapto;
				if (link.length()>0) {
					msg=link;
				} else {
					msg="unimplemented";
				}
				constraint=(position[i].constraint==null)?"-":(""+position[i].constraint);
			} else {
				msg="XXXUNUSEDXXX";
			}
			bytecodeDispatcher.printf("\t%-30s // %3d %s %s\n", "LABEL("+msg+"),", i, constraint, bc);
		}
		bytecodeDispatcher.printf("};\n");
	}
}
