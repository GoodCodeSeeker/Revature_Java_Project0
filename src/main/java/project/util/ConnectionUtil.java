package project.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
	
	// Private static instance
	
	private static Connection conn = null;
	
	// Private constructor
	
	private ConnectionUtil() {
	}
	
	// Public static getInstance() method
	
	public static Connection getConnection() {
		
		// Check to see if there is a connection instance
		// If there is then return it
		
		try {
			if (conn != null && !conn.isClosed()) {
				System.out.println("Using a previously made connection");
				return conn;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
		// If not we'll set one up
		
		// This is the not secure method
		
		// This will change when we talk about cloud services later this week
//		String url = "jdbc:postgresql://localhost:5432/postgres?currentSchema=bank";
//		String username = "postgres";
//		String password = "postgres";
//		
//		try {
//			conn = DriverManager.getConnection(url, username, password);
//			System.out.println("Established Connection to Database!");
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			System.out.println("Cannot establish connection");
//			e.printStackTrace();
//		}
		
		// Let's look at a more secure method
		
		String url = "";
		String username = "";
		String password = "";
		
		Properties prop = new Properties();
		
		try {
			
			prop.load(new FileReader("C:\\Users\\The_Plan\\Documents\\workspace-spring-tool-suite-4-4.14.1.RELEASE\\project0\\src\\main\\resources\\resources\\application.properties"));
			
			url = prop.getProperty("url");
			username = prop.getProperty("username");
			password = prop.getProperty("password");
			
			conn = DriverManager.getConnection(url, username, password);
			System.out.println("Established Connection to Database!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Cannot establish connection");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.out.println("Cannot find properties file");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Something's wrong with the properties file");
			e.printStackTrace();
		}
		
		/*
		// A final even more secure method
		String url = System.getenv("DB_URL");
		String username = System.getenv("DB_USERNAME");
		String password = System.getenv("DB_PASSWORD");
		
		try {
			conn = DriverManager.getConnection(url, username, password);
			System.out.println("Established Connection to Database!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Cannot establish connection");
			e.printStackTrace();
		}
		*/
		return conn;
	}

}
