package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.User;

public class UserDatabase extends DatabaseConnection {

	private Connection connection;
	private String sql;
	private PreparedStatement s;
	private ResultSet result;

	// RETURN USER OR RETURN NULL IF NOT EXIST.
	public User getUserByUsernameAndPassword(String username, String password) throws SQLException {
		connection = createConnection();
		sql = "SELECT * FROM user WHERE username =? AND password =? ";
		s = connection.prepareStatement(sql);
		s.setString(1, username);
		s.setString(2, password);
		result = s.executeQuery();
		User user = null;
		if (result.next())
			user = new User(result.getLong(1), result.getString(2), result.getString(3), result.getString(4),
					result.getString(5));
		if (result != null)
			result.close();
		if (s != null)
			s.close();
		if (connection != null)
			connection.close();
		return user;
	}

	public void addUser(User user) throws SQLException {
		connection = createConnection();
		sql = "INSERT INTO user (username , password , phone , age ) VALUES  (?,?,?,?)";
		s = connection.prepareStatement(sql);
		s.setString(1, user.getUsername());
		s.setString(2, user.getPassword());
		s.setString(3, user.getPhone());
		s.setString(4, user.getAge());
		s.execute();
		if (s != null)
			s.close();
		if (connection != null)
			connection.close();
	}

	public void updateUser(User user) throws SQLException {
		connection = createConnection();
		sql = "UPDATE user SET username =? , password =? , phone =? , age =?  WHERE id =? ";
		s = connection.prepareStatement(sql);
		s.setString(1, user.getUsername());
		s.setString(2, user.getPassword());
		s.setString(3, user.getPhone());
		s.setString(4, user.getAge());
		s.setLong(5, user.getId());
		s.execute();
		if (s != null)
			s.close();
		if (connection != null)
			connection.close();
	}

	public void deleteUser(long userId) throws SQLException {
		connection = createConnection();
		sql = "DELETE FROM user WHERE id =?";
		s = connection.prepareStatement(sql);
		s.setLong(1, userId);
		s.execute();
		if (s != null)
			s.close();
		if (connection != null)
			connection.close();
	}

	public ArrayList<User> getAllUsers() throws SQLException {

		connection = createConnection();
		sql = "SELECT * FROM user";
		s = connection.prepareStatement(sql);
		result = s.executeQuery();
		ArrayList<User> users = new ArrayList<>();
		while (result.next())
			users.add(new User(result.getLong(1), result.getString(2), result.getString(3), result.getString(4),
					result.getString(5)));
		if (result != null)
			result.close();
		if (s != null)
			s.close();
		if (connection != null)
			connection.close();
		return users;
	}

	public User getUserById(long userId) throws SQLException {
		connection = createConnection();
		sql = "SELECT * FROM user WHERE id =? ";
		s = connection.prepareStatement(sql);
		s.setLong(1, userId);
		result = s.executeQuery();
		User user = null;
		if (result.next())
			user = new User(result.getLong(1), result.getString(2), result.getString(3), result.getString(4),
					result.getString(5));
		if (result != null)
			result.close();
		if (s != null)
			s.close();
		if (connection != null)
			connection.close();
		return user;
	}

}
