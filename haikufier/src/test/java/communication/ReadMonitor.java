package communication;

import java.io.IOException;
import java.io.InputStream;

import base.JUnitSetUp.SerialReader;
import base.JUnitSetUp.SerialWriter;

public class ReadMonitor {

    private static InputStream in;
    private static int[] data = new int[100];
    private static int[] used = new int[100];
    private static int oldtime;
    private static int robotTime;
    private static long myTimeBase;

    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        int ms=0;
        double values_sec= -1;
        long t0=System.currentTimeMillis(), t1=0;
        int n=0;
        Communication con = new Communication();
        con.connect("COM17", 57600);
        in = con.getInputStream();
        String line;
        for (int i = 0; (line=readLine())!=null; i++) {
            int n0=0;
            //System.out.println(line);
            String[] token = line.split("[\\s]+");
            try {
                data[0]=token.length/2;
                used[0]++;
                for (int j = 0; j < token.length; j+=2) {
                    int idx = Integer.parseInt(token[j]);
                    int value = Integer.parseInt(token[j+1]);
                    data[idx]=value;
                    used[idx]++;
                    n++;
                    n0++;
                    if (idx==91) ms+=value;
                }
                consolidate(data);
                for (int j = 0; j < data.length; j++) {
                    if (used[j]>0) {
                        System.out.printf("(%02d,%5d)    ", j, data[j]);
                    }
                }
                t1=System.currentTimeMillis();
                if (t1>t0) {
                    if (i<10) values_sec=1000.0*n0/(t1-t0);
                    values_sec = 0.95 * values_sec + 0.05 * 1000.0*n0/(t1-t0);
                }
                //t0=t1; System.out.printf("values=%d, mavg=%f [values/sec] ms=%d\n", n, values_sec, ms);
                System.out.printf("values=%d, avg=%f [values/sec] ms=%d\n", n, 1000.0*n/(System.currentTimeMillis()-t0), ms);
            } catch (NumberFormatException e) {
                System.out.println(line);
                ms=0;
                t0=System.currentTimeMillis();
                n=0;
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println(line);
                ms=0;
                t0=System.currentTimeMillis();
                n=0;
            }
        }
    }

    private static void consolidate(int[] data) {
        if ((oldtime&0x8000)!=0 &&  (data[9] & 0x8000)==0) {
            robotTime+=0x10000;
        }
        robotTime = (robotTime & 0xffff0000) | (data[9]&0xffff);
        oldtime=data[9];
        System.out.printf("%8d: ", robotTime);
        if (robotTime<10000) {
            myTimeBase=System.currentTimeMillis()-robotTime;
        }
        System.out.printf("%8d: ", System.currentTimeMillis()-myTimeBase-robotTime);
        System.out.printf("h=%5.1f ", 180*Math.atan2(0.0001*data[32], 0.0001*data[31])/Math.PI);
    }

    private static String readLine() throws IOException {
        String res="";
        int c;
        while((c=in.read())!='\n') {
            if (c!='\r') res+=(char)c;
        }
        return res;
    }

}
