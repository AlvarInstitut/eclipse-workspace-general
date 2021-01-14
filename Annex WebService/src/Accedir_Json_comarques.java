import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.DeserializationException;
import org.json.simple.JsonArray;
import org.json.simple.JsonObject;
import org.json.simple.Jsoner;

public class Accedir_Json_comarques {

    public static void main(String[] args) throws IOException, DeserializationException {
        URL url = new URL("http://192.168.191.104/comarca_2018.php");
        URLConnection conn = url.openConnection();

        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String cadena = "";
        String linia;
        while ((linia = rd.readLine()) != null)
            cadena += linia;

        JsonArray array = (JsonArray) Jsoner.deserialize(cadena);
        
        for (int i = 0; i < array.size(); i++) {
            JsonObject e = (JsonObject) array.get(i);
            System.out.println(e.get("nom") + " (" + e.get("provincia") + ")");
        }
    }

}