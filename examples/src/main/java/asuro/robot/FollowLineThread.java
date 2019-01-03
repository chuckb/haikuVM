package asuro.robot;
import static haiku.avr.lib.asuro.lib2_8_0_rc1.AsuroLib2_8_0_rc1.*;
import static java.lang.Math.*;

public class FollowLineThread {
	static private int data[]=new int[2];  //Speicher bereitstellen

	private static final int TIME = 30000;

	private static class sense_Task extends Thread {
		public void run() {
			while (true) {
				LineData(data); 	// aktuelle Helligkeitswerte der
									// Fototransistoren einlesen
			}
		}
	}

	private static void follow() {
		long avg[]={0, 0};
		int i;
		long t0;
		final int lSpeed1=90, rSpeed1=90;
		final int lSpeed0=50, rSpeed0=50;
		int action=0;
		int status=3;
		final long threshold=40;
		long left, right;
		
		
		// Weiﬂabgleich in avg[]
		SetMotorPower(0, 0);	// Beide Motoren stop
		FrontLED(ON); 			// Linienbeleuchtung einschalten
		StatusLED(OFF);

		t0=Gettime();
		for(i=0; Gettime()-t0<3000; i++){
			avg[0]+=data[0];
			avg[1]+=data[1];
		}
		avg[0]/=i;
		avg[1]/=i;
		
		// Erstmal 5cm fahren damit Sensorfehler beim Start 
		// die state machine nicht irritieren
		SetMotorPower(lSpeed1, rSpeed1); 
		t0=Gettime();
		for(i=0; ; i++) {
			left=100L*data[0]/avg[0];
			right=100L*data[1]/avg[1];

			if(abs(100L*(left-right))>threshold) {
				if (left > right ) {// links heller als rechts...
					// ... dann links mehr Gas geben...
					action=2;
					StatusLED(RED);
				} else {
					// ... sonst rechts mehr Gas geben!
					action=1;
					StatusLED(GREEN);
				}
				status=action;
			} else {
				// equal
				if(left>100-threshold/2) {
					// lost in white space
					// correct last move
					status=0;
					StatusLED(OFF);
				} else {
					// black is on both sensors
					// go straight
					action=3;
					status=3;
					StatusLED(YELLOW);
				}
			}
			switch (status) {
			case 0: // lost in white space -> revert last action
				if (action == 1) {
					SetMotorPower(lSpeed1, rSpeed0);
				} else {
					SetMotorPower(lSpeed0, rSpeed1);
				}
				break;
			case 1:
				SetMotorPower(lSpeed0, rSpeed1);
				break;
			case 2:
				SetMotorPower(lSpeed1, rSpeed0);
				break;
			case 3:
				SetMotorPower(lSpeed1, rSpeed1);
				break;
			}
		}
	}
	
	public static void main(String[] args) {
		Init();
		new sense_Task().start();

		SerPrint("FollowLine");
		follow();
	}
}
