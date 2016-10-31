package enumerate_annotation;

/**
 * 1.基本注释：Override Deprecated SuppressWarnings
 * 2.如何自定义注解
 * 3.元注解
 * @author tianlong
 *
 */
public class TestAnnotation {

	public static void main(String[] args) {
		
	}
}

@MyAnnotation
class Student extends Person{
	@Override
	public void walk() {
		System.out.println("学生走路");
	}
	@Override
	public void eat() {
		System.out.println("学生吃饭");
	}
}

class Person{
	String name;
	int age;
	
	public Person() {
	}
	public Person(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}
	
	public void walk(){
		System.out.println("走路");
	}
	
	public void eat(){
		System.out.println("吃饭");
	}
			
	
}
