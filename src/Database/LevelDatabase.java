package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Level;

public class LevelDatabase extends DatabaseConnection {

	private Connection connection;
	private String sql;
	private PreparedStatement s;
	private ResultSet result;

	public ArrayList<Level> getAllLevelsForCourse(long courseId) throws SQLException {
		connection = createConnection();
		sql = "SELECT * FROM level WHERE courseId=?";
		s = connection.prepareStatement(sql);
		s.setLong(1, courseId);
		ArrayList<Level> levels = new ArrayList<>();
		result = s.executeQuery();
		while (result.next())
			levels.add(new Level(result.getLong(1), result.getString(2), result.getDouble(3), result.getDouble(4)));
		if (result != null)
			result.close();
		if (s != null)
			s.close();
		if (connection != null)
			connection.close();
		return levels;

	}

	public void addLevel(Level level) throws SQLException {
		connection = createConnection();
		sql = "INSERT INTO level (name ,price , pricePerMonth , courseId) VALUES (?,?,?,?)";
		s = connection.prepareStatement(sql);
		s.setString(1, level.getName());
		s.setDouble(2, level.getPrice());
		s.setDouble(3, level.getPricePerMonth());
		s.setLong(4, level.getCourse().getId());
		s.execute();
		if (s != null)
			s.close();
		if (connection != null)
			connection.close();
	}

	public void updateLevel(Level level) throws SQLException {
		connection = createConnection();
		sql = "UPDATE level SET name = ? , price=? , pricePerMonth=?  WHERE id=?";
		s = connection.prepareStatement(sql);
		s.setString(1, level.getName());
		s.setDouble(2, level.getPrice());
		s.setDouble(3, level.getPricePerMonth());
		s.setLong(4, level.getId());
		s.execute();
		if (s != null)
			s.close();
		if (connection != null)
			connection.close();
	}

	public void deleteLevel(long levelId) throws SQLException {
		connection = createConnection();
		sql = "DELETE FROM level WHERE id =?";
		s = connection.prepareStatement(sql);
		s.setLong(1, levelId);
		s.execute();
		if (s != null)
			s.close();
		if (connection != null)
			connection.close();
	}

	public Level getLevelById(long id) throws SQLException {
		connection = createConnection();
		sql = "SELECT * FROM level WHERE id=?";
		s = connection.prepareStatement(sql);
		s.setLong(1, id);
		result = s.executeQuery();
		Level level = null;
		if (result.next())
			level = new Level(result.getLong(1), result.getString(2), result.getDouble(3), result.getDouble(4));
		if (result != null)
			result.close();
		if (s != null)
			s.close();
		if (connection != null)
			connection.close();
		return level;
	}

}
