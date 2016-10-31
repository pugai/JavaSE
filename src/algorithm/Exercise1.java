package algorithm;

import java.util.Arrays;
import java.util.Scanner;

public class Exercise1 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int n;
		int m;
		n = scanner.nextInt();
		m = scanner.nextInt();
		int[] a = new int[n];
		for(int i = 0; i < n; i ++){
			a[i] = scanner.nextInt();
		}
		Customer[] cus = new Customer[m];
		for(int j = 0; j < m; j++){
			int b = scanner.nextInt();
			int c = scanner.nextInt();
			cus[j] = new Customer(b, c);
		}
		Arrays.sort(cus);
		for (Customer customer : cus) {
			System.out.println(customer);
		}
		
	}
	

}

class Customer implements Comparable<Customer>{
	public int b;
	public int c;
	public Customer(int b, int c) {
		this.b = b;
		this.c = c;
	}

	@Override
	public String toString() {
		return "Customer [b=" + b + ", c=" + c + "]";
	}

	@Override
	public int compareTo(Customer o) {
		return this.c >= o.c ? 1 : -1;
	}
}
