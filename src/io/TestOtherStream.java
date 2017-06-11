package io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.junit.Test;

public class TestOtherStream {
	
	@Test
	public void testData1(){
		DataInputStream dis = null;
		try{
			dis = new DataInputStream(new FileInputStream(new File("data.txt")));
			//不能这样读
//			byte[] b = new byte[20];
//			int len;
//			while((len = dis.read(b)) != -1){
//				System.out.println(new String(b,0,len));
//			}
			String str = dis.readUTF();
			System.out.println(str);
			boolean b = dis.readBoolean();
			System.out.println(b);
			long l = dis.readLong();
			System.out.println(l);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(dis != null){
				try {
					dis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	//数据流：用来处理基本数据类型、String、字节数组的数据:DataInputStream DataOutputStream
	@Test 
	public void testData(){
		DataOutputStream dos = null;
		try {
			FileOutputStream fos = new FileOutputStream("data.txt");
			dos = new DataOutputStream(fos);
			
			dos.writeUTF("我爱你，而你却不知道！");
			dos.writeBoolean(true);
			dos.writeLong(1432522344);
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(dos != null){
				try {
					dos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		
	}
	
	// 打印流：字节流：PrintStream 字符流：PrintWriter
	@Test
	public void testPrintStream() {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File("print.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// 创建打印输出流,设置为自动刷新模式(写入换行符或字节 '\n' 时都会刷新输出缓冲区)
		PrintStream ps = new PrintStream(fos, true);
		if (ps != null) { // 把标准输出流(控制台输出)改成文件
			System.setOut(ps);
		}
		for (int i = 1; i <= 255; i++) { // 输出ASCII字符
			System.out.print((char)i);
			if (i % 50 == 0) { // 每50个数据一行
				System.out.println(); // 换行
			}
		}
		ps.close();

	}
	
	@Test
	public void testPrintWriter() throws FileNotFoundException {
		File file = new File("file/scores.txt");
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
	
	
	/*
	 * 标准的输入输出流：
	 * 标准的输出流：System.out
	 * 标准的输入流：System.in
	 * 
	 * 题目：
	 * 从键盘输入字符串，要求将读取到的整行字符串转成大写输出。然后继续进行输入操作，
	 * 直至当输入“e”或者“exit”时，退出程序。

	 */
	@Test
	public void test2(){
		BufferedReader br = null;
		try {
			InputStream is = System.in;
			InputStreamReader isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			String str;
			while(true){
				System.out.println("请输入字符串：");
				str = br.readLine();
				if(str.equalsIgnoreCase("e") || str.equalsIgnoreCase("exit")){
					break;
				}
				String str1 = str.toUpperCase();
				System.out.println(str1);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}
	
	/*
	 * 如何实现字节流与字符流之间的转换：
	 * 转换流：InputStreamReader  OutputStreamWriter
	 * 编码：字符串  --->字节数组
	 * 解码：字节数组--->字符串
	 */
	@Test
	public void test1(){
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			//解码
			File file = new File("dbcp.txt");
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis, "GBK");
			br = new BufferedReader(isr);
			//编码
			File file1 = new File("dbcp4.txt");
			FileOutputStream fos = new FileOutputStream(file1);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "GBK");
			bw = new BufferedWriter(osw);
			String str;
			while((str = br.readLine()) != null){
				bw.write(str);
				bw.newLine();
				bw.flush();
			}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(bw != null){
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		
	}
}
