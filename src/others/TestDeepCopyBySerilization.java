package others;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.Serializable;

/**
 * 3．利用串行化来做深复制（主要是为了避免重写比较复杂对象的深复制的clone（）方法，也可以程序实现断点续传等等功能）
 * 把对象写到流里的过程是串行化（Serilization）过程，但是在Java程序师圈子里又非常形象地称为“冷冻”或者“腌咸菜（picking）”过程；
 * 而把对象从流中读出来的并行化（Deserialization）过程则叫做 “解冻”或者“回鲜(depicking)”过程。
 * 应当指出的是，写在流里的是对象的一个拷贝，而原对象仍然存在于JVM里面，因此“腌成咸菜”的只是对象的一个拷贝，Java咸菜还可以回鲜。
 * 在Java语言里深复制一个对象，常常可以先使对象实现Serializable接口，然后把对象（实际上只是对象的一个拷贝）写到一个流里（腌成咸菜），
 * 再从流里读出来（把咸菜回鲜），便可以重建对象。
 * 这样做的前提是对象以及对象内部所有引用到的对象都是可串行化的，否则，就需要仔细考察那些不可串行化的对象或属性可否设成transient，
 * 从而将之排除在复制过程之外。<br>
 * http://www.jb51.net/article/62909.htm
 * 
 * @author tianlong
 *
 */
public class TestDeepCopyBySerilization {
	public static void main(String[] args) throws OptionalDataException, ClassNotFoundException, IOException {
		Professor2 p = new Professor2("tangliang", 30);
		Student2 s1 = new Student2("zhangsan", 18, p);
		Student2 s2 = (Student2) s1.deepClone();
		s2.p.name = "tony";
		s2.p.age = 40;
		// 学生1的老师不改变
		System.out.println("name=" + s1.p.name + "," + "age=" + s1.p.age);
		System.out.println("name=" + s2.p.name + "," + "age=" + s2.p.age);
	}
}

class Professor2 implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	int age;

	public Professor2(String name, int age) {
		this.name = name;
		this.age = age;
	}
}

class Student2 implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;// 常量对象
	int age;
	Professor2 p;

	public Student2(String name, int age, Professor2 p) {
		this.name = name;
		this.age = age;
		this.p = p;
	}

	public Object deepClone() throws IOException, OptionalDataException, ClassNotFoundException {// 将对象写到流里
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		ObjectOutputStream oo = new ObjectOutputStream(bo);
		oo.writeObject(this);// 从流里读出来
		ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
		ObjectInputStream oi = new ObjectInputStream(bi);
		return (oi.readObject());
	}

}
