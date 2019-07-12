import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.Collection;
import static spark.Spark.*;

public class ApiService {
    private static Gson gson = new Gson();
    private static Consummer consummer = new Consummer();
    private static ItemServiceMapImpl itemsService = new ItemServiceMapImpl();

    public static void main(String[] args) throws Exception {

        consummer.setCurrencies();

        path("/collection", () -> {
            path("/:collection_name", () -> {
                get("", "application/json", (req, res) -> {
                    res.type("application/json");
                    String collection = req.params(":collection_name");
                    String order = req.queryParams("order");
                    String price_range = req.queryParams("price_range");
                    /* TODO  se puede cambiar en bool por una serie de tags para generalizar preguntar*/
                    Boolean good_quality_thumbnail = Boolean.parseBoolean(req.queryParams("good_quality_thumbnail"));
                    Item[] items = consummer.getCollectionFromStr(collection, order,
                            price_range, good_quality_thumbnail);
                    return gson.toJson(items);
                });
                get("/titles", "application/json", (req, res) -> {
                    res.type("application/json");
                    String collection = req.params(":collection_name");
                    String order = req.queryParams("order");
                    String price_range = req.queryParams("price_range");
                    Boolean good_quality_thumbnail = Boolean.parseBoolean(req.queryParams("good_quality_thumbnail"));
                    String[] items = consummer.getTitlesOfCollectionFromStr(collection, order,
                            price_range, good_quality_thumbnail);
                    return gson.toJson(items);
                });
            });

            path("/:collection_name/:id", () -> {
                get("", "application/json", (req, res) -> {
                    res.type("application/json");
                    String collection = req.params(":collection_name");
                    String id = req.params(":id");
                    try {
                        System.out.println("ACA ESTOY");
                        Item item = consummer.getItemFromCollection(collection, id);
                        return gson.toJson(item);
                    } catch (ItemException e) {
                        return e;
                    } catch (CollectionException e) {
                        return e;
                    }
                });
                post("", "application/json", (req, res) -> {


                    return 0;
                });
            });
        });
    }
}
