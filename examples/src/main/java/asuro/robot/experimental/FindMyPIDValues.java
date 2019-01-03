package asuro.robot.experimental;
import static haiku.avr.lib.asuro.lib2_8_0_rc1.AsuroLib2_8_0_rc1.*;
import static java.lang.Math.*;

/**
 * Obwohl nur wenig JAVA (16/32):
 *  totalMethods=      106
 *  totalBClength=     1831
 *  totalConstLength=  400
 *  totalClassesLength=108
 * 
 * Ist trotzdem .text section um 1454 Bytes überschritten.
 * 
 * Da ist dann wohl die VM selbst ca 7000 Byte groß.
 * Vermutlich wg. double.
 * 
 * @author genom2
 *
 */
public class FindMyPIDValues {
	static class PID {
		public PID(double kp, double ki, double kd, double esum, double ealt,
				double soll, double ticks, double pwm, double err) {
			Kp = kp;
			Ki = ki;
			Kd = kd;
			this.esum = esum;
			this.ealt = ealt;
			this.soll = soll;
			this.ticks = ticks;
			this.pwm = pwm;
			this.err = err;
		}

		double Kp, Ki, Kd, esum, ealt, soll, ticks, pwm, err;
	};
	
	static class Serial {

		public static void print(String string) {
			// TODO Auto-generated method stub
			
		}

		public static void println(double d) {
			// TODO Auto-generated method stub
			
		}

		public static void println() {
			// TODO Auto-generated method stub
			
		}

		public static void print(int no) {
			// TODO Auto-generated method stub
			
		}

		public static void print(double err) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private static final double r=115.0/2; // Radius der Räder [mm]
	private static final double u=2*PI*r; // [mm]
	private static final int ticksu=256; // ticks pro Umfang

	private static PID candidate=new PID(2,1,0,0,0,0,0,0,0);
	private static PID m=new PID(2,1,0,0,0,0,0,0,0);
	//PID best={0.05 ,0.00001 ,0,0,0,0,0,0,1e10};
	//PID best={0.01 ,0.0001 ,20,0,0,0,0,0,1e10};
	//PID best={0.11 ,0.00001 ,44,0,0,0,0,0,1e10};

	//PID best={0.124 ,0.0000095 ,47,0,0,0,0,0,1e10};
	private static PID best=new PID(0.124 ,0 ,47,0,0,0,0,0,1e10); // I>0 führt evtl. zum overflow und umschlagen z.B: 3000 -> -3000

	/*
	Software PID-Regler:

	e= soll - ist;
	esum = esum + e
	y = Kp * e + Ki * Ta * esum + Kd * (e - ealt)/Ta
	ealt = e
	*/
	//static int delta=60;

	private static int regeln(PID pid, int Ta, int offset) {
		double e= pid.soll - pid.ticks;
		pid.err+=e*e;
		pid.esum+=e;
		double y=(pid.Kp * e + Ta * pid.esum * pid.Ki + pid.Kd * (e - pid.ealt)/Ta );
		y+=pid.pwm;
		pid.ealt=e;
		/*
		if (pid->soll>0) {
			if (y<10) {
				y=10;
			} else if (y>LIMIT) {
				y=LIMIT;
			}
		} else if (pid->soll<0) {
			if (y>-10) {
				y=-10;
			} else if (y<-LIMIT) {
				y=-LIMIT;
			}
		}
		*/
		pid.pwm= y;
		return (int)(y+0.5);
	}

	//double step[]={5, 5, 5};
	static double step[]={0.1, 2.0, 0.0};

	private static double testPID(double best, int debug) {
		int xticks, yticks;
		final int duration=10000;
		int vSoll=1400; // [mm/s]
		double avg=0;
		int i=1;


		delay(1000);
		m.esum=0;
		m.ealt=0;
		m.soll=0;
		m.ticks=0;
		m.pwm=0;
		m.err=0;
		m.soll=vSoll;
		long timeout=millis()+duration;
		long t0=millis();

		for(i=0; millis()<timeout && (m.err < best || debug==1); i++) {
			delay(20);
			long t1=millis();
		    int delta=(int)(t1-t0);
		    t0=t1;
		    xticks=getXticks();
		    yticks=getYticks();

			double ticks=(xticks+yticks)/2;
			double vIst= (1000*ticks/ticksu)*u/delta;

			avg+=vIst;
			m.ticks=vIst;

			int x=regeln(m, delta, 0);
			if (debug==1) {
				Serial.print((int)vIst);	Serial.print(" ");	Serial.print(x); Serial.print(" ");	Serial.println(delta);
			}

			drive(x);
		}
		m.err=m.err*duration/(millis()-(timeout-duration));
		drive(80);
		delay(2000);
		drive(40);
		delay(2000);
		stopMotor();
		return avg/i;
	}


	private static int getYticks() {
		// TODO Auto-generated method stub
		return 0;
	}


	private static int getXticks() {
		// TODO Auto-generated method stub
		return 0;
	}


	private static void stopMotor() {
		// TODO Auto-generated method stub
		
	}


	private static void delay(int dauer) {
		Thread.nap(dauer);
	}


	private static long millis() {
		return System.currentTimeMillis();
	}

	private static int no=0;
	private static void searchBetterCandidate() {
		double avg=0;

		candidate=best;
		Serial.print("best: "); Serial.println(best.err);
		for(int i=0; i<15; i++) {
			no++;
			Serial.print("test no: "); Serial.print(no); Serial.print("\t");

			m=best;
			m.Kp+=step[0]*randomG(); if (m.Kp<0) m.Kp=0;
			m.Ki+=step[1]*randomG();
			m.Kd+=step[2]*randomG();
			avg=testPID(candidate.err, 0);
			if(m.err<candidate.err) {
				double err=m.err;
				// noch'n Test

				avg=testPID(candidate.err, 0);
				if(m.err<candidate.err) {
					err=(m.err+err)/2;
					testPID(candidate.err, 1);
					m.err=err;
					candidate=m;
				} else {
					Serial.print("\t");
				}
			} else {
				Serial.print("\t");
			}
			Serial.print("err="); Serial.print(m.err); Serial.print("  ");
			Serial.print("Kp="); Serial.print(m.Kp); Serial.print("  ");
			Serial.print("Ki="); Serial.print(m.Ki); Serial.print("  ");
			Serial.print("Kd="); Serial.print(m.Kd); Serial.print("  ");
			Serial.print("avg="); Serial.print(avg);
			Serial.println();
		}
	}

	private static double randomG() {
		double r=0;
		for(int i=0; i<5; i++) {
			r+=random(-100, 100);
		}
		return r/500.0;
	}


	private static double random(int i, int j) {
		// TODO Auto-generated method stub
		return 0;
	}


	private static void decreaseSearchRange() {
		for(int i=0; i<3 && step[2]>0.05; i++) {
			step[i]*=0.5;
		}
		Serial.print("f="); Serial.println(step[0]);
	}

	private static void searchPID() {
		while(true) {
			searchBetterCandidate();
			best=candidate;
			decreaseSearchRange();
		}
	}
			
	static int drive(int v) {
		return setServo( v);
	}

	private static int setServo(int v) {
		// TODO Auto-generated method stub
		return 0;
	}


	public static void main(String[] args) {
		Init();
		searchPID(); 
	}
}
