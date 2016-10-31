package generics;

public class TestIntegerMatrix {
	
	public static void main(String[] args) {
		
		Integer[][] m1 = new Integer[][]{{8,9,10},{1,2,3},{10,11,12}};
		Integer[][] m2 = new Integer[][]{{8,9,10},{1,2,3},{10,11,12}};
		
		IntegerMatrix integerMatrix = new IntegerMatrix();
		
		System.out.println("m1 + m2 is");
		GenericMatrix.printResult(m1, m2, integerMatrix.addMatrix(m1, m2), '+');
		
		System.out.println("m1 * m2 is");
		GenericMatrix.printResult(m1, m2, integerMatrix.multiplyMatrix(m1, m2), '*');
	}
	
}
