package Factura

import com.db4o.Db4oEmbedded

fun main() {
	val bd = Db4oEmbedded.openFile("Factures.db4o")
	var cMostra=Client()
	var pMostra=Poble()
	pMostra.nom="CASTELLÓ"
	cMostra.poble=pMostra
	val q = bd.queryByExample<Client>(cMostra)

	for (c in q) {
		println(c.nom)
		for (f in c.factures!!) {
			print("\tFactura número " + f.numF )
			var tot = 0.0
			for (l in f.liniesFac!!)
				tot += l.quant!! * l.preu!!
			println(". Total factura: " + (Math.round(tot * 100) / 100.0))
		}
	}
	bd.close()
}