package multithreading.account;
/*
 * 银行有一个账户。
有两个储户分别向同一个账户存3000元，每次存1000，存3次。每次存完打印账户余额。


1.是否涉及到多线程？是！有两个储户(两种方式创建多线程)
2.是否有共享数据？有！同一个账户
3.得考虑线程的同步。（两种方法处理线程的安全）

//拓展：实现二者交替打印。使用线程的通信
 */
public class TestAccount {
	
	public static void main(String[] args) {	
		Account account = new Account();
		Thread thread1 = new Thread(new DepositTask(account));
		Thread thread2 = new Thread(new DepositTask(account));
		thread1.setName("customer1");
		thread2.setName("customer2");
		thread1.start();
		thread2.start();
	}

}

class Account {
	private double balance;

	public Account() {
		System.out.println("new account with balance: " + balance);
	}

	public Account(double balance) {
		this.balance = balance;
		System.out.println("new account with balance: " + balance);
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public synchronized void deposit(double amt) {
		notify();
		balance += amt;
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + ": " + balance);
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

// class Customer {
// private Account account = null;
//
// public Customer() {
// }
//
// public Customer(Account account) {
// this.account = account;
// }
// }

class DepositTask implements Runnable {
	private Account account;

	public DepositTask(Account account) {
		this.account = account;
	}

	public void run() {
		for (int i = 0; i < 3; i++) {
			account.deposit(1000);
		}
	}
}
