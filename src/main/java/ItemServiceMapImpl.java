import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class ItemServiceMapImpl implements ItemService {

    private HashMap<String, Item[]> itemsMap = new HashMap<>();

    public boolean collectionExist(String collection) {
        try{
            return itemsMap.containsKey(collection);
        } catch (NullPointerException E) {
            System.out.println(E);
            return false;
        }
    }

    public Item[] getCollection(String key) {
        return itemsMap.get(key);
    }

    public void setCollection(String key, Item[] collection) {
        itemsMap.put(key, collection);
    }

    public void addItemToCollection(String collection, Item item) {

    }

    public Item getItemOfCollection(String collection, String id) {
        return null;
    }

    public Item editItemOfCollection(String collection, Item integrante) throws Exception {
        return null;
    }

    public void deleteItemOfCollection(String collection, String id) {

    }
}
