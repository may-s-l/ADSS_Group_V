package dev.src.services;
import dev.src.domain.*;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import static java.time.DayOfWeek.*;

public class HRManagerShiftService {

    HashMap<Integer,List<Shift>> Shifts_temp_database;
    HashMap<LocalDate,List<Constraint>> Constraint_tamp_database;
    public HRManagerShiftService() {
        this.Shifts_temp_database = new HashMap<Integer,List<Shift>>();
        this.Constraint_tamp_database=new HashMap<LocalDate,List<Constraint>>();
    }


//    public <Integer,List<Shift>> createweekCalenderShift(){
//
//         if(!isItTheTIMEtoAssignmenttToShifts()){
//             //להוסיף פונקציה שתעשה לנו את ההפסה שעדין לא ניתן לשבץ כי רק ימי חמישי ושישי ניתן להרכיב לוח לשבוע הבא
//             return null;
//         }
//         LocalDate today= LocalDate.now();
//         while (today.getDayOfWeek()!=SUNDAY){
//             today=today.plusDays(1);
//         }
//         for (int i=0;i<7;i++){
//             MornnigShift mornnigShift= new MornnigShift(today.plusDays(i));
//             EvningShift evningShift=new EvningShift(today.plusDays(i));
//
//
//         }
//
//
//
//
//    }

    public boolean isItTheTIMEtoAssignmenttToShifts(){
        LocalDate today= LocalDate.now();
        DayOfWeek week_day=today.getDayOfWeek();
        if(week_day==THURSDAY|week_day==FRIDAY){
            return true;
        }
        return false;
    }
}
