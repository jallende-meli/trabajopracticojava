public class Item implements Comparable<Item> {

    enum ORDER {
        PRICE_ASC,
        PRICE_DES,
        LISTING_TYPE_ASC,
        LISTING_TYPE_DES;
    }

    public ORDER order = ORDER.PRICE_ASC;

    private String id;
    private String site_id;
    private String title;
    private Float price;
    private String currency_id;
    private String listing_type_id;
    private String stop_time;
    private String thumbnail;
    private String[] tags;

    public Item(String id, String site_id, String title, float price, String currency_id,
                String listing_type_id, String stop_time, String thumbnail, String[] tags) {
        super();

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSite_id() {
        return site_id;
    }

    public void setSite_id(String site_id) {
        this.site_id = site_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getCurrency_id() {
        return currency_id;
    }

    public void setCurrency_id(String currency_id) {
        this.currency_id = currency_id;
    }

    public String getListing_type_id() {
        return listing_type_id;
    }

    public void setListing_type_id(String listing_type_id) {
        this.listing_type_id = listing_type_id;
    }

    public String getStop_time() {
        return stop_time;
    }

    public void setStop_time(String stop_time) {
        this.stop_time = stop_time;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public int compareTo(Item item) {
        return 0;
    }

    @Override
    public String toString() {
        return this.title;
    }
}
