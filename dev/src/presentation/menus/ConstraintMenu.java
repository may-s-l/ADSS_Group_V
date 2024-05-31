package dev.src.presentation.menus;

import dev.src.domain.Constraint;
import dev.src.domain.Employee;

import java.sql.Time;
import java.time.LocalDate;
import java.util.*;
import java.util.Scanner;

public class ConstraintMenu {
    public ConstraintMenu(Employee employee){
        Scanner scanner = new Scanner(System.in);
        Employee employee1=employee;


        while (true) {
            System.out.println("Welcome to Constraint manu!");
            System.out.println("1. Add new constraint");
            System.out.println("2. Edit constraint");
            System.out.println("3. Delete constraint");
            System.out.println("4. Show constraint");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter date: as year/day/month ");
                    LocalDate date = LocalDate.parse(scanner.nextLine());

                    System.out.print("Enter the start time: as hh:mm ");
                    String start_t_string=scanner.nextLine() + ":00";
//                    try{
//                        Time start_t = Time.valueOf(start_t_string);
//                    } catch ()}

                    System.out.print("Enter the end time: as hh:mm ");
                    String end_t_string=scanner.nextLine() + ":00";
                    Time end_t = Time.valueOf(end_t_string);
                //    Constraint new_constrain=new Constraint(employee1, date,start_t,end_t);
                case 2:
                    System.out.print("Enter date: as year/day/month ");
                    LocalDate dateToEdit = LocalDate.parse(scanner.nextLine());
                    //serch in list of constraint
                case 3:
                    System.out.print("Enter date: as year/day/month ");
                    LocalDate dateToDelete = LocalDate.parse(scanner.nextLine());
                    //serch in list of constraint
                case 4:

            }
        }
    }
}
