package algorithm;

import java.util.Arrays;

import arrays.BubbleSort;

/**
 * @author tianlong
 *
 */
public class First {

	public static void main(String[] args) {
		// int[] a = { 1, 3, 5, 7, 9 };
		// int[] b = { 2, 2, 2, 2, 5, 7, 10, 12 };
		// arraySort_2to1(a, b);
		// int[] oneMissingNumber = { 5, 6, 8, 4, 9, 7, 1, 3, 2, 11 };
		// findOneMissingNumber(oneMissingNumber);
		// int[] severalMissingNumber = { 7, 8, 9, 6, 4, 2, 12, 14, 10 };
		// findSeveralMissingNumbers(severalMissingNumber);
		String string = "aaaabbbbddddcccc1111";
		maxTimes(string);
	}

	/**
	 * 浼犲叆 2 涓湁搴忕殑鏁存暟鏁扮粍锛岀粍鎴愪竴涓湁搴忕殑鏁存暟鏁扮粍
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static void arraySort_2to1(int[] a, int[] b) {
		int[] result = new int[a.length + b.length];
		int m = 0;
		int n = 0;
		for (int i = 0; i < (a.length + b.length); i++) {
			if (m == a.length) {
				result[i] = b[n];
				n++;
			} else if (n == b.length) {
				result[i] = a[m];
				m++;
			} else {
				if (a[m] <= b[n]) {
					result[i] = a[m];
					m++;
				} else {
					result[i] = b[n];
					n++;
				}
			}
		}

		for (int i : result) {
			System.out.print(i + " ");
		}
	}

	/**
	 * 浠庢棤搴忔暣鏁版暟缁勶紙鍚嚑涓涪澶辩殑鏁帮級涓壘鍒板嚑涓涪澶辩殑鏁�
	 * 
	 * @param a
	 */
	public static void findSeveralMissingNumbers(int[] a) {
		System.out.print("array: ");
		for (int i : a) {
			System.out.print(i + " ");
		}
		BubbleSort.bubbleSort(a);
		System.out.print("\nsortedArray: ");
		for (int i : a) {
			System.out.print(i + " ");
		}
		System.out.print("\nseveralMissNumber: ");
		for (int i = 0; i < a.length - 1; i++) {
			int temp = a[i];
			while (++temp < a[i + 1])
				System.out.print(temp + " ");
		}
	}

	/**
	 * 浠庢棤搴忔暣鏁版暟缁勶紙鍙惈涓�涓涪澶辩殑鏁帮級涓壘鍒颁竴涓涪澶辩殑鏁�
	 * 
	 * @param a
	 */
	public static void findOneMissingNumber(int[] a) {
		System.out.print("array: ");
		for (int i : a) {
			System.out.print(i + " ");
		}
		BubbleSort.bubbleSort(a);
		System.out.print("\nsortedArray: ");
		for (int i : a) {
			System.out.print(i + " ");
		}
		int sum1 = (a[0] + a[a.length - 1]) * (a[a.length - 1] - a[0] + 1) / 2;
		int sum2 = 0;
		for (int i = 0; i < a.length; i++) {
			sum2 += a[i];
		}
		System.out.println("\noneMissNumber: " + (sum1 - sum2));
	}

	public static void compareTwoStringIgnoreOrder(String s1, String s2) {

	}

	/**
	 * 输入字符串，找出出现次数最多（如果出现次数一样，则找出数值大的）的字符
	 * @param s
	 */
	public static void maxTimes(String s) {
		System.out.println("String:\t\t" + s);
		char[] c = s.toCharArray();
		Arrays.sort(c);
		System.out.print("sortedString:\t");
		for (char i : c) {
			System.out.print(i);
		}
		char maxChar = c[0];
		int max = 1;
		int i = 0;
		while (i < c.length) {
			int j = 1;
			while (i + j < c.length && c[i] == c[i + j]) {
				j++;
			}
			if ((j > max) || ((j == max) && (c[i] > maxChar))) {
				max = j;
				maxChar = c[i];
			}
			i += j;
		}
		System.out.println("\nchar: " + maxChar + "\ntimes: " + max);
	}
	
	

}
