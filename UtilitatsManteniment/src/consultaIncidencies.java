import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.MessagingException;

public class consultaIncidencies {

	public static void main(String[] args) {

		Connection con;
		boolean autocommit;
		String incid="";
		System.out.println("-------------------------------------------------------------------");
		SimpleDateFormat sDF = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		System.out.println(sDF.format(new Date()));

		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventari?useSSL=false", "ocs", "ocs");
			System.out.println("Connexio feta a MySQL");
			// Muntem una transacció. Així es marcarà com a notificat únicament si ha anat bé l'enviament del correu
			autocommit = con.getAutoCommit();
			con.setAutoCommit(false);
			
			Statement st1 = con.createStatement();
			ResultSet rs1 = st1.executeQuery("SELECT * FROM INCIDENCIES WHERE NOTIFICAT<>1 ORDER BY EQUIP,DATA_HORA");

			PreparedStatement st2 = con.prepareStatement("UPDATE INCIDENCIES SET NOTIFICAT=1 WHERE ID = ?");
			while(rs1.next()) {
				// Mostem la incidència amb el format EQUIP: comentari incidència (data-hora)
				incid += rs1.getString(2) + " (Núm. " + rs1.getInt(1) + ", Data-hora: " + rs1.getString(3) + 
						"): " + rs1.getString(5) + " (" + rs1.getString(4) + ")\n";
				System.out.println("Incidencia " + rs1.getInt(1));
				// I ara marquem la incidència com a notificada
				st2.setInt(1, rs1.getInt(1));
				st2.executeUpdate();
			}
			System.out.println("Tot el de MySQL ha anat be");
			if (incid.length()>0) {
				EmailUtil.enviaCorreu("INCIDÈNCIES MANTENIMENT", incid);
				//si ha anat bé el correu, confirmem les actualitzacions
				System.out.println("Commit");
				con.commit();
			}
			else
				System.out.println("No hi ha incidencies");
			// Tornem a l'estat anterior de transacció


			con.setAutoCommit(autocommit);
			st2.close();
			st1.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Problemes amb la connexio a MySQL");
			System.out.println(e.getMessage());
			//e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			System.out.println("Problemes amb el correu");
			System.out.println(e.getMessage());
			//e.printStackTrace();
		}
				
	}

}