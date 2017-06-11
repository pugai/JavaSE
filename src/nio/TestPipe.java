package nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

import org.junit.Test;

public class TestPipe {

	//不同线程可以共用一个管道，用于传输数据
	@Test
	public void test1() throws IOException{
		//1. 获取管道
		Pipe pipe = Pipe.open();
		
		//2. 将缓冲区中的数据写入管道
		ByteBuffer buf = ByteBuffer.allocate(1024);
		
		Pipe.SinkChannel sinkChannel = pipe.sink();
		buf.put("通过单向管道发送数据".getBytes());
		buf.flip();
//		System.out.println(buf.position());
//		System.out.println(buf.limit());
		sinkChannel.write(buf);
//		System.out.println(buf.position());
//		System.out.println(buf.limit());
		buf.clear();
//		System.out.println(buf.position());
//		System.out.println(buf.limit());
		
		//3. 读取缓冲区中的数据
		Pipe.SourceChannel sourceChannel = pipe.source();
		int len = sourceChannel.read(buf);
//		System.out.println(buf.position());
//		System.out.println(buf.limit());
		buf.flip();
//		System.out.println(buf.position());
//		System.out.println(buf.limit());
//		System.out.println(buf.array().length);
		System.out.println(new String(buf.array(), 0, len));
		
		sourceChannel.close();
		sinkChannel.close();
	}
	
}
