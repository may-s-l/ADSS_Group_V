package dev.src;

import dev.src.domain.Branch;
import dev.src.domain.Employee;
import dev.src.domain.Job;

import java.util.ArrayList;
import java.util.List;

public class Temp_DataBase {

    private List<Branch> Branch_temp_database;
    private List<Job> Employeejobs_temp_database;
    private List<Employee> Employees_temp_database;


    public Temp_DataBase() {
        this.Employees_temp_database = new ArrayList<Employee>();
        this.Employeejobs_temp_database=new ArrayList<Job>();
        this.Branch_temp_database = new ArrayList<Branch>();
    }

    public List<Branch> getBranch_temp_database() {
        return Branch_temp_database;
    }

    public void setBranch_temp_database(List<Branch> branch_temp_database) {
        Branch_temp_database = branch_temp_database;
    }

    public List<Job> getEmployeejobs_temp_database() {
        return Employeejobs_temp_database;
    }

    public void setEmployeejobs_temp_database(List<Job> employeejobs_temp_database) {
        Employeejobs_temp_database = employeejobs_temp_database;
    }

    public List<Employee> getEmployees_temp_database() {
        return Employees_temp_database;
    }

    public void setEmployees_temp_database(List<Employee> employees_temp_database) {
        Employees_temp_database = employees_temp_database;
    }
}
