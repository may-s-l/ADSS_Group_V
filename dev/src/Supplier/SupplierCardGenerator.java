package Supplier;

import java.util.List;
import java.util.Scanner;

public class SupplierCardGenerator implements IObjectGenerator {
    @Override
    public SupplierCard generate() {
        String name, paymentMethod;
        String firmID, bankAccount;
        int isConstant;
        AContract contract;
        Contact[] contacts = new Contact[20];
        Scanner scanner = new Scanner(System.in);
        System.out.println("--Generating supplier--");
        System.out.println("Please enter name:");
        name = scanner.next();
        System.out.println("Please enter firmID:");
        firmID = scanner.next();
        System.out.println("Please enter payment method:");
        paymentMethod = scanner.next();
        System.out.println("Please enter bank account number:");
        bankAccount = scanner.next();
        System.out.println("Temporary(0) or fixed(1) supplier?");
        isConstant = scanner.nextInt();

        switch (isConstant) {
            case 1:
            {
                FixedContractGenerator t1 = new FixedContractGenerator();
                contract = t1.generate();
                break;
            }
            default:
            {
                TempContractGenerator t0 = new TempContractGenerator();
                contract = t0.generate();
                break;
            }
        }

        // generating contacts
        ContactGenerator contactGenerator = new ContactGenerator();
        int i = 0;
        while (true) {
            int brk;
            System.out.println("To generate a contact press 1 to stop press 0");
            brk = scanner.nextInt();
            if (brk == 0)
                break;
            contacts[i++] = contactGenerator.generate();
        }

        return new SupplierCard(name, firmID, bankAccount, paymentMethod, List.of(contacts), contract);
    }
}
