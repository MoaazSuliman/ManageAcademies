package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Student;

public class StudentDatabase extends DatabaseConnection {

	private Connection connection;
	private PreparedStatement s;
	private String sql;
	private ResultSet result;

	public void addStudent(Student student) throws SQLException {
		connection = createConnection();
		sql = "INSERT INTO STUDENT (name , email , phone , address , age ) VALUES (? , ? , ? ,? , ?)";
		s = connection.prepareStatement(sql);
		s.setString(1, student.getName());
		s.setString(2, student.getEmail());
		s.setString(3, student.getPhone());
		s.setString(4, student.getAddress());
		s.setString(5, student.getAge());
		s.execute();
		if (s != null)
			s.close();
		if (connection != null)
			connection.close();

		// close the resources in reverse order

	}

	public void updateStudent(Student student) throws SQLException {
		connection = createConnection();
		sql = "UPDATE STUDENT SET  name = ?  , email=?  , phone = ?  , address=? , age=? WHERE id = ?";
		s = connection.prepareStatement(sql);
		s.setString(1, student.getName());
		s.setString(2, student.getEmail());
		s.setString(3, student.getPhone());
		s.setString(4, student.getAddress());
		s.setString(5, student.getAge());
		s.setLong(6, student.getId());
		s.execute();

		if (s != null)
			s.close();
		if (connection != null)
			connection.close();
	}

	public void deleteStudent(long studentId) throws SQLException {
		connection = createConnection();
		sql = "DELETE FROM student WHERE id = ?";
		s = connection.prepareStatement(sql);
		s.setLong(1, studentId);
		s.execute();
		if (s != null)
			s.close();
		if (connection != null)
			connection.close();

	}

	public Student getStudentById(long studentId) throws SQLException {
		connection = createConnection();
		sql = "SELECT * FROM STUDENT WHERE id =?";
		s = connection.prepareStatement(sql);
		s.setLong(1, studentId);
		result = s.executeQuery();
		Student student = new Student();
		if (result.next())
			student = new Student(result.getInt(1), result.getString(2), result.getString(3), result.getString(4),
					result.getString(5), result.getString(6));
		if (result != null)
			result.close();
		if (s != null)
			s.close();
		if (connection != null)
			connection.close();
		return student;
	}

	public ArrayList<Student> getAllStudents() throws SQLException {
		connection = createConnection();
		sql = "SELECT * FROM STUDENT ";
		s = connection.prepareStatement(sql);
		result = s.executeQuery();
		ArrayList<Student> students = new ArrayList<>();
		while (result.next())
			students.add(new Student(result.getLong(1), result.getString(2), result.getString(3), result.getString(4),
					result.getString(5), result.getString(6)));
		if (result != null)
			result.close();
		if (s != null)
			s.close();
		if (connection != null)
			connection.close();
		return students;
	}

	public ArrayList<Student> search(String condition) throws SQLException {
		connection = createConnection();
		condition = condition + "%";
		sql = "SELECT * FROM student WHERE name like '" + condition + "'";
		s = connection.prepareStatement(sql);
		result = s.executeQuery();
		ArrayList<Student> students = new ArrayList<>();
		while (result.next())
			students.add(new Student(result.getLong(1), result.getString(2), result.getString(3), result.getString(4),
					result.getString(5), result.getString(6)));

		if (result != null)
			result.close();
		if (s != null)
			s.close();
		if (connection != null)
			connection.close();
		return students;
	}
}
