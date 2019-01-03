package avr.gallerie.user.mlade;

/**
 * Needs JAVA 7
 * Runs on Atmega16 15MHz 
 */
public class TestLed {

    private static void Sleep (int msec) {
        int j;
        for (int i=0; i<(msec*7); i++) {
            j=i;
        }
    }
    
    public static void main (String args[]){
        int zaehler=0;
        Led.LedInit();  // 1. Led an
        while (true){
            while (zaehler < 4){
                Led.LedSet((0b00000001 << zaehler)); 
                zaehler++;
                Sleep(1000);
            }
            zaehler=0;
        }   
    }
}