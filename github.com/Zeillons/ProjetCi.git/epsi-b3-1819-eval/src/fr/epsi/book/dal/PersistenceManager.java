package fr.epsi.book.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PersistenceManager {
	
	private static final String DB_URL = "jdbc:mysql://shyndard.eu:3306/epsi";
	private static final String DB_LOGIN = "epsi";
	private static final String DB_PWD = "epsi";
	
	private static Connection connection;
	
	private PersistenceManager() {}
	
	public static Connection getConnection() throws SQLException {
		if ( null == connection || connection.isClosed() ) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				connection = DriverManager.getConnection( DB_URL, DB_LOGIN, DB_PWD );
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return connection;
	}
	
	public static void closeConnection() throws SQLException {
		if ( null != connection && !connection.isClosed() ) {
			connection.close();
		}
	}
}
