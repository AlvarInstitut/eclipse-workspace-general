package Factura
// Generated 19 de febr. 2021, 12:15:29 by Hibernate Tools 5.2.12.Final


/**
 * Client generated by hbm2java
 */
class Client {
	var codCli: Int = 0
	var poble: Poble? = null
	var nom: String? = null
	var adreca: String? = null
	var cp: Int? = null
	var factures: ArrayList<Factura>?  = arrayListOf<Factura>()

	constructor() {}
	constructor(codCli: Int) {
		this.codCli = codCli
	}

	constructor(codCli: Int, poble: Poble?, nom: String?, adreca: String?, cp: Int?, factures: ArrayList<Factura>?) {
		this.codCli = codCli
		this.poble = poble
		this.nom = nom
		this.adreca = adreca
		this.cp = cp
		this.factures = factures
	}
}