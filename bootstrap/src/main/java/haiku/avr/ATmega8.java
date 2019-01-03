package haiku.avr;

public class ATmega8 extends AVRConstants {
	// values for ATmega8
	public static final boolean __AVR_ATmega8__ = true;
	public static final long F_CPU	= 8000000L;
    public static final int INT1 = 0;
    
    /* Constants */
    public static final int SPM_PAGESIZE = 64;
    public static final int RAMEND     = 0x45F;
    public static final int XRAMEND    = RAMEND;
    public static final int E2END      = 0x1FF;
    public static final int E2PAGESIZE = 4;
    public static final int FLASHEND   = 0x1FFF;


    /* Fuses */

    public static final int FUSE_MEMORY_SIZE = 2;

    /* Low Fuse Byte */
    //public static final int LFUSE_DEFAULT = FUSE_SUT0;

    /* High Fuse Byte */
    //public static final int HFUSE_DEFAULT = FUSE_SPIEN;


    /* Lock Bits */


    /* Signature */
    public static final int SIGNATURE_0 = 0x1E;
    public static final int SIGNATURE_1 = 0x93;
    public static final int SIGNATURE_2 = 0x07;


}
