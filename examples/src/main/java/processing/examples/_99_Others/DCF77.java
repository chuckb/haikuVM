package processing.examples._99_Others;

import static processing.hardware.arduino.cores.arduino.Arduino.*;

/**
     */
public class DCF77 {
    static private final int DCF77PIN = 2;

    static private long t0;
    static private long lastTick;
    static private int dataSignal;
    static private int bitCount;
    static private int lastSignal;

    static private int minute, hour, day, dow, month, year;
    
    static private int parity;
    static private int error;
    static private int errors;
    static private int count;

    static private String[] dowName = {
        " ????", " Mon  ", " Tues ", " Wed  ", " Thurs", " Fri  ", " Sat  ", " Sun  "
    };

    static private void setTime(int bitCount, int bitValue) {
        Serial.print(bitValue);
        switch(bitCount) {
          case 0:  error=bitValue;    Serial.print(error>0?"!": "-"); break;
          case 14:
          case 19: Serial.print("-"); break;
          case 20: error|=bitValue^1; Serial.print((bitValue^1)!=0?"!": "-"); break;
          
          case 21: minute =   bitValue; parity =bitValue; break;
          case 22: minute+= 2*bitValue; parity^=bitValue; break;
          case 23: minute+= 4*bitValue; parity^=bitValue; break;
          case 24: minute+= 8*bitValue; parity^=bitValue; break;
          case 25: minute+=10*bitValue; parity^=bitValue; break;
          case 26: minute+=20*bitValue; parity^=bitValue; break;
          case 27: minute+=40*bitValue; parity^=bitValue; break;
          case 28: parity^=bitValue; error|=parity; Serial.print(parity!=0?"!": "-"); break;
          
          case 29: hour   =   bitValue; parity =bitValue; break;
          case 30: hour  += 2*bitValue; parity^=bitValue; break;
          case 31: hour  += 4*bitValue; parity^=bitValue; break;
          case 32: hour  += 8*bitValue; parity^=bitValue; break;
          case 33: hour  +=10*bitValue; parity^=bitValue; break;
          case 34: hour  +=20*bitValue; parity^=bitValue; break;
          case 35: parity^=bitValue; error|=parity; Serial.print(parity!=0?"!": "-"); break;
          
          case 36: day    =   bitValue; parity =bitValue; break;
          case 37: day   += 2*bitValue; parity^=bitValue; break;
          case 38: day   += 4*bitValue; parity^=bitValue; break;
          case 39: day   += 8*bitValue; parity^=bitValue; break;
          case 40: day   +=10*bitValue; parity^=bitValue; break;
          case 41: day   +=20*bitValue; parity^=bitValue; Serial.print("-"); break;
           
          case 42: dow    =   bitValue; parity ^= bitValue; break;
          case 43: dow   += 2*bitValue; parity ^= bitValue; break;
          case 44: dow   += 4*bitValue; parity ^= bitValue; Serial.print("-"); break;
           
          case 45: month  =   bitValue; parity^=bitValue; break;
          case 46: month += 2*bitValue; parity^=bitValue; break;
          case 47: month += 4*bitValue; parity^=bitValue; break;
          case 48: month += 8*bitValue; parity^=bitValue; break;
          case 49: month +=10*bitValue; parity^=bitValue; Serial.print("-"); break;
           
          case 50: year   =   bitValue; parity^=bitValue; break;
          case 51: year  += 2*bitValue; parity^=bitValue; break;
          case 52: year  += 4*bitValue; parity^=bitValue; break;
          case 53: year  += 8*bitValue; parity^=bitValue; break;
          case 54: year  +=10*bitValue; parity^=bitValue; break;
          case 55: year  +=20*bitValue; parity^=bitValue; break;
          case 56: year  +=40*bitValue; parity^=bitValue; break;
          case 57: year  +=80*bitValue; parity^=bitValue; break;
          case 58: parity^=bitValue; error|=parity; Serial.print(parity!=0?"! ": "  "); break;
        }
    }

    private static void printFormated(String sep, int value) {
        Serial.print(sep);
        if (value < 10) Serial.print("0");
        Serial.print(value);
    }

    static private void printTime() {
        count++;
        if (error>0) errors++;
        Serial.print(dowName[dow]);
        printFormated(" ", day);  printFormated(".", month); printFormated(".", year);
        printFormated(" ", hour); printFormated(":", minute);
        Serial.print((error > 0) ? " (error) " : " (ok) ");
        Serial.print(errors); Serial.print("/"); Serial.println(count);
    }

    public static void setup() {
        Serial.begin(9600);
        while (!Serial.isOpen()) {
            ; // wait for serial port to connect. Needed for Leonardo only
        }
        pinMode(DCF77PIN, INPUT);
        Serial.println("DCF77");
    }

    public static void loop() {
        int signal = digitalRead(DCF77PIN);
        if (lastSignal != signal) {
            long t1 = millis();

            if (signal == 0) {
                dataSignal += (int) (t1 - t0);
            } else {
                if (t1 - lastTick > ((bitCount == 58)?1900:900)) {
                    setTime(bitCount, dataSignal / 170);

                    bitCount++;
                    if (bitCount > 58 || t1 - lastTick > 1500) {
                        if (bitCount <= 58) error++;
                        printTime();
                        bitCount = 0;
                    }
                    dataSignal = 0;

                    lastTick = t1;
                }
            }
            
            t0 = t1;
            lastSignal = signal;
        }
    }
}
