package AltaMecanitzada;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class AltaMcanitzadaBD {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader f1 = new BufferedReader(new FileReader("AltaAlumnes.csv"));
		PrintWriter f2 = new  PrintWriter("FiterAlumnesBD.txt");
		
		String cad = f1.readLine();
		while(cad != null) {
			String nom= cad.substring(0, cad.indexOf(","));
			String dni=cad.substring(cad.indexOf(",")+1);
			f2.println("CREATE USER FACTURA_" + dni + " WITH PASSWORD 'FACTURA_" + dni + "';") ;
			f2.println("CREATE DATABASE FACTURA_" + dni + " WITH OWNER FACTURA_" + dni + ";");
			f2.println("COMMENT ON DATABASE FACTURA_"+dni+" IS '"+nom+"';");
			f2.println("");
			cad=f1.readLine();
		}

		f2.close();
		f1.close();
	}

}
