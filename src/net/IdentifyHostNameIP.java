package net;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 通过主机名创建InetAddress类
 * @author tianlong
 *
 */
public class IdentifyHostNameIP {
	public static void main(String[] args) {
		String host = "www.163.com";
			try {
				InetAddress address = InetAddress.getByName(host);
				System.out.println("Host name: " + address.getHostName() + " ");
				System.out.println("IP address: " + address.getHostAddress());
			} catch (UnknownHostException ex) {
				System.err.println("Unknown host or IP address " + host);
			}
		
	}
}
