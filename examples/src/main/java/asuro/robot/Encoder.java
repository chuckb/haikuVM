package asuro.robot;

import static haiku.avr.lib.asuro.lib2_8_0_rc1.AsuroLib2_8_0_rc1.*;

public class Encoder {
    public static void main(String[] args) {
        Init();

        EncoderInit();

        MotorDir(FWD, FWD);
        MotorSpeed(150, 150);
        while (true) {
            // Dein Programm

            if (encoder[0] > 500) {
                EncoderStop();
                MotorSpeed(0, 0);
            }
        }
    }
}
