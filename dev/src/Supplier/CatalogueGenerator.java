package Supplier;

import java.util.Scanner;

public class CatalogueGenerator implements IObjectGenerator {
    public Catalogue generate() {
        ItemManagementSystem itemManagementSystem = new ItemManagementSystem();
        String itemName;
        Item item;
        int supplierItemID;
        Float basePrice;
        Float discount, maxDiscount;
        QuantityForm quantityForm = new QuantityForm();
        Catalogue catalogue = new Catalogue();
        Scanner scanner = new Scanner(System.in);
        System.out.println("--Catalogue Generator--");
        while (true) {
            System.out.println("To register an item press 1, if you wish to stop press 0");
            if (scanner.nextInt() == 0)
                break;
            System.out.println("Please enter item name:");
            itemName = scanner.next();
            item = itemManagementSystem.search(itemName);           // we got our item
            if (item == null) {
                System.out.println(itemName + " doesn't exist in the system");
                continue;
            }
            System.out.println("Please enter the supplier's item ID for " + itemName);
            supplierItemID = scanner.nextInt();
            System.out.println("Please enter base price for " + itemName);
            basePrice = scanner.nextFloat();
            System.out.println("Please enter discount per " + itemName);
            discount = scanner.nextFloat();
            System.out.println("Please enter max discount of " + itemName);
            maxDiscount = scanner.nextFloat();
            quantityForm.add(item, discount, maxDiscount);
            catalogue.add(item, supplierItemID, basePrice);
        }
        catalogue.setQuantityForm(quantityForm);
        return catalogue;
    }
}
