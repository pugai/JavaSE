package io;

import java.io.File;
import java.util.Date;

/**
 * 文件类的简单实例
 * @author tianlong
 *
 */
public class TestFileClass {

	public static void main(String[] args) {
		File file = new File("image/1.jpg");  //不会创建文件，不管文件存不存在
		System.out.println("Does it exist?" + file.exists());
		System.out.println("Can it be written?" + file.canWrite());
		System.out.println("Can it be read?" + file.canRead());
		System.out.println("Is it a directory?" + file.isDirectory());
		System.out.println("Is it a file?" + file.isFile());
		System.out.println("Is it absolute?" + file.isAbsolute());
		System.out.println("Is it hidden?" + file.isHidden());
		System.out.println("Absolute path is " + file.getAbsolutePath());
		System.out.println("Last modified on " + new Date(file.lastModified()));
	}
	
}
