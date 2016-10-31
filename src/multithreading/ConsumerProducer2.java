package multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 生产者/消费者，一个固定大小的缓冲整数区，取、放数字，用synchronized实现，通信用wait，notify实现，
 * 加入了多个生产者，消费者
 * @author tianlong
 *
 */
public class ConsumerProducer2 {
	private static Buffer buffer = new Buffer();

	public static void main(String[] args) {
		// Create a thread pool with two threads
		ExecutorService executor = Executors.newFixedThreadPool(3);
		executor.execute(new ProducerTask());
		executor.execute(new ProducerTask());
		executor.execute(new ConsumerTask());
		executor.shutdown();
		
//		ProducerTask p = new ProducerTask();
//		ConsumerTask c = new ConsumerTask();
//		new Thread(p).start();
//		new Thread(p).start();
//		new Thread(c).start();
	}

	// A task for adding an int to the buffer
	private static class ProducerTask implements Runnable {
		public static int i = 1;
		public void run() {
			try {
				while (true) {
					buffer.write(i++); // Add a value to the buffer
					// Put the thread into sleep
					Thread.sleep((int) (Math.random() * 1000));
				}
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

	// A task for reading and deleting an int from the buffer
	private static class ConsumerTask implements Runnable {
		public void run() {
			try {
				while (true) {
					System.out.println("\t\t\tConsumer reads " + buffer.read());
					// Put the thread into sleep
					Thread.sleep((int) (Math.random() * 1000));
				}
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

	// An inner class for buffer
	private static class Buffer {
		private static final int CAPACITY = 3; // buffer size
		private java.util.LinkedList<Integer> queue = new java.util.LinkedList<Integer>();

		public synchronized void write(int value) {
			while (queue.size() == CAPACITY) {
				System.out.println("Wait for notFull condition");
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			queue.offer(value);
			System.out.println("Producer writes " + value);
			notifyAll();
		}

		public synchronized int read() {
			int value = 0;
			while (queue.isEmpty()) {
				System.out.println("\t\t\tWait for notEmpty condition");
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			value = queue.remove();
			notifyAll();
			return value;
		}
	}
}
