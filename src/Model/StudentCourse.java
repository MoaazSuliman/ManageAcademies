package Model;

import java.util.ArrayList;

public class StudentCourse {

	private long id;
	private Student student;
	private Course course;
	private Level level;
	private double money;
	private double courseMoney;
	private int months;
	private String date;

	public StudentCourse() {
	}

	public StudentCourse(long id, Student student, Course course, Level level, double payedMoney, double courseMoney,
			int months) {
		this.id = id;
		this.student = student;
		this.course = course;
		this.money = payedMoney;
		this.courseMoney = courseMoney;
		this.months = months;
		this.level = level;
	}

	public StudentCourse(long id, Student student, Course course, Level level, double payedMoney, double courseMoney,
			int months, String date) {
		this();
		this.id = id;
		this.student = student;
		this.course = course;
		this.money = payedMoney;
		this.courseMoney = courseMoney;
		this.date = date;
		this.months = months;
		this.level = level;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public double getPayedMoney() {
		return this.money;
	}

	public void setPayedMoney(double payedMoney) {
		this.money = payedMoney;
	}

	public double getCourseMoney() {
		return this.courseMoney;
	}

	public void setCourseMoney(double courseMoney) {
		this.courseMoney = courseMoney;
	}

	public Level getLevel() {
		return this.level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public double getMoney() {
		return this.money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public int getMonths() {
		return this.months;
	}

	public void setMonths(int months) {
		this.months = months;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public ArrayList<StudentCourse> getByStudentName(ArrayList<StudentCourse> assigns, String condition) {
		ArrayList<StudentCourse> empty = new ArrayList<>();
		for (int i = 0; i < assigns.size(); i++) {
			if (assigns.get(i).getStudent().getName().contains(condition)) {
				empty.add(assigns.get(i));
			}
		}
		return empty;
	}

	public ArrayList<StudentCourse> getByCourseName(ArrayList<StudentCourse> assigns, String courseName) {
		ArrayList<StudentCourse> empty = new ArrayList<>();
		for (int i = 0; i < assigns.size(); i++) {
			if (assigns.get(i).getCourse().getName().contains(courseName)) {
				empty.add(assigns.get(i));
			}
		}
		return empty;
	}

	@Override
	public String toString() {
		return "StudentCourse [id=" + id + ", student=" + student + ", course=" + course + ", level=" + level
				+ ", money=" + money + ", courseMoney=" + courseMoney + ", months=" + months + ", date=" + date + "]";
	}

}
