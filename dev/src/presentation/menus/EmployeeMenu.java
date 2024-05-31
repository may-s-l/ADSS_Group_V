package dev.src.presentation.menus;

import dev.src.domain.Employee;

import java.util.*;
import java.util.Scanner;

public class EmployeeMenu {

    public EmployeeMenu(String ID, List<Employee> employees, Employee employee) {
        Scanner scanner = new Scanner(System.in);
        String id=ID;
        List<Employee> employeeList=employees;
        Employee employee1=employee;


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
                    System.out.println("name: "+ employee1.getName());
                    System.out.println("bank account: "+employee1.getBank_account());
                    System.out.println("your ID: "+ employee1.getID());
                    System.out.println("your work role: "+employee1.getJobs());
                    System.out.println("Salary: "+ employee1.getTerms().getSalary());
                    System.out.println("your start of work date: "+employee1.getTerms().getStart_date());

                case 2:
                    ConstraintMenu cons= new ConstraintMenu(employee1);

                case 3:

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
