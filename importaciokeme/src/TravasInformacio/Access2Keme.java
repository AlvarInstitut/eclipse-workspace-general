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

public class Access2Keme {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		Pantalla finestra = new Pantalla();
		finestra.iniciar();
	}

}

class Pantalla extends JFrame implements ActionListener {
	String URLSQLite = "jdbc:sqlite:/home/alvar/keme/amcastalia.sqlite";
	Connection conSQLite = null;

	JTable movs = new JTable(1, 1);
	JScrollPane scrollPaneMovs = new JScrollPane(movs);

	JCheckBox checkBANCcaixa = new JCheckBox("BANC / caixa", true);
	JTextField exercici = new JTextField(10);
	JButton anar = new JButton("anar");

	JTextField filtre = new JTextField(20);
	JButton aplicarFiltre = new JButton("Aplicar");

	JTable movsSel = new JTable(1, 1);
	JScrollPane scrollPaneMovsSel = new JScrollPane(movsSel);

	JList llistaComptesDebit = new JList();
	DefaultListModel modelDebit = new DefaultListModel();
	JScrollPane scrollLlistaDebit = new JScrollPane(llistaComptesDebit);
	JList llistaComptesInter = new JList();
	DefaultListModel modelInter = new DefaultListModel();
	JScrollPane scrollLlistaInter = new JScrollPane(llistaComptesInter);
	JList llistaComptesCredit = new JList();
	DefaultListModel modelCredit = new DefaultListModel();
	JScrollPane scrollLlistaCredit = new JScrollPane(llistaComptesCredit);

	JTextField textDebit = new JTextField(10);
	JButton buscarDebit = new JButton("Busca");
	JTextField textInter = new JTextField(10);
	JButton buscarInter = new JButton("Busca");
	JTextField textCredit = new JTextField(10);
	JButton buscarCredit = new JButton("Busca");

	JButton creaCompte = new JButton("Crear Compte");
	JCheckBox esCompra = new JCheckBox("Compra", false);
	JLabel tipIVA = new JLabel("Tipus IIVA:");
	String[] tIVA = {"4","10","21"};
	JComboBox tipusIVA = new JComboBox(tIVA);


	JTable assent = new JTable(5, 8);
	JScrollPane scrollPaneAssent = new JScrollPane(assent);

	String debit = null;
	String inter = null;
	String credit = null;

	boolean compra = false;

	JButton executar = new JButton("Executar");
	JButton executarTots = new JButton("Executar TOTS");

	Set filesFetes = new HashSet();

	void iniciar() throws SQLException {
		conSQLite = DriverManager.getConnection(URLSQLite);
		// Si no existeix la de TRASVASOS_REALITZATS la creem
		Statement st = conSQLite.createStatement();
		String sql = "CREATE TABLE IF NOT EXISTS TRASVASOS_REALITZATS ("
				+ "			    num_access    INTEGER PRIMARY KEY," + "			    assent_sqlite INTEGER"
				+ "			)";
		st.executeUpdate(sql);
		st.close();

		this.getContentPane().setLayout(new BorderLayout());
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel pan = new JPanel(new GridLayout(1, 2));
		JPanel pan1 = new JPanel(new BorderLayout());
		JPanel pan11 = new JPanel(new FlowLayout());
		pan11.add(checkBANCcaixa);
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
		/*
		 * pan21.add(movSelNum); pan21.add(movSelData); pan21.add(movSelSoci);
		 * pan21.add(movSelConcepte); pan21.add(movSelDescripcio);
		 * pan21.add(movSelDebit); pan21.add(movSelCredit);
		 */
		pan21.add(scrollPaneMovsSel);
		pan2.add(scrollPaneMovsSel);

		JPanel pan22 = new JPanel(new FlowLayout());
		pan2.add(pan22);

		/*
		 * Dimension d = llistaComptesDebit.getPreferredSize(); d.width = 200;
		 * scrollLlistaDebit.setPreferredSize(d);
		 */
		llistaComptesDebit.setFixedCellWidth(202);
		llistaComptesDebit.setFont(new Font("Courier New", Font.BOLD, 12));
		pan22.add(scrollLlistaDebit);
		llistaComptesInter.setFixedCellWidth(202);
		llistaComptesInter.setFont(new Font("Courier New", Font.BOLD, 12));
		pan22.add(scrollLlistaInter);
		llistaComptesCredit.setFixedCellWidth(202);
		llistaComptesCredit.setFont(new Font("Courier New", Font.BOLD, 12));
		pan22.add(scrollLlistaCredit);

		pan22.add(textDebit);
		pan22.add(buscarDebit);
		pan22.add(textInter);
		pan22.add(buscarInter);
		pan22.add(textCredit);
		pan22.add(buscarCredit);

		pan22.add(creaCompte);
		pan22.add(esCompra);
		pan22.add(tipIVA);
		pan22.add(tipusIVA);
		tipIVA.setVisible(false);
		tipusIVA.setVisible(false);

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
		buscarInter.addActionListener(this);
		buscarCredit.addActionListener(this);

		creaCompte.addActionListener(this);

		esCompra.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1) {
					System.out.println("S'ha activat compra");
					compra = true;
					inter = "472000001";
					int buscaInter = llistaComptesInter.getNextMatch(inter, 1, null);
					llistaComptesInter.setSelectedIndex(buscaInter);
					llistaComptesInter.ensureIndexIsVisible(buscaInter);
					try {
						canviJList();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					tipIVA.setVisible(true);
					tipusIVA.setVisible(true);
				}
				else {
					compra = false;
					inter = "";
					llistaComptesInter.clearSelection();
					try {
						canviJList();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					tipIVA.setVisible(false);
					tipusIVA.setVisible(false);
				}
					
				;
			}
		});

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
		llistaComptesInter.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					llistaComptesInter.clearSelection();
					try {
						canviJList();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		llistaComptesInter.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				try {
					if (!llistaComptesInter.isSelectionEmpty()) {
						inter = llistaComptesInter.getSelectedValue().toString().substring(0, 9);
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
				// do some actions here, for example
				// print first column value from selected row

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
						llistaComptesInter.clearSelection();
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

	private void agafarBanc(String exerc) throws SQLException {
		// TODO Auto-generated method stub
		movs.removeAll();
		filesFetes.clear();
		movs.setBackground(Color.white);
		String filename = "/home/alvar/Dropbox/Privat/AMCastàlia/Socis/AMCASTALIA_2019_04_01_NovaVersió.accdb";
		String url = "jdbc:ucanaccess://" + filename;
		Connection con = DriverManager.getConnection(url);
		Statement st0 = con.createStatement();
		Statement st = con.createStatement();
		ResultSet rs0;
		if (checkBANCcaixa.isSelected())
			rs0 = st0.executeQuery("SELECT count(*) FROM [LIBRO BANCO] WHERE YEAR([FECHA OPERACIÓN])=" + exerc);
		else 
			rs0 = st0.executeQuery("SELECT count(*) FROM [LIBRO CAJA] WHERE YEAR([FECHA OPERACIÓN])=" + exerc);
		
		rs0.next();
		int numFiles = rs0.getInt(1);

		ResultSet rs;
		
		if (checkBANCcaixa.isSelected())
			rs = st.executeQuery(
				"SELECT [MOVIMIENTO Nº], Format([FECHA OPERACIÓN],\"yyyy-mm-dd\") , COD_SOCIO , [CONCEPTO_ING_GAS] , DESCRIPCIÓN, HABER , DEBE "
						+ "FROM [LIBRO BANCO] WHERE YEAR([FECHA OPERACIÓN])=" + exerc + " ORDER BY 2,1");
		else
			rs = st.executeQuery(
					"SELECT [MOVIMIENTO Nº], Format([FECHA OPERACIÓN],\"yyyy-mm-dd\") , COD_SOCIO , [CONCEPTO_ING_GAS] , DESCRIPCIÓN, HABER , DEBE "
							+ "FROM [LIBRO CAJA] WHERE YEAR([FECHA OPERACIÓN])=" + exerc + " ORDER BY 2,1");

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

		comprovarRealitzats();
	}

	private void comprovarRealitzats() throws SQLException {
		// TODO Auto-generated method stub
		Statement stSQLite = conSQLite.createStatement();
		ResultSet rs = stSQLite.executeQuery("SELECT access_banc_caixa,num_access FROM TRASVASOS_REALITZATS");
		Set fets = new HashSet();
		
		while (rs.next()) {
			if (checkBANCcaixa.isSelected() && rs.getInt(1)==1)
				fets.add(rs.getString(2));
			if (! checkBANCcaixa.isSelected() && rs.getInt(1)==2)
				fets.add(rs.getString(2));
		}
		rs.close();
		stSQLite.close();
		int total = movs.getRowCount();
		for (int i = 0; i < total; i++) {
			if (fets.contains(movs.getValueAt(i, 0).toString())) {
				filesFetes.add(i);
			}
		}
//		if (index.size() > 0) {
		movs.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);
				c.setBackground(filesFetes.contains(row) ? Color.RED : Color.WHITE);
				c.setForeground(filesFetes.contains(row) ? Color.WHITE : Color.BLACK);
				if (isSelected)
					c.setBackground(Color.cyan);
				return c;
			}
		});
		// }
		// movs.setSelectionBackground(Color.cyan);
	}

	private void mostrarMovSel() throws SQLException {

		int[] filesSel = movs.getSelectedRows();
		TableModel modelMovs = movs.getModel();
		// capçaleres
		String[] caps = new String[modelMovs.getColumnCount()];
		for (int i = 0; i < modelMovs.getColumnCount(); i++)
			caps[i] = modelMovs.getColumnName(i);

		// contingut
		Object[][] tau = new Object[filesSel.length][modelMovs.getColumnCount()];
		for (int i = 0; i < movs.getSelectedRowCount(); i++) {
			System.out.println("Fila " + (i + 1) + ": " + filesSel[i]);
			for (int j = 0; j < modelMovs.getColumnCount(); j++)
				tau[i][j] = movs.getValueAt(filesSel[i], j);
		}
		movsSel.setModel(new DefaultTableModel(tau, caps));
		movsSel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		movsSel.addRowSelectionInterval(0, 0);

		triatMovSel();
	}

	private void triatMovSel() throws SQLException {
		int movSelSeleccionat = movsSel.getSelectedRow();
		if (movSelSeleccionat != -1) {
			Double debit = Double.parseDouble(movsSel.getValueAt(movSelSeleccionat, 5).toString());
			Double credit = Double.parseDouble(movsSel.getValueAt(movSelSeleccionat, 6).toString());
			if (debit != 0 && credit == 0)
				buscarComptes(0);
			if (debit == 0 && credit != 0)
				buscarComptes(1);
		} else {
			llistaComptesDebit.clearSelection();
			llistaComptesInter.clearSelection();
			llistaComptesCredit.clearSelection();
		}

	}

	private void buscarComptes(int lloc) {
		// TODO Auto-generated method stub
		llistaComptesDebit.clearSelection();
		llistaComptesInter.clearSelection();
		llistaComptesCredit.clearSelection();
		String banc;
		if (checkBANCcaixa.isSelected())
			banc = "572000001";
		else
			banc = "570000001";
		String inter = null;
		String altra = null;
		if (movsSel.getSelectedRow() != -1) {
			esCompra.setSelected(false);
			// Si afecta a un soci
			if (movsSel.getValueAt(movsSel.getSelectedRow(), 2) != null
					&& !movsSel.getValueAt(movsSel.getSelectedRow(), 2).toString().equals("")) {
				int soci = Integer.parseInt(movsSel.getValueAt(movsSel.getSelectedRow(), 2).toString());
				inter = Integer.toString(448200000 + soci);
				// Si és quota d'enguany (2019) o una altra cosa va a 720000001 (quotes afiliats) 
				// però si és any anterior va a 113000001 (pagaments endarrerits)
				if (movsSel.getValueAt(movsSel.getSelectedRow(), 3).toString().contains("QUOTA 18"))
					altra = "113000001";
				else
					altra = "720000001";
			}
			
			// Si afecta a la Directora
			if (movsSel.getValueAt(movsSel.getSelectedRow(), 3).toString().equals("DIRECTOR")) {
				inter = null;
				altra = "465000001";
			}
			// Si afecta a Hisenda
			if (movsSel.getValueAt(movsSel.getSelectedRow(), 3).toString().equals("HISENDA")) {
				inter = null;
				altra = "475100001";
			}
			// Si afecta a la Seguretat Social
			if (movsSel.getValueAt(movsSel.getSelectedRow(), 3).toString().equals("SEGURETAT SOCIAL")) {
				inter = null;
				altra = "476000001";
			}
			// Si afecta a Comissions del banc
			if (movsSel.getValueAt(movsSel.getSelectedRow(), 3).toString().equals("COMISSIÓ")) {
				inter = null;
				altra = "626000001";
			}
			// Si afecta a FEDERACIÓ
			if (movsSel.getValueAt(movsSel.getSelectedRow(), 3).toString().equals("FEDERACIÓ")) {
				inter = null;
				altra = "629000004";
			}
			// Si afecta a INTERNET
			if (movsSel.getValueAt(movsSel.getSelectedRow(), 3).toString().equals("INTERNET")) {
				esCompra.setSelected(true);
				tipusIVA.setSelectedItem("21");
				altra = "629000003";
			}
			// Si afecta a CEDRO
			if (movsSel.getValueAt(movsSel.getSelectedRow(), 3).toString().equals("CEDRO")) {
				esCompra.setSelected(true);
				tipusIVA.setSelectedItem("21");
				altra = "629000001";
			}
			// Si afecta a SOPARETS i és un pagament
			if (movsSel.getValueAt(movsSel.getSelectedRow(), 3).toString().equals("SOPARETS")
					&& lloc==1) {

				esCompra.setSelected(true);
				tipusIVA.setSelectedItem("10");
				altra = "627000001";
			}
			// Si afecta a SOPARETS i cobrem (un soci paga la seua part)
			if (movsSel.getValueAt(movsSel.getSelectedRow(), 3).toString().equals("SOPARETS")
					&& lloc==0) {

				esCompra.setSelected(false);
				altra = "726000021";
			}
			// Si afecta a INSTRUMENTS
			if (movsSel.getValueAt(movsSel.getSelectedRow(), 3).toString().equals("INSTRUMENTS")) {
				esCompra.setSelected(true);
				tipusIVA.setSelectedItem("21");
				// Haurem de crear un compte auxiliar nou, que és dels 219xxx
				// però com el número l'he de crear a partir de la llista d'Inventari, no anem a mecanitzar el número decompte demoment
				//creaCompte.doClick();
				
			}
			// Si afecta a un soci COMISSIÓ LOTERIA
			if (movsSel.getValueAt(movsSel.getSelectedRow(), 3).toString().equals("LOT2019COM")) {
				if (movsSel.getValueAt(movsSel.getSelectedRow(), 2) != null
						&& !movsSel.getValueAt(movsSel.getSelectedRow(), 2).toString().equals("")) {
					int soci = Integer.parseInt(movsSel.getValueAt(movsSel.getSelectedRow(), 2).toString());
					inter = Integer.toString(448200000 + soci);
					altra = "726000001";
				}
			}
			// Si afecta a LOTERIA
			if (movsSel.getValueAt(movsSel.getSelectedRow(), 3).toString().equals("LOT2019")) {
				if (movsSel.getValueAt(movsSel.getSelectedRow(), 2) != null
						&& !movsSel.getValueAt(movsSel.getSelectedRow(), 2).toString().equals("")) {
					int soci = Integer.parseInt(movsSel.getValueAt(movsSel.getSelectedRow(), 2).toString());
					inter = Integer.toString(448200000 + soci);
					altra = "600000001";
				}
			}
			// Si afecta a ACTUACIONS
			if (movsSel.getValueAt(movsSel.getSelectedRow(), 3).toString().equals("ACTUACIÓ")) {
				inter = null;
				altra = "705000001";
			}

		}

		if (lloc == 0) {
			debit = banc;
			credit = altra;
		}
		if (lloc == 1) {
			debit = altra;
			credit = banc;
		}
		if (debit != null) {
			int buscaDebit = llistaComptesDebit.getNextMatch(debit, 1, null);
			llistaComptesDebit.setSelectedIndex(buscaDebit);
			llistaComptesDebit.ensureIndexIsVisible(buscaDebit);
		}
		if (inter != null) {
			int buscaInter = llistaComptesInter.getNextMatch(inter, 1, null);
			llistaComptesInter.setSelectedIndex(buscaInter);
			llistaComptesInter.ensureIndexIsVisible(buscaInter);
		}
		if (credit != null) {
			int buscaCredit = llistaComptesCredit.getNextMatch(credit, 1, null);
			llistaComptesCredit.setSelectedIndex(buscaCredit);
			llistaComptesCredit.ensureIndexIsVisible(buscaCredit);
		}
	}

	private void omplirComptes() throws SQLException {
		modelDebit.clear();
		modelInter.clear();
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
		modelInter = modelDebit;
		llistaComptesInter.setModel(modelInter);
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
			ass[0][2] = movsSel.getValueAt(movsSel.getSelectedRow(), 1).toString();
			ass[0][3] = credit;
			ass[0][4] = movsSel.getValueAt(movsSel.getSelectedRow(), 4).toString();
			ass[0][5] = 0;
			ass[0][6] = Double.parseDouble(movsSel.getValueAt(movsSel.getSelectedRow(), 5).toString())
					+ Double.parseDouble(movsSel.getValueAt(movsSel.getSelectedRow(), 6).toString());
			ass[0][7] = exercici.getText();

			int numCredit = 1;
			if (!llistaComptesInter.isSelectionEmpty()) { // hi ha compte intermig
				ass[1][0] = rs1.getInt(1);
				ass[1][1] = rs.getInt(1) + 1;
				ass[1][2] = movsSel.getValueAt(movsSel.getSelectedRow(), 1).toString();
				ass[1][3] = inter;
				ass[1][4] = movsSel.getValueAt(movsSel.getSelectedRow(), 4).toString();
				if (compra) {
					ass[1][5] = Math
							.round((Double.parseDouble(movsSel.getValueAt(movsSel.getSelectedRow(), 5).toString())
									+ Double.parseDouble(movsSel.getValueAt(movsSel.getSelectedRow(), 6).toString()))
									/ (1+Integer.parseInt(tipusIVA.getSelectedItem().toString())/100.0) 
									* Integer.parseInt(tipusIVA.getSelectedItem().toString())/100.0 * 100.0)
							/ 100.0;
					numCredit = 2;
				} else
					ass[1][5] = Double.parseDouble(movsSel.getValueAt(movsSel.getSelectedRow(), 5).toString())
							+ Double.parseDouble(movsSel.getValueAt(movsSel.getSelectedRow(), 6).toString());
				ass[1][6] = 0;
				ass[1][7] = exercici.getText();

				if (!compra) {
					ass[2][0] = rs1.getInt(1);
					ass[2][1] = rs.getInt(1) + 2;
					ass[2][2] = movsSel.getValueAt(movsSel.getSelectedRow(), 1).toString();
					ass[2][3] = inter;
					ass[2][4] = movsSel.getValueAt(movsSel.getSelectedRow(), 4).toString();
					ass[2][5] = 0;
					ass[2][6] = Double.parseDouble(movsSel.getValueAt(movsSel.getSelectedRow(), 5).toString())
							+ Double.parseDouble(movsSel.getValueAt(movsSel.getSelectedRow(), 6).toString());
					ass[2][7] = exercici.getText();

					numCredit = 3;
				}
			}
			ass[numCredit][0] = rs1.getInt(1);
			ass[numCredit][1] = rs.getInt(1) + numCredit;
			ass[numCredit][2] = movsSel.getValueAt(movsSel.getSelectedRow(), 1).toString();
			ass[numCredit][3] = debit;
			ass[numCredit][4] = movsSel.getValueAt(movsSel.getSelectedRow(), 4).toString();
			if (compra)
				ass[numCredit][5] = Math
						.round((Double.parseDouble(movsSel.getValueAt(movsSel.getSelectedRow(), 5).toString())
								+ Double.parseDouble(movsSel.getValueAt(movsSel.getSelectedRow(), 6).toString())) 
								/ (1+Integer.parseInt(tipusIVA.getSelectedItem().toString())/100.0)
								* 100.0)
						/ 100.0;
			else
				ass[numCredit][5] = Double.parseDouble(movsSel.getValueAt(movsSel.getSelectedRow(), 5).toString())
						+ Double.parseDouble(movsSel.getValueAt(movsSel.getSelectedRow(), 6).toString());
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
				agafarBanc(exercici.getText());
				movs.setRowSelectionInterval(0, 0);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		if (e.getSource() == aplicarFiltre) {
			movs.clearSelection();
			for (int i = 0; i < movs.getRowCount(); i++) {
				if (movs.getValueAt(i, 3).toString().contains(filtre.getText()))
					movs.addRowSelectionInterval(i, i);
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
					if (checkBANCcaixa.isSelected())
						banc_caixa=1;
					else 
						banc_caixa=2;
					sql = "INSERT INTO TRASVASOS_REALITZATS VALUES(" + banc_caixa + ","
							+ movsSel.getValueAt(movsSel.getSelectedRow(), 0).toString() + "," + assent.getValueAt(0, 0)
							+ ")";
					// System.out.println(sql);
					stSQLite.executeUpdate(sql);
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
				agafarBanc(exercici.getText());
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
						int banc_caixa;
						if (checkBANCcaixa.isSelected())
							banc_caixa=1;
						else 
							banc_caixa=2;
						sql = "INSERT INTO TRASVASOS_REALITZATS VALUES(" + banc_caixa + ","
								+ movsSel.getValueAt(movsSel.getSelectedRow(), 0).toString() + ","
								+ assent.getValueAt(0, 0) + ")";
						// System.out.println(sql);
						stSQLite.executeUpdate(sql);

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
				agafarBanc(exercici.getText());
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

		if (e.getSource() == buscarInter) {
			// Però millor buscar en tot el contingutize()
			for (int i = 0; i < llistaComptesInter.getModel().getSize(); i++) {
				if (llistaComptesInter.getModel().getElementAt(i).toString().toLowerCase()
						.contains(textInter.getText().toLowerCase())) {
					llistaComptesInter.setSelectedIndex(i);
					llistaComptesInter.ensureIndexIsVisible(i);
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
			pantallaCreaCompte f = new pantallaCreaCompte(this);
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
