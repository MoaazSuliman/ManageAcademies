package Database;

import java.security.cert.Certificate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Method;
import Model.UserDetails;

public class UserDetailsDatabase extends DatabaseConnection {

	private Connection connection;
	private String sql;
	private PreparedStatement s;
	private ResultSet result;

	public void addUserDetails(UserDetails userDetails) throws SQLException {
		connection = createConnection();
		sql = "INSERT INTO userdetails (studentName  , courseName , money , subscription , date , userId) VALUES(?,?,?,?,?,?)";
		s = connection.prepareStatement(sql);
		s.setString(1, userDetails.getStudentName());
		s.setString(2, userDetails.getCourseName());
		s.setDouble(3, userDetails.getMoney());
		s.setString(4, userDetails.getSub());
		s.setString(5, new Method().returnDate());
		s.setLong(6, userDetails.getUserId());
		s.execute();
	}

	public ArrayList<UserDetails> getUserDetailsForUserByUserId(long userId) throws SQLException {
		connection = createConnection();
		sql = "SELECT * FROM userdetails WHERE userId =?";
		s = connection.prepareStatement(sql);
		s.setLong(1, userId);
		result = s.executeQuery();
		ArrayList<UserDetails> userDetails = new ArrayList<>();
		while (result.next()) {
			userDetails.add(new UserDetails(result.getLong(1), result.getString(2), result.getString(3),
					result.getDouble(4), result.getString(5), result.getString(6), result.getLong(7)));
		}
		return userDetails;
	}

	public void deleteUserDetails(long id) throws SQLException {
		connection = createConnection();
		sql = "DELETE FROM userdetails WHERE id = ?";
		s = connection.prepareStatement(sql);
		s.setLong(1, id);
		s.execute();
	}

	public void clearAllDataForUser(long userId) throws SQLException {
		connection = createConnection();
		sql = "DELETE FROM userdetails WHERE userId=?";
		s = connection.prepareStatement(sql);
		s.setLong(1, userId);
		s.execute();
	}

	public ArrayList<UserDetails> getAllByUserAndDate(String date, long userId) throws SQLException {
		connection = createConnection();
		date += "%";
		sql = "SELECT * FROM userdetails WHERE userId =? AND date like '" + date + "'";
		s = connection.prepareStatement(sql);
		s.setLong(1, userId);
		ArrayList<UserDetails> details = new ArrayList<>();
		result = s.executeQuery();
		while (result.next())
			details.add(new UserDetails(result.getLong(1), result.getString(2), result.getString(3),
					result.getDouble(4), result.getString(5), result.getString(6), result.getLong(7)));
		return details;
	}

}
