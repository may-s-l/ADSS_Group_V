package dev.src.domain;

import java.time.LocalDate;

public class TermsOfEmployment {


    private double vacationDay;
    private LocalDate Start_date;
    private LocalDate End_date;
    private double Salary;
    private Enums.jod_type jt;
    private Enums.Salary_type st;

    public TermsOfEmployment(double vacationday, LocalDate start_date, double salary, String jt, String st) {
        this.vacationDay = vacationday;
        Start_date = start_date;
        End_date = null;
        Salary = salary;
        this.jt = Enums.jod_type.valueOf(jt);
        this.st = Enums.Salary_type.valueOf(st);
    }

    //לראות איך מכניסים את זה לקובץ קונפיגורציה
    public TermsOfEmployment() {
        this.vacationDay = 16;
        Start_date = LocalDate.now();
        End_date = null;
        Salary = 29.12;
        this.jt = Enums.jod_type.PART;
        this.st = Enums.Salary_type.HOURLY;
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

    public Enums.jod_type getJt() {
        return jt;
    }

    public void setJt(String jt) {
        this.jt = Enums.jod_type.valueOf(jt);
    }

    public Enums.Salary_type getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = Enums.Salary_type.valueOf(st);
    }

    @Override
    public String toString() {
        return "TermsOfEmployment{" +
                "vacationday=" + vacationDay +
                ", Start_date=" + Start_date +
                ", End_date=" + End_date +
                ", Salary=" + Salary +
                ", jt=" + jt +
                ", st=" + st +
                '}';
    }
}