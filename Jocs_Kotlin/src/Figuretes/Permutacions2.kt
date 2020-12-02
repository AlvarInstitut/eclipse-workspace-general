package figuretes


fun main(args: Array<String>) {
	val v0 = arrayOf(7, 2, 8, 4, 5, 6, 1, 3, 9, 10, 11, 12, 13, 14, 15)
	perm2(v0, 0)
}

fun perm2(v: Array<Int>, ind: Int) {
	if (ind < v.size - 1) {
		for (i in ind..(v.size - 1)) {
			var v1 = v.copyOf()
			for (x in 0..v.size - 1)
				v1[x] = v[x]
			val t = v1[ind]
			v1[ind] = v1[i]
			v1[i] = t
			perm2(v1, ind + 1)
		}
	} else {
		val s1 = v[0] + v[1] + v[2] + v[8] 
		val s2 = v[0] + v[2] + v[3] + v[9] 
		val s3 = v[0] + v[3] + v[4] + v[10] 
		val s4 = v[0] + v[4] + v[5] + v[11] 
		val s5 = v[0] + v[5] + v[6] + v[12] 
		val s6 = v[0] + v[6] + v[7] + v[13] 
		val s7 = v[0] + v[7] + v[1] + v[14] 
		if (s1 == 30 && s2 == 30 && s3 == 30 && s4 == 30 && s5 == 30 && s6 == 30 && s7 == 30) {
			v.forEach { print("" + it + " ") }
			println(" ***************")
		}
		/*else{
			v.forEach { print("" + it + " ") }
			println()
		}*/
	}
}