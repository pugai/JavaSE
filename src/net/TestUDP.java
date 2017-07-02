package net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

//UDP编程的实现
public class TestUDP {

	// 简单的UDP客户端，实现向服务器端发送系统时间功能

	// 该程序发送多次数据到服务器端
	@Test
	public void client() {
		DatagramSocket ds = null; // 连接对象
		DatagramPacket sendDp = null; // 发送数据包对象
		DatagramPacket receiveDp = null; // 接收数据包对象
		String serverHost = "127.0.0.1"; // 服务器IP
		int serverPort = 10016; // 服务器端口号

		try {
			ds = new DatagramSocket();
			byte[] receiveData = new byte[1024];
			receiveDp = new DatagramPacket(receiveData, receiveData.length);
			System.out.println("client is alreday!");
			for (int i = 0; i < 5; i++) {
				Date date = new Date();
				byte[] sendData = date.toString().getBytes();
				// 创建一个数据报：每一个数据报不能大于64k，都记录着数据信息，发送端的IP、端口号,以及要发送到
				// 的接收端的IP、端口号。
				sendDp = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(serverHost), serverPort);
				ds.send(sendDp);

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				ds.receive(receiveDp);
				String str = new String(receiveDp.getData(), 0, receiveDp.getLength());
				System.out.println("server response is: " + str);
				receiveDp.setLength(receiveData.length);

			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ds != null) {
				ds.close();
			}
		}

	}

	// 可以并发处理数据包的服务器端
	// 功能为：显示客户端发送的内容，并向客户端反馈字符串“OK”
	@Test
	public void server() {
		DatagramSocket ds = null;
		DatagramPacket receiveDp = null;
		int port = 10016;
		ExecutorService executor = Executors.newFixedThreadPool(10);

		try {
			ds = new DatagramSocket(port);
			byte[] receiveData = new byte[1024];
			System.out.println("server start!");

//			receiveDp = new DatagramPacket(receiveData, receiveData.length); // 用同一个数据包接收会出错，线程共享安全问题
			
			while (true) {
				//注意，每次接收时都 new 一个数据包，由于是在多线程环境下，
				//不能用一个包同时在逻辑线程中读取信息，又在server线程中等待接收
				receiveDp = new DatagramPacket(receiveData, receiveData.length);
				ds.receive(receiveDp);
				executor.execute(new UDPLogicThread(ds, receiveDp));
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ds != null) {
				ds.close();
			}
		}

	}
}

// 服务器端逻辑线程
class UDPLogicThread implements Runnable {

	DatagramSocket ds;
	DatagramPacket receiveDp;

	public UDPLogicThread(DatagramSocket ds, DatagramPacket receiveDp) {
		super();
		this.ds = ds;
		this.receiveDp = receiveDp;
	}

	@Override
	public void run() {
		try {
			InetAddress clientIP = receiveDp.getAddress();
			int clientPort = receiveDp.getPort();
			System.out.println("received data: " + new String(receiveDp.getData(), 0, receiveDp.getLength()));
			System.out.println("client IP is: " + clientIP.getHostAddress());
			System.out.println("client port is: " + clientPort);

			byte[] response = "OK".getBytes();
			DatagramPacket sendDp = new DatagramPacket(response, response.length, clientIP, clientPort);
			ds.send(sendDp);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
