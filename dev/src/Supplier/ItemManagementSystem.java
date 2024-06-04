package Supplier;

import java.util.ArrayList;
import java.util.Scanner;

public class ItemManagementSystem implements ISearchable{
    static ArrayList<Item> itemsPool = new ArrayList<Item>();
    @Override
    public Item search(String itemName) {
         for (Item i : itemsPool) {
             if (i.getItemName().equals(itemName))
                 return i;
         }
         return null;
    }

    public void add(Item item) {
        itemsPool.add(item);
    }

    public void add() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What is the name of the item you wish to add?");
        String itemName = scanner.next();
        System.out.println("What is the ID of " + itemName + "?");
        int itemID = scanner.nextInt();
        add(new Item(itemName, itemID));
    }

    public boolean itemExists(Item item) {
        return search(item.getItemName()) != null;
    }
}
