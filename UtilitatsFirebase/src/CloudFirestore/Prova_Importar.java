package CloudFirestore;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

public class Prova_Importar {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		FileInputStream serviceAccount = new FileInputStream(
				"exadfirebase-firebase-adminsdk-wgvi4-9fb6b2831d.json");

		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.build();

		FirebaseApp.initializeApp(options);
        Firestore db = FirestoreClient.getFirestore();
		
		DocumentReference docXatProva = db.collection("alumnes").document("XatProva");

		Map<String, Object> dades = new HashMap<>();
		dades.put("Hola","Hola");
		dades.put("Prova","proveta");
		
		docXatProva.update(dades);
	}

}
