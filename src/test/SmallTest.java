package test;

import java.lang.reflect.Field;
import java.util.Arrays;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.junit.Test;


public class SmallTest {

	public static void main(String[] args) {
		// String s1 = "";
		// String s2 = "aa";
		// StringTest st1 = new StringTest("aa");
		// StringTest st2 = new StringTest("aa");
		//
		//
		// System.out.println(s1.length());

		// Scanner input = new Scanner(System.in);
		// System.out.println("Enter:");
		// String inputString = "11";
		// inputString = input.next();
		// String input = "(5+3)*(6-2)";
		// String[] newArr = input.replaceAll("([\\+\\-\\*\\/\\(\\)])", " $1
		// ").trim().replaceAll(" ", " ").split(" ");
		// System.out.println(newArr.length);
		// for (String string : newArr) {
		// System.out.println(string);
		//
		// }

		ScriptEngineManager manager = new ScriptEngineManager();

		ScriptEngine engine = manager.getEngineByName("javascript");
		try {
			Double d = (Double) engine.eval("10*(5.5+9)-25/2");
			System.out.println(d);
		} catch (ScriptException ex) {

		}

		// System.out.println(inputString.length());
		// input.close();
		// while(input.hasNext()){
		// inputString = input.next();
		// if(inputString == "1")
		// break;
		// }
		// System.out.println(inputString);
		// input.close();
	}
	
	
	@Test
	public void testGetResource(){
		System.out.println(SmallTest.class.getResource(""));
        System.out.println(SmallTest.class.getResource("/"));
        System.out.println(SmallTest.class.getClassLoader().getResource(""));  //总是相对于类加载根路径
        System.out.println(SmallTest.class.getClassLoader().getResource("/")); //没有这种用法
	}
	
	
	@Test
	public void testInteger() throws Exception{
		Class cache = Integer.class.getDeclaredClasses()[0]; // 1
		Field myCache = cache.getDeclaredField("cache"); // 2
		myCache.setAccessible(true);// 3

		Integer[] newCache = (Integer[]) myCache.get(cache); // 4
		newCache[132] = newCache[133]; // 5

		int a = 2;
		int b = a + a;
		System.out.printf("%d + %d = %d", a, a, b); //
	}
	
	
	@Test
	public void testPlusPlus(){
		int[] r = new int[]{0,0,0};
		int[] t = new int[]{1,2,3};
		int i = 0;
//		r[i] = t[++i];
		r[++i] = t[i];
		System.out.println(Arrays.toString(r));
		System.out.println(Arrays.toString(t));
		System.out.println(i);
	}

}

class StringTest {
	public String s;

	public StringTest(String s) {
		this.s = s;
	}
}
