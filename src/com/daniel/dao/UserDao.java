package com.daniel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.daniel.model.User;
import com.daniel.util.DbUtil;

public class UserDao {

	private Connection conn;

	public Connection getConnection() {
		conn = DbUtil.getConnection();
		return conn;
	}
	
	public boolean setupDb() {
		boolean status = false;
		Statement statement = null;
		
				
		try {
			statement = getConnection().createStatement();
			status = statement.execute(
					"CREATE TABLE IF NOT EXISTS sample_users ( " +
					"userid int(11) NOT NULL AUTO_INCREMENT, " +
					"firstname varchar(45) DEFAULT NULL, " +
					"lastname varchar(45) DEFAULT NULL," +
					"dob date DEFAULT NULL, " + 
					"email varchar(100) DEFAULT NULL, " +
					"PRIMARY KEY (userid))");			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(conn, statement, null);
		}
		
		try {
			// This should be in the independent try-catch block
			statement = getConnection().createStatement();
			status = statement.execute("SELECT 1 FROM sample_users");
		} catch (RuntimeException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}  finally {
			DbUtil.close(conn, statement, null);
		}

		return status;
	}

	public void addUser(User user) {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = getConnection()
					.prepareStatement("insert into sample_users(firstname,lastname,dob,email) values (?, ?, ?, ? )");
			// Parameters start with 1
			preparedStatement.setString(1, user.getFirstName());
			preparedStatement.setString(2, user.getLastName());
			preparedStatement.setDate(3, new java.sql.Date(user.getDob().getTime()));
			preparedStatement.setString(4, user.getEmail());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(conn, preparedStatement, null);
		}
	}
	
	public void deleteUser(int userId) {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = getConnection()
					.prepareStatement("delete from sample_users where userid=?");
			// Parameters start with 1
			preparedStatement.setInt(1, userId);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(conn, preparedStatement, null);
		}
	}
	
	public void updateUser(User user) {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = getConnection()
					.prepareStatement("update sample_users set firstname=?, lastname=?, dob=?, email=?" +
							"where userid=?");
			// Parameters start with 1
			preparedStatement.setString(1, user.getFirstName());
			preparedStatement.setString(2, user.getLastName());
			preparedStatement.setDate(3, new java.sql.Date(user.getDob().getTime()));
			preparedStatement.setString(4, user.getEmail());
			preparedStatement.setInt(5, user.getUserid());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
			DbUtil.close(conn, preparedStatement, null);
		}
	}

	public List<User> getAllUsers() {
		List<User> users = new ArrayList<User>();
		Statement statement = null;
		ResultSet rs = null;
		
		try {
			statement = getConnection().createStatement();
			rs = statement.executeQuery("select * from sample_users");
			while (rs.next()) {
				User user = new User();
				user.setUserid(rs.getInt("userid"));
				user.setFirstName(rs.getString("firstname"));
				user.setLastName(rs.getString("lastname"));
				user.setDob(rs.getDate("dob"));
				user.setEmail(rs.getString("email"));
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
			DbUtil.close(conn, statement, rs);
		}

		return users;
	}
	
	public User getUserById(int userId) {
		User user = new User();
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = getConnection().
					prepareStatement("select * from sample_users where userid=?");
			preparedStatement.setInt(1, userId);
			ResultSet rs = preparedStatement.executeQuery();
			
			if (rs.next()) {
				user.setUserid(rs.getInt("userid"));
				user.setFirstName(rs.getString("firstname"));
				user.setLastName(rs.getString("lastname"));
				user.setDob(rs.getDate("dob"));
				user.setEmail(rs.getString("email"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(conn, preparedStatement, null);
		}

		return user;
	}
}
