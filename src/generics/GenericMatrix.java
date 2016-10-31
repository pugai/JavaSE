package generics;
/**
 * 泛型矩阵父类，抽象类，实现矩阵相加和相乘，显示
 * @author tianlong
 * 
 * @param <E>
 */
public abstract class GenericMatrix<E extends Number> {

	protected abstract E add(E o1, E o2);

	protected abstract E multiplyl(E o1, E o2);

	protected abstract E zero();

	public E[][] addMatrix(E[][] matrix1, E[][] matrix2) {
		if ((matrix1.length != matrix2.length) || (matrix1[0].length != matrix2[0].length)) {
			throw new RuntimeException("The matrices do not have the saem size");
		}
		@SuppressWarnings("unchecked")
		E[][] result = (E[][]) new Number[matrix1.length][matrix1[0].length];

		for (int i = 0; i < matrix1.length; i++)
			for (int j = 0; j < matrix1[0].length; j++) {
				result[i][j] = add(matrix1[i][j], matrix2[i][j]);
			}
		return result;
	}

	public E[][] multiplyMatrix(E[][] matrix1, E[][] matrix2) {
		if (matrix1[0].length != matrix2.length) {
			throw new RuntimeException("The matrices do not have compatble size");
		}
		@SuppressWarnings("unchecked")
		E[][] result = (E[][]) new Number[matrix1.length][matrix2[0].length];
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[0].length; j++) {
				result[i][j] = zero();
				for (int k = 0; k < matrix1[0].length; k++) {
					result[i][j] = add(result[i][j], multiplyl(matrix1[i][k], matrix2[k][j]));
				}
			}
		}

		return result;
	}

	public static void printResult(Number[][] m1, Number[][] m2, Number[][] m3, char op) {
		for (int i = 0; i < (m1.length > m2.length ? m1.length : m2.length); i++) {
			for (int j = 0; j < m1[0].length; j++)
				if(i < m1.length)
					System.out.print(m1[i][j] + "\t");
				else 
					System.out.print("\t");
			
			if(i == m1.length / 2)
				System.out.print(op + "\t");
			else
				System.out.print("\t");
			
			for (int j = 0; j < m2[0].length; j++)
				if(i < m2.length)
					System.out.print(m2[i][j] + "\t");
				else 
					System.out.print("\t");
			
			if(i == m1.length / 2)
				System.out.print("=" + "\t");
			else
				System.out.print("\t");
			
			for (int j = 0; j < m3[0].length; j++)
				if(i < m3.length)
					System.out.print(m3[i][j] + "\t");
				else 
					System.out.print("\t");
			System.out.println();
		}
	}

}
