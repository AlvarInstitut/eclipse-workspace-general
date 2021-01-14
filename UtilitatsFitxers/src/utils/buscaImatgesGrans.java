package utils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class buscaImatgesGrans {
	static int comptador=0;

	public static void main(String[] args) throws IOException {
		System.out.println("Introdueix el directori on vols buscar");
		String nomDirOr = (new BufferedReader(new InputStreamReader(System.in)).readLine());
		File dirOr = new File(nomDirOr);
		
		System.out.println("Introdueix el directori on vols copiar");
		String nomDirDest = (new BufferedReader(new InputStreamReader(System.in)).readLine());
		File dirDest = new File(nomDirDest);
		
		buscaImatges(dirOr,dirDest);
		System.out.println(comptador + " imatges copiades");

	}

	private static void buscaImatges(File dirOr, File dirDest) throws IOException {
		for(File f : dirOr.listFiles()) {
			if (f.isDirectory())
				buscaImatges(f,dirDest);
			if (f.isFile()) {
				if (f.getName().contains(".jpg") || f.getName().contains(".png")) {
					System.out.println(f.getName() + " (" + f.length() + ")");
					if (f.length() > 50000) {
						System.out.print("\t ---> Sí");
						copiaFitxer(f,dirDest);
						comptador++;
						System.out.println(" (num. " + comptador + ")");
					}
				}
				
			}
		}
		System.out.println("Directori " + dirOr.getName() + " completat");
		
	}

	private static void copiaFitxer(File f, File dirDest) throws IOException {
		FileInputStream iOr = new FileInputStream(f);
		File fDest = new File(dirDest, f.getName());
		FileOutputStream iDest = new FileOutputStream(fDest);
		byte[] buffer = new byte[1024];
		int n = iOr.read(buffer);
		while (n != -1) {
			iDest.write(buffer, 0, n);
			n = iOr.read(buffer);
		}
		iDest.close();
		iOr.close();
		
	}

}
