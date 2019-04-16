package fr.epsi.book.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PersistenceManager {
	
	private static final String DB_URL = "jdbc:mysql://149.91.80.135:3306/teddy?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";//&serverTimezone=CEST
	private static final String DB_LOGIN = "teddy";
	private static String DB_PWD = "";
	private static final String ENV = "CITESTENV";
	
	private static Connection connection;
	
	private PersistenceManager() {
		DB_PWD= System.getenv(ENV);
	}
	
	public static Connection getConnection() throws SQLException {
		if ( null == connection || connection.isClosed() ) {
			connection = DriverManager.getConnection( DB_URL, DB_LOGIN, DB_PWD );
		}
		
		return connection;
	}
	
	public static void closeConnection() throws SQLException {
		if ( null != connection && !connection.isClosed() ) {
			connection.close();
		}
	}
}
