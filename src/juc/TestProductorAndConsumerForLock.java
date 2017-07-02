package juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * 线索：if else --> if(去else,防止线程无法结束) --> while(if改为while,将wait()总是使用在循环中,解决虚假唤醒)
 */
public class TestProductorAndConsumerForLock {

	public static void main(String[] args) {
		Clerk2 clerk = new Clerk2();
		Productor2 p = new Productor2(clerk);
		Consumer2 c = new Consumer2(clerk);
		
		new Thread(p, "生产者A").start();
		new Thread(c, "消费者B").start();
		
		new Thread(p, "生产者C").start();
		new Thread(c, "消费者D").start();
	}
	
}

//店员
class Clerk2 {
	private int product = 0;
	
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	
	//进货
	public /*synchronized*/ void get() {
		
		lock.lock();
		
		try {
			while (product >= 1) { // 为了避免虚假唤醒问题，应该将wait()总是使用在循环中
				System.out.println(Thread.currentThread().getName() + ":" + "产品已满！");
				
				try {
					condition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
			System.out.println(Thread.currentThread().getName() + ":" + ++product);
			condition.signalAll();
		} finally {
			lock.unlock();
		}
	}
	
	//卖货
	public /*synchronized*/ void sale() {
		
		lock.lock();
		
		try {
			while (product <= 0) {
				System.out.println(Thread.currentThread().getName() + ":" + "缺货！");
				
				try {
					condition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
			System.out.println(Thread.currentThread().getName() + ":" + --product);
			condition.signalAll();
		} finally {
			lock.unlock();
		}
	}
	
}

//生产者
class Productor2 implements Runnable {

	private Clerk2 clerk;
	
	public Productor2(Clerk2 clerk) {
		this.clerk = clerk;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < 20; i++) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			clerk.get();
		}
	}
}

//消费者
class Consumer2 implements Runnable {

	private Clerk2 clerk;
	
	public Consumer2(Clerk2 clerk) {
		this.clerk = clerk;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < 20; i++) {
			clerk.sale();
		}
	}
	
}


