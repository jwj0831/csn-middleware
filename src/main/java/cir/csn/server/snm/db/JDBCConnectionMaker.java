package cir.csn.server.snm.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnectionMaker implements ConnectionMaker {
	private static String DB_PATH = "jdbc:mysql://127.0.0.1:3306/csn_test";
	private static String DB_USER = "root";
	private static String DB_PW = "root";

	public Connection makeConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection c  = DriverManager.getConnection(DB_PATH, DB_USER, DB_PW);
		return c;
	}
}