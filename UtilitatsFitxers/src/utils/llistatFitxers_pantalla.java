package utils;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class llistatFitxers_pantalla extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	JLabel et_a1 = new JLabel("Contingut directori 1:");
	JTextArea area1 = new JTextArea(20, 25);
	JScrollPane scrollPane = new JScrollPane(area1);

	JLabel et_a2 = new JLabel("Contingut directori 2:");
	JTextArea area2 = new JTextArea(20, 25);
	JScrollPane scrollPane2 = new JScrollPane(area2);

	JLabel et_a3 = new JLabel("Combinació:");
	JTextArea area3 = new JTextArea(20, 25);
	JScrollPane scrollPane3 = new JScrollPane(area3);

	JButton genera = new JButton("Genera Fitxer A");
	JButton a_b = new JButton("A - B");
	JButton b_a = new JButton("B - A");
	JButton a_int_b = new JButton("A INT B");

	JButton esborrar = new JButton("esborrar");

	ArrayList<String> llistaFitxers1;
	ArrayList<String> llistaFitxers2;
	ArrayList<String> llistaFitxers3;
	
	JFileChooser fc;

	// en iniciar posem un contenidor per als elements anteriors
	public void iniciar() throws IOException {
		getContentPane().setLayout(new BorderLayout());
		setTitle("Tractament directoris d'imatges");

		JPanel panell2 = new JPanel(new FlowLayout());
		panell2.add(scrollPane);
		area1.setEditable(false);

		panell2.add(scrollPane3);
		area3.setEditable(false);

		panell2.add(scrollPane2);
		area2.setEditable(false);

		getContentPane().add(panell2, BorderLayout.CENTER);

		JPanel panell3 = new JPanel(new FlowLayout());
		getContentPane().add(panell3, BorderLayout.SOUTH);
		panell3.add(genera);
		panell3.add(a_b);
		panell3.add(b_a);
		panell3.add(a_int_b);
		panell3.add(esborrar);

		genera.addActionListener(this);
		a_b.addActionListener(this);
		b_a.addActionListener(this);
		a_int_b.addActionListener(this);
		esborrar.addActionListener(this);

		setVisible(true);
		pack();

		fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setDialogTitle("Tria del directori destí");
		int r = fc.showOpenDialog(this);
		if (r == JFileChooser.APPROVE_OPTION) {
			System.out.println("Fitxer seleccionat: " + fc.getSelectedFile().getName());
		} else
			System.out.println("No s'ha seleccionat res");

		llistaFitxers1 = new ArrayList<String>(Arrays.asList(fc.getSelectedFile().list()));
		llistaFitxers1.sort(null);
		for (String f : llistaFitxers1) {
			area1.append(f + "\n");
		}
		area1.append("\nTotal fitxers: " + llistaFitxers1.size());

		MyIcon icon = new MyIcon();
		String[] options = { "Directori", "Fitxer" };
		int seleccio = JOptionPane.showOptionDialog(null, "Quin és l'origen del directori amb el qual comparar?",
				"Directori 1", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, icon, options, options[0]);
		if (seleccio == 0)
			System.out.println("Directori");
		else
			System.out.println("Fitxer");

		JFileChooser fc2 = new JFileChooser();
		if (seleccio == 0)
			fc2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		else
			fc2.setFileSelectionMode(JFileChooser.FILES_ONLY);

		int s = fc2.showOpenDialog(this);
		if (s == JFileChooser.APPROVE_OPTION) {
			System.out.println("Fitxer seleccionat: " + fc2.getSelectedFile().getName());
		} else
			System.out.println("No s'ha seleccionat res");

		if (seleccio == 0) {
			llistaFitxers2 = new ArrayList<String>(Arrays.asList(fc2.getSelectedFile().list()));
			llistaFitxers2.sort(null);
			for (String f : llistaFitxers2) {
				area2.append(f + "\n");
			}
			area2.append("\nTotal fitxers: " + llistaFitxers2.size());

		} else {
			llistaFitxers2 = new ArrayList<String>();
			BufferedReader f_ent = new BufferedReader(new FileReader(fc2.getSelectedFile()));
			String linia = f_ent.readLine();
			while (linia != null) {
				area2.append(linia + "\n");
				llistaFitxers2.add(linia);
				linia = f_ent.readLine();
			}
			area2.append("\nTotal fitxers: " + llistaFitxers2.size());
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == genera) {
			JFileChooser fc3 = new JFileChooser();
			fc3.setFileSelectionMode(JFileChooser.FILES_ONLY);

			int s = fc3.showOpenDialog(this);
			if (s == JFileChooser.APPROVE_OPTION) {
				System.out.println("Fitxer seleccionat: " + fc3.getSelectedFile().getName());
				PrintWriter f_eix;
				try {
					f_eix = new PrintWriter(fc3.getSelectedFile());
					for (String f : llistaFitxers1)
						f_eix.println(f);
					f_eix.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else
				System.out.println("No s'ha seleccionat res");

		}

		if (e.getSource() == a_b) {
			System.out.println("A - B");
			area3.setText("");
			llistaFitxers3 = new ArrayList<String>();
			for (String f : llistaFitxers1) {
				if (!llistaFitxers2.contains(f)) {
					llistaFitxers3.add(f);
					area3.append(f + "\n");
				}
			}
			area3.append("\nTotal fitxers: " + llistaFitxers3.size());
		}

		if (e.getSource() == b_a) {
			System.out.println("A - B");
			area3.setText("");
			llistaFitxers3 = new ArrayList<String>();

			for (String f : llistaFitxers2) {
				if (!llistaFitxers1.contains(f)) {
					llistaFitxers3.add(f);
					area3.append(f + "\n");
				}
			}
			area3.append("\nTotal fitxers: " + llistaFitxers3.size());
		}

		if (e.getSource() == a_int_b) {
			System.out.println("A INT B");
			area3.setText("");
			llistaFitxers3 = new ArrayList<String>();

			for (String f : llistaFitxers1) {
				if (llistaFitxers2.contains(f)) {
					llistaFitxers3.add(f);
					area3.append(f + "\n");
				}
			}
			area3.append("\nTotal fitxers: " + llistaFitxers3.size());
		}

		if (e.getSource() == esborrar) {
			for (String f : llistaFitxers3) {
				System.out.println("esborrar" + f);
				File f1 = new File(fc.getSelectedFile(),f);
				f1.delete();
			}
		}
	}

}