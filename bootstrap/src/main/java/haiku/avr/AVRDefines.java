package haiku.avr;

import static haiku.vm.MemoryAccess.getMemory8;
import static haiku.vm.MemoryAccess.setMemory8;

public class AVRDefines {

	public static int _BV(int bit) {
		return 1 << bit;
	}

	/**
	 * NOOP<br>
	 * 
	 * #define interrupts() sei()
	 * @see haiku.avr.lib.arduino.WProgram.interrupts() 
	 */ 
	public static void sei() {
	}

	/**
	 * NOOP<br>
	 * 
	 * #define noInterrupts() cli()
	 * @see haiku.avr.lib.arduino.WProgram.noInterrupts() 
	 */ 
	public static void cli() {
	}

	public static void cbi(int mem, int bit) {
		setMemory8(mem, getMemory8(mem) & ~_BV(bit));
	}

	/**
	 * Set one bit at memory address adr.
	 * 
	 * _SFR_BYTE(adr) |= (1 << (bit)
	 * 
	 * @param mem
	 * @param bit
	 */
	public static void sbi(int mem, int bit) {
		setMemory8(mem, getMemory8(mem) | _BV(bit));
	}


	/**
	 * mem[add]|=val8
	 * 
	 * @param add
	 * @param val8
	 */
	public static void setMem(int add, int val8) {
		setMemory8(add, getMemory8(add) | val8);
	}

	/**
	 * mem[add]&=val8
	 * 
	 * @param add
	 * @param val8
	 */
	public static void unsetMem(int add, int val8) {
		setMemory8(add, getMemory8(add) & ~val8);
	}

	/**
	 * 
	 * http://www.codeforge.com/read/52725/rand.c__html
	 * 
	 */
	private static int _next = 1;

	public static void srand(int seed) {
		_next = seed;
	}

	public static int rand() {

		_next = (((_next * 0x4E6D) & 0xffff) + 12345) & 0x7fff;

		return _next;
	}	

}
