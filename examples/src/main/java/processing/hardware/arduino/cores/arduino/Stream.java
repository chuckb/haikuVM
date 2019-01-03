package processing.hardware.arduino.cores.arduino;

import static processing.hardware.arduino.cores.arduino.Arduino.*;

public abstract class Stream extends Print {
    private long _timeout; // number of milliseconds to wait for the next char
    // before aborting timed read
    private long _startMillis; // used for timeout measurement

    @Override
    public abstract byte write(byte c);

    public abstract int read();

    public abstract int available();

    public abstract int peek();

    public abstract void flush();

    private final int PARSE_TIMEOUT = 1000; // default number of milli-seconds
    // to wait
    private final char NO_SKIP_CHAR = 1; // a magic char not found in a valid

    // ASCII numeric field

    // private method to read stream with timeout
    private int timedRead() {
        int c;
        _startMillis = millis();
        do {
            c = read();
            if (c >= 0)
                return c;
        } while (millis() - _startMillis < _timeout);
        return -1; // -1 indicates timeout
    }

    // private method to peek stream with timeout
    private int timedPeek() {
        int c;
        _startMillis = millis();
        do {
            c = peek();
            if (c >= 0)
                return c;
        } while (millis() - _startMillis < _timeout);
        return -1; // -1 indicates timeout
    }

    public long parseInt() {
        return parseInt(NO_SKIP_CHAR);
    }

    /**
     * as above but a given skipChar is ignored this allows format characters
     * (typically commas) in values to be ignored
     * 
     * @param skipChar
     * @return
     */
    public long parseInt(char skipChar) {
        boolean isNegative = false;
        long value = 0;
        int c;

        c = peekNextDigit();
        // ignore non numeric leading characters
        if (c < 0)
            return 0; // zero returned if timeout

        do {
            if (c == skipChar)
                ; // ignore this charactor
            else if (c == '-')
                isNegative = true;
            else if (c >= '0' && c <= '9') // is c a digit?
                value = value * 10 + c - '0';
            read(); // consume the character we got with peek
            c = timedPeek();
        } while ((c >= '0' && c <= '9') || c == skipChar);

        if (isNegative)
            value = -value;
        return value;
    }

    /**
     * discards non-numeric characters
     * 
     * @return peek of the next digit in the stream or -1 if timeout
     */
    private int peekNextDigit() {
        int c;
        while (true) {
            c = timedPeek();
            if (c < 0)
                return c; // timeout
            if (c == '-')
                return c;
            if (c >= '0' && c <= '9')
                return c;
            read(); // discard non-numeric
        }
    }

}
