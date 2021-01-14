package utils;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class comparaDirectoris {

	public static void main(String[] args) throws IOException {
		System.out.println("Introdueix el primer directori: ");
		String s_dirOrigen = new BufferedReader(new InputStreamReader(System.in)).readLine();
		System.out.println("Introdueix el segon directori: ");
		String s_dirDesti = new BufferedReader(new InputStreamReader(System.in)).readLine();
		File dirOrigen = new File(s_dirOrigen);
		File dirDesti = new File(s_dirDesti);
		if (dirOrigen.exists() && dirDesti.exists()) {
			System.out.println("Van a comparar-se: ");
			System.out.println(dirOrigen.getCanonicalPath());
			System.out.println(dirDesti.getCanonicalPath());
			System.out.println("i s'esborraran els fitxers coincidents en el segon directori");
			System.out.println("Estàs d'acord? (S/n)");
			String conf = new BufferedReader(new InputStreamReader(System.in)).readLine();
			if (conf.equals("S") || conf.equals("s") || conf.equals("")) {
				compDir(dirOrigen, dirDesti);
				netejaDesti(dirDesti);
			} else
				System.out.println("Finalitzem");
		} else
			System.out.println("No existeix un dels dos. Finalitzem");
	}


	private static void compDir(File dirOrigen, File dirDesti) throws IOException {
		// TODO Auto-generated method stub
		// System.out.println("Comencem");
		for (File f : dirOrigen.listFiles()) {
			File fD = new File(dirDesti, f.getName());
			if (fD.exists()) {
				if (f.isDirectory())
					compDir(f, fD);
				else {
					System.out.print(f.getCanonicalPath());
					if ((f.length() == fD.length()) && (f.lastModified() == fD.lastModified())) {
						System.out.println("   --->  són iguals");
						fD.delete();
					} else
						System.out.println();
				}

			}
		}
	}
	
	private static void netejaDesti(File dirDesti) {
		// TODO Auto-generated method stub
		for (File f: dirDesti.listFiles()) {
			if (f.isDirectory())
				netejaDesti(f);
		}
		if (dirDesti.listFiles().length == 0)
			dirDesti.delete();
		
	}

}