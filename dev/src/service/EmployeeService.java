package dev.src.service;

import dev.src.Controllers.EmployeeConstraintController;
import dev.src.Controllers.MasterControllee;

public class EmployeeService {
    MasterControllee masterController=new MasterControllee();


//    public String viewEmployeeDetails(String employeeID) {
//        try {
//            return masterController.getEmployeesTempDatabase().get(employeeID).toStringfullinfo();
//        } catch (NullPointerException e) {
//            return "Employee not found.";
//        }
//    }
//
//    public String addConstraintToEmployee(String employeeID, String sdate, String sshiftType) {
//        try {
//            masterController.getEmployeeConstraintController().addConstraint(employeeID, sdate, sshiftType);
//            return "Constraint added successfully.";
//        } catch (IllegalArgumentException e) {
//            return e.getMessage();
//        }
//    }
//
//    public String removeConstraintFromEmployee(String employeeID, String sdate, String sshiftType) {
//        try {
//            masterController.getEmployeeConstraintController().removeConstraint(employeeID, sdate, sshiftType);
//            return "Constraint removed successfully.";
//        } catch (IllegalArgumentException e) {
//            return e.getMessage();
//        }
//    }
//
}
