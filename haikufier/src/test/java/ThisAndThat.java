import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.Arrays;
import java.util.Formatter;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;


public class ThisAndThat {
	
    private static void findScale(int prescale, long f_cpu) {
        double ticksPerMillis=f_cpu/(prescale*256.0*1000.0);
        double b=(128.0*16000000*prescale)/(125.0*f_cpu*64);
        double best=Double.MAX_VALUE;
        
        int dividend=1;
        int divisor=1;
        for (int i = 1; i< 4000; i++) {
            for (int j = 1; j< 4000; j++) {
                double t=ticksPerMillis*i/j;
                double err=t-1;
                if (Math.abs(best)>Math.abs(err)) {
                    best=err;
                    dividend=i;
                    divisor=j;
                }
            }
        }
        //System.out.printf("[%4d] %8d: %d / %d = %f (%f %f) error=%f\n", prescale, f_cpu, dividend, divisor, 1.0*dividend/divisor, b, 16000000.0/f_cpu, best);
        long turn32=0x100000000L/Math.max(1, divisor);
        long turn64=(long)(Math.pow(2, 64)/Math.max(1, divisor));
        if (prescale==1) {
            System.out.printf("| %d | %d| %d | %d |%g | %g | %g |\n", f_cpu, prescale, dividend, divisor, 1000*best, turn32/(1000.0*60), 1.0*dividend/ divisor);
        } else {
            System.out.printf("|~| %d| %d | %d |%g | %g | %g |\n", prescale, dividend, divisor, 1000*best, turn32/(1000.0*60), 1.0*dividend/ divisor);
        }
    }

    private static void prim(long p) {
        for (long i = 2; i*i < p; i++) {
            if (p%i==0) {
                System.out.print(i+", ");
                prim(p/i);
                return;
            }
        }
        System.out.println(p);
    }


	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
	    
        byte aByte = (byte)0xef;
        int a= aByte & 0xff;
        int b=0x70;
        if (a>b) {
            System.out.println("as byte "+aByte+" or as int "+ (aByte & 0xff));
        }
//      prim(13711253375L); //(375 + 137)/375 
//      prim(3752742375L); //(375 + 137)/375 
//      prim(8715L); //(375 + 137)/375 
//      System.out.printf("| Hz | prescale | DIVIDEND | DIVISOR | Error in ms/s | Overflow32 [min] | resolution [ms] |\n");
//      findScale(8000000);
//      findScale(12000000);
//      findScale(15000000);
//      findScale(16000000);
//      findScale(20000000);
	}

	/**
	 *        1 |  1uS 
              8 |  8uS 
             64 | 64uS 
            256 | 256uS 
           1024 | 1024uS 
	 * @param f_cpu
	 */
    private static void findScale(int f_cpu) {
        findScale(1, f_cpu);
        findScale(8, f_cpu);
        findScale(64, f_cpu);
        findScale(256, f_cpu);
        findScale(1024, f_cpu);
        System.out.printf("||||||||\n");
    }
}
