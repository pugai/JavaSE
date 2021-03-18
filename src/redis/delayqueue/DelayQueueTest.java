package redis.delayqueue;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import redis.RedisScriptHelper;

import java.util.concurrent.CountDownLatch;

/**
 * @author ctl
 * @date 2020/12/25
 */
public class DelayQueueTest {

	public static String sb =
			"local items = redis.call('zrangebyscore',KEYS[1],0,ARGV[1],'limit',0,1)\n" +
			"if #items == 0 then\n" +
			"\treturn ''\n" +
			"else\n" +
			"\tredis.call('zremrangebyrank',KEYS[1],0,0)\n" +
			"\treturn items[1]\n" +
			"end";
	public static RedisScript<String> script = RedisScriptHelper.createScript(sb, String.class);

	public static StringRedisTemplate getTemplate() {
		JedisConnectionFactory factory = new JedisConnectionFactory();
		factory.setHostName("");
		factory.setPort(6379);
		factory.setPassword("");
		factory.afterPropertiesSet();
		return new StringRedisTemplate(factory);
	}

	public static int num = 1000;
	public static CountDownLatch latch = new CountDownLatch(1);

	public static String cacheKey = "zsetkeyctl";

	public static void main(String[] args) {
//		StringRedisTemplate template = getTemplate();
//		long currentTime = 100000;
//		String result = template.execute(script, Lists.newArrayList(cacheKey), String.valueOf(currentTime));
//		System.out.println(result);

		Task task = new Task(getTemplate());
		for (int i = 0; i < num; i++) {
			new Thread(task, "test-" + i).start();
		}
		latch.countDown();
	}

	public static class Task implements Runnable {

		private StringRedisTemplate redisTemplate;

		private boolean quit = false;

		public Task(StringRedisTemplate redisTemplate) {
			this.redisTemplate = redisTemplate;
		}

		@Override
		public void run() {
			try {
				latch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			System.out.println(Thread.currentThread().getName() + ": " + System.currentTimeMillis());
			while (!quit) {
				long currentTime = 100000;
				String result = redisTemplate.execute(script, Lists.newArrayList(cacheKey), String.valueOf(currentTime));
				System.out.println(Thread.currentThread().getName() + ": [" + result + "]" + result.length());
			}
		}
	}

	@Test
	public void createData() {
		StringRedisTemplate template = getTemplate();
		for (int i = 0; i < 1000; i++) {
			template.opsForZSet().add(cacheKey, "content-" + i, i);
		}
	}
}
