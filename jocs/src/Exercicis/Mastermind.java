package Exercicis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Mastermind {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		char[] n = new char[4];
		n[0] = Integer.toString(((int) (Math.random() * 10))).charAt(0);
		n[1] = Integer.toString(((int) (Math.random() * 10))).charAt(0);
		while (n[1] == n[0])
			n[1] = Integer.toString(((int) (Math.random() * 10))).charAt(0);
		n[2] = Integer.toString(((int) (Math.random() * 10))).charAt(0);
		while (n[2] == n[0] || n[2] == n[1])
			n[2] = Integer.toString(((int) (Math.random() * 10))).charAt(0);
		n[3] = Integer.toString(((int) (Math.random() * 10))).charAt(0);
		while (n[3] == n[0] || n[3] == n[1] || n[3] == n[2])
			n[3] = Integer.toString(((int) (Math.random() * 10))).charAt(0);
		//System.out.println(n[0] + " " + n[1] + " " + n[2] + " " + n[3]);

		BufferedReader teclat = new BufferedReader(new InputStreamReader(System.in));
		String num = teclat.readLine();
		while (num.charAt(0) != n[0] || num.charAt(1) != n[1] || num.charAt(2) != n[2] || num.charAt(3) != n[3]) {
			int pos =0;
			int nopos=0;
			for (int i=0;i<4;i++)
				for (int j=0; j<4; j++)
					if (num.charAt(i)== n[j])
						if (i==j) pos++;
						else nopos++;
			for (int i=0;i<4;i++)
				System.out.print(num.charAt(i) + " " );
			System.out.println(" ---> " + pos + " " + nopos);

			num = teclat.readLine();
		}
		System.out.println("Trobat");

	}

}
