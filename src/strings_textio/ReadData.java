package strings_textio;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 使用Scanner读数据
 * @author tianlong
 *
 */
public class ReadData {

	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("scores.txt");
		Scanner input = new Scanner(file);
//		Scanner input = new Scanner(System.in); //从键盘输入
		while(input.hasNext()){
			String firstName = input.next();
			String mi = input.next();
			String lastName = input.next();
			int score = input.nextInt();
			System.out.println(firstName + " " + mi + " " + lastName + " " + score);
		}
		
		input.close();
	}
	
}
