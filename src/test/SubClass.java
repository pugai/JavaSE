package test;

public class SubClass extends SuperClass {

	private int mSubX = 1;
	
	public SubClass() {
	}
	
	@Override
	public void setmSuperX(int mSuperX) {
		super.setmSuperX(mSuperX);
		mSubX = mSuperX;
		System.out.println("SubX is assigned " + mSuperX);
	}
	
	public void printX() {
		System.out.println("SubX = " + mSubX);
	}
	
}