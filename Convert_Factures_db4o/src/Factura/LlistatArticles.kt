package Factura

import com.db4o.Db4oEmbedded

fun main(){
	val bd = Db4oEmbedded.openFile("Factures.db4o")
	val q = bd.query()          //node arrel.

	var node = q.descend("codA") //arribem a l'altura del sou, que és on posem la restricció
	node.orderAscending()

	val llista = q.execute<Article>()
	for (a in llista) {
		println(a.codA + " --> " + a.descrip)
	}
	println()
	println(llista.size)
	bd.close()
}