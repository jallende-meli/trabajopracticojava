public class Currency {
    private String id;
    private String symbol;
    private String description;
    private int decimal_places;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDecimal_places() {
        return decimal_places;
    }

    public void setDecimal_places(int decimal_places) {
        this.decimal_places = decimal_places;
    }
}
