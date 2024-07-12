package dev.src.Controllers;

import dev.src.Domain.*;
import dev.src.Domain.Enums.*;
import dev.src.Domain.Repository.ConstraintRep;
import dev.src.Domain.Repository.EmployeeRep;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmployeeConstraintController {

    //private ConstraintMenu constraintMenu;
    //private MyMap<String, Employee> employeesTempDatabase;//-V1-
    private EmployeeRep employeesTempDatabase;//-V2-

//    public EmployeeConstraintController(MyMap<String, Employee> Employees_temp_database) {
//        this.employeesTempDatabase=Employees_temp_database;
//    }
    public EmployeeConstraintController(EmployeeRep Employees_temp_database) {
        this.employeesTempDatabase=Employees_temp_database;
    }

    public String returnEmployeeDetails(String id) {
        return employeesTempDatabase.find(id).toStringfullinfo();
    }

    public void addConstraint(String employeeID, String sdate, String sshiftType) throws IllegalArgumentException {
        LocalDate date;
        ShiftType shiftType;

        // Parse the date
        try {
            date = LocalDate.parse(sdate);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("Invalid date format. Please use YYYY-MM-DD.");
        }

        // Validate and parse the shift type
        try {
            shiftType = ShiftType.valueOf(sshiftType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid shift type. Valid types are: MORNING, EVENING, FULLDAY.");
        }

        Employee employee = employeesTempDatabase.find(employeeID);

        if (employee == null) {
            throw new IllegalArgumentException("Employee not found.");
        }
        // Check if the date is in the past ---or in curent work day---
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Date is in the past.");
        }
        LocalDate today=LocalDate.now();
        LocalDate cant = LocalDate.now();
        List<LocalDate>DAYS_OF_CORENT_WORK_WEEK=new ArrayList<LocalDate>();
        DAYS_OF_CORENT_WORK_WEEK.add(today);
//        if(today.getDayOfWeek()==DayOfWeek.THURSDAY||today.getDayOfWeek()==DayOfWeek.FRIDAY||today.getDayOfWeek()==DayOfWeek.SATURDAY){
//            throw new IllegalArgumentException("Constraints can only be added from Sunday to Thursday");
//        }
        if(today.getDayOfWeek()==DayOfWeek.SUNDAY){
            DAYS_OF_CORENT_WORK_WEEK.add(today);
            cant=cant.plusDays(1);
            while (!cant.getDayOfWeek().equals(DayOfWeek.SUNDAY)){
                DAYS_OF_CORENT_WORK_WEEK.add(cant);
                cant=cant.plusDays(1);
            }
        }
        else {
            while (!cant.getDayOfWeek().equals(DayOfWeek.SUNDAY)){
                DAYS_OF_CORENT_WORK_WEEK.add(cant);
                cant=cant.plusDays(1);
            }
        }
        if(DAYS_OF_CORENT_WORK_WEEK.contains(date)||date.isEqual(cant.plusWeeks(1))||date.isAfter(cant.plusWeeks(1))){
            throw new IllegalArgumentException("Constraints can only be added for the next week!");
        }


        // Check if the constraint already exists
        ConstraintRep constraints = employee.getConstraintMyMap();
        if (constraints == null) {
            constraints = new ConstraintRep();
            employee.setConstraintMyMap(constraints);
        }

        Constraint newConstraint = new Constraint(employee, date, shiftType);
        if (employee.getConstraintByDate(date) != null) {
            constraints.update(newConstraint);
            throw new IllegalArgumentException("Constraint exists update");
        }
        constraints.add(newConstraint);
    }

    public void removeConstraint(String employeeID, String sdate, String sshiftType) throws IllegalArgumentException {
        LocalDate date;
        ShiftType shiftType;

        // Parse the date
        try {
            date = LocalDate.parse(sdate);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("Invalid date format. Please use YYYY-MM-DD.");
        }
        LocalDate today=LocalDate.now();
        if (date.isBefore(today))
            throw  new IllegalArgumentException("A past constraint cannot be deleted");

        // Validate and parse the shift type
        try {
            shiftType = ShiftType.valueOf(sshiftType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid shift type. Valid types are: MORNING, EVENING.");
        }

        Employee employee = employeesTempDatabase.find(employeeID);

        if (employee == null) {
            throw new IllegalArgumentException("Employee not found.");
        }

        ConstraintRep constraints = employee.getConstraintMyMap();
        String key =employeeID+","+date.toString();
        if (constraints == null || constraints.find(key) == null) {
            throw new IllegalArgumentException("Constraint not found.");
        }

        constraints.delete(key);
    }

    public String getConstraintFromToday(String employeeID) {
        MyMap<LocalDate, Constraint> constraintMyMap=employeesTempDatabase.find(employeeID).getFutureConstraintMap();

        if (constraintMyMap == null || constraintMyMap.size()==0) {
            return "No constraints found for this employee.";
        }

        String stringFutureConstraints="";

        for (LocalDate date : constraintMyMap.getKeys()) {
                stringFutureConstraints+=constraintMyMap.get(date).toString() + "\n";
        }
        return stringFutureConstraints;
    }
}

