package collections.map;

import java.util.HashMap;
import java.util.Map;

public class MyKey {
	
	private String name;
	
	public MyKey(String name) {
		this.name = name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MyKey other = (MyKey) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MyKey [name=" + name + "]";
	}
	
	public static void main(String[] args) {
		MyKey key = new MyKey("first");
		Map<MyKey, String> map = new HashMap<>();
		map.put(key, "v");
		key.setName("second");
		System.out.println(map.get(new MyKey("first"))); // null
		System.out.println(map.get(key)); // null
		
	}
	
	

}
