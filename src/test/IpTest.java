package test;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ctl
 * @date 2020/10/10
 */
public class IpTest {

	@Test
	public void testDivide() {
		BigDecimal a = new BigDecimal("10");
		BigDecimal b = new BigDecimal("3");
		BigDecimal c = a.divide(b, 5, RoundingMode.HALF_EVEN);
		System.out.println(c);
	}

	@Test
	public void testPing() throws InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(700);
		String suffix = "";
		for (int i = 0; i < 26; i++) {
			for (int j = 0; j < 26; j++) {
				String prefix = String.valueOf((char) (97 + i)) + (char) (97 + j);
				String url = prefix + suffix;
//				System.out.println(url);
//				new Thread(() -> testsss()).start();
				executorService.execute(() -> ping01(url));
			}
		}
		Thread.sleep(30000000);
	}
	@Test
	public void test1() {
		ping01("www.tongjilab.cn");
	}

	public static boolean ping01(String ipAddress) {
		boolean status;
		try {
			int  timeOut =  3000 ;  //超时应该在3钞以上
			status = InetAddress.getByName(ipAddress).isReachable(timeOut);     // 当返回值是true时，说明host是可用的，false则不可。
		} catch (Exception e) {
			System.out.println(ipAddress + " | " + e.getClass().getSimpleName());
			return false;
		}
		if (status) {
			System.out.println(ipAddress + " | pass");
		} else {
			System.out.println(ipAddress + " | false");
		}
		return status;
	}

	public static void testsss() {
		System.out.println("aaaaaaa");
	}

	/**
	 * ping IP地址或域名
	 * @param ip 如：www.baidu.com
	 * @return 是否通讯正常
	 */
	public static boolean ping(String ip){
		boolean res = false; // 结果
		Runtime runtime = Runtime.getRuntime(); // 获取当前程序的运行进对象
		Process process = null; // 声明处理类对象
		String line = null; // 返回行信息
		InputStream is = null; // 输入流
		InputStreamReader isr = null; // 字节流
		BufferedReader br = null;
		try {
			process = runtime.exec("ping " + ip); // PING
//			System.out.println("3333");
			is = process.getInputStream(); // 实例化输入流
			isr = new InputStreamReader(is,"gbk"); // 把输入流转换成字节流,传入参数为了解决"gbk"中文乱码问题
			br = new BufferedReader(isr); // 从字节中读取文本
			while ((line = br.readLine()) != null) {
				line = new String(line.getBytes("UTF-8"),"UTF-8");
				if (line.contains("TTL")) { // 通了
					res = true;
					System.out.println(line);
					break;
				}
			}
			is.close();
			isr.close();
			br.close();
			if (res){
				System.out.println("ping: " + ip + " 通..");
			} else{
				System.out.println("ping: " + ip + " 不通..");
			}
		} catch (Exception e) {
			System.out.println(e);
			runtime.exit(1);
		}
		return res;
	}

}
