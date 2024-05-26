package dev.src.domain;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;

public class EvningShift extends Shift {

    private static Time Ds_time = new Time(14,30,00);
    private static Time De_time=new Time(22,30,00);

    public EvningShift(LocalDate date){
        super(Ds_time,De_time,date);
    };

    public EvningShift(Time start_time,Time end_time,LocalDate date){
        super(start_time,end_time,date);
    }

    public static Time getDs_time() {
        return Ds_time;
    }

    public static void setDs_time(Time ds_time) {
        Ds_time = ds_time;
    }

    public static Time getDe_time() {
        return De_time;
    }

    public static void setDe_time(Time de_time) {
        De_time = de_time;
    }

}


