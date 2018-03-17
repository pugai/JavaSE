package test;

import java.io.IOException;

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

	public static void main(String[] args) {
		//考虑对象初始化顺序，运行时方法调用
		SubClass sc = new SubClass();
		sc.printX();
	}
	
	@Override
	public Integer testOverride(Number number) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
