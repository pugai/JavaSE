package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * 使用PrintWriter写数据
 * @author tianlong
 *
 */
public class WriteDataByPrintWriter {

	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("scores.txt");
		if(file.exists()){
			System.out.println("File already exists");
			System.exit(0);
		}
		
		PrintWriter output = new PrintWriter(file); //将创建一个新文件，如果已经存在，则内容删除
		
		output.print("John T Smith ");
		output.println(90);
		output.print("Eric K Jones ");
		output.println(80);
		
		output.close();
	}
	
}
