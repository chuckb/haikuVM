package haikuvm.bench;

/**
 * Example taken and adapted from
 * http://docs.oracle.com/javase/tutorial/java/javaOO/enum.html
 * 
 * Expected output is:

Mondays are bad.
Midweek days are so-so.
Fridays are better.
Weekends are best.
Weekends are best.
 *
 */
public class Clone {
    int a;
    int b;
    
    public Clone(int i, int j) {
       a=i;
       b=j;
    }

    public String toString() {
        return "("+a+", "+b+")";
    }
    
    public static void main(String[] args) {
        System.out.println("Begin");
        Clone c0 = new Clone(1, 3);
        System.out.println(c0);

        Clone c1=null;
        try {
            c1 = (Clone) c0.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("CloneNotSupportedException");
        } catch (Exception e) {
            System.out.println("Exception");
        }
        c0.b=30;
        System.out.println(c0);
        System.out.println(c1);
        
        int[] v0 = {8, 9};
        int[] v1 = v0.clone();
        
        v0[1]=40;
        System.out.println(v0[1]);
        System.out.println(v1[1]);
         

        System.out.println("Done");
    }
}
