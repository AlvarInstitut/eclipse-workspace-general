package exemples

import javax.imageio.ImageIO
import java.io.File
import java.awt.Color
import java.awt.image.BufferedImage

fun main() {
	val img = ImageIO.read(File("Animal.png"))
	val filtre = listOf(listOf(-1, -1, -1), listOf(-1, 8, -1), listOf(-1, -1, -1))
	println("" + img.getWidth() + "x" + img.getHeight())
	val img2 = BufferedImage(img.getWidth() - 2, img.getHeight() - 2, BufferedImage.TYPE_INT_RGB)
	for (i in 0 until img.getWidth() - 2)
		for (j in 0 until img.getHeight() - 2) {
			var t = 0.0
			for (ii in 0..2)
				for (jj in 0..2) {
					val c = descompRGB(img.getRGB(i + ii, j + jj))
					t += img.getRGB(i + ii, j + jj)*filtre[ii][jj]
				}
			img2.setRGB(i, j, t.toInt())
		}
	ImageIO.write(img2, "bmp", File("A2.bmp"))
	
	//Reducció
	val img3 = BufferedImage(img2.getWidth() / 2, img2.getHeight() / 2, BufferedImage.TYPE_INT_RGB)
	println("" + img3.getWidth() + "x" + img3.getHeight())
	for (i in 0 until img3.getWidth()){
		println(i)
		for (j in 0 until img3.getHeight()) {
			img3.setRGB(i, j, listOf(img2.getRGB(2*i,2*j),img2.getRGB(2*i,2*j+1),img2.getRGB(2*i+1,2*j),img2.getRGB(i+1,j+1)).max()!!)
		}
	}
	ImageIO.write(img3, "bmp", File("A2_red.bmp"))
	
	
}

fun descompRGB(c: Int): List<Int> {
	val color = Color(c)
	val b = color.getBlue()
	val g = color.getGreen()
	val r = color.getRed()
	return listOf(r, g, b)
}