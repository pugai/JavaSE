package java_collections.set;

import java.util.HashSet;
import java.util.Set;

public class TestMethodsInCollection {

	public static void main(String[] args) {
		Set<String> set1 = new HashSet<String>();
		
		set1.add("London");
		set1.add("Paris");
		set1.add("New York");
		set1.add("San Francisco");
		set1.add("Beijing");
		
		System.out.println(set1);
		System.out.println(set1.size());
		
		set1.remove("London");
		
		System.out.println(set1);
		System.out.println(set1.size());
		
		Set<String> set2 = new HashSet<String>();
		set2.add("London");
		set2.add("Paris");
		set2.add("Shanghai");
		
		System.out.println(set2);
		System.out.println(set2.size());
		
		System.out.println("Is Taipei in set2? " + set2.contains("Taipei"));
		
		set1.addAll(set2);
		System.out.println(set1);
		
		set1.removeAll(set2);
		System.out.println(set1);
		
		set1.retainAll(set2);
		System.out.println(set1);
	}
	
}
