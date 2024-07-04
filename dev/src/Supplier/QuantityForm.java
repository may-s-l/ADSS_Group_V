package Supplier;

import java.util.Dictionary;
import java.util.Hashtable;

public class QuantityForm {
    Dictionary<Item, Double> discountPerItem;
    Dictionary<Item, Double> maxDiscounts;

    public QuantityForm() {
        discountPerItem = new Hashtable<Item, Double>();
        maxDiscounts = new Hashtable<Item, Double>();
    }

    QuantityForm(Hashtable<Item, Double> discounts, Hashtable<Item, Double> maxDiscounts) {
        this.discountPerItem = discounts;
        this.maxDiscounts = maxDiscounts;
    }

    public void add(Item item, Double discount, Double maxDiscount) {
        discountPerItem.put(item, discount);
        maxDiscounts.put(item, maxDiscount);
    }

    public void remove(Item item) {
        discountPerItem.remove(item);
        maxDiscounts.remove(item);
    }

    public Double calculateDiscount(Item item, int amount) {
        return Math.min(maxDiscounts.get(item), amount * discountPerItem.get(item));
    }
}
