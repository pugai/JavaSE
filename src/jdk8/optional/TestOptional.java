package jdk8.optional;

import java.util.Optional;

import org.junit.Test;

public class TestOptional {
	
	@Test
	public void test4 () {
		Optional<Employee> op = Optional.ofNullable(new Employee("zhangsan", 18));
		
//		Optional<String> str = op.map((e) -> e.getName());
//		System.out.println(str.get());
		
		//在lambda表达式中返回的也必须用Optional包装一下，进一步防止空指针
		Optional<String> str = op.flatMap((e) -> Optional.of(e.getName()));
		System.out.println(str.get());
		
	}
	
	@Test
	public void test3() {
//		Optional<Employee> op = Optional.ofNullable(new Employee("lisi", 22));
		Optional<Employee> op = Optional.ofNullable(null);
		
//		if (op.isPresent()) {
//			System.out.println(op.get());
//		}
		
//		Employee emp = op.orElse(new Employee("zhangsan", 18));
//		System.out.println(emp);
		
		Employee emp = op.orElseGet(() -> new Employee());
		System.out.println(emp);
	}
	
	@Test
	public void test2() {
		Optional<Employee> op = Optional.empty();
		
		System.out.println(op.get());
	}
	
	@Test
	public void test1(){
	Optional<Employee> op = Optional.of(null);
	
	Employee employee = op.get();
	System.out.println(employee);
	}
	
	
	
	// 例题，应用 Optional
	@Test
	public void test5 () {
//		Man man = new Man();
//		String n = getGodnessName(man);
//		System.out.println(n);
		
		Optional<Godness> gn = Optional.ofNullable(new Godness("boduo"));
		Optional<NewMan> op = Optional.ofNullable(new NewMan(gn));
		String str = getGodnessName2(op);
		System.out.println(str);
	}
	
	public String getGodnessName2 (Optional<NewMan> man){
		return man.orElse(new NewMan())
			.getGodness()
			.orElse(new Godness("canglaoshi"))
			.getName();
	}
	
	// 需求：获取一个男人心中女神的名字
	public String getGodnessName (Man man) {
		if (man  != null) {
			if (man.getGodness() != null) {
				return man.getGodness().getName();
			}
		}
		return "canglaoshi";
//		return man.getGodness().getName(); //空指针异常
	}

}


