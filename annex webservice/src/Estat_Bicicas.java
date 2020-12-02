import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.DeserializationException;
import org.json.simple.JsonArray;
import org.json.simple.JsonObject;
import org.json.simple.Jsoner;

public class Estat_Bicicas {

    public static void main(String[] args) throws IOException, DeserializationException {
        URL url = new URL("http://localhost/rutes_2.php");
        URLConnection conn = url.openConnection();

        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String cadena = "";
        String linia;
        while ((linia = rd.readLine()) != null)
            cadena += linia;

        JsonArray array = (JsonArray) Jsoner.deserialize(cadena);
        
        for (int i = 0; i < array.size(); i++) {
            JsonObject o = (JsonObject) array.get(i);
            System.out.println("Ruta " + o.get("num_r") + ": " + o.get("nom_r")
                    + ". Desnivell: " + o.get("desn")
                    + ". Desnivell Acumulat: " + o.get("desn_acum"));
        }
    }

}