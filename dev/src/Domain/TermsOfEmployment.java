package dev.src.Domain;

import dev.src.Domain.Enums.JobType;
import dev.src.Domain.Enums.SalaryType;

import java.time.LocalDate;

public class TermsOfEmployment {

    private Employee emp;
    private double vacationDay;
    private LocalDate Start_date;
    private LocalDate End_date;
    private double Salary;
    private JobType jt;
    private SalaryType st;

    public TermsOfEmployment(double vacationday, LocalDate start_date, double salary, String jt, String st) {
        this.vacationDay = vacationday;
        Start_date = start_date;
        End_date = null;
        Salary = salary;
        this.jt = JobType.valueOf(jt);
        this.st = SalaryType.valueOf(st);
        this.emp = null;
    }

    public TermsOfEmployment(double vacationday, LocalDate start_date, LocalDate END_date, double salary, String jt, String st) {
        this.vacationDay = vacationday;
        Start_date = start_date;
        End_date = END_date;
        Salary = salary;
        this.jt = JobType.valueOf(jt);
        this.st = SalaryType.valueOf(st);
        this.emp = null;
    }

    public Employee getEmp() {
        return emp;
    }

    public void setEmp(Employee emp) {
        if(this.emp==null)
            this.emp = emp;
    }

    public double getVacationDay() {
        return vacationDay;
    }

    public void setVacationDay(double vacationDay) {
        this.vacationDay = vacationDay;
    }

    public LocalDate getStart_date() {
        return Start_date;
    }

    public void setStart_date(LocalDate start_date) {
        Start_date = start_date;
    }

    public LocalDate getEnd_date() {
        return End_date;
    }

    public void setEnd_date(LocalDate end_date) {
        End_date = end_date;
    }

    public double getSalary() {
        return Salary;
    }

    public void setSalary(double salary) {
        Salary = salary;
    }

    public JobType getJt() {
        return jt;
    }

    public void setJt(String jt) {
        this.jt = JobType.valueOf(jt);
    }

    public SalaryType getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st =SalaryType.valueOf(st);
    }

    @Override
    public String toString() {
        String S= "Terms Of Employment: \n" +
                "Salary: " + Salary+"\n"+
                "vacation day:" + vacationDay + ", job type: " + jt + ", salary type:" + st+ "\n"+
                "Start date: " + Start_date + ", End date: ";
        if(this.End_date==null){
            S+="----";
        }
        else {
            S+= this.End_date;
        }
        return S;
    }

}
