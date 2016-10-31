package java_collections.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 统计一个文本中单词的出现次数，按照出现次数升序显示单词及其对应次数
 * 
 * @author tianlong
 *
 */
public class CountOccurrenceOfWords2 {
	public static void main(String[] args) {
		// Set text in a string
		String text = "Good morning. Have a good class. " + "Have a good visit. Have fun!";

		// Create a HashMap to hold words as key and count as value
		HashMap<String, Integer> map = new HashMap<String, Integer>();

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
		List<WordOccurrence> wordslist = new ArrayList<WordOccurrence>();
		// Get key and value from each entry
		for (Map.Entry<String, Integer> entry : entrySet) {
			WordOccurrence word = new WordOccurrence(entry.getKey(), entry.getValue());
			wordslist.add(word);
		}

		Collections.sort(wordslist);

//		Iterator<WordOccurrence> iterator = wordslist.iterator();
//		while (iterator.hasNext())
//			System.out.println(iterator.next().toString());

		 for (WordOccurrence w : wordslist) {
		 System.out.println(w.toString());
		 }

	}
}

class WordOccurrence implements Comparable<WordOccurrence> {

	public String word;
	public int count;

	public WordOccurrence(String word, int count) {
		super();
		this.word = word;
		this.count = count;
	}

	@Override
	public int compareTo(WordOccurrence o) {
		// TODO Auto-generated method stub
		return count - o.count;
	}

	@Override
	public String toString() {
		return "[word=" + word + ", count=" + count + "]";
	}

}
