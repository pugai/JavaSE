package test;

import java.util.Map;

import org.junit.Test;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class FastJsonTest {

	@Test
	public void test() {
		// 注意，JSONObject 实现了 Map<String, Object> 接口，JSONArray 实现了 List<Object> 接口，
		// 因此可用 JSONObject 代替 Map 使用，用 JSONArray 代替 List 使用
		// 如果想进行纯属性的序列、反序列操作，不涉及具体类型和对象，推荐使用 JSONObject 和 JSONArray
		// Map --> JSON
//		Map<String, Object> map = new HashMap<>();
//		Map<String, Object> submap = new HashMap<>();
		JSONObject map = new JSONObject();
		JSONObject submap = new JSONObject();
		submap.put("test", 2);
		map.put("name", "ctl");
		map.put("age", submap);
		map.put("testobj", new Bar("1bar1", 5));
		
		String s = JSON.toJSONString(map, SerializerFeature.WriteClassName, SerializerFeature.PrettyFormat);
//		String s = JSON.toJSONString(map, true);
		System.out.println(s);
		
		//JSON --> Map
		Map<String, Object> result1 = (Map<String, Object>) JSON.parse(s); 
		// 严格按照最外层的 @type 执行，若为 HashMap, 则将其转为 HashMap, 因此不能强制转换为 JSONObject
//		JSONObject result1 = (JSONObject) JSON.parse(s);  
		System.out.println(result1.get("name") instanceof String);
		System.out.println(((Map<String, Object>)result1.get("age")).get("test"));
//		System.out.println(((JSONObject)result1.get("age")).get("test"));
		System.out.println(result1.get("testobj"));
		// 采用 SerializerFeature.WriteClassName 特性才能正常类型转换
		System.out.println(((Bar)result1.get("testobj")).getName());
		
		System.out.println("------------");
		
//		Map<String, Object> result2 = JSON.parseObject(s);
		JSONObject result2 = JSON.parseObject(s);
		System.out.println(result2.get("name"));
//		System.out.println(((Map<String, Object>)result2.get("age")).get("test"));
//		System.out.println(((JSONObject)result2.get("age")).get("test"));
		System.out.println(result2.get("testobj"));
		// 采用 SerializerFeature.WriteClassName 特性才能正常类型转换
		// 注意，采用JSON.parseObject(String s) 实现时， 若最顶层声明为 Map 而非 JSONObject，则会转换失败，
		// 此时会将 testobj 转换成 JSONObject 而不是 Bar，
		// 原因猜想：可能和最外层的 @type 有关，在JSON.parseObject(s)方法中，由于返回的是 JSONObject ，
		//       因此，若顶层存在 @type，且@type 的值不是com.alibaba.fastjson.JSONObject，则会将该 @type 和
		//       各个内层中的 @type 都忽略，最后元素的类型均为 JSONObject
		System.out.println(((Bar)result2.get("testobj")).getName());
		
		System.out.println("------------");
		
		Bar bar = new Bar("Tom", 22);
//		String barStr = JSON.toJSONString(bar, SerializerFeature.WriteClassName);
		String barStr = JSON.toJSONString(bar);
		System.out.println(barStr);
		// 正常情况下，JSON.parse(String s) 会实际返回 JSONObject 或 JSONArray，
		// 只有当 json 字符串中带有类型信息(@type)时，才会实际返回该类型对象，才能进行强转
//		System.out.println(((Bar)JSON.parse(barStr)).getName());
		System.out.println((JSON.parseObject(barStr, Bar.class)).getName());
	}

}

class Bar {
	private String name;
	private int age;
	
	public Bar() {
	}

	public Bar(String name, int age){
		this.name = name;
		this.age = age;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
}
