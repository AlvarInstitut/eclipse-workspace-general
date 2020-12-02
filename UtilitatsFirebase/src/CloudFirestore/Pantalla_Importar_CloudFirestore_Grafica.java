package CloudFirestore;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Pantalla_Importar_CloudFirestore_Grafica extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	JLabel etUltimMissatge= new JLabel("Últim missatge: ");
	JLabel ultimMissatge= new JLabel();
	
	JLabel etiqueta = new JLabel("Missatges:");
	JTextArea area = new JTextArea();

	JLabel etIntroduccioMissatge = new JLabel("Introdueix missatge:");
	JButton enviar = new JButton("Enviar");
	JTextField missatge = new JTextField(15);

	// en iniciar posem un contenidor per als elements anteriors
	public void iniciar() throws IOException {
		JFrame pantPrincipal = this;
		this.setBounds(100, 100, 450, 300);
		this.setLayout(new BorderLayout());
		// contenidor per als elements
		//Hi haurà títol. Panell de dalt: últim missatge. Panell de baix: per a introduir missatge. Panell central: tot el xat
		
		JPanel panell1 = new JPanel(new FlowLayout());
		panell1.add(etUltimMissatge);
		panell1.add(ultimMissatge);
		getContentPane().add(panell1, BorderLayout.NORTH);

		JPanel panell2 = new JPanel(new BorderLayout());
		panell2.add(etiqueta, BorderLayout.NORTH);
		area.setForeground(Color.blue);
		JScrollPane scroll = new JScrollPane(area);
		panell2.add(scroll, BorderLayout.CENTER);
		getContentPane().add(panell2, BorderLayout.CENTER);
		
		JPanel panell3 = new JPanel(new FlowLayout());
		panell3.add(etIntroduccioMissatge);
		panell3.add(missatge);
		panell3.add(enviar);
		getContentPane().add(panell3, BorderLayout.SOUTH);

		setVisible(true);
		enviar.addActionListener(this);

		FileInputStream serviceAccount = new FileInputStream(
				"exadfirebase-firebase-adminsdk-wgvi4-9fb6b2831d.json");

		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.build();
		FirebaseApp.initializeApp(options);

		// Exemple de listener de lectura única addListenerForSingleValue()
		// Per a posar el títol. Sobre nomXat
		
		// Exemple de listener de lectura contínua addValueEventListener()
		// Per a posar l'últim missatge registrat. Sobre a1
		
		// Exemple de listener d'una llista addChildEventListener()
		// Per a posar tota la llista de missatges. Sobre xat

	}

	@Override


public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == enviar) {
			 Firestore db = FirestoreClient.getFirestore();
			JsonArray arrel;
			try {
				arrel = (JsonArray) Jsoner.deserialize(new FileReader("CloudFirestore2.json"));
				for (int i=0; i<arrel.size();i++){
		            JsonObject e = (JsonObject) arrel.get(i);
		            System.out.println(e.get("nom"));
		            System.out.println("\t" + e.get("contingut"));
					DocumentReference doc = db.document(e.get("nom").toString());
					doc.set(e.get("contingut"));
					
		        }
			} catch (FileNotFoundException | JsonException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

	        
		}
	}

}

