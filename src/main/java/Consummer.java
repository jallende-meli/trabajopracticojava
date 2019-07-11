import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Consummer {
    private String id;

    public Collection<Item> searchItems(String item) throws Exception{

        Item[] itemsArray;
        URL url = new URL("https://api.mercadolibre.com/sites/MLA/search?q="+item);
        URLConnection urlConnection = url.openConnection();
        HttpURLConnection connection = null;
        if (urlConnection instanceof HttpURLConnection) {
            connection = (HttpURLConnection) urlConnection;
            connection.setRequestProperty("Accept", "application/json");
        } else {
            System.out.println("Url invalida");
            itemsArray = null;
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String urlString = "";
        String current = null;
        while ((current = in.readLine()) != null) {
            urlString += current;
        }

        Gson gson = new Gson();

        JsonObject jobj = new Gson().fromJson(urlString, JsonObject.class);
        String resultsJsonAsText = jobj.get("results").toString();
        itemsArray = gson.fromJson(resultsJsonAsText, Item[].class);
        System.out.println("paso por el consummer de search");
        return Arrays.asList(itemsArray);
    }
}
