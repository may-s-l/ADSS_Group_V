package Supplier;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;

public class Catalogue implements IPrintable, ISearchable{
    private Dictionary<Item, Integer> suppliedItems;            // Item to SupplierItemID
    private Dictionary<Item, Float> itemsPrices;                // Items to base price for each
    private QuantityForm quantityForm;

    Catalogue() {
        suppliedItems = new Hashtable<>();
        itemsPrices = new Hashtable<>();
    }

    Catalogue(Hashtable<Item, Integer> catalogueItems) {
        suppliedItems = catalogueItems;
        itemsPrices = new Hashtable<>();
    }

    public void add(Item item, int catalogueNumber, float basePrice) {
        suppliedItems.put(item, catalogueNumber);
        itemsPrices.put(item, basePrice);
    }

    public void remove(Item item) {
        suppliedItems.remove(item);
    }

    public int getCatalogueItemNumber(Item item) {
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

    public float getItemBasePrice(Item item) {
        return itemsPrices.get(item);
    }
}
