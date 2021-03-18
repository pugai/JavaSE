package redis.delayqueue;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import redis.RedisScriptHelper;
import util.common.SleepUtils;

import javax.annotation.PostConstruct;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 基于ZSET实现消息延迟处理，score存储执行时间点，到达时间点即会向指定队列发送该消息；
 * 定义一个继承本类的bean即可；
 * @author ctl
 * @date 2020/12/30
 */
public abstract class AbstractDelayedMsgScanTrigger implements Runnable, DisposableBean {

	private static final RedisScript<String> TRY_GET_AND_DEL_SCRIPT;
	static {
		String sb = "local items = redis.call('zrangebyscore',KEYS[1],0,ARGV[1],'limit',0,1)\n" +
				"if #items == 0 then\n" +
				"\treturn ''\n" +
				"else\n" +
				"\tredis.call('zremrangebyrank',KEYS[1],0,0)\n" +
				"\treturn items[1]\n" +
				"end";
		TRY_GET_AND_DEL_SCRIPT = RedisScriptHelper.createScript(sb, String.class);
	}

	private final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(getThreadNum(), getThreadNum(),
			0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), new NamedThreadFactory(getThreadNamePrefix()));
	private volatile boolean quit = false;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private RabbitTemplate rabbitTemplate;

	@PostConstruct
	public void startScan() {
		int threadNum = getThreadNum();
		for (int i = 0; i < threadNum; i++) {
			EXECUTOR.execute(this);
		}
	}

	@Override
	public void run() {
		while (!quit) {
			try {
				String msg = stringRedisTemplate.execute(TRY_GET_AND_DEL_SCRIPT,
						Lists.newArrayList(getDelayedMsgSourceKey()), String.valueOf(System.currentTimeMillis()));
				if (StringUtils.isNotBlank(msg)) {
					rabbitTemplate.convertAndSend(getSendExchange(), getSendRoutingKey(), msg);
				} else {
					SleepUtils.sleepSeconds(10);
				}
			} catch (Exception e) {
				// log
//				Logs.MSG.error("delayed msg scan error, sourceKey:{}", getDelayedMsgSourceKey(), e);
			}
		}
	}

	@Override
	public void destroy() throws Exception {
		quit = true;
	}

	public void setQuit(boolean quit) {
		this.quit = quit;
	}

	/**
	 * 获取消息的工作线程数量
	 */
	protected abstract int getThreadNum();

	/**
	 * 线程名称前缀，方便定位
	 */
	protected abstract String getThreadNamePrefix();

	/**
	 * 存放延迟消息的ZSET队列名
	 */
	protected abstract String getDelayedMsgSourceKey();

	/**
	 * 消息到达执行时间点时将其通过指定 exchange 发送到实时消费队列中
	 */
	protected abstract String getSendExchange();

	/**
	 * 消息到达执行时间点时将其通过指定 routingKey 发送到实时消费队列中
	 */
	protected abstract String getSendRoutingKey();

}
