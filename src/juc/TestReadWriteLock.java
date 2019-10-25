package juc;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
 * 1.ReadWriteLock：读写锁
 * 
 * 写写/读写：需要互斥
 * 读读：不需要互斥
 */
public class TestReadWriteLock {
	
	public static void main(String[] args) {
		ReadWriteLockDemo rw = new ReadWriteLockDemo();
		
		new Thread(() -> rw.set((int)(Math.random() * 101)), "Write").start();
		
		for (int i = 0; i < 100; i++) {
			new Thread(() -> rw.get()).start();
		}
		
	}

}

class ReadWriteLockDemo {
	private int num = 0;

	private ReadWriteLock lock = new ReentrantReadWriteLock();
	
	//读
	public void get() {
		lock.readLock().lock();
		try {
			System.out.println(Thread.currentThread().getName() + ": " + num);
		} finally {
			lock.readLock().unlock();
		}
	}
	
	//写
	public void set(int num){
		lock.writeLock().lock();
		try {
			System.out.println(Thread.currentThread().getName() + ": " + num);
			this.num = num;
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} finally {
			lock.writeLock().unlock();
		}
	}
}
