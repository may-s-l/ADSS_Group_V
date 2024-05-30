package dev.src.domain;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Shift {

    private LocalDate date;
    private Enums.Shift_type st;

    private MyMap<Job,List<Employee>> EmployeesInShift;

    public Shift(LocalDate date,String shift_type) {
        this.date = date;
        this.st = Enums.Shift_type.valueOf(shift_type);
        this.EmployeesInShift=new MyMap<Job,List<Employee>>();
    }

    public void addEmployeeToShift(Employee employee, Job job) {

        if (!EmployeesInShift.containsKey(job)) {
            EmployeesInShift.put(job, new ArrayList<>());
        }

        EmployeesInShift.get(job).add(employee);
    }

    public void removeEmployeeFromShift(Employee employee, Job job) {
        if (EmployeesInShift.containsKey(job)) {
            EmployeesInShift.get(job).remove(employee);
            if (EmployeesInShift.get(job).isEmpty()) {
                EmployeesInShift.remove(job);
            }
        }
    }




    public MyMap<Job, List<Employee>> getEmployeesInShift() {
        return EmployeesInShift;
    }

    public void setEmployeesInShift(MyMap<Job, List<Employee>> employeesInShift) {
        EmployeesInShift = employeesInShift;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Enums.Shift_type getSt() {
        return st;
    }

    public void setSt(Enums.Shift_type st) {
        this.st=st;
    }
}