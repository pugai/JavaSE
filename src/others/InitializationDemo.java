package others;

public class InitializationDemo {
	InitializationDemo() {
		new M();
	}

	public static void main(String[] args) {
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
	}

	{
		System.out.println("(5) N's instance initialization block");
	}

	static {
		System.out.println("(3) N's static initialization block");
	}
}

class M extends N {
	M() {
		System.out.println("(8) M's constructor body");
	}

	{
		System.out.println("(7) M's instance initialization block");
	}

	static {
		System.out.println("(4) M's static initialization block");
	}
}
