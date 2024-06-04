package Supplier;

import java.util.Scanner;

public class ContactGenerator implements IObjectGenerator {
    public Contact generate() {
        long ID;
        String name, phoneNumber;
        Scanner scanner = new Scanner(System.in);
        System.out.println("--Generating contact--");
        System.out.println("Please enter a name:");
        name = scanner.next();
        System.out.println("Please enter ID");
        ID = scanner.nextLong();
        System.out.println("Please enter a phone number:");
        phoneNumber = scanner.next();
        return new Contact(name, ID, phoneNumber);
    }
}
