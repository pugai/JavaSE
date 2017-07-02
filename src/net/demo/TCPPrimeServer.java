package net.demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 以TCP方式实现的质数判别服务器端, 见readme.txt
 * 
 * @author tianlong
 *
 */
public class TCPPrimeServer {
	static ServerSocket ss;
	static ExecutorService executor;
	final static int PORT = 11111;

	public static void main(String[] args) {
		executor = Executors.newFixedThreadPool(5);
		try {
			ss = new ServerSocket(PORT);
			System.out.println("TCPPrimeServer started at " + new Date());
			int clientNo = 1;
			while (true) {
				Socket socket = ss.accept();
				System.out.println("Starting thread for client " + clientNo + " at " + new Date());
				InetAddress inetAddress = socket.getInetAddress();
				System.out.println("Client " + clientNo + "'s host name is " + inetAddress.getHostName());
				System.out.println("Client " + clientNo + "'s IP Address is " + inetAddress.getHostAddress());
				executor.execute(new PrimeLogicThread(socket));
				clientNo++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ss != null) {
				try {
					ss.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}

/**
 * 实现质数判别逻辑的线程
 */
class PrimeLogicThread implements Runnable {
	Socket socket;
	InputStream is;
	OutputStream os;

	public PrimeLogicThread(Socket socket) {
		this.socket = socket;
		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		try {
			is = socket.getInputStream();
			os = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (true) {
			// 接收客户端反馈
			byte[] data = receive();
			// 判断是否是退出
			if (isQuit(data)) {
				break; // 结束循环
			}
			// 逻辑处理
			byte[] b = logic(data);
			// 反馈数据
			send(b);
		}
		close();
	}

	/**
	 * 接收客户端数据
	 * 
	 * @return 客户端发送的数据
	 */
	private byte[] receive() {
		byte[] b = new byte[1024];
		try {
			int n = is.read(b);
			byte[] data = new byte[n];
			// 复制有效数据
			System.arraycopy(b, 0, data, 0, n);
			return data;
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 向客户端发送数据
	 * 
	 * @param data
	 *            数据内容
	 */
	private void send(byte[] data) {
		try {
			os.write(data);
		} catch (Exception e) {
		}
	}

	/**
	 * 判断是否是quit
	 * 
	 * @return 是返回true，否则返回false
	 */
	private boolean isQuit(byte[] data) {
		if (data == null) {
			return false;
		} else {
			String s = new String(data);
			if (s.equalsIgnoreCase("quit")) {
				return true;
			} else {
				return false;
			}
		}
	}

	private byte[] logic(byte[] data) {
		// 反馈数组
		byte[] b = new byte[1];
		// 校验参数
		if (data == null) {
			b[0] = 2;
			return b;
		}
		try {
			// 转换为数字
			String s = new String(data);
			int n = Integer.parseInt(s);
			// 判断是否是质数
			if (n >= 2) {
				boolean flag = isPrime(n);
				if (flag) {
					b[0] = 0;
				} else {
					b[0] = 1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			b[0] = 2;
		}
		return b;
	}

	/**
	 *
	 * @param n
	 * @return
	 */
	private boolean isPrime(int n) {
		boolean b = true;
		int squareRoot = (int) Math.sqrt(n);
		for (int i = 2; i <= squareRoot; i++) {
			if (n % i == 0) {
				b = false;
				break;
			}
		}
		return b;
	}

	/**
	 * 关闭连接
	 */
	private void close() {
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
