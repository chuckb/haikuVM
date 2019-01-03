package processing.hardware.arduino.cores.arduino;

import static haiku.avr.AVRDefines._BV;

/**
 * TO DO void serialWrite(unsigned char); int serialAvailable(void); int
 * serialRead(void); void serialFlush(void);
 */
public abstract class Print {
    public static final int BYTE = 0;
    private static int write_error = 0;

    protected void setWriteError(int err) {
        write_error = err;
    };

    protected void setWriteError() {
        setWriteError(1);
    }

    public int getWriteError() {
        return write_error;
    }

    public void clearWriteError() {
        setWriteError(0);
    }

    public void print(char c) {
        write((byte) c);
    }

    public abstract byte write(byte c);

    public void println(String string) {
        print(string);
        println();
    }

    public void print(String string) {
        for (int i = 0; i < string.length(); i++) {
            print(string.charAt(i));
        }
    }

    public void print(int n) {
        print((long) n);
    }

    public void print(long n) {
        if (n < 0) {
            print('-');
            n = -n;
        }
        printNumber(n, 10);
    }

    public void print(long n, int base) {
        if (base == 0)
            print((char) n);
        else if (base == 10)
            print(n);
        else
            printNumber(n, base);
    }

    public void print(double n) {
        printFloat(n, 2);
    }

    public void println() {
        print('\r');
        print('\n');
    }

    public void println(char c) {
        print(c);
        println();
    }

    public void println(int n) {
        print(n);
        println();
    }

    public void println(long n) {
        print(n);
        println();
    }

    public void println(long n, int base) {
        print(n, base);
        println();
    }

    public void println(double n) {
        print(n);
        println();
    }

    // Private Methods
    // /////////////////////////////////////////////////////////////

    private void printNumber(long n, int base) {
        byte buf[] = new byte[8 * 4/* sizeof(long) */]; // Assumes 8-bit chars.
        int i = 0;

        if (n == 0) {
            print('0');
            return;
        }

        while (n > 0) {
            buf[i++] = (byte) (n % base);
            n /= base;
        }

        for (; i > 0; i--)
            print((char) (buf[i - 1] < 10 ? '0' + buf[i - 1]
                    : 'A' + buf[i - 1] - 10));
    }

    public void printFloat(double number, int digits) {
        // Handle negative numbers
        if (number < 0.0) {
            print('-');
            number = -number;
        }

        // Round correctly so that print(1.999, 2) prints as "2.00"
        double rounding = 0.5;
        for (int i = 0; i < digits; ++i)
            rounding /= 10.0;

        number += rounding;

        // Extract the integer part of the number and print it
        long int_part = (long) number;
        double remainder = number - (double) int_part;
        print(int_part);

        // Print the decimal point, but only if there are digits beyond
        if (digits > 0)
            print(".");

        // Extract digits from the remainder one at a time
        while (digits-- > 0) {
            remainder *= 10.0;
            int toPrint = (int) remainder;
            print(toPrint);
            remainder -= toPrint;
        }
    }
}