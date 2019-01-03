package avr.gallerie.user.peter.main;

public class Blink {  

    public static native void Main();  

    public static native void nBlink();  

    public static void main(String[] args) {  
        // Data direction of I/O-Port. 
        Main();
        while (true) {  
            nBlink();
        }  
    }  
} 