package Model;

public class Level {

	private long id;
	private String name;
	private double price;
	private double pricePerMonth;
	private Course course;

	public Level() {

	}

	public Level(long id, String name, double price, double pricePerMonth) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.pricePerMonth = pricePerMonth;
	}

	public Level(long id, String name, double price, double pricePerMonth, Course couse) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.pricePerMonth = pricePerMonth;
		this.course = couse;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
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

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course couse) {
		this.course = couse;
	}

	@Override
	public String toString() {
		return "Level [id=" + id + ", name=" + name + ", price=" + price + ", pricePerMonth=" + pricePerMonth
				+ ", course=" + course + "]";
	}

}
