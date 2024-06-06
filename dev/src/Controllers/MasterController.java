package dev.src.Controllers;

import dev.src.Domain.Branch;
import dev.src.Domain.Employee;
import dev.src.Domain.Job;
import dev.src.Domain.MyMap;

import java.time.LocalDate;
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
}






