package jdk8.defaultMethod;

public interface MyInterface {
	
	default String getName() {
		return "lalala";
	}
	
	public static void show () {
		System.out.println("method in interface");
	}

}
