package reflect;

public class ClassObject {

	public static void main(String[] args) {
		//获取Class实例的三种方式
		Class class1 = String.class;
		Class class2 = "No.1".getClass();
		try {
			Class class3 = Class.forName("java.lang.String");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
