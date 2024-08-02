package com.kafka.connect.service;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

	private static Connection conn;
	
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/topic", "root", "July12@2024");
		} catch (Exception e) {
			System.out.println(e);
		}
		return conn;
	}
}
