package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TestClass {

	private static final String DB_PASS = "SECRET123";
	private static final String DB_PORT = "3306";
	private static final String DB_HOST = "localhost";
	private static final String DB_NAME = "javatask";
	private static final String DB_USER = "root";

	public static void main(String[] args) {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:myslq://"+ DB_HOST +":"+ DB_PORT +"/"+ DB_NAME, DB_USER, DB_PASS);
			Statement statement = connection.createStatement();
			
			connection.setAutoCommit(false);
			connection.createStatement().executeUpdate("e.g create table");
			connection.commit();
			
			statement.close();
		} catch (ClassNotFoundException | SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		finally {
			if (connection!=null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
