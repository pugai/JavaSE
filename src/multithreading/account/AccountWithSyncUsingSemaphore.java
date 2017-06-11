package multithreading.account;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 只用一个许可的信号量可以用来模拟一个相互排斥的锁，确保同一时间只有一个线程可以访问deposit方法，线程同步
 * @author tianlong
 *
 */
public class AccountWithSyncUsingSemaphore {

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
		// 创建一个只有一个许可的信号量，用来模拟一个相互排斥的锁
		private static Semaphore semaphore = new Semaphore(1);
		private int balance = 0;

		public int getBalance() {
			return balance;
		}

		public void deposit(int amount) {
			try {
				//获取一个许可
				semaphore.acquire();
				int newBalance = balance + amount;
				Thread.sleep(5);
				balance = newBalance;
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				//释放一个许可
				semaphore.release();
			}

		}
	}

}
