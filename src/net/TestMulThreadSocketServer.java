package net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

/**
 * 多线程服务器
 * @author tianlong
 *
 */
public class TestMulThreadSocketServer {

	@Test
	public void client() {
		String serverIP = "127.0.0.1";
		int port = 10001;
		Socket socket = null;
		InputStream is = null;
		OutputStream os = null;
		String[] data = { "First", "Second", "Third","Fouth" };

		try {
			socket = new Socket(InetAddress.getByName(serverIP), port);
			os = socket.getOutputStream();
			is = socket.getInputStream();
			byte[] b = new byte[1024];
			int len;
			for (int i = 0; i < 4; i++) {
				os.write(data[i].getBytes("UTF-8"));
				len = is.read(b);
				System.out.println("服务器反馈：" + new String(b, 0, len, "UTF-8"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
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

	@Test
	public void server() {
		int port = 10001;
		ServerSocket serverSocket = null;
		Socket socket = null;
		ExecutorService executor = Executors.newFixedThreadPool(5);
		try {
			serverSocket = new ServerSocket(port);
			while (true) {
				socket = serverSocket.accept();
				executor.execute(new TCPLogicThread(socket));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (serverSocket != null) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}

	}

}

// 服务器端逻辑线程
class TCPLogicThread implements Runnable {
	Socket socket = null;
	InputStream is = null;
	OutputStream os = null;

	public TCPLogicThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			is = socket.getInputStream();
			os = socket.getOutputStream();
			byte[] b = new byte[1024];
			int len;
			for (int i = 0; i < 4; i++) {
				len = is.read(b);
				System.out.println("客户端发送消息为：" + new String(b, 0, len, "UTF-8"));
				byte[] response = logic(b, 0, len);
				
				try {
					Thread.sleep(1000); // 模拟服务器处理时间
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				os.write(response);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	/**
	 * 逻辑处理方法,实现echo逻辑
	 * 
	 * @param b
	 *            客户端发送数据缓冲区
	 * @param off
	 *            起始下标
	 * @param len
	 *            有效数据长度
	 * @return
	 */
	private byte[] logic(byte[] b, int off, int len) {
		byte[] response = new byte[len];
		System.arraycopy(b, off, response, 0, len);
		return response;
	}

	/**
	 * 关闭流和连接
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
