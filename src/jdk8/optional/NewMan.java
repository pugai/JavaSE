package jdk8.optional;

import java.util.Optional;

public class NewMan {
	
	// 可有可null的数据域可以用Optional包装
	private Optional<Godness> godness = Optional.empty();
	
	public NewMan() {
	}

	public NewMan(Optional<Godness> godness) {
		this.godness = godness;
	}
	
	public Optional<Godness> getGodness() {
		return godness;
	}
	
	public void setGodness(Optional<Godness> godness) {
		this.godness = godness;
	}

	@Override
	public String toString() {
		return "NewMan [godness=" + godness + "]";
	}
	
}
