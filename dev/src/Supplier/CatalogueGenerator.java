package Supplier;

import java.util.Date;
import java.util.Scanner;

public class CatalogueGenerator implements IObjectGenerator {
    public Catalogue generate() {
        ItemManagementSystem itemManagementSystem = new ItemManagementSystem();
        String itemName;
        Item item;
        String supplierItemID;
        Double basePrice;
        Double discount, maxDiscount;
        Catalogue catalogue = new Catalogue();
        QuantityForm quantityForm = new QuantityForm();
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
            supplierItemID = scanner.next();
            System.out.println("Please enter base price for " + itemName);
            basePrice = scanner.nextDouble();
            System.out.println("Please enter discount per " + itemName);
            discount = scanner.nextDouble();
            System.out.println("Please enter max discount of " + itemName);
            maxDiscount = (double) scanner.nextFloat();
            quantityForm.add(item, discount, maxDiscount);
            catalogue.add(item, supplierItemID, basePrice);
        }
        catalogue.setQuantityForm(quantityForm);
        return catalogue;
    }
}
