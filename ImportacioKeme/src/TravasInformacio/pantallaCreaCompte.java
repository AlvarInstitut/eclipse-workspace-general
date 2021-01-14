package TravasInformacio;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class pantallaCreaCompte extends JDialog implements ActionListener {

	JTextField num = new JTextField(25);
	JTextField desc = new JTextField(25);
	JTextField aux = new JTextField(25);
	JButton acceptar = new JButton("Acceptar");
	JButton cancelar = new JButton("Cancel·lar");

	public pantallaCreaCompte(Pantalla f) {
		super(f, "Diàleg", true);
		this.setSize(300, 300);
		setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.insets = new Insets(5, 15, 5, 1);
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.BOTH;
		this.add(new JLabel("Número de compte: "), gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(5, 15, 4, 1);
		this.add(num, gbc); 

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(1, 15, 4, 1);
		this.add(new JLabel("Descripció: "),gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(5, 15, 10, 1);
		this.add(desc, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(1, 15, 4, 1);
		this.add(new JLabel("Auxiliar: "),gbc);

		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(5, 15, 10, 1);
		this.add(aux, gbc);

		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.fill = GridBagConstraints.EAST;
		gbc.insets = new Insets(1, 15, 4, 1);
		this.add(acceptar,gbc);

		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.fill = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 15, 10, 1);
		this.add(cancelar, gbc);
		
		/*JPanel panell = new JPanel(new FlowLayout());
		panell.add(new JLabel("Número de compte: "));
		panell.add(num);
		panell.add(new JLabel("Descripció: "));
		panell.add(desc);
		panell.add(new JLabel("Auxiliar: "));
		panell.add(aux);

		panell.add(acceptar);
		panell.add(cancelar);*/

		acceptar.addActionListener(this);
		cancelar.addActionListener(this);

		//this.getContentPane().add(panell);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == acceptar) {
			try {
				int numero = Integer.parseInt(num.getText());
				if (desc.getText().equals(""))
					JOptionPane.showMessageDialog(this, "La descripció no pot estar en blanc", "error",
							JOptionPane.ERROR_MESSAGE);
				else {
					// Tot correcte: a introduir
					String URLSQLite = "jdbc:sqlite:/home/alvar/keme/amcastalia.sqlite";
					Connection conSQLite = DriverManager.getConnection(URLSQLite);
					PreparedStatement stSQLite = conSQLite.prepareStatement("INSERT INTO plancontable VALUES(?,?,0)");
					stSQLite.setInt(1, numero);
					stSQLite.setString(2, desc.getText());
					stSQLite.executeUpdate();
					System.out.println("S'ha creat el compte " + numero);
					stSQLite.close();
					conSQLite.close();
					this.setVisible(false);
					this.dispose();
				}
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "El númeo de compte no pot estar en blanc i ha de ser un número",
						"error", JOptionPane.ERROR_MESSAGE);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		if (e.getSource() == cancelar) {
			this.setVisible(false);
			this.dispose();
		}
	}
}
