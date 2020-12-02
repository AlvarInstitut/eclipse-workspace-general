package creacioPrestecs;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GeneracioPrestecs {
	static Connection con;

	public static void main(String[] args) throws SQLException {
		con = DriverManager.getConnection("jdbc:postgresql://89.36.214.106/bicicas", "bicicas", "bicicas");
		for (int i = 0; i < 9; i++) {
			System.out.println("Préstec " + (i+1));
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date data = new Date();
			System.out.println("\t" + dateFormat.format(data));
			int numUsuari = (int) (Math.random()*5000)+1;
			int numEst = (int) (Math.random()*50)+1;
			int numPos = (int) (Math.random()*28)+1;
			System.out.println("\tIntent de préstec " + numUsuari  + " - " + numEst + " - " + numPos);
			if (prestec(numUsuari, numEst, numPos, dateFormat.format(data)))
				System.out.println("\tCorrecte");
			else
				System.out.println("\tHa fallat alguna cosa");
			System.out.println();
		}

	}

	private static boolean prestec(int num_usu, int num_est, int num_pos, String data_pr) throws SQLException {
		boolean aux = false;
		Statement st1 = con.createStatement();
		ResultSet rs1 = st1.executeQuery("SELECT * FROM USUARI WHERE num_usu=" + num_usu);
		if (rs1.next()) {
			Statement st2 = con.createStatement();
			ResultSet rs2 = st2.executeQuery("SELECT * FROM BICICLETA WHERE usuari_pr=" + num_usu);
			if (rs2.next()) {
				System.out.println("\tL'usuari " + num_usu + " ja té un préstec actiu (bicicleta " + rs2.getInt(1));
			} else {
				// Usuari correcte
				Statement st3 = con.createStatement();
				ResultSet rs3 = st3
						.executeQuery("SELECT * FROM POSICIO WHERE num_est=" + num_est + " AND num_pos=" + num_pos);
				if (rs3.next()) {
					System.out.println("\t" + num_usu + " -> " + rs3.getInt(1) + " - " + rs3.getInt(2) + " - " + rs3.getInt(4));
					if (rs3.getInt(4) != 0) {
						// sí que hi ha bici
						Statement st4 = con.createStatement();
						st4.executeUpdate("UPDATE POSICIO " + "SET bici=NULL, data_p=NULL " + "WHERE num_est=" + num_est
								+ " AND num_pos=" + num_pos);
						st4.executeUpdate("UPDATE BICICLETA " + "SET usuari_pr=" + num_usu + ", data_pr='" + data_pr
								+ "' " + "WHERE num_b=" + rs3.getInt(4));

						aux = true;
					} else
						System.out.println("\tNo hi ha bici en la posició");
				} else
					System.out.println("\tNo existeix la posicio");
			}
		} else
			System.out.println("\tNo existeix l'usuari " + num_usu);
		return aux;
	}

}
