package io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * 文件复制，利用二进制流
 * @author tianlong
 *
 */
public class Copy {
	
	/**
	 * 
	 * @param args[0] source file
	 * @param args[1] target file
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		if (args.length != 2) {
			System.out.println("Usage: java Copy sourceFile targetFile");
			System.exit(0);
		}

		File sourceFile = new File(args[0]);
		if (!sourceFile.exists()) {
			System.out.println("Source file " + args[0] + "is not exist");
			System.exit(0);
		}

		File targetFile = new File(args[1]);
		if (targetFile.exists()) {
			System.out.println("Target file " + args[1] + "is already exist");
			System.exit(0);
		}

		BufferedInputStream input = new BufferedInputStream(new FileInputStream(sourceFile));

		BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(targetFile));

		System.out.println("The file " + args[0] + "has" + input.available() + "bytes");

		int r;
		while ((r = input.read()) != -1)
			output.write((byte) r);

		input.close();
		output.close();

		System.out.println("Copy done");
	}

}
