package avr.gallerie.user.bachiander;

/*
 *    LedControl.h - A library for controling Leds with a MAX7219/MAX7221
 *    Copyright (c) 2007 Eberhard Fahle
 *
 *    Permission is hereby granted, free of charge, to any person
 *    obtaining a copy of this software and associated documentation
 *    files (the "Software"), to deal in the Software without
 *    restriction, including without limitation the rights to use,
 *    copy, modify, merge, publish, distribute, sublicense, and/or sell
 *    copies of the Software, and to permit persons to whom the
 *    Software is furnished to do so, subject to the following
 *    conditions:
 *
 *    This permission notice shall be included in all copies or
 *    substantial portions of the Software.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *    EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 *    OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *    NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 *    HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *    WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *    FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 *    OTHER DEALINGS IN THE SOFTWARE.
 */


import haiku.vm.NativeCHeader;
import haiku.vm.NativeCppFunction;
import haiku.vm.NativeCppMethod;

@NativeCHeader(cImpl = "#include <LedControl.h>")
public class LedControl {

	private Object realSubject;

	@NativeCppFunction(cImpl = "return (jobject) new LedControl(arg1, arg2, arg3, arg4);")
	private native static Object cppConstructor(int dataPin, int clkPin, int csPin, int numDevices);
	private native Object getCObject();

	/*
	 * Create a new controler
	 * Params :
	 * dataPin		pin on the Arduino where data gets shifted out
	 * clockPin		pin for the clock
	 * csPin		pin for selecting the device
	 * numDevices	maximum number of devices that can be controled
	 */
	LedControl(int dataPin, int clkPin, int csPin, int numDevices) {
		realSubject=cppConstructor(dataPin, clkPin, csPin, numDevices);
	}

	/*
	 * Gets the number of devices attached to this LedControl.
	 * Returns :
	 * int	the number of devices on this LedControl
	 */
	public native int getDeviceCount();

	/*
	 * Set the shutdown (power saving) mode for the device
	 * Params :
	 * addr	The address of the display to control
	 * status	If true the device goes into power-down mode. Set to false
	 *		for normal operation.
	 */
	public native void shutdown(int addr, boolean status);

	/*
	 * Set the number of digits (or rows) to be displayed.
	 * See datasheet for sideeffects of the scanlimit on the brightness
	 * of the display.
	 * Params :
	 * addr	address of the display to control
	 * limit	number of digits to be displayed (1..8)
	 */
	public native void setScanLimit(int addr, int limit);

	/*
	 * Set the brightness of the display.
	 * Params:
	 * addr		the address of the display to control
	 * intensity	the brightness of the display. (0..15)
	 */
	public native void setIntensity(int addr, int intensity);

	/*
	 * Switch all Leds on the display off.
	 * Params:
	 * addr	address of the display to control
	 */
	public native void clearDisplay(int addr);

	/*
	 * Set the status of a single Led.
	 * Params :
	 * addr	address of the display
	 * row	the row of the Led (0..7)
	 * col	the column of the Led (0..7)
	 * state	If true the led is switched on,
	 *		if false it is switched off
	 */
	@NativeCppFunction(cImpl = 
		"#define cls avr_gallerie_user_bachiander_LedControl\n" +
		"jfieldID fid = (*env)->GetFieldID(env, cls, realSubject, \"Ljava/lang/Object;\");\n" +
		"if (fid == 0)\n" +
		"    return;\n" +
		"LedControl* cobj = (LedControl*)((*env)->GetObjectField(env, obj, fid));\n" +
		"cobj -> setLed(arg1, arg2, arg3, arg4);")
	public native void setLed(int addr, int row, int col, boolean state);

	/*
	 * Set all 8 Led's in a row to a new state
	 * Params:
	 * addr	address of the display
	 * row	row which is to be set (0..7)
	 * value	each bit set to 1 will light up the
	 *		corresponding Led.
	 */
	public native void setRow(int addr, int row, byte value);

	/*
	 * Set all 8 Led's in a column to a new state
	 * Params:
	 * addr	address of the display
	 * col	column which is to be set (0..7)
	 * value	each bit set to 1 will light up the
	 *		corresponding Led.
	 */
	public native void setColumn(int addr, int col, byte value);

	/*
	 * Display a hexadecimal digit on a 7-Segment Display
	 * Params:
	 * addr	address of the display
	 * digit	the position of the digit on the display (0..7)
	 * value	the value to be displayed. (0x00..0x0F)
	 * dp	sets the decimal point.
	 */
	public native void setDigit(int addr, int digit, byte value, boolean dp);

	/*
	 * Display a character on a 7-Segment display.
	 * There are only a few characters that make sense here :
	 *	'0','1','2','3','4','5','6','7','8','9','0',
	 *  'A','b','c','d','E','F','H','L','P',
	 *  '.','-','_',' '
	 * Params:
	 * addr	address of the display
	 * digit	the position of the character on the display (0..7)
	 * value	the character to be displayed.
	 * dp	sets the decimal point.
	 */
	public native void setChar(int addr, int digit, char value, boolean dp);
};





