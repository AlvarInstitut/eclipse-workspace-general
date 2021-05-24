package Factura

import com.db4o.Db4oEmbedded

fun main(){
	val bd = Db4oEmbedded.openFile("Factures.db4o")
	val p = Poble()
	p.nom="CASTELLÓ"
	val q = bd.queryByExample<Poble>(p)   
	
	for (p1 in q) {
		println(p1.clients?.size)
		for (c in p1.clients!!){
			println("" + c.codCli + ": " + c.nom + " " + c.cp)
			for (f in c.factures!!){
				println("\t" + f.numF + " " + f.liniesFac?.size)
				var tot = 0.0
				for (l in f.liniesFac!!){
					println(l.preu)
					tot += l.preu!! * l.quant!!
				}
				println("\t" + f.numF + " (" + f.data + "): " + tot)
			}
			println()
		}
	}
	bd.close()
}