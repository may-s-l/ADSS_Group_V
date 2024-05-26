package dev.src.domain;

import java.time.LocalDate;
import java.util.Date;

public class Employee {

    private String name;
    private String bank_accuont;
    private String ID;
    private Job job;
    private Float Salery;
    private LocalDate start_date;
    private LocalDate end_date;



    public Employee(String name, String bank_accuont, String ID, Job job, Float salery, LocalDate start_date) {
        this.name = name;
        this.bank_accuont = bank_accuont;
        this.ID = ID;
        this.job = job;
        this.Salery = salery;
        this.start_date=start_date;
        this.end_date=null;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBank_accuont() {
        return bank_accuont;
    }

    public void setBank_accuont(String bank_accuont) {
        this.bank_accuont = bank_accuont;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Float getSalery() {
        return Salery;
    }

    public void setSalery(Float salery) {
        Salery = salery;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }
    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
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
