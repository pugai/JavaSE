package nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.SortedMap;

import org.junit.Test;

/**
 * 1、通道 Channel：用于源节点与目标节点的连接。在Java NIO中负责缓冲区中数据的传输，Channel本身不存储数据，需要配合缓冲区进行传输
 * 
 * 2、通道主要实现类
 * 	java.nio.channels.Channel 接口：
 * 		|--FileChannel
 * 		|--SocketChannel
 * 		|--ServerSocketChannel
 * 		|--DatagramChannel
 * 
 * 3、获取通道
 * 	1、Java 针对支持通道的类提供了getChannel() 方法
 * 		本地IO：
 * 		FileInputStream/FileOutputStream/RandomAccessFile
 *		
 *		网络IO
 *		Socket
 *		ServerSocket
 *		DatagramSocket
 *
 *	2、JDK1.7中的 NIO2 针对各个通道提供了静态方法 open()
 *	3、JDK1.7中的 NIO2 的 Files 工具类的 newByteChannel()
 *
 * 4、通道之间的数据传输
 * 	transferFrom()
 * 	transferTo()
 * 
 * 5、分散（Scatter）与聚集（Gather）
 * 	分散读取（Scattering Reads）：将通道中的数据分散到多个缓冲区中
 * 	聚集写入（Gathering Writes）：将多个缓冲区中的数据聚集到通道中
 * 
 * 6、字符集：Charset
 * 	编码：字符串 -> 字节数组
 * 	解码：字节数组 -> 字符串
 *
 */
public class TestChannel {
	
	//编码、解码
	@Test
	public void test5() throws IOException {
		SortedMap<String,Charset> availableCharsets = Charset.availableCharsets();
		
		for (Map.Entry<String, Charset> entry : availableCharsets.entrySet()) {
			System.out.println(entry.getKey() + "=" + entry.getValue());
		}
		
		Charset cs1 = Charset.forName("GBK");
		
		//获取编码器
		CharsetEncoder ce = cs1.newEncoder();
		
		//获取解码器
		CharsetDecoder cd = cs1.newDecoder();
		
		CharBuffer cBuf = CharBuffer.allocate(1024);
		cBuf.put("同济大学！");
		cBuf.flip();
		
		// 编码
		ByteBuffer bBuf = ce.encode(cBuf);
		
		for (int i = 0; i < 10; i++) {
			System.out.println(bBuf.get());
		}
		
		// 解码
		bBuf.flip();
		CharBuffer cBuf2 = cd.decode(bBuf);
		System.out.println(cBuf2.toString());
		
		System.out.println("------用不同解码器，出现乱码------");
		
		Charset cs = Charset.forName("UTF-8");
		bBuf.flip();
		CharBuffer cBuf3 = cs.decode(bBuf);
		System.out.println(cBuf3.toString());
		
	}
	
	
	//分散（Scatter）与聚集（Gather）
	@Test
	public void test4() throws IOException {
		RandomAccessFile raf1 = new RandomAccessFile("file/test.txt", "rw");
		
		//1、获取通道
		FileChannel channel1 = raf1.getChannel();
		
		//2、分配指定大小的缓冲区
		ByteBuffer buf1 = ByteBuffer.allocate(64);
		ByteBuffer buf2 = ByteBuffer.allocate(1024);
		
		//3、分散读取
		ByteBuffer[] bufs = {buf1, buf2};
		channel1.read(bufs);
		
		for (ByteBuffer byteBuffer : bufs) {
			byteBuffer.flip();
		}
		
		System.out.println(new String(bufs[0].array(), 0, bufs[0].limit()));
		System.out.println("------");
		System.out.println(new String(bufs[1].array(), 0, bufs[1].limit()));
		
		//4、聚集写入
		RandomAccessFile raf2 = new RandomAccessFile("file/test2.txt", "rw");
		FileChannel channel2 = raf2.getChannel();
		
		channel2.write(bufs);
		
	}
	
	//通道之间的数据传输（使用直接缓冲区）
	@Test
	public void test3() throws IOException {
		FileChannel inChannel = FileChannel.open(Paths.get("image", "tree.jpg"), StandardOpenOption.READ);
		FileChannel outChannel = FileChannel.open(Paths.get("image", "tree2.jpg"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);
		
		//使用直接缓冲区
//		inChannel.transferTo(0, inChannel.size(), outChannel); // 与下行代码等效
		outChannel.transferFrom(inChannel, 0, inChannel.size());
		
		outChannel.close();
		inChannel.close();
	}
	
	//使用直接缓冲区完成文件复制（内存映射文件）
	// 效率很高，但是不一定稳定，垃圾回收可能不及时释放资源，会一直占用物理内存，特别是操作大文件时
	@Test
	public void test2() throws IOException {
		FileChannel inChannel = FileChannel.open(Paths.get("image", "tree.jpg"), StandardOpenOption.READ);
		// CREATE_NEW 若文件存在会报错，CREATE 文件存在则会覆盖原有内容
		FileChannel outChannel = FileChannel.open(Paths.get("image", "3.jpg"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);
		
		//内存映射文件，只支持ByteBuffer
		MappedByteBuffer inMappedBuf = inChannel.map(MapMode.READ_ONLY, 0, inChannel.size());
		MappedByteBuffer outMappedBuf = outChannel.map(MapMode.READ_WRITE, 0, inChannel.size());
		
		// 直接对缓冲区进行数据读写操作
		byte[] dst = new byte[inMappedBuf.limit()];
		inMappedBuf.get(dst);
		outMappedBuf.put(dst);
		
		inChannel.close();
		outChannel.close();
		
	}
	
	//利用通道完成文件复制（非直接缓冲区）
	@Test
	public void test1() {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		FileChannel inChannel = null;
		FileChannel outChannel = null;
		try {
			fis = new FileInputStream("image/1.jpg");
			fos = new FileOutputStream("image/2.jpg");
			
			//1、获取通道
			inChannel = fis.getChannel();
			outChannel = fos.getChannel();
			
			//2、分配指定大小缓冲区
			ByteBuffer buf = ByteBuffer.allocate(1024);
			
			//3、将通道中的数据存入缓冲区
			while(inChannel.read(buf) != -1) {
				buf.flip(); // 切换成读数据模式
				//4、将缓冲区中数据写入通道
				outChannel.write(buf);
				buf.clear();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (outChannel != null) {
				try {
					outChannel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (inChannel != null) {
				try {
					inChannel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

}
