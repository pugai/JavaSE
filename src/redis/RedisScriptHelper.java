package redis;

import com.google.common.io.Resources;
import org.apache.commons.io.IOUtils;
import org.springframework.data.redis.core.script.DigestUtils;
import org.springframework.data.redis.core.script.RedisScript;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Created On 10/24 2017
 *
 * @author hzpengjunjian@corp.netease.com
 */
public class RedisScriptHelper {

    private static final Map<URL, RedisScript<?>> SCRIPT_CACHE = new HashMap<URL, RedisScript<?>>();

    @SuppressWarnings("unchecked")
    public static <T> RedisScript<T> loadScript(URL url, Class<T> resultType) {
        RedisScript<T> script = (RedisScript<T>) SCRIPT_CACHE.get(url);
        if (script == null) {
            try {
                script = new RedisScriptImpl<T>(IOUtils.toString(url, "UTF-8"), resultType);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            SCRIPT_CACHE.put(url, script);
        }
        return script;
    }

    public static <T> RedisScript<T> loadScript(String resourceName, Class<T> resultType) {
        return loadScript(Resources.getResource(resourceName), resultType);
    }

    public static <T> RedisScript<T> createScript(String scriptAsString, Class<T> resultType) {
        return new RedisScriptImpl<T>(scriptAsString, resultType);
    }

    /**
     * 将集合中每个元素转换为String(通过#toString)，输出一个String的数组
     */
    public static Object[] toArgvArray(Collection<?> coll) {
        List<String> result = new ArrayList<String>(coll.size());
        for (Object obj : coll) {
            result.add(obj.toString());
        }
        return result.toArray();
    }

    private static class RedisScriptImpl<T> implements RedisScript<T> {

        private final String script;

        private final String sha1;

        private final Class<T> resultType;

        public RedisScriptImpl(String script, Class<T> resultType) {
            this.script = script;
            this.sha1 = DigestUtils.sha1DigestAsHex(script);
            this.resultType = resultType;
        }

        @Override
        public String getSha1() {
            return sha1;
        }

        @Override
        public Class<T> getResultType() {
            return resultType;
        }

        @Override
        public String getScriptAsString() {
            return script;
        }
    }
}
