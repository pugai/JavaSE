package jdk8.defaultMethod;

public class SubClass /*extends MyClass*/ implements MyFun, MyInterface {

	public static void main(String[] args) {
		SubClass sc = new SubClass();
		// “类优先”,如果方法签名都一样，优先用父类方法
		System.out.println(sc.getName());
		
		
		MyInterface.show();
	}

	// 不考虑父类中方法，如果两个接口中都有相同签名的方法，则子类必须 Override方法
	@Override
	public String getName() {
		return MyInterface.super.getName();
	}

}
