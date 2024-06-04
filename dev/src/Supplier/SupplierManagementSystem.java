package Supplier;

import java.util.ArrayList;
import java.util.Scanner;

public class SupplierManagementSystem {
    private static ArrayList<SupplierCard> suppliers = new ArrayList<>();
    private static int numOfSuppliers = 0;

    public void addSupplier() {
        SupplierCardGenerator s = new SupplierCardGenerator();
        suppliers.add(s.generate());
        numOfSuppliers++;
        System.out.println("Supplier Card was added successfully\n");
    }

    public void deleteSupplier() {
        SupplierCard toDelete = searchSupplier();
        if (toDelete != null) {
            suppliers.remove(toDelete);
            numOfSuppliers--;
            System.out.println(toDelete.getName() + " was deleted successfully\n");
        }
        System.out.println("Supplier doesn't exist in the system - can't delete");
    }

    public SupplierCard searchSupplier() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter name of supplier:");
        String name = scanner.next();
        for (SupplierCard s : suppliers) {
            if (s.getName().equals(name))
                return s;
        }
        return null;
    }

    public void printSupplierInfo() {
        SupplierCard toShow = searchSupplier();
        if (toShow != null)
            toShow.print();
        else
            System.out.println("Supplier doesn't exist in the system\n");
    }

    public void printSupplierContacts() {
        SupplierCard toShow = searchSupplier();
        int i = 1;
        if (toShow != null) {
            for (Contact c : toShow.getContacts()) {
                if (c != null) {
                    System.out.println("Contact #" + i);
                    c.print();
                    i++;
                }
            }
        }
        else {
            System.out.println("Supplier doesn't exist in the system\n");
        }
    }

    public void printSupplierContract() {
        SupplierCard toShow = searchSupplier();
        if (toShow != null) {
            toShow.getContract().print();
        }
        else {
            System.out.println("Supplier doesn't exist in the system\n");
        }
    }
}
