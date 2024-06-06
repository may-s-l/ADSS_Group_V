package dev.src.Controllers;

import dev.src.Domain.*;
import dev.src.Domain.Enums.*;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

public class EmployeeConstraintController {

    //private ConstraintMenu constraintMenu;
    private MyMap<String, Employee> employeesTempDatabase;

    public EmployeeConstraintController(MyMap<String, Employee> Employees_temp_database) {
        this.employeesTempDatabase=Employees_temp_database;
    }

    public String returnEmployeeDetails(String id) {
        return employeesTempDatabase.get(id).toString();
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

        Employee employee = employeesTempDatabase.get(employeeID);

        if (employee == null) {
            throw new IllegalArgumentException("Employee not found.");
        }

        // Check if the date is in the past
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Date is in the past.");
        }

        // Check if the constraint already exists
        MyMap<LocalDate, Constraint> constraints = employee.getConstraintMyMap();
        if (constraints == null) {
            constraints = new MyMap<>();
            employee.setConstraintMyMap(constraints);
        }

        Constraint newConstraint = new Constraint(employee, date, shiftType);
        if (constraints.get(date) != null && constraints.get(date).equals(newConstraint)) {
            throw new IllegalArgumentException("Constraint already exists.");
        }

        constraints.put(date, newConstraint);
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

        // Validate and parse the shift type
        try {
            shiftType = ShiftType.valueOf(sshiftType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid shift type. Valid types are: MORNING, EVENING.");
        }

        Employee employee = employeesTempDatabase.get(employeeID);

        if (employee == null) {
            throw new IllegalArgumentException("Employee not found.");
        }

        MyMap<LocalDate, Constraint> constraints = employee.getConstraintMyMap();
        if (constraints == null || constraints.get(date) == null || !constraints.get(date).getShiftType().equals(shiftType)) {
            throw new IllegalArgumentException("Constraint not found.");
        }

        constraints.remove(date);
    }

    public String getConstraintFromToday(String employeeID) {
        MyMap<LocalDate, Constraint> constraintMyMap=employeesTempDatabase.get(employeeID).getConstraintMyMap();

        if (constraintMyMap == null) {
            return "No constraints found for this employee.";
        }

        LocalDate today = LocalDate.now();
        MyMap<LocalDate, Constraint> futureConstraints = new MyMap<>();
        String stringFutureConstraints=null;
        for (LocalDate date : constraintMyMap.getKeys()) {
            if (!date.isBefore(today)) {
                futureConstraints.put(date, constraintMyMap.get(date));
                stringFutureConstraints+=constraintMyMap.get(date).toString() + "/n";
            }
        }
        if (futureConstraints == null) {
            return "No constraints found for this employee.";
        }
        return stringFutureConstraints;
    }
}

