package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Course;
import Model.Level;
import Model.Method;
import Model.StudentCourse;

public class StudentCourseDatabase extends DatabaseConnection {

	private Connection connection;
	private String sql;
	private PreparedStatement s;
	private ResultSet result;

	private StudentDatabase studentDatabase = new StudentDatabase();
	private CourseDatabase courseDatabase = new CourseDatabase();
	private LevelDatabase levelDatabase = new LevelDatabase();

	public ArrayList<StudentCourse> getAllAssigns() throws SQLException {
		connection = createConnection();
		sql = "SELECT * FROM studentcourse";
		s = connection.prepareStatement(sql);
		result = s.executeQuery();
		ArrayList<StudentCourse> assigns = new ArrayList<>();
		while (result.next()) {
			if (result.getLong(4) < 0) {

				Level level = new Level();
				level.setId(result.getLong(4));
				assigns.add(new StudentCourse(result.getLong(1), studentDatabase.getStudentById(result.getLong(2)),
						courseDatabase.getCourseById(result.getLong(3)), level, result.getDouble(5),
						result.getDouble(6), result.getInt(7), result.getString(8)));
			} else

				assigns.add(new StudentCourse(result.getLong(1), studentDatabase.getStudentById(result.getLong(2)),
						courseDatabase.getCourseById(result.getLong(3)), levelDatabase.getLevelById(result.getLong(4)),
						result.getDouble(5), result.getDouble(6), result.getInt(7), result.getString(8)));
		}
		if (result != null)
			result.close();
		if (s != null)
			s.close();
		if (connection != null)
			connection.close();
		return assigns;

	}

	public void addStudentCourse(StudentCourse studentCourse) throws SQLException {
		connection = createConnection();
		sql = "INSERT INTO studentcourse (studentId , courseId ,levelId,  money ,courseMoney, months , date) VALUES (?,?,? ,?, ? , ? ,?)";
		s = connection.prepareStatement(sql);
		s.setLong(1, studentCourse.getStudent().getId());
		s.setLong(2, studentCourse.getCourse().getId());
		s.setLong(3, studentCourse.getLevel().getId());
		s.setDouble(4, studentCourse.getPayedMoney());
		s.setDouble(5, studentCourse.getCourseMoney());
		s.setInt(6, studentCourse.getMonths());
		s.setString(7, new Method().returnDate());
		s.execute();

		if (s != null)
			s.close();
		if (connection != null)
			connection.close();
	}

	public void deleteStudentCourseById(long studentCourseId) throws SQLException {
		connection = createConnection();
		sql = "DELETE FROM studentcourse WHERE id =?";
		s = connection.prepareStatement(sql);
		s.setLong(1, studentCourseId);
		s.execute();

		if (s != null)
			s.close();
		if (connection != null)
			connection.close();
	}

	public void updateMoney(StudentCourse studentCourse, double newMoney) throws SQLException {
		connection = createConnection();
		sql = "UPDATE  studentcourse SET money =? WHERE id =?";
		s = connection.prepareStatement(sql);
		s.setDouble(1, newMoney);
		s.setLong(2, studentCourse.getId());
		s.execute();

		if (s != null)
			s.close();
		if (connection != null)
			connection.close();
	}

	public void deleteAllStudentAssigns(long studentId) throws SQLException {
		System.out.println("deleted");
		connection = createConnection();
		sql = "DELETE  FROM studentcourse WHERE studentId=?";
		s = connection.prepareStatement(sql);
		s.setLong(1, studentId);
		s.execute();

		if (s != null)
			s.close();
		if (connection != null)
			connection.close();
	}

	public void deleteAllCourseAssigns(long courseId) throws SQLException {
		connection = createConnection();
		sql = "DELETE  FROM studentcourse WHERE courseId=?";
		s = connection.prepareStatement(sql);
		s.setLong(1, courseId);
		s.execute();

		if (s != null)
			s.close();
		if (connection != null)
			connection.close();
	}

	public void addBouns(double bouns, long studentCourseId) throws SQLException {
		connection = createConnection();
		sql = "UPDATE studentcourse SET money=? WHERE id =?";
		s = connection.prepareStatement(sql);
		StudentCourse studentCourse = getAssignById(studentCourseId);
		s.setDouble(1, bouns + studentCourse.getPayedMoney());
		s.setLong(2, studentCourseId);
		s.execute();
		if (s != null)
			s.close();
		if (connection != null)
			connection.close();
	}

	private StudentCourse getAssignById(long studentCourseId) throws SQLException {
		connection = createConnection();
		sql = "SELECT * 	FROM studentcourse WHERE id =?";
		s = connection.prepareStatement(sql);
		s.setLong(1, studentCourseId);
		result = s.executeQuery();
		StudentCourse studentCourse = new StudentCourse();
		if (result.next()) {
			studentCourse.setId(result.getLong(1));
			studentCourse.setPayedMoney(result.getDouble(5));
		}
		if (result != null)
			result.close();
		if (s != null)
			s.close();
		if (connection != null)
			connection.close();
		return studentCourse;
	}
}
