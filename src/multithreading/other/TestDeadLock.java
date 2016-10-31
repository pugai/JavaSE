package multithreading.other;

//死锁的问题：处理线程同步时容易出现。
//不同的线程分别占用对方需要的同步资源不放弃，都在等待对方放弃自己需要的同步资源，就形成了线程的死锁
//写代码时，要避免死锁！
public class TestDeadLock {
	//注意StringBuffer也是线程安全的，因此用StringBuffer来演示会出现问题
	//可以使用StringBuilder来演示
//	static StringBuffer sb1 = new StringBuffer("sb1");
//	static StringBuffer sb2 = new StringBuffer("sb2");
	static StringBuilder sb1 = new StringBuilder("sb1:");
	static StringBuilder sb2 = new StringBuilder("sb2:");

	public static void main(String[] args) {
		new Thread() {
			public void run() {
				synchronized (sb1) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					sb1.append("A");
					System.out.println(sb1);
					System.out.println(sb2);
					synchronized (sb2) {
						sb2.append("B");
						System.out.println("dead+" + sb1);
						System.out.println("dead+" + sb2);
					}
				}
			}
		}.start();

		new Thread() {
			public void run() {
				synchronized (sb2) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					sb2.append("C");
					System.out.println(sb1);
					System.out.println(sb2);
					synchronized (sb1) {
						sb1.append("D");
						System.out.println("dead+" + sb1);
						System.out.println("dead+" + sb2);
					}
				}
			}
		}.start();
	}

}
