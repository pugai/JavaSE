package enumtype;

/**
 * 一、枚举类 1。如何自定义枚举类 2.如何使用enum关键字定义枚举类 常用方法：values() valueOf(String name)
 * 如何让枚举类实现接口：可以让不同的枚举类的对象调用被重写的抽象方法，执行效果不同，相当于让每个对象重写
 * 
 * @author tianlong
 *
 */
public class Enum_season1 {

	public static void main(String[] args) {
		Season1 spring = Season1.SPRING;
		System.out.println(spring.string);
		System.out.println(spring);
		spring.show();
		System.out.println(spring.getSeasonName());
		// 1.values()
		Season1[] seasons = Season1.values();
		for (Season1 s : seasons) {
			System.out.println(s);
		}
		// 2.valueOf(String name)：传入形参是枚举类对象的名字，不能随意传入
		String str = "WINTER";
		Season1 sea = Season1.valueOf(str);
		System.out.println(sea);
		sea.show();

		Thread.State[] states = Thread.State.values();
		for (Thread.State state : states) {
			System.out.println(state);
		}
	}

}

interface Info {
	public void show();
}

// 枚举类enum方式
enum Season1 implements Info {
	// 要写在第一行
	SPRING("spring", "春暖花开") {
		@Override
		public void show() {
			System.out.println("春天在哪里");
		}
	},
	SUMMER("sunmer", "夏日炎炎") {
		@Override
		public void show() {
			System.out.println("生如夏花");
		}
	},
	AUTUMN("autumn", "秋高气爽") {
		@Override
		public void show() {
			System.out.println("秋天不回来");
		}
	},
	WINTER("winter", "白雪皑皑") {
		@Override
		public void show() {
			System.out.println("冬天里的一把火");
		}
	};
	public String string = "nihao";
	private final String seasonName;
	private final String seasonDesc;

	private Season1(String seasonName, String seasonDesc) {
		this.seasonName = seasonName;
		this.seasonDesc = seasonDesc;
	}

	public String getSeasonName() {
		return seasonName;
	}

	public String getSeasonDesc() {
		return seasonDesc;
	}

	@Override
	public String toString() {
		return "Season [seasonName=" + seasonName + ", seasonDesc=" + seasonDesc + "]";
	}
	
	//可以被具体对象重写
	@Override
	public void show() {
		System.out.println("This is a season");
	}

}
