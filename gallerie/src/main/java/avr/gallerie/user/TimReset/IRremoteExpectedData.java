package avr.gallerie.user.TimReset;

/**
 * 
 * Generate artificial data for IRremote
 * 
 */
public class IRremoteExpectedData {
    // Pulse parms are *50-100 for the Mark and *50+100 for the space
    // First MARK is the one after the long gap
    // pulse parameters in usec
    
    
    static final int NEC_HDR_MARK = 9000;
    static final int NEC_HDR_SPACE = 4500;
    static final int NEC_BIT_MARK = 560; // offset found by experiments
    static final int NEC_ONE_SPACE = 1600;
    static final int NEC_ZERO_SPACE = 560;
    static final int NEC_RPT_SPACE = 2250;

    static final long TOPBIT = 0x80000000L; //0b10000000000000000000000000000000;//max_int

    static final long SPEAKER_IR_POWER =   2155823295L; //  0b10000000011111110100000010111111;

    static private byte ledPin = 3;
    
    private static void mark(int time) {
        //TIMER_ENABLE_PWM();
//    	System.out.println("mark : "+time);
    	for (int i = 0; i < time/25.5; i++) {
    		System.out.println(58);
    		System.out.println(58);
    		System.out.println(116);
    		System.out.println(116);
    		System.out.println(116);
    		System.out.println(117);
    		System.out.println(116);
    		System.out.println(116);
    		System.out.println(58);
    		System.out.println(58);
    		System.out.println(58);
    		System.out.println(58);
    		System.out.println(58);
    		System.out.println(57);
    		System.out.println(58);
    		if(Math.random()<0.7) System.out.println(58);
		}
    }

    /* Leave pin off for time (given in microseconds) */
    private static void space(int time) {
        //TIMER_DISABLE_PWM();
//    	System.out.println("space: "+time);
    	for (int i = 0; i < time/25.5; i++) {
    		System.out.println(58);
    		System.out.println(58);
    		System.out.println(59);
    		System.out.println(58);
    		System.out.println(58);
    		System.out.println(58);
    		System.out.println(58);
    		System.out.println(59);
    		System.out.println(58);
    		System.out.println(58);
    		System.out.println(58);
    		System.out.println(58);
    		System.out.println(58);
    		System.out.println(57);
    		System.out.println(58);
    		if(Math.random()<0.7) System.out.println(58);
		}
    }

	private static void sendNEC(long data, int nbits) {
		System.out.println("Send " + data + "; nbits " + nbits);
        //enableIROut(38);

        mark(NEC_HDR_MARK);
        space(NEC_HDR_SPACE);
        for (int i = 0; i < nbits; i++) {
            if ((data & TOPBIT) != 0) {
                mark(NEC_BIT_MARK);
                space(NEC_ONE_SPACE);
            } else {
                mark(NEC_BIT_MARK);
                space(NEC_ZERO_SPACE);
            }
//            System.out.println(Long.toBinaryString(data).substring(Long.toBinaryString(data).length()-32));
//            System.out.println(Long.toBinaryString(TOPBIT));
            data <<= 1;
        }
        mark(NEC_BIT_MARK);
        space(0);
    }

    public static void main(String[] args) {
        sendNEC(SPEAKER_IR_POWER, 32);
    }
}
