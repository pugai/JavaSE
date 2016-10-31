package test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Test {

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

}

class StringTest {
	public String s;

	public StringTest(String s) {
		// TODO Auto-generated constructor stub
		this.s = s;
	}
}
