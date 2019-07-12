import java.util.HashMap;

public class CurrencyService {
    private HashMap <String, Currency> currencyMap = new HashMap();

    public void setCurrencies(Currency[] currencies){
        for (int i = 0; i < currencies.length; i++) {
            currencyMap.put(currencies[i].getId(), currencies[i]);
        }
    }

    public Currency getCurrencyById(String id) {
        if (currencyMap.containsKey(id)) {
            return currencyMap.get(id);
        } else {
            Currency currency = new Currency();
            currency.setId(id);
            return currency;
        }
    }
}
