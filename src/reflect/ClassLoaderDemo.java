package reflect;

public class ClassLoaderDemo {
	public static void main(String[] args) {
		ClassLoader classloader;
		//获取系统缺省的ClassLoader
		classloader = ClassLoader.getSystemClassLoader();
		System.out.println(classloader);
		while (classloader != null) {
			//取得父的ClassLoader
			classloader = classloader.getParent();
			System.out.println(classloader);
		}
		try {
			Class cl = Class.forName("java.lang.Object");
			classloader = cl.getClassLoader();
			System.out.println("java.lang.Object's loader is  " + classloader);
			cl = Class.forName("reflect.ClassLoaderDemo");
			classloader = cl.getClassLoader();
			System.out.println("ClassLoaderDemo's loader is  " + classloader);
		} catch (Exception e) {
			System.out.println("Check name of the class");
		}
	}
}
