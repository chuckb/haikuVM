package haikuvm.bench;

public class NumberTest {

	public static void main(String[] args) {
        noChanceToOpt("57303 = ", 57303);
        System.out.println();
        noChanceToOpt("0xff = ", 0xff);
        noChanceToOpt("0xffff = ", 0xffff);
        noChanceToOpt("0xffffff = ", 0xffffff);
        noChanceToOpt("0xffffffff = ", 0xffffffff);
        System.out.println();
        noChanceToOpt("0x7f = ", 0x7f);
        noChanceToOpt("0x7fff = ", 0x7fff);
        noChanceToOpt("0x7fffff = ", 0x7fffff);
        noChanceToOpt("0x7fffffff = ", 0x7fffffff);
        System.out.println();
        noChanceToOpt("0xffL = ", 0xffL);
        noChanceToOpt("0xffffL = ", 0xffffL);
        noChanceToOpt("0xffffffL = ", 0xffffffL);
        noChanceToOpt("0xffffffffL = ", 0xffffffffL);
        noChanceToOpt("0xffffffffffL = ", 0xffffffffffL);
        noChanceToOpt("0xffffffffffffL = ", 0xffffffffffffL);
        noChanceToOpt("0xffffffffffffffL = ", 0xffffffffffffffL);
        noChanceToOpt("0xffffffffffffffffL = ", 0xffffffffffffffffL);
        System.out.println();
        noChanceToOpt("0x7fL = ", 0x7fL);
        noChanceToOpt("0x7fffL = ", 0x7fffL);
        noChanceToOpt("0x7fffffL = ", 0x7fffffL);
        noChanceToOpt("0x7fffffffL = ", 0x7fffffffL);
        noChanceToOpt("0x7fffffffffL = ", 0x7fffffffffL);
        noChanceToOpt("0x7fffffffffffL = ", 0x7fffffffffffL);
        noChanceToOpt("0x7fffffffffffffL = ", 0x7fffffffffffffL);
        noChanceToOpt("0x7fffffffffffffffL = ", 0x7fffffffffffffffL);
	}

    private static void noChanceToOpt(String msg, int value) {
        System.out.print(msg);
        System.out.println(value);
    }

    private static void noChanceToOpt(String msg, long value) {
        System.out.print(msg);
        System.out.println(value);
    }

}
