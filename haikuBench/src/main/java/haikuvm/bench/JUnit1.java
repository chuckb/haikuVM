package haikuvm.bench;

import static java.lang.Math.*;

import java.io.IOException;

/**
 * For interactive or remote JUnit use.
 *
 * 1345, 10 / ? ;
 * 134.5
 *
 * @author genom2
 *
 */
public class JUnit1 {

    private static double[] stack = new double[10];
    private static int sp;
    private static char[] line=new char[50];
    private static boolean verbose=true;

    public static void main(String[] args) throws IOException {
        System.out.println("Press any character to echo (exit() will exit):");
        while (true) {
            line = readLine();
            System.gc();
            if (!interpret(line)) break;
        }
        System.out.println("Bye!");
    }

    private static char[] readLine() throws IOException {
        for (int i=0; i<line.length; i++) {
            char c = (char) System.in.read();
            if (verbose) System.out.print((char) c );
            line[i]=(char)c;
            if (c==';') break;
        }
        return line;
    }

    private static boolean interpret(char[] line) {
        sp=0;
        double d=0;
        String cmd="";
        for (int i=0; i<line.length; i++) {
            char c=line[i];
            switch (c) {
            case '0':case '1':case '2':case '3':case '4':case '5':case '6':case '7':case '8':case '9':
                push(10*pop()+(c-'0'));
                break;
            case ';':
                return true;
            case '#':
                verbose= !verbose;
                break;
            case ',':
                push(0);
                break;
            case ':':
                d=pop();
                push(d); push(d);
                break;
            case '*':
                d=pop();
                push(pop()*d);
                break;
            case '/':
                d=pop();
                push(pop()/d);
                break;
            case '+':
                d=pop();
                push(pop()+d);
                break;
            case '-':
                d=pop();
                push(pop()-d);
                break;
            case '.':
                pop();
                break;
            case '!':
                char[] b=new char[] {256};
                if (b[0]==0) {
                    System.out.println("char8: "+(int)b[0]);
                } else {
                    System.out.println("char16: "+(int)b[0]);
                }

                int ti=0x7fff;
                if (ti+1<0) {
                    System.out.println("int16: "+(ti+1));
                } else {
                    System.out.println("int32: "+(ti+1));
                }

                long tl=0x7fff;
                if (tl+1<0) {
                    System.out.println("long16: "+(tl+1));
                } else {
                    tl=0x7fffffff;
                    if (tl+1<0) {
                        System.out.println("long32: "+(tl+1));
                    } else {
                        System.out.println("long64: "+(tl+1));
                    }
                }

                float f=120000f;
                if (abs(f/2-60000.0)<1) {
                    System.out.println("float32: "+f/2);
                } else {
                    System.out.println("float16: "+f/2);
                }

                d=120000.0;
                if (abs(d/2-60000.0)<1) {
                    d=6e100;
                    if (abs(d/2-3e100)<1) {
                        System.out.println("double64: "+d/2);
                    } else {
                        System.out.println("double32: "+d/2);
                    }
                } else {
                    System.out.println("double16: "+d/2);
                }
                break;
            case '?':
                System.out.println(" "+pop());
                break;
            case ')':
                if (cmd.equals("sin")) {
                    push(sin(pop()));
                } else if(cmd.equals("cos")) {
                    push(cos(pop()));
                } else if(cmd.equals("sqrt")) {
                    push(sqrt(pop()));
                } else if(cmd.equals("asin")) {
                    push(Math.asin(pop()));
                } else if(cmd.equals("random")) {
                    push(Math.random());
                } else if(cmd.equals("floor")) {
                    push(Math.floor(pop()));
                } else if(cmd.equals("dmax")) {
                    push(Double.MAX_VALUE);
                } else if(cmd.equals("dmin")) {
                    push(Double.MIN_VALUE);
                } else if(cmd.equals("fmax")) {
                    push(Float.MAX_VALUE);
                } else if(cmd.equals("fmin")) {
                    push(Float.MIN_VALUE);
                } else if(cmd.equals("exit")) {
                    return false;
                }
                cmd="";
                break;
            default:
                if ('a' <=c && c<='z') cmd+=(char)c;
            }
        }
        return true;
    }

    private static void push(double d) {
        stack[sp++]=d;
    }

    private static double pop() {
        double d=stack[(sp>0)?--sp:0];
        stack[sp]=0;
        return d;
    }

}
