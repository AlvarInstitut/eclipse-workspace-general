import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class arreplegaMacs {

	public static void main(String[] args) throws IOException {
		PrintWriter fFinal = new PrintWriter("dhcp.conf.final");
		BufferedReader fCap = new BufferedReader(new FileReader("dhcpd.conf.cap.txt"));

		String linia = fCap.readLine();
		while (linia != null) {
			fFinal.println(linia);
			linia = fCap.readLine();
		}
		fCap.close();

		for (int t = 1; t <= 6; t++) {

			File tMacs = new File("TI" + t + "_macs.txt");
			if (tMacs.exists()) {

				BufferedReader fCapT = new BufferedReader(new FileReader("dhcpd.conf.capT"+t+".txt"));

				linia = fCapT.readLine();
				while (linia != null) {
					fFinal.println(linia);
					linia = fCapT.readLine();
				}
				fCapT.close();

				BufferedReader fAdrT = new BufferedReader(new FileReader(tMacs));

				linia = fAdrT.readLine();
				int i = 1;
				while (linia != null) {
					String equip = "TI" + t + "-" + String.format("%02d", i);
					fFinal.println("\thost " + equip + "{");
					fFinal.println("\t\thardware ethernet " + linia + ";");
					fFinal.println("\t\tfixed address 192.168.20"+t+".1" + String.format("%02d", i) + ";");
					fFinal.println("\t}");
					fFinal.println();

					i++;
					linia = fAdrT.readLine();
				}
				fFinal.println("}");
				fFinal.println();
			}
		}

		BufferedReader fCapF = new BufferedReader(new FileReader("dhcpd.conf.capFinal.txt"));

		linia = fCapF.readLine();
		while (linia != null) {
			fFinal.println(linia);
			linia = fCapF.readLine();
		}
		fCapF.close();

		
		fFinal.close();

	}

}
