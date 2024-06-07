package dev.src.Controllers;

import dev.src.Domain.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;


public class MasterController {

    private MyMap<String, Branch> Branch_temp_database;//String key address
    private List<Job> Employeejobs_temp_database;
    private MyMap<String, Employee> Employees_temp_database;//String key ID
    private MyMap<Integer, MyMap<LocalDate, String>> History_Shifts_temp_database;//int branch num
    private HRManagerEmployeeController HR_Employee;
    private HRManagerShiftController HR_Shift;
    private EmployeeConstraintController Employee_Constraint;


    public MasterController() {
        this.Employees_temp_database = new MyMap<String, Employee>();
        this.Employeejobs_temp_database = new ArrayList<Job>();
        this.Branch_temp_database = new MyMap<String, Branch>();
        this.History_Shifts_temp_database = new MyMap<Integer, MyMap<LocalDate, String>>();
        this.HR_Shift = new HRManagerShiftController(Employeejobs_temp_database, Branch_temp_database, Employees_temp_database, History_Shifts_temp_database);
        this.HR_Employee = new HRManagerEmployeeController(Employeejobs_temp_database, Branch_temp_database, Employees_temp_database);
        this.Employee_Constraint = new EmployeeConstraintController(Employees_temp_database);

    }

    public HRManagerEmployeeController getHR_Employee() {
        return HR_Employee;
    }

    public HRManagerShiftController getHR_Shift() {
        return HR_Shift;
    }

    public EmployeeConstraintController getEmployee_Constraint() {
        return Employee_Constraint;
    }

    public String checkLoginEmployee(String ID){
        if(this.Employees_temp_database.containsKey(ID)){
            Employee employee = this.Employees_temp_database.get(ID);
            if(employee instanceof ManagerEmployee){
                return "HR-MANAGER";
            }
            return "Employee";
        }
        return "Login failed Check ID";
    }


    public String printingWeekHistory(int branchNum, String dateStr) {
        if (dateStr == null) {
            return "Date can't be null";
        }

        LocalDate date;
        try {
            date = LocalDate.parse(dateStr);
        } catch (DateTimeParseException e) {
            return "Invalid date format. Please use YYYY-MM-DD.";
        }

        MyMap<LocalDate, String> branchHistory = History_Shifts_temp_database.get(branchNum);

        if (branchHistory == null) {
            return "Branch not found or does not yet have a placement log";
        }

        String weekHistory = branchHistory.get(date);

        if (weekHistory == null) {
            return "No history found for the given week.";
        }

        return weekHistory;
    }


}