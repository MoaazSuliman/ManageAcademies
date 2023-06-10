package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Course;
import Model.Student;

public class CourseDatabase extends DatabaseConnection {

	private Connection connection;
	private String sql;
	private PreparedStatement s;
	private ResultSet result;

	public void addCourse(Course course) throws SQLException {
		connection = createConnection();
		sql = "INSERT INTO COURSE (name , description  , price , pricePerMonth  ) VALUES (?,?,? , ?)";
		s = connection.prepareStatement(sql);

		s.setString(1, course.getName());
		s.setString(2, course.getDescription());
		s.setDouble(3, course.getPrice());
		s.setDouble(4, course.getPricePerMonth());
		s.execute();

		if (s != null)
			s.close();
		if (connection != null)
			connection.close();
	}

	public void updateCourse(Course course) throws SQLException {
		connection = createConnection();
		sql = "UPDATE Course SET  name=? ,description=?  , price=?  , pricePerMonth=?  WHERE id = ? ";
		s = connection.prepareStatement(sql);

		s.setString(1, course.getName());
		s.setString(2, course.getDescription());
		s.setDouble(3, course.getPrice());
		s.setDouble(4, course.getPricePerMonth());
		s.setLong(5, course.getId());
		s.execute();

		if (s != null)
			s.close();
		if (connection != null)
			connection.close();
	}

	public Course getCourseById(long courseId) throws SQLException {
		connection = createConnection();
		sql = "SELECT * FROM COURSE WHERE id =? ";
		s = connection.prepareStatement(sql);
		s.setLong(1, courseId);
		result = s.executeQuery();
		Course course = new Course();
		if (result.next())
			course = new Course(result.getLong(1), result.getString(2), result.getString(3), result.getDouble(4),
					result.getDouble(5));
		if (result != null)
			result.close();
		if (s != null)
			s.close();
		if (connection != null)
			connection.close();
		return course;
	}

	public void deleteCourseById(long courseId) throws SQLException {
		connection = createConnection();
		sql = "DELETE FROM COURSE WHERE id = ?";
		s = connection.prepareStatement(sql);
		s.setLong(1, courseId);
		s.execute();

		if (s != null)
			s.close();
		if (connection != null)
			connection.close();
	}

	public ArrayList<Course> getAllCourse() throws SQLException {
		connection = createConnection();
		sql = "SELECT * FROM COURSE";
		s = connection.prepareStatement(sql);
		result = s.executeQuery();
		ArrayList<Course> courses = new ArrayList<>();
		while (result.next())
			courses.add(new Course(result.getLong(1), result.getString(2), result.getString(3), result.getDouble(4),
					result.getDouble(5)));
		if (result != null)
			result.close();
		if (s != null)
			s.close();
		if (connection != null)
			connection.close();

		return courses;

	}

	public ArrayList<Course> search(String condition) throws SQLException {
		connection = createConnection();
		condition = condition + "%";
		sql = "SELECT * FROM course WHERE name like '" + condition + "'";
		s = connection.prepareStatement(sql);
		result = s.executeQuery();
		ArrayList<Course> courses = new ArrayList<>();
		while (result.next())
			courses.add(new Course(result.getLong(1), result.getString(2), result.getString(3), result.getDouble(4),
					result.getDouble(5)));

		result.close();
		s.close();
		connection.close();
		return courses;
	}

}
