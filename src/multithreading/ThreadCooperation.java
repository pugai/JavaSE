package multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程间协作，利用具有条件的锁
 * @author tianlong
 *
 */
public class ThreadCooperation {
	private static Account account = new Account();

	public static void main(String[] args) {
		// Create a thread pool with two threads
		ExecutorService executor = Executors.newFixedThreadPool(2);
		System.out.println("Thread 1\t\tThread 2\t\tBalance");
		executor.execute(new DepositTask());
		executor.execute(new WithdrawTask());
		executor.shutdown();

	}

	// A task for adding an amount to the account
	public static class DepositTask implements Runnable {
		public void run() {
			try { // Purposely delay it to let the withdraw method proceed
				while (true) {
					account.deposit((int) (Math.random() * 10) + 1);
					Thread.sleep(1000);
				}
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

	// A task for subtracting an amount from the account
	public static class WithdrawTask implements Runnable {
		public void run() {
			while (true) {
				account.withdraw((int) (Math.random() * 10) + 1);
			}
		}
	}

	// An inner class for account
	private static class Account {
		// Create a new lock
		private static Lock lock = new ReentrantLock();

		// Create a condition
		private static Condition newDeposit = lock.newCondition();

		private int balance = 0;

		public int getBalance() {
			return balance;
		}

		public void withdraw(int amount) {
			lock.lock(); // Acquire the lock
			try {
				while (balance < amount) {
					System.out.println("\t\t\tWait for a deposit");
					//使用前必须先获取锁，即加锁
					//让当前线程等待并自动释放条件上的锁
					newDeposit.await();
				}
				balance -= amount;
				System.out.println("\t\t\tWithdraw " + amount + "\t\t" + getBalance());
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			} finally {
				lock.unlock(); // Release the lock
			}
		}

		public void deposit(int amount) {
			lock.lock(); // Acquire the lock
			try {
				balance += amount;
				System.out.println("Deposit " + amount + "\t\t\t\t\t" + getBalance());

				// Signal thread waiting on the condition
				//唤醒所有等待线程
				newDeposit.signalAll();
			} finally {
				lock.unlock(); // Release the lock
			}
		}
	}
}
