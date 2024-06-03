package dev.src.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Employee {
    private static int EmployeeNUM=0;
    private String Name;
    private String ID;
    private String Bank_account;
    private Branch Branch;
    private int employeeNum;
    private TermsOfEmployment terms;
    private List<Job> Jobs;

    //constructor- gets all the data for employee
    public Employee(String name, String ID, String bank_account, Branch branch, TermsOfEmployment terms,Job job) {
        Name = name;
        this.ID = ID;
        Bank_account = bank_account;
        Branch = branch;
//        this.IS_shift_manager = IS_shift_manager;
        this.terms = terms;
        Jobs = new ArrayList<Job>();
        Jobs.add(job);
        this.employeeNum =EmployeeNUM;
        EmployeeNUM+=1;
    }

    //constructor- gets all the data for employee
    public Employee(String name, String ID, String bank_account, Branch branch, double vacationDay, LocalDate start_date, double salary, String jod_type, String Salary_type,Job job) {
        Name = name;
        this.ID = ID;
        Bank_account = bank_account;
        Branch = branch;
        this.terms = new TermsOfEmployment(vacationDay,start_date,salary,jod_type,Salary_type);
        Jobs = new ArrayList<Job>();
        Jobs.add(job);
        this.employeeNum =EmployeeNUM;
        EmployeeNUM+=1;
    }

    //default constructor when is shift false and basic term of employment
    public Employee(String name, String ID, String bank_account, Branch branch, Job job) {
        Name = name;
        this.ID = ID;
        Bank_account = bank_account;
        Branch = branch;
//        this.IS_shift_manager = false;
        this.terms = new TermsOfEmployment();
        Jobs = new ArrayList<Job>();
        Jobs.add(job);
        this.employeeNum =EmployeeNUM;
        EmployeeNUM+=1;
    }


    public String getName() {
        return Name;
    }

    public boolean setName(String name) {
        if (name==null||name.contains("[0-9]+")) {
            return false;
        }
        this.Name = name;
        return true;
    }

    public String getID() {
        return ID;
    }

    public boolean setID(String ID) {
        if (ID==null||ID.contains("[a-z,A-Z]+")||ID.length()!=6) {
            return false;
        }
        this.ID = ID;
        return true;
    }

    public String getBank_account() {
        return Bank_account;
    }

    public boolean setBank_account(String bank_account) {
        if (bank_account==null||bank_account.contains("[a-z,A-Z]+")||bank_account.length()!=8) {
            return false;
        }
        Bank_account = bank_account;
        return true;
    }

    public Branch getBranch_ID() {
        return Branch;
    }

    public boolean setBranch_ID(Branch branch) {
        if (branch==null){
            return false;
        }
        Branch = branch;
        return true;
    }

//    public boolean isIS_shift_manager() {
//        return IS_shift_manager;
//    }
//
//    public void setIS_shift_manager(boolean IS_shift_manager) {
//        this.IS_shift_manager = IS_shift_manager;
//    }

    public TermsOfEmployment getTerms() {
        return terms;
    }

    public boolean setTerms(TermsOfEmployment terms) {
        if(terms==null){
            return false;
        }
        this.terms = terms;
        return true;
    }

    public List<Job> getJobs() {
        return Jobs;
    }

    public boolean setJobs(List<Job> jobs) {
        if(jobs==null||jobs.isEmpty()){
            return false;
        }
        Jobs = jobs;
        return true;
    }

    public boolean employeeCanbe(Job job){
        return this.Jobs.contains(job);
    }

    public int getEmployeeNum() {
        return employeeNum;
    }

    public dev.src.domain.Branch getBranch() {
        return Branch;
    }

    public void setBranch(dev.src.domain.Branch branch) {
        Branch = branch;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "Name='" + Name + '\'' +
                ", ID='" + ID + '\'' +
                ", Bank_account='" + Bank_account + '\'' +
                ", Branch=" + Branch +
                ", employeeNum=" + employeeNum +
                ", terms=" + terms +
                ", Jobs=" + Jobs +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o instanceof Employee) {
            return false;
        }
        Employee othre = (Employee) o;
        return this.getID()==othre.getID();
    }
}