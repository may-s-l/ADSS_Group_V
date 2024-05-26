package dev.src.presentation.menus;

import dev.src.domain.Employee;

import java.util.*;
import java.util.ArrayList;
import java.util.Scanner;

public class EmployeeMenu {

    public EmployeeMenu(String ID, List<Employee> employees) {
        Scanner scanner = new Scanner(System.in);
        String id=ID;
        List<Employee> employeeList=employees;


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
                    System.out.println();

            }
        }
    }
}
