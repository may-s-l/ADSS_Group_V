package dev.src.Controllers;

import dev.src.Domain.Branch;
import dev.src.Domain.Employee;
import dev.src.Domain.Job;
import dev.src.Domain.MyMap;

import java.util.ArrayList;
import java.util.List;

public class MasterControllee {

    private MyMap<String, Branch> Branch_temp_database;//String address
    private List<Job> Employeejobs_temp_database;
    private MyMap<String, Employee> Employees_temp_database;//String ID

    private HRManagerEmployeeController HR_Employee;
    private HRManagerShiftController HR_Shift;
    private EmployeeConstraintController Employee_Constraint;




    public MasterControllee() {
        this.Employees_temp_database = new MyMap<String, Employee>();
        this.Employeejobs_temp_database=new ArrayList<Job>();
        this.Branch_temp_database = new MyMap<String,Branch>();
        this.HR_Shif=new HRManagerShiftController(Employeejobs_temp_database,Branch_temp_database,Employees_temp_database);
        this.Employee_Constraint = new EmployeeConstraintController(Employees_temp_database);





    }
}
