package raw_rcx;

import static haiku.rcx.lib.ROM.*;

/**
 * Calculates the Fibonacci numbers from 0 to 19 and shows them on the LCD
 * display. At the end it displays the elapsed time in seconds for this job.
 * And waits until the power key is pressed.
 *
 *<pre>
  mkdir myProject
  cd myProject
  C:\haikuVM\bin\haiku -v --Config rcx C:\haikuVM\examples\src\main\java\raw_rcx\Fibonacci.java
 *</pre>
 *
 * @author Genom
 *
 */
public class Fibonacci {
	static int fib(int n) {
		if (n < 2)
			return 1;
		else
			return fib(n - 2) + fib(n - 1);
	}

	public static void main(String[] args) {
		int t0 = (int) System.currentTimeMillis();
		for (int i = 0; i <= 20; i++) {
			showNumber(fib(i) % 10000);
		}
		int dt = ((((int) System.currentTimeMillis()) - t0) / 1000) % 10000;
		showNumber(dt);

		short_t status = new short_t();
		get_power_status(POWER_KEY, status);
		while (status.s != 0) {
			get_power_status(POWER_KEY, status);
		}
	}

}
