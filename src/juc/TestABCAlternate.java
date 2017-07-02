package juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * 编写一个程序，开启 3 个线程，这三个线程的 ID 分别为 A、B、C，每个线程将自己的 ID 在屏幕上打印 10 遍，要求输出的结果必须按顺序显示。
 *	如：ABCABCABC…… 依次递归
 */
public class TestABCAlternate {
	
	public static void main(String[] args) {
		AlternateDemo ad = new AlternateDemo();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 20; i++) {
					ad.PrintA(i + 1);
				}
			}
		}, "A").start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 20; i++) {
					ad.PrintB(i + 1);
				}
			}
		}, "B").start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 20; i++) {
					ad.PrintC(i + 1);
				}
			}
		}, "C").start();
		
	}

}

class AlternateDemo {
	
	private int num = 1;
	
	private Lock lock = new ReentrantLock();
	
	private Condition condition1 = lock.newCondition();
	private Condition condition2 = lock.newCondition();
	private Condition condition3 = lock.newCondition();
	
	public void PrintA(int loopNo){
		lock.lock();
		try {
			//1.判断
			while(num != 1) {
				condition1.await();
			}
			
			//2.打印
			for (int i = 0; i < 1; i++) {
				System.out.println(Thread.currentThread().getName() +"\t" + loopNo);
			}
			
			//3.唤醒
			num = 2;
			condition2.signal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	public void PrintB(int loopNo){
		lock.lock();
		try {
			//1.判断
			while(num != 2) {
				condition2.await();
			}
			
			//2.打印
			for (int i = 0; i < 2; i++) {
				System.out.println(Thread.currentThread().getName() +"\t" + loopNo);
			}
			
			//3.唤醒
			num = 3;
			condition3.signal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	public void PrintC(int loopNo){
		lock.lock();
		try {
			//1.判断
			while(num != 3) {
				condition3.await();
			}
			
			//2.打印
			for (int i = 0; i < 3; i++) {
				System.out.println(Thread.currentThread().getName() +"\t" + loopNo);
			}
			
			//3.唤醒
			num = 1;
			condition1.signal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
}
