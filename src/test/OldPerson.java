package test;

import java.io.Serializable;

/**
 * @author ctl
 * @date 2020/12/4
 */
public class OldPerson implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private int age;

	public OldPerson(String name, int age) {
		this.name = name;
		this.age = age;
	}

	@Override
	public String toString() {
		return "Person{" +
				"name='" + name + '\'' +
				", age=" + age +
				'}';
	}
}
