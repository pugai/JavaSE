package object;

/**
 * 重写finalize方法，该方法由JAVA虚拟机调用，不要自己调用
 * @author tianlong
 *
 */
public class FinalizationDemo {

	public static void main(String[] args) {
		Cake c1 = new Cake(1);
		Cake c2 = new Cake(2);
		Cake c3 = new Cake(3);
		
		c1 = c2 = null;
		System.gc();
	}
	
}

class Cake {
	private int id;
	public Cake(int id) {
		this.id = id;
		System.out.println("Cake object " + id + " is created");
	}
	
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		System.out.println("Cake object " + id + " is disposed");
	}
}
