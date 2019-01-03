package haikuvm.bench;

import java.io.IOException;

public class IOEcho {
	public static void main(String[] args) throws IOException {
		int c = 0;

		System.out.println("Press any character to echo (q will quit):");
		while(c!='q') {
			c=System.in.read();
			System.out.println(c+"\t'"+(char)c+"'");
		}
		System.out.println("Bye!");
	}
}
