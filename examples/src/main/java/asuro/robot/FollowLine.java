package asuro.robot;
import static haiku.avr.lib.asuro.lib2_8_0_rc1.AsuroLib2_8_0_rc1.*;
import static java.lang.Math.*;

public class FollowLine {

	private static final int INIT_TIME = 3000; // in millis
	private static final int lSpeed1=90, rSpeed1=90;
	private static final int lSpeed0=40, rSpeed0=40;
	private static final long threshold=20;

	private static void follow() {
	    int i;
		int state=0;		
        long avg[]={0, 0};
		int data[]=new int[2];
		
		// calculate avg for white
		SetMotorPower(0, 0);	// both motors stop
		FrontLED(ON); 			// switch on line sensnor light

		long t0=Gettime();
		for(i=0; Gettime()-t0<INIT_TIME; i++){								
			LineData(data); 	// read both line sensors
			avg[0]+=data [0];
			avg[1]+=data [1];
		}
		avg[0]/=i;
		avg[1]/=i;
		
		SetMotorPower(lSpeed1, rSpeed1); 
		while(true) {
			LineData(data); 	// read both line sensors
			int left= (int)(100L*data[0]/avg[0]);  // left  is 100 on avg
			int right=(int)(100L*data[1]/avg[1]);  // right is 100 on avg
//            PrintInt(left);
//            SerPrint(", ");
//            PrintInt(right);
//            SerPrint(", ");
//            PrintInt(state);
//            SerPrint("\r\n");

			if(abs(left-right)>threshold) {
				if (left > right ) {// links heller als rechts...
					// ... dann links mehr Gas geben...
				    state=2;
	                SetMotorPower(lSpeed1, rSpeed0);
				} else {
					// ... sonst rechts mehr Gas geben!
				    state=1;
	                SetMotorPower(lSpeed0, rSpeed1);
				}
			} else {
				// equal
				if(left>100-threshold/2) {
					// lost in white space
					//  -> revert last turn action
	                if (state == 1) {
	                    SetMotorPower(lSpeed1, rSpeed0);
	                } else {
	                    SetMotorPower(lSpeed0, rSpeed1);
	                }
				} else {
					// black is on both sensors
					// go straight
					state=3;
	                SetMotorPower(lSpeed1, rSpeed1);
				}
			}
            StatusLED(state);
		}
	}
	
	public static void main(String[] args) {
		Init();
		follow();
	}
}
