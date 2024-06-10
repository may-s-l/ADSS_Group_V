package dev.src.presentation.menus;

import dev.src.service.EmployeeService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class EmployeeMenu {
    EmployeeService employeeService;
    private String employeeID;

    public EmployeeMenu(EmployeeService employeeService, String employeeID) {
        this.employeeService = employeeService;
        this.employeeID = employeeID;
        displayMenu();
    }

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to employee menu!");
            System.out.println("1. View employee details");
            System.out.println("2. Go to the Constraints menu");
            System.out.println("3. Show shifts");
            System.out.println("4. Back to Login menu");
            System.out.print("Enter your choice: ");

            int choice = -1;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // clear the buffer
                continue;
            }

            switch (choice) {
                case 1:
                    viewEmployeeDetails();
                    break;
                case 2:
                    goToConstraintsMenu(scanner);
                    break;
                case 3:
                    showShifts(scanner);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void viewEmployeeDetails() {
        String details = employeeService.viewEmployeeDetails(employeeID);
        System.out.println(details);
    }

    private void goToConstraintsMenu(Scanner scanner) {
        while (true) {
            System.out.println("Constraints Menu:");
            System.out.println("1. Add constraint");
            System.out.println("2. Remove constraint");
            System.out.println("3. View constraints");
            System.out.println("4. Back to employee menu");
            System.out.print("Enter your choice: ");

            int choice = -1;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // clear the buffer
                continue;
            }

            switch (choice) {
                case 1:
                    addConstraint(scanner);
                    break;
                case 2:
                    removeConstraint(scanner);
                    break;
                case 3:
                    viewConstraints();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addConstraint(Scanner scanner) {
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter shift type (MORNING/EVENING/FULLDAY): ");
        String shiftType = scanner.nextLine();
        String result = employeeService.addConstraintToEmployee(employeeID, date, shiftType);
        System.out.println(result);
    }

    private void removeConstraint(Scanner scanner) {
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter shift type (MORNING/EVENING/FULLDAY): ");
        String shiftType = scanner.nextLine();
        String result = employeeService.removeConstraintFromEmployee(employeeID, date, shiftType);
        System.out.println(result);
    }

    private void viewConstraints() {
        String constraints = employeeService.viewEmployeeConstraints(employeeID);
        System.out.println(constraints);
    }

    private void showShifts(Scanner scanner) {
        int branchNum = 0;
        while (true) {
            System.out.print("Enter branch number: ");
            try {
                branchNum = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
        System.out.print("Enter the start date of the week (YYYY-MM-DD): ");
        String dateStr = scanner.nextLine();
        String result = employeeService.printingWeekHistory(branchNum, dateStr);
        System.out.println(result);
    }
}
