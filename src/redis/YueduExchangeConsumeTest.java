package redis;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author ctl
 * @date 2020/12/31
 */
public class YueduExchangeConsumeTest {

	private static final String LIMIT_LIST_KEY = "exchange_consume_limit_list";
	private static final String USED_LIMIT_INDEX_LIST_KEY = "exchange_consume_used_index_list";
	private static final List<String> DEFAULT_LIMIT_LIST =
			Lists.newArrayList("1000000", "1100000", "1200000", "1300000", "1400000", "1500000");

	@Test
	public void test() {
		StringRedisTemplate stringRedisTemplate = getTemplate();
		// 随机消耗上限设置：1.0-1.5万，随机，不重复，再周而复始
		List<String> limitList = stringRedisTemplate.boundListOps(LIMIT_LIST_KEY).range(0, -1);
		if (CollectionUtils.isEmpty(limitList)) {
			System.out.println("exchange_consume_limit_list not config");
			limitList = Lists.newArrayList(DEFAULT_LIMIT_LIST);
		}
		// 本次运行可供选择的index
		List<Integer> availableIndex = IntStream.range(0, limitList.size()).boxed().collect(Collectors.toList());
		List<String> usedLimitIndexList = stringRedisTemplate.boundListOps(USED_LIMIT_INDEX_LIST_KEY).range(0, -1);
		if (CollectionUtils.isNotEmpty(usedLimitIndexList)) {
			Set<Integer> usedIndex = usedLimitIndexList.stream().map(NumberUtils::toInt).collect(Collectors.toSet());
			availableIndex.removeIf(usedIndex::contains);
		}
		if (CollectionUtils.isEmpty(availableIndex)) {
			// 所有下标都已经被使用过一遍，从新开始下一轮
			// 清空“已使用的下标”缓存
			stringRedisTemplate.delete(USED_LIMIT_INDEX_LIST_KEY);
			availableIndex = IntStream.range(0, limitList.size()).boxed().collect(Collectors.toList());
		}
		Random random = new Random();
		Integer targetIndex = availableIndex.get(random.nextInt(availableIndex.size()));
		int targetLimit = NumberUtils.toInt(limitList.get(targetIndex));
		stringRedisTemplate.boundListOps(USED_LIMIT_INDEX_LIST_KEY).rightPush(String.valueOf(targetIndex));
		System.out.println("targetIndex: " + targetIndex);
		System.out.println("targetLimit: " + targetLimit);
	}

	public static StringRedisTemplate getTemplate() {
		JedisConnectionFactory factory = new JedisConnectionFactory();
		factory.setHostName("10.177.228.14");
		factory.setPort(6379);
		factory.setPassword("yuedu163");
		factory.afterPropertiesSet();
		return new StringRedisTemplate(factory);
	}

}
