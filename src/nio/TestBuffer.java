package nio;

import java.nio.ByteBuffer;

import org.junit.Test;

/**
 * 1、缓冲区 Buffer ：在NIO中负责存取数据，底层是数组，用于存储不同数据类型的数据
 * 不同数据类型都有相应类型的缓冲区（boolea除外）
 * ByteBuffer
 * CharBuffer
 * ShortBuffer
 * IntBuffer
 * LongBuffer
 * FloatBuffer
 * DoubleBuffer
 * 
 * 上述缓冲区管理方式几乎一致，通过 allocate() 获取缓冲区
 *
 * 2、缓冲区存取数据的两个核心方法
 * put() 存
 * get() 取
 * 
 * 3、缓冲区中的4个核心属性
 * capacity：容量，最大存储数据容量，声明便不能改变
 * limit：界限，缓冲区中可以操作数据的大小（limit后面的数据不能进行读写）
 * position：位置，缓冲区中正在操作数据的位置
 * 
 * mark：标记，可以记录当前position的位置，通过reset() 恢复到mark 的位置
 * 
 * 0 <= mark <= position <= limit <= capacity
 * 
 * 4、直接缓冲区、非直接缓冲区
 * 非直接缓冲区：通过allocate() 方法分配缓冲区，将缓冲区建立在JVM的内存中
 * 直接缓冲区：通过allocateDirect() 方法分配直接缓冲区，将缓冲区建立在操作系统的物理内存中，可以提高效率
 * 
 */
public class TestBuffer {
	
	@Test
	public void test3() {
		// 分配直接缓冲区
		ByteBuffer buf = ByteBuffer.allocateDirect(1024);
		
		System.out.println(buf.isDirect());
	}
	
	
	@Test
	public void test2() {
		String str = "abcde";
		
		ByteBuffer buf = ByteBuffer.allocate(1024);
		
		buf.put(str.getBytes());
		
		buf.flip();
		
		byte[] dst = new byte[buf.limit()];
		buf.get(dst, 0, 2);
		System.out.println(new String(dst, 0, 2));
		
		System.out.println(buf.position());
		
		buf.mark(); //用mark标记position位置
		buf.get(dst, 2, 2);
		System.out.println(new String(dst, 2, 2));
		System.out.println(buf.position());
		
		buf.reset(); // 令position恢复到mark的位置
		System.out.println(buf.position());
		
		//判断是否还有数据
		if (buf.hasRemaining()) {
			//还有几个数据
			System.out.println(buf.remaining());
		}
		
	}

	@Test
	public void test1() {
		String str = "abcde";
		
		//1、分配指定大小的缓冲区
		ByteBuffer buf = ByteBuffer.allocate(1024);
		
		System.out.println("------allocate()------");
		System.out.println(buf.position());
		System.out.println(buf.limit());
		System.out.println(buf.capacity());
		
		//2、put()写数据
		buf.put(str.getBytes());
		System.out.println("------put()------");
		System.out.println(buf.position());
		System.out.println(buf.limit());
		System.out.println(buf.capacity());
		
		//3、通过flip()方法切换到读取数据的模式，会改变position和limit的值
		// position -> limit 指示实际可读取的数据坐标
		buf.flip();
		System.out.println("------flip()------");
		System.out.println(buf.position());
		System.out.println(buf.limit());
		System.out.println(buf.capacity());
		
		//4、利用 get() 读取缓冲区的数据
		byte[] dst = new byte[buf.limit()];
		buf.get(dst);
		System.out.println("------get()------");
		System.out.println(new String(dst, 0, dst.length));
		System.out.println(buf.position());
		System.out.println(buf.limit());
		System.out.println(buf.capacity());
		
		//5、rewind() 可重复读
		buf.rewind();
		System.out.println("------rewind()------");
		System.out.println(buf.position());
		System.out.println(buf.limit());
		System.out.println(buf.capacity());
		
		//6、clear() 清空缓冲区，但是缓冲区中的数据依然存在，只是数据处于“被遗忘”状态
		//指针都复位
		buf.clear();
		System.out.println("------clear()------");
		System.out.println(buf.position());
		System.out.println(buf.limit());
		System.out.println(buf.capacity());
		
		System.out.println((char) buf.get()); //证实数据仍存在
		System.out.println(buf.position());
		
	}
	
	
}
