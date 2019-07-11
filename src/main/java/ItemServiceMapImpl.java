import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class ItemServiceMapImpl implements ItemService {

    private HashMap<String, Item[]> itemsMap;
    public ItemServiceMapImpl () {
        itemsMap = new HashMap<>();
    }


    public Collection<Item> getCollection(String key) {
        List<Item> list = Arrays.asList(itemsMap.get(key));
        return list;
    }

    public void setCollection(String key, Collection<Item> collection) {
        Item[] items = (Item[]) collection.toArray();
        itemsMap.put(key, items);
    }


    public Collection<Item> getOrderItems(String order) {
        return null;
    }


    public void addItem(Item item) {

    }

    public Item getItem(String id) {
        return null;
    }

    public Item editItem(Item integrante) throws Exception {
        return null;
    }

    public void deleteItem(String id) {

    }

    public boolean itemExist(String search) {
        try{
            return itemsMap.containsKey(search);
        } catch (NullPointerException E) {
            System.out.println(E);
            return false;
        }
    }
}
