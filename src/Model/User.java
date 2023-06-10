package Model;

public class User {
	private long id;
	private String username;
	private String password;
	private String phone;
	private String age;

	public User() {

	}

	public User(long id, String username, String password, String phone, String age) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.phone = phone;
		this.age = age;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", phone=" + phone + ", age="
				+ age + "]";
	}

}
