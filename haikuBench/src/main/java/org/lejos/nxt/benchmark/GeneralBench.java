package org.lejos.nxt.benchmark;

import java.io.PrintStream;


/**
 * @author Bruce Boyes, based on code from Imsys (www.imsys.se)
 * 
 * @version 1.2
 * 
 *          <ul>
 *          <li>1.2 2009 Jul 27 skoehler: changes all over the place
 *          <li>1.1a 2003 Feb 19 bboyes adding byte math
 *          <li>1.0f 2003 Feb 18 bboyes making float and double more realistic,
 *          with actual float and double operands
 *          <li>1.0e 2003 Feb 18 bboyes total time and operations reporting
 *          <li>1.0d 2003 Feb 18 bboyes made int test better by using an int
 *          operand, added ops per second to report method.
 *          <li>1.0c 2003 Feb 17 bboyes attempting to improve math tests.
 *          Integer divide was just 3/3 then 1/1 forever, not very interesting.
 *          Using the iteration index in the test.
 *          <li>1.0 2003 Feb 08 bboyes Cleaning up this code a bit and fixing
 *          some problems such as a bug in arrayPerformance() which made byte
 *          and int array copies identical. Running on JStamp.
 *          <ul>
 *          <hr>
 *          This code is based on BenchMark.java from the Imsys SNAP examples.
 */
public class GeneralBench
{
	public GeneralBench()
	{
		// nothing
	}

	private void dummy1()
	{
		// dummy method
		return;
	}

	private static void dummy2()
	{
		// dummy method
		return;
	}

	private static final String VERSION = "1.2";
	private static final int[] PADVEC = { 8, 30, 6, 10 };

	/**
	 * Print out the time it took to complete a task Also calculate the rate per
	 * second = (count * 1000) / time <br>
	 * where time is in msec, hence the factor of 1000
	 * 
	 * @param count
	 *            How many iterations of the task
	 * @param task
	 *            String description of the task
	 * @param count2
	 *            How many items of unit
	 * @param unit
	 *            String description of the unit
	 * @param time
	 *            How many msec it took
	 */
	private static void report(long count, String task, long count2, String unit, long time)
	{
		BenchUtils.report(count, task, count2, unit, time, PADVEC);
	}
	/**
	 * 
	 * 
	 *
	 * <hr>
	 * <pre>
http://lejos.sourceforge.net/forum/viewtopic.php?f=7&t=2900&p=14357&hilit=performance+jvm#p14357

I would suggest float instead of double, although we had this discussion on the mailing list, where writing "1.2f" instead of "1.2" was considered a major drawback. 
(The performance drawback of float is not as huge as for double).
Code: Select all
 200000 int add:                         648 ms     308641 ops/sec
 200000 int sub:                         662 ms     302114 ops/sec
 200000 int mul:                         680 ms     294117 ops/sec
 200000 int div:                         941 ms     212539 ops/sec
 200000 float add:                       871 ms     229621 ops/sec
 200000 float sub:                       879 ms     227531 ops/sec
 200000 float mul:                      1028 ms     194552 ops/sec
 200000 float div:                      1868 ms     107066 ops/sec
 200000 double add:                     1723 ms     116076 ops/sec
 200000 double sub:                     1790 ms     111731 ops/sec
 200000 double mul:                     1678 ms     119189 ops/sec
 200000 double div:                     6090 ms      32840 ops/sec 
(Note, still old results from 2009. Will run the benchmark again, when I have the chance)
Also, we should teach our developers to avoid divisions (use x*0.1f instead of x/10f for example), if we go for float.

<hr>
Results from Arduino (09.11.11) with
#define MINSTACK 85
#define HEAPSIZE 1300 // 550 -> fib(20) alive
 
GeneralBench 1.2
IterationTime= 13207 msec
  200000 byte add:                      12592 ms      15883 ops/sec
  200000 byte sub:                      12390 ms      16142 ops/sec
  200000 byte mul:                      13172 ms      15183 ops/sec
  200000 byte div:                      20054 ms       9973 ops/sec
IterationTime= 13208 msec
  200000 short add:                     12591 ms      15884 ops/sec
  200000 short sub:                     12389 ms      16143 ops/sec
  200000 short mul:                     13171 ms      15184 ops/sec
  200000 short div:                     20203 ms       9899 ops/sec
IterationTime= 13206 msec
  200000 char add:                      12593 ms      15881 ops/sec
  200000 char sub:                      12391 ms      16140 ops/sec
  200000 char mul:                      13172 ms      15183 ops/sec
  200000 char div:                      20054 ms       9973 ops/sec
IterationTime= 13207 msec
  200000 int add:                       11896 ms      16812 ops/sec
  200000 int sub:                       11694 ms      17102 ops/sec
  200000 int mul:                       12476 ms      16030 ops/sec
  200000 int div:                       19509 ms      10251 ops/sec
IterationTime= 13208 msec
  200000 long add:                      14265 ms      14020 ops/sec
  200000 long sub:                      14278 ms      14007 ops/sec
  200000 long mul:                      23869 ms       8379 ops/sec
  200000 long div:                      20908 ms       9565 ops/sec
IterationTime= 13207 msec
  200000 float add:                     23580 ms       8481 ops/sec
  200000 float sub:                     23946 ms       8352 ops/sec
  200000 float mul:                     36071 ms       5544 ops/sec
  200000 float div:                     29176 ms       6854 ops/sec
IterationTime= 13207 msec
  200000 double add:                    23832 ms       8392 ops/sec
  200000 double sub:                    24199 ms       8264 ops/sec
  200000 double mul:                    36322 ms       5506 ops/sec
  200000 double div:                    29428 ms       6796 ops/sec
IterationTime= 13207 msec
  200000 method calls:                  14654 ms      13648 ops/sec
IterationTime= 13208 msec
  200000 static method calls:           11108 ms      18005 ops/sec
IterationTime= 13208 msec
  200000 native static method calls:        1 ms  200000000 ops/sec
IterationTime= 132 msec
<hr>
#define HEAPSIZE 3000

GeneralBench 1.2
IterationTime= 9 msec
  200000 byte add:                         22 ms    9090909 ops/sec
  200000 byte sub:                          7 ms   28571428 ops/sec
  200000 byte mul:                          7 ms   28571428 ops/sec
  200000 byte div:                          7 ms   28571428 ops/sec
IterationTime= 10 msec
  200000 short add:                        21 ms    9523809 ops/sec
  200000 short sub:                         6 ms   33333333 ops/sec
  200000 short mul:                         5 ms   40000000 ops/sec
  200000 short div:                        22 ms    9090909 ops/sec
IterationTime= 13 msec
  200000 char add:                          3 ms   66666666 ops/sec
  200000 char sub:                         18 ms   11111111 ops/sec
  200000 char mul:                         18 ms   11111111 ops/sec
  200000 char div:                         18 ms   11111111 ops/sec
IterationTime= 13 msec
  200000 int add:                          19 ms   10526315 ops/sec
  200000 int sub:                           2 ms  100000000 ops/sec
  200000 int mul:                           2 ms  100000000 ops/sec
  200000 int div:                          19 ms   10526315 ops/sec
IterationTime= 13 msec
  200000 long add:                          2 ms  100000000 ops/sec
  200000 long sub:                         19 ms   10526315 ops/sec
  200000 long mul:                         18 ms   11111111 ops/sec
  200000 long div:                         34 ms    5882352 ops/sec
IterationTime= 11 msec
  200000 float add:                         5 ms   40000000 ops/sec
  200000 float sub:                         5 ms   40000000 ops/sec
  200000 float mul:                         5 ms   40000000 ops/sec
  200000 float div:                        20 ms   10000000 ops/sec
IterationTime= 11 msec
  200000 double add:                      130 ms    1538461 ops/sec
  200000 double sub:                       83 ms    2409638 ops/sec
  200000 double mul:                      114 ms    1754385 ops/sec
  200000 double div:                       98 ms    2040816 ops/sec
IterationTime= 13 msec
  200000 method calls:                     18 ms   11111111 ops/sec
IterationTime= 13 msec
  200000 static method calls:              18 ms   11111111 ops/sec
IterationTime= 13 msec
  200000 native static method calls:       18 ms   11111111 ops/sec
IterationTime= 0 msec
    2000 string concats:                  140 ms      14285 ops/sec
IterationTime= 1 msec
   20000 string compares (easy):           14 ms    1428571 ops/sec
IterationTime= 0 msec
    1000 string compares (hard):           16 ms      62500 ops/sec
IterationTime= 1 msec
   20000 object creations:                 14 ms    1428571 ops/sec
 6243000 Total Loop Executions:         13593 ms     459280 loops/sec
Note: each Loop Execution includes multiple Java operations


GeneralBench 1.2
IterationTime= 1188 msec
20000000 byte add:                       1281 ms   15612802 ops/sec
20000000 byte sub:                        953 ms   20986358 ops/sec
20000000 byte mul:                       1265 ms   15810276 ops/sec
20000000 byte div:                       1375 ms   14545454 ops/sec
IterationTime= 1204 msec
20000000 short add:                      1046 ms   19120458 ops/sec
20000000 short sub:                      1109 ms   18034265 ops/sec
20000000 short mul:                      1280 ms   15625000 ops/sec
20000000 short div:                       984 ms   20325203 ops/sec
IterationTime= 1209 msec
20000000 char add:                       1104 ms   18115942 ops/sec
20000000 char sub:                       1275 ms   15686274 ops/sec
20000000 char mul:                        869 ms   23014959 ops/sec
20000000 char div:                       1322 ms   15128593 ops/sec
IterationTime= 1221 msec
20000000 int add:                        1076 ms   18587360 ops/sec
20000000 int sub:                         638 ms   31347962 ops/sec
20000000 int mul:                        1076 ms   18587360 ops/sec
20000000 int div:                        1841 ms   10863661 ops/sec
IterationTime= 1205 msec
20000000 long add:                        842 ms   23752969 ops/sec
20000000 long sub:                       1264 ms   15822784 ops/sec
20000000 long mul:                       1138 ms   17574692 ops/sec
20000000 long div:                       3670 ms    5449591 ops/sec
IterationTime= 1211 msec
20000000 float add:                       992 ms   20161290 ops/sec
20000000 float sub:                      1071 ms   18674136 ops/sec
20000000 float mul:                      1351 ms   14803849 ops/sec
20000000 float div:                      1367 ms   14630577 ops/sec
IterationTime= 1212 msec
20000000 double add:                     9757 ms    2049810 ops/sec
20000000 double sub:                     6835 ms    2926115 ops/sec
20000000 double mul:                     9491 ms    2107259 ops/sec
20000000 double div:                     7882 ms    2537427 ops/sec
IterationTime= 1244 msec
20000000 method calls:                    974 ms   20533880 ops/sec
IterationTime= 1223 msec
20000000 static method calls:             918 ms   21786492 ops/sec
IterationTime= 1224 msec
20000000 native static method calls:      729 ms   27434842 ops/sec
IterationTime= 13 msec
  200000 string concats:                17394 ms      11498 ops/sec
IterationTime= 110 msec
 2000000 string compares (easy):         1640 ms    1219512 ops/sec
IterationTime= 7 msec
  100000 string compares (hard):         2227 ms      44903 ops/sec
IterationTime= 121 msec
 2000000 object creations:                801 ms    2496878 ops/sec
624300000 Total Loop Executions:        1365906 ms     457059 loops/sec
Note: each Loop Execution includes multiple Java operations


GeneralBench 1.2
IterationTime= 12147 msec
200000000 byte add:                      11071 ms   18065215 ops/sec
200000000 byte sub:                      11572 ms   17283097 ops/sec
200000000 byte mul:                      11056 ms   18089725 ops/sec
200000000 byte div:                      12056 ms   16589250 ops/sec
IterationTime= 12195 msec
200000000 short add:                     10992 ms   18195050 ops/sec
200000000 short sub:                     11321 ms   17666283 ops/sec
200000000 short mul:                     11227 ms   17814197 ops/sec
200000000 short div:                     12101 ms   16527559 ops/sec
IterationTime= 12203 msec
200000000 char add:                      11297 ms   17703815 ops/sec
200000000 char sub:                      11016 ms   18155410 ops/sec
200000000 char mul:                      11000 ms   18181818 ops/sec
200000000 char div:                      11672 ms   17135023 ops/sec
IterationTime= 12205 msec
200000000 int add:                        9529 ms   20988561 ops/sec
200000000 int sub:                        9467 ms   21126016 ops/sec
200000000 int mul:                        9280 ms   21551724 ops/sec
200000000 int div:                       17607 ms   11359118 ops/sec
IterationTime= 12113 msec
200000000 long add:                      10606 ms   18857250 ops/sec
200000000 long sub:                      10918 ms   18318373 ops/sec
200000000 long mul:                      13028 ms   15351550 ops/sec
200000000 long div:                      35027 ms    5709880 ops/sec
IterationTime= 12275 msec
200000000 float add:                      9412 ms   21249468 ops/sec
200000000 float sub:                      9663 ms   20697505 ops/sec
200000000 float mul:                     15287 ms   13083011 ops/sec
200000000 float div:                     11944 ms   16744809 ops/sec
IterationTime= 12300 msec
200000000 double add:                    96310 ms    2076627 ops/sec
200000000 double sub:                    65575 ms    3049942 ops/sec
200000000 double mul:                    94590 ms    2114388 ops/sec
200000000 double div:                    69950 ms    2859185 ops/sec
IterationTime= 12358 msec
200000000 method calls:                  12235 ms   16346546 ops/sec
IterationTime= 12172 msec
200000000 static method calls:           10109 ms   19784350 ops/sec
IterationTime= 12224 msec
200000000 native static method calls:     5979 ms   33450409 ops/sec
IterationTime= 124 msec
 2000000 string concats:               174454 ms      11464 ops/sec
IterationTime= 1241 msec
20000000 string compares (easy):        15728 ms    1271617 ops/sec
IterationTime= 62 msec
 1000000 string compares (hard):        21172 ms      47232 ops/sec
IterationTime= 1215 msec
20000000 object creations:               7582 ms    2637826 ops/sec
6243000000 Total Loop Executions:        13736453 ms     454484 loops/sec
Note: each Loop Execution includes multiple Java operations
<hr>
#define HEAPSIZE 300000
GeneralBench 1.2
IterationTime= 9 msec
  200000 byte add:                          6 ms   33333333 ops/sec
  200000 byte sub:                         22 ms    9090909 ops/sec
  200000 byte mul:                          7 ms   28571428 ops/sec
  200000 byte div:                          7 ms   28571428 ops/sec
IterationTime= 9 msec
  200000 short add:                         6 ms   33333333 ops/sec
  200000 short sub:                         7 ms   28571428 ops/sec
  200000 short mul:                         6 ms   33333333 ops/sec
  200000 short div:                         7 ms   28571428 ops/sec
IterationTime= 10 msec
  200000 char add:                          6 ms   33333333 ops/sec
  200000 char sub:                          5 ms   40000000 ops/sec
  200000 char mul:                          6 ms   33333333 ops/sec
  200000 char div:                          5 ms   40000000 ops/sec
IterationTime= 9 msec
  200000 int add:                           6 ms   33333333 ops/sec
  200000 int sub:                           7 ms   28571428 ops/sec
  200000 int mul:                           6 ms   33333333 ops/sec
  200000 int div:                          22 ms    9090909 ops/sec
IterationTime= 9 msec
  200000 long add:                          7 ms   28571428 ops/sec
  200000 long sub:                          6 ms   33333333 ops/sec
  200000 long mul:                          6 ms   33333333 ops/sec
  200000 long div:                         23 ms    8695652 ops/sec
IterationTime= 12 msec
  200000 float add:                         4 ms   50000000 ops/sec
  200000 float sub:                        19 ms   10526315 ops/sec
  200000 float mul:                        20 ms   10000000 ops/sec
  200000 float div:                        19 ms   10526315 ops/sec
IterationTime= 13 msec
  200000 double add:                      112 ms    1785714 ops/sec
  200000 double sub:                       80 ms    2500000 ops/sec
  200000 double mul:                      112 ms    1785714 ops/sec
  200000 double div:                       81 ms    2469135 ops/sec
IterationTime= 13 msec
  200000 method calls:                     19 ms   10526315 ops/sec
IterationTime= 12 msec
  200000 static method calls:               4 ms   50000000 ops/sec
IterationTime= 9 msec
  200000 native static method calls:        1 ms  200000000 ops/sec
IterationTime= 0 msec
    2000 string concats:                  297 ms       6734 ops/sec
IterationTime= 1 msec
   20000 string compares (easy):           14 ms    1428571 ops/sec
IterationTime= 0 msec
    1000 string compares (hard):           16 ms      62500 ops/sec
IterationTime= 1 msec
   20000 object creations:                452 ms      44247 ops/sec
 6243000 Total Loop Executions:         12750 ms     489647 loops/sec
Note: each Loop Execution includes multiple Java operations
<hr>
minimal heapsize for WIN32
#define MINSTACK 95
#define HEAPSIZE 1852
GeneralBench 1.2
IterationTime= 13 msec
  200000 byte add:                          3 ms   66666666 ops/sec
  200000 byte sub:                          3 ms   66666666 ops/sec
  200000 byte mul:                          3 ms   66666666 ops/sec
  200000 byte div:                         18 ms   11111111 ops/sec
IterationTime= 11 msec
  200000 short add:                        21 ms    9523809 ops/sec
  200000 short sub:                         4 ms   50000000 ops/sec
  200000 short mul:                         5 ms   40000000 ops/sec
  200000 short div:                        20 ms   10000000 ops/sec
IterationTime= 12 msec
  200000 char add:                          4 ms   50000000 ops/sec
  200000 char sub:                          3 ms   66666666 ops/sec
  200000 char mul:                         20 ms   10000000 ops/sec
  200000 char div:                         19 ms   10526315 ops/sec
IterationTime= 13 msec
  200000 int add:                          18 ms   11111111 ops/sec
  200000 int sub:                          18 ms   11111111 ops/sec
  200000 int mul:                           3 ms   66666666 ops/sec
  200000 int div:                          19 ms   10526315 ops/sec
IterationTime= 13 msec
  200000 long add:                         18 ms   11111111 ops/sec
  200000 long sub:                          2 ms  100000000 ops/sec
  200000 long mul:                          3 ms   66666666 ops/sec
  200000 long div:                         34 ms    5882352 ops/sec
IterationTime= 13 msec
  200000 float add:                         3 ms   66666666 ops/sec
  200000 float sub:                         3 ms   66666666 ops/sec
  200000 float mul:                        18 ms   11111111 ops/sec
  200000 float div:                        18 ms   11111111 ops/sec
IterationTime= 9 msec
  200000 double add:                       85 ms    2352941 ops/sec
  200000 double sub:                       84 ms    2380952 ops/sec
  200000 double mul:                      116 ms    1724137 ops/sec
  200000 double div:                       85 ms    2352941 ops/sec
IterationTime= 13 msec
  200000 method calls:                      3 ms   66666666 ops/sec
IterationTime= 13 msec
  200000 static method calls:              19 ms   10526315 ops/sec
IterationTime= 13 msec
  200000 native static method calls:        3 ms   66666666 ops/sec
IterationTime= 0 msec
    2000 string concats:                  204 ms       9803 ops/sec
IterationTime= 1 msec
   20000 string compares (easy):           14 ms    1428571 ops/sec
IterationTime= 0 msec
    1000 string compares (hard):           31 ms      32258 ops/sec
IterationTime= 1 msec
   20000 object creations:                 15 ms    1333333 ops/sec
 6243000 Total Loop Executions:         14016 ms     445419 loops/sec
Note: each Loop Execution includes multiple Java operations

01.03.2014 HaikuVM 1.2.1
GeneralBench 1.2
IterationTime= 1 msec
      61 byte[16384] manual copies:       142 ms    7038197 bytes/sec
IterationTime= 1 msec
   12207 byte[16384] arraycopies:          15 ms 13333299200 bytes/sec
IterationTime= 0 msec
     244 int[4096] manual copies:         132 ms   30285575 bytes/sec
IterationTime= 1 msec
   12207 int[4096] arraycopies:            13 ms 15384576000 bytes/sec
IterationTime= 114 msec
 2000000 byte add:                         61 ms   32786885 ops/sec
 2000000 byte sub:                         80 ms   25000000 ops/sec
 2000000 byte mul:                         95 ms   21052631 ops/sec
 2000000 byte div:                         66 ms   30303030 ops/sec
IterationTime= 108 msec
 2000000 short add:                        69 ms   28985507 ops/sec
 2000000 short sub:                        97 ms   20618556 ops/sec
 2000000 short mul:                        92 ms   21739130 ops/sec
 2000000 short div:                        74 ms   27027027 ops/sec
IterationTime= 113 msec
 2000000 char add:                         64 ms   31250000 ops/sec
 2000000 char sub:                         97 ms   20618556 ops/sec
 2000000 char mul:                        166 ms   12048192 ops/sec
 2000000 char div:                         87 ms   22988505 ops/sec
IterationTime= 108 msec
 2000000 int add:                          63 ms   31746031 ops/sec
 2000000 int sub:                          68 ms   29411764 ops/sec
 2000000 int mul:                          95 ms   21052631 ops/sec
 2000000 int div:                          69 ms   28985507 ops/sec
IterationTime= 115 msec
 2000000 long add:                        111 ms   18018018 ops/sec
 2000000 long sub:                        111 ms   18018018 ops/sec
 2000000 long mul:                        111 ms   18018018 ops/sec
 2000000 long div:                        151 ms   13245033 ops/sec
IterationTime= 108 msec
 2000000 float add:                        81 ms   24691358 ops/sec
 2000000 float sub:                        80 ms   25000000 ops/sec
 2000000 float mul:                        78 ms   25641025 ops/sec
 2000000 float div:                        86 ms   23255813 ops/sec
IterationTime= 116 msec
 2000000 double add:                      114 ms   17543859 ops/sec
 2000000 double sub:                      109 ms   18348623 ops/sec
 2000000 double mul:                      108 ms   18518518 ops/sec
 2000000 double div:                      140 ms   14285714 ops/sec
IterationTime= 107 msec
 2000000 method calls:                     92 ms   21739130 ops/sec
IterationTime= 115 msec
 2000000 static method calls:              57 ms   35087719 ops/sec
IterationTime= 107 msec
 2000000 native static method calls:        1 ms 2000000000 ops/sec
IterationTime= 11 msec
  200000 object creations:              16942 ms      11804 ops/sec
IterationTime= 11 msec
  200000 string compares (easy):          112 ms    1785714 ops/sec
IterationTime= 1 msec
   10000 string compares (hard):          157 ms      63694 ops/sec
IterationTime= 1 msec
   20000 string concats:                 2721 ms       7350 ops/sec
64453262 Total Loop Executions:         27688 ms    2327841 loops/sec
Note: each Loop Execution includes multiple Java operations

	 * @param args
	 */
	public static void main(String args[])
	{
		System.out.println("GeneralBench " + VERSION);

		BenchUtils.cleanUp("At start");
		int chunkSize = 0x4000; // Must be power of 2

		final boolean MICRO = true;
        int iterate;
        if (MICRO) {
            // iterate = 200000;
            iterate = 20000; // Duemilanove
        } else {
            iterate = 2000000; // pc release
        }
		int tests = iterate / 10;

		long countAll = 0;
		long startAll = System.currentTimeMillis();

        if (!MICRO) {
    		countAll += benchArrayMCopyByte(iterate / chunkSize / 2, chunkSize);
    		BenchUtils.cleanUp(null);

    		countAll += benchArraySCopyByte(iterate * 100 / chunkSize, chunkSize);
    		BenchUtils.cleanUp(null);

    		countAll += benchArrayMCopyInt(iterate / chunkSize * 2, chunkSize / 4);
    		BenchUtils.cleanUp(null);

    		countAll += benchArraySCopyInt(iterate * 100 / chunkSize, chunkSize / 4);
    		BenchUtils.cleanUp(null);
        }

		countAll += benchArithByte(iterate);
		BenchUtils.cleanUp(null);

		countAll += benchArithShort(iterate);
		BenchUtils.cleanUp(null);

		countAll += benchArithChar(iterate);
		BenchUtils.cleanUp(null);

		countAll += benchArithInt(iterate);
		BenchUtils.cleanUp(null);

		countAll += benchArithLong(iterate);
		BenchUtils.cleanUp(null);

		countAll += benchArithFloat(iterate);
		BenchUtils.cleanUp(null);

		countAll += benchArithDouble(iterate);
		BenchUtils.cleanUp(null);

		countAll += benchMethod(iterate);
		BenchUtils.cleanUp(null);

		countAll += benchMethodStatic(iterate);
		BenchUtils.cleanUp(null);

		countAll += benchMethodStaticNative(iterate);
		BenchUtils.cleanUp(null);

        countAll += benchNewOp(tests);
        BenchUtils.cleanUp(null);

		countAll += benchStringCompareEasy(tests);
		BenchUtils.cleanUp(null);

		countAll += benchStringCompareHard(tests / 20);
		BenchUtils.cleanUp(null);

        countAll += benchStringConcat(tests / 10);
        BenchUtils.cleanUp(null);

		long endAll = System.currentTimeMillis();
		report(countAll, "Total Loop Executions", countAll, "loops", endAll - startAll);
		System.out.println("Note: each Loop Execution includes multiple Java operations");
		System.out.flush();
	}

	/**
	 * 
	 * @param count
	 * @param chunkSize
	 */
	private static int benchArrayMCopyByte(int count, int chunkSize)
	{
		byte b1[] = new byte[chunkSize];
		byte b2[] = new byte[chunkSize];

		long nullTime = BenchUtils.getIterationTime(chunkSize);

		// byte array copy
		long start = System.currentTimeMillis();		
		for (int i = 0; i < count; i++)
			for (int j = 0; j < chunkSize; j++)
				b1[j] = b2[j];
		long end = System.currentTimeMillis();

		report(count, "byte["+chunkSize+"] manual copies", count * chunkSize, "bytes", end - start - nullTime);
		
		return count * chunkSize;
	}

	/**
	 * 
	 * @param count
	 * @param chunkSize
	 */
	private static int benchArrayMCopyInt(int count, int chunkSize)
	{
		int i1[] = new int[chunkSize];
		int i2[] = new int[chunkSize];

		long nullTime = BenchUtils.getIterationTime(chunkSize);

		// int array access/copy
		long start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			for (int j = 0; j < chunkSize; j++)
				i1[j] = i2[j];
		long end = System.currentTimeMillis();

		report(count, "int["+chunkSize+"] manual copies", count * chunkSize * 4, "bytes", end - start - nullTime);
		
		return count * chunkSize;
	}

	/**
	 * We want to "touch" the same number of array elements as in the array
	 * access test, but using System.arraycopy
	 * 
	 * @param count
	 * @param chunkSize
	 */
	private static int benchArraySCopyByte(int count, int chunkSize)
	{
		byte b1[] = new byte[chunkSize];
		byte b2[] = new byte[chunkSize];
		
		long nullTime = BenchUtils.getIterationTime(count);

		long start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			System.arraycopy(b1, 0, b2, 0, chunkSize);
		long end = System.currentTimeMillis();

		report(count, "byte["+chunkSize+"] arraycopies", count * chunkSize, "bytes", end - start - nullTime);
		return count;
	}

	private static int benchArraySCopyInt(int count, int chunkSize)
	{
		int i1[] = new int[chunkSize];
		int i2[] = new int[chunkSize];

		long nullTime = BenchUtils.getIterationTime(count);

		long start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			System.arraycopy(i1, 0, i2, 0, chunkSize);
		long end = System.currentTimeMillis();

		report(count, "int["+chunkSize+"] arraycopies", count * chunkSize * 4, "bytes", end - start - nullTime);
		return count;
	}

	private static int benchStringConcat(int count) 
	{
		String s1 = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz";
		String s2 = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz";
		String s;

		long nullTime = BenchUtils.getIterationTime(count);
		long start, end;

		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			s = s1 + s2;
		end = System.currentTimeMillis();

		report(count, "string concats", count, "ops", end - start - nullTime);
		return count;
	}

	private static int benchStringCompareEasy(int count) 
	{
		String s1 = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz";
		String s2 = "Abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz";
		boolean b;

		long nullTime = BenchUtils.getIterationTime(count);
		long start, end;

		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			b = s1.equals(s2);
		end = System.currentTimeMillis();

		report(count, "string compares (easy)", count, "ops", end - start - nullTime);
		return count;
	}

	private static int benchStringCompareHard(int count) 
	{
		String s1 = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz";
		String s2 = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz";
		boolean b;

		long nullTime = BenchUtils.getIterationTime(count);
		long start, end;

		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			b = s1.equals(s2);
		end = System.currentTimeMillis();

		report(count, "string compares (hard)", count, "ops", end - start - nullTime);
		return count;
	}

	private static int benchNewOp(int count)
	{
		long nullTime = BenchUtils.getIterationTime(count);
		long start = 0, end = 0;

		// Object creations
		try
		{
			start = System.currentTimeMillis();
			for (int i = 0; i < count; i++)
				new GeneralBench();
			end = System.currentTimeMillis();
		}
		catch (Exception e)
		{
			BenchUtils.cleanUp(e.toString());
		}

		report(count, "object creations", count, "ops", end - start - nullTime);
		return count;
	}

	private static int benchMethod(int count)
	{
		long nullTime = BenchUtils.getIterationTime(count);
		GeneralBench b = new GeneralBench();

		// Function calls
		long start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			b.dummy1();
		long end = System.currentTimeMillis();

		report(count, "method calls", count, "ops", end - start - nullTime);
		return count;
	}

	private static int benchMethodStatic(int count)
	{
		long nullTime = BenchUtils.getIterationTime(count);

		// Function calls
		long start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			dummy2();
		long end = System.currentTimeMillis();

		report(count, "static method calls", count, "ops", end - start - nullTime);
		return count;
	}

	private static int benchMethodStaticNative(int count)
	{
		long nullTime = BenchUtils.getIterationTime(count);

		// Function calls
		long start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			;//VM.getFirmwareRevision();
		long end = System.currentTimeMillis();

		report(count, "native static method calls", count, "ops", end - start - nullTime);
		return count;
	}

	private static int benchArithDouble(int count)
	{
		double a, b, c;
		
		long nullTime = BenchUtils.getIterationTime(count);
		long start, end;

		// Double Add
		a = 0x7777777777777777p0;
		b = 0x1111111111111111p0;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = a + b;
		end = System.currentTimeMillis();
		report(count, "double add", count, "ops", end - start - nullTime);

		// Double sub
		a = 0x8888888888888888p0;
		b = 0x1111111111111111p0;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = a - b;
		end = System.currentTimeMillis();
		report(count, "double sub", count, "ops", end - start - nullTime);

		// Double Mul
		a = 0x0F0F0F0F0F0F0F0Fp0;
		b = 0x1111111111111111p0;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = a * b;
		end = System.currentTimeMillis();
		report(count, "double mul", count, "ops", end - start - nullTime);

		// Double Div
		a = 0xFEFEFEFEFEFEFEFEp0;
		b = 0x0E0E0E0E0E0E0E0Ep0;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = a / b;
		end = System.currentTimeMillis();
		report(count, "double div", count, "ops", end - start - nullTime);
		
		return count * 4;
	}

	private static int benchArithFloat(int count)
	{
		float a, b, c;
		long nullTime = BenchUtils.getIterationTime(count);
		long start, end;

		// Float Add
		a = 0x77777777p0f;
		b = 0x11111111p0f;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = a + b;
		end = System.currentTimeMillis();
		report(count, "float add", count, "ops", end - start - nullTime);

		// Float sub
		a = 0x88888888p0f;
		b = 0x11111111p0f;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = a - b;
		end = System.currentTimeMillis();
		report(count, "float sub", count, "ops", end - start - nullTime);

		// Float Mul
		a = 0x0F0F0F0Fp0f;
		b = 0x11111111p0f;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = a * b;
		end = System.currentTimeMillis();
		report(count, "float mul", count, "ops", end - start - nullTime);

		// Float Div
		a = 0xFEFEFEFEp0f;
		b = 0x0E0E0E0Ep0f;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = a / b;
		end = System.currentTimeMillis();
		report(count, "float div", count, "ops", end - start - nullTime);
		
		return count * 4;
	}

	/**
	 * int primitive performance: add, sub, mul, div. I recoded this to use the
	 * index in the calculation and an odd 32-bit constant as the other operand.
	 * 
	 * @param count
	 *            the number of iterations of each operation
	 */
	private static int benchArithInt(int count)
	{
		int a, b, c;

		long nullTime = BenchUtils.getIterationTime(count);
		long start, end;

		// Integer Add
		a = 0x77777777;
		b = 0x11111111;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = a + b;
		end = System.currentTimeMillis();
		report(count, "int add", count, "ops", end - start - nullTime);

		// Integer sub
		a = 0x88888888;
		b = 0x11111111;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = a - b;
		end = System.currentTimeMillis();
		report(count, "int sub", count, "ops", end - start - nullTime);

		// Integer Mul
		a = 0x0F0F0F0F;
		b = 0x11111111;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = a * b;
		end = System.currentTimeMillis();
		report(count, "int mul", count, "ops", end - start - nullTime);

		// Integer Div
		a = 0xFEFEFEFE;
		b = 0x0E0E0E0E;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = a / b;
		end = System.currentTimeMillis();
		report(count, "int div", count, "ops", end - start - nullTime);
		
		return count * 4;
	}

	/**
	 * int primitive performance: add, sub, mul, div. I recoded this to use the
	 * index in the calculation and an odd 32-bit constant as the other operand.
	 * 
	 * @param count
	 *            the number of iterations of each operation
	 */
	private static int benchArithLong(int count)
	{
		long a, b, c, j=1;

		long nullTime = BenchUtils.getIterationTime(count);
		long start, end;

		// Integer Add
		a = 0x7777777777777777L;
		b = 0x1111111111111111L;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			j = j + b;
			//a = a + b;
		end = System.currentTimeMillis();
		report(count, "long add", count, "ops", end - start - nullTime);

		// Integer sub
		a = 0x8888888888888888L;
		b = 0x1111111111111111L;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			j = j - b;
			//a = a - b;
		end = System.currentTimeMillis();
		report(count, "long sub", count, "ops", end - start - nullTime);

		// Integer Mul
		a = 0x0F0F0F0F0F0F0F0FL;
		b = 0x1111111111111111L;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			j=j*a;
			//c = a * b;
		end = System.currentTimeMillis();
		report(count, "long mul", count, "ops", end - start - nullTime);

		// Integer Div
		a = 0xFEFEFEFEFEFEFEFEL;
		b = 0x0E0E0E0E0E0E0E0EL;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			// Imsys original code: j = j / j;
			//c = a / b;
			j=j/b;
		end = System.currentTimeMillis();
		report(count, "long div", count, "ops", end - start - nullTime);
		
		return count * 4;
	}

	/**
	 * char primitive performance: add, sub, mul, div.
	 * 
	 * @param count
	 *            the number of iterations of each operation
	 */
	private static int benchArithChar(int count)
	{
		char a, b, c;

		long nullTime = BenchUtils.getIterationTime(count);
		long start, end;

		// Add
		a = (char)0x7777;
		b = (char)0x1111;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = (char) (a + b);
		end = System.currentTimeMillis();
		report(count, "char add", count, "ops", end - start - nullTime);

		// sub
		a = (char) 0x8888;
		b = (char) 0x1111;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = (char) (a - b);
		end = System.currentTimeMillis();
		report(count, "char sub", count, "ops", end - start - nullTime);

		// Mul
		a = (char) 0x0F0F;
		b = (char) 0x1111;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = (char) (a * b);
		end = System.currentTimeMillis();
		report(count, "char mul", count, "ops", end - start - nullTime);

		// Div
		a = (char) 0xFEFE;
		b = (char) 0x0E0E;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = (char) (a / b);
		end = System.currentTimeMillis();
		report(count, "char div", count, "ops", end - start - nullTime);
		
		return count * 4;
	}

	/**
	 * short primitive performance: add, sub, mul, div.
	 * 
	 * @param count
	 *            the number of iterations of each operation
	 */
	private static int benchArithShort(int count)
	{
		short a, b, c;

		long nullTime = BenchUtils.getIterationTime(count);
		long start, end;

		// Add
		a = (short)0x7777;
		b = (short)0x1111;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = (short) (a + b);
		end = System.currentTimeMillis();
		report(count, "short add", count, "ops", end - start - nullTime);

		// sub
		a = (short) 0x8888;
		b = (short) 0x1111;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = (short) (a - b);
		end = System.currentTimeMillis();
		report(count, "short sub", count, "ops", end - start - nullTime);

		// Mul
		a = (short) 0x0F0F;
		b = (short) 0x1111;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = (short) (a * b);
		end = System.currentTimeMillis();
		report(count, "short mul", count, "ops", end - start - nullTime);

		// Div
		a = (short) 0xFEFE;
		b = (short) 0x0E0E;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = (short) (a / b);
		end = System.currentTimeMillis();
		report(count, "short div", count, "ops", end - start - nullTime);
		
		return count * 4;
	}

	/**
	 * byte primitive performance: add, sub, mul, div.
	 * 
	 * @param count
	 *            the number of iterations of each operation
	 */
	private static int benchArithByte(int count)
	{
		byte a, b, c;

		long nullTime = BenchUtils.getIterationTime(count);
		long start, end;

		// Add
		a = (byte)0x77;
		b = (byte)0x11;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = (byte) (a + b);
		end = System.currentTimeMillis();
		report(count, "byte add", count, "ops", end - start - nullTime);

		// sub
		a = (byte) 0x88;
		b = (byte) 0x11;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = (byte) (a - b);
		end = System.currentTimeMillis();
		report(count, "byte sub", count, "ops", end - start - nullTime);

		// Mul
		a = (byte) 0x0F;
		b = (byte) 0x11;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = (byte) (a * b);
		end = System.currentTimeMillis();
		report(count, "byte mul", count, "ops", end - start - nullTime);

		// Div
		a = (byte) 0xFE;
		b = (byte) 0x0E;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = (byte) (a / b);
		end = System.currentTimeMillis();
		report(count, "byte div", count, "ops", end - start - nullTime);
		
		return count * 4;
	}
}
