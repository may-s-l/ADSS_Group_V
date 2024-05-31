package dev.src.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.*;

public class Week {
    private int WeekNUM;
    private LocalDate start_date;
    private LocalDate End_date;
    private MyMap<LocalDate,Day> DayInWEEK;

    public Week(LocalDate start_date) {
        this.start_date = start_date;
        this.DayInWEEK = new MyMap<LocalDate, Day>();
        LocalDate date = start_date;
        Day d = null;
        for (int i = 0; i < 7; i++) {
            if (date.getDayOfWeek() != DayOfWeek.SATURDAY) {
                d = new Day(false, date);
                this.DayInWEEK.put(date, d);
                if (date.getDayOfWeek() == DayOfWeek.TUESDAY) {
                    WeekFields weekFields = WeekFields.of(Locale.getDefault());
                    this.WeekNUM = date.get(weekFields.weekOfWeekBasedYear());
                }
            } else {
                d = new Day(true, date);
                this.DayInWEEK.put(date, d);
                this.End_date = date;
            }
            date = date.plusDays(1);
        }
    }

    public Day getDayOfWeek(LocalDate date){
        return this.DayInWEEK.get(date);
    }

    @Override
    public String toString() {
        return
                "Week number: " + WeekNUM + " " +start_date + "-" + End_date +
                "\n" + DayInWEEK ;
    }
}


