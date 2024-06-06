package dev.src.Domain;

import java.time.LocalDate;
public class ManagerEmployee extends Employee {
    public ManagerEmployee(String name, String ID, String bank_account, Branch branch, TermsOfEmployment terms, ManagementJob job) {
        super(name, ID, bank_account, branch, terms,(Domain.Job) job);
    }

    public ManagerEmployee(String name, String ID, String bank_account, Branch branch, double vacationday, LocalDate start_date, double salary, String jod_type, String Salary_type, ManagementJob job) {
        super(name, ID, bank_account, branch, vacationday, start_date, salary, jod_type, Salary_type,(Domain.Job) job);
    }

//    public ManagerEmployee(String name, String ID, String bank_account, Branch branch, ManagementJob job) {
//        super(name, ID, bank_account, branch,(Job) job);
//    }

}