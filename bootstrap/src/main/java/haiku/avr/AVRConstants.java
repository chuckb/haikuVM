package haiku.avr;

import haiku.vm.NativeCVariable16;
import haiku.vm.NativeCVariable8;

public class AVRConstants {
    public static final boolean __AVR_ATmega8__ = false;
    public static final boolean __AVR_ATmega168__ = false;
    public static final boolean __AVR_ATmega1280__ = false;
    public static final boolean __AVR_ATmega328P__ = false;
    public static final boolean __AVR_ATmega32U4__ = false;
    
    @NativeCVariable8
    public static volatile int SREG;

    // ARDUINO __AVR_ATmega328P__
    // public static final boolean __AVR_ATmega328P__ = true;
    /*
     * Generated with AVR Device Explorer for HaikuVM from file: 'iom328p.h'
     */

    public static final int _AVR_IOM328P_H_ = 1;

    /**
     * PINB IO 0x03 8 <br>
     * PINB0 0 <br>
     * PINB1 1 <br>
     * PINB2 2 <br>
     * PINB3 3 <br>
     * PINB4 4 <br>
     * PINB5 5 <br>
     * PINB6 6 <br>
     * PINB7 7 <br>
     */
    @NativeCVariable8
    public static volatile int PINB;
    public static final int PINB0 = 0;
    public static final int PINB1 = 1;
    public static final int PINB2 = 2;
    public static final int PINB3 = 3;
    public static final int PINB4 = 4;
    public static final int PINB5 = 5;
    public static final int PINB6 = 6;
    public static final int PINB7 = 7;

    /**
     * DDRB IO 0x04 8 <br>
     * DDB0 0 <br>
     * DDB1 1 <br>
     * DDB2 2 <br>
     * DDB3 3 <br>
     * DDB4 4 <br>
     * DDB5 5 <br>
     * DDB6 6 <br>
     * DDB7 7 <br>
     */
    @NativeCVariable8
    public static volatile int DDRB;
    public static final int DDB0 = 0;
    public static final int DDB1 = 1;
    public static final int DDB2 = 2;
    public static final int DDB3 = 3;
    public static final int DDB4 = 4;
    public static final int DDB5 = 5;
    public static final int DDB6 = 6;
    public static final int DDB7 = 7;

    /**
     * PORTB IO 0x05 8 <br>
     * PORTB0 0 <br>
     * PORTB1 1 <br>
     * PORTB2 2 <br>
     * PORTB3 3 <br>
     * PORTB4 4 <br>
     * PORTB5 5 <br>
     * PORTB6 6 <br>
     * PORTB7 7 <br>
     */
    @NativeCVariable8
    public static volatile int PORTB;
    public static final int PORTB0 = 0;
    public static final int PORTB1 = 1;
    public static final int PORTB2 = 2;
    public static final int PORTB3 = 3;
    public static final int PORTB4 = 4;
    public static final int PORTB5 = 5;
    public static final int PORTB6 = 6;
    public static final int PORTB7 = 7;

    /**
     * PINC IO 0x06 8 <br>
     * PINC0 0 <br>
     * PINC1 1 <br>
     * PINC2 2 <br>
     * PINC3 3 <br>
     * PINC4 4 <br>
     * PINC5 5 <br>
     * PINC6 6 <br>
     */
    @NativeCVariable8
    public static volatile int PINC;
    public static final int PINC0 = 0;
    public static final int PINC1 = 1;
    public static final int PINC2 = 2;
    public static final int PINC3 = 3;
    public static final int PINC4 = 4;
    public static final int PINC5 = 5;
    public static final int PINC6 = 6;

    /**
     * DDRC IO 0x07 8 <br>
     * DDC0 0 <br>
     * DDC1 1 <br>
     * DDC2 2 <br>
     * DDC3 3 <br>
     * DDC4 4 <br>
     * DDC5 5 <br>
     * DDC6 6 <br>
     */
    @NativeCVariable8
    public static volatile int DDRC;
    public static final int DDC0 = 0;
    public static final int DDC1 = 1;
    public static final int DDC2 = 2;
    public static final int DDC3 = 3;
    public static final int DDC4 = 4;
    public static final int DDC5 = 5;
    public static final int DDC6 = 6;

    /**
     * PORTC IO 0x08 8 <br>
     * PORTC0 0 <br>
     * PORTC1 1 <br>
     * PORTC2 2 <br>
     * PORTC3 3 <br>
     * PORTC4 4 <br>
     * PORTC5 5 <br>
     * PORTC6 6 <br>
     */
    @NativeCVariable8
    public static volatile int PORTC;
    public static final int PORTC0 = 0;
    public static final int PORTC1 = 1;
    public static final int PORTC2 = 2;
    public static final int PORTC3 = 3;
    public static final int PORTC4 = 4;
    public static final int PORTC5 = 5;
    public static final int PORTC6 = 6;

    /**
     * PIND IO 0x09 8 <br>
     * PIND0 0 <br>
     * PIND1 1 <br>
     * PIND2 2 <br>
     * PIND3 3 <br>
     * PIND4 4 <br>
     * PIND5 5 <br>
     * PIND6 6 <br>
     * PIND7 7 <br>
     */
    @NativeCVariable8
    public static volatile int PIND;
    public static final int PIND0 = 0;
    public static final int PIND1 = 1;
    public static final int PIND2 = 2;
    public static final int PIND3 = 3;
    public static final int PIND4 = 4;
    public static final int PIND5 = 5;
    public static final int PIND6 = 6;
    public static final int PIND7 = 7;

    /**
     * DDRD IO 0x0A 8 <br>
     * DDD0 0 <br>
     * DDD1 1 <br>
     * DDD2 2 <br>
     * DDD3 3 <br>
     * DDD4 4 <br>
     * DDD5 5 <br>
     * DDD6 6 <br>
     * DDD7 7 <br>
     */
    @NativeCVariable8
    public static volatile int DDRD;
    public static final int DDD0 = 0;
    public static final int DDD1 = 1;
    public static final int DDD2 = 2;
    public static final int DDD3 = 3;
    public static final int DDD4 = 4;
    public static final int DDD5 = 5;
    public static final int DDD6 = 6;
    public static final int DDD7 = 7;

    /**
     * PORTD IO 0x0B 8 <br>
     * PORTD0 0 <br>
     * PORTD1 1 <br>
     * PORTD2 2 <br>
     * PORTD3 3 <br>
     * PORTD4 4 <br>
     * PORTD5 5 <br>
     * PORTD6 6 <br>
     * PORTD7 7 <br>
     */
    @NativeCVariable8
    public static volatile int PORTD;
    public static final int PORTD0 = 0;
    public static final int PORTD1 = 1;
    public static final int PORTD2 = 2;
    public static final int PORTD3 = 3;
    public static final int PORTD4 = 4;
    public static final int PORTD5 = 5;
    public static final int PORTD6 = 6;
    public static final int PORTD7 = 7;

    /**
     * TIFR0 IO 0x15 8 <br>
     * TOV0 0 <br>
     * OCF0A 1 <br>
     * OCF0B 2 <br>
     */
    @NativeCVariable8
    public static volatile int TIFR0;
    public static final int TOV0 = 0;
    public static final int OCF0A = 1;
    public static final int OCF0B = 2;

    /**
     * TIFR1 IO 0x16 8 <br>
     * TOV1 0 <br>
     * OCF1A 1 <br>
     * OCF1B 2 <br>
     * ICF1 5 <br>
     */
    @NativeCVariable8
    public static volatile int TIFR1;
    public static final int TOV1 = 0;
    public static final int OCF1A = 1;
    public static final int OCF1B = 2;
    public static final int ICF1 = 5;

    /**
     * TIFR2 IO 0x17 8 <br>
     * TOV2 0 <br>
     * OCF2A 1 <br>
     * OCF2B 2 <br>
     */
    @NativeCVariable8
    public static volatile int TIFR2;
    public static final int TOV2 = 0;
    public static final int OCF2A = 1;
    public static final int OCF2B = 2;

    /**
     * PCIFR IO 0x1B 8 <br>
     * PCIF0 0 <br>
     * PCIF1 1 <br>
     * PCIF2 2 <br>
     */
    @NativeCVariable8
    public static volatile int PCIFR;
    public static final int PCIF0 = 0;
    public static final int PCIF1 = 1;
    public static final int PCIF2 = 2;

    /**
     * EIFR IO 0x1C 8 <br>
     * INTF0 0 <br>
     * INTF1 1 <br>
     */
    @NativeCVariable8
    public static volatile int EIFR;
    public static final int INTF0 = 0;
    public static final int INTF1 = 1;

    /**
     * EIMSK IO 0x1D 8 <br>
     * INT0 0 <br>
     * INT1 1 <br>
     */
    @NativeCVariable8
    public static volatile int EIMSK;
    public static final int INT0 = 0;
    public static final int INT1 = 1;

    /**
     * GPIOR0 IO 0x1E 8 <br>
     * GPIOR00 0 <br>
     * GPIOR01 1 <br>
     * GPIOR02 2 <br>
     * GPIOR03 3 <br>
     * GPIOR04 4 <br>
     * GPIOR05 5 <br>
     * GPIOR06 6 <br>
     * GPIOR07 7 <br>
     */
    @NativeCVariable8
    public static volatile int GPIOR0;
    public static final int GPIOR00 = 0;
    public static final int GPIOR01 = 1;
    public static final int GPIOR02 = 2;
    public static final int GPIOR03 = 3;
    public static final int GPIOR04 = 4;
    public static final int GPIOR05 = 5;
    public static final int GPIOR06 = 6;
    public static final int GPIOR07 = 7;

    /**
     * EECR IO 0x1F 8 <br>
     * EERE 0 <br>
     * EEPE 1 <br>
     * EEMPE 2 <br>
     * EERIE 3 <br>
     * EEPM0 4 <br>
     * EEPM1 5 <br>
     */
    @NativeCVariable8
    public static volatile int EECR;
    public static final int EERE = 0;
    public static final int EEPE = 1;
    public static final int EEMPE = 2;
    public static final int EERIE = 3;
    public static final int EEPM0 = 4;
    public static final int EEPM1 = 5;

    /**
     * EEDR IO 0x20 8 <br>
     * EEDR0 0 <br>
     * EEDR1 1 <br>
     * EEDR2 2 <br>
     * EEDR3 3 <br>
     * EEDR4 4 <br>
     * EEDR5 5 <br>
     * EEDR6 6 <br>
     * EEDR7 7 <br>
     */
    @NativeCVariable8
    public static volatile int EEDR;
    public static final int EEDR0 = 0;
    public static final int EEDR1 = 1;
    public static final int EEDR2 = 2;
    public static final int EEDR3 = 3;
    public static final int EEDR4 = 4;
    public static final int EEDR5 = 5;
    public static final int EEDR6 = 6;
    public static final int EEDR7 = 7;

    /**
     * EEAR IO 0x21 16 <br>
     */
    @NativeCVariable16
    public static volatile int EEAR;

    /**
     * EEARL IO 0x21 8 <br>
     * EEAR0 0 <br>
     * EEAR1 1 <br>
     * EEAR2 2 <br>
     * EEAR3 3 <br>
     * EEAR4 4 <br>
     * EEAR5 5 <br>
     * EEAR6 6 <br>
     * EEAR7 7 <br>
     */
    @NativeCVariable8
    public static volatile int EEARL;
    public static final int EEAR0 = 0;
    public static final int EEAR1 = 1;
    public static final int EEAR2 = 2;
    public static final int EEAR3 = 3;
    public static final int EEAR4 = 4;
    public static final int EEAR5 = 5;
    public static final int EEAR6 = 6;
    public static final int EEAR7 = 7;

    /**
     * EEARH IO 0x22 8 <br>
     * EEAR8 0 <br>
     * EEAR9 1 <br>
     */
    @NativeCVariable8
    public static volatile int EEARH;
    public static final int EEAR8 = 0;
    public static final int EEAR9 = 1;

    /**
     * GTCCR IO 0x23 8 <br>
     * PSRSYNC 0 <br>
     * PSRASY 1 <br>
     * TSM 7 <br>
     */
    @NativeCVariable8
    public static volatile int GTCCR;
    public static final int PSRSYNC = 0;
    public static final int PSRASY = 1;
    public static final int TSM = 7;

    /**
     * TCCR0A IO 0x24 8 <br>
     * WGM00 0 <br>
     * WGM01 1 <br>
     * COM0B0 4 <br>
     * COM0B1 5 <br>
     * COM0A0 6 <br>
     * COM0A1 7 <br>
     */
    @NativeCVariable8
    public static volatile transient int TCCR0A;
    public static final int WGM00 = 0;
    public static final int WGM01 = 1;
    public static final int COM0B0 = 4;
    public static final int COM0B1 = 5;
    public static final int COM0A0 = 6;
    public static final int COM0A1 = 7;

    /**
     * TCCR0B IO 0x25 8 <br>
     * CS00 0 <br>
     * CS01 1 <br>
     * CS02 2 <br>
     * WGM02 3 <br>
     * FOC0B 6 <br>
     * FOC0A 7 <br>
     */
    @NativeCVariable8
    public static volatile int TCCR0B;
    public static final int CS00 = 0;
    public static final int CS01 = 1;
    public static final int CS02 = 2;
    public static final int WGM02 = 3;
    public static final int FOC0B = 6;
    public static final int FOC0A = 7;

    /**
     * TCNT0 IO 0x26 8 <br>
     * TCNT0_0 0 <br>
     * TCNT0_1 1 <br>
     * TCNT0_2 2 <br>
     * TCNT0_3 3 <br>
     * TCNT0_4 4 <br>
     * TCNT0_5 5 <br>
     * TCNT0_6 6 <br>
     * TCNT0_7 7 <br>
     */
    @NativeCVariable8
    public static volatile int TCNT0;
    public static final int TCNT0_0 = 0;
    public static final int TCNT0_1 = 1;
    public static final int TCNT0_2 = 2;
    public static final int TCNT0_3 = 3;
    public static final int TCNT0_4 = 4;
    public static final int TCNT0_5 = 5;
    public static final int TCNT0_6 = 6;
    public static final int TCNT0_7 = 7;

    /**
     * OCR0A IO 0x27 8 <br>
     * OCR0A_0 0 <br>
     * OCR0A_1 1 <br>
     * OCR0A_2 2 <br>
     * OCR0A_3 3 <br>
     * OCR0A_4 4 <br>
     * OCR0A_5 5 <br>
     * OCR0A_6 6 <br>
     * OCR0A_7 7 <br>
     */
    @NativeCVariable8
    public static volatile int OCR0A;
    public static final int OCR0A_0 = 0;
    public static final int OCR0A_1 = 1;
    public static final int OCR0A_2 = 2;
    public static final int OCR0A_3 = 3;
    public static final int OCR0A_4 = 4;
    public static final int OCR0A_5 = 5;
    public static final int OCR0A_6 = 6;
    public static final int OCR0A_7 = 7;

    /**
     * OCR0B IO 0x28 8 <br>
     * OCR0B_0 0 <br>
     * OCR0B_1 1 <br>
     * OCR0B_2 2 <br>
     * OCR0B_3 3 <br>
     * OCR0B_4 4 <br>
     * OCR0B_5 5 <br>
     * OCR0B_6 6 <br>
     * OCR0B_7 7 <br>
     */
    @NativeCVariable8
    public static volatile int OCR0B;
    public static final int OCR0B_0 = 0;
    public static final int OCR0B_1 = 1;
    public static final int OCR0B_2 = 2;
    public static final int OCR0B_3 = 3;
    public static final int OCR0B_4 = 4;
    public static final int OCR0B_5 = 5;
    public static final int OCR0B_6 = 6;
    public static final int OCR0B_7 = 7;

    /**
     * GPIOR1 IO 0x2A 8 <br>
     * GPIOR10 0 <br>
     * GPIOR11 1 <br>
     * GPIOR12 2 <br>
     * GPIOR13 3 <br>
     * GPIOR14 4 <br>
     * GPIOR15 5 <br>
     * GPIOR16 6 <br>
     * GPIOR17 7 <br>
     */
    @NativeCVariable8
    public static volatile int GPIOR1;
    public static final int GPIOR10 = 0;
    public static final int GPIOR11 = 1;
    public static final int GPIOR12 = 2;
    public static final int GPIOR13 = 3;
    public static final int GPIOR14 = 4;
    public static final int GPIOR15 = 5;
    public static final int GPIOR16 = 6;
    public static final int GPIOR17 = 7;

    /**
     * GPIOR2 IO 0x2B 8 <br>
     * GPIOR20 0 <br>
     * GPIOR21 1 <br>
     * GPIOR22 2 <br>
     * GPIOR23 3 <br>
     * GPIOR24 4 <br>
     * GPIOR25 5 <br>
     * GPIOR26 6 <br>
     * GPIOR27 7 <br>
     */
    @NativeCVariable8
    public static volatile int GPIOR2;
    public static final int GPIOR20 = 0;
    public static final int GPIOR21 = 1;
    public static final int GPIOR22 = 2;
    public static final int GPIOR23 = 3;
    public static final int GPIOR24 = 4;
    public static final int GPIOR25 = 5;
    public static final int GPIOR26 = 6;
    public static final int GPIOR27 = 7;

    /**
     * SPCR IO 0x2C 8 <br>
     * SPR0 0 <br>
     * SPR1 1 <br>
     * CPHA 2 <br>
     * CPOL 3 <br>
     * MSTR 4 <br>
     * DORD 5 <br>
     * SPE 6 <br>
     * SPIE 7 <br>
     */
    @NativeCVariable8
    public static volatile int SPCR;
    public static final int SPR0 = 0;
    public static final int SPR1 = 1;
    public static final int CPHA = 2;
    public static final int CPOL = 3;
    public static final int MSTR = 4;
    public static final int DORD = 5;
    public static final int SPE = 6;
    public static final int SPIE = 7;

    /**
     * SPSR IO 0x2D 8 <br>
     * SPI2X 0 <br>
     * WCOL 6 <br>
     * SPIF 7 <br>
     */
    @NativeCVariable8
    public static volatile int SPSR;
    public static final int SPI2X = 0;
    public static final int WCOL = 6;
    public static final int SPIF = 7;

    /**
     * SPDR IO 0x2E 8 <br>
     * SPDR0 0 <br>
     * SPDR1 1 <br>
     * SPDR2 2 <br>
     * SPDR3 3 <br>
     * SPDR4 4 <br>
     * SPDR5 5 <br>
     * SPDR6 6 <br>
     * SPDR7 7 <br>
     */
    @NativeCVariable8
    public static volatile int SPDR;
    public static final int SPDR0 = 0;
    public static final int SPDR1 = 1;
    public static final int SPDR2 = 2;
    public static final int SPDR3 = 3;
    public static final int SPDR4 = 4;
    public static final int SPDR5 = 5;
    public static final int SPDR6 = 6;
    public static final int SPDR7 = 7;

    /**
     * ACSR IO 0x30 8 <br>
     * ACIS0 0 <br>
     * ACIS1 1 <br>
     * ACIC 2 <br>
     * ACIE 3 <br>
     * ACI 4 <br>
     * ACO 5 <br>
     * ACBG 6 <br>
     * ACD 7 <br>
     */
    @NativeCVariable8
    public static volatile int ACSR;
    public static final int ACIS0 = 0;
    public static final int ACIS1 = 1;
    public static final int ACIC = 2;
    public static final int ACIE = 3;
    public static final int ACI = 4;
    public static final int ACO = 5;
    public static final int ACBG = 6;
    public static final int ACD = 7;

    /**
     * SMCR IO 0x33 8 <br>
     * SE 0 <br>
     * SM0 1 <br>
     * SM1 2 <br>
     * SM2 3 <br>
     */
    @NativeCVariable8
    public static volatile int SMCR;
    public static final int SE = 0;
    public static final int SM0 = 1;
    public static final int SM1 = 2;
    public static final int SM2 = 3;

    /**
     * MCUSR IO 0x34 8 <br>
     * PORF 0 <br>
     * EXTRF 1 <br>
     * BORF 2 <br>
     * WDRF 3 <br>
     */
    @NativeCVariable8
    public static volatile int MCUSR;
    public static final int PORF = 0;
    public static final int EXTRF = 1;
    public static final int BORF = 2;
    public static final int WDRF = 3;

    /**
     * MCUCR IO 0x35 8 <br>
     * IVCE 0 <br>
     * IVSEL 1 <br>
     * PUD 4 <br>
     * BODSE 5 <br>
     * BODS 6 <br>
     */
    @NativeCVariable8
    public static volatile int MCUCR;
    public static final int IVCE = 0;
    public static final int IVSEL = 1;
    public static final int PUD = 4;
    public static final int BODSE = 5;
    public static final int BODS = 6;

    /**
     * SPMCSR IO 0x37 8 <br>
     * SELFPRGEN 0 <br>
     * PGERS 1 <br>
     * PGWRT 2 <br>
     * BLBSET 3 <br>
     * RWWSRE 4 <br>
     * RWWSB 6 <br>
     * SPMIE 7 <br>
     */
    @NativeCVariable8
    public static volatile int SPMCSR;
    public static final int SELFPRGEN = 0;
    public static final int PGERS = 1;
    public static final int PGWRT = 2;
    public static final int BLBSET = 3;
    public static final int RWWSRE = 4;
    public static final int RWWSB = 6;
    public static final int SPMIE = 7;

    /**
     * WDTCSR MEM Addr=0x60 Bits=8 <br>
     * WDP0 0 <br>
     * WDP1 1 <br>
     * WDP2 2 <br>
     * WDE 3 <br>
     * WDCE 4 <br>
     * WDP3 5 <br>
     * WDIE 6 <br>
     * WDIF 7 <br>
     */
    @NativeCVariable8
    public static volatile int WDTCSR;
    public static final int WDP0 = 0;
    public static final int WDP1 = 1;
    public static final int WDP2 = 2;
    public static final int WDE = 3;
    public static final int WDCE = 4;
    public static final int WDP3 = 5;
    public static final int WDIE = 6;
    public static final int WDIF = 7;

    /**
     * CLKPR MEM Addr=0x61 Bits=8 <br>
     * CLKPS0 0 <br>
     * CLKPS1 1 <br>
     * CLKPS2 2 <br>
     * CLKPS3 3 <br>
     * CLKPCE 7 <br>
     */
    @NativeCVariable8
    public static volatile int CLKPR;
    public static final int CLKPS0 = 0;
    public static final int CLKPS1 = 1;
    public static final int CLKPS2 = 2;
    public static final int CLKPS3 = 3;
    public static final int CLKPCE = 7;

    /**
     * PRR MEM Addr=0x64 Bits=8 <br>
     * PRADC 0 <br>
     * PRUSART0 1 <br>
     * PRSPI 2 <br>
     * PRTIM1 3 <br>
     * PRTIM0 5 <br>
     * PRTIM2 6 <br>
     * PRTWI 7 <br>
     */
    @NativeCVariable8
    public static volatile int PRR;
    public static final int PRADC = 0;
    public static final int PRUSART0 = 1;
    public static final int PRSPI = 2;
    public static final int PRTIM1 = 3;
    public static final int PRTIM0 = 5;
    public static final int PRTIM2 = 6;
    public static final int PRTWI = 7;

    /**
     * OSCCAL MEM Addr=0x66 Bits=8 <br>
     * CAL0 0 <br>
     * CAL1 1 <br>
     * CAL2 2 <br>
     * CAL3 3 <br>
     * CAL4 4 <br>
     * CAL5 5 <br>
     * CAL6 6 <br>
     * CAL7 7 <br>
     */
    @NativeCVariable8
    public static volatile int OSCCAL;
    public static final int CAL0 = 0;
    public static final int CAL1 = 1;
    public static final int CAL2 = 2;
    public static final int CAL3 = 3;
    public static final int CAL4 = 4;
    public static final int CAL5 = 5;
    public static final int CAL6 = 6;
    public static final int CAL7 = 7;

    /**
     * PCICR MEM Addr=0x68 Bits=8 <br>
     * PCIE0 0 <br>
     * PCIE1 1 <br>
     * PCIE2 2 <br>
     */
    @NativeCVariable8
    public static volatile int PCICR;
    public static final int PCIE0 = 0;
    public static final int PCIE1 = 1;
    public static final int PCIE2 = 2;

    /**
     * EICRA MEM Addr=0x69 Bits=8 <br>
     * ISC00 0 <br>
     * ISC01 1 <br>
     * ISC10 2 <br>
     * ISC11 3 <br>
     */
    @NativeCVariable8
    public static volatile int EICRA;
    public static final int ISC00 = 0;
    public static final int ISC01 = 1;
    public static final int ISC10 = 2;
    public static final int ISC11 = 3;

    /**
     * PCMSK0 MEM Addr=0x6B Bits=8 <br>
     * PCINT0 0 <br>
     * PCINT1 1 <br>
     * PCINT2 2 <br>
     * PCINT3 3 <br>
     * PCINT4 4 <br>
     * PCINT5 5 <br>
     * PCINT6 6 <br>
     * PCINT7 7 <br>
     */
    @NativeCVariable8
    public static volatile int PCMSK0;
    public static final int PCINT0 = 0;
    public static final int PCINT1 = 1;
    public static final int PCINT2 = 2;
    public static final int PCINT3 = 3;
    public static final int PCINT4 = 4;
    public static final int PCINT5 = 5;
    public static final int PCINT6 = 6;
    public static final int PCINT7 = 7;

    /**
     * PCMSK1 MEM Addr=0x6C Bits=8 <br>
     * PCINT8 0 <br>
     * PCINT9 1 <br>
     * PCINT10 2 <br>
     * PCINT11 3 <br>
     * PCINT12 4 <br>
     * PCINT13 5 <br>
     * PCINT14 6 <br>
     */
    @NativeCVariable8
    public static volatile int PCMSK1;
    public static final int PCINT8 = 0;
    public static final int PCINT9 = 1;
    public static final int PCINT10 = 2;
    public static final int PCINT11 = 3;
    public static final int PCINT12 = 4;
    public static final int PCINT13 = 5;
    public static final int PCINT14 = 6;

    /**
     * PCMSK2 MEM Addr=0x6D Bits=8 <br>
     * PCINT16 0 <br>
     * PCINT17 1 <br>
     * PCINT18 2 <br>
     * PCINT19 3 <br>
     * PCINT20 4 <br>
     * PCINT21 5 <br>
     * PCINT22 6 <br>
     * PCINT23 7 <br>
     */
    @NativeCVariable8
    public static volatile int PCMSK2;
    public static final int PCINT16 = 0;
    public static final int PCINT17 = 1;
    public static final int PCINT18 = 2;
    public static final int PCINT19 = 3;
    public static final int PCINT20 = 4;
    public static final int PCINT21 = 5;
    public static final int PCINT22 = 6;
    public static final int PCINT23 = 7;

    /**
     * TIMSK0 MEM Addr=0x6E Bits=8 <br>
     * TOIE0 0 <br>
     * OCIE0A 1 <br>
     * OCIE0B 2 <br>
     */
    @NativeCVariable8
    public static volatile int TIMSK0;
    public static final int TOIE0 = 0;
    public static final int OCIE0A = 1;
    public static final int OCIE0B = 2;

    /**
     * TIMSK1 MEM Addr=0x6F Bits=8 <br>
     * TOIE1 0 <br>
     * OCIE1A 1 <br>
     * OCIE1B 2 <br>
     * ICIE1 5 <br>
     */
    @NativeCVariable8
    public static volatile int TIMSK1;
    public static final int TOIE1 = 0;
    public static final int OCIE1A = 1;
    public static final int OCIE1B = 2;
    public static final int ICIE1 = 5;

    /**
     * TIMSK2 MEM Addr=0x70 Bits=8 <br>
     * TOIE2 0 <br>
     * OCIE2A 1 <br>
     * OCIE2B 2 <br>
     */
    @NativeCVariable8
    public static volatile int TIMSK2;
    public static final int TOIE2 = 0;
    public static final int OCIE2A = 1;
    public static final int OCIE2B = 2;

    /**
     * ADC MEM Addr=0x78 Bits=16 <br>
     */
    @NativeCVariable16
    public static volatile int ADC;

    /**
     * ADCW MEM Addr=0x78 Bits=16 <br>
     */
    @NativeCVariable16
    public static volatile int ADCW;

    /**
     * ADCL MEM Addr=0x78 Bits=8 <br>
     * ADCL0 0 <br>
     * ADCL1 1 <br>
     * ADCL2 2 <br>
     * ADCL3 3 <br>
     * ADCL4 4 <br>
     * ADCL5 5 <br>
     * ADCL6 6 <br>
     * ADCL7 7 <br>
     */
    @NativeCVariable8
    public static volatile int ADCL;
    public static final int ADCL0 = 0;
    public static final int ADCL1 = 1;
    public static final int ADCL2 = 2;
    public static final int ADCL3 = 3;
    public static final int ADCL4 = 4;
    public static final int ADCL5 = 5;
    public static final int ADCL6 = 6;
    public static final int ADCL7 = 7;

    /**
     * ADCH MEM Addr=0x79 Bits=8 <br>
     * ADCH0 0 <br>
     * ADCH1 1 <br>
     * ADCH2 2 <br>
     * ADCH3 3 <br>
     * ADCH4 4 <br>
     * ADCH5 5 <br>
     * ADCH6 6 <br>
     * ADCH7 7 <br>
     */
    @NativeCVariable8
    public static volatile int ADCH;
    public static final int ADCH0 = 0;
    public static final int ADCH1 = 1;
    public static final int ADCH2 = 2;
    public static final int ADCH3 = 3;
    public static final int ADCH4 = 4;
    public static final int ADCH5 = 5;
    public static final int ADCH6 = 6;
    public static final int ADCH7 = 7;

    /**
     * ADCSRA MEM Addr=0x7A Bits=8 <br>
     * ADPS0 0 <br>
     * ADPS1 1 <br>
     * ADPS2 2 <br>
     * ADIE 3 <br>
     * ADIF 4 <br>
     * ADATE 5 <br>
     * ADSC 6 <br>
     * ADEN 7 <br>
     */
    @NativeCVariable8
    public static volatile int ADCSRA;
    public static final int ADPS0 = 0;
    public static final int ADPS1 = 1;
    public static final int ADPS2 = 2;
    public static final int ADIE = 3;
    public static final int ADIF = 4;
    public static final int ADATE = 5;
    public static final int ADSC = 6;
    public static final int ADEN = 7;

    /**
     * ADCSRB MEM Addr=0x7B Bits=8 <br>
     * ADTS0 0 <br>
     * ADTS1 1 <br>
     * ADTS2 2 <br>
     * ACME 6 <br>
     */
    @NativeCVariable8
    public static volatile int ADCSRB;
    public static final int ADTS0 = 0;
    public static final int ADTS1 = 1;
    public static final int ADTS2 = 2;
    public static final int ACME = 6;

    /**
     * ADMUX MEM Addr=0x7C Bits=8 <br>
     * MUX0 0 <br>
     * MUX1 1 <br>
     * MUX2 2 <br>
     * MUX3 3 <br>
     * ADLAR 5 <br>
     * REFS0 6 <br>
     * REFS1 7 <br>
     */
    @NativeCVariable8
    public static volatile int ADMUX;
    public static final int MUX0 = 0;
    public static final int MUX1 = 1;
    public static final int MUX2 = 2;
    public static final int MUX3 = 3;
    public static final int ADLAR = 5;
    public static final int REFS0 = 6;
    public static final int REFS1 = 7;

    /**
     * DIDR0 MEM Addr=0x7E Bits=8 <br>
     * ADC0D 0 <br>
     * ADC1D 1 <br>
     * ADC2D 2 <br>
     * ADC3D 3 <br>
     * ADC4D 4 <br>
     * ADC5D 5 <br>
     */
    @NativeCVariable8
    public static volatile int DIDR0;
    public static final int ADC0D = 0;
    public static final int ADC1D = 1;
    public static final int ADC2D = 2;
    public static final int ADC3D = 3;
    public static final int ADC4D = 4;
    public static final int ADC5D = 5;

    /**
     * DIDR1 MEM Addr=0x7F Bits=8 <br>
     * AIN0D 0 <br>
     * AIN1D 1 <br>
     */
    @NativeCVariable8
    public static volatile int DIDR1;
    public static final int AIN0D = 0;
    public static final int AIN1D = 1;

    /**
     * TCCR1A MEM Addr=0x80 Bits=8 <br>
     * WGM10 0 <br>
     * WGM11 1 <br>
     * COM1B0 4 <br>
     * COM1B1 5 <br>
     * COM1A0 6 <br>
     * COM1A1 7 <br>
     */
    @NativeCVariable8
    public static volatile int TCCR1A;
    public static final int WGM10 = 0;
    public static final int WGM11 = 1;
    public static final int COM1B0 = 4;
    public static final int COM1B1 = 5;
    public static final int COM1A0 = 6;
    public static final int COM1A1 = 7;

    /**
     * TCCR1B MEM Addr=0x81 Bits=8 <br>
     * CS10 0 <br>
     * CS11 1 <br>
     * CS12 2 <br>
     * WGM12 3 <br>
     * WGM13 4 <br>
     * ICES1 6 <br>
     * ICNC1 7 <br>
     */
    @NativeCVariable8
    public static volatile int TCCR1B;
    public static final int CS10 = 0;
    public static final int CS11 = 1;
    public static final int CS12 = 2;
    public static final int WGM12 = 3;
    public static final int WGM13 = 4;
    public static final int ICES1 = 6;
    public static final int ICNC1 = 7;

    /**
     * TCCR1C MEM Addr=0x82 Bits=8 <br>
     * FOC1B 6 <br>
     * FOC1A 7 <br>
     */
    @NativeCVariable8
    public static volatile int TCCR1C;
    public static final int FOC1B = 6;
    public static final int FOC1A = 7;

    /**
     * TCNT1 MEM Addr=0x84 Bits=16 <br>
     */
    @NativeCVariable16
    public static volatile int TCNT1;

    /**
     * TCNT1L MEM Addr=0x84 Bits=8 <br>
     * TCNT1L0 0 <br>
     * TCNT1L1 1 <br>
     * TCNT1L2 2 <br>
     * TCNT1L3 3 <br>
     * TCNT1L4 4 <br>
     * TCNT1L5 5 <br>
     * TCNT1L6 6 <br>
     * TCNT1L7 7 <br>
     */
    @NativeCVariable8
    public static volatile int TCNT1L;
    public static final int TCNT1L0 = 0;
    public static final int TCNT1L1 = 1;
    public static final int TCNT1L2 = 2;
    public static final int TCNT1L3 = 3;
    public static final int TCNT1L4 = 4;
    public static final int TCNT1L5 = 5;
    public static final int TCNT1L6 = 6;
    public static final int TCNT1L7 = 7;

    /**
     * TCNT1H MEM Addr=0x85 Bits=8 <br>
     * TCNT1H0 0 <br>
     * TCNT1H1 1 <br>
     * TCNT1H2 2 <br>
     * TCNT1H3 3 <br>
     * TCNT1H4 4 <br>
     * TCNT1H5 5 <br>
     * TCNT1H6 6 <br>
     * TCNT1H7 7 <br>
     */
    @NativeCVariable8
    public static volatile int TCNT1H;
    public static final int TCNT1H0 = 0;
    public static final int TCNT1H1 = 1;
    public static final int TCNT1H2 = 2;
    public static final int TCNT1H3 = 3;
    public static final int TCNT1H4 = 4;
    public static final int TCNT1H5 = 5;
    public static final int TCNT1H6 = 6;
    public static final int TCNT1H7 = 7;

    /**
     * ICR1 MEM Addr=0x86 Bits=16 <br>
     */
    @NativeCVariable16
    public static volatile int ICR1;

    /**
     * ICR1L MEM Addr=0x86 Bits=8 <br>
     * ICR1L0 0 <br>
     * ICR1L1 1 <br>
     * ICR1L2 2 <br>
     * ICR1L3 3 <br>
     * ICR1L4 4 <br>
     * ICR1L5 5 <br>
     * ICR1L6 6 <br>
     * ICR1L7 7 <br>
     */
    @NativeCVariable8
    public static volatile int ICR1L;
    public static final int ICR1L0 = 0;
    public static final int ICR1L1 = 1;
    public static final int ICR1L2 = 2;
    public static final int ICR1L3 = 3;
    public static final int ICR1L4 = 4;
    public static final int ICR1L5 = 5;
    public static final int ICR1L6 = 6;
    public static final int ICR1L7 = 7;

    /**
     * ICR1H MEM Addr=0x87 Bits=8 <br>
     * ICR1H0 0 <br>
     * ICR1H1 1 <br>
     * ICR1H2 2 <br>
     * ICR1H3 3 <br>
     * ICR1H4 4 <br>
     * ICR1H5 5 <br>
     * ICR1H6 6 <br>
     * ICR1H7 7 <br>
     */
    @NativeCVariable8
    public static volatile int ICR1H;
    public static final int ICR1H0 = 0;
    public static final int ICR1H1 = 1;
    public static final int ICR1H2 = 2;
    public static final int ICR1H3 = 3;
    public static final int ICR1H4 = 4;
    public static final int ICR1H5 = 5;
    public static final int ICR1H6 = 6;
    public static final int ICR1H7 = 7;

    /**
     * OCR1A MEM Addr=0x88 Bits=16 <br>
     */
    @NativeCVariable16
    public static volatile int OCR1A;

    /**
     * OCR1AL MEM Addr=0x88 Bits=8 <br>
     * OCR1AL0 0 <br>
     * OCR1AL1 1 <br>
     * OCR1AL2 2 <br>
     * OCR1AL3 3 <br>
     * OCR1AL4 4 <br>
     * OCR1AL5 5 <br>
     * OCR1AL6 6 <br>
     * OCR1AL7 7 <br>
     */
    @NativeCVariable8
    public static volatile int OCR1AL;
    public static final int OCR1AL0 = 0;
    public static final int OCR1AL1 = 1;
    public static final int OCR1AL2 = 2;
    public static final int OCR1AL3 = 3;
    public static final int OCR1AL4 = 4;
    public static final int OCR1AL5 = 5;
    public static final int OCR1AL6 = 6;
    public static final int OCR1AL7 = 7;

    /**
     * OCR1AH MEM Addr=0x89 Bits=8 <br>
     * OCR1AH0 0 <br>
     * OCR1AH1 1 <br>
     * OCR1AH2 2 <br>
     * OCR1AH3 3 <br>
     * OCR1AH4 4 <br>
     * OCR1AH5 5 <br>
     * OCR1AH6 6 <br>
     * OCR1AH7 7 <br>
     */
    @NativeCVariable8
    public static volatile int OCR1AH;
    public static final int OCR1AH0 = 0;
    public static final int OCR1AH1 = 1;
    public static final int OCR1AH2 = 2;
    public static final int OCR1AH3 = 3;
    public static final int OCR1AH4 = 4;
    public static final int OCR1AH5 = 5;
    public static final int OCR1AH6 = 6;
    public static final int OCR1AH7 = 7;

    /**
     * OCR1B MEM Addr=0x8A Bits=16 <br>
     */
    @NativeCVariable16
    public static volatile int OCR1B;

    /**
     * OCR1BL MEM Addr=0x8A Bits=8 <br>
     * OCR1BL0 0 <br>
     * OCR1BL1 1 <br>
     * OCR1BL2 2 <br>
     * OCR1BL3 3 <br>
     * OCR1BL4 4 <br>
     * OCR1BL5 5 <br>
     * OCR1BL6 6 <br>
     * OCR1BL7 7 <br>
     */
    @NativeCVariable8
    public static volatile int OCR1BL;
    public static final int OCR1BL0 = 0;
    public static final int OCR1BL1 = 1;
    public static final int OCR1BL2 = 2;
    public static final int OCR1BL3 = 3;
    public static final int OCR1BL4 = 4;
    public static final int OCR1BL5 = 5;
    public static final int OCR1BL6 = 6;
    public static final int OCR1BL7 = 7;

    /**
     * OCR1BH MEM Addr=0x8B Bits=8 <br>
     * OCR1BH0 0 <br>
     * OCR1BH1 1 <br>
     * OCR1BH2 2 <br>
     * OCR1BH3 3 <br>
     * OCR1BH4 4 <br>
     * OCR1BH5 5 <br>
     * OCR1BH6 6 <br>
     * OCR1BH7 7 <br>
     */
    @NativeCVariable8
    public static volatile int OCR1BH;
    public static final int OCR1BH0 = 0;
    public static final int OCR1BH1 = 1;
    public static final int OCR1BH2 = 2;
    public static final int OCR1BH3 = 3;
    public static final int OCR1BH4 = 4;
    public static final int OCR1BH5 = 5;
    public static final int OCR1BH6 = 6;
    public static final int OCR1BH7 = 7;

    /**
     * TCCR2A MEM Addr=0xB0 Bits=8 <br>
     * WGM20 0 <br>
     * WGM21 1 <br>
     * COM2B0 4 <br>
     * COM2B1 5 <br>
     * COM2A0 6 <br>
     * COM2A1 7 <br>
     */
    @NativeCVariable8
    public static volatile int TCCR2A;
    public static final int WGM20 = 0;
    public static final int WGM21 = 1;
    public static final int COM2B0 = 4;
    public static final int COM2B1 = 5;
    public static final int COM2A0 = 6;
    public static final int COM2A1 = 7;

    /**
     * TCCR2B MEM Addr=0xB1 Bits=8 <br>
     * CS20 0 <br>
     * CS21 1 <br>
     * CS22 2 <br>
     * WGM22 3 <br>
     * FOC2B 6 <br>
     * FOC2A 7 <br>
     */
    @NativeCVariable8
    public static volatile int TCCR2B;
    public static final int CS20 = 0;
    public static final int CS21 = 1;
    public static final int CS22 = 2;
    public static final int WGM22 = 3;
    public static final int FOC2B = 6;
    public static final int FOC2A = 7;

    /**
     * TCNT2 MEM Addr=0xB2 Bits=8 <br>
     * TCNT2_0 0 <br>
     * TCNT2_1 1 <br>
     * TCNT2_2 2 <br>
     * TCNT2_3 3 <br>
     * TCNT2_4 4 <br>
     * TCNT2_5 5 <br>
     * TCNT2_6 6 <br>
     * TCNT2_7 7 <br>
     */
    @NativeCVariable8
    public static volatile int TCNT2;
    public static final int TCNT2_0 = 0;
    public static final int TCNT2_1 = 1;
    public static final int TCNT2_2 = 2;
    public static final int TCNT2_3 = 3;
    public static final int TCNT2_4 = 4;
    public static final int TCNT2_5 = 5;
    public static final int TCNT2_6 = 6;
    public static final int TCNT2_7 = 7;

    /**
     * OCR2A MEM Addr=0xB3 Bits=8 <br>
     * OCR2_0 0 <br>
     * OCR2_1 1 <br>
     * OCR2_2 2 <br>
     * OCR2_3 3 <br>
     * OCR2_4 4 <br>
     * OCR2_5 5 <br>
     * OCR2_6 6 <br>
     * OCR2_7 7 <br>
     */
    @NativeCVariable8
    public static volatile int OCR2A;
    // public static final int OCR2_0 = 0;
    // public static final int OCR2_1 = 1;
    // public static final int OCR2_2 = 2;
    // public static final int OCR2_3 = 3;
    // public static final int OCR2_4 = 4;
    // public static final int OCR2_5 = 5;
    // public static final int OCR2_6 = 6;
    // public static final int OCR2_7 = 7;

    /**
     * OCR2B MEM Addr=0xB4 Bits=8 <br>
     * OCR2_0 0 <br>
     * OCR2_1 1 <br>
     * OCR2_2 2 <br>
     * OCR2_3 3 <br>
     * OCR2_4 4 <br>
     * OCR2_5 5 <br>
     * OCR2_6 6 <br>
     * OCR2_7 7 <br>
     */
    @NativeCVariable8
    public static volatile int OCR2B;
    public static final int OCR2_0 = 0;
    public static final int OCR2_1 = 1;
    public static final int OCR2_2 = 2;
    public static final int OCR2_3 = 3;
    public static final int OCR2_4 = 4;
    public static final int OCR2_5 = 5;
    public static final int OCR2_6 = 6;
    public static final int OCR2_7 = 7;

    /**
     * ASSR MEM Addr=0xB6 Bits=8 <br>
     * TCR2BUB 0 <br>
     * TCR2AUB 1 <br>
     * OCR2BUB 2 <br>
     * OCR2AUB 3 <br>
     * TCN2UB 4 <br>
     * AS2 5 <br>
     * EXCLK 6 <br>
     */
    @NativeCVariable8
    public static volatile int ASSR;
    public static final int TCR2BUB = 0;
    public static final int TCR2AUB = 1;
    public static final int OCR2BUB = 2;
    public static final int OCR2AUB = 3;
    public static final int TCN2UB = 4;
    public static final int AS2 = 5;
    public static final int EXCLK = 6;

    /**
     * TWBR MEM Addr=0xB8 Bits=8 <br>
     * TWBR0 0 <br>
     * TWBR1 1 <br>
     * TWBR2 2 <br>
     * TWBR3 3 <br>
     * TWBR4 4 <br>
     * TWBR5 5 <br>
     * TWBR6 6 <br>
     * TWBR7 7 <br>
     */
    @NativeCVariable8
    public static volatile int TWBR;
    public static final int TWBR0 = 0;
    public static final int TWBR1 = 1;
    public static final int TWBR2 = 2;
    public static final int TWBR3 = 3;
    public static final int TWBR4 = 4;
    public static final int TWBR5 = 5;
    public static final int TWBR6 = 6;
    public static final int TWBR7 = 7;

    /**
     * TWSR MEM Addr=0xB9 Bits=8 <br>
     * TWPS0 0 <br>
     * TWPS1 1 <br>
     * TWS3 3 <br>
     * TWS4 4 <br>
     * TWS5 5 <br>
     * TWS6 6 <br>
     * TWS7 7 <br>
     */
    @NativeCVariable8
    public static volatile int TWSR;
    public static final int TWPS0 = 0;
    public static final int TWPS1 = 1;
    public static final int TWS3 = 3;
    public static final int TWS4 = 4;
    public static final int TWS5 = 5;
    public static final int TWS6 = 6;
    public static final int TWS7 = 7;

    /**
     * TWAR MEM Addr=0xBA Bits=8 <br>
     * TWGCE 0 <br>
     * TWA0 1 <br>
     * TWA1 2 <br>
     * TWA2 3 <br>
     * TWA3 4 <br>
     * TWA4 5 <br>
     * TWA5 6 <br>
     * TWA6 7 <br>
     */
    @NativeCVariable8
    public static volatile int TWAR;
    public static final int TWGCE = 0;
    public static final int TWA0 = 1;
    public static final int TWA1 = 2;
    public static final int TWA2 = 3;
    public static final int TWA3 = 4;
    public static final int TWA4 = 5;
    public static final int TWA5 = 6;
    public static final int TWA6 = 7;

    /**
     * TWDR MEM Addr=0xBB Bits=8 <br>
     * TWD0 0 <br>
     * TWD1 1 <br>
     * TWD2 2 <br>
     * TWD3 3 <br>
     * TWD4 4 <br>
     * TWD5 5 <br>
     * TWD6 6 <br>
     * TWD7 7 <br>
     */
    @NativeCVariable8
    public static volatile int TWDR;
    public static final int TWD0 = 0;
    public static final int TWD1 = 1;
    public static final int TWD2 = 2;
    public static final int TWD3 = 3;
    public static final int TWD4 = 4;
    public static final int TWD5 = 5;
    public static final int TWD6 = 6;
    public static final int TWD7 = 7;

    /**
     * TWCR MEM Addr=0xBC Bits=8 <br>
     * TWIE 0 <br>
     * TWEN 2 <br>
     * TWWC 3 <br>
     * TWSTO 4 <br>
     * TWSTA 5 <br>
     * TWEA 6 <br>
     * TWINT 7 <br>
     */
    @NativeCVariable8
    public static volatile int TWCR;
    public static final int TWIE = 0;
    public static final int TWEN = 2;
    public static final int TWWC = 3;
    public static final int TWSTO = 4;
    public static final int TWSTA = 5;
    public static final int TWEA = 6;
    public static final int TWINT = 7;

    /**
     * TWAMR MEM Addr=0xBD Bits=8 <br>
     * TWAM0 0 <br>
     * TWAM1 1 <br>
     * TWAM2 2 <br>
     * TWAM3 3 <br>
     * TWAM4 4 <br>
     * TWAM5 5 <br>
     * TWAM6 6 <br>
     */
    @NativeCVariable8
    public static volatile int TWAMR;
    public static final int TWAM0 = 0;
    public static final int TWAM1 = 1;
    public static final int TWAM2 = 2;
    public static final int TWAM3 = 3;
    public static final int TWAM4 = 4;
    public static final int TWAM5 = 5;
    public static final int TWAM6 = 6;

    /**
     * UCSR0A MEM Addr=0xC0 Bits=8 <br>
     * MPCM0 0 <br>
     * U2X0 1 <br>
     * UPE0 2 <br>
     * DOR0 3 <br>
     * FE0 4 <br>
     * UDRE0 5 <br>
     * TXC0 6 <br>
     * RXC0 7 <br>
     */
    @NativeCVariable8
    public static volatile int UCSR0A;
    public static final int MPCM0 = 0;
    public static final int U2X0 = 1;
    public static final int UPE0 = 2;
    public static final int DOR0 = 3;
    public static final int FE0 = 4;
    public static final int UDRE0 = 5;
    public static final int TXC0 = 6;
    public static final int RXC0 = 7;

    /**
     * UCSR0B MEM Addr=0xC1 Bits=8 <br>
     * TXB80 0 <br>
     * RXB80 1 <br>
     * UCSZ02 2 <br>
     * TXEN0 3 <br>
     * RXEN0 4 <br>
     * UDRIE0 5 <br>
     * TXCIE0 6 <br>
     * RXCIE0 7 <br>
     */
    @NativeCVariable8
    public static volatile int UCSR0B;
    public static final int TXB80 = 0;
    public static final int RXB80 = 1;
    public static final int UCSZ02 = 2;
    public static final int TXEN0 = 3;
    public static final int RXEN0 = 4;
    public static final int UDRIE0 = 5;
    public static final int TXCIE0 = 6;
    public static final int RXCIE0 = 7;

    /**
     * UCSR0C MEM Addr=0xC2 Bits=8 <br>
     * UCPOL0 0 <br>
     * UCSZ00 1 <br>
     * UCPHA0 1 <br>
     * UCSZ01 2 <br>
     * UDORD0 2 <br>
     * USBS0 3 <br>
     * UPM00 4 <br>
     * UPM01 5 <br>
     * UMSEL00 6 <br>
     * UMSEL01 7 <br>
     */
    @NativeCVariable8
    public static volatile int UCSR0C;
    public static final int UCPOL0 = 0;
    public static final int UCSZ00 = 1;
    public static final int UCPHA0 = 1;
    public static final int UCSZ01 = 2;
    public static final int UDORD0 = 2;
    public static final int USBS0 = 3;
    public static final int UPM00 = 4;
    public static final int UPM01 = 5;
    public static final int UMSEL00 = 6;
    public static final int UMSEL01 = 7;

    /**
     * UBRR0 MEM Addr=0xC4 Bits=16 <br>
     */
    @NativeCVariable16
    public static volatile int UBRR0;

    /**
     * UBRR0L MEM Addr=0xC4 Bits=8 <br>
     * UBRR0_0 0 <br>
     * UBRR0_1 1 <br>
     * UBRR0_2 2 <br>
     * UBRR0_3 3 <br>
     * UBRR0_4 4 <br>
     * UBRR0_5 5 <br>
     * UBRR0_6 6 <br>
     * UBRR0_7 7 <br>
     */
    @NativeCVariable8
    public static volatile int UBRR0L;
    public static final int UBRR0_0 = 0;
    public static final int UBRR0_1 = 1;
    public static final int UBRR0_2 = 2;
    public static final int UBRR0_3 = 3;
    public static final int UBRR0_4 = 4;
    public static final int UBRR0_5 = 5;
    public static final int UBRR0_6 = 6;
    public static final int UBRR0_7 = 7;

    /**
     * UBRR0H MEM Addr=0xC5 Bits=8 <br>
     * UBRR0_8 0 <br>
     * UBRR0_9 1 <br>
     * UBRR0_10 2 <br>
     * UBRR0_11 3 <br>
     */
    @NativeCVariable8
    public static volatile int UBRR0H;
    public static final int UBRR0_8 = 0;
    public static final int UBRR0_9 = 1;
    public static final int UBRR0_10 = 2;
    public static final int UBRR0_11 = 3;

    /**
     * UDR0 MEM Addr=0xC6 Bits=8 <br>
     * UDR0_0 0 <br>
     * UDR0_1 1 <br>
     * UDR0_2 2 <br>
     * UDR0_3 3 <br>
     * UDR0_4 4 <br>
     * UDR0_5 5 <br>
     * UDR0_6 6 <br>
     * UDR0_7 7 <br>
     */
    @NativeCVariable8
    public static volatile int UDR0;
    public static final int UDR0_0 = 0;
    public static final int UDR0_1 = 1;
    public static final int UDR0_2 = 2;
    public static final int UDR0_3 = 3;
    public static final int UDR0_4 = 4;
    public static final int UDR0_5 = 5;
    public static final int UDR0_6 = 6;
    public static final int UDR0_7 = 7;

    /* Constants */
    public static final int SPM_PAGESIZE = 128;
    public static final int RAMEND     = 0x8FF; /* Last On-Chip SRAM Location */
    public static final int XRAMSIZE   = 0;
    public static final int XRAMEND    = RAMEND;
    public static final int E2END      = 0x3FF;
    public static final int E2PAGESIZE = 4;
    public static final int FLASHEND   = 0x7FFF;


    public static final int FUSE_MEMORY_SIZE = 3;

    public static final int SIGNATURE_0 = 0x1E;

    public static final int SIGNATURE_1 = 0x95;

    public static final int SIGNATURE_2 = 0x0F;

    // unknown values for ATmega8
    @NativeCVariable8
    public static volatile int GICR;
    @NativeCVariable8
    public static volatile int ADCSRA_8;
    
    /** ADCSR      IO  0x06 8 <br>
     */
    @NativeCVariable8
    public static volatile int ADCSR;

    public static final int PD6 = 6;
    public static final int PD7 = 7;
    public static final int PC1 = 1;
    public static final int PC0 = 0;
    public static final int PD5 = 5;
    public static final int PD4 = 4;
    public static final int PB5 = 5;
    public static final int PB4 = 4;
    public static final int PWM = (1 << (1)) | (1 << (2));
    public static final int IRTX = (1 << (3));
    public static final int UCSZ0 = 1;
    public static final int UCSZ1 = 2;
    public static final int URSEL = 7;
    public static final int TXEN = 3;
    public static final int RXEN = 4;
    public static final int COM20 = 4;
    public static final int COM21 = 5;
    @NativeCVariable8
    public static volatile int TCCR0;
    @NativeCVariable8
    public static volatile int OCR2;
    @NativeCVariable8
    public static volatile int UBRRH;
    @NativeCVariable8
    public static volatile int UBRRL;
    @NativeCVariable8
    public static volatile int UCSRB;
    @NativeCVariable8
    public static volatile int UCSRC;
    @NativeCVariable8
    public static volatile int TIMSK;
    @NativeCVariable8
    public static volatile int TCCR2;
    @NativeCVariable8
    public static volatile int TICIE1;

    public static final int RXC = 7;
    @NativeCVariable8
    public static volatile int UCSRA;
    @NativeCVariable8
    public static volatile int UDR;
    public static final int UDRE = 5;
    public static final int TXC = 6;
    /** WDTCR      IO  0x21 8 <br>
     */
    @NativeCVariable8
    public static volatile int WDTCR;
    
    /** GIFR       IO  0x3A 8 <br>
     */
    @NativeCVariable8
    public static volatile int GIFR;

    /** GIMSK      IO  0x3B 8 <br>
     */
    @NativeCVariable8
    public static volatile int GIMSK;

    /** MCUCSR     IO  0x34 8 <br>
     */
    @NativeCVariable8
    public static volatile int MCUCSR;

    /** SFIOR      IO  0x30 8 <br>
     */
    @NativeCVariable8
    public static volatile int SFIOR;

    /** SPMCR      IO  0x37 8 <br>
     */
    @NativeCVariable8
    public static volatile int SPMCR;

    /** TIFR       IO  0x38 8 <br>
     */
    @NativeCVariable8
    public static volatile int TIFR;






    // unknown values for __AVR_ATmega168__

    // public static final boolean __AVR_ATmega1280__ = false;
    @NativeCVariable8
    public static volatile int TCCR3B;
    public static final int CS31 = 0;
    public static final int CS30 = 0;
    @NativeCVariable8
    public static volatile int TCCR4B;
    public static final int CS41 = 0;
    public static final int CS40 = 0;
    @NativeCVariable8
    public static volatile int TCCR5B;
    public static final int CS51 = 0;
    public static final int CS50 = 0;
    @NativeCVariable8
    public static volatile int TCCR3A;
    public static final int WGM30 = 0;
    public static final int WGM40 = 0;
    @NativeCVariable8
    public static volatile int TCCR4A;
    public static final int WGM50 = 0;
    @NativeCVariable8
    public static volatile int TCCR5A;

    
    /* Generated with AVR Device Explorer for HaikuVM
     * from file: 'iom32u4.h'
     */







    /* $Id: iom32u4.h,v 1.1.2.11 2009/02/11 18:05:28 arcanum Exp $ */

    /* avr/iom32u4.h - definitions for ATmega32U4. */

    /* This file should only be included from <avr/io.h>, never directly. */




    public static final int _AVR_IOM32U4_H_ = 1;


    /* Registers and associated bit numbers */


    /** PINC       IO  0x06 8 <br>
     *  PINC6      6 <br>
     *  PINC7      7 <br>
     */
    public static final int PINC7      = 7;

    /** DDRC       IO  0x07 8 <br>
     *  DDC6       6 <br>
     *  DDC7       7 <br>
     */
    @NativeCVariable8
    public static final int DDC7       = 7;

    /** PORTC      IO  0x08 8 <br>
     *  PORTC6     6 <br>
     *  PORTC7     7 <br>
     */
    public static final int PORTC7     = 7;


    /** PINE       IO  0x0C 8 <br>
     *  PINE2      2 <br>
     *  PINE6      6 <br>
     */
    @NativeCVariable8
    public static volatile int PINE;
    public static final int PINE2      = 2;
    public static final int PINE6      = 6;

    /** DDRE       IO  0x0D 8 <br>
     *  DDE2       2 <br>
     *  DDE6       6 <br>
     */
    @NativeCVariable8
    public static volatile int DDRE;
    public static final int DDE2       = 2;
    public static final int DDE6       = 6;

    /** PORTE      IO  0x0E 8 <br>
     *  PORTE2     2 <br>
     *  PORTE6     6 <br>
     */
    @NativeCVariable8
    public static volatile int PORTE;
    public static final int PORTE2     = 2;
    public static final int PORTE6     = 6;

    /** PINF       IO  0x0F 8 <br>
     *  PINF0      0 <br>
     *  PINF1      1 <br>
     *  PINF4      4 <br>
     *  PINF5      5 <br>
     *  PINF6      6 <br>
     *  PINF7      7 <br>
     */
    @NativeCVariable8
    public static volatile int PINF;
    public static final int PINF0      = 0;
    public static final int PINF1      = 1;
    public static final int PINF4      = 4;
    public static final int PINF5      = 5;
    public static final int PINF6      = 6;
    public static final int PINF7      = 7;

    /** DDRF       IO  0x10 8 <br>
     *  DDF0       0 <br>
     *  DDF1       1 <br>
     *  DDF4       4 <br>
     *  DDF5       5 <br>
     *  DDF6       6 <br>
     *  DDF7       7 <br>
     */
    @NativeCVariable8
    public static volatile int DDRF;
    public static final int DDF0       = 0;
    public static final int DDF1       = 1;
    public static final int DDF4       = 4;
    public static final int DDF5       = 5;
    public static final int DDF6       = 6;
    public static final int DDF7       = 7;

    /** PORTF      IO  0x11 8 <br>
     *  PORTF0     0 <br>
     *  PORTF1     1 <br>
     *  PORTF4     4 <br>
     *  PORTF5     5 <br>
     *  PORTF6     6 <br>
     *  PORTF7     7 <br>
     */
    @NativeCVariable8
    public static volatile int PORTF;
    public static final int PORTF0     = 0;
    public static final int PORTF1     = 1;
    public static final int PORTF4     = 4;
    public static final int PORTF5     = 5;
    public static final int PORTF6     = 6;
    public static final int PORTF7     = 7;


    /** TIFR1      IO  0x16 8 <br>
     *  TOV1       0 <br>
     *  OCF1A      1 <br>
     *  OCF1B      2 <br>
     *  OCF1C      3 <br>
     *  ICF1       5 <br>
     */
    public static final int OCF1C      = 3;

    /** TIFR3      IO  0x18 8 <br>
     *  TOV3       0 <br>
     *  OCF3A      1 <br>
     *  OCF3B      2 <br>
     *  OCF3C      3 <br>
     *  ICF3       5 <br>
     */
    @NativeCVariable8
    public static volatile int TIFR3;
    public static final int TOV3       = 0;
    public static final int OCF3A      = 1;
    public static final int OCF3B      = 2;
    public static final int OCF3C      = 3;
    public static final int ICF3       = 5;

    /** TIFR4      IO  0x19 8 <br>
     *  TOV4       2 <br>
     *  OCF4B      5 <br>
     *  OCF4A      6 <br>
     *  OCF4D      7 <br>
     */
    @NativeCVariable8
    public static volatile int TIFR4;
    public static final int TOV4       = 2;
    public static final int OCF4B      = 5;
    public static final int OCF4A      = 6;
    public static final int OCF4D      = 7;

    /** TIFR5      IO  0x1A 8 <br>
     */
    @NativeCVariable8
    public static volatile int TIFR5;

    /** EIFR       IO  0x1C 8 <br>
     *  INTF0      0 <br>
     *  INTF1      1 <br>
     *  INTF2      2 <br>
     *  INTF3      3 <br>
     *  INTF4      4 <br>
     *  INTF5      5 <br>
     *  INTF6      6 <br>
     *  INTF7      7 <br>
     */
    public static final int INTF2      = 2;
    public static final int INTF3      = 3;
    public static final int INTF4      = 4;
    public static final int INTF5      = 5;
    public static final int INTF6      = 6;
    public static final int INTF7      = 7;

    /** EIMSK      IO  0x1D 8 <br>
     *  INT0       0 <br>
     *  INT1       1 <br>
     *  INT2       2 <br>
     *  INT3       3 <br>
     *  INT4       4 <br>
     *  INT5       5 <br>
     *  INT6       6 <br>
     *  INT7       7 <br>
     */
    public static final int INT2       = 2;
    public static final int INT3       = 3;
    public static final int INT4       = 4;
    public static final int INT5       = 5;
    public static final int INT6       = 6;
    public static final int INT7       = 7;

    /** EEARH      IO  0x22 8 <br>
     *  EEAR8      0 <br>
     *  EEAR9      1 <br>
     *  EEAR10     2 <br>
     *  EEAR11     3 <br>
     */
    public static final int EEAR10     = 2;
    public static final int EEAR11     = 3;

    /** PLLCSR     IO  0x29 8 <br>
     *  PLOCK      0 <br>
     *  PLLE       1 <br>
     *  PINDIV     4 <br>
     */
    @NativeCVariable8
    public static volatile int PLLCSR;
    public static final int PLOCK      = 0;
    public static final int PLLE       = 1;
    public static final int PINDIV     = 4;

    /** OCDR       IO  0x31 8 <br>
     *  OCDR0      0 <br>
     *  OCDR1      1 <br>
     *  OCDR2      2 <br>
     *  OCDR3      3 <br>
     *  OCDR4      4 <br>
     *  OCDR5      5 <br>
     *  OCDR6      6 <br>
     *  OCDR7      7 <br>
     */
    @NativeCVariable8
    public static volatile int OCDR;
    public static final int OCDR0      = 0;
    public static final int OCDR1      = 1;
    public static final int OCDR2      = 2;
    public static final int OCDR3      = 3;
    public static final int OCDR4      = 4;
    public static final int OCDR5      = 5;
    public static final int OCDR6      = 6;
    public static final int OCDR7      = 7;

    /** PLLFRQ     IO  0x32 8 <br>
     *  PDIV0      0 <br>
     *  PDIV1      1 <br>
     *  PDIV2      2 <br>
     *  PDIV3      3 <br>
     *  PLLTM0     4 <br>
     *  PLLTM1     5 <br>
     *  PLLUSB     6 <br>
     *  PINMUX     7 <br>
     */
    @NativeCVariable8
    public static volatile int PLLFRQ;
    public static final int PDIV0      = 0;
    public static final int PDIV1      = 1;
    public static final int PDIV2      = 2;
    public static final int PDIV3      = 3;
    public static final int PLLTM0     = 4;
    public static final int PLLTM1     = 5;
    public static final int PLLUSB     = 6;
    public static final int PINMUX     = 7;

    /** MCUSR      IO  0x34 8 <br>
     *  PORF       0 <br>
     *  EXTRF      1 <br>
     *  BORF       2 <br>
     *  WDRF       3 <br>
     *  JTRF       4 <br>
     */
    public static final int JTRF       = 4;

    /** MCUCR      IO  0x35 8 <br>
     *  IVCE       0 <br>
     *  IVSEL      1 <br>
     *  PUD        4 <br>
     *  JTD        7 <br>
     */
    public static final int JTD        = 7;

    /** SPMCSR     IO  0x37 8 <br>
     *  SPMEN      0 <br>
     *  PGERS      1 <br>
     *  PGWRT      2 <br>
     *  BLBSET     3 <br>
     *  RWWSRE     4 <br>
     *  SIGRD      5 <br>
     *  RWWSB      6 <br>
     *  SPMIE      7 <br>
     */
    public static final int SPMEN      = 0;
    public static final int SIGRD      = 5;

    /** RAMPZ      IO  0x3B 8 <br>
     *  RAMPZ0     0 <br>
     */
    @NativeCVariable8
    public static volatile int RAMPZ;
    public static final int RAMPZ0     = 0;

    /** EIND       IO  0x3C 8 <br>
     *  EIND0      0 <br>
     */
    @NativeCVariable8
    public static volatile int EIND;
    public static final int EIND0      = 0;

    /** PRR0       MEM Addr=0x64 Bits=8 <br>
     *  PRADC      0 <br>
     *  PRUSART0   1 <br>
     *  PRSPI      2 <br>
     *  PRTIM1     3 <br>
     *  PRTIM0     5 <br>
     *  PRTIM2     6 <br>
     *  PRTWI      7 <br>
     */
    @NativeCVariable8
    public static volatile int PRR0;

    /** PRR1       MEM Addr=0x65 Bits=8 <br>
     *  PRUSART1   0 <br>
     *  PRTIM3     3 <br>
     *  PRUSB      7 <br>
     */
    @NativeCVariable8
    public static volatile int PRR1;
    public static final int PRUSART1   = 0;
    public static final int PRTIM3     = 3;
    public static final int PRUSB      = 7;

    /** RCCTRL     MEM Addr=0x67 Bits=8 <br>
     *  RCFREQ     0 <br>
     */
    @NativeCVariable8
    public static volatile int RCCTRL;
    public static final int RCFREQ     = 0;

    /** EICRA      MEM Addr=0x69 Bits=8 <br>
     *  ISC00      0 <br>
     *  ISC01      1 <br>
     *  ISC10      2 <br>
     *  ISC11      3 <br>
     *  ISC20      4 <br>
     *  ISC21      5 <br>
     *  ISC30      6 <br>
     *  ISC31      7 <br>
     */
    public static final int ISC20      = 4;
    public static final int ISC21      = 5;
    public static final int ISC30      = 6;
    public static final int ISC31      = 7;

    /** EICRB      MEM Addr=0x6A Bits=8 <br>
     *  ISC40      0 <br>
     *  ISC41      1 <br>
     *  ISC50      2 <br>
     *  ISC51      3 <br>
     *  ISC60      4 <br>
     *  ISC61      5 <br>
     *  ISC70      6 <br>
     *  ISC71      7 <br>
     */
    @NativeCVariable8
    public static volatile int EICRB;
    public static final int ISC40      = 0;
    public static final int ISC41      = 1;
    public static final int ISC50      = 2;
    public static final int ISC51      = 3;
    public static final int ISC60      = 4;
    public static final int ISC61      = 5;
    public static final int ISC70      = 6;
    public static final int ISC71      = 7;

    /** TIMSK1     MEM Addr=0x6F Bits=8 <br>
     *  TOIE1      0 <br>
     *  OCIE1A     1 <br>
     *  OCIE1B     2 <br>
     *  OCIE1C     3 <br>
     *  ICIE1      5 <br>
     */
    public static final int OCIE1C     = 3;
    /** TIMSK3     MEM Addr=0x71 Bits=8 <br>
     *  TOIE3      0 <br>
     *  OCIE3A     1 <br>
     *  OCIE3B     2 <br>
     *  OCIE3C     3 <br>
     *  ICIE3      5 <br>
     */
    @NativeCVariable8
    public static volatile int TIMSK3;
    public static final int TOIE3      = 0;
    public static final int OCIE3A     = 1;
    public static final int OCIE3B     = 2;
    public static final int OCIE3C     = 3;
    public static final int ICIE3      = 5;

    /** TIMSK4     MEM Addr=0x72 Bits=8 <br>
     *  TOIE4      2 <br>
     *  OCIE4B     5 <br>
     *  OCIE4A     6 <br>
     *  OCIE4D     7 <br>
     */
    @NativeCVariable8
    public static volatile int TIMSK4;
    public static final int TOIE4      = 2;
    public static final int OCIE4B     = 5;
    public static final int OCIE4A     = 6;
    public static final int OCIE4D     = 7;

    /** TIMSK5     MEM Addr=0x73 Bits=8 <br>
     */
    @NativeCVariable8
    public static volatile int TIMSK5;

    /** ADCSRB     MEM Addr=0x7B Bits=8 <br>
     *  ADTS0      0 <br>
     *  ADTS1      1 <br>
     *  ADTS2      2 <br>
     *  ADTS3      4 <br>
     *  MUX5       5 <br>
     *  ACME       6 <br>
     *  ADHSM      7 <br>
     */
    public static final int ADTS3      = 4;
    public static final int MUX5       = 5;
    public static final int ADHSM      = 7;

    /** ADMUX      MEM Addr=0x7C Bits=8 <br>
     *  MUX0       0 <br>
     *  MUX1       1 <br>
     *  MUX2       2 <br>
     *  MUX3       3 <br>
     *  MUX4       4 <br>
     *  ADLAR      5 <br>
     *  REFS0      6 <br>
     *  REFS1      7 <br>
     */
    public static final int MUX4       = 4;

    /** DIDR2      MEM Addr=0x7D Bits=8 <br>
     *  ADC8D      0 <br>
     *  ADC9D      1 <br>
     *  ADC10D     2 <br>
     *  ADC11D     3 <br>
     *  ADC12D     4 <br>
     *  ADC13D     5 <br>
     */
    @NativeCVariable8
    public static volatile int DIDR2;
    public static final int ADC8D      = 0;
    public static final int ADC9D      = 1;
    public static final int ADC10D     = 2;
    public static final int ADC11D     = 3;
    public static final int ADC12D     = 4;
    public static final int ADC13D     = 5;

    /** DIDR0      MEM Addr=0x7E Bits=8 <br>
     *  ADC0D      0 <br>
     *  ADC1D      1 <br>
     *  ADC2D      2 <br>
     *  ADC3D      3 <br>
     *  ADC4D      4 <br>
     *  ADC5D      5 <br>
     *  ADC6D      6 <br>
     *  ADC7D      7 <br>
     */
    public static final int ADC6D      = 6;
    public static final int ADC7D      = 7;

    /** TCCR1A     MEM Addr=0x80 Bits=8 <br>
     *  WGM10      0 <br>
     *  WGM11      1 <br>
     *  COM1C0     2 <br>
     *  COM1C1     3 <br>
     *  COM1B0     4 <br>
     *  COM1B1     5 <br>
     *  COM1A0     6 <br>
     *  COM1A1     7 <br>
     */
    public static final int COM1C0     = 2;
    public static final int COM1C1     = 3;

    /** TCCR1C     MEM Addr=0x82 Bits=8 <br>
     *  FOC1C      5 <br>
     *  FOC1B      6 <br>
     *  FOC1A      7 <br>
     */
    public static final int FOC1C      = 5;

    /** OCR1C      MEM Addr=0x8C Bits=16 <br>
     */
    @NativeCVariable16
    public static volatile int OCR1C;

    /** OCR1CL     MEM Addr=0x8C Bits=8 <br>
     *  OCR1CL0    0 <br>
     *  OCR1CL1    1 <br>
     *  OCR1CL2    2 <br>
     *  OCR1CL3    3 <br>
     *  OCR1CL4    4 <br>
     *  OCR1CL5    5 <br>
     *  OCR1CL6    6 <br>
     *  OCR1CL7    7 <br>
     */
    @NativeCVariable8
    public static volatile int OCR1CL;
    public static final int OCR1CL0    = 0;
    public static final int OCR1CL1    = 1;
    public static final int OCR1CL2    = 2;
    public static final int OCR1CL3    = 3;
    public static final int OCR1CL4    = 4;
    public static final int OCR1CL5    = 5;
    public static final int OCR1CL6    = 6;
    public static final int OCR1CL7    = 7;

    /** OCR1CH     MEM Addr=0x8D Bits=8 <br>
     *  OCR1CH0    0 <br>
     *  OCR1CH1    1 <br>
     *  OCR1CH2    2 <br>
     *  OCR1CH3    3 <br>
     *  OCR1CH4    4 <br>
     *  OCR1CH5    5 <br>
     *  OCR1CH6    6 <br>
     *  OCR1CH7    7 <br>
     */
    @NativeCVariable8
    public static volatile int OCR1CH;
    public static final int OCR1CH0    = 0;
    public static final int OCR1CH1    = 1;
    public static final int OCR1CH2    = 2;
    public static final int OCR1CH3    = 3;
    public static final int OCR1CH4    = 4;
    public static final int OCR1CH5    = 5;
    public static final int OCR1CH6    = 6;
    public static final int OCR1CH7    = 7;

    /** TCCR3A     MEM Addr=0x90 Bits=8 <br>
     *  WGM30      0 <br>
     *  WGM31      1 <br>
     *  COM3C0     2 <br>
     *  COM3C1     3 <br>
     *  COM3B0     4 <br>
     *  COM3B1     5 <br>
     *  COM3A0     6 <br>
     *  COM3A1     7 <br>
     */
    public static final int WGM31      = 1;
    public static final int COM3C0     = 2;
    public static final int COM3C1     = 3;
    public static final int COM3B0     = 4;
    public static final int COM3B1     = 5;
    public static final int COM3A0     = 6;
    public static final int COM3A1     = 7;

    /** TCCR3B     MEM Addr=0x91 Bits=8 <br>
     *  CS30       0 <br>
     *  CS31       1 <br>
     *  CS32       2 <br>
     *  WGM32      3 <br>
     *  WGM33      4 <br>
     *  ICES3      6 <br>
     *  ICNC3      7 <br>
     */
    public static final int CS32       = 2;
    public static final int WGM32      = 3;
    public static final int WGM33      = 4;
    public static final int ICES3      = 6;
    public static final int ICNC3      = 7;

    /** TCCR3C     MEM Addr=0x92 Bits=8 <br>
     *  FOC3C      5 <br>
     *  FOC3B      6 <br>
     *  FOC3A      7 <br>
     */
    @NativeCVariable8
    public static volatile int TCCR3C;
    public static final int FOC3C      = 5;
    public static final int FOC3B      = 6;
    public static final int FOC3A      = 7;

    /** TCNT3      MEM Addr=0x94 Bits=16 <br>
     */
    @NativeCVariable16
    public static volatile int TCNT3;

    /** TCNT3L     MEM Addr=0x94 Bits=8 <br>
     *  TCNT3L0    0 <br>
     *  TCNT3L1    1 <br>
     *  TCNT3L2    2 <br>
     *  TCNT3L3    3 <br>
     *  TCNT3L4    4 <br>
     *  TCNT3L5    5 <br>
     *  TCNT3L6    6 <br>
     *  TCNT3L7    7 <br>
     */
    @NativeCVariable8
    public static volatile int TCNT3L;
    public static final int TCNT3L0    = 0;
    public static final int TCNT3L1    = 1;
    public static final int TCNT3L2    = 2;
    public static final int TCNT3L3    = 3;
    public static final int TCNT3L4    = 4;
    public static final int TCNT3L5    = 5;
    public static final int TCNT3L6    = 6;
    public static final int TCNT3L7    = 7;

    /** TCNT3H     MEM Addr=0x95 Bits=8 <br>
     *  TCNT3H0    0 <br>
     *  TCNT3H1    1 <br>
     *  TCNT3H2    2 <br>
     *  TCNT3H3    3 <br>
     *  TCNT3H4    4 <br>
     *  TCNT3H5    5 <br>
     *  TCNT3H6    6 <br>
     *  TCNT3H7    7 <br>
     */
    @NativeCVariable8
    public static volatile int TCNT3H;
    public static final int TCNT3H0    = 0;
    public static final int TCNT3H1    = 1;
    public static final int TCNT3H2    = 2;
    public static final int TCNT3H3    = 3;
    public static final int TCNT3H4    = 4;
    public static final int TCNT3H5    = 5;
    public static final int TCNT3H6    = 6;
    public static final int TCNT3H7    = 7;

    /** ICR3       MEM Addr=0x96 Bits=16 <br>
     */
    @NativeCVariable16
    public static volatile int ICR3;

    /** ICR3L      MEM Addr=0x96 Bits=8 <br>
     *  ICR3L0     0 <br>
     *  ICR3L1     1 <br>
     *  ICR3L2     2 <br>
     *  ICR3L3     3 <br>
     *  ICR3L4     4 <br>
     *  ICR3L5     5 <br>
     *  ICR3L6     6 <br>
     *  ICR3L7     7 <br>
     */
    @NativeCVariable8
    public static volatile int ICR3L;
    public static final int ICR3L0     = 0;
    public static final int ICR3L1     = 1;
    public static final int ICR3L2     = 2;
    public static final int ICR3L3     = 3;
    public static final int ICR3L4     = 4;
    public static final int ICR3L5     = 5;
    public static final int ICR3L6     = 6;
    public static final int ICR3L7     = 7;

    /** ICR3H      MEM Addr=0x97 Bits=8 <br>
     *  ICR3H0     0 <br>
     *  ICR3H1     1 <br>
     *  ICR3H2     2 <br>
     *  ICR3H3     3 <br>
     *  ICR3H4     4 <br>
     *  ICR3H5     5 <br>
     *  ICR3H6     6 <br>
     *  ICR3H7     7 <br>
     */
    @NativeCVariable8
    public static volatile int ICR3H;
    public static final int ICR3H0     = 0;
    public static final int ICR3H1     = 1;
    public static final int ICR3H2     = 2;
    public static final int ICR3H3     = 3;
    public static final int ICR3H4     = 4;
    public static final int ICR3H5     = 5;
    public static final int ICR3H6     = 6;
    public static final int ICR3H7     = 7;

    /** OCR3A      MEM Addr=0x98 Bits=16 <br>
     */
    @NativeCVariable16
    public static volatile int OCR3A;

    /** OCR3AL     MEM Addr=0x98 Bits=8 <br>
     *  OCR3AL0    0 <br>
     *  OCR3AL1    1 <br>
     *  OCR3AL2    2 <br>
     *  OCR3AL3    3 <br>
     *  OCR3AL4    4 <br>
     *  OCR3AL5    5 <br>
     *  OCR3AL6    6 <br>
     *  OCR3AL7    7 <br>
     */
    @NativeCVariable8
    public static volatile int OCR3AL;
    public static final int OCR3AL0    = 0;
    public static final int OCR3AL1    = 1;
    public static final int OCR3AL2    = 2;
    public static final int OCR3AL3    = 3;
    public static final int OCR3AL4    = 4;
    public static final int OCR3AL5    = 5;
    public static final int OCR3AL6    = 6;
    public static final int OCR3AL7    = 7;

    /** OCR3AH     MEM Addr=0x99 Bits=8 <br>
     *  OCR3AH0    0 <br>
     *  OCR3AH1    1 <br>
     *  OCR3AH2    2 <br>
     *  OCR3AH3    3 <br>
     *  OCR3AH4    4 <br>
     *  OCR3AH5    5 <br>
     *  OCR3AH6    6 <br>
     *  OCR3AH7    7 <br>
     */
    @NativeCVariable8
    public static volatile int OCR3AH;
    public static final int OCR3AH0    = 0;
    public static final int OCR3AH1    = 1;
    public static final int OCR3AH2    = 2;
    public static final int OCR3AH3    = 3;
    public static final int OCR3AH4    = 4;
    public static final int OCR3AH5    = 5;
    public static final int OCR3AH6    = 6;
    public static final int OCR3AH7    = 7;

    /** OCR3B      MEM Addr=0x9A Bits=16 <br>
     */
    @NativeCVariable16
    public static volatile int OCR3B;

    /** OCR3BL     MEM Addr=0x9A Bits=8 <br>
     *  OCR3BL0    0 <br>
     *  OCR3BL1    1 <br>
     *  OCR3BL2    2 <br>
     *  OCR3BL3    3 <br>
     *  OCR3BL4    4 <br>
     *  OCR3BL5    5 <br>
     *  OCR3BL6    6 <br>
     *  OCR3BL7    7 <br>
     */
    @NativeCVariable8
    public static volatile int OCR3BL;
    public static final int OCR3BL0    = 0;
    public static final int OCR3BL1    = 1;
    public static final int OCR3BL2    = 2;
    public static final int OCR3BL3    = 3;
    public static final int OCR3BL4    = 4;
    public static final int OCR3BL5    = 5;
    public static final int OCR3BL6    = 6;
    public static final int OCR3BL7    = 7;

    /** OCR3BH     MEM Addr=0x9B Bits=8 <br>
     *  OCR3BH0    0 <br>
     *  OCR3BH1    1 <br>
     *  OCR3BH2    2 <br>
     *  OCR3BH3    3 <br>
     *  OCR3BH4    4 <br>
     *  OCR3BH5    5 <br>
     *  OCR3BH6    6 <br>
     *  OCR3BH7    7 <br>
     */
    @NativeCVariable8
    public static volatile int OCR3BH;
    public static final int OCR3BH0    = 0;
    public static final int OCR3BH1    = 1;
    public static final int OCR3BH2    = 2;
    public static final int OCR3BH3    = 3;
    public static final int OCR3BH4    = 4;
    public static final int OCR3BH5    = 5;
    public static final int OCR3BH6    = 6;
    public static final int OCR3BH7    = 7;

    /** OCR3C      MEM Addr=0x9C Bits=16 <br>
     */
    @NativeCVariable16
    public static volatile int OCR3C;

    /** OCR3CL     MEM Addr=0x9C Bits=8 <br>
     *  OCR3CL0    0 <br>
     *  OCR3CL1    1 <br>
     *  OCR3CL2    2 <br>
     *  OCR3CL3    3 <br>
     *  OCR3CL4    4 <br>
     *  OCR3CL5    5 <br>
     *  OCR3CL6    6 <br>
     *  OCR3CL7    7 <br>
     */
    @NativeCVariable8
    public static volatile int OCR3CL;
    public static final int OCR3CL0    = 0;
    public static final int OCR3CL1    = 1;
    public static final int OCR3CL2    = 2;
    public static final int OCR3CL3    = 3;
    public static final int OCR3CL4    = 4;
    public static final int OCR3CL5    = 5;
    public static final int OCR3CL6    = 6;
    public static final int OCR3CL7    = 7;

    /** OCR3CH     MEM Addr=0x9D Bits=8 <br>
     *  OCR3CH0    0 <br>
     *  OCR3CH1    1 <br>
     *  OCR3CH2    2 <br>
     *  OCR3CH3    3 <br>
     *  OCR3CH4    4 <br>
     *  OCR3CH5    5 <br>
     *  OCR3CH6    6 <br>
     *  OCR3CH7    7 <br>
     */
    @NativeCVariable8
    public static volatile int OCR3CH;
    public static final int OCR3CH0    = 0;
    public static final int OCR3CH1    = 1;
    public static final int OCR3CH2    = 2;
    public static final int OCR3CH3    = 3;
    public static final int OCR3CH4    = 4;
    public static final int OCR3CH5    = 5;
    public static final int OCR3CH6    = 6;
    public static final int OCR3CH7    = 7;

    /** UHCON      MEM Addr=0x9E Bits=8 <br>
     */
    @NativeCVariable8
    public static volatile int UHCON;

    /** UHINT      MEM Addr=0x9F Bits=8 <br>
     */
    @NativeCVariable8
    public static volatile int UHINT;

    /** UHIEN      MEM Addr=0xA0 Bits=8 <br>
     */
    @NativeCVariable8
    public static volatile int UHIEN;

    /** UHADDR     MEM Addr=0xA1 Bits=8 <br>
     */
    @NativeCVariable8
    public static volatile int UHADDR;

    /** UHFNUM     MEM Addr=0xA2 Bits=16 <br>
     */
    @NativeCVariable16
    public static volatile int UHFNUM;

    /** UHFNUML    MEM Addr=0xA2 Bits=8 <br>
     */
    @NativeCVariable8
    public static volatile int UHFNUML;

    /** UHFNUMH    MEM Addr=0xA3 Bits=8 <br>
     */
    @NativeCVariable8
    public static volatile int UHFNUMH;

    /** UHFLEN     MEM Addr=0xA4 Bits=8 <br>
     */
    @NativeCVariable8
    public static volatile int UHFLEN;

    /** UPINRQX    MEM Addr=0xA5 Bits=8 <br>
     */
    @NativeCVariable8
    public static volatile int UPINRQX;

    /** UPINTX     MEM Addr=0xA6 Bits=8 <br>
     */
    @NativeCVariable8
    public static volatile int UPINTX;

    /** UPNUM      MEM Addr=0xA7 Bits=8 <br>
     */
    @NativeCVariable8
    public static volatile int UPNUM;

    /** UPRST      MEM Addr=0xA8 Bits=8 <br>
     */
    @NativeCVariable8
    public static volatile int UPRST;

    /** UPCONX     MEM Addr=0xA9 Bits=8 <br>
     */
    @NativeCVariable8
    public static volatile int UPCONX;

    /** UPCFG0X    MEM Addr=0xAA Bits=8 <br>
     */
    @NativeCVariable8
    public static volatile int UPCFG0X;

    /** UPCFG1X    MEM Addr=0xAB Bits=8 <br>
     */
    @NativeCVariable8
    public static volatile int UPCFG1X;

    /** UPSTAX     MEM Addr=0xAC Bits=8 <br>
     */
    @NativeCVariable8
    public static volatile int UPSTAX;

    /** UPCFG2X    MEM Addr=0xAD Bits=8 <br>
     */
    @NativeCVariable8
    public static volatile int UPCFG2X;

    /** UPIENX     MEM Addr=0xAE Bits=8 <br>
     */
    @NativeCVariable8
    public static volatile int UPIENX;

    /** UPDATX     MEM Addr=0xAF Bits=8 <br>
     */
    @NativeCVariable8
    public static volatile int UPDATX;

    /** TCNT4      MEM Addr=0xBE Bits=16 <br>
     */
    @NativeCVariable16
    public static volatile int TCNT4;

    /** TCNT4L     MEM Addr=0xBE Bits=8 <br>
     *  TC40       0 <br>
     *  TC41       1 <br>
     *  TC42       2 <br>
     *  TC43       3 <br>
     *  TC44       4 <br>
     *  TC45       5 <br>
     *  TC46       6 <br>
     *  TC47       7 <br>
     */
    @NativeCVariable8
    public static volatile int TCNT4L;
    public static final int TC40       = 0;
    public static final int TC41       = 1;
    public static final int TC42       = 2;
    public static final int TC43       = 3;
    public static final int TC44       = 4;
    public static final int TC45       = 5;
    public static final int TC46       = 6;
    public static final int TC47       = 7;

    /** TCNT4H     MEM Addr=0xBF Bits=8 <br>
     *  Alias for naming consistency. <br>
     */
    @NativeCVariable8
    public static volatile int TCNT4H;

    /** TC4H       MEM Addr=0xBF Bits=8 <br>
     *  Per XML device file. <br>
     *  TC48       0 <br>
     *  TC49       1 <br>
     *  TC410      2 <br>
     */
    @NativeCVariable8
    public static volatile int TC4H;
    public static final int TC48       = 0;
    public static final int TC49       = 1;
    public static final int TC410      = 2;

    /** TCCR4A     MEM Addr=0xC0 Bits=8 <br>
     *  PWM4B      0 <br>
     *  PWM4A      1 <br>
     *  FOC4B      2 <br>
     *  FOC4A      3 <br>
     *  COM4B0     4 <br>
     *  COM4B1     5 <br>
     *  COM4A0     6 <br>
     *  COM4A1     7 <br>
     */
    public static final int PWM4B      = 0;
    public static final int PWM4A      = 1;
    public static final int FOC4B      = 2;
    public static final int FOC4A      = 3;
    public static final int COM4B0     = 4;
    public static final int COM4B1     = 5;
    public static final int COM4A0     = 6;
    public static final int COM4A1     = 7;

    /** TCCR4B     MEM Addr=0xC1 Bits=8 <br>
     *  CS40       0 <br>
     *  CS41       1 <br>
     *  CS42       2 <br>
     *  CS43       3 <br>
     *  DTPS40     4 <br>
     *  DTPS41     5 <br>
     *  PSR4       6 <br>
     *  PWM4X      7 <br>
     */
    public static final int CS42       = 2;
    public static final int CS43       = 3;
    public static final int DTPS40     = 4;
    public static final int DTPS41     = 5;
    public static final int PSR4       = 6;
    public static final int PWM4X      = 7;

    /** TCCR4C     MEM Addr=0xC2 Bits=8 <br>
     *  PWM4D      0 <br>
     *  FOC4D      1 <br>
     *  COM4D0     2 <br>
     *  COM4D1     3 <br>
     *  COM4B0S    4 <br>
     *  COM4B1S    5 <br>
     *  COM4A0S    6 <br>
     *  COM4A1S    7 <br>
     */
    @NativeCVariable8
    public static volatile int TCCR4C;
    public static final int PWM4D      = 0;
    public static final int FOC4D      = 1;
    public static final int COM4D0     = 2;
    public static final int COM4D1     = 3;
    public static final int COM4B0S    = 4;
    public static final int COM4B1S    = 5;
    public static final int COM4A0S    = 6;
    public static final int COM4A1S    = 7;

    /** TCCR4D     MEM Addr=0xC3 Bits=8 <br>
     *  WGM40      0 <br>
     *  WGM41      1 <br>
     *  FPF4       2 <br>
     *  FPAC4      3 <br>
     *  FPES4      4 <br>
     *  FPNC4      5 <br>
     *  FPEN4      6 <br>
     *  FPIE4      7 <br>
     */
    @NativeCVariable8
    public static volatile int TCCR4D;
    public static final int WGM41      = 1;
    public static final int FPF4       = 2;
    public static final int FPAC4      = 3;
    public static final int FPES4      = 4;
    public static final int FPNC4      = 5;
    public static final int FPEN4      = 6;
    public static final int FPIE4      = 7;

    /** TCCR4E     MEM Addr=0xC4 Bits=8 <br>
     *  OC4OE0     0 <br>
     *  OC4OE1     1 <br>
     *  OC4OE2     2 <br>
     *  OC4OE3     3 <br>
     *  OC4OE4     4 <br>
     *  OC4OE5     5 <br>
     *  ENHC4      6 <br>
     *  TLOCK4     7 <br>
     */
    @NativeCVariable8
    public static volatile int TCCR4E;
    public static final int OC4OE0     = 0;
    public static final int OC4OE1     = 1;
    public static final int OC4OE2     = 2;
    public static final int OC4OE3     = 3;
    public static final int OC4OE4     = 4;
    public static final int OC4OE5     = 5;
    public static final int ENHC4      = 6;
    public static final int TLOCK4     = 7;

    /** CLKSEL0    MEM Addr=0xC5 Bits=8 <br>
     *  CLKS       0 <br>
     *  EXTE       2 <br>
     *  RCE        3 <br>
     *  EXSUT0     4 <br>
     *  EXSUT1     5 <br>
     *  RCSUT0     6 <br>
     *  RCSUT1     7 <br>
     */
    @NativeCVariable8
    public static volatile int CLKSEL0;
    public static final int CLKS       = 0;
    public static final int EXTE       = 2;
    public static final int RCE        = 3;
    public static final int EXSUT0     = 4;
    public static final int EXSUT1     = 5;
    public static final int RCSUT0     = 6;
    public static final int RCSUT1     = 7;

    /** CLKSEL1    MEM Addr=0xC6 Bits=8 <br>
     *  EXCKSEL0   0 <br>
     *  EXCKSEL1   1 <br>
     *  EXCKSEL2   2 <br>
     *  EXCKSEL3   3 <br>
     *  RCCKSEL0   4 <br>
     *  RCCKSEL1   5 <br>
     *  RCCKSEL2   6 <br>
     *  RCCKSEL3   7 <br>
     */
    @NativeCVariable8
    public static volatile int CLKSEL1;
    public static final int EXCKSEL0   = 0;
    public static final int EXCKSEL1   = 1;
    public static final int EXCKSEL2   = 2;
    public static final int EXCKSEL3   = 3;
    public static final int RCCKSEL0   = 4;
    public static final int RCCKSEL1   = 5;
    public static final int RCCKSEL2   = 6;
    public static final int RCCKSEL3   = 7;

    /** CLKSTA     MEM Addr=0xC7 Bits=8 <br>
     *  EXTON      0 <br>
     *  RCON       1 <br>
     */
    @NativeCVariable8
    public static volatile int CLKSTA;
    public static final int EXTON      = 0;
    public static final int RCON       = 1;

    /** UCSR1A     MEM Addr=0xC8 Bits=8 <br>
     *  MPCM1      0 <br>
     *  U2X1       1 <br>
     *  UPE1       2 <br>
     *  DOR1       3 <br>
     *  FE1        4 <br>
     *  UDRE1      5 <br>
     *  TXC1       6 <br>
     *  RXC1       7 <br>
     */
    @NativeCVariable8
    public static volatile int UCSR1A;
    public static final int MPCM1      = 0;
    public static final int U2X1       = 1;
    public static final int UPE1       = 2;
    public static final int DOR1       = 3;
    public static final int FE1        = 4;
    public static final int UDRE1      = 5;
    public static final int TXC1       = 6;
    public static final int RXC1       = 7;

    /** UCSR1B     MEM Addr=0xC9 Bits=8 <br>
     *  TXB81      0 <br>
     *  RXB81      1 <br>
     *  UCSZ12     2 <br>
     *  TXEN1      3 <br>
     *  RXEN1      4 <br>
     *  UDRIE1     5 <br>
     *  TXCIE1     6 <br>
     *  RXCIE1     7 <br>
     */
    @NativeCVariable8
    public static volatile int UCSR1B;
    public static final int TXB81      = 0;
    public static final int RXB81      = 1;
    public static final int UCSZ12     = 2;
    public static final int TXEN1      = 3;
    public static final int RXEN1      = 4;
    public static final int UDRIE1     = 5;
    public static final int TXCIE1     = 6;
    public static final int RXCIE1     = 7;

    /** UCSR1C     MEM Addr=0xCA Bits=8 <br>
     *  UCPOL1     0 <br>
     *  UCSZ10     1 <br>
     *  UCSZ11     2 <br>
     *  USBS1      3 <br>
     *  UPM10      4 <br>
     *  UPM11      5 <br>
     *  UMSEL10    6 <br>
     *  UMSEL11    7 <br>
     */
    @NativeCVariable8
    public static volatile int UCSR1C;
    public static final int UCPOL1     = 0;
    public static final int UCSZ10     = 1;
    public static final int UCSZ11     = 2;
    public static final int USBS1      = 3;
    public static final int UPM10      = 4;
    public static final int UPM11      = 5;
    public static final int UMSEL10    = 6;
    public static final int UMSEL11    = 7;

    /** UBRR1      MEM Addr=0xCC Bits=16 <br>
     */
    @NativeCVariable16
    public static volatile int UBRR1;

    /** UBRR1L     MEM Addr=0xCC Bits=8 <br>
     */
    @NativeCVariable8
    public static volatile int UBRR1L;

    /** UBRR1H     MEM Addr=0xCD Bits=8 <br>
     */
    @NativeCVariable8
    public static volatile int UBRR1H;

    /** UDR1       MEM Addr=0xCE Bits=8 <br>
     *  UDR1_0     0 <br>
     *  UDR1_1     1 <br>
     *  UDR1_2     2 <br>
     *  UDR1_3     3 <br>
     *  UDR1_4     4 <br>
     *  UDR1_5     5 <br>
     *  UDR1_6     6 <br>
     *  UDR1_7     7 <br>
     */
    @NativeCVariable8
    public static volatile int UDR1;
    public static final int UDR1_0     = 0;
    public static final int UDR1_1     = 1;
    public static final int UDR1_2     = 2;
    public static final int UDR1_3     = 3;
    public static final int UDR1_4     = 4;
    public static final int UDR1_5     = 5;
    public static final int UDR1_6     = 6;
    public static final int UDR1_7     = 7;

    /** OCR4A      MEM Addr=0xCF Bits=8 <br>
     *  OCR4A0     0 <br>
     *  OCR4A1     1 <br>
     *  OCR4A2     2 <br>
     *  OCR4A3     3 <br>
     *  OCR4A4     4 <br>
     *  OCR4A5     5 <br>
     *  OCR4A6     6 <br>
     *  OCR4A7     7 <br>
     */
    @NativeCVariable8
    public static volatile int OCR4A;
    public static final int OCR4A0     = 0;
    public static final int OCR4A1     = 1;
    public static final int OCR4A2     = 2;
    public static final int OCR4A3     = 3;
    public static final int OCR4A4     = 4;
    public static final int OCR4A5     = 5;
    public static final int OCR4A6     = 6;
    public static final int OCR4A7     = 7;

    /** OCR4B      MEM Addr=0xD0 Bits=8 <br>
     *  OCR4B0     0 <br>
     *  OCR4B1     1 <br>
     *  OCR4B2     2 <br>
     *  OCR4B3     3 <br>
     *  OCR4B4     4 <br>
     *  OCR4B5     5 <br>
     *  OCR4B6     6 <br>
     *  OCR4B7     7 <br>
     */
    @NativeCVariable8
    public static volatile int OCR4B;
    public static final int OCR4B0     = 0;
    public static final int OCR4B1     = 1;
    public static final int OCR4B2     = 2;
    public static final int OCR4B3     = 3;
    public static final int OCR4B4     = 4;
    public static final int OCR4B5     = 5;
    public static final int OCR4B6     = 6;
    public static final int OCR4B7     = 7;

    /** OCR4C      MEM Addr=0xD1 Bits=8 <br>
     *  OCR4C0     0 <br>
     *  OCR4C1     1 <br>
     *  OCR4C2     2 <br>
     *  OCR4C3     3 <br>
     *  OCR4C4     4 <br>
     *  OCR4C5     5 <br>
     *  OCR4C6     6 <br>
     *  OCR4C7     7 <br>
     */
    @NativeCVariable8
    public static volatile int OCR4C;
    public static final int OCR4C0     = 0;
    public static final int OCR4C1     = 1;
    public static final int OCR4C2     = 2;
    public static final int OCR4C3     = 3;
    public static final int OCR4C4     = 4;
    public static final int OCR4C5     = 5;
    public static final int OCR4C6     = 6;
    public static final int OCR4C7     = 7;

    /** OCR4D      MEM Addr=0xD2 Bits=8 <br>
     *  OCR4D0     0 <br>
     *  OCR4D1     1 <br>
     *  OCR4D2     2 <br>
     *  OCR4D3     3 <br>
     *  OCR4D4     4 <br>
     *  OCR4D5     5 <br>
     *  OCR4D6     6 <br>
     *  OCR4D7     7 <br>
     */
    @NativeCVariable8
    public static volatile int OCR4D;
    public static final int OCR4D0     = 0;
    public static final int OCR4D1     = 1;
    public static final int OCR4D2     = 2;
    public static final int OCR4D3     = 3;
    public static final int OCR4D4     = 4;
    public static final int OCR4D5     = 5;
    public static final int OCR4D6     = 6;
    public static final int OCR4D7     = 7;

    /** DT4        MEM Addr=0xD4 Bits=8 <br>
     *  DT4L0      0 <br>
     *  DT4L1      1 <br>
     *  DT4L2      2 <br>
     *  DT4L3      3 <br>
     *  DT4L4      4 <br>
     *  DT4L5      5 <br>
     *  DT4L6      6 <br>
     *  DT4L7      7 <br>
     */
    @NativeCVariable8
    public static volatile int DT4;
    public static final int DT4L0      = 0;
    public static final int DT4L1      = 1;
    public static final int DT4L2      = 2;
    public static final int DT4L3      = 3;
    public static final int DT4L4      = 4;
    public static final int DT4L5      = 5;
    public static final int DT4L6      = 6;
    public static final int DT4L7      = 7;

    /** UHWCON     MEM Addr=0xD7 Bits=8 <br>
     *  UVREGE     0 <br>
     */
    @NativeCVariable8
    public static volatile int UHWCON;
    public static final int UVREGE     = 0;

    /** USBCON     MEM Addr=0xD8 Bits=8 <br>
     *  VBUSTE     0 <br>
     *  OTGPADE    4 <br>
     *  FRZCLK     5 <br>
     *  USBE       7 <br>
     */
    @NativeCVariable8
    public static volatile int USBCON;
    public static final int VBUSTE     = 0;
    public static final int OTGPADE    = 4;
    public static final int FRZCLK     = 5;
    public static final int USBE       = 7;

    /** USBSTA     MEM Addr=0xD9 Bits=8 <br>
     *  VBUS       0 <br>
     *  SPEED      3 <br>
     */
    @NativeCVariable8
    public static volatile int USBSTA;
    public static final int VBUS       = 0;
    public static final int SPEED      = 3;

    /** USBINT     MEM Addr=0xDA Bits=8 <br>
     *  VBUSTI     0 <br>
     */
    @NativeCVariable8
    public static volatile int USBINT;
    public static final int VBUSTI     = 0;

    /** OTGCON     MEM Addr=0xDD Bits=8 <br>
     */
    @NativeCVariable8
    public static volatile int OTGCON;

    /** OTGIEN     MEM Addr=0xDE Bits=8 <br>
     */
    @NativeCVariable8
    public static volatile int OTGIEN;

    /** OTGINT     MEM Addr=0xDF Bits=8 <br>
     */
    @NativeCVariable8
    public static volatile int OTGINT;

    /** UDCON      MEM Addr=0xE0 Bits=8 <br>
     *  DETACH     0 <br>
     *  RMWKUP     1 <br>
     *  LSM        2 <br>
     *  RSTCPU     3 <br>
     */
    @NativeCVariable8
    public static volatile int UDCON;
    public static final int DETACH     = 0;
    public static final int RMWKUP     = 1;
    public static final int LSM        = 2;
    public static final int RSTCPU     = 3;

    /** UDINT      MEM Addr=0xE1 Bits=8 <br>
     *  SUSPI      0 <br>
     *  SOFI       2 <br>
     *  EORSTI     3 <br>
     *  WAKEUPI    4 <br>
     *  EORSMI     5 <br>
     *  UPRSMI     6 <br>
     */
    @NativeCVariable8
    public static volatile int UDINT;
    public static final int SUSPI      = 0;
    public static final int SOFI       = 2;
    public static final int EORSTI     = 3;
    public static final int WAKEUPI    = 4;
    public static final int EORSMI     = 5;
    public static final int UPRSMI     = 6;

    /** UDIEN      MEM Addr=0xE2 Bits=8 <br>
     *  SUSPE      0 <br>
     *  SOFE       2 <br>
     *  EORSTE     3 <br>
     *  WAKEUPE    4 <br>
     *  EORSME     5 <br>
     *  UPRSME     6 <br>
     */
    @NativeCVariable8
    public static volatile int UDIEN;
    public static final int SUSPE      = 0;
    public static final int SOFE       = 2;
    public static final int EORSTE     = 3;
    public static final int WAKEUPE    = 4;
    public static final int EORSME     = 5;
    public static final int UPRSME     = 6;

    /** UDADDR     MEM Addr=0xE3 Bits=8 <br>
     *  UADD0      0 <br>
     *  UADD1      1 <br>
     *  UADD2      2 <br>
     *  UADD3      3 <br>
     *  UADD4      4 <br>
     *  UADD5      5 <br>
     *  UADD6      6 <br>
     *  ADDEN      7 <br>
     */
    @NativeCVariable8
    public static volatile int UDADDR;
    public static final int UADD0      = 0;
    public static final int UADD1      = 1;
    public static final int UADD2      = 2;
    public static final int UADD3      = 3;
    public static final int UADD4      = 4;
    public static final int UADD5      = 5;
    public static final int UADD6      = 6;
    public static final int ADDEN      = 7;

    /** UDFNUM     MEM Addr=0xE4 Bits=16 <br>
     */
    @NativeCVariable16
    public static volatile int UDFNUM;

    /** UDFNUML    MEM Addr=0xE4 Bits=8 <br>
     *  FNUM0      0 <br>
     *  FNUM1      1 <br>
     *  FNUM2      2 <br>
     *  FNUM3      3 <br>
     *  FNUM4      4 <br>
     *  FNUM5      5 <br>
     *  FNUM6      6 <br>
     *  FNUM7      7 <br>
     */
    @NativeCVariable8
    public static volatile int UDFNUML;
    public static final int FNUM0      = 0;
    public static final int FNUM1      = 1;
    public static final int FNUM2      = 2;
    public static final int FNUM3      = 3;
    public static final int FNUM4      = 4;
    public static final int FNUM5      = 5;
    public static final int FNUM6      = 6;
    public static final int FNUM7      = 7;

    /** UDFNUMH    MEM Addr=0xE5 Bits=8 <br>
     *  FNUM8      0 <br>
     *  FNUM9      1 <br>
     *  FNUM10     2 <br>
     */
    @NativeCVariable8
    public static volatile int UDFNUMH;
    public static final int FNUM8      = 0;
    public static final int FNUM9      = 1;
    public static final int FNUM10     = 2;

    /** UDMFN      MEM Addr=0xE6 Bits=8 <br>
     *  FNCERR     4 <br>
     */
    @NativeCVariable8
    public static volatile int UDMFN;
    public static final int FNCERR     = 4;

    /** UDTST      MEM Addr=0xE7 Bits=8 <br>
     */
    @NativeCVariable8
    public static volatile int UDTST;

    /** UEINTX     MEM Addr=0xE8 Bits=8 <br>
     *  TXINI      0 <br>
     *  STALLEDI   1 <br>
     *  RXOUTI     2 <br>
     *  RXSTPI     3 <br>
     *  NAKOUTI    4 <br>
     *  RWAL       5 <br>
     *  NAKINI     6 <br>
     *  FIFOCON    7 <br>
     */
    @NativeCVariable8
    public static volatile int UEINTX;
    public static final int TXINI      = 0;
    public static final int STALLEDI   = 1;
    public static final int RXOUTI     = 2;
    public static final int RXSTPI     = 3;
    public static final int NAKOUTI    = 4;
    public static final int RWAL       = 5;
    public static final int NAKINI     = 6;
    public static final int FIFOCON    = 7;

    /** UENUM      MEM Addr=0xE9 Bits=8 <br>
     *  UENUM_0    0 <br>
     *  UENUM_1    1 <br>
     *  UENUM_2    2 <br>
     */
    @NativeCVariable8
    public static volatile int UENUM;
    public static final int UENUM_0    = 0;
    public static final int UENUM_1    = 1;
    public static final int UENUM_2    = 2;

    /** UERST      MEM Addr=0xEA Bits=8 <br>
     *  EPRST0     0 <br>
     *  EPRST1     1 <br>
     *  EPRST2     2 <br>
     *  EPRST3     3 <br>
     *  EPRST4     4 <br>
     *  EPRST5     5 <br>
     *  EPRST6     6 <br>
     */
    @NativeCVariable8
    public static volatile int UERST;
    public static final int EPRST0     = 0;
    public static final int EPRST1     = 1;
    public static final int EPRST2     = 2;
    public static final int EPRST3     = 3;
    public static final int EPRST4     = 4;
    public static final int EPRST5     = 5;
    public static final int EPRST6     = 6;

    /** UECONX     MEM Addr=0xEB Bits=8 <br>
     *  EPEN       0 <br>
     *  RSTDT      3 <br>
     *  STALLRQC   4 <br>
     *  STALLRQ    5 <br>
     */
    @NativeCVariable8
    public static volatile int UECONX;
    public static final int EPEN       = 0;
    public static final int RSTDT      = 3;
    public static final int STALLRQC   = 4;
    public static final int STALLRQ    = 5;

    /** UECFG0X    MEM Addr=0xEC Bits=8 <br>
     *  EPDIR      0 <br>
     *  EPTYPE0    6 <br>
     *  EPTYPE1    7 <br>
     */
    @NativeCVariable8
    public static volatile int UECFG0X;
    public static final int EPDIR      = 0;
    public static final int EPTYPE0    = 6;
    public static final int EPTYPE1    = 7;

    /** UECFG1X    MEM Addr=0xED Bits=8 <br>
     *  ALLOC      1 <br>
     *  EPBK0      2 <br>
     *  EPBK1      3 <br>
     *  EPSIZE0    4 <br>
     *  EPSIZE1    5 <br>
     *  EPSIZE2    6 <br>
     */
    @NativeCVariable8
    public static volatile int UECFG1X;
    public static final int ALLOC      = 1;
    public static final int EPBK0      = 2;
    public static final int EPBK1      = 3;
    public static final int EPSIZE0    = 4;
    public static final int EPSIZE1    = 5;
    public static final int EPSIZE2    = 6;

    /** UESTA0X    MEM Addr=0xEE Bits=8 <br>
     *  NBUSYBK0   0 <br>
     *  NBUSYBK1   1 <br>
     *  DTSEQ0     2 <br>
     *  DTSEQ1     3 <br>
     *  UNDERFI    5 <br>
     *  OVERFI     6 <br>
     *  CFGOK      7 <br>
     */
    @NativeCVariable8
    public static volatile int UESTA0X;
    public static final int NBUSYBK0   = 0;
    public static final int NBUSYBK1   = 1;
    public static final int DTSEQ0     = 2;
    public static final int DTSEQ1     = 3;
    public static final int UNDERFI    = 5;
    public static final int OVERFI     = 6;
    public static final int CFGOK      = 7;

    /** UESTA1X    MEM Addr=0xEF Bits=8 <br>
     *  CURRBK0    0 <br>
     *  CURRBK1    1 <br>
     *  CTRLDIR    2 <br>
     */
    @NativeCVariable8
    public static volatile int UESTA1X;
    public static final int CURRBK0    = 0;
    public static final int CURRBK1    = 1;
    public static final int CTRLDIR    = 2;

    /** UEIENX     MEM Addr=0xF0 Bits=8 <br>
     *  TXINE      0 <br>
     *  STALLEDE   1 <br>
     *  RXOUTE     2 <br>
     *  RXSTPE     3 <br>
     *  NAKOUTE    4 <br>
     *  NAKINE     6 <br>
     *  FLERRE     7 <br>
     */
    @NativeCVariable8
    public static volatile int UEIENX;
    public static final int TXINE      = 0;
    public static final int STALLEDE   = 1;
    public static final int RXOUTE     = 2;
    public static final int RXSTPE     = 3;
    public static final int NAKOUTE    = 4;
    public static final int NAKINE     = 6;
    public static final int FLERRE     = 7;

    /** UEDATX     MEM Addr=0xF1 Bits=8 <br>
     *  DAT0       0 <br>
     *  DAT1       1 <br>
     *  DAT2       2 <br>
     *  DAT3       3 <br>
     *  DAT4       4 <br>
     *  DAT5       5 <br>
     *  DAT6       6 <br>
     *  DAT7       7 <br>
     */
    @NativeCVariable8
    public static volatile int UEDATX;
    public static final int DAT0       = 0;
    public static final int DAT1       = 1;
    public static final int DAT2       = 2;
    public static final int DAT3       = 3;
    public static final int DAT4       = 4;
    public static final int DAT5       = 5;
    public static final int DAT6       = 6;
    public static final int DAT7       = 7;

    /** UEBCX      MEM Addr=0xF2 Bits=16 <br>
     */
    @NativeCVariable16
    public static volatile int UEBCX;

    /** UEBCLX     MEM Addr=0xF2 Bits=8 <br>
     *  BYCT0      0 <br>
     *  BYCT1      1 <br>
     *  BYCT2      2 <br>
     *  BYCT3      3 <br>
     *  BYCT4      4 <br>
     *  BYCT5      5 <br>
     *  BYCT6      6 <br>
     *  BYCT7      7 <br>
     */
    @NativeCVariable8
    public static volatile int UEBCLX;
    public static final int BYCT0      = 0;
    public static final int BYCT1      = 1;
    public static final int BYCT2      = 2;
    public static final int BYCT3      = 3;
    public static final int BYCT4      = 4;
    public static final int BYCT5      = 5;
    public static final int BYCT6      = 6;
    public static final int BYCT7      = 7;

    /** UEBCHX     MEM Addr=0xF3 Bits=8 <br>
     */
    @NativeCVariable8
    public static volatile int UEBCHX;

    /** UEINT      MEM Addr=0xF4 Bits=8 <br>
     *  EPINT0     0 <br>
     *  EPINT1     1 <br>
     *  EPINT2     2 <br>
     *  EPINT3     3 <br>
     *  EPINT4     4 <br>
     *  EPINT5     5 <br>
     *  EPINT6     6 <br>
     */
    @NativeCVariable8
    public static volatile int UEINT;
    public static final int EPINT0     = 0;
    public static final int EPINT1     = 1;
    public static final int EPINT2     = 2;
    public static final int EPINT3     = 3;
    public static final int EPINT4     = 4;
    public static final int EPINT5     = 5;
    public static final int EPINT6     = 6;

    /** UPERRX     MEM Addr=0xF5 Bits=8 <br>
     */
    @NativeCVariable8
    public static volatile int UPERRX;

    /** UPBCLX     MEM Addr=0xF6 Bits=8 <br>
     */
    @NativeCVariable8
    public static volatile int UPBCLX;

    /** UPBCHX     MEM Addr=0xF7 Bits=8 <br>
     */
    @NativeCVariable8
    public static volatile int UPBCHX;

    /** UPINT      MEM Addr=0xF8 Bits=8 <br>
     */
    @NativeCVariable8
    public static volatile int UPINT;

    /** OTGTCON    MEM Addr=0xF9 Bits=8 <br>
     */
    @NativeCVariable8
    public static volatile int OTGTCON;



    /* Interrupt Vectors */
    /* Interrupt Vector 0 is the reset vector. */

    public static final int _VECTORS_SIZE = 43;



    /* Constants */
//    public static final int SPM_PAGESIZE = 128;
    public static final int RAMSTART   = 0x100;
    public static final int RAMSIZE    = 0xA00;
//    public static final int RAMEND     = RAMSTART;
    public static final int XRAMSTART  = 0x2200;
//    public static final int XRAMSIZE   = 0x10000;
//    public static final int XRAMEND    = XRAMSIZE;
//    public static final int E2END      = 0x3FF;
//    public static final int E2PAGESIZE = 4;
//    public static final int FLASHEND   = 0x7FFF;



    /* Fuses */
//    public static final int FUSE_MEMORY_SIZE = 3;

    /* Low Fuse Byte */
//    public static final int LFUSE_DEFAULT = FUSE_CKSEL1;

    /* High Fuse Byte */
//    public static final int HFUSE_DEFAULT = FUSE_BOOTSZ0;

    /* Extended Fuse Byte */
    public static final int EFUSE_DEFAULT = 0xFF;

    /* Lock Bits */

    /* Signature */
//    public static final int SIGNATURE_0 = 0x1E;
//    public static final int SIGNATURE_1 = 0x95;
//    public static final int SIGNATURE_2 = 0x87;

}
