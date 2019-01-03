package haikuvm.bench;
import static java.lang.Math.*;

public class FollowLine {
	public static void LineData(int[] data) {
		/*
		 * Linken Linien-Sensor lesen
		 */
		data[0] = 257;
		/*
		 * Rechten Linien-Sensor lesen
		 */
		data[1] = 259;
	}

	private static void follow() {
		float favg[]={0, 0};
		float avg[]=new float[2];
		int i;
		float left, right, diff;
		
		int data[]=new int[2];  //Speicher bereitstellen
		
		for(i=0; i<2000; i++){
								
			LineData(data); 	// aktuelle Helligkeitswerte der
								// Fototransistoren einlesen
			favg[0]+=data [0];
			favg[1]+=data [1];

			System.out.println(i+" inc[1] "+data[1]+"\t"+favg[1]);
		}
		avg[0]=favg[0]/i;
		avg[1]=favg[1]/i;
		System.out.println(i+" ==> "+avg[1]+"\t"+favg[1]);

		LineData(data); 	// aktuelle Helligkeitswerte der
		left=data[0]/avg[0];
		right=data[1]/avg[1];

		diff=left-right;
		System.out.println(diff+" is "+left+"\t"+right);
	}
	
	public static void main(String[] args) {
		System.out.println("FollowLine");
		follow();
	}
}
