package annotation;

import java.lang.reflect.Method;

/**
 * 1.基本注释：Override Deprecated SuppressWarnings
 * 2.如何自定义注解
 * 3.元注解
 * @author tianlong
 *
 */
public class TestAnnotationProcessor {

	public static void main(String[] args) {
		ClassAnnotation annotation = Student.class.getDeclaredAnnotation(ClassAnnotation.class);
		System.out.println(annotation.value());
		for(Method m : Student.class.getDeclaredMethods()){
			MethodAnnotation annotation2 = m.getDeclaredAnnotation(MethodAnnotation.class);
			if(annotation2 != null){
				System.out.println("the method id is: " + annotation2.id());
			}
		}
	}
}

@ClassAnnotation("classname is Student")
class Student extends Person{
	
	@MethodAnnotation(id=1)
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
