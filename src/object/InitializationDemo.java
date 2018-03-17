package object;

public class InitializationDemo {
	InitializationDemo() {
		new M();
	}

	public static void main(String[] args) {
		System.out.println("---");
		new InitializationDemo();
	}

	{
		System.out.println("(2) InitializationDemo's instance initialization block");
	}

	static {
		System.out.println("(1) InitializationDemo's static initialization block");
	}
}

class N {
	N() {
		System.out.println("(6) N's constructor body");
		print();
	}

	{
		System.out.println("(5) N's instance initialization block");
	}

	static {
		System.out.println("(3) N's static initialization block");
	}
	
	public void print() {
		System.out.println("父类print方法");
	}
}

class M extends N {
	public int m = 1;
	M() {
		System.out.println("(8) M's constructor body");
	}

	{
		System.out.println("(7) M's instance initialization block");
		print();
	}

	static {
		System.out.println("(4) M's static initialization block");
	}
	
	public void print() {
		System.out.println("子类print方法，m值为" + m);
	}
}
