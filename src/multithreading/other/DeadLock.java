package multithreading.other;

class A {
	public synchronized void foo(B b) {//锁：A的对象a
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		b.last();
	}

	public synchronized void last() {//锁：A的对象a
	}
}

class B {
	public synchronized void bar(A a) {//锁：B的对象 b
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		a.last();
	}

	public synchronized void last() {//锁：B的对象 b
	}
}

public class DeadLock implements Runnable {
	A a = new A();
	B b = new B();

	public void init() {
		a.foo(b);
	}

	public void run() {
		b.bar(a);
	}

	public static void main(String[] args) {
		DeadLock dl = new DeadLock();
		new Thread(dl).start();
		dl.init();
	}
}
