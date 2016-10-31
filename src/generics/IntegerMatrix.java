package generics;

public class IntegerMatrix extends GenericMatrix<Integer> {

	@Override
	protected Integer add(Integer o1, Integer o2) {
		return o1 + o2;
	}

	@Override
	protected Integer multiplyl(Integer o1, Integer o2) {
		// TODO Auto-generated method stub
		return o1 * o2;
	}

	@Override
	protected Integer zero() {
		// TODO Auto-generated method stub
		return 0;
	}

}
