package Factura

import java.sql.DriverManager
import com.db4o.Db4oEmbedded
import java.util.Date

fun main() {
	val url = "jdbc:postgresql://89.36.214.106:5432/e_factura"
	val usuari = "e_factura"
	val password = "e_factura"

	val con = DriverManager.getConnection(url, usuari, password)

	val st4 = con.createStatement()
	val rs4 = st4.executeQuery("SELECT * FROM article")
	val llistaArticles = arrayListOf<Article>()
	while (rs4.next()){
		val a = Article(rs4.getString(1),rs4.getString(2),rs4.getFloat(3),rs4.getInt(4),rs4.getInt(5),arrayListOf<LiniaFac>())
		llistaArticles.add(a)
	}

	val st = con.createStatement()
	val rs = st.executeQuery("SELECT * FROM poble")

	val llistaPobles = arrayListOf<Poble>()
	while (rs.next()) {
		val p = Poble(rs.getInt(1), rs.getString(2), rs.getString(3), arrayListOf<Client>())
		println(p.nom)
		llistaPobles.add(p)
		val st1 = con.createStatement()
		val rs1 = st1.executeQuery("SELECT * FROM client where cod_pob=" + rs.getInt(1))
		while (rs1.next()) {
			val c = Client(rs1.getInt(1), p, rs1.getString(2), rs1.getString(3), rs1.getInt(4), arrayListOf<Factura>())
			p.clients?.add(c)
			val st2 = con.createStatement()
			val rs2 = st2.executeQuery("SELECT * FROM factura where cod_cli=" + rs1.getInt(1))
			while (rs2.next()) {
				//println(rs2.getString(3))
				val f = Factura(rs2.getInt(1), c, rs2.getDate(2), rs2.getInt(5), arrayListOf<LiniaFac>())
				c.factures?.add(f)
				val st3 = con.createStatement()
				val rs3 = st3.executeQuery("SELECT * FROM linia_fac where num_f=" + rs2.getInt(1))
				while(rs3.next()){
					//buscar l'article
					val art = llistaArticles.find { it.codA == rs3.getString(3) }
					println("\t\t" + art?.codA)
					val l = LiniaFac(rs3.getInt(1),art,f,rs3.getInt(4),rs3.getFloat(5))
					f.liniesFac?.add(l)
					art?.liniesFac?.add(l)
				}
				st3.close()
			}
			st2.close()
		}
		st1.close()
	}
	st.close()
	
	val bd = Db4oEmbedded.openFile("Factures.db4o")

	for (p in llistaPobles) {
		println(p.nom + " ---> " + p.clients?.size)
		bd.store(p)
	}
	for (a in llistaArticles) {
		println(a.codA + " ---> " + a.descrip)
		bd.store(a)
	}
	bd.close()
}