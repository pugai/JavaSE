package enumtype;

public class Enum_season {

	public static void main(String[] args) {
		Season spring =  Season.SPRING;
		System.out.println(spring);
		spring.show();
		System.out.println(spring.getSeasonDesc());
	}
	
}
//枚举类自定义方式
class Season{
	//1.提供类的属性，声明为private final
	private final String seasonName;
	private final String seasonDesc;
	//2.声明为final的属性，在构造器中初始化
	private Season(String seasonName, String seasonDesc) {
		this.seasonName = seasonName;
		this.seasonDesc = seasonDesc;
	}
	//3.通过公共方法来调用
	public String getSeasonName() {
		return seasonName;
	}
	public String getSeasonDesc() {
		return seasonDesc;
	}
	//4.创建枚举类的对象：将类的对象声明为public static final
	public static final Season SPRING = new Season("spring", "春暖花开");
	public static final Season SUNMER = new Season("sunmer", "夏日炎炎");
	public static final Season AUTUMN = new Season("autumn", "秋高气爽");
	public static final Season WINTER = new Season("winter", "白雪皑皑");
	@Override
	public String toString() {
		return "Season [seasonName=" + seasonName + ", seasonDesc=" + seasonDesc + "]";
	}
	
	public void show(){
		System.out.println("This is a season");
	}
	
	
	
}
