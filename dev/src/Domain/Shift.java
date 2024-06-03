package dev.src.Domain;

import dev.src.Domain.*;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class Shift {

    private MyMap<Job,List<Domain.Employee>> employee_in_shift;
    private Time start_time;
    private Time end_time;
    private LocalDate date;

    public Shift() {
        this.start_time =null;
        this.end_time=null;
        this.date=null;
        this.employee_in_shift=new MyMap<Job,List<Domain.Employee>>();

    }
    public Shift(Time start_time,Time end_time,LocalDate date){
        this.date=date;
        this.start_time=start_time;
        this.end_time=end_time;
        this.employee_in_shift=new MyMap<Job,List<Domain.Employee>>();
    }

    public Time getStart_time() {
        return start_time;
    }

    public void setStart_time(Time start_time) {
        this.start_time = start_time;
    }

    public Time getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Time end_time) {
        this.end_time = end_time;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public MyMap<Job,List<Domain.Employee>> getEmployee_in_shift() {
        return employee_in_shift;
    }

    public void setEmployee_in_shift(MyMap<Job,List<Domain.Employee>> employee_in_shift) {
        this.employee_in_shift = employee_in_shift;
    }


    public void addEmployeeToShift(Domain.Employee employee, Job job) {
        if (!employee_in_shift.containsKey(job)) {
            employee_in_shift.put(job, new ArrayList<>());
        }
        employee_in_shift.get(job).add(employee);
    }

    public void removeEmployeeFromShift(Domain.Employee employee, Job job) {
        if (employee_in_shift.containsKey(job)) {
            employee_in_shift.get(job).remove(employee);
            if (employee_in_shift.get(job).isEmpty()) {
                employee_in_shift.remove(job);
            }
        }
    }

    public void removeEmployeeFromShift(Domain.Employee employee){
        Set<Job> jobs=this.employee_in_shift.getKeys();
        if (jobs!=null){
            for(Job j:jobs){
                List<Domain.Employee>empl=this.employee_in_shift.get(j);
                if(empl.contains(employee)){
                    empl.remove(employee);
                }
                if (empl.isEmpty()) {
                    employee_in_shift.remove(j);
                }
            }
        }
    }

    public boolean addJobToShift(Job job){
        if(job==null){
            return false;
        }
        Set<Job> jobs=this.employee_in_shift.getKeys();
        if(jobs.contains(job)){
            return false;
        }
        List<Domain.Employee>employeeList=new ArrayList<Domain.Employee>();
        employee_in_shift.put(job,employeeList);
        return true;
    }

    public Set<Job> getAllJobInShift(){
        return this.employee_in_shift.getKeys();
    }

    @Override
    public String toString() {
        return   start_time + " - " + end_time+"\n"+this.employee_in_shift+"\n";
    }
}
