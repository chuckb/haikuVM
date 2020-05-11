/*
*  Created on: 01.06.2020
*  Author: Chuck Benedict - adapted from Raspberry Pi Bare Metal Tutorials
*  Copyright (c) 2020, Chuck Benedict
*/
//  Part of the Raspberry-Pi Bare Metal Tutorials
//  Copyright (c) 2013-2015, Brian Sidebotham
//  All rights reserved.
//
//  Redistribution and use in source and binary forms, with or without
//  modification, are permitted provided that the following conditions are met:
//
//  1. Redistributions of source code must retain the above copyright notice,
//      this list of conditions and the following disclaimer.
//
//  2. Redistributions in binary form must reproduce the above copyright notice,
//      this list of conditions and the following disclaimer in the
//      documentation and/or other materials provided with the distribution.
//
//  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
//  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
//  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
//  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
//  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
//  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
//  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
//  INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
//  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
//  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
//  POSSIBILITY OF SUCH DAMAGE.

#if defined ( RPI0 ) || defined ( RPI1 ) || defined ( RPI2 ) || defined ( RPI3 ) || defined ( RPI4 )

#include <stdarg.h>
#include "haikuJ2C.h"
#include "pi/rpi-systimer.h"
#include "pi/rpi-shared.h"
#include "pi/printf.h"

// This is the assembler startup for BCM2835
__asm__(
  ".pushsection \".text.startup\"\n\n"
  ".global _start\n"
  ".global _get_stack_pointer\n"
  ".global _exception_table\n"
  ".global _enable_interrupts\n\n"
  ".global _disable_interrupts\n\n"
  ".equ    CPSR_MODE_USER,         0x10\n"
  ".equ    CPSR_MODE_FIQ,          0x11\n"
  ".equ    CPSR_MODE_IRQ,          0x12\n"
  ".equ    CPSR_MODE_SVR,          0x13\n"
  ".equ    CPSR_MODE_ABORT,        0x17\n"
  ".equ    CPSR_MODE_UNDEFINED,    0x1B\n"
  ".equ    CPSR_MODE_SYSTEM,       0x1F\n\n"
  ".equ    CPSR_IRQ_INHIBIT,       0x80\n"
  ".equ    CPSR_FIQ_INHIBIT,       0x40\n"
  ".equ    CPSR_THUMB,             0x20\n\n"
  ".equ	SCTLR_ENABLE_DATA_CACHE,        0x4\n"
  ".equ	SCTLR_ENABLE_BRANCH_PREDICTION, 0x800\n"
  ".equ	SCTLR_ENABLE_INSTRUCTION_CACHE, 0x1000\n\n"
  "_start:\n\t"
    "ldr pc, _reset_h\n\t"
    "ldr pc, _undefined_instruction_vector_h\n\t"
    "ldr pc, _software_interrupt_vector_h\n\t"
    "ldr pc, _prefetch_abort_vector_h\n\t"
    "ldr pc, _data_abort_vector_h\n\t"
    "ldr pc, _unused_handler_h\n\t"
    "ldr pc, _interrupt_vector_h\n\t"
    "ldr pc, _fast_interrupt_vector_h\n\n"
  "_reset_h:                           .word   _reset_\n"
  "_undefined_instruction_vector_h:    .word   undefined_instruction_vector\n"
  "_software_interrupt_vector_h:       .word   software_interrupt_vector\n"
  "_prefetch_abort_vector_h:           .word   prefetch_abort_vector\n"
  "_data_abort_vector_h:               .word   data_abort_vector\n"
  "_unused_handler_h:                  .word   _reset_\n"
  "_interrupt_vector_h:                .word   interrupt_vector\n"
  "_fast_interrupt_vector_h:           .word   fast_interrupt_vector\n\n"
  "_reset_:\n\t"
    "mov     r0, #0x8000\n\t"
    "mov     r1, #0x0000\n\t"
    "ldmia   r0!,{r2, r3, r4, r5, r6, r7, r8, r9}\n\t"
    "stmia   r1!,{r2, r3, r4, r5, r6, r7, r8, r9}\n\t"
    "ldmia   r0!,{r2, r3, r4, r5, r6, r7, r8, r9}\n\t"
    "stmia   r1!,{r2, r3, r4, r5, r6, r7, r8, r9}\n\t"
    "mov r0, #(CPSR_MODE_IRQ | CPSR_IRQ_INHIBIT | CPSR_FIQ_INHIBIT )\n\t"
    "msr cpsr_c, r0\n\t"
    "mov sp, #0x7000\n\t"
    "mov r0, #(CPSR_MODE_SVR | CPSR_IRQ_INHIBIT | CPSR_FIQ_INHIBIT )\n\t"
    "msr cpsr_c, r0\n\t"
    "mov sp, #0x8000\n\t"
    "mrc p15,0,r0,c1,c0,0\n\t"
    "orr r0,#SCTLR_ENABLE_BRANCH_PREDICTION\n\t"
    "orr r0,#SCTLR_ENABLE_DATA_CACHE\n\t"
    "orr r0,#SCTLR_ENABLE_INSTRUCTION_CACHE\n\t"
    "mcr p15,0,r0,c1,c0,0\n\t"
    "MRC p15, #0, r1, c1, c0, #2\n\t"
    "ORR r1, r1, #(0xf << 20)\n\t"
    "MCR p15, #0, r1, c1, c0, #2\n\t"
    "MOV r1, #0\n\t"
    "MCR p15, #0, r1, c7, c5, #4\n\t"
    "MOV r0,#0x40000000\n\t"
    "FMXR FPEXC, r0\n\t"
    "bl      _cstartup\n\n"
  "_inf_loop:\n\t"
    "b       _inf_loop\n\n"
  "_get_stack_pointer:\n\t"
    "str     sp, [sp]\n\t"
    "ldr     r0, [sp]\n\t"
    "mov     pc, lr\n\n"
  "_enable_interrupts:\n\t"
    "mrs     r0, cpsr\n\t"
    "bic     r0, r0, #0x80\n\t"
    "msr     cpsr_c, r0\n\t"
    "mov     pc, lr\n\n"
  "_disable_interrupts:\n\t"
    "mrs     r0, cpsr\n\t"
    "orr     r0, r0, #0x80\n\t"
    "msr     cpsr_c, r0\n\t"
    "mov     pc, lr\n"
  ".popsection\n"
);

extern int __bss_start__;
extern int __bss_end__;

extern int main();

int console_enabled = 0;

#if (_DEBUG || DEBUG || TRACEING)
int jprintf(const char * format, ...) {
	char buf[280]; // nicht globaler buf !!
	int err;
	va_list list;
	va_start(list, format);
	vsprintf(buf, format, list);
	err=printf(buf);
	va_end(list);
	return err;
}
#endif

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

// Tell GCC to never optimize this function away
#pragma GCC push_options
#pragma GCC optimize ("O0")
unsigned long millis()
{
  uint32_t c_hi;
  uint32_t c_lo;
  uint64_t final;

  // Avoid rollover
  do {
    c_hi = RPI_GetSystemTimer()->counter_hi;
    c_lo = RPI_GetSystemTimer()->counter_lo;
  } while (c_hi != RPI_GetSystemTimer()->counter_hi);

  // Assemble final value
  final = (uint64_t)c_hi << 32 | c_lo;

  // ARM system timer yields microseconds...convert to milliseconds
  return final * 0.001;
}
#pragma GCC pop_options

// Implement interrupts
void sei()
{
  _enable_interrupts();
}

void cli()
{
  _disable_interrupts();
}
#endif