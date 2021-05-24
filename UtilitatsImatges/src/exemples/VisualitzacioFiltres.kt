package exemples

import javax.swing.*
import java.awt.*
import javax.imageio.ImageIO
import java.io.File
import java.awt.image.BufferedImage

class Finestra : JFrame() {

	init {
		defaultCloseOperation = JFrame.EXIT_ON_CLOSE
		setTitle("Visualització de Filtres")
		setSize(400, 300)
		setLayout(FlowLayout())
		val img = ImageIO.read(File("Animal.png"))

		val boto1 = JButton(ImageIcon(img))
		val panell1 = JPanel(GridLayout(3, 3))
		val f = Array(3){Array(3){JTextField(2)}}
		for (i in 0..2)
			for(j in 0..2){
				f[i][j].setHorizontalAlignment(JTextField.CENTER)
				panell1.add(f[i][j])
			}

		val panell0 = JPanel(BorderLayout())
		panell0.add(panell1, BorderLayout.CENTER)
		val apl = JButton("Aplicar")
		panell0.add(apl, BorderLayout.SOUTH)
		val img2 = BufferedImage(img.getWidth() - 2, img.getHeight() - 2, BufferedImage.TYPE_INT_RGB)
		val boto2 = JButton(ImageIcon(img2))
		add(boto1)
		add(panell0)
		add(boto2)

		apl.addActionListener {
			for (i in 0 until img.getWidth() - 2)
				for (j in 0 until img.getHeight() - 2) {
					var t = 0.0
					for (ii in 0..2)
						for (jj in 0..2) {
							val c = descompRGB(img.getRGB(i + ii, j + jj))
							t += img.getRGB(i + ii, j + jj) * f[ii][jj].getText().toDouble()
						}
					img2.setRGB(i, j, t.toInt())
				}
			boto2.repaint()
		}

	}
}

fun main(args: Array<String>) {
	EventQueue.invokeLater {
		Finestra().isVisible = true
	}
}

