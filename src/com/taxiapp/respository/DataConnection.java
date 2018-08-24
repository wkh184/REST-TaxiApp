package com.taxiapp.respository;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataConnection {
	public static Connection GetConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/taxiapp?user=taxiapp&password=password&useSSL=false");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}
