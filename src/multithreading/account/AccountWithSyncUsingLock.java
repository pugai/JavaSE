package multithreading.account;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用显式锁使线程同步
 * @author tianlong
 *
 */
public class AccountWithSyncUsingLock {

	private static Account account = new Account();

	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		for (int i = 0; i < 100; i++) {
			executor.execute(new AddPennyTask());
		}
		executor.shutdown();

		while (!executor.isTerminated()) {
		}

		System.out.println("What is balance?" + account.getBalance());
	}

	private static class AddPennyTask implements Runnable {
		@Override
		public void run() {
			account.deposit(1);
		}
	}

	private static class Account {
		//创建一个锁
		private static Lock lock = new ReentrantLock();
		private int balance = 0;

		public int getBalance() {
			return balance;
		}

		// 使用显式锁使线程同步
		public void deposit(int amount) {
			//获取锁
			lock.lock();
			try {
				int newBalance = balance + amount;
				Thread.sleep(1);
				balance = newBalance;
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}

		}
	}

}
