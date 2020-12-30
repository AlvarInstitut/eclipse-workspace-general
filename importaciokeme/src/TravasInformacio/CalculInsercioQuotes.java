package TravasInformacio;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.table.TableCellRenderer;

public class CalculInsercioQuotes {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		PantallaQuotes finestra = new PantallaQuotes();
		finestra.iniciar();
	}

}

class PantallaQuotes extends JFrame implements ActionListener {
	String URLSQLite = "jdbc:sqlite:/home/alvar/keme/amcastalia.sqlite";
	Connection conSQLite = null;

	JTable movs = new JTable(1, 1);
	JScrollPane scrollPaneMovs = new JScrollPane(movs);

	JCheckBox checkMUSICScols = new JCheckBox("MUSICS / col·laboradors", true);
	JTextField exercici = new JTextField(10);
	JButton anar = new JButton("anar");
	String[] trim = {"   ","Q1T","Q2T","Q3T","Q4T"};
	JComboBox filtre = new JComboBox(trim);
	JButton aplicarFiltre = new JButton("Aplicar");

	JTable movsSel = new JTable(1, 1);
	JScrollPane scrollPaneMovsSel = new JScrollPane(movsSel);

	JList llistaComptesDebit = new JList();
	DefaultListModel modelDebit = new DefaultListModel();
	JScrollPane scrollLlistaDebit = new JScrollPane(llistaComptesDebit);
	JList llistaComptesCredit = new JList();
	DefaultListModel modelCredit = new DefaultListModel();
	JScrollPane scrollLlistaCredit = new JScrollPane(llistaComptesCredit);

	JTextField textDebit = new JTextField(15);
	JButton buscarDebit = new JButton("Busca");
	JTextField textCredit = new JTextField(15);
	JButton buscarCredit = new JButton("Busca");

	JButton creaCompte = new JButton("Crear Compte");
	JLabel etDataMovs = new JLabel("Quina data posem als moviments?");
	JTextField dataMovs = new JTextField(10);


	JTable assent = new JTable(5, 8);
	JScrollPane scrollPaneAssent = new JScrollPane(assent);

	String debit = null;
	String credit = null;

	JButton executar = new JButton("Executar");
	JButton executarTots = new JButton("Executar TOTS");

	Set filesFetes = new HashSet();

	void iniciar() throws SQLException {
		conSQLite = DriverManager.getConnection(URLSQLite);

		this.getContentPane().setLayout(new BorderLayout());
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel pan = new JPanel(new GridLayout(1, 2));
		JPanel pan1 = new JPanel(new BorderLayout());
		JPanel pan11 = new JPanel(new FlowLayout());
		pan11.add(checkMUSICScols);
		pan11.add(new JLabel("Exercici"));
		pan11.add(exercici);
		pan11.add(anar);
		pan1.add(pan11, BorderLayout.NORTH);
		pan1.add(scrollPaneMovs, BorderLayout.CENTER);
		pan.add(pan1);

		JPanel pan13 = new JPanel(new FlowLayout());
		pan13.add(new JLabel("Filtre "));
		pan13.add(filtre);
		pan13.add(aplicarFiltre);
		pan1.add(pan13, BorderLayout.SOUTH);

		JPanel pan2 = new JPanel(new GridLayout(3, 1));
		pan.add(pan2);
		JPanel pan21 = new JPanel(new FlowLayout());

		pan21.add(scrollPaneMovsSel);
		pan2.add(scrollPaneMovsSel);

		JPanel pan22 = new JPanel(new FlowLayout());
		pan2.add(pan22);

		llistaComptesDebit.setFixedCellWidth(303);
		llistaComptesDebit.setFont(new Font("Courier New", Font.BOLD, 12));
		pan22.add(scrollLlistaDebit);
		llistaComptesCredit.setFixedCellWidth(303);
		llistaComptesCredit.setFont(new Font("Courier New", Font.BOLD, 12));
		pan22.add(scrollLlistaCredit);

		pan22.add(textDebit);
		pan22.add(buscarDebit);
		pan22.add(textCredit);
		pan22.add(buscarCredit);

		pan22.add(creaCompte);
		pan22.add(etDataMovs);
		pan22.add(dataMovs);

		JPanel pan23 = new JPanel(new GridLayout(2, 1));
		pan2.add(pan23);
		pan23.add(scrollPaneAssent);

		JPanel pan231 = new JPanel(new FlowLayout());
		pan23.add(pan231);
		pan231.add(executar);
		pan231.add(executarTots);

		omplirComptes();

		setVisible(true);
		setContentPane(pan);

		anar.addActionListener(this);
		aplicarFiltre.addActionListener(this);

		buscarDebit.addActionListener(this);
		buscarCredit.addActionListener(this);

		creaCompte.addActionListener(this);

		llistaComptesDebit.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				try {
					if (!llistaComptesDebit.isSelectionEmpty()) {
						debit = llistaComptesDebit.getSelectedValue().toString().substring(0, 9);
						canviJList();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		llistaComptesCredit.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				try {
					if (!llistaComptesCredit.isSelectionEmpty()) {
						credit = llistaComptesCredit.getSelectedValue().toString().substring(0, 9);
						canviJList();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		movs.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {

				// ací hauria de controlar si el fons de la fila seleccionada és roig, i que no
				// es pug a seleccionar
				int[] filesSel = movs.getSelectedRows();
				for (int f : filesSel) {
					if (filesFetes.contains(f)) {
						movs.removeRowSelectionInterval(f, f);
						System.out.println("\t Llevem la selecció de " + f);
					}

				}

				try {
					if (movs.getSelectedRowCount() > 0)
						mostrarMovSel();
					else {
						DefaultTableModel dm = (DefaultTableModel) movsSel.getModel();
						dm.getDataVector().removeAllElements();
						dm.fireTableDataChanged();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		movs.setRowSelectionAllowed(true);
		movs.setColumnSelectionAllowed(false);

		movsSel.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				// do some actions here, for example
				// print first column value from selected row
				try {
					if (movsSel.getSelectedRow() != -1)
						triatMovSel();
					else {
						llistaComptesDebit.clearSelection();
						llistaComptesCredit.clearSelection();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		executar.addActionListener(this);
		executarTots.addActionListener(this);

	}

	private void agafarSocis(String exerc) throws SQLException {
		// TODO Auto-generated method stub
		movs.removeAll();
		filesFetes.clear();
		movs.setBackground(Color.white);
		String filename = "/home/alvar/Dropbox/Privat/AMCastàlia/Socis/AMCASTALIA_2019_04_01_NovaVersió.accdb";
		String url = "jdbc:ucanaccess://" + filename;
		Connection con = DriverManager.getConnection(url);
		Statement st0 = con.createStatement();
		PreparedStatement st = null;

		ResultSet rs0;
		if (checkMUSICScols.isSelected())
			rs0 = st0.executeQuery("SELECT COUNT(*) FROM MUSICOS WHERE ALTA=TRUE AND TIPUS=1");
		else
			rs0 = st0.executeQuery("SELECT COUNT(*) FROM MUSICOS WHERE ALTA=TRUE AND TIPUS=2");
		
		rs0.next();
		int numFiles = rs0.getInt(1);
		
		ResultSet rs;
		
		if (checkMUSICScols.isSelected()) {
			String quinAny = exercici.getText();
			/*st= con.prepareStatement(
				"SELECT COD_SOCIO, APELLIDOS_MUSICO, NOMBRE_MUSICO, [ALTA DESDE], NIVEL_ESTUDIOS, "
						+ "CDate(\"1-\" & IIf(Day([ALTA DESDE])>15,Month([ALTA DESDE])+1,Month([ALTA DESDE])) & \"-\" & '" + quinAny + "') AS [Següent compliment], "
						+ "Int(DateDiff(\"m\",[ALTA DESDE],CDate(\"1-1-\" & '" + quinAny + "'))/12) AS A1M, CalculReduccio([ALTA DESDE],CDate(\"1-1-\" & '" + quinAny + "'),"
						+ "[NIVEL_ESTUDIOS]) AS RQ1M, 10*(1-[RQ1M]/100) AS Q1M, Int(DateDiff(\"m\",[ALTA DESDE],CDate(\"1-2-\" & '" + quinAny + "'))/12) AS A2M, "
						+ "CalculReduccio([ALTA DESDE],CDate(\"1-2-\" & '" + quinAny + "'),[NIVEL_ESTUDIOS]) AS RQ2M, 10*(1-[RQ2M]/100) AS Q2M, "
						+ "Int(DateDiff(\"m\",[ALTA DESDE],CDate(\"1-3-\" & '" + quinAny + "'))/12) AS A3M, CalculReduccio([ALTA DESDE],CDate(\"1-3-\" & '" + quinAny + "'),[NIVEL_ESTUDIOS]) AS RQ3M,"
						+ " 10*(1-[RQ3M]/100) AS Q3M, (Q1M+Q2M+Q3M) AS Q1T, Int(DateDiff(\"m\",[ALTA DESDE],CDate(\"1-4-\" & '" + quinAny + "'))/12) AS A4M, CalculReduccio([ALTA DESDE],CDate(\"1-4-\" & '" + quinAny + "'),[NIVEL_ESTUDIOS]) AS RQ4M, "
						+ "10*(1-[RQ4M]/100) AS Q4M, Int(DateDiff(\"m\",[ALTA DESDE],CDate(\"1-5-\" & '" + quinAny + "'))/12) AS A5M, CalculReduccio([ALTA DESDE],CDate(\"1-5-\" & '" + quinAny + "'),[NIVEL_ESTUDIOS]) AS RQ5M,"
						+ " 10*(1-[RQ5M]/100) AS Q5M, Int(DateDiff(\"m\",[ALTA DESDE],CDate(\"1-6-\" & '" + quinAny + "'))/12) AS A6M, CalculReduccio([ALTA DESDE],CDate(\"1-6-\" & '" + quinAny + "'),[NIVEL_ESTUDIOS]) AS RQ6M, 10*(1-[RQ6M]/100) AS Q6M, "
						+ "(Q4M+Q5M+Q6M) AS Q2T, Int(DateDiff(\"m\",[ALTA DESDE],CDate(\"1-7-\" & '" + quinAny + "'))/12) AS A7M, CalculReduccio([ALTA DESDE],CDate(\"1-7-\" & '" + quinAny + "'),[NIVEL_ESTUDIOS]) AS RQ7M, 10*(1-[RQ7M]/100) AS Q7M, "
						+ "Int(DateDiff(\"m\",[ALTA DESDE],CDate(\"1-8-\" & '" + quinAny + "'))/12) AS A8M, CalculReduccio([ALTA DESDE],CDate(\"1-8-\" & '" + quinAny + "'),[NIVEL_ESTUDIOS]) AS RQ8M, 10*(1-[RQ8M]/100) AS Q8M, "
						+ "Int(DateDiff(\"m\",[ALTA DESDE],CDate(\"1-9-\" & '" + quinAny + "'))/12) AS A9M, CalculReduccio([ALTA DESDE],CDate(\"1-9-\" & '" + quinAny + "'),[NIVEL_ESTUDIOS]) AS RQ9M, 10*(1-[RQ9M]/100) AS Q9M, "
						+ "(Q7M+Q8M+Q9M) AS Q3T, Int(DateDiff(\"m\",[ALTA DESDE],CDate(\"1-10-\" & '" + quinAny + "'))/12) AS A10M, CalculReduccio([ALTA DESDE],CDate(\"1-10-\" & '" + quinAny + "'),[NIVEL_ESTUDIOS]) AS RQ10M, 10*(1-[RQ10M]/100) AS Q10M, "
						+ "Int(DateDiff(\"m\",[ALTA DESDE],CDate(\"1-11-\" & '" + quinAny + "'))/12) AS A11M, CalculReduccio([ALTA DESDE],CDate(\"1-11-\" & '" + quinAny + "'),[NIVEL_ESTUDIOS]) AS RQ11M, 10*(1-[RQ11M]/100) AS Q11M, "
						+ "Int(DateDiff(\"m\",[ALTA DESDE],CDate(\"1-12-\" & '" + quinAny + "'))/12) AS A12M, CalculReduccio([ALTA DESDE],CDate(\"1-12-\" & '" + quinAny + "'),[NIVEL_ESTUDIOS]) AS RQ12M, 10*(1-[RQ12M]/100) AS Q12M, (Q10M+Q11M+Q12M) AS Q4T, "
						+ "MUSICOS.ALTA, MUSICOS.TIPUS\n" + 
				"FROM MUSICOS " + 
				"WHERE (((MUSICOS.ALTA)=Yes) AND ((MUSICOS.TIPUS)=1)) " + 
				"ORDER BY MUSICOS.APELLIDOS_MUSICO");
			*/
			// De moment ha quedat molt pobre, agafant d'una taula creada previament quotes2020. S'haurà de canviar per a l'any que ve, i segurament per al següent semestre
			st= con.prepareStatement(
					"SELECT COD_SOCIO, APELLIDOS_MUSICO, NOMBRE_MUSICO, [ALTA DESDE], NIVEL_ESTUDIOS, ROUND(Q1T,2) AS Q1T, ROUND(Q2T,2) AS Q2T, ROUND(Q3T,2) AS Q3T, ROUND(Q4T,2) AS Q4T "
					+ "FROM quotes2020");	
			rs=st.executeQuery();
		}
		else{
			st= con.prepareStatement(
					"SELECT MUSICOS.COD_SOCIO, MUSICOS.APELLIDOS_MUSICO, MUSICOS.NOMBRE_MUSICO, MUSICOS.[ALTA DESDE], NIVEL_ESTUDIOS, 30 AS QANUAL "
							+ "FROM MUSICOS WHERE ALTA=TRUE AND TIPUS=2");
			rs=st.executeQuery();
		}

		java.sql.ResultSetMetaData rsmd = rs.getMetaData();
		int numCols = rsmd.getColumnCount();

		String[] caps = new String[numCols];
		for (int i = 1; i <= rsmd.getColumnCount(); i++)
			caps[i - 1] = rsmd.getColumnName(i);

		Object[][] tau = new Object[numFiles][numCols];
		int i = 0;
		while (rs.next()) {
			for (int j = 0; j < rsmd.getColumnCount(); j++)
				tau[i][j] = rs.getString(j + 1);
			i++;
		}
		movs.setModel(new DefaultTableModel(tau, caps));

		rs.close();
		st.close();
		con.close();

	}


	private void mostrarMovSel() throws SQLException {

		int[] filesSel = movs.getSelectedRows();
		TableModel modelMovs = movs.getModel();
		// capçaleres
		String[] caps = new String[6];
		for (int i = 0; i < 5; i++)
			caps[i] = modelMovs.getColumnName(i);
		if (!filtre.getSelectedItem().equals("   "))
			caps[5]=filtre.getSelectedItem().toString();
		else
			caps[5]="Quota anual";

		// contingut
		Object[][] tau = new Object[filesSel.length][6];
		for (int i = 0; i < movs.getSelectedRowCount(); i++) {
			System.out.println("Fila " + (i + 1) + ": " + filesSel[i]);
			for (int j = 0; j < 5; j++)
				tau[i][j] = movs.getValueAt(filesSel[i], j);
			if (!filtre.getSelectedItem().equals("   ")) {
				int  j = Integer.parseInt(filtre.getSelectedItem().toString().substring(1, 2));
				tau[i][5] = movs.getValueAt(filesSel[i], (4+j));
			}
			else
				tau[i][5] = movs.getValueAt(filesSel[i], 5);
		}
		movsSel.setModel(new DefaultTableModel(tau, caps));
		movsSel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		movsSel.addRowSelectionInterval(0, 0);

		triatMovSel();
	}

	private void triatMovSel() throws SQLException {
		int movSelSeleccionat = movsSel.getSelectedRow();
		if (movSelSeleccionat != -1) {
			Double quant = Double.parseDouble(movsSel.getValueAt(movSelSeleccionat, 5).toString());
			if (quant != 0)
				buscarComptes();
		} else {
			llistaComptesDebit.clearSelection();
			llistaComptesCredit.clearSelection();
		}

	}

	private void buscarComptes() {
		// TODO Auto-generated method stub
		llistaComptesDebit.clearSelection();
		llistaComptesCredit.clearSelection();
		String compteQuotes = "720000001";
		String altra = null;
		if (movsSel.getSelectedRow() != -1) {
			// Si afecta a un soci
				int soci = Integer.parseInt(movsSel.getValueAt(movsSel.getSelectedRow(), 0).toString());
				altra = Integer.toString(448200000 + soci);
			}

		debit = altra;
		credit = compteQuotes;

		if (debit != null) {
			int buscaDebit = llistaComptesDebit.getNextMatch(debit, 1, null);
			llistaComptesDebit.setSelectedIndex(buscaDebit);
			llistaComptesDebit.ensureIndexIsVisible(buscaDebit);
		}
		if (credit != null) {
			int buscaCredit = llistaComptesCredit.getNextMatch(credit, 1, null);
			llistaComptesCredit.setSelectedIndex(buscaCredit);
			llistaComptesCredit.ensureIndexIsVisible(buscaCredit);
		}
	}

	private void omplirComptes() throws SQLException {
		modelDebit.clear();
		modelCredit.clear();
		Statement stSQLite = conSQLite.createStatement();
		ResultSet rs = stSQLite.executeQuery("SELECT * FROM plancontable ORDER BY 1");
		int i = 0;
		while (rs.next()) {
			// if (rs.getString(1).length() == 9) {
			String cad = String.format("%-9s", rs.getString(1)) + " " + rs.getString(2);
			if (cad.length() > 30)
				cad = cad.substring(0, 29);
			modelDebit.addElement(cad);
			i++;
			// }
		}
		llistaComptesDebit.setModel(modelDebit);
		modelCredit = modelDebit;
		llistaComptesCredit.setModel(modelCredit);

		rs.close();
		stSQLite.close();
	}

	private void canviJList() throws SQLException {
		// TODO Auto-generated method stub
		if (!llistaComptesDebit.isSelectionEmpty() && !llistaComptesCredit.isSelectionEmpty()) {
			assent.removeAll();
			Connection con = DriverManager.getConnection(URLSQLite);
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT prox_pase FROM configuracion");
			rs.next();
			Statement st1 = con.createStatement();
			ResultSet rs1 = st1
					.executeQuery("SELECT prox_asiento FROM ejercicios WHERE codigo='" + exercici.getText() + "'");
			rs1.next();
			String[] caps = { "assentament", "auto", "data", "compte", "concepte", "debit", "credit", "exercici" };
			Object[][] ass = new Object[5][8];
			ass[0][0] = rs1.getInt(1);
			ass[0][1] = rs.getInt(1);
			ass[0][2] = dataMovs.getText();
			ass[0][3] = credit;
			ass[0][4] = "Quota " + exercici.getText() + " " + filtre.getSelectedItem() + " " 
						+ movsSel.getValueAt(movsSel.getSelectedRow(), 2).toString() + " "+ movsSel.getValueAt(movsSel.getSelectedRow(), 1).toString();
			ass[0][5] = 0;
			ass[0][6] = Double.parseDouble(movsSel.getValueAt(movsSel.getSelectedRow(), 5).toString());
			ass[0][7] = exercici.getText();

			int numCredit = 1;
			ass[numCredit][0] = rs1.getInt(1);
			ass[numCredit][1] = rs.getInt(1) + numCredit;
			ass[numCredit][2] = dataMovs.getText();
			ass[numCredit][3] = debit;
			ass[numCredit][4] = "Quota " + exercici.getText() + " " + filtre.getSelectedItem() + " " 
					+ movsSel.getValueAt(movsSel.getSelectedRow(), 2).toString() + " "+ movsSel.getValueAt(movsSel.getSelectedRow(), 1).toString();
			ass[numCredit][5] = Double.parseDouble(movsSel.getValueAt(movsSel.getSelectedRow(), 5).toString());
			ass[numCredit][6] = 0;
			ass[numCredit][7] = exercici.getText();

			assent.setModel(new javax.swing.table.DefaultTableModel(ass, caps));
			rs.close();
			rs1.close();
			st.close();
			con.close();
		} else {
			DefaultTableModel dm = (DefaultTableModel) assent.getModel();
			dm.getDataVector().removeAllElements();
			dm.fireTableDataChanged();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == anar) {
			try {
				//if (checkBANCcaixa.se)
				agafarSocis(exercici.getText());
				movs.setRowSelectionInterval(0, 0);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		if (e.getSource() == aplicarFiltre) {
			movs.clearSelection();
			System.out.println("Trimestre: " + filtre.getSelectedItem().toString().substring(1, 2));
			for (int i = 0; i < movs.getRowCount(); i++) {
				switch (filtre.getSelectedItem().toString().substring(1, 2)) {
				case "1": if (Double.parseDouble(movs.getValueAt(i, 5).toString())!=0)
							movs.addRowSelectionInterval(i, i);
						  dataMovs.setText(exercici.getText()+"-01-02");
						  break;
				case "2": if (Double.parseDouble(movs.getValueAt(i, 6).toString())!=0)
							movs.addRowSelectionInterval(i, i);
				  			dataMovs.setText(exercici.getText()+"-04-01");
				  		  break;
				case "3": if (Double.parseDouble(movs.getValueAt(i, 7).toString())!=0)
							movs.addRowSelectionInterval(i, i);
				  			dataMovs.setText(exercici.getText()+"-07-01");
				  		  break;
				case "4": if (Double.parseDouble(movs.getValueAt(i, 8).toString())!=0)
							movs.addRowSelectionInterval(i, i);
				  			dataMovs.setText(exercici.getText()+"-10-01");
						  break;
				default:  if (Double.parseDouble(movs.getValueAt(i, 5).toString())!=0)
							movs.addRowSelectionInterval(i, i);
				  			dataMovs.setText(exercici.getText()+"-01-02");
				  			break;
				  
				}
			}
		}

		if (e.getSource() == executar) {
			int indexSelPrev = movsSel.getSelectedRow();
			try {
				Statement stSQLite = conSQLite.createStatement();
				String sql = null;
				int proxAss = 0;
				int proxPase = 0;
				for (int i = 0; i < assent.getRowCount(); i++) {
					if (assent.getValueAt(i, 0) != null && !(assent.getValueAt(i, 0).toString().equals(""))) {
						sql = "INSERT INTO DIARIO (asiento,pase,fecha,cuenta,debe,haber,concepto,diario,usuario,ejercicio,fecha_factura) "
								+ "VALUES(" + assent.getValueAt(i, 0) + "," + assent.getValueAt(i, 1) + ",'"
								+ assent.getValueAt(i, 2) + "','" + assent.getValueAt(i, 3) + "',"
								+ assent.getValueAt(i, 5) + "," + assent.getValueAt(i, 6) + "," + "'"
								+ assent.getValueAt(i, 4).toString().replaceAll("'", "''") + "','','alvar','" + assent.getValueAt(i, 7) + "','"
								+ assent.getValueAt(i, 2) + "')";
						// System.out.println(sql);
						stSQLite.executeUpdate(sql);
						proxPase = Integer.parseInt(assent.getValueAt(i, 1).toString());
					}
				}
				if (assent.getRowCount() > 0) {
					int banc_caixa;
					proxAss = Integer.parseInt(assent.getValueAt(0, 0).toString()) + 1;
					sql = "UPDATE ejercicios SET prox_asiento=" + proxAss + " WHERE codigo='"
							+ assent.getValueAt(0, 7).toString() + "'";
					stSQLite.executeUpdate(sql);
					proxPase++;
					sql = "UPDATE configuracion SET prox_pase=" + proxPase;
					stSQLite.executeUpdate(sql);
				}
				stSQLite.close();
				movsSel.addRowSelectionInterval(indexSelPrev, indexSelPrev);
				agafarSocis(exercici.getText());
			} catch (SQLException e1) {
				System.out.println("No ha anat bé la inserció: " + e1.getMessage());
			}
		}

		if (e.getSource() == executarTots) {
			int indexSelPrev = movsSel.getSelectedRow();

			try {
				Statement stSQLite = conSQLite.createStatement();
				System.out.println("Total: " + movsSel.getRowCount());
				int proxAss = 0;
				int proxPase = 0;
				for (int x = 0; x < movsSel.getRowCount(); x++) {
					System.out.println(x);
					movsSel.addRowSelectionInterval(x, x);
					String sql = null;
					for (int i = 0; i < assent.getRowCount(); i++) {
						if (assent.getValueAt(i, 0) != null && !(assent.getValueAt(i, 0).toString().equals(""))) {
							System.out.print(i + " --> ");
							System.out.print(assent.getValueAt(i, 2) + " --> ");
							System.out.println(assent.getValueAt(i, 3).toString().trim());
							sql = "INSERT INTO DIARIO (asiento,pase,fecha,cuenta,debe,haber,concepto,diario,usuario,ejercicio,fecha_factura) "
									+ "VALUES(" + assent.getValueAt(i, 0) + "," + assent.getValueAt(i, 1) + ",'"
									+ assent.getValueAt(i, 2) + "','" + assent.getValueAt(i, 3).toString().trim() + "',"
									+ assent.getValueAt(i, 5) + "," + assent.getValueAt(i, 6) + "," + "'"
									+ assent.getValueAt(i, 4).toString().replaceAll("'", "''") + "','','alvar','" + assent.getValueAt(i, 7) + "','"
									+ assent.getValueAt(i, 2) + "')";
							// System.out.println(sql);
							stSQLite.executeUpdate(sql);
							proxPase = Integer.parseInt(assent.getValueAt(i, 1).toString());
						}
					}
					if (assent.getRowCount() > 0) {
						proxAss = Integer.parseInt(assent.getValueAt(0, 0).toString()) + 1;
						sql = "UPDATE ejercicios SET prox_asiento=" + proxAss + " WHERE codigo='"
								+ assent.getValueAt(0, 7).toString() + "'";
						stSQLite.executeUpdate(sql);
						System.out.println("Prox pase: " + proxAss);
						proxPase++;
						sql = "UPDATE configuracion SET prox_pase=" + proxPase;
						stSQLite.executeUpdate(sql);
					}
				}
				stSQLite.close();
				movsSel.addRowSelectionInterval(indexSelPrev, indexSelPrev);
				agafarSocis(exercici.getText());
			} catch (SQLException e1) {
				System.out.println("No ha anat bé la inserció: " + e1.getMessage());
			}
		}

		if (e.getSource() == buscarDebit) {
			// Però millor buscar en tot el contingutize()
			for (int i = 0; i < llistaComptesDebit.getModel().getSize(); i++) {
				if (llistaComptesDebit.getModel().getElementAt(i).toString().toLowerCase()
						.contains(textDebit.getText().toLowerCase())) {
					llistaComptesDebit.setSelectedIndex(i);
					llistaComptesDebit.ensureIndexIsVisible(i);
					break;
				}
			}
		}

		if (e.getSource() == buscarCredit) {
			// Però millor buscar en tot el contingutize()
			for (int i = 0; i < llistaComptesCredit.getModel().getSize(); i++) {
				if (llistaComptesCredit.getModel().getElementAt(i).toString().toLowerCase()
						.contains(textCredit.getText().toLowerCase())) {
					llistaComptesCredit.setSelectedIndex(i);
					llistaComptesCredit.ensureIndexIsVisible(i);
					break;
				}
			}
		}

		if (e.getSource() == creaCompte) {
			//pantallaCreaCompte f = new pantallaCreaCompte(this);
			try {
				omplirComptes();
				triatMovSel();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
