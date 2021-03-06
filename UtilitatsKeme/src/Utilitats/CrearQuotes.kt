package Utilitats

import javax.swing.*
import java.awt.*
import java.io.File
import java.sql.DriverManager
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class Finestra : JFrame() {
	val fCh = JFileChooser("/home/alvar/Dropbox/Privat/AMCastàlia")
	val taula = JTable(1, 6)
	val area = JTextArea(10, 100)
	val concepte = JTextField(15)


	init {

		defaultCloseOperation = JFrame.EXIT_ON_CLOSE
		setTitle("Crear apunts Quotes")
		//setSize(400, 300)
		setLayout(GridLayout(2, 1))

		val panell1 = JPanel(BorderLayout())
		val panell11 = JPanel(FlowLayout())
		val panell12 = JPanel(BorderLayout())
		val panell13 = JPanel(FlowLayout())
		add(panell1)
		panell1.add(panell11, BorderLayout.NORTH)
		panell1.add(panell12, BorderLayout.CENTER)
		panell1.add(panell13, BorderLayout.SOUTH)


		val fitxer = JTextField(25)
		panell11.add(fitxer)
		val botoTriarFitxer = JButton("Triar fitxer")
		panell11.add(botoTriarFitxer)


		panell12.add(JLabel("Llista de músics i quotes:"), BorderLayout.NORTH)
		taula.setEnabled(false)
		val scroll = JScrollPane(taula)
		panell12.add(scroll, null)

		botoTriarFitxer.addActionListener {
			val r = fCh.showOpenDialog(this)
			if (r == JFileChooser.APPROVE_OPTION) {
				fitxer.setText(fCh.getSelectedFile().getName())
				val f = fCh.getSelectedFile()
				val linies = ArrayList(f.readLines())
				//llevem la primera línia de les capçaleres
				linies.removeAt(0)
				plenarTaula(linies)
				concepte.setText(fCh.getSelectedFile().getName().substring(0,fCh.getSelectedFile().getName().length-4))
			}
		}

		panell13.add(JLabel("Data assentaments:"))
		val data = JTextField(15)
		val avui = LocalDateTime.now()
		val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
		data.setText(avui.format(formatter))
		panell13.add(data)

		panell13.add(JLabel("Concepte:"))
		panell13.add(concepte)

		val botoPassarSQL = JButton("Convertir SQL")
		panell13.add(botoPassarSQL)

		val comboCompte = JComboBox(arrayOf("Quotes", "Loteria"))
		panell13.add(comboCompte)
		val compte = JTextField(10)
		panell13.add(compte)

		comboCompte.addActionListener {
			when (comboCompte.getSelectedItem()) {
				"Quotes" -> compte.setText("720000001")
				"Loteria" -> compte.setText("726000001")
			}
		}
		comboCompte.setSelectedIndex(0)


		botoPassarSQL.addActionListener {
			val dadesConnexio = "jdbc:sqlite:/home/alvar/Dropbox/Privat/AMCastàlia/Comptabilitat/keme/amcastalia.sqlite"
			val con = DriverManager.getConnection(dadesConnexio)
			val st0 = con.createStatement()
			val rs0 = st0.executeQuery("SELECT MAX (codigo) FROM EJERCICIOS")
			val exercici = rs0.getString(1)
			val st1 = con.createStatement()
			val rs1 =
				st1.executeQuery("SELECT prox_asiento FROM EJERCICIOS WHERE codigo = '" + exercici + "'")
			var segAssent = 0
			if (rs1.next())
				segAssent = rs1.getInt(1)

			val st2 = con.createStatement()
			val rs2 = st2.executeQuery("SELECT prox_pase FROM CONFIGURACION")
			var segPasse = rs2.getInt(1)

			val st3 = con.createStatement()

			area.setText("")
			for (l in 0 until taula.getRowCount()) {
				/*	var cadena = "INSERT INTO DIARIO(asiento, pase, fecha, cuenta, debe, haber, concepto, usuario, ejercicio, fecha_factura) " +
                                                 "VALUES (?,?,?,?,?,?,?,?,?,?)"
                    val st2 = con.prepareStatement(cadena)
                    st2.setInt(1,segAssent)
                    st2.setInt(2,segAssent)
                    st2.setString(3,"2-12-2020")
                    st2.setString(4,"44820"+taula.getModel().getValueAt(l, 0).toString().padStart(4, '0'))
                    st2.setFloat(5,taula.getModel().getValueAt(l, 5).toString().replace(",",".").toFloat())
                    st2.setFloat(6,"0.0")
                    st2.setString(7,"QUOTA 2021-1T " + taula.getModel().getValueAt(l, 1) +", " + taula.getModel().getValueAt(l, 2))
                    st2.setString(8,"Alvar")
                    st2.setString(9,"2020")
                    st2.setString(10,"2-12-2020")*/
				var cadena =
					"INSERT INTO DIARIO(asiento, pase, fecha, cuenta, debe, haber, concepto, usuario, ejercicio) " +
							"VALUES ("
				cadena = cadena + segAssent + ","
				cadena = cadena + segPasse + ","
				cadena = cadena + "'" + data.getText() + "',"
				cadena = cadena + "'44820" + (taula.getModel().getValueAt(l, 0)).toString().padStart(4, '0') + "',"
				cadena = cadena + taula.getModel().getValueAt(l, 5).toString().replace(",", ".").toFloat() + ","
				cadena = cadena + "0" + ",'"
				cadena = cadena + concepte.getText() + ": " + taula.getModel().getValueAt(l, 1).toString().replace("'", "''") +
						", " + taula.getModel().getValueAt(l, 2).toString().replace("'", "''") + "',"
				cadena = cadena + "'alvar'" + ","
				cadena = cadena + "'2021')"

				st3.executeUpdate(cadena)
				area.append(cadena + "\n")

				segPasse++
				cadena =
					"INSERT INTO DIARIO(asiento, pase, fecha, cuenta, debe, haber, concepto, usuario, ejercicio) " +
							"VALUES ("
				cadena = cadena + segAssent + ","
				cadena = cadena + segPasse + ","
				cadena = cadena + "'" + data.getText() + "',"
				cadena = cadena + "'" + compte.getText() + "',"
				cadena = cadena + "0" + ","
				cadena = cadena + taula.getModel().getValueAt(l, 5).toString().replace(",", ".").toFloat() + ",'"
				cadena = cadena + concepte.getText() + ": " + taula.getModel().getValueAt(l, 1) + ", " + taula.getModel()
					.getValueAt(l, 2) + "',"
				cadena = cadena + "'alvar'" + ","
				cadena = cadena + "'2021')"
				st3.executeUpdate(cadena)

				area.append(cadena + "\n")
				segPasse++
				segAssent++
			}
			st3.executeUpdate("UPDATE EJERCICIOS SET prox_asiento=" + segAssent + " WHERE codigo ='" + exercici + "'")
			st3.executeUpdate("UPDATE CONFIGURACION SET prox_pase=" + segPasse)
			con.close()
		}


		val panell2 = JPanel(BorderLayout())
		add(panell2)
		panell2.add(area)
		pack()

	}

	fun plenarTaula(linies: List<String>) {
		var ll = Array(linies.size) { arrayOfNulls<String>(6) }
		var compt = 0
		for (i in 0 until linies.size) {
			val camps = linies.get(i).split(";")
			if (camps.get(5) != "0") {
				for (j in 0 until camps.size) {
					ll[compt][j] = camps.get(j)

				}
				compt++
			}
		}
		val ll2 = Array(compt) { arrayOfNulls<String>(6) }
		ll.copyInto(ll2, 0, 0, compt)
		val caps = arrayOf("Num", "Cognoms", "Nom", "Data", "Estudis", "Quota")
		taula.setModel(javax.swing.table.DefaultTableModel(ll2, caps))
	}
}


fun main(args: Array<String>) {
	EventQueue.invokeLater {
		Finestra().isVisible = true
	}
}

