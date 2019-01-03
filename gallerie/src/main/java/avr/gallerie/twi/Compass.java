package avr.gallerie.twi;

import static haiku.avr.ATmega328p.*;
import static haiku.avr.lib.arduino.WProgram.*;
import static haiku.avr.lib.arduino.Print.*;
import static java.lang.Math.*;

/**
 * http://www.pololu.com/catalog/product/1273
 * 
 * arduino-1.0.2 -> Binäre Sketchgröße: 9.446 Bytes
 * HaikuVM -> 15104 (with code reduced to the min: 11684)
 * 
 *
 */
public class Compass {
    /*
     * compass: for the Orangutan LV, SV, SVP, and X2.
     *
     * This example program demonstrates how to use the LSM303DLM 3D compass and
     * accelerometer carrier with an Orangutan robot controller.
     *
     * The LSM303DLM carrier should be connected to the Orangutan's I2C pins; on the
     * LV and SV, these are PC5 and PC4 for SCL and SDA, respectively, and on the
     * SVP and X2, these are PC0 and PC1, respectively. (PC0 and PC1 are LCD data
     * lines on the X2, but this code will work on it if the LCD is not used, or
     * used in 4-bit mode.) 
     *
     * http://www.pololu.com
     * http://forum.pololu.com

    #include <avr/io.h>  
    #include <pololu/orangutan.h>  
    #include <math.h>
    #include <stdlib.h>
    #include <string.h>
    #include "vector.h"

     * This program assumes that the LSM303DLM carrier is oriented with X pointing
     * to the right, Y pointing backward, and Z pointing down (toward the ground).
     * The code compensates for tilts of up to 90 degrees away from horizontal.
     * Vector p should be defined as pointing forward, parallel to the ground,
     * with coordinates {X, Y, Z}.
     */
    public static class vector {
        public vector(double a, double b, double c) {
            x = a;
            y = b;
            z = c;
        }

        public vector() {

        }

        public void show(String msg) {
            Serial.print(msg+" (");
            Serial.print(x); Serial.print(", ");
            Serial.print(y); Serial.print(", ");
            Serial.print(z); Serial.println(")");
        }

        public double x, y, z;
    }
    
    /**
     *     Newton's method 
     */
    static double xsqrt(double x) {
        if (!(x > 0))
            return (x == 0) ? x : Double.NaN;
        double x0=0.1*x;
        double x1=x0-(x0*x0-x)/(2*x0);
        while(abs(x1/x0-1)>0.001) {
            x0=x1;
            x1=x0-(x0*x0-x)/(2*x0);
        }
        return x1;
    }
    
    static void vector_cross(final vector a, final vector b, vector out)
    {
        out.x = a.y * b.z - a.z * b.y;
        out.y = a.z * b.x - a.x * b.z;
        out.z = a.x * b.y - a.y * b.x;
    }

    static double vector_dot(final vector a, final vector b)
    {
      return a.x * b.x + a.y * b.y + a.z * b.z;
    }

    static void vector_normalize(vector a)
    {
        double dot = vector_dot(a, a);
        double mag = sqrt(dot);
        //print("dot="); println(dot);
        //print("mag="); println(mag);
        a.x /= mag;
        a.y /= mag;
        a.z /= mag;
    }

    static vector p = new vector(0, -1, 0);

    /*
     * m_max and m_min are calibration values for the maximum and minimum
     * measurements recorded on each magnetic axis, which can vary for each
     * LSM303DLM. You should replace the values below with max and min readings from
     * your particular device.
     *
     * To obtain the max and min values, you can use this program's
     * calibration mode, which is enabled by pressing one of the pushbuttons. While
     * calibration mode is active, point each of the axes of the LSM303DLM toward
     * and away from the earth's North Magnetic Pole. Due to space constraints on an
     * 8x2 LCD, only one axis is displayed at a time; each button selects an axis to
     * display (top = X, middle = Y, bottom = Z), and pressing any button a second
     * time exits calibration mode and returns to normal operation.
     */
    static vector m_max = new vector(527, 419, 417);
    static vector m_min = new vector(-472, -584, -562);

    static String ribbon = "   N   NE   E   SE   S   SW   W   NW   N   "; // 42

    static enum calibration_mode {CAL_NONE, CAL_X, CAL_Y, CAL_Z};

    static void i2c_start() {  
        TWCR = (byte)((1 << TWINT) | (1 << TWSTA) | (1 << TWEN)); // send start condition  
        while (((TWCR & (1 << TWINT))==0));  
    }  
      
    static void i2c_write_byte(int b) {  
        TWDR = (byte)(b);              
        TWCR = (byte)((1 << TWINT) | (1 << TWEN)); // start address transmission  
        while (((TWCR & (1 << TWINT))==0));  
    }  
      
    static int i2c_read_byte() {  
        TWCR = (1 << TWINT) | (1 << TWEA) | (1 << TWEN); // start data reception, transmit ACK  
        while (((TWCR & (1 << TWINT))==0));  
        return TWDR&0xff;
    }  

    static int i2c_read_last_byte() {  
        TWCR = (byte)((1 << TWINT) | (1 << TWEN)); // start data reception
        while (((TWCR & (1 << TWINT))==0));  
        return TWDR&0xff;
    }  
      
    static void i2c_stop() {  
        TWCR = (byte)((1 << TWINT) | (1 << TWSTO) | (1 << TWEN)); // send stop condition  
    }  

    // Returns a set of acceleration and raw magnetic readings from the cmp01a.
    static void read_data_raw(vector a, vector m)
    {
        // read accelerometer values
        i2c_start();
        i2c_write_byte(0x30); // write acc
        i2c_write_byte(0xa8); // OUT_X_L_A, MSB set to enable auto-increment
        i2c_start();          // repeated start
        i2c_write_byte(0x31); // read acc
        /*unsigned*/ int axl = i2c_read_byte();
        /*unsigned*/ int axh = i2c_read_byte();
        /*unsigned*/ int ayl = i2c_read_byte();
        /*unsigned*/ int ayh = i2c_read_byte();
        /*unsigned*/ int azl = i2c_read_byte();
        /*unsigned*/ int azh = i2c_read_last_byte();
        i2c_stop();

        // read magnetometer values
        i2c_start(); 
        i2c_write_byte(0x3C); // write mag
        i2c_write_byte(0x03); // OUTXH_M
        i2c_start();          // repeated start
        i2c_write_byte(0x3D); // read mag
        /*unsigned*/ int mxh = i2c_read_byte();
        /*unsigned*/ int mxl = i2c_read_byte();
        /*unsigned*/ int mzh = i2c_read_byte();
        /*unsigned*/ int mzl = i2c_read_byte();
        /*unsigned*/ int myh = i2c_read_byte();
        /*unsigned*/ int myl = i2c_read_last_byte();
        i2c_stop();

        a.x = axh << 8 | axl;
        a.y = ayh << 8 | ayl;
        a.z = azh << 8 | azl;
        m.x = mxh << 8 | mxl;
        m.y = myh << 8 | myl;
        m.z = mzh << 8 | mzl;
    }

    // Returns a set of acceleration and adjusted magnetic readings from the cmp01a.
    static void read_data(vector a, vector m)
    {
        read_data_raw(a, m);

        // shift and scale
        m.x = ((m.x - m_min.x) / (m_max.x - m_min.x) * 2 - 1.0);
        m.y = ((m.y - m_min.y) / (m_max.y - m_min.y) * 2 - 1.0);
        m.z = ((m.z - m_min.z) / (m_max.z - m_min.z) * 2 - 1.0);
    }

    // Returns a heading (in degrees) given an acceleration vector a due to gravity, a magnetic vector m, and a facing vector p.
    static int get_heading(final vector a, final vector m, final vector p)
    {
        vector E = new vector();
        vector N = new vector();

        // cross magnetic vector (magnetic north + inclination) with "down" (acceleration vector) to produce "east"
        vector_cross(m, a, E);
        vector_normalize(E);

        // cross "down" with "east" to produce "north" (parallel to the ground)
        vector_cross(a, E, N);
        vector_normalize(N);

        // compute heading
        int heading = (int) Math.round(atan2(vector_dot(E, p), vector_dot(N, p)) * 180 / PI);
        if (heading < 0)
            heading += 360;
        return heading;
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        DDRC = 0;                              // all inputs
        PORTC = (byte)((1 << PORTC4) | (1 << PORTC5)); // enable pull-ups on SDA and SCL, respectively

        TWSR = 0;   // clear bit-rate prescale bits
        TWBR = 17;  // produces an SCL frequency of 400 kHz with a 20 MHz CPU clock speed

        //clear();  
        //enable accelerometer
        i2c_start(); 
        i2c_write_byte(0x30); // write acc
        i2c_write_byte(0x20); // CTRL_REG1_A
        i2c_write_byte(0x27); // normal power mode, 50 Hz data rate, all axes enabled
        i2c_stop();

        //enable magnetometer
        i2c_start(); 
        i2c_write_byte(0x3C); // write mag
        i2c_write_byte(0x02); // MR_REG_M
        i2c_write_byte(0x00); // continuous conversion mode
        i2c_stop();

        vector a=new vector(), m=new vector();
        //char ribbon_segment[] = new char[8];
        /*unsigned*/ char button;
        calibration_mode mode = calibration_mode.CAL_NONE;
        vector cal_m_max = new vector();
        vector cal_m_min = new vector();

        Serial.println(ribbon);
        while(true)
        {
            // see if a button was pressed to enable calibration mode
            // each button displays max and min measurements for one axis:
            // top = X, middle = Y, bottom = Z
            // if any button is pressed a second time, return to normal mode
            //while (Serial.available()==0);
            if (Serial.available()>0) {
                button = (char)Serial.read();
                //println(":"+button);
            } else {
                button = ' ';
                //println("-");
            }

            if (button=='x')
            {
                if (mode != calibration_mode.CAL_X)
                    mode = calibration_mode.CAL_X;
                else
                    mode = calibration_mode.CAL_NONE;
            }
            else if (button=='y')
            {
                if (mode != calibration_mode.CAL_Y)
                    mode = calibration_mode.CAL_Y;
                else
                    mode = calibration_mode.CAL_NONE;
            }
            else if (button=='z')
            {
                if (mode != calibration_mode.CAL_Z)
                    mode = calibration_mode.CAL_Z;
                else
                    mode = calibration_mode.CAL_NONE;
            }

            
            if (mode == calibration_mode.CAL_NONE) // normal mode - display heading and compass ribbon
            {
                vector a_avg = new vector(), m_avg = new vector();
            
                // take 5 acceleration and magnetic readings and average them
                for(int i = 0; i < 5; i++)
                {
                    read_data(a, m);

                    a_avg.x += a.x;
                    a_avg.y += a.y;
                    a_avg.z += a.z;
                    m_avg.x += m.x;
                    m_avg.y += m.y;
                    m_avg.z += m.z;
                }

                a_avg.x /= 5;
                a_avg.y /= 5;
                a_avg.z /= 5;
                m_avg.x /= 5;
                m_avg.y /= 5;
                m_avg.z /= 5;

                int heading = get_heading(a_avg, m_avg, p);

                // select the portion of the ribbon to display
//                strlcpy(ribbon_segment, &ribbon[(heading + 5) / 10], 8);

                //clear();
                //print("heading: ");  println(heading);
                heading=(heading + 5) / 10;
                for (int i = 0; i < 42; i++) {
                    Serial.print(' ');
                }
                Serial.print('\r');
                for (int i = 0; i < heading; i++) {
                    Serial.print(' ');
                }
                Serial.print('V');  // center indicator
                Serial.print((char)8);
                
                
//                lcd_goto_xy(4, 0);
//                print('v');  // center indicator
//                lcd_goto_xy(1, 1);
//                print(ribbon_segment); // ribbon segment
            }
            else // calibration mode - record and display max/min measurements
            {
                read_data_raw(a, m);
                if (m.x < cal_m_min.x) cal_m_min.x = m.x;
                if (m.x > cal_m_max.x) cal_m_max.x = m.x;
                if (m.y < cal_m_min.y) cal_m_min.y = m.y;
                if (m.y > cal_m_max.y) cal_m_max.y = m.y;
                if (m.z < cal_m_min.z) cal_m_min.z = m.z;
                if (m.z > cal_m_max.z) cal_m_max.z = m.z;

                //clear();

                switch (mode)
                {
                    case CAL_X:
                        Serial.print("Xmax: ");
                        Serial.print(cal_m_max.x);
                        Serial.print(" min: ");
                        Serial.println(cal_m_min.x);
                        break;
                    case CAL_Y:
                        Serial.print("Ymax: ");
                        Serial.print(cal_m_max.y);
                        Serial.print(" min: ");
                        Serial.println(cal_m_min.y);
                        break;
                    default:
                        Serial.print("Zmax: ");
                        Serial.print(cal_m_max.z);
                        Serial.print(" min: ");
                        Serial.println(cal_m_min.z);
                        break;
                }
            }

            delay(100);
        }
    }
}
