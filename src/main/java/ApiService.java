import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.Collection;
import static spark.Spark.*;

public class ApiService {
    private static Gson gson = new Gson();
    private static ItemServiceMapImpl itemsService = new ItemServiceMapImpl();
    private static CurrencyService currencyService = new CurrencyService();
    private static Consummer consummer = new Consummer(itemsService, currencyService);

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
                    try {
                        Item[] items = consummer.getCollectionFromStr(collection, order,
                                price_range, good_quality_thumbnail);

                        return gson.toJson(new StandardResponse(StatusResponse.SUCCESS,
                                new JsonParser().parse(gson.toJson(items))));
                    } catch (CollectionException e) {
                        return gson.toJson(new StandardResponse(StatusResponse.ERROR, e.getMessage()));
                    } catch (IOException e) {
                        return gson.toJson(new StandardResponse(StatusResponse.ERROR, e.getMessage()));
                    }

                });
                post("", "application/json", (req, res) -> {
                    String collection = req.params(":collection_name");
                    Item item = gson.fromJson(req.body(), Item.class);
                    item.setCurrency(currencyService.getCurrencyById(item.getCurrency_id()));
                    itemsService.addItemToCollection(collection, item);
                    return gson.toJson(new StandardResponse(StatusResponse.SUCCESS,
                            new JsonParser().parse(gson.toJson(item))));
                });
                get("/titles", "application/json", (req, res) -> {
                    res.type("application/json");
                    String collection = req.params(":collection_name");
                    String order = req.queryParams("order");
                    String price_range = req.queryParams("price_range");
                    Boolean good_quality_thumbnail = Boolean.parseBoolean(req.queryParams("good_quality_thumbnail"));
                    try {
                        String[] items = consummer.getTitlesOfCollectionFromStr(collection, order,
                                price_range, good_quality_thumbnail);
                        return gson.toJson(new StandardResponse(StatusResponse.SUCCESS,
                                new JsonParser().parse(gson.toJson(items))));
                    } catch (CollectionException e) {
                        return gson.toJson(new StandardResponse(StatusResponse.ERROR, e.getMessage()));
                    }
                });
            });

            path("/:collection_name/:id", () -> {
                get("", "application/json", (req, res) -> {
                    res.type("application/json");
                    String collection = req.params(":collection_name");
                    String id = req.params(":id");
                    try {
                        Item item = consummer.getItemFromCollection(collection, id);
                        return gson.toJson(new StandardResponse(StatusResponse.SUCCESS,
                                new JsonParser().parse(gson.toJson(item))));
                    } catch (ItemException e) {
                        return gson.toJson(new StandardResponse(StatusResponse.ERROR, e.getMessage()));
                    } catch (CollectionException e) {
                        return gson.toJson(new StandardResponse(StatusResponse.ERROR, e.getMessage()));
                    }
                });
                post("", "application/json", (req, res) -> {
                    String collection = req.params(":collection_name");
                    String id = req.params(":id");
                    Item item = gson.fromJson(req.body(), Item.class);
                    item.setCurrency(currencyService.getCurrencyById(item.getCurrency_id()));
                    try {
                        itemsService.editItemOfCollection(collection,id, item);
                        return gson.toJson(new StandardResponse(StatusResponse.SUCCESS,
                                new JsonParser().parse(gson.toJson(item))));
                    } catch (ItemException e) {
                        return gson.toJson(new StandardResponse(StatusResponse.ERROR, e.getMessage()));
                    } catch (CollectionException e) {
                        return gson.toJson(new StandardResponse(StatusResponse.ERROR, e.getMessage()));
                    }
                });
                delete("", "application/json", (req, res) -> {
                    String collection = req.params(":collection_name");
                    String id = req.params(":id");
                    try {
                        itemsService.deleteItemOfCollection(collection, id);
                        return gson.toJson(new StandardResponse(StatusResponse.SUCCESS));
                    } catch (CollectionException e) {
                        return gson.toJson(new StandardResponse(StatusResponse.ERROR, e.getMessage()));
                    }
                });
            });
        });
    }
}
