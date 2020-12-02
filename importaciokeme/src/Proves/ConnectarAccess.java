package Proves;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;


public class ConnectarAccess {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		String filename ="/home/alvar/Dropbox/Privat/AMCastàlia/Socis/AMCASTALIA_2019_04_01_NovaVersió.accdb";
		String url = "jdbc:ucanaccess://"+filename;
		Connection con = DriverManager.getConnection(url);
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM [LIBRO BANCO] WHERE YEAR([FECHA OPERACIÓN])=2018 ORDER BY 2");
		while (rs.next()) {
			System.out.println(rs.getInt(1) + " " + rs.getDate(2) + " " + rs.getString(5));
		}
		rs.close();
		st.close();
		con.close();
	}

}
