package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.junit.Test;

public class TestBlockingNIO2 {
	
	//客户端
	@Test
	public void client() throws IOException {
		SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
		
		FileChannel inChannel = FileChannel.open(Paths.get("image", "1.jpg"), StandardOpenOption.READ);
		
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		
		while(inChannel.read(buffer) != -1) {
			buffer.flip();
			sChannel.write(buffer);
			buffer.clear();
		}
		
		sChannel.shutdownOutput(); // 若不添加，则会一直阻塞，服务端不知道客户端什么时候发送结束
		
		// 接收服务端反馈
		while(sChannel.read(buffer) != -1) {
			buffer.flip();
			System.out.println(new String(buffer.array(), 0, buffer.limit()));
			buffer.clear();
		}
		
		sChannel.close();
		inChannel.close();
		
	}
	
	//服务端
	@Test
	public void server() throws IOException {
		ServerSocketChannel ssChannel = ServerSocketChannel.open();
		
		ssChannel.bind(new InetSocketAddress(9898));
		
		SocketChannel sChannel = ssChannel.accept();
		
		FileChannel outChannel = FileChannel.open(Paths.get("image", "socket2.jpg"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
		
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		
		while(sChannel.read(buffer) != -1) {
			buffer.flip();
			outChannel.write(buffer);
			buffer.clear();
		}
		
		// 发送反馈给客户端
		buffer.put("服务端接收成功".getBytes());
		buffer.flip();
		sChannel.write(buffer);
		
		outChannel.close();
		sChannel.close();
		ssChannel.close();
		
		
	}

}
