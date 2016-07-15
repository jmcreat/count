package kr.co.adflow.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class CommonDBCP {

	private static Connection con = null;

	private CommonDBCP() {
	}

//

	public static Connection getConn() {
		if (con == null) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://192.168.0.70:3306/counter", "root", "0000");
				System.out.println("conn");
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			return con;
		}
	
		return con;
	}
	
	public static void closeConnection(){
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
