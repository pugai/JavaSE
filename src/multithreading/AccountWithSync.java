package multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程同步，使用synchronized
 * @author tianlong
 *
 */
public class AccountWithSync {

	private static Account account = new Account();
	
	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		for (int i = 0; i < 100; i++) {
			executor.execute(new AddPennyTask());			
		}
		executor.shutdown();
		
		while(!executor.isTerminated()){
		}
		
		System.out.println("What is balance?" + account.getBalance());
	}
	
	private static class AddPennyTask implements Runnable {
		@Override
		public void run() {
			//也可将此处改为同步语句使线程安全
//			synchronized (account) {
//				account.deposit(1);
//			}
			account.deposit(1);
		}
	}
	
	private  static class Account {
		private Integer balance = 0;
		
		public int getBalance() {
			return balance;
		}
		//synchronized关键字使deposit方法为同步方法，线程安全
		public synchronized void deposit(int amount) {
				int newBalance = balance + amount;
				//故意放大数据破坏的程度
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				balance = newBalance;
		}
	}
	
	
	
}
