package Supplier;

import java.util.ArrayList;
import java.util.Scanner;

public class OrderManagementSystem {
    ArrayList<Order> orderHistory;
    SupplierManagementSystem supplierManagementSystem;
    ItemManagementSystem itemManagementSystem;

    OrderManagementSystem(SupplierManagementSystem SMS, ItemManagementSystem IMS) {
        orderHistory = new ArrayList<>();
        supplierManagementSystem = SMS;
        itemManagementSystem = IMS;
    }

    public void order() {
        int amount;
        String itemName;
        Scanner scanner = new Scanner(System.in);
        System.out.println("--Order Management System--");
        SupplierCard supplier = supplierManagementSystem.searchSupplier();
        if (supplier == null){
            System.out.println("Supplier doesn't exist in the system - can't order");
            return;
        }
        System.out.println("Please enter address:");
        String address = scanner.next();
        Order newOrder = new Order(supplier, address);
        System.out.println("Please choose from the following list the items you wish to order: ");
        supplier.getContract().getItemCatalogue().print();
        do {
            itemName = scanner.next();
            if (itemName.equals("stop"))
                break;
            Item cur = supplier.getContract().getItemCatalogue().search(itemName);
            if (cur != null && itemManagementSystem.itemExists(cur)) {
                System.out.println("Great! item can be ordered, please enter amount");
                amount = scanner.nextInt();
                newOrder.addItemToOrder(cur, amount, supplier.getContract().getItemCatalogue().getQuantityForm().calculateDiscount(cur, amount));
                System.out.println("Item has been successfully added to the order.");
            }
            System.out.println("Enter the next item, if you wish to stop input 'stop'");
        } while (true);
        // adding the order to history:
        orderHistory.add(newOrder);
        supplier.getContract().getOrderHistory().add(newOrder);
        System.out.println("Success! the order has been created and added to the order history");
        newOrder.print();
    }

    public void printOrderHistory() {
        for (int i = 0; i < orderHistory.size(); i++) {
            orderHistory.get(i).print();
        }
    }
}
