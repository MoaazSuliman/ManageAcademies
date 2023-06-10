package Model;

public class Course {

	private long id;
	private String name;
	private String description;
	private double price;
	private double pricePerMonth;

	public Course() {

	}

	public Course(long id, String name, String description, double price, double pricePerMonth) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.pricePerMonth = pricePerMonth;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getPricePerMonth() {
		return pricePerMonth;
	}

	public void setPricePerMonth(double pricePerMonth) {
		this.pricePerMonth = pricePerMonth;
	}

}
