package main.java.com.andradev.prodreglist;

public class Product {
	private String name;
	private String description;
	private Double value;
	private Boolean available;
	
	public Product(String name, String description, Double value, Boolean available) {
		this.name = name;
		this.description = description;
		this.value = value;
		this.available = available;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public double getValue() {
		return value;
	}
	
	public boolean isAvailable() {
		return available;
	}
}
