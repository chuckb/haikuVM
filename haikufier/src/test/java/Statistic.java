import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

import org.junit.Test;


public class Statistic {
	/**
	 * @param args
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		//sizesOfFoos();
		new Statistic().buildJUnitStat();
	}

    static class Info {
        int block;
        String date;
        Integer elapsed;
        Integer program;
        Integer data;
    };
    
    @Test
    public void buildOneJUnitStat() throws IOException {
        Map<String, Vector<Info>> table = new TreeMap<String, Vector<Info>>();
        Set<String> block = new TreeSet<String>();
        readFile(table, block, "src/test/resources/junit_arduino_16_32.log");
        readFile(table, block, "src/test/resources/junit_duemilanove_16_32.log");
        
        //print by block
        showVerticalBlock(table, 2);
    }

	private void readFile(Map<String, Vector<Info>> table, Set<String> block, String filename) throws IOException {
        BufferedReader bi= new BufferedReader(new FileReader(filename));
    	boolean isTarget=false;
		String line, testee=null;
        Info info=new Info();
        for(int i=1; (line=bi.readLine())!=null; i++) {
            /*
            ##########  Date: 2013-11-14 21:51:54.562
            ##########  haikuvm.bench.SynchronizedThreads3
            ...
            ##########  elapsed time: 72437 ms
            ##########  Program:    9064 bytes (27.7% Full)
            ##########  Data:       1673 bytes (81.7% Full)
            ##########  end
            */
            if (line.startsWith("##########  ")) {
                String list[]= line.split("[ ]+");
                if (list.length>3 && list[1].equals("Date:")) {
                    info.date=list[2]+" "+list[3];
                } else if (list.length>3 && list[1].equals("elapsed")) {
                    info.elapsed=Integer.parseInt(list[3]);
                } else if (list.length>2 && list[1].equals("Program:")) {
                    info.program=Integer.parseInt(list[2]);
                } else if (list.length>2 && list[1].equals("Data:")) {
                    info.data=Integer.parseInt(list[2]);
                } else if (list.length>1 && list[1].equals("end")) {
                	if (isTarget) {
                        if (!block.add(testee)) {
                            block = new TreeSet<String>();
                        }
                        if (table.get(testee)==null) {
                            table.put(testee, new Vector<Info>());
                        }
                        table.get(testee).add(info);
                        info=new Info();
                        isTarget=false;
                	}
                } else if (list.length>1) {
                	isTarget=list[1].endsWith(".Fibonacci");
                    testee=list[1];
                }
            }
        }
        bi.close();
	}
   
    @Test
    public void buildJUnitStat() throws IOException {
        int blockCount=0;
        Map<String, Vector<Info>> table = new TreeMap<String, Vector<Info>>();
        Set<String> block = new TreeSet<String>();
        //BufferedReader bi= new BufferedReader(new FileReader("src/test/resources/junit.log"));
        BufferedReader bi= new BufferedReader(new FileReader("src/test/resources/junit_arduino_16_32.log"));
        String line, testee=null;
        Info info=new Info();
        for(int i=1; (line=bi.readLine())!=null; i++) {
            /*
            ##########  Date: 2013-11-14 21:51:54.562
            ##########  haikuvm.bench.SynchronizedThreads3
            ...
            ##########  elapsed time: 72437 ms
            ##########  Program:    9064 bytes (27.7% Full)
            ##########  Data:       1673 bytes (81.7% Full)
            ##########  end
            */
            if (line.startsWith("##########  ")) {
                String list[]= line.split("[ ]+");
                if (list.length>3 && list[1].equals("Date:")) {
                    info.date=list[2]+" "+list[3];
                } else if (list.length>3 && list[1].equals("elapsed")) {
                    info.elapsed=Integer.parseInt(list[3]);
                } else if (list.length>2 && list[1].equals("Program:")) {
                    info.program=Integer.parseInt(list[2]);
                } else if (list.length>2 && list[1].equals("Data:")) {
                    info.data=Integer.parseInt(list[2]);
                } else if (list.length>1 && list[1].equals("end")) {
                    info.block=blockCount;
                    if (!block.add(testee)) {
                        block = new TreeSet<String>();
                        blockCount++;
                    }
                    if (table.get(testee)==null) {
                        table.put(testee, new Vector<Info>());
                    }
                    table.get(testee).add(info);
                    info=new Info();
                } else if (list.length>1) {
                    testee=list[1];
                }
            }
        }
        bi.close();
        
        //print by block
        showByBlock(blockCount, table, 1);
    }


    private void showByBlock(int blockCount, Map<String, Vector<Info>> table, int mode) {
        for (String key : table.keySet()) {
            Vector<Info> row = table.get(key);
            System.out.printf("%s\t", key);
            Integer norm=1;
            for (Info value : row) {
                Integer target=get(value, mode);
                if (target!=null) {
                    norm=target;
                }
            }
            for (int i = 0; i < blockCount; i++) {
                int c=0;
                for (String key2 : table.keySet()) {
                    Vector<Info> row2 = table.get(key2);
                    for (Info value : row2) {
                        if (value.block==i) c++;
                    }
                }
                if (c<10) continue;
                
                Info found=null;
                for (Info value : row) {
                    if (value.block==i) found=value;
                }
                Integer value=get(found, mode);
                if (value==null) {
                    System.out.printf("\"\"\t");
                } else {
                    System.out.printf("%f\t", 1.0*value/norm);
                }
            }
            System.out.printf("\n");
        }
    }

    private void showVerticalBlock(Map<String, Vector<Info>> table, int mode) {
        for (String key : table.keySet()) {
            Vector<Info> row = table.get(key);
            System.out.printf("%s\n", key);
            for (int i = 0; i < row.size(); i++) {
                System.out.printf("%s\t%s\t%s\n", get(row.get(i), 0)==null?"":""+get(row.get(i), 0), get(row.get(i), 1)==null?"":""+get(row.get(i),1), get(row.get(i), 2)==null?"":""+get(row.get(i), 2));
            }
        }
    }


    /**
     *         switch (mode) {<br>
     *         case 0: return value.program;<br>
     *         case 1: return value.data;<br>
     *         case 2: return value.elapsed;<br>
     *         
     * 
     * @param value
     * @param mode
     * @return
     */
    private Integer get(Info value, int mode) {
        if (value==null) return null;
        switch (mode) {
        case 0: return value.program;
        case 1: return value.data;
        case 2: return value.elapsed;
        }        
        return null;
    }


    private static void sizesOfFoos() throws FileNotFoundException, IOException {
		class Desc {

			private String foo;
			private int len;

			public Desc(String foo, int len) {
				this.foo=foo;
				this.len=len;
			}
			
		};
		Vector<Desc> foos=new Vector<Desc>();
		//BufferedReader bi= new BufferedReader(new FileReader("../arduinoApp/Release/asuroApp.lss"));
		BufferedReader bi= new BufferedReader(new FileReader("../avrTest2/Release/avrTest2.lss"));
		String foo=null, bl="";
		int p0=0, p1=0;
		int s=0;
		String line;
		for(int i=1; (line=bi.readLine())!=null; i++) {
			if (line.matches(".+ <.+>:")) {
				String list[]= line.split("[ <>:]+");
				p1=Integer.parseInt(list[0], 16);
				if(foo!=null) {
					p0=p1-p0;
					foos.add(new Desc(foo, p0));
				}
				foo=list[1];
				p0=p1;
			} else if (foo!=null && foo.startsWith("bytecodeLabels") && line.length()>10) {
				String list[]= line.substring(0, "     3f8:	14 03 14 03 14 03 7b 03 a0 03 a9 03 20 03 a9 03".length()).split("\\s+");
				for (int j = 2; j < list.length; j+=2) {
					String hex=list[j+1]+list[j];
					int h=Integer.parseInt(hex, 16);
					if (s==0) s=h;
					bl+=hex+"\t"+h+"\t"+(h-s)+"\n";
				}
			}

		}
		
		Collections.sort(foos, new Comparator<Desc>() {
			   public int compare(Desc o1, Desc o2){
			      return Integer.valueOf(o1.len).compareTo(o2.len);
			   }
			});
		int sum=0;
		for (int i = 0; i < foos.size(); i++) {
			sum+=foos.get(i).len;
			System.out.printf("%4d    %4d    %s\n", i, foos.get(i).len, foos.get(i).foo);
		}
		System.out.printf("sum     %4d\n", sum);
		System.out.println(bl);
	}


}
