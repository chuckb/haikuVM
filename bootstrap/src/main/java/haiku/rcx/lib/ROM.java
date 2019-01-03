package haiku.rcx.lib;

import static haiku.rcx.lib.ROM.__rcall3;
import static haiku.rcx.lib.ROM.refresh_display;
import haiku.vm.NativeCFunction;

/**
 * rom.h
 *
 * Interface to ROM routines.
 *
 * This file is a complete hack. Expect it to change significantly.
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
 * the specific language governing rights and limitations under the License.
 *
 * The Original Code is Librcx code, released February 9, 1999.
 *
 * The Initial Developer of the Original Code is Kekoa Proudfoot. Portions
 * created by Kekoa Proudfoot are Copyright (C) 1999 Kekoa Proudfoot. All Rights
 * Reserved.
 *
 * Contributor(s): Kekoa Proudfoot <kekoa@graphics.stanford.edu>
 */
public class ROM {

	public static final short BUTTON_VIEW = 0x02;
	public static final short BUTTON_PRGM = 0x04;
	public static final short BUTTON_RUN = 0x01;


	@NativeCFunction
	public static native char __rcall0(short a);

	@NativeCFunction
	public static native char __rcall1(short a, short p0);

	@NativeCFunction
	public static native char __rcall2(short a, short p0, short p1);

	@NativeCFunction
	public static native char __rcall3(short a, short p0, short p1, short p2);

	@NativeCFunction
	public static native char __rcall4(short a, short p0, short p1, short p2, short p3);

	/**
	 * @param error
	 * 1: restart from scratch and ready for new incoming firmware<br>
	 * error != 1: not supported
	 */
	@NativeCFunction
	public static native void exit(int error);

	////////////////////////////////////////////

	private static short ptr2short(Object obj) {
		return (short) System.identityHashCode(obj);
	}

	private static char __rcall2(short a, short code, Object obj) {
		return __rcall2(a, code, ptr2short(obj));
	}

	private static char __rcall2(short a, void_t valid, void_t nextbyte) {
		return __rcall2(a, ptr2short(valid), ptr2short(nextbyte));
	}

	private static char __rcall2(short a, async_t ptr0, byte[] ptr1) {
		return __rcall2(a, ptr2short(ptr0), ptr2short(ptr1));
	}

	private static char __rcall3(short a, void_t data, char maxlen, void_t len) {
		return __rcall3(a, ptr2short(data), (short)maxlen, ptr2short(len));
	}

	private static char __rcall4(short a, void_t ptr0, void_t ptr1, short code0, short code1) {
		return __rcall4(a, ptr2short(ptr0), ptr2short(ptr1), code0, code1);
	}

	private static char __rcall4(short a, short code, short param, void_t data, short len) {
		return __rcall4(a, code, param, ptr2short(data), len);
	}

	/**
	 * Init sensors ()
	 *
	 * Sets port 6 bits 0, 1, and 2 to output.
	 */

	public static void init_sensors() {
		__rcall0((short)0x1498);
	}

	public static class sensor_t {
		public byte type;
		public byte mode;
		public short raw;
		public short value;
		public boolean b;
	};

	public static class short_t {
		public short s;
	};

	public static class void_t {
		int v;
	};

	public static final short SENSOR_0 = 0x1000;
	public static final short SENSOR_1 = 0x1001;
	public static final short SENSOR_2 = 0x1002;

	public static final short SENSOR_TYPE_RAW = 0;
	public static final short SENSOR_TYPE_TOUCH = 1;
	public static final short SENSOR_TYPE_TEMP = 2;
	public static final short SENSOR_TYPE_LIGHT = 3;
	public static final short SENSOR_TYPE_ROT = 4;

	public static final short SENSOR_MODE_RAW = 0x00;
	public static final short SENSOR_MODE_BOOL = 0x20;
	public static final short SENSOR_MODE_EDGE = 0x40;
	public static final short SENSOR_MODE_PULSE = 0x60;
	public static final short SENSOR_MODE_PCT = 0x80;
	public static final short SENSOR_MODE_DEGC = 0xa0;
	public static final short SENSOR_MODE_DEGF = 0xc0;
	public static final short SENSOR_MODE_ANGLE = 0xe0;

	/**
	 * Read sensor (short code, sensor_t *sensor)
	 *
	 * code=1000: Read sensor 0
	 * code=1001: Read sensor 1
	 * code=1002: Read sensor 2
	 * You fill in type, mode, old sensor value, old boolean value Mode can be
	 * OR'ed with slope in 0..31; slopes are described elsewhere Function fills
	 * in raw value, new value, and new boolean value of sensor Return value is
	 * busy flag ?
	 */
	public static char read_sensor(short code, sensor_t sensor) {
		return __rcall2((short)0x14c0, code, sensor);
	}

	/**
	 * <pre>
	 * Set sensor active (short code)
	 *
	 * code=1000: Set sensor 0 active Call 3de0 (700a, 4) to turn on power to sensor 0
	 * code=1001: Set sensor 1 active Call 3de0 (700a, 2) to turn on power to sensor 1
	 * code=1002: Set sensor 2 active Call 3de0 (700a, 1) to turn on power to sensor 2
	 * </pre>
	 */
	public static void set_sensor_active(short code) {
		__rcall1((short)0x1946, code);
	}

	/**
	 * Set sensor passive (short code)
	 *
	 * code=1000: Set sensor 0 passive Call 3e9e (700a, 4) to turn off power to sensor 0
	 * code=1001: Set sensor 1 passive Call 3e9e (700a, 2) to turn off power to sensor 1
	 * code=1002: Set sensor 2 passive Call 3e9e (700a, 1) to turn off power to sensor 2
	 */
	public static void set_sensor_passive(short code) {
		__rcall1((short)0x19c4, code);
	}

	/**
	 * Shutdown sensors ()
	 *
	 * Sets port 6 bits 0, 1, and 2 to input.
	 */
	public static void shutdown_sensors() {
		__rcall0((short)0x1a22);
	}

	/**
	 * Init motors ()
	 *
	 * Does nothing, it is a no op.
	 */
	public static void init_motors() {
		/* __rcall0 (0x1a4a); *//* 1a4a does nothing */
	}

	public static final short MOTOR_0 = 0x2000;
	public static final short MOTOR_1 = 0x2001;
	public static final short MOTOR_2 = 0x2002;

	public static final short MOTOR_FWD = 01;
	public static final short MOTOR_REV = 02;
	public static final short MOTOR_STOP = 03;
	public static final short MOTOR_FLOAT = 04;

	/**
	 * Control motor (short code, short mode, short power)
	 *
	 * code=2000: Control motor 0 Calls 3ce6 to set motor 0 mode and power
	 * code=2001: Control motor 1 Calls 3ce6 to set motor 1 mode and power
	 * code=2002: Control motor 2 Calls 3ce6 to set motor 2 mode and power
	 * All versions use the following:
	 * modes:
	 *   1=forward,
	 *   2=backward,
	 *   3=stop,
	 *   4=float
	 * out of range values cause mode to be ignored power: valid are 0..7,
	 * values are taken modulo 8
	 */
	public static void control_motor(short code, short mode, int power) {
		__rcall3((short)0x1a4e, code, mode, (short)power);
	}

	/**
	 * Shutdown motors ()
	 *
	 * Does nothing, it is a no op.
	 */
	public static void shutdown_motors() {
		/* __rcall0 (0x1ab4); *//* 1ab4 does nothing */
	}

	/**
	 * Init buttons ()
	 *
	 * Sets software IRQ0 vector to IRQ0 handler, which does nothing
	 * Sets port 7 bits 6 and 7 to low (for view, prgm buttons) (useless?)
	 * Sets port 4 bit 2 to input (for run button)
	 * Enables IRQ0 Sets port 6 bits 5 and 6 to output, high (for LCD)
	 * Clears internal LCD state ef3e[15], ef4e, ef4f
	 *
	 * Maybe call this init_buttons_and_lcd?
	 */
	public static void init_buttons() {
		__rcall0((short)0x1aba);
	}

	public static final short BUTTONS_VIEW_SOUND = 0x301e;

	/**
	 * Play view button sound (short code)
	 *
	 * code=301e: Play view button sound Calls 3de0 to play system sound 1
	 * unqueued Pretty useless. Probably won't export this.
	 */
	public static void play_view_button_sound(short code) {
		__rcall1((short)0x1b32, code);
	}

	public static final short LCD_STANDING = 0x3006;
	public static final short LCD_WALKING = 0x3007;
	public static final short LCD_SENSOR_0_VIEW = 0x3008;
	public static final short LCD_SENSOR_0_ACTIVE = 0x3009;
	public static final short LCD_SENSOR_1_VIEW = 0x300a;
	public static final short LCD_SENSOR_1_ACTIVE = 0x300b;
	public static final short LCD_SENSOR_2_VIEW = 0x300c;
	public static final short LCD_SENSOR_2_ACTIVE = 0x300d;
	public static final short LCD_MOTOR_0_VIEW = 0x300e;
	public static final short LCD_MOTOR_0_REV = 0x300f;
	public static final short LCD_MOTOR_0_FWD = 0x3010;
	public static final short LCD_MOTOR_1_VIEW = 0x3011;
	public static final short LCD_MOTOR_1_REV = 0x3012;
	public static final short LCD_MOTOR_1_FWD = 0x3013;
	public static final short LCD_MOTOR_2_VIEW = 0x3014;
	public static final short LCD_MOTOR_2_REV = 0x3015;
	public static final short LCD_MOTOR_2_FWD = 0x3016;
	public static final short LCD_DATALOG = 0x3018;
	public static final short LCD_DOWNLOAD = 0x3019;
	public static final short LCD_UPLOAD = 0x301a;
	public static final short LCD_BATTERY = 0x301b;
	public static final short LCD_RANGE_SHORT = 0x301c;
	public static final short LCD_RANGE_LONG = 0x301d;
	public static final short LCD_ALL = 0x3020;

	/**
	 * Set lcd segment (short code)
	 *
	 * code=3006: standing figure
	 * code=3007: walking figure
	 * code=3008: sensor 0 view selected
	 * code=3009: sensor 0 active
	 * code=300a: sensor 1 view selected
	 * code=300b: sensor 1 active
	 * code=300c: sensor 2 view selected
	 * code=300d: sensor 2 active
	 * code=300e: motor 0 view selected
	 * code=300f: motor 0 backward arrow
	 * code=3010: motor 0 forward arrow
	 * code=3011: motor 1 view selected
	 * code=3012: motor 1 backward arrow
	 * code=3013: motor 1 forward arrow
	 * code=3014: motor 2 view selected
	 * code=3015: motor 2 backward arrow
	 * code=3016: motor 2 forward arrow
	 *
	 * code=3018: datalog indicator, multiple calls add 4 quarters clockwise
	 * code=3019: download in progress, multiple calls adds up to 5 dots to right
	 * code=301a: upload in progress, multiple calls removes up to 5 dots from left
	 * code=301b: battery low
	 * code=301c: short range indicator
	 * code=301d: long range indicator
	 * code=3020: all segments
	 * All codes set bits in @ef43[10] array to affect display
	 * Display must be refreshed for changes to become visible
	 */
	public static void set_lcd_segment(short code) {
		__rcall1((short)0x1b62, code);
	}

	/**
	 * Clear lcd segment (short code)
	 *
	 * code=3006: standing figure
	 * code=3007: walking figure
	 * code=3008: sensor 0 view selected
	 * code=3009: sensor 0 active
	 * code=300a: sensor 1 view selected
	 * code=300b: sensor 1 active
	 * code=300c: sensor 2 view selected
	 * code=300d: sensor 2 active
	 * code=300e: motor 0 view selected
	 * code=300f: motor 0 backward arrow
	 * code=3010: motor 0 forward arrow
	 * code=3011: motor 1 view selected
	 * code=3012: motor 1 backward arrow
	 * code=3013: motor 1 forward arrow
	 * code=3014: motor 2 view selected
	 * code=3015: motor 2 backward arrow
	 * code=3016: motor 2 forward arrow
	 *
	 * code=3018: datalog indicator
	 * code=3019: upload/download indicators
	 * code=301a: upload/download indicators
	 * code=301b: battery low
	 * code=301c: range indicators
	 * code=301d: range indicators
	 * All codes clear bits in @ef43[10] array to affect display
	 * Display must be refreshed for changes to become visible
	 */
	public static void clear_lcd_segment(short code) {
		__rcall1((short)0x1e4a, code);
	}

	public static final short BUTTONS_READ = 0x3000;

	/**
	 * Read buttons (short code, short *ptr)
	 *
	 * code=3000: Read buttons Set *ptr = 0 If port 7 bit 6 (@ffbe & 40) is set,
	 * *ptr |= 02 (view button pressed) If port 7 bit 7 (@ffbe & 80) is set,
	 * *ptr |= 04 (prgm button pressed) If port 4 bit 2 (@ffb7 & 04) is set,
	 * *ptr |= 01 (run button pressed)
	 */
	public static void read_buttons(int code, short_t ptr) {
		__rcall2((short)0x1fb6, (short)code, ptr);
	}

	public static final short LCD_SIGNED = 0x3001;
	public static final short LCD_DECIMAL_0 = 0x3002;
	public static final short LCD_DECIMAL_1 = 0x3003;
	public static final short LCD_DECIMAL_2 = 0x3004;
	public static final short LCD_DECIMAL_3 = 0x3005;
	public static final short LCD_PROGRAM = 0x3017;
	public static final short LCD_UNSIGNED = 0x301f;

	/**
	 * Set lcd number (short code, short value, short pointcode)
	 *
	 * code=3001: Set lcd main number signed Set main number on display to
	 * signed value, with no leading zeros If value > 9999, displayed value is
	 * 9999 If value < -9999, displayed value is -9999
	 *
	 * code=3017: Set lcd program number
	 * Set program number on display to value, use pointcode=0
	 * If value < 0, no value is displayed
	 * If value > 0, no value is displayed
	 * Pointcode is ignored, no real need to set to zero
	 *
	 * code=301f: Set lcd main number unsigned Set main number on display to unsigned value, with
	 * leading zeros Value is unsigned, so it is never less than 0
	 * For 3001, 301f:
	 * pointcode=3002: no decimal point
	 * pointcode=3003: 000.0 format
	 * pointcode=3004: 00.00 format
	 * pointcode=3005: 0.000 format
	 */
	public static void set_lcd_number(int code, int value, int pointcode) {
		__rcall3((short)0x1ff2, (short)code, (short)value, (short)pointcode);
	}

	/**
	 * Sets a number to be displayed in the LCD.
	 * It does not show up until refresh() is called.
	 * @param aCode SIGNED=0x3001, PROGRAM=0x3017, UNSIGNED=0x301f
	 * @param aValue The value to be displayed.
	 * @param aPoint 0x3002 - 0x3005, indicating decimal point location.
	*/
	public static void setNumber (int aCode, int aValue, int aPoint)
	{
		__rcall3 ((short)0x1ff2, (short)aCode, (short)aValue, (short)aPoint);
	}

	/**
	 * Shows a number to be displayed in the LCD.
	 * @param aValue The positive value in [0, 9999] to be displayed.
	 */
	public static void showNumber(int aValue) {
	    setNumber (0x301f, aValue, 0x3002);
	    refresh_display();
	}

	/**
	 * Clear display ()
	 *
	 * Clears all bits in @ef43[10] array
	 * Display must be refreshed for changes to become visible
	 */
	public static void clear_display() {
		__rcall0((short)0x27ac);
	}

	/**
	 * Refresh display
	 *
	 * Sets @ef3e[5] to { 7c, c8, 80, e0, 80 }
	 * Calls 283c (ef3e) to have ef3e array sent to lcd using port 5 bits 5,6
	 *
	 * This function takes about 1.58 ms to run, in case that's important to you
	 */
	public static void refresh_display() {
		__rcall0((short)0x27c8);
	}

	/**
	 * Shutdown buttons ()
	 *
	 * Sets port 7 bits 6 and 7 to low (for view, prgm buttons) (useless?)
	 * Sets port 4 bit 2 to input (for run button)
	 * Disables IRQ0 Sets port 6 bits 5 and 6 to input (for LCD)
	 *
	 * Maybe call this shutdown_buttons_and_lcd?
	 */
	public static void shutdown_buttons() {
		__rcall0((short)0x27f4);
	}

	/**
	 * Init power ()
	 *
	 * Sets software IRQ1 vector to IRQ1 handler, which disables IRQ1 on IRQ1
	 * Sets port 4 bit 1 to input (on/off button input)
	 * Disables IRQ1 Sets port 5 bit 2 to output (RAM enable)
	 * Sets sleep mode to software standby, recovery time of 131,072 states
	 */
	public static void init_power() {
		__rcall0((short)0x2964);
	}

	public static final short SOUND_QUEUED = 0x4003;
	public static final short SOUND_UNQUEUD = 0x4004;

	/**
	 * Play system sound (short code, short sound)
	 *
	 * code=4003: Play unqueued system sound
	 * Calls 3de0 to play an unqueued system sound, index of sound is sound
	 *
	 * code=4004: Play queued system sound
	 * Calls 3de0 to play a queued system sound, index of sound is sound
	 * In either case, if sound is not in 0..5, the sound is 4 (low buzz)
	 *
	 * Why is this an 0x4000 function?
	 * Because it is called for power on/off sounds only
	 */
	public static void play_system_sound(int code, int sound) {
		__rcall2((short)0x299a, (short)code, (short)sound);
	}

	public static final short POWER_KEY = 0x4000;
	public static final short POWER_BATTERY = 0x4001;

	/**<pre>
	 * Get power status (short code, short *ptr)
	 *
	 * code=4000: Read on/off key Read port 4 bit 1 (@ffb7 & 0x02)
	 * Set *ptr to 0x20 if bit is set, set *ptr to 0x00 otherwise
	 *
	 * code=4001: Read battery power Read a/d register d (@ffe6)
	 * Set *ptr to value from a/d register
	 *
	 * Units are strange, multiply by 43988 then divide by 1560 to get mV
	 * </pre>
	 */
	public static void get_power_status(short code, short_t ptr) {
		__rcall2((short)0x29f2, code, ptr);
	}

	/**
	 * Shutdown power ()
	 *
	 * Stack must be on-chip before calling this function
	 *
	 * Sets port 4 bit 1 to input (on/off button input)
	 * Enables IRQ1 Sets port 5 bit 2 to high (enable RAM low power mode)
	 * Goes to sleep Sets port 5 bit 2 to low (disable RAM low power mode)
	 */
	public static void shutdown_power() {
		__rcall0((short)0x2a62);
	}

	/**
	 * The init routine is at 03ae
	 *    Clears some memory, specifically ee5e-f000
	 *    Copies default interrupt handler 046a into RAM interrupt vector table
	 *    Calls main at 0580
	 *
	 */
	public static void rom_init() {
		__rcall0((short)0x03ae);
	}

	public static void rom_main() {
		__rcall0((short)0x0580);
	}

	/**
	 *	070c - void rom_update (r6=dataptr)
	 */
	public static void rom_update() {
		__rcall1((short)0x070c, (short)0x8000);
	}

	/**
	 *	0d44 - void rom_init_program (r6=unuseddataptr)
	 */
	public static void rom_init_program() {
		__rcall1((short)0x0d44, (short)0x8000);
	}

	/**
	 *	8000 - void rom_firmware_init ()
	 */
	public static void rom_firmware_init() {
		__rcall0((short)0x8000);
	}




	/**
	 * Init serial (void *ptr0, void *ptr1, char code0, char code1)
	 *
	 * Initializes a lot of port stuff (what exactly?) Initializes the serial
	 * routines Sets *ptr0 to run when valid data received (ptr0 is a byte) Uses
	 * *ptr1 as the serial receive counter (ptr1 is a byte) Uses and expects
	 * packet headers if code0 is 1 Uses and expects data/checksum complements
	 * if code1 is 1
	 *
	 * Using anything but code0=1 code1=1 breaks things when sending data
	 * code0=0 code1=0: short packets have extra byte, long packets no opcode
	 * code0=1 code1=0: short packets ok, long packets opcode complemented
	 * code0=0 code1=1: short packets have extra byte, long packets no opcode
	 * code0=1 code1=1: all packets ok
	 *
	 * Not sure about how code0 and code1 affect correctness while receiving.
	 *
	 * Seems to operate independently of init_timers Not quite though, because
	 * init_timers decrements a timeout counter
	 *
	 * This function requires bits in the port 5 data direction register to be
	 * cleared. The standard firmware does this by calling init_timer followed
	 * by init_power, in that order. Init_timer clears values that init_power
	 * eventually stores to the data direction register.
	 */
	public static void init_serial(void_t ptr0, void_t ptr1, int code0, int code1) {
		__rcall4((short)0x30d0, ptr0, ptr1, (short)code0, (short)code1);
	}

	public static final short SERIAL_RANGE = 0x1770;

	/**
	 * Set range long (short code)
	 *
	 * code=1770: Set range long Clear port 4 bit 0 (@ffb7 |= 01) (sets range
	 * long)
	 */
	public static void set_range_long(short code) {
		__rcall1((short)0x3250, code);
	}

	/* public static final short SERIAL_RANGE 0x1770 */

	/**
	 * Set range short (short code)
	 *
	 * code=1770: Set range short Set port 4 bit 0 (@ffb7 &= ~01) (sets range
	 * short)
	 */
	public static void set_range_short(short code) {
		__rcall1((short)0x3266, code);
	}

	public static final short SERIAL_POINTER = 0x1771;
	public static final short SOUND_TONE = 0x1773;
	public static final short SOUND_SYSTEM = 0x1772;

	/**
	 * Play sound or set data pointer ? (short code, short param0, short param1)
	 *
	 * code=1771: Set data pointer (short code, char *ptr, short unused) Sets
	 * incoming data pointer to ptr Also resets internal transfer data sequence
	 * number Data pointer is used for IR opcode 45 If param0 is NULL, then data
	 * pointer is reset to invalid
	 *
	 * code=1773: Play tone (short code, short freq,
	 * char duration) If freq < 1f or freq > 4e20, call 3de0 to play a pause for
	 * duration Call looks like 3de0 (700b, 0, duration, 7) Otherwise, lookup
	 * freq in table: Freq Div Ctl 1f-7a 400 6 7b-1e9 100 7 1ea-3d3 40 4 3d4-f51
	 * 20 5 f52-4e20 8 2 Then call 3de0 (700b, (7a1200/div)/freq, duration, ctl)
	 * Tone is queued
	 *
	 * code=1772: Play system sound (short code, short unused,
	 * short index) Call 3de0 to play system sound with specified index Sound is
	 * queued
	 */
	public static void play_sound_or_set_data_pointer(short code, short param0,
			short param1) {
		__rcall3((short)0x327c, code, param0, param1);
	}

	/**
	 * Reset minute timer (short code)
	 *
	 * Calls 3e9e (700e) to reset internal minute timer
	 * The code for this function, which is typically either 0 or 1774, is ignored
	 */
	public static void reset_minute_timer(short code) {
		__rcall1((short)0x339a, code);
	}

	/**
	 * Receive data (void *data, char maxlen, char *len)
	 *
	 * Receive up to maxlen bytes into ptr. Add the number of bytes received to
	 * len. Flush receive buffer and clear data ready flag. Does not set len to
	 * the number of bytes received! RXI details follow: ROM assumes a
	 * particular opcode-to-message length mapping:
	 * length = (op & 7); if (length > 5)
	 * length -= 6; Op 45 received specially
	 * If incoming data
	 * pointer set, transferred data stored there
	 * Else transferred data not
	 * stored Total length is not checked, data will overflow if too much sent!
	 * Op 45 sequence numbers automatically parsed and checked Op 45 returns
	 * short index, byte sent_checksum, byte received_checksum
	 */
	public static void receive_data(void_t data, char maxlen, void_t len) {
		__rcall3((short)0x33b0, data, maxlen, len);
	}

	/**
	 * Check for data (char *valid, char **nextbyte)
	 *
	 * If data is ready to be received, set valid to 1, else set value to 0 Set
	 * *nextbyte to the next byte to the current transfer data address Because
	 * address 0 is in ROM, passing NULL as nextbyte is acceptable
	 */
	public static void check_for_data(void_t valid, void_t nextbyte) {
		__rcall2((short)0x3426, valid, nextbyte);
	}

	public static final short SERIAL_NO_POINTER = 0x1775;
	public static final short SERIAL_USE_POINTER = 0x1776;

	/**
	 * Send data (short code, byte opcode, byte *data, short len)
	 *
	 * code=1775: Send data short (short code, byte unused, byte *data, short len)
	 *
	 * Builds short message in temporary global memory Sends data[len] out
	 * serial port Header/complements added if specified in init_serial Extra
	 * trailing byte sent if no headers!
	 *
	 * code=1776: Send data long (short code, byte opcode, byte *data, short len)
	 * Builds message on the fly, so
	 * data[len] must persist during send Sends data[len] out serial port
	 * Header/complements added if specified in init_serial Opcode is opcode of
	 * request, not opcode of reply Both versions return 0 on success, 4c if
	 * serial port is busy sending
	 */
	public static char send_data(short code, short param, void_t data, short len) {
		return __rcall4((short)0x343e, code, param, data, len);
	}

	/**
	 * Shutdown serial ()
	 *
	 * Stops serial timer (TMR1) Stops serial tranmit/receive Sets port 4 bit 0
	 * to output, low Sets port 6 bit 7 to output, low Sets port 5 bits 0 and 1
	 * to output, low
	 */
	public static void shutdown_serial() {
		__rcall0((short)0x3636);
	}

	/**
	 * Init port 6 bit 3 ()
	 *
	 * Nobody I know of knows what this does.
	 */
	public static void init_port_6_bit_3() {
		__rcall0((short)0x3692);
	}

	/**
	 * Shutdown port 6 bit 3 ()
	 *
	 * Nobody I know of knows what this does.
	 */
	public static void shutdown_port_6_bit_3() {
		__rcall0((short)0x36aa);
	}

	/** ptr0 struct for init_timer, unused for now */
	public static class async_t {
	    short serial;
	    //short timers[4];
	    short timer0, timer1, timer2, timer3;
	    short clock_minutes;
	    short shutoff_minutes;
	    //short task_wakeup[10];
	    short task_wakeup0, task_wakeup1, task_wakeup2, task_wakeup3, task_wakeup4, task_wakeup5, task_wakeup6, task_wakeup7, task_wakeup8, task_wakeup9;
	    //short motor_wakeup[3];
	    short motor_wakeup0, motor_wakeup1, motor_wakeup2;
	};

	/**
	 * <pre>
	 * Init timer (void *ptr0, void *ptr1)
	 *
	 * Sets async data pointer (@efc6) to ptr0
	 * Sets handler dispatch table pointer (@efc8) to ptr1
	 * Initializes rom interrupt handlers, including ocia, the millisecond handler
	 * Initializes a lot of other stuff
	 *
	 * Specifically, what stuff?
	 *
	 * How are tables updated after this? (offsets in hex)
	 * For firmware, ptr0=cc06, ptr1=cc00
	 * ptr1+00 updated to run by A/D handler on A/D completion
	 * ptr1+01 updated to run by OCIA if motor timer (word (ptr0+22)[3]) reaches 1
	 * ptr1+02 updated to run by OCIA every 130 ms
	 * ptr1+03 updated to run by OCIA every 130 ms
	 * ptr1+04 updated to run by RXI on successful message receipt
	 * ptr0+00 inited by init_serial_manager, updated by RXI, ERI, OCIA handlers
	 * firmware timers (word (ptr0+02)[4]) incremented every 1/10th second
	 * minutes on clock/watch (word ptr0+0a) incremented every 60 seconds
	 * minutes to power off (word ptr0+0c) decremented every 60 seconds
	 * per task wakeup delays (word (ptr0+0e)[10]) decremented every 1/100th second
	 * motor timers (word (ptr0+22)[3]) decremented every millisecond if non-zero
	 * internal millisecond counters (efcf-efd4) updated as appropriate
	 *
	 * That's all the tables. What's updated as a result of this routine only?
	 *
	 * Because this function clears the shadow registers in ROM (fd80-fd86),
	 * it is important that this function be called before any other
	 * initialization routines.  Very important!!!
	 * </pre>
	 */
	public static void init_timer(async_t ptr0, byte[] ptr1) {
		__rcall2((short) 0x3b9a, ptr0, ptr1);
	}

	public static final short TIMER_SOUND_PLAYING = 0x700c;

	/**
	 * Get sound playing flag (short code, char *ptr)
	 *
	 * code=700c: Copies sound playing flag (@efff) to *ptr
	 */
	public static void get_sound_playing_flag(short code, void_t ptr) {
		__rcall2((short)0x3ccc, code, ptr);
	}

	public static final short OUTPUT_SENSOR = 0x700a;
	public static final short OUTPUT_QUEUED = 0x700b;
	public static final short OUTPUT_UNQUEUED = 0x700d;

	/**
	 * Control output (short code, short param0, char param1, char param2)
	 *
	 * code=700a: Control sensor output (short code, short bits, char u0, char
	 * u1) Sets output to sensors by setting @efd5 |= bits bits: 0x01=sensor 2,
	 * 0x02=sensor 1, 0x04=sensor 0
	 * code=700b: Play queued sound (short code,
	 * short type, char p0, char p1) Play a queued sound If type = 0, Play pause
	 * (short code, short type, char duration, char u1) Pause for duration ms If
	 * type = 1, Play sound (short code, short type, char sound, char u1) Play
	 * the specified system sound If type > 1, Play tone (short code, short
	 * pitch, char duration, char ctl) Play the tone specified by pitch/ctl for
	 * duration ms
	 * code=700d: Play unqueued sound (short code, short type, char
	 * p0, char p1) Play an unqueued sound; flush sound queue before playing If
	 * type = 0, Play pause (short code, short type, char duration, char u1)
	 * Pause for duration ms If type = 1, Play sound (short code, short type,
	 * char sound, char u1) Play the specified system sound
	 */
	public static void control_output(short code, short sp0, short sp1, short sp2) {
		__rcall4((short)0x3de0, code, sp0, sp1, sp2);
	}

	/**
	 * Shutdown timer ()
	 *
	 * Deactivates speaker output
	 * Turns off timer 0 (sound generation)
	 * Stops a/d conversion
	 * Resets motor state and sets motors to stop
	 * Deactivates millisecond handler
	 */
	public static void shutdown_timer() {
		__rcall0((short)0x3ed4);
	}


	/* ROM interrupt vectors */

	// public static final short VECTOR(addr) (*(void (**)(void))(addr))
	// public static final short VECTOR_RESET VECTOR(0xfd90)
	// public static final short VECTOR_NMI VECTOR(0xfd92)
	// public static final short VECTOR_IRQ0 VECTOR(0xfd94)
	// public static final short VECTOR_IRQ1 VECTOR(0xfd96)
	// public static final short VECTOR_IRQ2 VECTOR(0xfd98)
	// public static final short VECTOR_ICIA VECTOR(0xfd9a)
	// public static final short VECTOR_ICIB VECTOR(0xfd9c)
	// public static final short VECTOR_ICIC VECTOR(0xfd9e)
	// public static final short VECTOR_ICID VECTOR(0xfda0)
	// public static final short VECTOR_OCIA VECTOR(0xfda2)
	// public static final short VECTOR_OCIB VECTOR(0xfda4)
	// public static final short VECTOR_FOVI VECTOR(0xfda6)
	// public static final short VECTOR_CMI0A VECTOR(0xfda8)
	// public static final short VECTOR_CMI0B VECTOR(0xfdaa)
	// public static final short VECTOR_OVI0 VECTOR(0xfdac)
	// public static final short VECTOR_CMI1A VECTOR(0xfdae)
	// public static final short VECTOR_CMI1B VECTOR(0xfdb0)
	// public static final short VECTOR_OVI1 VECTOR(0xfdb2)
	// public static final short VECTOR_ERI VECTOR(0xfdb4)
	// public static final short VECTOR_RXI VECTOR(0xfdb6)
	// public static final short VECTOR_TXI VECTOR(0xfdb8)
	// public static final short VECTOR_TEI VECTOR(0xfdba)
	// public static final short VECTOR_A_D VECTOR(0xfdbc)
	// public static final short VECTOR_WOVF VECTOR(0xfdbe)
}
