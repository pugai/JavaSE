package collections.set;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
/**
 * 散列集，无序
 * @author tianlong
 *
 */
public class TestHashSet {

	public static void main(String[] args) {
		Set<String> set = new HashSet<String>();
		set.add("London");
		set.add("Paris");
		set.add("New York");
		set.add("San Francisco");
		set.add("Beijing");
		set.add("New York");
		
		System.out.println(set);
		
		Iterator<String> iterator = set.iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next().toUpperCase());
		}
		
		for (String element : set) {
			System.out.println(element.toUpperCase());
		}
		
	}
	
}
