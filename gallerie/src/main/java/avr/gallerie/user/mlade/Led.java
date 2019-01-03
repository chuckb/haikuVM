package avr.gallerie.user.mlade;
import static haiku.vm.MemoryAccess.*;

public class Led {
    public static final int DDRB=(((0x17) + 0x20));
    public static final int PORTB=(((0x18) + 0x20));

    public static void LedInit() {
        setMemory8(DDRB,(getMemory8(DDRB)| 0b00001111));
        setMemory8(PORTB, 0b00000001);
    }
    
    public static void LedSwitch(){
        int portb;
        portb=getMemory8(PORTB);
        if (portb < 0b00001111){
            setMemory8(PORTB, (portb << 1));
        }
        else {
            setMemory8(PORTB, 0b00000001);
        }
    }
    
    public static void LedSet(int leds){
        if (leds > 0b00001111){
            leds=0b00001111;
        }
        setMemory8(PORTB,leds);
    }
}