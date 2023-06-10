package Model;

public class UserDetails {

	private long id;
	private String studentName;
	private String courseName;
	private double money;
	private String sub;
	private String date;
	private long userId;

	public UserDetails() {

	}

	public UserDetails(long id, String studentName, String courseName, double money, String sub, String date,
			long userId) {
		super();
		this.id = id;
		this.studentName = studentName;
		this.courseName = courseName;
		this.money = money;
		this.sub = sub;
		this.date = date;
		this.userId = userId;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStudentName() {
		return this.studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "UserDetails [id=" + id + ", studentName=" + studentName + ", courseName=" + courseName + ", money="
				+ money + ", sub=" + sub + ", date=" + date + ", userId=" + userId + "]";
	}

}
