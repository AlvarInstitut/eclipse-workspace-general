package TravasInformacio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Amortitzacio {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		String URLSQLite = "jdbc:sqlite:/home/alvar/keme/amcastalia.sqlite";
		Connection conSQLite = DriverManager.getConnection(URLSQLite);
		Statement stSQLite = conSQLite.createStatement();

		String filename = "/home/alvar/Dropbox/Privat/AMCastàlia/Socis/AMCASTALIA_2019_04_01_NovaVersió.accdb";
		String url = "jdbc:ucanaccess://" + filename;
		Connection conAccess = DriverManager.getConnection(url);
		Statement st0 = conAccess.createStatement();
		ResultSet rs0 = st0.executeQuery(
				"SELECT NUM_INVENT, data_compra, 2018-year(DATA_COMPRA),DESCRIPCIO,PREU FROM INVENTARI WHERE NUM_INVENT NOT IN (53,57,60) ");

		while (rs0.next()) {
			String numInvent = String.format("%04d", rs0.getInt(1));
			String sqlPlan = "INSERT INTO plancontable VALUES ('28100" + numInvent + "','AMORTITZACIÓ ACUMULADA "
					+ numInvent + " " + rs0.getString(4) + "',0)";
			System.out.println(sqlPlan);
			stSQLite.executeUpdate(sqlPlan);
			sqlPlan = "INSERT INTO plancontable VALUES ('68100" + numInvent + "','AMORTITZACIÓ " + numInvent + " "
					+ rs0.getString(4) + "',0)";
			System.out.println(sqlPlan);
			stSQLite.executeUpdate(sqlPlan);
			String sql1 = "INSERT INTO planamortizaciones VALUES";
			int anysPortem = rs0.getInt(3)-1;
			if (anysPortem<0) anysPortem=0;
			int anysFalten = 10 -anysPortem;
			System.out.println(anysPortem + " --> " + anysFalten);
			String sql2 = "('21900" + numInvent + "','" + rs0.getDate(2) + "','" + numInvent + "','28100"
						+ numInvent + "','68100" + numInvent + "',0,0," + (1.0/anysFalten) + ",0,0,0,'2000-01-01',null,null,null)";
			System.out.println(sql1+sql2);
			stSQLite.executeUpdate(sql1+sql2);
/* Calcul pla personalitzat. No valdrà la pena perquè el preu inicial dels instruments ja estan desvaloritzats
				String sql2 = "('21900" + numInvent + "','" + rs0.getDate(2) + "','" + numInvent + "','28100"
						+ numInvent + "','68100" + numInvent + "',0,1,0,0,0,0,'2000-01-01',null,null,null)";
				System.out.println(sql1+sql2);
				// stSQLite.executeUpdate(sql1+sql2);
				int any1=2018;
				float preuCompra = rs0.getFloat(5);
				float amort= (float) (0.1*(rs0.getInt(3)+1));
				System.out.println(preuCompra + " ---> " + amort);
				String sql3 = "INSERT INTO amortpers VALUES('21900"+ numInvent +"','" + any1 + "'," + ((float)amort*preuCompra) +")";
				System.out.println(sql3);
				 //stSQLite.executeUpdate(sql3); 
				while (amort < 1) { 
					amort +=0.1;
					any1++;
					sql3 = "INSERT INTO amortpers VALUES('21900"+ numInvent +"','"+ any1 + "'," + preuCompra*0.1 + ")";
					System.out.println(sql3);
					//stSQLite.executeUpdate(sql3);
				}
				
				 
			}
			System.out.println();
			*/
		}
		stSQLite.close();
		conSQLite.close();
		rs0.close();
		st0.close();
		conAccess.close();
	}

}
