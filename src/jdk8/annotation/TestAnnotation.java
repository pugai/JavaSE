package jdk8.annotation;

import java.lang.reflect.Method;

import org.junit.Test;

/**
 * 重复注解和类型注解
 * @author Tianlong
 *
 */
public class TestAnnotation {
	
//	private @NonNull Object obj = null;  //java8未内置，需要配合第三方框架，如 checker，framework 进行编译时检查
	
	
	@Test
	public void test1() throws Exception {
		//应用注解
		Class<TestAnnotation> clazz = TestAnnotation.class;
		Method m1 = clazz.getMethod("show");
		
		MyAnnotation[] mas = m1.getAnnotationsByType(MyAnnotation.class);
		
		for (MyAnnotation myAnnotation : mas) {
			System.out.println(myAnnotation.value());
		}
	}
	
	
	@MyAnnotation("hello")
	@MyAnnotation("world")
	public void show() {
		
	}

}
