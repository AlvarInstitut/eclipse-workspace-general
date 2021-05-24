package Utilitats

import javax.xml.parsers.DocumentBuilderFactory
import org.w3c.dom.Element
import java.io.File

fun main(){
	val doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse("diario_resumen.xml")
	val fOut = File("diario_resumen_regula.csv")
    val periodes = doc.getElementsByTagName("Periodo")
	println(periodes.getLength())
	for (i in 0..periodes.getLength()-1){
		val p = periodes.item(i) as Element
		if (p.getElementsByTagName("NombrePeriodo").item(0).getTextContent() == "REGULARIZACION 2020"){
			println("L'hem trobat")
			val linies = p.getElementsByTagName("Linea")
			for (j in 0..linies.getLength()-1){
				val l = linies.item(j) as Element
				var linia = ""
				linia += l.getElementsByTagName("Cuenta").item(0).getTextContent() + ";"
				linia += l.getElementsByTagName("Descrip").item(0).getTextContent() + ";"
				linia += l.getElementsByTagName("Debe").item(0).getTextContent() + ";"
				linia += l.getElementsByTagName("Haber").item(0).getTextContent() + "\n"
				println(linia)
				fOut.appendText(linia)
			}
		}
	}
}