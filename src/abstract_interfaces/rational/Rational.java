package abstract_interfaces.rational;

/**
 * 有理数类Rational
 * @author tianlong
 *
 */
public class Rational extends Number implements Comparable<Rational> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long numerator = 0;
	private long denominator = 1;

	public Rational() {
		this(0, 1);

	}

	public Rational(long numerator, long denominator) {
		//求最大公约数
		long gcd = gcd(numerator, denominator);
		this.numerator = ((denominator > 0) ? 1 : -1) * numerator / gcd;
		//存储时分母一律存为正数
		this.denominator = Math.abs(denominator) / gcd;
	}

	// 求最大公约数
	private static long gcd(long n, long d) {
		long n1 = Math.abs(n);
		long n2 = Math.abs(d);
		int gcd = 1;

		for (int k = 1; k <= n && k <= d; k++) {
			if (n1 % k == 0 && n2 % k == 0)
				gcd = k;
		}

		return gcd;
	}

	public long getNumerator() {
		return numerator;
	}

	public long getDenominator() {
		return denominator;
	}

	public Rational add(Rational secondRational) {
		long n = numerator * secondRational.getDenominator() + denominator * secondRational.getNumerator();
		long d = denominator * secondRational.getDenominator();
		return new Rational(n, d);
	}

	public Rational subtract(Rational secondRational) {
		long n = numerator * secondRational.getDenominator() - denominator * secondRational.getNumerator();
		long d = denominator * secondRational.getDenominator();
		return new Rational(n, d);
	}

	public Rational multiply(Rational secondRational) {
		long n = numerator * secondRational.getNumerator();
		long d = denominator * secondRational.getDenominator();
		return new Rational(n, d);
	}

	public Rational divide(Rational secondRational) {
		long n = numerator * secondRational.getDenominator();
		long d = denominator * secondRational.getNumerator();
		return new Rational(n, d);
	}

	@Override
	public String toString() {
		if (denominator == 1)
			return numerator + "";
		else
			return numerator + "/" + denominator;
	}

	@Override
	public boolean equals(Object obj) {
		if ((this.subtract((Rational) obj)).getNumerator() == 0)
			return true;
		else
			return false;
	}

	@Override
	public int hashCode() {
		return new Double(this.doubleValue()).hashCode();
	}

	@Override
	public int compareTo(Rational o) {
		// TODO Auto-generated method stub
		if ((this.subtract(o)).getNumerator() > 0)
			return 1;
		else if ((this.subtract(o)).getNumerator() < 0)
			return -1;
		else
			return 0;
	}

	@Override
	public int intValue() {
		// TODO Auto-generated method stub
		return (int) doubleValue();
	}

	@Override
	public long longValue() {
		// TODO Auto-generated method stub
		return (long) doubleValue();
	}

	@Override
	public float floatValue() {
		// TODO Auto-generated method stub
		return (float) doubleValue();
	}

	@Override
	public double doubleValue() {
		// TODO Auto-generated method stub
		return numerator * 1.0 / denominator;
	}

}
