import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.List;

import static spark.Spark.*;

public class ApiService {
    public static void main(String[] args) throws Exception {
        ItemServiceMapImpl itemsService = new ItemServiceMapImpl();
        path("collection", () ->{
            get("/search", "application/json", (req, res) -> {
                res.type("application/json");
                String item = req.queryParams("item");
                Collection<Item> items;
                if (itemsService.itemExist(item)) {
                    System.out.println("ESTOY ACA IF");
                    items = itemsService.getCollection(item);
                } else {
                    items = new Consummer().searchItems(item);
                    System.out.println("ESTOY ACA");
                    itemsService.setCollection(item, items);
                }
                return items.toString();
            });
        });
    }
}
