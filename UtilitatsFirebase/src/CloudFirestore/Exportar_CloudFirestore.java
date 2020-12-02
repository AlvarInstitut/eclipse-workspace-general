package CloudFirestore;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;


public class Exportar_CloudFirestore {
	static JsonArray llistaDocs = new JsonArray();


	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
		FileInputStream serviceAccount = new FileInputStream(
				"acces-a-dades-6e5a6-firebase-adminsdk-ei7uc-8f5d926921.json");

		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setDatabaseUrl("https://acces-a-dades-6e5a6.firebaseio.com").build();

		FirebaseApp.initializeApp(options);

		Firestore db = FirestoreClient.getFirestore();

		// Obtenir totes les col·leccions
		Iterable<CollectionReference> cols = db.listCollections();
		System.out.println("Col·leccions");
		for (CollectionReference c : cols) {
			obtenirColeccio(c);
		}
		
		FileWriter f = new FileWriter("CloudFirestore.json");
        f.write(llistaDocs.toJson());
        f.close();
		
	}
	
	private static void obtenirColeccio(CollectionReference c) throws InterruptedException, ExecutionException {
		System.out.println(c.getPath());
		ApiFuture<QuerySnapshot> future = c.get();
		// future.get() blocks on response
		List<QueryDocumentSnapshot> documents = future.get().getDocuments();
		for (QueryDocumentSnapshot document : documents) {
			obtenirDocument(document);
		}
		
	}

	private static void obtenirDocument(QueryDocumentSnapshot document) throws InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
		System.out.println(document.getReference().getPath());
		// asynchronously retrieve the document
		ApiFuture<DocumentSnapshot> future2 = document.getReference().get();
		// ...
		// future.get() blocks on response
		DocumentSnapshot document2 = future2.get();
		if (document2.exists()) {
			System.out.println("\t" + document2.getData());
			JsonObject m = new JsonObject();
			m.put("nom",document2.getReference().getPath());
			m.put("contingut",document2.getData());
			llistaDocs.add(m);
			Iterable<CollectionReference> subCols = document2.getReference().listCollections();
			if (subCols != null)
				//System.out.println("\t\tSubcol·leccions");
			for (CollectionReference c2 : subCols) {
				obtenirColeccio(c2);
			}
		} else {
			System.out.println("No such document!");
		}
		
	}
}
