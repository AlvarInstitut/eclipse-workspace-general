package utils;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfSearchElement;
import com.gnostice.pdfone.PdfSearchMode;
import com.gnostice.pdfone.PdfSearchOptions;

public class buscaPdfRenda {

	public static void main(String[] args) throws IOException, PdfException {
		System.out.println("Introdueix el directori: ");
		String s_dirOrigen = new BufferedReader(new InputStreamReader(System.in)).readLine();
		File dirOrigen = new File(s_dirOrigen);
		//System.out.println("Introdueix la paraula a buscar: ");
		//String text = new BufferedReader(new InputStreamReader(System.in)).readLine();
		String text ="ren[d|t]a de l";
		if (dirOrigen.exists()) {
			System.out.println("Van a buscar-se els pdf del directori: ");
			System.out.println(dirOrigen.getCanonicalPath());
			busca(dirOrigen, text);
		} else
			System.out.println("No existeix el directori. Finalitzem");
	}

	private static void busca(File dirOrigen, String text) throws IOException, PdfException {
		// TODO Auto-generated method stub
		// System.out.println("Comencem");
		for (File f : dirOrigen.listFiles()) {
			if (f.isDirectory())
				busca(f, text);
			else {
				if (f.getName().endsWith(".pdf")) {
					//System.out.print(f.getCanonicalPath());
					/*if (f.getName().contains(text)) {
						System.out.println();
						System.out.println("Ieeeeppp!!!!");
						System.exit(0);
					}
					else {
						for (int i=0;i<f.getCanonicalPath().length();i++)
							System.out.print('\b');
					}*/
						
					if (buscaTextPdf(f, text)) {
						System.out.print("TROBAT!!!!  --->  ");
						System.out.println(f.getCanonicalPath());
						//System.exit(0);
					}
				}
			}

		}
	}

	private static boolean buscaTextPdf(File f, String text) throws IOException {
		int i, n;
		PdfSearchElement pseResult;
		ArrayList lstSearchResults1;
		
		// Load a PDF document
		PdfDocument doc = new PdfDocument();
		//System.out.println("Fitxer: " + f.getCanonicalPath());
		try {
			doc.load(f.getCanonicalPath());
			// Obtain all instances of the word "alcohol" in page 4
			lstSearchResults1 = (ArrayList) doc.search(text, 2, PdfSearchMode.REGEX, PdfSearchOptions.NONE);
			// Close the document
			doc.close();
			//System.out.println(lstSearchResults1.size() + " trobades");
			if (lstSearchResults1.size() > 0)
				return true;
			else
				return false;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Error pdf: " + f.getCanonicalPath());
			return false;
		}


		// Iterate through all search results

	}

}
