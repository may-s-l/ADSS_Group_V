package Supplier;

import java.util.ArrayList;

public abstract class AContract implements IPrintable {
    protected ArrayList<Order> orderHistory;
    protected Catalogue itemCatalogue;

    AContract(Catalogue itemCatalogue) {
        this.itemCatalogue = itemCatalogue;
        this.orderHistory = new ArrayList<Order>();
    }

    public abstract void print();
    public abstract String getType();

    public Catalogue getItemCatalogue() {
        return itemCatalogue;
    }
    public ArrayList<Order> getOrderHistory() {
        return orderHistory;
    }
}

