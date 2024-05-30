package dev.src.presentation.menus;
import dev.src.domain.Branch;
import dev.src.domain.Employee;
import dev.src.services.HRManagerEmployeeController;

import java.util.Scanner;

public class Login_menu {

    public Login_menu() {
        HRManagerEmployeeController HRMcontroller = new HRManagerEmployeeController();
        ManagmantJob job = HRMcontroller.createManagingJob("SystemManager");
        Branch branch = HRMcontroller.createBranch("MAIN Branch SuperLEE");
        HRMcontroller.createManagmentEmployee("ADMIN","012345678910","111111",job,(float)1000000,branch);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Login Menu");
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:

                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter ID: ");
                    String password = scanner.nextLine();
                    Employee emp= HRMcontroller.getEmployeeByID(password);
                    if (emp==null){
                        System.out.println("Invalid ID. Please try again.");
                    }
                    else if(emp!=null && emp.getJob() instanceof ManagmantJob && emp.getJob().getJobName()=="SystemManagar"){
                        new SystemManagarMenu(password,HRMcontroller);
                    }
                    else {
                        new EmployeeMenu(password, HRMcontroller.getEmployees_temp_database());
                    }
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


