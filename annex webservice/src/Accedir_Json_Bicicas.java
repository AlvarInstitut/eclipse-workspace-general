import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.DeserializationException;
import org.json.simple.JsonArray;
import org.json.simple.JsonObject;
import org.json.simple.Jsoner;

public class Accedir_Json_Bicicas {

    public static void main(String[] args) throws IOException, DeserializationException {
        URL url = new URL("https://gestiona.bicicas.es/apps/apps.php");
        URLConnection conn = url.openConnection();

        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String cadena = "";
        String linia;
        while ((linia = rd.readLine()) != null)
            cadena += linia;

        JsonArray array = (JsonArray) Jsoner.deserialize(cadena);
        JsonArray estacions = (JsonArray) ((JsonObject) array.get(0)).get("ocupacion");
        
        for (int i = 0; i < estacions.size(); i++) {
            JsonObject e = (JsonObject) estacions.get(i);
            System.out.println(e.get("id") + ".- " + e.get("punto") + " (" + e.get("ocupados") + "/" + e.get("puestos") + ")");
        }
    }

}