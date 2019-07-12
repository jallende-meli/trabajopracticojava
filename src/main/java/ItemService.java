import java.util.Collection;

public interface ItemService {

    public boolean collectionExist(String collection);
    public Item[] getCollection(String key);
    public void setCollection(String key, Item[] collection);

    /** CRUD to items of collection **/
    public void addItemToCollection(String collection, Item item);
    public Item getItemOfCollection(String collection, String id);
    public Item editItemOfCollection(String collection, Item integrante) throws Exception;
    public void deleteItemOfCollection(String collection, String id);

}
