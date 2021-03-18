package util.common;

/**
 * @author ctl
 * @date 2020/12/30
 */
public class SleepUtils {

	public static void sleepMillis(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException ignored) {}
	}

	public static void sleepSeconds(long seconds) {
		sleepMillis(seconds * 1000);
	}

	public static void sleepMinutes(long minutes) {
		sleepMillis(minutes * 60000);
	}

}
