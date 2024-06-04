package Supplier;

import java.util.Scanner;

public class ManagementSystemMenu {
    public int display() {
        Scanner scanner = new Scanner(System.in);
        int option;
        System.out.println("---Welcome to Super Li supplier management system---\n" +
                "Please choose one of the following options:\n" +
                "1. Add new supplier card\n" +
                "2. Delete supplier card\n" +
                "3. Add a new item to the system\n" +
                "4. Print supplier info\n" +
                "5. Print supplier contact info\n" +
                "6. Print supplier contract info\n" +
                "7. Create a new order\n" +
                "8. Show order history\n" +
                "0. Exit system");
        try {
            option = scanner.nextInt();
            if (option < 0 || option > 8) {
                System.out.println("Error: unknown input");
                return -1;
            }
            return option;
        }
        catch (Exception e) {
            System.out.println("Error: input is not an integer");
        }
        return -1;
    }
}
