 /**
 * Genom's changes:
 * 2015-09-30
 *    Put data (mainly heap) at location 0xd000:   .bss 0xd000
 * 2015-10-15
 *    Introduce section .haiku (after .text) to have a chance to produce small deltas in future.
 *    Idea: .test is for HaikuVM VM-code. Where .haiku is for user byte-code.
 */

 /**
 * Jose's changes:
 * -- _end is ALIGN(2) now.
 *
 * 2001-08-09 juergen: added extra0 with angle_state for rotation sensor
 * 2002-07-05 paul: Many changes for code relocation
 */

/*
 *  rcx.lds
 *
 *  GNU ld script for RCX replacement firmware
 *
 *  Thanks to Markus Noga for observing that an ld script could could be
 *  used to customize the link.  The best documentation for writing GNU ld
 *  scripts is contained in the GNU info pages for ld.
 *
 *  This ld script does not support C++ objects.  My apologies.
 *
 *  The contents of this file are subject to the Mozilla Public License
 *  Version 1.0 (the "License"); you may not use this file except in
 *  compliance with the License. You may obtain a copy of the License at
 *  http://www.mozilla.org/MPL/
 *
 *  Software distributed under the License is distributed on an "AS IS"
 *  basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 *  License for the specific language governing rights and limitations
 *  under the License.
 *
 *  The Original Code is Librcx code, released February 9, 1999.
 *
 *  The Initial Developer of the Original Code is Kekoa Proudfoot.
 *  Portions created by Kekoa Proudfoot are Copyright (C) 1999
 *  Kekoa Proudfoot. All Rights Reserved.
 *
 *  Contributor(s): Kekoa Proudfoot <kekoa@graphics.stanford.edu>
 */

OUTPUT_FORMAT("coff-h8300")
OUTPUT_ARCH("h8300")
ENTRY("__start");

/*
 Layout of each row is important as it is parsed by a none-to-smart
 program
*/
MEMORY
{
    rom (r)     : o = 0x0000 , l = 0x8000
    mem         : o = 0x8000 , l = 0x6f32
    romdata1    : o = 0xef32 , l = 0x00cf
    motorIO     : o = 0xf000 , l = 0x0004     /* Memory mapped motor I/O */
    extra1      : o = 0xf004 , l = 0x0b7c     /* Aligned to a long word, just in case */
    hwreserved1 : o = 0xfb80 , l = 0x0200     /* reserved by hardware */
    romdata2    : o = 0xfd80 , l = 0x0010     /* used in init_timer */
    intvectors  : o = 0xfd90 , l = 0x0030     /* interrupt vectors */
    stack       : o = 0xfdc0 , l = 0x0220     /* grows down below 0xff80 */
    extra2      : o = 0xff80 , l = 0x0008
    hwreserved2 : o = 0xff88 , l = 0x0008     /* reserved by hardware */
    hwregisters : o = 0xff90 , l = 0x0070     /* hardware registers */
}

SECTIONS
{
    /* The rom */
    .rom 0x0000 : {
        _rom_ocia_handler = 0x36ba ;
    } > rom
    
    /* Extra text segment. Has to be before the wildcard .mem section */
    .extra1 0xf004 : {
        __extra_start = . ;
        __extra_end = . ;
    }
    /*
       Note. When GNU LD computes the size of a section it uses the pre-relax
       size of each piece of code, not the actual relaxed size. As a consequence
       we don't define the size of this section as we know it will be correct in
       the generated version (we perform our own packing).
    */

	/* Start of standard firmware memory */
    .mem 0x8000 : {
        __text_begin = . ;
        *(.init)
        *(.text)
        *(.haiku)
        *(.rodata)
        *(.vectors)
        __text_end = . ;
    } > mem
    
    /*
       Need uninitialised data before initialised data so that the memory
       relocator can work properly (i.e. there have to be records up to
       __mem_start in the srec).
    */
    .bss 0xd000 : {
        __bss_start = . ;
        *(.bss)
        *(COMMON)
        __end = . ;	/* Used by librcx.a(init.o) */
    } > mem
    
    .data : {
        __data_begin = . ;
        *(.data.*)
        __data_end = . ;
        __mem_start = ALIGN(2);
    } > mem

    /* Where the ROM keeps its variables */
    .romdata1 : {
        __romdata1 = . ;
        _angle_state = 0x00 ; /* 0xef32 */
        _serial_output_buffer = 0x20; /* 0xef52[64] */
        _transmitting = 0x61 ; /* 0xef93 */
    } > romdata1

    .romdata2 : {
        _rom_port4_ddr = 0x03 ; 
        _rom_port5_ddr = 0x04 ; 
    } > romdata2 

    /* Interrupt vectors */
    .intvectors : {
       _ocia_vector  = 0x12 ; /* 0xfda2 */
       _eri_vector   = 0x24 ; 
       _rxi_vector   = 0x26 ; 
       _txi_vector   = 0x28 ; 
       _tei_vector   = 0x2a ; 
    } > intvectors

    /* Hardware registers */
    .hwregisters : {
       _T_IER        = 0x00 ; /* 0xff90 */
       _PORT4_DDR    = 0x25 ;
       _PORT4        = 0x27 ;
       _PORT5_DDR    = 0x28 ;
       _T1_CR        = 0x40 ;
       _T1_CSR       = 0x41 ;
       _T1_CORA      = 0x42 ;
       _S_MR         = 0x48 ;
       _S_BRR        = 0x49 ;
       _S_CR         = 0x4a ;
       _S_TDR        = 0x4b ;
       _S_SR         = 0x4c ;
       _S_RDR        = 0x4d ;
    } > hwregisters
}

