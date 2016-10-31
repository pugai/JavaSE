package arrays;

/**
 * 选择排序法，升序
 * 
 * @author tianlong
 *
 */
public class SelectionSort {

	public static void selectionSort(int[] list) {
		for (int i = 0; i < list.length - 1; i++) {
			int currentMin = list[i];
			int currentMinIndex = i;
			for (int j = i + 1; j < list.length; j++)
				if (currentMin > list[j]) {
					currentMin = list[j];
					currentMinIndex = j;
				}
			if (currentMinIndex != i) {
				list[currentMinIndex] = list[i];
				list[i] = currentMin;
			}
		}
	}

	public static void main(String[] args) {
		int[] list = new int[10000];
		for (int i = 0; i < list.length; i++) {
			list[i] = 10000 - i;
		}

		// 计算运行时间
		long start = System.currentTimeMillis();

		selectionSort(list);
		// 遍历显示
		// for (int i = 0; i < list.length; i++) {
		// System.out.print(" " + list[i]);
		// }
		//
		long end = System.currentTimeMillis();
		System.out.println("所花时间：" + (end - start));
	}

}
