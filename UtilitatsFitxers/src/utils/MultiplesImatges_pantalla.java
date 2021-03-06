package utils;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.TransferHandler;
import javax.swing.WindowConstants;

import javax.swing.JTable;

public class MultiplesImatges_pantalla extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	static int numCols = 5;
	static int numFiles = 2;
	static int numDestins = 4;

	JButton allMemoria = new JButton("Alliberar memòria");
	JButton dirActiu = new JButton("Canviar el directori d'imatges");
	JButton dirMoure = new JButton("Canviar el directori destí general");
	JButton[] dirMoure1 = new JButton[numDestins];

	JButton[][] imatges = new JButton[numFiles][numCols];
	JButton anterior10000 = new JButton("<<10000");
	JButton anterior1000 = new JButton("<<1000");
	JButton anterior = new JButton("<<");
	JButton esbTotes = new JButton("Esborrar-les totes");
	JButton moureTotes = new JButton("Moure-les totes");
	JButton esbSel = new JButton("Esborrar seleccionades");
	JButton moureSel = new JButton("Moure seleccionades");
	JButton seguent = new JButton(">>");
	JButton seguent1000 = new JButton("1000>>");
	JButton seguent10000 = new JButton("10000>>");

	File directori;
	File[] filesDirectori;
	File[] imatgesSel = new File[numCols * numFiles];
	Boolean[] imatgesSeleccionades = new Boolean[numCols * numFiles];
	int indexFile;

	int ampleBoto;
	int altBoto;

	JFileChooser fc = new JFileChooser();
	JFileChooser fcDesti = new JFileChooser();
	JFileChooser[] fcDesti1 = new JFileChooser[numDestins];
	// quantes imatges estem veirent
	int q;
	
	final JFrame pantallaPrincipal = this;
	
	Runtime garbage = Runtime.getRuntime();


	// en iniciar posem un contenidor per als elements anteriors
	public void iniciar() throws IOException {
		getContentPane().setLayout(new BorderLayout());
		JPanel p_prin = new JPanel();
		p_prin.setLayout(new GridLayout(numFiles, numCols));
		// contenidor per als elements
		JPanel p_botonsDalt = new JPanel(new FlowLayout());
		JPanel p_botons = new JPanel(new FlowLayout());
		getContentPane().add(p_botonsDalt, BorderLayout.NORTH);
		getContentPane().add(p_prin, BorderLayout.CENTER);
		getContentPane().add(p_botons, BorderLayout.SOUTH);
		p_botonsDalt.add(allMemoria);
		p_botonsDalt.add(dirActiu);
		p_botonsDalt.add(dirMoure);
		allMemoria.addActionListener(this);
		dirActiu.addActionListener(this);
		dirMoure.addActionListener(this);
		for (int i = 0; i < numDestins; i++) {
			final int numBotoDesti = i;
			dirMoure1[numBotoDesti] = new JButton("Destí " + numBotoDesti);
			p_botonsDalt.add(dirMoure1[numBotoDesti]);
			fcDesti1[numBotoDesti] = new JFileChooser();
			dirMoure1[numBotoDesti].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if (fcDesti1[numBotoDesti].getSelectedFile() == null) {
						// la primera vegada triem el directori i el mostrem
						fcDesti1[numBotoDesti].setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						int r = fcDesti1[numBotoDesti].showOpenDialog(pantallaPrincipal);
						if (r == JFileChooser.APPROVE_OPTION) {
							dirMoure1[numBotoDesti].setText(fcDesti1[numBotoDesti].getSelectedFile().getName());
							System.out.println(
									"Fitxer seleccionat: " + fcDesti1[numBotoDesti].getSelectedFile().getName());
						} else
							System.out.println("No s'ha seleccionat res");

					} else {
						System.out.println("Anem a moure-les a: " + fcDesti1[numBotoDesti].getSelectedFile());
						System.out.println("d'un total de " + q);
						for (int i = 0; i < q; i++)
							if (imatgesSeleccionades[i] && imatgesSel[i].getName() != null) {
								System.out.println("Moure " + imatgesSel[i].getName() + " a " + fcDesti1[numBotoDesti].getSelectedFile());
								imatgesSel[i].renameTo(
										new File(fcDesti1[numBotoDesti].getSelectedFile(), imatgesSel[i].getName()));
							}
						filesDirectori = directori.listFiles();
						System.out.println(directori.getName() + ": Comencem");
						q = agafarImatges(indexFile);
						System.out.println("Num imatges " + q);
						try {
							visImatges(q);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						llevarSeleccio();
						garbage.gc();
						pantallaPrincipal.setTitle(garbage.freeMemory()+"");
					}
				}
			});
		}

		p_botons.add(anterior10000);
		p_botons.add(anterior1000);
		p_botons.add(anterior);
		p_botons.add(esbTotes);
		p_botons.add(moureTotes);
		p_botons.add(esbSel);
		p_botons.add(moureSel);
		p_botons.add(seguent);
		p_botons.add(seguent1000);
		p_botons.add(seguent10000);
		anterior10000.addActionListener(this);
		anterior1000.addActionListener(this);
		anterior.addActionListener(this);
		esbTotes.addActionListener(this);
		moureTotes.addActionListener(this);
		esbSel.addActionListener(this);
		moureSel.addActionListener(this);
		seguent.addActionListener(this);
		seguent1000.addActionListener(this);
		seguent10000.addActionListener(this);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(screenSize.width, screenSize.height);
		setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		// System.out.println("Ample total:" + screenSize.width);
		// System.out.println("Alt total:" + screenSize.height);

		int ampleTotal = p_prin.getWidth();
		int altTotal = p_prin.getHeight();
		ampleBoto = ampleTotal / numCols;
		altBoto = altTotal / numFiles;
		// System.out.println("Ample Pannell:" + ampleTotal);
		// System.out.println("Alt Panell:" + altTotal);
		// System.out.println("Ample botó:" + ampleBoto);
		// System.out.println("Alt botó:" + altBoto);
		for (int i = 0; i < numFiles; i++)
			for (int j = 0; j < numCols; j++) {
				imatges[i][j] = new JButton();
				// imatges[i][j].setDragEnabled(true); açò és per si faig drag and drop
				// imatges[i][j].setText(""+(i*numCols+j));
				final int num = i * numCols + j;
				// imatges[i][j].setSize(screenSize.width/5, screenSize.height/2);
				p_prin.add(imatges[i][j]);
				imatges[i][j].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						try {
							accioBotoImatge(num);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				imatges[i][j].addMouseListener(new MouseListener() {
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						if (e.getButton() == MouseEvent.BUTTON3) {
							seleccionar(num);
						}

					}

					public void mousePressed(MouseEvent e) {
					}

					public void mouseReleased(MouseEvent e) {
					}

					public void mouseEntered(MouseEvent e) {
					}

					public void mouseExited(MouseEvent e) {
					}
				});
			}

		directori = triar_directori();
		indexFile = 0;

		filesDirectori = directori.listFiles();
		System.out.println(directori.getName() + ": Comencem (" + filesDirectori.length + " fitxers)");
		q = agafarImatges(indexFile);
		System.out.println("Num imatges " + q);
		visImatges(q);

		System.out.println("Directori triat: " + directori.getAbsolutePath());

		Arrays.fill(imatgesSeleccionades, false);
		// pack();

	}

	private void visImatges(int quantes) throws IOException {
		// TODO Auto-generated method stub
		garbage.gc();
		pantallaPrincipal.setTitle(garbage.freeMemory()+"");

		int num = 0;

		for (int i = 0; i < numFiles; i++) {
			for (int j = 0; j < numCols; j++) {
				num = i * numCols + j;
				if (num < quantes) {
					System.out.println("visualitzem la número " + num);
					System.out.println(imatgesSel[num].getName());
					BufferedImage img = ImageIO.read(imatgesSel[num]);
					int ampleImg = img.getWidth();
					int altImg = img.getHeight();
					// System.out.println("Ample img:" + ampleImg);
					// System.out.println("Alt img:" + altImg);

					float relAmple = (float) (ampleBoto) / ampleImg;
					float relAlt = (float) (altBoto) / altImg;
					// System.out.println("Ample relació:" + relAmple);
					// System.out.println("Alt relació:" + relAlt);

					if (relAmple > relAlt) {
						Image scaled = img;
						scaled = scaled.getScaledInstance(Math.round(ampleImg * relAlt), altBoto, Image.SCALE_DEFAULT);
						imatges[i][j].setIcon(new ImageIcon(scaled));
						scaled = null;
					} else {
						Image scaled = img;
						scaled = scaled.getScaledInstance(ampleBoto, Math.round(altImg * relAmple),
								Image.SCALE_DEFAULT);
						imatges[i][j].setIcon(new ImageIcon(scaled));
						scaled = null;
					}
					img = null;
				} else
					imatges[i][j].setIcon(null);
			}
		}
		garbage.gc();
		pantallaPrincipal.setTitle(garbage.freeMemory()+"");

	}

	private int agafarImatges(int index) {
		// TODO Auto-generated method stub
		garbage.gc();
		pantallaPrincipal.setTitle(garbage.freeMemory()+"");
		int compt = index;
		int num = 0;
		System.out.println("Comencem pel " + compt);
		while (num < (numFiles * numCols) && compt < filesDirectori.length) {
			imatgesSel[num] = null;
			String nom = filesDirectori[compt].getName();
			System.out.println(nom);
			if (nom.toLowerCase().contains(".png") || nom.toLowerCase().contains(".jpg")
					|| nom.toLowerCase().contains(".jpeg") || nom.toLowerCase().contains(".bmp")) {
				imatgesSel[num] = filesDirectori[compt];
				num++;
			}
			compt++;
		}
		garbage.gc();
		pantallaPrincipal.setTitle(garbage.freeMemory()+"");
		return (num);
	}

	private File triar_directori() {
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int r = fc.showOpenDialog(this);
		if (r == JFileChooser.APPROVE_OPTION) {
			System.out.println("Fitxer seleccionat: " + fc.getSelectedFile().getName());
		} else
			System.out.println("No s'ha seleccionat res");
		garbage.gc();
		pantallaPrincipal.setTitle(garbage.freeMemory()+"");

		return fc.getSelectedFile();
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == allMemoria) {
			garbage.gc();
			pantallaPrincipal.setTitle(garbage.freeMemory()+"");
		}
		
		if (e.getSource() == dirActiu) {
			directori = triar_directori();
			indexFile = 0;
			filesDirectori = directori.listFiles();
			System.out.println(directori.getName() + ": Comencem");
			q = agafarImatges(indexFile);
			System.out.println("Num imatges " + q);
			try {
				visImatges(q);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			System.out.println("Directori triat: " + directori.getAbsolutePath());

		}

		if (e.getSource() == dirMoure) {
			fcDesti.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int r = fcDesti.showOpenDialog(this);
			if (r == JFileChooser.APPROVE_OPTION) {
				System.out.println("Fitxer seleccionat: " + fc.getSelectedFile().getName());
			} else
				System.out.println("No s'ha seleccionat res");
		}

		if (e.getSource() == esbTotes) {
			for (int i = 0; i < q; i++)
				if (imatgesSel[i].getName() != null) {
					System.out.println("Esborrar " + imatgesSel[i].getName());
					imatgesSel[i].delete();
				}
			filesDirectori = directori.listFiles();
			System.out.println(directori.getName() + ": Comencem");
			q = agafarImatges(indexFile);
			System.out.println("Num imatges " + q);
			try {
				visImatges(q);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			llevarSeleccio();
		}

		if (e.getSource() == esbSel) {
			for (int i = 0; i < q; i++)
				if (imatgesSeleccionades[i] && imatgesSel[i].getName() != null) {
					System.out.println("Esborrar " + imatgesSel[i].getName());
					imatgesSel[i].delete();
				}
			filesDirectori = directori.listFiles();
			System.out.println(directori.getName() + ": Comencem");
			q = agafarImatges(indexFile);
			System.out.println("Num imatges " + q);
			try {
				visImatges(q);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			llevarSeleccio();
		}

		if (e.getSource() == moureTotes) {
			if (fcDesti.getSelectedFile() == null)
				JOptionPane.showMessageDialog(this, "No s'ha triat cap directori de destí", "Error",
						JOptionPane.ERROR_MESSAGE);
			else {
				System.out.println("Anem a moure-les a: " + fcDesti.getSelectedFile());
				for (int i = 0; i < q; i++)
					if (imatgesSel[i].getName() != null) {
						System.out.println("Moure " + imatgesSel[i].getName() + " a " + fcDesti.getSelectedFile());
						imatgesSel[i].renameTo(new File(fcDesti.getSelectedFile(), imatgesSel[i].getName()));
					}

			}
			filesDirectori = directori.listFiles();
			System.out.println(directori.getName() + ": Comencem");
			q = agafarImatges(indexFile);
			System.out.println("Num imatges " + q);
			try {
				visImatges(q);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			llevarSeleccio();
		}

		if (e.getSource() == moureSel) {
			if (fcDesti.getSelectedFile() == null)
				JOptionPane.showMessageDialog(this, "No s'ha triat cap directori de destí", "Error",
						JOptionPane.ERROR_MESSAGE);
			else {
				System.out.println("Anem a moure-les a: " + fcDesti.getSelectedFile());
				System.out.println("d'un total de " + q);
				for (int i = 0; i < q; i++)
					if (imatgesSeleccionades[i] && imatgesSel[i].getName() != null) {
						System.out.println("Moure " + imatgesSel[i].getName() + " a " + fcDesti.getSelectedFile());
						imatgesSel[i].renameTo(new File(fcDesti.getSelectedFile(), imatgesSel[i].getName()));
					}

			}
			filesDirectori = directori.listFiles();
			System.out.println(directori.getName() + ": Comencem");
			q = agafarImatges(indexFile);
			System.out.println("Num imatges " + q);
			try {
				visImatges(q);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			llevarSeleccio();
		}

		if (e.getSource() == anterior10000) {
			indexFile -= 10000;
			if (indexFile < 0)
				indexFile = 0;
			q = agafarImatges(indexFile);
			System.out.println("Num imatges " + q);
			try {
				visImatges(q);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			llevarSeleccio();
		}

		if (e.getSource() == anterior1000) {
			indexFile -= 1000;
			if (indexFile < 0)
				indexFile = 0;
			q = agafarImatges(indexFile);
			System.out.println("Num imatges " + q);
			try {
				visImatges(q);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			llevarSeleccio();
		}

		if (e.getSource() == anterior) {
			indexFile -= numFiles * numCols;
			if (indexFile < 0)
				indexFile = 0;
			q = agafarImatges(indexFile);
			System.out.println("Num imatges " + q);
			try {
				visImatges(q);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			llevarSeleccio();
		}

		if (e.getSource() == seguent) {
			indexFile += numFiles * numCols;
			if (indexFile > (filesDirectori.length - numFiles * numCols))
				indexFile = (filesDirectori.length - numFiles * numCols);
			q = agafarImatges(indexFile);
			System.out.println("Num imatges " + q);
			try {
				visImatges(q);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			llevarSeleccio();
		}

		if (e.getSource() == seguent1000) {
			indexFile += 1000;
			if (indexFile > (filesDirectori.length - numFiles * numCols))
				indexFile = (filesDirectori.length - numFiles * numCols);
			q = agafarImatges(indexFile);
			System.out.println("Num imatges " + q);
			try {
				visImatges(q);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			llevarSeleccio();

		}
		if (e.getSource() == seguent10000) {
			indexFile += 10000;
			if (indexFile > (filesDirectori.length - numFiles * numCols))
				indexFile = (filesDirectori.length - numFiles * numCols);
			q = agafarImatges(indexFile);
			System.out.println("Num imatges " + q);
			try {
				visImatges(q);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			llevarSeleccio();
		}
	}

	private void accioBotoImatge(int i) throws IOException {
		// TODO Auto-generated method stub
		garbage.gc();
		pantallaPrincipal.setTitle(garbage.freeMemory()+"");

		System.out.println(i);
		// imatgeSolta.iniciar(imatgesSel[i]);
		JFrame frame = new JFrame(imatgesSel[i].getName());
		JPanel container = new JPanel();
		JScrollPane scrPane = new JScrollPane(container);
		frame.add(scrPane);
		BufferedImage img = ImageIO.read(imatgesSel[i]);
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(img));
		frame.setSize(img.getWidth(), img.getHeight());
		container.add(label);
		frame.pack();
		frame.setVisible(true);
	}

	private void seleccionar(int num) {
		// TODO Auto-generated method stub
		imatgesSeleccionades[num] = !imatgesSeleccionades[num];
		int i = num / numCols;
		int j = num % numCols;
		if (imatgesSeleccionades[num])
			imatges[i][j].setBackground(Color.GREEN);
		else
			imatges[i][j].setBackground(null);
		garbage.gc();
		pantallaPrincipal.setTitle(garbage.freeMemory()+"");

	}

	private void llevarSeleccio() {
		for (int n = 0; n < numFiles * numCols; n++) {
			if (imatgesSeleccionades[n]) {
				imatgesSeleccionades[n] = false;
				int i = n / numCols;
				int j = n % numCols;
				imatges[i][j].setBackground(null);
			}
		}
		garbage.gc();
		pantallaPrincipal.setTitle(garbage.freeMemory()+"");

	}
}