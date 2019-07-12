import com.google.gson.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

public class Consummer {
    private static ItemServiceMapImpl itemsService;
    private static CurrencyService currencyService;
    private static Gson gson = new Gson();

    public Consummer (ItemServiceMapImpl itemsService, CurrencyService currencyService){
        this.itemsService = itemsService;
        this.currencyService = currencyService;
    }

    public Item getItemFromCollection(String collection, String id) throws ItemException, CollectionException{
      try {
          Item[] itemsCollection = getCollectionFromStr(collection, null, null, false);
          return Arrays.stream(itemsCollection)
                  .filter(s -> s.getId().equals(id))
                  .findFirst()
                  .get();
      } catch (Exception e) {
          /*TODO cambiar esto caundo el metodo getCollectionFromStr cambie por throw CollectionException */
          throw new CollectionException(e.getMessage());
      }
    }

    public Item[] getCollectionFromStr(String collection, String order, String price_range,
                                       Boolean good_quality_thumbnail) throws CollectionException, IOException {
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
            int min_price = Integer.parseInt(price_range.split("-")[0]);
            int max_price = Integer.parseInt(price_range.split("-")[1]);
            items = Arrays.stream(items)
                    .filter(i -> i.getPrice() > min_price && i.getPrice() < max_price).toArray(Item[]::new);
        }
        if (good_quality_thumbnail) {
            items = Arrays.stream(items)
                    .filter(i -> Arrays.asList(i.getTags()).contains("good_quality_thumbnail"))
                    .toArray(Item[]::new);

        }
        return items;
    };


    public String[] getTitlesOfCollectionFromStr(String collection, String order, String price_range,
                                                 Boolean good_quality_thumbnail) throws CollectionException,
            IOException {
        Item[] items = this.getCollectionFromStr(collection, order, price_range, good_quality_thumbnail);
        String[] titles = new String[items.length];
        for (int i = 0; i < items.length; i++) {
            titles[i] = items[i].getTitle();
        }
        return titles;
    }

    public void setCurrencies () {
        Currency[] currencies;
        try {
            String strCurrencies = readConnectionBufferFromUrl("https://api.mercadolibre.com/currencies");
            currencies = gson.fromJson(strCurrencies, Currency[].class);
            currencyService.setCurrencies(currencies);
        } catch (Exception e) {
            System.out.println("Exception getting currencies: "+ e);
        }

    }

    private Item[] searchItemsOnApi(String item) throws IOException, CollectionException{
        Item[] itemsArray;
        String urlString = readConnectionBufferFromUrl("https://api.mercadolibre.com/sites/MLA/search?q="+item);
        Gson gson = new Gson();
        JsonObject jobj = new Gson().fromJson(urlString, JsonObject.class);
        String resultsJsonAsText = jobj.get("results").toString();
        itemsArray = gson.fromJson(resultsJsonAsText, Item[].class);
        for (int i = 0; i<itemsArray.length; i++) {
            itemsArray[i].setCurrency(currencyService.getCurrencyById(itemsArray[i].getCurrency_id()));
        }
        return itemsArray;
    }

    private Item[] orderCollection(Item[] collection, String order) {
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

    private String readConnectionBufferFromUrl(String strUrl) throws IOException, CollectionException {
        URL url = new URL(strUrl);
        URLConnection urlConnection = url.openConnection();
        HttpURLConnection connection = null;
        if (urlConnection instanceof HttpURLConnection) {
            connection = (HttpURLConnection) urlConnection;
            connection.setRequestProperty("Accept", "application/json");
        } else {
            System.out.println("Url invalida");
            throw new CollectionException("url invalida para leer collection");
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String urlString = "";
        String current = null;
        while ((current = in.readLine()) != null) {
            urlString += current;
        }
        return urlString;
    }
}
