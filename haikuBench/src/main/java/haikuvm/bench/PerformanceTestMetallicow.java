package haikuvm.bench;

/**
 * Performance of ~HaikuVM 1.4.2 compared with micropython (https://micropython.org/).
 *
 * Taken from https://github.com/micropython/micropython/wiki/Performance. A performance test program given by Metallicow.
 *
 */
public class PerformanceTestMetallicow {
	public static void main(String[] args) {
		for (int i = 0; ; i++) {
		    long endTime = System.currentTimeMillis() + 1000;
		    long count = 0;
		    while (System.currentTimeMillis() < endTime)
		        count++;
		    System.out.println(i+" Count: "+count);
		}
	}
}

/*
//with Arduino Uno
#define SomethingInSetUp 1

void setup() {
 Serial.begin(19200);

#if SomethingInSetUp
 uint32_t endTime = millis() + 10000;
 uint32_t count = 0;
 while (millis() < endTime)
     count++;
 Serial.print("Count# ");
 Serial.println(count);
#else
#endif
}


#if SomethingInSetUp
//Count# 5129696
//Count: 5130033
//Count: 5130032
//Count: 5130032
//Count: 5130032
//...
#else
//Count: 7228208
//Count: 7228686
//Count: 7228686
//Count: 7228686
//...
#endif

void loop() {
 uint32_t endTime = millis() + 10000;
 uint32_t count = 0;
 while (millis() < endTime)
     count++;
 Serial.print("Count: ");
 Serial.println(count);
}
*/