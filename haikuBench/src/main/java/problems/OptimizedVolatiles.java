package problems;

// problem with proguard
public class OptimizedVolatiles {
	private static volatile int sensor01;

	private native static void readSensors();

	public static void main(String[] args) {
		while (true) {
		    readSensors();
			if (sensor01>0)  {
				System.out.println("Sensor01 value is "+sensor01);
				return;
			}
		}
	}
}

