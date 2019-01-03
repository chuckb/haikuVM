package nanovm.bench;

class A {
	int x=1;
	
	int get() {
		return x;
	}
	int sget() {
		return 0;
	}
}

class B extends A {
	int x=2;
	
	int get() {
		return x;
	}
	int sget() {
		return super.x;
	}
}

class C extends A {
	int x=3;
	
	int get() {
		return x;
	}
	int sget() {
		return super.x;
	}
}

class D extends A {	
}

class E extends D {	
	int get() {
		return x;
	}
	int sget() {
		return super.x;
	}
}

public class Poly {
	
	static int get(A a) {
		return a.get();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		A a= new A();
		B b= new B();
		C c= new C();
		D d= new D();
		E e= new E();
		
		Assert.equal("a.get()=", a.get(), 1);
		Assert.equal("b.get()=", b.get(), 2);
		Assert.equal("c.get()=", c.get(), 3);
		Assert.equal("d.get()=", d.get(), 1);
		Assert.equal("e.get()=", e.get(), 1);

		Assert.equal("a.get()==get(a) : ", a.get(), get(a));
		Assert.equal("b.get()==get(b) : ", b.get(), get(b));
		Assert.equal("c.get()==get(c) : ", c.get(), get(c));
		Assert.equal("d.get()==get(d) : ", d.get(), get(d));
		Assert.equal("e.get()==get(e) : ", e.get(), get(e));

		Assert.equal("a.sget()=", a.sget(), 0);
		Assert.equal("b.sget()=", b.sget(), 1);
		Assert.equal("c.sget()=", c.sget(), 1);
		Assert.equal("d.sget()=", d.sget(), 0);
		Assert.equal("e.sget()=", e.sget(), 1);

	}
}
