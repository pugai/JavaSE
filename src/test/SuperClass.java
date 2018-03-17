package test;

import java.util.Map;

public class SuperClass {
	
	private int mSuperX;
	
	public SuperClass() {
		setmSuperX(99);
	}
	
	public void setmSuperX(int mSuperX) {
		this.mSuperX = mSuperX;
	}
	
	// 测试重定义的限制，如返回值，访问修饰符，异常类型
	// 需要满足：
	// 访问修饰符一样或更公开；返回类型一样或子类；异常一样或子类
	// 注意，参数类型需要相同，不能为子类，如果子类方法没有添加@Override注解，此时参数类型为子类是可以的，但是这其实不是重定义
	protected Number testOverride(Number number) throws Exception {
		return null;
	}

}
