package haikuvm.bench;


/**
 *
 * http://lejos.sourceforge.net/forum/viewtopic.php?f=7&t=2989
 *
 * But I ran it anyway in Java the total time taken for
 * up to and including fib 30 is 197566, the
 * same code in gcc based C takes 3432ms,
 * so the native C code in this instance is 57 times faster.
 *
 * @author genom2
 *
 * haikuVM 32/64 Release (pc14)
Fibonacci of 0 is 1 in 0 ms
Fibonacci of 1 is 1 in 0 ms
Fibonacci of 2 is 2 in 16 ms
Fibonacci of 3 is 3 in 16 ms
Fibonacci of 4 is 5 in 16 ms
Fibonacci of 5 is 8 in 16 ms
Fibonacci of 6 is 13 in 16 ms
Fibonacci of 7 is 21 in 16 ms
Fibonacci of 8 is 34 in 32 ms
Fibonacci of 9 is 55 in 32 ms
Fibonacci of 10 is 89 in 32 ms
Fibonacci of 11 is 144 in 32 ms
Fibonacci of 12 is 233 in 32 ms
Fibonacci of 13 is 377 in 47 ms
Fibonacci of 14 is 610 in 47 ms
Fibonacci of 15 is 987 in 47 ms
Fibonacci of 16 is 1597 in 47 ms
Fibonacci of 17 is 2584 in 47 ms
Fibonacci of 18 is 4181 in 47 ms
Fibonacci of 19 is 6765 in 63 ms
Fibonacci of 20 is 10946 in 63 ms
Fibonacci of 21 is 17711 in 79 ms
Fibonacci of 22 is 28657 in 79 ms
Fibonacci of 23 is 46368 in 110 ms
Fibonacci of 24 is 75025 in 125 ms
Fibonacci of 25 is 121393 in 172 ms
Fibonacci of 26 is 196418 in 235 ms
Fibonacci of 27 is 317811 in 344 ms
Fibonacci of 28 is 514229 in 500 ms
Fibonacci of 29 is 832040 in 766 ms
Fibonacci of 30 is 1346269 in 1204 ms
Elapsed time 1204 msec

JAVA 1.6 -Xint (pc14)
Fibonacci of 0 is 1 in 0 ms
Fibonacci of 1 is 1 in 0 ms
Fibonacci of 2 is 2 in 0 ms
Fibonacci of 3 is 3 in 0 ms
Fibonacci of 4 is 5 in 0 ms
Fibonacci of 5 is 8 in 0 ms
Fibonacci of 6 is 13 in 0 ms
Fibonacci of 7 is 21 in 0 ms
Fibonacci of 8 is 34 in 0 ms
Fibonacci of 9 is 55 in 0 ms
Fibonacci of 10 is 89 in 0 ms
Fibonacci of 11 is 144 in 0 ms
Fibonacci of 12 is 233 in 0 ms
Fibonacci of 13 is 377 in 0 ms
Fibonacci of 14 is 610 in 0 ms
Fibonacci of 15 is 987 in 0 ms
Fibonacci of 16 is 1597 in 0 ms
Fibonacci of 17 is 2584 in 0 ms
Fibonacci of 18 is 4181 in 16 ms
Fibonacci of 19 is 6765 in 16 ms
Fibonacci of 20 is 10946 in 16 ms
Fibonacci of 21 is 17711 in 16 ms
Fibonacci of 22 is 28657 in 16 ms
Fibonacci of 23 is 46368 in 31 ms
Fibonacci of 24 is 75025 in 47 ms
Fibonacci of 25 is 121393 in 62 ms
Fibonacci of 26 is 196418 in 94 ms
Fibonacci of 27 is 317811 in 156 ms
Fibonacci of 28 is 514229 in 250 ms
Fibonacci of 29 is 832040 in 406 ms
Fibonacci of 30 is 1346269 in 656 ms

 */

/**
revision 180
haikuVM 32/64 Release (pc14)
Fibonacci of 0 is 1 in 0 ms
Fibonacci of 1 is 1 in 0 ms
Fibonacci of 2 is 2 in 0 ms
Fibonacci of 3 is 3 in 0 ms
Fibonacci of 4 is 5 in 0 ms
Fibonacci of 5 is 8 in 0 ms
Fibonacci of 6 is 13 in 16 ms
Fibonacci of 7 is 21 in 16 ms
Fibonacci of 8 is 34 in 16 ms
Fibonacci of 9 is 55 in 16 ms
Fibonacci of 10 is 89 in 31 ms
Fibonacci of 11 is 144 in 31 ms
Fibonacci of 12 is 233 in 31 ms
Fibonacci of 13 is 377 in 31 ms
Fibonacci of 14 is 610 in 31 ms
Fibonacci of 15 is 987 in 47 ms
Fibonacci of 16 is 1597 in 47 ms
Fibonacci of 17 is 2584 in 47 ms
Fibonacci of 18 is 4181 in 63 ms
Fibonacci of 19 is 6765 in 63 ms
Fibonacci of 20 is 10946 in 63 ms
Fibonacci of 21 is 17711 in 78 ms
Fibonacci of 22 is 28657 in 94 ms
Fibonacci of 23 is 46368 in 109 ms
Fibonacci of 24 is 75025 in 125 ms
Fibonacci of 25 is 121393 in 172 ms
Fibonacci of 26 is 196418 in 219 ms
Fibonacci of 27 is 317811 in 313 ms
Fibonacci of 28 is 514229 in 469 ms
Fibonacci of 29 is 832040 in 703 ms
Fibonacci of 30 is 1346269 in 1078 ms
Elapsed time 1094 msec (Average. Minimum was: Elapsed time 860 msec)

revision 709
haikuVM 32/32 Release
here with HAIKU_AOTBytecodeAsJump fastest
#HAIKU_AOTThreadedAsCall
#HAIKU_AOTBytecodeAsSwitch slowest
########## RCX
##########  Date: 2015-10-31 23:21:05.435
##########  haikuvm.bench.Fibonacci32Bit
Fibonacci of 0 is 1 in 72 ms
Fibonacci of 1 is 1 in 474 ms
Fibonacci of 2 is 2 in 899 ms
Fibonacci of 3 is 3 in 1413 ms
Fibonacci of 4 is 5 in 1901 ms
Fibonacci of 5 is 8 in 2460 ms
Fibonacci of 6 is 13 in 2959 ms
Fibonacci of 7 is 21 in 3581 ms
Fibonacci of 8 is 34 in 4075 ms
Fibonacci of 9 is 55 in 4666 ms
Fibonacci of 10 is 89 in 5312 ms
Fibonacci of 11 is 144 in 5974 ms
Fibonacci of 12 is 233 in 6759 ms
Fibonacci of 13 is 377 in 7590 ms
Fibonacci of 14 is 610 in 8482 ms
Fibonacci of 15 is 987 in 9720 ms
Fibonacci of 16 is 1597 in 11165 ms
Fibonacci of 17 is 2584 in 13340 ms
Fibonacci of 18 is 4181 in 16296 ms
Fibonacci of 19 is 6765 in 20793 ms
Fibonacci of 20 is 10946 in 27679 ms
Fibonacci of 21 is 17711 in 38206 ms
Fibonacci of 22 is 28657 in 54943 ms
Fibonacci of 23 is 46368 in 81469 ms
Fibonacci of 24 is 75025 in 124156 ms
Fibonacci of 25 is 121393 in 192829 ms
Fibonacci of 26 is 196418 in 303488 ms
Fibonacci of 27 is 317811 in 481966 ms
Fibonacci of 28 is 514229 in 770393 ms
Fibonacci of 29 is 832040 in 1236589 ms
Fibonacci of 30 is 1346269 in 1990471 ms
##########  elapsed time: 1994421 ms
 */
public class Fibonacci32Bit {
  static int fib(int n) {
    if(n < 2)
      return 1;
    else
      return fib(n-2) + fib(n-1);
  }

  public static void main(String[] args) {
	long t0 = System.currentTimeMillis();
    for(int i=0;i<=30;i++)
      System.out.println("Fibonacci of "+i+" is "+fib(i)+" in "+(System.currentTimeMillis()-t0)+" ms");
  }
}


