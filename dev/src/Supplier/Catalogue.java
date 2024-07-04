package Supplier;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;

public class Catalogue implements IPrintable, ISearchable{
    private Dictionary<Item, String> suppliedItems;            // Item to SupplierItemID
    private Dictionary<Item, Double> itemsPrices;                // Items to base price for each
    private QuantityForm quantityForm;

    public Catalogue() {
        suppliedItems = new Hashtable<>();
        itemsPrices = new Hashtable<Item, Double>();
    }

    Catalogue(Hashtable<Item, String> catalogueItems) {
        suppliedItems = catalogueItems;
        itemsPrices = new Hashtable<Item, Double>();
    }

    public void add(Item item, String catalogueNumber, Double basePrice) {
        suppliedItems.put(item, catalogueNumber);
        itemsPrices.put(item, basePrice);
    }

    public void remove(Item item) {
        suppliedItems.remove(item);
    }

    public String getCatalogueItemNumber(Item item) {
        return suppliedItems.get(item);
    }

    public void setQuantityForm(QuantityForm q) {
        quantityForm = q;
    }

    @Override
    public void print() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("number of supplied items #" + suppliedItems.size() + ":\n");
        for (Iterator<Item> it = suppliedItems.keys().asIterator(); it.hasNext(); ) {
            Item i = it.next();
            builder.append(i);
            builder.append("\n");
        }
        return builder.toString();
    }

    @Override
    public Item search(String name) {
        for (Iterator<Item> it = suppliedItems.keys().asIterator(); it.hasNext();) {
            Item i = it.next();
            if (i.getItemName().equals(name))
                return i;
        }
        return null;
    }

    public QuantityForm getQuantityForm() {
        return quantityForm;
    }

    public Double getItemBasePrice(Item item) {
        return itemsPrices.get(item);
    }
}
