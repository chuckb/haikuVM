package haikuvm.bench;
/*
  Fibonacci.java
 */

public class FibonacciFloat {
//	static long flops;
  static float fib(float n) {
    if(n < 2) 
      return 1;
    else {
//    	flops+=3;
      return fib(n-2) + fib(n-1);
    }
  } 

  public static void main(String[] args) {
    for(float f=0;f<=20;f++)
      System.out.println("Fibonacci of "+f+" is "+fib(f));
//    System.out.println("Flops "+flops);
  }
}

     
