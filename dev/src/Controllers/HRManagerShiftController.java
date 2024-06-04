package dev.src.Controllers;
import dev.src.Domain.*;
import dev.src.Data.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;


public class HRManagerShiftController {

    private Temp_DataBase Temp_Database;

    private MyMap<String,Employee> employeeByBranch;
    private MyMap<Job,Integer> NumberofWorkersPerPosition;

    private String[3][]

    public HRManagerShiftController(Temp_DataBase Temp_DataBase) {
        this.Temp_Database=Temp_DataBase;
        this.employeeByBranch=new MyMap<String,Employee>();
        this.NumberofWorkersPerPosition =new MyMap<Job, Integer>();
    }
    public void getAllEmployeeByBranch(ManagerEmployee HRM){
        if(HRM==null){
            return;
        }
        Branch branch=HRM.getBranch();
        MyMap<String,Employee> employees =this.Temp_Database.getEmployees_temp_database();
        Set<String> employeeID = employees.getKeys();
        if(employeeID !=null){
            Employee emp ;
            for (String e :employeeID){
                emp = this.Temp_Database.getEmployeeByID(e);
                if(emp.getBranch()==branch){
                    this.employeeByBranch.put(e,emp);
                }
            }
        }
    }



    public Week createWeekforassignment(String date) {
        LocalDate Ldate = LocalDate.parse(date);
        if (Ldate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            Week week = new Week(Ldate);
            Day day;
            for (int i = 0; i < 7; i++) {
                day = week.getDayOfWeek(Ldate.plusDays(i));
                if (!day.isIsdayofrest()) {
                    for (Job j : Temp_Database.getEmployeejobs_temp_database()) {
                        if (!(j instanceof ManagementJob)) {
                            day.getShiftsInDay()[0].addJobToShift(j);
                            day.getShiftsInDay()[1].addJobToShift(j);
                            Shift.ChangingtheDifultNumberOfemployeesPerJob(j,1);
                        }
                    }
                }
            }
            return week;

        }
        return null;
    }
//
//
//    public boolean isItTheTIMEtoAssignmenttToShifts(){
//        LocalDate today= LocalDate.now();
//        DayOfWeek week_day=today.getDayOfWeek();
//        if(week_day==THURSDAY|week_day==FRIDAY){
//            return true;
//        }
//        return false;
//    }

}
