package multithreading;


/**
 * 实现Runnable接口创建任务，推荐使用，也可定义一个Thread类的扩展类，实现run方法，但是不推荐
 * @author tianlong
 *
 */
public class TaskThreadDemo {

	public static void main(String[] args) {
		Runnable printA = new PrintChar('A', 1000);
		Runnable printB = new PrintChar('B', 1000);
		Runnable print100 = new PrintNum(1000);
		
		Thread thread1 = new Thread(printA);
		Thread thread2 = new Thread(printB);
		Thread thread3 = new Thread(print100);
		
		//高优先级线程必须定时调用sleep方法或yield方法来给低优先级线程一个运行的机会
		thread3.setPriority(Thread.MAX_PRIORITY);
		thread1.start();
		thread2.start();
		thread3.start();
	}
	
}

class PrintChar implements Runnable {
	private char charToPrint;
	private int times;
	
	public PrintChar(char c, int t) {
		// TODO Auto-generated constructor stub
		charToPrint = c;
		times = t;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (int i = 0; i < times; i++) {
			System.out.print(charToPrint);
		}
	}
}

class PrintNum implements Runnable {
	private int lastNum;
	
	public PrintNum(int n) {
		// TODO Auto-generated constructor stub
		lastNum = n;
	}
	
	@Override
	public void run() {
//		Thread thread4 = new Thread(new PrintChar('C', 5000));
//		thread4.start();
//		try {
			for (int i = 1; i <= lastNum; i++) {
				System.out.print(" " + i);
				//在线程thread4结束后（打印C40次）再打印51到100之间的数
//				if(i == 50) thread4.join();
				//线程休眠
//				if(i >= 50) Thread.sleep(1);
				//暂停，为其它线程临时让出时间
//				Thread.yield();
			}			
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		
	}
}
