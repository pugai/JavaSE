package test;

/**
 * @author ctl
 * @date 2020/11/20
 */
public class Woman implements Human {

	private String type;
	private int age;

	public void setType(String type) {
		this.type = type;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public int getAge() {
		return age;
	}

	@Override
	public String toString() {
		return "Woman{" +
				"type='" + type + '\'' +
				", age=" + age +
				'}';
	}
}
