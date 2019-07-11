import java.util.Collection;

public interface ItemService {

    public Collection<Item> getCollection(String key);
    public void setCollection(String key, Collection<Item> collection);

    public Collection<Item> getOrderItems(String order);

    /** CRUD to items of collection **/
    public void addItemToCollection(Item item);
    public Item getItem(String id);
    public Item editItem(Item integrante) throws Exception;
    public void deleteItem(String id);

    public boolean itemExist(String search);
}
