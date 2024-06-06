package dev.src.service;

import dev.src.Controllers.MasterController;

public class EmployeeService {
    MasterController masterController;

    public EmployeeService(MasterController masterController) {
        this.masterController = masterController;
    }

    public String viewEmployeeDetails(String employeeID) {
        try {
            return masterController.getEmployee_Constraint().returnEmployeeDetails(employeeID);
        } catch (NullPointerException e) {
            return "Employee not found.";
        }
    }

    public String addConstraintToEmployee(String employeeID, String sdate, String sshiftType) {
        try {
            masterController.getEmployee_Constraint().addConstraint(employeeID, sdate, sshiftType);
            return "Constraint added successfully.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    public String removeConstraintFromEmployee(String employeeID, String sdate, String sshiftType) {
        try {
            masterController.getEmployee_Constraint().removeConstraint(employeeID, sdate, sshiftType);
            return "Constraint removed successfully.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    public String viewEmployeeConstraints(String employeeID) {
        try {
            return masterController.getEmployee_Constraint().getConstraintFromToday(employeeID);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    public String printingWeekHistory(int branchNum, String dateStr) {
        try {
            return masterController.printingWeekHistory(branchNum, dateStr);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

}
