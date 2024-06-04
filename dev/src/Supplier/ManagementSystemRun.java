package Supplier;

public class ManagementSystemRun {
    public static void main(String[] args) {
        run();
    }
    public static void run() {
        ItemManagementSystem itemManagementSystem = new ItemManagementSystem();
        SupplierManagementSystem supplierManagementSystem = new SupplierManagementSystem();
        OrderManagementSystem orderManagementSystem = new OrderManagementSystem(supplierManagementSystem, itemManagementSystem);
        ManagementSystemMenu managementSystemMenu = new ManagementSystemMenu();
        int option;
        do {
            option = managementSystemMenu.display();
            switch (option) {
                case 1:
                    supplierManagementSystem.addSupplier();
                    break;
                case 2:
                    supplierManagementSystem.deleteSupplier();
                    break;
                case 3:
                    itemManagementSystem.add();
                    break;
                case 4:
                    supplierManagementSystem.printSupplierInfo();
                    break;
                case 5:
                    supplierManagementSystem.printSupplierContacts();
                    break;
                case 6:
                    supplierManagementSystem.printSupplierContract();
                    break;
                case 7:
                    orderManagementSystem.order();
                    break;
                case 8:
                    orderManagementSystem.printOrderHistory();
                    break;
            }
        } while (option != 0);
        System.out.println("Thank you for using Super Li supplier management system");
    }

    public static boolean isExit(String string) {
        return string.equals("exit");
    }
}
