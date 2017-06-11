package jdk8.newtimedate;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 原有datatime api 存在多线程安全问题。
 * @author Tianlong
 *
 */
public class TestSimpleDateFormat {

	public static void main(String[] args) throws Exception {
		//有多线程安全问题，会抛异常
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		Callable<Date> task = new Callable<Date>() {
			@Override
			public Date call() throws Exception {
//				return sdf.parse("20161122"); // 有异常
				
				//用ThreadLocal解决多线程安全问题
				return DateFormatThreadLocal.convert("20161122");
			}
		};
		
		ExecutorService pool = Executors.newFixedThreadPool(10);
		
		List<Future<Date>> results = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			results.add(pool.submit(task));
		}
		
		for (Future<Date> future : results) {
			System.out.println(future.get());
		}
		
		pool.shutdown();*/
		
		
		
		DateTimeFormatter dtf = DateTimeFormatter.BASIC_ISO_DATE;
		
		Callable<LocalDate> task = new Callable<LocalDate>() {
			@Override
			public LocalDate call() throws Exception {
				// 使用Java8 新API解决多线程问题，利用不可变对象解决
				return LocalDate.parse("20170102", dtf);
			}
		};
		
		ExecutorService pool = Executors.newFixedThreadPool(10);
		
		List<Future<LocalDate>> results = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			results.add(pool.submit(task));
		}
		
		for (Future<LocalDate> future : results) {
			System.out.println(future.get());
		}
		
		pool.shutdown();
		
		
		
	}
	
}
