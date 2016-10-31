package java_collections.set;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * 树形集，按规则排序
 * @author tianlong
 *
 */
public class TestTreeSet {

	public static void main(String[] args) {
		Set<Integer> set = new HashSet<Integer>();
		set.add(5);
		set.add(2);
		set.add(4);
		set.add(3);
		set.add(2);
		set.add(1);

		TreeSet<Integer> treeSet = new TreeSet<>(set);
		System.out.println(treeSet);

		System.out.println(treeSet.first());
		System.out.println(treeSet.last());
		System.out.println(treeSet.headSet(3));
		System.out.println(treeSet.tailSet(3));

		System.out.println(treeSet.lower(3));
		System.out.println(treeSet.higher(3));
		System.out.println(treeSet.floor(3));
		System.out.println(treeSet.ceiling(3));
		System.out.println(treeSet.pollFirst());
		System.out.println(treeSet.pollLast());
		System.out.println(treeSet);
	}

}
