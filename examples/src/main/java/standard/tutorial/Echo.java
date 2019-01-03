package standard.tutorial;

import java.io.IOException;

public class Echo {

	public static void main(String[] args) throws IOException {
		long t0=System.currentTimeMillis();
		int inChar=0;
	    for(int i=0; inChar!='q' && System.currentTimeMillis()-t0<120000;i++) {
	        if (i%30000==0) {
	        	System.out.println("Please start typing "+i+":");
	        }
            while (System.in.available() != 0) {
                inChar = System.in.read();
                System.out.println(i+": "+(char)inChar);
        		t0=System.currentTimeMillis();
            }
	    }
    	System.out.println("done!");
	}
}
