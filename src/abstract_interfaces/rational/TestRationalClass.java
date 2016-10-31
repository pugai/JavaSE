package abstract_interfaces.rational;

/**
 * 有理数类测试
 * @author tianlong
 *
 */
public class TestRationalClass {

	public static void main(String[] args) {
		Rational r1 = new Rational(4, 2);
		Rational r2 = new Rational(2, 3);
		Rational r3 = new Rational(4, 6);

		System.out.println(r1 + "+" + r2 + "=" + r1.add(r2));
		System.out.println(r1 + "-" + r2 + "=" + r1.subtract(r2));
		System.out.println(r1 + "*" + r2 + "=" + r1.multiply(r2));
		System.out.println(r1 + "/" + r2 + "=" + r1.divide(r2));
		System.out.println(r1 + " is " + r1.doubleValue());
		System.out.println(r2 + " is " + r2.doubleValue());
		
		System.out.println(r2.equals(r3));
		System.out.println(r2.hashCode() + " " + r3.hashCode());
		System.out.println(r2.compareTo(r3));
	}

}
