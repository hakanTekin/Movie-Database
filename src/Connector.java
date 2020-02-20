import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public abstract class Connector {
	
	/*
	 * This creates a connection.
	 * It returns a connection object. Thats how it works (look at the TableCreator class main method)
	 * You are supposed to send database user name and password to this method.
	 * 
	*/
	
	public static Connection CreateConnection(String username, String password) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC Driver not Found!!!");
			JOptionPane.showMessageDialog(null, "JDBC Driver not Found", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return null;
		}
		Connection con = null;
		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "ADMIN", "amk123");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "->Incorrect username or password\n->Database connection could not be established", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return null;
		}
		return con;
	}
	//Terminate your connection when the program is done.
	public static void TerminateConnection(Connection con) {
		
		try {
			con.close();
		} catch (SQLException e) {
			System.out.println("Cannot terminate the connection safely.");
			e.printStackTrace();
		}
	}
	
	
}
