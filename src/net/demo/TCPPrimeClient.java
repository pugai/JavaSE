package net.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 以TCP方式实现的质数判断客户端程序
 * 
 * @author tianlong
 *
 */
public class TCPPrimeClient {
	static BufferedReader br;
	static Socket socket;
	static InputStream is;
	static OutputStream os;

	final static String HOST = "127.0.0.1";
	final static int PORT = 11111;

	public static void main(String[] args) {
		init();
		System.out.println("client start");
		while (true) {
			System.out.println("please enter a number: ");
			String input = readInput();// 读取输入
			if (isQuit(input)) {// 判断是否结束
				byte[] b = "quit".getBytes();
				send(b);
				break;// 结束程序
			}
			if (isLegal(input)) {// 判断是否合法
				send(input.getBytes());// 发送数据
				byte[] data = receive();// 接受数据
				parse(data);// 解析反馈数据
			} else {
				System.out.println("input is not legal");
			}
		}
		close();// 关闭流和连接
	}

	/**
	 * 初始化
	 */
	private static void init() {
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			socket = new Socket(HOST, PORT);
			is = socket.getInputStream();
			os = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取输入
	 * 
	 * @return
	 */
	private static String readInput() {
		try {
			return br.readLine();
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * 判断是否输入quit
	 * 
	 * @param input
	 * @return
	 */
	private static boolean isQuit(String input) {
		if (input == null) {
			return false;
		} else if ("quit".equalsIgnoreCase(input)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 向服务器端发送数据
	 * 
	 * @param b
	 */
	private static void send(byte[] b) {
		try {
			os.write(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 校验输入
	 * 
	 * @param input
	 * @return
	 */
	private static boolean isLegal(String input) {
		if (input == null) {
			return false;
		} else {
			try {
				int n = Integer.parseInt(input);
				if (n >= 2) {
					return true;
				} else {
					return false;
				}
			} catch (NumberFormatException e) {
				return false;
			}
		}
	}

	/**
	 * 接收服务器端反馈
	 * 
	 * @return
	 */
	private static byte[] receive() {
		byte[] b = new byte[1024];
		try {
			int len = is.read(b);
			// 复制有效数据
			byte[] data = new byte[len];
			System.arraycopy(b, 0, data, 0, len);
			return data;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * 解析协议数据
	 * 
	 * @param data
	 */
	private static void parse(byte[] data) {
		if (data == null) {
			System.out.println("server response is null");
			return;
		}
		byte value = data[0];// 取第一个byte
		// 按照协议格式解析
		switch (value) {
		case 0:
			System.out.println("is prime number");
			break;
		case 1:
			System.out.println("is not primr number");
			break;
		case 2:
			System.out.println("something is wrong");
			break;
		}
	}

	/**
	 * 关闭流和连接
	 */
	private static void close() {
		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
