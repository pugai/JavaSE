package arrays;

import java.util.Arrays;

public class Main {
	public static void main(String[] args) {
		int[] r = new int[]{0,0,0};
		int[] t = new int[]{1,2,3};
		int i = 0;
//		r[i] = t[++i];
		r[++i] = t[i];
		System.out.println(Arrays.toString(r));
		System.out.println(Arrays.toString(t));
		System.out.println(i);
	}

}
