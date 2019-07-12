import java.util.Collection;

public interface ItemService {

    public boolean collectionExist(String collection);
    public Item[] getCollection(String key) throws CollectionException;
    public void setCollection(String key, Item[] collection);

    /** CRUD to items of collection **/
    public void addItemToCollection(String collection, Item item) throws ItemException, CollectionException;
    public Item getItemOfCollection(String collection, String id) throws ItemException, CollectionException;
    public void editItemOfCollection(String collection, String id, Item item) throws ItemException, CollectionException;
    public void deleteItemOfCollection(String collection, String id) throws ItemException, CollectionException;

}
