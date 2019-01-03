package haikuvm.bench;

/**
 * The use of synchroniced will not hurt.
 * 
 * @author genom2
 *
 */
public class Sync {
	static private final int N = 20;

	synchronized int sum_n_loop(int n) {
		int sum = 0;
		int i;

		for (i = n; i > 0; i--)
			sum += i;

		return sum;
	}

	// Berechnung mit rekursiver Funktion
	synchronized static int sum_n_rekursiv(int n) {
		if (n == 0)
			return 0;

		return n + sum_n_rekursiv(n - 1);
	}

	// Berechnung durch Formel
	private int sum_n_formel(int n) {
		synchronized (this) {
			return n * (n + 1) / 2;
		}
	}

	public static void main(String[] args) {
		long t1, t0, it = 0;
		int s = 0;
		int r = 0;
		Sync sync=new Sync();

		synchronized (Sync.class) {
			System.out.printf("Aufsummieren in einer Schleife\n");
			t0 = System.currentTimeMillis();
			r = sync.sum_n_loop(N);
			t1 = System.currentTimeMillis();
			System.out.printf("r=%d\t dt=%d ms (%d)\n", r, t1 - t0 - it, s);
		}

		System.out.printf("Berechnung mit rekursiver Funktion\n");
		t0 = System.currentTimeMillis();
		r = sum_n_rekursiv(N);
		t1 = System.currentTimeMillis();
		System.out.printf("r=%d\t dt=%d ms (%d)\n", r, t1 - t0 - it, s);

		System.out.printf("Berechnung durch Formel\n");
		t0 = System.currentTimeMillis();
		r = sync.sum_n_formel(N);
		t1 = System.currentTimeMillis();
		System.out.printf("r=%d\t dt=%d ms (%d)\n", r, t1 - t0 - it, s);

	}
}
