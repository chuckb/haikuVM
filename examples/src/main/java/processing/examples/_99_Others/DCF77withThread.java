package processing.examples._99_Others;

import static processing.hardware.arduino.cores.arduino.Arduino.*;

/**
     */
public class DCF77withThread {
    private static final int DCF77PIN = 2;

    static byte commited[]=new byte[8];
    static byte bits[]=new byte[8];
    
    static private long t0;
    static private long lastTick;
    static private int dataSignal;
    static int second;
    static private int lastSignal;

    private static void setBit(int idx, int value) {
        bits[idx/8]|=(byte)(value<<(idx%8));
    }

    public static void setup() {
        Serial.begin(9600);
        while (!Serial.isOpen()) {
            ; // wait for serial port to connect. Needed for Leonardo only
        }
        pinMode(DCF77PIN, INPUT);
        Serial.println("DCF77");
        new DCF77Thread().start();
    }
    
    public static void loop() {
        int signal = digitalRead(DCF77PIN);
        if (lastSignal != signal) {
            long t1 = millis();

            if (signal == 0) {
                dataSignal += (int) (t1 - t0);
            } else {
                if (t1 - lastTick > ((second == 58)?1900:900)) {
                    //Serial.println(dataSignal);

                    setBit(second, dataSignal / 185);

                    second++;
                    if (second > 58 || t1 - lastTick > 1500) {
                        for (int i = 0; i < 8; i++) {
                            commited[i]=bits[i];
                            bits[i]=0;
                        }
                        second = 0;
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

class DCF77Thread extends Thread {
    private int error;
    private int errors;
    private int count;

    private String[] dowName = {
        " ????", " Mon  ", " Tues ", " Wed  ", " Thurs", " Fri  ", " Sat  ", " Sun  "
    };

    private void printFormated(String sep, int value) {
        Serial.print(sep);
        if (value < 10) Serial.print("0");
        Serial.print(value);
    }

    private int parity(int a, int b) {
        int parity=0;
        for (int i = 0; i < b-a; i++) {
            int bit=((DCF77withThread.commited[(a+i)/8] >> ((a+i)%8) ) & 1);
            parity^=bit;
        }
        return parity;
    }

    private int getValue(int a, int b) {
        int value=0;
        for (int i = 0; i < b-a; i++) {
            int bit=((DCF77withThread.commited[(a+i)/8] >> ((a+i)%8) ) & 1);
            if (i<4) {
                value+= bit << i;
            } else {
                value+= 10*(bit << (i-4));
            }
        }
        return value;
    }

    private void printTime() {
        if (DCF77withThread.second==0) {
            error= getValue(0, 1) + (getValue(20, 21)^1) + parity(21, 29) + parity(29, 36) + parity(36, 59);
            count++;
            if (error>0) errors++;
        }
        Serial.print(dowName[getValue(42, 45)]);
        printFormated(" ", getValue(36, 42)); printFormated(".", getValue(45, 50)); printFormated(".", getValue(50, 58));
        printFormated(" ", getValue(29, 35)); printFormated(":", getValue(21, 28)); printFormated(":", DCF77withThread.second);
        Serial.print((error > 0) ? " (error) " : " (ok) ");
        Serial.print(errors); Serial.print("/"); Serial.println(count);
    }
    
    public void run() {
        while(true) {
            int lastSecond=DCF77withThread.second;
            while(lastSecond==DCF77withThread.second) {};
            printTime();
        }
    }
}
