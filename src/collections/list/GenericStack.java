package collections.list;

/**
 * 使用泛型定义一个存储元素的数据列表（栈）
 **/
public class GenericStack<E> {
	private java.util.ArrayList<E> list = new java.util.ArrayList<E>();

	// 返回栈的元素个数
	public int getSize() {
		return list.size();
	}

	// 返回栈顶元素
	public E peek() {
		return list.get(getSize() - 1);
	}

	// 返回栈顶元素并删除
	public E pop() {
		E o = peek();
		list.remove(getSize() - 1);
		return o;
	}

	// 向栈顶添加一个元素
	public void push(E o) {
		list.add(o);
	}

	// 判断栈是否为空
	public boolean isEmpty() {
		return list.isEmpty();
	}
}