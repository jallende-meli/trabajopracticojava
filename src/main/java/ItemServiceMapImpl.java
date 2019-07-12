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
            return false;
        }
    }

    public Item[] getCollection(String key) throws CollectionException {
        if (collectionExist(key)) {
            return itemsMap.get(key);
        } else {
            throw new CollectionException("La coleccion no existe");
        }
    }

    public void setCollection(String key, Item[] collection) {
        itemsMap.put(key, collection);
    }

    public void addItemToCollection(String collection, Item item) {
        Item[] newCollection;
        try {
            Item[] itemsCollection = this.getCollection(collection);
            newCollection = new Item[itemsCollection.length + 1];
            newCollection[newCollection.length - 1] = item;
            this.setCollection(collection, newCollection);
        } catch (CollectionException e) {
            newCollection = new Item[1];
            newCollection[0] = item;
            this.setCollection(collection, newCollection);
        }
    }

    public Item getItemOfCollection(String collection, String id) throws ItemException, CollectionException{
        Item item = null;
        if (collectionExist(collection)) {
            Item[] itemsCollection = getCollection(collection);
            item = Arrays.stream(itemsCollection)
                    .filter(s -> s.getId().equals(id))
                    .findFirst()
                    .get();
        } else {
            throw new CollectionException("La coleccion no existe");
        }
        return item;
    }

    public void editItemOfCollection(String collection, String id,  Item item) throws ItemException , CollectionException{
        if (this.collectionExist(collection)) {
            Item[] itemsCollection = this.getCollection(collection);
            for (int i = 0; i < itemsCollection.length; i++) {
                if( itemsCollection[i].getId().equals(id)) {
                    itemsCollection[i] = item;
                }
            }
            itemsMap.replace(collection, itemsCollection);
        } else {
            throw new CollectionException("La coleccion no existe");
        }
    }

    public void deleteItemOfCollection(String collection, String id) throws ItemException , CollectionException{
        if (this.collectionExist(collection)) {
            Item[] itemsCollection = this.getCollection(collection);
            Item[] newItemsCollection = Arrays.stream(itemsCollection)
                    .filter(s -> !s.getId().equals(id)).toArray(Item[]::new);
            if (newItemsCollection.length == itemsCollection.length) {
                throw (new ItemException("No se encontro elemento para borrar"));
            }
            this.setCollection(collection, newItemsCollection);
        } else {
            throw new CollectionException("La coleccion no existe");
        }

    }
}
