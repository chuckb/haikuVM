package avr.gallerie.user.cvdenzen.arduino.libraries.liquidcrystal;



// if used with configuration arduinoIDE or *.UsingCLIB (e.g. leonardo.UsingCLIB)
import static processing.hardware.arduino.cores.arduino.Arduino.*;
import processing.hardware.arduino.cores.arduino.Print;

// else
//import static haiku.arduino.api.Arduino.*;
//import haiku.avr.lib.arduino.Print;


public class LiquidCrystal extends Print {

    // commands;
    final static private byte LCD_CLEARDISPLAY=0x01;
    final static private byte LCD_RETURNHOME=0x02;
    final static private byte LCD_ENTRYMODESET=0x04;
    final static private byte LCD_DISPLAYCONTROL=0x08;
    final static private byte LCD_CURSORSHIFT=0x10;
    final static private byte LCD_FUNCTIONSET=0x20;
    final static private byte LCD_SETCGRAMADDR=0x40;
    final static private byte LCD_SETDDRAMADDR=(byte) 0x80;

    // flags for display entry mode
    final static private byte LCD_ENTRYRIGHT=0x00;
    final static private byte LCD_ENTRYLEFT=0x02;
    final static private byte LCD_ENTRYSHIFTINCREMENT=0x01;
    final static private byte LCD_ENTRYSHIFTDECREMENT=0x00;

    // flags for display on/off control
    final static private byte LCD_DISPLAYON=0x04;
    final static private byte LCD_DISPLAYOFF=0x00;
    final static private byte LCD_CURSORON=0x02;
    final static private byte LCD_CURSOROFF=0x00;
    final static private byte LCD_BLINKON=0x01;
    final static private byte LCD_BLINKOFF=0x00;

    // flags for display/cursor shift
    final static private byte LCD_DISPLAYMOVE=0x08;
    final static private byte LCD_CURSORMOVE=0x00;
    final static private byte LCD_MOVERIGHT=0x04;
    final static private byte LCD_MOVELEFT=0x00;

    // flags for function set
    final static private byte LCD_8BITMODE=0x10;
    final static private byte LCD_4BITMODE=0x00;
    final static private byte LCD_2LINE=0x08;
    final static private byte LCD_1LINE=0x00;
    final static private byte LCD_5x10DOTS=0x04;
    final static private byte LCD_5x8DOTS=0x00;


    private byte _rs_pin; // LOW: command.  HIGH: character.
    private byte _rw_pin; // LOW: write to LCD.  HIGH: read from LCD.
    private byte _enable_pin; // activated by a HIGH pulse.
    private byte[] _data_pins=new byte[8];

    private byte _displayfunction;
    private byte _displaycontrol;
    private byte _displaymode;

    private byte _initialized;

    private byte _numlines,_currline;

    /*
    #include "LiquidCrystal.h"

    #include <stdio.h>
    #include <string.h>
    #include <inttypes.h>
    #include "Arduino.h"
*/
    
// arduino.h

    private final static byte OUTPUT=1;
    
    
    // When the display powers up, it is configured as follows:
    //
    // 1. Display clear
    // 2. Function set: 
//      DL = 1; 8-bit interface data 
//      N = 0; 1-line display 
//      F = 0; 5x8 dot character font 
    // 3. Display on/off control: 
//      D = 0; Display off 
//      C = 0; Cursor off 
//      B = 0; Blinking off 
    // 4. Entry mode set: 
//      I/D = 1; Increment by 1 
//      S = 0; No shift 
    //
    // Note, however, that resetting the Arduino doesn't reset the LCD, so we
    // can't assume that its in that state when a sketch starts (and the
    // LiquidCrystal constructor is called).

    public LiquidCrystal(byte rs, byte rw, byte enable,
                     byte d0, byte d1, byte d2, byte d3,
                     byte d4, byte d5, byte d6, byte d7)
    {
      init((byte)0, rs, rw, enable, d0, d1, d2, d3, d4, d5, d6, d7);
    }

    public LiquidCrystal(byte rs, byte enable,
                     byte d0, byte d1, byte d2, byte d3,
                     byte d4, byte d5, byte d6, byte d7)
    {
      init((byte)0, rs, (byte)255, enable, d0, d1, d2, d3, d4, d5, d6, d7);
    }

    public LiquidCrystal(byte rs, byte rw, byte enable,
                     byte d0, byte d1, byte d2, byte d3)
    {
      init((byte)1, rs, rw, enable, d0, d1, d2, d3, (byte)0, (byte)0, (byte)0, (byte)0);
    }

    public LiquidCrystal(byte rs,  byte enable,
                     byte d0, byte d1, byte d2, byte d3)
    {
      init((byte)1, rs, (byte)255, enable, d0, d1, d2, d3, (byte)0, (byte)0, (byte)0, (byte)0);
    }

    private void init(byte fourbitmode, byte rs, byte rw, byte enable,
                 byte d0, byte d1, byte d2, byte d3,
                 byte d4, byte d5, byte d6, byte d7)
    {
      _rs_pin = rs;
      _rw_pin = rw;
      _enable_pin = enable;
      
      _data_pins[0] = d0;
      _data_pins[1] = d1;
      _data_pins[2] = d2;
      _data_pins[3] = d3; 
      _data_pins[4] = d4;
      _data_pins[5] = d5;
      _data_pins[6] = d6;
      _data_pins[7] = d7; 
    pinMode(_rs_pin, OUTPUT);
      // we can save 1 pin by not using RW. Indicate by passing 255 instead of pin#
      if (_rw_pin != -1) { 
        pinMode(_rw_pin, OUTPUT);
      }
      pinMode(_enable_pin, OUTPUT);
      
      if (fourbitmode!=0)
        _displayfunction = LCD_4BITMODE | LCD_1LINE | LCD_5x8DOTS;
      else 
        _displayfunction = LCD_8BITMODE | LCD_1LINE | LCD_5x8DOTS;
      
      // I do not understand how in c++ this could have worked
      // begin((byte)16, (byte)1);  
      begin((byte)16, (byte)1,(byte)0);
    }

    public void begin(byte cols, byte lines, byte dotsize) {
      if (lines > 1) {
        _displayfunction |= LCD_2LINE;
      }
      _numlines = lines;
      _currline = 0;

      // for some 1 line displays you can select a 10 pixel high font
      if ((dotsize != 0) && (lines == 1)) {
        _displayfunction |= LCD_5x10DOTS;
      }

      // SEE PAGE 45/46 FOR INITIALIZATION SPECIFICATION!
      // according to datasheet, we need at least 40ms after power rises above 2.7V
      // before sending commands. Arduino can turn on way befer 4.5V so we'll wait 50
      delayMicroseconds(50000); 
      // Now we pull both RS and R/W low to begin commands
      digitalWrite(_rs_pin, LOW);
      digitalWrite(_enable_pin, LOW);
      if (_rw_pin != -1) { 
        digitalWrite(_rw_pin, LOW);
      }
      
      //put the LCD into 4 bit or 8 bit mode
      if ( (_displayfunction & LCD_8BITMODE)==0) {
        // this is according to the hitachi HD44780 datasheet
        // figure 24, pg 46

        // we start in 8bit mode, try to set 4 bit mode
        write4bits((byte) 0x03);
        delayMicroseconds(4500); // wait min 4.1ms

        // second try
        write4bits((byte) 0x03);
        delayMicroseconds(4500); // wait min 4.1ms
        
        // third go!
        write4bits((byte) 0x03); 
        delayMicroseconds(150);

        // finally, set to 4-bit interface
        write4bits((byte) 0x02); 
      } else {
        // this is according to the hitachi HD44780 datasheet
        // page 45 figure 23

        // Send function set command sequence
        command((byte) (LCD_FUNCTIONSET | _displayfunction));
        delayMicroseconds(4500);  // wait more than 4.1ms

        // second try
        command((byte) (LCD_FUNCTIONSET | _displayfunction));
        delayMicroseconds(150);

        // third go
        command((byte) (LCD_FUNCTIONSET | _displayfunction));
      }

      // finally, set # lines, font size, etc.
      command((byte) (LCD_FUNCTIONSET | _displayfunction));  

      // turn the display on with no cursor or blinking default
      _displaycontrol = LCD_DISPLAYON | LCD_CURSOROFF | LCD_BLINKOFF;  
      display();

      // clear it off
      clear();

      // Initialize to default text direction (for romance languages)
      _displaymode = LCD_ENTRYLEFT | LCD_ENTRYSHIFTDECREMENT;
      // set the entry mode
      command((byte) (LCD_ENTRYMODESET | _displaymode));

    }
    
    /**
     * Start at given position with default dotsize
     * @param cols
     * @param lines
     */
    public void begin(byte cols, byte lines) {
        begin(cols,lines,(byte)0);
    }

    /********** high level commands, for the user! */
    public void clear()
    {
      command(LCD_CLEARDISPLAY);  // clear display, set cursor position to zero
      delayMicroseconds(2000);  // this command takes a long time!
    }

    public void home()
    {
      command(LCD_RETURNHOME);  // set cursor position to zero
      delayMicroseconds(2000);  // this command takes a long time!
    }

    public void setCursor(byte col, byte row)
    {
      byte row_offsets[] = { 0x00, 0x40, 0x14, 0x54 };
      if ( row >= _numlines ) {
        row = (byte) (_numlines-1);    // we count rows starting w/0
      }
      
      command((byte) (LCD_SETDDRAMADDR | (col + row_offsets[row])));
    }

    // Turn the display on/off (quickly)
    public void noDisplay() {
      _displaycontrol &= ~LCD_DISPLAYON;
      command((byte) (LCD_DISPLAYCONTROL | _displaycontrol));
    }
    public void display() {
      _displaycontrol |= LCD_DISPLAYON;
      command((byte) (LCD_DISPLAYCONTROL | _displaycontrol));
    }

    // Turns the underline cursor on/off
    public void noCursor() {
      _displaycontrol &= ~LCD_CURSORON;
      command((byte) (LCD_DISPLAYCONTROL | _displaycontrol));
    }
    public void cursor() {
      _displaycontrol |= LCD_CURSORON;
      command((byte) (LCD_DISPLAYCONTROL | _displaycontrol));
    }

    // Turn on and off the blinking cursor
    public void noBlink() {
      _displaycontrol &= ~LCD_BLINKON;
      command((byte) (LCD_DISPLAYCONTROL | _displaycontrol));
    }
    public void blink() {
      _displaycontrol |= LCD_BLINKON;
      command((byte) (LCD_DISPLAYCONTROL | _displaycontrol));
    }

    // These commands scroll the display without changing the RAM
    public void scrollDisplayLeft() {
      command((byte) (LCD_CURSORSHIFT | LCD_DISPLAYMOVE | LCD_MOVELEFT));
    }
    public void scrollDisplayRight() {
      command((byte) (LCD_CURSORSHIFT | LCD_DISPLAYMOVE | LCD_MOVERIGHT));
    }

    // This is for text that flows Left to Right
    public void leftToRight() {
      _displaymode |= LCD_ENTRYLEFT;
      command((byte) (LCD_ENTRYMODESET | _displaymode));
    }

    // This is for text that flows Right to Left
    public void rightToLeft() {
      _displaymode &= ~LCD_ENTRYLEFT;
      command((byte) (LCD_ENTRYMODESET | _displaymode));
    }

    // This will 'right justify' text from the cursor
    public void autoscroll() {
      _displaymode |= LCD_ENTRYSHIFTINCREMENT;
      command((byte) (LCD_ENTRYMODESET | _displaymode));
    }

    // This will 'left justify' text from the cursor
    public void noAutoscroll() {
      _displaymode &= ~LCD_ENTRYSHIFTINCREMENT;
      command((byte) (LCD_ENTRYMODESET | _displaymode));
    }

    // Allows us to fill the first 8 CGRAM locations
    // with custom characters
    public void createChar(byte location, byte charmap[]) {
      location &= 0x7; // we only have 8 locations 0-7
      command((byte) (LCD_SETCGRAMADDR | (location << 3)));
      for (int i=0; i<8; i++) {
        write(charmap[i]);
      }
    }

    /*********** mid level commands, for sending data/cmds */

    private void command(byte value) {
      send(value, (byte)LOW);
    }

    public byte write(byte value) {
      send(value, (byte)HIGH);
      return 1; // assume success
    }

    /************ low level data pushing commands **********/

    // write either command or data, with automatic 4/8-bit selection
    private void send(byte value, byte mode) {
      digitalWrite(_rs_pin, mode);

      // if there is a RW pin indicated, set it low to Write
      if (_rw_pin != -1) { 
        digitalWrite(_rw_pin, LOW);
      }
      
      if ((_displayfunction & LCD_8BITMODE)!=0) {
        write8bits(value); 
      } else {
        write4bits((byte) (value>>4));
        write4bits(value);
      }
    }

    public void pulseEnable() {
      digitalWrite(_enable_pin, LOW);
      delayMicroseconds(1);    
      digitalWrite(_enable_pin, HIGH);
      delayMicroseconds(1);    // enable pulse must be >450ns
      digitalWrite(_enable_pin, LOW);
      delayMicroseconds(100);   // commands need > 37us to settle
    }

    public void write4bits(byte value) {
      for (int i = 0; i < 4; i++) {
        pinMode(_data_pins[i], OUTPUT);
        digitalWrite(_data_pins[i], (byte)((value >> i) & 0x01));
      }

      pulseEnable();
    }

    public void write8bits(byte value) {
      for (int i = 0; i < 8; i++) {
        pinMode(_data_pins[i], OUTPUT);
        digitalWrite(_data_pins[i], (byte)((value >> i) & 0x01));
      }
      
      pulseEnable();
    }


}
