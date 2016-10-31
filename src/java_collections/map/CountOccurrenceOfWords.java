package java_collections.map;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * 统计一个文本中单词的出现次数，按照字典序显示单词及其对应次数
 * @author tianlong
 *
 */
public class CountOccurrenceOfWords {
	public static void main(String[] args) {
		// Set text in a string
		String text = "Good morning. Have a good class. " + "Have a good visit. Have fun!";

		// Create a TreeMap to hold words as key and count as value
		//树形图中默认按照键值得自然顺序排列，所以单词为升序
		TreeMap<String, Integer> map = new TreeMap<String, Integer>();

		String[] words = text.split("[ \n\t\r.,;:!?(){}]");
		for (int i = 0; i < words.length; i++) {
			String key = words[i].toLowerCase();

			if (key.length() > 0) {
				if (map.get(key) == null) {
					map.put(key, 1);
				} else {
					int value = map.get(key).intValue();
					value++;
					map.put(key, value);
				}
			}
		}

		// Get all entries into a set
		Set<Map.Entry<String, Integer>> entrySet = map.entrySet();

		// Get key and value from each entry
		for (Map.Entry<String, Integer> entry : entrySet)
			System.out.println(entry.getValue() + "\t" + entry.getKey());
	}
}
