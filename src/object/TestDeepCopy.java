package object;

/**
 * 深复制，对源对象中的对象成员也进行复制，均需实现Cloneable接口<br>
 * 利用串行化来做深复制见TestDeepCopyBySerilization.java<br>
 * http://www.jb51.net/article/62909.htm<br>
 * @author tianlong
 *
 */
public class TestDeepCopy {
	public static void main(String[] args) {
		Professor1 p = new Professor1("wangwu", 50);
		Student1 s1 = new Student1("zhangsan", 18, p);
		Student1 s2 = (Student1) s1.clone();
		s2.p.name = "lisi";
		s2.p.age = 30;
		// 学生1的教授不 改变。
		System.out.println("name=" + s1.p.name + "," + "age=" + s1.p.age);
		System.out.println("name=" + s2.p.name + "," + "age=" + s2.p.age);
	}
}

class Professor1 implements Cloneable {
	String name;
	int age;

	Professor1(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public Object clone() {
		Object o = null;
		try {
			o = super.clone();
		} catch (CloneNotSupportedException e) {
			System.out.println(e.toString());
		}
		return o;
	}
}

class Student1 implements Cloneable {
	String name;
	int age;
	Professor1 p;

	Student1(String name, int age, Professor1 p) {
		this.name = name;
		this.age = age;
		this.p = p;
	}

	public Object clone() {
		Student1 o = null;
		try {
			o = (Student1) super.clone();
		} catch (CloneNotSupportedException e) {
			System.out.println(e.toString());
		}
		// 对引用的对象也进行复制
		o.p = (Professor1) p.clone();
		return o;
	}

}
