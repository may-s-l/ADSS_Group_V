package Supplier;

import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;

public class Order implements IPrintable{
    Dictionary<Item, Integer> orderedItems;         // items : amount of each
    Dictionary<Item, Float> discountsMapped;        // items : discounts for each
    static long index = 1;
    SupplierCard supplierCard;
    long orderID;
    Date orderDate;
    String address;
    float finalPrice;
    int amountOfDisctinctItems;
    Order(SupplierCard supplier, String add) {
        supplierCard = supplier;
        address = add;
        orderDate = new Date();
        orderID = index++;
        finalPrice = 0;
        orderedItems = new Hashtable<>();
        discountsMapped = new Hashtable<>();
        amountOfDisctinctItems = 0;
    }

    public void addItemToOrder(Item item, int amount, float discount) {
        orderedItems.put(item, amount);
        discountsMapped.put(item, discount);
        finalPrice += supplierCard.getContract().getItemCatalogue().getItemBasePrice(item) * discount * amount;
        amountOfDisctinctItems++;
    }

    public void print() {
        System.out.println("--Order #" + orderID + " summary--\n" +
                "-Supplier: " + supplierCard.getName() + "\n" +
                "-Address: " + address + "\n" +
                "-Supplier ID: " + supplierCard.getFirmID() + "\n" +
                "-Order date: " + orderDate + "\n" +
                "List of ordered items (name | amount | final discount):");
        for (Iterator<Item> it = orderedItems.keys().asIterator(); it.hasNext();) {
            Item i = it.next();
            System.out.println("\t" + i.getItemName() + " | " + orderedItems.get(i) + " | " + Math.round(discountsMapped.get(i) * 100) + "%");
        }
        System.out.println("The total price is: " + finalPrice + "\n");
    }
}
