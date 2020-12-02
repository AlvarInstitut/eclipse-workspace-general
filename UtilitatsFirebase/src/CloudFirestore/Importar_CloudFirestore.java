package CloudFirestore;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import com.google.api.core.ListenableFutureToApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

public class Importar_CloudFirestore {

	public static void main(String[] args) throws IOException, JsonException, InterruptedException {
		// TODO Auto-generated method stub
		FileInputStream serviceAccount = new FileInputStream(
				"exadfirebase-firebase-adminsdk-wgvi4-9fb6b2831d.json");

		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.build();

		FirebaseApp.initializeApp(options);
        Firestore db = FirestoreClient.getFirestore();
		
		JsonArray arrel = (JsonArray) Jsoner.deserialize(new FileReader("CloudFirestore2.json"));

        for (int i=0; i<arrel.size();i++){
            JsonObject e = (JsonObject) arrel.get(i);
            System.out.println(e.get("nom"));
            System.out.println("\t" + e.get("contingut"));
			DocumentReference doc = db.document(e.get("nom").toString());
			CountDownLatch done = new CountDownLatch(1);
			 doc.set(e.get("contingut")).wait();
			
        }
	}

}
