package multithreading.forkjoin;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * 等差数列累加， start到end，利用forkjoin，需要设定阈值，制定最小任务的数列计算项数
 * 计算的数列越大，效果越明显，CPU利用效率越高
 * 但是fork join操作比较麻烦，使用并不广泛
 * @author Tianlong
 *
 */
public class ForkJoinCalculate extends RecursiveTask<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2291691137528551705L;

	private long start;
	private long end;
	
	private static final long THRESHOLD = 10000L;
	
	public ForkJoinCalculate(long start, long end) {
		this.start = start;
		this.end = end;
	}
	
	@Override
	protected Long compute() {
		long length = end - start;
		if (length <= THRESHOLD) {
			long sum = 0;
			for (long i = start; i <= end; i++) {
				sum += i;
			}
			return sum;
		} else {
			long middle = (start + end) / 2;
			ForkJoinCalculate left = new ForkJoinCalculate(start, middle);
			left.fork(); // 拆分子任务，同时压入线程队列
			ForkJoinCalculate right = new ForkJoinCalculate(middle + 1, end);
			right.fork();
			return left.join() + right.join();
		}
	}
	
	
	public static void main(String[] args) {
		// 测试
		Instant start = Instant.now();
		
		//使用forkjoin
		ForkJoinPool pool = new ForkJoinPool();
		ForkJoinTask<Long> task = new ForkJoinCalculate(0, 50000000000L);
		long sum = pool.invoke(task);
		System.out.println(sum);
		
		Instant end = Instant.now();
		System.out.println("used time: " + Duration.between(start, end).toMillis());
		
		
		Instant start2 = Instant.now();
		
		//使用普通 for 循环
		long sum2 = 0L;
		for (long i = 0; i <= 50000000000L; i++) {
			sum2 += i;
		}
		System.out.println(sum2);
		
		Instant end2 = Instant.now();
		System.out.println("used time: " + Duration.between(start2, end2).toMillis());
		
		//使用Java8并行流,底层也是forkjoin
		Instant start3 = Instant.now();
		LongStream.rangeClosed(0, 50000000000L)
			.parallel()
			.reduce(Long::sum);
		Instant end3 = Instant.now();
		System.out.println("used time: " + Duration.between(start3, end3).toMillis());
	}

}

