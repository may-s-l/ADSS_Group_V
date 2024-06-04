package dev.src.Domain;

import dev.src.Domain.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class Week {
    private int weekNUM;
    private LocalDate start_date;
    private LocalDate end_date;
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
                    this.weekNUM = date.get(weekFields.weekOfWeekBasedYear());
                }
            } else {
                d = new Day(true, date);
                this.DayInWEEK.put(date, d);
                this.end_date = date;
            }
            date = date.plusDays(1);
        }
    }

    public Day getDayOfWeek(LocalDate date){
        return this.DayInWEEK.get(date);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Week number: ").append(weekNUM)
                .append(" ").append(start_date).append(" - ").append(end_date).append("\n");

        for (LocalDate date : DayInWEEK.getKeys()) {
            sb.append(DayInWEEK.get(date)).append("\n");
        }

        return sb.toString();
    }

    public String weekInTableToShow() {
        StringBuilder sb = new StringBuilder();
        sb.append("Week number: ").append(weekNUM).append("\n");
        sb.append("Start date: ").append(start_date).append("\n");
        sb.append("End date: ").append(end_date).append("\n");
        sb.append("Day\t\tDate\t\tMorning Shift\t\tEvening Shift\n");
        sb.append("--------------------------------------------------------\n");

        for (LocalDate date : DayInWEEK.getKeys()) {
            Day day = DayInWEEK.get(date);
            sb.append(day.getDayOfWeek()).append("\t\t")
                    .append(day.getDate()).append("\t\t");

            if (day.isIsdayofrest()) {
                sb.append("Day off").append("\n");
            } else {
                sb.append(day.getShiftsInDay()[0].toString()).append("\t\t")
                        .append(day.getShiftsInDay()[1].toString()).append("\n");
            }
        }

        return sb.toString();
    }
}

