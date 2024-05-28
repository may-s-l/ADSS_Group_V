package dev.src.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Employee {

    private String name;
    private String bank_accuont;
    private String ID;
    private List<Job> jobs;
    private Float Salery;
    private LocalDate start_date;
    private LocalDate end_date;

    private Branch Branch;


    public Employee(String name, String bank_accuont, String ID, Job job, Float salery, LocalDate start_date,Branch Branch) {
        this.name = name;
        this.bank_accuont = bank_accuont;
        this.ID = ID;
        this.jobs = new ArrayList<Job>();
        this.jobs.add(job);
        this.Salery = salery;
        this.Branch =Branch;
        this.start_date=start_date;
        this.end_date=null;
    }

    public String getName() {
        return name;
    }

    public Boolean setName(String name) {
        if(name==null){
            return false;
        }
        this.name = name;
        return true;
    }

    public String getBank_accuont() {
        return bank_accuont;
    }

    public boolean setBank_accuont(String bank_accuont) {
        if(bank_accuont==null){
            return false;
        }
        this.bank_accuont = bank_accuont;
        return true;
    }

    public String getID() {
        return ID;
    }

    public boolean setID(String ID) {
        if(ID==null) {
            return false;
        }
        this.ID = ID;
        return true;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public boolean AddJob(Job job) {
        if(job==null||job instanceof ManagmantJob){
            return false;
        }
        this.jobs.add(job);
        return true;
    }

    public boolean RemoveJob(Job job) {
        if (job == null) {
            return false;
        }
        return this.jobs.remove(job);
    }

    public Float getSalery() {
        return Salery;
    }

    public boolean setSalery(Float salery) {
        if(salery==null||salery<=0) {
            return false ;
        }
        Salery = salery;
        return true;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public boolean setStart_date(LocalDate start_date) {
        if(start_date==null){
            return false;
        }
        this.start_date = start_date;
        return true;
    }
    public LocalDate getEnd_date() {
        return end_date;
    }

    public boolean setEnd_date(LocalDate end_date) {
        if(end_date==null||end_date.isBefore(this.start_date)) {
            return false;
        }
        this.end_date = end_date;
        return true;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public Branch getBranch() {
        return Branch;
    }

    public void setBranch(Branch branch) {
        Branch = branch;
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
