package figuretes

fun main(args: Array<String>) {
	var tauler = Array(4) { Array(4) { "" } }

	var figures = Array(16) { FiguraCol("", false) }
	var ind = 0
	for (i in sequenceOf("B", "R", "V", "G"))
		for (j in sequenceOf("R", "Q", "T", "C")) {
			figures[ind] = FiguraCol((i + j), false)
			ind++
		}

	for (f in figures)
		println(f.figura)

	if (colFig(tauler, figures, 0, 0)) {
		println("Trobat !!!!")
		for (fila in tauler) {
			for (col in fila)
				print(col + " ")
			println()
		}

	}
}

fun colFig(t: Array<Array<String>>, f: Array<FiguraCol>, x: Int, y: Int): Boolean {
	var ind = 0
	var trobat = false
	while (!trobat && ind < 16) {
		if (!f[ind].col) {
			if (benCol(t, x, y, f[ind].figura)) {
				println(ind)
				f[ind].col = true
				t[x][y] = f[ind].figura
				var y1 = y + 1
				var x1 = x
				if (y1 > 3) {
					y1 = 0
					x1++
				}
				if (x < 4) {
					if (colFig(t, f, x1, y1))
						trobat = true
					else {
						trobat = false
						f[ind].col = false
					}
				} else
					trobat = true

			}
		}
		ind++
	}
	if (ind==16)
		trobat=true
	return trobat

}

fun benCol(t: Array<Array<String>>, x: Int, y: Int, s: String): Boolean {
	var band = true
	for (i in 0..(x - 1))
		if (t[i][y][0] == s[0] || t[i][y][1] == s[1])
			band = false
	for (j in 0..(y - 1))
		if (t[x][j][0] == s[0] || t[x][j][1] == s[1])
			band = false

	return band
}

class FiguraCol(var figura: String, var col: Boolean)
