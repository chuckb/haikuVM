package analysis;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadData {
    private static int[] data = new int[100];
    private static int[] dataMin = new int[100];
    private static int[] dataMax = new int[100];
    private static int[] used = new int[100];
    private static int[] dataTrans = null;
    private static int[] usedTrans = null;
    private static int robotTime;
    private static int oldtime;
    private static long myTimeBase;
    
    public static void read(String expected) throws IOException {
        System.out.println("begin");
        for (int j = 0; j < data.length; j++) {
            dataMax[j]=Integer.MIN_VALUE;
            dataMin[j]=Integer.MAX_VALUE;
        }

        long t0=System.currentTimeMillis(), t1=0;
        int n=0;
        int nTrans=0;
        BufferedReader f0 = new BufferedReader(new FileReader(expected));
        String line;
        for(int i=1; (line=f0.readLine())!=null; i++) {
            dataTrans=data.clone();
            usedTrans=used.clone();
            nTrans=n;
            //System.out.println(line);
            String[] token0 = line.split("[\\s]+");
            try {
                String[] token = new String[token0.length-3];
                System.arraycopy(token0, 3, token, 0, token.length);
                data[0]=token.length/2;
                used[0]++;
                for (int j = 0; j < token.length; j+=2) {
                    int idx = Integer.parseInt(token[j]);
                    int value = Integer.parseInt(token[j+1]);
                    data[idx]=value;
                    used[idx]++;
                    if (used[idx]>10) {
                        if (dataMax[idx]<data[idx]) dataMax[idx]=data[idx]; 
                        if (dataMin[idx]>data[idx]) dataMin[idx]=data[idx]; 
                    }
                    n++;
                }
                consolidate(data);
                for (int j = 0; j < data.length; j++) {
                    if (used[j]>0) {
                        System.out.printf("(%02d,%5d)    ", j, data[j]);
                    }
                }
                t1=System.currentTimeMillis();
                //t0=t1; System.out.printf("values=%d, mavg=%f [values/sec] ms=%d\n", n, values_sec, ms);
                System.out.printf("values=%d, avg=%f [values/sec]\n", n, 1000.0*n/(System.currentTimeMillis()-t0));
            } catch (NumberFormatException e) {
                used=usedTrans;
                data=dataTrans;
                n=nTrans;
                System.out.println(line);
            } catch (ArrayIndexOutOfBoundsException e) {
                used=usedTrans;
                data=dataTrans;
                n=nTrans;
                System.out.println(line);
            } catch (NegativeArraySizeException e) {
                used=usedTrans;
                data=dataTrans;
                n=nTrans;
                System.out.println(line);
            } finally {
                t0=System.currentTimeMillis();
            }
        }
        f0.close();
        for (int j = 0; j < data.length; j++) {
            if (used[j]>10) {
                System.out.printf("(%02d,%6d)    ", j, dataMin[j]);
            }
        }
        System.out.println();
        for (int j = 0; j < data.length; j++) {
            if (used[j]>10) {
                System.out.printf("(%02d,%6d)    ", j, dataMax[j]);
            }
        }
        System.out.println();
    }

    public static void excel(String filename) throws IOException {
        long oldtr=0;
        BufferedReader f0 = new BufferedReader(new FileReader(filename));
        String line;
        for(int i=1; (line=f0.readLine())!=null; i++) {
            //System.out.println(line);
            String[] token0 = line.split("[\\s]+");
            try {
                String[] token = new String[token0.length-3];
                System.arraycopy(token0, 3, token, 0, token.length);
                data[0]=token.length/2;
                used[0]++;
                for (int j = 0; j < token.length; j+=2) {
                    int idx = Integer.parseInt(token[j]);
                    int value = Integer.parseInt(token[j+1]);
                    data[idx]=value;
                    used[idx]++;
                    if (used[idx]>10) {
                        if (dataMax[idx]<data[idx]) dataMax[idx]=data[idx]; 
                        if (dataMin[idx]>data[idx]) dataMin[idx]=data[idx]; 
                    }
                }
                long tr = getRobotTime(data);
                if (tr/1000>oldtr/1000) {
                    oldtr=tr;
//                    for (int idx : new int[]{60, 63, 64, 99}) {
                        for (int idx : new int[]{12, 13, 81}) {
//                        for (int idx : new int[]{80}) {
//                    for (int idx : new int[]{31, 32, 63, 72}) {
//                    for (int idx : new int[]{70, 71}) {
                        switch (idx) {
                        case 80:
                            switch (data[idx]) {
                            case '\r': case '\n': break;
                            case -1: 
                                System.out.printf("-\t");
                                break;
                            case 1: 
                                System.out.printf("Red\t");
                                break;
                            case 2: 
                                System.out.printf("Blue\t");
                                break;
                            default: 
                                System.out.printf("%c\t", (char)data[idx]);
                            }
                        default:
                            System.out.printf("%d\t", data[idx]);
                        }
                    }
                    System.out.printf("\n");
                }
            } catch (NumberFormatException e) {
            } catch (ArrayIndexOutOfBoundsException e) {
            } catch (NegativeArraySizeException e) {
            }
        }
        f0.close();
    }

    private static void consolidate(int[] data) {
        getRobotTime(data);
        System.out.printf("%8d: ", robotTime);
        if (robotTime<10000) {
            myTimeBase=System.currentTimeMillis()-robotTime;
        }
        System.out.printf("%8d: ", System.currentTimeMillis()-myTimeBase-robotTime);
        System.out.printf("h=%5.1f ", 180*Math.atan2(0.0001*data[32], 0.0001*data[31])/Math.PI);
    }

    private static long getRobotTime(int[] data) {
        if ((oldtime&0x8000)!=0 &&  (data[9] & 0x8000)==0) {
            robotTime+=0x10000;
        }
        robotTime = (robotTime & 0xffff0000) | (data[9]&0xffff);
        oldtime=data[9];
        return robotTime;
    }

    public static void main ( String[] args ) throws Exception
    {
        //read("//genom2/C/robot/bin/logger55904.1.out");
        read("//169.254.203.237/C/robot/bin/logger18704.1.out");
    }
}
