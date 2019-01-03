package haikuvm.bench;
public class CodeVergleich {
	static final int T = 260;
	static final int N = 20;

	/*
	http://www.rn-wissen.de/index.php/Codevergleich_AVR-Compiler

	Summe der ersten n Zahlen 
	Berechnet wird die Summe der ersten n Zahlen: 
	Die Zahl n wird als 16-Bit Zahl angegeben und das Ergebnis als 16-Bit-Zahl berechnet. 
	Ein eventueller Überlauf wird nicht beachtet.
	 
	Der Code wird jeweils als eigene Funktion implementiert, um Abhängigkeiten vom umliegenden Code zu vermeiden.
	 
	Für diese Berechnung gibt es mehrere Möglichkeiten:
	*/


	//Aufsummieren in einer Schleife 
	static int sum_n_loop (int n)
	{
	   int sum = 0;
	   int i;

	   for (i=n; i > 0; i--)
	      sum += i;
		
	   return sum;	
	}
	 
	//Berechnung mit rekursiver Funktion 
	static int sum_n_rekursiv (int n)
	{
	   if (n == 0)
	      return 0;

	   return n + sum_n_rekursiv (n-1);	
	}
	 
	//Berechnung durch Formel 
	static int sum_n_formel (int n)
	{
	   return n*(n+1) / 2;
	}
	 
	public static void main(String[] args) {
		long t1, t0, it=0;
		int i,j,s=0;
		int r=0;

		System.out.printf("intra loop\n");
		t0=System.currentTimeMillis();
		for(i=0; i<T; i++) {
			for(j=0; j<T; j++) {
				r=j;
				s+=i+j+r;
			}
		}
		t1=System.currentTimeMillis();
		System.out.printf("r=%d\t dt=%d ms (%d)\n", r, t1-t0-it, s);
		it=t1-t0;


		System.out.printf("Aufsummieren in einer Schleife\n");
		t0=System.currentTimeMillis();
		for(i=0; i<T; i++) {
			for(j=0; j<T; j++) {
				r=sum_n_loop(N);
				s+=i+j+r;
			}
		}
		t1=System.currentTimeMillis();
		System.out.printf("r=%d\t dt=%d ms (%d)\n", r, t1-t0-it, s);


		System.out.printf("Berechnung mit rekursiver Funktion\n");
		t0=System.currentTimeMillis();
		for(i=0; i<T; i++) {
			for(j=0; j<T; j++) {
				r=sum_n_rekursiv(N);
				s+=i+j+r;
			}
		}
		t1=System.currentTimeMillis();
		System.out.printf("r=%d\t dt=%d ms (%d)\n", r, t1-t0-it, s);


		System.out.printf("Berechnung durch Formel\n");
		t0=System.currentTimeMillis();
		for(i=0; i<T; i++) {
			for(j=0; j<T; j++) {
				r=sum_n_formel(j); //(j nicht N ! Damit sum_n_formel von C nicht weg optimiert wird)
				s+=i+j+r;
			}
		}
		t1=System.currentTimeMillis();
		System.out.printf("r=%d\t dt=%d ms (%d)\n", r, t1-t0-it, s);

	}
}
