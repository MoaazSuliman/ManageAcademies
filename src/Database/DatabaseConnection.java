package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnection {
	private Connection connection;
	private String host = "jdbc:sqlite:studentcourse.db";
	private String user = "root";
	private String password = "";

	public Connection createConnection() {
		try {
			connection = DriverManager.getConnection(host, user, password);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return connection;
	}

	public void closeConnectionWithResult(Connection connection, PreparedStatement s, ResultSet res)
			throws SQLException {
		connection.close();
		s.close();
		res.close();
	}

	public void closeConnection(Connection connection, PreparedStatement s) throws SQLException {
		connection.close();
		s.close();
	}
}
