package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestClass {

	private static final String DB_PASS = "root123";
	private static final String DB_PORT = "3306";
	private static final String DB_HOST = "localhost";
	private static final String DB_NAME = "javatask";
	private static final String DB_USER = "root";

	public static void main(String[] args) {

		DBActions<Employee> dbaction = new DBActions<Employee>();
		Employee employee = new Employee("gosho", "toshov", 5);

		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME, DB_USER,
					DB_PASS);
			connection.setAutoCommit(false);
			try {
				connection.createStatement().executeUpdate(dbaction.createTable(employee));
			connection.commit();
			} catch (SQLException e) {
				connection.rollback();
				e.printStackTrace();
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
