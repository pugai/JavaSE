package collections.set;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
/**
 * 链式散列集，按插入顺序排序
 * @author tianlong
 *
 */
public class TestLinkedHashSet {
	
	public static void main(String[] args) {
		Set<String> set = new LinkedHashSet<String>();
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
