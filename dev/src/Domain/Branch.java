package dev.src.Domain;

import dev.src.Domain.*;
import dev.src.Domain.Enums.*;

import java.util.ArrayList;
import java.util.List;

public class Branch {

    private String name;
    private String address;
    private ManagerEmployee managerEmployee;
    private MyMap<String,Employee> EmployeesInBranch;
    private int branchNum;



    public Branch(String name, String address, ManagerEmployee managerEmployee) {
        this.name = name;
        this.address = address;
        this.managerEmployee = managerEmployee;
        this.EmployeesInBranch=new MyMap<String,Employee>();
    }

    public Branch(String name, String address) {
        this.name = name;
        this.address = address;
        this.managerEmployee = null;
        this.EmployeesInBranch=new MyMap<String,Employee>();

    }

    public String getBranchName() {
        return name;
    }

    public void setBranchName(String name) {
        this.name = name;
    }

    public String getBranchAddress() {
        return address;
    }

    public void setBranchAddress(String address) {
        this.address = address;
    }

    public ManagerEmployee getManagerEmployee() {
        return managerEmployee;
    }

    public MyMap<String,Employee> getEmployeesInBranch() {
        return EmployeesInBranch;
    }

    public void setEmployeesInBranch(MyMap<String,Employee> employeesInBranch) {
        EmployeesInBranch = employeesInBranch;
    }

    public void setManagerEmployee(ManagerEmployee managerEmployee) {
        this.managerEmployee = managerEmployee;
    }

    public void addEmployeeToBranch(Employee employee){
        this.EmployeesInBranch.put(employee.getID(),employee);
    }

    public Employee getEmployee(String ID){
        return this.EmployeesInBranch.get(ID);
    }

    public void removEmployeeFromBranch(Employee employee){
        this.EmployeesInBranch.remove(employee.getID());
    }


    @Override
    public String toString() {
        return "Branch name: " + name + '\n' +
                "address: " + address + '\n' +
                "branch Num: " + branchNum +'\n' +
                "managerEmployee: " + managerEmployee ;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o instanceof Branch) {
            return false;
        }
        Branch othre = (Branch) o;
        return this.getBranchName()==othre.getBranchName();
    }
}
