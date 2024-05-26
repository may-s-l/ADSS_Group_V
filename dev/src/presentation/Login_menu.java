package presentation;
import java.util.Scanner;

public class Login_menu {


    public Login_menu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Login Menu");
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

//            switch (choice) {
//                case 1:
//
//                    System.out.print("Enter username: ");
//                    String username = scanner.nextLine();
//                    System.out.print("Enter password: ");
//                    String password = scanner.nextLine();
//                    if()
//
//                    } else {
//                        System.out.println("Invalid username or password. Please try again.");
//                    }
//                    break;
//                case 2:
//                    System.out.println("Exiting...");
//                    scanner.close();
//                    System.exit(0);
//                default:
//                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


