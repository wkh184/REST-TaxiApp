package com.taxiapp.respository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;

import com.sun.xml.rpc.tools.wscompile.UsageIf.UsageError;
import com.taxiapp.models.UserModel;

public class UserStore {
	
	public ArrayList<UserModel> GetAll() {
		// TODO get all from database, return array list of users
		ArrayList<UserModel> users = new ArrayList();
		Connection conn = DataConnection.GetConnection();
		if (conn != null) {
			// SELECT * FROM users
			try {
				Statement stmt = conn.createStatement();
				ResultSet result = stmt.executeQuery("SELECT * FROM users");
				
				while (result.next()) {

					UserModel user = new UserModel();
					user.UserId = result.getInt("user_id");
					user.Username = result.getString("username");
					user.Password = result.getString("password");
					user.FirstName = result.getString("first_name");
					user.LastName = result.getString("last_name");
					user.FailedLoginCount = result.getInt("failed_login_count");

					String last_failed_login_date_str = result.getString("last_failed_login_date");
					
					if (last_failed_login_date_str != null) {
						java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						user.LastFailedLoginDate = new Date(sdf.parse(last_failed_login_date_str).getTime());
					}
					
					user.Role = result.getString("role");
					
					users.add(user);
				}
				
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return users;
	}
	
	public boolean Add(UserModel user) {
		// TODO add user, check for duplicate username, return true or false for success
		boolean result = true;
		
		Connection conn = DataConnection.GetConnection();
		if (conn != null) {
			try {
				
				PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (username, password, first_name, last_name, failed_login_count, last_failed_login_date, role) VALUES (?, ?, ?, ?, ?, ?, ?)");
				stmt.setString(1, user.Username);
				stmt.setString(2, user.Password);
				stmt.setString(3, user.FirstName);
				stmt.setString(4, user.LastName);
				stmt.setInt(5, 0);
				stmt.setDate(6, null);
				stmt.setString(7, user.Role);
				
				stmt.execute();
				
			} catch (Exception e) {
				result = false;
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public boolean IsUserNameExists(String username) {
		boolean result = false;
		
		Connection conn = DataConnection.GetConnection();
		// EXERCISE #1
		
		// SQL SELECT * FROM user WHERE username = username
		if (conn != null) {
			try {
				// Statement / Prepared Statement
				/*
				Statement stmt = conn.createStatement();
				ResultSet resultSet = stmt.executeQuery("SELECT * FROM users WHERE username = '" + username + "'");
				*/
				PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
				stmt.setString(1, username);
				ResultSet resultSet = stmt.executeQuery();
				
				// Result Set
				result = resultSet.next();
				
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public UserModel Get(int id) {
		// TODO return 1 user based on id, if not found, return null
		UserModel user = null;
		Connection conn = DataConnection.GetConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE user_id = ?");
			stmt.setInt(1, id);
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				user = new UserModel();
				user.FirstName = result.getString("first_name");
				user.LastName = result.getString("last_name");
				user.Username = result.getString("username");
				user.Password = result.getString("password");
				user.UserId = result.getInt("user_id");		
				user.FailedLoginCount = result.getInt("failed_login_count");

				String last_failed_login_date_str = result.getString("last_failed_login_date");
				
				if (last_failed_login_date_str != null) {
					java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					user.LastFailedLoginDate = new Date(sdf.parse(last_failed_login_date_str).getTime());
				}
					
				user.Role = result.getString("role");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return user;
	}

	public UserModel Get(String username) {
		// TODO return 1 user based on id, if not found, return null
		UserModel user = null;
		Connection conn = DataConnection.GetConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
			stmt.setString(1, username);
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				user = new UserModel();
				user.FirstName = result.getString("first_name");
				user.LastName = result.getString("last_name");
				user.Username = result.getString("username");
				user.Password = result.getString("password");
				user.UserId = result.getInt("user_id");
				user.FailedLoginCount = result.getInt("failed_login_count");
				
				String last_failed_login_date_str = result.getString("last_failed_login_date");
				
				if (last_failed_login_date_str != null) {
					java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					user.LastFailedLoginDate = new Date(sdf.parse(last_failed_login_date_str).getTime());
				}
				
				user.Role = result.getString("role");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return user;
	}
	
	public UserModel Get(String username, String password) {
		// TODO return 1 user based on id, if not found, return null
		UserModel user = null;
		Connection conn = DataConnection.GetConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? and password = ?");
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				user = new UserModel();
				user.FirstName = result.getString("first_name");
				user.LastName = result.getString("last_name");
				user.Username = result.getString("username");
				user.Password = result.getString("password");
				user.UserId = result.getInt("user_id");
				user.FailedLoginCount = result.getInt("failed_login_count");
				user.Role = result.getString("role");

				String last_failed_login_date_str = result.getString("last_failed_login_date");
				
				if (last_failed_login_date_str != null) {
					java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					user.LastFailedLoginDate = new Date(sdf.parse(last_failed_login_date_str).getTime());
				}
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return user;
	}
	
	public void Update(UserModel user) {
		Connection conn = DataConnection.GetConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement("UPDATE users SET first_name = ?, last_name = ?, failed_login_count = ?, last_failed_login_date = ? WHERE user_id = ?");
			stmt.setString(1, user.FirstName);
			stmt.setString(2, user.LastName);
			stmt.setInt(3, user.FailedLoginCount);
			
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			stmt.setString(4, (user.LastFailedLoginDate != null ? sdf.format(user.LastFailedLoginDate) : null));
			stmt.setInt(5, user.UserId);
			
			stmt.execute();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
