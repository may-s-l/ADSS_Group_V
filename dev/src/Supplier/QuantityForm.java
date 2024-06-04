package Supplier;

import java.util.Dictionary;
import java.util.Hashtable;

public class QuantityForm {
    Dictionary<Item, Float> discountPerItem;
    Dictionary<Item, Float> maxDiscounts;

    QuantityForm() {
        discountPerItem = new Hashtable<Item, Float>();
        maxDiscounts = new Hashtable<Item, Float>();
    }

    QuantityForm(Hashtable<Item, Float> discounts, Hashtable<Item, Float> maxDiscounts) {
        this.discountPerItem = discounts;
        this.maxDiscounts = maxDiscounts;
    }

    public void add(Item item, Float discount, Float maxDiscount) {
        discountPerItem.put(item, discount);
        maxDiscounts.put(item, maxDiscount);
    }

    public void remove(Item item) {
        discountPerItem.remove(item);
        maxDiscounts.remove(item);
    }

    public Float calculateDiscount(Item item, int amount) {
        return Math.min(maxDiscounts.get(item), amount * discountPerItem.get(item));
    }
}
