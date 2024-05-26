package domain;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Shift {

    private List<Employee> employee_in_shift;
    private Time start_time;
    private Time end_time;
    private LocalDate date;

    public Shift() {
        this.start_time =null;
        this.end_time=null;
        this.date=null;
        this.employee_in_shift=new ArrayList<Employee>();

    }
    public Shift(Time start_time,Time end_time,LocalDate date){
        this.date=date;
        this.start_time=start_time;
        this.end_time=end_time;
        this.employee_in_shift=new ArrayList<Employee>();
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

    public List<Employee> getEmployee_in_shift() {
        return employee_in_shift;
    }

    public void setEmployee_in_shift(List<Employee> employee_in_shift) {
        this.employee_in_shift = employee_in_shift;
    }

    public void addEmployee_to_shift(Employee employee){
        this.employee_in_shift.add(employee);
    }

    public void removeEmployee_from_shift(Employee employee){
        this.employee_in_shift.remove(employee);
    }

    public void removeEmployee_from_shift(String IDemployee){
        for(Employee emp :this.employee_in_shift){
            if (emp.getID()==IDemployee){
                this.employee_in_shift.remove(emp);
                break;
            }
        }
    }
}
