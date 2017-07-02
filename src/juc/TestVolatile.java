package juc;


/*
 * 一、volatile 关键字：当多个线程操作共享数据时，可以保证内存中的数据可见。工作内存每次都与主存同步（近似于就是在主存中操作，读和写）
 * 		用 volatile 性能会下降（JVM底层会对代码进行重排序优化，使用 volatile 会禁止重排序），但比锁高。
 * 		相较于 synchronized 是一种较为轻量级的同步策略
 * 
 * 注意：
 * 1、volatile 不具备“互斥性”
 * 2、volatile 不能保证变量的“原子性”
 */
public class TestVolatile {

	public static void main(String[] args) {
		ThreadDemo td = new ThreadDemo();
		
		new Thread(td).start();
		
		// while(true)执行速度非常快
		while(true) {
			
			if (td.isFlag()) {
				System.out.println("------");
				break;
			}
			
			//使用同步锁，每次都会从主存中取，不过效率低
//			synchronized (td) {
//				if (td.isFlag()) {
//					System.out.println("------");
//					break;
//				}
//			}
		}
	}
}

class ThreadDemo implements Runnable {

	// 添加 volatile 保证内存可见
	private volatile boolean flag = false;
	
	@Override
	public void run() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		flag = true;
		System.out.println("flag = " + isFlag());
	}

	public boolean isFlag() {
		return flag;
	}
	
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
}
