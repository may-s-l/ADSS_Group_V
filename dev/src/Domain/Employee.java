package dev.src.Domain;

import dev.src.Data.DaoM.EmployeeTDao;
import dev.src.Domain.Repository.ConstraintRep;
import dev.src.Domain.Repository.EJobsRep;


import java.security.Key;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Employee {
    private static int EmployeeNUM= EmployeeTDao.getInstance().getMaxEmployeeNUM()+1;
    private String Name;
    private String ID;
    private String Bank_account;
    private Branch Branch;
    private int employeeNum;
    private TermsOfEmployment terms;
    //private List<Job> Jobs;
    //private MyMap<LocalDate, Constraint> constraintMyMap;
    private ConstraintRep constraintMyMap;
    private EJobsRep Jobs;


    //constructor- gets all the data for employee
    public Employee(String name, String ID, String bank_account, Branch branch, TermsOfEmployment terms,Job job) {
        Name = name;
        this.ID = ID;
        Bank_account = bank_account;
        Branch = branch;
        this.terms = terms;
        Jobs = new EJobsRep(ID);
        Jobs.add(job);
        this.employeeNum =EmployeeNUM;
        EmployeeNUM+=1;
        constraintMyMap=null;
    }

    //constructor- gets all the data and terms for employee
    public Employee(String name, String ID, String bank_account, Branch branch, double vacationDay, LocalDate start_date, double salary, String job_type, String Salary_type, Job job) {
        Name = name;
        this.ID = ID;
        Bank_account = bank_account;
        Branch = branch;
        this.terms = new TermsOfEmployment(vacationDay,start_date,salary,job_type,Salary_type);
        Jobs = new EJobsRep(ID);
        Jobs.add(job);
        this.employeeNum =EmployeeNUM;
        EmployeeNUM+=1;
        constraintMyMap=null;
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

    public Branch getBranch() {
        return Branch;
    }

    public boolean setBranch(Branch branch) {
        if (branch==null){
            return false;
        }
        Branch = branch;
        return true;
    }


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

    public EJobsRep getJobs() {
        return Jobs;
    }

    public boolean setJobs(EJobsRep jobs) {
        if(jobs==null||jobs.getsize()==0){

            return false;
        }
        Jobs = jobs;
        return true;
    }

    public boolean addJob(Job job) {
        if(job==null){
            return false;
        }
        String S =Jobs.add(job);
        return S.equals("S");
    }

    public boolean employeeCanbe(Job job){
        return this.Jobs.find(job.getJobName())!=null;
    }

    public int getEmployeeNum() {
        return employeeNum;
    }

//    public MyMap<LocalDate, Constraint> getConstraintMyMap() {
//        return constraintMyMap;
//    }
//
//    public void setConstraintMyMap(MyMap<LocalDate, Constraint> constraintMyMap) {
//        this.constraintMyMap = constraintMyMap;
//    }

    public ConstraintRep getConstraintMyMap() {
    return constraintMyMap;
    }

    public void setConstraintMyMap(ConstraintRep constraintMyMap) {
        this.constraintMyMap = constraintMyMap;
    }


    public Constraint getConstraintByDate(LocalDate date) {
        if (this.constraintMyMap==null){
            return null;
        }
        String key = this.getID()+","+date.toString();
        Constraint c =constraintMyMap.find(key);
        return c;
    }

    public MyMap<LocalDate,Constraint> getFutureConstraintMap(){
        LocalDate today = LocalDate.now();
        MyMap<LocalDate,Constraint> MAP = new MyMap<>();
        LocalDate until = today.plusDays(31);
        String key = this.getID()+","+today.toString();
        while (!today.equals(until)) {
            Constraint C =this.constraintMyMap.find(key);
            if (C!=null) {
                MAP.put(today, C);
            }
            today=today.plusDays(1);
            key = this.getID() + "," + today.toString();
        }
        return MAP;
    }


    @Override
    public String toString() {
        return "Name:" + Name +" "+
                "employeeNum:" + employeeNum;
    }

    public String toStringfullinfo() {
        return "Name:" + Name + ", ID:" + ID + '\n' +
                "employeeNum:" + employeeNum +
                ", Bank_account:" + Bank_account + '\n' +
                "Branch:" + Branch.getBranchAddress() +
                ", Jobs:" + Jobs+ "\n"+
                "terms" + terms ;

    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o instanceof Employee) {
            return false;
        }
        Employee othre = (Employee) o;
        return this.getID()==othre.getID();
    }


    //////////////forDATABASE///////////
    public Employee (String name, String ID, String bank_account, Branch branch,int employeeNum,TermsOfEmployment termsOfEmployment) {
        Name = name;
        this.ID = ID;
        Bank_account = bank_account;
        Branch = branch;
        this.employeeNum =employeeNum;
        constraintMyMap=null;
        this.terms =null;
        this.Jobs =null;
    }
}
