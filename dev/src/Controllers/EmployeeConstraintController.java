package dev.src.Controllers;

import dev.src.Data.*;
import dev.src.Domain.*;
import dev.src.Domain.Enums.*;
import dev.src.service.EmployeeConstraintService;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeConstraintController {

    //private ConstraintMenu constraintMenu;
    private MyMap<String, Employee> employeesTempDatabase;

    public EmployeeConstraintController(MyMap<String, Employee> Employees_temp_database) {
        this.employeesTempDatabase=Employees_temp_database;
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
            throw new IllegalArgumentException("Invalid shift type. Valid types are: MORNING, EVENING.");
        }

        Employee employee = employeesTempDatabase.get(employeeID);

        // Check if the date is in the past
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Date is in the past.");
        }

        // Check if the constraint already exists
        MyMap<LocalDate, Constraint> constraints = employee.getConstraintMyMap();
        if (constraints != null) {
            if (constraints.get(date).equals(constraint))
            for (Constraint constraint : constraints) {
                if (constraint.getShiftDate().equals(date) && constraint.getShiftType() == shiftType) {
                    throw new IllegalArgumentException("Constraint already exists.");
                }
            }
        } else {
            constraints = new ArrayList<>();
        }

        // Add the new constraint
        Constraint newConstraint = new Constraint(employee, date, shiftType);
        constraints.add(newConstraint);
        tempDatabase.getConstraint_temp_database().put(employee, constraints);
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

        // Check if the employee exists
        Employee employee = tempDatabase.getEmployees_temp_database().get(employeeID);

        // Check if the constraint exists
        List<Constraint> constraints = tempDatabase.getEmployeeConstraint(employee);
        if (constraints == null || constraints.isEmpty()) {
            throw new IllegalArgumentException("No constraints found for the given employee.");
        }

        // Find and remove the constraint
        Constraint toRemove = null;
        for (Constraint constraint : constraints) {
            if (constraint.getShiftDate().equals(date) && constraint.getShiftType() == shiftType) {
                toRemove = constraint;
                break;
            }
        }

        if (toRemove == null) {
            throw new IllegalArgumentException("Constraint not found.");
        }

        constraints.remove(toRemove);
        if (constraints.isEmpty()) {
            tempDatabase.getConstraint_temp_database().remove(employee);
        } else {
            tempDatabase.getConstraint_temp_database().put(employee, constraints);
        }
    }
}
