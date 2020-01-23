/*
    Part of the Raspberry-Pi Bare Metal Tutorials
    https://www.valvers.com/rpi/bare-metal/
    Copyright (c) 2013-2018, Brian Sidebotham

    This software is licensed under the MIT License.
    Please see the LICENSE file included with this software.

*/

#if defined ( RPI0 ) || defined ( RPI1 ) || defined ( RPI2 ) || defined ( RPI3 ) || defined ( RPI4 )

#include <stdint.h>
#include "rpi-gpio.h"

static rpi_gpio_t* rpiGpio = (rpi_gpio_t*)RPI_GPIO_BASE;

size_t o_GPFSEL0 = offsetof(rpi_gpio_t, GPFSEL0);
size_t o_GPFSEL1 = offsetof(rpi_gpio_t, GPFSEL1);
size_t o_GPFSEL2 = offsetof(rpi_gpio_t, GPFSEL2);
size_t o_GPFSEL3 = offsetof(rpi_gpio_t, GPFSEL3);
size_t o_GPFSEL4 = offsetof(rpi_gpio_t, GPFSEL4);
size_t o_GPFSEL5 = offsetof(rpi_gpio_t, GPFSEL5);
size_t o_GPSET0 = offsetof(rpi_gpio_t, GPSET0);
size_t o_GPSET1 = offsetof(rpi_gpio_t, GPSET1);
size_t o_GPCLR0 = offsetof(rpi_gpio_t, GPCLR0);
size_t o_GPCLR1 = offsetof(rpi_gpio_t, GPCLR1);
size_t o_GPLEV0 = offsetof(rpi_gpio_t, GPLEV0);
size_t o_GPLEV1 = offsetof(rpi_gpio_t, GPLEV1);
size_t o_GPEDS0 = offsetof(rpi_gpio_t, GPEDS0);
size_t o_GPEDS1 = offsetof(rpi_gpio_t, GPEDS1);
size_t o_GPREN0 = offsetof(rpi_gpio_t, GPREN0);
size_t o_GPREN1 = offsetof(rpi_gpio_t, GPREN1);
size_t o_GPFEN0 = offsetof(rpi_gpio_t, GPFEN0);
size_t o_GPFEN1 = offsetof(rpi_gpio_t, GPFEN1);
size_t o_GPHEN0 = offsetof(rpi_gpio_t, GPHEN0);
size_t o_GPHEN1 = offsetof(rpi_gpio_t, GPHEN1);
size_t o_GPLEN0 = offsetof(rpi_gpio_t, GPLEN0);
size_t o_GPLEN1 = offsetof(rpi_gpio_t, GPLEN1);
size_t o_GPAREN0 = offsetof(rpi_gpio_t, GPAREN0);
size_t o_GPAREN1 = offsetof(rpi_gpio_t, GPAREN1);
size_t o_GPAFEN0 = offsetof(rpi_gpio_t, GPAFEN0);
size_t o_GPAFEN1 = offsetof(rpi_gpio_t, GPAFEN1);
size_t o_GPPUD = offsetof(rpi_gpio_t, GPPUD);
size_t o_GPPUDCLK0 = offsetof(rpi_gpio_t, GPPUDCLK0);
size_t o_GPPUDCLK1 = offsetof(rpi_gpio_t, GPPUDCLK1);
size_t o_LED_GPFSEL = offsetof(rpi_gpio_t, LED_GPFSEL);

rpi_gpio_t* RPI_GetGpio(void)
{
    return rpiGpio;
}

void RPI_GpioInit(void)
{

}

void RPI_SetGpioPinFunction( rpi_gpio_pin_t gpio, rpi_gpio_alt_function_t func )
{
    rpi_reg_rw_t* fsel_reg = &((rpi_reg_rw_t*)rpiGpio)[ gpio / 10 ];
    rpi_reg_rw_t fsel_copy = *fsel_reg;
    fsel_copy &= ~( FS_MASK << ( ( gpio % 10 ) * 3 ) );
    fsel_copy |= (func << ( ( gpio % 10 ) * 3 ) );
    *fsel_reg = fsel_copy;
}


void RPI_SetGpioOutput( rpi_gpio_pin_t gpio )
{
    RPI_SetGpioPinFunction( gpio, FS_OUTPUT );
}


void RPI_SetGpioInput( rpi_gpio_pin_t gpio )
{
    RPI_SetGpioPinFunction( gpio, FS_INPUT );
}


rpi_gpio_value_t RPI_GetGpioValue( rpi_gpio_pin_t gpio )
{
    rpi_gpio_value_t result = RPI_IO_UNKNOWN;

    switch( gpio / 32 )
    {
        case 0:
            result = rpiGpio->GPLEV0 & ( 1 << gpio );
            break;

        case 1:
            result = rpiGpio->GPLEV1 & ( 1 << ( gpio - 32 ) );
            break;

        default:
            break;
    }

    if( result != RPI_IO_UNKNOWN )
    {
        if( result )
            result = RPI_IO_HI;
    }

    return result;
}


void RPI_ToggleGpio( rpi_gpio_pin_t gpio )
{
    if( RPI_GetGpioValue( gpio ) )
        RPI_SetGpioLo( gpio );
    else
        RPI_SetGpioHi( gpio );
}


void RPI_SetGpioHi( rpi_gpio_pin_t gpio )
{
    switch( gpio / 32 )
    {
        case 0:
            rpiGpio->GPSET0 = ( 1 << gpio );
            break;

        case 1:
            rpiGpio->GPSET1 = ( 1 << ( gpio - 32 ) );
            break;

        default:
            break;
    }
}


void RPI_SetGpioLo( rpi_gpio_pin_t gpio )
{
    switch( gpio / 32 )
    {
        case 0:
            rpiGpio->GPCLR0 = ( 1 << gpio );
            break;

        case 1:
            rpiGpio->GPCLR1 = ( 1 << ( gpio - 32 ) );
            break;

        default:
            break;
    }
}


void RPI_SetGpioValue( rpi_gpio_pin_t gpio, rpi_gpio_value_t value )
{
    if( ( value == RPI_IO_LO ) || ( value == RPI_IO_OFF ) )
        RPI_SetGpioLo( gpio );
    else if( ( value == RPI_IO_HI ) || ( value == RPI_IO_ON ) )
        RPI_SetGpioHi( gpio );
}

#endif