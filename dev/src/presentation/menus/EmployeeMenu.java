package dev.src.presentation.menus;

import dev.src.domain.Employee;

import java.time.LocalDate;
import java.util.*;
import java.util.Scanner;

public class EmployeeMenu {

    public EmployeeMenu(Employee emp) {
        Scanner scanner = new Scanner(System.in);
        Employee employee=emp;


        while (true) {
            System.out.println("Welcome to employee menu!");
            System.out.println("1. View employee details");
            System.out.println("2. Go to the Constraints menu");
            System.out.println("3. Show shifts");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    employee.toString();
                    new EmployeeMenu(employee);

                case 2:
                    new ConstraintMenu(employee);

                case 3:

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void ShowMenu(Employee employee){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the start date of the shift you want: as year/day/month ");
        LocalDate startDate;
        try {
            startDate = LocalDate.parse(scanner.nextLine());
        } catch (IllegalArgumentException e) {
            System.out.println("you provide invalid date. ");
            new EmployeeMenu(employee);
            return;
        }
        System.out.println("Enter the end date of the shift you want: as year/day/month ");
        LocalDate endDate;
        try {
            endDate = LocalDate.parse(scanner.nextLine());
        } catch (IllegalArgumentException e) {
            System.out.println("you provide invalid date. ");
            new EmployeeMenu(employee);
            return;
        }


    }
}
