package Supplier;

public class Item implements IPrintable {
    String itemName;
    int itemID;

    public Item(String itemName, int itemID) {
        this.itemName = itemName;
        this.itemID = itemID;
    }

    @Override
    public void print() {
        System.out.println(this);
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public String toString() {
        return "{" + itemName + "," + itemID + "}";
    }
}
