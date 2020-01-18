/*
    Part of the Raspberry-Pi Bare Metal Tutorials
    https://www.valvers.com/rpi/bare-metal/
    Copyright (c) 2013-2018, Brian Sidebotham
    This software is licensed under the MIT License.
    Please see the LICENSE file included with this software.
*/

#if defined( RPI )

#include <stdint.h>

// This is the assembler startup
// Set the stack pointer to beginning to program...it grows downward
// Set up VFP
// Jump to c startup function
__asm__(
  ".section \".text.startup\"\n\n"
  ".global _start\n\n"
  "_start:\n\t"
    "ldr     sp, =0x8000\n\t"
    "MRC     p15, #0, r1, c1, c0, #2\n\t"
    "ORR     r1, r1, #(0xf << 20)\n\t"
    "MCR     p15, #0, r1, c1, c0, #2\n\t"
    "MOV     r1, #0\n\t"
    "MCR     p15, #0, r1, c7, c5, #4\n\t"
    "MOV     r0,#0x40000000\n\t"
    "FMXR    FPEXC, r0\n\t"
    "b       _cstartup\n\n"
  "_inf_loop:\n\t"
    "b       _inf_loop"
);

extern int __bss_start__;
extern int __bss_end__;

extern int main();

#define RPI_SYSTIMER_BASE       0x20003000
typedef struct {
    volatile uint32_t control_status;
    volatile uint32_t counter_lo;
    volatile uint32_t counter_hi;
    volatile uint32_t compare0;
    volatile uint32_t compare1;
    volatile uint32_t compare2;
    volatile uint32_t compare3;
    } rpi_sys_timer_t;
static rpi_sys_timer_t* rpiSystemTimer = (rpi_sys_timer_t*)RPI_SYSTIMER_BASE;

void kernel_main( unsigned int r0, unsigned int r1, unsigned int atags ) {
  main();
}

void _cstartup( unsigned int r0, unsigned int r1, unsigned int r2 )
{
    /*__bss_start__ and __bss_end__ are defined in the linker script */
    int* bss = &__bss_start__;
    int* bss_end = &__bss_end__;

    /*
        Clear the BSS section
        See http://en.wikipedia.org/wiki/.bss for further information on the
            BSS section
        See https://sourceware.org/newlib/libc.html#Stubs for further
            information on the c-library stubs
    */
    while( bss < bss_end )
        *bss++ = 0;

    /* We should never return from main ... */
    kernel_main( r0, r1, r2 );

    /* ... but if we do, safely trap here */
    while(1)
    {
        /* EMPTY! */
    }
}

// TODO: Fix this
unsigned long millis()
{
  return rpiSystemTimer->counter_lo * 0.001;
}

// TODO: Implement interrupts
void sei()
{
//  _enable_interrupts();
}

#endif