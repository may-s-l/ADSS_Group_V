package dev.src.services;
import dev.src.Temp_DataBase;
import dev.src.domain.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import static java.time.DayOfWeek.*;
public class HRManagerShiftController {
    private Temp_DataBase Temp_Database;

    public HRManagerShiftController() {
        this.Temp_Database=new Temp_DataBase();
    }

    public Week createWeekforassignment(){
        Week week= new Week();

    }


    public boolean isItTheTIMEtoAssignmenttToShifts(){
        LocalDate today= LocalDate.now();
        DayOfWeek week_day=today.getDayOfWeek();
        if(week_day==THURSDAY|week_day==FRIDAY){
            return true;
        }
        return false;
    }
}
