package dev.src.domain;

import java.time.LocalDate;

public class ManagerEmployee extends Employee {
    public ManagerEmployee(String name, String ID, String bank_account, Branch branch, TermsOfEmployment terms, Job job) {
        super(name, ID, bank_account, branch, terms, job);
    }

    public ManagerEmployee(String name, String ID, String bank_account, Branch branch, double vacationday, LocalDate start_date, double salary, String jod_type, String Salary_type, Job job) {
        super(name, ID, bank_account, branch, vacationday, start_date, salary, jod_type, Salary_type, job);
    }

    public ManagerEmployee(String name, String ID, String bank_account, Branch branch, Job job) {
        super(name, ID, bank_account, branch, job);
    }
}