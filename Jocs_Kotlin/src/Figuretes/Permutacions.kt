package figuretes


fun main(args: Array<String>) {
	val v0 = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,14,15)
	perm(v0, 0)
}

fun perm(v: Array<Int>, ind: Int) {
	if (ind < v.size - 1) {
		for (i in ind..(v.size - 1)) {
			var v1 = v.copyOf()
			for (x in 0..v.size - 1)
				v1[x] = v[x]
			val t = v1[ind]
			v1[ind] = v1[i]
			v1[i] = t
			perm(v1, ind + 1)
		}
	} else {
		val s1 = v[0] + v[1] + v[2] + v[3] + v[4] + v[6]
		val s2 = v[1] + v[3] + v[5] + v[6] + v[8] + v[10]
		val s3 = v[2] + v[4] + v[6] + v[7] + v[9] + v[11]
		val s4 = v[6] + v[8] + v[9] + v[10] + v[11] + v[12]
		if (s1 == 40 && s2 == 40 && s3 == 40 && s4 == 40) {
			v.forEach { print("" + it + " ") }
			println()
		}
	}
}