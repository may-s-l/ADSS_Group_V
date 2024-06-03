package dev.src.presentation.menus;


import dev.src.Controllers.HRManagerEmployeeController;

import java.util.Scanner;

public class SystemManagarMenu{

    public SystemManagarMenu(String ID, HRManagerEmployeeController C){
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Welcome to the HR Manager menu");
            System.out.println("1. Employee data");
            System.out.println("2. Jobs data");
            System.out.println("3. Employee placement system");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    EmployeeDataMenu EMP_MENU= new EmployeeDataMenu();

                    break;
                case 2:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
}

}