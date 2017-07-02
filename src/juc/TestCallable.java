package juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/*
 * 一、创建执行线程的方式三：实现 Callable 接口
 * 
 * 二、执行 Callable 方式，需要 FutureTask 实现类的支持，用于接收运算结果 。FutureTask 是 Future 接口的实现类
 */
public class TestCallable {
	
	public static void main(String[] args) {
		CallableThreadDemo ctd = new CallableThreadDemo();
		
		//1、执行 Callable 方式，需要 FutureTask 实现类的支持，用于接收运算结果 。
		FutureTask<Integer> result = new FutureTask<>(ctd);
		
		new Thread(result).start();
		
		//2、接收线程运算后的结果
		try {
			Integer i = result.get(); // 会等待线程执行结束，FutureTask 可用于闭锁
			System.out.println(i);
			System.out.println("------------");
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
	}

}

class CallableThreadDemo implements Callable<Integer> {
	

	@Override
	public Integer call() throws Exception {
		int sum = 0;
		for (int i = 0; i <= Integer.MAX_VALUE; i++) {
			sum += i;
		}
		return sum;
	}
	
}

/*class ThreadDemo implements Runnable {

	@Override
	public void run() {
	}
	
}*/
