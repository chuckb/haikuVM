/*
*  Created on: 08.10.2015
*      Author: genom2
* But borrowed code from leJOS.
*/

#ifdef RCX

#include <stdio.h>
#include <stdarg.h>
#include <string.h>
#include "haikuJ2C.h"
#include "simpleOS.h"
#include <setjmp.h>


extern char __rcall0(short a);
extern char __rcall1(short a, short p0);
extern char __rcall2(short a, short p0, short p1);
extern char __rcall3(short a, short p0, short p1, short p2);
extern char __rcall4(short a, short p0, short p1, short p2, short p3);

__asm__(
	".section .init\n\t"
	".global __start\n"
	"__start:\n\t"
	"jsr __init\n\t"
	".string \"Do you byte, when I knock?\""
	);

#if DEBUG_
void setNumber(int aCode, int aValue, int aPoint)
{
	__rcall3(0x1ff2, aCode, aValue, aPoint);
}

/**
* Refreshes LCD. Has to be called for certain LCD methods
* to take effect.
*/
void refresh()
{
	__rcall0(0x27c8);
}

void msleep(unsigned int msec) {
	unsigned long int i;
	for (i = 0; i < msec * 10000; i++) {
	}
}

void sleep(unsigned int sec) {
	unsigned int i;
	for (i = 0; i < sec; i++) {
		msleep(200);
	}
}

void LCD_showNumber(int aValue) {
	setNumber(0x301f, aValue % 10000, 0x3002);
	refresh();
	sleep(10);
}

void LCD_showInt(int deb, jint aValue) {
	LCD_showNumber(deb + 3);	LCD_showNumber((aValue >> 24) & 0xff);
	LCD_showNumber(deb + 2);	LCD_showNumber((aValue >> 16) & 0xff);
	LCD_showNumber(deb + 1);	LCD_showNumber((aValue >> 8) & 0xff);
	LCD_showNumber(deb);		LCD_showNumber(aValue & 0xff);
}
#endif

extern int main(void);

extern char _bss_start, _end;

static jmp_buf env;

void _init(void) {
	/*
	// patch the code
	int i = 0;
	int * addrPtr = (int*)&_end;
	LCD_showNumber(i);
	addrPtr--;
	while (*addrPtr) {
		int * addr = (int*) *addrPtr--;
		int value= *addrPtr--;
		LCD_showNumber(++i);
		*addr = value;
	}
	*/
	/* Clear the .bss data */
	memset(&_bss_start, 0, &_end - &_bss_start);

	if (setjmp(env) == 0)
	{
		/* Call main */
		main();
	}
	__asm__(
		"pop     r6\n"
		"pop     r6\n"
		"pop     r6\n"
		"pop     r6\n"
		"pop     r6\n"
		"pop     r6\n"
		"pop     r6\n"
		"pop     r6\n"
		"pop     r6\n"
		);
		__rcall0(0x0580);
}

void exit(int error) {
	longjmp(env, error);
}

jshort stacktop() {
	int marker=123;
	return (jshort)(&marker);
}

#if 0
/*
from lib
00009aa6 <__rcall0>:
9aa6 : 6d f6             mov.w	r6, @-e7
9aa8:	5d 00             jsr	@r0
9aaa:	0d 60             mov.w	r6, r0
9aac : 6d 76             mov.w	@r7+, r6
9aae:	54 70             rts

00009ab0 <__rcall1>:
9ab0 : 6d f6             mov.w	r6, @-e7
9ab2:	0d 16             mov.w	r1, r6
9ab4 : 5d 00             jsr	@r0
9ab6:	0d 60             mov.w	r6, r0
9ab8 : 6d 76             mov.w	@r7+, r6
9aba:	54 70             rts


from C code here
00009500 <__rcall0>:
9500:	5d 00             jsr	@r0
9502:	f0 00             mov.b	#0x0,r0h
9504:	54 70             rts

00009506 <__rcall1>:
9506:	0d 03             mov.w	r0,r3
9508:	0d 10             mov.w	r1,r0
950a:	5d 30             jsr	@r3
950c:	f0 00             mov.b	#0x0,r0h
950e:	54 70             rts

*/


char __rcall0(short a) {
	return ( (char(*)()) a )();
}

char __rcall1(short a, short p0) {
	return ((char(*)(short)) a)(p0);
}

char __rcall2(short a, short p0, short p1) {
	return ((char(*)(short, short)) a)(p0, p1);
}

char __rcall3(short a, short p0, short p1, short p2) {
	return ((char(*)(short, short, short)) a)(p0, p1, p2);
}

char __rcall4(short a, short p0, short p1, short p2, short p3) {
	return ((char(*)(short, short, short, short)) a)(p0, p1, p2, p3);
}
#endif

#if 0
__asm__(
	".section .text\n"
	".global ___rcall0\n"
	"___rcall0 :\n"
	"push    r6\n"
	"jsr     @r0\n"
	"mov.w   r6, r0\n"
	"pop     r6\n"
	"rts\n"

	".global ___rcall1\n"
	"___rcall1 :\n"
	"push    r6\n"
	"mov.w   r1, r6\n"
	"jsr     @r0\n"
	"mov.w   r6, r0\n"
	"pop     r6\n"
	"rts\n"

	".global ___rcall2\n"
	"___rcall2 :\n"
	"push    r6\n"
	"push    r2\n"
	"mov.w   r1, r6\n"
	"jsr     @r0\n"
	"adds    #2, r7\n"
	"mov.w   r6, r0\n"
	"pop     r6\n"
	"rts\n"

	".global ___rcall3\n"
	"___rcall3 :\n"
	"push    r6\n"
	"mov.w   @(4, r7), r3\n"
	"push    r3\n"
	"push    r2\n"
	"mov.w   r1, r6\n"
	"jsr     @r0\n"
	"adds    #2, r7\n"
	"adds    #2, r7\n"
	"mov.w   r6, r0\n"
	"pop     r6\n"
	"rts\n"

	".global ___rcall4\n"
	"___rcall4 :\n"
	"push    r6\n"
	"mov.w   @(6, r7), r3\n"
	"push    r3\n"
	"mov.w   @(6, r7), r3\n"
	"push    r3\n"
	"push    r2\n"
	"mov.w   r1, r6\n"
	"jsr     @r0\n"
	"add.b   #6, r7l\n"
	"addx.b  #0, r7h\n"
	"mov.w   r6, r0\n"
	"pop     r6\n"
	"rts\n"
);

#endif

volatile unsigned char gMakeRequest;

/*********
* Systime functionality by Ryan VanderBijl
* Borrowed code from legOS. Orignally by
*   Markus L. Noga. Perhaps some from David Van Wagner.
*/


// variables:
#define TIER_ENABLE_OCA         0x08

extern          unsigned char T_IER;
extern                   void *    ocia_vector;
extern                   void *rom_ocia_handler;

// functions
extern void systime_handler(void);

/**
* systime_init() should be called after
* every call to init_timer().
*/
void systime_init(void) {
	T_IER &= ~TIER_ENABLE_OCA;
	ocia_vector = &systime_handler;
	T_IER |= TIER_ENABLE_OCA;
}

/**
* sys_time should be initialized every
* time a new program is started.
*/
volatile unsigned long sys_time;

/***
* the systime_handler doesn't do too much. After all, its gotta
* run every millisecond. What we do can perhaps be best described
* as "borrowing" the OCIA interrupt. Because, the first thing we
* do is call the default ROM handler. This causes the rom to do
* a bunch of stuff for us (IR communication, sensor, motor, sound, to
* name a few.) After its done, we increment our time counter, and
* then return.
*/
__asm__(
	".text\n"
	".align 1\n"
	".global _systime_handler\n"
	"_systime_handler:\n"
	"                ; r6 saved by ROM\n"
	"\n"
	"                ; call the ROM OCIA handler\n"
	"                jsr     _rom_ocia_handler \n"
	"\n"
	"                ; increment system timer\n"
	"                mov.w @_sys_time+2,r6          ; LSW -> r6\n"
	"                add.b #0x1,r6l                 ; 16 bit: add 1\n"
	"                addx  #0x0,r6h\n"
	"                mov.w r6,@_sys_time+2\n"
	"                bcc sys_nohigh                 ; speedup for 65535 cases\n"
	"                  mov.w @_sys_time,r6          ; MSW -> r6\n"
	"                  add.b #0x1,r6l\n"
	"                  addx  #0x0,r6h\n"
	"                  mov.w r6,@_sys_time\n"
	"              sys_nohigh:\n"
	"\n"
	"                ; set tick request\n"
	"                mov.b #0x1,r6l\n"
	"                mov.b r6l,@_gMakeRequest\n"
	"\n"
	"                ;bclr    #3,@0x91:8        ; reset compare A IRQ flag\n"
	"                rts\n"
	);


unsigned long millis() {
	return sys_time;
}


/**
* Machine-generated file. Do not modify.
*/

#ifndef _SPECIALSIGNATURES_H
#define _SPECIALSIGNATURES_H
#define main_4_1Ljava_3lang_3String_2_5V 0
#define run_4_5V 1
#define _6init_7_4_5V 2
#define _6clinit_7_4_5V 3
#define notify_4_5V 4
#define notifyAll_4_5V 5
#define wait_4_5V 6
#define wait_4J_5V 7
#define start_4_5V 8
#define yield_4_5V 9
#define sleep_4J_5V 10
#define currentThread_4_5Ljava_3lang_3Thread_2 11
#define getPriority_4_5I 12
#define setPriority_4I_5V 13
#define interrupt_4_5V 14
#define interrupted_4_5Z 15
#define isInterrupted_4_5Z 16
#define setDaemon_4Z_5V 17
#define isDaemon_4_5Z 18
#define join_4_5V 19
#define join_4J_5V 20
#define currentTimeMillis_4_5J 21
#define exit_4I_5V 22
#define freeMemory_4_5J 23
#define totalMemory_4_5J 24
#define getMessage_4_5Ljava_3lang_3String_2 25
#define call_4S_5V 26
#define call_4SS_5V 27
#define call_4SSS_5V 28
#define call_4SSSS_5V 29
#define call_4SSSSS_5V 30
#define readByte_4I_5B 31
#define writeByte_4IB_5V 32
#define setBit_4III_5V 33
#define getDataAddress_4Ljava_3lang_3Object_2_5I 34
#define resetSerial_4_5V 35
#define readSensorValue_4II_5I 36
#define setSensorValue_4III_5V 37
#define setPoller_4_5V 38
#define setThrottle_4I_5V 39
#define test_4Ljava_3lang_3String_2Z_5V 40
#define testEQ_4Ljava_3lang_3String_2II_5V 41
#define floatToIntBits_4F_5I 42
#define intBitsToFloat_4I_5F 43
#define init_4_5V 44
#define write_4_1BI_5V 45
#define read_4_5I 46
#define isSending_4_5Z 47
#define isSendError_4_5Z 48
#define getRegionAddress_4_5I 49
#endif // _SPECIALSIGNATURES_H

/*
* Proprietary HaikuVM stack to JNI Adapter function.
* DO NOT EDIT THIS FUNCTION - it is machine generated.
*
* Class:     josx.platform.rcx.ROM
* Method:    call
* Signature: (S)V
*/
void native_josx_platform_rcx_ROM_call_SV(void) {
	pushTop();    // Save variable top onto stack.
	{
		jshort    arg1 = (jshort)pop()->i;
		__rcall0(arg1);
	}
	popTop();
}

/*
* Proprietary HaikuVM stack to JNI Adapter function.
* DO NOT EDIT THIS FUNCTION - it is machine generated.
*
* Class:     josx.platform.rcx.ROM
* Method:    call
* Signature: (SS)V
*/
void native_josx_platform_rcx_ROM_call_SSV(void) {
	pushTop();    // Save variable top onto stack.
	{
		jshort    arg2 = (jshort)pop()->i;
		jshort    arg1 = (jshort)pop()->i;
		__rcall1(arg1, arg2);
	}
	popTop();
}

/*
* Proprietary HaikuVM stack to JNI Adapter function.
* DO NOT EDIT THIS FUNCTION - it is machine generated.
*
* Class:     josx.platform.rcx.ROM
* Method:    call
* Signature: (SSS)V
*/
void native_josx_platform_rcx_ROM_call_SSSV(void) {
	pushTop();    // Save variable top onto stack.
	{
		jshort    arg3 = (jshort)pop()->i;
		jshort    arg2 = (jshort)pop()->i;
		jshort    arg1 = (jshort)pop()->i;
		__rcall2(arg1, arg2, arg3);
	}
	popTop();
}

/*
* Proprietary HaikuVM stack to JNI Adapter function.
* DO NOT EDIT THIS FUNCTION - it is machine generated.
*
* Class:     josx.platform.rcx.ROM
* Method:    call
* Signature: (SSSS)V
*/
void native_josx_platform_rcx_ROM_call_SSSSV(void) {
	pushTop();    // Save variable top onto stack.
	{
		jshort    arg4 = (jshort)pop()->i;
		jshort    arg3 = (jshort)pop()->i;
		jshort    arg2 = (jshort)pop()->i;
		jshort    arg1 = (jshort)pop()->i;
		__rcall3(arg1, arg2, arg3, arg4);
	}
	popTop();
}

/*
* Proprietary HaikuVM stack to JNI Adapter function.
* DO NOT EDIT THIS FUNCTION - it is machine generated.
*
* Class:     josx.platform.rcx.ROM
* Method:    call
* Signature: (SSSSS)V
*/
void native_josx_platform_rcx_ROM_call_SSSSSV(void) {
	pushTop();    // Save variable top onto stack.
	{
		jshort    arg5 = (jshort)pop()->i;
		jshort    arg4 = (jshort)pop()->i;
		jshort    arg3 = (jshort)pop()->i;
		jshort    arg2 = (jshort)pop()->i;
		jshort    arg1 = (jshort)pop()->i;
		__rcall4(arg1, arg2, arg3, arg4, arg5);
	}
	popTop();
}

/*
* Proprietary HaikuVM stack to JNI Adapter function.
* DO NOT EDIT THIS FUNCTION - it is machine generated.
*
* Class:     josx.platform.rcx.Memory
* Method:    getDataAddress
* Signature: (Ljava/lang/Object;)I
*/
void native_josx_platform_rcx_Memory_getDataAddress_Ljava_lang_Object_I(void) {
	pushTop();    // Save variable top onto stack.
	top.s1.i = (jint)pop()->a;
	// Variable top holds the return value.
}

/*
* Proprietary HaikuVM stack to JNI Adapter function.
* DO NOT EDIT THIS FUNCTION - it is machine generated.
*
* Class:     josx.platform.rcx.Memory
* Method:    setBit
* Signature: (III)V
*/
void native_josx_platform_rcx_Memory_setBit_IIIV(void) {
	pushTop();    // Save variable top onto stack.
	{
		jint  value = (jint)pop()->i;
		jint    bit = (jint)pop()->i;
		char* aAddr = (char*)pop()->i;
		*aAddr = (*aAddr & (~(1 << bit))) | (((value != 0) ? 1 : 0) << bit);
	}
	popTop();
}


/*
* Proprietary HaikuVM stack to JNI Adapter function.
* DO NOT EDIT THIS FUNCTION - it is machine generated.
*
* Class:     josx.platform.rcx.Memory
* Method:    readByte
* Signature: (I)B
*/
void native_josx_platform_rcx_Memory_readByte_IB(void) {
	pushTop();    // Save variable top onto stack.
	{
		top.s1.i = *(char*)(pop()->i);
	}
	// Variable top holds the return value.
}

#endif

