package util.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ctl
 * @date 2021/1/16
 */
public class GsonUtil {

	public static Type MAP_TYPE = new TypeToken<Map<String, Object>>(){}.getType();
	public static Type MAP_STRING_TYPE = new TypeToken<Map<String, String>>(){}.getType();

	private static final Gson GSON;

	static {
		GSON = new GsonBuilder().disableHtmlEscaping().create();
	}

	public static Gson gson() {
		return GSON;
	}

	public static void main(String[] args) {
		Map<String, Object> map = new HashMap<>();
		map.put("url", "http://www.lofter.com/collectionManage.do?targetBlogName=qatest5");
		map.put("a", 2);
		map.put("b", 3.0);
		String content = gson().toJson(map);
		System.out.println(content);
		Map<String, Object> r1 = gson().fromJson(content, MAP_TYPE);
		System.out.println(r1);
//		System.out.println(gson().toJson(r1));
	}

	@Test
	public void test2() {
		String json = "{\"data\":[{\"id\":1,\"quantity\":2,\"name\":\"apple\"}, {\"id\":3,\"quantity\":4,\"name\":\"orange\"}]}";
		System.out.println("json == " + json);
        Map<String, Object> map = new LinkedTreeMap<>();
        map = new Gson().fromJson(json, map.getClass());
        System.out.println(map);

//		GsonBuilder gsonBuilder = new GsonBuilder();
//		gsonBuilder.registerTypeAdapter(new TypeToken<Map <String, Object>>(){}.getType(),  new MapDeserializerDoubleAsIntFix());
//		Gson gson = gsonBuilder.create();
//		Map<String, Object> map = gson.fromJson(json, new TypeToken<Map<String, Object>>(){}.getType());
//		System.out.println(map);
	}

}
