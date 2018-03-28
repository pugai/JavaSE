package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.MatchResult;

import org.junit.Test;

import jdk.nashorn.internal.runtime.linker.JavaAdapterFactory;

/**
 * 使用Scanner读数据
 * @author tianlong
 *
 */
public class TestScanner {

	@Test
	public void testScanner() throws FileNotFoundException {
		File file = new File("file/scores.txt");
		Scanner input = new Scanner(file);
		// Scanner input = new Scanner(System.in); //从键盘输入
		while (input.hasNext()) {
			String firstName = input.next();
			String mi = input.next();
			String lastName = input.next();
			int score = input.nextInt();
			System.out.println(firstName + " " + mi + " " + lastName + " " + score);
		}
		input.close();
	}

	/**
	 * Scanner默认定界符为  \p{javaWhitespace}+  
	 * \p{javaWhitespace}等效于 java.lang.Character.isWhitespace() 包括回车\r,换行\n,制表符\t 等等
	 * Windows下回车键代表\r\n
	 */
	@Test
	public void testDelimiter() {
		System.out.println(java.lang.Character.isWhitespace('\n'));
		System.out.println(java.lang.Character.isWhitespace('\t'));
		System.out.println(java.lang.Character.isWhitespace('\r'));
		System.out.println(java.lang.Character.isWhitespace(' '));
		Scanner scanner = new Scanner("1, 2, 3, 4");
		scanner.useDelimiter("\\s*,\\s*");
		while (scanner.hasNextInt()) {
			System.out.println(scanner.next());
		}
		scanner.close();
	}

	/**
	 * 在配合正则表达式扫描时，需要注意：它仅仅针对下一个输入分词进行匹配，
	 * 如果正则表达式中含有定界符，则永远都不可能匹配成功
	 */
	@Test
	public void testPattern(){
		String s = 
			"123.206.216.23@01/12/2016\n" +
			"12.206.216.24@02/12/2016\n" +
			"23.206.216.25@03/12/2016\n" +
			"[Some Other log]\n" + 
			"1.206.216.23@01/12/2016\n" +
			"2.206.216.24@02/12/2016\n";
		Scanner scanner = new Scanner(s);
		String pattern = "(\\d+[.]\\d+[.]\\d+[.]\\d+)@" + 
				"(\\d{2}/\\d{2}/\\d{4})";
		while(scanner.hasNext()){
			if(scanner.hasNext(pattern)){  //scanner.hasNext(pattern) 需要满足下一个分词完全匹配该正则表达式才会返回true
				scanner.next(pattern);
				MatchResult match = scanner.match();
				String ip = match.group(1);
				String date = match.group(2);
				System.out.println("date:" + date + ",ip:" + ip);
			}else
				scanner.next();
		}
		scanner.close();
	}
	
	@Test
	public void testPattern2(){
		String s = "123.206.216.23@01/12/2016aa";
		String pattern = "(\\d+[.]\\d+[.]\\d+[.]\\d+)@" + 
				"(\\d{2}/\\d{2}/\\d{4})";
		Scanner sc = new Scanner(s);
		System.out.println(sc.hasNext()); // true
		System.out.println(sc.hasNext(pattern)); // false
		System.out.println(sc.next(pattern)); // 异常
		sc.close();
	}

}
