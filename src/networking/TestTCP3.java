package networking;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Test;

//TCP����������ӿͻ��˷����ļ�������ˣ�����˱��浽���ء������ء����ͳɹ������ͻ��ˡ����ر���Ӧ�����ӡ�
//���µĳ��򣬴����쳣ʱ��Ҫʹ��try-catch-finally!!������Ϊ����д����~
public class TestTCP3 {
	@Test
	public void client() throws Exception {
		// 1.����Socket�Ķ���
		Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), 9898);
		// 2.�ӱ��ػ�ȡһ���ļ����͸������
		OutputStream os = socket.getOutputStream();
		FileInputStream fis = new FileInputStream(new File("1.jpg"));
		byte[] b = new byte[1024];
		int len;
		while ((len = fis.read(b)) != -1) {
			os.write(b, 0, len);
		}
		socket.shutdownOutput();
		// 3.���������ڷ���˵���Ϣ
		InputStream is = socket.getInputStream();
		byte[] b1 = new byte[1024];
		int len1;
		while ((len1 = is.read(b1)) != -1) {
			String str = new String(b1, 0, len1);
			System.out.print(str);
		}
		// 4.�ر���Ӧ������Socket����
		is.close();
		os.close();
		fis.close();
		socket.close();
	}

	@Test
	public void server() throws Exception {
		// 1.����һ��ServerSocket�Ķ���
		ServerSocket ss = new ServerSocket(9898);
		// 2.������accept()����������һ��Socket�Ķ���
		Socket s = ss.accept();
		// 3.���ӿͻ��˷���������Ϣ���浽����
		InputStream is = s.getInputStream();
		FileOutputStream fos = new FileOutputStream(new File("3.jpg"));
		byte[] b = new byte[1024];
		int len;
		while ((len = is.read(b)) != -1) {
			fos.write(b, 0, len);
		}
		System.out.println("�յ�������" + s.getInetAddress().getHostAddress() + "���ļ�");
		// 4.����"���ճɹ�"����Ϣ�������ͻ���
		OutputStream os = s.getOutputStream();
		os.write("�㷢�͵�ͼƬ���ѽ��ճɹ���".getBytes());
		// 5.�ر���Ӧ������Socket��ServerSocket�Ķ���
		os.close();
		fos.close();
		is.close();
		s.close();
		ss.close();
	}
}
