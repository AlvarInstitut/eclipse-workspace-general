package Proves

import java.io.File

fun main(args: Array<String>) {
	val f = File("prova.csv")
	val linies = f.readLines()
	linies.forEach {
		val camps = it.split(";")
		if (camps.get(5) != "0") {
			camps.forEach {
				print(it + "\t")
			}
			println()
		}
	}

}