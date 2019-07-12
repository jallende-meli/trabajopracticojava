import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

public class Consummer {
    private static ItemServiceMapImpl itemsService = new ItemServiceMapImpl();

    private Item[] searchItemsOnApi(String item) throws Exception{
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

        return itemsArray;
    }

    public Item[] getCollectionFromStr(String collection, String order, String price_range, Boolean good_quality_thumbnail) throws Exception {
        Item[] items;
        if (itemsService.collectionExist(collection)) {
            items = itemsService.getCollection(collection);
        } else {
            items = this.searchItemsOnApi(collection);
            itemsService.setCollection(collection, items);
        }
        if (order != null) {
            items = this.orderCollection(items, order.toUpperCase());
        }
        if (price_range != null) {

            /** No es la forma mas sana para hacerlo pero es la unica que se me ocurrio **/
            /* TODO mejorar eso si o si */
            int min_price = Integer.parseInt(price_range.split("-")[0]);
            int max_price = Integer.parseInt(price_range.split("-")[1]);
            AtomicInteger index = new AtomicInteger();

            int count = (int) Arrays.stream(items)
                    .filter(i -> i.getPrice() > min_price && i.getPrice() < max_price)
                    .count();
            Item[] newItems = new Item[count];
            Arrays.stream(items)
                    .filter(i -> i.getPrice() > min_price && i.getPrice() < max_price)
                    .forEach(i -> {
                        newItems[index.get()] = i;
                        index.incrementAndGet();
                    });
            items = newItems;
        }

        if (good_quality_thumbnail) {

            AtomicInteger index = new AtomicInteger();

            int count = (int) Arrays.stream(items)
                    .filter(i -> Arrays.asList(i.getTags()).contains("good_quality_thumbnail"))
                    .count();
            Item[] newItems = new Item[count];
            Arrays.stream(items)
                    .filter(i -> Arrays.asList(i.getTags()).contains("good_quality_thumbnail"))
                    .forEach(i -> {
                        newItems[index.get()] = i;
                        index.incrementAndGet();
                    });
            items = newItems;
        }
        return items;
    }

    public String[] getTitlesOfCollectionFromStr(String collection, String order, String price_range, Boolean good_quality_thumbnail) throws Exception {
        Item[] items = this.getCollectionFromStr(collection, order, price_range, good_quality_thumbnail);
        String[] titles = new String[items.length];
        for (int i = 0; i < items.length; i++) {
            titles[i] = items[i].getTitle();
        }
        return titles;
    }

    public Item[] orderCollection(Item[] collection, String order) {
        Arrays.sort(collection, new Comparator<Item>() {
            @Override
            public int compare(Item i1, Item i2) {
                int result = 0;
                switch (order) {
                    case "PRICE_ASC":
                        return i1.getPrice().compareTo(i2.getPrice());
                    case "PRICE_DES":
                        return -(i1.getPrice().compareTo(i2.getPrice()));
                    case "LISTING_TYPE_ASC":
                        return i1.getListing_type_id().compareTo(i2.getListing_type_id());
                    case "LISTING_TYPE_DES":
                        return -(i1.getListing_type_id().compareTo(i2.getListing_type_id()));
                }
                return result;
            }
        });

        return collection;
    }
}
