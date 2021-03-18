package test;

/**
 * @author ctl
 * @date 2020/11/20
 */
public class Home {

	private String name;
	private Human human;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Human getHuman() {
		return human;
	}

	public void setHuman(Human human) {
		this.human = human;
	}

	@Override
	public String toString() {
		return "Home{" +
				"name='" + name + '\'' +
				", human=" + human +
				'}';
	}
}
