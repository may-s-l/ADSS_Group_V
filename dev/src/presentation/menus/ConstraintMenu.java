package dev.src.presentation.menus;

import dev.src.Controllers.*;
import dev.src.Data.*;
import dev.src.Domain.*;
import dev.src.Domain.Enums.*;



import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Scanner;

public class ConstraintMenu {
    private static final EmployeeConstraintController employeeConstraintController=new EmployeeConstraintController();

    public ConstraintMenu(String employeeID){

        Scanner scanner = new Scanner(System.in);
        String id=employeeID;


        while (true) {
            System.out.println("Welcome to Constraint manu!");
            System.out.println("1. Add new constraint");
            System.out.println("2. Delete constraint");
            System.out.println("3. Show constraint");
            System.out.println("4. Go Back to employee menu:)");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    Scanner scanner1 = new Scanner(System.in);
                    System.out.print("please Enter date and shift type: as year/day/month, MORNING,EVENING");
                    String input = scanner.nextLine();
                    String[] details = input.split(", ");


                case 2:
                    deleteConstraint(employee);
                case 3:
                    ShowConstraint(employee);
                case 4:
                    new EmployeeMenu(employee);
            }
        }
    }
    public static void addConstraintToEmployee(Employee employee){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter date: as year/day/month");
        LocalDate date;
        try {
            date = LocalDate.parse(scanner.nextLine());
        } catch (IllegalArgumentException e) {
            System.out.println("you provide invalid date. ");
            addConstraintToEmployee(employee);
            return;
        }
        if (date.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
            System.out.println("We do not work on Saturdays.");
            addConstraintToEmployee(employee);
        }
        LocalDate today = LocalDate.now();
        if (date.isBefore(today)) {
            System.out.println("You cannot add a constraint for a past date.");
            addConstraintToEmployee(employee);
            return;
        }
        LocalDate nextSaturday = today.with(DayOfWeek.SATURDAY);
        if (ChronoUnit.DAYS.between(today, nextSaturday) <= 3) {
            System.out.println("You can only add constraints until 72 hours before the next Saturday.");
            addConstraintToEmployee(employee);
            return;
        }
        System.out.print("Enter type of shift: ");
        String shiftType=scanner.nextLine().toUpperCase();
        try {
            ShiftType shift_type=ShiftType.valueOf(shiftType);
        } catch (IllegalArgumentException e) {
            System.out.print("you provide a wrong type of shift. ");
            addConstraintToEmployee(employee);
            return;
        }
        ShiftType shift_type=ShiftType.valueOf(shiftType);
        if (employeeConstraintService.isConstraintExist(employee,date,shift_type)){
            System.out.println("constrain already exit");
            new ConstraintMenu(employee);
        } else {
            employeeConstraintService.addConstrain(employee,date,shift_type);
        }
    }


    public static void deleteConstraint(Employee employee){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter date: as year/day/month ");
        try {
            LocalDate date = LocalDate.parse(scanner.nextLine());
        } catch (IllegalArgumentException e) {
            System.out.println("you provide invalid date. ");
            addConstraintToEmployee(employee);
        }
        LocalDate date = LocalDate.parse(scanner.nextLine());
        System.out.print("Enter type of shift: ");
        String shift_type=scanner.nextLine().toUpperCase();
        try {
            ShiftType shiftType=ShiftType.valueOf(shift_type);
        } catch (IllegalArgumentException e) {
            System.out.print("you provide a wrong type of shift. ");
            addConstraintToEmployee(employee);
        }
        ShiftType shiftType=ShiftType.valueOf(shift_type);
        if (employeeConstraintService.isConstraintExist(employee,date,shiftType)){
            employeeConstraintService.deleteConstrain(employee,date,shiftType);
        } else {
            System.out.println("constrain not exit");
            new ConstraintMenu(employee);
        }
    }

    public static void ShowConstraint(Employee employee){
        List<Constraint> constraints=employeeConstraintService.getConstraintForEmployee(employee);
        LocalDate today = LocalDate.now();
        if (constraints.isEmpty()) {
            System.out.println("No constraints from today.");
        } else {
            for (Constraint constraint : constraints) {
                if (constraint.getShiftDate().isAfter(today))
                    System.out.println(constraint+ "/n");
            }
        }
        new ConstraintMenu(employee);
    }


}